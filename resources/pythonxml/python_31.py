class Node:                                     # 字符节点
    def __init__(self):                         # 初始化字符节点
        self.children = dict()                  # 初始化子节点
        self.isEnd = False                      # isEnd 用于标记单词结束