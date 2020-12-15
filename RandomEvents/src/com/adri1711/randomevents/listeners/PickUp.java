package com.adri1711.randomevents.listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class PickUp implements Listener {

	private RandomEvents plugin;

	public PickUp(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayerHandler().getPlayersObj().contains(player)) {

			switch (plugin.getMatchActive().getMatch().getMinigame()) {
			case GEM_CRAWLER:
				Item item = evt.getItem();

				if (plugin.getMatchActive().getPuntuacion().containsKey(player.getName())) {
					plugin.getMatchActive().getPuntuacion().put(player.getName(),
							plugin.getMatchActive().getPuntuacion().get(player.getName())
									+ item.getItemStack().getAmount());

				} else {
					plugin.getMatchActive().getPuntuacion().put(player.getName(), item.getItemStack().getAmount());
				}
				item.remove();
				if (plugin.getMatchActive().getPlayerHandler().getPlayerContador() == null) {
					UtilsRandomEvents.mandaMensaje(plugin, plugin.getMatchActive().getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getNowGems().replace("%player%", player.getName()).replace("%points%",
									plugin.getMatchActive().getPuntuacion().get(player.getName()).toString()),
							true);
					plugin.getMatchActive().compruebaPartida();
				}
				evt.setCancelled(true);

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
