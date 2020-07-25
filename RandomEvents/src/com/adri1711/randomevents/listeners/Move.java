package com.adri1711.randomevents.listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Move implements Listener {

	private RandomEvents plugin;

	public Move(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlaying() && plugin.getMatchActive().getPlayersObj().contains(player)) {

			switch (plugin.getMatchActive().getMatch().getMinigame()) {
			case BOAT_RUN:

				if (plugin.getMatchActive().getCuboid().contains(player.getLocation())) {

					plugin.getMatchActive().setPlayerContador(player);
					plugin.getMatchActive().daRecompensas(false);
				}
				break;
			default:
				break;
			}
		}

	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

}
