package cn.flypigeon.springbootdemo.bombplane;

import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Player {

    private Integer id;

    private String name;

    private Room room;

    public Room createRoom() {
        this.room = Hall.INSTANCE.createRoom(this);
        return this.room;
    }

    public Room joinRoom(Integer id) {
        Room room = Hall.INSTANCE.getRoom(id);
        if (room.getPlayer1() == this || room.getPlayer2() == this) {
            return room;
        }
        if (room.getPlayer1() == null) {
            room.setPlayer1(this);
            this.room = room;
            return room;
        }
        if (room.getPlayer2() == null) {
            room.setPlayer2(this);
            this.room = room;
            return room;
        }
        throw new IllegalOperationException("房间已满");
    }

    public void exitRoom() {
        if (this.room != null) {
            if (this.room.getPlayer1() == this) {
                this.room.setPlayer1(null);
            } else if (this.room.getPlayer2() == this) {
                this.room.setPlayer2(null);
            }
        }
    }
}
