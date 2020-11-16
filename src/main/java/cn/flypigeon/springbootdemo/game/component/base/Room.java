package cn.flypigeon.springbootdemo.game.component.base;

import lombok.Data;

/**
 * Created by htf on 2020/10/16.
 */
@Data
public class Room {

    private Integer id;

    private Player[] players;

    private Game game;

    public Room(int id, int playerCount) {
        this.id = id;
        this.players = new Player[playerCount];
    }

    boolean addPlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null || players[i].equals(player)) {
                players[i] = player;
                return true;
            }
        }
        return false;
    }

    void removePlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null && players[i].equals(player)) {
                players[i] = null;
                break;
            }
        }
    }

    public boolean allReady() {
        for (Player player : players) {
            if (player == null || !player.isReady()) {
                return false;
            }
        }
        return true;
    }

    public void broadcast(Object message, Player exclude) {
        for (Player player : players) {
            if (player != null && (exclude == null || !exclude.equals(player))) {
                player.send(message);
            }
        }
    }

    public void broadcast(Object message) {
        for (Player player : players) {
            if (player != null) {
                player.send(message);
            }
        }
    }

}
