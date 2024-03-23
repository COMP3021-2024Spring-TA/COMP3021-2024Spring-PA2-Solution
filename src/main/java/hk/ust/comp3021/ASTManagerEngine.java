package hk.ust.comp3021;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.query.QueryOnMethod;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.io.*;
import java.util.*;
import java.util.AbstractMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ASTManagerEngine {
    private final String defaultXMLFileDir;
    private final HashMap<String, ASTModule> id2ASTModules = new HashMap<>();

    private QueryOnMethod queryOnMethod = new QueryOnMethod(id2ASTModules);
    
    public ASTManagerEngine() {
        defaultXMLFileDir = "resources/pythonxml/";
    }

    public String getDefaultXMLFileDir() {
        return defaultXMLFileDir;
    }

    public HashMap<String, ASTModule> getId2ASTModules() {
        return id2ASTModules;
    }

    public void userInterface() {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("ASTManager is running...");

        while (true) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Please select the following operations with the corresponding numbers:");
            System.out.println("  0: Given AST ID, parse AST from XML files");
            // PA1 tasks, need to be rewritten with lambda expression
            System.out.println("  1: Print all functions with # arguments greater than user specified N");
            System.out.println("  2: Find the most commonly used operators in all ASTs");
            System.out.println("  3: Print all functions names and the functions invoked by each function");
            System.out.println("  4: Given AST ID, count the number of all node types");
            System.out.println("  5: Sort all functions based on # children nodes");
            System.out.println("  6: Given AST ID, recover Python Code (Bonus Task)");
            // PA2 tasks code patterns for methods
            System.out.println("  7: Given func name, find all comparison expressions with \"==\"");
            System.out.println("  8: Find all functions using boolean parameter as an if-condition");
            System.out.println("  9: Given func name, find all unused parameters");
            System.out.println("  10: Given name of func B, find all functions being directly called by functions other than B");
            System.out.println("  11: Can func A directly or transitively call by method B or C");

            // PA2 tasks code patterns for class 12-16
            
            System.out.println("  17: Exit");
            System.out.println("----------------------------------------------------------------------");
            Scanner scan1 = new Scanner(System.in);
            if (scan1.hasNextInt()) {
                int i = scan1.nextInt();
                if (i < 0 || i > 18) {
                    System.out.println("You should enter 0~7.");
                    continue;
                }

                switch (i) {
                    case 0: {
                        userInterfaceParseXML();
                        break;
                    }
                    case 1: {
                        userInterfaceParamNum();
                        break;
                    }
                    case 2: {
                        userInterfaceCommonOp();
                        break;
                    }
                    case 3: {
                        userInterfaceCallFuncs();
                        break;
                    }
                    case 4: {
                        userInterfaceCountNum();
                        break;
                    }
                    case 5: {
                        userInterfaceSortByChild();
                        break;
                    }
                    case 6: {
                        userInterfaceRecoverCode();
                        break;
                    }
                    case 7: {
                        queryOnMethod.findEqualCompareInFunc.apply("1_foo_1");
                        break;
                    }
                    case 8: {
                        queryOnMethod.findFuncWithBoolParam.get();
                        break;
                    }
                    case 9: {
                        queryOnMethod.findUnusedParamInFunc.apply("3_foo_1");
                    }
                    default: {

                    }
                }
                if (i == 17) {
                    break;
                }
            } else {
                System.out.println("You should enter integer 0~6.");
            }
        }
    }

    

    public int countXMLFiles(String dirPath) {
        int count = 0;
        File directory = new File(dirPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        count += countXMLFiles(file.getAbsolutePath());
                    } else if (file.isFile() && file.getName().toLowerCase().endsWith(".xml")) {
                        count++;
                    }
                }
            }
        }

        return count;
    }


    /*
     * Task 0: Given AST ID, parse AST from XML files
     */

    public void processXMLParsing(String xmlID) {
        ASTParser parser = new ASTParser(xmlID);
        parser.parse();
        if (!parser.isErr()) {
            this.id2ASTModules.put(xmlID, parser.getASTModule());
            System.out.println("AST " + xmlID + " Succeed! The XML file is loaded!");
        } else {
            System.out.println("AST " + xmlID + " Failed! Please check your implementation!");
        }
    }

    public void userInterfaceParseXML() {
        int xmlCount = countXMLFiles(this.defaultXMLFileDir);
        System.out.println("Please specify the XML file ID to parse (0~" + xmlCount + ") or -1 for all:");
        Scanner scan1 = new Scanner(System.in);
        if (scan1.hasNextLine()) {
            String xmlID = scan1.nextLine();
            if (!xmlID.equals("-1")) {
                processXMLParsing(xmlID);
            } else {
                File directory = new File(this.defaultXMLFileDir);
                if (directory.isDirectory()) {
                    File[] files = directory.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().toLowerCase().endsWith(".xml")) {
                                String str = file.getName().toLowerCase();
                                int startIndex = str.indexOf('_') + 1;
                                int endIndex = str.indexOf(".xml");

                                if (endIndex > startIndex) {
                                    processXMLParsing(str.substring(startIndex, endIndex));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * Task 1: Print all functions with # arguments greater than user specified N
     */

    public Consumer<Integer> findFuncWithArgGtN = paramN -> {
        id2ASTModules.values().forEach(module -> {
            module.filter(node -> node instanceof FunctionDefStmt)
                    .stream().filter(func -> ((FunctionDefStmt) func).getParamNum() >= paramN)
                    .forEach(func -> System.out.println(module.getASTID() + "_" + ((FunctionDefStmt) func).getName() + "_" + func.getLineNo()));
        });
    };
    

    public void userInterfaceParamNum() {
        System.out.println("Please indicate the value of N (recommended range 0~5):");
        Scanner scan2 = new Scanner(System.in);
        if (scan2.hasNextLine()) {
            String paramN = scan2.nextLine();
            try {
                int number = Integer.parseInt(paramN);
                System.out.println("Parsed number: " + number);
                findFuncWithArgGtN.accept(number);
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid number format");
            }

        }
    }

    /*
     * Task 2: Find the most commonly used operators in all ASTs
     */

    /*
     * Calculate the frequency of each node in the AST
     * @return: HashMap that records the mapping from operator name to the
     *          frequency of this operator
     */

    public Supplier<HashMap<String, Integer>> calculateOp2Nums = () -> {
        HashMap<String, Integer> op2Num = new HashMap<>();

        Consumer<ASTElement> binOp = node -> {
            if (node instanceof BinOpExpr) {
                op2Num.merge(((BinOpExpr) node).getOp().getOperatorName(), 1, Integer::sum);
            }
        };

        Consumer<ASTElement> boolOp = node -> {
            if (node instanceof BoolOpExpr) {
                op2Num.merge(((BoolOpExpr) node).getOp().getOperatorName(), 1, Integer::sum);
            }
        };

        Consumer<ASTElement> unaryOp = node -> {
            if (node instanceof UnaryOpExpr) {
                op2Num.merge(((UnaryOpExpr) node).getOp().getOperatorName(), 1, Integer::sum);
            }
        };

        Consumer<ASTElement> augAssignOp = node -> {
            if (node instanceof AugAssignStmt) {
                ASTEnumOp op = ((AugAssignStmt) node).getOp();
                op2Num.merge(op.getOperatorName(), 1, Integer::sum);
            }
        };

        id2ASTModules.values().forEach(module -> {
            module.forEach(binOp.andThen(boolOp).andThen(unaryOp).andThen(augAssignOp));
        });
        return op2Num;
    };
    
    public void userInterfaceCommonOp() {
        HashMap<String, Integer> op2Num = calculateOp2Nums.get();
        Map.Entry<String, Integer> maxEntry = Collections.max(op2Num.entrySet(), 
                Comparator.comparing(Map.Entry::getValue));
        System.out.println("Most common operator is " + maxEntry.getKey()
                + " with frequency " + maxEntry.getValue());
    }


    /*
     * Task 3: Print all functions names and the functions invoked by each function")
     */

    /*
     * First, you need to find all declarative functions, whose node is FunctionDefStmt.
     * Then, starting from the node, find all CallExpr nodes,
     * Finally, obtain the called function name.
     *
     * Hints: we have prepared some methods for you, e.g., getCalledFuncName.
     *
     * @return: Hashmap that stores the mapping from function name
     *          to the set of functions that invokes
     */
    public HashMap<String, Set<String>> calculateCalledFunc() {
        HashMap<String, Set<String>> func2CalledFuncs = new HashMap<String, Set<String>>();
//        for (String key : id2ASTModules.keySet()) {
//            ASTModule module = id2ASTModules.get(key);
//            for (FunctionDefStmt func : module.getAllFunctions()) {
//                Set<String> calledFuncNames = new HashSet<String>();
//                for (CallExpr call : func.getAllCalledFunc()) {
//                    calledFuncNames.add(module.getASTID() + "_" + call.getCalledFuncName() + "_" + call.getLineNo());
//                }
//                func2CalledFuncs.put(module.getASTID() + "_" + func.getName() + "_" + func.getLineNo(), calledFuncNames);
//            }
//        }
        return func2CalledFuncs;
    }

    public void userInterfaceCallFuncs() {
        HashMap<String, Set<String>> func2CalledFuncs = calculateCalledFunc();

        for (Map.Entry<String, Set<String>> entry : func2CalledFuncs.entrySet()) {
            String curFunc = entry.getKey();
            for (String calledFunc : entry.getValue()) {
                System.out.println("Func " + curFunc + " invokes func " + calledFunc);
            }
        }
    }

    /*
     * Task 4: Given AST ID, count the number of all node types
     */

    public Function<String, Map<String, Long>> calculateNode2Nums = astID -> {
        // TODO: complete the definition of the method `calculateNode2Nums`
        return this.id2ASTModules.get(astID).groupingBy(ASTElement::getNodeType, Collectors.counting());
    };
    
    public void userInterfaceCountNum() {
        System.out.println("Please specify the AST ID to count Node (" + id2ASTModules.keySet() + ") or -1 for all:");
        Scanner scan1 = new Scanner(System.in);
        if (scan1.hasNextLine()) {
            String astID = scan1.nextLine();
            if (!astID.equals("-1")) {
                if (id2ASTModules.containsKey(astID)) {
                    calculateNode2Nums.apply(astID).entrySet().stream().forEach(
                            entry -> System.out.println(astID + entry.getKey() + " node with frequency " + entry.getValue())
                    );
                }
            } else {
                HashMap<String, Long> totNode2Num = new HashMap<>();
                id2ASTModules.keySet().forEach(key ->
                        calculateNode2Nums.apply(key).forEach(
                                (nodeKey, nodeValue) -> totNode2Num.merge(nodeKey, nodeValue, Long::sum)
                        )
                );
                totNode2Num.forEach((node, num) -> System.out.println("All" + node + " node with frequency " + num + "\n"));
            }
        }
    }

    /*
     * Task 5: Sort all functions based on # children nodes
     */

    /*
     * First, you need to find all declarative functions, whose node is FunctionDefStmt.
     * Then, starting from the node, find all children nodes and get the number of children
     * nodes as the complexity of the function.
     *
     * @return: Hashmap that stores the mapping from function name
     *          to the number of children nodes
     */
    public Supplier<HashMap<String, Integer>> processNodeFreq = () -> {
        HashMap<String, Integer> funcName2NodeNum = new HashMap<>();

        id2ASTModules.values().forEach(module -> {
            module.filter(node -> node instanceof FunctionDefStmt)
                    .stream()
                    .map(func -> {
                        final int[] nodeCount = {0};
                        func.forEach(node -> nodeCount[0]++);
                        String uniqueFuncName = module.getASTID() + "_" +
                                ((FunctionDefStmt) func).getName() + "_" + func.getLineNo();
                        return new AbstractMap.SimpleEntry<>(uniqueFuncName, nodeCount[0]);
                    })
                    .forEach(entry -> funcName2NodeNum.merge(
                            entry.getKey(),
                            entry.getValue(),
                            (value1, value2) -> value1));
        });
        return funcName2NodeNum;
    };
    
    public void userInterfaceSortByChild() {
        HashMap<String, Integer> funcName2NodeNum = processNodeFreq.get();

        funcName2NodeNum.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Func " + entry.getKey() + " has complexity " + entry.getValue()));
    }

    /*
     * Task 6: Recover the Python code from its AST
     */
    public void userInterfaceRecoverCode() {
        System.out.println("Please specify the AST ID to recover code (" + id2ASTModules.keySet() + ")");
        Scanner scan1 = new Scanner(System.in);
        if (scan1.hasNextLine()) {
            String astID = scan1.nextLine();
            if (id2ASTModules.containsKey(astID)) {
                ASTModule module = id2ASTModules.get(astID);
                System.out.println("Python Code " + astID);
                StringBuilder stringBuilder = new StringBuilder("");
                module.printByPos(stringBuilder);
                System.out.println(stringBuilder);
            } else {
                System.out.println("Invalid AST ID " + astID + "!");
            }
        }
    }
}
