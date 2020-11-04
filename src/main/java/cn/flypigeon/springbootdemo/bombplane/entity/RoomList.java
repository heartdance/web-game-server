package cn.flypigeon.springbootdemo.bombplane.entity;

import cn.flypigeon.springbootdemo.bombplane.component.base.Room;
import lombok.Data;

@Data
public class RoomList extends Command {

    private Room[] rooms;

    public RoomList() {
        super(1);
    }
}
