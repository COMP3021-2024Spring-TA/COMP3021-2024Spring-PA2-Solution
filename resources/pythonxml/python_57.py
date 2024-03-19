# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def __init__(self):
        self.ans = float('-inf')
        
    def dfs(self, node):
        if not node:
            return 0
        left_max = max(self.dfs(node.left), 0)     # 左子树提供的最大贡献值
        right_max = max(self.dfs(node.right), 0)   # 右子树提供的最大贡献值

        cur_max = left_max + right_max + node.val  # 包含当前节点和左右子树的最大路径和
        self.ans = max(self.ans, cur_max)          # 更新所有路径中的最大路径和

        return max(left_max, right_max) + node.val # 返回包含当前节点的子树的最大贡献值

    def maxPathSum(self, root: Optional[TreeNode]) -> int:
        self.dfs(root)
        return self.ans