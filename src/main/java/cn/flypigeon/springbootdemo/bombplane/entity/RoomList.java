package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.game.component.base.Room;
import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

@Data
public class RoomList extends Command {

    private Room[] rooms;

    public RoomList() {
        super(1);
    }
}
