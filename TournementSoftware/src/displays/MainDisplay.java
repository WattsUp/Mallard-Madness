package displays;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import managers.Team;
import utils.CustomColors;

public class MainDisplay implements ActionListener {

	private JFrame frame;
	private JPanel cardHolder;
	private CardLayout cardLayout;
	private CardSwitcher cardSwitcher;
	private ArrayList<JPanel> cards;
	private ScoringBar scoringBar;
	private ControlPanel controlPanel;

	private Logo cardLogo;
	private MatchResults cardResults;
	private AllianceSelection cardAlliance;
	private EliminationBracket cardBracket;
	private MatchPreview cardPreview;
	private Congratulations cardWinner;

	public MainDisplay() {
		cards = new ArrayList<JPanel>();

		cardLogo = new Logo();
		cardPreview = new MatchPreview();
		cardResults = new MatchResults();
		cardAlliance = new AllianceSelection();
		cardBracket = new EliminationBracket();
		cardWinner = new Congratulations();

		addCard(cardLogo);
		addCard(cardPreview);
		addCard(cardResults);
		addCard(cardAlliance);
		addCard(cardBracket);
		addCard(cardWinner);

		cardLayout = new CardLayout();
		cardHolder = new JPanel();
		cardHolder.setLayout(cardLayout);
		cardHolder.setSize(new Dimension(1920, 1080));
		cardHolder.setBackground(CustomColors.CHROMAKEY);

		cardSwitcher = new CardSwitcher(addCards(), this);

		frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(1926, 1109);
		frame.add(cardHolder);
		cardHolder.setBounds(0, 0, cardHolder.getPreferredSize().width, cardHolder.getPreferredSize().height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Display");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		scoringBar = new ScoringBar();
		controlPanel = new ControlPanel();

		scoringBar.setLocation(0, 0);
		frame.setLocation(0, scoringBar.getHeight());
		cardSwitcher.setLocation(0, scoringBar.getHeight());//TODO + frame.getHeight());
		controlPanel.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().getBounds().width - controlPanel.getWidth(), 0);
		
		scoringBar.repaint();
		frame.repaint();
		cardSwitcher.repaint();
		controlPanel.repaint();
	}

	private void addCard(JPanel card) {
		cards.add(card);
	}

	private String[] addCards() {
		ArrayList<String> cardNames = new ArrayList<String>();
		for (JPanel card : cards) {
			cardHolder.add(card, card.toString());
			cardNames.add(card.toString());
		}
		return cardNames.toArray(new String[cardNames.size()]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		setActiveCard(e.getActionCommand());
		cardSwitcher.setButtonEnabled((JButton) e.getSource());
	}

	public void setActiveCard(String cardName) {
		cardLayout.show(cardHolder, cardName);
		cardSwitcher.setButtonEnabled(cardName);
	}

	public void postResults(String match, String[] redTeamInfo, String[] blueTeamInfo, int[] redScores,
			int[] blueScores, Boolean redWon, Boolean highScore) {
		cardResults.postResults(match, redTeamInfo, blueTeamInfo, redScores, blueScores, redWon, highScore);
	}

	public void setMatchInfo(String matchTitle, int team1, String team1Name, int team1Rank, int team2, String team2Name,
			int team2Rank, int team3, String team3Name, int team3Rank, int team4, String team4Name, int team4Rank) {
		scoringBar.setMatchInfo(matchTitle, team1, team1Name, team2, team2Name, team3, team3Name, team4, team4Name);
		cardPreview.setMatchPreview(matchTitle, team1, team1Name, team1Rank, team2, team2Name, team2Rank, team3,
				team3Name, team3Rank, team4, team4Name, team4Rank);
	}

	public void setMatchInfoEliminations(String matchTitle, int team1, String team1Name, int team1Alliance, int team2,
			String team2Name, int team2Alliance, int team3, String team3Name, int team3Alliance, int team4,
			String team4Name, int team4Alliance) {
		scoringBar.setMatchInfo(matchTitle, team1, team1Name, team2, team2Name, team3, team3Name, team4, team4Name);
		cardPreview.setMatchPreviewEliminations(matchTitle, team1, team1Name, team1Alliance, team2, team2Name,
				team2Alliance, team3, team3Name, team3Alliance, team4, team4Name, team4Alliance);
	}

	public void resetTimerBar() {
		scoringBar.resetTimerBar();
	}

	public void setTimerBar(int timeLeft, int warningPeriodStart, int endTime) {
		scoringBar.setTimerBar(timeLeft, warningPeriodStart, endTime);
	}

	public void setTimerBar(int maxTime, int timeLeft, int warningPeriodStart, int endTime) {
		scoringBar.setTimerBar(maxTime, timeLeft, warningPeriodStart, endTime);
	}

	public void setScore(int redScore, int blueScore) {
		scoringBar.setScore(redScore, blueScore);
	}
	
	public void setFlags(int[] flagDetails) {
		scoringBar.setFlags(flagDetails);
	}

	public void setEliminationTeam(int match, int[] redAlliance, int[] blueAlliance) {
		cardBracket.setTeam(match, redAlliance, blueAlliance);
	}

	public void setEliminationWinner(int round, int alliance) {
		cardBracket.setWinner(round, alliance);
	}

	public void refreshAvailableList(ArrayList<Team> availableTeams) {
		cardAlliance.refreshAvailableList(availableTeams);
	}

	public void setAlliance(int teamNumber, int position) {
		cardAlliance.setAlliance(teamNumber, position);
	}

	public void setAllianceButtonsEnabled(boolean enabled) {
		controlPanel.setAllianceButtonsEnabled(enabled);
	}

	public void setControlPanelEnabled(String button, boolean enabled) {
		controlPanel.setEnabled(button, enabled);
	}

	public void resetScoringWrapper() {
		controlPanel.resetScoringWrapper();
	}

	public void setTimeout(boolean enabled) {
		controlPanel.setTimeout(enabled);
	}

	public void addControlPanelEvent(ActionEvent e) {
		controlPanel.addControlPanelEvent(e);
	}

	public void addScoringModuleEvent(int modulePosition, ActionEvent e) {
		controlPanel.addScoringModuleEvent(modulePosition, e);
	}

	public void setTournementWinner(String captainName, String partnerName) {
		cardWinner.setWinner(captainName, partnerName);
	}
	
	public void repaintAll(){
		frame.repaint();
		scoringBar.repaint();
		controlPanel.repaint();
		cardSwitcher.repaint();
	}
}
