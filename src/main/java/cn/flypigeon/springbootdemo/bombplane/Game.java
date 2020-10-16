package cn.flypigeon.springbootdemo.bombplane;

import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;

/**
 * Created by htf on 2020/10/16.
 */
public class Game {

    private Checkerboard checkerboard1 = new Checkerboard(10, 10);
    private Checkerboard checkerboard2 = new Checkerboard(10, 10);

    /**
     * 游戏阶段
     * 1 放置飞机
     * 2 炸飞机
     * 3 游戏结束
     */
    private int status = 0;

    public Game() {

    }

    public void startAddPlane() {
        if (status != 0) {
            throw new IllegalOperationException("游戏已经开始");
        }
        status = 1;
    }

    public void startAttack() {

    }
}
