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

    private int current = 0;

    public BombPlane(int playerCount) {
        players = new Player[playerCount];
        checkerboards = new Checkerboard[playerCount];
        for (int i = 0; i < checkerboards.length; i++) {
            checkerboards[i] = new Checkerboard(10, 10);
        }
    }

    public Checkerboard getCheckerboard(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (player.equals(players[i])) {
                return checkerboards[i];
            }
        }
        return null;
    }

    public void startPlacePlane() {
        if (status != 0 && status != 3) {
            throw new IllegalOperationException("游戏已经开始");
        }
        status = 1;
    }

    public void startAttack() {
        if (status != 1) {
            throw new IllegalOperationException("请先放置飞机");
        }
        status = 2;
    }

    public void gameOver() {
        status = 3;
    }

    public Player currentPlayer() {
        return players[current];
    }

    public Checkerboard currentCheckerboard() {
        return checkerboards[current];
    }

    public Player anotherPlayer() {
        return players[current == 0 ? 1 : 0];
    }

    public Checkerboard anotherCheckerboard() {
        return checkerboards[current == 0 ? 1 : 0];
    }

    public void next() {
        if (this.current == 0) {
            this.current = 1;
        } else {
            this.current = 0;
        }
    }

    private boolean end;

    public boolean isEnd() {
        if (end) {
            return true;
        }
        for (Checkerboard checkerboard : checkerboards) {
            if (checkerboard.isEnd()) {
                end = true;
                return true;
            }
        }
        return false;
    }

}
