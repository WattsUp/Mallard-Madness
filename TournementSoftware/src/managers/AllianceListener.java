package managers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AllianceListener implements ActionListener {

	private String[] confirmationOptions = { "They accepted", "Remove", "Do nothing" };

	public AllianceListener() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int teamNumber = Integer.parseInt(e.getActionCommand().replace("T#", ""));
		int response = JOptionPane.showOptionDialog((Component) e.getSource(),
				"How did team " + teamNumber + " respond?", "AllianceConfirmation", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, confirmationOptions, confirmationOptions[2]);
		if (response == JOptionPane.YES_OPTION) {
			AllianceManager.getInstance().selectNextTeam(teamNumber);
			((JButton) e.getSource()).setEnabled(false);
		} else if (response == JOptionPane.NO_OPTION) {
			AllianceManager.getInstance().removeTeam(teamNumber);
			((JButton) e.getSource()).setEnabled(false);
		} else {
			//Do nothing
		}
	}

}
