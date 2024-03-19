# 查找元素：在链表中查找值为 val 的元素
def find(self, val):
    cur = self.head
    while cur:
        if val == cur.val:
            return cur
        cur = cur.next

    return None