package com.adri1711.randomeventsquests.objectives;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.adri1711.randomevents.api.events.ReventBeginEvent;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;

public class ParticipateReventObjective extends CustomObjective implements Listener {
	Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");

	public ParticipateReventObjective() {
		this.setName("Participate RandomEvents Objective");
		this.setAuthor("adri1711");
		this.setShowCount(true);
		this.setCountPrompt("Enter the number of joins that the player must acquire:");
		this.setDisplay("RandomEvents Joins: %count%");
	}

	@EventHandler
	public void onWinRandomEvent(ReventBeginEvent evt) {
		if (evt.getMatchActive().getPlayerHandler().getPlayersObj() != null && !evt.getMatchActive().getPlayerHandler().getPlayersObj().isEmpty()) {
			for (Player p : evt.getMatchActive().getPlayerHandler().getPlayersObj()) {
				for (Quest quest : qp.getQuester(p.getUniqueId()).getCurrentQuests().keySet()) {
					incrementObjective(p, this, 1, quest);
				}
			}
		}
	}
}
