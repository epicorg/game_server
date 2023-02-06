package exceptions;

import game.model.RoomRemoverThread;

/**
 * Thrown when trying to do something with a {@link game.model.Room} that has been removed.
 *
 * @author Micieli
 * @see RoomRemoverThread
 */
public class RoomCancelledException extends Exception {
}
