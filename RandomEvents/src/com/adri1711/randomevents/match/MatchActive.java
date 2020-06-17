package com.adri1711.randomevents.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class MatchActive {

	private RandomEvents plugin;

	private Match match;

	private String password;

	private List<String> players;

	private List<Player> playersObj;

	private List<Entity> mobs;

	private Map<Integer, List<String>> equipos;

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
		this.players = new ArrayList<String>();
		this.mobs = new ArrayList<Entity>();
		this.playing = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		matchWaitingPlayers();
	}

	public void uneAPlayer(Player player) {
		if (!getPlayers().contains(player.getName())) {
			if (getPlayers().size() < match.getAmountPlayers()) {
				UtilsRandomEvents.guardaInventario(plugin, player);
				player.getInventory().clear();
				player.updateInventory();

				getPlayers().add(player.getName());
				getPlayersObj().add(player);
				UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("BAT", "HURT"));

				player.teleport(match.getPlayerSpawn());

			} else {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.PARTIDA_LLENA);
			}

		}
	}

	public void echaDePartida(Player player) {
		if (getPlayers().contains(player.getName())) {
			if (!getPlayersObj().remove(player)) {
				UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), player);
			}
			getPlayers().remove(player.getName());
			
			player.teleport(plugin.getSpawn());

		}
		compruebaPartida();
	}

	private void compruebaPartida() {
		if (getPlayersObj().isEmpty()) {
			for (Entity entity : getMobs()) {
				entity.remove();
			}
			finalizaPartida(Boolean.FALSE);
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

			player.getInventory().clear();
			player.updateInventory();

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

		for (Player player : ganadores) {
			// UtilsStats.aumentaStats(player.getName(), getMatch().getName(),
			// StatsEnum.PARTIDAS_SUPERADAS, plugin);
			player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.WIN_RANDOM_EVENTS);
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
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				finalizaPartida(Boolean.FALSE);
			}
		}, 20 * 5L);
	}

	public void finalizaPartida(Boolean abrupto) {
		for (Entity ent : getMobs()) {
			ent.remove();
		}
		for (Player p : getPlayersObj()) {
			p.getInventory().clear();
			p.updateInventory();
			p.teleport(plugin.getSpawn());

			UtilsRandomEvents.sacaInventario(plugin, p);

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
				// spawnMobs(bWave.getMobs(), getPlugin());
			}

		}, 20 * 3L);

	}

	public void matchWaitingPlayers() {

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (match.getAmountPlayers().equals(getPlayers().size())) {
					if (!getPlaying())
						matchBegin();
				} else {
					if (!getPlayers().isEmpty()) {
						String waiting = Constantes.WAITING_FOR_PLAYERS;
						UtilsRandomEvents
								.mandaMensaje(getPlayersObj(),
										waiting.replaceAll("%players%", "" + getPlayers().size())
												.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString()),
										Boolean.TRUE);
					}
					String firstPart = "";

					if (firstAnnounce) {
						firstPart = Constantes.FIRST_ANNOUNCE;
					} else {
						firstPart = Constantes.NEXT_ANNOUNCE;

					}
					String lastPart = Constantes.LAST_PART;

					plugin.getApi().send(plugin.getServer().getConsoleSender(), firstPart, Constantes.CLICK_HERE,
							new ArrayList<String>(), "revent join " + password,
							lastPart.replaceAll("%players%", "" + getPlayers().size()).replaceAll("%neededPlayers%",
									match.getAmountPlayersMin().toString()));

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

	public Map<Integer, List<String>> getEquipos() {
		return equipos;
	}

	public void setEquipos(Map<Integer, List<String>> equipos) {
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
