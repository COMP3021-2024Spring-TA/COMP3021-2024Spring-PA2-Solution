package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.BoolOpExpr;
import hk.ust.comp3021.misc.ASTElement;
import hk.ust.comp3021.stmt.FunctionDefStmt;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class QueryOnMethod {
    
    public Function<String, List<BoolOpExpr>> findEqualCompareInFunc = funcName -> {
        
    };
    
    public Supplier<List<FunctionDefStmt>> findFuncWithBoolParam = () -> {
        
    };
    
    public Function<String, List<String>> findUnusedParamInFunc = funcName -> {
        
    };
    
    
    public Function<String, List<String>> findDirectCalledOtherB = funcName -> {
        
    };
    
    public Predicate<String> answerIfACalledB = (funcNameA, funcNameB) -> {
        
    };
    
    
}
