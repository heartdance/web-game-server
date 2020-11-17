package cn.flypigeon.springbootdemo.minesweeper.entity;

import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

import java.util.ArrayList;
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

    public GameOver(boolean win) {
        this();
        this.win = win;
    }

    public static GameOver ofWin() {
        return new GameOver(true);
    }

    public static GameOver ofLose() {
        GameOver gameOver = new GameOver(false);
        gameOver.setMines(new ArrayList<>());
        return gameOver;
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
