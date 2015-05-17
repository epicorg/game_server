package registration_tests;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import server.ServerInitializer;
import services.Register;
import check_fields.FieldsNames;

/**
 * Test for client registration with an error: email already used.
 * 
 * @author Noris
 * @date 2015/04/21
 *
 */

class Test03 {

	public static void main(String[] args) throws JSONException {

		// Initialize the server
		new ServerInitializer().initDataManager();

		// Generate a random username
		String randomUsername = "Democrito" + new Random().nextInt(10000);

		// CLIENT: Registration
		JSONObject jsonRegFromClient = new JSONObject();
		jsonRegFromClient.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		jsonRegFromClient.put(FieldsNames.USERNAME, randomUsername);
		jsonRegFromClient.put(FieldsNames.PASSWORD, "abdera460");
		jsonRegFromClient.put(FieldsNames.EMAIL, randomUsername + "@atom.com");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Register the user
		Register register = new Register();
		register.setRequest(jsonRegFromClient);
		System.out.println("SERVER Registration Message: "
				+ register.start() + "\n");

		// CLIENT: Try to register with the same email
		JSONObject jsonRegFromClientCopy = new JSONObject();
		jsonRegFromClientCopy.put(FieldsNames.SERVICE, FieldsNames.REGISTER);
		jsonRegFromClientCopy.put(FieldsNames.USERNAME, randomUsername + "LOL");
		jsonRegFromClientCopy.put(FieldsNames.PASSWORD, "abdera460");
		jsonRegFromClientCopy.put(FieldsNames.EMAIL, randomUsername
				+ "@atom.com");
		System.out.println("CLIENT Registration Message: " + jsonRegFromClient);

		// SERVER: Try to register the user
		register.setRequest(jsonRegFromClientCopy);
		System.out.println("SERVER Registration Message: "
				+ register
				.start() + "\n");

	}

}
