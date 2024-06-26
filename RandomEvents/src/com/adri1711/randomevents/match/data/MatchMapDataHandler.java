package com.adri1711.randomevents.match.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
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

	private Map<Location, Material> blockDisappearedType;

	private Map<Location, MaterialData> blockPlaced;

	private Set<Fireball> fireballs;

	private List<Location> blockPartyBlocks;

	private List<Location> locationsGlasses;
	
	private List<List<Location>> locationsPlatforms;

	public MatchMapDataHandler() {
		super();
		this.blockDisappear = new HashMap<Location, Long>();
		this.blockDisappeared = new HashMap<Location, MaterialData>();
		this.blockDisappearedType = new HashMap<Location, Material>();
		this.blockPlaced = new HashMap<Location, MaterialData>();
		this.checkpoints = new HashMap<String, Location>();
		this.chests = new ArrayList<Location>();
		this.fireballs = new HashSet<Fireball>();
		this.blockPartyBlocks = new ArrayList<Location>();
		this.locationsGlasses = new ArrayList<Location>();
		this.locationsPlatforms = new ArrayList<>();

	}

	public List<List<Location>> getLocationsPlatforms() {
		return locationsPlatforms;
	}

	public void setLocationsPlatforms(List<List<Location>> locationsPlatforms) {
		this.locationsPlatforms = locationsPlatforms;
	}

	public List<Location> getLocationsGlasses() {
		return locationsGlasses;
	}

	public void setLocationsGlasses(List<Location> locationsGlasses) {
		this.locationsGlasses = locationsGlasses;
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

	public Map<Location, Material> getBlockDisappearedType() {
		return blockDisappearedType;
	}

	public void setBlockDisappearedType(Map<Location, Material> blockDisappearedType) {
		this.blockDisappearedType = blockDisappearedType;
	}

	public Map<Location, MaterialData> getBlockPlaced() {
		return blockPlaced;
	}

	public void setBlockPlaced(Map<Location, MaterialData> blockPlaced) {
		this.blockPlaced = blockPlaced;
	}

	public void setBlockPartyBlocks(List<Location> blocksLocation) {
		this.blockPartyBlocks = new ArrayList<Location>(blocksLocation);

	}

	public List<Location> getBlockPartyBlocks() {
		return blockPartyBlocks;
	}

}
