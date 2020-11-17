package cn.flypigeon.springbootdemo.minesweeper.service;

import cn.flypigeon.springbootdemo.game.entity.Command;
import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.minesweeper.component.Minesweeper;
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
        Minesweeper game = (Minesweeper) server.getPlayer().getRoom().getGame();
        game.startGame();
        server.sendJSON(new Command(1));
    }
}
