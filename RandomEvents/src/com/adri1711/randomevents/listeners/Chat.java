package com.adri1711.randomevents.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Kit;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.WaterDropStep;
import com.adri1711.randomevents.match.enums.Creacion;
import com.adri1711.randomevents.match.enums.CreacionKit;
import com.adri1711.randomevents.match.enums.CreacionWaterDrop;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.utils.InventoryPers;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.XMaterial;

public class Chat implements Listener {

	private RandomEvents plugin;

	public Chat(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {

		Player p = event.getPlayer();
		String aliase = event.getMessage().split(" ")[0];
		aliase = aliase.replaceAll("/", "");
		String[] fields = event.getMessage().split(" ");

		List<String> campos = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0) {
				campos.add(fields[i]);
			}
		}

		if (aliase.equalsIgnoreCase(plugin.getReventConfig().getCmdAlias())) {
			event.setCancelled(true);
			plugin.onCommand(event.getPlayer(), null, "revent", campos.toArray(new String[campos.size()]));
		} else {

			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(p)) {

				if (!plugin.getReventConfig().getAllowedCmds().contains(aliase.toLowerCase()) && !p.hasPermission("randomevent.bypass")) {
					event.setCancelled(true);
					p.sendMessage(plugin.getLanguage().getCmdNotAllowed());
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (plugin.getPlayerWaterDrop().containsKey(player.getName())) {
			if (plugin.getReventConfig().isDebugMode()) {
				System.out.println("RandomEvents :: Message of creation: " + event.getMessage());
			}
			checkMessageCreationWaterDrop(ChatColor.stripColor(event.getMessage().trim()), player,
					plugin.getPlayersCreationWaterDrop().get(player.getName()));

			event.setCancelled(Boolean.TRUE);

		} else if (plugin.getPlayerKit().containsKey(player.getName())) {
			if (plugin.getReventConfig().isDebugMode()) {
				System.out.println("RandomEvents :: Message of creation: " + event.getMessage());
			}

			checkMessageCreationKit(ChatColor.stripColor(event.getMessage().trim()), player,
					plugin.getPlayersCreationKit().get(player.getName()));

			event.setCancelled(Boolean.TRUE);

		} else if (plugin.getPlayerMatches().containsKey(player.getName())) {
			if (plugin.getReventConfig().isDebugMode()) {
				System.out.println("RandomEvents :: Message of creation: " + event.getMessage());
			}

			checkMessageCreation(ChatColor.stripColor(event.getMessage().trim()), player,
					plugin.getPlayersCreation().get(player.getName()));

			event.setCancelled(Boolean.TRUE);

		} else {
			if (plugin.getReventConfig().isMatchPrivateMatch() && plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
				event.getRecipients().clear();
				event.getRecipients().addAll(plugin.getMatchActive().getPlayerHandler().getPlayersSpectators());

				for (Player pla : Bukkit.getOnlinePlayers()) {
					if (pla.hasPermission("randomevent.chat.spy")) {
						event.getRecipients().add(pla);
					}
				}

				event.setFormat(plugin.getLanguage().getTagChat() + event.getFormat());
			}
		}
	}

	private void checkMessageCreationKit(String message, Player player, Integer position) {
		Kit kit = plugin.getPlayerKit().get(player.getName());
		Boolean actualiza = Boolean.TRUE;
		Boolean actua = Boolean.TRUE;
		Boolean pasado = Boolean.FALSE;
		if (kit == null)
			kit = new Kit();

		CreacionKit c = null;
		if (position != null) {
			c = CreacionKit.getByPosition(position);
		} else {
			try {
				position = Integer.valueOf(message);
				if (CreacionKit.getByPosition(Integer.valueOf(message)) != null) {
					c = CreacionKit.getByPosition(Integer.valueOf(message));
					actua = Boolean.FALSE;
				}
			} catch (Exception e) {
			}
		}

		if (c != null) {
			if (actua) {
				try {

					switch (c) {
					case NAME:
						kit.setName(UtilsRandomEvents.cambiarMensajeConEtiqueta(message).trim());
						plugin.getPlayersCreationKit().remove(player.getName());

						break;

					case PERMISSION_OPTIONAL:
						kit.setPermission(ChatColor.stripColor(message).trim());
						plugin.getPlayersCreationKit().remove(player.getName());

						break;
					case ITEM_DESCRIPTIVE:
						if (message.equals(Constantes.DONE)) {
							kit.setItem(player.getItemInHand());
							plugin.getPlayersCreationKit().remove(player.getName());
						}
						break;
					case INVENTORY:
						if (message.equals(Constantes.DONE)) {

							ItemStack[] contenido = player.getInventory().getContents();
							List<ItemStack> contenidoList = Arrays.asList(contenido);
							try {
								contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
							} catch (Exception e) {

							}
							ItemStack[] arrayContenido = new ItemStack[contenidoList.size()];
							arrayContenido = contenidoList.toArray(arrayContenido);
							InventoryPers inv = new InventoryPers();
							inv.setContents(arrayContenido);
							inv.setHelmet(player.getInventory().getHelmet());
							inv.setBoots(player.getInventory().getBoots());
							inv.setLeggings(player.getInventory().getLeggings());
							inv.setChestplate(player.getInventory().getChestplate());
							kit.setInventory(inv);

							plugin.getPlayersCreationKit().remove(player.getName());

						}
						break;

					case SAVE:

						if (actua) {
							try {
								if (message.toUpperCase().equals("Y")) {

									String s = UtilsRandomEvents.compruebaKitCorrecto(kit, plugin);

									if (s != null) {
										player.sendMessage(s);
										actua = Boolean.FALSE;
									} else {

										UtilsRandomEvents.terminaCreacionKit(plugin, player, null);

										actualiza = Boolean.FALSE;
									}
								} else {

									plugin.getPlayersCreationKit().remove(player.getName());
									actua = Boolean.TRUE;

								}

							} catch (Exception e) {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								player.sendMessage(c.getMessage());
								actua = Boolean.FALSE;

							}
						} else {
							if (plugin.getPlayerKit().keySet().contains(player.getName())) {
								player.sendMessage(c.getMessage());
								plugin.getPlayersCreationKit().put(player.getName(), c.getPosition());
								actua = Boolean.FALSE;
							} else {
								String s = UtilsRandomEvents.compruebaKitCorrecto(kit, plugin);
								if (s != null) {
									player.sendMessage(s);
								} else {

									UtilsRandomEvents.terminaCreacionKit(plugin, player, null);

									actualiza = Boolean.FALSE;
								}
							}

						}

						break;
					case CANCEL:
						if (message.toUpperCase().equals("Y")) {

							plugin.getPlayerKit().remove(player.getName());
							plugin.getPlayersCreationKit().remove(player.getName());

							actualiza = Boolean.FALSE;

						} else {

							plugin.getPlayersCreationKit().remove(player.getName());
							actua = Boolean.TRUE;

						}

						break;
					case DELETE:
						try {
							Integer valor = Integer.valueOf(message);

							CreacionKit delete = null;

							try {
								if (CreacionKit.getByPosition(valor) != null) {
									delete = CreacionKit.getByPosition(valor);
									actua = Boolean.FALSE;
								}
							} catch (Exception e) {
							}
							switch (delete) {

							case NAME:
								kit.setName(null);
								break;
							case PERMISSION_OPTIONAL:
								kit.setPermission(null);
								break;
							case INVENTORY:
								kit.setInventory(null);
								break;
							case ITEM_DESCRIPTIVE:
								kit.setItem(XMaterial.CHEST.parseItem());
								break;
							default:
								break;
							}
							actua = Boolean.TRUE;

							plugin.getPlayersCreationKit().remove(player.getName());

						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
							player.sendMessage(c.getMessage());
							actua = Boolean.FALSE;

						}

						break;
					default:
						break;
					}
				} catch (Exception e) {
					player.sendMessage(plugin.getLanguage().getInvalidInput());
					switch (CreacionKit.getByPosition(position)) {

					default:
						player.sendMessage(c.getMessage());
						break;
					}
					actua = Boolean.FALSE;
				}
			} else {
				// pasado = Boolean.TRUE;
				actua = Boolean.FALSE;
				player.sendMessage(Constantes.SALTO_LINEA);
				switch (CreacionKit.getByPosition(position)) {

				default:
					plugin.getPlayersCreationKit().put(player.getName(), c.getPosition());

					break;
				}
			}
		}

		if (actualiza) {
			plugin.getPlayerKit().put(player.getName(), kit);
			plugin.getPlayerKit().put(player.getName(), kit);
			if (actua) {
				player.sendMessage(UtilsRandomEvents.enviaInfoCreacionKit(kit, player, plugin));
			} else {
				c = CreacionKit.getByPosition(plugin.getPlayersCreationKit().get(player.getName()));
				if (c != null && !pasado) {
					player.sendMessage(c.getMessage());
				}
			}
		} else {
			plugin.getPlayerKit().remove(player.getName());
			plugin.getPlayersCreationKit().remove(player.getName());
			if (plugin.getPlayerMatches().containsKey(player.getName())) {
				player.sendMessage(Creacion.KITS.getMessage());
				for (Kit m : plugin.getKits()) {
					player.sendMessage("§6§l" + plugin.getKits().indexOf(m) + " - " + m.getName());
				}
				player.sendMessage("§6§l" + plugin.getKits().size() + " - §e§lNew Kit");
			}

		}
	}

	private void checkMessageCreationWaterDrop(String message, Player player, Integer position) {
		WaterDropStep waterDrop = plugin.getPlayerWaterDrop().get(player.getName());
		Boolean actualiza = Boolean.TRUE;
		Boolean actua = Boolean.TRUE;
		Boolean pasado = Boolean.FALSE;
		if (waterDrop == null)
			waterDrop = new WaterDropStep();

		CreacionWaterDrop c = null;
		if (position != null) {
			c = CreacionWaterDrop.getByPosition(position);
		} else {
			try {
				position = Integer.valueOf(message);
				if (CreacionWaterDrop.getByPosition(Integer.valueOf(message)) != null) {
					c = CreacionWaterDrop.getByPosition(Integer.valueOf(message));
					actua = Boolean.FALSE;
				}
			} catch (Exception e) {
			}
		}

		if (c != null) {
			if (actua) {
				try {

					switch (c) {
					case NAME:
						waterDrop.setName(UtilsRandomEvents.cambiarMensajeConEtiqueta(message).trim());
						plugin.getPlayersCreationWaterDrop().remove(player.getName());

						break;

					case SPAWN:
						if (message.equals(Constantes.DONE)) {
							waterDrop.setSpawn(player.getLocation());
							plugin.getPlayersCreationWaterDrop().remove(player.getName());

						}
						break;
					case GOAL_LOCATION1:
						if (message.equals(Constantes.DONE)) {
							waterDrop.setGoalLoc1(player.getLocation());
							plugin.getPlayersCreationWaterDrop().remove(player.getName());
						}
						break;
					case GOAL_LOCATION2:
						if (message.equals(Constantes.DONE)) {
							waterDrop.setGoalLoc2(player.getLocation());
							plugin.getPlayersCreationWaterDrop().remove(player.getName());
						}
						break;
					case SAVE:

						if (actua) {
							try {
								if (message.toUpperCase().equals("Y")) {

									String s = UtilsRandomEvents.compruebaWaterDropCorrecto(waterDrop, plugin);

									if (s != null) {
										player.sendMessage(s);
										actua = Boolean.FALSE;
									} else {

										UtilsRandomEvents.terminaCreacionWD(plugin, player);

										actualiza = Boolean.FALSE;
									}
								} else {

									plugin.getPlayersCreationWaterDrop().remove(player.getName());
									actua = Boolean.TRUE;

								}

							} catch (Exception e) {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								player.sendMessage(c.getMessage());
								actua = Boolean.FALSE;

							}
						} else {
							if (plugin.getPlayerWaterDrop().keySet().contains(player.getName())) {
								player.sendMessage(c.getMessage());
								plugin.getPlayersCreationWaterDrop().put(player.getName(), c.getPosition());
								actua = Boolean.FALSE;
							} else {
								String s = UtilsRandomEvents.compruebaWaterDropCorrecto(waterDrop, plugin);
								if (s != null) {
									player.sendMessage(s);
								} else {

									UtilsRandomEvents.terminaCreacionMatch(plugin, player);

									actualiza = Boolean.FALSE;
								}
							}

						}

						break;
					case CANCEL:
						if (message.toUpperCase().equals("Y")) {

							plugin.getPlayerWaterDrop().remove(player.getName());
							plugin.getPlayersCreationWaterDrop().remove(player.getName());

							actualiza = Boolean.FALSE;

						} else {

							plugin.getPlayersCreationWaterDrop().remove(player.getName());
							actua = Boolean.TRUE;

						}

						break;
					case DELETE:
						try {
							Integer valor = Integer.valueOf(message);

							CreacionWaterDrop delete = null;

							try {
								if (CreacionWaterDrop.getByPosition(valor) != null) {
									delete = CreacionWaterDrop.getByPosition(valor);
									actua = Boolean.FALSE;
								}
							} catch (Exception e) {
							}
							switch (delete) {

							case NAME:
								waterDrop.setName(null);
								break;
							case SPAWN:
								waterDrop.setSpawn(null);
								break;
							case GOAL_LOCATION1:
								waterDrop.setGoalLoc1(null);
								break;
							case GOAL_LOCATION2:
								waterDrop.setGoalLoc2(null);
								break;
							default:
								break;
							}
							actua = Boolean.TRUE;

							plugin.getPlayersCreationWaterDrop().remove(player.getName());

						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
							player.sendMessage(c.getMessage());
							actua = Boolean.FALSE;

						}

						break;
					default:
						break;
					}
				} catch (Exception e) {
					player.sendMessage(plugin.getLanguage().getInvalidInput());
					switch (CreacionWaterDrop.getByPosition(position)) {

					default:
						player.sendMessage(c.getMessage());
						break;
					}
					actua = Boolean.FALSE;
				}
			} else {
				// pasado = Boolean.TRUE;
				actua = Boolean.FALSE;
				player.sendMessage(Constantes.SALTO_LINEA);
				switch (CreacionWaterDrop.getByPosition(position)) {

				default:
					plugin.getPlayersCreationWaterDrop().put(player.getName(), c.getPosition());

					break;
				}
			}
		}

		if (actualiza) {
			plugin.getPlayerWaterDrop().put(player.getName(), waterDrop);
			plugin.getPlayerWaterDrop().put(player.getName(), waterDrop);
			if (actua) {
				player.sendMessage(UtilsRandomEvents.enviaInfoCreacionWaterDrop(waterDrop, player, plugin));
			} else {
				c = CreacionWaterDrop.getByPosition(plugin.getPlayersCreationWaterDrop().get(player.getName()));
				if (c != null && !pasado) {
					player.sendMessage(c.getMessage());
				}
			}
		} else {
			plugin.getPlayerWaterDrop().remove(player.getName());
			plugin.getPlayersCreationWaterDrop().remove(player.getName());
			player.sendMessage(Creacion.WATER_DROP_SCENES.getMessage());
			for (WaterDropStep m : plugin.getWaterDrops()) {
				player.sendMessage("§6§l" + plugin.getWaterDrops().indexOf(m) + " - " + m.getName());
			}
			player.sendMessage("§6§l" + plugin.getWaterDrops().size() + " - §e§lNew Water Drop");

		}
	}

	private void checkMessageCreation(String message, Player player, Integer position) {
		Match match = plugin.getPlayerMatches().get(player.getName());
		Boolean actualiza = Boolean.TRUE;
		Boolean actua = Boolean.TRUE;
		Boolean pasado = Boolean.FALSE;
		if (match == null)
			match = new Match();

		Creacion c = null;
		if (position != null) {
			c = Creacion.getByPosition(position);
		} else {
			try {
				position = Integer.valueOf(message);
				if (Creacion.getByPosition(Integer.valueOf(message)) != null) {
					c = Creacion.getByPosition(Integer.valueOf(message));
					actua = Boolean.FALSE;
				}
			} catch (Exception e) {
			}
		}

		if (c != null) {
			if (actua) {
				try {

					switch (c) {
					case BATTLE_NAME:
						match.setName(UtilsRandomEvents.cambiarMensajeConEtiqueta(message).trim());
						plugin.getPlayersCreation().remove(player.getName());

						break;

					case AMOUNT_PLAYERS:
						match.setAmountPlayers(Integer.valueOf(message.trim()));
						match.setSpawns(new ArrayList<Location>());

						plugin.getPlayersCreation().remove(player.getName());

						break;
					case AMOUNT_PLAYERS_MIN:
						match.setAmountPlayersMin(Integer.valueOf(message.trim()));
						plugin.getPlayersCreation().remove(player.getName());

						break;
					case NUMBER_OF_TEAMS:
						Integer number = Integer.valueOf(message.trim());
						if (number > 1 && number < 9) {
							match.setNumberOfTeams(number);
							plugin.getPlayersCreation().remove(player.getName());
						} else {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
						}

						break;
					case SPAWN_PLAYER:
						if (message.equals(Constantes.DONE)) {
							match.setPlayerSpawn(player.getLocation());
							plugin.getPlayersCreation().remove(player.getName());

						}
						break;
					case SPAWN_BEAST:
						if (message.equals(Constantes.DONE)) {
							match.setBeastSpawn(player.getLocation());
							plugin.getPlayersCreation().remove(player.getName());

						}
						break;
					case ARENA_SPAWNS:
						if (message.equals(Constantes.DONE)) {
							if (match.getSpawns().size() != match.getAmountPlayers()) {

								match.getSpawns().add(player.getLocation());
								if (match.getSpawns().size() == match.getAmountPlayers()) {
									plugin.getPlayersCreation().remove(player.getName());

								} else {
									plugin.getPlayersCreation().put(player.getName(),
											Creacion.ANOTHER_ARENA_SPAWNS.getPosition());
									actua = Boolean.FALSE;
								}
							}
						} else if (message.equals(Constantes.NEXT)) {
							plugin.getPlayersCreation().remove(player.getName());
						}
						break;
					case TEAM_SPAWNS:
						if (message.equals(Constantes.DONE)) {
							if (match.getSpawns().size() != match.getNumberOfTeams()) {

								match.getSpawns().add(player.getLocation());
								if (match.getSpawns().size() == match.getNumberOfTeams()) {
									plugin.getPlayersCreation().remove(player.getName());

								} else {
									plugin.getPlayersCreation().put(player.getName(),
											Creacion.ANOTHER_TEAM_SPAWNS.getPosition());
									actua = Boolean.FALSE;
								}
							}
						}
						break;
					case CANNON_SPAWNS:
						if (message.equals(Constantes.DONE)) {

							match.getEntitySpawns().add(player.getLocation());
							actua = Boolean.FALSE;

						} else if (message.equals(Constantes.NEXT)) {

							plugin.getPlayersCreation().remove(player.getName());

						}

						break;
					case ENTITY_SPAWNS:
						if (message.equals(Constantes.DONE)) {
							if (match.getEntitySpawns().size() != match.getAmountPlayers()) {

								match.getEntitySpawns().add(player.getLocation());
								if (match.getEntitySpawns().size() == match.getAmountPlayers()) {
									plugin.getPlayersCreation().remove(player.getName());

								} else {
									plugin.getPlayersCreation().put(player.getName(),
											Creacion.ANOTHER_ENTITY_SPAWNS.getPosition());
									actua = Boolean.FALSE;

								}
							}
						} else if (message.equals(Constantes.NEXT)) {

							plugin.getPlayersCreation().remove(player.getName());

						}
						break;
					case ANOTHER_ENTITY_SPAWNS:
						if (message.equals(Constantes.DONE)) {
							match.getEntitySpawns().add(player.getLocation());
							if (match.getEntitySpawns().size() == match.getAmountPlayers()) {
								plugin.getPlayersCreation().remove(player.getName());

							} else {
								plugin.getPlayersCreation().put(player.getName(),
										Creacion.ANOTHER_ENTITY_SPAWNS.getPosition());
								actua = Boolean.FALSE;

							}
						}
						break;
					case ANOTHER_ARENA_SPAWNS:
						if (message.equals(Constantes.DONE)) {
							match.getSpawns().add(player.getLocation());
							if (match.getSpawns().size() == match.getAmountPlayers()) {
								plugin.getPlayersCreation().remove(player.getName());

							} else {

								plugin.getPlayersCreation().put(player.getName(),
										Creacion.ANOTHER_ARENA_SPAWNS.getPosition());
								actua = Boolean.FALSE;

							}
						} else if (message.equals(Constantes.NEXT)) {
							plugin.getPlayersCreation().remove(player.getName());
						}
						break;

					case ANOTHER_TEAM_SPAWNS:
						if (message.equals(Constantes.DONE)) {
							match.getSpawns().add(player.getLocation());
							if (match.getSpawns().size() == match.getNumberOfTeams()) {
								plugin.getPlayersCreation().remove(player.getName());

							} else {

								plugin.getPlayersCreation().put(player.getName(),
										Creacion.ANOTHER_TEAM_SPAWNS.getPosition());
								actua = Boolean.FALSE;

							}
						}
						break;
					case SPECTATOR_SPAWNS:

						if (message.equals(Constantes.DONE)) {
							match.getSpectatorSpawns().add(player.getLocation());
							actua = Boolean.FALSE;
						} else if (message.equals(Constantes.NEXT)) {
							plugin.getPlayersCreation().remove(player.getName());

						}
						break;
					case KITS:
						try {
							Integer value = Integer.valueOf(message);
							if (value.equals(plugin.getKits().size())) {
								Kit kit = new Kit();
								plugin.getPlayerKit().put(player.getName(), kit);
								player.sendMessage(UtilsRandomEvents.enviaInfoCreacionKit(kit, player, plugin));
								actua = Boolean.FALSE;
								pasado = Boolean.TRUE;
							} else if (value < plugin.getKits().size() && value >= 0) {
								match.getKits().add(plugin.getKits().get(value).getName());
								plugin.getPlayersCreation().remove(player.getName());

							} else {
								player.sendMessage(plugin.getLanguage().getInvalidInput());

							}
						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
						}

						break;
					case TNT_TAG_HEAD:
						if (message.equals(Constantes.DONE)) {

							match.setHead(player.getItemInHand());

							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position + 1, match);
						}
						break;

					case INVENTORY_BEAST:
						if (message.equals(Constantes.DONE)) {

							ItemStack[] contenido = player.getInventory().getContents();
							List<ItemStack> contenidoList = Arrays.asList(contenido);
							try {
								contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
							} catch (Exception e) {

							}
							ItemStack[] arrayContenido = new ItemStack[contenidoList.size()];
							arrayContenido = contenidoList.toArray(arrayContenido);

							InventoryPers inv = new InventoryPers();
							inv.setContents(arrayContenido);
							inv.setHelmet(player.getInventory().getHelmet());
							inv.setBoots(player.getInventory().getBoots());
							inv.setLeggings(player.getInventory().getLeggings());
							inv.setChestplate(player.getInventory().getChestplate());
							match.setInventoryBeast(inv);

							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position + 1, match);
						}
						break;
					case INVENTORY_RUNNERS:
						if (message.equals(Constantes.DONE)) {

							ItemStack[] contenido = player.getInventory().getContents();
							List<ItemStack> contenidoList = Arrays.asList(contenido);
							try {
								contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
							} catch (Exception e) {

							}
							ItemStack[] arrayContenido = new ItemStack[contenidoList.size()];
							arrayContenido = contenidoList.toArray(arrayContenido);

							InventoryPers inv = new InventoryPers();
							inv.setContents(arrayContenido);
							inv.setHelmet(player.getInventory().getHelmet());
							inv.setBoots(player.getInventory().getBoots());
							inv.setLeggings(player.getInventory().getLeggings());
							inv.setChestplate(player.getInventory().getChestplate());
							match.setInventoryRunners(inv);

							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, Creacion.GOAL_LOCATION1.getPosition(),
							// match);
						}
						break;
					case INVENTORY_CHESTS:
						if (message.equals(Constantes.DONE)) {

							ItemStack[] contenido = player.getInventory().getContents();
							List<ItemStack> contenidoList = new ArrayList<ItemStack>();
							for (ItemStack item : contenido) {
								if (item != null) {
									contenidoList.add(item);
								}
							}
							try {
								contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
							} catch (Exception e) {

							}
							ItemStack[] arrayContenido = new ItemStack[contenidoList.size()];
							arrayContenido = contenidoList.toArray(arrayContenido);

							InventoryPers inv = new InventoryPers();
							inv.setContents(arrayContenido);

							match.setInventoryChests(inv);

							plugin.getPlayersCreation().remove(player.getName());

						} else if (message.equals(Constantes.ADD)) {
							ItemStack[] contenido = player.getInventory().getContents();
							List<ItemStack> contenidoList = new ArrayList<ItemStack>();
							for (ItemStack item : contenido) {
								if (item != null) {
									contenidoList.add(item);
								}
							}
							try {
								contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
							} catch (Exception e) {

							}
							if (match.getInventoryChests() != null && match.getInventoryChests().getContents() != null)
								contenidoList.addAll(Arrays.asList(match.getInventoryChests().getContents()));
							ItemStack[] arrayContenido = new ItemStack[contenidoList.size()];
							arrayContenido = contenidoList.toArray(arrayContenido);

							InventoryPers inv = new InventoryPers();
							inv.setContents(arrayContenido);

							match.setInventoryChests(inv);

							plugin.getPlayersCreation().remove(player.getName());
						}
						break;
					case REWARDS:
						match.getRewards().add(message);
						plugin.getPlayersCreation().put(player.getName(), Creacion.ANOTHER_REWARDS.getPosition());
						actua = Boolean.FALSE;

						// actualiza = UtilsRandomEvents.pasaACreation(plugin,
						// player,
						// position + 1, match);
						break;
					case ANOTHER_REWARDS:
						if (message.equals(Constantes.DONE)) {
							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position + 1, match);
						} else {
							match.getRewards().add(message);
							plugin.getPlayersCreation().put(player.getName(), Creacion.ANOTHER_REWARDS.getPosition());
							actua = Boolean.FALSE;

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position, match);
						}
						break;
					case MINIGAME_TYPE:
						try {
							MinigameType minigame = MinigameType.values()[Integer.valueOf(message)];
							if (minigame != null) {
								match.setMinigame(minigame);
								match.setSecondsMobSpawn(null);
								match.setMob(null);
								match.setEntitySpawns(new ArrayList<Location>());
								match.setTiempoPartida(null);
								match.setLocation1(null);
								match.setLocation2(null);
								match.setBeastSpawn(null);
								match.setInventoryBeast(null);
								match.setInventoryRunners(null);
								match.setMaterial(null);
								match.setDatas(new ArrayList<MaterialData>());
								plugin.getPlayersCreation().remove(player.getName());

								// actualiza =
								// UtilsRandomEvents.pasaACreation(plugin,
								// player, position + 1, match);

							} else {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								// actualiza =
								// UtilsRandomEvents.pasaACreation(plugin,
								// player, position, match);
							}
						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position, match);
						}
						break;
					case MOB_NAME:
						break;

					case MOB_SPAWN:
						break;
					case TIMER_MOB_SPAWN:
						break;
					case PLAY_TIME:
					case SHRINK_TIME:
						match.setTiempoPartida(Integer.valueOf(message.trim()));
						plugin.getPlayersCreation().remove(player.getName());

						break;
					case NO_MOVE_TIME:
						match.setSecondsToBegin(Integer.valueOf(message.trim()));
						plugin.getPlayersCreation().remove(player.getName());

						break;
					case ARROW_LOCATION1:
						if (message.equals(Constantes.DONE)) {
							match.setLocation1(player.getLocation());
							match.setMob(EntityType.ARROW);
							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position + 1, match);
						}
						break;
					case GEM_LOCATION1:
					case KOTH_LOCATION1:
					case GOAL_LOCATION1:
					case MAP_LOCATION1:
						if (message.equals(Constantes.DONE)) {
							match.setLocation1(player.getLocation());
							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position + 1, match);
						}
						break;
					case ARROW_LOCATION2:
					case KOTH_LOCATION2:
					case GEM_LOCATION2:
					case MAP_LOCATION2:
					case GOAL_LOCATION2:

						if (message.equals(Constantes.DONE)) {
							match.setLocation2(player.getLocation());
							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position + 1, match);
						}
						break;
					case BLOCKS_ALLOWED:
						if (message.equals(Constantes.DONE)) {
							if (player.getItemInHand() != null
									&& player.getItemInHand().getType() != (XMaterial.AIR.parseMaterial())) {
								try {
									MaterialData mat = new MaterialData(player.getItemInHand().getType(),
											player.getItemInHand().getData().getData());
									match.getDatas().add(mat);
								} catch (Throwable eb) {
									match.getDatas().add(player.getItemInHand().getData());
								}
								plugin.getPlayersCreation().remove(player.getName());

							} else {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								actua = Boolean.FALSE;
							}
						}
						break;
					case MATERIAL_SPLEEF:
						if (message.equals(Constantes.DONE)) {

							if (player.getItemInHand() != null
									&& player.getItemInHand().getType() != (XMaterial.AIR.parseMaterial())) {
								try {
									MaterialData mat = new MaterialData(player.getItemInHand().getType(),
											player.getItemInHand().getData().getData());
									match.getDatas().add(mat);
								} catch (Throwable eb) {
									match.getDatas().add(player.getItemInHand().getData());
								}
								plugin.getPlayersCreation().remove(player.getName());
								plugin.getPlayersCreation().put(player.getName(),
										Creacion.ANOTHER_MATERIAL_SPLEEF.getPosition());
								actua = Boolean.FALSE;

								// actualiza =
								// UtilsRandomEvents.pasaACreation(plugin,
								// player,
								// Creacion.ANOTHER_MATERIAL_SPLEEF.getPosition(),
								// match);
							} else {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								actua = Boolean.FALSE;

								// actualiza =
								// UtilsRandomEvents.pasaACreation(plugin,
								// player, position, match);
							}
						}
						break;
					case ANOTHER_MATERIAL_SPLEEF:
						if (message.equals(Constantes.DONE)) {

							if (player.getItemInHand() != null
									&& player.getItemInHand().getType() != (XMaterial.AIR.parseMaterial())) {
								try {
									MaterialData mat = new MaterialData(player.getItemInHand().getType(),
											player.getItemInHand().getData().getData());
									match.getDatas().add(mat);
								} catch (Throwable eb) {
									match.getDatas().add(player.getItemInHand().getData());
								}
								plugin.getPlayersCreation().remove(player.getName());
								actua = Boolean.FALSE;
								// actualiza =
								// UtilsRandomEvents.pasaACreation(plugin,
								// player,
								// Creacion.ANOTHER_MATERIAL_SPLEEF.getPosition(),
								// match);
							} else {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								actua = Boolean.FALSE;

								// actualiza =
								// UtilsRandomEvents.pasaACreation(plugin,
								// player, position, match);
							}
						} else if (message.equals(Constantes.NEXT)) {
							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, Creacion.END.getPosition(), match);

						}
						break;
					case TIMER_ARROW_SPAWN:
					case TIMER_ANVIL_SPAWN:
					case TIMER_GEM_SPAWN:
					case SECONDS_TO_SPAWN_BEAST:
					case TIMER_BOMB:
					case WARMUP_TIME:
						try {
							match.setSecondsMobSpawn(Double.valueOf(message.trim()));
							plugin.getPlayersCreation().remove(player.getName());

							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, Creacion.END.getPosition(), match);
						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
							// actualiza =
							// UtilsRandomEvents.pasaACreation(plugin,
							// player, position, match);
						}
						break;
					case WATER_DROP_SCENES:
						try {
							Integer value = Integer.valueOf(message);
							if (value.equals(plugin.getWaterDrops().size())) {
								WaterDropStep wd = new WaterDropStep();
								plugin.getPlayerWaterDrop().put(player.getName(), wd);
								player.sendMessage(UtilsRandomEvents.enviaInfoCreacionWaterDrop(wd, player, plugin));
								actua = Boolean.FALSE;
								pasado = Boolean.TRUE;
							} else if (value < plugin.getWaterDrops().size() && value >= 0) {
								match.getScenes().add(plugin.getWaterDrops().get(value).getName());
								plugin.getPlayersCreation().remove(player.getName());

							} else {
								player.sendMessage(plugin.getLanguage().getInvalidInput());

							}
						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
						}

						break;
					case SAVE:

						if (actua) {
							try {
								if (message.toUpperCase().equals("Y")) {

									String s = UtilsRandomEvents.compruebaMatchCorrecta(match, plugin);

									if (s != null) {
										player.sendMessage(s);
										actua = Boolean.FALSE;
									} else {

										UtilsRandomEvents.terminaCreacionMatch(plugin, player);

										actualiza = Boolean.FALSE;
									}
								} else {

									plugin.getPlayersCreation().remove(player.getName());
									actua = Boolean.TRUE;

								}

							} catch (Exception e) {
								player.sendMessage(plugin.getLanguage().getInvalidInput());
								player.sendMessage(c.getMessage(match));
								actua = Boolean.FALSE;

							}
						} else {
							if (plugin.getPlayerMatches().keySet().contains(player.getName())) {
								player.sendMessage(c.getMessage(match));
								plugin.getPlayersCreation().put(player.getName(), c.getPosition());
								actua = Boolean.FALSE;
							} else {
								String s = UtilsRandomEvents.compruebaMatchCorrecta(match, plugin);
								if (s != null) {
									player.sendMessage(s);
								} else {

									UtilsRandomEvents.terminaCreacionMatch(plugin, player);

									actualiza = Boolean.FALSE;
								}
							}

						}

						break;
					case CANCEL:
						if (message.toUpperCase().equals("Y")) {

							plugin.getPlayerMatches().remove(player.getName());
							plugin.getPlayersCreation().remove(player.getName());
							plugin.getEditando().remove(player.getName());

							player.sendMessage(plugin.getLanguage().getCancelOfMatchCreation());
							actualiza = Boolean.FALSE;

						} else {

							plugin.getPlayersCreation().remove(player.getName());
							actua = Boolean.TRUE;

						}

						break;
					case DELETE:
						try {
							Integer valor = Integer.valueOf(message);

							Creacion delete = null;

							try {
								if (Creacion.getByPosition(valor) != null) {
									delete = Creacion.getByPosition(valor);
									actua = Boolean.FALSE;
								}
							} catch (Exception e) {
							}
							switch (delete) {
							case MINIGAME_TYPE:
								match.setMinigame(null);
								match.setName(null);
								match.setAmountPlayers(null);
								match.setAmountPlayersMin(null);
								match.setPlayerSpawn(null);
								match.setSpawns(new ArrayList<Location>());
								match.setSpectatorSpawns(new ArrayList<Location>());
								match.setRewards(new ArrayList<String>());
								match.setInventory(null);
								match.setSecondsMobSpawn(null);
								match.setMob(null);
								match.setEntitySpawns(new ArrayList<Location>());
								match.setTiempoPartida(null);
								match.setLocation1(null);
								match.setLocation2(null);
								match.setBeastSpawn(null);
								match.setInventoryBeast(null);
								match.setInventoryRunners(null);
								match.setMaterial(null);
								match.setDatas(new ArrayList<MaterialData>());

								break;
							case BATTLE_NAME:
								match.setName(null);
								break;
							case AMOUNT_PLAYERS:
								match.setAmountPlayers(null);
								break;
							case AMOUNT_PLAYERS_MIN:
								match.setAmountPlayersMin(null);
								break;
							case SPAWN_PLAYER:
								match.setPlayerSpawn(null);
								break;
							case ARENA_SPAWNS:
							case ANOTHER_ARENA_SPAWNS:
								match.setSpawns(new ArrayList<Location>());

								break;
							case SPECTATOR_SPAWNS:
								match.setSpectatorSpawns(new ArrayList<Location>());

								break;
							case REWARDS:
							case ANOTHER_REWARDS:
								match.setRewards(new ArrayList<String>());

								break;
							case KITS:
								match.setKits(new ArrayList<String>());
								break;
							case TIMER_MOB_SPAWN:
							case TIMER_ARROW_SPAWN:
							case TIMER_GEM_SPAWN:
							case TIMER_BOMB:
							case SECONDS_TO_SPAWN_BEAST:
								match.setSecondsMobSpawn(null);
								break;
							case MOB_NAME:
								match.setMob(null);
								break;
							case MOB_SPAWN:
							case ENTITY_SPAWNS:
							case ANOTHER_ENTITY_SPAWNS:
								match.setEntitySpawns(new ArrayList<Location>());
								break;
							case PLAY_TIME:
								match.setTiempoPartida(null);
								break;
							case ARROW_LOCATION1:
							case GEM_LOCATION1:
							case KOTH_LOCATION1:
							case GOAL_LOCATION1:
							case MAP_LOCATION1:
								match.setLocation1(null);
								break;
							case ARROW_LOCATION2:
							case GEM_LOCATION2:
							case KOTH_LOCATION2:
							case GOAL_LOCATION2:
							case MAP_LOCATION2:
								match.setLocation2(null);
								break;
							case SPAWN_BEAST:
								match.setBeastSpawn(null);
								break;
							case INVENTORY_BEAST:
								match.setInventoryBeast(null);
								break;
							case TNT_TAG_HEAD:
								match.setHead(null);
								break;
							case INVENTORY_RUNNERS:
								match.setInventoryRunners(null);
								break;

							case BLOCKS_ALLOWED:
							case MATERIAL_SPLEEF:
							case ANOTHER_MATERIAL_SPLEEF:
								match.setDatas(new ArrayList<MaterialData>());
								break;
							default:
								break;
							}
							actua = Boolean.TRUE;

							plugin.getPlayersCreation().remove(player.getName());

						} catch (Exception e) {
							player.sendMessage(plugin.getLanguage().getInvalidInput());
							player.sendMessage(c.getMessage(match));
							actua = Boolean.FALSE;

						}

						break;

					default:
						break;
					}
				} catch (Exception e) {
					player.sendMessage(plugin.getLanguage().getInvalidInput());
					switch (Creacion.getByPosition(position)) {
					case MINIGAME_TYPE:
						for (MinigameType m : MinigameType.values()) {
							player.sendMessage("§6§l" + m.ordinal() + " - " + m.getMessage(plugin));
						}
						break;
					case MOB_NAME:
						for (EntityType m : EntityType.values()) {
							player.sendMessage("§6§l" + m.ordinal() + " - " + m.name());
						}
						break;
					case WATER_DROP_SCENES:
						player.sendMessage(c.getMessage(match));
						for (WaterDropStep m : plugin.getWaterDrops()) {
							player.sendMessage("§6§l" + plugin.getWaterDrops().indexOf(m) + " - " + m.getName());
						}
						player.sendMessage("§6§l" + plugin.getWaterDrops().size() + " - §e§lNew Water Drop");

						break;
					case KITS:
						player.sendMessage(c.getMessage(match));
						for (Kit m : plugin.getKits()) {
							player.sendMessage("§6§l" + plugin.getKits().indexOf(m) + " - " + m.getName());
						}
						player.sendMessage("§6§l" + plugin.getKits().size() + " - §e§lNew Kit");

						break;
					default:
						player.sendMessage(c.getMessage(match));
						break;
					}
					actua = Boolean.FALSE;
				}
			} else {
				pasado = Boolean.TRUE;
				player.sendMessage(Constantes.SALTO_LINEA);
				switch (Creacion.getByPosition(position)) {
				case MINIGAME_TYPE:
					if (!plugin.getEditando().contains(player.getName())) {
						for (MinigameType m : MinigameType.values()) {
							player.sendMessage("§6§l" + m.ordinal() + " - " + m.getMessage(plugin));
						}
						plugin.getPlayersCreation().put(player.getName(), c.getPosition());
					}
					break;
				case BATTLE_NAME:
					if (!plugin.getEditando().contains(player.getName())) {
						player.sendMessage(c.getMessage(match));
						plugin.getPlayersCreation().put(player.getName(), c.getPosition());
					}
					break;
				case MOB_NAME:
					for (EntityType m : EntityType.values()) {
						player.sendMessage("§6§l" + m.ordinal() + " - " + m.name());
					}
					plugin.getPlayersCreation().put(player.getName(), c.getPosition());

					break;
				case WATER_DROP_SCENES:
					player.sendMessage(c.getMessage(match));
					plugin.getPlayersCreation().put(player.getName(), c.getPosition());
					for (WaterDropStep m : plugin.getWaterDrops()) {
						player.sendMessage("§6§l" + plugin.getWaterDrops().indexOf(m) + " - " + m.getName());
					}
					player.sendMessage("§6§l" + plugin.getWaterDrops().size() + " - §e§lNew Water Drop");

					break;
				case KITS:
					player.sendMessage(c.getMessage(match));
					plugin.getPlayersCreation().put(player.getName(), c.getPosition());

					for (Kit m : plugin.getKits()) {
						player.sendMessage("§6§l" + plugin.getKits().indexOf(m) + " - " + m.getName());
					}
					player.sendMessage("§6§l" + plugin.getKits().size() + " - §e§lNew Kit");

					break;
				default:
					player.sendMessage(c.getMessage(match));
					plugin.getPlayersCreation().put(player.getName(), c.getPosition());

					break;
				}
			}
		}

		if (actualiza)

		{
			plugin.getPlayerMatches().put(player.getName(), match);
			plugin.getPlayerMatches().put(player.getName(), match);
			if (actua) {
				player.sendMessage(UtilsRandomEvents.enviaInfoCreacion(match, player, plugin));
			} else {
				c = Creacion.getByPosition(plugin.getPlayersCreation().get(player.getName()));
				if (c != null && !pasado) {
					player.sendMessage(c.getMessage(match));
				}
			}
		} else {
			plugin.getPlayerMatches().remove(player.getName());
			plugin.getPlayersCreation().remove(player.getName());
			plugin.getEditando().remove(player.getName());
		}
	}

}
