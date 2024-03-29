package game.model;

import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import exceptions.UserNotOnlineException;
import messages.GameEndMessagesCreator;
import online_management.OnlineManager;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Thread that periodically checks if a team win the game; if there are any update all the player in the room end ends the game.
 *
 * @author Micieli
 * @date 2015/05/12
 */
public class WinCheckerThread extends Thread {

    private static final int CHECKING_PERIOD = 200;
    private IWinChecker winChecker;
    private Timer timer;
    private Room room;
    private GameEndMessagesCreator messagesCreator;

    /**
     * @param room       the room to be checked
     * @param winChecker an implementation of {@link IWinChecker}
     */
    public WinCheckerThread(Room room, IWinChecker winChecker) {
        super();
        this.room = room;
        this.winChecker = winChecker;
        timer = new Timer();
        messagesCreator = new GameEndMessagesCreator();
    }

    @Override
    public void run() {
        timer.scheduleAtFixedRate(new WinTask(), 0, CHECKING_PERIOD);
    }

    public void shutdown() {
        timer.cancel();
    }

    /**
     * Periodical checking task.
     */
    public class WinTask extends TimerTask {

        @Override
        public void run() {
            ArrayList<Team> winner = new ArrayList<>();

            for (Team team : room.getTeamManager().getTeams()) {
                if (winChecker.checkWin(team))
                    winner.add(team);
            }

            if (winner.isEmpty())
                return;

            endGame(winner);

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                GameDataManager gameDataManager = GameDataManager.getInstance();
                gameDataManager.stopCallForRoom(room.getName());
            } catch (NoSuchRoomException e) {
                e.printStackTrace();
            }

        }
    }

    private void endGame(ArrayList<Team> winner) {

        timer.cancel();

        ArrayList<Team> losers = new ArrayList<>(room.getTeamManager().getTeams());
        losers.removeAll(winner);

        try {

            if (winner.size() > 1) {
                for (Team team : winner) {
                    updatePlayers(team.getPlayers(), messagesCreator.generateDrawMessage());
                }
            } else {
                updatePlayers(winner.get(0).getPlayers(), messagesCreator.generateWinMessage());
            }
            for (Team team : losers) {
                updatePlayers(team.getPlayers(), messagesCreator.generateLoseMessage());
            }

        } catch (UserNotOnlineException e) {
            e.printStackTrace();
        }

        room.setInPlay(false);
    }

    private void updatePlayers(ArrayList<Player> players, JSONObject message) throws UserNotOnlineException {
        String strMessage = message.toString();
        for (Player player : players) {
            PrintWriter writer = OnlineManager.getInstance()
                    .getOnlineUserByUsername(player.getUsername()).getOutStream();
            writer.println(strMessage);
        }
    }

}
