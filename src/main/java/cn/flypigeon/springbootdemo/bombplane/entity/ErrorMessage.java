package cn.flypigeon.springbootdemo.bombplane.entity;

import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class ErrorMessage extends Command {

    private String message;

    public ErrorMessage() {
        super(0);
    }
}
