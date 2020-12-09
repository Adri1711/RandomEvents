package com.adri1711.randomeventsquests.objectives;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.adri1711.randomevents.api.events.ReventEndEvent;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;

public class WinReventObjective extends CustomObjective implements Listener {
	Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");

	public WinReventObjective() {
		this.setName("Win RandomEvents Objective");
		this.setAuthor("adri1711");
		this.setShowCount(true);
		this.setCountPrompt("Enter the number of wins that the player must acquire:");
		this.setDisplay("RandomEvents Wins: %count%");
	}

	@EventHandler
	public void onWinRandomEvent(ReventEndEvent evt) {
		if (evt.getWinners() != null
				&& !evt.getWinners().isEmpty()) {
			for (Player p : evt.getWinners()) {
				for (Quest quest : qp.getQuester(p.getUniqueId()).getCurrentQuests().keySet()) {
					incrementObjective(p, this, 1, quest);
				}
			}
		}
	}
}
