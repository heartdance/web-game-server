package cn.flypigeon.springbootdemo.snake.entity;

import cn.flypigeon.springbootdemo.game.entity.Command;
import lombok.Data;

@Data
public class StopGame extends Command {

    private int type;

    public StopGame(int type) {
        super(4);
        this.type = type;
    }

    public static StopGame ofResume() {
        return new StopGame(1);
    }

    public static StopGame ofStop() {
        return new StopGame(2);
    }

}
