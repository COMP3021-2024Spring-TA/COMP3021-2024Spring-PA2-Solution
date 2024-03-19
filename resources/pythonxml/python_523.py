# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def __init__(self):
        self.ans = 0

    def dfs(self, node):
        if not node:
            return 0

        left_len = self.dfs(node.left)          # 左子树高度
        right_len = self.dfs(node.right)        # 右子树高度
        if node.left and node.left.val == node.val:
            left_len += 1
        else:
            left_len = 0
        if node.right and node.right.val == node.val:
            right_len += 1
        else:
            right_len = 0
        self.ans = max(self.ans, left_len + right_len)
        return max(left_len, right_len)

    def longestUnivaluePath(self, root: Optional[TreeNode]) -> int:
        self.dfs(root)

        return self.ans