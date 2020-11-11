package com.adri1711.randomevents.match;

import java.util.HashMap;
import java.util.Map;

public class BannedPlayers {
	private Map<String, Long> bannedPlayers;

	public BannedPlayers() {
		super();
		this.bannedPlayers = new HashMap<String, Long>();
	}

	public Map<String, Long> getBannedPlayers() {
		return bannedPlayers;
	}

	public void setBannedPlayers(Map<String, Long> bannedPlayers) {
		this.bannedPlayers = bannedPlayers;
	}

}
