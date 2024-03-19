class Solution:
    def duplicateZeros(self, arr: List[int]) -> None:
        """
        Do not return anything, modify arr in-place instead.
        """
        size = len(arr)
        slow, fast = 0, 0
        while fast < size:
            if arr[slow] == 0:
                fast += 1
            slow += 1
            fast += 1
        
        slow -= 1 # slow 指向最后一个有效数字
        fast -= 1 # fast 指向丢失部分的最后一个数字（可能在减 1 之后为 size，比如输入 [0, 0, 0]）

        while slow >= 0:
            if fast < size: # 防止 fast 越界
                arr[fast] = arr[slow] # 将 slow 位置元素移动到 fast 位置
            if arr[slow] == 0 and fast >= 0: # 遇见 0 则复制 0 到 fast - 1 位置
                fast -= 1
                arr[fast] = arr[slow]
            fast -= 1
            slow -= 1