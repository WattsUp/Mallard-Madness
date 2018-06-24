package displays;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
abstract class ScoringModule extends JPanel {
	private ArrayList<ActionListener> actionListeners;

	/**
	 * Makes a scoring module with label, counter, and up/down buttons
	 * 
	 * @param text
	 *            of label
	 * @param color
	 *            of background
	 */
	public ScoringModule(int position, String text, Color color, int maxCount, int defaultCount) {
		super();
	}

	public void resetCount() {
	}

	/**
	 * Adds an action listener to be notified
	 * 
	 * @param l
	 */
	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	public void actionPerformed(ActionEvent e) {
	}
}
