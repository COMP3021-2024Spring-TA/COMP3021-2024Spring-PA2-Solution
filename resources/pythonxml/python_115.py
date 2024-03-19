def find(self, x):                              # 查找元素根节点的集合编号内部实现方法
    while self.fa[x] != x:                      # 递归查找元素的父节点，直到根节点
        self.fa[x] = self.fa[self.fa[x]]        # 隔代压缩
        x = self.fa[x]
    return x                                    # 返回元素根节点的集合编号