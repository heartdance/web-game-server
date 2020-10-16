package cn.flypigeon.springbootdemo.bombplane;

import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void convert() {
        int x = this.x;
        this.x = y;
        this.y = x;
    }
}
