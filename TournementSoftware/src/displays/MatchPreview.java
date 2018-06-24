package displays;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class MatchPreview extends BackgroundPanel {
	private Fonts fonts = Fonts.getInstance();

	private JPanel mainWrapper;

	private TeamPreview redPreview;
	private TeamPreview bluePreview;

	private JLabel matchLabel;

	/**
	 * Makes a FHD panel with alliances and eligible teams
	 */
	public MatchPreview() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

		mainWrapper = new JPanel();

		redPreview = new TeamPreview(CustomColors.RED);
		bluePreview = new TeamPreview(CustomColors.BLUE);

		matchLabel = SwingUtils.label("Qualification #45", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.scienceFair[fonts.X_LARGE], 0, 0, JLabel.CENTER, BorderFactory.createEmptyBorder(7, 100, 0, 100));

		initializeMainWrapper();

		mainWrapper.add(matchLabel);
		mainWrapper.add(SwingUtils.rectangle(888, 18, CustomColors.CLEAR));
		mainWrapper.add(redPreview);
		mainWrapper.add(SwingUtils.rectangle(888, 40, CustomColors.CLEAR));
		mainWrapper.add(bluePreview);
		mainWrapper.add(SwingUtils.rectangle(888, 25, CustomColors.CLEAR));
		mainWrapper.add(SwingUtils.label("Match Preview", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.scienceFair[fonts.XX_LARGE], 0, 0, JLabel.CENTER, BorderFactory.createEmptyBorder()));

		this.add(new JLabel(String.format("%500s", "")));
		this.add(mainWrapper);
		this.setVisible(true);
	}

	private void initializeMainWrapper() {
		mainWrapper.setMinimumSize(new Dimension(1400, 888));
		mainWrapper.setMaximumSize(new Dimension(1400, 888));
		mainWrapper.setPreferredSize(new Dimension(1400, 888));
		mainWrapper.setBorder(BorderFactory.createLineBorder(CustomColors.WHITE, 10));
		mainWrapper.setBackground(CustomColors.WHITE_TRANSPARENT);
		mainWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
	}

	public void setMatchPreview(String matchTitle, int team1, String team1Name, int team1Rank, int team2,
			String team2Name, int team2Rank, int team3, String team3Name, int team3Rank, int team4, String team4Name,
			int team4Rank) {
		matchLabel.setText(matchTitle);
		redPreview.setTeamPreview(team1, team1Name, team1Rank, team2, team2Name, team2Rank);
		bluePreview.setTeamPreview(team3, team3Name, team3Rank, team4, team4Name, team4Rank);
		this.repaint();
	}

	public void setMatchPreviewEliminations(String matchTitle, int team1, String team1Name, int team1Alliiance,
			int team2, String team2Name, int team2Alliance, int team3, String team3Name, int team3Alliance, int team4,
			String team4Name, int team4Alliance) {
		matchLabel.setText(matchTitle);
		redPreview.setTeamPreviewEliminations(team1, team1Name, team1Alliiance, team2, team2Name, team2Alliance);
		bluePreview.setTeamPreviewEliminations(team3, team3Name, team3Alliance, team4, team4Name, team4Alliance);
		this.repaint();
	}

	/**
	 * Returns a string representation of this component
	 */
	@Override
	public String toString() {
		return "Match Preview";
	}
}
