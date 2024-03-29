package services.current_room;

import game.RoomPlayersUpdater;
import game.model.Player;
import game.model.PlayerEventListener;
import game.model.Room;
import messages.fields_names.ServicesFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IExtendedService;
import services.IService;

import java.util.HashMap;

/**
 * CurrentRoom service provides information about the {@link Room} joined by the client. It gives the complete
 * {@link Player}s list currently in the <code>Room</code>, updated in real time according to Player getting in or out
 * from the <code>Room</code>. It also elaborates the client request to exit from a specific <code>Room</code>.
 *
 * @author Torlaschi
 * @author Micieli
 * @date 2015/04/18
 * @see PlayerEventListener
 * @see RoomPlayersUpdater
 */
public class CurrentRoom implements IExtendedService {

    private HashMap<String, IService> subServices;

    public CurrentRoom() {
        subServices = new HashMap<>();
    }

    @Override
    public JSONObject start(JSONObject request) {
        String serviceType = null;
        try {
            serviceType = request.getString(ServicesFields.SERVICE_TYPE.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IService subService = subServices.get(serviceType);
        return subService == null ? null : subService.start(request);
    }

    @Override
    public void addSubService(IService... subServices) {
        for (IService subService : subServices)
            this.subServices.put(subService.getName(), subService);
    }

    @Override
    public String getName() {
        return ServicesFields.CURRENT_ROOM.toString();
    }

}
