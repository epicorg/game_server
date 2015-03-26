package services;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author      Noris
 * @since       2015-03-26
 */

public class Register implements Service {
	
	private JSONObject json;
	
	private String username;
	private String password;
	private String email;
	
	private boolean value;
	private String error;
	
	public Register(JSONObject json) {
		super();
		this.json = json;
	}
	
	@Override
	public String start() {
		readFields();
		saveFields();
		return getResponse().toString();
	}
	
	private void readFields() {
		try {
			
			username = json.getString("username");
			password = json.getString("password");
			password = json.getString("email");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveFields(){
		//TODO Save fields into database
		value = true;
	}
	
	private JSONObject getResponse() {
		
		JSONObject jsonResponse = new JSONObject();
		
		try {
			
			jsonResponse.put("service", "REGISTER");

			if (value) {
				jsonResponse.put("value", true);
				return jsonResponse;
			}
			
			jsonResponse.put("value", false);
			jsonResponse.put("error", error);
			return jsonResponse;
			
		} catch (JSONException e) {
			//TODO
			return new JSONObject();
		}
	}
}
