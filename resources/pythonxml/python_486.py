class Solution:
    def minDepth(self, root: TreeNode) -> int:
        # 遍历到空节点，直接返回 0
        if root == None:
            return 0

        # 左右子树为空，说明为叶子节点 返回 1
        if root.left == None and root.right == None:
            return 1

        leftHeight = self.minDepth(root.left)
        rightHeight = self.minDepth(root.right)

        # 当前节点的左右子树的最小叶子节点深度
        min_depth = 0xffffff
        if root.left:
            min_depth = min(leftHeight, min_depth)
        if root.right:
            min_depth = min(rightHeight, min_depth)

        # 当前节点的最小叶子节点深度
        return min_depth + 1