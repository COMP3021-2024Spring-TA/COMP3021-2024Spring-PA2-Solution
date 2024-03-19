for i in range(size - 1, -1, -1):       # 枚举区间起点
    for j in range(i + 1, size):        # 枚举区间终点
        # 状态转移方程，计算转移到更大区间后的最优值
        dp[i][j] = max(dp[i + 1][j - 1], dp[i + 1][j], dp[i][j - 1]) + cost[i][j]