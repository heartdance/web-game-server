package cn.flypigeon.springbootdemo.snake.service;

import cn.flypigeon.springbootdemo.game.entity.Command;
import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.snake.component.Snake;
import cn.flypigeon.springbootdemo.snake.entity.Point;
import cn.flypigeon.springbootdemo.snake.entity.SnakeFood;
import cn.flypigeon.springbootdemo.snake.entity.SnakePoint;
import com.alibaba.fastjson.JSONObject;

public class StartGameService extends Service {

    public StartGameService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 1;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Snake game = new Snake();
        server.getPlayer().getRoom().setGame(game);
        game.startGame();
        server.sendJSON(new Command(1));
        flushSnakeStatusTimer(server, game);
    }

    private void flushSnakeStatusTimer(Server server, Snake game) {
        new Thread(() -> {
            while (game.getStatus() != 3) {
                if (game.getStatus() == 1) {
                    Snake.MoveResult moveResult = game.move();
                    if (moveResult == Snake.MoveResult.NOTHING || moveResult == Snake.MoveResult.EAT) {
                        SnakePoint snakePoint = new SnakePoint();
                        snakePoint.setPoints(game.getPoints());
                        server.sendJSON(snakePoint);
                        if (moveResult == Snake.MoveResult.EAT) {
                            Point food = game.getFood();
                            server.sendJSON(new SnakeFood(food.getX(), food.getY()));
                        }
                    } else if (moveResult == Snake.MoveResult.COLLIDE) {
                        game.gameOver();
                        server.sendJSON(new Command(5));
                        break;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }
}
