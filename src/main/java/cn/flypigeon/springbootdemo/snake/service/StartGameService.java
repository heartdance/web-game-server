package cn.flypigeon.springbootdemo.snake.service;

import cn.flypigeon.springbootdemo.game.component.base.Game;
import cn.flypigeon.springbootdemo.game.component.base.Room;
import cn.flypigeon.springbootdemo.game.entity.Command;
import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.snake.component.Snake;
import cn.flypigeon.springbootdemo.snake.entity.HardLevel;
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
        Room room = server.getPlayer().getRoom();
        Game game = room.getGame();
        if (game != null) {
            Snake snakeGame = (Snake) game;
            snakeGame.gameOver();
        }
        Snake snakeGame = new Snake();
        room.setGame(snakeGame);
        Integer level = command.getInteger("level");
        if (level != null) {
            snakeGame.setRate(HardLevel.ofLevel(level).rate());
        }
        snakeGame.startGame();
        server.sendJSON(new Command(1));
        SnakePoint snakePoint = new SnakePoint();
        snakePoint.setPoints(snakeGame.getPoints());
        snakePoint.setDirection(snakeGame.getDirection().code());
        server.sendJSON(snakePoint);
        Point food = snakeGame.getFood();
        server.sendJSON(new SnakeFood(food.getX(), food.getY()));
        flushSnakeStatusTimer(server, snakeGame);
    }

    private void flushSnakeStatusTimer(Server server, Snake game) {
        new Thread(() -> {
            while (game.getStatus() != 3) {
                try {
                    Thread.sleep(game.getRate());
                    long current = System.currentTimeMillis();
                    long interval = current - game.getLastMoveTime();
                    if (interval < game.getRate()) {
                        try {
                            Thread.sleep(game.getRate() - interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
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
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }
}
