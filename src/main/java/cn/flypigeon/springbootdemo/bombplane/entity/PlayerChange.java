package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.game.component.base.Player;
import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

/**
 * 房间内玩家变动
 * Created by htf on 2020/10/28.
 */
@Data
public class PlayerChange extends Command {

    private Player player;
    private Boolean join;

    public PlayerChange() {
        super(3);
    }
}
