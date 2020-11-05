package cn.flypigeon.springbootdemo.bombplane.component;

import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Plane {

    private int x;
    private int y;
    private int direction;

    public Coordinate[] getAllPoint() {
        Coordinate[] coordinates = new Coordinate[10];
        Direction direction = Direction.ofCode(this.direction);

        int i = 0;
        coordinates[i++] = new Coordinate(x, y);
        int convert = direction == Direction.BOTTOM || direction == Direction.RIGHT ? -1 : 1;

        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            for (int j = -2; j <= 2; j++) {
                coordinates[i++] = new Coordinate(x + convert, y + j);
            }
            coordinates[i++] = new Coordinate(x + 2 * convert, y);
            for (int j = -1; j <= 1; j++) {
                coordinates[i++] = new Coordinate(x + 3 * convert, y + j);
            }
        } else {
            for (int j = -2; j <= 2; j++) {
                coordinates[i++] = new Coordinate(x + j, y + convert);
            }
            coordinates[i++] = new Coordinate(x, y + 2 * convert);
            for (int j = -1; j <= 1; j++) {
                coordinates[i++] = new Coordinate(x + j, y + 3 * convert);
            }
        }
        return coordinates;
    }

    public enum Direction {
        UP(3), RIGHT(0), BOTTOM(1), LEFT(2);

        private final int code;

        Direction(int code) {
            this.code = code;
        }

        public static Direction ofCode(int code) {
            switch (code) {
                case 0: return RIGHT;
                case 1: return BOTTOM;
                case 2: return LEFT;
                case 3: return UP;
            }
            return null;
        }

        public int code() {
            return code;
        }
    }
}
