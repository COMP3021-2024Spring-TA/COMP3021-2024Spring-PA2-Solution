left = 0
right = 0

while right < len(nums):
    window.append(nums[right])
    
    # 超过窗口大小时，缩小窗口，维护窗口中始终为 window_size 的长度
    if right - left + 1 >= window_size:
        # ... 维护答案
        window.popleft()
        left += 1
    
    # 向右侧增大窗口
    right += 1