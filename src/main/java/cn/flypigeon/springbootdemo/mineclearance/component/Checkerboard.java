package cn.flypigeon.springbootdemo.mineclearance.component;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by htf on 2020/11/16.
 */
@Data
public class Checkerboard {

    /**
     * 棋盘点坐标
     * 第一层为 x，第二层为 y
     */
    private final CheckerPoint[][] points;

    private CheckerPoint[] minePoints;

    public Checkerboard(int x, int y, int mineCount) {
        points = new CheckerPoint[x][];
        for (int i = 0; i < points.length; i++) {
            points[i] = new CheckerPoint[y];
            for (int j = 0; j < points[i].length; j++) {
                points[i][j] = new CheckerPoint(i, j);
            }
        }
        minePoints = new CheckerPoint[mineCount];
        addMines(x, y, mineCount);
    }

    private void addMines(int x, int y, int mineCount) {
        Random random = new Random();
        for (int i = 0; i < mineCount; ) {
            CheckerPoint checkerPoint = points[random.nextInt(x)][random.nextInt(y)];
            if (checkerPoint.mine) {
                continue;
            }
            checkerPoint.mine = true;
            minePoints[i] = checkerPoint;
            List<CheckerPoint> around = around(checkerPoint.x, checkerPoint.y);
            for (CheckerPoint point : around) {
                point.mineNum++;
            }
            i++;
        }
    }

    public List<CheckerPoint> around(int x, int y) {
        List<CheckerPoint> list = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            addPointToList(x + i, y - 1, list);
        }
        addPointToList(x - 1, y, list);
        addPointToList(x + 1, y, list);
        for (int i = -1; i < 2; i++) {
            addPointToList(x + i, y + 1, list);
        }
        return list;
    }

    private void addPointToList(int x, int y, List<CheckerPoint> list) {
        try {
            list.add(points[x][y]);
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }

    public int pointShowCount() {
        int count = 0;
        for (CheckerPoint[] point : points) {
            for (CheckerPoint checkerPoint : point) {
                if (checkerPoint.getStatus() == 3) {
                    count++;
                }
            }
        }
        return count;
    }

    public int pointHideCount() {
        int count = 0;
        for (CheckerPoint[] point : points) {
            for (CheckerPoint checkerPoint : point) {
                if (checkerPoint.getStatus() != 3) {
                    count++;
                }
            }
        }
        return count;
    }

    @Data
    public static class CheckerPoint {

        /**
         * 0:无标记 1:旗子 2:问号 3:已点开
         */
        private int status;

        private boolean mine;

        private int mineNum;

        private int x;

        private int y;

        public CheckerPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
