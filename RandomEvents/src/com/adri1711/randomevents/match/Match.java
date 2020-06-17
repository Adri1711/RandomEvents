package com.adri1711.randomevents.match;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Match {

	private String name;

	private ItemStack[] inventario;
	
	private Integer amountPlayers;
	
	private Integer amountPlayersMin;
	
	private Location playerSpawn;
	
	private MinigameType minigame;
	
	private List<Location> spawns;
	
	private List<Location> mobsSpawn;
	
	private Location eventSpawn;
	
	private List<String> rewards;


	public Match() {
		super();
		this.inventario=new ItemStack[0];
		this.rewards = new ArrayList<String>();
		this.spawns=new ArrayList<Location>();
		this.mobsSpawn=new ArrayList<Location>();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getAmountPlayers() {
		return amountPlayers;
	}


	public void setAmountPlayers(Integer amountPlayers) {
		this.amountPlayers = amountPlayers;
	}


	public Integer getAmountPlayersMin() {
		return amountPlayersMin;
	}


	public void setAmountPlayersMin(Integer amountPlayersMin) {
		this.amountPlayersMin = amountPlayersMin;
	}


	public Location getPlayerSpawn() {
		return playerSpawn;
	}


	public void setPlayerSpawn(Location playerSpawn) {
		this.playerSpawn = playerSpawn;
	}


	public MinigameType getMinigame() {
		return minigame;
	}


	public void setMinigame(MinigameType minigame) {
		this.minigame = minigame;
	}


	public List<Location> getSpawns() {
		return spawns;
	}


	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}


	public List<Location> getMobsSpawn() {
		return mobsSpawn;
	}


	public void setMobsSpawn(List<Location> mobsSpawn) {
		this.mobsSpawn = mobsSpawn;
	}


	public Location getEventSpawn() {
		return eventSpawn;
	}


	public void setEventSpawn(Location eventSpawn) {
		this.eventSpawn = eventSpawn;
	}


	public List<String> getRewards() {
		return rewards;
	}


	public void setRewards(List<String> rewards) {
		this.rewards = rewards;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountPlayers == null) ? 0 : amountPlayers.hashCode());
		result = prime * result + ((amountPlayersMin == null) ? 0 : amountPlayersMin.hashCode());
		result = prime * result + ((eventSpawn == null) ? 0 : eventSpawn.hashCode());
		result = prime * result + ((minigame == null) ? 0 : minigame.hashCode());
		result = prime * result + ((mobsSpawn == null) ? 0 : mobsSpawn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((playerSpawn == null) ? 0 : playerSpawn.hashCode());
		result = prime * result + ((rewards == null) ? 0 : rewards.hashCode());
		result = prime * result + ((spawns == null) ? 0 : spawns.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (amountPlayers == null) {
			if (other.amountPlayers != null)
				return false;
		} else if (!amountPlayers.equals(other.amountPlayers))
			return false;
		if (amountPlayersMin == null) {
			if (other.amountPlayersMin != null)
				return false;
		} else if (!amountPlayersMin.equals(other.amountPlayersMin))
			return false;
		if (eventSpawn == null) {
			if (other.eventSpawn != null)
				return false;
		} else if (!eventSpawn.equals(other.eventSpawn))
			return false;
		if (minigame != other.minigame)
			return false;
		if (mobsSpawn == null) {
			if (other.mobsSpawn != null)
				return false;
		} else if (!mobsSpawn.equals(other.mobsSpawn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playerSpawn == null) {
			if (other.playerSpawn != null)
				return false;
		} else if (!playerSpawn.equals(other.playerSpawn))
			return false;
		if (rewards == null) {
			if (other.rewards != null)
				return false;
		} else if (!rewards.equals(other.rewards))
			return false;
		if (spawns == null) {
			if (other.spawns != null)
				return false;
		} else if (!spawns.equals(other.spawns))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Match [name=" + name + ", amountPlayers=" + amountPlayers + ", amountPlayersMin=" + amountPlayersMin
				+ ", playerSpawn=" + playerSpawn + ", minigame=" + minigame + ", spawns=" + spawns + ", mobsSpawn="
				+ mobsSpawn + ", eventSpawn=" + eventSpawn + ", rewards=" + rewards + "]";
	}


	public ItemStack[] getInventario() {
		return inventario;
	}


	public void setInventario(ItemStack[] inventario) {
		this.inventario = inventario;
	}
	
	
	
	


	
	

}
