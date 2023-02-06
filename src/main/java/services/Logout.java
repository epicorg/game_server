package services;

import exceptions.UserNotOnlineException;
import messages.fields_names.CommonFields;
import messages.fields_names.ServicesFields;
import online_management.OnlineManager;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Elaborates an explicit logout requests setting the applicant offline.
 *
 * @author Micieli
 * @date 2015/05/17
 */
public class Logout implements IService {

    @Override
    public JSONObject start(JSONObject request) {
        OnlineManager onlineManager = OnlineManager.getInstance();
        try {
            String username = request.getString(CommonFields.USERNAME.toString());
            int hashCode = request.getInt(CommonFields.HASHCODE.toString());
            onlineManager.setOffline(username, hashCode);
        } catch (JSONException | UserNotOnlineException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() {
        return ServicesFields.LOGOUT.toString();
    }

}
