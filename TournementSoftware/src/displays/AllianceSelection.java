package displays;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import managers.Team;
import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class AllianceSelection extends BackgroundPanel {
	private Fonts fonts = Fonts.getInstance();

	private JPanel mainWrapper;
	private AllianceGrid allianceGrid;
	private TeamList teamList;

	/**
	 * Makes a FHD panel with alliances and eligible teams
	 */
	public AllianceSelection() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

		mainWrapper = new JPanel();
		allianceGrid = new AllianceGrid();
		teamList = new TeamList();

		initializeMainWrapper();

		this.add(new JLabel(String.format("%500s", "")));
		this.add(mainWrapper);
		this.setVisible(true);
	}

	/**
	 * Makes the main wrapper with alliance grid and team list
	 */
	private void initializeMainWrapper() {
		mainWrapper.setMinimumSize(new Dimension(1400, 888));
		mainWrapper.setMaximumSize(new Dimension(1400, 888));
		mainWrapper.setPreferredSize(new Dimension(1400, 888));
		mainWrapper.setBorder(BorderFactory.createLineBorder(CustomColors.WHITE, 10));
		mainWrapper.setBackground(CustomColors.WHITE_TRANSPARENT);
		mainWrapper.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(5, 5, 40, 5);
		c.gridx = 0;
		c.gridy = 1;

		mainWrapper.add(allianceGrid, c);

		c.gridx = 1;
		mainWrapper.add(teamList, c);

		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		mainWrapper.add(SwingUtils.label("Alliance Selection", CustomColors.BLACK, CustomColors.CLEAR,
				fonts.scienceFair[fonts.LARGE], 0, 0, JLabel.CENTER, BorderFactory.createEmptyBorder()), c);
	}

	/**
	 * Sets details of a Team Detail
	 * @param team
	 * @param position
	 */
	public void setTeamDetail(Team team, int position) {
		teamList.setTeamDetail(team, position);
	}

	/**
	 * Sets a team in the Alliance Grid
	 * @param teamNumber
	 * @param position
	 */
	public void setAlliance(int teamNumber, int position) {
		allianceGrid.setAlliance(position, teamNumber);
	}

	/**
	 * Returns a string representation of this component
	 */
	@Override
	public String toString() {
		return "Alliance Selection";
	}

	public void refreshAvailableList(ArrayList<Team> availableTeams) {
		for(int i = 0; i < 12; i++){
			teamList.setTeamDetail(availableTeams.get(i), i);
		}
	}
}
