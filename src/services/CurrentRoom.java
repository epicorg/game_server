package services;

import game.Player;
import game.Room;
import game.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class CurrentRoom implements Service {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private GameDataManager gameDataManager;

	public CurrentRoom(JSONObject json) {
		jsonRequest = json;
		jsonResponse = new JSONObject();

		gameDataManager = GameDataManager.getInstance();
	}

	@Override
	public String start() {
		try {
			runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonResponse.toString();
	}

	private void runService(String serviceType) throws JSONException {
		switch (serviceType) {
		case FieldsNames.ROOM_PLAYER_LIST:	
			generatePlayerListResponse();
			break;
		case FieldsNames.ROOM_ACTIONS:	
			generateActionsResponse();
			break;
		}
	}

	private void generatePlayerListResponse(){		
		try {
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			Room room = gameDataManager.getRoomByName(roomName);
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_PLAYER_LIST);

			jsonResponse.put(FieldsNames.ROOM_MAX_PLAYERS, Room.MAX_PLAYERS);

			JSONArray teams = new JSONArray();
			for(Team t : room.getTeamGenerator().getTeams()){
				JSONObject team = new JSONObject();
				JSONArray players = new JSONArray();

				for(Player p : t.getPlayers()){
					JSONObject jObject = new JSONObject();
					jObject.put(FieldsNames.NAME, p.getUsername());
					players.put(jObject);
				}

				team.put(FieldsNames.ROOM_TEAM_COLOR, t.getTeamColor().getRGB());
				team.put(FieldsNames.ROOM_NAME, t.getTeamName());
				team.put(FieldsNames.LIST, players);				
				teams.put(team);
			}
			jsonResponse.put(FieldsNames.ROOM_TEAM, teams);
		} catch (JSONException | NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateActionsResponse(){		
		try {
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_ACTIONS);	

			switch (jsonRequest.getString(FieldsNames.ROOM_ACTION)) {
			case FieldsNames.ROOM_EXIT:
				generateActionExitResponse();
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateActionExitResponse(){
		try {
			jsonResponse.put(FieldsNames.ROOM_ACTION, FieldsNames.ROOM_EXIT);		

			try{
				String playerName = jsonRequest.getString(FieldsNames.USERNAME);
				String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
				Room room = gameDataManager.getRoomByName(roomName);

				room.removePlayer(room.getPlayerByName(playerName));
				jsonResponse.put(FieldsNames.NO_ERRORS, true);
			} catch (NoSuchRoomException | NoSuchPlayerException e) {
				e.printStackTrace();
				jsonResponse.put(FieldsNames.NO_ERRORS, false);
			}
		} catch (JSONException e){
			e.printStackTrace();
		}
	}

}
