package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.game.component.base.Player;
import cn.flypigeon.springbootdemo.game.entity.Command;
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
