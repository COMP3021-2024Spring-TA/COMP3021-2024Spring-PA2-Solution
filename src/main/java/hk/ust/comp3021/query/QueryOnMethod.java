package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class QueryOnMethod {
    
    ASTModule module = null;
    
    public QueryOnMethod(ASTModule module) {
        this.module = module;
    }
        
    public Function<String, Optional<ASTElement>> findFuncInModule = name ->
            module.filter(node -> node instanceof FunctionDefStmt)
                    .stream()
                    .filter(func -> name.equals(module.getASTID() + "_" + ((FunctionDefStmt) func).getName()))
                    .findFirst();
    
    /*
    * @ param
    * @ return
     */
    // TODO: please finish the implementation of this function
    public Function<String, List<String>> findEqualCompareInFunc = funcName -> {
        List<String> results = new ArrayList<>();
        
        if (findFuncInModule.apply(funcName).isPresent()) {
            ASTElement func = findFuncInModule.apply(funcName).get();
            results.addAll(func.filter(node -> node instanceof CompareExpr)
                    .stream()
                    .map(expr -> (CompareExpr) expr)
                    .filter(expr -> expr.getOps().stream().anyMatch(op -> op.getOperatorName().equals("Eq")))
                    .map(expr -> expr.getLineNo() + ":" + expr.getColOffset() + "-" +
                            expr.getEndLineNo() + ":" + expr.getEndColOffset())
                    .toList());
        }
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


        return module.filter(node -> node instanceof FunctionDefStmt)
                .stream()
                .filter(func -> hasAstArg.test(func))
                .map(func -> (FunctionDefStmt) func)
                .map(func -> module.getASTID() + "_" + func.getName() + "_" + func.getLineNo())
                .collect(Collectors.toList());
    };
    
    public Function<String, List<String>> findUnusedParamInFunc = funcName -> {
        List<String> results = new ArrayList<>();
        
        // find all functions whose name matches funcName
        if (findFuncInModule.apply(funcName).isPresent()) {
            ASTElement func = findFuncInModule.apply(funcName).get();
            Map<String, ASTElement> arg2FirstReadLoc = new HashMap<>();

            Map<String, List<ASTElement>> readVariables = func.filter(node -> node instanceof ASTArguments.ASTArg)
                    .stream()
                    .map(arg -> ((ASTArguments.ASTArg) arg).getArg())
                    .collect(Collectors.toMap(argName -> argName, 
                            argName -> new ArrayList<>(func.filter(readVar -> readVar instanceof NameExpr
                                                    && ((NameExpr) readVar).getId().equals(argName)
                                                    && ((NameExpr) readVar).getCtx().getOp() == ASTEnumOp.ASTOperator.Ctx_Load))
                            )
                    );
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
            
            arg2FirstReadLoc.entrySet()
                    .stream()
                    .filter(entry -> func.filter(writeVar -> writeVar instanceof NameExpr)  // there is write before first read
                            .stream()
                            .map(writeVar -> (NameExpr) writeVar)
                            .anyMatch(writeVar -> writeVar.getCtx().getOp() == ASTEnumOp.ASTOperator.Ctx_Store
                                    && writeVar.getId().equals(((NameExpr) entry.getValue()).getId())
                                    && writeVar.getLineNo() < entry.getValue().getLineNo()
                                    && writeVar.getColOffset() < entry.getValue().getColOffset())
                    )
                    .forEach(entry -> results.add(entry.getKey()));
        }
        
        return results;
    };
    
    public Function<ASTElement, Optional<String>> getCallExprName = callexpr ->
            callexpr.filter(node -> node instanceof NameExpr)
                    .stream()
                    .map(node -> (NameExpr) node)
                    .map(node -> node.getId()).findFirst();
    
    public Function<ASTElement, List<ASTElement>> findAllCalledFuncs = func -> 
            func.filter(expr -> expr instanceof CallExpr).stream().collect(Collectors.toList());
    
    public Function<String, List<String>> findDirectCalledOtherB = funcName -> {
        List<String> results = new ArrayList<>();
        
        Map<FunctionDefStmt, List<ASTElement>> func2CalledFuncs =
                module.filter(func -> func instanceof FunctionDefStmt)
                        .stream()
                        .map(func -> (FunctionDefStmt) func)
                        .collect(Collectors.toMap(func -> func,
                                func -> findAllCalledFuncs.apply(func)));
        Map<String, List<String>> callee2AllCallers = new HashMap<>();
        func2CalledFuncs.entrySet()
                .stream()
                .forEach(entry -> entry.getValue().forEach(callee -> {
                    if (getCallExprName.apply(callee).isPresent()) {
                        
                        String calleeName = getCallExprName.apply(callee).get();
                        if (findFuncInModule.apply(calleeName).isPresent()) {
                            if (!callee2AllCallers.containsKey(calleeName)) {
                                callee2AllCallers.put(calleeName, new ArrayList<>());
                            }
                            callee2AllCallers.get(calleeName).add(entry.getKey().getName());
                        }
                    }
                }));
        results.addAll(callee2AllCallers
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue()
                        .stream()
                        .filter(callerName -> !callerName.equals(funcName))
                        .collect(Collectors.toList()).isEmpty())
                .map(Map.Entry::getKey).collect(Collectors.toList()));
        return results;
    };
    
    
    public BiPredicate<String, String> answerIfACalledB = (funcNameA, funcNameB) -> {
        String moduleIdA = Arrays.stream(funcNameA.split("_")).findFirst().get();
        String moduleIdB = Arrays.stream(funcNameB.split("_")).findFirst().get();
        if (!moduleIdA.equals(moduleIdB)) {
            return false;
        }
        List<String> tobeProcessed = new ArrayList<>();
        tobeProcessed.add(funcNameA);

        while (!tobeProcessed.isEmpty()) {
            String curFuncName = tobeProcessed.get(0);
            tobeProcessed.remove(0);

            if (curFuncName.equals(funcNameB)) {
                return true;
            }
            if (!findFuncInModule.apply(curFuncName).isPresent()) {
                continue;
            }

            ASTElement curFuncNode = findFuncInModule.apply(curFuncName).get();
            for (ASTElement called : findAllCalledFuncs.apply(curFuncNode)) {
                if (!getCallExprName.apply(called).isPresent()) {
                    continue;
                }
                String calledFuncName = getCallExprName.apply(called).get();
                tobeProcessed.add(moduleIdA + "_" + calledFuncName);
            }
        }
        return false;
    };
    
    
}
