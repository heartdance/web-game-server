package cn.flypigeon.springbootdemo.mineclearance.service;

import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.mineclearance.component.Checkerboard;
import cn.flypigeon.springbootdemo.mineclearance.component.MineClearance;
import cn.flypigeon.springbootdemo.mineclearance.entity.GameOver;
import cn.flypigeon.springbootdemo.mineclearance.entity.ShowPoint;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htf on 2020/11/16.
 */
public class ClickPointService extends Service {

    public ClickPointService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 2;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Integer x = command.getInteger("x");
        Integer y = command.getInteger("y");
        Integer type = command.getInteger("type");
        MineClearance game = (MineClearance) server.getPlayer().getRoom().getGame();
        Checkerboard checkerboard = game.getCheckerboard();
        Checkerboard.CheckerPoint point = checkerboard.getPoints()[x][y];
        if (type == 1) {
            if (point.getStatus() == 0) {
                point.setStatus(3);
                ShowPoint showPoint = new ShowPoint();
                List<ShowPoint.Point> points = new ArrayList<>();
                showPoint.setPoints(points);
                points.add(new ShowPoint.Point(point.getX(), point.getY(), point.getMineNum(), point.isMine()));
                if (point.isMine()) {
                    game.gameOver();
                    server.sendJSON(showPoint);
                    GameOver gameOver = new GameOver();
                    gameOver.setWin(false);
                    gameOver.setMines(new ArrayList<>());
                    List<GameOver.Point> mines = gameOver.getMines();
                    Checkerboard.CheckerPoint[] minePoints = checkerboard.getMinePoints();
                    for (Checkerboard.CheckerPoint minePoint : minePoints) {
                        mines.add(new GameOver.Point(minePoint.getX(), minePoint.getY()));
                    }
                    server.sendJSON(gameOver);
                } else {
                    if (point.getMineNum() == 0) {
                        showAround(checkerboard, x, y, points);
                    }
                    server.sendJSON(showPoint);
                    if (game.isEnd()) {
                        game.gameOver();
                        GameOver gameOver = new GameOver();
                        gameOver.setWin(true);
                        server.sendJSON(gameOver);
                    }
                }
            }
        }
    }

    private void showAround(Checkerboard checkerboard, int x, int y, List<ShowPoint.Point> points) {
        List<Checkerboard.CheckerPoint> around = checkerboard.around(x, y);
        for (Checkerboard.CheckerPoint point : around) {
            if (point.getStatus() != 3) {
                point.setStatus(3);
                points.add(new ShowPoint.Point(point.getX(), point.getY(), point.getMineNum(), false));
                if (point.getMineNum() == 0) {
                    showAround(checkerboard, point.getX(), point.getY(), points);
                }
            }
        }
    }
}
