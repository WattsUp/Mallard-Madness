package displays;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
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
class Congratulations extends BackgroundPanel {
	private Fonts fonts = Fonts.getInstance();

	private MainWrapper mainWrapper;
	private JPanel logoHolder;

	private JLabel captainLabel;
	private JLabel partnerLabel;

	private BufferedImage logoImage;

	public Congratulations() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

		mainWrapper = new MainWrapper();
		logoHolder = SwingUtils.rectangle(1000, 250, CustomColors.CLEAR);

		captainLabel = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.scienceFair[fonts.XXX_LARGE],
				1200, 120, JLabel.CENTER, BorderFactory.createEmptyBorder());
		partnerLabel = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.scienceFair[fonts.XXX_LARGE],
				1200, 120, JLabel.CENTER, BorderFactory.createEmptyBorder());

		initializeMainWrapper();
		initializeLogo();

		mainWrapper.add(SwingUtils.label("Congratulations", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.terminal[fonts.X_LARGE], 1200, 120, JLabel.CENTER, BorderFactory.createEmptyBorder()));
		mainWrapper.add(logoHolder);
		mainWrapper.add(SwingUtils.rectangle(1200, 30, CustomColors.CLEAR));
		mainWrapper.add(captainLabel);
		mainWrapper.add(partnerLabel);

		this.add(new JLabel(String.format("%500s", "")));
		this.add(mainWrapper);
		this.setVisible(true);
	}

	private class MainWrapper extends JPanel {

		public MainWrapper() {
			super();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			float aspect = (float)logoImage.getWidth() / (float)logoImage.getHeight();
			int imageHeight = (int) (logoHolder.getHeight());
			int imageWidth = (int) (imageHeight * aspect);
			logoImage = SwingUtils.getScaledImage(logoImage, imageWidth, imageHeight);
			int imageX = logoHolder.getX();
			int imageY = logoHolder.getY();
			g2d.drawImage(logoImage, imageX, imageY, null);
		}

	}

	/**
	 * Loads the logo image
	 */
	private void initializeLogo() {
		try {
			logoImage = ImageIO.read(new File("images/2017GameLogo.png"));
			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(logoImage, 0);
			tracker.waitForAll();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void initializeMainWrapper() {
		mainWrapper.setMinimumSize(new Dimension(1400, 888));
		mainWrapper.setMaximumSize(new Dimension(1400, 888));
		mainWrapper.setPreferredSize(new Dimension(1400, 888));
		mainWrapper.setBorder(BorderFactory.createLineBorder(CustomColors.WHITE, 10));
		mainWrapper.setBackground(CustomColors.WHITE_TRANSPARENT);
		mainWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	}

	/**
	 * Returns a string representation of this component
	 */
	@Override
	public String toString() {
		return "Congratulations";
	}

	public void setWinner(String captainName, String partnerName) {
		System.out.println("Changing winners");
		captainLabel.setText(captainName);
		partnerLabel.setText(partnerName);
		SwingUtils.setTextFit(captainLabel, captainName);
		SwingUtils.setTextFit(partnerLabel, partnerName);
		this.repaint();
	}

}
