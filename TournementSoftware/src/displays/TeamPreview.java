package displays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class TeamPreview extends JPanel {
	private Fonts fonts = Fonts.getInstance();

	private JPanel teamGrid;

	private JLabel title1;
	private JLabel title2;
	private JLabel title3;
	private JLabel team1Number;
	private JLabel team2Number;
	private JLabel team1Name;
	private JLabel team2Name;
	private JLabel team1Rank;
	private JLabel team2Rank;

	public TeamPreview(Color foreground) {
		this.setMinimumSize(new Dimension(1290, 300));
		this.setMaximumSize(new Dimension(1290, 300));
		this.setPreferredSize(new Dimension(1290, 300));
		this.setBackground(CustomColors.WHITE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		initializeTeamGrid();
		title1 = SwingUtils.label("Team #", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 150,
				55, JLabel.CENTER, BorderFactory.createEmptyBorder());
		title2 = SwingUtils.label("Team Name", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM],
				960, 55, JLabel.CENTER, BorderFactory.createEmptyBorder());
		title3 = SwingUtils.label("Rank", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 150, 55,
				JLabel.CENTER, BorderFactory.createEmptyBorder());
		team1Number = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE], 150, 75,
				JLabel.CENTER, BorderFactory.createEmptyBorder());
		team2Number = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE], 150, 75,
				JLabel.CENTER, BorderFactory.createEmptyBorder());
		team1Name = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE], 960, 75,
				JLabel.LEFT, BorderFactory.createEmptyBorder());
		team2Name = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE], 960, 75,
				JLabel.LEFT, BorderFactory.createEmptyBorder());
		team1Rank = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE], 150, 75,
				JLabel.CENTER, BorderFactory.createEmptyBorder());
		team2Rank = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.LARGE], 150, 75,
				JLabel.CENTER, BorderFactory.createEmptyBorder());

		teamGrid.add(title1);
		teamGrid.add(title2);
		teamGrid.add(title3);
		teamGrid.add(team1Number);
		teamGrid.add(team1Name);
		teamGrid.add(team1Rank);
		teamGrid.add(team2Number);
		teamGrid.add(team2Name);
		teamGrid.add(team2Rank);

		this.add(SwingUtils.rectangle(1290, 75, foreground));
		this.add(teamGrid);
	}

	private void initializeTeamGrid() {
		teamGrid = new JPanel();
		teamGrid.setMinimumSize(new Dimension(1290, 225));
		teamGrid.setMaximumSize(new Dimension(1290, 225));
		teamGrid.setPreferredSize(new Dimension(1290, 225));
		teamGrid.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		teamGrid.setBackground(CustomColors.CLEAR);
	}

	public void setTeamPreview(int team1, String team1Name, int team1Rank, int team2, String team2Name, int team2Rank) {
		title3.setText("Rank");
		this.team1Number.setText(String.format("%02d", team1));
		this.team2Number.setText(String.format("%02d", team2));
		this.team1Name.setText(team1Name);
		this.team2Name.setText(team2Name);
		this.team1Rank.setText(String.format("%02d", team1Rank));
		this.team2Rank.setText(String.format("%02d", team2Rank));
		this.repaint();
	}

	public void setTeamPreviewEliminations(int team1, String team1Name, int team1Alliance, int team2, String team2Name,
			int team2Alliance) {
		title3.setText("Alliance");
		this.team1Number.setText(String.format("%02d", team1));
		this.team2Number.setText(String.format("%02d", team2));
		this.team1Name.setText(team1Name);
		this.team2Name.setText(team2Name);
		this.team1Rank.setText(String.format("%02d", team1Alliance));
		this.team2Rank.setText(String.format("%02d", team2Alliance));
		this.repaint();
	}

}
