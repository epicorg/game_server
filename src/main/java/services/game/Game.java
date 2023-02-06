package services.game;

import messages.fields_names.ServicesFields;
import org.json.JSONException;
import org.json.JSONObject;
import services.IExtendedService;
import services.IService;
import services.game.subservices.*;

import java.util.HashMap;

/**
 * Game <code>Service</code> is the central one of all server. It provides actions while player are playing the game.
 *
 * @author Torlaschi
 * @author Micieli
 * @date 2015/04/18
 * @see GameExit
 * @see GameMap
 * @see GamePositions
 * @see GameStatus
 * @see GameReady
 */
public class Game implements IExtendedService {

    private HashMap<String, IService> subServices;

    public Game() {
        subServices = new HashMap<>();
    }

    @Override
    public JSONObject start(JSONObject request) {
        IService subService = null;
        try {
            String serviceType = request.getString(ServicesFields.SERVICE_TYPE.toString());
            subService = subServices.get(serviceType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subService == null ? null : subService.start(request);
    }

    @Override
    public String getName() {
        return ServicesFields.GAME.toString();
    }

    @Override
    public void addSubService(IService... subServices) {
        for (IService service : subServices) {
            this.subServices.put(service.getName(), service);
        }
    }

}
