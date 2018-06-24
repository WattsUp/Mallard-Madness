package displays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class ScoreDetails extends JPanel {
	private Fonts fonts = Fonts.getInstance();

	private JLabel pointsFlags;
	private JLabel pointsHome;
	private JLabel pointsFouls;
	private JLabel header;
	private JLabel infoTeamHeaderFirst;
	private JLabel infoTeam1First;
	private JLabel infoTeam2First;
	private JLabel infoTeamHeaderSecond;
	private JLabel infoTeam1Second;
	private JLabel infoTeam2Second;
	private JLabel infoTeamHeaderThird;
	private JLabel infoTeam1Third;
	private JLabel infoTeam2Third;

	private JPanel infoTeamWrapper;

	/**
	 * This makes a panel with score breakdowns and team info
	 * 
	 * @param color
	 *            of accent
	 * @param isRed
	 *            of alliance
	 */
	public ScoreDetails(Color color, Boolean isRed) {
		this.setMinimumSize(new Dimension(600, 550));
		this.setMaximumSize(new Dimension(600, 550));
		this.setPreferredSize(new Dimension(600, 550));
		this.setBackground(CustomColors.WHITE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

		header = new JLabel();
		infoTeamWrapper = new JPanel();

		pointsFlags = SwingUtils.label("0", color, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 100, 65,
				JLabel.LEFT, BorderFactory.createEmptyBorder());
		pointsHome = SwingUtils.label("0", color, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 100, 65,
				JLabel.LEFT, BorderFactory.createEmptyBorder());
		pointsFouls = SwingUtils.label("0", color, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 100, 65,
				JLabel.LEFT, BorderFactory.createEmptyBorder());

		int infoCellWidth = 175;
		int infoCellHeight = 60;
		infoTeamHeaderFirst = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE],
				infoCellWidth, infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder());
		infoTeam1First = SwingUtils.label("", CustomColors.WHITE, color, fonts.terminal[fonts.LARGE], infoCellWidth,
				infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		infoTeam2First = SwingUtils.label("", CustomColors.WHITE, color, fonts.terminal[fonts.LARGE], infoCellWidth,
				infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		infoTeamHeaderSecond = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE],
				infoCellWidth, infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder());
		infoTeam1Second = SwingUtils.label("", CustomColors.WHITE, color, fonts.terminal[fonts.LARGE], infoCellWidth,
				infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		infoTeam2Second = SwingUtils.label("", CustomColors.WHITE, color, fonts.terminal[fonts.LARGE], infoCellWidth,
				infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		infoTeamHeaderThird = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE],
				infoCellWidth, infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder());
		infoTeam1Third = SwingUtils.label("", CustomColors.WHITE, color, fonts.terminal[fonts.LARGE], infoCellWidth,
				infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder(3, 3, 3, 3));
		infoTeam2Third = SwingUtils.label("", CustomColors.WHITE, color, fonts.terminal[fonts.LARGE], infoCellWidth,
				infoCellHeight, JLabel.CENTER, BorderFactory.createEmptyBorder(3, 3, 3, 3));

		initializeHeader(color);

		infoTeamWrapper.setLayout(new GridLayout(0, 3, 5, 8));
		infoTeamWrapper.setBackground(CustomColors.CLEAR);
		infoTeamWrapper.add(infoTeamHeaderFirst);
		infoTeamWrapper.add(infoTeamHeaderSecond);
		infoTeamWrapper.add(infoTeamHeaderThird);
		infoTeamWrapper.add(infoTeam1First);
		infoTeamWrapper.add(infoTeam1Second);
		infoTeamWrapper.add(infoTeam1Third);
		infoTeamWrapper.add(infoTeam2First);
		infoTeamWrapper.add(infoTeam2Second);
		infoTeamWrapper.add(infoTeam2Third);

		this.add(header);
		this.add(new JLabel(String.format("%100s", "")));
		this.add(infoTeamWrapper);
		this.add(new JLabel(String.format("%100s", "")));
		this.add(new JLabel(String.format("%100s", "")));
		this.add(SwingUtils.label("Flags & Balls:", color, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 400, 65,
				JLabel.RIGHT, BorderFactory.createEmptyBorder()));
		this.add(pointsFlags);
		this.add(SwingUtils.label("Return Home:", color, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 400, 65,
				JLabel.RIGHT, BorderFactory.createEmptyBorder()));
		this.add(pointsHome);
		this.add(SwingUtils.label((isRed) ? "Blue Penalty:" : "Red Penalty:", color, CustomColors.CLEAR,
				fonts.terminal[fonts.X_LARGE], 400, 65, JLabel.RIGHT, BorderFactory.createEmptyBorder()));
		this.add(pointsFouls);
	}

	/**
	 * Makes the color bar header
	 * 
	 * @param color
	 */
	private void initializeHeader(Color color) {
		header.setMinimumSize(new Dimension(600, 75));
		header.setMaximumSize(new Dimension(600, 75));
		header.setPreferredSize(new Dimension(600, 75));
		header.setBackground(color);
		header.setOpaque(true);
	}

	/**
	 * Sets the details displayed
	 * 
	 * @param teamInfo
	 *            {Label, Value1, Value2, Label, Value1, Value2, Label, Value1,
	 *            Value2}
	 * @param scores
	 *            {Flags, Home, Fouls}
	 */
	public void setDetails(String[] teamInfo, int[] scores) {
		pointsFlags.setText(String.valueOf(scores[0]));
		pointsHome.setText(String.valueOf(scores[1]));
		pointsFouls.setText(String.valueOf(scores[2]));
		infoTeamHeaderFirst.setText(teamInfo[0]);
		infoTeam1First.setText(teamInfo[1]);
		infoTeam2First.setText(teamInfo[2]);
		infoTeamHeaderSecond.setText(teamInfo[3]);
		infoTeam1Second.setText(teamInfo[4]);
		infoTeam2Second.setText(teamInfo[5]);
		infoTeamHeaderThird.setText(teamInfo[6]);
		infoTeam1Third.setText(teamInfo[7]);
		infoTeam2Third.setText(teamInfo[8]);
	}
}
