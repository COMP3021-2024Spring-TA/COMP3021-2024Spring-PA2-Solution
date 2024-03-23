# **COMP3021 Spring 2024 Java Programming Assignment 2 (PA2)**

## **Python AST Management System**

[AST (Abstract Syntax Tree)](https://en.wikipedia.org/wiki/Abstract_syntax_tree) is a tree representation that represents the syntactic structure of source code. It is widely used in compilers and interpreters to reason about relationships between program elements. In this project, you are required to implement your own management system, named ASTManager, to parse and analyze Python ASTs.

### **Grading System**

PA2 aims to practice the lambda expression and generics. **ASTManager** should be enhanced to support the following additional functionalities:

- Task 1: Finish generic tree traversal skeleton that takes lambda expressions as the node visitor and rewrite the task in PA1 (30%)
- Task 2: Support code searching of ten patterns with lambda expressions. (70%)
- Bonus Task: Support simple bug detection with lambda expressions. (10%)

Similar to PA1, each test case is an XML file that represents a Python AST. The XML files used to test rewrite logics of PA1 are resided in `resources/pythonxmlPA1` while those for ten code patterns and bonus tasks are located in `resources/pythonxml`. Before task specification, we first explain the grading policy as follows for your reference so that you will get it.

| Item                                                      | Ratio | Notes                                                        |
| --------------------------------------------------------- | ----- | ------------------------------------------------------------ |
| Keeping your GitHub repository private                    | 5%    | You must keep your repository **priavte** at all times.      |
| Having at least three commits on different days           | 5%    | You should commit three times during different days in your repository |
| Code style                                                | 10%   | You get 10% by default, and every 5 warnings from CheckStyle deducts 1%. |
| Public test cases (Task 1 + Task 2 + Task 3 + Bonus Task) | 30%   | (# of passing tests / # of provided tests) * 30%             |
| Hidden test cases (Task 1 + Task 2 + Task 3 + Bonus Task) | 50%   | (# of passing tests / # of provided tests) * 50%             |

### Task Description

The specifications of each task are shown below.


#### Task 1: Rewrite tasks in PA1 with lambda expression (30%)

First, we need to understand the mechanism of common collectors and the functional interface. You are requested to implement the following functional interface fully in the class `ASTElement`.

1. Implementing a method in `ASTElement` named `filter` to mimic the behavior of `Stream.filter` on AST.
   ```Java
    /**
     * TODO `filter` mimic {@link java.util.stream.Stream#filter(Predicate)} but operates on AST tree structure instead of List 
     * TODO please design the function by yourself to pass complication and the provided test cases
     *
     * @param predicate representing a boolean-valued function that takes ASTElement as input parameter and returns a bool result
     * @return an ArrayList of ASTElement where predicate returns true
     * 
     * Hints: traverse the tree and put those satisfy predicates into array list
     */
   ```
2. Implementing a method in `ASTElement` named `groupingBy` to mimic the behavior of `Collectors.groupingBy` on AST.
   ```Java
    /**
     * TODO `groupingBy` mimic {@link java.util.stream.Collectors#groupingBy(Function, Collector)} )} but operates on AST tree structure instead of List 
     * TODO please design the function by yourself to pass complication and the provided test cases
     *
     * @param classifier representing a function that classifies an ASTElement argument and produces the classification result with generic type
     * @param collector representing a collector used to accumulate the ASTElement object into results
     * @return a map whose key and value are all generic types
     * 
     * Hints: traverse the tree and group them if they belong to the same categories
     * Hints: please refer to the usage of {@link java.util.stream.Collectors#groupingBy(Function, Collector)}} to learn more about this method
     */
   ```
3. Implementing a method in `ASTElement` named `forEach` to mimic `Iterable.forEach` on AST.
   ```Java
    /**
     * TODO `forEach` mimic {@link Iterable#forEach(Consumer)} but operates on AST tree structure instead of List 
     * TODO please design the function by yourself to pass complication and the provided test cases
     *
     * @param action representing an operation that accepts ASTElement as input and performs some action 
     *               on it without returning any result.
     * @return null
     * 
     * Hints: traverse the tree and perform the action on every node in the tree
     */
   ```

All of the above functions can take lambda repression as parameters. These functions will be used as helper functions in the following tasks. Then, you should utilize them to implement the following functions with lambda expressions in `QueryOnNode`, following the original logic of those in PA1.

1. `public Consumer<Integer> findFuncWithArgGtN`
2. `public Supplier<HashMap<String, Integer>> calculateOp2Nums`
3. `public Function<String, Map<String, Long>> calculateNode2Nums`
4. `public Supplier<List<Map.Entry<String, Integer>>> processNodeFreq`


#### Task 2: Support code search of five patterns with lambda expressions (70%)

<!--@bowen, you can add more tasks on top-->

Task 1 only focuses on querying the attributes of singleton nodes. In task 2, you are required to support the code searching for the following patterns, which consider the correlation between multiple nodes. We have given the sample code that conforms to the patterns to ease your understanding. You should utilize the functions mentioned earlier, e.g., `filter`, as much as possible. 

<table>
<tr>
<td> Code Pattern  </td> <td> TODO Methods  </td>  <td> Code Example </td>
</tr>

<tr>
<td> All the comparison expressions with "=="</td>
<td> QueryOnMethod.<br>findEqualCompareInFunc </td>

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
<td> Functions using a boolean parameter as an if-condition </td>
<td> QueryOnMethod.<br>findFuncWithBoolParam </td>
<td>

```Python
def toggle_light(turn_on: bool):
    if turn_on:
        state = True
        print("The light is now ON.")
    else:
        state = False
        print("The light is now OFF.")

```

</td>
</tr>

<tr>
<td> Function parameters that are never read from or assigned to before it's read  </td>
<td> QueryOnMethod.<br>findUnusedParamInFunc </td>
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
<td> Find all functions that are directly called by some functions other than B</td>
<td> QueryOnMethod.<br>findDirectCalledOtherB </td>
<td>

```python
def A():
    print("A")

def B():
    print("B")
    A()

def C():
    A() # A being called both B and C
```

</td>
</tr>

<tr>
<td> Can method A directly or transitively call method B  </td>
<td> QueryOnMethod.<br>answerIfACalledB </td>
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
```

</td>
</tr>


<tr>
<td>All the superclasses of A</td>
<td> </td>
<td>

```python
class C:
    pass
class B(C):
    pass
class A(B):  # superclasses of A: B, C
    pass 
```

</td>
</tr>


<tr>
<td> Classes having a subclass  </td>
<td>  </td>
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
<td> Overriding methods of classes </td>
<td>  </td>
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

<tr>
<td>All the methods of A</td>
<td>  </td>
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
<<<<<<< HEAD
<td>All the classes that possess a main function</td>
=======
<td>All the classes with main function</td>
<td>  </td>
>>>>>>> 3b7eda8489db051557b1d453718cfc19679ac3c9
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

</table>


#### Bonus Task: Implement an API misuse bug detector (10%)
In this task, we will write a bud detector to check for unclosed files. For instance, in the following code example, the function `bar` contains a bug because the file `f` is opened but not closed. The function `foo` does not contain a bug because the file `f` was opened and closed correctly.
```python
def bar():
    f = open()
    # a bug: file not closed.

def foo():
    f = open()
    close(f) 
    # correct usage
```

In this task, you don't need to consider a simplified version of Python programs: (1) there are no function calls other than `open` and `close`. (2) no conditional statements like `if`, `else`, `while`, and `for`. However, you need to handle a case where variables may be copied to other variables. For instance, in the following code example, there is no bug because the variable `f` is copied to `g` and `g` closes the file.
```python
def bar():
    f = open()
    g = f
    g.close()
    # no bug: the file is closed using variable g
```

### What YOU need to do

We have marked the methods you need to implement using `TODO` in the skeleton. Specifically, please

- Learn three collectors, design, and implement them in `ASTElement`.
- Fully implement the lambda expressions in the class `QueryOnNode`.
- Fully implement the lambda expressions in the class `QueryOnMethod`.
- Fully implement the lambda expressions in the class `QueryOnClass`.
- @bowen, update info for bonus task

**Note**: You can add more methods or even classes into the skeleton in your solution, but **DO NOT** modify existing code.

You need to follow the comments on the methods to be implemented in the provided skeleton. We have provided detailed descriptions and even several hints for these methods. To convenience the testing and debugging, you can just run the `main` method of `ASTManager` to interact with the system.

We use the JUnit test to verify the functionalities of all methods you implement. Please do not modify the type signatures of these functions.

### How to TEST

Public test cases are released in `src/test/java/hk.ust.comp3021/query` and `src/test/java/hk.ust.comp3021/misc`. Please try to test your code with `./gradlew test` before submission to ensure your implementation can pass all public test cases.

We use JUnit test to validate the correctness of individual methods that you need to implement. The mapping between public test cases and methods to be tested is shown below.

| Test Case      | Target Method |
| ----------- | ----------- |
| `ASTElementTest`        | Three collectors for AST in `ASTElement`   |
| `QueryOnNodeTest`        | Methods in `QueryOnNode`     |
| `QueryOnMethodTest`   | Methods in `QueryOnMethod`  |
| `QueryOnClassTest` | Methods in `QueryOnClass` |
| `testBonusPrintByPos` | `module.printByPos` (For Bonus Task Only) |
@bowen, update the information for bonus task

You can fix the problem of your implementation based on the failed test cases.


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

If you have any questions on the PA2, please email TA Wei Chen via wei.chen@connect.ust.hk

---

Last Update: March 24, 2024

