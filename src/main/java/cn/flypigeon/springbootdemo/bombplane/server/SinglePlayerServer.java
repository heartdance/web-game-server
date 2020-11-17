package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.service.RandomPlacePlaneService;
import cn.flypigeon.springbootdemo.bombplane.service.SingleBoomPointService;
import cn.flypigeon.springbootdemo.game.component.base.Room;
import cn.flypigeon.springbootdemo.game.server.WebSocketServer;
import cn.flypigeon.springbootdemo.game.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by htf on 2020/10/16.
 */
@ServerEndpoint("/ws/plane/single/{userId}/{userName}")
@Component
public class SinglePlayerServer extends WebSocketServer {

    private final static Service service;

    static {
        Service tempService = new RandomPlacePlaneService(null);
        tempService = new SingleBoomPointService(tempService);
        service = tempService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("userName") String userName) {
        super.onOpen(session);
        player.setRoom(new Room(1, 1));
        player.setId(userId);
        player.setName(userName);
    }

    @OnClose
    public void onClose() {

    }

    public void onMessage(String message) {
        JSONObject command = JSON.parseObject(message);
        Integer code = command.getInteger("code");
        service.process(code, this, command);
    }

}
