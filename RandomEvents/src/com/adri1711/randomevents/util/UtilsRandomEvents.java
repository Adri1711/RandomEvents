package com.adri1711.randomevents.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.language.LanguageMessages;
import com.adri1711.randomevents.match.InventoryPers;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.MinigameType;
import com.adri1711.randomevents.match.enums.Creacion;
import com.adri1711.randomevents.util.Constantes.Messages;
import com.google.common.io.Files;

public class UtilsRandomEvents {

	public static Boolean pasaACreation(RandomEvents plugin, Player player, Integer position) {
		Boolean ponerPlayerCreation = Boolean.TRUE;
		player.sendMessage(Constantes.TAG_PLUGIN + " " + Creacion.getByPosition(position).getMessage());
		switch (Creacion.getByPosition(position)) {
		case MINIGAME_TYPE:
			for (MinigameType m : MinigameType.values()) {
				player.sendMessage("§6§l" + m.ordinal() + " - " + m.getMessage());
			}
			break;
		case MOB_NAME:
			for (EntityType m : EntityType.values()) {
				player.sendMessage("§6§l" + m.ordinal() + " - " + m.name());
			}
			break;
		case END:
			terminaCreacionMatch(plugin, player);
			ponerPlayerCreation = Boolean.FALSE;
			break;
		default:
			break;

		}

		if (ponerPlayerCreation) {
			plugin.getPlayersCreation().put(player.getName(), position);
		} else {
			plugin.getPlayersCreation().remove(player.getName(), position);
		}
		return ponerPlayerCreation;

	}

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
				}
				FileWriter fw = new FileWriter(bossFile, true);

				PrintWriter pw = new PrintWriter(fw);

				pw.println(json);

				pw.flush();

				pw.close();
				plugin.getPlayersCreation().remove(player.getName());
				plugin.getPlayerMatches().remove(player.getName());
				plugin.getMatches().add(match);
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getEndOfArenaCreation());
			} else {
				System.out.println("JSON was null.");
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getError());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getError());

		}

	}

	public static Boolean guardaInventario(RandomEvents plugin, Player player) {
		Boolean exitoso = Boolean.TRUE;
		File bossFile = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//inventories",
				player.getName() + ".json");
		try {
			InventoryPers inventario = new InventoryPers();

			ItemStack[] contenido = player.getInventory().getContents();
			List<ItemStack> contenidoList = Arrays.asList(contenido);
			contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));

			inventario.setContents((ItemStack[]) contenidoList.toArray());

			inventario.setHelmet(player.getInventory().getHelmet());
			inventario.setBoots(player.getInventory().getBoots());
			inventario.setLeggings(player.getInventory().getLeggings());
			inventario.setChestplate(player.getInventory().getChestplate());

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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			exitoso = Boolean.FALSE;
			if (bossFile.exists()) {
				bossFile.delete();
			}

		}

		return exitoso;

	}

	public static void sacaInventario(RandomEvents plugin, Player player) {
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
					UtilsRandomEvents.borraInventario(player);

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

	public static List<Match> cargarPartidas(RandomEvents plugin) {
		List<Match> listaPartidas = new ArrayList<Match>();
		File dataFolder = new File(String.valueOf(plugin.getDataFolder().getPath()) + "//events");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		for (File file : dataFolder.listFiles()) {
			BufferedReader br = null;
			FileReader fr = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
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

		resultado = match.getMatch().getName().replaceAll("&", "§") + " ( " + match.getPlayers().size() + " / "
				+ match.getMatch().getAmountPlayers() + " )";
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

	public static void mandaMensaje(List<Player> players, String message, Boolean tag) {
		message = message.replaceAll("<color>", "§");
		if (tag) {
			message = Constantes.TAG_PLUGIN + " " + message;
		}
		for (Player p : players) {
			p.sendMessage(message);
		}
	}

	public static void mandaMensaje(List<Player> players, List<String> messages, Boolean tag) {
		String message = "";
		for (String msg : messages) {
			if (tag) {
				message += Constantes.TAG_PLUGIN + " " + msg.replaceAll("<color>", "§") + "\n";
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

	public static Sound buscaSonido(String s, String s2) {
		Sound sound = null;
		for (Sound sou : Sound.values()) {
			if (sou.toString().contains(s) && sou.toString().contains(s2)) {
				sound = sou;
				break;
			}
		}
		return sound;
	}

	public static void playSound(List<Player> player, Sound sonido) {
		if (sonido != null) {
			for (Player p : player) {
				playSound(p, sonido);
			}
		}
	}

	public static void playSound(Player player, Sound sonido) {
		if (sonido != null) {
			player.playSound(player.getLocation(), sonido, 10.0F, 1.0F);
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

		MatchActive matchActive = new MatchActive(matches.get(plugin.getRandom().nextInt(matches.size())), plugin,
				forzada);

		return matchActive;
	}

	public static void borraInventario(Player player) {
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.getInventory().setChestplate(null);
		player.getEquipment().setArmorContents(null);
		if(player.getOpenInventory()!=null){
			if(player.getOpenInventory().getTopInventory()!=null){
				player.getOpenInventory().getTopInventory().clear();
			}
			if(player.getOpenInventory().getBottomInventory()!=null){
				player.getOpenInventory().getBottomInventory().clear();
			}
		}
		player.updateInventory();
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

	public static void spawnEntity(Location l, EntityType entityType, RandomEvents plugin) {

		Entity entity = l.getWorld().spawnEntity(l, entityType);

		if (entityType == EntityType.ARROW) {
			Double multiplicadorX = plugin.getRandom().nextDouble() > 0.5 ? 1. : -1.;
			Double multiplicadorZ = plugin.getRandom().nextDouble() > 0.5 ? 1. : -1.;

			Integer validadorX = plugin.getRandom().nextInt(10) < 1 ? 1 : 0;
			Integer validadorZ = plugin.getRandom().nextInt(10) < 1 ? 1 : 0;
			entity.setVelocity(new Vector(plugin.getRandom().nextDouble() * multiplicadorX * validadorX, -4,
					plugin.getRandom().nextDouble() * multiplicadorZ * validadorZ));
		}

	}
	
	

	
	
	

}
