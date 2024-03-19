class Solution:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        # 调整为大顶堆
        def heapify(nums, index, end):
            left = index * 2 + 1
            right = left + 1
            while left <= end:
                # 当前节点为非叶子节点
                max_index = index
                if nums[left] > nums[max_index]:
                    max_index = left
                if right <= end and nums[right] > nums[max_index]:
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
        def buildMaxHeap(nums):
            size = len(nums)
            # (size-2) // 2 是最后一个非叶节点，叶节点不用调整
            for i in range((size - 2) // 2, -1, -1):
                heapify(nums, i, size - 1)
            return nums

        buildMaxHeap(nums)
        size = len(nums)
        for i in range(k-1):
            nums[0], nums[size-i-1] = nums[size-i-1], nums[0]
            heapify(nums, 0, size-i-2)
        return nums[0]