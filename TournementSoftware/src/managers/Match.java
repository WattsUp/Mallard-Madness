package managers;

public class Match {
	private int number = 0;
	private int[] teams = new int[4];
	
	public Match(int matchNumber, int red1, int red2, int blue1, int blue2){
		this.number = matchNumber;
		teams[0] = red1;
		teams[1] = red2;
		teams[2] = blue1;
		teams[3] = blue2;
	}
	
	public int getNumber(){
		return number;
	}
	
	public int[] getTeams(){
		return teams;
	}
}
