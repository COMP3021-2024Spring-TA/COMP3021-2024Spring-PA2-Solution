# 改变元素：将链表中第 i 个元素值改为 val
def change(self, index, val):
    count = 0
    cur = self.head
    while cur and count < index:
        count += 1
        cur = cur.next
        
    if not cur:
        return 'Error'
    
    cur.val = val