package cn.flypigeon.springbootdemo.bombplane.websocket;

import cn.flypigeon.springbootdemo.bombplane.Checkerboard;
import cn.flypigeon.springbootdemo.bombplane.Plane;
import cn.flypigeon.springbootdemo.bombplane.entity.BoomPoint;
import cn.flypigeon.springbootdemo.bombplane.entity.ErrorMessage;
import cn.flypigeon.springbootdemo.bombplane.entity.GameOver;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by htf on 2020/10/16.
 */
@ServerEndpoint("/plane/single/{userId}")
@Component
public class SinglePlayerServer {

    private Session session;
    private Checkerboard checkerboard;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
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
            checkerboard = new Checkerboard(10, 10);
            Random random = new Random();
            for (int i = 0; i < 3; ) {
                Plane plane = new Plane();
                plane.setX(random.nextInt(10));
                plane.setY(random.nextInt(10));
                plane.setDirection(random.nextInt(4));
                try {
                    checkerboard.addPlane(plane);
                } catch (IllegalOperationException e) {
                    continue;
                }
                i++;
            }
        } else if (code == 5) {
            Integer x = command.getInteger("x");
            Integer y = command.getInteger("y");
            Checkerboard.CheckerPoint point = checkerboard.attack(x, y);
            BoomPoint boomPoint = new BoomPoint();
            boomPoint.setOwner(1);
            boomPoint.setEnd(checkerboard.isEnd());
            boomPoint.setType(point.getType().code());
            boomPoint.setX(x);
            boomPoint.setY(y);
            sendJSON(boomPoint);
            if (checkerboard.isEnd()) {
                GameOver gameOver = new GameOver();
                gameOver.setPlanes(Arrays.asList(checkerboard.getPlanes()));
                sendJSON(gameOver);
            }
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
}
