package messages;

import fields_names.FieldsNames;
import game.model.Player;
import game.model.Room;
import game.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.current_room.CurrentRoom;

/**
 * Generates messages related to the {@link CurrentRoom} service.
 * 
 * @author Micieli
 * @date 2015/05/20
 * @see CurrentRoom
 */

public class CurrentRoomMessagesCreator {

	/**
	 * Generates a players list, informing the client about the players who are
	 * in the {@link Room}.
	 * 
	 * @param room
	 *            the room from which take the players list
	 * 
	 * @return the message to send
	 */
	public JSONObject generatePlayersListMessage(Room room) {
		JSONObject response = new JSONObject();

		try {

			response.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_PLAYER_LIST);
			response.put(FieldsNames.ROOM_MAX_PLAYERS, room.getMaxPlayers());

			JSONArray teams = formatTeams(room);
			response.put(FieldsNames.ROOM_TEAM, teams);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Generates the response after an exit request from a {@link Player}.
	 * 
	 * @param result
	 *            true if the exit succeeded, false otherwise
	 * 
	 * @return the response to send
	 */
	public JSONObject generateExitResponse(boolean result) {
		JSONObject response = new JSONObject();

		try {

			response.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_ACTIONS);
			response.put(FieldsNames.ROOM_ACTION, FieldsNames.ROOM_EXIT);
			response.put(FieldsNames.NO_ERRORS, result);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	private JSONArray formatTeams(Room room) throws JSONException {
		JSONArray teams = new JSONArray();

		for (Team t : room.getTeamGenerator().getTeams()) {
			JSONObject team = new JSONObject();
			JSONArray players = formatPlayers(t);
			team.put(FieldsNames.ROOM_TEAM_COLOR, t.getTeamColor().getRGB());
			team.put(FieldsNames.ROOM_NAME, t.getTeamName());
			team.put(FieldsNames.LIST, players);
			teams.put(team);
		}
		return teams;
	}

	private JSONArray formatPlayers(Team t) throws JSONException {
		JSONArray players = new JSONArray();
		for (Player p : t.getPlayers()) {
			JSONObject jObject = new JSONObject();
			jObject.put(FieldsNames.USERNAME, p.getUsername());
			players.put(jObject);
		}
		return players;
	}
}
