package game.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Noris
 * @author Torlaschi
 * @date 2015/04/20
 */
public class TeamManager {

    public static final int NUMBER_OF_TEAMS = 1;

    private ArrayList<Team> teams = new ArrayList<>();

    private int numberOfTeams;

    public TeamManager() {
        generateTeams(Team.MAX_PLAYERS);
        this.numberOfTeams = NUMBER_OF_TEAMS;
    }

    public TeamManager(int numberOfTeams, int numberOfPlayersPerTeam) {
        this.numberOfTeams = numberOfTeams;
        generateTeams(numberOfPlayersPerTeam);
    }

    /**
     * Generates teams with different colors.
     */
    private void generateTeams(int numberOfPlayersPerTeam) {
        for (int i = 0; i < numberOfTeams; i++) {
            Team team = new Team(numberOfPlayersPerTeam);
            team.setTeamName("Team " + (teams.size() + 1));
            team.setRandomTeamColor();
            teams.add(team);
        }
    }

    /**
     * Empties all teams removing all players.
     */
    public void emptyTeams() {
        for (Team team : teams) {
            team.empty();
        }
    }

    /**
     * Selects a team for the incoming player.
     *
     * @return the team with fewer players
     */
    public Team getRandomTeam() {
        Team t = teams.get(0);
        int min = teams.get(0).getPlayers().size();

        for (int i = 1; i < teams.size(); i++) {
            Team temp = teams.get(i);
            if (temp.getPlayers().size() < min) {
                t = temp;
                min = t.getPlayers().size();
            } else if (temp.getPlayers().size() == min) {
                if (new Random().nextInt(2) == 0) {
                    t = temp;
                    min = t.getPlayers().size();
                }
            }
        }

        return t;
    }

    public void removePlayer(Player player) {
        for (Team team : teams)
            if (team.getPlayers().contains(player))
                team.removePlayer(player);
    }

    /**
     * @return an ArrayList containing the teams
     */
    public ArrayList<Team> getTeams() {
        return teams;
    }

}
