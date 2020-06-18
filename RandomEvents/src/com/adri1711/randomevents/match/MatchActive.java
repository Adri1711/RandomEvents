package com.adri1711.randomevents.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.commands.ComandosEnum;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class MatchActive {

	private RandomEvents plugin;

	private Match match;

	private String password;

	private List<String> players;

	private List<Player> playersObj;

	private List<Entity> mobs;

	private Map<Integer, List<Player>> equipos;

	private Map<String, Entity> mascotas;

	private Map<String, Integer> puntuacion;

	private String objetivo;

	private Boolean playing;

	private Random random;

	private boolean firstAnnounce;

	public MatchActive(Match match, RandomEvents plugin) {
		super();
		this.match = match;
		this.plugin = plugin;
		this.random = new Random();

		this.players = new ArrayList<String>();

		this.playersObj = new ArrayList<Player>();

		this.mobs = new ArrayList<Entity>();

		this.equipos = new HashMap<Integer, List<Player>>();

		this.mascotas = new HashMap<String, Entity>();

		this.puntuacion = new HashMap<String, Integer>();

		this.playing = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		matchWaitingPlayers();
	}

	public void uneAPlayer(Player player) {
		if (!getPlayers().contains(player.getName())) {
			if (getPlayers().size() < match.getAmountPlayers()) {
				if (UtilsRandomEvents.checkLeatherItems(player)) {
					UtilsRandomEvents.guardaInventario(plugin, player);
					UtilsRandomEvents.borraInventario(player);
					hazComandosDeUnion(player);
					getPlayers().add(player.getName());
					getPlayersObj().add(player);
					UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("BAT", "HURT"));

					player.teleport(match.getPlayerSpawn());
				} else {
					player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.DISPOSE_LEATHER_ITEMS);

				}

			} else {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.PARTIDA_LLENA);
			}

		}
	}

	private void hazComandosDeUnion(Player player) {
		for (String cmd : plugin.getCommandsOnUserJoin()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}
	}

	public void echaDePartida(Player player) {
		if (getPlayers().contains(player.getName())) {
			if (!getPlayersObj().remove(player)) {
				UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), player);
			}
			getPlayers().remove(player.getName());
			UtilsRandomEvents.borraInventario(player);
			if (mascotas.containsKey(player)) {
				mascotas.get(player).remove();
				mascotas.remove(player);
			}
			Integer equipo=getEquipo(player);
			if(equipo!=null){
				echaDeEquipo(equipo,player);
			}
			player.teleport(plugin.getSpawn());

			UtilsRandomEvents.sacaInventario(plugin, player);
		}
		compruebaPartida();
	}

	public void echaDeEquipo(Integer equipo, Player player) {
		equipos.get(equipo).remove(player);
		if(equipos.get(equipo).isEmpty()){
			equipos.remove(equipo);
		}
		
	}

	public Integer getEquipo(Player player) {
		Integer equipo=null;
		for(Integer numeroEquipo:equipos.keySet()){
			if(equipos.get(numeroEquipo).contains(player)){
				equipo=numeroEquipo;
			}
		}
		return equipo;
		
	}

	private void compruebaPartida() {
		if (getPlayersObj().isEmpty()) {
			for (Entity entity : getMobs()) {
				entity.remove();
			}
			finalizaPartida(getPlayersObj(), Boolean.FALSE);
		}
		if (getPlayersObj().size() == 1) {
			daRecompensas();
		} else {
			if (getEquipos().keySet().size() == 1) {
				daRecompensas();
			}
		}

	}

	public void dejarPartida(Player player, Boolean muerto) {
		if (!getPlaying()) {
			if (!getPlayersObj().remove(player)) {
				UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), player);
			}
			getPlayers().remove(player.getName());

			UtilsRandomEvents.borraInventario(player);

			player.teleport(plugin.getSpawn());

			if (!muerto) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.ALREADY_PLAYING_MATCH);
		}
	}

	public void daRecompensas() {
		UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("ENDERDRAGON", "DEATH"));
		List<Player> ganadores = new ArrayList<Player>();
		switch (getMatch().getMinigame()) {
		case BATTLE_ROYALE:
		case BATTLE_ROYALE_CABALLO:
		case BATTLE_ROYALE_TEAM_2:
			ganadores.addAll(getPlayersObj());
			break;
		}

		String cadenaGanadores = "";
		if (ganadores.size() == 1) {
			cadenaGanadores = ganadores.get(0).getName();
		} else {
			for (Player p : ganadores) {
				if (ganadores.indexOf(p) == 0) {
					cadenaGanadores = p.getName();
				} else {
					if (ganadores.indexOf(p) == (ganadores.size() - 1)) {
						cadenaGanadores += " and " + p.getName();
					} else {
						cadenaGanadores += ", " + p.getName();
					}
				}
			}
		}
		for (Player play : Bukkit.getOnlinePlayers()) {
			play.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.WINNERS.replace("%players%", cadenaGanadores));
		}
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				finalizaPartida(ganadores, Boolean.FALSE);
			}
		}, 20 * 5L);
	}

	public void finalizaPartida(List<Player> ganadores, Boolean abrupto) {
		for (Entity ent : getMobs()) {
			ent.remove();
		}
		for (Player p : getPlayersObj()) {
			UtilsRandomEvents.borraInventario(p);

			p.teleport(plugin.getSpawn());

			UtilsRandomEvents.sacaInventario(plugin, p);

		}
		for (Player player : ganadores) {
			// UtilsStats.aumentaStats(player.getName(), getMatch().getName(),
			// StatsEnum.PARTIDAS_SUPERADAS, plugin);
			for (String comando : match.getRewards()) {
				Boolean ejecutaComando = Boolean.TRUE;
				String[] trozosComandos = comando.split(" ");

				if (trozosComandos[0].trim().equals(Constantes.PROBABILITY_CMD)) {
					Integer probabilidad = Integer.valueOf(trozosComandos[1]);
					Integer aleatorio = random.nextInt(100);

					if (aleatorio > probabilidad) {
						ejecutaComando = Boolean.FALSE;
					}
					String nuevoCmd = "";
					if (trozosComandos.length > 2) {
						for (int i = 2; i < trozosComandos.length; i++) {
							nuevoCmd += trozosComandos[i] + " ";
						}
						comando = nuevoCmd.substring(0, nuevoCmd.length() - 1);
					}
				}
				if (ejecutaComando) {
					Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
							comando.replaceAll("%player%", player.getName()));
				}
			}
		}

		reiniciaValoresPartida();
	}

	private void reiniciaValoresPartida() {
		this.players.clear();
		this.playersObj.clear();
		setPlaying(Boolean.FALSE);
		plugin.comienzaTemporizador();
	}

	public void matchBegin() {
		setPlaying(Boolean.TRUE);
		// for (Player player : getPlayersVivos()) {
		// UtilsStats.aumentaStats(player.getName(), getMatch().getName(),
		// StatsEnum.PARTIDAS_JUGADAS, plugin);
		// }

		cuentaAtras(Boolean.TRUE);

	}

	public void cuentaAtras(Boolean playSound) {

		UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
		UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.SECONDS_3_REMAINING, Boolean.FALSE);
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.SECONDS_2_REMAINING, Boolean.FALSE);
			}
		}, 20 * 1L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.SECONDS_1_REMAINING, Boolean.FALSE);
			}
		}, 20 * 2L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (playSound) {
					UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("ENDERDRAGON", "GROWL"));
				}
				comienzaPartida();
				// spawnMobs(bWave.getMobs(), getPlugin());
			}

		}, 20 * 3L);

	}

	public void comienzaPartida() {
		switch (match.getMinigame()) {
		case BATTLE_ROYALE:
			for (Player p : playersObj) {
				p.teleport(match.getSpawns().get(playersObj.indexOf(p)));
				ponInventarioMatch(p);
			}
			break;
		case BATTLE_ROYALE_TEAM_2:
			for (Player p : playersObj) {
				Integer indice = playersObj.indexOf(p);
				p.teleport(match.getSpawns().get(indice));
				ponInventarioMatch(p);

				if (indice % 2 == 0) {
					List<Player> players = new ArrayList<Player>();
					players.add(p);
					equipos.put(indice / 2, players);
				} else {
					equipos.get((indice - 1) / 2).add(p);
				}

			}
			break;
		case BATTLE_ROYALE_CABALLO:
			for (Player p : playersObj) {
				p.teleport(match.getSpawns().get(playersObj.indexOf(p)));
				ponInventarioMatch(p);

				Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE); // Spawns
																									// the
																									// horse
				horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1)); // Gives
																					// horse
																					// saddle
				horse.setTamed(true); // Sets horse to tamed
				horse.setOwner(p); // Makes the horse the players
				horse.setPassenger(p);

				getMobs().add(horse);
				getMascotas().put(p.getName(), horse);

				horse.setPassenger(p);

			}
			break;
		}

	}

	private void ponInventarioMatch(Player p) {
		p.getInventory().setContents(match.getInventory().getContents());
		p.getInventory().setHelmet(match.getInventory().getHelmet());
		p.getInventory().setLeggings(match.getInventory().getLeggings());
		p.getInventory().setBoots(match.getInventory().getBoots());
		p.getInventory().setChestplate(match.getInventory().getChestplate());

		p.updateInventory();

	}

	public void matchWaitingPlayers() {

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {

				if (match.getAmountPlayersMin() <= (getPlayers().size())) {
					if (!getPlaying())
						matchBegin();
				} else {
					// if (!getPlayers().isEmpty()) {
					// String waiting = Constantes.WAITING_FOR_PLAYERS;
					// UtilsRandomEvents
					// .mandaMensaje(getPlayersObj(),
					// waiting.replaceAll("%players%", "" + getPlayers().size())
					// .replaceAll("%neededPlayers%",
					// match.getAmountPlayersMin().toString()),
					// Boolean.TRUE);
					// }
					String firstPart = Constantes.TAG_PLUGIN + " ";

					if (firstAnnounce) {
						firstPart += Constantes.FIRST_ANNOUNCE;
						firstAnnounce = Boolean.FALSE;
					} else {
						firstPart += Constantes.NEXT_ANNOUNCE;

					}
					String lastPart = Constantes.LAST_PART;
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())) {
							plugin.getApi().send(p, firstPart, Constantes.CLICK_HERE, new ArrayList<String>(),
									"/revent join " + password,
									lastPart.replaceAll("%players%", "" + getPlayers().size())
											.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString()));
						}
					}

					matchWaitingPlayers();
				}

			}
		}, 20 * 10L);
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public List<Entity> getMobs() {
		return mobs;
	}

	public void setMobs(List<Entity> mobs) {
		this.mobs = mobs;
	}

	public Map<Integer, List<Player>> getEquipos() {
		return equipos;
	}

	public void setEquipos(Map<Integer, List<Player>> equipos) {
		this.equipos = equipos;
	}

	public Map<String, Entity> getMascotas() {
		return mascotas;
	}

	public void setMascotas(Map<String, Entity> mascotas) {
		this.mascotas = mascotas;
	}

	public Map<String, Integer> getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Map<String, Integer> puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public Boolean getPlaying() {
		return playing;
	}

	public void setPlaying(Boolean playing) {
		this.playing = playing;
	}

	public List<Player> getPlayersObj() {
		return playersObj;
	}

	public void setPlayersObj(List<Player> playersObj) {
		this.playersObj = playersObj;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

}
