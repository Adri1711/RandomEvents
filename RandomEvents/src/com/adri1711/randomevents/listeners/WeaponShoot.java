package com.adri1711.randomevents.listeners;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.MinigameType;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;

public class WeaponShoot implements Listener {

	private RandomEvents plugin;

	public WeaponShoot(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void weaponDamage(WeaponDamageEntityEvent ev) // Listens to
															// EntityDamageEvent
	{
		if (ev.getVictim() instanceof Player) {

			Player player = (Player) ev.getVictim();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				if (!plugin.getMatchActive().getPlaying()) {
					ev.setCancelled(true);
				} else {
					if (ev.getPlayer() instanceof Player) {
						Player damager = (Player) ev.getPlayer();
						if (plugin.getMatchActive().getEquipo(player) != null
								&& plugin.getMatchActive().getEquipo(damager) != null && plugin.getMatchActive()
										.getEquipo(player).equals(plugin.getMatchActive().getEquipo(damager))) {
							ev.setCancelled(true);
						} else {
							if (plugin.getMatchActive().getPlayers().contains(damager.getName())) {

								if (((player.getHealth() - ev.getDamage()) <= 0)) {
									ev.setCancelled(true);
									switch (plugin.getMatchActive().getMatch().getMinigame()) {
									case BATTLE_ROYALE:
									case BATTLE_ROYALE_CABALLO:
									case BATTLE_ROYALE_TEAM_2:
										plugin.getMatchActive().echaDePartida(player, true, true, false);
										player.setHealth(20);
										UtilsRandomEvents.playSound(player,
												UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));
										UtilsRandomEvents.playSound(damager,
												UtilsRandomEvents.buscaSonido("LEVEL", "UP"));

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
										if (!player.getName().equals(damager.getName())) {
											UtilsRandomEvents.playSound(damager,
													UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
											if (plugin.getMatchActive().getPuntuacion()
													.containsKey(damager.getName())) {
												plugin.getMatchActive().getPuntuacion().put(damager.getName(),
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																+ 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
											}

											damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion()
																	.get(damager.getName()).toString()));
										}

										break;

									case GEM_CRAWLER:
										plugin.getMatchActive().reiniciaPlayer(player);
										UtilsRandomEvents.playSound(player,
												UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

										UtilsRandomEvents.playSound(damager,
												UtilsRandomEvents.buscaSonido("LEVEL", "UP"));

										break;
									case BOMB_TAG:
										ev.setDamage(0);
										if (damager.equals(plugin.getMatchActive().getPlayerContador())) {
											UtilsRandomEvents.borraInventario(damager,plugin);
											UtilsRandomEvents.playSound(damager,
													UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
											UtilsRandomEvents.playSound(player,
													UtilsRandomEvents.buscaSonido("VILLAGER", "HIT"));
											plugin.getMatchActive().ponInventarioMatch(player);
											plugin.getMatchActive().setPlayerContador(player);
										}
										break;
									case BOAT_RUN:
									case RACE:
									case TNT_RUN:
									case SPLEEF:
									case SPLEGG:
										ev.setCancelled(true);
										break;
									case ESCAPE_FROM_BEAST:
										if (damager == null
												|| damager.getName()
														.equals(plugin.getMatchActive().getBeast().getName())
												|| player.getName()
														.equals(plugin.getMatchActive().getBeast().getName())) {
											plugin.getMatchActive().echaDePartida(player, true, true, false);
											player.setHealth(20);
											UtilsRandomEvents.playSound(player,
													UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));
											UtilsRandomEvents.playSound(damager,
													UtilsRandomEvents.buscaSonido("LEVEL", "UP"));

										} else {
											ev.setCancelled(true);
										}

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
									case ESCAPE_FROM_BEAST:
										if (damager == null
												|| damager.getName()
														.equals(plugin.getMatchActive().getBeast().getName())
												|| player.getName()
														.equals(plugin.getMatchActive().getBeast().getName())) {

										} else {
											ev.setCancelled(true);
										}

										break;
									case KNOCKBACK_DUEL:
									case ESCAPE_ARROW:
										ev.setDamage(0);
										break;
									case BOMB_TAG:
										ev.setDamage(0);
										if (damager.equals(plugin.getMatchActive().getPlayerContador())) {
											UtilsRandomEvents.borraInventario(damager,plugin);
											UtilsRandomEvents.playSound(damager,
													UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
											UtilsRandomEvents.playSound(player,
													UtilsRandomEvents.buscaSonido("VILLAGER", "HIT"));
											plugin.getMatchActive().ponInventarioMatch(player);
											plugin.getMatchActive().setPlayerContador(player);
										}
										break;
									case BOAT_RUN:
									case RACE:
									case TNT_RUN:
									case SPLEEF:
									case SPLEGG:
										ev.setCancelled(true);
										break;
									default:
										break;
									}
								}
							} else {
								if (plugin.getMatchActive().getPlayersSpectators().contains(damager)) {
									ev.setCancelled(true);
								}
							}
						}

					} else {

						if (((player.getHealth() - ev.getDamage()) <= 0)) {

							ev.setCancelled(true);
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
							case ESCAPE_ARROW:
								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(20);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							case ESCAPE_FROM_BEAST:

								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(20);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							case KNOCKBACK_DUEL:
							case BOMB_TAG:
								ev.setDamage(0);
								break;
							case TOP_KILLER:
							case GEM_CRAWLER:
								plugin.getMatchActive().reiniciaPlayer(player);
								UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("VILLAGER", "DEATH"));

								break;
							case BOAT_RUN:
							case RACE:
							case TNT_RUN:
							case SPLEEF:
							case SPLEGG:
								ev.setCancelled(true);
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
							case ESCAPE_FROM_BEAST:
								break;
							case KNOCKBACK_DUEL:
							case BOMB_TAG:
								ev.setDamage(0);
								break;
							case BOAT_RUN:
							case RACE:
							case TNT_RUN:
							case SPLEEF:
							case SPLEGG:
								ev.setCancelled(true);
								break;
							default:
								break;
							}

						}
					}
				}
			} else if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayersSpectators().contains(player)) {
				ev.setCancelled(true);
			}
		} else if (ev.getVictim() instanceof Horse && ev.getPlayer() instanceof Player) {
			Player player = (Player) ev.getPlayer();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
						.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
					ev.setDamage(0);
				}
			}
		} else if (ev.getVictim() instanceof Boat) {
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getMobs().contains(ev.getVictim())) {
				ev.setCancelled(true);
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
