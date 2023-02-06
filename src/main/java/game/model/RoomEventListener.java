package game.model;

import services.current_room.CurrentRoom;
import services.game.Game;
import services.rooms.Rooms;

/**
 * Defines a generic interface for an implementation of a components that react to Room Event.
 *
 * @author Micieli
 * @date 2015/04/25
 * @see Room
 */
public interface RoomEventListener {

    /**
     * Reacts to an entrance of a new player in a <code>Room</code>.
     *
     * @param player the player just entered
     * @see Rooms
     */
    void onNewPlayerAdded(Player player);

    /**
     * Reacts to the room full event, starting the game.
     *
     * @see Game
     */
    void onRoomFull();

    /**
     * Reacts to the exit of a player from a Room.
     *
     * @param player the player removed
     * @see CurrentRoom
     */
    void onPlayerRemoved(Player player);

    /**
     * Reacts to a forced game stops.
     *
     * @see Game
     */
    void onGameExit();

    /**
     * Reacts to the end of the game preparing the room for another round.
     */
    void onGameEnded();

}
