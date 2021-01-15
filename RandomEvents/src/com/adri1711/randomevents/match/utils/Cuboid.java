package com.adri1711.randomevents.match.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Cuboid {
	private final World world;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;

	public Cuboid(Location loc1, Location loc2) {
		this(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(),
				loc2.getZ());
	}
	
	public Cuboid(Cuboid cube) {
		this(cube.getWorld(), cube.getMaxX(), cube.getMaxY(), cube.getMaxZ(), cube.getMinX(), cube.getMinY(),
				cube.getMinZ());
	}

	public Cuboid(World world, double x1, double y1, double z1, double x2, double y2, double z2) {
		this.world = world;

		minX = Math.min(x1, x2);
		minY = Math.min(y1, y2);
		minZ = Math.min(z1, z2);
		maxX = Math.max(x1, x2);
		maxY = Math.max(y1, y2);
		maxZ = Math.max(z1, z2);
	}

	public World getWorld() {
		return world;
	}

	public double getMinX() {
		return minX;
	}

	public double getMinY() {
		return minY;
	}

	public double getMinZ() {
		return minZ;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public boolean contains(Cuboid cuboid) {
		return cuboid.getWorld().equals(world) && cuboid.getMinX() >= minX && cuboid.getMaxX() <= maxX
				&& cuboid.getMinY() >= minY && cuboid.getMaxY() <= maxY && cuboid.getMinZ() >= minZ
				&& cuboid.getMaxZ() <= maxZ;
	}

	public boolean contains(Location location) {
		return contains(location.getX(), location.getY(), location.getZ());
	}

	public boolean contains(double x, double y, double z) {
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}

	public boolean overlaps(Cuboid cuboid) {
		return cuboid.getWorld().equals(world)
				&& !(cuboid.getMinX() > maxX || cuboid.getMinY() > maxY || cuboid.getMinZ() > maxZ
						|| minZ > cuboid.getMaxX() || minY > cuboid.getMaxY() || minZ > cuboid.getMaxZ());
	}

	public void shrink(Double percen) {
		Double difX = (maxX - minX);
		Double difZ = (maxZ - minZ);
		difX = Double.valueOf((difX / 2) * percen);
		difZ = Double.valueOf((difZ / 2) * percen);
		maxX -= difX;
		maxZ -= difZ;
		minX += difX;
		minZ += difZ;
	}
	
	public void withdraw(Double blocks) {
		
		maxX -= blocks;
		maxZ -= blocks;
		minX += blocks;
		minZ += blocks;
	}

	public Location getCenter() {
		Double difX = (maxX - minX);
		Double difY = (maxY - minY);
		Double difZ = (maxZ - minZ);
		difX = Double.valueOf((difX / 2));
		difY = Double.valueOf((difY / 2));
		difZ = Double.valueOf((difZ / 2));
		return new Location(world, maxX - difX, maxY - difY, maxZ - difZ);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Cuboid)) {
			return false;
		}
		final Cuboid other = (Cuboid) obj;
		return world.equals(other.world) && minX == other.minX && minY == other.minY && minZ == other.minZ
				&& maxX == other.maxX && maxY == other.maxY && maxZ == other.maxZ;
	}

	@Override
	public String toString() {
		return "Cuboid[world:" + world.getName() + ", minX:" + minX + ", minY:" + minY + ", minZ:" + minZ + ", maxX:"
				+ maxX + ", maxY:" + maxY + ", maxZ:" + maxZ + "]";
	}
}