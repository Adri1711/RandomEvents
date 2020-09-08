package com.adri1711.randomevents.match;

import java.util.List;

import org.bukkit.Location;

public class Tournament {
	private Integer numberOfRounds;

	private Integer maxPlayers;

	private Integer minPlayers;

	private List<String> rewards;
	
	
	private Location playerSpawn;


	public Integer getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(Integer minPlayers) {
		this.minPlayers = minPlayers;
	}

	public List<String> getRewards() {
		return rewards;
	}

	public void setRewards(List<String> rewards) {
		this.rewards = rewards;
	}

	public Integer getNumberOfRounds() {
		return numberOfRounds;
	}

	public void setNumberOfRounds(Integer numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}

	public Integer getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public Location getPlayerSpawn() {
		return playerSpawn;
	}

	public void setPlayerSpawn(Location playerSpawn) {
		this.playerSpawn = playerSpawn;
	}
	
}
