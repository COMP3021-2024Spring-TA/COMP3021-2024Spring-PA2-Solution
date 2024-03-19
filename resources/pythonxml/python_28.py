# 生成后移位数表
# bc_table[bad_char] 表示遇到坏字符可以向右移动的距离
def generateBadCharTable(p: str):
    m = len(p)
    bc_table = dict()
    
    for i in range(m):                      # 迭代到最后一个位置 m - 1
        bc_table[p[i]] = m - i              # 更新遇到坏字符可向右移动的距离
    return bc_table