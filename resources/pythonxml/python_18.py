class A:
    def a():
        return 1
    
class B(A):
    def a():
        return 1
    def main():
        return 0

class C(B):
    def a():
        return 1

class D(C):
    def a():
        return 1
    def main():
        return 0
    
class E(D):
    def a():
        return 1
    
class F(D):
    def a():
        return 1
    
class G(E):
    def a():
        return 1
    def main():
        return 0
    
class H(F):
    def a():
        return 1