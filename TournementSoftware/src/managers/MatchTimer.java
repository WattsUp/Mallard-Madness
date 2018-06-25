package managers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import utils.CustomEvents;

public class MatchTimer implements ActionListener {
	private static MatchTimer instance;

	private ArrayList<ActionListener> actionListeners;

	private Timer timer;

	private static final int MAX_TIME = 16;
	private static final int WARNING_TIME = 15;
	private int timeLeft;
	private boolean isTimeout = false;

	public static MatchTimer getInstance() {
		if (instance == null) {
			instance = new MatchTimer();
		}
		return instance;
	}

	private MatchTimer() {
		timer = new Timer(1000, this);
		actionListeners = new ArrayList<ActionListener>();
	}

	public int getMaxTime() {
		return MAX_TIME;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			timeLeft--;
			Tournement.getMainDisplay().setTimerBar(timeLeft, WARNING_TIME, 0);
			if (timeLeft == WARNING_TIME) {
				endOfMatchWarning();
			}
			if (timeLeft == 0) {
				softStop();
			}
		} else if (e.getActionCommand().toLowerCase().contains("start")) {
			start();
		} else if (e.getActionCommand().toLowerCase().contains("stop")) {
			hardStop();
		} else {
			System.out.println(e.getActionCommand() + "not recognized");
		}
	}

	private void start() {
		if (!timer.isRunning()) {
			timeLeft = MAX_TIME;
			timer.restart();
			Tournement.getMainDisplay().setTimerBar(MAX_TIME, timeLeft, WARNING_TIME, 0);
			SoundManager.playSoundStart();
		}
	}

	private void start(int duration) {
		timeLeft = duration;
		timer.restart();
		Tournement.getMainDisplay().setTimerBar(duration, timeLeft, WARNING_TIME, 0);
	}

	private void softStop() {
		if (timer.isRunning()) {
			timer.stop();
			Tournement.getMainDisplay().setTimerBar(0, WARNING_TIME, 0);
			SoundManager.playSoundSoftStop();
			ActionEvent e = new ActionEvent(this, CustomEvents.EVENT_ID_MATCH_ENDED, "Match ended",
					System.currentTimeMillis(), 0);
			alertActionListeners(e);
			if (isTimeout) {
				isTimeout = false;
				Tournement.getMainDisplay().setTimeout(false);
			}
		}
	}

	private void endOfMatchWarning() {
		if (!isTimeout) {
			SoundManager.playSoundWarning();
		}
	}

	private void hardStop() {
		if (timer.isRunning()) {
			timer.stop();
			Tournement.getMainDisplay().setTimerBar(timeLeft, WARNING_TIME, timeLeft);
			if (isTimeout) {
				isTimeout = false;
				Tournement.getMainDisplay().setTimeout(false);
			} else {
				SoundManager.playSoundHardStop();
			}
		}
	}

	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	private void alertActionListeners(ActionEvent e) {
		for (ActionListener l : actionListeners) {
			l.actionPerformed(e);
		}
	}

	public void startTimeout(int duration) {
		if (!timer.isRunning()) {
			isTimeout = true;
			Tournement.getMainDisplay().setTimeout(true);
			start(duration);
		}
	}

}
