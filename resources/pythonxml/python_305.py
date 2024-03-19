class Solution:
    def canIWin(self, maxChoosableInteger: int, desiredTotal: int) -> bool:
        @cache
        def dfs(state, curTotal):
            for k in range(1, maxChoosableInteger + 1):             # 从 1 ~ maxChoosableInteger 中选择一个数
                if state >> (k - 1) & 1 != 0:                       # 如果之前选过该数则跳过
                    continue
                if curTotal + k >= desiredTotal:                    # 如果选择了 k，累积整数和大于等于 desiredTotal，则该玩家一定赢
                    return True
                if not dfs(state | (1 << (k - 1)), curTotal + k):   # 如果当前选择了 k 之后，对手一定输，则当前玩家一定赢
                    return True
            return False                                            # 以上都赢不了的话，当前玩家一定输

        # maxChoosableInteger 直接大于等于 desiredTotal，则先手玩家一定赢
        if maxChoosableInteger >= desiredTotal:
            return True
            
        # 1 ~ maxChoosableInteger 所有数加起来都不够 desiredTotal，则先手玩家一定输
        if (1 + maxChoosableInteger) * maxChoosableInteger // 2 < desiredTotal:
            return False
        return dfs(0, 0)