package com.adri1711.randomevents.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XSound;

public class Death implements Listener {

	private RandomEvents plugin;

	public Death(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void damage(EntityDamageEvent ev) // Listens to EntityDamageEvent
	{
		Boolean cancelled = false;
		Long now = (new Date()).getTime();
		if (ev.getEntity() instanceof Player) {
			Player p6 = (Player) ev.getEntity();

			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p6.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
			}
		}
		if (ev.getCause() != DamageCause.ENTITY_ATTACK && ev.getCause() != DamageCause.PROJECTILE) {

			if (ev.getEntity() instanceof Player) {

				Player player = (Player) ev.getEntity();
				if (plugin.getMatchActive() != null
						&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
					plugin.getMatchActive().setDamageCounter(0);
					if (!plugin.getMatchActive().getPlaying() || !plugin.getMatchActive().getAllowDamage()
							|| (plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
									.containsKey(player.getName())
									&& plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
											.get(player.getName()) > now)) {
						ev.setCancelled(true);
					} else {
						if (plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
								.containsKey(player.getName())
								&& plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
										.get(player.getName()) <= now) {
							plugin.getMatchActive().getPlayerHandler().getPlayersInvincible().remove(player.getName());
						}
						if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
							if (!ev.isCancelled()) {
								ev.setCancelled(true);

							} else {
								cancelled = true;
							}
							Boolean totem = false;

							if (player.getInventory().getItemInHand() != null && player.getInventory().getItemInHand()
									.getType() == (XMaterial.TOTEM_OF_UNDYING.parseMaterial())) {
								player.getInventory().setItemInHand(null);
								player.updateInventory();
								totem = true;
							} else {
								try {
									Method method = player.getInventory().getClass()
											.getDeclaredMethod("getItemInOffHand");
									if (method != null) {

										ItemStack item = (ItemStack) method.invoke(player.getInventory());
										if (item != null
												&& item.getType() == (XMaterial.TOTEM_OF_UNDYING.parseMaterial())) {
											Method method2 = player.getInventory().getClass()
													.getDeclaredMethod("setItemInOffHand", ItemStack.class);
											method2.invoke(player.getInventory(), XMaterial.AIR.parseItem());
											player.updateInventory();
											totem = true;
										}

									}
								} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
										| NoSuchMethodException | SecurityException e) {

								}
							}

							if (totem) {
								UtilsRandomEvents.playSound(plugin, player, XSound.ITEM_TOTEM_USE);

								player.setHealth(1);
								player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 4, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 45, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 40, 0));

								ev.setDamage(0);
							} else {

								switch (plugin.getMatchActive().getMatch().getMinigame()) {
								case SW:
								case SG:
								case TSG:
								case TSW:
								case TSG_REAL:
								case TSW_REAL:
									if (!plugin.getMatchActive().getAllowDamage()) {
										ev.setCancelled(true);
									} else {
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false, false, false);
										plugin.getMatchActive().hazComandosDeMuerte(null, player);
										plugin.getMatchActive().echaDePartida(player, true, true, false, true, true,false);
										UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
									}
									break;
								case BATTLE_ROYALE:
								case BATTLE_ROYALE_CABALLO:
								case BATTLE_ROYALE_TEAM_2:
								case BATTLE_ROYALE_TEAMS:
								case HIDE_AND_SEEK:
								case PAINTBALL:
								case TNT_RUN:
								case SPLEEF:
								case ANVIL_SPLEEF:
								case BOMBARDMENT:
								case SPLEGG:
									if (!cancelled) {
										UtilsRandomEvents.mandaMensaje(plugin,
												plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
												plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
														player.getName()),
												false, false, false);
										plugin.getMatchActive().hazComandosDeMuerte(null, player);
										plugin.getMatchActive().echaDePartida(player, true, true, false);
										UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
									}
									break;
								case GLASS_WALK:
								case KNOCKBACK_DUEL:
								case BLOCK_PARTY:
									if (!cancelled) {
										if (!plugin.getMatchActive().getAllowDamage()) {
											ev.setCancelled(true);
										} else {
											UtilsRandomEvents.mandaMensaje(plugin,
													plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
													plugin.getLanguage().getPvpDeath().replaceAll("%victim%",
															player.getName()),
													false, false, false);
											plugin.getMatchActive().hazComandosDeMuerte(null, player);
											plugin.getMatchActive().echaDePartida(player, true, true, false);
											UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

										}
									}
									break;
								case FISH_SLAP:
									ev.setCancelled(true);
									break;
								case ESCAPE_FROM_BEAST:

									ev.setCancelled(true);

									break;
								case OITC:
								case TOP_KILLER:
								case TOP_KILLER_TEAM_2:
								case TOP_KILLER_TEAMS:
								case PAINTBALL_TOP_KILL:
								case KOTH:

									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false, false, false);
									plugin.getMatchActive().hazComandosDeMuerte(null, player);
									ev.setDamage(0);
									plugin.getMatchActive().reiniciaPlayer(player);
									ev.setDamage(0);
									UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

									break;
								case GEM_CRAWLER:
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()),
											false, false, false);
									plugin.getMatchActive().hazComandosDeMuerte(null, player);
									ev.setDamage(0);
									plugin.getMatchActive().reiniciaPlayer(player);
									UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
									break;
								case WDROP:
									plugin.getMatchActive().hazComandosDeMuerte(null, player);
									ev.setDamage(0);
									plugin.getMatchActive().reiniciaPlayer(player);
									UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_BAT_HURT);
									break;
								case BOMB_TAG:
									ev.setDamage(0);
									break;
								case BOAT_RUN:
								case HORSE_RUN:
									ev.setCancelled(true);
									break;
								case RACE:
								case RED_GREEN_LIGHT:
									ev.setCancelled(true);

									break;
								case QUAKECRAFT:
								case HOEHOEHOE:
								case SPLATOON:
									ev.setCancelled(true);
									break;
								default:
									break;
								}
							}

						} else {
							switch (plugin.getMatchActive().getMatch().getMinigame()) {
							case WDROP:
								ev.setCancelled(true);
								plugin.getMatchActive().hazComandosDeMuerte(null, player);
								ev.setDamage(0);
								plugin.getMatchActive().reiniciaPlayer(player);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
								break;
							case ESCAPE_FROM_BEAST:

								ev.setCancelled(true);

								break;
							case RACE:
							case RED_GREEN_LIGHT:
								ev.setCancelled(true);
								// UtilsRandomEvents.teleportaPlayer(player,
								// plugin.getMatchActive().getCheckpoints().get(player.getName()),
								// plugin);
								// player.addPotionEffect(new
								// PotionEffect(PotionEffectType.SLOW, 60, 99));

								break;
							case SG:
							case TSG:
							case SW:
							case TSW:
							case TSG_REAL:
							case TSW_REAL:
								if (!plugin.getMatchActive().getAllowDamage()) {
									ev.setCancelled(true);
								}
								break;
							case HOEHOEHOE:
							case SPLATOON:
							case QUAKECRAFT:
							case BOAT_RUN:
							case HORSE_RUN:
								ev.setCancelled(true);
								break;
							default:

								break;
							}
						}
					}
				}
			} else if (ev.getEntity() instanceof Boat) {
				if (plugin.getMatchActive() != null && plugin.getMatchActive().getMobs().contains(ev.getEntity())) {
					ev.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void damage(EntityDamageByEntityEvent ev) // Listens to
														// EntityDamageEvent
	{
		if (ev.getEntity() instanceof Player) {
			Long now = (new Date()).getTime();
			Player player = (Player) ev.getEntity();
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
				if (!plugin.getMatchActive().getPlaying() || !plugin.getMatchActive().getAllowDamagePVP()
						|| (plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
								.containsKey(player.getName())
								&& plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
										.get(player.getName()) > now)) {
					ev.setCancelled(true);
				} else {
					if (plugin.getMatchActive().getPlayerHandler().getPlayersInvincible().containsKey(player.getName())
							&& plugin.getMatchActive().getPlayerHandler().getPlayersInvincible()
									.get(player.getName()) <= now) {
						plugin.getMatchActive().getPlayerHandler().getPlayersInvincible().remove(player.getName());
					}

					Boolean totem = false;
					if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {

						if (player.getInventory().getItemInHand() != null && player.getInventory().getItemInHand()
								.getType() == (XMaterial.TOTEM_OF_UNDYING.parseMaterial())) {
							player.getInventory().setItemInHand(null);
							player.updateInventory();
							totem = true;
						} else {
							try {
								Method method = player.getInventory().getClass().getDeclaredMethod("getItemInOffHand");
								if (method != null) {

									ItemStack item = (ItemStack) method.invoke(player.getInventory());
									if (item != null
											&& item.getType() == (XMaterial.TOTEM_OF_UNDYING.parseMaterial())) {
										Method method2 = player.getInventory().getClass()
												.getDeclaredMethod("setItemInOffHand", ItemStack.class);
										method2.invoke(player.getInventory(), XMaterial.AIR.parseItem());
										player.updateInventory();
										totem = true;
									}

								}
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
									| NoSuchMethodException | SecurityException e) {

							}
						}
					}

					if (totem) {
						UtilsRandomEvents.playSound(plugin, player, XSound.ITEM_TOTEM_USE);

						player.setHealth(1);
						player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 4, 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 45, 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 40, 0));

						ev.setDamage(0);
					} else {

						if (ev.getDamager() instanceof Player) {

							checkPlayerDamage(player, ev);

						} else if (ev.getDamager() instanceof Arrow) {
							Arrow arrow = (Arrow) ev.getDamager();

							if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
								checkArrowDamage(player, (Player) arrow.getShooter(), ev);
							} else {
								checkOtherDamage(player, ev);
							}

						} else if (ev.getDamager() instanceof Snowball) {
							Snowball snowball = (Snowball) ev.getDamager();

							if (snowball.getShooter() != null && snowball.getShooter() instanceof Player) {
								checkSnowBallDamage(player, (Player) snowball.getShooter(), ev);
							} else {
								checkOtherDamage(player, ev);
							}

						} else if (ev.getDamager() instanceof Egg) {
							Egg egg = (Egg) ev.getDamager();

							if (egg.getShooter() != null && egg.getShooter() instanceof Player) {
								checkEggDamage(player, (Player) egg.getShooter(), ev);
							} else {
								checkOtherDamage(player, ev);
							}

						} else if (ev.getDamager() instanceof Fireball) {

							checkFireballDamage(player, ev);

						} else {

							checkOtherDamage(player, ev);
						}
					}
				}
			} else if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
				ev.setCancelled(true);
			}
		} else if (ev.getEntity() instanceof Horse && ev.getDamager() instanceof Player)

		{
			Player player = (Player) ev.getDamager();
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				plugin.getMatchActive().setDamageCounter(0);
				if (plugin.getMatchActive().getMatch().getMinigame().getCodigo()
						.equals(MinigameType.BATTLE_ROYALE_CABALLO.getCodigo())) {
					ev.setDamage(0);
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}
			}
		} else if (ev.getEntity() instanceof Boat) {
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getMobs().contains(ev.getEntity())) {
				ev.setCancelled(true);
			}
		}
	}

	private void checkOtherDamage(Player player, EntityDamageByEntityEvent ev) {

		if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {
			ev.setDamage(0);
			ev.setCancelled(true);
			switch (plugin.getMatchActive().getMatch().getMinigame()) {
			case SG:
			case SW:
			case TSG:
			case TSW:
			case TSG_REAL:
			case TSW_REAL:
				if (!plugin.getMatchActive().getAllowDamagePVP()) {
					ev.setCancelled(true);
				} else {
					UtilsRandomEvents.mandaMensaje(plugin,
							plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
							false);

					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
					plugin.getMatchActive().hazComandosDeMuerte(null, player);
					plugin.getMatchActive().echaDePartida(player, true, true, false, true, true,false);
					player.setHealth(player.getMaxHealth());
				}

				break;
			case BATTLE_ROYALE:
			case BATTLE_ROYALE_CABALLO:
			case BATTLE_ROYALE_TEAM_2:
			case BATTLE_ROYALE_TEAMS:
			case HIDE_AND_SEEK:
			case PAINTBALL:
			case ESCAPE_ARROW:
			case ANVIL_SPLEEF:
			case BOMBARDMENT:

				UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
				UtilsRandomEvents.mandaMensaje(plugin,
						plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
						false);
				plugin.getMatchActive().hazComandosDeMuerte(null, player);
				plugin.getMatchActive().echaDePartida(player, true, true, false);
				player.setHealth(player.getMaxHealth());

				break;
			case ESCAPE_FROM_BEAST:

				UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
				UtilsRandomEvents.mandaMensaje(plugin,
						plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
						false);
				plugin.getMatchActive().hazComandosDeMuerte(null, player);
				plugin.getMatchActive().echaDePartida(player, true, true, false);
				player.setHealth(player.getMaxHealth());
				break;
			case GLASS_WALK:
				if (!plugin.getMatchActive().getAllowDamagePVP() || !plugin.getReventConfig().isAllowGlassWalkPvP()) {
					ev.setCancelled(true);
				} else {
					ev.setDamage(0);
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}
				break;
			case KNOCKBACK_DUEL:
			case BOMB_TAG:
			case FISH_SLAP:
			case BLOCK_PARTY:
				if (!plugin.getMatchActive().getAllowDamagePVP()) {
					ev.setCancelled(true);
				} else {
					ev.setDamage(0);
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
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

						if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())
								&& !player.getName().equals(p.getName())) {
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
							Location pLoc = p.getLocation();
							Location playerLoc = player.getLocation();
							pLoc.setWorld(playerLoc.getWorld());
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getBowKill().replaceAll("%victim%", player.getName())
											.replaceAll("%distance%",
													"" + Double.valueOf(pLoc.distance(playerLoc)).intValue())
											.replaceAll("%killer%", p.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(p, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(p, player);

							UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_PLAYER_LEVELUP);
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							if (plugin.getMatchActive().getPuntuacion().containsKey(p.getName())) {
								plugin.getMatchActive().getPuntuacion().put(p.getName(),
										plugin.getMatchActive().getPuntuacion().get(p.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(p.getName(), 1);
							}

							p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(p.getName()).toString()));
							p.getInventory().addItem(XMaterial.ARROW.parseItem());
							if (plugin.getReventConfig().isOitcHealAfterKill()) {
								p.setHealth(p.getMaxHealth());
							}

						}
					} else {
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						plugin.getMatchActive().hazComandosDeMuerte(null, player);
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false,
								false, false);
					}
					ev.setDamage(0);
					plugin.getMatchActive().reiniciaPlayer(player);
				} else {
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
					UtilsRandomEvents.mandaMensaje(plugin,
							plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
							false);
					plugin.getMatchActive().hazComandosDeMuerte(null, player);
				}
				ev.setDamage(0);
				plugin.getMatchActive().reiniciaPlayer(player);

				break;
			case TOP_KILLER:
			case TOP_KILLER_TEAM_2:
			case TOP_KILLER_TEAMS:

				if (ev.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) ev.getDamager();

					if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

						Player damager = (Player) arrow.getShooter();

						if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())
								&& !player.getName().equals(damager.getName())) {
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(),
										plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
							}
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
							if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
								damager.setHealth(damager.getMaxHealth());
							}
						} else {
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false,
									false, false);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
						}
					} else {
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false,
								false, false);
						plugin.getMatchActive().hazComandosDeMuerte(null, player);
						ev.setDamage(0);
						plugin.getMatchActive().reiniciaPlayer(player);
					}
				} else {
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
					UtilsRandomEvents.mandaMensaje(plugin,
							plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
							false);
					plugin.getMatchActive().hazComandosDeMuerte(null, player);
					ev.setDamage(0);
					plugin.getMatchActive().reiniciaPlayer(player);

				}
				break;
			case KOTH:
				if (ev.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) ev.getDamager();

					if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {

						Player damager = (Player) arrow.getShooter();

						if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())
								&& !player.getName().equals(damager.getName())) {
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);

							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
						} else {
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false,
									false, false);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
						}
					} else {
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false,
								false, false);
						plugin.getMatchActive().hazComandosDeMuerte(null, player);
						ev.setDamage(0);
						plugin.getMatchActive().reiniciaPlayer(player);
					}
				} else {
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
					UtilsRandomEvents.mandaMensaje(plugin,
							plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
							false);
					plugin.getMatchActive().hazComandosDeMuerte(null, player);
					ev.setDamage(0);
					plugin.getMatchActive().reiniciaPlayer(player);

				}
				break;
			case GEM_CRAWLER:

				UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
				UtilsRandomEvents.mandaMensaje(plugin,
						plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
						false);
				plugin.getMatchActive().hazComandosDeMuerte(null, player);
				ev.setDamage(0);
				plugin.getMatchActive().reiniciaPlayer(player);

				break;
			case BOAT_RUN:
			case HORSE_RUN:
			case RACE:
			case RED_GREEN_LIGHT:
				ev.setCancelled(true);
				break;
			case HOEHOEHOE:
			case SPLATOON:
			case QUAKECRAFT:
				ev.setCancelled(true);
				break;
			default:
				if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
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

						if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())
								&& !player.getName().equals(p.getName())) {

							Location pLoc = p.getLocation();
							Location playerLoc = player.getLocation();
							pLoc.setWorld(playerLoc.getWorld());
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getBowKill().replaceAll("%victim%", player.getName())
											.replaceAll("%distance%",
													"" + Double.valueOf(pLoc.distance(playerLoc)).intValue())
											.replaceAll("%killer%", p.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(p, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(p, player);
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
							UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_PLAYER_LEVELUP);
							if (plugin.getMatchActive().getPuntuacion().containsKey(p.getName())) {
								plugin.getMatchActive().getPuntuacion().put(p.getName(),
										plugin.getMatchActive().getPuntuacion().get(p.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(p.getName(), 1);
							}

							p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(p.getName()).toString()));
							p.getInventory().addItem(XMaterial.ARROW.parseItem());
							if (plugin.getReventConfig().isOitcHealAfterKill()) {
								p.setHealth(p.getMaxHealth());
							}

						}
					} else {
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
					}
				} else {
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}

				break;
			case SG:
			case SW:
			case TSG:
			case TSW:
			case TSG_REAL:
			case TSW_REAL:
				if (!plugin.getMatchActive().getAllowDamagePVP()) {
					ev.setCancelled(true);
				} else {
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}
				break;
			case BATTLE_ROYALE:
			case BATTLE_ROYALE_CABALLO:
			case BATTLE_ROYALE_TEAM_2:
			case BATTLE_ROYALE_TEAMS:
			case HIDE_AND_SEEK:
			case PAINTBALL:
			case TOP_KILLER:
			case TOP_KILLER_TEAM_2:
			case TOP_KILLER_TEAMS:
			case PAINTBALL_TOP_KILL:
			case ANVIL_SPLEEF:
			case BOMBARDMENT:
			case ESCAPE_FROM_BEAST:
			case KOTH:
				if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
					ev.setCancelled(false);
				}
				break;

			case GLASS_WALK:
				if (!plugin.getMatchActive().getAllowDamagePVP() || !plugin.getReventConfig().isAllowGlassWalkPvP()) {
					ev.setCancelled(true);
				} else {
					ev.setDamage(0);
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}
				break;
			case KNOCKBACK_DUEL:
			case BOMB_TAG:
			case FISH_SLAP:
			case BLOCK_PARTY:
				if (!plugin.getMatchActive().getAllowDamagePVP()) {
					ev.setCancelled(true);
				} else {
					ev.setDamage(0);
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}
				break;
			case TNT_RUN:
			case WDROP:
			case SPLEEF:
			case SPLEGG:
			case BOAT_RUN:
			case HORSE_RUN:
			case RACE:
			case RED_GREEN_LIGHT:
				ev.setCancelled(true);
				break;

			case ESCAPE_ARROW:
				if (((player.getHealth() - plugin.getReventConfig().getArrowRainDamage()) <= 0)) {

					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
					UtilsRandomEvents.mandaMensaje(plugin,
							plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
							false);
					plugin.getMatchActive().echaDePartida(player, true, true, false);
					player.setHealth(player.getMaxHealth());
				} else {
					player.damage(plugin.getReventConfig().getArrowRainDamage());
					if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
						ev.setCancelled(false);
					}
				}
				break;
			case HOEHOEHOE:
			case SPLATOON:
			case QUAKECRAFT:
				ev.setCancelled(true);
				break;
			default:
				if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
					ev.setCancelled(false);
				}
				break;
			}

		}
	}

	private void checkFireballDamage(Player player, EntityDamageByEntityEvent ev) {

		switch (plugin.getMatchActive().getMatch().getMinigame()) {
		case BOMBARDMENT:
			UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
			UtilsRandomEvents.mandaMensaje(plugin, plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
					plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false, false);
			plugin.getMatchActive().hazComandosDeMuerte(null, player);
			plugin.getMatchActive().echaDePartida(player, true, true, false);
			player.setHealth(player.getMaxHealth());

			break;

		default:
			checkOtherDamage(player, ev);
			break;
		}

	}

	private void checkEggDamage(Player player, Player damager, EntityDamageByEntityEvent ev) {

		switch (plugin.getMatchActive().getMatch().getMinigame()) {
		case SPLATOON:
			if (((player.getHealth() - plugin.getReventConfig().getSplatoonEggDamage()) <= 0)) {
				Location pLoc = damager.getLocation();
				Location playerLoc = player.getLocation();
				pLoc.setWorld(playerLoc.getWorld());
				Integer distancia = Double.valueOf(pLoc.distance(playerLoc)).intValue();
				plugin.getMatchActive().hazComandosDeMuerte(damager, player);
				ev.setDamage(0);
				plugin.getMatchActive().reiniciaPlayer(player);
				UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

				UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);

				UtilsRandomEvents.mandaMensaje(plugin,
						plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
								.replaceAll("%victim%", player.getName()).replaceAll("%killer%", damager.getName()),
						false, false, false);
				UtilsRandomEvents.doCommandsKill(damager, plugin);

			} else {
				ev.setDamage(plugin.getReventConfig().getSplatoonEggDamage());
			}
			break;
		case SPLEGG:
			if (plugin.getReventConfig().isAvoidEggKnockback()) {
				ev.setCancelled(true);
			}
			break;
		default:
			checkOtherDamage(player, ev);
			break;
		}

	}

	private void checkPlayerDamage(Player player, EntityDamageByEntityEvent ev) {

		Player damager = (Player) ev.getDamager();
		if (plugin.getMatchActive().getEquipo(player) != null && plugin.getMatchActive().getEquipo(damager) != null
				&& plugin.getMatchActive().getEquipo(player).equals(plugin.getMatchActive().getEquipo(damager))) {
			ev.setCancelled(true);
		} else {
			if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())) {

				if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {

					Boolean totem = false;

					if (player.getInventory().getItemInHand() != null && player.getInventory().getItemInHand()
							.getType() == (XMaterial.TOTEM_OF_UNDYING.parseMaterial())) {
						player.getInventory().setItemInHand(null);
						player.updateInventory();
						totem = true;
					} else {
						try {
							Method method = player.getInventory().getClass().getDeclaredMethod("getItemInOffHand");
							if (method != null) {

								ItemStack item = (ItemStack) method.invoke(player.getInventory());
								if (item != null && item.getType() == (XMaterial.TOTEM_OF_UNDYING.parseMaterial())) {
									Method method2 = player.getInventory().getClass()
											.getDeclaredMethod("setItemInOffHand", ItemStack.class);
									method2.invoke(player.getInventory(), XMaterial.AIR.parseItem());
									player.updateInventory();
									totem = true;
								}

							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
								| NoSuchMethodException | SecurityException e) {

						}
					}

					if (totem) {
						UtilsRandomEvents.playSound(plugin, player, XSound.ITEM_TOTEM_USE);

						player.setHealth(1);
						player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 4, 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 45, 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 40, 0));

						ev.setDamage(0);
						ev.setCancelled(true);
					} else {
						ev.setDamage(0);
						ev.setCancelled(true);
						switch (plugin.getMatchActive().getMatch().getMinigame()) {
						case SG:
						case SW:
						case TSG:
						case TSW:
						case TSG_REAL:
						case TSW_REAL:
							if (!plugin.getMatchActive().getAllowDamagePVP()) {
								ev.setCancelled(true);
							} else {
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
												.replaceAll("%killer%", damager.getName()),
										false, false, false);
								UtilsRandomEvents.doCommandsKill(damager, plugin);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
								plugin.getMatchActive().hazComandosDeMuerte(damager, player);
								plugin.getMatchActive().echaDePartida(player, true, true, false, true, true,false);
								player.setHealth(player.getMaxHealth());

							}
							break;
						case BATTLE_ROYALE:
						case BATTLE_ROYALE_CABALLO:
						case PAINTBALL:
						case BATTLE_ROYALE_TEAM_2:
						case BATTLE_ROYALE_TEAMS:
						case HIDE_AND_SEEK:
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							player.setHealth(player.getMaxHealth());

							break;
						case GLASS_WALK:
							if (!plugin.getMatchActive().getAllowDamagePVP() || !plugin.getReventConfig().isAllowGlassWalkPvP()) {
								ev.setCancelled(true);
							} else {
								ev.setDamage(0);
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}
							}
							break;
						case KNOCKBACK_DUEL:
						case FISH_SLAP:
						case BLOCK_PARTY:
							if (!plugin.getMatchActive().getAllowDamagePVP()) {
								ev.setCancelled(true);
							} else {

								ev.setDamage(0);
							}
							break;
						case ESCAPE_ARROW:
							if (!plugin.getMatchActive().getAllowDamagePVP()) {
								ev.setCancelled(true);
							} else {

								ev.setDamage(0);
							}
							break;
						case ANVIL_SPLEEF:
						case BOMBARDMENT:
						case TNT_RUN:
						case WDROP:
						case SPLEEF:
						case SPLEGG:
							ev.setCancelled(true);
							break;
						case OITC:
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(),
										plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
							}
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
							damager.getInventory().addItem(XMaterial.ARROW.parseItem());
							if (plugin.getReventConfig().isOitcHealAfterKill()) {
								damager.setHealth(damager.getMaxHealth());
							}
							break;

						case TOP_KILLER:
						case TOP_KILLER_TEAM_2:
						case TOP_KILLER_TEAMS:
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(),
										plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
							}
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
							if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
								damager.setHealth(damager.getMaxHealth());
							}
							break;
						case KOTH:
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);

							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							break;

						case GEM_CRAWLER:
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							break;
						case BOMB_TAG:
							ev.setDamage(0);
							if (damager.equals(plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
								UtilsRandomEvents.borraInventario(damager, plugin);
								UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_HURT);
								plugin.getMatchActive().ponInventarioMatch(player,false);
								plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);

								if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
									if (player.hasPotionEffect(PotionEffectType.SPEED)) {
										player.removePotionEffect(PotionEffectType.SPEED);
									}
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
											20 * plugin.getReventConfig().getSpeedDuration(),
											plugin.getReventConfig().getTntTagSpeedHolder() - 1));
								}
								if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
									if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
										damager.removePotionEffect(PotionEffectType.SPEED);
									}
									damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
											20 * plugin.getReventConfig().getSpeedDuration(),
											plugin.getReventConfig().getTntTagSpeedRunners() - 1));
								}

								if (plugin.getMatchActive().getPlayerHandler().getPlayerContador() != null) {
									UtilsRandomEvents.removeGlow(plugin, damager,
											plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
									UtilsRandomEvents.addGlow(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayerContador(),
											plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
								}

							}
							break;
						case BOAT_RUN:
						case HORSE_RUN:
						case RACE:
						case RED_GREEN_LIGHT:
							ev.setCancelled(true);
							break;
						case ESCAPE_FROM_BEAST:
							if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
								ev.setCancelled(true);

							} else {
								if (damager == null
										|| damager.getName()
												.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())
										|| player.getName().equals(
												plugin.getMatchActive().getPlayerHandler().getBeast().getName())) {
									if (player.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())) {
										plugin.getMatchActive().getPlayerHandler().setPlayerContador(damager);
									}
									UtilsRandomEvents.mandaMensaje(plugin,
											plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
											plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
													.replaceAll("%killer%", damager.getName()),
											false, false, false);
									UtilsRandomEvents.doCommandsKill(damager, plugin);
									UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
									UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
									plugin.getMatchActive().hazComandosDeMuerte(damager, player);
									plugin.getMatchActive().echaDePartida(player, true, true, false);
									player.setHealth(player.getMaxHealth());

								} else {
									ev.setCancelled(true);
								}
							}

							break;
						case HOEHOEHOE:
						case SPLATOON:
						case QUAKECRAFT:
							ev.setCancelled(true);
							break;
						default:
							break;
						}
					}
				} else {
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case SG:
					case SW:
					case TSG:
					case TSW:
					case TSG_REAL:
					case TSW_REAL:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case BATTLE_ROYALE:
					case BATTLE_ROYALE_CABALLO:
					case BATTLE_ROYALE_TEAM_2:
					case BATTLE_ROYALE_TEAMS:
					case HIDE_AND_SEEK:
					case PAINTBALL:
					case TOP_KILLER:
					case TOP_KILLER_TEAM_2:
					case TOP_KILLER_TEAMS:
					case PAINTBALL_TOP_KILL:
					case OITC:
					case KOTH:
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
						break;
					case ESCAPE_FROM_BEAST:
						if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
							ev.setCancelled(true);

						} else {

							if (damager == null
									|| damager.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())
									|| player.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())) {
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}

							} else {
								ev.setCancelled(true);
							}
						}

						break;
					case GLASS_WALK:
						if (!plugin.getMatchActive().getAllowDamagePVP() || !plugin.getReventConfig().isAllowGlassWalkPvP()) {
							ev.setCancelled(true);
						} else {
							ev.setDamage(0);
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case KNOCKBACK_DUEL:
					case ESCAPE_ARROW:
					case FISH_SLAP:
					case BLOCK_PARTY:

						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							ev.setDamage(0);
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case ANVIL_SPLEEF:
					case BOMBARDMENT:
					case TNT_RUN:
					case WDROP:
					case SPLEEF:
					case SPLEGG:
						ev.setCancelled(true);
						break;
					case BOMB_TAG:
						ev.setDamage(0);
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
						if (damager.equals(plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
							UtilsRandomEvents.borraInventario(damager, plugin);
							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_HURT);
							plugin.getMatchActive().ponInventarioMatch(player,false);
							plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);
							if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
								if (player.hasPotionEffect(PotionEffectType.SPEED)) {
									player.removePotionEffect(PotionEffectType.SPEED);
								}
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
										20 * plugin.getReventConfig().getSpeedDuration(),
										plugin.getReventConfig().getTntTagSpeedHolder() - 1));
							}
							if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
								if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
									damager.removePotionEffect(PotionEffectType.SPEED);
								}
								damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
										20 * plugin.getReventConfig().getSpeedDuration(),
										plugin.getReventConfig().getTntTagSpeedRunners() - 1));
							}
							if (plugin.getMatchActive().getPlayerHandler().getPlayerContador() != null) {
								UtilsRandomEvents.removeGlow(plugin, damager,
										plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
								UtilsRandomEvents.addGlow(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayerContador(),
										plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
							}

						}
						break;
					case BOAT_RUN:
					case HORSE_RUN:
					case RACE:
					case RED_GREEN_LIGHT:
						ev.setCancelled(true);
						break;
					case HOEHOEHOE:
					case SPLATOON:
					case QUAKECRAFT:
						ev.setCancelled(true);
						break;

					default:
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
						break;
					}
				}
			} else {
				if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
					ev.setCancelled(true);
				}
				if (plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(damager)) {
					ev.setCancelled(true);
				}
			}
		}

	}

	private void checkArrowDamage(Player player, Player damager, EntityDamageByEntityEvent ev) {

		Location pLoc = damager.getLocation();
		Location playerLoc = player.getLocation();
		pLoc.setWorld(playerLoc.getWorld());
		Integer distancia = Double.valueOf(pLoc.distance(playerLoc)).intValue();
		if (plugin.getMatchActive().getEquipo(player) != null && plugin.getMatchActive().getEquipo(damager) != null
				&& plugin.getMatchActive().getEquipo(player).equals(plugin.getMatchActive().getEquipo(damager))) {
			ev.setCancelled(true);
		} else {
			if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())) {

				if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {

					ev.setCancelled(true);
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case SG:
					case SW:
					case TSG:
					case TSW:
					case TSG_REAL:
					case TSW_REAL:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
											.replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							plugin.getMatchActive().echaDePartida(player, true, true, false, true, true,false);
							player.setHealth(player.getMaxHealth());

						}
						break;
					case BATTLE_ROYALE:
					case BATTLE_ROYALE_CABALLO:
					case BATTLE_ROYALE_TEAM_2:
					case BATTLE_ROYALE_TEAMS:
					case HIDE_AND_SEEK:
					case PAINTBALL:
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
										.replaceAll("%victim%", player.getName())
										.replaceAll("%killer%", damager.getName()),
								false, false, false);
						UtilsRandomEvents.doCommandsKill(damager, plugin);
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
						plugin.getMatchActive().hazComandosDeMuerte(damager, player);
						plugin.getMatchActive().echaDePartida(player, true, true, false);
						player.setHealth(player.getMaxHealth());

						break;
					case GLASS_WALK:
						if (!plugin.getMatchActive().getAllowDamagePVP() || !plugin.getReventConfig().isAllowGlassWalkPvP()) {
							ev.setCancelled(true);
						} else {
							ev.setDamage(0);
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case KNOCKBACK_DUEL:
					case FISH_SLAP:
					case BLOCK_PARTY:

						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {

							ev.setDamage(0);
						}
						break;
					case ESCAPE_ARROW:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {

							ev.setDamage(0);
						}
						break;
					case ANVIL_SPLEEF:
					case BOMBARDMENT:
					case TNT_RUN:
					case WDROP:
					case SPLEEF:
					case SPLEGG:
						ev.setCancelled(true);
						break;
					case OITC:
						ev.setDamage(0);
						plugin.getMatchActive().reiniciaPlayer(player);
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

						UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
						if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
							plugin.getMatchActive().getPuntuacion().put(damager.getName(),
									plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

						} else {
							plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
						}
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
										.replaceAll("%victim%", player.getName())
										.replaceAll("%killer%", damager.getName()),
								false, false, false);
						UtilsRandomEvents.doCommandsKill(damager, plugin);
						plugin.getMatchActive().hazComandosDeMuerte(damager, player);
						damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
								+ plugin.getLanguage().getNowPoints().replace("%points%",
										plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
						damager.getInventory().addItem(XMaterial.ARROW.parseItem());
						if (plugin.getReventConfig().isOitcHealAfterKill()) {
							damager.setHealth(damager.getMaxHealth());
						}
						break;

					case TOP_KILLER:
					case TOP_KILLER_TEAM_2:
					case TOP_KILLER_TEAMS:
						ev.setDamage(0);
						plugin.getMatchActive().reiniciaPlayer(player);
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

						UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
						if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
							plugin.getMatchActive().getPuntuacion().put(damager.getName(),
									plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

						} else {
							plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
						}
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
										.replaceAll("%victim%", player.getName())
										.replaceAll("%killer%", damager.getName()),
								false, false, false);
						UtilsRandomEvents.doCommandsKill(damager, plugin);
						plugin.getMatchActive().hazComandosDeMuerte(damager, player);
						damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
								+ plugin.getLanguage().getNowPoints().replace("%points%",
										plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
						if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
							damager.setHealth(damager.getMaxHealth());
						}
						break;
					case KOTH:
						ev.setDamage(0);
						plugin.getMatchActive().reiniciaPlayer(player);
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

						UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);

						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
										.replaceAll("%victim%", player.getName())
										.replaceAll("%killer%", damager.getName()),
								false, false, false);
						UtilsRandomEvents.doCommandsKill(damager, plugin);
						plugin.getMatchActive().hazComandosDeMuerte(damager, player);
						break;

					case GEM_CRAWLER:
						UtilsRandomEvents.mandaMensaje(plugin,
								plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
										.replaceAll("%victim%", player.getName())
										.replaceAll("%killer%", damager.getName()),
								false, false, false);
						UtilsRandomEvents.doCommandsKill(damager, plugin);
						plugin.getMatchActive().hazComandosDeMuerte(damager, player);
						UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

						UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
						ev.setDamage(0);
						plugin.getMatchActive().reiniciaPlayer(player);
						break;
					case BOMB_TAG:
						ev.setDamage(0);
						if (damager.equals(plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
							UtilsRandomEvents.borraInventario(damager, plugin);
							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_HURT);
							plugin.getMatchActive().ponInventarioMatch(player,false);
							plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);

							if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
								if (player.hasPotionEffect(PotionEffectType.SPEED)) {
									player.removePotionEffect(PotionEffectType.SPEED);
								}
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
										20 * plugin.getReventConfig().getSpeedDuration(),
										plugin.getReventConfig().getTntTagSpeedHolder() - 1));
							}
							if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
								if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
									damager.removePotionEffect(PotionEffectType.SPEED);
								}
								damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
										20 * plugin.getReventConfig().getSpeedDuration(),
										plugin.getReventConfig().getTntTagSpeedRunners() - 1));
							}
							if (plugin.getMatchActive().getPlayerHandler().getPlayerContador() != null) {
								UtilsRandomEvents.removeGlow(plugin, damager,
										plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
								UtilsRandomEvents.addGlow(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayerContador(),
										plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
							}

						}
						break;
					case BOAT_RUN:
					case HORSE_RUN:
					case RACE:
					case RED_GREEN_LIGHT:
						ev.setCancelled(true);
						break;
					case ESCAPE_FROM_BEAST:
						if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
							ev.setCancelled(true);

						} else {
							if (damager == null
									|| damager.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())
									|| player.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())) {
								if (player.getName()
										.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())) {
									plugin.getMatchActive().getPlayerHandler().setPlayerContador(damager);
								}
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
												.replaceAll("%victim%", player.getName()).replaceAll("%killer%",
														damager.getName()),
										false, false, false);
								UtilsRandomEvents.doCommandsKill(damager, plugin);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
								plugin.getMatchActive().hazComandosDeMuerte(damager, player);
								plugin.getMatchActive().echaDePartida(player, true, true, false);
								player.setHealth(player.getMaxHealth());

							} else {
								ev.setCancelled(true);
							}
						}

						break;
					case HOEHOEHOE:
					case SPLATOON:
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
					case TSG_REAL:
					case TSW_REAL:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case BATTLE_ROYALE:
					case BATTLE_ROYALE_CABALLO:
					case BATTLE_ROYALE_TEAM_2:
					case BATTLE_ROYALE_TEAMS:
					case HIDE_AND_SEEK:
					case PAINTBALL:
					case TOP_KILLER:
					case TOP_KILLER_TEAM_2:
					case TOP_KILLER_TEAMS:
					case PAINTBALL_TOP_KILL:
					case KOTH:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;

					case OITC:
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}

						if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())
								&& !player.getName().equals(damager.getName())) {
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
											.replaceAll("%victim%", player.getName())

											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(),
										plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
							}

							damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
							damager.getInventory().addItem(XMaterial.ARROW.parseItem());
							if (plugin.getReventConfig().isOitcHealAfterKill()) {
								damager.setHealth(damager.getMaxHealth());
							}

						}

						break;
					case ESCAPE_FROM_BEAST:
						if (plugin.getMatchActive().getPlayerHandler().getBeast() == null) {
							ev.setCancelled(true);

						} else {

							if (damager == null
									|| damager.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())
									|| player.getName()
											.equals(plugin.getMatchActive().getPlayerHandler().getBeast().getName())) {
								if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
									ev.setCancelled(false);
								}

							} else {
								ev.setCancelled(true);
							}
						}

						break;
					case GLASS_WALK:
						if (!plugin.getMatchActive().getAllowDamagePVP() || !plugin.getReventConfig().isAllowGlassWalkPvP()) {
							ev.setCancelled(true);
						} else {
							ev.setDamage(0);
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case KNOCKBACK_DUEL:
					case ESCAPE_ARROW:
					case FISH_SLAP:
					case BLOCK_PARTY:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							ev.setDamage(0);
							if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
								ev.setCancelled(false);
							}
						}
						break;
					case ANVIL_SPLEEF:
					case BOMBARDMENT:
					case TNT_RUN:
					case WDROP:
					case SPLEEF:
					case SPLEGG:
						ev.setCancelled(true);
						break;
					case BOMB_TAG:
						ev.setDamage(0);
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
						if (damager.equals(plugin.getMatchActive().getPlayerHandler().getPlayerContador())) {
							UtilsRandomEvents.borraInventario(damager, plugin);
							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_HURT);
							plugin.getMatchActive().ponInventarioMatch(player,false);
							plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);
							if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
								if (player.hasPotionEffect(PotionEffectType.SPEED)) {
									player.removePotionEffect(PotionEffectType.SPEED);
								}
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
										20 * plugin.getReventConfig().getSpeedDuration(),
										plugin.getReventConfig().getTntTagSpeedHolder() - 1));
							}
							if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
								if (damager.hasPotionEffect(PotionEffectType.SPEED)) {
									damager.removePotionEffect(PotionEffectType.SPEED);
								}
								damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
										20 * plugin.getReventConfig().getSpeedDuration(),
										plugin.getReventConfig().getTntTagSpeedRunners() - 1));
							}
							if (plugin.getMatchActive().getPlayerHandler().getPlayerContador() != null) {
								UtilsRandomEvents.removeGlow(plugin, damager,
										plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
								UtilsRandomEvents.addGlow(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayerContador(),
										plugin.getMatchActive().getPlayerHandler().getPlayersTotalObj());
							}

						}
						break;
					case BOAT_RUN:
					case HORSE_RUN:
					case RACE:
					case RED_GREEN_LIGHT:
						ev.setCancelled(true);
						break;
					case HOEHOEHOE:
					case SPLATOON:
					case QUAKECRAFT:
						ev.setCancelled(true);
						break;
					default:
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
						break;
					}
				}
			} else {
				if (plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(damager)) {
					ev.setCancelled(true);
				}
			}
		}

	}

	private void checkSnowBallDamage(Player player, Player damager, EntityDamageByEntityEvent ev) {

		Location pLoc = damager.getLocation();
		Location playerLoc = player.getLocation();
		pLoc.setWorld(playerLoc.getWorld());
		Integer distancia = Double.valueOf(pLoc.distance(playerLoc)).intValue();
		if (plugin.getMatchActive().getEquipo(player) != null && plugin.getMatchActive().getEquipo(damager) != null
				&& plugin.getMatchActive().getEquipo(player).equals(plugin.getMatchActive().getEquipo(damager))) {
			ev.setCancelled(true);
		} else {
			if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(damager.getName())) {

				if (((player.getHealth() - ev.getFinalDamage()) <= 0)) {

					ev.setCancelled(true);
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case PAINTBALL:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {

							if (((player.getHealth() - plugin.getReventConfig().getSnowballsDamage()) <= 0)) {
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
												.replaceAll("%victim%", player.getName()).replaceAll("%killer%",
														damager.getName()),
										false, false, false);
								UtilsRandomEvents.doCommandsKill(damager, plugin);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
								plugin.getMatchActive().hazComandosDeMuerte(damager, player);
								plugin.getMatchActive().echaDePartida(player, true, true, false, true, false,false);
								player.setHealth(player.getMaxHealth());
							} else {
								ev.setCancelled(true);
								player.damage(plugin.getReventConfig().getSnowballsDamage() * 1.0);
							}

						}
						break;
					case PAINTBALL_TOP_KILL:
						if (((player.getHealth() - plugin.getReventConfig().getSnowballsDamage()) <= 0)) {
							ev.setDamage(0);
							plugin.getMatchActive().reiniciaPlayer(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

							UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
							if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(),
										plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

							} else {
								plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
							}
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
											.replaceAll("%killer%", damager.getName()),
									false, false, false);
							UtilsRandomEvents.doCommandsKill(damager, plugin);
							plugin.getMatchActive().hazComandosDeMuerte(damager, player);
							damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getNowPoints().replace("%points%",
											plugin.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
							if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
								damager.setHealth(damager.getMaxHealth());
							}
						} else {
							ev.setCancelled(true);
							player.damage(plugin.getReventConfig().getSnowballsDamage() * 1.0);
						}
						break;
					case SPLEEF:
						if (plugin.getReventConfig().isAvoidSnowballKnockback()) {
							ev.setCancelled(true);
						}
						break;
					default:
						break;
					}
				} else {
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case PAINTBALL:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							if (((player.getHealth() - plugin.getReventConfig().getSnowballsDamage()) <= 0)) {
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getBowKill().replaceAll("%distance%", "" + distancia)
												.replaceAll("%victim%", player.getName()).replaceAll("%killer%",
														damager.getName()),
										false, false, false);
								UtilsRandomEvents.doCommandsKill(damager, plugin);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
								UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
								plugin.getMatchActive().hazComandosDeMuerte(damager, player);
								plugin.getMatchActive().echaDePartida(player, true, true, false, true, false,false);
								player.setHealth(player.getMaxHealth());
							} else {
								ev.setCancelled(true);
								player.damage(plugin.getReventConfig().getSnowballsDamage() * 1.0);
								// ev.setDamage(plugin.getReventConfig().getSnowballsDamage());
							}

						}
						break;
					case SPLEEF:
						if (plugin.getReventConfig().isAvoidSnowballKnockback()) {
							ev.setCancelled(true);
						}
						break;
					case PAINTBALL_TOP_KILL:
						if (!plugin.getMatchActive().getAllowDamagePVP()) {
							ev.setCancelled(true);
						} else {
							if (((player.getHealth() - plugin.getReventConfig().getSnowballsDamage()) <= 0)) {
								ev.setDamage(0);
								plugin.getMatchActive().reiniciaPlayer(player);
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

								UtilsRandomEvents.playSound(plugin, damager, XSound.ENTITY_PLAYER_LEVELUP);
								if (plugin.getMatchActive().getPuntuacion().containsKey(damager.getName())) {
									plugin.getMatchActive().getPuntuacion().put(damager.getName(),
											plugin.getMatchActive().getPuntuacion().get(damager.getName()) + 1);

								} else {
									plugin.getMatchActive().getPuntuacion().put(damager.getName(), 1);
								}
								UtilsRandomEvents.mandaMensaje(plugin,
										plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
										plugin.getLanguage().getPvpKill().replaceAll("%victim%", player.getName())
												.replaceAll("%killer%", damager.getName()),
										false, false, false);
								UtilsRandomEvents.doCommandsKill(damager, plugin);
								plugin.getMatchActive().hazComandosDeMuerte(damager, player);
								damager.sendMessage(plugin.getLanguage().getTagPlugin() + " "
										+ plugin.getLanguage().getNowPoints().replace("%points%", plugin
												.getMatchActive().getPuntuacion().get(damager.getName()).toString()));
								if (plugin.getReventConfig().isTopKillerHealAfterKill()) {
									damager.setHealth(damager.getMaxHealth());
								}
							} else {
								ev.setCancelled(true);
								player.damage(plugin.getReventConfig().getSnowballsDamage() * 1.0);
								// ev.setDamage(plugin.getReventConfig().getSnowballsDamage());
							}
						}
						break;
					default:
						if (plugin.getReventConfig().isHighestPriorityDamageEvents()) {
							ev.setCancelled(false);
						}
						break;
					}
				}
			} else {
				if (plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(damager)) {
					ev.setCancelled(true);
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
