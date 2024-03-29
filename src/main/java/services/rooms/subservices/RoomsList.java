package services.rooms.subservices;

import data_management.GameDataManager;
import messages.RoomsMessagesCreator;
import messages.fields_names.RoomsFields;
import org.json.JSONObject;
import services.IService;

/**
 * @author Micieli
 * @date 2015/05/24
 */
public class RoomsList implements IService {

    private RoomsMessagesCreator messagesCreator;

    public RoomsList() {
        super();
        messagesCreator = new RoomsMessagesCreator();
    }

    @Override
    public JSONObject start(JSONObject request) {
        return messagesCreator.generateRoomsListMessage(GameDataManager.getInstance().getRooms());
    }

    @Override
    public String getName() {
        return RoomsFields.ROOMS_LIST.toString();
    }

}
