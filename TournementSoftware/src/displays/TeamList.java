package displays;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import managers.Team;
import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class TeamList extends JPanel {
	private Fonts fonts = Fonts.getInstance();
	private ArrayList<TeamDetail> teamDetails;
	private TeamDetail header;

	/**
	 * Makes a list of teams sorted by rank
	 */
	public TeamList() {
		this.setMinimumSize(new Dimension(780, 730));
		this.setMaximumSize(new Dimension(780, 730));
		this.setPreferredSize(new Dimension(780, 730));
		this.setBackground(CustomColors.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		teamDetails = new ArrayList<TeamDetail>();
		header = new TeamDetail();
		header.setDetails("RANK", "#", "NAME", "AVG");

		initializeTeamDetails();

		this.add(header);
		this.add(SwingUtils.rectangle(780, 5, CustomColors.BLUE_LIGHT));

		Boolean first = true;
		for (TeamDetail teamDetail : teamDetails) {
			if (!first) {
				this.add(new JSeparator());
			}
			this.add(teamDetail);
			first = false;
		}

	}

	/**
	 * A panel with rank, team number, team name, and average score
	 * @author Bradley
	 *
	 */
	private class TeamDetail extends JPanel {
		JLabel rankLabel;
		JLabel numberLabel;
		JLabel nameLabel;
		JLabel averageScoreLabel;

		/**
		 * Makes the team detail panel
		 */
		TeamDetail() {
			this.setMinimumSize(new Dimension(780, 50));
			this.setMaximumSize(new Dimension(780, 50));
			this.setPreferredSize(new Dimension(780, 50));
			this.setBackground(CustomColors.CLEAR);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setAlignmentY(Component.BOTTOM_ALIGNMENT);

			rankLabel = SwingUtils.label("", CustomColors.GREY, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 110,
					50, JLabel.CENTER, BorderFactory.createEmptyBorder(0, 5, 0, 0));
			numberLabel = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 60,
					50, JLabel.LEFT, BorderFactory.createEmptyBorder());
			nameLabel = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 480,
					50, JLabel.LEFT, BorderFactory.createEmptyBorder());
			averageScoreLabel = SwingUtils.label("", CustomColors.BLACK, CustomColors.CLEAR,
					fonts.terminal[fonts.MEDIUM], 128, 50, JLabel.RIGHT, BorderFactory.createEmptyBorder());

			this.add(rankLabel);
			this.add(numberLabel);
			this.add(nameLabel);
			this.add(averageScoreLabel);
		}

		/**
		 * Sets the information displayed in Team Detail
		 * @param rank
		 * @param teamNumber
		 * @param name
		 * @param averageScore
		 */
		public void setDetails(int rank, int teamNumber, String name, float averageScore) {
			rankLabel.setText(String.format("%02d", rank));
			numberLabel.setText(String.format("%02d", teamNumber));
			nameLabel.setText(name);
			averageScoreLabel.setText(String.format("%5.2f", averageScore));
			this.repaint();
		}
		
		/**
		 * Sets the information displayed in Team Detail
		 * @param rank
		 * @param teamNumber
		 * @param name
		 * @param averageScore
		 */
		public void setDetails(String rank, String teamNumber, String name, String averageScore) {
			rankLabel.setText(rank);
			numberLabel.setText(teamNumber);
			nameLabel.setText(name);
			averageScoreLabel.setText(averageScore);
			this.repaint();
		}
		
		/**
		 * Clears the information displayed in Team Detail
		 */
		public void clearDetails() {
			rankLabel.setText("");
			numberLabel.setText("");
			nameLabel.setText("");
			averageScoreLabel.setText("");
			this.repaint();
		}

	}

	/**
	 * Makes 12 empty team detail and adds them to ArrayList
	 */
	private void initializeTeamDetails() {
		for (int i = 0; i < 12; i++) {
			teamDetails.add(new TeamDetail());
		}
	}

	/**
	 * Sets the details of a TeamDetail
	 * @param team
	 * @param position
	 */
	public void setTeamDetail(Team team, int position) {
		if(team != null){
			teamDetails.get(position).setDetails(team.getRank(), team.getNumber(), team.getName(), team.getAverageScore());	
		}else{
			teamDetails.get(position).clearDetails();
		}
		
		this.repaint();
	}
}
