package managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import utils.Utils;

public class MatchManager {
	private static MatchManager instance;

	private int currentMatch = 0;
	private int highScore = 0;
	private ArrayList<Match> matches = new ArrayList<Match>();

	private File file;

	private Round currentRound = Round.QUALIFICATIONS;

	public enum Round {
		QUALIFICATIONS, SEMIFINALS, FINALS
	}

	public static MatchManager getInstance() {
		if (instance == null) {
			instance = new MatchManager();
		}
		return instance;
	}

	private MatchManager() {
	}

	public Match getMatch(int number) {
		for (Match match : matches) {
			if (match.getNumber() == number) {
				return match;
			}
		}
		return null;
	}

	public int getCurrentMatch() {
		return currentMatch;
	}

	public String getCurrentMatchLabel() {
		switch (currentRound) {
		case FINALS:
			if (currentMatch > 2) {
				return "Final Tiebreaker";
			} else {
				return String.format("Final %d of %d", currentMatch, matches.size());
			}
		case QUALIFICATIONS:
			return String.format("Qualification %d of %d", currentMatch, matches.size());
		case SEMIFINALS:
			if (currentMatch > 4) {
				return "Semifinal Tiebreaker";
			} else {
				return String.format("Semifinal %d of %d", currentMatch, matches.size());
			}
		}
		return "IDK";
	}

	public int[] getCurrentAlliance() {
		return getMatch(currentMatch).getTeams();
	}

	public void loadNextMatch() {
		currentMatch++;
	}

	public void setMatch(int match) {
		currentMatch = match;
	}

	public Boolean isRoundFinished() {
		if (getMatch(currentMatch + 1) == null) {
			return true;
		}
		return false;
	}

	public String[] getTeamInfo(Boolean isRed) {
		String[] info = new String[9];
		switch (currentRound) {
		case QUALIFICATIONS:
			TeamManager teamManager = TeamManager.getInstance();
			info[0] = "Team #";
			info[1] = String.valueOf(teamManager.getTeam(getCurrentAlliance()[isRed ? 0 : 2]).getNumber());
			info[2] = String.valueOf(teamManager.getTeam(getCurrentAlliance()[isRed ? 1 : 3]).getNumber());
			info[3] = "Rank";
			info[4] = String.valueOf(teamManager.getTeam(getCurrentAlliance()[isRed ? 0 : 2]).getRank());
			info[5] = String.valueOf(teamManager.getTeam(getCurrentAlliance()[isRed ? 1 : 3]).getRank());
			info[6] = "AVG";
			info[7] = String.format("%5.2f",
					teamManager.getTeam(getCurrentAlliance()[isRed ? 0 : 2]).getAverageScore());
			info[8] = String.format("%5.2f",
					teamManager.getTeam(getCurrentAlliance()[isRed ? 1 : 3]).getAverageScore());
			break;
		case SEMIFINALS:
		case FINALS:
			EliminationsManager eliminationsManager = EliminationsManager.getInstance();
			info[0] = "Team #";
			info[1] = String.valueOf(eliminationsManager.getTeams(getCurrentAlliance()[isRed ? 0 : 2] - 1)[0]);
			info[2] = String.valueOf(eliminationsManager.getTeams(getCurrentAlliance()[isRed ? 0 : 2] - 1)[1]);
			info[3] = "Alliance";
			info[4] = String.valueOf(getCurrentAlliance()[isRed ? 0 : 2]);
			info[5] = String.valueOf(getCurrentAlliance()[isRed ? 0 : 2]);
			info[6] = "Wins";
			info[7] = String
					.valueOf(EliminationsManager.getInstance().getWins(getCurrentAlliance()[isRed ? 0 : 2] - 1));
			info[8] = String
					.valueOf(EliminationsManager.getInstance().getWins(getCurrentAlliance()[isRed ? 0 : 2] - 1));

			break;
		}
		return info;
	}

	public void saveScores(int[] red, int[] blue) {
		TeamManager teamManager = TeamManager.getInstance();
		teamManager.getTeam(getCurrentAlliance()[0]).addScore(red);
		teamManager.getTeam(getCurrentAlliance()[1]).addScore(red);
		teamManager.getTeam(getCurrentAlliance()[2]).addScore(blue);
		teamManager.getTeam(getCurrentAlliance()[3]).addScore(blue);
	}

	public Boolean getHighScoreStatus() {
		Boolean high = (ScoringManager.getInstance().getScores(true)[0] > highScore
				|| ScoringManager.getInstance().getScores(false)[0] > highScore);
		if (high) {
			highScore = Math.max(ScoringManager.getInstance().getScores(true)[0], highScore);
			highScore = Math.max(ScoringManager.getInstance().getScores(false)[0], highScore);
			System.out.println(String.format("New High Score: %3d", highScore));
		}
		return high;
	}

	private void addMatch(Match match) {
		matches.add(match);
	}

	public void loadMatchesFromFile(File file) {
		this.file = file;
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				if (line.contains("Match")) {
					// File header
				} else if (line.contains("Semifinals")) {
					System.out.println("Found Semifinal matches");
					currentRound = Round.SEMIFINALS;
					currentMatch = 0;
					matches.clear();
				} else if (line.contains("Finals")) {
					System.out.println("Found Final matches");
					currentRound = Round.FINALS;
					currentMatch = 0;
					matches.clear();
				} else {
					String[] match = line.split(",");
					int matchNumber = Integer.parseInt(match[0]);
					int red1 = Integer.parseInt(match[1]);
					int red2 = Integer.parseInt(match[2]);
					int blue1 = Integer.parseInt(match[3]);
					int blue2 = Integer.parseInt(match[4]);
					addMatch(new Match(matchNumber, red1, red2, blue1, blue2));
					System.out.println(String.format("Added Match #%2d with teams:%2d, %2d, %2d, %2d", matchNumber,
							red1, red2, blue1, blue2));
					switch (currentRound) {
					case FINALS:
						if (matchNumber == 1) {
							EliminationsManager.getInstance().setTeam(0, red1, red2);
							EliminationsManager.getInstance().setTeam(1, blue1, blue2);
						}
						break;
					case QUALIFICATIONS:
						break;
					case SEMIFINALS:
						if (matchNumber == 1) {
							EliminationsManager.getInstance().setTeam(0, red1, red2);
							EliminationsManager.getInstance().setTeam(3, blue1, blue2);
						} else if (matchNumber == 2) {
							EliminationsManager.getInstance().setTeam(1, red1, red2);
							EliminationsManager.getInstance().setTeam(2, blue1, blue2);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setNextRound() {
		if (currentRound == Round.QUALIFICATIONS) {
			currentRound = Round.SEMIFINALS;
			currentMatch = 0;
			matches.clear();
			Utils.appendToFile(file, "Semifinals");
			ScoringManager.getInstance().setRound("Semifinals");
		} else if (currentRound == Round.SEMIFINALS) {
			currentRound = Round.FINALS;
			currentMatch = 0;
			matches.clear();
			Utils.appendToFile(file, "Finals");
			ScoringManager.getInstance().setRound("Finals");
		}
	}

	public Round getCurrentRound() {
		return currentRound;
	}

	public void addMatch(int matchNumber, int[] redAlliance, int[] blueAlliance) {
		matches.add(new Match(matchNumber, redAlliance[0], redAlliance[1], blueAlliance[0], blueAlliance[1]));
		Utils.appendToFile(file, String.format("%d,%d,%d,%d,%d", matchNumber, redAlliance[0], redAlliance[1],
				blueAlliance[0], blueAlliance[1]));
	}
}
