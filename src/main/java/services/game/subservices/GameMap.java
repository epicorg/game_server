package services.game.subservices;

import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import game.map.MapJSONizer;
import game.model.PlayerStatus;
import game.model.Room;
import messages.GameMessagesCreator;
import messages.fields_names.GameFields;
import messages.fields_names.RoomFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IService;
import services.game.Game;

/**
 * A {@link Game} sub-service. This service sends to the client the generated map for the current {@link Room} in
 * which the player is entered.
 *
 * @author Micieli
 * @date 2015/05/24
 * @see game.map
 */
public class GameMap implements IService {

    private GameMessagesCreator messagesCreator;

    public GameMap() {
        super();
        this.messagesCreator = new GameMessagesCreator();
    }

    @Override
    public JSONObject start(JSONObject request) {
        try {

            Room room;
            try {
                room = GameDataManager.getInstance().getRoomByName(request.getString(RoomFields.ROOM_NAME.toString()));
            } catch (NoSuchRoomException e) {
                e.printStackTrace();
                return null;
            }

            MapJSONizer mapJSONizer = new MapJSONizer(room.getMap());
            mapJSONizer.generateMap();

            PlayerStatus spawnPoint = room.getMap().getSpawnPoint();

            return messagesCreator.generateMapMessage(mapJSONizer.getMap(), MapJSONizer.getAdaptedSpawnPoints(spawnPoint));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getName() {
        return GameFields.GAME_MAP.toString();
    }

}
