package cn.flypigeon.springbootdemo.snake.server;

import cn.flypigeon.springbootdemo.game.component.base.Room;
import cn.flypigeon.springbootdemo.game.server.WebSocketServer;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.snake.component.Snake;
import cn.flypigeon.springbootdemo.snake.service.ChangeDirectionService;
import cn.flypigeon.springbootdemo.snake.service.StartGameService;
import cn.flypigeon.springbootdemo.snake.service.StopGameService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by htf on 2020/11/17.
 */
@ServerEndpoint("/ws/snake")
@Component
public class SnakeServer extends WebSocketServer {

    private static final Service service;

    static {
        Service temp = new StartGameService(null);
        temp = new ChangeDirectionService(temp);
        temp = new StopGameService(temp);
        service = temp;
    }

    @OnOpen
    public void onOpen(Session session) {
        super.onOpen(session);
        player.setRoom(new Room(1, 1));
        player.getRoom().setGame(new Snake());
    }

    @Override
    public void onMessage(String message) {
        JSONObject command = JSON.parseObject(message);
        Integer code = command.getInteger("code");
        service.process(code, this, command);
    }
}
