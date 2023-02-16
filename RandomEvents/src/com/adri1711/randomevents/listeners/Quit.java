package com.adri1711.randomevents.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Quit implements Listener {

	private RandomEvents plugin;

	public Quit(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDisconnect(PlayerQuitEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
			UtilsRandomEvents.borraInventario(player, plugin);
			plugin.getMatchActive().outKoth(player);
			plugin.getMatchActive().echaDePartida(player, true, false, true);
		}

	}

	@EventHandler
	public void onExit(VehicleExitEvent e) {

		if (e.getVehicle() instanceof Horse || e.getVehicle() instanceof Boat) {

			if (e.getExited() instanceof Player) {
				Player p = (Player) e.getExited();

				if (plugin.getMatchActive() != null
						&& plugin.getMatchActive().getPlayerHandler().getPlayersObj().contains(p)) {
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case BATTLE_ROYALE_CABALLO:
					case BOAT_RUN:
					case HORSE_RUN:

						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

							@Override
							public void run() {
								if (e.getVehicle().getPassenger() == null)
									e.getVehicle().setPassenger(e.getExited());

							}

						}, 2);
						break;
					default:
						break;
					}

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
