package managers;

import java.awt.event.ActionEvent;

import displays.MainDisplay;
import utils.Utils;

public class Tournement {
	private static ScoringManager scoringManager;
	private static TeamManager teamManager;
	private static MatchManager matchManager;

	private static WebSocketServer webSocketServer;

	private static MainDisplay mainDisplay;

	public static void main(String[] args) {
		scoringManager = ScoringManager.getInstance();
		teamManager = TeamManager.getInstance();
		matchManager = MatchManager.getInstance();

		webSocketServer = new WebSocketServer(new byte[] { 127, 0, 0, 1 }, 2046);

		mainDisplay = new MainDisplay();

		System.out.println("Where are teams?");
		teamManager.loadTeamsFromFile(Utils.selectFile("csv", null, "Teams.csv"));

		System.out.println("Where is the match schedule?");
		matchManager.loadMatchesFromFile(Utils.selectFile("csv", null, "MatchSchedule.csv"));

		System.out.println("Where to save match results?");
		scoringManager.setSaveFile(Utils.selectFile("csv", null, "MatchResults.csv"));

		mainDisplay
				.addControlPanelEvent(new ActionEvent(matchManager, ActionEvent.ACTION_PERFORMED, "Load Next Match"));

		webSocketServer.startServer();
		webSocketServer.reformatWebpageRankings();
	}

	public static MainDisplay getMainDisplay() {
		return mainDisplay;
	}

	public static void shutdown() {
		webSocketServer.stopServer();
		System.exit(0);
	}

	public static WebSocketServer getWebServer() {
		return webSocketServer;
	}
}
