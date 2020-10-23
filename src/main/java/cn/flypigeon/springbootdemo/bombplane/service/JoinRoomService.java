package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.component.base.Room;
import cn.flypigeon.springbootdemo.bombplane.entity.JoinRoom;
import cn.flypigeon.springbootdemo.bombplane.server.MultiPlayerServer;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by htf on 2020/10/23.
 */
public class JoinRoomService extends Service {

    public JoinRoomService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 2;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Room room = MultiPlayerServer.HALL.getRoom(command.getInteger("roomId"));
        Player thePlayer = server.getPlayer();
        thePlayer.joinRoom(room);
        JoinRoom joinRoom = new JoinRoom();
        joinRoom.setRoomId(room.getId());
        Player[] players = room.getPlayers();
        for (Player player : players) {
            if (player != null && !player.equals(thePlayer)) {
                joinRoom.setPlayer(player);
            }
        }
        server.sendJSON(joinRoom);
    }
}
