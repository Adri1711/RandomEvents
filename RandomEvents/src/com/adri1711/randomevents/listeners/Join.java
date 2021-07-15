package com.adri1711.randomevents.listeners;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Join implements Listener {

	private RandomEvents plugin;

	public Join(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		UtilsRandomEvents.sacaInventario(plugin, player);
		if (plugin.getMatchActive() != null && plugin.getReventConfig().isForcePlayersToSpectate()) {

			if (plugin.getMatchActive().getPlaying()) {

				if (!UtilsRandomEvents.checkBanned(player, plugin)) {
					plugin.getMatchActive().uneAPlayerSpec(player);
				}

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
