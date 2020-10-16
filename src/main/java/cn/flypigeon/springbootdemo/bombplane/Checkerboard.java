package cn.flypigeon.springbootdemo.bombplane;

import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
public class Checkerboard {

    /**
     * 棋盘点坐标
     * 第一层为 x，第二层为 y
     */
    private CheckerPoint[][] points;

    private Plane[] planes = new Plane[3];
    private int planeCount = 0;
    private int aliveCount = 3;

    public Checkerboard(int x, int y) {
        points = new CheckerPoint[x][];
        for (int i = 0; i < points.length; i++) {
            points[i] = new CheckerPoint[y];
            for (int j = 0; j < points[i].length; j++) {
                points[i][j] = new CheckerPoint();
            }
        }
    }

    public void addPlane(Plane plane) {
        if (planeCount > 2) {
            throw new IllegalOperationException("");
        }
        checkPlaneBound(plane);
        planes[planeCount++] = plane;
        Coordinate[] planePoint = plane.getAllPoint();
        Coordinate head = planePoint[0];
        CheckerPoint checkerPoint = points[head.getX()][head.getY()];
        checkerPoint.setType(PointType.HEAD);
        for (int i = 1; i < planePoint.length; i++) {
            Coordinate body = planePoint[i];
            checkerPoint = points[body.getX()][body.getY()];
            checkerPoint.setType(PointType.BODY);
        }
    }

    private void checkPlaneBound(Plane plane) {
        Coordinate[] planePoint = plane.getAllPoint();
        for (Coordinate coordinate : planePoint) {
            CheckerPoint checkerPoint;
            try {
                checkerPoint = points[coordinate.getX()][coordinate.getY()];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalOperationException("飞机越界");
            }
            if (checkerPoint.getType() != PointType.EMPTY) {
                throw new IllegalOperationException("飞机间碰撞");
            }
        }
    }

    public CheckerPoint attack(int x, int y) {
        CheckerPoint checkerPoint = points[x][y];
        if (checkerPoint.isDestroy()) {
            throw new IllegalOperationException("该坐标点已被摧毁");
        }
        checkerPoint.setDestroy(true);
        if (checkerPoint.getType() == PointType.HEAD) {
            aliveCount--;
        }
        return checkerPoint;
    }

    public int getAliveCount() {
        return aliveCount;
    }

    public boolean isEnd() {
        return getAliveCount() <= 0;
    }

    public Plane[] getPlanes() {
        return planes;
    }

    @Data
    public static class CheckerPoint {

        /**
         * 是否被摧毁
         */
        private boolean destroy = false;

        private PointType type = PointType.EMPTY;
    }

    public enum PointType {
        EMPTY, BODY, HEAD;

        public int code() {
            switch (this) {
                case EMPTY: return 1;
                case BODY: return 2;
                case HEAD: return 3;
            }
            return -1;
        }
    }
}