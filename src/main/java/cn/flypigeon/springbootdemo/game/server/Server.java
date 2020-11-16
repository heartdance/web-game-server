package cn.flypigeon.springbootdemo.game.server;

import cn.flypigeon.springbootdemo.game.component.base.Player;
import cn.flypigeon.springbootdemo.game.component.base.Sender;

/**
 * Created by htf on 2020/10/23.
 */
public interface Server extends Sender {

    @Override
    default void send(Object message) {
        sendJSON(message);
    }

    void sendJSON(Object json);

    Player getPlayer();
}
