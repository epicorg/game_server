package exceptions;

import game.model.Room;

/**
 * Thrown while removing a <code>Room</code> that isn't empty.
 *
 * @author Micieli
 * @see Room#cancel()
 */
public class RoomNotEmptyException extends Exception {
}
