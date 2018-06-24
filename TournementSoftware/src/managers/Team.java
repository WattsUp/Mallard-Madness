package managers;

import java.util.ArrayList;

public class Team {
	private int number = 0;
	private int rank = 0;
	private String name;
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private ArrayList<Integer> ducklings = new ArrayList<Integer>();
	private ArrayList<Integer> mallards = new ArrayList<Integer>();
	private ArrayList<Integer> fouls = new ArrayList<Integer>();

	public Team(int number, String name) {
		this.number = number;
		this.name = name;
	}

	//(Total, Flags, Home, Fouls)
	public void addScore(int[] score) {
		scores.add(score[0]);
		ducklings.add(score[1]);
		mallards.add(score[2]);
		fouls.add(score[3]);
	}

	public int getNumber() {
		return number;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public float getAverageScore() {
		if(scores.size() == 0){
			return 0;
		}
		float sum = 0;
		for (int score : scores) {
			sum += score;
		}
		return sum / scores.size();
	}
	
	public float getAverageDuckling() {
		if(ducklings.size() == 0){
			return 0;
		}
		float sum = 0;
		for (int flag : ducklings) {
			sum += flag;
		}
		return sum / ducklings.size();
	}
	
	public float getAverageMallard() {
		if(mallards.size() == 0){
			return 0;
		}
		float sum = 0;
		for (int home : mallards) {
			sum += home;
		}
		return sum / mallards.size();
	}
	
	public float getAverageFouls() {
		if(fouls.size() == 0){
			return 0;
		}
		float sum = 0;
		for (int foul : fouls) {
			sum += foul;
		}
		return sum / fouls.size();
	}

	public String getName() {
		return name;
	}

	public int getMaxScore() {
		int max = 0;
		for (int score : scores) {
			max = Math.max(max, score);
		}
		return max;
	}

}
