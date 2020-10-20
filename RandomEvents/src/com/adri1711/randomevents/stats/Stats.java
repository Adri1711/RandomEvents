package com.adri1711.randomevents.stats;

import java.util.HashMap;
import java.util.Map;

public class Stats {

	private Map<String, Integer> tries;
	private Map<String, Integer> wins;

	public Stats() {
		super();
		this.tries = new HashMap<String, Integer>();
		this.wins = new HashMap<String, Integer>();
	}

	public void addTries(String game, Integer numberOfTries) {
		if (tries.containsKey(game)) {
			tries.put(game, tries.get(game) + numberOfTries);
		} else {
			tries.put(game, numberOfTries);
		}
	}

	public void addWins(String game, Integer numberOfWins) {
		if (wins.containsKey(game)) {
			wins.put(game, wins.get(game) + numberOfWins);
		} else {
			wins.put(game, numberOfWins);
		}
	}

	public Integer getTries(String game) {
		Integer resultado = 0;
		if (tries.containsKey(game)) {
			resultado = tries.get(game);
		}
		return resultado;
	}

	public Integer getWins(String game) {
		Integer resultado = 0;
		if (wins.containsKey(game)) {
			resultado = wins.get(game);
		}
		return resultado;
	}

	public Map<String, Integer> getTries() {
		return tries;
	}

	public void setTries(Map<String, Integer> tries) {
		this.tries = tries;
	}

	public Map<String, Integer> getWins() {
		return wins;
	}

	public void setWins(Map<String, Integer> wins) {
		this.wins = wins;
	}

}
