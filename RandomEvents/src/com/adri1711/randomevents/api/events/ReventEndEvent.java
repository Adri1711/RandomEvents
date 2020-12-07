package com.adri1711.randomevents.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.adri1711.randomevents.match.MatchActive;

public class ReventEndEvent extends Event {

	private final MatchActive matchActive;

	public ReventEndEvent(MatchActive matchActive) {
		super();
		this.matchActive = matchActive;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public MatchActive getMatchActive() {
		return matchActive;
	}

}