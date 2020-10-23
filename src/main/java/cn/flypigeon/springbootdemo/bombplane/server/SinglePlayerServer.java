package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.component.base.Room;
import cn.flypigeon.springbootdemo.bombplane.entity.ErrorMessage;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
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
@ServerEndpoint("/plane/single/{userId}/{userName}")
@Component
public class SinglePlayerServer implements Server {

    private Session session;
    private Player player;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("userName") String userName) {
        this.session = session;
        player = new Player();
        player.setRoom(new Room(1, 1));
        player.setId(userId);
        player.setName(userName);
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
        if (code == 3) {

        } else if (code == 5) {

        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
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
