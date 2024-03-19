class Heapq:
    # 堆调整方法：调整为大顶堆
    def heapAdjust(self, nums: [int], nums_dict, index: int, end: int):
        left = index * 2 + 1
        right = left + 1
        while left <= end:
            # 当前节点为非叶子结点
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
    
    # 将数组构建为二叉堆
    def heapify(self, nums: [int], nums_dict):
        size = len(nums)
        # (size - 2) // 2 是最后一个非叶节点，叶节点不用调整
        for i in range((size - 2) // 2, -1, -1):
            # 调用调整堆函数
            self.heapAdjust(nums, nums_dict, i, size - 1)
    
    # 入队操作
    def heappush(self, nums: list, nums_dict, value):
        nums.append(value)
        size = len(nums)
        i = size - 1
        # 寻找插入位置
        while (i - 1) // 2 >= 0:
            cur_root = (i - 1) // 2
            # value 小于当前根节点，则插入到当前位置
            if nums_dict[nums[cur_root]] > nums_dict[value]:
                break
            # 继续向上查找
            nums[i] = nums[cur_root]
            i = cur_root
        # 找到插入位置或者到达根位置，将其插入
        nums[i] = value
                
    # 出队操作
    def heappop(self, nums: list, nums_dict) -> int:
        size = len(nums)
        nums[0], nums[-1] = nums[-1], nums[0]
        # 得到最大值（堆顶元素）然后调整堆
        top = nums.pop()
        if size > 0:
            self.heapAdjust(nums, nums_dict, 0, size - 2)
            
        return top

class Solution:
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

        heap = Heapq()
        queue = []
        for num in new_nums:
            heap.heappush(queue, nums_dict, num)
        
        res = []
        for i in range(k):
            res.append(heap.heappop(queue, nums_dict))
        return res