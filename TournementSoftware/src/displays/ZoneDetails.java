package displays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.CustomColors;
import utils.Fonts;
import utils.SwingUtils;

@SuppressWarnings("serial")
class ZoneDetails extends JPanel {
	private Fonts fonts = Fonts.getInstance();
	
	private Flag flagRed;
	private Flag flagWhite;
	private Flag flagBlue;
	private JPanel flagRedWrapper;
	private JPanel flagWhiteWrapper;
	private JPanel flagBlueWrapper;
	
	public ZoneDetails(Color color) {
		super();
		
		flagRed		= new Flag(CustomColors.RED);
		flagWhite	= new Flag(CustomColors.WHITE);
		flagBlue	= new Flag(CustomColors.BLUE);
		
		flagRedWrapper = new JPanel();
		flagWhiteWrapper = new JPanel();
		flagBlueWrapper = new JPanel();
		
		flagRedWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		flagRedWrapper.setPreferredSize(new Dimension(118, 40));
		flagWhiteWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		flagWhiteWrapper.setPreferredSize(new Dimension(118, 40));
		flagBlueWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		flagBlueWrapper.setPreferredSize(new Dimension(118, 40));
		
		flagRedWrapper.add(flagRed);
		flagWhiteWrapper.add(flagWhite);
		flagBlueWrapper.add(flagBlue);
		
		flagRedWrapper.setBackground(CustomColors.CLEAR);
		flagWhiteWrapper.setBackground(CustomColors.CLEAR);
		flagBlueWrapper.setBackground(CustomColors.CLEAR);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(118, 128));
		this.setBackground(color);
		this.add(flagRedWrapper);
		this.add(flagWhiteWrapper);
		this.add(flagBlueWrapper);
		this.setVisible(true);
	}
	
	class Flag extends JPanel {
		JLabel ballCount = SwingUtils.label("0", CustomColors.WHITE, CustomColors.CLEAR, fonts.terminal[fonts.MEDIUM], 0,
				0, JLabel.CENTER, BorderFactory.createEmptyBorder());
		
		Flag(Color color) {
			super();
			
			
			
			this.setBackground(CustomColors.GREEN);
			this.setLayout(new FlowLayout(FlowLayout.LEADING, 7, 0));
			this.setPreferredSize(new Dimension(90, 36));
			this.add(SwingUtils.rectangle(30, 25, color));
			this.add(ballCount);
		}
		
		void setCount(int count){
			ballCount.setText(String.format("%d", count));
		}
	}
	
	/**
	 * This changes the score label
	 * 
	 * @param flagDetails {redCount, whiteCount, blueCount}
	 */
	public void setFlags(int[] flagDetails){
		if(flagDetails[0] == -1){	//red not present
			flagRed.setVisible(false);
		} else {
			flagRed.setVisible(true);
			flagRed.setCount(flagDetails[0]);
		}
		if(flagDetails[1] == -1){	//white not present
			flagWhite.setVisible(false);
		} else {
			flagWhite.setVisible(true);
			flagWhite.setCount(flagDetails[1]);
		}
		if(flagDetails[2] == -1){	//blue not present
			flagBlue.setVisible(false);
		} else {
			flagBlue.setVisible(true);
			flagBlue.setCount(flagDetails[2]);
		}
	}
}