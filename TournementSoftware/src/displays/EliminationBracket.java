package displays;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class EliminationBracket extends BackgroundPanel {
	private Fonts fonts = Fonts.getInstance();

	private JPanel mainWrapper;
	private BracketWrapper bracketWrapper;

	private EliminationMatch semifinalOneMatch;
	private EliminationMatch semifinalTwoMatch;
	private EliminationMatch finalMatch;

	private BufferedImage logoImage;

	/**
	 * Makes a FHD panel with a double elimination four alliance bracket
	 */
	public EliminationBracket() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

		mainWrapper = new JPanel();
		bracketWrapper = new BracketWrapper();
		semifinalOneMatch = new EliminationMatch("Semifinal 1", fonts.scienceFair[fonts.X_LARGE]);
		semifinalTwoMatch = new EliminationMatch("Semifinal 2", fonts.scienceFair[fonts.X_LARGE]);
		finalMatch = new EliminationMatch("Finals", fonts.scienceFair[fonts.XX_LARGE]);

		initializeMainWrapper();
		initializeBracketWrapper();
		initializeLogo();

		mainWrapper.add(bracketWrapper);

		this.add(new JLabel(String.format("%500s", "")));
		this.add(mainWrapper);
		this.setVisible(true);
	}

	/**
	 * Makes the main wrapper with title
	 */
	private void initializeMainWrapper() {
		mainWrapper.setMinimumSize(new Dimension(1400, 888));
		mainWrapper.setMaximumSize(new Dimension(1400, 888));
		mainWrapper.setPreferredSize(new Dimension(1400, 888));
		mainWrapper.setBorder(BorderFactory.createLineBorder(CustomColors.WHITE, 10));
		mainWrapper.setBackground(CustomColors.WHITE_TRANSPARENT);
		mainWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainWrapper.add(SwingUtils.label("Elimination Bracket", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.scienceFair[fonts.LARGE], 0, 0, JLabel.CENTER, BorderFactory.createEmptyBorder()));
	}

	/**
	 * Makes the wrapper for the matches
	 */
	private void initializeBracketWrapper() {
		int height = 700;
		bracketWrapper.setMinimumSize(new Dimension(1400, height));
		bracketWrapper.setMaximumSize(new Dimension(1400, height));
		bracketWrapper.setPreferredSize(new Dimension(1400, height));
		bracketWrapper.setBorder(BorderFactory.createEmptyBorder());
		bracketWrapper.setBackground(CustomColors.CLEAR);
		bracketWrapper.setLayout(new GridLayout(2, 3));
		bracketWrapper.add(new JLabel());
		bracketWrapper.add(finalMatch);
		bracketWrapper.add(new JLabel());
		bracketWrapper.add(semifinalOneMatch);
		bracketWrapper.add(new JLabel());
		bracketWrapper.add(semifinalTwoMatch);
	}

	/**
	 * Wraps the bracket matches and draws bracket connections
	 * 
	 * @author Bradley
	 *
	 */
	private class BracketWrapper extends JPanel {

		/**
		 * Makes a bracket wrapper
		 */
		BracketWrapper() {
			super();
		}

		/**
		 * Paints the bracket connections
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			int gap = 20;

			int line1X1 = semifinalOneMatch.getRightX() + gap;
			int line1X2 = semifinalTwoMatch.getLeftX() - gap;
			int line1Y = semifinalOneMatch.getCenterY();

			int line2X = finalMatch.getCenterX();
			int line2Y1 = finalMatch.getBottomY() + gap;
			int line2Y2 = semifinalOneMatch.getCenterY();

			g2d.setPaint(CustomColors.WHITE);
			g2d.setStroke(new BasicStroke(15));
			g2d.drawLine(line1X1, line1Y, line1X2, line1Y);
			g2d.drawLine(line2X, line2Y1, line2X, line2Y2);

			int imageWidth = 279;
			int imageHeight = 329;
			int imageX = line2X - imageWidth / 2;
			int imageY = line1Y - imageHeight / 2;
			g2d.drawImage(logoImage, imageX, imageY, null);
		}
	}

	/**
	 * Loads the logo image
	 */
	private void initializeLogo() {
		try {
			logoImage = ImageIO.read(new File("images/LogoSimple.png"));
			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(logoImage, 0);
			tracker.waitForAll();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the outline of team to winner
	 * 
	 * @param alliance
	 */
	public void setWinner(int round, int alliance) {
		switch (alliance) {
		case 0:
			if (round == 2) {
				semifinalOneMatch.setWinner(0);
			} else if (round == 1) {
				finalMatch.setWinner(0);

			}
			break;
		case 1:
			if (round == 2) {
				semifinalTwoMatch.setWinner(0);

			} else if (round == 1) {
				finalMatch.setWinner(1);

			}
			break;
		case 2:
			semifinalTwoMatch.setWinner(1);
			break;
		case 3:
			semifinalOneMatch.setWinner(1);
			break;
		}
		this.repaint();
	}

	/**
	 * Sets the teams in the matches
	 * 
	 * @param match
	 * @param teams
	 */
	public void setTeam(int match, int[] redAlliance, int[] blueAlliance) {
		if (match == 0) {
			semifinalOneMatch.setTeams(redAlliance, blueAlliance);
		} else if (match == 1) {
			semifinalTwoMatch.setTeams(redAlliance, blueAlliance);
		} else if (match == 2) {
			finalMatch.setTeams(redAlliance, blueAlliance);
		}
		this.repaint();
	}

	/**
	 * Returns a string representation of this component
	 */
	@Override
	public String toString() {
		return "Elimination Bracket";
	}

}
