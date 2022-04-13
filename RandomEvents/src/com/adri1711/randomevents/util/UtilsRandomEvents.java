package com.adri1711.randomevents.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Kit;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.WaterDropStep;
import com.adri1711.randomevents.match.enums.Creacion;
import com.adri1711.randomevents.match.enums.CreacionKit;
import com.adri1711.randomevents.match.enums.CreacionWaterDrop;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.enums.Petos;
import com.adri1711.randomevents.match.schedule.DayWeek;
import com.adri1711.randomevents.match.schedule.Schedule;
import com.adri1711.randomevents.match.utils.BannedPlayers;
import com.adri1711.randomevents.match.utils.Cuboid;
import com.adri1711.randomevents.match.utils.InventoryPers;
import com.adri1711.randomevents.stats.Stats;
import com.adri1711.util.enums.Particle1711;
import com.adri1711.util.enums.ParticleDisplay;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XParticle;
import com.adri1711.util.enums.XSound;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.io.Files;

public class UtilsRandomEvents {

	public static String preparaStringTiempo(Integer tiempo, RandomEvents plugin) {

		Integer horas = Double.valueOf(tiempo / 3600.0).intValue();
		Integer minutos = Double.valueOf((tiempo - 3600 * horas) / 60.0).intValue();
		Integer segundos = Double.valueOf((tiempo - 3600 * horas) - 60 * minutos).intValue();
		String resultado = "";
		if (horas != 0) {
			resultado += plugin.getLanguage().getHoursFormat().replace("%hours%", "" + horas);
		}
		if (minutos != 0) {
			resultado += plugin.getLanguage().getMinutesFormat().replace("%minutes%", "" + minutos);
		}

		if (segundos != 0) {
			resultado += plugin.getLanguage().getSecondsFormat().replace("%seconds%", "" + segundos);
		}

		return resultado;
	}

	//
	public static void terminaCreacionMatch(RandomEvents plugin, Player player) {
		terminaCreacionMatch(plugin, player, null);
	}

	public static void terminaCreacionMatch(RandomEvents plugin, Player player, Match match) {
		if (match == null)
			match = plugin.getPlayerMatches().get(player.getName());
		try {
			String json = UtilidadesJson.fromMatchToJSON(plugin, match);
			if (json != null) {
				File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}

				File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events",
						match.getMinigame().getCodigo() + "_"
								+ ChatColor.stripColor(match.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
								+ ".json");
				if (!bossFile.exists()) {
					bossFile.createNewFile();
				} else {
					bossFile.delete();
					bossFile.createNewFile();
				}

				OutputStream os = new FileOutputStream(bossFile, true);
				PrintWriter pw = null;

				pw = new PrintWriter(
						new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

				pw.println(json);

				pw.flush();

				pw.close();
				if (player != null) {
					plugin.getPlayersCreation().remove(player.getName());
					plugin.getPlayerMatches().remove(player.getName());
				}

				Match matchAux = null;
				for (Match m : plugin.getMatches()) {
					if (m.getName().equals(match.getName())) {
						matchAux = m;
					}

				}
				if (matchAux == null) {
					plugin.getMatches().add(match);
				} else {
					plugin.getMatches().set(plugin.getMatches().indexOf(matchAux), match);
				}
				if (player != null) {

					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEndOfArenaCreation());
				}
			} else {
				plugin.getLoggerP().info("JSON was null.");
				if (player != null)
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			plugin.getLoggerP().info(e.getMessage());
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());

		}

	}

	public static void terminaCreacionWD(RandomEvents plugin, Player player) {
		WaterDropStep waterDrop = plugin.getPlayerWaterDrop().get(player.getName());
		try {
			String json = UtilidadesJson.fromWDToJSON(plugin, waterDrop);
			if (json != null) {
				File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//waterdrop");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}

				File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//waterdrop",
						ChatColor.stripColor(waterDrop.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
								+ ".json");
				if (!bossFile.exists()) {
					bossFile.createNewFile();
				} else {
					bossFile.delete();
					bossFile.createNewFile();
				}

				OutputStream os = new FileOutputStream(bossFile, true);
				PrintWriter pw = null;
				pw = new PrintWriter(
						new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

				pw.println(json);

				pw.flush();

				pw.close();
				plugin.getPlayersCreationWaterDrop().remove(player.getName());
				plugin.getPlayerWaterDrop().remove(player.getName());
				plugin.getWaterDrops().add(waterDrop);
			} else {
				plugin.getLoggerP().info("JSON was null.");
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			plugin.getLoggerP().info(e.getMessage());
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());

		}

	}

	public static void terminaCreacionKit(RandomEvents plugin, Player player, Kit kit) {
		if (kit == null)
			kit = plugin.getPlayerKit().get(player.getName());
		try {
			String json = UtilidadesJson.fromKitToJSON(plugin, kit);
			if (json != null) {
				File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//kits");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}

				File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//kits",
						ChatColor.stripColor(kit.getName().replaceAll("<color>", "§")).replaceAll(" ", "_") + ".json");
				if (!bossFile.exists()) {
					bossFile.createNewFile();
				} else {
					bossFile.delete();
					bossFile.createNewFile();
				}

				OutputStream os = new FileOutputStream(bossFile, true);
				PrintWriter pw = null;
				pw = new PrintWriter(
						new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

				pw.println(json);

				pw.flush();

				pw.close();
				plugin.getPlayersCreationKit().remove(player.getName());
				plugin.getPlayerKit().remove(player.getName());

				Kit kitAux = null;
				for (Kit m : plugin.getKits()) {
					if (m.getName().equals(kit.getName())) {
						kitAux = m;
					}

				}
				if (kitAux == null) {
					plugin.getKits().add(kit);
				} else {
					if (plugin.getMatches().indexOf(kitAux) != -1) {
						plugin.getKits().set(plugin.getMatches().indexOf(kitAux), kit);
					} else {
						plugin.getKits().add(kit);
					}
				}
				if (player != null) {

					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEndOfKitCreation());
				}
			} else {
				plugin.getLoggerP().info("JSON was null.");
				if (player != null)
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			plugin.getLoggerP().info(e.getMessage());
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());

		}

	}

	public static void enableMatch(RandomEvents plugin, Match match, Player player) {
		if (match.getEnabled() != null && !match.getEnabled()) {
			plugin.getMatches().remove(match);
			match.setEnabled(Boolean.TRUE);
			try {
				String json = UtilidadesJson.fromMatchToJSON(plugin, match);
				if (json != null) {
					File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
					if (!dataFolder.exists()) {
						dataFolder.mkdir();
					}

					File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events",
							match.getMinigame().getCodigo() + "_" + ChatColor
									.stripColor(match.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
									+ ".json");
					if (!bossFile.exists()) {
						bossFile.createNewFile();
					} else {
						bossFile.delete();
						bossFile.createNewFile();
					}

					OutputStream os = new FileOutputStream(bossFile, true);
					PrintWriter pw = new PrintWriter(
							new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

					pw.println(json);

					pw.flush();

					pw.close();
					plugin.getMatches().add(match);
					plugin.getMatchesAvailable().add(match);
					if (player != null) {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventEnabled());
					}
				} else {
					plugin.getLoggerP().info("JSON was null.");
					if (player != null) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
					}
				}
			} catch (Exception e) {
				plugin.getLoggerP().info(e.getMessage());
				if (player != null) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
				}
			}
		}
	}

	public static void disableMatch(RandomEvents plugin, Match match, Player player) {
		if (match.getEnabled() == null || match.getEnabled()) {
			plugin.getMatches().remove(match);
			plugin.getMatchesAvailable().remove(match);
			match.setEnabled(Boolean.FALSE);
			try {
				String json = UtilidadesJson.fromMatchToJSON(plugin, match);
				if (json != null) {
					File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
					if (!dataFolder.exists()) {
						dataFolder.mkdir();
					}

					File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events",
							match.getMinigame().getCodigo() + "_" + ChatColor
									.stripColor(match.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
									+ ".json");
					if (!bossFile.exists()) {
						bossFile.createNewFile();
					} else {
						bossFile.delete();
						bossFile.createNewFile();
					}

					OutputStream os = new FileOutputStream(bossFile, true);
					PrintWriter pw = null;
					pw = new PrintWriter(
							new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

					pw.println(json);

					pw.flush();

					pw.close();
					plugin.getMatches().add(match);
					if (player != null) {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventDisabled());
					}
				} else {
					plugin.getLoggerP().info("JSON was null.");
					if (player != null) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
					}
				}
			} catch (Exception e) {
				plugin.getLoggerP().info(e.getMessage());
				if (player != null) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
				}
			}
		}
	}

	public static void enableMatchSchedule(RandomEvents plugin, Match match, Player player) {
		if (match.getEnabledSchedule() != null && !match.getEnabledSchedule()) {
			plugin.getMatches().remove(match);
			match.setEnabledSchedule(Boolean.TRUE);
			try {
				String json = UtilidadesJson.fromMatchToJSON(plugin, match);
				if (json != null) {
					File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
					if (!dataFolder.exists()) {
						dataFolder.mkdir();
					}

					File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events",
							match.getMinigame().getCodigo() + "_" + ChatColor
									.stripColor(match.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
									+ ".json");
					if (!bossFile.exists()) {
						bossFile.createNewFile();
					} else {
						bossFile.delete();
						bossFile.createNewFile();
					}

					OutputStream os = new FileOutputStream(bossFile, true);
					PrintWriter pw = new PrintWriter(
							new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

					pw.println(json);

					pw.flush();

					pw.close();
					plugin.getMatches().add(match);
					plugin.getMatchesAvailableSchedule().add(match);
					if (player != null) {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventEnabled());
					}
				} else {
					plugin.getLoggerP().info("JSON was null.");
					if (player != null) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
					}
				}
			} catch (Exception e) {
				plugin.getLoggerP().info(e.getMessage());
				if (player != null) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
				}
			}
		}
	}

	public static void disableMatchSchedule(RandomEvents plugin, Match match, Player player) {
		if (match.getEnabledSchedule() == null || match.getEnabledSchedule()) {
			plugin.getMatches().remove(match);
			plugin.getMatchesAvailableSchedule().remove(match);
			match.setEnabledSchedule(Boolean.FALSE);
			try {
				String json = UtilidadesJson.fromMatchToJSON(plugin, match);
				if (json != null) {
					File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
					if (!dataFolder.exists()) {
						dataFolder.mkdir();
					}

					File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events",
							match.getMinigame().getCodigo() + "_" + ChatColor
									.stripColor(match.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
									+ ".json");
					if (!bossFile.exists()) {
						bossFile.createNewFile();
					} else {
						bossFile.delete();
						bossFile.createNewFile();
					}

					OutputStream os = new FileOutputStream(bossFile, true);
					PrintWriter pw = null;
					pw = new PrintWriter(
							new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

					pw.println(json);

					pw.flush();

					pw.close();
					plugin.getMatches().add(match);
					if (player != null) {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventDisabled());
					}
				} else {
					plugin.getLoggerP().info("JSON was null.");
					if (player != null) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
					}
				}
			} catch (Exception e) {
				plugin.getLoggerP().info(e.getMessage());
				if (player != null) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
				}
			}
		}
	}

	public static void borraMatch(RandomEvents plugin, Match match, Player player) {
		try {

			// File dataFolder = new
			// File(String.valueOf(plugin.getDataFolder().getPath()) +
			// "//events");

			File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events",
					match.getMinigame().getCodigo() + "_"
							+ ChatColor.stripColor(match.getName().replaceAll("<color>", "§")).replaceAll(" ", "_")
							+ ".json");
			if (bossFile.exists()) {
				bossFile.delete();
			}
			plugin.getMatches().remove(match);
			plugin.getMatchesAvailable().remove(match);

			if (player != null) {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventDeleted());
			}

		} catch (Exception e) {
			plugin.getLoggerP().info(e.getMessage());
			if (player != null) {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}

		}

	}

	public static void terminaCreacionSchedule(RandomEvents plugin, Player player, Schedule schedule) {
		try {
			String json = UtilidadesJson.fromScheduleToJSON(plugin, schedule);
			if (json != null) {
				File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//schedule");
				if (!dataFolder.exists()) {
					dataFolder.mkdir();
				}

				File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//schedule",
						"SCH_" + schedule.getDay() + "-" + schedule.getHour() + "-" + schedule.getMinute() + ".json");
				if (!bossFile.exists()) {
					bossFile.createNewFile();
				}
				FileWriter fw = new FileWriter(bossFile, true);

				PrintWriter pw = new PrintWriter(fw);

				pw.println(json);

				pw.flush();

				pw.close();

				player.sendMessage(
						plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEndOfScheduleCreation());
			} else {
				plugin.getLoggerP().info("JSON was null.");
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			plugin.getLoggerP().info(e.getMessage());
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());

		}

	}

	public static Boolean guardaInventario(RandomEvents plugin, Player player) {
		Boolean exitoso = Boolean.TRUE;
		if (plugin.getReventConfig().isInventoryManagement()
				&& (plugin.getMatchActive() == null || !plugin.getMatchActive().getMatch().getUseOwnInventory())) {

			File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventories",
					player.getName() + ".json");
			try {

				InventoryPers inventario = new InventoryPers();
				ItemStack[] contenido = player.getInventory().getContents();
				List<ItemStack> contenidoList = null;

				if (contenido != null)
					contenidoList = Arrays.asList(contenido);

				if (player.getInventory() != null && player.getInventory().getArmorContents() != null
						&& contenidoList != null) {
					if (contenido.length != 36) {
						for (ItemStack it : Arrays.asList(player.getInventory().getArmorContents())) {
							if (contenidoList.lastIndexOf(it) != -1)
								contenidoList.set(contenidoList.lastIndexOf(it), null);
						}
					}

				}
				ItemStack[] arrayContenido = new ItemStack[contenidoList.size()];
				arrayContenido = contenidoList.toArray(arrayContenido);
				inventario.setGamemode(player.getGameMode().toString());
				inventario.setContents(arrayContenido);
				inventario.setTotalExp(player.getExp());
				inventario.setHunger(player.getFoodLevel());
				inventario.setHealth(player.getHealth());
				inventario.setLevel(player.getLevel());
				inventario.setHelmet(player.getInventory().getHelmet());
				inventario.setBoots(player.getInventory().getBoots());
				inventario.setLeggings(player.getInventory().getLeggings());
				inventario.setChestplate(player.getInventory().getChestplate());

				try {
					Method method = player.getInventory().getClass().getDeclaredMethod("getItemInOffHand");
					Object item = method.invoke(player.getInventory());
					if (item != null && item instanceof ItemStack) {
						ItemStack itemS = (ItemStack) item;
						inventario.setItemOffHand(itemS);
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
				}
				if (plugin.getReventConfig().getUseLastLocation()) {
					inventario.setLastLocation(player.getLocation());
				}

				String json = UtilidadesJson.fromInventoryToJSON(plugin, inventario);

				if (json != null) {
					File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventories");
					if (!dataFolder.exists()) {
						dataFolder.mkdir();
					}

					if (!bossFile.exists()) {
						bossFile.createNewFile();

						OutputStream os = new FileOutputStream(bossFile, true);
						PrintWriter pw = null;
						pw = new PrintWriter(
								new OutputStreamWriter(os, Charset.forName(plugin.getReventConfig().getUseEncoding())));

						pw.println(json);

						pw.flush();

						pw.close();
					} else {
						plugin.getLoggerP().info(
								"[RandomEvents] Error :: The player " + player.getName() + " already has an inventory");
						plugin.getLoggerP().info(json);
					}
				} else {
					exitoso = Boolean.FALSE;

					plugin.getLoggerP().info("JSON was null.");
				}
			} catch (IOException e) {
				plugin.getLoggerP().info(e.getMessage());
				exitoso = Boolean.FALSE;
				if (bossFile.exists()) {
					bossFile.delete();
				}

			}
		}
		if (exitoso) {
			player.setExp(0);
			player.setLevel(0);
		}
		return exitoso;

	}

	public static void sacaInventario(RandomEvents plugin, Player player) {
		if (plugin.getReventConfig().isInventoryManagement()
				&& (plugin.getMatchActive() == null || !plugin.getMatchActive().getMatch().getUseOwnInventory())) {
			if (player.getActivePotionEffects() != null) {
				for (PotionEffect effect : player.getActivePotionEffects()) {
					player.removePotionEffect(effect.getType());
				}

			}
			player.updateInventory();
			File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventories");
			File dataFolderOld = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventoriesold");
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}
			if (!dataFolderOld.exists()) {
				dataFolderOld.mkdir();
			}
			File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventories",
					player.getName() + ".json");
			File bossFileOld = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventoriesold",
					player.getName() + ".json");
			InventoryPers inventario = null;
			if (bossFile.exists()) {
				BufferedReader br = null;
				FileInputStream fr = null;
				try {
					fr = new FileInputStream(bossFile);
					Charset sc = Charset.forName(plugin.getReventConfig().getUseEncoding());

					br = new BufferedReader(new InputStreamReader(fr, sc));

					try {

						inventario = UtilidadesJson.fromJSONToInventory(plugin, br);
					} catch (Exception e) {
						plugin.getLoggerP().info("[RandomEvents] Error al cargar el inventario de " + player.getName()
								+ ", probablemente una leather armor con color");
						plugin.getLoggerP().info("[RandomEvents]" + br);

					}

				} catch (FileNotFoundException e) {
					plugin.getLoggerP().info("Error in sacaInventario catch 1");
					plugin.getLoggerP().info(e.getMessage());
				} finally {
					try {
						if (fr != null)
							fr.close();
						if (br != null)
							br.close();
					} catch (IOException e) {
						plugin.getLoggerP().info(e.getMessage());
					}
				}

				try {
					if (inventario != null) {
						UtilsRandomEvents.borraInventario(player, plugin);

						if (plugin.getReventConfig().getUseLastLocation()) {
							if (inventario.getLastLocation() != null) {
								teleportaPlayer(player, inventario.getLastLocation(), plugin);
							}
						}
						GameMode gm = GameMode.valueOf(inventario.getGamemode());
						if (gm == null) {
							gm = GameMode.SURVIVAL;
						}
						player.setGameMode(gm);
						player.updateInventory();
						if (inventario.getTotalExp() != -1) {
							player.setExp(inventario.getTotalExp());
						}
						if (inventario.getLevel() != -1) {
							player.setLevel(inventario.getLevel());
						}
						try {
							if (inventario.getHealth() > 0 && inventario.getHealth() <= player.getMaxHealth()) {
								player.setHealth(inventario.getHealth());
							}
						} catch (Exception e) {

						}
						try {
							if (inventario.getHunger() > 0) {
								player.setFoodLevel(inventario.getHunger());
							}
						} catch (Exception e) {

						}
						player.getInventory().setContents(inventario.getContents());
						player.getInventory().setHelmet(inventario.getHelmet());
						player.getInventory().setLeggings(inventario.getLeggings());
						player.getInventory().setBoots(inventario.getBoots());
						player.getInventory().setChestplate(inventario.getChestplate());

						try {
							Method method = player.getInventory().getClass().getDeclaredMethod("setItemInOffHand",
									ItemStack.class);
							method.invoke(player.getInventory(), inventario.getItemOffHand());

						} catch (NoSuchMethodException | SecurityException | IllegalAccessException
								| IllegalArgumentException | InvocationTargetException e) {

						}

						player.updateInventory();

						if (bossFileOld.exists()) {
							bossFileOld.delete();
						}
						Files.move(bossFile, bossFileOld);
						if (bossFile.exists()) {
							bossFile.delete();
						}

						if (player.getActivePotionEffects() != null) {
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							}

						}
					}
				} catch (Exception exc) {
					plugin.getLoggerP().info("Error in sacaInventario catch 2");
					plugin.getLoggerP().info(exc.getMessage());
				}
			}
		}
	}

	public static Boolean teleportaPlayer(Player p, Location loc, RandomEvents plugin) {
		return teleportaPlayer(p, loc, plugin, false);
	}

	public static Boolean teleportaPlayer(Player p, Location loc, RandomEvents plugin, Boolean comprueba) {
		Boolean res = true;
		if (!comprueba || !plugin.getReventConfig().getUseLastLocation()) {
			if (loc != null) {
				p.setVelocity(new Vector(0, 0, 0));
				try {
					p.teleport(loc);

				} catch (Exception e) {
					try {
						Location loc2 = loc.clone();
						if (loc2 != null) {
							if (plugin.getReventConfig().isDebugMode())
								plugin.getLoggerP().info("Initial Loc ->" + loc2.toString());
						}
						loc2.setWorld(Bukkit.getWorld(loc2.getWorld().getUID()));
						if (loc2 != null) {
							if (plugin.getReventConfig().isDebugMode())
								plugin.getLoggerP().info("Updated Loc ->" + loc2.toString());
						}
						p.teleport(loc2);

					} catch (Exception e2) {
						try {
							Location loc2 = loc.clone();
							if (loc2 != null) {
								if (plugin.getReventConfig().isDebugMode())
									plugin.getLoggerP().info("Initial Loc ->" + loc2.toString());
							}
							loc2.setWorld(Bukkit.getWorld(loc2.getWorld().getName()));
							if (loc2 != null) {
								if (plugin.getReventConfig().isDebugMode())
									plugin.getLoggerP().info("Updated Loc ->" + loc2.toString());
							}

							p.teleport(loc2);

						} catch (Exception e3) {
							res = false;
							plugin.getLoggerP().info(e3.toString());

						}
					}
				}
			}
		}
		return res;
	}

	public static List<Match> cargarPartidas(RandomEvents plugin) {
		List<Match> listaPartidas = new ArrayList<Match>();
		File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
		List<Match> matchToConvert = new ArrayList<Match>();
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		for (File file : dataFolder.listFiles()) {
			BufferedReader br = null;
			FileInputStream fr = null;
			try {
				fr = new FileInputStream(file);
				Charset sc = Charset.forName(plugin.getReventConfig().getUseEncoding());

				br = new BufferedReader(new InputStreamReader(fr, sc));
				Match match = UtilidadesJson.fromJSONToMatch(plugin, br);
				if (match != null) {
					if (match.getInventory() != null) {
						match = UtilsRandomEvents.convertInventoryToKits(plugin, match);
						matchToConvert.add(match);
					}

					listaPartidas.add(match);
				}

			} catch (FileNotFoundException e) {
				plugin.getLoggerP().info(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					plugin.getLoggerP().info(e.getMessage());
				}
			}

		}
		for (Match m : matchToConvert) {
			UtilsRandomEvents.terminaCreacionMatch(plugin, null, m);
		}
		Collections.sort(listaPartidas);
		return listaPartidas;
	}

	private static Match convertInventoryToKits(RandomEvents plugin, Match match) {
		Kit kit = new Kit();
		kit.setName(ChatColor.stripColor(match.getName()));
		kit.setInventory(match.getInventory());
		terminaCreacionKit(plugin, null, kit);
		match.setInventory(null);
		match.getKits().add(kit.getName());
		return match;
	}

	public static Schedule findSchedule(RandomEvents plugin, Integer day, Integer hour, Integer minute) {
		Schedule sched = null;
		for (Schedule s : plugin.getSchedules()) {
			if ((s.getDay().equals(DayWeek.EVERYDAY.getPosition()) || s.getDay().equals(day))
					&& s.getHour().equals(hour) && s.getMinute().equals(minute)) {
				sched = s;
			}
		}
		return sched;
	}

	public static List<Schedule> cargarSchedules(RandomEvents plugin) {
		List<Schedule> listaPartidas = new ArrayList<Schedule>();
		File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//schedule");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		for (File file : dataFolder.listFiles()) {
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				Schedule match = UtilidadesJson.fromJSONToSchedule(plugin, br);
				if (match != null)
					listaPartidas.add(match);

			} catch (FileNotFoundException e) {
				plugin.getLoggerP().info(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					plugin.getLoggerP().info(e.getMessage());
				}
			}

		}
		return listaPartidas;
	}

	public static String preparaNombrePartida(MatchActive match) {
		String resultado = "";

		resultado = match.getMatch().getName().replaceAll("&", "§") + " ( "
				+ match.getPlayerHandler().getPlayers().size() + " / " + match.getMatch().getAmountPlayers() + " )";
		return resultado;
	}

	public static String leerArchivo(File file) {
		BufferedReader objReader = null;
		String archivo = "";
		try {
			String strCurrentLine;

			objReader = new BufferedReader(new FileReader(file.getPath()));

			while ((strCurrentLine = objReader.readLine()) != null) {

				archivo += strCurrentLine;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {
				if (objReader != null)
					objReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return archivo;
	}

	public static void mandaMensaje(RandomEvents plugin, List<Player> players, String message, Boolean tag) {
		mandaMensaje(plugin, players, message, tag, true, false);
	}

	public static void mandaMensaje(RandomEvents plugin, List<Player> players, String message, Boolean tag,
			Boolean allowTitle, Boolean forceTitle) {
		message = message.replaceAll("<color>", "§");
		if (tag) {
			message = plugin.getLanguage().getTagPlugin() + message;
		}
		if (!message.isEmpty()) {
			for (Player p : players) {
				if (plugin.getReventConfig().isOptionalTitles() && allowTitle) {
					plugin.getApi().usaTitle(p, "", message);
				} else {
					p.sendMessage(message);
					if (forceTitle) {
						plugin.getApi().usaTitle(p, "", message);
					}
				}
			}
		}
	}

	// public static void mandaMensaje(RandomEvents plugin, List<Player>
	// players, List<String> messages, Boolean tag) {
	// String message = "";
	// for (String msg : messages) {
	// if (tag) {
	// message += plugin.getLanguage().getTagPlugin() +
	// msg.replaceAll("<color>", "§") + "\n";
	// } else {
	// message += msg.replaceAll("<color>", "§") + "\n";
	// }
	// }
	// for (Player p : players) {
	// p.sendMessage(message);
	// }
	// }

	public static void tiraCohete(Location l) {
		Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		Random r = new Random();

		int rt = r.nextInt(4) + 1;
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1)
			type = FireworkEffect.Type.BALL;
		if (rt == 2)
			type = FireworkEffect.Type.BALL_LARGE;
		if (rt == 3)
			type = FireworkEffect.Type.BURST;
		if (rt == 4)
			type = FireworkEffect.Type.CREEPER;
		if (rt == 5) {
			type = FireworkEffect.Type.STAR;
		}

		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type)
				.trail(r.nextBoolean()).build();

		fwm.addEffect(effect);

		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		fw.setFireworkMeta(fwm);
	}

	public static Color getColor(int i) {
		Color c = null;
		if (i == 1) {
			c = Color.AQUA;
		}
		if (i == 2) {
			c = Color.BLACK;
		}
		if (i == 3) {
			c = Color.BLUE;
		}
		if (i == 4) {
			c = Color.FUCHSIA;
		}
		if (i == 5) {
			c = Color.GRAY;
		}
		if (i == 6) {
			c = Color.GREEN;
		}
		if (i == 7) {
			c = Color.LIME;
		}
		if (i == 8) {
			c = Color.MAROON;
		}
		if (i == 9) {
			c = Color.NAVY;
		}
		if (i == 10) {
			c = Color.OLIVE;
		}
		if (i == 11) {
			c = Color.ORANGE;
		}
		if (i == 12) {
			c = Color.PURPLE;
		}
		if (i == 13) {
			c = Color.RED;
		}
		if (i == 14) {
			c = Color.SILVER;
		}
		if (i == 15) {
			c = Color.TEAL;
		}
		if (i == 16) {
			c = Color.WHITE;
		}
		if (i == 17) {
			c = Color.YELLOW;
		}

		return c;
	}

	public static List<Player> getPlayersWithin(Player player, List<Player> players, int distance) {
		List<Player> res = new ArrayList<Player>();
		int d2 = distance * distance;
		for (Player p : players) {
			if (p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
				res.add(p);
			}
		}
		return res;
	}

	public static void playSound(RandomEvents plugin, List<Player> player, XSound sonido) {
		if (!plugin.getReventConfig().isDeactivateSounds()) {
			if (sonido != null) {
				for (Player p : player) {
					playSound(plugin, p, sonido);
				}
			}
		}
	}

	public static void playSound(RandomEvents plugin, Player player, XSound sonido) {
		if (!plugin.getReventConfig().isDeactivateSounds()) {
			if (sonido != null && player != null) {
				player.playSound(player.getLocation(), sonido.parseSound(), 10.0F, 1.0F);
			}
		}
	}

	public static void playSound(RandomEvents plugin, Player player, XSound sonido, float volume, float pitch) {
		if (!plugin.getReventConfig().isDeactivateSounds()) {
			if (sonido != null && player != null) {
				player.playSound(player.getLocation(), sonido.parseSound(), volume, pitch);
			}
		}
	}

	public static String sacaNombrePartida(String displayName) {
		return displayName.split("\\(")[0].trim();
	}

	public static String cambiarMensajeConEtiqueta(String message) {
		return message.replaceAll("&", "<color>").replaceAll("§", "<color>");
	}

	public static void normalizaColorsMatch(Match match) {
		match.setName(match.getName().replace("<color>", "§"));

	}

	public static void normalizaColorsWaterDrop(WaterDropStep match) {
		match.setName(match.getName().replace("<color>", "§"));

	}

	public static void normalizaColorsKit(Kit match) {
		match.setName(match.getName().replace("<color>", "§"));

	}

	public static void borraPlayerPorName(List<Player> players, Player player) {
		Player p = null;
		for (Player jugador : players) {
			if (jugador.getName().equals(player.getName())) {
				p = jugador;
			}
		}
		if (p != null) {
			players.remove(p);
		}

	}

	public static Player obtieneJugador(Set<Player> players, String name) {
		Player p = null;

		for (Player player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				p = player;
			}
		}
		return p;
	}

	public static MatchActive escogeMatchActiveAleatoria(RandomEvents plugin, List<Match> matches, Boolean forzada) {
		Match m = null;
		List<Match> matchAvailables = new ArrayList<Match>();

		for (Match match : matches) {
			if (match.getEnabled() == null || match.getEnabled()) {
				matchAvailables.add(match);
			}
		}
		m = matches.get(plugin.getRandom().nextInt(matchAvailables.size()));

		MatchActive matchActive = new MatchActive(m, plugin, forzada);

		return matchActive;
	}

	public static void borraInventario(Player player, RandomEvents plugin) {
		if (plugin.getReventConfig().isInventoryManagement()
				&& (plugin.getMatchActive() == null || !plugin.getMatchActive().getMatch().getUseOwnInventory())) {
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setLeggings(null);
			player.getInventory().setBoots(null);
			player.getInventory().setChestplate(null);
			player.getEquipment().setArmorContents(null);
			player.setItemOnCursor(null);
			if (player.getOpenInventory() != null) {
				if (player.getOpenInventory().getTopInventory() != null) {
					player.getOpenInventory().getTopInventory().clear();
				}
				if (player.getOpenInventory().getBottomInventory() != null) {
					player.getOpenInventory().getBottomInventory().clear();
				}
			}
			player.updateInventory();
		}
	}

	public static boolean checkLeatherItems(Player player) {
		Boolean leather = false;
		leather = leather || UtilsRandomEvents.compruebaItem(player.getInventory().getHelmet());
		leather = leather || UtilsRandomEvents.compruebaItem(player.getInventory().getChestplate());
		leather = leather || UtilsRandomEvents.compruebaItem(player.getInventory().getLeggings());
		leather = leather || UtilsRandomEvents.compruebaItem(player.getInventory().getBoots());

		for (ItemStack item : player.getInventory().getContents()) {
			if (!leather) {
				leather = UtilsRandomEvents.compruebaItem(item);
			}

		}
		return !leather;
	}

	public static boolean checkInventoryVacio(Player p) {
		boolean res = true;
		int limit = p.getInventory().getSize();
		for (int i = 0; i < limit; i++) {
			if (p.getInventory().getItem(i) != null) {
				res = false;
				break;
			}
		}

		int limit2 = p.getInventory().getArmorContents().length;
		for (int i = 0; i < limit2; i++) {
			if (p.getInventory().getArmorContents()[i] != null) {
				if (p.getInventory().getArmorContents()[i].getType() != null
						&& p.getInventory().getArmorContents()[i].getType() != XMaterial.AIR.parseMaterial()) {
					res = false;
					break;
				}
			}
		}
		return res;
	}

	private static Boolean compruebaItem(ItemStack item) {
		Boolean leather = false;
		if (item != null) {
			switch (item.getType()) {
			case LEATHER_BOOTS:
			case LEATHER_CHESTPLATE:
			case LEATHER_HELMET:
			case LEATHER_LEGGINGS:
				leather = true;
				break;
			default:
				break;
			}
		}
		return leather;

	}

	public static List<Location> getAllPossibleLocations(Location loc1, Location loc2) {

		List<Location> locations = new ArrayList<Location>();
		if (loc1 != null && loc2 != null) {
			for (Double x = loc1.getX() > loc2.getX() ? loc2.getX() : loc1.getX(); (loc1.getX() > loc2.getX())
					? (x <= loc1.getX()) : (x <= loc2.getX()); x += 0.2) {
				for (Double y = loc1.getY() > loc2.getY() ? loc2.getY() : loc1.getY(); (loc1.getY() > loc2.getY())
						? (y <= loc1.getY()) : (y <= loc2.getY()); y++) {
					for (Double z = loc1.getZ() > loc2.getZ() ? loc2.getZ() : loc1.getZ(); (loc1.getZ() > loc2.getZ())
							? (z <= loc1.getZ()) : (z <= loc2.getZ()); z += 0.2) {

						Location lll = new Location(loc1.getWorld(), x.doubleValue(), y.doubleValue(), z.doubleValue());
						locations.add(lll);
					}
				}
			}
		}
		return locations;
	}

	public static void spawnPowerUp(Location l, RandomEvents plugin) {

		l.getWorld().dropItem(l, plugin.getReventConfig().getPowerUpItem());

	}

	public static void dropItem(Location l, ItemStack item) {

		l.getWorld().dropItem(l, item);

	}

	public static void spawnEntity(MatchActive matchActive, Location l, EntityType entityType, RandomEvents plugin) {

		Entity entity = l.getWorld().spawnEntity(l, entityType);

		if (entityType == EntityType.ARROW) {
			Double multiplicadorX = plugin.getRandom().nextDouble() > 0.5 ? 1. : -1.;
			Double multiplicadorZ = plugin.getRandom().nextDouble() > 0.5 ? 1. : -1.;

			Integer validadorX = plugin.getRandom().nextInt(10) < 1 ? 1 : 0;
			Integer validadorZ = plugin.getRandom().nextInt(10) < 1 ? 1 : 0;
			entity.setVelocity(new Vector(plugin.getRandom().nextDouble() * multiplicadorX * validadorX, -4,
					plugin.getRandom().nextDouble() * multiplicadorZ * validadorZ));
		}
		matchActive.getMobs().add(entity);

	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return sortByValue(map, true);
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, Boolean reverseOrder) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		if (reverseOrder) {
			list.sort(Collections.reverseOrder(Entry.comparingByValue()));
		} else {
			list.sort(Entry.comparingByValue());
		}

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public static Match findMatch(RandomEvents plugin, String matchName) {
		Match m = null;
		for (Match match : plugin.getMatches()) {
			if (match.getName().equals(matchName)) {
				m = match;
			}
		}
		return m;
	}

	public static Location getRandomLocation(RandomEvents plugin, Cuboid cuboid, MatchActive matchActive) {
		Integer difX = Double.valueOf(cuboid.getMaxX()).intValue() - Double.valueOf(cuboid.getMinX()).intValue();
		Integer difY = Double.valueOf(cuboid.getMaxY()).intValue() - Double.valueOf(cuboid.getMinY()).intValue();
		Integer difZ = Double.valueOf(cuboid.getMaxZ()).intValue() - Double.valueOf(cuboid.getMinZ()).intValue();
		Integer sumX = difX == 0 ? 0
				: (difX > 0 ? plugin.getRandom().nextInt(difX) : (-1 * plugin.getRandom().nextInt(Math.abs(difX))));
		Integer sumY = difY == 0 ? 0
				: (difY > 0 ? plugin.getRandom().nextInt(difY) : (-1 * plugin.getRandom().nextInt(Math.abs(difY))));
		Integer sumZ = difZ == 0 ? 0
				: (difZ > 0 ? plugin.getRandom().nextInt(difZ) : (-1 * plugin.getRandom().nextInt(Math.abs(difZ))));

		Location loc = null;

		try {
			loc = new Location(cuboid.getWorld(), cuboid.getMinX() + sumX, cuboid.getMinY() + sumY,
					cuboid.getMinZ() + sumZ);
		} catch (Exception e) {
			try {
				loc = new Location(matchActive.getMatch().getSpawns().get(0).getWorld(), cuboid.getMinX() + sumX,
						cuboid.getMinY() + sumY, cuboid.getMinZ() + sumZ);

			} catch (Exception e2) {
				try {
					loc = new Location(Bukkit.getWorld(matchActive.getMatch().getSpawns().get(0).getWorld().getName()),
							cuboid.getMinX() + sumX, cuboid.getMinY() + sumY, cuboid.getMinZ() + sumZ);
				} catch (Exception e3) {
					plugin.getLoggerP().info(e3.toString());

				}
			}
		}

		return loc;
	}

	public static void queueTNT(RandomEvents plugin, MatchActive matchActive, Location l, double distance,
			Boolean comprueba) {
		Location l3 = l.clone();
		l3.setY(l3.getY() - 1);
		Location l4 = l.clone();
		l4.setY(l4.getY() - 2);

		if (l3.getBlock() != null
				&& !matchActive.getMapHandler().getBlockDisappear().containsKey(l3.getBlock().getLocation())
				&& l3.getBlock().getType() == XMaterial.TNT.parseMaterial()) {
			Date d = new Date();
			Long time = d.getTime();
			time += (long) (300);
			matchActive.getMapHandler().getBlockDisappear().put(l3.getBlock().getLocation(), time);
		} else if (l4.getBlock() != null
				&& !matchActive.getMapHandler().getBlockDisappear().containsKey(l4.getBlock().getLocation())
				&& l4.getBlock().getType() == XMaterial.TNT.parseMaterial()) {
			Date d = new Date();
			Long time = d.getTime();
			time += (long) (300);
			matchActive.getMapHandler().getBlockDisappear().put(l3.getBlock().getLocation(), time);
			matchActive.getMapHandler().getBlockDisappear().put(l4.getBlock().getLocation(), time);
		}
		if (comprueba && distance < 0.1) {
			Location l2 = l.clone();
			if (Math.abs(l2.getX() % 1) <= 0.3) {
				l2.setX(l2.getX() + 1 * (l2.getX() > 0 ? -1 : 1));
				queueTNT(plugin, matchActive, l2, distance, false);

			} else if (Math.abs(l2.getX() % 1) >= 0.7) {
				l2.setX(l2.getX() - 1 * (l2.getX() > 0 ? -1 : 1));
				queueTNT(plugin, matchActive, l2, distance, false);
			}

			if (Math.abs(l2.getZ() % 1) <= 0.3) {
				l2.setZ(l2.getZ() + 1 * (l2.getZ() > 0 ? -1 : 1));
				queueTNT(plugin, matchActive, l2, distance, false);

			} else if (Math.abs(l2.getZ() % 1) >= 0.7) {
				l2.setZ(l2.getZ() - 1 * (l2.getZ() > 0 ? -1 : 1));
				queueTNT(plugin, matchActive, l2, distance, false);
			}
			if (Math.abs(l.getZ() % 1) <= 0.3) {
				l.setZ(l.getZ() + 1 * (l.getZ() > 0 ? -1 : 1));
				queueTNT(plugin, matchActive, l, distance, false);

			} else if (Math.abs(l.getZ() % 1) >= 0.7) {
				l.setZ(l.getZ() - 1 * (l.getZ() > 0 ? -1 : 1));
				queueTNT(plugin, matchActive, l, distance, false);
			}
		}

	}

	public static void checkBlocksDisappear(RandomEvents plugin, MatchActive matchActive, Date date) {
		List<Location> blocksToDisappear = new ArrayList<Location>();
		Long time = date.getTime();
		for (Entry<Location, Long> entry : matchActive.getMapHandler().getBlockDisappear().entrySet()) {
			if (entry.getValue() <= time) {

				blocksToDisappear.add(entry.getKey());
			}
		}
		for (Location l : blocksToDisappear) {
			matchActive.getMapHandler().getBlockDisappear().remove(l);
			matchActive.getMapHandler().getBlockDisappeared().put(l, l.getBlock().getState().getData());
			l.getBlock().setType(XMaterial.AIR.parseMaterial());

		}

	}

	public static void applyPotionEffects(PotionEffect potEffect, List<Player> players) {
		for (Player p : players) {
			if (p.hasPotionEffect(potEffect.getType())) {
				p.removePotionEffect(potEffect.getType());
			}
			p.addPotionEffect(potEffect);
		}
	}

	public static Inventory createGUI(String name, Stats estadisticas, RandomEvents plugin) {
		Inventory inv = Bukkit.createInventory(null, plugin.getReventConfig().getStatsSize(),
				plugin.getLanguage().getStatsGuiName() + name);
		for (MinigameType minigame : MinigameType.values()) {
			Integer position = -1;
			try {
				Method method = plugin.getReventConfig().getClass()
						.getDeclaredMethod("getStats" + minigame.getCodigo());
				position = (Integer) method.invoke(plugin.getReventConfig());
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}

			if (position >= 0) {

				ItemStack item = new ItemStack(minigame.getMaterial());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName("§6§l" + minigame.getMessage(plugin));
				List<String> lore = new ArrayList<String>();
				Integer wins = estadisticas.getWins(minigame.getCodigo());
				Integer tries = estadisticas.getTries(minigame.getCodigo());
				Double rat = (tries > 0) ? (wins * 1.0 / tries) : 0.;
				BigDecimal ratio = new BigDecimal(rat).setScale(2, RoundingMode.HALF_UP);

				lore.add(plugin.getLanguage().getStatsWins() + wins);
				lore.add(plugin.getLanguage().getStatsTries() + tries);
				lore.add(plugin.getLanguage().getStatsWinsRatio() + ratio.toPlainString());
				itemMeta.setLore(lore);

				itemMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
				itemMeta.getItemFlags().add(ItemFlag.HIDE_POTION_EFFECTS);
				itemMeta.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);

				item.setItemMeta(itemMeta);
				inv.setItem(position, item);
			}
		}

		Integer position = -1;
		try {
			Method method = plugin.getReventConfig().getClass().getDeclaredMethod("getStatsALLTIME");
			position = (Integer) method.invoke(plugin.getReventConfig());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		if (position >= 0) {

			ItemStack item = new ItemStack(XMaterial.NETHER_STAR.parseMaterial());
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName("§6§lAll_Time");
			List<String> lore = new ArrayList<String>();

			Integer wins = 0;
			Integer tries = 0;
			for (Integer w : estadisticas.getWins().values()) {
				wins += w;
			}
			for (Integer t : estadisticas.getTries().values()) {
				tries += t;
			}
			Double rat = (tries > 0) ? (wins * 1.0 / tries) : 0.;
			BigDecimal ratio = new BigDecimal(rat).setScale(2, RoundingMode.HALF_UP);

			lore.add(plugin.getLanguage().getStatsWins() + wins);
			lore.add(plugin.getLanguage().getStatsTries() + tries);
			lore.add(plugin.getLanguage().getStatsWinsRatio() + ratio.toPlainString());
			itemMeta.setLore(lore);
			itemMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
			itemMeta.getItemFlags().add(ItemFlag.HIDE_POTION_EFFECTS);
			itemMeta.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(itemMeta);
			inv.setItem(position, item);
		}

		ItemStack[] contents = inv.getContents();
		int i = 0;
		ItemStack itemFill = plugin.getReventConfig().getStatsFill();
		for (ItemStack item : contents) {
			if (item == null) {
				inv.setItem(i, itemFill);
			}
			i++;
		}
		return inv;
	}

	public static Integer sacaTamanyoGUI(RandomEvents plugin) {
		Integer i = MinigameType.values().length;
		Integer tamanyo = 9;

		if ((i % 9) != 0) {
			tamanyo = i + 9 - (i % 9);
		} else if (tamanyo < i) {
			tamanyo = i;

		}
		return tamanyo;
	}

	public static boolean contieneMaterialData(Block block, Match match) {
		Boolean res = false;
		// TODO
		// if (plugin.getMatchActive().getMatch().getDatas()
		// .contains(evt.getBlock().getState().getData())) {
		//
		// }

		for (MaterialData matDat : match.getDatas()) {
			Material mat = null;
			byte data = block.getState().getData().getData();

			if (block.getState().getData().getItemType().toString().contains("_LOG")) {
				data = Byte.valueOf("" + data % 4);
			}

			if (matDat.getItemType().toString().contains("LEGACY_")) {
				mat = block.getState().getData().getItemType();
			} else {
				mat = block.getType();
			}

			if (matDat.getItemType() == mat && matDat.getData() == data) {
				res = true;
			}
		}

		return res;
	}

	public static BannedPlayers cargarBannedPlayers(RandomEvents plugin) {

		BannedPlayers banned = new BannedPlayers();
		File file = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//banned-players.json");
		if (file.exists()) {

			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				banned = UtilidadesJson.fromJSONToBanned(plugin, br);

			} catch (FileNotFoundException e) {
				plugin.getLoggerP().info(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					plugin.getLoggerP().info(e.getMessage());
				}
			}

		}
		return banned;
	}

	public static void terminaCreacionBannedPlayers(RandomEvents plugin, BannedPlayers banned) {
		try {
			String json = UtilidadesJson.fromBannedToJSON(plugin, banned);
			if (json != null) {

				File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//banned-players.json");
				if (bossFile.exists()) {
					bossFile.delete();
				}
				bossFile.createNewFile();

				FileWriter fw = new FileWriter(bossFile, true);

				PrintWriter pw = new PrintWriter(fw);

				pw.println(json);

				pw.flush();

				pw.close();

			} else {
				plugin.getLoggerP().info("JSON was null.");
			}
		} catch (Exception e) {
			plugin.getLoggerP().info(e.getMessage());

		}

	}

	public static String calculateTime(long seconds, RandomEvents plugin) {
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(day);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.DAYS.toMinutes(day)
				- TimeUnit.HOURS.toMinutes(hours);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.DAYS.toSeconds(day)
				- TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minute);

		String s = "";

		if (day > 0) {
			s += plugin.getLanguage().getDaysFormat().replace("%days%", "" + day);
		}
		if (hours != 0) {
			s += plugin.getLanguage().getHoursFormat().replace("%hours%", "" + hours);
		}
		if (minute != 0) {
			s += plugin.getLanguage().getMinutesFormat().replace("%minutes%", "" + minute);
		}

		if (second != 0) {
			s += plugin.getLanguage().getSecondsFormat().replace("%seconds%", "" + second);
		}
		return s;
	}

	public static String calculateTimeHoursPoints(long seconds) {
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(day);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.DAYS.toMinutes(day)
				- TimeUnit.HOURS.toMinutes(hours);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.DAYS.toSeconds(day)
				- TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minute);

		String s = "";
		hours = hours + 24 * day;

		if (hours > 9) {
			s += hours + "";
		} else if (hours > 0) {
			s += "0" + hours;
		} else {
			s += "00";
		}
		s += ":";

		if (minute > 9) {
			s += minute + "";
		} else if (minute > 0) {
			s += "0" + minute;
		} else {
			s += "00";
		}
		s += ":";
		if (second > 9) {
			s += second + "";
		} else if (second > 0) {
			s += "0" + second;
		} else {
			s += "00";
		}
		return s;
	}

	public static String calculateTimeTwoPoints(long seconds) {
		long minute = TimeUnit.SECONDS.toMinutes(seconds);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(minute);

		String s = "";

		if (minute > 9) {
			s += minute + "";
		} else if (minute > 0) {
			s += "0" + minute;
		} else {
			s += "00";
		}
		s += ":";
		if (second > 9) {
			s += second + "";
		} else if (second > 0) {
			s += "0" + second;
		} else {
			s += "00";
		}
		return s;
	}

	public static boolean checkBanned(Player player, RandomEvents plugin) {
		Boolean res = Boolean.FALSE;
		Long now = (new Date()).getTime();
		if (plugin.getReventConfig().getBannedPlayers().getBannedPlayers().containsKey(player.getName())) {
			if (plugin.getReventConfig().getBannedPlayers().getBannedPlayers().get(player.getName()) > now) {
				res = Boolean.TRUE;
			} else {
				plugin.getReventConfig().getBannedPlayers().getBannedPlayers().remove(player.getName());
			}

		}
		return res;
	}

	public static void checkDamageCounter(RandomEvents plugin, MatchActive matchActive) {
		List<Player> playerMuertos = new ArrayList<Player>();
		if (matchActive.getDamageCounter() >= plugin.getReventConfig().getIdleTimeForDamage()) {
			for (Player p : matchActive.getPlayerHandler().getPlayersObj()) {
				p.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getIdleDamage());
				if (p.getHealth() > 1) {
					p.damage(1);
				} else {
					playerMuertos.add(p);
				}
			}
		} else {
			matchActive.setDamageCounter(matchActive.getDamageCounter() + 1);
		}
		for (Player p : playerMuertos) {
			matchActive.echaDePartida(p, true, true, false);
		}

	}

	public static Map<Schedule, Date> nextEvent(List<Schedule> schedules, Player player, RandomEvents plugin) {
		Map<Schedule, Date> res = new HashMap<Schedule, Date>();
		Schedule sch = null;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		Integer day = c.get(Calendar.DAY_OF_WEEK);
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		Integer minute = c.get(Calendar.MINUTE);
		List<Schedule> sched = new ArrayList<Schedule>();
		for (int i = 0; i < 7; i++) {
			if (sch == null) {
				for (Schedule s : schedules) {
					if ((s.getDay() == DayWeek.EVERYDAY.getPosition() || s.getDay() == day)
							&& (s.getHour() > hour || (s.getHour() == hour && s.getMinute() > minute))) {
						sched.add(s);
					}
				}
				for (Schedule schedule : sched) {
					if (sch == null || sch.getHour() > schedule.getHour()
							|| (sch.getHour() == schedule.getHour() && sch.getMinute() > schedule.getMinute())) {
						sch = schedule;
					}
				}
				if (sch == null)
					c.add(Calendar.DAY_OF_WEEK, 1);
				day = c.get(Calendar.DAY_OF_WEEK);
				hour = 0;
				minute = 0;

			}

		}
		c.set(Calendar.HOUR_OF_DAY, sch.getHour());
		c.set(Calendar.MINUTE, sch.getMinute());
		c.set(Calendar.SECOND, 0);
		res.put(sch, c.getTime());

		return res;

	}

	public static String enviaInfoCreacion(Match match, Player player, RandomEvents plugin) {
		String info = plugin.getLanguage().getTagPlugin() + " ";
		if (match.getMinigame() == null) {
			info += Constantes.SALTO_LINEA + "§e§l " + Creacion.MINIGAME_TYPE.ordinal() + " - "
					+ Creacion.MINIGAME_TYPE.getMessage();

		} else {

			for (Creacion c : Creacion.getCreaciones(match)) {
				if (!match.getMinigame().equals(MinigameType.WDROP)
						|| (match.getMinigame().equals(MinigameType.WDROP) && !c.equals(Creacion.ARENA_SPAWNS))) {
					info += Constantes.SALTO_LINEA + "§e§l " + c.getPosition() + " - " + c.toString();

					switch (c) {

					case MINIGAME_TYPE:
						if (match.getMinigame() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getMinigame();
						}

						break;
					case BATTLE_NAME:
						if (match.getName() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getName();
						}

						break;
					case PERMISSION_OPTIONAL:
						if (match.getPermission() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getPermission();
						}

						break;
					case USE_OWN_INVENTORY:
						if (match.getUseOwnInventory() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ (match.getUseOwnInventory() ? "Yes" : "No");
						}

						break;
					case ALL_BLOCKS_ALLOWED:
						if (match.getAllMaterialAllowed() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ (match.getAllMaterialAllowed() ? "Yes" : "No");
						}

						break;
					case GAMEMODE:
						if (match.getGamemode() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getGamemode().toString();
						}

						break;

					case AMOUNT_PLAYERS:
						if (match.getAmountPlayers() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getAmountPlayers();
						}
						break;
					case AMOUNT_PLAYERS_MIN:
						if (match.getAmountPlayersMin() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getAmountPlayersMin();
						}
						break;

					case NUMBER_OF_TEAMS:
						if (match.getNumberOfTeams() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getNumberOfTeams();
						}
						break;
					case ID_NPC:
						if (match.getNPCId() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getNPCId();
						}
						break;
					case SPAWN_PLAYER:
						if (match.getPlayerSpawn() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getPlayerSpawn().getWorld().getName() + match.getPlayerSpawn().getX() + ", "
									+ match.getPlayerSpawn().getY() + ", " + match.getPlayerSpawn().getZ();
						}
						break;
					case ARENA_SPAWNS:
					case TEAM_SPAWNS:
					case ANOTHER_ARENA_SPAWNS:
					case ANOTHER_TEAM_SPAWNS:
						if (match.getSpawns() != null && !match.getSpawns().isEmpty()) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Spawns completed";
						}
						break;
					case SPECTATOR_SPAWNS:
						if (match.getSpectatorSpawns() != null && !match.getSpectatorSpawns().isEmpty()) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Spawns completed";
						}
						break;
					case REWARDS:
					case ANOTHER_REWARDS:
						if (match.getRewards() != null && !match.getRewards().isEmpty()) {
							for (String s : match.getRewards()) {
								info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + s;
							}
						}

						break;
					case KITS:
						if (match.getKits() != null && !match.getKits().isEmpty()) {
							for (String s : match.getKits()) {
								info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + s;
							}
						}
						break;
					case TIMER_MOB_SPAWN:
					case TIMER_ARROW_SPAWN:
					case TIMER_ANVIL_SPAWN:
					case TIMER_GEM_SPAWN:
					case TIMER_BOMB:
					case WARMUP_TIME:
					case SECONDS_TO_SPAWN_BEAST:

						if (match.getSecondsMobSpawn() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getSecondsMobSpawn();
						}
						break;
					case MOB_NAME:
						if (match.getMob() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getMob();
						}
						break;
					case MOB_SPAWN:
					case ENTITY_SPAWNS:
					case CANNON_SPAWNS:
					case ANOTHER_ENTITY_SPAWNS:
						if (match.getEntitySpawns() != null && !match.getEntitySpawns().isEmpty()) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Spawns completed";
						}
						break;
					case PLAY_TIME:
					case SHRINK_TIME:
						if (match.getTiempoPartida() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getTiempoPartida();
						}
						break;
					case NO_MOVE_TIME:
						if (match.getSecondsToBegin() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + match.getSecondsToBegin();
						}
						break;
					case ARROW_LOCATION1:
					case GEM_LOCATION1:
					case KOTH_LOCATION1:
					case GOAL_LOCATION1:
					case MAP_LOCATION1:
						if (match.getLocation1() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getLocation1().getWorld().getName() + match.getLocation1().getX() + ", "
									+ match.getLocation1().getY() + ", " + match.getLocation1().getZ();
						}
						break;
					case ARROW_LOCATION2:
					case GEM_LOCATION2:
					case KOTH_LOCATION2:
					case GOAL_LOCATION2:
					case MAP_LOCATION2:
						if (match.getLocation2() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getLocation2().getWorld().getName() + match.getLocation2().getX() + ", "
									+ match.getLocation2().getY() + ", " + match.getLocation2().getZ();
						}
						break;
					case SPAWN_BEAST:
						if (match.getBeastSpawn() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getBeastSpawn().getWorld().getName() + match.getBeastSpawn().getX() + ", "
									+ match.getBeastSpawn().getY() + ", " + match.getBeastSpawn().getZ();
						}
						break;
					case INVENTORY_BEAST:
						if (match.getInventoryBeast() != null) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Inventory completed";
						}
						break;
					case TNT_TAG_HEAD:
						if (match.getHead() != null) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Head set";
						}
						break;
					case INVENTORY_RUNNERS:
						if (match.getInventoryRunners() != null) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Inventory completed";
						}
						break;
					case INVENTORY_CHESTS:
						if (match.getInventoryChests() != null) {
							for (ItemStack i : match.getInventoryChests().getContents()) {
								info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9" + i.getType() + "x"
										+ i.getAmount();
							}
						}
						break;
					case BLOCKS_ALLOWED:
					case MATERIAL_SPLEEF:
					case ANOTHER_MATERIAL_SPLEEF:
						if (match.getDatas() != null && !match.getDatas().isEmpty()) {
							for (MaterialData s : match.getDatas()) {
								info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
										+ s.getItemType().toString() + " : " + s.getData();
							}
						}
						break;
					case WATER_DROP_SCENES:
						if (match.getScenes() != null && !match.getScenes().isEmpty()) {
							for (String s : match.getScenes()) {
								info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + s;
							}
						}
						break;
					default:
						break;

					}
				}
			}

		}
		return info;
	}

	public static String compruebaMatchCorrecta(Match match, RandomEvents plugin) {
		Boolean res = Boolean.TRUE;
		for (Creacion c : Creacion.getCreaciones(match)) {

			switch (c) {

			case MINIGAME_TYPE:
				if (match.getMinigame() == null) {
					res = Boolean.FALSE;
				}

				break;
			case BATTLE_NAME:
				if (match.getName() == null) {
					res = Boolean.FALSE;
				}

				break;
			case AMOUNT_PLAYERS:
				if (match.getAmountPlayers() == null) {
					res = Boolean.FALSE;
				}
				break;
			case AMOUNT_PLAYERS_MIN:
				if (match.getAmountPlayersMin() == null) {
					res = Boolean.FALSE;
				}
				break;
			case NUMBER_OF_TEAMS:
				if (match.getNumberOfTeams() == null) {
					res = Boolean.FALSE;
				}
				break;
			case SPAWN_PLAYER:
				if (match.getPlayerSpawn() == null) {
					res = Boolean.FALSE;

				}
				break;
			case ARENA_SPAWNS:
			case ANOTHER_ARENA_SPAWNS:
			case TEAM_SPAWNS:
			case ANOTHER_TEAM_SPAWNS:
				if (match.getSpawns() == null || match.getSpawns().isEmpty()) {
					if (match.getMinigame() == null || !match.getMinigame().equals(MinigameType.WDROP)) {

						res = Boolean.FALSE;
					}
				}
				break;
			case SPECTATOR_SPAWNS:
				break;
			case REWARDS:
			case ANOTHER_REWARDS:

				break;
			case KITS:
				if (match.getKits() == null || match.getKits().isEmpty()) {
					res = Boolean.FALSE;
				}
				break;
			case ID_NPC:
				if (match.getNPCId() == null) {
					res = Boolean.FALSE;
				}
				break;
			case TIMER_MOB_SPAWN:
			case TIMER_ARROW_SPAWN:
			case TIMER_ANVIL_SPAWN:
			case TIMER_GEM_SPAWN:
			case TIMER_BOMB:
			case WARMUP_TIME:
			case SECONDS_TO_SPAWN_BEAST:

				if (match.getSecondsMobSpawn() == null) {
					res = Boolean.FALSE;
				}
				break;
			case MOB_NAME:
				if (match.getMob() == null) {
					res = Boolean.FALSE;
				}
				break;
			case MOB_SPAWN:
			case ENTITY_SPAWNS:
			case CANNON_SPAWNS:
			case ANOTHER_ENTITY_SPAWNS:
				break;
			case PLAY_TIME:
			case SHRINK_TIME:
				if (match.getTiempoPartida() == null) {
					res = Boolean.FALSE;
				}
				break;
			case NO_MOVE_TIME:
				if (match.getSecondsToBegin() == null) {
					res = Boolean.FALSE;
				}
				break;
			case ARROW_LOCATION1:
			case GEM_LOCATION1:
			case KOTH_LOCATION1:
			case GOAL_LOCATION1:
			case MAP_LOCATION1:
				if (match.getLocation1() == null) {
					res = Boolean.FALSE;

				}
				break;
			case ARROW_LOCATION2:
			case GEM_LOCATION2:
			case KOTH_LOCATION2:
			case GOAL_LOCATION2:
			case MAP_LOCATION2:
				if (match.getLocation2() == null) {
					res = Boolean.FALSE;

				}
				break;
			case SPAWN_BEAST:
				if (match.getBeastSpawn() == null) {
					res = Boolean.FALSE;

				}
				break;
			case INVENTORY_BEAST:
				if (match.getInventoryBeast() == null) {

					res = Boolean.FALSE;
				}
				break;

			case INVENTORY_RUNNERS:
				if (match.getInventoryRunners() == null) {

					res = Boolean.FALSE;
				}
				break;

			case MATERIAL_SPLEEF:
			case ANOTHER_MATERIAL_SPLEEF:
				if (match.getDatas() == null || match.getDatas().isEmpty()) {
					res = Boolean.FALSE;

				}
				break;
			case WATER_DROP_SCENES:
				if (match.getScenes() == null || match.getScenes().isEmpty()) {
					res = Boolean.FALSE;
				}
				break;
			default:
				break;

			}
		}
		String respuesta = null;

		if (!res) {
			respuesta = plugin.getLanguage().getLacksInfoCreation();
		}

		return respuesta;
	}

	public static void setWorldBorder(RandomEvents plugin, Location center, Double size, Player p) {
		if (plugin.getReventConfig().isShowBorders()) {
			plugin.getApi().createWorldBorder(p, size, center);
		}

	}

	public static void spawnParticles(Particle1711 particle, RandomEvents plugin, Location location) {
		if (plugin.getReventConfig().isUseParticles()) {
			try {
				ParticleDisplay pa = ParticleDisplay.display(plugin.getApi(), location, particle);

				String part = plugin.getReventConfig().getParticleType().toUpperCase();
				if (part.equals("RANDOM")) {
					part = ParticleEffectRevent.values()[plugin.getRandom()
							.nextInt(ParticleEffectRevent.values().length)].toString().toUpperCase();
				}
				switch (part) {
				case "BLACKSUN":
					XParticle.blackSun(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRadiusRate(),
							plugin.getReventConfig().getParticleRate(),
							plugin.getReventConfig().getParticleRateChange(), pa);
					break;
				case "CIRCLE":

					XParticle.circle(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRate(), pa);
					break;
				case "CRESCENT":
					XParticle.crescent(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRate(), pa);
					break;
				case "CYLINDER":
					XParticle.cylinder(plugin.getReventConfig().getParticleHeight(),
							plugin.getReventConfig().getParticleRadius(), plugin.getReventConfig().getParticleRate(),
							pa);
					break;
				case "DIAMOND":
					XParticle.diamond(plugin.getReventConfig().getParticleRadiusRate(),
							plugin.getReventConfig().getParticleRate(), plugin.getReventConfig().getParticleHeight(),
							pa);
					break;
				case "ELLIPSE":
					XParticle.ellipse(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRadius2(), plugin.getReventConfig().getParticleRate(),
							pa);
					break;
				case "EYE":
					XParticle.eye(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRadius2(), plugin.getReventConfig().getParticleRate(),
							plugin.getReventConfig().getParticleExtension(), pa);
					break;
				case "FILLEDCIRCLE":
					XParticle.filledCircle(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRate(),
							plugin.getReventConfig().getParticleRadiusRate(), pa);
					break;
				case "ILLUMINATI":
					XParticle.illuminati(plugin.getReventConfig().getParticleSize(),
							plugin.getReventConfig().getParticleExtension(), pa);
					break;
				case "INFINITY":
					XParticle.infinity(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRate(), pa);
					break;
				case "RING":
					XParticle.ring(plugin.getReventConfig().getParticleRate(),
							plugin.getReventConfig().getParticleRadius(), plugin.getReventConfig().getParticleRadius2(),
							pa);
					break;
				case "SPHERE":
					location.setY(location.getY() + 1);
					XParticle.sphere(plugin.getReventConfig().getParticleRadius(),
							plugin.getReventConfig().getParticleRate(), pa);
					break;
				case "MEGUMINEXPLOSION":
					XParticle.meguminExplosion(plugin, plugin.getReventConfig().getParticleSize(), pa);
					break;
				}

			} catch (Exception e) {
				plugin.getLoggerP().info("RandomEvents:: Particle failed to spawn");
			}
		}

	}

	public static List<String> prepareLines(RandomEvents plugin, MatchActive matchActive, Player player) {
		List<String> lines = new ArrayList<String>();

		List<String> playersDead = new ArrayList<String>();
		playersDead.addAll(matchActive.getPlayerHandler().getPlayersTotal());
		playersDead.removeAll(matchActive.getPlayerHandler().getPlayers());

		List<Player> playersDeadObj = new ArrayList<Player>();
		playersDeadObj.addAll(matchActive.getPlayerHandler().getPlayersTotalObj());
		playersDeadObj.removeAll(matchActive.getPlayerHandler().getPlayersObj());
		lines.add("");
		switch (matchActive.getMatch().getMinigame()) {
		case PAINTBALL:
		case BATTLE_ROYALE_TEAMS:
		case TSW_REAL:

			lines = prepareLinesTeam(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesDeadAliveTeam(lines, matchActive, plugin, playersDeadObj);

			break;
		case BATTLE_ROYALE_TEAM_2:
		case TSW:

			lines = prepareLinesTeammate(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesDeadAlive(lines, matchActive, plugin, playersDead);

			break;
		case BOMB_TAG:
			lines = prepareLinesHolder(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");
			lines = prepareLinesDeadAlive(lines, matchActive, plugin, playersDead);

			break;
		case TSG_REAL:
			lines = prepareLinesTeam(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");
			lines = prepareLinesDeadAliveTeam(lines, matchActive, plugin, playersDeadObj);
			break;
		case TSG:
			lines = prepareLinesTeammate(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");
			lines = prepareLinesDeadAlive(lines, matchActive, plugin, playersDead);
			break;
		case SG:
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");
			lines = prepareLinesDeadAlive(lines, matchActive, plugin, playersDead);
			break;
		case BATTLE_ROYALE:
		case BATTLE_ROYALE_CABALLO:
		case ESCAPE_ARROW:
		case ANVIL_SPLEEF:
		case BOMBARDMENT:
		case KNOCKBACK_DUEL:
		case SPLEEF:
		case SPLEGG:
		case SW:
		case TNT_RUN:
		case RACE:
		case RED_GREEN_LIGHT:
			lines = prepareLinesDeadAlive(lines, matchActive, plugin, playersDead);
			break;

		case ESCAPE_FROM_BEAST:
			lines = prepareLinesBeast(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesDeadAlive(lines, matchActive, plugin, playersDead);
			break;
		case TOP_KILLER_TEAM_2:
			lines = prepareLinesTeammate(lines, matchActive, plugin, player);
			lines.add("");
		case TOP_KILLER:
		case FISH_SLAP:
		case QUAKECRAFT:
		case OITC:
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");

			Map<String, Integer> mapaOrdenado = sortByValue(matchActive.getPuntuacion(), true);
			for (Entry<String, Integer> entrada : mapaOrdenado.entrySet()) {
				lines.add(plugin.getLanguage().getScoreboardPoints().replaceAll("%name%", entrada.getKey())
						.replaceAll("%points%", "" + entrada.getValue()));
			}
			break;

		case HOEHOEHOE:
		case SPLATOON:
		case TOP_KILLER_TEAMS:
		case PAINTBALL_TOP_KILL:

			lines = prepareLinesTeam(lines, matchActive, plugin, player);
			lines.add("");

			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");
			List<String> linesPoints = new ArrayList<String>();
			Map<Petos, Integer> mapaEquipo = new HashMap<Petos, Integer>();
			Map<String, Integer> mapaOrdenadoHoe = sortByValue(matchActive.getPuntuacion(), true);
			for (Entry<String, Integer> entrada : mapaOrdenadoHoe.entrySet()) {
				String line = plugin.getLanguage().getScoreboardPointsTeam().replaceAll("%name%", entrada.getKey())
						.replaceAll("%points%", "" + entrada.getValue());

				Player playerAux = getPlayer(matchActive.getPlayerHandler().getPlayersTotalObj(), entrada.getKey());
				if (playerAux != null) {
					Integer equipo = matchActive.getEquipoCopy(playerAux);
					if (equipo != null) {
						Petos peto = Petos.getPeto(equipo);
						if (peto != null) {
							line = line.replaceAll("%team_color%", "" + peto.getChatColor());

							if (mapaEquipo.containsKey(peto)) {
								mapaEquipo.put(peto, mapaEquipo.get(peto) + entrada.getValue());
							} else {
								mapaEquipo.put(peto, entrada.getValue());
							}

						} else {
							line = line.replaceAll("%team_color%", "");

						}

					} else {
						line = line.replaceAll("%team_color%", "");

					}
				} else {
					line = line.replaceAll("%team_color%", "");
				}
				linesPoints.add(line);
			}

			mapaEquipo = sortByValue(mapaEquipo, true);

			for (Entry<Petos, Integer> entrada : mapaEquipo.entrySet()) {
				String line = plugin.getLanguage().getScoreboardTeamPoints()
						.replaceAll("%team_name%", entrada.getKey().getName())
						.replaceAll("%team_color%", "" + entrada.getKey().getChatColor())
						.replaceAll("%points%", "" + entrada.getValue());
				lines.add(line);
			}
			lines.add("");
			lines.addAll(linesPoints);

			break;
		case KOTH:

			lines = prepareLinesHolder(lines, matchActive, plugin, player);
			lines.add("");
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");

			Map<String, Integer> mapaOrdenadoKoth = sortByValue(matchActive.getPuntuacion(), true);
			for (Entry<String, Integer> entrada : mapaOrdenadoKoth.entrySet()) {
				lines.add(plugin.getLanguage().getScoreboardPoints().replaceAll("%name%", entrada.getKey())
						.replaceAll("%points%", "" + entrada.getValue()));
			}
			break;

		case GEM_CRAWLER:
			Map<String, Integer> mapaOrdenado3 = sortByValue(matchActive.getPuntuacion(), true);
			for (Entry<String, Integer> entrada : mapaOrdenado3.entrySet()) {
				lines.add(plugin.getLanguage().getScoreboardPoints().replaceAll("%name%", entrada.getKey())
						.replaceAll("%points%", "" + entrada.getValue()));
			}
			break;
		case WDROP:
			lines = prepareLinesStep(lines, plugin, matchActive, player);
			lines.add("");

			Map<Player, Integer> mapaOrdenadoStep = sortByValue(matchActive.getStep(), true);
			for (Entry<Player, Integer> entrada : mapaOrdenadoStep.entrySet()) {
				lines.add(plugin.getLanguage().getScoreboardPoints().replaceAll("%name%", entrada.getKey().getName())
						.replaceAll("%points%", "" + (entrada.getValue() + 1)));
			}
			break;
		default:
			break;
		}
		lines.add("");
		if (lines.size() > 15) {
			lines = lines.subList(0, 15);
		}
		List<String> linesFormated = new ArrayList<String>();
		for (String l : lines) {
			if (l.length() > 30) {
				linesFormated.add(l.substring(0, 29));
			} else {
				linesFormated.add(l);
			}
		}
		lines = linesFormated;
		return lines;
	}

	private static Player getPlayer(List<Player> playersTotalObj, String key) {
		Player player = null;
		for (Player p : playersTotalObj) {
			if (p.getName().equals(key)) {
				player = p;
			}
		}
		return player;
	}

	private static List<String> prepareLinesStep(List<String> lines, RandomEvents plugin, MatchActive matchActive,
			Player player) {

		lines.add(plugin.getLanguage().getScoreboardStep()
				.replaceAll("%name%", matchActive.getWaterDrops().get(matchActive.getStep().get(player)).getName())
				.replaceAll("%max%", "" + matchActive.getWaterDrops().size())
				.replaceAll("%actual%", "" + (matchActive.getStep().get(player) + 1)));

		return lines;
	}

	private static List<String> prepareLinesBeast(List<String> lines, MatchActive matchActive, RandomEvents plugin,
			Player player) {
		if (matchActive.getPlayerHandler().getPlayerContador() == null) {
			lines.add(plugin.getLanguage().getScoreboardBeast().replaceAll("%name%", "-"));
		} else {
			lines.add(plugin.getLanguage().getScoreboardBeast().replaceAll("%name%",
					matchActive.getPlayerHandler().getPlayerContador().getName()));

		}
		return lines;
	}

	private static List<String> prepareLinesHolder(List<String> lines, MatchActive matchActive, RandomEvents plugin,
			Player player) {
		if (matchActive.getPlayerHandler().getPlayerContador() == null) {
			lines.add(plugin.getLanguage().getScoreboardHolder().replaceAll("%name%", "-"));
		} else {
			lines.add(plugin.getLanguage().getScoreboardHolder().replaceAll("%name%",
					matchActive.getPlayerHandler().getPlayerContador().getName()));

		}
		return lines;
	}

	private static List<String> prepareLinesTime(List<String> lines, RandomEvents plugin, MatchActive matchActive) {
		long seconds = (matchActive.getEndDate() - new Date().getTime()) / 1000;
		lines.add(plugin.getLanguage().getScoreboardTime().replaceAll("%time%", calculateTimeTwoPoints(seconds)));
		return lines;
	}

	private static List<String> prepareLinesTeammate(List<String> lines, MatchActive matchActive, RandomEvents plugin,
			Player player) {
		Integer equipo = matchActive.getEquipo(player);
		if (equipo != null) {
			List<Player> playersTeam = new ArrayList<Player>(matchActive.getPlayerHandler().getEquipos().get(equipo));
			if (playersTeam != null) {
				playersTeam.remove(player);
				if (playersTeam.isEmpty()) {
					lines.add(plugin.getLanguage().getScoreboardTeammate().replaceAll("%name%", "-"));
				} else {
					lines.add(plugin.getLanguage().getScoreboardTeammate().replaceAll("%name%",
							playersTeam.get(0).getName()));
				}
			}
		}
		return lines;
	}

	private static List<String> prepareLinesTeam(List<String> lines, MatchActive matchActive, RandomEvents plugin,
			Player player) {
		Integer equipo = matchActive.getEquipoCopy(player);
		if (equipo != null) {
			Petos peto = Petos.getPeto(equipo);
			if (peto != null) {
				lines.add(plugin.getLanguage().getScoreboardTeam().replaceAll("%team_color%", "" + peto.getChatColor())
						.replaceAll("%name%", peto.getName()));
			}

		}
		return lines;
	}

	private static List<String> prepareLinesDeadAlive(List<String> lines, MatchActive matchActive, RandomEvents plugin,
			List<String> playersDead) {
		List<String> players = new ArrayList<String>();
		players.addAll(matchActive.getPlayerHandler().getPlayers());
		for (String p : players) {
			lines.add(plugin.getLanguage().getScoreboardAlive().replaceAll("%name%", p));
		}
		for (String p : playersDead) {
			lines.add(plugin.getLanguage().getScoreboardDeath().replaceAll("%name%", p));
		}
		return lines;
	}

	private static List<String> prepareLinesDeadAliveTeam(List<String> lines, MatchActive matchActive,
			RandomEvents plugin, List<Player> playersDead) {
		List<Player> players = new ArrayList<Player>();
		players.addAll(matchActive.getPlayerHandler().getPlayersObj());
		for (Player p : players) {
			Integer equipo = matchActive.getEquipoCopy(p);
			if (equipo != null) {
				Petos peto = Petos.getPeto(equipo);
				if (peto != null) {
					lines.add(plugin.getLanguage().getScoreboardTeamAlive()
							.replaceAll("%team_color%", "" + peto.getChatColor()).replaceAll("%name%", p.getName()));
				}
			}
		}
		for (Player p : playersDead) {
			Integer equipo = matchActive.getEquipoCopy(p);

			if (equipo != null) {
				Petos peto = Petos.getPeto(equipo);
				if (peto != null) {
					lines.add(plugin.getLanguage().getScoreboardTeamDeath()
							.replaceAll("%team_color%", "" + peto.getChatColor()).replaceAll("%name%", p.getName()));
				}
			}
		}
		return lines;
	}

	public static String enviaInfoCreacionWaterDrop(WaterDropStep waterDrop, Player player, RandomEvents plugin) {
		String info = plugin.getLanguage().getTagPlugin() + " ";

		for (CreacionWaterDrop c : CreacionWaterDrop.values()) {
			info += Constantes.SALTO_LINEA + "§e§l " + c.getPosition() + " - " + c.toString();

			switch (c) {

			case NAME:
				if (waterDrop.getName() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + waterDrop.getName();
				}

				break;

			case SPAWN:
				if (waterDrop.getSpawn() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
							+ waterDrop.getSpawn().getWorld().getName() + waterDrop.getSpawn().getX() + ", "
							+ waterDrop.getSpawn().getY() + ", " + waterDrop.getSpawn().getZ();
				}
				break;

			case GOAL_LOCATION1:
				if (waterDrop.getGoalLoc1() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
							+ waterDrop.getGoalLoc1().getWorld().getName() + waterDrop.getGoalLoc1().getX() + ", "
							+ waterDrop.getGoalLoc1().getY() + ", " + waterDrop.getGoalLoc1().getZ();
				}
				break;

			case GOAL_LOCATION2:
				if (waterDrop.getGoalLoc2() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
							+ waterDrop.getGoalLoc2().getWorld().getName() + waterDrop.getGoalLoc2().getX() + ", "
							+ waterDrop.getGoalLoc2().getY() + ", " + waterDrop.getGoalLoc2().getZ();
				}
				break;

			default:
				break;

			}
		}

		return info;
	}

	public static String enviaInfoCreacionKit(Kit kit, Player player, RandomEvents plugin) {
		String info = plugin.getLanguage().getTagPlugin() + " ";

		for (CreacionKit c : CreacionKit.values()) {
			info += Constantes.SALTO_LINEA + "§e§l " + c.getPosition() + " - " + c.toString();

			switch (c) {

			case NAME:
				if (kit.getName() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + kit.getName();
				}

				break;

			case PERMISSION_OPTIONAL:
				if (kit.getPermission() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + kit.getPermission();

				}
				break;
			case INVENTORY:
				if (kit.getInventory() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + "Inventory completed";

				}
				break;
			case ITEM_DESCRIPTIVE:
				if (kit.getItem() != null) {
					info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 " + kit.getItem().toString();

				}
				break;
			default:
				break;

			}
		}

		return info;
	}

	public static String compruebaWaterDropCorrecto(WaterDropStep waterDrop, RandomEvents plugin) {
		Boolean res = Boolean.TRUE;
		for (CreacionWaterDrop c : CreacionWaterDrop.values()) {

			switch (c) {
			case NAME:
				if (waterDrop.getName() == null) {
					res = Boolean.FALSE;
				}

				break;
			case SPAWN:
				if (waterDrop.getSpawn() == null) {
					res = Boolean.FALSE;

				}
				break;
			case GOAL_LOCATION1:
				if (waterDrop.getGoalLoc1() == null) {
					res = Boolean.FALSE;

				}
				break;
			case GOAL_LOCATION2:
				if (waterDrop.getGoalLoc2() == null) {
					res = Boolean.FALSE;

				}
				break;
			default:
				break;

			}
		}
		String respuesta = null;

		if (!res) {
			respuesta = plugin.getLanguage().getLacksInfoCreation();
		}

		return respuesta;
	}

	public static String compruebaKitCorrecto(Kit kit, RandomEvents plugin) {
		Boolean res = Boolean.TRUE;
		for (CreacionKit c : CreacionKit.values()) {

			switch (c) {
			case NAME:
				if (kit.getName() == null) {
					res = Boolean.FALSE;
				}

				break;
			case INVENTORY:
				if (kit.getInventory() == null) {
					res = Boolean.FALSE;

				}
				break;
			case ITEM_DESCRIPTIVE:
				if (kit.getItem() == null) {
					res = Boolean.FALSE;

				}
				break;

			default:
				break;

			}
		}
		String respuesta = null;

		if (!res) {
			respuesta = plugin.getLanguage().getLacksInfoCreation();
		}

		return respuesta;
	}

	public static List<WaterDropStep> cargarWaterDrops(RandomEvents plugin) {
		List<WaterDropStep> listaPartidas = new ArrayList<WaterDropStep>();
		File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//waterdrop");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		for (File file : dataFolder.listFiles()) {
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				WaterDropStep match = UtilidadesJson.fromJSONToWD(plugin, br);
				if (match != null)
					listaPartidas.add(match);

			} catch (FileNotFoundException e) {
				plugin.getLoggerP().info(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					plugin.getLoggerP().info(e.getMessage());
				}
			}

		}
		return listaPartidas;
	}

	public static List<Kit> cargarKits(RandomEvents plugin) {
		List<Kit> listaPartidas = new ArrayList<Kit>();
		File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//kits");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		for (File file : dataFolder.listFiles()) {
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				Kit match = UtilidadesJson.fromJSONToKit(plugin, br);
				if (match != null)
					listaPartidas.add(match);

			} catch (FileNotFoundException e) {
				plugin.getLoggerP().info(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					plugin.getLoggerP().info(e.getMessage());
				}
			}

		}
		return listaPartidas;
	}

	public static Inventory createGUICredits(Player p, Map<String, Integer> creditos, Integer page,
			RandomEvents plugin) {
		Inventory inv = Bukkit.createInventory(null, 45, plugin.getLanguage().getCreditsGuiName());
		ItemStack nextPage = new ItemStack(XMaterial.OAK_SIGN.parseMaterial());
		ItemStack backPage = new ItemStack(XMaterial.OAK_SIGN.parseMaterial());
		ItemMeta nextPageMeta = nextPage.getItemMeta();
		ItemMeta backPageMeta = backPage.getItemMeta();
		nextPageMeta.setDisplayName(plugin.getLanguage().getCreditsGuiPage() + " " + (page + 1));
		backPageMeta.setDisplayName(plugin.getLanguage().getCreditsGuiPage() + " " + (page - 1));
		nextPage.setItemMeta(nextPageMeta);
		backPage.setItemMeta(backPageMeta);

		for (int i = page * 36; i < (page + 1) * 36 && i < plugin.getMatchesAvailable().size(); i++) {
			Match match = plugin.getMatchesAvailable().get(i);
			ItemStack cabeza = new ItemStack(match.getMinigame().getMaterial());
			ItemMeta cabezaMeta = cabeza.getItemMeta();
			cabezaMeta.setDisplayName("§e§l" + match.getName());

			List<String> lore = new ArrayList<String>();
			if (p.hasPermission(Constantes.PERM_COOLDOWN_BYPASS)) {
				if (plugin.getMatchActive() != null) {
					lore.add(plugin.getLanguage().getCreditsEventRunning());

				} else {
					lore.add(plugin.getLanguage().getCreditsReady());

				}
			} else if (p.hasPermission(Constantes.PERM_COOLDOWN)) {
				if (plugin.getMatchActive() != null) {
					lore.add(plugin.getLanguage().getCreditsEventRunning());

				} else {
					if (plugin.getCooldowns().containsKey(p.getName())) {
						if (plugin.getCooldowns().get(p.getName()).before(new Date())) {
							plugin.getCooldowns().remove(p.getName());
							lore.add(plugin.getLanguage().getCreditsReady());

						} else {
							if (plugin.getReventConfig().isMysqlEnabled()) {

								Integer credits = creditos.containsKey(match.getName()) ? creditos.get(match.getName())
										: 0;
								if (credits != 0) {
									lore.add(plugin.getLanguage().getCreditsBal().replaceAll("%credits%",
											"" + (creditos.containsKey(match.getName()) ? creditos.get(match.getName())
													: 0)));
								} else {
									lore.add(plugin.getLanguage().getCreditsCooldown());

								}

							} else {
								lore.add(plugin.getLanguage().getCreditsCooldown());
							}
						}

					} else {
						lore.add(plugin.getLanguage().getCreditsReady());
					}

				}
			} else if (plugin.getReventConfig().isMysqlEnabled()) {
				lore.add(plugin.getLanguage().getCreditsBal().replaceAll("%credits%",
						"" + (creditos.containsKey(match.getName()) ? creditos.get(match.getName()) : 0)));
			}
			cabezaMeta.setLore(lore);
			cabezaMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			cabezaMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			cabezaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			cabeza.setItemMeta(cabezaMeta);
			inv.setItem(i - (page * 36), cabeza);
		}

		if (page == 0) {
			inv.setItem(44, nextPage);

		} else if (plugin.getMatchesAvailable().size() > (page * 36)) {
			inv.setItem(44, nextPage);
			inv.setItem(36, backPage);
		} else {
			inv.setItem(36, backPage);
		}

		return inv;
	}

	public static Inventory createGUIKits(Player p, Integer page, RandomEvents plugin, MatchActive matchActive) {

		Inventory inv = Bukkit.createInventory(null, 45, plugin.getLanguage().getKitGuiName());

		if (matchActive != null) {
			Match match = matchActive.getMatch();
			List<Kit> kitsAvailable = UtilsRandomEvents.kitsAvailable(p, match.getKits(), plugin);

			Integer size = UtilsRandomEvents.sizeGUIKits(kitsAvailable);
			inv = Bukkit.createInventory(null, size, plugin.getLanguage().getKitGuiName());
			ItemStack nextPage = new ItemStack(XMaterial.OAK_SIGN.parseMaterial());
			ItemStack backPage = new ItemStack(XMaterial.OAK_SIGN.parseMaterial());
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			ItemMeta backPageMeta = backPage.getItemMeta();
			nextPageMeta.setDisplayName(plugin.getLanguage().getCreditsGuiPage() + " " + (page + 1));
			backPageMeta.setDisplayName(plugin.getLanguage().getCreditsGuiPage() + " " + (page - 1));
			nextPage.setItemMeta(nextPageMeta);
			backPage.setItemMeta(backPageMeta);
			for (int i = page * (size - (kitsAvailable.size() > 45 ? 9 : 0)); i < (page + 1)
					* (size - (kitsAvailable.size() > 45 ? 9 : 0)) && i < kitsAvailable.size(); i++) {
				Kit kit = kitsAvailable.get(i);
				ItemStack cabeza = kit.getItem().clone();
				ItemMeta cabezaMeta = cabeza.getItemMeta();
				if (cabezaMeta.getDisplayName() == null || cabezaMeta.getDisplayName().isEmpty()) {
					cabezaMeta.setDisplayName(
							plugin.getLanguage().getKitDefaultName().replaceAll("%kit_name%", kit.getName()));
				}
				// plugin.getLoggerP().info(cabezaMeta.getDisplayName());
				if (cabezaMeta.getLore() == null || cabezaMeta.getLore().isEmpty()) {
					cabezaMeta.setLore(plugin.getLanguage().getKitDefaultLore());
				}
				cabezaMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
				cabezaMeta.getItemFlags().add(ItemFlag.HIDE_POTION_EFFECTS);
				cabezaMeta.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);

				cabeza.setItemMeta(cabezaMeta);
				inv.setItem(i - (page * (size - 9)), cabeza);
			}
			if (kitsAvailable.size() > 45) {
				if (page == 0) {
					inv.setItem((size - 1), nextPage);

				} else if (kitsAvailable.size() > (page * (size - (kitsAvailable.size() > 45 ? 9 : 0)))) {
					inv.setItem((size - 1), nextPage);
					inv.setItem((size - 9), backPage);
				} else {
					inv.setItem((size - 9), backPage);
				}
			}
		}
		return inv;
	}

	public static Inventory createGUITeams(Player p, Integer page, RandomEvents plugin, MatchActive matchActive) {

		Inventory inv = Bukkit.createInventory(null, 9, plugin.getLanguage().getTeamGuiName());

		if (matchActive != null) {
			Match match = matchActive.getMatch();

			for (int i = 0; i < match.getNumberOfTeams(); i++) {
				Petos peto = Petos.getPeto(i);
				ItemStack cabeza = peto.getClayItem();
				ItemMeta cabezaMeta = cabeza.getItemMeta();

				cabezaMeta.setDisplayName(peto.getChatColor() + peto.getName());
				List<String> lore = new ArrayList<String>();

				List<Player> players = new ArrayList<Player>();

				if (matchActive.getPlayerHandler().getEquipos().containsKey(i)) {
					players.addAll(matchActive.getPlayerHandler().getEquipos().get(i));
				}

				for (Player pla : players) {
					lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + pla.getName());
				}

				cabezaMeta.setLore(lore);

				cabezaMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
				cabezaMeta.getItemFlags().add(ItemFlag.HIDE_POTION_EFFECTS);
				cabezaMeta.getItemFlags().add(ItemFlag.HIDE_ENCHANTS);
				cabeza.setItemMeta(cabezaMeta);
				inv.setItem(i, cabeza);
			}

		}
		return inv;
	}

	private static Integer sizeGUIKits(List<Kit> kitsAvailable) {
		Integer size = kitsAvailable.size();
		if (size > 45) {
			size = 45;
		} else {
			size = size + (9 - size % 9);
		}
		return size;
	}

	public static List<Kit> kitsAvailable(Player p, List<String> kits, RandomEvents plugin) {
		List<Kit> kitsAvailable = new ArrayList<Kit>();
		for (Kit kit : plugin.getKits()) {
			if (kits.contains(kit.getName()) && (kit.getPermission() == null || p.hasPermission(kit.getPermission()))) {
				kitsAvailable.add(kit);
			}
		}
		return kitsAvailable;
	}

	public static void sendCreditsInfo(Player p, Player playerBal, Map<String, Integer> creditos, RandomEvents plugin) {

		String infoBal = plugin.getLanguage().getTagPlugin() + playerBal.getName() + Constantes.SALTO_LINEA;

		for (Entry<String, Integer> entrada : creditos.entrySet()) {
			infoBal += ChatColor.YELLOW + entrada.getKey() + " - "
					+ plugin.getLanguage().getCreditsBal().replaceAll("%credits%", "" + entrada.getValue())
					+ Constantes.SALTO_LINEA;
		}
		p.sendMessage(infoBal);
	}

	public static Match searchEvent(String name, RandomEvents plugin) {
		Match m = null;
		for (Match match : plugin.getMatchesAvailable()) {
			if (match.getName().equals(name)) {
				m = match;
			}
		}
		return m;

	}

	public static void hidePlayers(Player player, List<Player> playersObj, RandomEvents plugin) {
		for (Player p : playersObj) {
			if (!p.equals(player)) {
				player.hidePlayer(p);

			}
		}
		player.setItemInHand(plugin.getReventConfig().getEndVanishItem());
		player.updateInventory();
		plugin.getMatchActive().getPlayerHandler().getPlayersVanish().add(player);
	}

	public static void showPlayers(Player player, List<Player> playersObj, RandomEvents plugin) {
		for (Player p : playersObj) {
			if (!p.equals(player)) {
				player.showPlayer(p);
			}
		}
		player.setItemInHand(plugin.getReventConfig().getVanishItem());
		player.updateInventory();
		plugin.getMatchActive().getPlayerHandler().getPlayersVanish().remove(player);

	}

	public static List<Block> getNearbyBlocks(Match match, Location location, int radius, List<MaterialData> datas,
			Boolean wool, RandomEvents plugin) {
		List<Block> blocks = new ArrayList<Block>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					Location loc = new Location(location.getWorld(), x, y, z);
					if (datas == null || (loc.getBlock() != null && loc.getBlock().getType() != null
							&& (UtilsRandomEvents.contieneMaterialData(loc.getBlock(), match)
									|| (wool && loc.getBlock().getType().toString().toUpperCase().contains("WOOL"))))) {

						blocks.add(loc.getBlock());
					}
				}
			}
		}
		return blocks;
	}

	public static Integer getTeamLessPlayers(RandomEvents plugin, MatchActive matchActive,
			Map<Integer, Set<Player>> teams, Integer numberOfTeams) {
		List<Integer> teamsToSee = new ArrayList<Integer>();
		if ((plugin.getReventConfig().isEquilibrateTeams() && plugin.getReventConfig().isForceNonEmptyTeams())
				|| matchActive.teamsWithPlayers() < 2) {
			for (int i = 0; i < numberOfTeams; i++) {
				teamsToSee.add(i);
			}
		} else {
			teamsToSee.addAll(teams.keySet());
		}
		Integer max = 9999;
		Integer res = 0;
		for (Integer team : teamsToSee) {
			if (!teams.containsKey(team)) {
				if (max > 0) {
					res = team;
					max = 0;
				}
			} else {
				if (max > teams.get(team).size()) {
					max = teams.get(team).size();
					res = team;
				}
			}
		}
		return res;
	}

	public static Integer getTeamMorePlayers(RandomEvents plugin, Map<Integer, Set<Player>> teams) {

		Integer max = 0;
		Integer res = 0;
		for (Integer team : teams.keySet()) {

			if (max < teams.get(team).size()) {
				max = teams.get(team).size();
				res = team;
			}

		}
		return res;

	}

	public static void doCommandsKill(Player p, RandomEvents plugin) {
		for (String cmd : plugin.getReventConfig().getCommandsOnKill()) {

			Boolean ejecutaComando = Boolean.TRUE;
			String[] trozosComandos = cmd.split(" ");

			if (trozosComandos[0].trim().equals(Constantes.PROBABILITY_CMD)) {
				Integer probabilidad = Integer.valueOf(trozosComandos[1]);
				Integer aleatorio = plugin.getRandom().nextInt(100);

				if (aleatorio > probabilidad) {
					ejecutaComando = Boolean.FALSE;
				}
				String nuevoCmd = "";
				if (trozosComandos.length > 2) {
					for (int i = 2; i < trozosComandos.length; i++) {
						nuevoCmd += trozosComandos[i] + " ";
					}
					cmd = nuevoCmd.substring(0, nuevoCmd.length() - 1);
				}
			}
			if (ejecutaComando) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
						cmd.replaceAll("%player%", p.getName()));
			}

		}

	}

	public static void addGlow(RandomEvents plugin, Player player, List<Player> receivers) {
		if (plugin != null && plugin.getReventConfig() != null && plugin.getReventConfig().getIsLibsDisguises() != null
				&& plugin.getReventConfig().getIsLibsDisguises()) {
			try {
				UtilsDisguises.addGlow(player, plugin);
			} catch (Throwable e) {
				plugin.getLoggerP().info(e.toString());
			}
		}
	}

	public static void removeGlow(RandomEvents plugin, Player player, List<Player> receivers) {
		if (plugin != null && plugin.getReventConfig() != null && plugin.getReventConfig().getIsLibsDisguises() != null
				&& plugin.getReventConfig().getIsLibsDisguises()) {
			try {
				UtilsDisguises.removeGlow(player, plugin);
			} catch (Throwable e) {
				plugin.getLoggerP().info(e.toString());
			}
		}
	}

	public static void invinciblePlayer(Player player, RandomEvents plugin) {
		if (plugin.getReventConfig().getInvincibleAfterGame() > 0) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
					plugin.getReventConfig().getInvincibleAfterGame(), 20));
		}

	}

}
