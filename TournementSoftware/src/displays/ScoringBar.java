package displays;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import managers.MatchTimer;
import utils.CustomColors;
import utils.Fonts;

class ScoringBar {
	private Fonts fonts = Fonts.getInstance();

	private JFrame frame;
	private JPanel panel;
	private JPanel lowerWrapper;
	private JPanel leftWrapper;
	private JPanel rightWrapper;

	private JProgressBar timerBar;

	private JLabel timeLabel;
	private JLabel matchLabel;
	private JLabel matchLabelBlank;
	private JLabel scoreRedLabel;
	private JLabel scoreBlueLabel;
	private JLabel teamsRedLabel;
	private JLabel teamsBlueLabel;

	private JPanel timerOverlay;

	/**
	 * This makes a scoring bar
	 */
	public ScoringBar() {
		timerBar = new JProgressBar(0, MatchTimer.getInstance().getMaxTime());
		timeLabel = new JLabel("");
		scoreRedLabel = new JLabel("0");
		scoreBlueLabel = new JLabel("0");
		teamsRedLabel = new JLabel("");
		teamsBlueLabel = new JLabel("");
		matchLabel = new JLabel("");
		matchLabelBlank = new JLabel("");
		timerOverlay = new JPanel();

		initializeTimerBar();
		initializeTimeLabel();
		initializeMatchLabel();
		initializeMatchLabelBlank();
		initializeTimerOverlay();
		initializeScoreRedLabel();
		initializeScoreBlueLabel();
		initializeTeamsRedLabel();
		initializeTeamsBlueLabel();

		timerOverlay.add(matchLabel);
		timerOverlay.add(timeLabel);
		timerOverlay.add(matchLabelBlank);

		timerBar.add(timerOverlay);

		leftWrapper = new JPanel();
		leftWrapper.setLayout(new BorderLayout(0, 0));
		leftWrapper.add(teamsRedLabel, BorderLayout.CENTER);
		leftWrapper.add(scoreRedLabel, BorderLayout.LINE_END);
		
		rightWrapper = new JPanel();
		rightWrapper.setLayout(new BorderLayout(0, 0));
		rightWrapper.add(teamsBlueLabel, BorderLayout.CENTER);
		rightWrapper.add(scoreBlueLabel, BorderLayout.LINE_START);
		
		lowerWrapper = new JPanel();
		lowerWrapper.setLayout(null);
		lowerWrapper.setPreferredSize(new Dimension(1920, 128));
		lowerWrapper.add(leftWrapper);
		leftWrapper.setBounds(0, 0, 960, lowerWrapper.getPreferredSize().height);
		lowerWrapper.add(rightWrapper);
		rightWrapper.setBounds(960, 0, 960, lowerWrapper.getPreferredSize().height);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(1920, 160));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(timerBar);
		panel.add(lowerWrapper);
		panel.setBackground(CustomColors.GREEN);

		frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(1926, 189);
		frame.add(panel);
		panel.setBounds(0, 0, panel.getPreferredSize().width, panel.getPreferredSize().height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Scoring Bar");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.repaint();
	}

	/**
	 * This changes the score label
	 * 
	 * @param redScore
	 * @param blueScore
	 */
	public void setScore(int redScore, int blueScore) {
		scoreRedLabel.setText(String.valueOf(redScore));
		scoreBlueLabel.setText(String.valueOf(blueScore));
		frame.repaint();
	}
	
	/**
	 * This sets the timer bar and label to the appropriate color and value
	 * 
	 * @param timeLeft
	 * @param warningPeriodStart
	 * @param endTime
	 */
	public void setTimerBar(int timeLeft, int warningPeriodStart, int endTime) {
		timerBar.setValue(timerBar.getMaximum() - timeLeft);
		timeLabel.setText(String.valueOf(timeLeft));
		if (timeLeft == warningPeriodStart) {
			timerBar.setForeground(CustomColors.YELLOW);
		}
		if (timeLeft == endTime) {
			timerBar.setForeground(CustomColors.RED_BRIGHT);
		}
		frame.repaint();
	}

	public void setTimerBar(int maxTime, int timeLeft, int warningPeriodStart, int endTime) {
		timerBar.setMaximum(maxTime);
		timerBar.setValue(timerBar.getMaximum() - timeLeft);
		timeLabel.setText(String.valueOf(timeLeft));
		frame.repaint();
		if (timeLeft == warningPeriodStart) {
			timerBar.setForeground(CustomColors.YELLOW);
		}
		if (timeLeft == endTime) {
			timerBar.setForeground(CustomColors.RED_BRIGHT);
		}
		frame.repaint();
	}

	/**
	 * This sets the timer bar and label to zero and reverts the color
	 */
	public void resetTimerBar() {
		timerBar.setValue(0);
		timerBar.setForeground(CustomColors.GREEN);
		timeLabel.setText("");
		frame.repaint();
	}

	/**
	 * This sets the Teams' names and numbers and the match title
	 * 
	 * @param matchTitle
	 * @param team1
	 * @param team1Name
	 * @param team2
	 * @param team2Name
	 * @param team3
	 * @param team3Name
	 * @param team4
	 * @param team4Name
	 */
	public void setMatchInfo(String matchTitle, int team1, String team1Name, int team2, String team2Name, int team3,
			String team3Name, int team4, String team4Name) {
		resetTimerBar();
		matchLabel.setText(matchTitle);
		String teamInfoRed = "";
		String teamInfoBlue = "";
		if (team1 != 0) {
			teamInfoRed = String.format(
					"<html><div style='text-align: right;'><p>%s %02d</p><p>%s %02d</p></div></html>", team1Name, team1,
					team2Name, team2);
			teamInfoBlue = String.format(
					"<html><div style='text-align: left;'><p>%02d %s</p><p>%02d %s</p></div></html>", team3, team3Name,
					team4, team4Name);
		}
		teamsRedLabel.setText(teamInfoRed);
		teamsBlueLabel.setText(teamInfoBlue);
		frame.repaint();
	}

	/**
	 * Makes the Team Label for blue
	 */
	private void initializeTeamsBlueLabel() {
		teamsBlueLabel.setMinimumSize(new Dimension(800, 118));
		teamsBlueLabel.setMaximumSize(new Dimension(800, 118));
		teamsBlueLabel.setPreferredSize(new Dimension(800, 118));
		teamsBlueLabel.setHorizontalAlignment(JLabel.LEFT);
		teamsBlueLabel.setFont(fonts.terminal[fonts.LARGE]);
		teamsBlueLabel.setForeground(CustomColors.BLACK);
	}

	/**
	 * Makes the Team Label for red
	 */
	private void initializeTeamsRedLabel() {
		teamsRedLabel.setMinimumSize(new Dimension(800, 118));
		teamsRedLabel.setMaximumSize(new Dimension(800, 118));
		teamsRedLabel.setPreferredSize(new Dimension(800, 118));
		teamsRedLabel.setHorizontalAlignment(JLabel.RIGHT);
		teamsRedLabel.setFont(fonts.terminal[fonts.LARGE]);
		teamsRedLabel.setForeground(CustomColors.BLACK);
	}

	/**
	 * Makes the Score Label for blue
	 */
	private void initializeScoreBlueLabel() {
		scoreBlueLabel.setMinimumSize(new Dimension(150, 118));
		scoreBlueLabel.setMaximumSize(new Dimension(150, 118));
		scoreBlueLabel.setPreferredSize(new Dimension(150, 118));
		scoreBlueLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreBlueLabel.setFont(fonts.scienceFair[fonts.X_LARGE]);
		scoreBlueLabel.setBackground(CustomColors.BLUE);
		scoreBlueLabel.setForeground(CustomColors.WHITE);
		scoreBlueLabel.setOpaque(true);
	}

	/**
	 * Makes the Score Label for red
	 */
	private void initializeScoreRedLabel() {
		scoreRedLabel.setMinimumSize(new Dimension(150, 118));
		scoreRedLabel.setMaximumSize(new Dimension(150, 118));
		scoreRedLabel.setPreferredSize(new Dimension(150, 118));
		scoreRedLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreRedLabel.setFont(fonts.scienceFair[fonts.X_LARGE]);
		scoreRedLabel.setBackground(CustomColors.RED);
		scoreRedLabel.setForeground(CustomColors.WHITE);
		scoreRedLabel.setOpaque(true);
	}

	/**
	 * Makes the Timer Overlay wrapper
	 */
	private void initializeTimerOverlay() {
		timerOverlay.setMinimumSize(new Dimension(1536, 42));
		timerOverlay.setMaximumSize(new Dimension(1536, 42));
		timerOverlay.setPreferredSize(new Dimension(1536, 42));
		timerOverlay.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		timerOverlay.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
		timerOverlay.setBackground(CustomColors.CLEAR);
		timerOverlay.setOpaque(true);
	}

	/**
	 * Makes the Match Label spacer
	 */
	private void initializeMatchLabelBlank() {
		matchLabelBlank.setMinimumSize(new Dimension(700, 42));
		matchLabelBlank.setMaximumSize(new Dimension(700, 42));
		matchLabelBlank.setPreferredSize(new Dimension(700, 42));
		matchLabelBlank.setBorder(BorderFactory.createEmptyBorder());
	}

	/**
	 * Makes the Match Label
	 */
	private void initializeMatchLabel() {
		matchLabel.setMinimumSize(new Dimension(700, 42));
		matchLabel.setMaximumSize(new Dimension(700, 42));
		matchLabel.setPreferredSize(new Dimension(700, 42));
		matchLabel.setHorizontalAlignment(JLabel.LEFT);
		matchLabel.setBorder(BorderFactory.createEmptyBorder());
		matchLabel.setFont(fonts.scienceFair[fonts.MEDIUM]);
		matchLabel.setBackground(CustomColors.CLEAR);
		matchLabel.setForeground(CustomColors.BLACK);
		matchLabel.setOpaque(true);
	}

	/**
	 * Makes the Time Label
	 */
	private void initializeTimeLabel() {
		timeLabel.setMinimumSize(new Dimension(70, 42));
		timeLabel.setMaximumSize(new Dimension(70, 42));
		timeLabel.setPreferredSize(new Dimension(70, 42));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setBorder(BorderFactory.createEmptyBorder());
		timeLabel.setFont(fonts.scienceFair[fonts.MEDIUM]);
		timeLabel.setBackground(CustomColors.WHITE_TRANSPARENT);
		timeLabel.setForeground(CustomColors.BLACK);
		timeLabel.setOpaque(true);
	}

	/**
	 * Makes the Timer Bar
	 */
	private void initializeTimerBar() {
		timerBar.setMinimumSize(new Dimension(1920, 42));
		timerBar.setMaximumSize(new Dimension(1920, 42));
		timerBar.setPreferredSize(new Dimension(1920, 42));
		timerBar.setLayout(new BoxLayout(timerBar, BoxLayout.Y_AXIS));
		timerBar.setBorder(BorderFactory.createEmptyBorder());
		timerBar.setForeground(CustomColors.GREEN);
	}

	public String toString() {
		return "Scoring Bar";
	}

	public void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	public int getHeight() {
		return frame.getHeight();
	}
	
	public void repaint(){
		frame.repaint();
	}
}
