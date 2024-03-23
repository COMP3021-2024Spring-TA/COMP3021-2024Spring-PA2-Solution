package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;

public class QueryOnClass {
    // TODO: bowen, code pattern 6-10 is written here

    ASTModule module = null;

    public QueryOnClass(ASTModule module) {
        this.module = module;
    }

    // Helper
    private BiFunction<String, ASTModule, Optional<ASTElement>> findClassInModule = (name, curModule) ->
        curModule.filter(node-> node instanceof ClassDefStmt)
            .stream()
            .filter(clazz -> name.equals(((ClassDefStmt) clazz).getName()))
            .findFirst();
    
    // TODO
    public Function<String, List<String>> findSuperClasses = (className) -> {
        List<String> results = new ArrayList<>();
        if(findClassInModule.apply(className, module).isPresent()) {
            ClassDefStmt clazz = (ClassDefStmt) findClassInModule.apply(className, module).get();
            clazz.getChildren().forEach(node -> {
                if(node instanceof NameExpr) {
                    String superClass = ((NameExpr) node).getId();
                    results.add(superClass);
                    List<String> recSuperClasses = this.findSuperClasses.apply(superClass);
                    results.addAll(recSuperClasses);
                }
            }); 
        }
        return results;
    };

    // TODO
    public BiFunction<String, String, Boolean> haveSuperClass = (classA, classB) -> {
        return findSuperClasses.apply(classA).contains(classB);
    };


    // Helper
    private Function<String, List<String>> findDirectMethods = (classA) -> {
        List<String> results = new ArrayList<String>();
        ClassDefStmt clazz = (ClassDefStmt) findClassInModule.apply(classA, module).get();
        clazz.getChildren().forEach(node -> {
            if(node instanceof FunctionDefStmt) {
                results.add(((FunctionDefStmt) node).getName());
            }
        });
        return results;
    };

    // TODO
    public Supplier<List<String>> findOverridingMethods = () -> {
        List<String> results = new ArrayList<String>();
        module.filter(node-> node instanceof ClassDefStmt).forEach(clazz -> { 
            String className = ((ClassDefStmt) clazz).getName();
            List<String> directMethods = findDirectMethods.apply(className);
            List<String> superMethods = new ArrayList<String>();
            findSuperClasses.apply(className).forEach(superClassName -> {
                superMethods.addAll(findDirectMethods.apply(superClassName));
            });
            directMethods.forEach(methodName -> {
                if(superMethods.contains(methodName)) {
                    results.add(methodName);
                }
            });
        });
        return results;
    };

    // TODO
    public Function<String, List<String>> findAllMethods = (className) -> {
        HashSet<String> results = new HashSet<String>();
        results.addAll(findDirectMethods.apply(className));
        findSuperClasses.apply(className).forEach(superClass -> {
            results.addAll(findDirectMethods.apply(superClass));
        });
        return new ArrayList<String>(results);
    };

    // TODO
    public Supplier<List<String>> findClassesWithMain = () -> {
        List<String> results = new ArrayList<String>();
        module.filter(node-> node instanceof ClassDefStmt).forEach(clazz -> {
            String className = ((ClassDefStmt) clazz).getName();
            List<String> allMethods = findAllMethods.apply(className);
            if(allMethods.contains("main")) {
                results.add(className);
            }
        });
        return results;
    };

}

