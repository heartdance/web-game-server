package cn.flypigeon.springbootdemo.snake.component;

import cn.flypigeon.springbootdemo.game.component.base.Game;
import cn.flypigeon.springbootdemo.snake.entity.HardLevel;
import cn.flypigeon.springbootdemo.snake.entity.Point;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by htf on 2020/11/17.
 */
@Data
public class Snake implements Game {

    /**
     * 每隔多少毫秒动一下
     */
    private int rate = HardLevel.NORMAL.rate();

    /**
     * 0: 未开始  1: 正在执行  2: 暂停  3: 已结束
     */
    private int status;

    private List<Point> points = new LinkedList<>();

    private Direction direction = Direction.RIGHT;

    private Point food;
    
    private long lastMoveTime;

    public void startGame() {
        this.status = 1;
        createFood();
        points.add(new Point(2, 0));
        points.add(new Point(1, 0));
        points.add(new Point(0, 0));
    }

    public void gameOver() {
        this.status = 3;
    }

    public synchronized MoveResult move() {
        lastMoveTime = System.currentTimeMillis();
        Point head = points.get(0);
        int headX = head.getX();
        int headY = head.getY();
        if (direction == Direction.UP) {
            headY--;
        } else if (direction == Direction.RIGHT) {
            headX++;
        } else if (direction == Direction.DOWN) {
            headY++;
        } else if (direction == Direction.LEFT) {
            headX--;
        }
        // 碰撞地图边缘
        if (headX < 0 || headX >= 20 || headY < 0 || headY >= 20) {
            return MoveResult.COLLIDE;
        }
        // 碰撞身体
        for (int i = 0; i < points.size() - 1; i++) {
            Point point = points.get(i);
            if (point.getX() == headX && point.getY() == headY) {
                return MoveResult.COLLIDE;
            }
        }
        points.add(0, new Point(headX, headY));
        if (food.getX() == headX && food.getY() == headY) {
            createFood();
            return MoveResult.EAT;
        }
        points.remove(points.size() - 1);
        return MoveResult.NOTHING;
    }

    public synchronized List<Point> getPoints() {
        return new ArrayList<>(this.points);
    }

    public void stop() {
        if (this.status == 1) {
            this.status = 2;
        }
    }

    public void resume() {
        if (this.status == 2) {
            this.status = 1;
        }
    }

    private void createFood() {
        Random random = new Random();
        boolean overlap;
        int x;
        int y;
        do {
            overlap = false;
            x = random.nextInt(20);
            y = random.nextInt(20);
            for (Point point : points) {
                if (point.getX() == x && point.getY() == y) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
        this.food = new Point(x, y);
    }

    public enum Direction {
        UP(1), RIGHT(2), DOWN(3), LEFT(4);

        private final int code;

        Direction(int code) {
            this.code = code;
        }

        public int code() {
            return code;
        }

        public Direction negative() {
            switch (this) {
                case UP: return DOWN;
                case RIGHT: return LEFT;
                case DOWN: return UP;
                case LEFT: return RIGHT;
            }
            return null;
        }

        public static Direction ofCode(int code) {
            switch (code) {
                case 1: return UP;
                case 2: return RIGHT;
                case 3: return DOWN;
                case 4: return LEFT;
            }
            return null;
        }
    }

    public enum MoveResult {
        NOTHING, EAT, COLLIDE
    }

}
