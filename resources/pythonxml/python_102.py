left = 0
right = 0

while right < len(nums):
    window.append(nums[right])
    
    while 窗口需要缩小:
        # ... 可维护答案
        window.popleft()
        left += 1
    
    # 向右侧增大窗口
    right += 1