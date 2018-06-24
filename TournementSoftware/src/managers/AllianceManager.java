package managers;

import java.util.ArrayList;

public class AllianceManager {
	private static AllianceManager instance;

	private ArrayList<Team> availableTeams;
	private ArrayList<Team> allianceTeams;

	public static AllianceManager getInstance() {
		if (instance == null) {
			instance = new AllianceManager();
		}
		return instance;
	}

	private AllianceManager() {
		availableTeams = new ArrayList<Team>();
		allianceTeams = new ArrayList<Team>();
	}

	public void startSelection() {
		availableTeams = new ArrayList<Team>(TeamManager.getInstance().getTeams());
		Tournement.getMainDisplay().refreshAvailableList(availableTeams);
	}

	public void selectNextTeam(int teamNumber) {
		allianceTeams.add(TeamManager.getInstance().getTeam(teamNumber));
		Tournement.getMainDisplay().setAlliance(teamNumber,
				allianceTeams.indexOf(TeamManager.getInstance().getTeam(teamNumber)));
		availableTeams.remove(TeamManager.getInstance().getTeam(teamNumber));
		Tournement.getMainDisplay().refreshAvailableList(availableTeams);
		System.out.println(String.format("Team #%2d selected", teamNumber));
		if (allianceTeams.size() == 8) {
			System.out.println("Alliances Formed");
			EliminationsManager.getInstance().setTeams(allianceTeams);
			MatchManager.getInstance().setNextRound();
			Tournement.getMainDisplay().setAllianceButtonsEnabled(false);
			Tournement.getMainDisplay().setControlPanelEnabled("Next", true);
			EliminationsManager.getInstance().generateMatches();
		}
	}

	public void removeTeam(int teamNumber) {
		availableTeams.remove(TeamManager.getInstance().getTeam(teamNumber));
		Tournement.getMainDisplay().refreshAvailableList(availableTeams);
		System.out.println(String.format("Team #%2d removed", teamNumber));
	}
}
