package com.adri1711.randomevents.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;

public class GUI implements Listener {

	private RandomEvents plugin;

	public GUI(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (Constantes.GUI_NAME.equals(ChatColor.stripColor(plugin.getApi().getInventoryName(event)))) {
			event.setCancelled(true);
		}
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

}
