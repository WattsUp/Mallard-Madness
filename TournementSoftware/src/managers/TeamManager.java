package managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TeamManager {
	private static TeamManager instance;

	private ArrayList<Team> teams = new ArrayList<Team>();

	public static TeamManager getInstance() {
		if (instance == null) {
			instance = new TeamManager();
		}
		return instance;
	}

	private TeamManager() {

	}

	private void addTeam(Team team) {
		teams.add(team);
	}

	public Team getTeam(int teamNumber) {
		for (Team team : teams) {
			if (team.getNumber() == teamNumber) {
				return team;
			}
		}
		return null;
	}

	public void loadTeamsFromFile(File file) {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				if (line.contains("Team ")) {
					// File header
				} else {
					String[] info = line.split(",");
					int teamNumber = Integer.parseInt(info[0]);
					addTeam(new Team(teamNumber, info[1]));
					System.out.println(String.format("Added Team #%2d with name:%s", teamNumber, info[1]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(String.format("There are %d teams\n", teams.size()));
	}

	public void recalculateRank() {
		Collections.shuffle(teams);
		Collections.sort(teams, new Comparator<Team>() {
			@Override
			public int compare(Team team2, Team team1) { // Opposite for
															// ascending
				if (team1.getAverageScore() > team2.getAverageScore()) {
					return 1;
				} else if (team1.getAverageScore() < team2.getAverageScore()) {
					return -1;
				} else if (team1.getAverageMallard() > team2.getAverageMallard()) {
					return 1;
				} else if (team1.getAverageMallard() < team2.getAverageMallard()) {
					return -1;
				} else if (team1.getAverageDuckling() > team2.getAverageDuckling()) {
					return 1;
				} else if (team1.getAverageDuckling() < team2.getAverageDuckling()) {
					return -1;
				} else if (team1.getAverageFouls() > team2.getAverageFouls()) {
					return 1;
				} else if (team1.getAverageFouls() < team2.getAverageFouls()) {
					return -1;
				} else if (team1.getMaxScore() > team2.getMaxScore()) {
					return 1;
				} else if (team1.getMaxScore() < team2.getMaxScore()) {
					return -1;
				}
				return 0;
			}
		});
		int rank = 1;
		for (Team team : teams) {
			teams.get(rank - 1).setRank(rank);
			rank++;
			System.out.println(String.format("Team #%02d is rank %d", team.getNumber(), team.getRank()));
		}
		Tournement.getWebServer().reformatWebpageRankings();
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}
}
