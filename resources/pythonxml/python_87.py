# 从数组 nums 中读取下标为 i 的数据元素值
def value(nums, i):
    if 0 <= i <= len(nums) - 1:
        print(nums[i])
        
arr = [0, 5, 2, 3, 7, 1, 6]
value(arr, 3)