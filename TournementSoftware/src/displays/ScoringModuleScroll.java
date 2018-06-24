package displays;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.CustomColors;
import utils.CustomEvents;
import utils.Fonts;

@SuppressWarnings("serial")
class ScoringModuleScroll extends ScoringModule implements ChangeListener {
	private Fonts fonts = Fonts.getInstance();

	private JLabel label;
	private JTextField field;
	private JSlider slider;
	private int count;
	private int maxCount;
	private int defaultCount;
	private int position;
	private ArrayList<ActionListener> actionListeners;

	/**
	 * Makes a scoring module with label, counter, and up/down buttons
	 * 
	 * @param text
	 *            of label
	 * @param color
	 *            of background
	 */
	public ScoringModuleScroll(int position, String text, Color color, int maxCount, int defaultCount) {
		super(position, text, color, maxCount, defaultCount);

		this.position = position;
		this.maxCount = maxCount;
		this.defaultCount = defaultCount;

		this.setLayout(new FlowLayout());
		this.setBackground(color);

		actionListeners = new ArrayList<ActionListener>();

		label = new JLabel(String.format("%15s", text));
		label.setForeground(CustomColors.WHITE);
		label.setFont(fonts.terminal[fonts.MEDIUM]);

		field = new JTextField(3);
		field.setFont(fonts.terminal[fonts.MEDIUM]);
		field.setEditable(false);

		slider = new JSlider(JSlider.HORIZONTAL, 0, maxCount, defaultCount);
		slider.addChangeListener(this);
		slider.setFont(fonts.terminal[fonts.MEDIUM]);
		slider.setInverted(true);

		this.add(label);
		this.add(field);
		this.add(slider);
		this.setVisible(true);
	}

	/**
	 * Sets the counter
	 * 
	 * @param count
	 */
	private void setCount(int count) {
		this.count = Math.max(count, 0);
		this.count = Math.min(this.count, maxCount);
		field.setText(String.valueOf(this.count));
		slider.setValue(count);
		this.repaint();
	}

	public void resetCount() {
		setCount(defaultCount);
	}

	/**
	 * Adds an action listener to be notified
	 * 
	 * @param l
	 */
	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		setCount(slider.getValue());
		int modifier = position << 8 | count;
		ActionEvent event = new ActionEvent(this, CustomEvents.EVENT_ID_SCORING_CHANGED,
				String.format("Score module %s changed count", label.getText()), System.currentTimeMillis(), modifier);
		for (ActionListener l : actionListeners) {
			l.actionPerformed(event);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e != null) {
			count = Integer.parseInt(e.getActionCommand());
			slider.setValue(count);
		}
		int modifier = position << 8 | count;
		ActionEvent event = new ActionEvent(this, CustomEvents.EVENT_ID_SCORING_CHANGED,
				String.format("Score module %s changed count", label.getText()), System.currentTimeMillis(), modifier);
		for (ActionListener l : actionListeners) {
			l.actionPerformed(event);
		}
	}
}
