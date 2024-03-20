# **COMP3021 Spring 2024 Java Programming Assignment 2 (PA2)**

## **Python AST Management System**

[AST (Abstract Syntax Tree)](https://en.wikipedia.org/wiki/Abstract_syntax_tree) is a tree representation that represents the syntactic structure of source code. It is widely used in compilers and interpreters to reason about relationships between program elements. In this project, you are required to implement your own management system, named ASTManager, to parse and analyze Python ASTs.

### **Grading System**

PA2 aims to practice the lambda expression and generics. **ASTManager** should be enhanced to support the following additional functionalities:

- Task 1: Finish generic tree traversal skeleton that takes lambda expressions as the node visitor and rewrite the task in PA1 (30%)
- Task 2: Support code searching of five patterns with lambda expressions. (50%)
- Task 3: Rewrite the given Python code recovery code with lambda expressions without the guidance of positional information. (20%)
- Bonus Task: Support simple bug detection with lambda expressions. (10%)

Similar to PA1, each test case is an XML file that represents a Python AST.

Before task specification, we first explain the grading policy as follows for your reference so that you will get it.

| Item                                                      | Ratio | Notes                                                        |
| --------------------------------------------------------- | ----- | ------------------------------------------------------------ |
| Keeping your GitHub repository private                    | 5%    | You must keep your repository **priavte** at all times.      |
| Having at least three commits on different days           | 5%    | You should commit three times during different days in your repository |
| Code style                                                | 10%   | You get 10% by default, and every 5 warnings from CheckStyle deducts 1%. |
| Public test cases (Task 1 + Task 2 + Task 3 + Bonus Task) | 30%   | (# of passing tests / # of provided tests) * 30%             |
| Hidden test cases (Task 1 + Task 2 + Task 3 + Bonus Task) | 50%   | (# of passing tests / # of provided tests) * 50%             |

### What YOU need to do

We have marked the methods you need to implement using `TODO` in the skeleton. The specifications of each task are shown below.


#### Task 1: Rewrite tasks in PA1 with lambda expression (30%)

First, we need to understand the mechanism of common collectors and the functional interface. You are requested to implement the following functional interface fully in the class `ASTElement`.

1. Implementing the following method to mimic the behavior of `Stream.filter` on AST.
   ```Java
   /**
    * @param: predicate representing a boolean-valued function that takes ASTElement as input parameter and returns a bool result
    * @return: an ArrayList of ASTElement where predicate returns true
    */
   public ArrayList<ASTElement> filter(Predicate<ASTElement> predicate);
   ```
2. Implementing the following method to mimic the behavior of `Collectors.groupingBy` on AST.
   ```Java
   /**
    * @param: classifier representing a function that classifies an ASTElement argument and produces an object of generic type K
    *         collector representing a collector used to accumulate the ASTElement object into result D, and A is an intermediate accumulation type
    * @return: a Map where keys are of type K and values are of type D
    */
   public <K, D, A> Map<K, D> groupingBy(Function<ASTElement, K> classifier,
                                        Collector<ASTElement, A, D> collector);
   ```
3. Implementing the following method to mimic `Iterable.forEach` on AST.
   ```Java
    /**
    * @param: consumer representing an operation that accepts ASTElement as input and performs some action on it without returning any result.
    */
   public void forEach(Consumer<ASTElement> action);
   ```

All of the above functions can take lambda repression as parameters. These functions will be used as helper functions in the following tasks. Then, you should utilize them to implement the following functional interfaces with lambda expressions, following the original logic of those in PA1.

1. `public Consumer<Integer> findFuncWithArgGtN`
2. `public Supplier<HashMap<String, Integer>> calculateOp2Nums`
3. `public Function<String, Map<String, Long>> calculateNode2Nums`
4. `public Supplier<HashMap<String, Integer>> processNodeFreq`

Moreover, you should finish the following methods within `ASTManagerEngine` by achieving the requried functionalities using the above functional interfaces.
1. `userInterfaceCommonOp`
2. `userInterfaceCountNum`
3. `userInterfaceSortByChild`

#### Task 2: Support code search of five patterns with lambda expressions (50%)

@bowen, you can add more tasks on top

Task 1 only focuses on querying the attributes of singleton nodes. In task 2, you are required to support the code searching for the following patterns, which consider the correlation between multiple nodes. We have given the sample code that conforms to the patterns to ease your understanding. You should utilize the functions mentioned earlier, e.g., `filter` and `groupingBy`, as much as possible. 

<table>
<tr>
<td> Code Pattern  </td> <td> Code Example </td>
</tr>
<tr>
<td> Function A has been directly called by some function other than B </td>
<td>

```python
def A():
    print("A")

def B():
    print("B")
    A()

def C():
    A() # A being called by C, not B
```

</td>
</tr>

<tr>
<td>All the super classes of A</td>
<td>

```python
class C:
    pass
class B(C):
    pass
class A(B):  # super classeso f A: B, C
    pass 
```

</td>
</tr>

<tr>
<td>All the methods of A</td>
<td>

```python
class C:
    def foo():
        pass
class B(C):
    def bar():
        pass
class A(B):  # methods: foo(), bar(), baz()
    def baz():
        pass 
```

</td>
</tr>
<tr>
<td>All the classes with main function</td>
<td>

```python
class A:  # Yes
    def main():
        pass
class B(A):  # Yes
    def foo():
        pass

class C: # No
    def bar():
        pass
```

</td>


</tr>

<tr>
<td> All the comparison expressions with "=="</td>
<td>

```python
def foo():
    if "foo" == "bar":   # first
        x = "foo" == "bar" # second
    else:
        x = "bar" == "foo" # third
```
</td>
</tr>

<tr>
<td> Classes having a subclass  </td>
<td>

```Python
# superclass
class Person():
    def display1(self):
        print("This is superclass")
 
# subclass		
class Employee(Person):
    def display2(self):
        print("This is subclass")
		
emp = Employee()  # creating object of subclass
 
emp.display1()
emp.display2()
```

</td>
</tr>
<tr>
<td> Functions using a boolean parameter as an if-condition </td>
<td>

```Python
class LightSwitch:
    def __init__(self):
        self.state = False  # False means the light is off
                            # True means it's on.

    def toggle_light(self, turn_on: bool):
        if turn_on:
            self.state = True
            print("The light is now ON.")
        else:
            self.state = False
            print("The light is now OFF.")

switch = LightSwitch()

# Use the method with a boolean parameter
switch.toggle_light(True)  # Should turn the light on
switch.toggle_light(False) # Should turn the light off
```

</td>
</tr>
<tr>
<td> Can method A directly or transitively call method B or C  </td>
<td>

```Python
def baz():
    print("baz invoked")

def bar():
    print("bar invoked")
    baz()

def intermediary():
    print("intermediary invoked")
    bar()

def foo():
    print("foo invoked")
    intermediary()

# Call foo, which will transitively invoke bar and baz
foo()
```

</td>
</tr>

<tr>
<td> Function parameters that are never read from or assigned to before it's read  </td>
<td>

```Python
def foo(param1, param2, param3):
    print(param1) # param1 is read
    param2 = 4  # param2 is written to, 
                # but never read before, hence is unused
    print(param2)
    # param3 is unused
```

</td>
</tr>

<tr>
<td> Overriding methods of classes </td>
<td>

```Python
class ParentClass:
    def common_method(self):
        print("Method of ParentClass")

class ChildClass(ParentClass):
    def common_method(self):
        print("Overridden method in ChildClass")

# Create instances of the classes
parent = ParentClass()
child = ChildClass()

# Call the method on the instance
parent.common_method()  # Output: Method of ParentClass
child.common_method()   # Output: Overridden method in ChildClass
```

</td>
</tr>


</table>

#### Task 3: Rewrite the given Python code recovery code （20%）

In PA1, the Python code recovery task is set as a bonus task, and you can use positional information to help you get the recovery process aligned with the original Python code. In PA2, we release the implementation of PA1, but now you need to rewrite the code to satisfy the following two requirements:

- **DO NOT** use positional information. You need to recover the Python code following the Python format. For instance, we should add indent and new lines when there is a `if` condition.
- **USE** the aforementioned helper functions and lambda expressions to finish the code.

A Sample
```Java
import java.util.function.Function;

// Define a functional interface for handling AST nodes
@FunctionalInterface
interface NodeHandler {
    String generateCode(ASTNode node);
}

class ASTNode {
    // AST node properties and methods
}

class PythonCodeGenerator {
    public static void main(String[] args) {
        // Example of handling a specific type of AST node with a lambda expression
        NodeHandler binaryOperationHandler = (node) -> {
            // Assuming 'node' has left, operator, and right properties
            ASTNode left = ...; // retrieve the left node
            ASTNode right = ...; // retrieve the right node
            String operator = ...; // retrieve the operator
            return generateCode(left) + operator + generateCode(right);
        };

        // More handlers for different types of AST nodes can be defined here

        // Function to convert the AST to Python code
        Function<ASTNode, String> generateCode = (ASTNode node) -> {
            // Switch or if-else statements to handle different types of nodes
            // Delegate to the appropriate NodeHandler
            if (node.type.equals("BinaryOperation")) {
                return binaryOperationHandler.generateCode(node);
            }
            // Additional type checks and handlers here...
            return "";
        };

        // Example usage
        ASTNode rootNode = ...; // This would be the root of the AST
        String pythonCode = generateCode.apply(rootNode);
        System.out.println(pythonCode);
    }
}
```

#### Bonus Task: Implement an API misuse bug detector (10%)


@Bowen, we need an example here.



### Submission Policy

Please submit your code on Canvas before the deadline **April 13, 2024, 23:59:59.** You should submit a single text file specified as follows:

- A file named `<itsc-id>.txt` containing the URL of your private repository at the first line. We will ask you to add the TAs' accounts as collaborators near the deadline.

For example, a student CHAN, Tai Man with ITSC ID `tmchanaa` having a repository at `https://github.com/tai-man-chan/COMP3021-PA2` should submit a file named `tmchanaa.txt` with the following content:

```txt
https://github.com/tai-man-chan/COMP3021-PA2
```

Note that we are using automatic scripts to process your submission on test cases rather than testing via the console manually. **DO NOT add extra explanation** to the file; otherwise, they will prevent our scripts from correctly processing your submission. 

**We will grade your submission based on the latest committed version before the deadline.** Please make sure all the amendments are made before the deadline and do not make changes after the deadline.

We have pre-configured a gradle task to check style for you. You can run `./gradlew checkstyleMain` in the integrated terminal of IntelliJ to check style.

Before submission, please make sure that: 

1. Your code can be complied with successfully. Please try to compile your code with `./gradlew build` before submission. You will not get any marks for public/hidden test cases if your code cannot be successfully compiled.

2. Your implementation can pass the public test cases we provided in `src/test`.

3. Your implementation should not yield too many errors when running `./gradlew checkstyleMain`.

### Academic Integrity

We trust that you are familiar with the Honor Code of HKUST. If not, refer to [this page](https://course.cse.ust.hk/comp3021/#policy).

### Contact US

If you have any questions on the PA1, please email TA Wei Chen via wei.chen@connect.ust.hk

---

Last Update: March 24, 2024

