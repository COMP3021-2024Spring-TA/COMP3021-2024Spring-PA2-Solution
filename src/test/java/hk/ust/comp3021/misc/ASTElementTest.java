package hk.ust.comp3021.misc;

import hk.ust.comp3021.ASTManagerEngine;
import hk.ust.comp3021.utils.TestKind;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ASTElementTest {
    @Tag(TestKind.PUBLIC)
    @Test
    public void testASTFileter() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        var results = engine.getId2ASTModules().get("1").filter(node -> node.getLineNo() == 2);
        assertEquals(4, results.size());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testASTForEach() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        List<ASTElement> results = new ArrayList<>();
        engine.getId2ASTModules().get("1").forEach(
                node -> {
                    if (node.getLineNo() == 2) {
                        results.add(node);
                    }
                    ;
                });
        assertEquals(4, results.size());
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testASTForEachAll() {
        ASTManagerEngine engine = new ASTManagerEngine();
        
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        List<ASTElement> results = new ArrayList<>();
        engine.getId2ASTModules().get("1").forEach(results::add);
        assertEquals(17, results.size());
    }

    @Tag(TestKind.PUBLIC)
    @Test
    public void testASTGroupingByList() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        Map<Integer, List<ASTElement>> lineNo2Elems =  engine.getId2ASTModules().get("1").groupingBy(
                ASTElement::getLineNo,
                Collectors.toList()
                );
        assertEquals(5, lineNo2Elems.get(5).size());
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testASTGroupingByCounting() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        Map<Integer, Long> lineNo2Elems =  engine.getId2ASTModules().get("1").groupingBy(
                ASTElement::getLineNo,
                Collectors.counting()
        );
        assertEquals(5, lineNo2Elems.get(5));
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testASTGroupingByJoin() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(1));
        Map<String, String> lineNo2Elems =  engine.getId2ASTModules().get("1").groupingBy(
                ASTElement::getNodeType,
                Collectors.mapping(node -> 
                    Integer.toString(node.getLineNo())
                , Collectors.joining(","))
        );
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("Constant", "2,2,3,3,5,5");
        expectedMap.put("FunctionDef", "1");
        expectedMap.put("Assign", "3,5");
        expectedMap.put("Compare", "2,3,5");
        expectedMap.put("arguments", "0");
        expectedMap.put("Module", "-1");
        expectedMap.put("If", "2");
        expectedMap.put("Name", "3,5");
        assertEquals(expectedMap, lineNo2Elems);
    }
}
