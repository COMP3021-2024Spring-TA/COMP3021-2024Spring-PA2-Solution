package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.BoolOpExpr;
import hk.ust.comp3021.expr.CompareExpr;
import hk.ust.comp3021.expr.NameExpr;
import hk.ust.comp3021.misc.ASTArguments;
import hk.ust.comp3021.misc.ASTElement;
import hk.ust.comp3021.stmt.FunctionDefStmt;
import hk.ust.comp3021.utils.ASTModule;

import javax.naming.Name;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class QueryOnMethod {

    private HashMap<String, ASTModule> id2ASTModules = null;
    
    public QueryOnMethod(HashMap<String, ASTModule> id2ASTModules) {
        this.id2ASTModules = id2ASTModules;
    }
    
    public Function<String, List<String>> findEqualCompareInFunc = funcName -> {
        List<ASTElement> nameMatchedFunc = new ArrayList<>();
        id2ASTModules.values().forEach(module -> {
            nameMatchedFunc.addAll(module.filter(node -> node instanceof FunctionDefStmt)
                    .stream()
                    .filter(func -> funcName.equals(
                            module.getASTID() + "_" + ((FunctionDefStmt) func).getName() + "_" + func.getLineNo()))
                    .collect(Collectors.toList()));
        });

        List<String> results = new ArrayList<>();
        nameMatchedFunc.forEach(func -> {
            results.addAll(func.filter(node -> node instanceof CompareExpr)
                    .stream()
                    .map(expr -> (CompareExpr) expr)
                    .filter(expr -> expr.getOps().stream().anyMatch(op -> op.getOperatorName().equals("Eq")))
                    .map(Object::toString) 
                    .toList());
        });
        return results;
    };
    
    public Supplier<List<String>> findFuncWithBoolParam = () -> {
        Predicate<ASTElement> hasBoolName = annotation -> annotation instanceof NameExpr
                && ((NameExpr) annotation).getId().equals("bool");

        Predicate<ASTElement> hasAstArg = func -> func
                .filter(node -> node instanceof ASTArguments.ASTArg)
                .stream()
                .map(arg -> (ASTArguments.ASTArg) arg)
                .filter(arg -> arg.getAnnotation() != null)
                .anyMatch(arg -> hasBoolName.test(arg.getAnnotation()));


        List<String> results = new ArrayList<>();
        id2ASTModules.values().stream().forEach(module -> {
            results.addAll(module.filter(node -> node instanceof FunctionDefStmt)
                    .stream()
                    .filter(func -> hasAstArg.test(func))
                    .map(func -> (FunctionDefStmt) func)
                    .map(func -> module.getASTID() + "_" + func.getName() + "_" + func.getLineNo())
                    .collect(Collectors.toList()));

        });
        return results;
    };
    
    public Function<String, List<String>> findUnusedParamInFunc = funcName -> {
        List<String> results = new ArrayList<>();
        
        return results;
    };
    
    
    public Function<String, List<String>> findDirectCalledOtherB = funcName -> {
        List<String> results = new ArrayList<>();
        
        return results;
    };
    
    public BiPredicate<String, String> answerIfACalledB = (funcNameA, funcNameB) -> {
        
        return false;
    };
    
    
}
