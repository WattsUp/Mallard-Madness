package displays;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class MatchResults extends BackgroundPanel {
	private Fonts fonts = Fonts.getInstance();

	private JLabel scoreRedLabel;
	private JLabel scoreBlueLabel;
	private JLabel winnerRed;
	private JLabel winnerBlue;
	private JLabel highScoreRed;
	private JLabel highScoreBlue;
	private JLabel matchLabel;

	private JPanel scoreBreakdownWrapper;
	private JPanel scoreWrapper;

	private ScoreDetails scoreDetailsRed;
	private ScoreDetails scoreDetailsBlue;

	/**
	 * This makes a match results display
	 * 
	 * @param location
	 *            of top left corner
	 */
	public MatchResults() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

		matchLabel = SwingUtils.label("Qualification #1", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.scienceFair[fonts.X_LARGE], 0, 0, JLabel.CENTER, BorderFactory.createEmptyBorder());
		scoreDetailsRed = new ScoreDetails(CustomColors.RED, true);
		scoreDetailsBlue = new ScoreDetails(CustomColors.BLUE, false);
		scoreRedLabel = new JLabel("0");
		scoreBlueLabel = new JLabel("0");
		winnerRed = new JLabel("");
		winnerBlue = new JLabel("");
		highScoreRed = new JLabel("");
		highScoreBlue = new JLabel("");
		scoreBreakdownWrapper = new JPanel();
		scoreWrapper = new JPanel();

		initializeScoreBreakdownWrapper();
		initializeScoreRedLabel();
		initializeScoreBlueLabel();
		initializeScoreWrapper();
		initializeWinnerRed();
		initializeWinnerBlue();
		initializeHighScoreRed();
		initializeHighScoreBlue();

		scoreRedLabel.add(highScoreRed);
		scoreBlueLabel.add(highScoreBlue);

		scoreWrapper.add(winnerRed, 0);
		scoreWrapper.add(Box.createHorizontalGlue(), 1);
		scoreWrapper.add(scoreRedLabel, 2);
		scoreWrapper.add(scoreBlueLabel, 3);
		scoreWrapper.add(Box.createHorizontalGlue(), 4);
		scoreWrapper.add(winnerBlue, 5);

		this.add(new JLabel(String.format("%500s", "")));
		this.add(scoreBreakdownWrapper);
		this.add(scoreWrapper);
		this.setVisible(true);
	}

	/**
	 * Makes the wrapper for the score and team details
	 */
	private void initializeScoreBreakdownWrapper() {
		scoreBreakdownWrapper.setMinimumSize(new Dimension(1400, 700));
		scoreBreakdownWrapper.setMaximumSize(new Dimension(1400, 700));
		scoreBreakdownWrapper.setPreferredSize(new Dimension(1400, 700));
		scoreBreakdownWrapper.setBorder(BorderFactory.createLineBorder(CustomColors.WHITE, 10));
		scoreBreakdownWrapper.setBackground(CustomColors.WHITE_TRANSPARENT);
		scoreBreakdownWrapper.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(5, 5, 40, 5);
		c.gridx = 0;
		c.gridy = 1;

		scoreBreakdownWrapper.add(scoreDetailsRed, c);

		c.gridx = 1;
		scoreBreakdownWrapper.add(scoreDetailsBlue, c);

		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		scoreBreakdownWrapper.add(matchLabel, c);
	}

	/**
	 * Makes the wrapper for the scores
	 */
	private void initializeScoreWrapper() {
		scoreWrapper.setMinimumSize(new Dimension(1400, 214));
		scoreWrapper.setMaximumSize(new Dimension(1400, 214));
		scoreWrapper.setPreferredSize(new Dimension(1400, 214));
		scoreWrapper.setLayout(new BoxLayout(scoreWrapper, BoxLayout.X_AXIS));
		scoreWrapper.setBackground(CustomColors.CLEAR);
	}

	/**
	 * Makes the score label for red
	 */
	private void initializeScoreRedLabel() {
		scoreRedLabel.setMinimumSize(new Dimension(200, 214));
		scoreRedLabel.setMaximumSize(new Dimension(200, 214));
		scoreRedLabel.setPreferredSize(new Dimension(200, 214));
		scoreRedLabel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		scoreRedLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreRedLabel.setFont(fonts.scienceFair[fonts.XX_LARGE]);
		scoreRedLabel.setBackground(CustomColors.RED);
		scoreRedLabel.setForeground(CustomColors.WHITE);
		scoreRedLabel.setOpaque(true);
		scoreRedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreRedLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	}

	/**
	 * Makes the score label for blue
	 */
	private void initializeScoreBlueLabel() {
		scoreBlueLabel.setMinimumSize(new Dimension(200, 214));
		scoreBlueLabel.setMaximumSize(new Dimension(200, 214));
		scoreBlueLabel.setPreferredSize(new Dimension(200, 214));
		scoreBlueLabel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		scoreBlueLabel.setFont(fonts.scienceFair[fonts.XX_LARGE]);
		scoreBlueLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreBlueLabel.setBackground(CustomColors.BLUE);
		scoreBlueLabel.setForeground(CustomColors.WHITE);
		scoreBlueLabel.setOpaque(true);
		scoreBlueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreBlueLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	}

	/**
	 * Makes the winner label for red
	 */
	private void initializeWinnerRed() {
		winnerRed.setMinimumSize(new Dimension(400, 214));
		winnerRed.setMaximumSize(new Dimension(400, 214));
		winnerRed.setPreferredSize(new Dimension(400, 214));
		winnerRed.setHorizontalAlignment(JLabel.CENTER);
		winnerRed.setFont(fonts.scienceFair[fonts.XX_LARGE]);
		winnerRed.setBackground(CustomColors.RED);
		winnerRed.setForeground(CustomColors.WHITE);
		winnerRed.setOpaque(true);
		winnerRed.setAlignmentX(Component.CENTER_ALIGNMENT);
		winnerRed.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		winnerRed.setText("Winner");
	}

	/**
	 * Makes the winner label for Blue
	 */
	private void initializeWinnerBlue() {
		winnerBlue.setMinimumSize(new Dimension(400, 214));
		winnerBlue.setMaximumSize(new Dimension(400, 214));
		winnerBlue.setPreferredSize(new Dimension(400, 214));
		winnerBlue.setHorizontalAlignment(JLabel.CENTER);
		winnerBlue.setFont(fonts.scienceFair[fonts.XX_LARGE]);
		winnerBlue.setBackground(CustomColors.BLUE);
		winnerBlue.setForeground(CustomColors.WHITE);
		winnerBlue.setOpaque(true);
		winnerBlue.setAlignmentX(Component.CENTER_ALIGNMENT);
		winnerBlue.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		winnerBlue.setText("Winner");
	}

	/**
	 * Makes a transparent winner label
	 * 
	 * @return label
	 */
	private JLabel winnerBlank() {
		JLabel label = new JLabel();
		label.setMinimumSize(new Dimension(400, 214));
		label.setMaximumSize(new Dimension(400, 214));
		label.setPreferredSize(new Dimension(400, 214));
		label.setBackground(CustomColors.CLEAR);
		label.setForeground(CustomColors.CLEAR);
		label.setOpaque(true);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		return label;
	}

	/**
	 * Makes the high score label for red
	 */
	private void initializeHighScoreRed() {
		highScoreRed.setMinimumSize(new Dimension(200, 50));
		highScoreRed.setMaximumSize(new Dimension(200, 50));
		highScoreRed.setPreferredSize(new Dimension(200, 50));
		highScoreRed.setHorizontalAlignment(JLabel.CENTER);
		highScoreRed.setFont(fonts.scienceFair[fonts.MEDIUM]);
		highScoreRed.setBackground(CustomColors.RED);
		highScoreRed.setForeground(CustomColors.WHITE);
		highScoreRed.setOpaque(true);
		highScoreRed.setText("High Score");
	}

	/**
	 * Makes the high score label for blue
	 */
	private void initializeHighScoreBlue() {
		highScoreBlue.setMinimumSize(new Dimension(200, 50));
		highScoreBlue.setMaximumSize(new Dimension(200, 50));
		highScoreBlue.setPreferredSize(new Dimension(200, 50));
		highScoreBlue.setHorizontalAlignment(JLabel.CENTER);
		highScoreBlue.setFont(fonts.scienceFair[fonts.MEDIUM]);
		highScoreBlue.setBackground(CustomColors.BLUE);
		highScoreBlue.setForeground(CustomColors.WHITE);
		highScoreBlue.setOpaque(true);
		highScoreBlue.setText("High Score");
	}

	/**
	 * Post the match results to the display
	 * 
	 * @param match
	 *            title
	 * @param redTeamInfo
	 *            {Label, Value1, Value2, Label, Value1, Value2, Label, Value1,
	 *            Value2}
	 * @param blueTeamInfo
	 *            {Label, Value1, Value2, Label, Value1, Value2, Label, Value1,
	 *            Value2}
	 * @param redScores
	 *            (Total, Balls, Balance, Fouls}
	 * @param blueScores
	 *            (Total, Balls, Balance, Fouls}
	 * @param redWon
	 *            match
	 * @param highScore
	 *            match
	 */
	public void postResults(String match, String[] redTeamInfo, String[] blueTeamInfo, int[] redScores,
			int[] blueScores, Boolean redWon, Boolean highScore) {
		matchLabel.setText(match);
		scoreDetailsRed.setDetails(redTeamInfo, Arrays.copyOfRange(redScores, 1, 4));
		scoreDetailsBlue.setDetails(blueTeamInfo, Arrays.copyOfRange(blueScores, 1, 4));
		scoreRedLabel.setText(String.valueOf(redScores[0]));
		scoreBlueLabel.setText(String.valueOf(blueScores[0]));
		if (redWon != null) {
			if (redWon) {
				highScoreRed.setVisible(highScore);
				highScoreBlue.setVisible(false);
				scoreWrapper.remove(0);
				scoreWrapper.add(winnerRed, 0);
				scoreWrapper.remove(5);
				scoreWrapper.add(winnerBlank(), 5);
			} else {
				highScoreRed.setVisible(false);
				highScoreBlue.setVisible(highScore);
				scoreWrapper.remove(0);
				scoreWrapper.add(winnerBlank(), 0);
				scoreWrapper.remove(5);
				scoreWrapper.add(winnerBlue, 5);
			}
		} else {
			highScoreRed.setVisible(highScore);
			highScoreBlue.setVisible(highScore);
			scoreWrapper.remove(0);
			scoreWrapper.add(winnerBlank(), 0);
			scoreWrapper.remove(5);
			scoreWrapper.add(winnerBlank(), 5);
		}
		this.repaint();
	}

	@Override
	public String toString() {
		return "Match Results";
	}
}
