package cn.flypigeon.springbootdemo.minesweeper.entity;

import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htf on 2020/11/16.
 */
@Data
public class ShowPoint extends Command {

    private List<Point> points = new ArrayList<>();

    public ShowPoint() {
        super(2);
    }

    @Data
    public static class Point {
        private int x;
        private int y;
        private int mineNum;
        private boolean mine;

        public Point(int x, int y, int mineNum, boolean mine) {
            this.x = x;
            this.y = y;
            this.mineNum = mineNum;
            this.mine = mine;
        }
    }
}
