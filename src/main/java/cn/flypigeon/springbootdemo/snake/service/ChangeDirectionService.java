package cn.flypigeon.springbootdemo.snake.service;

import cn.flypigeon.springbootdemo.game.entity.Command;
import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.snake.component.Snake;
import cn.flypigeon.springbootdemo.snake.entity.Point;
import cn.flypigeon.springbootdemo.snake.entity.SnakeFood;
import cn.flypigeon.springbootdemo.snake.entity.SnakePoint;
import cn.flypigeon.springbootdemo.snake.entity.StopGame;
import com.alibaba.fastjson.JSONObject;

public class ChangeDirectionService extends Service {

    public ChangeDirectionService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 2;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Integer code = command.getInteger("direction");
        Snake.Direction direction = Snake.Direction.ofCode(code);
        Snake game = (Snake) server.getPlayer().getRoom().getGame();
        if (game.getStatus() == 2) {
            game.resume();
            server.sendJSON(StopGame.ofResume());
        }
        if (game.getStatus() == 1 && direction != null &&
                game.getDirection() != direction && game.getDirection().negative() != direction) {
            game.setDirection(direction);
            Snake.MoveResult moveResult = game.move();

            SnakePoint snakePoint = new SnakePoint();
            snakePoint.setPoints(game.getPoints());
            snakePoint.setDirection(direction.code());
            server.sendJSON(snakePoint);

            if (moveResult == Snake.MoveResult.NOTHING || moveResult == Snake.MoveResult.EAT) {
                if (moveResult == Snake.MoveResult.EAT) {
                    Point food = game.getFood();
                    server.sendJSON(new SnakeFood(food.getX(), food.getY()));
                }
            } else if (moveResult == Snake.MoveResult.COLLIDE) {
                game.gameOver();
                server.sendJSON(new Command(5));
            }
        }
    }
}
