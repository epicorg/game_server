package services;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import online_management.OnlineManager;
import online_management.OnlineUser;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.DataManager;
import data_management.RegisteredUser;

/**
 * 
 * <code>Login</code> <code>Service</code> allow the client to access to others <code>Services</code>.
 * The {@link OnlineManager} provides function to manages {@link OnlineUser}.
 * Before Logging in a user must be Registered.
 * The client have to provides his credentials (Username and Password) to be identified
 * 
 * @author Noris
 * @date 2015/03/26
 * @see Register
 * @see RegisteredUser
 * @see OnlineManager
 * @see OnlineUser
 */

public class Login implements IService {

	private JSONObject jsonRequest;

	private InetAddress ipAddress;
	private int hashCode;
	private PrintWriter printWriter;
	private RegisteredUser user;	

	public Login(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	@Override
	public JSONObject start(JSONObject request) {
		this.jsonRequest = request;	
		
		readFields();
		
		boolean noErrors = checkFields();
		if(noErrors)
			setUserOnline();
		
		return generateResponse(noErrors);

	}

	private void readFields(){
		
		try {

			String username = jsonRequest.getString(FieldsNames.USERNAME);
			String password = jsonRequest.getString(FieldsNames.PASSWORD);
			user = new RegisteredUser(username, password, null);
			ipAddress = InetAddress.getByName(jsonRequest
					.getString(FieldsNames.IP_ADDRESS));

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkFields() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		if(onlineManager.checkIfOnline(user.getUsername())){
			return false;
		}
		return DataManager.getInstance().checkPassword(user);
	}

	private void setUserOnline() {
		OnlineManager onlineManager = OnlineManager.getInstance();
		hashCode = onlineManager.setOnline(user.getUsername(), ipAddress, printWriter);
	}

	private JSONObject generateResponse(boolean noErrors) {
		JSONObject response = new JSONObject();
		try {
			response.put(FieldsNames.SERVICE, FieldsNames.LOGIN);
			response.put(FieldsNames.USERNAME, user.getUsername());
			response.put(FieldsNames.HASHCODE, hashCode);
			response.put(FieldsNames.NO_ERRORS, noErrors);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
}
