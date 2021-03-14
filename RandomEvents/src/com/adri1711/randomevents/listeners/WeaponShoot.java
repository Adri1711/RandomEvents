package com.adri1711.randomevents.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XSound;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponExplodeEvent;

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
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
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
							if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())) {

								if (((player.getHealth() - ev.getDamage()) <= 0)) {

									ev.setCancelled(true);
									switch (plugin.getMatchActive().getMatch().getMinigame()) {
									case SG:
									case SW:
									case TSG:
									case TSW:
										if (!plugin.getMatchActive().getAllowDamage()) {
											ev.setCancelled(true);
										} else {
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%killer%", damager.getName()),
													false);
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
											plugin.getMatchActive().echaDePartida(player, true, true, false, true,
													true);
											player.setHealth(player.getMaxHealth());

										}
										break;
									case BATTLE_ROYALE:
									case BATTLE_ROYALE_CABALLO:
									case BATTLE_ROYALE_TEAM_2:
									case PAINTBALL:
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
										plugin.getMatchActive().echaDePartida(player, true, true, false);
										player.setHealth(player.getMaxHealth());

										break;
									case KNOCKBACK_DUEL:
									case ESCAPE_ARROW:

										ev.setDamage(0);
										break;
									case ANVIL_SPLEEF:
									case TNT_RUN:
									case WDROP:
									case SPLEEF:
									case SPLEGG:
										ev.setCancelled(true);
										break;
									case OITC:
										plugin.getMatchActive().reiniciaPlayer(player);
										UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
										if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(),
													plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

										} else {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
										}
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
												+ plugin.getLanguage().getNowPoints().replace("%points%",
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																.toString()));
										damager.getInventory().addItem(XMaterial.ARROW.parseItem());
										if (plugin.isOitcHealAfterKill()) {
											damager.setHealth(damager.getMaxHealth());
										}
										break;

									case TOP_KILLER:
									case TOP_KILLER_TEAM_2:
										plugin.getMatchActive().reiniciaPlayer(player);
										UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
										if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(),
													plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

										} else {
											plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
										}
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
												+ plugin.getLanguage().getNowPoints().replace("%points%",
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																.toString()));

										if (plugin.isTopKillerHealAfterKill()) {
											damager.setHealth(damager.getMaxHealth());
										}

										break;

									case GEM_CRAWLER:
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpKill()
														.replaceAll("%victim%", player.getName())
														.replaceAll("%killer%", damager.getName()),
												false);
										UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);

										UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
										plugin.getMatchActive().reiniciaPlayer(player);
										break;
									case BOMB_TAG:
										ev.setDamage(0);
										if (damager.equals(
												plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
											UtilsRandomEvents.borraInventario(damager, plugin);
											UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_HURT);
											plugin.getMatchActive().ponInventarioMatch(player);
											plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);

											if (plugin.getTntTagSpeedHolder() > 0) {
												if (player.hasPotionEffect(PotionEffectType.SPEED)) {
													player.removePotionEffect(PotionEffectType.SPEED);
												}
												player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getSpeedDuration(),
														plugin.getTntTagSpeedHolder() - 1));
											}
											if (plugin.getTntTagSpeedRunners() > 0) {
												if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
													damager.removePotionEffect(PotionEffectType.SPEED);
												}
												damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getSpeedDuration(),
														plugin.getTntTagSpeedRunners() - 1));
											}

										}
										break;
									case BOAT_RUN:
									case HORSE_RUN:
									case RACE:
										ev.setCancelled(true);
										break;
									case ESCAPE_FROM_BEAST:
										if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
											ev.setCancelled(true);

										} else {
											if (damager == null
													|| damager.getName()
															.equals(plugin.getMatchActive().getPlayerHandler()
																	.getBeast().getName())
													|| player.getName().equals(plugin.getMatchActive()
															.getPlayerHandler().getBeast().getName())) {
												if (player.getName().equals(plugin.getMatchActive().getPlayerHandler()
														.getBeast().getName())) {
													plugin.getMatchActive().getPlayerHandler()
															.setPlayerContador(damager);
												}
												UtilsRandomEvents.mandaMensaje(plugin,
														plugin.getMatchActive().getPlayerHandler()
																.getPlayersSpectators(),
														plugin.getLanguage().getPvpKill()
																.replaceAll("%victim%", player.getName())
																.replaceAll("%killer%", damager.getName()),
														false);
												UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
												UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
												plugin.getMatchActive().echaDePartida(player, true, true, false);
												player.setHealth(player.getMaxHealth());

											} else {
												ev.setCancelled(true);
											}
										}

										break;
									case QUAKECRAFT:
										ev.setCancelled(true);
										break;
									default:
										break;
									}
								} else {
									switch (plugin.getMatchActive().getMatch().getMinigame()) {
									case SG:
									case SW:
									case TSG:
									case TSW:
										if (!plugin.getMatchActive().getAllowDamage()) {
											ev.setCancelled(true);
										} else {
											if (plugin.isHighestPriorityDamageEvents()) {
												ev.setCancelled(false);
											}
										}
										break;
									case BATTLE_ROYALE:
									case BATTLE_ROYALE_CABALLO:
									case BATTLE_ROYALE_TEAM_2:
									case PAINTBALL:
									case TOP_KILLER:
									case TOP_KILLER_TEAM_2:
									case OITC:
										if (plugin.isHighestPriorityDamageEvents()) {
											ev.setCancelled(false);
										}
										break;
									case ESCAPE_FROM_BEAST:
										if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
											ev.setCancelled(true);

										} else {

											if (damager == null
													|| damager.getName()
															.equals(plugin.getMatchActive().getPlayerHandler()
																	.getBeast().getName())
													|| player.getName().equals(plugin.getMatchActive()
															.getPlayerHandler().getBeast().getName())) {
												if (plugin.isHighestPriorityDamageEvents()) {
													ev.setCancelled(false);
												}

											} else {
												ev.setCancelled(true);
											}
										}

										break;
									case KNOCKBACK_DUEL:
									case ESCAPE_ARROW:
										ev.setDamage(0);
										if (plugin.isHighestPriorityDamageEvents()) {
											ev.setCancelled(false);
										}
										break;
									case ANVIL_SPLEEF:
									case TNT_RUN:
									case WDROP:
									case SPLEEF:
									case SPLEGG:
										ev.setCancelled(true);
										break;
									case BOMB_TAG:
										ev.setDamage(0);
										if (plugin.isHighestPriorityDamageEvents()) {
											ev.setCancelled(false);
										}
										if (damager.equals(
												plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
											UtilsRandomEvents.borraInventario(damager, plugin);
											UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_HURT);
											plugin.getMatchActive().ponInventarioMatch(player);
											plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);
											if (plugin.getTntTagSpeedHolder() > 0) {
												if (player.hasPotionEffect(PotionEffectType.SPEED)) {
													player.removePotionEffect(PotionEffectType.SPEED);
												}
												player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getSpeedDuration(),
														plugin.getTntTagSpeedHolder() - 1));
											}
											if (plugin.getTntTagSpeedRunners() > 0) {
												if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
													damager.removePotionEffect(PotionEffectType.SPEED);
												}
												damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
														20 * plugin.getSpeedDuration(),
														plugin.getTntTagSpeedRunners() - 1));
											}

										}
										break;
									case BOAT_RUN:
									case HORSE_RUN:
									case RACE:
										ev.setCancelled(true);
										break;
									case QUAKECRAFT:
										ev.setCancelled(true);
										break;

									default:
										if (plugin.isHighestPriorityDamageEvents()) {
											ev.setCancelled(false);
										}
										break;
									}
								}
							} else {
								if (plugin.getMatchActive().getPlayerHandler().getPlayersSpectators()
										.contains(damager)) {
									ev.setCancelled(true);
								}
							}
						}

					} else {

						if (((player.getHealth() - ev.getDamage()) <= 0)) {

							ev.setCancelled(true);
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case SG:
							case SW:
							case TSG:
							case TSW:
								if (!plugin.getMatchActive().getAllowDamage()) {
									ev.setCancelled(true);
								} else {
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);

									UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);

									plugin.getMatchActive().echaDePartida(player, true, true, false, true, true);
									player.setHealth(player.getMaxHealth());
								}

								break;
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
							case PAINTBALL:
							case ESCAPE_ARROW:
							case ANVIL_SPLEEF:

								UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
										false);
								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(player.getMaxHealth());

								break;
							case ESCAPE_FROM_BEAST:

								UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
										false);
								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(player.getMaxHealth());
								break;
							case KNOCKBACK_DUEL:
							case BOMB_TAG:
								ev.setDamage(0);
								if (plugin.isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}
								break;
							case TNT_RUN:
							case WDROP:
							case SPLEEF:
							case SPLEGG:
								ev.setCancelled(true);
								break;
							case OITC:
								if (ev.getDamager() instanceof Arrow) {
									Arrow arrow = (Arrow) ev.getDamager();

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player p = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers()
												.contains(p.getName()) && !player.getName().equals(p.getName())) {
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
											Location pLoc = p.getLocation();
											Location playerLoc = player.getLocation();
											pLoc.setWorld(playerLoc.getWorld());
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getBowKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%distance%",
																	"" + Double.valueOf(pLoc.distance(playerLoc))
																			.intValue())
															.replaceAll("%killer%", p.getName()),
													false);

											UtilsRandomEvents.playSound(p, XSound.ENTITY_PLAYER_LEVELUP);
											plugin.getMatchActive().reiniciaPlayer(player);
											if (plugin.getMatchActive().getPuntuacion().containsKey(p.getName())) {
												plugin.getMatchActive().getPuntuacion().put(p.getName(),
														plugin.getMatchActive().getPuntuacion().get(p.getName()) + 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(p.getName(), 1);
											}

											p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion().get(p.getName())
																	.toString()));
											p.getInventory().addItem(XMaterial.ARROW.parseItem());
											if (plugin.isOitcHealAfterKill()) {
												p.setHealth(p.getMaxHealth());
											}
										}
									} else {
										UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false);
									}
									plugin.getMatchActive().reiniciaPlayer(player);
								} else {
									UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
								}
								plugin.getMatchActive().reiniciaPlayer(player);

								break;
							case TOP_KILLER:
							case TOP_KILLER_TEAM_2:
								
								if (ev.getDamager() instanceof Arrow) {
									Arrow arrow = (Arrow) ev.getDamager();

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player damager = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(
												damager.getName()) && !player.getName().equals(damager.getName())) {
											plugin.getMatchActive().reiniciaPlayer(player);
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);

											UtilsRandomEvents.playSound(damager, XSound.ENTITY_PLAYER_LEVELUP);
											if (plugin.getMatchActive().getPuntuacion()
													.containsKey(damager.getName())) {
												plugin.getMatchActive().getPuntuacion().put(damager.getName(),
														plugin.getMatchActive().getPuntuacion().get(damager.getName())
																+ 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
											}
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%killer%", damager.getName()),
													false);
											damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion()
																	.get(damager.getName()).toString()));
											if (plugin.isTopKillerHealAfterKill()) {
												damager.setHealth(damager.getMaxHealth());
											}
										} else {
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
															player.getName()),
													false);
											plugin.getMatchActive().reiniciaPlayer(player);
										}
									} else {
										UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false);
										plugin.getMatchActive().reiniciaPlayer(player);
									}
								} else {
									UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
									plugin.getMatchActive().reiniciaPlayer(player);

								}
								break;
							case GEM_CRAWLER:
								UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
										false);
								plugin.getMatchActive().reiniciaPlayer(player);
								break;
							case BOAT_RUN:
							case HORSE_RUN:
							case RACE:
								ev.setCancelled(true);
								break;
							case QUAKECRAFT:
								ev.setCancelled(true);
								break;
							default:
								if (plugin.isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}
								break;
							}

						} else {
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case OITC:
								if (ev.getDamager() instanceof Arrow) {
									Arrow arrow = (Arrow) ev.getDamager();

									if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

										Player p = (Player) arrow.getShooter();

										if (plugin.getMatchActive().getPlayerHandler().getPlayers()
												.contains(p.getName()) && !player.getName().equals(p.getName())) {

											Location pLoc = p.getLocation();
											Location playerLoc = player.getLocation();
											pLoc.setWorld(playerLoc.getWorld());
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getBowKill()
															.replaceAll("%victim%", player.getName())
															.replaceAll("%distance%",
																	"" + Double.valueOf(pLoc.distance(playerLoc))
																			.intValue())
															.replaceAll("%killer%", p.getName()),
													false);
											plugin.getMatchActive().reiniciaPlayer(player);
											UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
											UtilsRandomEvents.playSound(p, XSound.ENTITY_PLAYER_LEVELUP);
											if (plugin.getMatchActive().getPuntuacion().containsKey(p.getName())) {
												plugin.getMatchActive().getPuntuacion().put(p.getName(),
														plugin.getMatchActive().getPuntuacion().get(p.getName()) + 1);

											} else {
												plugin.getMatchActive().getPuntuacion().put(p.getName(), 1);
											}

											p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
													+ plugin.getLanguage().getNowPoints().replace("%points%",
															plugin.getMatchActive().getPuntuacion().get(p.getName())
																	.toString()));
											p.getInventory().addItem(XMaterial.ARROW.parseItem());
											if (plugin.isOitcHealAfterKill()) {
												p.setHealth(p.getMaxHealth());
											}
										}
									} else {
										if (plugin.isHighestPriorityDamageEvents()) {
											ev.setCancelled(false);
										}
									}
								} else {
									if (plugin.isHighestPriorityDamageEvents()) {
										ev.setCancelled(false);
									}
								}

								break;
							case SG:
							case SW:
							case TSG:
							case TSW:
								if (!plugin.getMatchActive().getAllowDamage()) {
									ev.setCancelled(true);
								} else {
									if (plugin.isHighestPriorityDamageEvents()) {
										ev.setCancelled(false);
									}
								}
								break;
							case BATTLE_ROYALE:
							case BATTLE_ROYALE_CABALLO:
							case BATTLE_ROYALE_TEAM_2:
							case PAINTBALL:
							case TOP_KILLER:
							case TOP_KILLER_TEAM_2:
							case ANVIL_SPLEEF:
							case ESCAPE_FROM_BEAST:
								if (plugin.isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}
								break;

							case KNOCKBACK_DUEL:
							case BOMB_TAG:
								ev.setDamage(0);
								if (plugin.isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}
								break;
							case TNT_RUN:
							case WDROP:
							case SPLEEF:
							case SPLEGG:
							case BOAT_RUN:
							case HORSE_RUN:
							case RACE:
								ev.setCancelled(true);
								break;

							case ESCAPE_ARROW:
								if (((player.getHealth() - plugin.getArrowRainDamage()) <= 0)) {

									UtilsRandomEvents.playSound(player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false);
									plugin.getMatchActive().echaDePartida(player, true, true, false);
									player.setHealth(player.getMaxHealth());
								} else {
									player.damage(plugin.getArrowRainDamage());
									if (plugin.isHighestPriorityDamageEvents()) {
										ev.setCancelled(false);
									}
								}
								break;
							case QUAKECRAFT:
								ev.setCancelled(true);
								break;
							default:
								if (plugin.isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}
								break;
							}

						}
					}
				}
			} else if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
				ev.setCancelled(true);
			}
		} else if (ev.getVictim() instanceof Horse && ev.getPlayer() instanceof Player)

		{
			Player player = (Player) ev.getPlayer();
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
				if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
						.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
					ev.setDamage(0);
					if (plugin.isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
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
