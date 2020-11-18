package cn.flypigeon.springbootdemo.snake.entity;

import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

import java.util.List;

@Data
public class SnakePoint extends Command {

    private List<Point> points;
    private Integer direction;

    public SnakePoint() {
        super(2);
    }

}
