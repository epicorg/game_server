package server;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import services.Audio;
import services.CurrentRoom;
import services.Encrypt;
import services.Game;
import services.IService;
import services.Logout;
import services.Polling;
import services.Register;
import services.RoomService;
import services.Unknown;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @author Micieli
 * @date 2015/03/26
 */

public class ServiceChooser {

	private HashMap<String, IService> services = new HashMap<>();

	public ServiceChooser() {
		initMap();
	}

	private void initMap() {
		addService(FieldsNames.ENCRYPT, new Encrypt());
		addService(FieldsNames.REGISTER, new Register());
		addService(FieldsNames.ROOMS, new RoomService());
		addService(FieldsNames.CURRENT_ROOM, new CurrentRoom());
		addService(FieldsNames.GAME, new Game());
		addService(FieldsNames.AUDIO, new Audio());
		addService(FieldsNames.UNKNOWN, new Unknown());
		addService(FieldsNames.POLLING, new Polling());
		addService(FieldsNames.LOGOUT, new Logout());

	}

	public IService chooseService(JSONObject json) {

		String serviceName;
		try {
			serviceName = json.getString(FieldsNames.SERVICE);
		} catch (JSONException e) {
			e.printStackTrace();
			serviceName = null;
		}

		IService service = services.get(serviceName);
		if (service == null)
			service = services.get(FieldsNames.UNKNOWN);

		return service;
	}

	public void addService(String name, IService service) {
		services.put(name, service);
	}
}