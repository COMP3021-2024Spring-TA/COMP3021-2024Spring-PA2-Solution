class Solution:
    def preorderTraversal(self, root: Optional[TreeNode]) -> List[int]:
        if not root:                        # 二叉树为空直接返回
            return []
            
        res = []
        stack = [root]

        while stack:                        # 栈不为空
            node = stack.pop()              # 弹出根节点
            res.append(node.val)            # 访问根节点
            if node.right:
                stack.append(node.right)    # 右子树入栈
            if node.left:
                stack.append(node.left)     # 左子树入栈

        return res