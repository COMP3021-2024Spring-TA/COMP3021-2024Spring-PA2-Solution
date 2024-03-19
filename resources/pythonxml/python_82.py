class Solution:
    def bubbleSort(self, nums: [int]) -> [int]:
        # 第 i 趟「冒泡」
        for i in range(len(nums) - 1):
            flag = False    # 是否发生交换的标志位
            # 从数组中前 n - i + 1 个元素的第 1 个元素开始，相邻两个元素进行比较
            for j in range(len(nums) - i - 1):
                # 相邻两个元素进行比较，如果前者大于后者，则交换位置
                if nums[j] > nums[j + 1]:
                    nums[j], nums[j + 1] = nums[j + 1], nums[j]
                    flag = True
            if not flag:    # 此趟遍历未交换任何元素，直接跳出
                break
        
        return nums
    
    def sortArray(self, nums: [int]) -> [int]:
        return self.bubbleSort(nums)