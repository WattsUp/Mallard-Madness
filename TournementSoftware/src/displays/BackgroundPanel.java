package displays;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class BackgroundPanel extends JPanel {
	private BufferedImage backgroundImage;

	/**
	 * Makes a FHD panel with a background image
	 */
	public BackgroundPanel() {
		try {
			backgroundImage = ImageIO.read(new File("images/backgroundYellow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1920, 1080);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (backgroundImage != null) {
			g2d.drawImage(backgroundImage, 0, 0, null);
		}
	}

	@Override
	public String toString() {
		return "Blank Panel";
	}

}
