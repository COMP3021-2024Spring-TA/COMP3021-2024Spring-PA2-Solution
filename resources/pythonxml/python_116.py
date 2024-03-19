def find(self, x):                              # 查找元素根节点的集合编号内部实现方法
    if self.fa[x] != x:                         # 递归查找元素的父节点，直到根节点
        self.fa[x] = self.find(self.fa[x])      # 完全压缩优化
    return self.fa[x]