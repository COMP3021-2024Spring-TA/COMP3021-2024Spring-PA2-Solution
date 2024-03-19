# 生成坏字符位置表
# bc_table[bad_char] 表示坏字符在模式串中最后一次出现的位置
def generateBadCharTable(p: str):
    bc_table = dict()
    
    for i in range(len(p)):
        bc_table[p[i]] = i                          # 更新坏字符在模式串中最后一次出现的位置
    return bc_table