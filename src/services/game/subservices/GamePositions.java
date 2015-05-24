package services.game.subservices;

import messages.GameMessagesCreator;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Player;
import game.model.Room;
import services.IService;

public class GamePositions implements IService {
	
	private GameMessagesCreator messagesCreator;

	public GamePositions() {
		super();
		messagesCreator = new GameMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		try {

			String roomName = request.getString(FieldsNames.ROOM_NAME);
			Room room = GameDataManager.getInstance().getRoomByName(roomName);
			String username = request.getString(FieldsNames.USERNAME);

			UpdatePlayerStatus(username, request, room);
			return messagesCreator.generatePositionMessage(username, room);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void UpdatePlayerStatus(String username, JSONObject request, Room room) throws JSONException,
			NoSuchPlayerException {
		JSONObject posObject = request
				.getJSONObject(FieldsNames.GAME_POSITION);
		JSONObject dirObject = request
				.getJSONObject(FieldsNames.GAME_DIRECTION);
		float xPos = (float) posObject.getDouble(FieldsNames.GAME_X);
		float yPos = (float) posObject.getDouble(FieldsNames.GAME_Y);
		float zPos = (float) posObject.getDouble(FieldsNames.GAME_Z);
		float xDir = (float) dirObject.getDouble(FieldsNames.GAME_X);
		float yDir = (float) dirObject.getDouble(FieldsNames.GAME_Y);
		float zDir = (float) dirObject.getDouble(FieldsNames.GAME_Z);

		Player player = room.getPlayerByName(username);
		player.getPlayerStatus().setPosition(xPos, yPos, zPos);
		player.getPlayerStatus().setDirection(xDir, yDir, zDir);
	}

	@Override
	public String getName() {
		return FieldsNames.GAME_POSITIONS;
	}
}