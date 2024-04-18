package hk.ust.comp3021.query;

import hk.ust.comp3021.ASTManagerEngine;
import hk.ust.comp3021.utils.TestKind;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class QueryOnNodeTest {

    @Tag(TestKind.PUBLIC)
    @Test
    public void testPrintedInformation() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(227));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        engine.queryOnNode.findFuncWithArgGtN.accept(4);
        System.setOut(originalPrintStream);
        String printedOutput = outputStream.toString();

        // the function name should be astID_FuncName_UniqueID
        Set<String> expectedOutput = Set.of(
                "227_diagonalBinarySearch_2",
                "227_rowBinarySearch_13",
                "227_colBinarySearch_27");
        assertEquals(expectedOutput, Set.of(printedOutput.trim().split("\\r?\\n")));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testCalculateOp2Nums() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
           engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        HashMap<String, Integer> op2Num = engine.queryOnNode.calculateOp2Nums.get();
        HashMap<String, Integer> expectedOp2Num = new HashMap<>();

        expectedOp2Num.put("And", 253);
        expectedOp2Num.put("Or", 101);

        expectedOp2Num.put("Add", 1257);
        expectedOp2Num.put("Sub", 862);
        expectedOp2Num.put("Mult", 171);
        expectedOp2Num.put("Div", 18);
        expectedOp2Num.put("Mod", 86);
        expectedOp2Num.put("Pow", 11);
        expectedOp2Num.put("LShift", 16);
        expectedOp2Num.put("RShift", 22);
        expectedOp2Num.put("BitOr", 6);
        expectedOp2Num.put("BitXor", 24);
        expectedOp2Num.put("BitAnd", 43);
        expectedOp2Num.put("FloorDiv", 153);

        expectedOp2Num.put("Invert", 2);
        expectedOp2Num.put("Not", 222);
        expectedOp2Num.put("USub", 265);
        expectedOp2Num.put("Eq", 671);
        expectedOp2Num.put("NotEq", 119);

        expectedOp2Num.put("Lt", 375);
        expectedOp2Num.put("LtE", 156);
        expectedOp2Num.put("Gt", 238);
        expectedOp2Num.put("GtE", 92);
        expectedOp2Num.put("Is", 2);
        expectedOp2Num.put("IsNot", 17);
        expectedOp2Num.put("In", 95);
        expectedOp2Num.put("NotIn", 76);
        assertEquals(expectedOp2Num, op2Num);
    }
    


    @Tag(TestKind.PUBLIC)
    @Test
    void testCalculateNode2Num() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        Map<String, Long> node2Num = engine.queryOnNode.calculateNode2Nums.apply("0");
        Map<String, Long> expectedNode2Num = new HashMap<>();
        expectedNode2Num.put("Module", 1L);
        expectedNode2Num.put("ClassDef", 1L);
        expectedNode2Num.put("FunctionDef", 2L);
        expectedNode2Num.put("arguments", 2L);

        expectedNode2Num.put("arg", 4L);
        expectedNode2Num.put("Name", 29L);
        expectedNode2Num.put("Assign", 7L);
        expectedNode2Num.put("Constant", 1L);

        expectedNode2Num.put("While", 2L);

        expectedNode2Num.put("BoolOp", 1L);
        expectedNode2Num.put("Compare", 2L);
        expectedNode2Num.put("Attribute", 13L);

        expectedNode2Num.put("Tuple", 2L);
        expectedNode2Num.put("Return", 2L);

        expectedNode2Num.put("Subscript", 2L);

        expectedNode2Num.put("Call", 1L);
        expectedNode2Num.put("If", 1L);
        assertEquals(expectedNode2Num, node2Num);
    }
    
    @Tag(TestKind.PUBLIC)
    @Test
    public void testProcessNodeFreq() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }

        assertEquals(engine.getId2ASTModules().size(), 837);
        List<Map.Entry<String, Integer>> funcName2NodeNum = engine.queryOnNode.processNodeFreq.get();
        assertEquals(funcName2NodeNum.size(), 1126);
        assertEquals(funcName2NodeNum.get(0).getValue(), 221);
        assertEquals(funcName2NodeNum.get(funcName2NodeNum.size() - 1).getValue(), 6);
    }


    /*
     * Print all functions with # arguments greater than user-specified N
     */
    @Tag(TestKind.PUBLIC)
    @Test
    public void testPrintedInformationAllOn3() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        engine.queryOnNode.findFuncWithArgGtN.accept(3);
        System.setOut(originalPrintStream);
        String printedOutput = outputStream.toString();
        Set<String> computedOutput = Set.of(printedOutput.trim().split("\\r?\\n"));
        System.out.println(computedOutput.size());
        assertEquals(338, computedOutput.size());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testPrintedInformationAllOn4() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        engine.queryOnNode.findFuncWithArgGtN.accept(4);
        System.setOut(originalPrintStream);
        String printedOutput = outputStream.toString();
        Set<String> computedOutput = Set.of(printedOutput.trim().split("\\r?\\n"));
        System.out.println(computedOutput.size());
        assertEquals(92, computedOutput.size());
    }


    /*
     * Find the most commonly used operators in all ASTs
     */

    @Tag(TestKind.PUBLIC)
    @Test
    void testCalculateOp2NumsOn100() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < 100; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        HashMap<String, Integer> op2Num = engine.queryOnNode.calculateOp2Nums.get();
        HashMap<String, Integer> expectedOp2Num = new HashMap<>();

        expectedOp2Num.put("And", 32);
        expectedOp2Num.put("Or", 10);

        expectedOp2Num.put("Add", 126);
        expectedOp2Num.put("Sub", 77);
        expectedOp2Num.put("Mult", 23);
        expectedOp2Num.put("Div", 1);
        expectedOp2Num.put("Mod", 8);
        expectedOp2Num.put("LShift", 1);
        expectedOp2Num.put("RShift", 1);
        expectedOp2Num.put("BitXor", 3);
        expectedOp2Num.put("BitAnd", 2);
        expectedOp2Num.put("FloorDiv", 9);

        expectedOp2Num.put("Not", 18);
        expectedOp2Num.put("USub", 34);
        expectedOp2Num.put("Eq", 55);
        expectedOp2Num.put("NotEq", 12);

        expectedOp2Num.put("Lt", 35);
        expectedOp2Num.put("LtE", 17);
        expectedOp2Num.put("Gt", 22);
        expectedOp2Num.put("GtE", 7);
        expectedOp2Num.put("IsNot", 4);
        expectedOp2Num.put("In", 9);
        expectedOp2Num.put("NotIn", 10);
        assertEquals(expectedOp2Num, op2Num);
    }
    
    /*
     * Given AST ID, count the number of all node types.
     */
    @Tag(TestKind.PUBLIC)
    @Test
    void testTotCalculateNode400() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < 400; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        Map<String, Long> expectedNode2Num = new HashMap<>();
        Map<String, Long> totNode2Num = engine.queryOnNode.calculateNode2Nums.apply("-1");


        expectedNode2Num.put("Module", 400L);
        expectedNode2Num.put("ClassDef", 364L);
        expectedNode2Num.put("FunctionDef", 590L);
        expectedNode2Num.put("arguments", 590L);

        expectedNode2Num.put("arg", 1375L);
        expectedNode2Num.put("Name", 11196L);
        expectedNode2Num.put("Assign", 1766L);
        expectedNode2Num.put("Constant", 2537L);

        expectedNode2Num.put("While", 234L);

        expectedNode2Num.put("BoolOp", 181L);
        expectedNode2Num.put("Compare", 883L);
        expectedNode2Num.put("Attribute", 1645L);

        expectedNode2Num.put("Tuple", 254L);
        expectedNode2Num.put("Return", 769L);

        expectedNode2Num.put("Subscript", 1411L);

        expectedNode2Num.put("Call", 1476L);
        expectedNode2Num.put("If", 756L);

        expectedNode2Num.put("AugAssign", 409L);
        expectedNode2Num.put("Break", 35L);

        expectedNode2Num.put("Continue", 17L);

        expectedNode2Num.put("List", 201L);

        expectedNode2Num.put("For", 335L);
        expectedNode2Num.put("BinOp", 966L);
        expectedNode2Num.put("Expr", 379L);
        expectedNode2Num.put("UnaryOp", 297L);
        assertEquals(expectedNode2Num, totNode2Num);
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testTotCalculateNode2Num() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }
        Map<String, Long> expectedNode2Num = new HashMap<>();
        Map<String, Long> totNode2Num = engine.queryOnNode.calculateNode2Nums.apply("-1");


        expectedNode2Num.put("Module", 837L);
        expectedNode2Num.put("ClassDef", 647L);
        expectedNode2Num.put("FunctionDef", 1126L);
        expectedNode2Num.put("arguments", 1126L);

        expectedNode2Num.put("arg", 2508L);
        expectedNode2Num.put("Name", 22230L);
        expectedNode2Num.put("Assign", 3624L);
        expectedNode2Num.put("Constant", 5627L);

        expectedNode2Num.put("While", 431L);

        expectedNode2Num.put("BoolOp", 354L);
        expectedNode2Num.put("Compare", 1809L);
        expectedNode2Num.put("Attribute", 3480L);

        expectedNode2Num.put("Tuple", 466L);
        expectedNode2Num.put("Return", 1365L);

        expectedNode2Num.put("Subscript", 2425L);

        expectedNode2Num.put("Call", 3708L);
        expectedNode2Num.put("If", 1505L);

        expectedNode2Num.put("AugAssign", 730L);
        expectedNode2Num.put("Break", 87L);

        expectedNode2Num.put("Continue", 29L);

        expectedNode2Num.put("List", 454L);
        expectedNode2Num.put("keyword", 66L);

        expectedNode2Num.put("For", 658L);
        expectedNode2Num.put("BinOp", 1939L);
        expectedNode2Num.put("Expr", 1199L);
        expectedNode2Num.put("UnaryOp", 489L);
        assertEquals(expectedNode2Num, totNode2Num);
    }


    /*
     * Sort all functions based on # children nodes
     */
    @Tag(TestKind.PUBLIC)
    @Test
    public void testProcessNodeFreq1() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }

        assertEquals(engine.getId2ASTModules().size(), 837);
        List<Map.Entry<String, Integer>> funcName2NodeNum = engine.queryOnNode.processNodeFreq.get();
        assertEquals(funcName2NodeNum.size(), 1126);

        Map.Entry<String, Integer> maxEntry = funcName2NodeNum
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<String, Integer> minEntry = funcName2NodeNum
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        assertEquals(maxEntry.getKey(), "637_maxPoints_2");
        assertEquals(minEntry.getKey(), "737_getBalance_22");
        Optional<String> mediumKey = funcName2NodeNum
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .skip(funcName2NodeNum.size() / 2)
                .limit(1)
                .map(Map.Entry::getKey)
                .findFirst();

        assertEquals(mediumKey.get(), "341_next_6");

    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testProcessNodeFreq2() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles("resources/pythonxmlPA1");
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing("resources/pythonxmlPA1", String.valueOf(i));
        }

        assertEquals(engine.getId2ASTModules().size(), 837);
        List<Map.Entry<String, Integer>> funcName2NodeNum = engine.queryOnNode.processNodeFreq.get();
        assertEquals(funcName2NodeNum.size(), 1126);

        Map.Entry<String, Integer> maxEntry = funcName2NodeNum
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<String, Integer> minEntry = funcName2NodeNum
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        double average = funcName2NodeNum.stream().map(Map.Entry::getValue).mapToInt(Integer::intValue).average().orElse(0.0);
        assertEquals(average, 47.01065719360568);
        assertEquals(funcName2NodeNum.stream().map(Map.Entry::getValue).mapToDouble(value -> Math.pow(value - average, 2)).sum(), 1095127.8721136767);
    }
}
