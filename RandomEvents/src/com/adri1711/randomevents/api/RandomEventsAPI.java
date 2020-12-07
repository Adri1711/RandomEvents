package com.adri1711.randomevents.api;

import java.util.List;

import org.bukkit.Bukkit;

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

}
