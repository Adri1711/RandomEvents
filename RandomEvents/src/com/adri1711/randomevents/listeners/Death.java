package com.adri1711.randomevents.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Death implements Listener {

	private RandomEvents plugin;

	public Death(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageEvent ev) // Listens to EntityDamageEvent
	{
		if (ev.getEntity() instanceof Player) {
			Player player = (Player) ev.getEntity();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {

				if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
					ev.setCancelled(true);
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case BATTLE_ROYALE:
					case BATTLE_ROYALE_CABALLO:
					case BATTLE_ROYALE_TEAM_2:
						plugin.getMatchActive().echaDePartida(player);
						player.setHealth(20);
						UtilsRandomEvents.playSound(player, Sound.ENTITY_PLAYER_DEATH);

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
