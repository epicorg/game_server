package game;

import game.map.Dimension;

import java.util.ArrayList;

/**
 * @author Micieli
 * @date 2015/05/05
 */

public class WinCheckerTest implements IWinChecher {

	private float xWin = 0;
	private float yWin = -1;
	private float zWin = 17;
	private float ray = 3;
	
	public WinCheckerTest() {

	}

	public WinCheckerTest(float xWin, float yWin, float zWin, float ray) {
		super();
		this.xWin = xWin;
		this.yWin = yWin;
		this.zWin = zWin;
		this.ray = ray;
	}

	public WinCheckerTest(Dimension position, float ray) {
		super();
		this.xWin = (float) position.getWidth();
		this.yWin = (float) position.getHeight();
		this.zWin = (float) position.getLength();
		this.ray = ray;

	}

	@Override
	public boolean checkWin(Team team) {

		ArrayList<Player> players = team.getPlayers();
		for (Player player : players) {
			if (!isInPosition(player))
				return false;
		}

		System.out.println("Winner: " + team.getTeamName());
		return true;

	}

	private boolean isInPosition(Player player) {
		PlayerStatus status = player.getPlayerStatus();
		float x = status.getxPosition();
		float y = status.getyPosition();
		float z = status.getzPosition();

		if ((x - xWin) * (x - xWin) + (z - zWin) * (z - zWin) <= ray * ray) {
			System.out.println(player.getUsername() + " in position.");
			return true;
		}

		return false;
	}

}
