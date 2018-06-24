package managers;

import java.util.ArrayList;

public class EliminationsManager {
	private static EliminationsManager instance;
	private int[][] semifinalsTeams;
	private int[][] finalsTeams;
	private int winner;

	public static EliminationsManager getInstance() {
		if (instance == null) {
			instance = new EliminationsManager();
		}
		return instance;
	}

	private EliminationsManager() {
		semifinalsTeams = new int[4][3];
		finalsTeams = new int[2][3];
	}

	public void setTeams(ArrayList<Team> allianceTeams) {
		semifinalsTeams[0][0] = allianceTeams.get(0).getNumber();
		semifinalsTeams[0][1] = allianceTeams.get(1).getNumber();
		semifinalsTeams[1][0] = allianceTeams.get(2).getNumber();
		semifinalsTeams[1][1] = allianceTeams.get(3).getNumber();
		semifinalsTeams[2][0] = allianceTeams.get(4).getNumber();
		semifinalsTeams[2][1] = allianceTeams.get(5).getNumber();
		semifinalsTeams[3][0] = allianceTeams.get(6).getNumber();
		semifinalsTeams[3][1] = allianceTeams.get(7).getNumber();
	}

	public void setTeam(int alliance, int captain, int partner) {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			semifinalsTeams[alliance][0] = captain;
			semifinalsTeams[alliance][1] = partner;
			Tournement.getMainDisplay().setEliminationTeam(0, semifinalsTeams[0], semifinalsTeams[3]);
			Tournement.getMainDisplay().setEliminationTeam(1, semifinalsTeams[1], semifinalsTeams[2]);
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			finalsTeams[alliance][0] = captain;
			finalsTeams[alliance][1] = partner;
			Tournement.getMainDisplay().setEliminationTeam(2, semifinalsTeams[0], semifinalsTeams[3]);
		}
	}

	public int[] getTeams(int alliance) {
		return semifinalsTeams[alliance];
	}

	public void generateMatches() {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			matchManager.addMatch(1, semifinalsTeams[0], semifinalsTeams[3]);
			matchManager.addMatch(2, semifinalsTeams[1], semifinalsTeams[2]);
			matchManager.addMatch(3, semifinalsTeams[0], semifinalsTeams[3]);
			matchManager.addMatch(4, semifinalsTeams[1], semifinalsTeams[2]);
			Tournement.getMainDisplay().setEliminationTeam(0, semifinalsTeams[0], semifinalsTeams[3]);
			Tournement.getMainDisplay().setEliminationTeam(1, semifinalsTeams[1], semifinalsTeams[2]);
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			matchManager.addMatch(1, finalsTeams[0], finalsTeams[1]);
			matchManager.addMatch(2, finalsTeams[0], finalsTeams[1]);
			Tournement.getMainDisplay().setEliminationTeam(2, finalsTeams[0], finalsTeams[1]);
		}
	}

	public void setWinner(int alliance) {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			semifinalsTeams[alliance][2]++;
			if (semifinalsTeams[alliance][2] == 2) {
				// They won
				int finalsAlliance = (alliance == 0 || alliance == 3) ? 0 : 1;
				finalsTeams[finalsAlliance][0] = semifinalsTeams[alliance][0];
				finalsTeams[finalsAlliance][1] = semifinalsTeams[alliance][1];
				Tournement.getMainDisplay().setEliminationTeam(2, finalsTeams[0], finalsTeams[1]);
			}
			Tournement.getMainDisplay().setEliminationWinner(2, alliance);
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			finalsTeams[alliance][2]++;
			Tournement.getMainDisplay().setEliminationWinner(1, alliance);
		}
	}

	public void setWinner(boolean semifinals, int alliance) {
		if (semifinals) {
			semifinalsTeams[alliance][2]++;
			if (semifinalsTeams[alliance][2] == 2) {
				// They won
				int finalsAlliance = (alliance == 0 || alliance == 3) ? 0 : 1;
				finalsTeams[finalsAlliance][0] = semifinalsTeams[alliance][0];
				finalsTeams[finalsAlliance][1] = semifinalsTeams[alliance][1];
				Tournement.getMainDisplay().setEliminationTeam(2, finalsTeams[0], finalsTeams[1]);
			}
			Tournement.getMainDisplay().setEliminationWinner(2, alliance);
		} else {
			if (semifinalsTeams[alliance][0] == finalsTeams[0][0]) {
				finalsTeams[0][2]++;
				Tournement.getMainDisplay().setEliminationWinner(1, 0);
			} else if (semifinalsTeams[alliance][0] == finalsTeams[1][0]) {
				finalsTeams[1][2]++;
				Tournement.getMainDisplay().setEliminationWinner(1, 1);
			}
		}
	}

	public void setWinner(Boolean redWon) {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			if (redWon == null) {
				// Replay match
			} else {
				if (matchManager.getCurrentMatch() % 2 == 1) {
					setWinner(redWon ? 0 : 3);
				} else {
					setWinner(redWon ? 1 : 2);
				}
			}
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			if (redWon == null) {
				// Replay Match
			} else {
				setWinner(redWon ? 0 : 1);
			}
		}
	}

	public int getAlliance(int team) {
		for (int i = 0; i < 4; i++) {
			if (semifinalsTeams[i][0] == team || semifinalsTeams[i][1] == team) {
				return i;
			}
		}
		return 0;
	}

	public int getRoundAlliance(int team) {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			for (int i = 0; i < 4; i++) {
				if (semifinalsTeams[i][0] == team || semifinalsTeams[i][1] == team) {
					return i;
				}
			}
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			for (int i = 0; i < 2; i++) {
				if (finalsTeams[i][0] == team || finalsTeams[i][1] == team) {
					return i;
				}
			}
		}
		return 0;
	}

	public int getWins(int alliance) {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			return semifinalsTeams[alliance][2];
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			return finalsTeams[getRoundAlliance(getTeams(alliance)[0])][2];
		}
		return -1;
	}

	public void advanceRound() {
		MatchManager matchManager = MatchManager.getInstance();
		if (matchManager.getCurrentRound() == MatchManager.Round.SEMIFINALS) {
			if (finalsTeams[0][0] == 0) {
				// No winner for semifinal 1
				matchManager.addMatch(matchManager.getCurrentMatch() + 1, semifinalsTeams[0], semifinalsTeams[3]);
			} else if (finalsTeams[1][0] == 0) {
				// No winner for semifinal 2
				matchManager.addMatch(matchManager.getCurrentMatch() + 1, semifinalsTeams[1], semifinalsTeams[2]);
			} else {
				matchManager.setNextRound();
				generateMatches();
			}
		} else if (matchManager.getCurrentRound() == MatchManager.Round.FINALS) {
			if (finalsTeams[0][2] != 2 || finalsTeams[1][2] != 2) {
				// No winner for finals
				matchManager.addMatch(matchManager.getCurrentMatch() + 1, finalsTeams[0], finalsTeams[1]);
			} else {
				winner = (finalsTeams[0][2] == 2) ? 0 : 1;
			}
		}
	}
	
	public boolean isDone(){
		return finalsTeams[0][2] == 2 || finalsTeams[1][2] == 2;
	}

	public int[] getWinner() {
		return finalsTeams[winner];
	}
}
