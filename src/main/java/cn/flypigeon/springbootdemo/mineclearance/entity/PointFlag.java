package cn.flypigeon.springbootdemo.mineclearance.entity;

import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

/**
 * Created by htf on 2020/11/16.
 */
@Data
public class PointFlag extends Command {

    private int x;
    private int y;
    private int flag;

    public PointFlag() {
        super(3);
    }

    public PointFlag(int x, int y, int flag) {
        this();
        this.x = x;
        this.y = y;
        this.flag = flag;
    }
}
