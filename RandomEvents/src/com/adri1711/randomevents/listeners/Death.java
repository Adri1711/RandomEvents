package com.adri1711.randomevents.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.MinigameType;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Death implements Listener {

	private RandomEvents plugin;

	public Death(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void damage(EntityDamageEvent ev) // Listens to EntityDamageEvent
	{
		if (ev.getEntity() instanceof Player) {
			Player player = (Player) ev.getEntity();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				if (!plugin.getMatchActive().getPlaying()) {
					ev.setCancelled(true);
				} else {

					if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
						ev.setCancelled(true);
						switch (plugin.getMatchActive().getMatch().getMinigame()) {
						case BATTLE_ROYALE:
						case BATTLE_ROYALE_CABALLO:
						case BATTLE_ROYALE_TEAM_2:
							plugin.getMatchActive().echaDePartida(player);
							player.setHealth(20);
							UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

							break;
						default:
							break;
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void damage(EntityDamageByEntityEvent ev) // Listens to
														// EntityDamageEvent
	{
		if (ev.getEntity() instanceof Player) {
			Player player = (Player) ev.getEntity();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				if (!plugin.getMatchActive().getPlaying()) {
					ev.setCancelled(true);
				} else {
					if (ev.getDamager() instanceof Player) {
						Player damager = (Player) ev.getDamager();
						if (plugin.getMatchActive().getEquipo(player) != null
								&& plugin.getMatchActive().getEquipo(damager) != null && plugin.getMatchActive()
										.getEquipo(player).equals(plugin.getMatchActive().getEquipo(damager))) {
							ev.setCancelled(true);
						} else {

							if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
								ev.setCancelled(true);
								switch (plugin.getMatchActive().getMatch().getMinigame()) {
								case BATTLE_ROYALE:
								case BATTLE_ROYALE_CABALLO:
								case BATTLE_ROYALE_TEAM_2:
									plugin.getMatchActive().echaDePartida(player);
									player.setHealth(20);
									UtilsRandomEvents.playSound(player,
											UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

									break;
								default:
									break;
								}
							}

						}
					} else {
						if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
							ev.setCancelled(true);
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
								plugin.getMatchActive().echaDePartida(player);
								player.setHealth(20);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							default:
								break;
							}
						}
					}
				}
			}
		} else if (ev.getEntity() instanceof Horse && ev.getDamager() instanceof Player) {
			Player player = (Player) ev.getDamager();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
						.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
					ev.setDamage(0);
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
