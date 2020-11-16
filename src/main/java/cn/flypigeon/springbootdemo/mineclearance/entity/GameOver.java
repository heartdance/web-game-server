package cn.flypigeon.springbootdemo.mineclearance.entity;

import cn.flypigeon.springbootdemo.bombplane.entity.Command;
import lombok.Data;

import java.util.List;

/**
 * Created by htf on 2020/11/16.
 */
@Data
public class GameOver extends Command {

    private boolean win;
    private List<Point> mines;

    public GameOver() {
        super(4);
    }

    @Data
    public static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
