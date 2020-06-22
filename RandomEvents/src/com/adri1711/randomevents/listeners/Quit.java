package com.adri1711.randomevents.listeners;


import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.MinigameType;
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
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
			UtilsRandomEvents.borraInventario(player);
			plugin.getMatchActive().echaDePartida(player,true,false);
		}

	}

	@EventHandler
	public void onExit(VehicleExitEvent e) {
		if (e.getVehicle() instanceof Horse) {
			Horse horse = (Horse) e.getVehicle();
//			if (horse.getPassengers() instanceof Player) {
//				Player p = (Player) horse.getPassengers();
//				if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(p.getName())) {
//					if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
//							.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
//						e.setCancelled(true);
//					}
//				}
//			}else 
//				
				if(e.getExited() instanceof Player){
				Player p = (Player) e.getExited();
				if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(p.getName())) {
					if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
							.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
						e.setCancelled(true);
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
