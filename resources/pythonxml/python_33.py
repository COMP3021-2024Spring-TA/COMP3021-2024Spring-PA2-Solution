# 向字典树中插入一个单词
def insert(self, word: str) -> None:
    cur = self.root
    for ch in word:                         # 遍历单词中的字符
        if ch not in cur.children:          # 如果当前节点的子节点中，不存在键为 ch 的节点
            cur.children[ch] = Node()       # 建立一个节点，并将其保存到当前节点的子节点
        cur = cur.children[ch]              # 令当前节点指向新建立的节点，继续处理下一个字符
    cur.isEnd = True                        # 单词处理完成时，将当前节点标记为单词结束