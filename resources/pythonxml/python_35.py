# 查找字典树中是否存在一个单词
def search(self, word: str) -> bool:
    cur = self.root
    for ch in word:                         # 遍历单词中的字符
        if ch not in cur.children:          # 如果当前节点的子节点中，不存在键为 ch 的节点
            return False                    # 直接返回 False
        cur = cur.children[ch]              # 令当前节点指向新建立的节点，然后继续查找下一个字符

    return cur is not None and cur.isEnd    # 判断当前节点是否为空，并且是否有单词结束标记