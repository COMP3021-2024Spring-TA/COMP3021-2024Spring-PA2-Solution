class Solution:
    def strStr(self, haystack: str, needle: str) -> int:
        def horspool(T: str, p: str) -> int:
            n, m = len(T), len(p)
            
            bc_table = generateBadCharTable(p)
            
            i = 0
            while i <= n - m:
                j = m - 1
                while j > -1 and T[i + j] == p[j]:
                    j -= 1
                if j < 0:
                    return i
                i += bc_table.get(T[i + m - 1], m)
            return -1

        # 生成后移位置表
        # bc_table[bad_char] 表示坏字符在模式串中最后一次出现的位置
        def generateBadCharTable(p: str):
            m = len(p)
            bc_table = dict()
            
            for i in range(m - 1):
                bc_table[p[i]] = m - i - 1    # 更新坏字符在模式串中最后一次出现的位置
            return bc_table

        return horspool(haystack, needle)