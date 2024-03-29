package services;

import org.json.JSONObject;
import services.current_room.CurrentRoom;
import services.encrypt.Encrypt;
import services.game.Game;
import services.rooms.Rooms;

/**
 * General interface for server services. <code>Service</code> are the center of all the server functionalities.
 *
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 * @see Audio
 * @see CurrentRoom
 * @see Encrypt
 * @see Game
 * @see Login
 * @see Logout
 * @see Polling
 * @see Register
 * @see Rooms
 * @see Unknown
 */
public interface IService {

    /**
     * Elaborates client requests end responding to them according to the <code>Service</code> type.
     *
     * @return service response message
     */
    JSONObject start(JSONObject request);

    String getName();

}
