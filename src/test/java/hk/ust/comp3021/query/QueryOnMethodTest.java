package hk.ust.comp3021.query;

import hk.ust.comp3021.ASTManagerEngine;
import hk.ust.comp3021.utils.TestKind;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QueryOnMethodTest {

    @Tag(TestKind.PUBLIC)
    @Test
    public void testFindEqualCompareInFunc() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get("1"));
        List<String> exprs = queryOnMethod.findEqualCompareInFunc.apply("foo");

        // the function name should be astID_FuncName_UniqueID
        Set<String> expectedOutput = Set.of(
                "2:7-2:21",
                "3:12-3:26",
                "5:12-5:26");
        assertEquals(expectedOutput, new HashSet<>(exprs));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testFindFuncWithBoolParam() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(2));
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get("2"));
        
        List<String> funcNames = queryOnMethod.findFuncWithBoolParam.get();
        Set<String> expectedOutput = Set.of("toggle_light");
        assertEquals(expectedOutput, new HashSet<>(funcNames));
    }
    
    @Tag(TestKind.PUBLIC)
    @Test
    void testFindUnusedParamInFunc() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(3));
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get("3"));

        List<String> params = queryOnMethod.findUnusedParamInFunc.apply("foo");
        Set<String> expectedOutput = Set.of("param2", "param3");
        assertEquals(expectedOutput, new HashSet<>(params));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testFindDirectCalledOtherB() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(4));
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get("4"));

        List<String> params = queryOnMethod.findDirectCalledOtherB.apply("B");
        Set<String> expectedOutput = Set.of("A");
        assertEquals(expectedOutput, new HashSet<>(params));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testAnswerIfACalledB() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(5));
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get("5"));
        
        assertEquals(true, queryOnMethod.answerIfACalledB.test("foo", "bar"));
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindEqualCompareInFunc")
    public void testFindEqualCompareInFuncHidden(String caseID, String inputFunc, Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get(caseID));
        List<String> exprs = queryOnMethod.findEqualCompareInFunc.apply(inputFunc);

        assertEquals(expectedOutput, new HashSet<>(exprs));
    }

    private static Stream<Object[]> dataFindEqualCompareInFunc() {
        return Stream.of(
//                new Object[] {"22", "bar", Set.of()},
//                new Object[] {"22", "baz", Set.of("5:7-5:18", "5:23-5:34")},
//                new Object[] {"22", "foo", Set.of("17:7-17:18", "18:15-18:37")},
//                new Object[] {"22", "func1", Set.of("9:7-9:29", "9:8-9:20")},
//                new Object[] {"22", "func2", Set.of("13:8-13:26", "14:11-14:31")},
                new Object[] {"22", "paz", Set.of("21:11-21:40")},
                new Object[] {"22", "par", Set.of("24:4-24:34", "24:21-24:33", "24:9-24:15")},
                new Object[] {"22", "test", Set.of("27:9-27:15")}
//                new Object[] {"22", "abc", Set.of()}
        );
    }


    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindFuncWithBoolParam")
    void testFindFuncWithBoolParamHidden(String caseID, Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get(caseID));

        List<String> funcNames = queryOnMethod.findFuncWithBoolParam.get();
        assertEquals(expectedOutput, new HashSet<>(funcNames));
    }
    
    private static Stream<Object[]> dataFindFuncWithBoolParam() {
        return Stream.of(
                new Object[]{"23", Set.of("func1")},
                new Object[]{"24", Set.of("func4")},
//                new Object[]{"27", Set.of("func19")},
                new Object[]{"28", Set.of("func20", "func21")}
        );
    }



    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindUnusedParamInFunc")
    void testFindUnusedParamInFuncHidden(String caseID, String inputFunc, Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get(caseID));

        List<String> funcNames = queryOnMethod.findUnusedParamInFunc.apply(inputFunc);
        assertEquals(expectedOutput, new HashSet<>(funcNames));
    }

    private static Stream<Object[]> dataFindUnusedParamInFunc() {
        return Stream.of(
//                new Object[]{"25", "func1", Set.of("param1", "param2")},
                new Object[]{"25", "func2", Set.of("param1")},
                new Object[]{"25", "func3", Set.of("param2")},
                new Object[]{"25", "func4", Set.of()},
                new Object[]{"25", "func5", Set.of()},
                new Object[]{"25", "func6", Set.of()},
                new Object[]{"25", "func7", Set.of("param3", "param4")}
//                new Object[]{"25", "func8", Set.of()}
        );
    }

    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataFindDirectCalledOtherB")
    public void testFindDirectCalledOtherB(String caseID, String inputFunc, Set<String> expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get(caseID));

        List<String> funcs = queryOnMethod.findDirectCalledOtherB.apply(inputFunc);
        assertEquals(expectedOutput, new HashSet<>(funcs));
    }

    private static Stream<Object[]> dataFindDirectCalledOtherB() {
        return Stream.of(
                new Object[] {"26", "func1", Set.of("func1", "func2", "func3", "func4", "func5", "func6")},
                new Object[] {"26", "func2", Set.of("func2", "func3", "func4", "func5", "func7")},
                new Object[] {"26", "func3", Set.of("func1", "func3", "func6", "func7")},
                new Object[] {"26", "func4", Set.of("func1", "func2", "func3", "func4", "func5", "func6", "func7")}
        );
    }
    
    
    @Tag(TestKind.HIDDEN)
    @ParameterizedTest
    @MethodSource("dataAnswerIfACalledB")
    public void testAnswerIfACalledBHidden(String caseID, String FuncA, String FuncB, Boolean expectedOutput) {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", caseID);
        QueryOnMethod queryOnMethod = new QueryOnMethod(engine.getId2ASTModules().get(caseID));

        assertEquals(expectedOutput, queryOnMethod.answerIfACalledB.test(FuncA, FuncB));
    }

    private static Stream<Object[]> dataAnswerIfACalledB() {
        return Stream.of(
                new Object[] {"26", "func2", "func3", true},
//                new Object[] {"26", "func1", "func2", true},
                new Object[] {"26", "func4", "func2", false},
                new Object[] {"26", "func4", "func5", false},
                new Object[] {"26", "func2", "func2", true},
                new Object[] {"26", "func6", "func6", false},
                new Object[] {"26", "func9", "func9", false}
        );
    }
}
 