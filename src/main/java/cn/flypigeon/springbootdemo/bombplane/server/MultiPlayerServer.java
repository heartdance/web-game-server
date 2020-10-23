package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.component.base.Hall;
import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.entity.ErrorMessage;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import cn.flypigeon.springbootdemo.bombplane.service.GetRoomService;
import cn.flypigeon.springbootdemo.bombplane.service.JoinRoomService;
import cn.flypigeon.springbootdemo.bombplane.service.PlayerReadyService;
import cn.flypigeon.springbootdemo.bombplane.service.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by htf on 2020/10/16.
 */
@ServerEndpoint("/plane/multi/{userId}/{userName}")
@Component
public class MultiPlayerServer implements Server {

    private final static Service service;

    static {
        Service tempService = new PlayerReadyService(null);
        tempService = new JoinRoomService(tempService);
        tempService = new GetRoomService(tempService);
        service = tempService;
    }

    public static final Hall HALL = new Hall(1, 2);
    public static final Map<Integer, MultiPlayerServer> PLAYER_MAP = new ConcurrentHashMap<>();

    private Session session;
    private Player player;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("userName") String userName) {
        this.session = session;
        player = new Player();
        player.setId(userId);
        player.setName(userName);
        PLAYER_MAP.put(player.getId(), this);
    }

    @OnClose
    public void onClose() {

    }

    @OnMessage
    public void warpOnMessage(String message, Session session) {
        try {
            onMessage(message, session);
        } catch (IllegalOperationException e) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage(e.getMessage());
            sendJSON(errorMessage);
        }
    }

    public void onMessage(String message, Session session) {
        JSONObject command = JSON.parseObject(message);
        Integer code = command.getInteger("code");
        service.process(code, this, command);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        sendMessage("");
    }

    public void sendJSON(Object json) {
        try {
            this.session.getBasicRemote().sendText(JSON.toJSONString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return this.player;
    }
}
