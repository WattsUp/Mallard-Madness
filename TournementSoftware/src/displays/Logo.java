package displays;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.SwingUtils;

@SuppressWarnings("serial")
class Logo extends BackgroundPanel {
	private BufferedImage logoImage;

	/**
	 * Makes a blank FHD panel with background and logo
	 */
	public Logo() {
		this.setLayout(null);
		try {
			logoImage = ImageIO.read(new File("images/Logo.png"));
			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(logoImage, 0);
			tracker.waitForAll();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		float aspect = (float)logoImage.getHeight() / (float)logoImage.getWidth();
		int width = this.getPreferredSize().width * 4 / 5;
		int height = (int) (width * aspect);
		height = Math.min(height, this.getPreferredSize().height * 4 /5);
		width = (int) (height / aspect);
		logoImage = SwingUtils.getScaledImage(logoImage, width, height);
		
	}

	/**
	 * Returns a string representation of this component
	 */
	@Override
	public String toString() {
		return "Logo";
	}

	/**
	 * Paints the logo in the center of the screen
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (logoImage != null) {
			int x = (this.getWidth() - logoImage.getWidth()) / 2;
			int y = (this.getHeight() - logoImage.getHeight()) / 2;
			g2d.drawImage(logoImage, x, y, null);
		}
	}

}
