package cn.flypigeon.springbootdemo.bombplane.entity;

import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Command {
    private Integer code;

    public Command() {
    }

    public Command(Integer code) {
        this.code = code;
    }
}
