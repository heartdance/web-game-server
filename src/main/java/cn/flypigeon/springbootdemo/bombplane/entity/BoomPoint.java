package cn.flypigeon.springbootdemo.bombplane.entity;

import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class BoomPoint extends Command {

    private int x;
    private int y;
    private int owner;
    private boolean end;
    private int type;

    public BoomPoint() {
        super(8);
    }
}
