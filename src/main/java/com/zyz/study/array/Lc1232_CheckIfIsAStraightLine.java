package com.zyz.study.array;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/17 21:32
 */
public class Lc1232_CheckIfIsAStraightLine {

    /**
     * 思路：计算斜率
     * @param coordinates
     * @return
     */
    public boolean checkStraightLine(int[][] coordinates) {

        // 单点或者两个点必成直线
        if (coordinates.length <= 2) {
            return true;
        }

        // 计算前两个点的斜率
        int xDelta = coordinates[1][0] - coordinates[0][0];
        int yDelta = coordinates[1][1] - coordinates[0][1];

        for (int i = 2; i < coordinates.length; i++) {
            int curXDelta = coordinates[i][0] - coordinates[i-1][0];
            int curYDelta = coordinates[i][1] - coordinates[i-1][1];

            if (xDelta * curYDelta != yDelta * curXDelta) {
                return false;
            }

            xDelta = curXDelta;
            yDelta = curYDelta;
        }
        return true;

    }
}
