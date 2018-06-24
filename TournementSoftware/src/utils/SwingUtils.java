package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class SwingUtils {
	/**
	 * This makes a label with parameters
	 * 
	 * @param text
	 *            to display
	 * @param foreground
	 *            color
	 * @param background
	 *            color
	 * @param font
	 *            to display text in
	 * @param width
	 *            of label
	 * @param height
	 *            of label
	 * @param horizontalAlignment
	 *            of text
	 * @param border
	 * @return the label
	 */
	public static JLabel label(String text, Color foreground, Color background, Font font, int width, int height,
			int horizontalAlignment, Border border) {
		JLabel label = new JLabel(text);
		if (width > 0 && height > 0) {
			label.setMinimumSize(new Dimension(width, height));
			label.setMaximumSize(new Dimension(width, height));
			label.setPreferredSize(new Dimension(width, height));
		}
		label.setHorizontalAlignment(horizontalAlignment);
		label.setFont(font);
		label.setBorder(border);
		label.setBackground(background);
		label.setForeground(foreground);
		label.setOpaque(true);
		return label;
	}

	/**
	 * Makes a wrapper with a solid background and the content aligned to the
	 * bottom
	 * 
	 * @param content
	 *            to add to wrapper
	 * @param width
	 *            of wrapper
	 * @param height
	 *            of wrapper
	 * @param chromaKeyColor
	 *            of background
	 * @return wrapper panel
	 */
	public static JPanel chromaKeyWrapper(Component content, int width, int height, Color chromaKeyColor) {
		@SuppressWarnings("serial")
		JPanel panel = new JPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(width, height);
			}

			@Override
			public String toString() {
				return content.toString();
			}
		};
		panel.setLayout(null);
		panel.setBackground(chromaKeyColor);
		panel.add(content);
		Dimension size = content.getPreferredSize();
		content.setBounds(0, height - size.height, size.width, size.height);
		return panel;
	}

	/**
	 * Makes a solid background rectangle
	 * 
	 * @param width
	 * @param height
	 * @param color
	 *            of background
	 * @return rectangle
	 */
	public static JPanel rectangle(int width, int height, Color color) {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(width, height));
		panel.setMaximumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setBackground(color);
		return panel;
	}

	/**
	 * Makes a button with parameters
	 * 
	 * @param label
	 * @param height
	 * @param width
	 * @param font
	 * @param listeners
	 * @return button
	 */
	public static JButton button(String label, int height, int width, Font font, ActionListener... listeners) {
		JButton button = new JButton(label);
		if (height > 0 && width > 0) {
			button.setMinimumSize(new Dimension(width, height));
			button.setMaximumSize(new Dimension(width, height));
			button.setPreferredSize(new Dimension(width, height));
		}
		button.setFont(font);
		for (int i = 0; i < listeners.length; i++) {
			button.addActionListener(listeners[i]);
		}
		return button;
	}

	/**
	 * Resizes an image using a Graphics2D object backed by a BufferedImage.
	 * 
	 * @param srcImg
	 *            - source image to scale
	 * @param w
	 *            - desired width
	 * @param h
	 *            - desired height
	 * @return - the new resized image
	 */
	public static BufferedImage getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.drawImage(srcImg.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
		g2.dispose();
		return resizedImg;
	}
	
	public static void setTextFit(JLabel label, String text) {
	    Font originalFont = (Font)label.getClientProperty("originalfont"); // Get the original Font from client properties
	    if (originalFont == null) { // First time we call it: add it
	        originalFont = label.getFont();
	        label.putClientProperty("originalfont", originalFont);
	    }

	    int stringWidth = label.getFontMetrics(originalFont).stringWidth(text);
	    int componentWidth = label.getWidth();

	    if (stringWidth > componentWidth) { // Resize only if needed
	        // Find out how much the font can shrink in width.
	        double widthRatio = (double)componentWidth / (double)stringWidth;

	        int newFontSize = (int)Math.floor(originalFont.getSize() * widthRatio); // Keep the minimum size

	        // Set the label's font size to the newly determined size.
	        label.setFont(new Font(originalFont.getName(), originalFont.getStyle(), newFontSize));
	    } else
	        label.setFont(originalFont); // Text fits, do not change font size

	    label.setText(text);
	}
}
