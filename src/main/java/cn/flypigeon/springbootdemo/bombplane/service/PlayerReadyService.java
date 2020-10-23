package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.BombPlane;
import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.entity.PlayerReady;
import cn.flypigeon.springbootdemo.bombplane.server.MultiPlayerServer;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by htf on 2020/10/23.
 */
public class PlayerReadyService extends Service {

    public PlayerReadyService(Service next) {
        super(next);
    }

    @Override
    protected int getCode() {
        return 3;
    }

    @Override
    protected void process0(Server server, JSONObject command) {
        Player thePlayer = server.getPlayer();
        Boolean ready = command.getBoolean("ready");
        if (ready) {
            thePlayer.ready();
        } else {
            thePlayer.cancelReady();
        }
        Player[] players = thePlayer.getRoom().getPlayers();
        for (Player player : players) {
            if (player != null) {
                PlayerReady playerReady = new PlayerReady();
                playerReady.setId(thePlayer.getId());
                playerReady.setReady(ready);
                MultiPlayerServer.PLAYER_MAP.get(player.getId()).sendJSON(playerReady);
            }
        }
        if (ready) {
            if (thePlayer.getRoom().allReady()) {
                BombPlane bombPlane = new BombPlane(2);
                bombPlane.setPlayers(thePlayer.getRoom().getPlayers());
                bombPlane.startPlacePlane();
                thePlayer.getRoom().setGame(bombPlane);
            }
        }
    }
}
