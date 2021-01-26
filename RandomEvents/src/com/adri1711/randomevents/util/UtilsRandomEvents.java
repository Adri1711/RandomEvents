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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.WaterDropStep;
import com.adri1711.randomevents.match.enums.Creacion;
import com.adri1711.randomevents.match.enums.CreacionWaterDrop;
import com.adri1711.randomevents.match.enums.MinigameType;
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
import com.google.common.io.Files;

public class UtilsRandomEvents {

	public static String preparaStringTiempo(Integer tiempo) {

		Integer horas = Double.valueOf(tiempo / 3600.0).intValue();
		Integer minutos = Double.valueOf((tiempo - 3600 * horas) / 60.0).intValue();
		Integer segundos = Double.valueOf((tiempo - 3600 * horas) - 60 * minutos).intValue();
		String resultado = "";
		if (horas != 0) {
			resultado += horas + " h ";
		}
		if (minutos != 0) {
			resultado += minutos + " min ";
		}

		if (segundos != 0) {
			resultado += segundos + " seconds ";
		}

		return resultado;
	}

	//

	public static void terminaCreacionMatch(RandomEvents plugin, Player player) {
		Match match = plugin.getPlayerMatches().get(player.getName());
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
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

				pw.println(json);

				pw.flush();

				pw.close();
				plugin.getPlayersCreation().remove(player.getName());
				plugin.getPlayerMatches().remove(player.getName());
				plugin.getMatches().add(match);
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEndOfArenaCreation());
			} else {
				System.out.println("JSON was null.");
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

				pw.println(json);

				pw.flush();

				pw.close();
				plugin.getPlayersCreationWaterDrop().remove(player.getName());
				plugin.getPlayerWaterDrop().remove(player.getName());
				plugin.getWaterDrops().add(waterDrop);
			} else {
				System.out.println("JSON was null.");
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

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
					System.out.println("JSON was null.");
					if (player != null) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
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
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

					pw.println(json);

					pw.flush();

					pw.close();
					plugin.getMatches().add(match);
					if (player != null) {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventDisabled());
					}
				} else {
					System.out.println("JSON was null.");
					if (player != null) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
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
				System.out.println("JSON was null.");
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getError());

		}

	}

	public static Boolean guardaInventario(RandomEvents plugin, Player player) {
		Boolean exitoso = Boolean.TRUE;
		if (plugin.isInventoryManagement()) {

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
				inventario.setLevel(player.getLevel());
				inventario.setHelmet(player.getInventory().getHelmet());
				inventario.setBoots(player.getInventory().getBoots());
				inventario.setLeggings(player.getInventory().getLeggings());
				inventario.setChestplate(player.getInventory().getChestplate());

				if (plugin.getUseLastLocation()) {
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

						FileWriter fw = new FileWriter(bossFile, true);

						PrintWriter pw = new PrintWriter(fw);

						pw.println(json);

						pw.flush();

						pw.close();
					} else {
						System.out.println(
								"[RandomEvents] Error :: The player " + player.getName() + " already has an inventory");
						System.out.println(json);
					}
				} else {
					exitoso = Boolean.FALSE;

					System.out.println("JSON was null.");
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
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
		if (plugin.isInventoryManagement()) {

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
				FileReader fr = null;
				try {
					fr = new FileReader(bossFile);
					br = new BufferedReader(fr);
					try {

						inventario = UtilidadesJson.fromJSONToInventory(plugin, br);
					} catch (Exception e) {
						System.out.println("[RandomEvents] Error al cargar el inventario de " + player.getName()
								+ ", probablemente una leather armor con color");
						System.out.println("[RandomEvents]" + br);

					}

				} catch (FileNotFoundException e) {
					System.out.println("[RandomEvents] Error in sacaInventario catch 1");
					System.out.println(e.getMessage());
				} finally {
					try {
						if (fr != null)
							fr.close();
						if (br != null)
							br.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}

				try {
					if (inventario != null) {
						UtilsRandomEvents.borraInventario(player, plugin);

						if (plugin.getUseLastLocation()) {
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
						player.getInventory().setContents(inventario.getContents());
						player.getInventory().setHelmet(inventario.getHelmet());
						player.getInventory().setLeggings(inventario.getLeggings());
						player.getInventory().setBoots(inventario.getBoots());
						player.getInventory().setChestplate(inventario.getChestplate());

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
					System.out.println("[RandomEvents] Error in sacaInventario catch 2");
					System.out.println(exc.getMessage());
				}
			}
		}
	}

	public static Boolean teleportaPlayer(Player p, Location loc, RandomEvents plugin) {
		Boolean res = true;
		if (loc != null) {
			try {
				p.teleport(loc);
			} catch (Exception e) {
				try {
					Location loc2 = loc.clone();
					if (loc2 != null) {
						if (plugin.isDebugMode())
							System.out.println("Initial Loc ->" + loc2.toString());
					}
					loc2.setWorld(Bukkit.getWorld(loc2.getWorld().getUID()));
					if (loc2 != null) {
						if (plugin.isDebugMode())
							System.out.println("Updated Loc ->" + loc2.toString());
					}
					p.teleport(loc2);
				} catch (Exception e2) {
					try {
						Location loc2 = loc.clone();
						if (loc2 != null) {
							if (plugin.isDebugMode())
								System.out.println("Initial Loc ->" + loc2.toString());
						}
						loc2.setWorld(Bukkit.getWorld(loc2.getWorld().getName()));
						if (loc2 != null) {
							if (plugin.isDebugMode())
								System.out.println("Updated Loc ->" + loc2.toString());
						}

						p.teleport(loc2);
					} catch (Exception e3) {
						res = false;
						System.out.println(e3);

					}
				}
			}
		}
		return res;
	}

	public static List<Match> cargarPartidas(RandomEvents plugin) {
		List<Match> listaPartidas = new ArrayList<Match>();
		File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		for (File file : dataFolder.listFiles()) {
			BufferedReader br = null;
			FileInputStream fr = null;
			try {
				fr = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(fr, StandardCharsets.UTF_8));
				Match match = UtilidadesJson.fromJSONToMatch(plugin, br);
				if (match != null)
					listaPartidas.add(match);

			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}

		}
		Collections.sort(listaPartidas);
		return listaPartidas;
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
				System.out.println(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}

		}
		return listaPartidas;
	}

	public static String readAll(File file) {
		String content = "";
		try {
			List<String> lineas = Files.readLines(file, StandardCharsets.UTF_8);
			for (String linea : lineas) {
				content += linea;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
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
		message = message.replaceAll("<color>", "§");
		if (tag) {
			message = plugin.getLanguage().getTagPlugin() + message;
		}
		for (Player p : players) {
			if (plugin.isOptionalTitles()) {
				plugin.getApi().usaTitle(p, "", message);
			} else {
				p.sendMessage(message);
			}
		}
	}

	public static void mandaMensaje(RandomEvents plugin, List<Player> players, List<String> messages, Boolean tag) {
		String message = "";
		for (String msg : messages) {
			if (tag) {
				message += plugin.getLanguage().getTagPlugin() + msg.replaceAll("<color>", "§") + "\n";
			} else {
				message += msg.replaceAll("<color>", "§") + "\n";
			}
		}
		for (Player p : players) {
			p.sendMessage(message);
		}
	}

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

	public static void playSound(List<Player> player, XSound sonido) {
		if (sonido != null) {
			for (Player p : player) {
				playSound(p, sonido);
			}
		}
	}

	public static void playSound(Player player, XSound sonido) {
		if (sonido != null && player != null) {
			player.playSound(player.getLocation(), sonido.parseSound(), 10.0F, 1.0F);
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
		if (plugin.isInventoryManagement()) {
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

		l.getWorld().dropItem(l, plugin.getPowerUpItem());

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
					System.out.println(e3);

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
		Inventory inv = Bukkit.createInventory(null, 45, plugin.getLanguage().getStatsGuiName() + name);
		for (MinigameType minigame : MinigameType.values()) {
			Integer position = -1;
			try {
				Method method = plugin.getClass().getDeclaredMethod("getStats" + minigame.getCodigo());
				position = (Integer) method.invoke(plugin);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}

			if (position >= 0) {

				ItemStack item = new ItemStack(minigame.getMaterial());
				ItemMeta itemMeta = item.getItemMeta();
				itemMeta.setDisplayName("§6§l" + minigame.getMessage());
				List<String> lore = new ArrayList<String>();
				Integer wins = estadisticas.getWins(minigame.getCodigo());
				Integer tries = estadisticas.getTries(minigame.getCodigo());
				Double rat = (tries > 0) ? (wins * 1.0 / tries) : 0.;
				BigDecimal ratio = new BigDecimal(rat).setScale(2, RoundingMode.HALF_UP);

				lore.add(plugin.getLanguage().getStatsWins() + wins);
				lore.add(plugin.getLanguage().getStatsTries() + tries);
				lore.add(plugin.getLanguage().getStatsWinsRatio() + ratio.toPlainString());
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				inv.setItem(position, item);
			}
		}

		Integer position = -1;
		try {
			Method method = plugin.getClass().getDeclaredMethod("getStatsALLTIME");
			position = (Integer) method.invoke(plugin);
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
			item.setItemMeta(itemMeta);
			inv.setItem(position, item);
		}

		ItemStack[] contents = inv.getContents();
		int i = 0;
		ItemStack itemFill = plugin.getStatsFill();
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

	public static boolean contieneMaterialData(MaterialData data, Match match) {
		Boolean res = false;
		for (MaterialData matDat : match.getDatas()) {
			if (matDat.getItemType() == data.getItemType() && matDat.getData() == data.getData()) {
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
				System.out.println(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
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
				System.out.println("JSON was null.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

	public static String calculateTime(long seconds) {
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(day);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.DAYS.toMinutes(day)
				- TimeUnit.HOURS.toMinutes(hours);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.DAYS.toSeconds(day)
				- TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minute);

		String s = "";
		if (day > 0) {
			s += day + "d ";
		}
		if (hours > 0) {
			s += day + "h ";
		}
		if (minute > 0) {
			s += minute + "m ";
		}
		if (second > 0) {
			s += second + "s";
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
		if (plugin.getBannedPlayers().getBannedPlayers().containsKey(player.getName())) {
			if (plugin.getBannedPlayers().getBannedPlayers().get(player.getName()) > now) {
				res = Boolean.TRUE;
			} else {
				plugin.getBannedPlayers().getBannedPlayers().remove(player.getName());
			}

		}
		return res;
	}

	public static void checkDamageCounter(RandomEvents plugin, MatchActive matchActive) {
		List<Player> playerMuertos = new ArrayList<Player>();
		if (matchActive.getDamageCounter() >= plugin.getIdleTimeForDamage()) {
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

	public static Schedule nextEvent(List<Schedule> schedules, Player player, RandomEvents plugin) {
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

				c.add(Calendar.DAY_OF_WEEK, 1);
				day = c.get(Calendar.DAY_OF_WEEK);
				hour = 0;
				minute = 0;

			}

		}

		return sch;

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
					case SPAWN_PLAYER:
						if (match.getPlayerSpawn() != null) {
							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9 "
									+ match.getPlayerSpawn().getWorld().getName() + match.getPlayerSpawn().getX() + ", "
									+ match.getPlayerSpawn().getY() + ", " + match.getPlayerSpawn().getZ();
						}
						break;
					case ARENA_SPAWNS:
					case ANOTHER_ARENA_SPAWNS:
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
					case INVENTORY:
						if (match.getInventory() != null) {

							info += Constantes.SALTO_LINEA + Constantes.TABULACION + "§9Inventory completed";
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
			case SPAWN_PLAYER:
				if (match.getPlayerSpawn() == null) {
					res = Boolean.FALSE;

				}
				break;
			case ARENA_SPAWNS:
			case ANOTHER_ARENA_SPAWNS:
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
			case INVENTORY:
				if (match.getInventory() == null) {

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
			case GOAL_LOCATION1:
			case MAP_LOCATION1:
				if (match.getLocation1() == null) {
					res = Boolean.FALSE;

				}
				break;
			case ARROW_LOCATION2:
			case GEM_LOCATION2:
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
		plugin.getApi().createWorldBorder(p, size, center);

	}

	public static void spawnParticles(Particle1711 particle, RandomEvents plugin, Location location) {
		if (plugin.isUseParticles()) {
			try {
				ParticleDisplay pa = ParticleDisplay.display(plugin.getApi(), location, particle);

				String part = plugin.getParticleType().toUpperCase();
				if (part.equals("RANDOM")) {
					part = ParticleEffectRevent.values()[plugin.getRandom()
							.nextInt(ParticleEffectRevent.values().length)].toString().toUpperCase();
				}
				switch (part) {
				case "BLACKSUN":
					XParticle.blackSun(plugin.getParticleRadius(), plugin.getParticleRadiusRate(),
							plugin.getParticleRate(), plugin.getParticleRateChange(), pa);
					break;
				case "CIRCLE":
					XParticle.circle(plugin.getParticleRadius(), plugin.getParticleRate(), pa);
					break;
				case "CRESCENT":
					XParticle.crescent(plugin.getParticleRadius(), plugin.getParticleRate(), pa);
					break;
				case "CYLINDER":
					XParticle.cylinder(plugin.getParticleHeight(), plugin.getParticleRadius(), plugin.getParticleRate(),
							pa);
					break;
				case "DIAMOND":
					XParticle.diamond(plugin.getParticleRadiusRate(), plugin.getParticleRate(),
							plugin.getParticleHeight(), pa);
					break;
				case "ELLIPSE":
					XParticle.ellipse(plugin.getParticleRadius(), plugin.getParticleRadius2(), plugin.getParticleRate(),
							pa);
					break;
				case "EYE":
					XParticle.eye(plugin.getParticleRadius(), plugin.getParticleRadius2(), plugin.getParticleRate(),
							plugin.getParticleExtension(), pa);
					break;
				case "FILLEDCIRCLE":
					XParticle.filledCircle(plugin.getParticleRadius(), plugin.getParticleRate(),
							plugin.getParticleRadiusRate(), pa);
					break;
				case "ILLUMINATI":
					XParticle.illuminati(plugin.getParticleSize(), plugin.getParticleExtension(), pa);
					break;
				case "INFINITY":
					XParticle.infinity(plugin.getParticleRadius(), plugin.getParticleRate(), pa);
					break;
				case "RING":
					XParticle.ring(plugin.getParticleRate(), plugin.getParticleRadius(), plugin.getParticleRadius2(),
							pa);
					break;
				case "SPHERE":
					location.setY(location.getY() + 1);
					XParticle.sphere(plugin.getParticleRadius(), plugin.getParticleRate(), pa);
					break;
				case "MEGUMINEXPLOSION":
					XParticle.meguminExplosion(plugin, plugin.getParticleSize(), pa);
					break;
				}

			} catch (Exception e) {
				System.out.println("RandomEvents:: Particle failed to spawn");
			}
		}

	}

	public static List<String> prepareLines(RandomEvents plugin, MatchActive matchActive, Player player) {
		List<String> lines = new ArrayList<String>();
		List<String> playersDead = new ArrayList<String>();
		playersDead.addAll(matchActive.getPlayerHandler().getPlayersTotal());
		playersDead.removeAll(matchActive.getPlayerHandler().getPlayers());
		lines.add("");
		switch (matchActive.getMatch().getMinigame()) {
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
		case KNOCKBACK_DUEL:
		case SPLEEF:
		case SPLEGG:
		case SW:
		case TNT_RUN:
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
		case OITC:
			lines = prepareLinesTime(lines, plugin, matchActive);
			lines.add("");

			Map<String, Integer> mapaOrdenado = sortByValue(matchActive.getPuntuacion(), true);
			for (Entry<String, Integer> entrada : mapaOrdenado.entrySet()) {
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
		return lines;
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
				System.out.println(e.getMessage());
			} finally {
				try {
					if (fr != null)
						fr.close();
					if (br != null)
						br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
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
							if (plugin.isMysqlEnabled()) {

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
			} else if (plugin.isMysqlEnabled()) {
				lore.add(plugin.getLanguage().getCreditsBal().replaceAll("%credits%",
						"" + (creditos.containsKey(match.getName()) ? creditos.get(match.getName()) : 0)));
			}
			cabezaMeta.setLore(lore);
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
}
