package managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import utils.CustomEvents;

public class ControlPanelListener implements ActionListener {

	public ControlPanelListener() {
		MatchTimer.getInstance().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getID() == CustomEvents.EVENT_ID_MATCH_ENDED) {
			Tournement.getMainDisplay().setControlPanelEnabled("Stop", false);
			Tournement.getMainDisplay().setControlPanelEnabled("Save", true);
			return;
		}
		String eventString = e.getActionCommand().toLowerCase();
		if (eventString.contains("Start".toLowerCase())) {
			Tournement.getMainDisplay().setControlPanelEnabled("Start", false);
			Tournement.getMainDisplay().setControlPanelEnabled("Stop", true);
		} else if (eventString.contains("Reset".toLowerCase())) {
			Tournement.getMainDisplay().setControlPanelEnabled("Start", true);
			Tournement.getMainDisplay().setControlPanelEnabled("Reset", false);
			Tournement.getMainDisplay().resetScoringWrapper();
			Tournement.getMainDisplay().resetTimerBar();
			ScoringManager.getInstance().resetCounts();
		} else if (eventString.contains("E-Stop".toLowerCase())) {
			Tournement.getMainDisplay().setControlPanelEnabled("Reset", true);
			Tournement.getMainDisplay().setControlPanelEnabled("Stop", false);
		} else if (eventString.contains("Next".toLowerCase())) {
			Tournement.getMainDisplay().setControlPanelEnabled("Start", true);
			Tournement.getMainDisplay().setControlPanelEnabled("Next", false);
			Tournement.getMainDisplay().resetScoringWrapper();

			ScoringManager.getInstance().resetCounts();

			MatchManager matchManager = MatchManager.getInstance();
			TeamManager teamManager = TeamManager.getInstance();
			if (matchManager.isRoundFinished()) {
				switch (matchManager.getCurrentRound()) {
				case FINALS:
					EliminationsManager.getInstance().advanceRound();
					break;
				case QUALIFICATIONS:
					Tournement.getMainDisplay().setControlPanelEnabled("AllianceStart", true);
					Tournement.getMainDisplay().setControlPanelEnabled("Start", false);
					return;
				case SEMIFINALS:
					EliminationsManager.getInstance().advanceRound();
					break;
				}
			}
			matchManager.loadNextMatch();
			int[] teams = matchManager.getCurrentAlliance();

			switch (matchManager.getCurrentRound()) {
			case QUALIFICATIONS:
				Tournement.getMainDisplay().setMatchInfo(matchManager.getCurrentMatchLabel(), teams[0],
						teamManager.getTeam(teams[0]).getName(), teamManager.getTeam(teams[0]).getRank(), teams[1],
						teamManager.getTeam(teams[1]).getName(), teamManager.getTeam(teams[1]).getRank(), teams[2],
						teamManager.getTeam(teams[2]).getName(), teamManager.getTeam(teams[2]).getRank(), teams[3],
						teamManager.getTeam(teams[3]).getName(), teamManager.getTeam(teams[3]).getRank());
				return;
			case FINALS:
			case SEMIFINALS:
				EliminationsManager eliminationsManager = EliminationsManager.getInstance();
				Tournement.getMainDisplay().setMatchInfoEliminations(matchManager.getCurrentMatchLabel(), teams[0],
						teamManager.getTeam(teams[0]).getName(), eliminationsManager.getAlliance(teams[0]) + 1,
						teams[1], teamManager.getTeam(teams[1]).getName(),
						eliminationsManager.getAlliance(teams[1]) + 1, teams[2],
						teamManager.getTeam(teams[2]).getName(), eliminationsManager.getAlliance(teams[2]) + 1,
						teams[3], teamManager.getTeam(teams[3]).getName(),
						eliminationsManager.getAlliance(teams[3]) + 1);
				break;
			}
			if (EliminationsManager.getInstance().isDone()){
				String captainName = TeamManager.getInstance().getTeam(EliminationsManager.getInstance().getWinner()[0]).getName();
				String partnerName = TeamManager.getInstance().getTeam(EliminationsManager.getInstance().getWinner()[1]).getName();
				Tournement.getMainDisplay().setTournementWinner(captainName, partnerName);
				Tournement.getMainDisplay().setControlPanelEnabled("Start", false);
			}

			Tournement.getMainDisplay().setActiveCard("Match Preview");
		} else if (eventString.contains("Save".toLowerCase())) {
			Tournement.getMainDisplay().setControlPanelEnabled("Next", true);
			Tournement.getMainDisplay().setControlPanelEnabled("Save", false);

			ScoringManager scoringManager = ScoringManager.getInstance();
			MatchManager matchManager = MatchManager.getInstance();

			scoringManager.saveScores();
			matchManager.saveScores(scoringManager.getScores(true), scoringManager.getScores(false));
			switch (matchManager.getCurrentRound()) {
			case FINALS:
			case SEMIFINALS:
				EliminationsManager.getInstance().setWinner(scoringManager.getRedWon());
				break;
			case QUALIFICATIONS:
				TeamManager.getInstance().recalculateRank();
				break;
			}
			Tournement.getMainDisplay().postResults(matchManager.getCurrentMatchLabel(), matchManager.getTeamInfo(true),
					matchManager.getTeamInfo(false), scoringManager.getScores(true), scoringManager.getScores(false),
					scoringManager.getRedWon(), matchManager.getHighScoreStatus());
			if (matchManager.isRoundFinished() && matchManager.getCurrentRound() == MatchManager.Round.QUALIFICATIONS) {
				Tournement.getMainDisplay().setControlPanelEnabled("Next", false);
				Tournement.getMainDisplay().setControlPanelEnabled("AllianceStart", true);
			}else if (EliminationsManager.getInstance().isDone()){
				String captainName = TeamManager.getInstance().getTeam(EliminationsManager.getInstance().getWinner()[0]).getName();
				String partnerName = TeamManager.getInstance().getTeam(EliminationsManager.getInstance().getWinner()[1]).getName();
				Tournement.getMainDisplay().setTournementWinner(captainName, partnerName);
				Tournement.getMainDisplay().setControlPanelEnabled("Next", false);
			}
			Tournement.getMainDisplay().setActiveCard("Match Results");
		} else if (eventString.contains("Alliance".toLowerCase())) {
			Tournement.getMainDisplay().setControlPanelEnabled("AllianceSelections", true);
			Tournement.getMainDisplay().setControlPanelEnabled("AllianceStart", false);
			AllianceManager.getInstance().startSelection();
			Tournement.getMainDisplay().setActiveCard("Alliance Selection");
		} else if (eventString.contains("Close".toLowerCase())) {
			Tournement.shutdown();
		}
	}

}
