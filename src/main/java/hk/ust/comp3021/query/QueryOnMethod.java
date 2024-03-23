package hk.ust.comp3021.query;

import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;

public class QueryOnMethod {
    /**
     * IMPORTANT: for all test cases for QueryOnMethod, we would not involve class
     */
    ASTModule module = null;

    public QueryOnMethod(ASTModule module) {
        this.module = module;
    }

    /**
     * TODO `findEqualCompareInFunc` find all comparison expression with operator \"==\" in current module {@link QueryOnMethod#module}
     *
     * @param funcName the name of the function to be queried
     * @return results List of strings where each represents a comparison expression, in format, lineNo:colOffset-endLineNo:endColOffset
     * Hints1: if func does not exist in current module, return empty list
     * Hints2: use {@link ASTElement#filter(Predicate)} method to implement the function
     */
    public Function<String, List<String>> findEqualCompareInFunc;

    /**
     * TODO `findFuncWithBoolParam` find all functions that use boolean parameter as if condition in current module {@link QueryOnMethod#module}
     *
     * @param null
     * @return List of strings where each represents the name of function that satisfy the requirements
     * Hints1: the boolean parameter is annotated with type bool
     * Hints2: as long as the boolean parameter shown in the {@link IfStmt#getTest()} expression, we say it's used
     * Hints3: use {@link ASTElement#filter(Predicate)} method to implement the function
     */
    public Supplier<List<String>> findFuncWithBoolParam;


    /**
     * TODO Given func name `funcName`, `findUnusedParamInFunc` find all unused parameter in current module {@link QueryOnMethod#module}
     *
     * @param funcName to be queried function name
     * @return results List of strings where each represents the name of an unused parameter
     * Hints1: if a variable is read, the ctx is `Load`, otherwise `Store` if written
     * Hints2: for the case where variable is written before read, we use line number and col offset to
     * check if the write operation is conducted before the first place where the parameter is read
     * Hints3: use {@link ASTElement#filter(Predicate)} method to implement the function
     * Hints4: if func does not exist in current module, return empty list
     */
    public Function<String, List<String>> findUnusedParamInFunc;


    /**
     * TODO Given func name `funcName`, `findDirectCalledOtherB` find all functions being direct called by functions other than B in current module {@link QueryOnMethod#module}
     *
     * @param funcName the name of function B
     * @return results List of strings where each represents the name of a function that satisfy the requirement
     * Hints1: there is no class in the test cases for this code pattern, thus, no function names such as a.b()
     * Hints2: for a call expr foo(), we can directly use the called function name foo to location the implementation
     * Hints3: use {@link ASTElement#filter(Predicate)} method to implement the function
     * Hints4: if func does not exist in current module, return empty list
     */
    public Function<String, List<String>> findDirectCalledOtherB;

    /**
     * TODO Given func name `funcNameA` and `funcNameB`, `answerIfACalledB` checks if A calls B directly or transitively in current module {@link QueryOnMethod#module}
     *
     * @param funcNameA the name of function A
     * @param funcNameB the name of function B
     * @return a boolean return value to answer yes or no
     * Hints1: there is no class in the test cases for this code pattern, thus, no function names such as a.b()
     * Hints2: for a call expr foo(), we can directly use the called function name foo to location the implementation
     * Hints3: use {@link ASTElement#filter(Predicate)} method to implement the function
     */

    public BiPredicate<String, String> answerIfACalledB;


}
