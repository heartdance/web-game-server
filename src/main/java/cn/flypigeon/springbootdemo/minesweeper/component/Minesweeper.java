package cn.flypigeon.springbootdemo.minesweeper.component;

import cn.flypigeon.springbootdemo.game.component.base.Game;
import lombok.Data;

/**
 * Created by htf on 2020/11/16.
 */
@Data
public class Minesweeper implements Game {

    private Checkerboard checkerboard;

    /**
     * 游戏阶段
     * 0 未开始
     * 1 正在扫雷
     * 2 游戏结束
     */
    private int status = 0;

    public void startGame() {
        checkerboard = new Checkerboard(10, 10, 10);
        status = 1;
    }

    public void gameOver() {
        status = 2;
    }

    public boolean isEnd() {
        return status == 2 || checkerboard.pointHideCount() == 10;
    }
}
