package cn.flypigeon.springbootdemo.minesweeper.server;

import cn.flypigeon.springbootdemo.game.component.base.Room;
import cn.flypigeon.springbootdemo.game.server.WebSocketServer;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.minesweeper.component.Minesweeper;
import cn.flypigeon.springbootdemo.minesweeper.service.ClickPointService;
import cn.flypigeon.springbootdemo.minesweeper.service.StartGameService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by htf on 2020/11/10.
 */
@ServerEndpoint("/ws/mine")
@Component
public class MinesweeperServer extends WebSocketServer {

    private static final Service service;

    static {
        Service temp = new StartGameService(null);
        temp = new ClickPointService(temp);
        service = temp;
    }

    @OnOpen
    public void onOpen(Session session) {
        super.onOpen(session);
        player.setId(1);
        player.setRoom(new Room(1, 1));
        player.getRoom().setGame(new Minesweeper());
    }

    @Override
    public void onMessage(String message) {
        JSONObject command = JSON.parseObject(message);
        Integer code = command.getInteger("code");
        service.process(code, this, command);
    }
}
