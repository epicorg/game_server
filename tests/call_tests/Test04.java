package call_tests;

import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import services.Call;
import services.Service;
import check_fields.FieldsNames;

/**
 * Test for an invalid call request (caller not online).
 * 
 * @author Noris
 * @date 2015/04/12
 *
 */
public class Test04 {

	public static void main(String[] args) throws UnknownHostException,
			JSONException {

		// CLIENT: Send an invalid call request (caller is not online)
		JSONObject jsonCallFromClient = new JSONObject();
		jsonCallFromClient.put(FieldsNames.SERVICE, FieldsNames.CALL);
		jsonCallFromClient.put(FieldsNames.CALLER, "GWFHegel");
		jsonCallFromClient.put(FieldsNames.HASHCODE, 17240422);
		jsonCallFromClient.put(FieldsNames.PORT, 6666);
		jsonCallFromClient.put(FieldsNames.CALLEE, "JohnLocke");
		System.out.println("Call Client Message:  " + jsonCallFromClient);

		// SERVER: Read call request
		Service call = new Call(jsonCallFromClient);
		String stringCallFromServer = call.start().toString();
		System.out.println("Call Server Message:  " + stringCallFromServer);
	}

}