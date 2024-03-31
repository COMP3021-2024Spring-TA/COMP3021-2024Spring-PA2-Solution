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



}
