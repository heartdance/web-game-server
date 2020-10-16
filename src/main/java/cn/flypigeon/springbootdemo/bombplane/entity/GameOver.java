package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.bombplane.Plane;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class GameOver extends Command {

    private List<Plane> planes = new ArrayList<>();

    public GameOver() {
        super(5);
    }
}
