package cn.flypigeon.springbootdemo.bombplane.websocket;

import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by htf on 2020/10/16.
 */
@ServerEndpoint("/plane/multi/{userId}")
@Component
public class MultiPlayerServer {

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
    }

    @OnClose
    public void onClose() {

    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnError
    public void onError(Session session, Throwable error) {
        sendMessage("");
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object obj) {
        try {
            this.session.getBasicRemote().sendObject(obj);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
