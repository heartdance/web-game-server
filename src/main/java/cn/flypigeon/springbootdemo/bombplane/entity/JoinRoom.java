package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import lombok.Data;

/**
 * Created by htf on 2020/10/22.
 */
@Data
public class JoinRoom extends Command {

    private Integer roomId;
    private Player player;

    public JoinRoom() {
        super(2);
    }

}
