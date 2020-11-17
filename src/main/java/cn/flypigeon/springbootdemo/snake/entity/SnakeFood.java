package cn.flypigeon.springbootdemo.snake.entity;

import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

@Data
public class SnakeFood extends Command {

    private int x;
    private int y;

    public SnakeFood(int x, int y) {
        super(3);
        this.x = x;
        this.y = y;
    }
}
