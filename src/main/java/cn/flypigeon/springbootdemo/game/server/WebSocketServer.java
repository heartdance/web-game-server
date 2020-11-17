package cn.flypigeon.springbootdemo.game.server;

import cn.flypigeon.springbootdemo.game.entity.ErrorMessage;
import cn.flypigeon.springbootdemo.game.component.base.Player;
import cn.flypigeon.springbootdemo.game.component.base.Room;
import cn.flypigeon.springbootdemo.game.exception.IllegalOperationException;
import com.alibaba.fastjson.JSON;

import javax.websocket.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by htf on 2020/11/16.
 */
public abstract class WebSocketServer implements Server {

    protected Session session;
    protected Player player;

    @OnClose
    public void onClose() {
        this.player.setOnline(false);
        Room room = player.getRoom();
        if (room != null) {
            Player[] players = room.getPlayers();
            if (players != null) {
                for (Player player : players) {
                    if (player != null && player.isOnline()) {
                        return;
                    }
                }
                Arrays.fill(players, null);
            }
        }
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

    public abstract void onMessage(String message);

    @OnError
    public void onError(Throwable error) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(error.getMessage() == null ? "发生错误..." : error.getMessage());
        sendJSON(errorMessage);
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
