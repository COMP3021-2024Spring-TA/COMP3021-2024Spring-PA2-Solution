package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;

public class QueryOnClass {

    ASTModule module = null;

    public QueryOnClass(ASTModule module) {
        this.module = module;
    }
    
    /**
     * TODO Given class name `className`, `findSuperClasses` finds all the super classes of
     * it in the current module {@link QueryOnClass#module}
     * @param className the name of class 
     * @return results List of strings where each represents the name of a class that satisfy the requirement
     * Hint1: you can implement a helper function which receives the class name and 
     * returns the ClassDefStmt object.
     * Hint2: You can first find the direct super classes, and then RECURSIVELY finds the
     * super classes of the direct super classes.
     */
    public Function<String, List<String>> findSuperClasses;

     /**
     * TODO Given class name `classA` and `classB` representing two classes A and B,
     *  `haveSuperClass` checks whether B is a super class of A in the current module.
     *  {@link QueryOnClass#module}
     * @param classA the name of class A.
     * @param classB the name of class B
     * @return returns true if B is A's super class, otherwise false.
     * Hint1: you can just reuse {@link QueryOnClass#findSuperClasses}
     */
    public BiFunction<String, String, Boolean> haveSuperClass;


    /**
     * TODO Returns all the overriding methods within the current module 
     * {@link QueryOnClass#module}
     * @return results List of strings of the names of overriding methods. 
     * Note: If there are multiple overriding functions with the same name, please include name
     * in the result list for MULTIPLE times. You can refer to the test case.
     * Hint1: you can implement a helper function that first finds the methods that a class
     *  directly contains.
     * Hint2: you can reuse the results of {@link QueryOnClass#findSuperClasses}
     */
    public Supplier<List<String>> findOverridingMethods;

    /**
     * TODO Returns all the methods that a class possesses in the current module
     * {@link QueryOnClass#module}
     * @param className the name of the class
     * @return results List of strings of names of the methods it possesses
     * Note: the same function name should appear in the list only once, due to overriding.
     * Hint1: you can implement a helper function that first finds the methods that a class
     *  directly contains.
     * Hint2: you can reuse the results of {@link QueryOnClass#findSuperClasses}
     */
    public Function<String, List<String>> findAllMethods;

     /**
     * TODO Returns all the classes that possesses a main function in the current module
     * {@link QueryOnClass#module}
     * @return results List of strings of names of the classes
     * Hint1: You can reuse the results of {@link QueryOnClass#findAllMethods}
     */
    public Supplier<List<String>> findClassesWithMain;

}

