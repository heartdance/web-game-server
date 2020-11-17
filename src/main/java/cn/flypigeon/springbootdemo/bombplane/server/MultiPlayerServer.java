package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.service.*;
import cn.flypigeon.springbootdemo.game.component.base.Hall;
import cn.flypigeon.springbootdemo.game.server.WebSocketServer;
import cn.flypigeon.springbootdemo.game.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by htf on 2020/10/16.
 */
@ServerEndpoint("/ws/plane/multi/{userId}/{userName}")
@Component
public class MultiPlayerServer extends WebSocketServer {

    private final static Service service;

    static {
        Service tempService = new PlayerReadyService(null);
        tempService = new JoinRoomService(tempService);
        tempService = new GetRoomService(tempService);
        tempService = new PlayerReadyService(tempService);
        tempService = new PlacePlaneService(tempService);
        tempService = new BoomPointService(tempService);
        service = tempService;
    }

    public static final Hall HALL = new Hall(10, 2);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("userName") String userName) {
        super.onOpen(session);
        player.setId(userId);
        player.setName(userName);
    }

    public void onMessage(String message) {
        JSONObject command = JSON.parseObject(message);
        Integer code = command.getInteger("code");
        service.process(code, this, command);
    }
}
