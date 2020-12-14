package com.adri1711.randomevents.match;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryPers {

	private String gamemode;

	private ItemStack[] contents;

	private float totalExp;
	private int level;

	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;

	private Location lastLocation;

	public InventoryPers() {
		super();
		this.contents = new ItemStack[0];
	}

	public String getGamemode() {
		return gamemode;
	}

	public void setGamemode(String gamemode) {
		this.gamemode = gamemode;
	}

	public ItemStack[] getContents() {
		return contents;
	}

	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public void setLeggings(ItemStack leggings) {
		this.leggings = leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public float getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(float  f) {
		this.totalExp = f;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "InventoryPers [contents=" + Arrays.toString(contents) + ", helmet=" + helmet + ", chestplate="
				+ chestplate + ", leggings=" + leggings + ", boots=" + boots + ", lastLocation=" + lastLocation + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boots == null) ? 0 : boots.hashCode());
		result = prime * result + ((chestplate == null) ? 0 : chestplate.hashCode());
		result = prime * result + Arrays.hashCode(contents);
		result = prime * result + ((helmet == null) ? 0 : helmet.hashCode());
		result = prime * result + ((lastLocation == null) ? 0 : lastLocation.hashCode());
		result = prime * result + ((leggings == null) ? 0 : leggings.hashCode());
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
		InventoryPers other = (InventoryPers) obj;
		if (boots == null) {
			if (other.boots != null)
				return false;
		} else if (!boots.equals(other.boots))
			return false;
		if (chestplate == null) {
			if (other.chestplate != null)
				return false;
		} else if (!chestplate.equals(other.chestplate))
			return false;
		if (!Arrays.equals(contents, other.contents))
			return false;
		if (helmet == null) {
			if (other.helmet != null)
				return false;
		} else if (!helmet.equals(other.helmet))
			return false;
		if (lastLocation == null) {
			if (other.lastLocation != null)
				return false;
		} else if (!lastLocation.equals(other.lastLocation))
			return false;
		if (leggings == null) {
			if (other.leggings != null)
				return false;
		} else if (!leggings.equals(other.leggings))
			return false;
		return true;
	}

}
