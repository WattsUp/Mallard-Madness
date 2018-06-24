package managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import utils.Utils;

public class ScoringManager implements ActionListener {
	private static ScoringManager instance;

	private int[] counts = new int[16];
	private int[] score = new int[16];
	private final int[] value = { 10, 5, 1, 60, 60, 30, 10, 40, 10, 5, 1, 60, 60, 30, 10, 40 };

	private File file;

	public static ScoringManager getInstance() {
		if (instance == null) {
			instance = new ScoringManager();
		}
		return instance;
	}

	private ScoringManager() {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
	}

	public void setCounts(int position, int count) {
		if (position == 3 || position == 9) {
			counts[position] = (count >= 2) ? 3 : count;
		} else {
			counts[position] = count;
		}
		score = Utils.productOfArrays(counts, value);
		System.out.println(Arrays.toString(score));
		int redScore = Utils.sumOfArray(Arrays.copyOfRange(score, 0, 6), Arrays.copyOfRange(score, 14, 16));
		int blueScore = Utils.sumOfArray(Arrays.copyOfRange(score, 8, 14), Arrays.copyOfRange(score, 6, 8));
		Tournement.getMainDisplay().setScore(redScore, blueScore);
	}

	public void resetCounts() {
		Utils.fillZeros(counts);
		score = Utils.productOfArrays(counts, value);
		System.out.println(Arrays.toString(score));
		int redScore = Utils.sumOfArray(Arrays.copyOfRange(score, 0, 6), Arrays.copyOfRange(score, 14, 16));
		int blueScore = Utils.sumOfArray(Arrays.copyOfRange(score, 8, 14), Arrays.copyOfRange(score, 6, 8));
		Tournement.getMainDisplay().setScore(redScore, blueScore);
	}

	public void saveScores() {
		switch (MatchManager.getInstance().getCurrentRound()) {
		case QUALIFICATIONS:
			Utils.appendToFile(file, String.format("%d,%s,%s", MatchManager.getInstance().getCurrentMatch(),
					Utils.arrayToCSV(MatchManager.getInstance().getCurrentAlliance()), Utils.arrayToCSV(score)));
			break;
		case FINALS:
		case SEMIFINALS:
			int[] alliances = MatchManager.getInstance().getCurrentAlliance();
			for (int i = 0; i < alliances.length; i++) {
				alliances[i] = EliminationsManager.getInstance().getAlliance(alliances[i]) + 1;
			}
			Utils.appendToFile(file, String.format("%d,%s,%s", MatchManager.getInstance().getCurrentMatch(),
					Utils.arrayToCSV(alliances), Utils.arrayToCSV(score)));
			break;
		}
	}

	public void setSaveFile(File file) {
		this.file = file;
		System.out.println("Saving match results in " + file.getAbsolutePath());
		if (file.exists()) {
			loadScoresFromFile();
		} else {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
				String line = new String(
						"Match,Red1,Red2,Blue1,Blue2,Red High Duckling,Red Low Duckling,Red Pen Duckling,Red High Mallard,Red Low Mallard,Red Pen Mallard,Red Minor,Red Major,Blue High Duckling,Blue Low Duckling,Blue Pen Duckling,Blue High Mallard,Blue Low Mallard,Blue Pen Mallard,Blue Minor,Blue Major");
				bw.write(line);
				bw.newLine();
				bw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void loadScoresFromFile() {
		MatchManager matchManager = MatchManager.getInstance();
		BufferedReader br = null;
		String line = "";
		boolean semifinalSection = false;
		boolean finalSection = false;
		try {
			br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while ((line = br.readLine()) != null) {
				if (line.contains("Match")) {
					// File header
				} else {
					int[] lineValues;
					switch (matchManager.getCurrentRound()) {
					case FINALS:
					case SEMIFINALS:
						if (line.contains("Final")) {
							finalSection = true;
							semifinalSection = false;
						} else if (line.contains("Semifinals")) {
							semifinalSection = true;
							finalSection = false;
						} else {
							if (semifinalSection) {
								lineValues = Utils.CSVToIntArray(line);
								score = Arrays.copyOfRange(lineValues, 5, lineValues.length);
								matchManager.setMatch(lineValues[0]);
								System.out.println(String.format("Loaded scores for match #%2d", lineValues[0]));
								matchManager.getHighScoreStatus();
								if (getRedWon() != null) {
									if (getRedWon()) {
										EliminationsManager.getInstance().setWinner(true, lineValues[1] - 1);
									} else {
										EliminationsManager.getInstance().setWinner(true, lineValues[3] - 1);
									}
								}

							} else if (finalSection) {
								lineValues = Utils.CSVToIntArray(line);
								score = Arrays.copyOfRange(lineValues, 5, lineValues.length);
								matchManager.setMatch(lineValues[0]);
								System.out.println(String.format("Loaded scores for match #%2d", lineValues[0]));
								matchManager.getHighScoreStatus();
								if (getRedWon() != null) {
									if (getRedWon()) {
										EliminationsManager.getInstance().setWinner(false, lineValues[1] - 1);
									} else {
										EliminationsManager.getInstance().setWinner(false, lineValues[3] - 1);
									}
								}
							} else {
								lineValues = Utils.CSVToIntArray(line);
								score = Arrays.copyOfRange(lineValues, 5, lineValues.length);
								matchManager.getHighScoreStatus();
							}
						}
						break;
					case QUALIFICATIONS:
						lineValues = Utils.CSVToIntArray(line);
						if (matchManager.getMatch(lineValues[0]).getTeams()[0] == lineValues[1]) {
							score = Arrays.copyOfRange(lineValues, 5, lineValues.length);
							matchManager.setMatch(lineValues[0]);
							//matchManager.saveScores(getScores(true)[0], getScores(false)[0], getScores(true)[2],
							//		getScores(false)[2]);
							System.out.println(String.format("Loaded scores for match #%2d", lineValues[0]));
							matchManager.getHighScoreStatus();
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TeamManager.getInstance().recalculateRank();
	}

	/**
	 * 
	 * @param isRed
	 * @return(Total, Ducklings, Mallards, Fouls}
	 */
	public int[] getScores(boolean isRed) {
		int[] scores = new int[4];
		scores[0] = Utils.sumOfArray(Arrays.copyOfRange(score, isRed ? 0 : 8, isRed ? 6 : 14),
				Arrays.copyOfRange(score, isRed ? 14 : 6, isRed ? 16 : 8));
		scores[1] = score[isRed ? 0 : 8];
		scores[1] += score[isRed ? 1 : 9];
		scores[1] += score[isRed ? 2 : 10];
		scores[2] = score[isRed ? 3 : 11];
		scores[2] += score[isRed ? 4 : 12];
		scores[2] += score[isRed ? 5 : 13];
		scores[3] = score[isRed ? 14 : 6];
		scores[3] += score[isRed ? 15 : 7];
		return scores;
	}

	public Boolean getRedWon() {
		int[] redScores = getScores(true);
		int[] blueScores = getScores(false);
		if (redScores[0] == blueScores[0]) {
			if (MatchManager.getInstance().getCurrentRound() == MatchManager.Round.QUALIFICATIONS) {
				return null;
			} else {
				if (redScores[2] != blueScores[2]) {
					return redScores[2] > blueScores[2];
				} else if (redScores[1] != blueScores[1]) {
					return redScores[1] > blueScores[1];
				} else if (redScores[3] != blueScores[3]) {
					return redScores[3] > blueScores[3];
				} else {
					return null;
				}
			}
		}
		return redScores[0] > blueScores[0];
	}

	public int[] getCounts() {
		return counts;
	}

	public void setRound(String round) {
		Utils.appendToFile(file, round);
	}
}
