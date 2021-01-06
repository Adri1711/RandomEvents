package com.adri1711.randomevents.match;

import org.bukkit.Location;

import com.adri1711.randomevents.match.utils.Cuboid;

public class WaterDropStepActive {

	private String name;

	private Location spawn;

	private Cuboid goal;

	public WaterDropStepActive(WaterDropStep wd) {
		super();
		this.name = wd.getName();
		this.spawn = wd.getSpawn();
		this.goal = new Cuboid(wd.getGoalLoc1(), wd.getGoalLoc2());
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

	public Cuboid getGoal() {
		return goal;
	}

	public void setGoal(Cuboid goal) {
		this.goal = goal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goal == null) ? 0 : goal.hashCode());
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
		WaterDropStepActive other = (WaterDropStepActive) obj;
		if (goal == null) {
			if (other.goal != null)
				return false;
		} else if (!goal.equals(other.goal))
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
