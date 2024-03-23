package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class QueryOnNode {

    private HashMap<String, ASTModule> id2ASTModules;

    public QueryOnNode(HashMap<String, ASTModule> id2ASTModules) {
        this.id2ASTModules = id2ASTModules;
    }
    
    public Consumer<Integer> findFuncWithArgGtN = paramN -> {
        id2ASTModules.values().forEach(module -> {
            module.filter(node -> node instanceof FunctionDefStmt)
                    .stream().filter(func -> ((FunctionDefStmt) func).getParamNum() >= paramN)
                    .forEach(func -> System.out.println(module.getASTID() + "_" + ((FunctionDefStmt) func).getName() + "_" + func.getLineNo()));
        });
    };

    public Supplier<HashMap<String, Integer>> calculateOp2Nums = () -> {
        HashMap<String, Integer> op2Num = new HashMap<>();

        Consumer<ASTElement> binOp = node -> {
            if (node instanceof BinOpExpr) {
                op2Num.merge(((BinOpExpr) node).getOp().getOperatorName(), 1, Integer::sum);
            }
        };

        Consumer<ASTElement> boolOp = node -> {
            if (node instanceof BoolOpExpr) {
                op2Num.merge(((BoolOpExpr) node).getOp().getOperatorName(), 1, Integer::sum);
            }
        };

        Consumer<ASTElement> unaryOp = node -> {
            if (node instanceof UnaryOpExpr) {
                op2Num.merge(((UnaryOpExpr) node).getOp().getOperatorName(), 1, Integer::sum);
            }
        };

        Consumer<ASTElement> augAssignOp = node -> {
            if (node instanceof AugAssignStmt) {
                ASTEnumOp op = ((AugAssignStmt) node).getOp();
                op2Num.merge(op.getOperatorName(), 1, Integer::sum);
            }
        };

        id2ASTModules.values().forEach(module -> {
            module.forEach(binOp.andThen(boolOp).andThen(unaryOp).andThen(augAssignOp));
        });
        return op2Num;
    };


    public Function<String, Map<String, Long>> calculateNode2Nums = astID -> {
        // TODO: complete the definition of the method `calculateNode2Nums`
        return this.id2ASTModules.get(astID).groupingBy(ASTElement::getNodeType, Collectors.counting());
    };

    public Supplier<HashMap<String, Integer>> processNodeFreq = () -> {
        HashMap<String, Integer> funcName2NodeNum = new HashMap<>();

        id2ASTModules.values().forEach(module -> {
            module.filter(node -> node instanceof FunctionDefStmt)
                    .stream()
                    .map(func -> {
                        final int[] nodeCount = {0};
                        func.forEach(node -> nodeCount[0]++);
                        String uniqueFuncName = module.getASTID() + "_" +
                                ((FunctionDefStmt) func).getName() + "_" + func.getLineNo();
                        return new AbstractMap.SimpleEntry<>(uniqueFuncName, nodeCount[0]);
                    })
                    .forEach(entry -> funcName2NodeNum.merge(
                            entry.getKey(),
                            entry.getValue(),
                            (value1, value2) -> value1));
        });
        return funcName2NodeNum;
    };



}
