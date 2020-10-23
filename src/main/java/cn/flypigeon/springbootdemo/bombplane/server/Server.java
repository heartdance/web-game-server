package cn.flypigeon.springbootdemo.bombplane.server;

import cn.flypigeon.springbootdemo.bombplane.component.base.Player;

/**
 * Created by htf on 2020/10/23.
 */
public interface Server {

    void sendJSON(Object json);

    Player getPlayer();
}
