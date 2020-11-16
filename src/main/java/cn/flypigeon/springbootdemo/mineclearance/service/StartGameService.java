package cn.flypigeon.springbootdemo.mineclearance.service;

import cn.flypigeon.springbootdemo.bombplane.entity.Command;
import cn.flypigeon.springbootdemo.game.component.base.Game;
import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.mineclearance.component.MineClearance;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by htf on 2020/11/16.
 */
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
        MineClearance game = (MineClearance) server.getPlayer().getRoom().getGame();
        game.startGame();
        server.sendJSON(new Command(1));
    }
}
