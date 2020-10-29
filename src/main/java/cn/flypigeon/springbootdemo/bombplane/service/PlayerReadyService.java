package cn.flypigeon.springbootdemo.bombplane.service;

import cn.flypigeon.springbootdemo.bombplane.component.BombPlane;
import cn.flypigeon.springbootdemo.bombplane.component.base.Player;
import cn.flypigeon.springbootdemo.bombplane.entity.Command;
import cn.flypigeon.springbootdemo.bombplane.entity.PlayerReady;
import cn.flypigeon.springbootdemo.bombplane.server.MultiPlayerServer;
import cn.flypigeon.springbootdemo.bombplane.server.Server;
import com.alibaba.fastjson.JSONObject;

/**
 * 准备/取消准备
 * 1. 检查游戏状态
 * 1. 检查是否全部准备就绪，是则开始飞机放置
 * 2. 反馈给房间内所有玩家该准备状态
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
                playerReady.setPlayer(thePlayer);
                player.send(playerReady);
            }
        }
        if (ready) {
            if (thePlayer.getRoom().allReady()) {
                BombPlane bombPlane = new BombPlane(2);
                bombPlane.setPlayers(players);
                bombPlane.startPlacePlane();
                thePlayer.getRoom().setGame(bombPlane);
                for (Player player : players) {
                    player.send(new Command(5));
                }
            }
        }
    }
}
