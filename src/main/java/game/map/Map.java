package game.map;

import game.model.PlayerStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * A map that is constructed starting from single {@link MapObject} (one, more, or none), the size of the map,
 * spawn point(s) and win point(s).
 *
 * @author Noris
 * @date 2015/04/23
 */
public class Map implements IMap {

    private MapDimension mapSize;

    private ArrayList<MapObject> items;
    private int numItems = 0;

    private LinkedList<PlayerStatus> spawnPoints;

    private ArrayList<MapDimension> winPoints;

    public Map() {
        items = new ArrayList<>();
        spawnPoints = new LinkedList<>();
        winPoints = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#setMapSize(game.map.MapDimension)
     */
    @Override
    public void setMapSize(MapDimension mapSize) {
        this.mapSize = mapSize;
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#getMapSize()
     */
    @Override
    public MapDimension getMapSize() {
        return mapSize;
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#addMapObject(game.map.MapObject)
     */
    @Override
    public void addMapObject(MapObject item) {
        items.add(item);
        numItems++;
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#getItems()
     */
    @Override
    public ArrayList<MapObject> getItems() {
        return items;
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#getNumItems()
     */
    @Override
    public int getNumItems() {
        return numItems;
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#addMapObjects(game.map.MapObject)
     */
    @Override
    public void addMapObjects(MapObject... items) {
        Collections.addAll(this.items, items);
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#addSpawnPoint(game.model.PlayerStatus)
     */
    @Override
    public void addSpawnPoint(PlayerStatus playerStatus) {
        spawnPoints.add(playerStatus);
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#getSpawnPoints()
     */
    @Override
    public LinkedList<PlayerStatus> getSpawnPoints() {
        if (spawnPoints.isEmpty())
            spawnPoints.add(new PlayerStatus());
        return spawnPoints;
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#addWinPoint(game.map.MapObject)
     */
    @Override
    public void addWinPoint(MapObject mapObject) {
        winPoints.add(mapObject.getPosition());
        addMapObject(mapObject);
    }

    /*
     * (non-Javadoc)
     *
     * @see game.map.IMap#getWinPoints()
     */
    @Override
    public ArrayList<MapDimension> getWinPoints() {
        // TODO DEBUG: win points
        System.out.println("Win Points: " + winPoints);
        return winPoints;
    }

    public PlayerStatus getSpawnPoint() {
        // TODO DEBUG: spawn points
        System.out.println("Spawn Points: " + spawnPoints);

        if (spawnPoints.size() == 1)
            return spawnPoints.getFirst();
        else if (spawnPoints.isEmpty())
            return new PlayerStatus();
        return spawnPoints.remove();
    }

}
