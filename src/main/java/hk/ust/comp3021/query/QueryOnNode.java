package hk.ust.comp3021.query;

import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class QueryOnNode {

    private HashMap<String, ASTModule> id2ASTModules;

    public QueryOnNode(HashMap<String, ASTModule> id2ASTModules) {
        this.id2ASTModules = id2ASTModules;
    }

    /**
     * TODO `findFuncWithArgGtN` find all functions whose # arguments > given `paramN` in all modules {@link QueryOnNode#id2ASTModules}
     *
     * @param paramN the number of arguments user expects
     * @return null as PA1, simply print out all functions that satisfy the requirements with format ModuleID_FuncName_LineNo
     * Hints1: use {@link ASTElement#filter(Predicate)} method to implement the function
     */
    public Consumer<Integer> findFuncWithArgGtN;


    /**
     * TODO `calculateOp2Nums` count the frequency of each operator in all modules {@link QueryOnNode#id2ASTModules}
     *
     * @param null
     * @return op2Num as PA1,the key is operator name, and value is the frequency
     * Hints1: use {@link ASTElement#forEach(Consumer)} method to implement the function
     */
    public Supplier<HashMap<String, Integer>> calculateOp2Nums;

    /**
     * TODO `calculateNode2Nums` count the frequency of each node in all modules {@link QueryOnNode#id2ASTModules}
     *
     * @param astID, a number to represent a specific AST or -1 for all
     * @return node2Nums as PA1,the key is node type, and value is the frequency
     * Hints1: use {@link ASTElement#groupingBy(Function, Collector)} method to implement the function
     * Hints2: if astID is invalid, return empty map
     */
    public Function<String, Map<String, Long>> calculateNode2Nums;

    /**
     * TODO `processNodeFreq` sort all functions in all modules {@link QueryOnNode#id2ASTModules} based on the number of nodes in FunctionDefStmt subtree
     *
     * @param null
     * @return a list of entries sorted in descending order where the key is function name 
     *         with format ModuleID_FuncName_LineNo, and value is the # nodes
     * Hints1: use {@link ASTElement#forEach(Consumer)} method to implement the function
     * Hint2: note that `countChildren` method is removed, please do not use this method
     */
    public Supplier<List<Map.Entry<String, Integer>>> processNodeFreq;



}
