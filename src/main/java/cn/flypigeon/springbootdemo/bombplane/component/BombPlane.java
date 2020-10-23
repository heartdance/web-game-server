package cn.flypigeon.springbootdemo.bombplane.component;

import cn.flypigeon.springbootdemo.bombplane.component.base.Game;
import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class BombPlane implements Game {

    private Player[] players;
    private Checkerboard[] checkerboards;

    /**
     * 游戏阶段
     * 1 放置飞机
     * 2 炸飞机
     * 3 游戏结束
     */
    private int status = 0;

    public BombPlane(int playerCount) {
        players = new Player[playerCount];
        checkerboards = new Checkerboard[playerCount];
        for (int i = 0; i < checkerboards.length; i++) {
            checkerboards[i] = new Checkerboard(10, 10);
        }
    }

    public void startPlacePlane() {
        if (status != 0) {
            throw new IllegalOperationException("游戏已经开始");
        }
        status = 1;
    }

    public void startAttack() {

    }

}
