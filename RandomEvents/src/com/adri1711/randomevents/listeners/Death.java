package com.adri1711.randomevents.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.MinigameType;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Death implements Listener {

	private RandomEvents plugin;

	public Death(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void damage(EntityDamageEvent ev) // Listens to EntityDamageEvent
	{
		if (ev.getCause() != DamageCause.ENTITY_ATTACK) {
			if (ev.getEntity() instanceof Player) {
				Player player = (Player) ev.getEntity();
				if (plugin.getMatchActive() != null
						&& plugin.getMatchActive().getPlayers().contains(player.getName())) {
					if (!plugin.getMatchActive().getPlaying()) {
						ev.setCancelled(true);
					} else {

						if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
							ev.setCancelled(true);
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case BATTLE_ROYALE:
							case KNOCKBACK_DUEL:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
								plugin.getMatchActive().echaDePartida(player,true);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							case TOP_KILLER:
							case TOP_KILLER_TEAM_2:
								plugin.getMatchActive().reiniciaPlayer(player);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							case GEM_CRAWLER:
								plugin.getMatchActive().reiniciaPlayer(player);
								UtilsRandomEvents.playSound(player,
										UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));
								break;
							default:
								break;
							}
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
									plugin.getMatchActive().echaDePartida(player,true);
									player.setHealth(20);
									UtilsRandomEvents.playSound(player,
											UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));
									UtilsRandomEvents.playSound(damager, UtilsRandomEvents.buscaSonido("LEVEL", "UP"));

									break;
								case KNOCKBACK_DUEL:
								case ESCAPE_ARROW:

									ev.setDamage(0);
									break;
								case TOP_KILLER:
								case TOP_KILLER_TEAM_2:
									plugin.getMatchActive().reiniciaPlayer(player);
									UtilsRandomEvents.playSound(player,
											UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

									UtilsRandomEvents.playSound(damager, UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
									if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
										plugin.getMatchActive().getPuntuacion().put(damager.getName(),
												plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

									} else {
										plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
									}

									damager.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.NOW_POINTS.replace(
											"%points%",
											plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));

									break;
									
								case GEM_CRAWLER:
									plugin.getMatchActive().reiniciaPlayer(player);
									UtilsRandomEvents.playSound(player,
											UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

									UtilsRandomEvents.playSound(damager, UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
									

									break;

								default:
									break;
								}
							} else {
								switch (plugin.getMatchActive().getMatch().getMinigame()) {
								case BATTLE_ROYALE:
								case BATTLE_ROYALE_CABALLO:
								case BATTLE_ROYALE_TEAM_2:
								case TOP_KILLER:
									break;
								case KNOCKBACK_DUEL:
								case ESCAPE_ARROW:
									ev.setDamage(0);
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
							case ESCAPE_ARROW:
								plugin.getMatchActive().echaDePartida(player,true);
								player.setHealth(20);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							case KNOCKBACK_DUEL:
								ev.setDamage(0);
								break;
							case TOP_KILLER:
							case GEM_CRAWLER:
								plugin.getMatchActive().reiniciaPlayer(player);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));
							
								break;
							default:
								break;
							}
						} else {
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
							case TOP_KILLER:
							case ESCAPE_ARROW:
								break;
							case KNOCKBACK_DUEL:
								ev.setDamage(0);
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
