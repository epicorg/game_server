package server;

import services.*;
import services.current_room.CurrentRoom;
import services.current_room.subservices.ListReceived;
import services.current_room.subservices.PlayerList;
import services.current_room.subservices.RoomActions;
import services.current_room.subservices.RoomExit;
import services.encrypt.Encrypt;
import services.encrypt.subservices.PublicKeyRequest;
import services.encrypt.subservices.WrappedKey;
import services.game.Game;
import services.game.subservices.*;
import services.rooms.Rooms;
import services.rooms.subservices.CreateRoom;
import services.rooms.subservices.JoinPlayer;
import services.rooms.subservices.RoomsList;

import java.util.ArrayList;

/**
 * Initialize Service creating the services tree, adding sub-services into a single service.
 *
 * @author Micieli
 * @date 2015/05/24
 */
public class ServicesInitializer {

    private ArrayList<IService> services;

    public ServicesInitializer() {
        services = new ArrayList<>();
        init();
    }

    private void init() {
        IExtendedService encrypt = new Encrypt();
        encrypt.addSubService(new PublicKeyRequest());
        encrypt.addSubService(new WrappedKey());
        services.add(encrypt);

        services.add(new Register());
        IExtendedService rooms = initRooms();
        services.add(rooms);
        IExtendedService currentRoom = initCurrentRoom();
        services.add(currentRoom);
        IExtendedService game = initGame();

        services.add(game);
        services.add(new Audio());
        services.add(new Unknown());
        services.add(new Polling());
        services.add(new Logout());
    }

    private IExtendedService initGame() {
        IExtendedService game = new Game();
        IExtendedService status = new GameStatus();
        status.addSubService(new GameReady());
        status.addSubService(new GameExit());
        game.addSubService(new GamePositions(), new GameMap(), status);
        return game;
    }

    private IExtendedService initCurrentRoom() {
        IExtendedService currentRoom = new CurrentRoom();
        IService playerList = new PlayerList();
        IExtendedService roomsActions = new RoomActions();
        IService roomExit = new RoomExit();
        IService playerListReceived = new ListReceived();
        roomsActions.addSubService(roomExit, playerListReceived);
        currentRoom.addSubService(roomsActions, playerList);
        return currentRoom;
    }

    private IExtendedService initRooms() {
        IExtendedService rooms = new Rooms();
        rooms.addSubService(new JoinPlayer());
        rooms.addSubService(new CreateRoom());
        rooms.addSubService(new RoomsList());
        return rooms;
    }

    public ArrayList<IService> getServices() {
        return services;
    }

}
