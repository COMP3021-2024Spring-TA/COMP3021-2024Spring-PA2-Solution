class Solution:
    def subsetsWithDup(self, nums: List[int]) -> List[List[int]]:
        nums.sort()
        n = len(nums)                       # n 为集合 nums 的元素个数
        sub_sets = []                       # sub_sets 用于保存所有子集
        for i in range(1 << n):             # 枚举 0 ~ 2^n - 1
            sub_set = []                    # sub_set 用于保存当前子集
            flag = True                     # flag 用于判断重复元素
            for j in range(n):  # 枚举第 i 位元素
                if i >> j & 1:  # 如果第 i 为元素对应二进制位为 1，则表示选取该元素
                    if j > 0 and (i >> (j - 1) & 1) == 0 and nums[j] == nums[j - 1]:
                        flag = False        # 如果出现重复元素，则跳过当前生成的子集
                        break
                    sub_set.append(nums[j]) # 将选取的元素加入到子集 sub_set 中
            if flag:
                sub_sets.append(sub_set)    # 将子集 sub_set 加入到所有子集数组 sub_sets 中
        return sub_sets                     # 返回所有子集