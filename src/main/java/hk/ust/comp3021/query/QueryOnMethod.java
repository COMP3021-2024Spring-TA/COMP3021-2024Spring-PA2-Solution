package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class QueryOnMethod {

    private HashMap<String, ASTModule> id2ASTModules = null;
    
    public QueryOnMethod(HashMap<String, ASTModule> id2ASTModules) {
        this.id2ASTModules = id2ASTModules;
    }
    
    public Function<String, List<String>> findEqualCompareInFunc = funcName -> {
        List<ASTElement> nameMatchedFunc = new ArrayList<>();
        id2ASTModules.values().forEach(module -> {
            nameMatchedFunc.addAll(module
                    .filter(node -> node instanceof FunctionDefStmt)
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
        List<ASTElement> nameMatchedFunc = new ArrayList<>();
        
        // find all functions whose name matches funcName
        id2ASTModules.values().forEach(module -> {
            nameMatchedFunc.addAll(module
                    .filter(node -> node instanceof FunctionDefStmt)
                    .stream()
                    .filter(func -> funcName.equals(
                            module.getASTID() + "_" + ((FunctionDefStmt) func).getName() + "_" + func.getLineNo()))
                    .collect(Collectors.toList()));
        });
        
        Map<ASTElement, Map<String, ASTElement>> func2Arg2FirstReadLoc = new HashMap<>();
        nameMatchedFunc.forEach(
                func -> {
                    Map<String, List<ASTElement>> readVariables =
                            func.filter(node -> node instanceof ASTArguments.ASTArg)
                                    .stream()
                                    .map(arg -> ((ASTArguments.ASTArg) arg).getArg())
                                    .collect(Collectors.toMap(
                                                    argName -> argName,
                                                    argName -> func
                                                            .filter(readVar -> readVar instanceof NameExpr
                                                                && ((NameExpr) readVar).getId().equals(argName)
                                                                && ((NameExpr) readVar).getCtx().getOp() == ASTEnumOp.ASTOperator.Ctx_Load)
                                                            .stream()
                                                            .collect(Collectors.toList())
                                            )
                                    );
                    Map<String, ASTElement> arg2FirstReadLoc = new HashMap<>();
                    readVariables.forEach((argName, readLocs) -> {
                        if (readLocs.isEmpty()) {
                            results.add(argName);
                        } else {
                            ASTElement firstRead = readLocs.stream()
                                    .min((loc1, loc2) -> {
                                        int lineComparison = Integer.compare(loc1.getLineNo(), loc2.getLineNo());
                                        if (lineComparison == 0) {
                                            return Integer.compare(loc1.getColOffset(), loc2.getColOffset());
                                        }
                                        return lineComparison;
                                    })
                                    .orElse(null);
                            if (firstRead != null) {
                                arg2FirstReadLoc.put(argName, firstRead);
                            }
                        }
                    });
                    func2Arg2FirstReadLoc.put(func, arg2FirstReadLoc);
                });


        func2Arg2FirstReadLoc.forEach((func, readLocs) -> {
            readLocs.entrySet().stream().filter(entry ->
                    // there is write before first read
                    func.filter(writeVar -> writeVar instanceof NameExpr)
                            .stream()
                            .map(writeVar -> (NameExpr) writeVar)
                            .anyMatch(writeVar -> writeVar.getCtx().getOp() == ASTEnumOp.ASTOperator.Ctx_Store
                                    && writeVar.getId().equals(((NameExpr) entry.getValue()).getId())
                                    && writeVar.getLineNo() < entry.getValue().getLineNo()
                                    && writeVar.getColOffset() < entry.getValue().getColOffset())
            ).forEach(entry -> results.add(entry.getKey()));
        });
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
