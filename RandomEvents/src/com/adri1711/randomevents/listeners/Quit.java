package com.adri1711.randomevents.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.adri1711.randomevents.RandomEvents;

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
			player.getInventory().clear();
			player.updateInventory();
			plugin.getMatchActive().echaDePartida(player);
		}
		
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}
	
	
	
	
	
}
