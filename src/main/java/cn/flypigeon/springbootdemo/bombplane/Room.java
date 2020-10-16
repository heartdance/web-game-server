package cn.flypigeon.springbootdemo.bombplane;

import cn.flypigeon.springbootdemo.bombplane.exception.IllegalOperationException;
import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Room {

    private Integer id;

    private Player player1;

    private Player player2;

    private Game game;

    public Game startGame() {
        if (player1 == null || player2 == null) {
            throw new IllegalOperationException("人不齐");
        }
        game = new Game();
        return game;
    }
}
