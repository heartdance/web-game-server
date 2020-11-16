package cn.flypigeon.springbootdemo.mineclearance.entity;

import cn.flypigeon.springbootdemo.bombplane.entity.Command;
import lombok.Data;

/**
 * Created by htf on 2020/11/16.
 */
@Data
public class PointFlag extends Command {

    private int flag;

    public PointFlag() {
        super(3);
    }
}
