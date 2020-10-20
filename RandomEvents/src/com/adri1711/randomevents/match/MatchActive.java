package com.adri1711.randomevents.match;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.commands.ComandosEnum;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;
import com.adri1711.util.enums.AMaterials;

public class MatchActive {

	private RandomEvents plugin;

	private Match match;

	private String password;

	private List<String> players;

	private List<Player> playersObj;

	private List<Player> playersGanadores;

	private List<Player> playersSpectators;

	private List<Entity> mobs;

	private Map<Integer, List<Player>> equipos;

	private Map<String, Entity> mascotas;

	private Map<String, Integer> puntuacion;

	private List<Player> goalPlayers;

	private Player beast;

	private String objetivo;

	private Boolean playing;

	private Random random;

	private boolean firstAnnounce;
	private Integer tiempoPartida;

	private Integer maximo;

	private Player playerContador;

	private Integer numeroSegRestantes;

	private ItemStack gema;

	private Integer timerPassword;

	private Boolean forzada;

	private Cuboid cuboid;

	private Boolean tournament;

	private TournamentActive tournamentObj;

	private Integer limitPlayers;

	private Integer tries;

	private BukkitRunnable task;

	private Map<Location, Long> blockDisappear;

	private List<Location> blockDisappeared;

	public MatchActive(Match match, RandomEvents plugin, Boolean forzada) {
		super();
		this.match = match;
		this.maximo = 0;
		this.setTiempoPartida(match.getTiempoPartida());
		this.plugin = plugin;
		this.random = new Random();
		this.tournament = false;
		this.players = new ArrayList<String>();

		this.playersObj = new ArrayList<Player>();
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = new ArrayList<Player>();

		this.mobs = new ArrayList<Entity>();

		this.equipos = new HashMap<Integer, List<Player>>();

		this.mascotas = new HashMap<String, Entity>();
		this.goalPlayers = new ArrayList<Player>();
		this.puntuacion = new HashMap<String, Integer>();
		switch (match.getMinigame()) {
		case BOMB_TAG:
			this.numeroSegRestantes = match.getSecondsMobSpawn().intValue();
			break;
		case BOAT_RUN:
		case RACE:
		case ESCAPE_FROM_BEAST:
			this.cuboid = new Cuboid(match.getLocation1(), match.getLocation2());
			break;
		default:
			this.numeroSegRestantes = 10;
			break;
		}
		if (match.getLocation1() != null && match.getLocation2() != null)
			this.cuboid = new Cuboid(match.getLocation1(), match.getLocation2());
		this.limitPlayers = 1;
		this.playing = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		this.gema = new ItemStack(plugin.getApi().getMaterial(AMaterials.EMERALD));
		this.setForzada(forzada);
		this.blockDisappear = new HashMap<Location, Long>();
		this.blockDisappeared = new ArrayList<Location>();
		tries = 0;
		matchWaitingPlayers();
	}

	public MatchActive(Match match, RandomEvents plugin, Boolean forzada, Boolean tournament,
			TournamentActive tournamentObj, List<String> players, List<Player> playersGanadores,
			List<Player> playersSpectators) {
		super();
		this.goalPlayers = new ArrayList<Player>();

		this.match = match;
		this.maximo = 0;
		this.setTiempoPartida(match.getTiempoPartida());
		this.plugin = plugin;
		this.random = new Random();
		this.players = players;

		this.playersObj = playersGanadores;
		this.playersGanadores = new ArrayList<Player>();
		this.playersSpectators = playersSpectators;
		this.tournament = tournament;
		this.tournamentObj = tournamentObj;
		this.limitPlayers = tournamentObj.getRange().getTo() - 1;
		this.mobs = new ArrayList<Entity>();

		this.equipos = new HashMap<Integer, List<Player>>();

		this.mascotas = new HashMap<String, Entity>();

		this.puntuacion = new HashMap<String, Integer>();
		switch (match.getMinigame()) {
		case BOMB_TAG:
			this.numeroSegRestantes = match.getSecondsMobSpawn().intValue();
			break;
		case BOAT_RUN:
		case RACE:
		case ESCAPE_FROM_BEAST:
			this.cuboid = new Cuboid(match.getLocation1(), match.getLocation2());
			break;
		default:
			this.numeroSegRestantes = 10;
			break;
		}
		this.playing = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		this.gema = new ItemStack(plugin.getApi().getMaterial(AMaterials.EMERALD));
		this.setForzada(forzada);
		this.blockDisappear = new HashMap<Location, Long>();
		this.blockDisappeared = new ArrayList<Location>();

		tries = 0;

	}

	public void uneAPlayer(Player player) {
		if (!getPlayers().contains(player.getName())) {
			if (getPlayers().size() < match.getAmountPlayers()) {
				if (UtilsRandomEvents.checkLeatherItems(player)) {
					if (UtilsRandomEvents.guardaInventario(plugin, player)) {
						UtilsRandomEvents.borraInventario(player);
						hazComandosDeUnion(player);
						getPlayers().add(player.getName());
						getPlayersObj().add(player);
						getPlayersSpectators().add(player);
						UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("BAT", "HURT"));

						player.teleport(match.getPlayerSpawn());
					} else {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
								+ plugin.getLanguage().getErrorSavingInventory());

					}
				} else {
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getDisposeLeatherItems());

				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchFull());
			}

		}
	}

	private void hazComandosDeUnion(Player player) {
		for (String cmd : plugin.getCommandsOnUserJoin()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}
	}

	public void echaDePartida(Player player, Boolean comprueba, Boolean sacaInv, Boolean sacaSpectator) {
		echaDePartida(player, comprueba, sacaInv, sacaSpectator, true);
	}

	public void echaDePartida(Player player, Boolean comprueba, Boolean sacaInv, Boolean sacaSpectator,
			Boolean compruebaSpectator) {
		if (getPlayersSpectators().contains(player)) {
			if (comprueba) {
				if (!getPlayersObj().remove(player)) {
					UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), player);
				}
				getPlayers().remove(player.getName());

			}

			if (compruebaSpectator
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				if (!getPlayersSpectators().remove(player)) {
					UtilsRandomEvents.borraPlayerPorName(getPlayersSpectators(), player);
				}
			}
			UtilsRandomEvents.borraInventario(player);
			if (mascotas.containsKey(player)) {
				mascotas.get(player).remove();
				mascotas.remove(player);
			}
			if (comprueba) {
				Integer equipo = getEquipo(player);
				if (equipo != null) {
					echaDeEquipo(equipo, player);
				}
			}
			try {
				if (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty()) {
					player.teleport(plugin.getSpawn());
				} else {
					Location l = null;
					Integer i = 0;
					while (l == null && i < 20) {
						l = match.getSpectatorSpawns().get(getRandom().nextInt(match.getSpectatorSpawns().size()));
						i++;
					}
					if (l != null) {
						player.teleport(l);
					}
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getLeaveCommand());

				}
				player.setHealth(20);
				player.setFoodLevel(20);
				player.setFireTicks(0);
			} catch (Exception e) {
				if (player != null) {
					System.out.println("[RandomEvents] The player " + player.getName() + " failed on teleport");
					if (plugin.isDebugMode()) {
						System.out.println("RandomEvents::DebugMode:: " + e);
					}
				}
			}
			if (sacaInv
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		}
		if (comprueba)
			compruebaPartida();

	}

	public void echaDePartida(List<Player> players, Boolean comprueba, Boolean sacaInv, Boolean sacaSpectator,
			Boolean compruebaSpectator) {
		if (comprueba) {
			if (!getPlayersObj().remove(players)) {
				for (Player p : players) {
					UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), p);
				}
			}
			for (Player p : players) {

				getPlayers().remove(p.getName());
			}
		}
		for (Player player : players) {
			if (compruebaSpectator
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				if (!getPlayersSpectators().remove(player)) {
					UtilsRandomEvents.borraPlayerPorName(getPlayersSpectators(), player);
				}
			}

			UtilsRandomEvents.borraInventario(player);
			if (mascotas.containsKey(player)) {
				mascotas.get(player).remove();
				mascotas.remove(player);
			}
			if (comprueba) {
				Integer equipo = getEquipo(player);
				if (equipo != null) {
					echaDeEquipo(equipo, player);
				}
			}
			if (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty()) {
				player.teleport(plugin.getSpawn());
			} else {
				player.teleport(match.getSpectatorSpawns().get(getRandom().nextInt(match.getSpectatorSpawns().size())));
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getLeaveCommand());

			}
			player.setHealth(20);
			player.setFoodLevel(20);
			player.setFireTicks(0);

			if (sacaInv
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		}
		if (comprueba)
			compruebaPartida();
	}

	public void echaDeEquipo(Integer equipo, Player player) {
		equipos.get(equipo).remove(player);
		if (equipos.get(equipo).isEmpty()) {
			equipos.remove(equipo);
		}

	}

	public Integer getEquipo(Player player) {
		Integer equipo = null;
		for (Integer numeroEquipo : equipos.keySet()) {
			if (equipos.get(numeroEquipo).contains(player)) {
				equipo = numeroEquipo;
			}
		}
		return equipo;

	}

	public void compruebaPartida() {
		if (getPlaying()) {
			if (getPlayersObj().isEmpty()) {
				for (Entity entity : getMobs()) {
					entity.remove();
				}
				if (tournamentObj != null)
					tournamentObj.finalizaPartida(getCopiaP(getPlayersObj()), getCopiaP(getPlayersSpectators()),
							Boolean.FALSE, Boolean.FALSE);
				finalizaPartida(getPlayersObj(), Boolean.FALSE, Boolean.FALSE);

			}
			if (getPlayersObj().size() == 1) {
				if (getTournament()) {
					tournamentObj.nextGame(getCopia(getPlayers()), getCopiaP(getPlayersObj()),
							getCopiaP(getPlayersSpectators()));

				} else {
					daRecompensas(false);
				}
			} else if (getTournament()
					&& (getPlayersObj().size() <= limitPlayers || getPlayersGanadores().size() >= limitPlayers)) {
				if (limitPlayers == 1) {
					tournamentObj.nextGame(getCopia(getPlayers()), getCopiaP(getPlayersObj()),
							getCopiaP(getPlayersSpectators()));
				} else {
					if (!getPlayersGanadores().isEmpty()) {
						List<String> jugadores = new ArrayList<String>();
						for (Player p : getPlayersGanadores()) {
							jugadores.add(p.getName());
						}
						tournamentObj.nextGame(getCopia(jugadores), getCopiaP(getPlayersGanadores()),
								getCopiaP(getPlayersSpectators()));
					} else {
						tournamentObj.nextGame(getCopia(getPlayers()), getCopiaP(getPlayersObj()),
								getCopiaP(getPlayersSpectators()));

					}
					reiniciaValoresPartida();

				}
			} else if (!getTournament() && getPlayersGanadores().size() >= limitPlayers) {
				daRecompensas(false);

			} else {
				if (getMatch().getMinigame().equals(MinigameType.ESCAPE_FROM_BEAST)
						&& !getPlayersObj().contains(beast)) {
					daRecompensas(false);
				}

				if (getEquipos().keySet().size() == 1) {
					if (getTournament()) {
						tournamentObj.nextGame(getCopia(getPlayers()), getCopiaP(getPlayersObj()),
								getCopiaP(getPlayersSpectators()));

					} else {
						daRecompensas(false);
					}
				}
			}

			switch (match.getMinigame()) {
			case GEM_CRAWLER:
				checkPuntuacionesGemas();
				break;
			default:
				break;
			}
		}
	}

	private List<String> getCopia(List<String> players2) {
		List<String> lista = new ArrayList<String>();
		lista.addAll(players2);
		return lista;
	}

	private List<Player> getCopiaP(List<Player> players2) {
		List<Player> lista = new ArrayList<Player>();
		lista.addAll(players2);
		return lista;
	}

	private void checkPuntuacionesGemas() {
		Integer maximo = 0;

		for (Integer i : getPuntuacion().values()) {
			if (i > maximo) {
				maximo = i;
			}
		}
		if (maximo >= 10) {

			for (Player p : getPlayersObj()) {
				if (getPlayerContador() == null && getPuntuacion().containsKey(p.getName())
						&& getPuntuacion().get(p.getName()) == maximo) {
					setPlayerContador(p);
					UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(),
							plugin.getLanguage().getPlayerWinning().replace("%player%", p.getName()), true);
					setTimerPassword(getRandom().nextInt(100000));
				}
			}
			empiezaTemporizadorGemas(getTimerPassword());
		}
	}

	private void empiezaTemporizadorGemas(Integer timerPass) {
		if (getPlaying() && getPlayerContador() != null && getTimerPassword() != null
				&& getTimerPassword().equals(timerPass)) {
			if (numeroSegRestantes == 0) {
				if (getTournament()) {
					getPlayersGanadores().add(getPlayerContador());
					tournamentObj.nextGame(getCopia(getPlayers()), getCopiaP(getPlayersGanadores()),
							getCopiaP(getPlayersSpectators()));

				} else {
					daRecompensas(false);
				}
			} else {
				Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
					public void run() {
						numeroSegRestantes--;
						if (numeroSegRestantes > 0 && getTimerPassword() != null && getTimerPassword().equals(timerPass)
								&& getPlayerContador() != null)
							UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(),
									plugin.getLanguage().getPlayerWinningSeconds()
											.replace("%seconds%", numeroSegRestantes.toString())
											.replace("%player%", getPlayerContador().getName()),
									true);

						empiezaTemporizadorGemas(timerPass);

					}
				}, 20);
			}
		}
	}

	public void dejarPartida(Player player, Boolean muerto) {
		if (!getPlaying()) {
			if (!getPlayersObj().remove(player)) {
				UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), player);
			}

			if (!getPlayersSpectators().remove(player)) {
				UtilsRandomEvents.borraPlayerPorName(getPlayersSpectators(), player);
			}

			getPlayers().remove(player.getName());

			UtilsRandomEvents.borraInventario(player);

			player.teleport(plugin.getSpawn());

			if (!muerto) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		} else {
			player.sendMessage(
					plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getAlreadyPlayingMatch());
		}
	}

	public void daRecompensas(Boolean tiempo) {
		setPlaying(Boolean.FALSE);
		UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("ENDERDRAGON", "DEATH"));
		List<Player> ganadores = new ArrayList<Player>();
		switch (getMatch().getMinigame()) {
		case BATTLE_ROYALE:
		case KNOCKBACK_DUEL:
		case BATTLE_ROYALE_CABALLO:
		case BATTLE_ROYALE_TEAM_2:
		case ESCAPE_ARROW:
		case BOMB_TAG:
		case TNT_RUN:
		case SPLEEF:
			ganadores.addAll(getPlayersObj());
			break;
		case TOP_KILLER:
		case TOP_KILLER_TEAM_2:
			ganadores.addAll(sacaGanadoresPartidaTiempo());
			break;
		case GEM_CRAWLER:
			ganadores.add(getPlayerContador());
			break;
		case BOAT_RUN:
		case RACE:
		case ESCAPE_FROM_BEAST:
			if (getPlayerContador() != null)
				ganadores.add(getPlayerContador());
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
			if (tiempo) {
				play.sendMessage(plugin.getLanguage().getTagPlugin() + " "
						+ plugin.getLanguage().getWinnersPoints().replace("%points%", maximo.toString())
								.replace("%players%", cadenaGanadores).replace("%event%", match.getName()));

			} else {
				play.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getWinners()
						.replace("%players%", cadenaGanadores).replace("%event%", match.getName()));
			}
		}
		for (Player g : ganadores) {
			UtilsSQL.updateWins(g, match.getMinigame(), plugin);

		}
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				finalizaPartida(ganadores, Boolean.FALSE, Boolean.FALSE);
			}
		}, 20 * 5L);
	}

	private Collection<? extends Player> sacaGanadoresPartidaTiempo() {
		List<Player> winners = new ArrayList<Player>();
		if (equipos == null || equipos.isEmpty()) {
			maximo = 0;
			for (Integer puntos : puntuacion.values()) {
				if (maximo < puntos) {
					maximo = puntos;
				}
			}
			if (getTournament()) {
				puntuacion = UtilsRandomEvents.sortByValue(puntuacion);
				List<String> gana = new ArrayList<String>();
				for (String s : puntuacion.keySet()) {
					if (gana.size() < limitPlayers) {
						gana.add(s);
					}
				}
				for (Player p : playersObj) {
					if (gana.contains(p.getName())) {
						winners.add(p);
					}
				}

			} else {
				for (Player p : playersObj) {
					if (puntuacion.containsKey(p.getName()) && puntuacion.get(p.getName()).equals(maximo)) {
						winners.add(p);
					}
				}
			}
		} else {
			Map<Integer, Integer> puntuacionesEquipo = new HashMap<Integer, Integer>();

			for (Integer e : equipos.keySet()) {
				List<Player> players = equipos.get(e);
				Integer puntuacionEquipo = 0;
				for (Player p : players) {

					if (puntuacion.containsKey(p.getName())) {
						puntuacionEquipo += puntuacion.get(p.getName());
					}
				}
				puntuacionesEquipo.put(e, puntuacionEquipo);
			}

			maximo = 0;

			if (getTournament()) {
				puntuacionesEquipo = UtilsRandomEvents.sortByValue(puntuacionesEquipo);
				for (Integer s : puntuacionesEquipo.keySet()) {
					if (winners.size() < limitPlayers) {
						winners.addAll(equipos.get(s));
					}
				}

			} else {

				for (Integer puntos : puntuacionesEquipo.values()) {
					if (maximo < puntos) {
						maximo = puntos;
					}
				}

				for (Integer equipo : puntuacionesEquipo.keySet()) {
					if (puntuacionesEquipo.get(equipo) == maximo) {
						winners.addAll(equipos.get(equipo));
					}
				}
			}
		}

		return winners;
	}

	public void finalizaPartida(List<Player> ganadores, Boolean abrupto, Boolean cancelled) {
		if (tournamentObj == null) {

			for (Player p : getPlayersSpectators()) {

				// UtilsRandomEvents.borraInventario(p);
				//
				// p.teleport(plugin.getSpawn());
				//
				// UtilsRandomEvents.sacaInventario(plugin, p);
				echaDePartida(p, false, true, true, false);
			}
			for (Player player : ganadores) {
				// UtilsStats.aumentaStats(player.getName(),
				// getMatch().getName(),
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
			if (cancelled) {
				List<Player> playersOnline = new ArrayList<Player>();
				playersOnline.addAll(Bukkit.getOnlinePlayers());
				UtilsRandomEvents.mandaMensaje(plugin, playersOnline, plugin.getLanguage().getEventCancelled()
						.replaceAll("%event%", match.getName()).replaceAll("%type%", match.getMinigame().getMessage()),
						true);

			}

			reiniciaValoresPartida();
		}
	}

	private void reiniciaValoresPartida() {

		for (Entity ent : getMobs()) {
			ent.remove();
		}
		this.players.clear();
		this.playersObj.clear();
		this.playersGanadores.clear();
		this.playersSpectators.clear();
		if (task != null)
			task.cancel();
		Material mat = null;
		if (match.getMinigame().equals(MinigameType.TNT_RUN)) {
			mat = plugin.getApi().getMaterial(AMaterials.TNT);
		} else {
			if (match.getMaterial() != null) {
				mat = Material.getMaterial(match.getMaterial());
			}
		}
		if (mat != null) {
			getBlockDisappeared().addAll(getBlockDisappear().keySet());
			for (Location l : getBlockDisappeared()) {
				l.getBlock().setType(mat);
			}
		}
		setPlaying(Boolean.FALSE);
		if (tournamentObj == null)
			plugin.reiniciaPartida(forzada);
	}

	public void matchBegin() {
		setPlaying(Boolean.TRUE);
		// for (Player player : getPlayersVivos()) {
		// UtilsStats.aumentaStats(player.getName(), getMatch().getName(),
		// StatsEnum.PARTIDAS_JUGADAS, plugin);
		// }
		mandaDescripcion();
		cuentaAtras(Boolean.TRUE);

	}

	private void mandaDescripcion() {
		List<String> desc = getField("minigameDescription" + match.getMinigame().getCodigo());
		for (Player p : getPlayersSpectators()) {
			for (String d : desc) {
				p.sendMessage(d.replaceAll("&", "§"));
			}
		}

	}

	public List<String> getField(String javaField) {

		Method method;
		List<String> res = new ArrayList<String>();
		try {
			method = plugin.getLanguage().getClass().getMethod(getComandoGet(javaField));
			res = (List<String>) method.invoke(plugin.getLanguage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public String getComandoGet(String javaField) {
		String campo = javaField;
		String primeraLetra = campo.substring(0, 1);
		String campoFinal = primeraLetra.toUpperCase() + javaField.substring(1);

		return "get" + campoFinal;
	}

	public void cuentaAtras(Boolean playSound) {

		UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(),
				plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getEventAnnounce()
						.replace("%event%", match.getName()).replace("%type%", match.getMinigame().getMessage()),
				Boolean.FALSE);

		UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
		UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), Constantes.SECONDS_3_REMAINING, Boolean.FALSE);
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), Constantes.SECONDS_2_REMAINING,
						Boolean.FALSE);
			}
		}, 20 * 1L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), Constantes.SECONDS_1_REMAINING,
						Boolean.FALSE);
			}
		}, 20 * 2L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (playSound) {
					UtilsRandomEvents.playSound(getPlayersSpectators(),
							UtilsRandomEvents.buscaSonido("ENDERDRAGON", "GROWL"));
				}
				comienzaPartida();
				// spawnMobs(bWave.getMobs(), getPlugin());
			}

		}, 20 * 3L);

	}

	public void comienzaPartida() {
		switch (match.getMinigame()) {
		case BATTLE_ROYALE:
		case KNOCKBACK_DUEL:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);

			}

			break;
		case TNT_RUN:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);

			}
			task = new BukkitRunnable() {
				public void run() {

					UtilsRandomEvents.checkBlocksDisappear(plugin, getMatchActive(), new Date());
				}
			};
			task.runTaskTimer(plugin, 0, 1L);
			break;
		case SPLEEF:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);

			}
			PotionEffect pot = new PotionEffect(PotionEffectType.FAST_DIGGING, 240, 99);
			UtilsRandomEvents.applyPotionEffects(pot, getPlayersObj());

			task = new BukkitRunnable() {
				public void run() {

					UtilsRandomEvents.applyPotionEffects(pot, getPlayersObj());
				}
			};
			task.runTaskTimer(plugin, 0, 120L);
			break;
		case SPLEGG:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			ItemStack item = new ItemStack(plugin.getApi().getMaterial(AMaterials.STONE_HOE));
			for (Player p : playersObj) {
				iniciaPlayer(p);
				p.getInventory().setItem(0, item);
				p.updateInventory();

			}

			break;
		case ESCAPE_FROM_BEAST:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			setBeast(playersObj.get(plugin.getRandom().nextInt(playersObj.size())));
			setPlayerContador(getBeast());
			for (Player p : playersObj) {
				if (p.getName().equals(beast.getName())) {
					iniciaPlayerBeast(p);

				} else {
					iniciaPlayer(p);
				}

			}

			break;
		case TOP_KILLER:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);
			}
			partidaPorTiempo();
			break;
		case TOP_KILLER_TEAM_2:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				Integer indice = playersObj.indexOf(p);

				iniciaPlayer(p);
				if (indice % 2 == 0) {
					List<Player> players = new ArrayList<Player>();
					players.add(p);
					equipos.put(indice / 2, players);
				} else {
					equipos.get((indice - 1) / 2).add(p);
				}
			}

			mandaMensajesEquipo(equipos);

			partidaPorTiempo();
			break;

		case BATTLE_ROYALE_TEAM_2:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				Integer indice = playersObj.indexOf(p);
				iniciaPlayer(p);

				if (indice % 2 == 0) {
					List<Player> players = new ArrayList<Player>();
					players.add(p);
					equipos.put(indice / 2, players);
				} else {
					equipos.get((indice - 1) / 2).add(p);
				}

			}
			mandaMensajesEquipo(equipos);
			break;
		case BATTLE_ROYALE_CABALLO:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);

				Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE); // Spawns
																									// the
				// horse
				horse.getInventory().setSaddle(new ItemStack(plugin.getApi().getMaterial(AMaterials.SADDLE), 1)); // Gives
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
		case BOAT_RUN:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);

				Boat boat = null;
				if (getMatch().getEntitySpawns() == null || getMatch().getEntitySpawns().isEmpty()) {
					boat = (Boat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BOAT);

				} else {
					boat = (Boat) p.getWorld().spawnEntity(match.getEntitySpawns().get(getPlayersObj().indexOf(p)),
							EntityType.BOAT);
				}

				getMobs().add(boat);
				getMascotas().put(p.getName(), boat);

			}
			break;
		case RACE:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);

			}
			break;

		case ESCAPE_ARROW:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);
			}
			partidaEscapeArrow();
			break;
		case GEM_CRAWLER:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				iniciaPlayer(p);
			}
			spawneaGemas();
			break;
		case BOMB_TAG:
			for (Player p : playersSpectators) {
				if (!playersObj.contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : playersObj) {
				teleportaPlayer(p);
			}
			bombRandom();
			partidaBombTag();

			break;
		}

	}

	public void bombRandom() {
		setPlayerContador(getPlayersObj().get(getRandom().nextInt(getPlayersObj().size())));
		ponInventarioMatch(getPlayerContador());
		UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(),
				plugin.getLanguage().getPlayerHasBomb().replace("%player%", getPlayerContador().getName()), true);
		UtilsRandomEvents.playSound(getPlayerContador(), UtilsRandomEvents.buscaSonido("VILLAGER", "HIT"));

	}

	private void partidaBombTag() {

		if (getPlaying()) {
			for (Player p : getPlayersObj()) {
				if (p.equals(getPlayerContador())) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
				} else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 0));
				}
			}
			if (numeroSegRestantes == 0) {

				UtilsRandomEvents.playSound(getPlayersSpectators(),
						UtilsRandomEvents.buscaSonido("EXPLODE", "EXPLODE"));

				List<Player> muertos = UtilsRandomEvents.getPlayersWithin(getPlayerContador(), getPlayersObj(), 4);
				for (Player p : muertos) {
					UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(),
							plugin.getLanguage().getBombExplode().replace("%player%", p.getName()), true);
				}
				echaDePartida(muertos, true, true, false, true);
				if (getPlayersObj().size() > limitPlayers) {
					numeroSegRestantes = match.getSecondsMobSpawn().intValue();
					bombRandom();
					partidaBombTag();
				} else {
					compruebaPartida();
				}
			} else {
				Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
					public void run() {
						numeroSegRestantes--;
						if (numeroSegRestantes > 5 && numeroSegRestantes % 5 == 0) {
							UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), plugin.getLanguage()
									.getBombSeconds().replace("%seconds%", numeroSegRestantes.toString()), true);
						} else if (numeroSegRestantes > 0 && numeroSegRestantes <= 5) {
							UtilsRandomEvents.playSound(getPlayersSpectators(),
									UtilsRandomEvents.buscaSonido("NOTE", "PIANO"));
							UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), plugin.getLanguage()
									.getBombSeconds().replace("%seconds%", numeroSegRestantes.toString()), true);
						}

						partidaBombTag();

					}
				}, 20);
			}
		}

	}

	private void spawneaGemas() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {

					Location l = UtilsRandomEvents.getRandomLocation(plugin, getCuboid());

					Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
						public void run() {
							UtilsRandomEvents.dropItem(l, gema);
						}
					});

					spawneaGemas();
				}
			}, timer.longValue());
		}

	}

	public void partidaEscapeArrow() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {

					Location l = UtilsRandomEvents.getRandomLocation(plugin, getCuboid());
					if (getRandom().nextInt(1000) <= plugin.getProbabilityPowerUp()) {

						Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
							public void run() {
								UtilsRandomEvents.spawnPowerUp(l, plugin);
							}
						});
					} else {
						Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
							public void run() {
								UtilsRandomEvents.spawnEntity(l, match.getMob(), plugin);
							}
						});
					}
					partidaEscapeArrow();
				}
			}, timer.longValue());
		}

	}

	private void partidaPorTiempo() {
		if (getTiempoPartida() <= 60) {
			Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
				public void run() {
					acabaPartidaEnTiempo();
				}
			}, 20 * getTiempoPartida());
		} else {
			Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {

				public void run() {
					setTiempoPartida(getTiempoPartida() - 60);
					UtilsRandomEvents.mandaMensaje(plugin, playersObj, plugin.getLanguage().getTimeRemaining()
							.replace("%minutes%", UtilsRandomEvents.preparaStringTiempo(tiempoPartida)), true);
					partidaPorTiempo();
				}

			}, 20 * 60);
		}

	}

	public void acabaPartidaEnTiempo() {

		UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
		UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), Constantes.SECONDS_3_REMAINING, Boolean.TRUE);
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), Constantes.SECONDS_2_REMAINING,
						Boolean.TRUE);
			}
		}, 20 * 1L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersSpectators(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(), Constantes.SECONDS_1_REMAINING,
						Boolean.TRUE);
			}
		}, 20 * 2L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (getTournament()) {
					playersGanadores.addAll(sacaGanadoresPartidaTiempo());
					List<String> jugadores = new ArrayList<String>();
					for (Player p : getPlayersGanadores()) {
						jugadores.add(p.getName());
					}
					tournamentObj.nextGame(getCopia(jugadores), getCopiaP(getPlayersGanadores()),
							getCopiaP(getPlayersSpectators()));
				} else {
					daRecompensas(true);
				}
				// spawnMobs(bWave.getMobs(), getPlugin());
			}

		}, 20 * 3L);
	}

	public void iniciaPlayer(Player p) {
		teleportaPlayer(p);
		ponInventarioMatch(p);
		UtilsSQL.updateTries(p, match.getMinigame(), plugin);
	}

	public void iniciaPlayerBeast(Player p) {
		p.teleport(getMatch().getBeastSpawn());
		p.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getYouBeast());
		UtilsSQL.updateTries(p, match.getMinigame(), plugin);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				teleportaPlayer(p);
				ponInventarioBeast(p);
			}
		}, Double.valueOf(20 * plugin.getMatchActive().getMatch().getSecondsMobSpawn()).longValue());

	}

	public void mandaSpectatorPlayer(Player p) {
		p.teleport(match.getSpectatorSpawns().get(getRandom().nextInt(match.getSpectatorSpawns().size())));
	}

	private void teleportaPlayer(Player p) {
		p.teleport(match.getSpawns().get(getPlayersObj().indexOf(p)));

	}

	public void reiniciaPlayer(Player p) {
		Location location = p.getLocation();

		p.teleport(match.getSpawns().get(getRandom().nextInt(match.getSpawns().size())));
		ponInventarioMatch(p);

		switch (match.getMinigame()) {
		case GEM_CRAWLER:
			Integer amount = 0;
			if (getPuntuacion().containsKey(p.getName())) {
				amount = getPuntuacion().get(p.getName());
			}

			if (amount > 0) {
				ItemStack puntos = new ItemStack(plugin.getApi().getMaterial(AMaterials.EMERALD));
				puntos.setAmount(amount);
				location.getWorld().dropItem(location, puntos);
				getPuntuacion().put(p.getName(), 0);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayersSpectators(),
						plugin.getLanguage().getLostGems().replace("%player%", p.getName()), true);
				if (getPlayerContador() != null && getPlayerContador().equals(p)) {
					setPlayerContador(null);
					setNumeroSegRestantes(10);
				}
				compruebaPartida();
			}
			break;
		default:
			break;
		}

	}

	private void mandaMensajesEquipo(Map<Integer, List<Player>> equipos2) {
		List<Player> restoEquipo = new ArrayList<Player>();
		for (List<Player> players : equipos2.values()) {
			for (Player player : players) {
				restoEquipo.clear();
				restoEquipo.addAll(players);
				restoEquipo.remove(player);

				if (restoEquipo.size() == 0) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getShowAlone());
				} else if (restoEquipo.size() == 1) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
							+ plugin.getLanguage().getShowTeam().replace("%players%", restoEquipo.get(0).getName()));
					// plugin.getApi().cambiaNombre(player, Constantes.GREEN +
					// player.getName(), players);

				} else {
					String cadenaEquipo = "";
					for (Player p : restoEquipo) {
						if (restoEquipo.indexOf(p) == 0) {
							cadenaEquipo = p.getName();
						} else {
							if (restoEquipo.indexOf(p) == (restoEquipo.size() - 1)) {
								cadenaEquipo += " and " + p.getName();
							} else {
								cadenaEquipo += ", " + p.getName();
							}
						}
					}
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
							+ plugin.getLanguage().getShowTeam().replace("%players%", cadenaEquipo));

					// player.sendMessage(players.toString());
					// player.sendMessage(Constantes.GREEN +
					// player.getName()+"aaaaa");

					// plugin.getApi().cambiaNombre(player, Constantes.GREEN +
					// player.getName()+"aaaaa", players);

				}
			}

		}

	}

	public void ponInventarioMatch(Player p) {
		p.getInventory().setContents(match.getInventory().getContents());
		p.getInventory().setHelmet(match.getInventory().getHelmet());
		p.getInventory().setLeggings(match.getInventory().getLeggings());
		p.getInventory().setBoots(match.getInventory().getBoots());
		p.getInventory().setChestplate(match.getInventory().getChestplate());

		p.updateInventory();
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);

		if (p.getActivePotionEffects() != null) {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}

		}

		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2));

	}

	public void ponInventarioRunner(Player p) {
		p.getInventory().setContents(match.getInventoryRunners().getContents());
		p.getInventory().setHelmet(match.getInventoryRunners().getHelmet());
		p.getInventory().setLeggings(match.getInventoryRunners().getLeggings());
		p.getInventory().setBoots(match.getInventoryRunners().getBoots());
		p.getInventory().setChestplate(match.getInventoryRunners().getChestplate());

		p.updateInventory();
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);

		if (p.getActivePotionEffects() != null) {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}

		}

		// p.addPotionEffect(new
		// PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2));

	}

	public void ponInventarioBeast(Player p) {
		p.getInventory().setContents(match.getInventoryBeast().getContents());
		p.getInventory().setHelmet(match.getInventoryBeast().getHelmet());
		p.getInventory().setLeggings(match.getInventoryBeast().getLeggings());
		p.getInventory().setBoots(match.getInventoryBeast().getBoots());
		p.getInventory().setChestplate(match.getInventoryBeast().getChestplate());

		p.updateInventory();
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);

		if (p.getActivePotionEffects() != null) {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}

		}

		// p.addPotionEffect(new
		// PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2));

	}

	public void matchWaitingPlayers() {

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (plugin.getMatchActive() != null && plugin.getMatchActive().getPassword().equals(getPassword())) {
					tries++;
					Boolean playSound = Boolean.FALSE;

					if (match.getAmountPlayersMin() <= (getPlayers().size())) {
						if (!getPlaying())
							matchBegin();
					} else {
						// if (!getPlayers().isEmpty()) {
						// String waiting = Constantes.WAITING_FOR_PLAYERS;
						// UtilsRandomEvents
						// .mandaMensaje(plugin,getPlayersSpectators(),
						// waiting.replaceAll("%players%", "" +
						// getPlayers().size())
						// .replaceAll("%neededPlayers%",
						// match.getAmountPlayersMin().toString()),
						// Boolean.TRUE);
						// }
						String firstPart = plugin.getLanguage().getTagPlugin() + " ";

						if (firstAnnounce) {
							playSound = Boolean.TRUE;
							firstPart += plugin.getLanguage().getFirstAnnounce();
							firstAnnounce = Boolean.FALSE;
						} else {
							firstPart += plugin.getLanguage().getNextAnnounce();

						}

						firstPart.replaceAll("%event%", match.getName()).replaceAll("%type%",
								match.getMinigame().getMessage());

						String lastPart = plugin.getLanguage().getLastPart();

						lastPart.replaceAll("%event%", match.getName()).replaceAll("%type%",
								match.getMinigame().getMessage());

						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())) {
								if (playSound) {
									UtilsRandomEvents.playSound(p, UtilsRandomEvents.buscaSonido("VILLAGER", "HIT"));
								}
								plugin.getApi().send(p, firstPart, plugin.getLanguage().getClickHere(),
										new ArrayList<String>(), "/revent join " + password,
										lastPart.replaceAll("%players%", "" + getPlayers().size())
												.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString()));
							}
						}
						if (tries <= plugin.getNumberOfTriesBeforeCancelling()) {
							matchWaitingPlayers();
						} else {
							finalizaPartida(new ArrayList<Player>(), Boolean.FALSE, Boolean.TRUE);

						}
					}

				}
			}
		}, 20 * plugin.getSecondsCheckPlayers());

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

	public Integer getTiempoPartida() {
		return tiempoPartida;
	}

	public void setTiempoPartida(Integer tiempoPartida) {
		this.tiempoPartida = tiempoPartida;
	}

	public boolean isFirstAnnounce() {
		return firstAnnounce;
	}

	public void setFirstAnnounce(boolean firstAnnounce) {
		this.firstAnnounce = firstAnnounce;
	}

	public Integer getMaximo() {
		return maximo;
	}

	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}

	public Player getPlayerContador() {
		return playerContador;
	}

	public void setPlayerContador(Player playerContador) {
		this.playerContador = playerContador;
	}

	public Integer getNumeroSegRestantes() {
		return numeroSegRestantes;
	}

	public void setNumeroSegRestantes(Integer numeroSegRestantes) {
		this.numeroSegRestantes = numeroSegRestantes;
	}

	public ItemStack getGema() {
		return gema;
	}

	public void setGema(ItemStack gema) {
		this.gema = gema;
	}

	public Integer getTimerPassword() {
		return timerPassword;
	}

	public void setTimerPassword(Integer timerPassword) {
		this.timerPassword = timerPassword;
	}

	public Boolean getForzada() {
		return forzada;
	}

	public void setForzada(Boolean forzada) {
		this.forzada = forzada;
	}

	public List<Player> getPlayersSpectators() {
		return playersSpectators;
	}

	public void setPlayersSpectators(List<Player> playersSpectators) {
		this.playersSpectators = playersSpectators;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public void setCuboid(Cuboid cuboid) {
		this.cuboid = cuboid;
	}

	public Boolean getTournament() {
		return tournament;
	}

	public void setTournament(Boolean tournament) {
		this.tournament = tournament;
	}

	public List<Player> getPlayersGanadores() {
		return playersGanadores;
	}

	public void setPlayersGanadores(List<Player> playersGanadores) {
		this.playersGanadores = playersGanadores;
	}

	public TournamentActive getTournamentObj() {
		return tournamentObj;
	}

	public void setTournamentObj(TournamentActive tournamentObj) {
		this.tournamentObj = tournamentObj;
	}

	public Integer getLimitPlayers() {
		return limitPlayers;
	}

	public void setLimitPlayers(Integer limitPlayers) {
		this.limitPlayers = limitPlayers;
	}

	@Override
	public String toString() {
		return "MatchActive [match=" + match + ", players=" + players + ", playersObj=" + playersObj
				+ ", playersGanadores=" + playersGanadores + ", playersSpectators=" + playersSpectators + "]";
	}

	public Player getBeast() {
		return beast;
	}

	public void setBeast(Player beast) {
		this.beast = beast;
	}

	public List<Player> getGoalPlayers() {
		return goalPlayers;
	}

	public void setGoalPlayers(List<Player> goalPlayers) {
		this.goalPlayers = goalPlayers;
	}

	public MatchActive getMatchActive() {
		return this;
	}

	public Integer getTries() {
		return tries;
	}

	public void setTries(Integer tries) {
		this.tries = tries;
	}

	public BukkitRunnable getTask() {
		return task;
	}

	public void setTask(BukkitRunnable task) {
		this.task = task;
	}

	public Map<Location, Long> getBlockDisappear() {
		return blockDisappear;
	}

	public void setBlockDisappear(Map<Location, Long> blockDisappear) {
		this.blockDisappear = blockDisappear;
	}

	public List<Location> getBlockDisappeared() {
		return blockDisappeared;
	}

	public void setBlockDisappeared(List<Location> blockDisappeared) {
		this.blockDisappeared = blockDisappeared;
	}

}
