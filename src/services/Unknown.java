package services;

import org.json.JSONException;
import org.json.JSONObject;

public class Unknown implements Service {

	@Override
	public String start() {
		return getResponse().toString();
	}
	
	private JSONObject getResponse() {
		
		JSONObject jsonResponse = new JSONObject();
		
		try {
			
			jsonResponse.put("service", "UNKNOWN");
			return jsonResponse;
			
		} catch (JSONException e) {
			//TODO
			return new JSONObject();
		}

	}

}
