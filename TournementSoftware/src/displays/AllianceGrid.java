package displays;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class AllianceGrid extends JPanel {
	private Fonts fonts = Fonts.getInstance();
	ArrayList<JLabel> teams = new ArrayList<JLabel>();

	/**
	 * Makes a grid of 4 alliances with two teams each
	 */
	public AllianceGrid() {
		this.setMinimumSize(new Dimension(420, 300));
		this.setMaximumSize(new Dimension(420, 300));
		this.setPreferredSize(new Dimension(420, 300));
		this.setBackground(CustomColors.WHITE);
		this.setLayout(new GridLayout(5, 3, 2, 2));

		initializeTeamLabels();

		this.add(SwingUtils.label("Alliance", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0,
				0, JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(SwingUtils.label("Captain", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
				JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(SwingUtils.label("Partner", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
				JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(SwingUtils.label("1", CustomColors.GREY, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
				JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(teams.get(0));
		this.add(teams.get(1));
		this.add(SwingUtils.label("2", CustomColors.GREY, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
				JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(teams.get(2));
		this.add(teams.get(3));
		this.add(SwingUtils.label("3", CustomColors.GREY, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
				JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(teams.get(4));
		this.add(teams.get(5));
		this.add(SwingUtils.label("4", CustomColors.GREY, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
				JLabel.CENTER, BorderFactory.createEmptyBorder()));
		this.add(teams.get(6));
		this.add(teams.get(7));
	}

	/**
	 * Makes 8 team labels and adds them to ArrayList
	 */
	private void initializeTeamLabels() {
		for (int i = 0; i < 8; i++) {
			teams.add(SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0, 0,
					JLabel.CENTER, BorderFactory.createEmptyBorder()));
		}
	}

	/**
	 * Sets a team label with team number
	 * 
	 * @param position
	 *            of team
	 * @param teamNumber
	 */
	public void setAlliance(int position, int teamNumber) {
		teams.get(position).setText(String.valueOf(teamNumber));
		this.repaint();
	}
}
