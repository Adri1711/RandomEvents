package com.adri1711.randomevents.exception;

public class InvalidWorldException extends Exception
{
	private final String world;

	public InvalidWorldException(final String world)
	{
		super("InvalidWorld");
		this.world = world;
	}

	public String getWorld()
	{
		return this.world;
	}
}
