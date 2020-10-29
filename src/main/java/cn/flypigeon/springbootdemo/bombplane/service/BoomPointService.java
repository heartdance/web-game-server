package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.BombPlane;
import cn.flypigeon.springbootdemo.bombplane.component.Checkerboard;
import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.entity.BoomPoint;
import cn.flypigeon.springbootdemo.bombplane.entity.GameOver;
import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

/**
 * 炸点
 * 1. 检查是否轮到此玩家
 * 2. 炸点
 * 3. 反馈给所有玩家被炸点的信息
 * Created by htf on 2020/10/23.
 */
public class BoomPointService extends Service {

    public BoomPointService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 5;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        BombPlane game = (BombPlane) server.getPlayer().getRoom().getGame();
        Player player = game.currentPlayer();
        if (server.getPlayer().equals(player)) {
            throw new IllegalOperationException("还没轮到你");
        }
        Integer x = command.getInteger("x");
        Integer y = command.getInteger("y");
        Checkerboard.CheckerPoint checkerPoint = game.anotherCheckerboard().attack(x, y);
        BoomPoint boomPoint = new BoomPoint();
        boomPoint.setX(x);
        boomPoint.setY(y);
        boomPoint.setType(checkerPoint.getType().code());
        boomPoint.setOwner(game.anotherPlayer().getId());
        boomPoint.setEnd(game.isEnd());
        player.getRoom().broadcast(boomPoint);
        if (boomPoint.isEnd()) {
            game.gameOver();
            GameOver gameOver = new GameOver();
            Player[] players = game.getPlayers();
            Checkerboard[] checkerboards = game.getCheckerboards();
            gameOver.setPlanes(Arrays.asList(checkerboards[1].getPlanes()));
            players[0].send(gameOver);
            gameOver.setPlanes(Arrays.asList(checkerboards[0].getPlanes()));
            players[1].send(gameOver);
        }
        game.next();
    }
}
