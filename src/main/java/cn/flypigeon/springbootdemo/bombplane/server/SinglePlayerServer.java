package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.component.base.Room;
import cn.flypigeon.springbootdemo.bombplane.entity.ErrorMessage;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import cn.flypigeon.springbootdemo.bombplane.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by htf on 2020/10/16.
 */
@ServerEndpoint("/ws/plane/single/{userId}/{userName}")
@Component
public class SinglePlayerServer implements Server {

    private Session session;
    private Player player;

    private final static Service service;

    static {
        Service tempService = new RandomPlacePlaneService(null);
        tempService = new SingleBoomPointService(tempService);
        service = tempService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("userName") String userName) {
        this.session = session;
        player = new Player();
        player.setRoom(new Room(1, 1));
        player.setId(userId);
        player.setSender(this);
        player.setName(userName);
    }

    @OnClose
    public void onClose() {

    }

    @OnMessage
    public void warpOnMessage(String message) {
        try {
            onMessage(message);
        } catch (IllegalOperationException e) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage(e.getMessage());
            sendJSON(errorMessage);
        }
    }

    public void onMessage(String message) {
        JSONObject command = JSON.parseObject(message);
        Integer code = command.getInteger("code");
        service.process(code, this, command);
    }

    @OnError
    public void onError(Throwable error) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(error.getMessage());
        sendJSON(errorMessage);
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendJSON(Object json) {
        try {
            this.session.getBasicRemote().sendText(JSON.toJSONString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
