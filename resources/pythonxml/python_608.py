class Solution:
    # 调整为大顶堆
    def heapify(self, nums, nums_dict, index, end):
        left = index * 2 + 1
        right = left + 1
        while left <= end:
            # 当前节点为非叶子节点
            max_index = index
            if nums_dict[nums[left]] > nums_dict[nums[max_index]]:
                max_index = left
            if right <= end and nums_dict[nums[right]] > nums_dict[nums[max_index]]:
                max_index = right
            if index == max_index:
                # 如果不用交换，则说明已经交换结束
                break
            nums[index], nums[max_index] = nums[max_index], nums[index]
            # 继续调整子树
            index = max_index
            left = index * 2 + 1
            right = left + 1

    # 初始化大顶堆
    def buildMaxHeap(self, nums, nums_dict):
        size = len(nums)
        # (size-2) // 2 是最后一个非叶节点，叶节点不用调整
        for i in range((size - 2) // 2, -1, -1):
            self.heapify(nums, nums_dict, i, size - 1)
        return nums

    # 堆排序方法（本题未用到）
    def maxHeapSort(self, nums, nums_dict):
        self.buildMaxHeap(nums)
        size = len(nums)
        for i in range(size):
            nums[0], nums[size - i - 1] = nums[size - i - 1], nums[0]
            self.heapify(nums, nums_dict, 0, size - i - 2)
        return nums

    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        # 统计元素频数
        nums_dict = dict()
        for num in nums:
            if num in nums_dict:
                nums_dict[num] += 1
            else:
                nums_dict[num] = 1

        # 使用 set 方法去重，得到新数组
        new_nums = list(set(nums))
        size = len(new_nums)
        # 初始化大顶堆
        self.buildMaxHeap(new_nums, nums_dict)
        res = list()
        for i in range(k):
            # 堆顶元素为当前堆中频数最高的元素，将其加入答案中
            res.append(new_nums[0])
            # 交换堆顶和末尾元素，继续调整大顶堆
            new_nums[0], new_nums[size - i - 1] = new_nums[size - i - 1], new_nums[0]
            self.heapify(new_nums, nums_dict, 0, size - i - 2)
        return res