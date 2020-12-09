package com.adri1711.randomevents.api.events;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.adri1711.randomevents.match.MatchActive;

public class ReventEndEvent extends Event {

	private final MatchActive matchActive;
	
	private final List<Player> winners;

	public ReventEndEvent(MatchActive matchActive, List<Player> ganadores) {
		super();
		this.matchActive = matchActive;
		this.winners=ganadores;
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

	public List<Player> getWinners() {
		return winners;
	}

}