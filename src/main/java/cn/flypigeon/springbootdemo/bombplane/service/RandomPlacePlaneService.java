package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.BombPlane;
import cn.flypigeon.springbootdemo.bombplane.component.Checkerboard;
import cn.flypigeon.springbootdemo.bombplane.component.Plane;
import cn.flypigeon.springbootdemo.bombplane.entity.Command;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;

/**
 * 随机生成飞机位置
 * Created by htf on 2020/10/23.
 */
public class RandomPlacePlaneService extends Service {

    public RandomPlacePlaneService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 3;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        BombPlane game = new BombPlane(1);
        server.getPlayer().getRoom().setGame(game);
        randomPlacePlaneToBoard(game.getCheckerboards()[0]);
        server.sendJSON(new Command(5));
    }

    public static void randomPlacePlaneToBoard(Checkerboard checkerboard) {
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
    }
}
