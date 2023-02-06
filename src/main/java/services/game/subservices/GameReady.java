package services.game.subservices;

import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Player;
import messages.fields_names.CommonFields;
import messages.fields_names.GameFields;
import messages.fields_names.RoomFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IService;

/**
 * A {@link GameStatus} sub-service. After fully charged the game map the {@link Player} must send a Ready Message
 * informing the server that he's ready to start the game.
 *
 * @author Micieli
 * @date 2015/05/24
 */
public class GameReady implements IService {

    @Override
    public JSONObject start(JSONObject request) {
        try {
            String roomName = request.getString(RoomFields.ROOM_NAME.toString());
            String username = request.getString(CommonFields.USERNAME.toString());
            Player player = GameDataManager.getInstance().getRoomByName(roomName).getPlayerByName(username);
            player.setStatus(request.getBoolean(GameFields.GAME_READY.toString()));
        } catch (JSONException | NoSuchRoomException | NoSuchPlayerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() {
        return GameFields.GAME_READY.toString();
    }

}
