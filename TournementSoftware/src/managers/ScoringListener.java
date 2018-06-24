package managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoringListener implements ActionListener {

	public ScoringListener() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int position = e.getModifiers() >> 8;
		int count = e.getModifiers() & 0b1111111;
		ScoringManager.getInstance().setCounts(position, count);
		Tournement.getWebServer().reformatWebpage();
	}

}
