class Solution:
    def dfs(self, node: 'Node'):
        # 找到链表的尾节点或 child 链表不为空的节点
        while node.next and not node.child:
            node = node.next
        tail = None
        if node.child:
            # 如果 child 链表不为空，将 child 链表扁平化
            tail = self.dfs(node.child)

            # 将扁平化的 child 链表链接在该节点之后
            temp = node.next
            node.next = node.child
            node.next.prev = node
            node.child = None
            tail.next = temp
            if temp:
                temp.prev = tail
            # 链接之后，从 child 链表的尾节点继续向后处理链表
            return self.dfs(tail)
        # child 链表为空，则该节点是尾节点，直接返回
        return node

    def flatten(self, head: 'Node') -> 'Node':
        if not head:
            return head
        self.dfs(head)
        return head