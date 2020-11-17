package cn.flypigeon.springbootdemo.minesweeper.service;

import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.minesweeper.component.Checkerboard;
import cn.flypigeon.springbootdemo.minesweeper.component.Minesweeper;
import cn.flypigeon.springbootdemo.minesweeper.entity.GameOver;
import cn.flypigeon.springbootdemo.minesweeper.entity.PointFlag;
import cn.flypigeon.springbootdemo.minesweeper.entity.ShowPoint;
import com.alibaba.fastjson.JSONObject;

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
        // 0: 左键 1:中键 2: 右键
        Integer type = command.getInteger("type");
        Minesweeper game = (Minesweeper) server.getPlayer().getRoom().getGame();
        if (game.isEnd()) {
            return;
        }
        Checkerboard checkerboard = game.getCheckerboard();
        Checkerboard.CheckerPoint point = checkerboard.getPoints()[x][y];
        if (type == 0) {
            if (point.getStatus() == 0) {
                point.setStatus(3);
                ShowPoint showPoint = new ShowPoint();
                List<ShowPoint.Point> points = showPoint.getPoints();
                points.add(new ShowPoint.Point(point.getX(), point.getY(), point.getMineNum(), point.isMine()));
                if (point.isMine()) {
                    game.gameOver();
                    server.sendJSON(showPoint);
                    server.sendJSON(createGameOverOfLose(checkerboard));
                } else {
                    if (point.getMineNum() == 0) {
                        showAround(checkerboard, x, y, points);
                    }
                    server.sendJSON(showPoint);
                    if (game.isEnd()) {
                        game.gameOver();
                        server.sendJSON(GameOver.ofWin());
                    }
                }
            } else {
                point.setStatus(0);
                PointFlag pointFlag = new PointFlag(point.getX(), point.getY(), 0);
                server.sendJSON(pointFlag);
            }
        } else if (type == 1) {
            if (point.getStatus() == 3) {
                List<Checkerboard.CheckerPoint> around = checkerboard.around(point.getX(), point.getY());
                int flagMineCount = 0;
                boolean existNotShow = false;
                for (Checkerboard.CheckerPoint p : around) {
                    if (p.getStatus() == 3) {
                        continue;
                    }
                    existNotShow = true;
                    if (p.getStatus() == 1) {
                        flagMineCount++;
                    } else if (p.getStatus() == 2) {
                        return;
                    }
                }
                if (existNotShow && flagMineCount == point.getMineNum()) {
                    ShowPoint showPoint = new ShowPoint();
                    List<ShowPoint.Point> points = showPoint.getPoints();
                    boolean hasMine = false;
                    for (Checkerboard.CheckerPoint p : around) {
                        if (p.getStatus() == 0) {
                            if (p.isMine()) {
                                hasMine = true;
                            }
                            p.setStatus(3);
                            points.add(new ShowPoint.Point(p.getX(), p.getY(), p.getMineNum(), p.isMine()));
                            if (p.getMineNum() == 0) {
                                showAround(checkerboard, p.getX(), p.getY(), points);
                            }
                        }
                    }
                    server.sendJSON(showPoint);
                    if (hasMine) {
                        game.gameOver();
                        server.sendJSON(createGameOverOfLose(checkerboard));
                    } else if (game.isEnd()) {
                        game.gameOver();
                        server.sendJSON(GameOver.ofWin());
                    }
                }
            }
        } else if (type == 2) {
            if (point.getStatus() != 3) {
                point.setStatus((point.getStatus() + 1) % 3);
                PointFlag pointFlag = new PointFlag(point.getX(), point.getY(), point.getStatus());
                server.sendJSON(pointFlag);
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

    private static GameOver createGameOverOfLose(Checkerboard checkerboard) {
        GameOver gameOver = GameOver.ofLose();
        List<GameOver.Point> mines = gameOver.getMines();
        Checkerboard.CheckerPoint[] minePoints = checkerboard.getMinePoints();
        for (Checkerboard.CheckerPoint minePoint : minePoints) {
            mines.add(new GameOver.Point(minePoint.getX(), minePoint.getY()));
        }
        return gameOver;
    }

}
