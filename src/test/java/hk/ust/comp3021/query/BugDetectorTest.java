package hk.ust.comp3021.query;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import hk.ust.comp3021.ASTManagerEngine;
import hk.ust.comp3021.utils.TestKind;

public class BugDetectorTest {
    @Tag(TestKind.PUBLIC)
    @Test
    public void testBugDetector() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(10));
        BugDetector bugDetector = new BugDetector(engine.getId2ASTModules().get("10"));
        List<String> functionsWithBugs = bugDetector.detect.get();

        Set<String> expectedOutput = Set.of("bar3", "bar");
        assertEquals(expectedOutput, new HashSet<String>(functionsWithBugs));
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testBugDetector29() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(29));
        BugDetector bugDetector = new BugDetector(engine.getId2ASTModules().get("29"));
        List<String> functionsWithBugs = bugDetector.detect.get();

        Set<String> expectedOutput = Set.of("shadow_bad");
        assertEquals(expectedOutput, new HashSet<String>(functionsWithBugs));
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testBugDetector30() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(30));
        BugDetector bugDetector = new BugDetector(engine.getId2ASTModules().get("30"));
        List<String> functionsWithBugs = bugDetector.detect.get();

        Set<String> expectedOutput = Set.of("swap_bad");
        assertEquals(expectedOutput, new HashSet<String>(functionsWithBugs));
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testBugDetector31() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(31));
        BugDetector bugDetector = new BugDetector(engine.getId2ASTModules().get("31"));
        List<String> functionsWithBugs = bugDetector.detect.get();

        Set<String> expectedOutput = Set.of("chain_bad");
        assertEquals(expectedOutput, new HashSet<String>(functionsWithBugs));
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testBugDetector32() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(32));
        BugDetector bugDetector = new BugDetector(engine.getId2ASTModules().get("32"));
        List<String> functionsWithBugs = bugDetector.detect.get();

        Set<String> expectedOutput = Set.of("reuse_bad");
        assertEquals(expectedOutput, new HashSet<String>(functionsWithBugs));
    }

    @Tag(TestKind.HIDDEN)
    @Test
    public void testBugDetector33() {
        ASTManagerEngine engine = new ASTManagerEngine();
        engine.processXMLParsing("resources/pythonxml", String.valueOf(33));
        BugDetector bugDetector = new BugDetector(engine.getId2ASTModules().get("33"));
        List<String> functionsWithBugs = bugDetector.detect.get();

        Set<String> expectedOutput = Set.of("pop_bad");
        assertEquals(expectedOutput, new HashSet<String>(functionsWithBugs));
    }
}
