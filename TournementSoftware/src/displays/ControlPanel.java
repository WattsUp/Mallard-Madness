package displays;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import managers.AllianceListener;
import managers.ControlPanelListener;
import managers.MatchManager;
import managers.MatchTimer;
import managers.ScoringListener;
import managers.TeamManager;
import managers.Tournement;
import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

class ControlPanel {
	private Fonts fonts = Fonts.getInstance();

	private ControlPanelListener cpListener;
	private ScoringListener scoringListener;
	private AllianceListener allianceListener;

	private JFrame frame;

	private JPanel mainWrapper;
	private JPanel scoringWrapper;
	private JPanel allianceWrapper;
	private JPanel timeoutWrapper;

	private JButton startMatch;
	private JButton resetMatch;
	private JButton stopMatch;
	private JButton loadNextMatch;
	private JButton saveMatch;
	private JButton allianceSelection;
	private JButton exitTournement;
	private ArrayList<JButton> allianceButtons;

	private JTextArea consoleOut;

	private JScrollPane consoleOutWrapper;

	private ArrayList<ScoringModule> scoringModules;

	public ControlPanel() {
		frame = new JFrame();

		frame.setTitle("Control Panel");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setAlwaysOnTop(true);

		cpListener = new ControlPanelListener();
		scoringListener = new ScoringListener();
		allianceListener = new AllianceListener();

		mainWrapper = new JPanel();
		scoringWrapper = new JPanel();
		allianceWrapper = new JPanel();
		timeoutWrapper = new JPanel();
		consoleOut = new JTextArea(150, 0);
		consoleOutWrapper = new JScrollPane(consoleOut);
		allianceButtons = new ArrayList<JButton>();

		mainWrapper.setPreferredSize(new Dimension(900, 1000));

		startMatch = SwingUtils.button("Start Match", 0, 0, fonts.terminal[fonts.MEDIUM], MatchTimer.getInstance(),
				cpListener);
		resetMatch = SwingUtils.button("Reset Match", 0, 0, fonts.terminal[fonts.MEDIUM], cpListener);
		stopMatch = SwingUtils.button("E-Stop Match", 0, 0, fonts.terminal[fonts.MEDIUM], MatchTimer.getInstance(),
				cpListener);
		loadNextMatch = SwingUtils.button("Load Next Match", 0, 0, fonts.terminal[fonts.MEDIUM], cpListener);
		saveMatch = SwingUtils.button("Save Match", 0, 0, fonts.terminal[fonts.MEDIUM], cpListener);
		allianceSelection = SwingUtils.button("Alliance Selection", 0, 0, fonts.terminal[fonts.MEDIUM], cpListener);
		exitTournement = SwingUtils.button("Close Program", 0, 0, fonts.terminal[fonts.SMALL], cpListener);

		scoringModules = new ArrayList<ScoringModule>();

		initializeScoringModules();
		initializeScoringWrapper();
		initializeConsoleOut();
		initializeAllianceButtons();
		initializeTimeoutWrapper();

		mainWrapper.add(startMatch);
		mainWrapper.add(resetMatch);
		mainWrapper.add(stopMatch);
		mainWrapper.add(loadNextMatch);
		mainWrapper.add(scoringWrapper);
		mainWrapper.add(saveMatch);
		mainWrapper.add(allianceSelection);
		mainWrapper.add(allianceWrapper);
		mainWrapper.add(consoleOutWrapper);
		mainWrapper.add(timeoutWrapper);
		mainWrapper.add(exitTournement);

		frame.add(mainWrapper);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		setAllianceButtonsEnabled(false);
		startMatch.setEnabled(false);
		resetMatch.setEnabled(false);
		stopMatch.setEnabled(false);
		loadNextMatch.setEnabled(true);
		saveMatch.setEnabled(false);
		allianceSelection.setEnabled(false);
		frame.repaint();
	}

	private void initializeScoringModules() {
		scoringModules.add(new ScoringModuleButton(0, "Red Flag Balls", CustomColors.RED, 127, 0, true));
		scoringModules.add(new ScoringModuleScroll(3, "Red Location", CustomColors.RED, 4, 2));
		scoringModules.add(new ScoringModuleButton(1, "White Flag Balls", CustomColors.BLACK, 127, 0, true));
		scoringModules.add(new ScoringModuleScroll(4, "White Location", CustomColors.BLACK, 4, 2));
		scoringModules.add(new ScoringModuleButton(2, "Blue Flag Balls", CustomColors.BLUE, 127, 0, true));
		scoringModules.add(new ScoringModuleScroll(5, "Blue Location", CustomColors.BLUE, 4, 2));

		scoringModules.add(new ScoringModuleButton(6, "Red Return Home", CustomColors.RED, 2, 0, false));
		scoringModules.add(new ScoringModuleButton(8, "Red Minor", CustomColors.RED, 127, 0, false));
		scoringModules.add(new ScoringModuleButton(10, "Red Major", CustomColors.RED, 127, 0, false));
		scoringModules.add(new ScoringModuleButton(7, "Blue Return Home", CustomColors.BLUE, 2, 0, false));
		scoringModules.add(new ScoringModuleButton(9, "Blue Minor", CustomColors.BLUE, 127, 0, false));
		scoringModules.add(new ScoringModuleButton(11, "Blue Major", CustomColors.BLUE, 127, 0, false));

		for (ScoringModule module : scoringModules) {
			module.addActionListener(scoringListener);
		}
	}

	private void initializeScoringWrapper() {
		scoringWrapper.setLayout(new GridLayout(0, 2));
		for (int i = 0; i < scoringModules.size() / 2; i++) {
			scoringWrapper.add(scoringModules.get(i));
			scoringWrapper.add(scoringModules.get(i + scoringModules.size() / 2));
		}
	}

	private void initializeTimeoutWrapper() {
		JTextField duration = new JTextField(5);
		JButton startTimeout = SwingUtils.button("Start Timeout", 0, 0, fonts.codeNewRoman[fonts.MEDIUM],
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String durationText = duration.getText();
						if (durationText.matches("[0-9]+")) {
							MatchTimer.getInstance().startTimeout(Integer.parseInt(durationText));
						} else {
							System.out.println("Timeout is not a number");
						}
					}

				});
		timeoutWrapper.add(SwingUtils.label("Timeout length:", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.codeNewRoman[fonts.MEDIUM], 0, 0, JButton.RIGHT, BorderFactory.createEmptyBorder()));
		timeoutWrapper.add(duration);
		timeoutWrapper.add(startTimeout);
	}

	public void resetScoringWrapper() {
		for (ScoringModule module : scoringModules) {
			module.resetCount();
		}
	}

	private void initializeConsoleOut() {
		consoleOutWrapper.setMinimumSize(new Dimension(750, 200));
		consoleOutWrapper.setMaximumSize(new Dimension(750, 200));
		consoleOutWrapper.setPreferredSize(new Dimension(750, 200));
		consoleOut.setEditable(false);
		consoleOut.setLineWrap(true);
		consoleOut.setFont(fonts.codeNewRoman[fonts.SMALL]);
		PrintStream printStream = new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				consoleOut.append(String.valueOf((char) b));
				if (consoleOut.getLineCount() > consoleOut.getRows()) {
					int numberOfLinesToTrunk = consoleOut.getLineCount() - consoleOut.getRows();
					try {
						int posOfLastLineToTrunk = consoleOut.getLineEndOffset(numberOfLinesToTrunk - 1);
						consoleOut.replaceRange("", 0, posOfLastLineToTrunk);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
				consoleOut.setCaretPosition(consoleOut.getDocument().getLength());
			}
		});
//		System.setOut(printStream);
		// System.setErr(printStream);
	}

	private void initializeAllianceButtons() {
		allianceWrapper.setLayout(new GridLayout(0, 8));
		for (int i = 0; i < 24; i++) {
			allianceButtons.add(SwingUtils.button("T#" + String.valueOf(i + 1), 64, 96, fonts.terminal[fonts.MEDIUM],
					allianceListener));
			allianceWrapper.add(allianceButtons.get(i));
		}
	}

	public void setAllianceButtonsEnabled(Boolean enabled) {
		for (JButton button : allianceButtons) {
			button.setEnabled(enabled);
		}
	}

	public void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	public int getWidth() {
		return frame.getWidth();
	}

	public void setEnabled(String button, boolean enabled) {
		button = button.toLowerCase();
		if (button.equals("start")) {
			startMatch.setEnabled(enabled);
		} else if (button.equals("reset")) {
			resetMatch.setEnabled(enabled);
		} else if (button.equals("stop")) {
			stopMatch.setEnabled(enabled);
		} else if (button.equals("next")) {
			loadNextMatch.setEnabled(enabled);
		} else if (button.equals("save")) {
			saveMatch.setEnabled(enabled);
		} else if (button.equals("alliancestart")) {
			allianceSelection.setEnabled(enabled);
		} else if (button.equals("allianceselections")) {
			setAllianceButtonsEnabled(enabled);
		}
	}

	public int getScoringPosition(ScoringModuleButton scoringModule) {
		return scoringModules.indexOf(scoringModule);
	}

	public void setTimeout(boolean enabled) {
		if (enabled) {
			Tournement.getMainDisplay().setMatchInfo("Timeout", 0, "", 0, 0, "", 0, 0, "", 0, 0, "", 0);
			setAllianceButtonsEnabled(false);
			startMatch.setEnabled(false);
			resetMatch.setEnabled(false);
			stopMatch.setEnabled(true);
			loadNextMatch.setEnabled(false);
			saveMatch.setEnabled(false);
			allianceSelection.setEnabled(false);
		} else {
			MatchManager matchManager = MatchManager.getInstance();
			TeamManager teamManager = TeamManager.getInstance();
			int[] teams = matchManager.getCurrentAlliance();

			Tournement.getMainDisplay().setMatchInfo(matchManager.getCurrentMatchLabel(), teams[0],
					teamManager.getTeam(teams[0]).getName(), teamManager.getTeam(teams[0]).getRank(), teams[1],
					teamManager.getTeam(teams[1]).getName(), teamManager.getTeam(teams[1]).getRank(), teams[2],
					teamManager.getTeam(teams[2]).getName(), teamManager.getTeam(teams[2]).getRank(), teams[3],
					teamManager.getTeam(teams[3]).getName(), teamManager.getTeam(teams[3]).getRank());
			setAllianceButtonsEnabled(false);
			startMatch.setEnabled(false);
			resetMatch.setEnabled(true);
			stopMatch.setEnabled(false);
			loadNextMatch.setEnabled(false);
			saveMatch.setEnabled(false);
			allianceSelection.setEnabled(false);
		}
	}

	public void addControlPanelEvent(ActionEvent e) {
		cpListener.actionPerformed(e);
	}

	public void addScoringModuleEvent(int moduleposition, ActionEvent e) {
		scoringModules.get(moduleposition).actionPerformed(e);
	}

	public void repaint() {
		frame.repaint();
	}
}
