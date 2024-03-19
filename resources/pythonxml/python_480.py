# 线段树的节点类
class TreeNode:
    def __init__(self, left, right, val=False, lazy_tag=None, letNode=None, rightNode=None):
        self.left = left  # 区间左边界
        self.right = right  # 区间右边界
        self.mid = (left + right) >> 1
        self.leftNode = letNode  # 区间左节点
        self.rightNode = rightNode  # 区间右节点
        self.val = val  # 节点值（区间值）
        self.lazy_tag = lazy_tag  # 区间问题的延迟更新标记


class RangeModule:

    def __init__(self):
        self.tree = TreeNode(0, int(1e9))

    # 向上更新 node 节点区间值，节点的区间值等于该节点左右子节点元素值的聚合计算结果
    def __pushup(self, node):
        if node.leftNode and node.rightNode:
            node.val = node.leftNode.val and node.rightNode.val
        else:
            node.val = False

    # 向下更新 node 节点所在区间的左右子节点的值和懒惰标记
    def __pushdown(self, node):
        if not node.leftNode:
            node.leftNode = TreeNode(node.left, node.mid)
        if not node.rightNode:
            node.rightNode = TreeNode(node.mid + 1, node.right)
        if node.lazy_tag is not None:
            node.leftNode.lazy_tag = node.lazy_tag  # 更新左子节点懒惰标记
            node.leftNode.val = node.lazy_tag  # 左子节点每个元素值增加 lazy_tag

            node.rightNode.lazy_tag = node.lazy_tag  # 更新右子节点懒惰标记
            node.rightNode.val = node.lazy_tag  # 右子节点每个元素值增加 lazy_tag

            node.lazy_tag = None  # 更新当前节点的懒惰标记

    # 区间更新
    def __update_interval(self, q_left, q_right, val, node):
        if q_left <= node.left and node.right <= q_right:  # 节点所在区间被 [q_left, q_right] 所覆盖
            node.lazy_tag = val  # 将当前节点的延迟标记增加 val
            node.val = val  # 当前节点所在区间每个元素值增加 val
            return

        self.__pushdown(node)

        if q_left <= node.mid:
            self.__update_interval(q_left, q_right, val, node.leftNode)
        if q_right > node.mid:
            self.__update_interval(q_left, q_right, val, node.rightNode)

        self.__pushup(node)

    # 区间查询，在线段树的 [left, right] 区间范围中搜索区间为 [q_left, q_right] 的区间值
    def __query_interval(self, q_left, q_right, node):
        if q_left <= node.left and node.right <= q_right:  # 节点所在区间被 [q_left, q_right] 所覆盖
            return node.val  # 直接返回节点值

        # 需要向下更新节点所在区间的左右子节点的值和懒惰标记
        self.__pushdown(node)

        if q_right <= node.mid:
            return self.__query_interval(q_left, q_right, node.leftNode)
        if q_left > node.mid:
            return self.__query_interval(q_left, q_right, node.rightNode)

        return self.__query_interval(q_left, q_right, node.leftNode) and self.__query_interval(q_left, q_right, node.rightNode)  # 返回左右子树元素值的聚合计算结果


    def addRange(self, left: int, right: int) -> None:
        self.__update_interval(left, right - 1, True, self.tree)


    def queryRange(self, left: int, right: int) -> bool:
        return self.__query_interval(left, right - 1, self.tree)


    def removeRange(self, left: int, right: int) -> None:
        self.__update_interval(left, right - 1, False, self.tree)