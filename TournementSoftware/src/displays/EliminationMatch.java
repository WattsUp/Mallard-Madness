package displays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class EliminationMatch extends JPanel {
	private Fonts fonts = Fonts.getInstance();

	private EliminationTeam red = new EliminationTeam(CustomColors.RED);
	private EliminationTeam blue = new EliminationTeam(CustomColors.BLUE);

	/**
	 * Makes a match with title, red alliance, and blue alliance
	 * 
	 * @param name
	 * @param titleFont
	 */
	public EliminationMatch(String name, Font titleFont) {
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(CustomColors.CLEAR);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.add(SwingUtils.label(name, CustomColors.BLACK, CustomColors.CLEAR, titleFont, 0, 0, JLabel.CENTER,
				BorderFactory.createEmptyBorder()));
		this.add(red);
		this.add(blue);

	}

	/**
	 * An alliance display with a color label, and team numbers
	 * 
	 * @author Bradley
	 *
	 */
	private class EliminationTeam extends JPanel {
		JLabel colorSquare;
		JLabel team1;
		JLabel team2;
		int winCount = 0;
		private final static int borderWeight = 6;

		/**
		 * Makes an alliance display with a color label, and team numbers
		 * 
		 * @param color
		 */
		EliminationTeam(Color color) {
			this.setMinimumSize(new Dimension(400, 80));
			this.setMaximumSize(new Dimension(400, 80));
			this.setPreferredSize(new Dimension(400, 80));
			this.setBorder(BorderFactory.createEmptyBorder(borderWeight, borderWeight, borderWeight, borderWeight));
			this.setBackground(CustomColors.WHITE);
			this.setForeground(CustomColors.GREEN);
			this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

			colorSquare = SwingUtils.label("", CustomColors.CLEAR, color, fonts.scienceFair[fonts.LARGE],
					80 - borderWeight * 2, 80 - borderWeight * 2, JLabel.CENTER, BorderFactory.createEmptyBorder());
			team1 = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 160,
					80 - borderWeight * 2, JLabel.CENTER, BorderFactory.createEmptyBorder());
			team2 = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.X_LARGE], 160,
					80 - borderWeight * 2, JLabel.CENTER, BorderFactory.createEmptyBorder());

			this.add(colorSquare);
			this.add(team1);
			this.add(team2);
		}

		/**
		 * Sets outline to winner status
		 */
		public void setWinner() {
			winCount++;
			if (winCount == 1) {
				this.setBorder(BorderFactory.createDashedBorder(null, borderWeight, 1.83f, 2.5f, false));
			} else if (winCount == 2) {
				this.setBorder(BorderFactory.createLineBorder(CustomColors.GREEN, borderWeight));
			}
			this.repaint();
		}

		/**
		 * Sets team numbers
		 * 
		 * @param teamNumbers
		 */
		public void setTeams(int[] teamNumbers) {
			team1.setText(teamNumbers[0] == 0 ? "" : String.valueOf(teamNumbers[0]));
			team2.setText(teamNumbers[1] == 0 ? "" : String.valueOf(teamNumbers[1]));
			this.repaint();
		}
	}

	/**
	 * Sets outline of alliance to winner status
	 * 
	 * @param alliance
	 *            that won
	 */
	public void setWinner(int alliance) {
		if (alliance == 0) {
			red.setWinner();
		} else if (alliance == 1) {
			blue.setWinner();
		}
		this.repaint();
	}

	/**
	 * 
	 * @return the center vertical position
	 */
	public int getCenterY() {
		int y = this.getY();
		y += red.getY();
		y += red.getHeight();
		y += 5;
		return y;
	}

	/**
	 * 
	 * @return the center horizontal position
	 */
	public int getCenterX() {
		int x = this.getX();
		x += this.getWidth() / 2;
		return x;
	}

	/**
	 * 
	 * @return the left edge position
	 */
	public int getLeftX() {
		int x = this.getX();
		x += red.getX();
		return x;
	}

	/**
	 * 
	 * @return the right edge position
	 */
	public int getRightX() {
		int x = red.getX();
		x += red.getWidth();
		return x;
	}

	/**
	 * 
	 * @return the bottom edge position
	 */
	public int getBottomY() {
		int y = this.getY();
		y += blue.getY();
		y += blue.getHeight();
		return y;
	}

	/**
	 * Sets team numbers
	 * 
	 * @param teamsRed
	 * @param teamsBlue
	 */
	public void setTeams(int[] teamsRed, int[] teamsBlue) {
		red.setTeams(teamsRed);
		blue.setTeams(teamsBlue);
	}
}
