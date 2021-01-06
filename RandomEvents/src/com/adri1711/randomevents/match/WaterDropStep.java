package com.adri1711.randomevents.match;

import org.bukkit.Location;

public class WaterDropStep {
	
	private String name;
	
	private Location spawn;
	
	private Location goalLoc1;
	
	private Location goalLoc2;

	public WaterDropStep() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getGoalLoc1() {
		return goalLoc1;
	}

	public void setGoalLoc1(Location goalLoc1) {
		this.goalLoc1 = goalLoc1;
	}

	public Location getGoalLoc2() {
		return goalLoc2;
	}

	public void setGoalLoc2(Location goalLoc2) {
		this.goalLoc2 = goalLoc2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goalLoc1 == null) ? 0 : goalLoc1.hashCode());
		result = prime * result + ((goalLoc2 == null) ? 0 : goalLoc2.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((spawn == null) ? 0 : spawn.hashCode());
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
		WaterDropStep other = (WaterDropStep) obj;
		if (goalLoc1 == null) {
			if (other.goalLoc1 != null)
				return false;
		} else if (!goalLoc1.equals(other.goalLoc1))
			return false;
		if (goalLoc2 == null) {
			if (other.goalLoc2 != null)
				return false;
		} else if (!goalLoc2.equals(other.goalLoc2))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (spawn == null) {
			if (other.spawn != null)
				return false;
		} else if (!spawn.equals(other.spawn))
			return false;
		return true;
	}
	
	

}
