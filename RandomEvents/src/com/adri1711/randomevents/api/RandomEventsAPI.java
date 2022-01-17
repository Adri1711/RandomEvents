package com.adri1711.randomevents.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;

public class RandomEventsAPI {

	private static RandomEvents plugin = (RandomEvents) Bukkit.getPluginManager().getPlugin("RandomEvents");

	public static List<Match> getMatches() {
		return plugin.getMatches();
	}

	public static MatchActive getMatch() {
		return plugin.getMatchActive();
	}

	public static List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		if (plugin.getMatchActive() != null) {
			players = plugin.getMatchActive().getPlayerHandler().getPlayersObj();
		}
		return players;

	}

	public static List<Player> getSpectators() {
		List<Player> players = new ArrayList<Player>();
		if (plugin.getMatchActive() != null) {
			players = plugin.getMatchActive().getPlayerHandler().getPlayersSpectators();
		}
		return players;

	}

	public static String getMinigame() {
		return plugin.getMatchActive() != null ? plugin.getMatchActive().getMatch().getMinigame().getCodigo() : null;
	}

	public static String getMatchName() {
		return plugin.getMatchActive() != null ? plugin.getMatchActive().getMatch().getName() : null;
	}
	

}
