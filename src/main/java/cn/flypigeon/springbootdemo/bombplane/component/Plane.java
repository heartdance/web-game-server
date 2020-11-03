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
        int x = this.x;
        int y = this.y;

        int i = 0;
        Coordinate coordinate = new Coordinate(x, y);
        coordinates[i++] = coordinate;
        int convert = 1;
        if (direction == Direction.BOTTOM || direction == Direction.RIGHT) {
            convert = -1;
        }
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            x = this.y;
            y = this.x;
        }

        for (int j = 0; j < 5; j++) {
            coordinate = new Coordinate(x - 2 + j, y + convert);
            coordinates[i++] = coordinate;
        }
        coordinate = new Coordinate(x, y + 2 * convert);
        coordinates[i++] = coordinate;
        for (int j = 0; j < 3; j++) {
            coordinate = new Coordinate(x - 1 + j, y + 3 * convert);
            coordinates[i++] = coordinate;
        }

        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            for (int j = 1; j < coordinates.length; j++) {
                coordinates[j].convert();
            }
        }

        return coordinates;
    }

    public enum Direction {
        UP, RIGHT, BOTTOM, LEFT;

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
            switch (this) {
                case UP: return 3;
                case LEFT: return 2;
                case RIGHT: return 0;
                case BOTTOM: return 1;
            }
            return -1;
        }
    }
}
