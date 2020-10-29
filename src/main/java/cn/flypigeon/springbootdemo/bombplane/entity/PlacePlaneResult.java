package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.bombplane.component.Plane;
import lombok.Data;

import java.util.List;

/**
 * Created by htf on 2020/10/28.
 */
@Data
public class PlacePlaneResult extends Command {

    private List<Plane> planes;
    private Integer playerId;

    public PlacePlaneResult() {
        super(6);
    }
}
