package com.adri1711.randomevents.match.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.material.MaterialData;

import com.adri1711.randomevents.match.utils.Cuboid;

public class MatchMapDataHandler {

	private Map<String, Location> checkpoints;

	private List<Location> chests;

	private Cuboid cuboid;

	private Cuboid actualCuboid;

	private Map<Location, Long> blockDisappear;

	private Map<Location, MaterialData> blockDisappeared;

	private Map<Location, MaterialData> blockPlaced;

	private Set<Fireball> fireballs;

	public MatchMapDataHandler() {
		super();
		this.blockDisappear = new HashMap<Location, Long>();
		this.blockDisappeared = new HashMap<Location, MaterialData>();
		this.blockPlaced = new HashMap<Location, MaterialData>();
		this.checkpoints = new HashMap<String, Location>();
		this.chests = new ArrayList<Location>();
		this.fireballs = new HashSet<Fireball>();

	}

	public Set<Fireball> getFireballs() {
		return fireballs;
	}

	public void setFireballs(Set<Fireball> fireballs) {
		this.fireballs = fireballs;
	}

	public Map<String, Location> getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(Map<String, Location> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public List<Location> getChests() {
		return chests;
	}

	public void setChests(List<Location> chests) {
		this.chests = chests;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public void setCuboid(Cuboid cuboid) {
		this.cuboid = cuboid;
	}

	public Cuboid getActualCuboid() {
		return actualCuboid;
	}

	public void setActualCuboid(Cuboid actualCuboid) {
		this.actualCuboid = actualCuboid;
	}

	public Map<Location, Long> getBlockDisappear() {
		return blockDisappear;
	}

	public void setBlockDisappear(Map<Location, Long> blockDisappear) {
		this.blockDisappear = blockDisappear;
	}

	public Map<Location, MaterialData> getBlockDisappeared() {
		return blockDisappeared;
	}

	public void setBlockDisappeared(Map<Location, MaterialData> blockDisappeared) {
		this.blockDisappeared = blockDisappeared;
	}

	public Map<Location, MaterialData> getBlockPlaced() {
		return blockPlaced;
	}

	public void setBlockPlaced(Map<Location, MaterialData> blockPlaced) {
		this.blockPlaced = blockPlaced;
	}

}
