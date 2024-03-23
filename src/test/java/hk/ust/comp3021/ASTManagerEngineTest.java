package hk.ust.comp3021;

import hk.ust.comp3021.utils.ASTParser;
import hk.ust.comp3021.utils.TestKind;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ASTManagerEngineTest {

    @Tag(TestKind.PUBLIC)
    @Test
    void testParse2XMLNode() {
        ASTParser parser = new ASTParser("1");
        parser.parse2XMLNode();
        assertNotNull(parser.getRootXMLNode());
        assertEquals(parser.getRootXMLNode().getTagName(), "ast");
        assertFalse(parser.isErr());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testParseASTNode() {
        ASTParser parser = new ASTParser("1");
        parser.parse();
        assertNotNull(parser.getASTModule());
        assertFalse(parser.isErr());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testPrintedInformation() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing(String.valueOf(227));

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
        int xmlFileTot = engine.countXMLFiles(engine.getDefaultXMLFileDir());
        for (int i = 0; i < xmlFileTot; i++) {
           engine.processXMLParsing(String.valueOf(i));
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
        int xmlFileTot = engine.countXMLFiles(engine.getDefaultXMLFileDir());
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing(String.valueOf(i));
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
    void testTotCalculateNode2Num() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles(engine.getDefaultXMLFileDir());
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing(String.valueOf(i));
        }
        HashMap<String, Long> expectedNode2Num = new HashMap<>();

        HashMap<String, Long> totNode2Num = new HashMap<>();
        for (String key : engine.getId2ASTModules().keySet()) {
            Map<String, Long> node2Num = engine.queryOnNode.calculateNode2Nums.apply(key);
            for (Map.Entry<String, Long> entry : node2Num.entrySet()) {
                if (totNode2Num.containsKey(entry.getKey())) {
                    Long currentValue = totNode2Num.get(entry.getKey());
                    totNode2Num.put(entry.getKey(), currentValue + entry.getValue());
                } else {
                    totNode2Num.put(entry.getKey(), entry.getValue());
                }
            }
        }

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
    
    @Tag(TestKind.PUBLIC)
    @Test
    public void testProcessNodeFreq() {
        ASTManagerEngine engine = new ASTManagerEngine();
        int xmlFileTot = engine.countXMLFiles(engine.getDefaultXMLFileDir());
        for (int i = 0; i < xmlFileTot; i++) {
            engine.processXMLParsing(String.valueOf(i));
        }

        assertEquals(engine.getId2ASTModules().size(), 837);
        HashMap<String, Integer> funcName2NodeNum = engine.queryOnNode.processNodeFreq.get();
        assertEquals(funcName2NodeNum.size(), 1126);
        assertEquals(Collections.max(funcName2NodeNum.values()), 221);
        assertEquals(Collections.min(funcName2NodeNum.values()), 6);
    }
    
}
