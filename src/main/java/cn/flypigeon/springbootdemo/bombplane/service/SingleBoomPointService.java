package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.BombPlane;
import cn.flypigeon.springbootdemo.bombplane.component.Checkerboard;
import cn.flypigeon.springbootdemo.bombplane.entity.BoomPoint;
import cn.flypigeon.springbootdemo.bombplane.entity.GameOver;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * 单人炸飞机
 * 炸后反馈被炸点的状态
 * Created by htf on 2020/10/23.
 */
public class SingleBoomPointService extends Service {

    public SingleBoomPointService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 5;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Integer x = command.getInteger("x");
        Integer y = command.getInteger("y");
        Checkerboard checkerboard = ((BombPlane) server.getPlayer().getRoom().getGame()).getCheckerboards()[0];
        Checkerboard.CheckerPoint point = checkerboard.attack(x, y);
        BoomPoint boomPoint = new BoomPoint();
        boomPoint.setOwner(1);
        boomPoint.setEnd(checkerboard.isEnd());
        boomPoint.setType(point.getType().code());
        boomPoint.setX(x);
        boomPoint.setY(y);
        server.sendJSON(boomPoint);
        if (checkerboard.isEnd()) {
            GameOver gameOver = new GameOver();
            gameOver.setPlanes(Arrays.asList(checkerboard.getPlanes()));
            server.sendJSON(gameOver);
        }
    }
}
