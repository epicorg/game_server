package game.map;

import game.model.PlayerStatus;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Defines the typical map behavior.
 *
 * @author Noris
 * @author Micieli
 */
public interface IMap {

    void setMapSize(MapDimension mapSize);

    MapDimension getMapSize();

    void addMapObject(MapObject item);

    ArrayList<MapObject> getItems();

    int getNumItems();

    /**
     * Add multiple {@link MapObject} to the list of the objects.
     *
     * @param items an array of {@link MapObject}
     */
    void addMapObjects(MapObject... items);

    void addSpawnPoint(PlayerStatus playerStatus);

    /**
     * It gets all the spawn points. If there aren't spawn points it
     * returns a single default spawn point (set in {@link PlayerStatus}).
     *
     * @return the spawn points
     */
    LinkedList<PlayerStatus> getSpawnPoints();

    /**
     * It adds a spawn point to the spawn points list. It also adds the win point object to the objects list.
     *
     * @param mapObject the {@link MapObject} who represents a win point.
     */
    void addWinPoint(MapObject mapObject);

    ArrayList<MapDimension> getWinPoints();

    /**
     * When a player ask for a spawnPoint call this method and the map gives him one.
     *
     * @return a spawnPoint int the map
     */
    PlayerStatus getSpawnPoint();

}