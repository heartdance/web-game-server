package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.BombPlane;
import cn.flypigeon.springbootdemo.bombplane.component.Checkerboard;
import cn.flypigeon.springbootdemo.bombplane.component.Plane;
import cn.flypigeon.springbootdemo.bombplane.entity.Command;
import cn.flypigeon.springbootdemo.bombplane.entity.PlacePlaneResult;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * 放置飞机
 * 1. 检查游戏状态
 * 2. 反馈玩家飞机放置结果
 * Created by htf on 2020/10/23.
 */
public class PlacePlaneService extends Service {

    public PlacePlaneService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 4;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        JSONArray planes = command.getJSONArray("planes");
        if (planes.size() != 3) {
            throw new IllegalOperationException("飞机必须放置三架");
        }
        BombPlane game = (BombPlane) server.getPlayer().getRoom().getGame();
        Checkerboard checkerboard = game.getCheckerboard(server.getPlayer());
        for (int i = 0; i < planes.size(); i++) {
            JSONObject jo = planes.getJSONObject(i);
            Plane plane = new Plane();
            plane.setX(jo.getInteger("x"));
            plane.setY(jo.getInteger("y"));
            plane.setDirection(jo.getInteger("direction"));
            checkerboard.addPlane(plane);
        }
        PlacePlaneResult placePlaneResult = new PlacePlaneResult();
        placePlaneResult.setPlanes(Arrays.asList(checkerboard.getPlanes()));
        placePlaneResult.setPlayerId(server.getPlayer().getId());
        server.sendJSON(placePlaneResult);
        placePlaneResult.setPlanes(null);
        server.getPlayer().getRoom().broadcast(placePlaneResult, server.getPlayer());
        Checkerboard[] checkerboards = game.getCheckerboards();
        for (Checkerboard cb : checkerboards) {
            if (cb.getAliveCount() != 3) {
                return;
            }
        }
        game.startAttack();
        game.currentPlayer().send(new Command(7));
    }
}
