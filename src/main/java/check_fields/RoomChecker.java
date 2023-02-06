package check_fields;

/**
 * Checking of the room's creation client request fields.
 *
 * @author Noris
 * @date 2015/04/19
 * @see services.rooms.Rooms
 */
public class RoomChecker {

    private static final int MAX_TEAMS = 5;
    private static final int MAX_PLAYERS_PER_TEAM = 5;

    /**
     * Checks if the room name is valid.
     *
     * @param roomName the name of the room
     * @return true if the room's name is valid, false otherwise
     */
    public boolean isRoomNameValid(String roomName) {
        boolean fieldIsOk = roomName.length() >= FieldsValues.ROOMNAME_MIN_LENGTH;
        if (roomName.length() > FieldsValues.ROOMNAME_MAX_LENGTH)
            fieldIsOk = false;
        return fieldIsOk;
    }

    /**
     * Checks if the given number of team is a valid one.
     *
     * @param numbersOfTeams the number to check
     * @return true if it is allowed, false otherwise
     */
    public boolean isNumberOfTeamsValid(int numbersOfTeams) {
        return numbersOfTeams <= MAX_TEAMS && numbersOfTeams >= 1;
    }

    /**
     * Checks if the given number of player per team is a valid one.
     *
     * @param numberOfPlayersPerTeam the number to check
     * @return true if it is allowed, false otherwise
     */
    public boolean isNumberOfPlayersValid(int numberOfPlayersPerTeam) {
        return numberOfPlayersPerTeam <= MAX_PLAYERS_PER_TEAM && numberOfPlayersPerTeam >= 1;
    }

}
