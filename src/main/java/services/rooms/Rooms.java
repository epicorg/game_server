package services.rooms;

import game.model.Room;
import messages.fields_names.ServicesFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IExtendedService;
import services.IService;

import java.util.HashMap;

/**
 * <code>RoomService</code> manages {@link Room} actions. Allows {@link Room} creation with a specific name request from
 * the client. It provides current available {@link Room}s. It enables the client to join a specific existing {@link Room}.
 *
 * @author Micieli
 * @author Noris
 * @author Torlaschi
 * @date 2015/04/18
 */
public class Rooms implements IExtendedService {

    private HashMap<String, IService> subServices;

    public Rooms() {
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
    public String getName() {
        return ServicesFields.ROOMS.toString();
    }

    @Override
    public void addSubService(IService... subServices) {
        for (IService service : subServices)
            this.subServices.put(service.getName(), service);
    }

}
