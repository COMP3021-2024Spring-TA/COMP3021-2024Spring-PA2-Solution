# 链表尾部插入元素
def insertRear(self, val):
    node = ListNode(val)
    cur = self.head
    while cur.next:
        cur = cur.next
    cur.next = node