package displays;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.Fonts;
import utils.SwingUtils;

class CardSwitcher {
	private Fonts fonts = Fonts.getInstance();

	private JFrame frame;
	private JPanel switcher;
	private ArrayList<JButton> buttons;

	public CardSwitcher(String[] cardNames, ActionListener listener) {
		switcher = new JPanel();
		buttons = new ArrayList<JButton>();

		for (int i = 0; i < cardNames.length; i++) {
			buttons.add(SwingUtils.button(cardNames[i], 0, 0, fonts.codeNewRoman[fonts.SMALL], listener));
			switcher.add(buttons.get(i));
		}

		frame = new JFrame();
		frame.setTitle("Switcher");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.add(switcher);
		frame.setVisible(true);
		frame.pack();
	}

	public void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	public void setButtonEnabled(JButton button) {
		for (JButton switchButton : buttons) {
			if (switchButton == button) {
				switchButton.setEnabled(false);
			} else {
				switchButton.setEnabled(true);
			}
		}
	}

	public void setButtonEnabled(String cardName) {
		for (JButton switchButton : buttons) {
			if (switchButton.getText().contains(cardName)) {
				switchButton.setEnabled(false);
			} else {
				switchButton.setEnabled(true);
			}
		}
	}
	
	public void repaint(){
		frame.repaint();
	}
}
