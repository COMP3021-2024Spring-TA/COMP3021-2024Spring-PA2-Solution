class Solution:
    def subsets(self, S):                   # 返回集合 S 的所有子集
        n = len(S)                          # n 为集合 S 的元素个数
        sub_sets = []                       # sub_sets 用于保存所有子集
        for i in range(1 << n):             # 枚举 0 ~ 2^n - 1
            sub_set = []                    # sub_set 用于保存当前子集
            for j in range(n):              # 枚举第 i 位元素
                if i >> j & 1:              # 如果第 i 为元素对应二进位删改为 1，则表示选取该元素
                    sub_set.append(S[j])    # 将选取的元素加入到子集 sub_set 中
            sub_sets.append(sub_set)        # 将子集 sub_set 加入到所有子集数组 sub_sets 中
        return sub_sets                     # 返回所有子集