package cn.flypigeon.springbootdemo.bombplane.entity;

import lombok.Data;

/**
 * Created by htf on 2020/11/5.
 */
@Data
public class StartBomb extends Command {

    private int owner;

    public StartBomb() {
        super(7);
    }
}
