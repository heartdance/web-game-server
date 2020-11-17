package cn.flypigeon.springbootdemo.snake.service;

import cn.flypigeon.springbootdemo.game.server.Server;
import cn.flypigeon.springbootdemo.game.service.Service;
import cn.flypigeon.springbootdemo.snake.component.Snake;
import cn.flypigeon.springbootdemo.snake.entity.StopGame;
import com.alibaba.fastjson.JSONObject;

public class StopGameService extends Service {

    public StopGameService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 3;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        // 1继续 2暂停
        Integer type = command.getInteger("type");
        Snake game = (Snake) server.getPlayer().getRoom().getGame();
        if (type == 2) {
            game.stop();
            server.sendJSON(StopGame.ofStop());
        } else if (type == 1) {
            game.resume();
            server.sendJSON(StopGame.ofResume());
        }
    }
}
