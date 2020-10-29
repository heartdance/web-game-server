package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.base.Room;
import cn.flypigeon.springbootdemo.bombplane.server.MultiPlayerServer;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取房间列表
 * 反馈给此玩家所有房间信息，包括房间内玩家信息
 * Created by htf on 2020/10/23.
 */
public class GetRoomService extends Service {

    public GetRoomService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 1;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Room[] rooms = MultiPlayerServer.HALL.getRooms();
        server.sendJSON(rooms);
    }
}
