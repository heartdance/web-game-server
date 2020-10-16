package cn.flypigeon.springbootdemo.bombplane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by htf on 2020/10/16.
 */
public class Hall {

    public static final Hall INSTANCE = new Hall();

    private int id = 1;

    private List<Room> rooms = new ArrayList<>();
    private Map<Integer, Room> roomMap = new HashMap<>();

    public synchronized Room createRoom(Player creator) {
        int id = this.id++;
        Room room = new Room();
        room.setPlayer1(creator);
        rooms.add(room);
        roomMap.put(id, room);
        return room;
    }

    public synchronized List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public synchronized Room getRoom(Integer id) {
        return roomMap.get(id);
    }
}
