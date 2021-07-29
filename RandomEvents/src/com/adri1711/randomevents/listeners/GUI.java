package com.adri1711.randomevents.listeners;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Kit;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XSound;

public class GUI implements Listener {

	private RandomEvents plugin;

	public GUI(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player) {
			try {
				Player p = (Player) event.getWhoClicked();
				if (event.getCurrentItem() != null) {
					if (plugin.getMatchActive() != null
							&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())) {
						if ( event.getSlotType() != null && (event.getSlotType() == InventoryType.SlotType.ARMOR)) {
							switch(plugin.getMatchActive().getMatch().getMinigame()){
							case PAINTBALL:
							case SPLATOON:
							case BATTLE_ROYALE_TEAMS:
							case TOP_KILLER_TEAMS:
							case TSW_REAL:
							case TSG_REAL:
							case HOEHOEHOE:
								event.setCancelled(true);
								break;
							default:
								break;
							}
							
						}
					}
				}
			} catch (Throwable e) {
				System.out.println(e);
			}
		}

		if (ChatColor.stripColor(plugin.getApi().getInventoryName(event)) != null
				&& ChatColor.stripColor(plugin.getApi().getInventoryName(event))
						.contains(ChatColor.stripColor(plugin.getLanguage().getStatsGuiName()))) {
			event.setCancelled(true);
		} else if (ChatColor.stripColor(plugin.getApi().getInventoryName(event)) != null
				&& ChatColor.stripColor(plugin.getApi().getInventoryName(event))
						.contains(ChatColor.stripColor(plugin.getLanguage().getCreditsGuiName()))) {
			useCreditsGui(event);
		} else if (ChatColor.stripColor(plugin.getApi().getInventoryName(event)) != null
				&& ChatColor.stripColor(plugin.getApi().getInventoryName(event))
						.contains(ChatColor.stripColor(plugin.getLanguage().getKitGuiName()))) {
			useKitGUI(event);

		} else if (ChatColor.stripColor(plugin.getApi().getInventoryName(event)) != null
				&& ChatColor.stripColor(plugin.getApi().getInventoryName(event))
						.contains(ChatColor.stripColor(plugin.getLanguage().getTeamGuiName()))) {
			useTeamGUI(event);

		}
	}

	private void useCreditsGui(InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player) {
			event.setCancelled(true);
			try {
				Player p = (Player) event.getWhoClicked();
				if (event.getCurrentItem() != null) {
					if (plugin.getMatchActive() != null
							&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())) {
						if (event.getSlotType() != null && (event.getSlotType() == InventoryType.SlotType.ARMOR)) {
							event.setCancelled(true);
						}
					}

					if (event.getCurrentItem().getType() != null
							&& event.getCurrentItem().getType().equals(XMaterial.OAK_SIGN.parseMaterial())) {
						String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
						Integer page = Integer
								.valueOf(itemName.split(plugin.getLanguage().getCreditsGuiPage())[1].trim());
						UtilsSQL.getCreditsGUI(p, page, plugin);
					} else {
						ItemStack item = event.getCurrentItem();
						if (item.hasItemMeta()) {
							ItemMeta itemMeta = item.getItemMeta();
							String itemName = itemMeta.getDisplayName();
							if (p.hasPermission(Constantes.PERM_COOLDOWN_BYPASS)) {
								if (plugin.getMatchActive() != null) {
									p.sendMessage(plugin.getLanguage().getTagPlugin()
											+ plugin.getLanguage().getAlreadyMatch());

								} else {
									Match m = UtilsRandomEvents.searchEvent(ChatColor.stripColor(itemName), plugin);
									if (m != null) {
										plugin.setForzado(Boolean.TRUE);
										plugin.setMatchActive(new MatchActive(m, plugin, true));
										p.closeInventory();

									}

								}
							} else if (p.hasPermission(Constantes.PERM_COOLDOWN)) {
								if (plugin.getMatchActive() != null) {
									p.sendMessage(plugin.getLanguage().getTagPlugin()
											+ plugin.getLanguage().getAlreadyMatch());

								} else {

									if (plugin.getCooldowns().containsKey(p.getName())) {
										if (plugin.getCooldowns().get(p.getName()).before(new Date())) {
											plugin.getCooldowns().remove(p.getName());
											Match m = UtilsRandomEvents.searchEvent(ChatColor.stripColor(itemName),
													plugin);
											if (m != null) {
												plugin.setForzado(Boolean.TRUE);
												plugin.setMatchActive(new MatchActive(m, plugin, true));
												p.closeInventory();
												Calendar c = Calendar.getInstance();
												c.setTime(new Date());
												c.add(Calendar.SECOND, plugin.getReventConfig().getCooldownUsersBeginEvents());
												plugin.getCooldowns().put(p.getName(), c.getTime());

											}

										} else {
											if (plugin.getReventConfig().isMysqlEnabled()) {
												String credits = itemMeta.getLore().get(0);
												if (credits.equals(plugin.getLanguage().getCreditsCooldown())) {
													Date now = new Date();
													Date cooldown = plugin.getCooldowns().get(p.getName());
													p.sendMessage(plugin.getLanguage().getTagPlugin()
															+ plugin.getLanguage().getCreditsCooldown() + " "
															+ UtilsRandomEvents.calculateTimeHoursPoints(
																	(cooldown.getTime() - now.getTime()) / 1000));
												} else {
													String languageCredits = plugin.getLanguage().getCreditsBal();
													languageCredits = ChatColor.stripColor(languageCredits);
													credits = ChatColor.stripColor(credits);
													String[] splitLanguage = languageCredits.split("%credits%");

													String aux = "";
													if (splitLanguage[0] != null && !splitLanguage[0].isEmpty()) {
														aux = credits.split(splitLanguage[0])[1];
														if (aux == null || aux.isEmpty()) {
															credits = credits.split(splitLanguage[0])[0];
														} else {
															credits = aux;

														}
													}
													if (splitLanguage.length > 1 && splitLanguage[1] != null
															&& !splitLanguage[1].isEmpty()) {
														aux = credits.split(splitLanguage[1])[0];

														if (aux == null || aux.isEmpty()) {
															credits = credits.split(splitLanguage[1])[1];
														} else {
															credits = aux;

														}
													}

													Integer amount = Integer.valueOf(credits);
													if (amount > 0) {
														Match m = UtilsRandomEvents
																.searchEvent(ChatColor.stripColor(itemName), plugin);
														if (m != null) {
															UtilsSQL.removeCredits(p, m.getName(), plugin);
															plugin.setForzado(Boolean.TRUE);
															plugin.setMatchActive(new MatchActive(m, plugin, true));
															p.closeInventory();
														}
													}
												}
											} else {
												Date now = new Date();
												Date cooldown = plugin.getCooldowns().get(p.getName());
												p.sendMessage(plugin.getLanguage().getTagPlugin()
														+ plugin.getLanguage().getCreditsCooldown() + " "
														+ UtilsRandomEvents.calculateTimeHoursPoints(
																(cooldown.getTime() - now.getTime()) / 1000));
											}
										}

									} else {
										Match m = UtilsRandomEvents.searchEvent(ChatColor.stripColor(itemName), plugin);
										if (m != null) {
											plugin.setForzado(Boolean.TRUE);
											plugin.setMatchActive(new MatchActive(m, plugin, true));
											p.closeInventory();
											Calendar c = Calendar.getInstance();
											c.setTime(new Date());
											c.add(Calendar.SECOND, plugin.getReventConfig().getCooldownUsersBeginEvents());
											plugin.getCooldowns().put(p.getName(), c.getTime());
										}
									}

								}
							} else if (plugin.getReventConfig().isMysqlEnabled()) {
								String credits = itemMeta.getLore().get(0);
								String languageCredits = plugin.getLanguage().getCreditsBal();
								languageCredits = ChatColor.stripColor(languageCredits);
								credits = ChatColor.stripColor(credits);
								String[] splitLanguage = languageCredits.split("%credits%");

								String aux = "";
								if (splitLanguage[0] != null && !splitLanguage[0].isEmpty()) {
									aux = credits.split(splitLanguage[0])[1];
									if (aux == null || aux.isEmpty()) {
										credits = credits.split(splitLanguage[0])[0];
									} else {
										credits = aux;

									}
								}
								if (splitLanguage.length > 1 && splitLanguage[1] != null
										&& !splitLanguage[1].isEmpty()) {
									aux = credits.split(splitLanguage[1])[0];

									if (aux == null || aux.isEmpty()) {
										credits = credits.split(splitLanguage[1])[1];
									} else {
										credits = aux;

									}
								}

								Integer amount = Integer.valueOf(credits);
								if (amount > 0) {
									Match m = UtilsRandomEvents.searchEvent(ChatColor.stripColor(itemName), plugin);
									if (m != null) {
										UtilsSQL.removeCredits(p, m.getName(), plugin);
										plugin.setForzado(Boolean.TRUE);
										plugin.setMatchActive(new MatchActive(m, plugin, true));
										p.closeInventory();
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void useKitGUI(InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player) {

			event.setCancelled(true);
			try {
				Player p = (Player) event.getWhoClicked();

				if (event.getCurrentItem() != null) {

					if (event.getCurrentItem().getType() != null
							&& event.getCurrentItem().getType().equals(XMaterial.OAK_SIGN.parseMaterial())) {

						String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
						Integer page = Integer
								.valueOf(itemName.split(plugin.getLanguage().getCreditsGuiPage())[1].trim());
						UtilsRandomEvents.createGUIKits(p, page, plugin, plugin.getMatchActive());
					} else {

						ItemStack item = event.getCurrentItem();
						if (item.hasItemMeta()) {

							List<Kit> kits = UtilsRandomEvents.kitsAvailable(p,
									plugin.getMatchActive().getMatch().getKits(), plugin);
							Integer pos = event.getInventory().first(item);

							if (pos < kits.size()) {
								Kit kit = kits.get(pos);
								plugin.getMatchActive().getPlayerHandler().getPlayerKits().put(p, kit);
								p.sendMessage(plugin.getLanguage().getTagPlugin()
										+ plugin.getLanguage().getKitChosen().replaceAll("%kit_name%", kit.getName()));
								p.closeInventory();
								UtilsRandomEvents.playSound(plugin,p, XSound.ENTITY_PLAYER_LEVELUP);
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void useTeamGUI(InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player) {

			event.setCancelled(true);
			try {
				Player p = (Player) event.getWhoClicked();

				if (event.getCurrentItem() != null) {

					ItemStack item = event.getCurrentItem();
					if (item.hasItemMeta()) {

						Integer equipoActual = plugin.getMatchActive().getEquipo(p);

						Integer pos = event.getInventory().first(item);

						if (pos < plugin.getMatchActive().getMatch().getNumberOfTeams()) {
							if (equipoActual != null) {
								plugin.getMatchActive().getPlayerHandler().getEquipos().get(equipoActual).remove(p);
								plugin.getMatchActive().getPlayerHandler().getTeamsCopy().get(equipoActual).remove(p);
							}
							if (plugin.getMatchActive().getPlayerHandler().getEquipos().containsKey(pos)) {
								plugin.getMatchActive().getPlayerHandler().getEquipos().get(pos).add(p);
								plugin.getMatchActive().getPlayerHandler().getTeamsCopy().get(pos).add(p);
							} else {
								plugin.getMatchActive().getPlayerHandler().getEquipos().put(pos, new HashSet<Player>());
								plugin.getMatchActive().getPlayerHandler().getTeamsCopy().put(pos,
										new HashSet<Player>());
								plugin.getMatchActive().getPlayerHandler().getEquipos().get(pos).add(p);
								plugin.getMatchActive().getPlayerHandler().getTeamsCopy().get(pos).add(p);

							}

							p.closeInventory();
							UtilsRandomEvents.playSound(plugin,p, XSound.ENTITY_PLAYER_LEVELUP);
							plugin.getMatchActive().updateTeamItem(p);

						}
					}

				}
			} catch (Exception e) {
				System.out.println(e);
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
