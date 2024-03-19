# **COMP3021 Spring 2024 Java Programming Assignment 2 (PA2)**

## **Python AST Management System**

[AST (Abstract Syntax Tree)](https://en.wikipedia.org/wiki/Abstract_syntax_tree) is a tree representation that represents the syntactic structure of source code. It is widely used in compilers and interpreters to reason about relationships between program elements. In this project, you are required to implement your own management system, named ASTManager, for parsing and analyzing Python ASTs.

### **Grading System**

PA2 aims to practice the lambda expression and generics. ASTManager should be enhanced to support the following additional functionalities:

- Task 1: Finish generic tree traversal skeleton that takes lambda expressions as the node visitor and rewrite the task in PA1 (20%)
- Task 2: Support code searching of five patterns with lambda expressions. (50%)
- Task 3: Rewrite the given Python code recovery code with lambda expressions without the guidance of positional information. (30%)
- Bonus Task: Support simple bug detection with lambda expressions. (10%)

Similar to PA1, each test case is an XML file that represents a Python AST.


### What YOU need to do

We have marked the methods you need to implement using `TODO` in the skeleton. Specifically, please

<table>
<tr>
<td> Code Pattern  </td> <td> Code Example </td>
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

# Call the method on the instances
parent.common_method()  # Output: Method of ParentClass
child.common_method()   # Output: Overridden method in ChildClass
```

</td>
</tr>
</table>

### Submission Policy

Please submit your code on Canvas before the deadline **March 23, 2024, 23:59:59.** You should submit a single text file specified as follows:

- A file named `<itsc-id>.txt` containing the URL of your private repository at the first line. We will ask you to add the TAs' accounts as collaborators near the deadline.

For example, a student CHAN, Tai Man with ITSC ID `tmchanaa` having a repository at `https://github.com/tai-man-chan/COMP3021-PA2` should submit a file named `tmchanaa.txt` with the following content:

```txt
https://github.com/tai-man-chan/COMP3021-PA1
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

Last Update: Feb 24, 2024
