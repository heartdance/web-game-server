package cn.flypigeon.springbootdemo.snake.entity;

/**
 * Created by htf on 2020/11/18.
 */
public enum HardLevel {

    SIMPLE(1000), NORMAL(500), HARD(200);

    private final int rate;

    HardLevel(int rate) {
        this.rate = rate;
    }

    public int rate() {
        return rate;
    }

    public static HardLevel ofLevel(int level) {
        switch (level) {
            case 1:
                return SIMPLE;
            case 3:
                return HARD;
            case 2:
            default:
                return NORMAL;
        }
    }
}
