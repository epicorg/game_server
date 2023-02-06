package game.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Every room has N teams, which play one against the others. This class tracks the {@link Player}s of a single team.
 *
 * @author Noris
 * @date 2015/04/18
 * @see Player
 */
public class Team {

    public static final int MAX_PLAYERS = 1;

    private String teamName;
    private Color teamColor;
    private int numberOfPlayersPerTeam;

    private ArrayList<Player> players = new ArrayList<>();

    public Team(int numberOfPlayersPerTeam) {
        super();
        setRandomTeamColor();
        this.numberOfPlayersPerTeam = numberOfPlayersPerTeam;
    }

    public Team() {
        super();
        setRandomTeamColorFromList();
        this.numberOfPlayersPerTeam = MAX_PLAYERS;
    }

    public Team(String teamName) {
        super();
        this.teamName = teamName;
        setRandomTeamColorFromList();
        this.numberOfPlayersPerTeam = MAX_PLAYERS;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Add a player to the team.
     */
    public void addPlayer(Player player) {
        // throws FullTeamException {
        // if (isFull()) {
        // throw new FullTeamException();
        // }
        players.add(player);
    }

    /**
     * Remove a player from the team, and set to null the player's team.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * @return the numbers of the users currently playing in the team.
     */
    public int getSize() {
        return players.size();
    }

    /**
     * @return an array list containing all the players of the team.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return true if the team is full (no more players can join the team), false otherwise (there are free slots).
     */
    public boolean isFull() {
        return getSize() >= numberOfPlayersPerTeam;
    }

    public void setTeamColor(Color teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * Generate a random team color. If used twice it can generate two different colors but very, very similar.
     */
    public void setRandomTeamColor() {
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        teamColor = new Color(r, g, b);
    }

    /**
     * Select a random color from a predefined colors list.
     */
    public void setRandomTeamColorFromList() {
        ArrayList<Color> admittedColors = new ArrayList<>();
        admittedColors.add(Color.BLACK);
        admittedColors.add(Color.BLUE);
        admittedColors.add(Color.GRAY);
        admittedColors.add(Color.MAGENTA);
        admittedColors.add(Color.ORANGE);
        admittedColors.add(Color.PINK);
        admittedColors.add(Color.RED);
        admittedColors.add(Color.WHITE);
        admittedColors.add(Color.YELLOW);
        int randomNumber = new Random().nextInt(admittedColors.size());
        this.teamColor = admittedColors.get(randomNumber);
    }

    public Color getTeamColor() {
        return teamColor;
    }

    /**
     * Empties the team removing all players.
     */
    public void empty() {
        players.removeAll(players);
    }

}
