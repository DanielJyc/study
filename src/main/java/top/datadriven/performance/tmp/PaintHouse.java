package top.datadriven.performance.tmp;

/**
 * @description: 3. （选做）题目：贴墙纸
 * 你是一位装修工，根据设计师的要求给客户的客厅背景墙贴墙纸。
 * 假设背景墙面积为 n x m，装修风格为现代极简风格，需要使用尽可能少的 不同颜色的 正方形 墙纸包 来铺满墙面。
 * 假设正方形墙纸包块的规格不限，边长都是整数。
 * 请你帮设计师计算一下，最少需要用到多少块方形墙纸包？
 * 示例 1：
 * 输入：n = 2, m = 3
 * 输出：3
 * 解释：3 块墙纸包就可以铺满墙面。
 * 2 块 1x1 墙纸包
 * 1 块 2x2 墙纸包
 * 示例 2：
 * 输入：n = 5, m = 8
 * 输出：5
 * 示例 3：
 * 输入：n = 11, m = 13
 * 输出：6
 * 提示：
 * 1 <= n <= 13
 * 1 <= m <= 13
 * @author: jiayancheng
 * @email: jiayancheng@foxmail.com
 * @datetime: 2020/7/2 8:49 下午
 * @version: 1.0.0
 */
public class PaintHouse {

    public static void main(String[] args) {
        int m = 5, n = 8;
        System.out.println(getCount(m, n));
    }

    /**
     * 思路：
     * 使用动态规划，状态转移方程为：
     * f(m, n) =
     * 1） m < n : f(m, m)+ f(m, n-m)
     * 2)  m >n : f(n, n) +f(n, m -n)
     * <p>
     * 递推过程：
     * f(1,1) = 1
     * f(1,2) = 2
     * f(2, 1) = 2
     * f(2,2) = 1
     * <p>
     * <p>
     * f(m,m) = 1
     * f(n,n) = 1
     * f(1,n) = 1
     * f(m,1) = m
     */
    private static int getCount(int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        int[][] arr = new int[m + 1][n + 1];

        arr[0][0] = 1;
        arr[1][0] = 1;
        arr[0][1] = 1;
        arr[1][1] = 1;
        arr[1][2] = 2;
        arr[2][1] = 2;

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (i == 1) {
                    arr[i][j] = j;
                }
                if (j == 1) {
                    arr[i][j] = i;
                }

                if (i == j) {
                    arr[i][j] = 1;
                }

                if (i < j) {
                    arr[i][j] = 1 + arr[i][j - i];
                }
                if (i > j) {
                    arr[i][j] = 1 + arr[j][i - j];
                }
            }
        }
        return arr[m][n];
    }

}
