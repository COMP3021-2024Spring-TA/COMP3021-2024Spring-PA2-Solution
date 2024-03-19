# 链表头部插入元素
def insertFront(self, val):
    node = ListNode(val)
    node.next = self.head
    self.head = node