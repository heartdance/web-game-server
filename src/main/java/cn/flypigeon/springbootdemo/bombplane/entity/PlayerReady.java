package cn.flypigeon.springbootdemo.bombplane.entity;

import lombok.Data;

/**
 * Created by htf on 2020/10/22.
 */
@Data
public class PlayerReady extends Command {

    private Integer id;
    private Boolean ready;

    public PlayerReady() {
        super(4);
    }
}
