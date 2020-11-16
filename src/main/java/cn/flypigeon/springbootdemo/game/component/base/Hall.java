package cn.flypigeon.springbootdemo.game.component.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by htf on 2020/10/16.
 */
public class Hall {

    private final Room[] rooms;
    private final Map<Integer, Room> roomMap = new HashMap<>();

    public Hall(int roomCount, int roomPlayerCount) {
        rooms = new Room[roomCount];
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = createRoom(i, roomPlayerCount);
        }
    }

    private Room createRoom(int id, int roomPlayerCount) {
        Room room = new Room(id, roomPlayerCount);
        roomMap.put(id, room);
        return room;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Room getRoom(Integer id) {
        return roomMap.get(id);
    }
}
