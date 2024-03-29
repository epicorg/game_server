package services.current_room.subservices;

import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import game.model.Room;
import messages.CurrentRoomMessagesCreator;
import messages.fields_names.RoomFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IService;
import services.game.Game;

/**
 * A {@link Game} sub-service that responds to the player list request of a client providing the full list currently
 * in the requested {@link Room}.
 *
 * @author Micieli
 * @date 2015/05/24
 */

public class PlayerList implements IService {

    private CurrentRoomMessagesCreator messagesCreator;

    public PlayerList() {
        super();
        messagesCreator = new CurrentRoomMessagesCreator();
    }

    @Override
    public JSONObject start(JSONObject request) {
        try {
            String roomName = request.getString(RoomFields.ROOM_NAME.toString());
            Room room = GameDataManager.getInstance().getRoomByName(roomName);
            return messagesCreator.generatePlayersListMessage(room);
        } catch (JSONException | NoSuchRoomException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() {
        return RoomFields.ROOM_PLAYER_LIST.toString();
    }

}
