package hk.ust.comp3021.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import hk.ust.comp3021.ASTManagerEngine;
import hk.ust.comp3021.utils.TestKind;

public class QueryOnClassTest {
    @Tag(TestKind.PUBLIC)
    @Test
    public void testFindSuperClasses() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(7));
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get("7"));
        List<String> superClasses = queryOnClass.findSuperClasses.apply("Bar");

        // should have the two super classes
        Set<String> expectedOutput = Set.of("Foo", "Baz");
        assertEquals(expectedOutput, new HashSet<>(superClasses));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testHaveSuperClass() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(7));
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get("7"));
        Boolean hasSuper = queryOnClass.haveSuperClass.apply("Bar", "Baz");
        
        assertEquals(true, hasSuper);
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testFindOverrideMethods() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(8));
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get("8"));
        List<String> superClasses = queryOnClass.findOverridingMethods.get();

        // there are two overriding of `foo`
        List<String> expectedOutput = new ArrayList<>(Arrays.asList("baz", "foo", "foo"));
        assertEquals(expectedOutput.size(), superClasses.size());
        for (String superClass : superClasses) {
            int index = expectedOutput.indexOf(superClass);
            if(index != -1) {
                expectedOutput.remove(index);
            }
        }
        assertEquals(expectedOutput.size(), 0);
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testFindAllMethods() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(8));
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get("8"));
        List<String> allMethods = queryOnClass.findAllMethods.apply("Bar");

        Set<String> expectedOutput = Set.of("foo", "bar", "baz");
        assertEquals(expectedOutput, new HashSet<String>(allMethods));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testFindClassesWithMain() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(9));
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get("9"));
        List<String> classesWithMain = queryOnClass.findClassesWithMain.get();

        Set<String> expectedOutput = Set.of("Baz", "Bar", "Foo");
        assertEquals(expectedOutput, new HashSet<String>(classesWithMain));
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindSuperClasses")
    public void testFindSuperClassesHidden(String caseID, String inputFunc, Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get(caseID));
        List<String> superClasses = queryOnClass.findSuperClasses.apply(inputFunc);

        assertEquals(expectedOutput, new HashSet<>(superClasses));
    }

    private static Stream<Object[]> dataFindSuperClasses() {
        return Stream.of(
            new Object[] {"7", "Bar", Set.of("Foo", "Baz")},
            new Object[] {"7", "Foo", Set.of()}
        );
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataHaveSuperClass")
    public void testHaveSuperClassHidden(String caseID, String FuncA, String FuncB, Boolean expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get(caseID));
        Boolean hasSuper = queryOnClass.haveSuperClass.apply(FuncA, FuncB);
        assertEquals(expectedOutput, hasSuper);
    }

    private static Stream<Object[]> dataHaveSuperClass() {
        return Stream.of(
            new Object[] {"7","Bar", "Baz", true},
            new Object[] {"7", "Foo", "Bar", false}
        );
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindOverrideMethods")
    public void testFindOverrideMethodsHidden(String caseID, List<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get(caseID));
        List<String> superClasses = queryOnClass.findOverridingMethods.get();

        assertEquals(expectedOutput.size(), superClasses.size());
        for (String superClass : superClasses) {
            int index = expectedOutput.indexOf(superClass);
            if(index != -1) {
                expectedOutput.remove(index);
            }
        }
        assertEquals(expectedOutput.size(), 0);
    }

    private static Stream<Object[]> dataFindOverrideMethods() {
        return Stream.of(
            new Object[] {"8", new ArrayList<>(Arrays.asList("baz", "foo", "foo"))},
            new Object[] {"9", new ArrayList<>(Arrays.asList())}
        );
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindAllMethods")
    public void testFindAllMethodsHidden(String caseID, String inputFunc, Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get(caseID));
        List<String> allMethods = queryOnClass.findAllMethods.apply(inputFunc);

        assertEquals(expectedOutput, new HashSet<String>(allMethods));
    }

    private static Stream<Object[]> dataFindAllMethods() {
        return Stream.of(
            new Object[] {"7", "Bar", Set.of("foo", "bar", "baz")},
            new Object[] {"8", "Bar", Set.of("foo", "bar", "baz")}
        );
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindClassesWithMain")
    public void testFindClassesWithMainHidden(String caseID,  Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnClass queryOnClass = new QueryOnClass(engine.getId2ASTModules().get(caseID));
        List<String> classesWithMain = queryOnClass.findClassesWithMain.get();

        assertEquals(expectedOutput, new HashSet<String>(classesWithMain));
    }

    private static Stream<Object[]> dataFindClassesWithMain() {
        return Stream.of(
            new Object[] {"9", Set.of("Baz", "Bar", "Foo")},
            new Object[] {"9", Set.of("Baz", "Bar", "Foo")}
        );
    }




}
