plt.figure(figsize=(6, 4), dpi=120)
# 绘制直方图
plt.hist(heights, bins=np.arange(145, 196, 5), color='darkcyan', density=True, cumulative=True)
# 定制横轴标签
plt.xlabel('身高')
# 定制纵轴标签
plt.ylabel('概率')
plt.show()