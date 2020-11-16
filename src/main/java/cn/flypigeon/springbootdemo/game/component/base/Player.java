package cn.flypigeon.springbootdemo.game.component.base;

import cn.flypigeon.springbootdemo.game.exception.IllegalOperationException;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Objects;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Player {

    private Integer id;

    private String name;

    @JSONField(serialize = false)
    private Sender sender;

    @JSONField(serialize = false)
    private Room room;

    private boolean ready;

    private boolean online;

    public void ready() {
        if (this.room == null) {
            throw new IllegalOperationException("未进入房间");
        }
        this.ready = true;
    }

    public void cancelReady() {
        this.ready = false;
    }

    public void joinRoom(Room room) {
        exitRoom();
        if (room.addPlayer(this)) {
            this.room = room;
        } else {
            throw new IllegalOperationException("房间已满");
        }
    }

    public void exitRoom() {
        if (this.room != null) {
            this.room.removePlayer(this);
            this.room = null;
            this.ready = false;
        }
    }

    public void send(Object message) {
        sender.send(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
