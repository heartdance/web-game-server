package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.component.base.Sender;

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
