for l in range(1, n):               # 枚举区间长度
    for i in range(n):              # 枚举区间起点
        j = i + l - 1               # 根据起点和长度得到终点
        if j >= n:
            break
        dp[i][j] = float('-inf')    # 初始化 dp[i][j]
        for k in range(i, j + 1):   # 枚举区间分割点
            # 状态转移方程，计算合并区间后的最优值
            dp[i][j] = max(dp[i][j], dp[i][k] + dp[k + 1][j] + cost[i][j])