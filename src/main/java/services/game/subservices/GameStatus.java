package services.game.subservices;

import game.model.Player;
import game.model.PlayerStatus;
import messages.fields_names.GameFields;
import org.json.JSONObject;
import services.IExtendedService;
import services.IService;
import services.game.Game;

import java.util.HashMap;

/**
 * A {@link Game} sub-service. Allows the {@link Player} to update his
 * {@link PlayerStatus} during the game.
 *
 * @author Micieli
 * @date 2015/05/24
 */
public class GameStatus implements IExtendedService {

    private HashMap<String, IService> subServices;

    public GameStatus() {
        super();
        subServices = new HashMap<>();
    }

    @Override
    public JSONObject start(JSONObject request) {
        if (request.has(GameFields.GAME_READY.toString())) {
            return subServices.get(GameFields.GAME_READY.toString()).start(request);
        } else if (request.has(GameFields.GAME_EXIT.toString())) {
            return subServices.get(GameFields.GAME_EXIT.toString()).start(request);
        }
        return null;
    }

    @Override
    public String getName() {
        return GameFields.GAME_STATUS.toString();
    }

    @Override
    public void addSubService(IService... subServices) {
        for (IService service : subServices) {
            this.subServices.put(service.getName(), service);
        }
    }

}
