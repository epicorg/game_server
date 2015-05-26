package services;

import online_management.OnlineManager;
import online_management.OnlineUser;
import online_management.PollingThread;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.UserNotOnlineException;
import fields_name.FieldsNames;

/**
 * 
 * Receives Pooling response from the client.
 * Doing so the server can check if the client is still achievable.
 * 
 * @author Micieli
 * @date 2015/05/11
 * @see PollingThread
 */

public class Polling implements IService {

	@Override
	public JSONObject start(JSONObject request) {	
		
		System.out.println(request.toString());
		
		OnlineManager onlineManager = OnlineManager.getInstance();
		
		try {
			String username = request.getString(FieldsNames.USERNAME);
			OnlineUser user = onlineManager.getOnlineUserByUsername(username);
			user.setPolled(true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public String getName() {
		return FieldsNames.POLLING;
	}
}
