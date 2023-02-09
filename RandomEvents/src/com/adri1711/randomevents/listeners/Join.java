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
		if (plugin.getReventConfig().isDebugMode()) {
			if (plugin.getMatchActive() != null) {
				plugin.getLogger().info("Match found: " + plugin.getMatchActive().getMatch().getName());
				plugin.getLogger().info("Players: ");
				for (String p : plugin.getMatchActive().getPlayerHandler().getPlayers()) {

					plugin.getLogger().info("    - " + p);

				}
				plugin.getLogger().info("Spectators: ");
				for (Player p : plugin.getMatchActive().getPlayerHandler().getPlayersSpectators()) {

					plugin.getLogger().info("    - " + p.getName());

				}
			} else {
				plugin.getLogger().info("No Match");

			}
		}
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
			if (plugin.getReventConfig().isDebugMode()) {
				plugin.getLogger().info("Detected player, kicking from match: "+player.getName());

			}
			
			plugin.getMatchActive().echaDePartida(player, true, true, true);
			
		} else {

			UtilsRandomEvents.sacaInventario(plugin, player);
		}
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
