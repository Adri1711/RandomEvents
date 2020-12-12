package com.adri1711.randomevents.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.commands.ComandosEnum;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.XSound;

public class TournamentActive {

	private Tournament tournament;

	private Map<RangePlayers, Match> matchPerRound;

	private MatchActive partida;

	private RangePlayers range;

	private String password;

	private RandomEvents plugin;

	private List<String> players;

	private List<Player> playersObj;

	private Boolean playing;

	private Boolean firstAnnounce;

	private List<Player> playersSpectators;

	private Boolean forzada;

	private boolean siguiente;

	private Integer tries;

	public TournamentActive(Tournament tournament, RandomEvents plugin, Boolean forzada) {
		super();
		this.tournament = tournament;
		playing = false;
		this.forzada = forzada;
		this.plugin = plugin;
		this.password = "" + plugin.getRandom().nextInt(10000);
		this.players = new ArrayList<String>();
		this.playersObj = new ArrayList<Player>();
		this.playersSpectators = new ArrayList<Player>();
		this.firstAnnounce = true;
		this.matchPerRound = calculateMatchPerRound();
		tries = 0;
		matchWaitingPlayers();
	}

	private Map<RangePlayers, Match> calculateMatchPerRound() {
		Map<RangePlayers, Match> mapa = new HashMap<RangePlayers, Match>();
		Integer max = tournament.getMaxPlayers();
		Integer rounds = tournament.getNumberOfRounds();
		Double percen = 1 / rounds.doubleValue();
		Integer playerPerRound = Double.valueOf(max * percen).intValue();
		Integer nPlayer = Integer.valueOf(max);
		Integer fromIni = Integer.valueOf(max);
		Integer maxMatch = 0;
		for (Match m : plugin.getMatches()) {
			if (m.getAmountPlayers() > maxMatch) {
				maxMatch = m.getAmountPlayers();
			}
		}
		if (fromIni > maxMatch) {
			fromIni = Integer.valueOf(maxMatch);
		}

		while (nPlayer > playerPerRound + 4) {
			RangePlayers rangePlayer = new RangePlayers(fromIni, nPlayer - playerPerRound + 1);
			nPlayer -= playerPerRound;
			Match match = searchCompatibleMatch(rangePlayer.getFrom(), rangePlayer.getTo());
			if (match != null) {
				fromIni = nPlayer;
				mapa.put(rangePlayer, match);
			}
		}

		RangePlayers rangePlayer = new RangePlayers(fromIni, 1);
		Match match = searchCompatibleMatch(rangePlayer.getFrom(), rangePlayer.getTo());
		if (match != null) {
			mapa.put(rangePlayer, match);
		} else {
			RangePlayers rAux = null;
			for (RangePlayers r : mapa.keySet()) {
				if (r.getTo().equals(fromIni + 1)) {
					rAux = r;
				}
			}
			if (rAux != null) {
				mapa.remove(rAux);
				rAux.setTo(1);
				mapa.put(rAux, searchCompatibleMatch(rAux.getFrom(), rAux.getTo()));
			}
		}

		return mapa;
	}

	private Match searchCompatibleMatch(Integer from, Integer to) {
		List<Match> matches = new ArrayList<Match>();

		for (Match m : plugin.getMatches()) {

			if (from >= m.getAmountPlayersMin() && from <= m.getAmountPlayers()) {

				if (to == 1) {

					if (!m.getMinigame().equals(MinigameType.BATTLE_ROYALE_TEAM_2)
							&& !m.getMinigame().equals(MinigameType.TOP_KILLER_TEAM_2)
							&& !m.getMinigame().equals(MinigameType.TSG) && !m.getMinigame().equals(MinigameType.TSW)) {
						matches.add(m);
					}

				} else {

					if (!m.getMinigame().equals(MinigameType.GEM_CRAWLER)
							&& !m.getMinigame().equals(MinigameType.ESCAPE_FROM_BEAST)) {
						matches.add(m);
					}
				}

			}
		}

		if (matches.isEmpty()) {

			return null;
		} else {

			return matches.get(plugin.getRandom().nextInt(matches.size()));
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

			UtilsRandomEvents.borraInventario(player, plugin);

			UtilsRandomEvents.teleportaPlayer(player, plugin.getSpawn(), plugin);

			if (!muerto) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		} else {
			player.sendMessage(
					plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getAlreadyPlayingMatch());
		}
	}

	public void matchWaitingPlayers() {

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (plugin.getTournamentActive() != null
						&& plugin.getTournamentActive().getPassword().equals(getPassword())) {
					tries++;

					Boolean playSound = Boolean.FALSE;

					if (tournament.getMinPlayers() <= (getPlayers().size())) {
						if (!getPlaying()) {
							playing = true;
							for (Entry<RangePlayers, Match> entrada : matchPerRound.entrySet()) {
								if (getPlayersObj().size() >= entrada.getKey().getTo()
										&& getPlayersObj().size() <= entrada.getKey().getFrom()) {
									range = entrada.getKey();
									partida = new MatchActive(entrada.getValue(), plugin, forzada, true, getInstance(),
											getPlayers(), getPlayersObj(), getPlayersSpectators());
									plugin.setMatchActive(partida);
								}
								System.out.println(entrada);
							}

							getPartida().matchBegin();

						}
					} else {
						String firstPart = plugin.getLanguage().getTagPlugin() + " ";

						if (firstAnnounce) {
							playSound = Boolean.TRUE;
							firstPart += plugin.getLanguage().getFirstAnnounceTournament();
							firstAnnounce = Boolean.FALSE;
						} else {
							firstPart += plugin.getLanguage().getNextAnnounceTournament();

						}
						String lastPart = plugin.getLanguage().getLastPartTournament();
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission(ComandosEnum.CMD_JOIN_TOURNAMENT.getPermission())) {
								if (playSound) {
									UtilsRandomEvents.playSound(p, XSound.ENTITY_VILLAGER_HURT);
								}
								plugin.getApi().send(p, firstPart, plugin.getLanguage().getClickHere(),
										new ArrayList<String>(), "/revent tjoin " + password,
										lastPart.replaceAll("%players%", "" + getPlayers().size())
												.replaceAll("%neededPlayers%", tournament.getMinPlayers().toString()));
							}
						}
						if (tries <= plugin.getNumberOfTriesBeforeCancelling()) {
							matchWaitingPlayers();
						} else {
							finalizaPartida(new ArrayList<Player>(), playersSpectators, Boolean.FALSE, Boolean.TRUE);

						}
					}

				}
			}
		}, 20 * 15L);

	}

	public void nextGame(List<String> players, List<Player> playersGanadores, List<Player> playersSpectators) {
		if (!siguiente) {
			siguiente = Boolean.TRUE;
			if (playersGanadores.size() == 1) {
				daRecompensas(playersGanadores, playersSpectators);
			} else {
				String info = plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getTournamentAlive()
						+ Constantes.SALTO_LINEA;
				for (Player p : playersGanadores) {
					info += "§6§l" + p.getName() + ", ";
				}
				info = info.substring(0, info.length() - 2);
				for (Player p : playersSpectators) {
					p.sendMessage(info);
				}
				Boolean buscando = Boolean.TRUE;
				for (Entry<RangePlayers, Match> entrada : matchPerRound.entrySet()) {
					if (buscando) {
						if (playersGanadores.size() >= entrada.getKey().getTo()
								&& playersGanadores.size() <= entrada.getKey().getFrom()) {
							range = entrada.getKey();
							buscando = Boolean.FALSE;
							for (Player p : playersSpectators) {
								UtilsRandomEvents.teleportaPlayer(p, tournament.getPlayerSpawn(), plugin);
							}

						}
					}
				}

				setPartida(new MatchActive(matchPerRound.get(getRange()), plugin, forzada, true, getInstance(), players,
						playersGanadores, playersSpectators));

				plugin.setMatchActive(partida);

				Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
					public void run() {

						getPartida().setPlayers(players);
						getPartida().setPlayersObj(playersGanadores);
						getPartida().setPlayersSpectators(playersSpectators);

						plugin.setMatchActive(getPartida());

						getPartida().matchBegin();
						siguiente = Boolean.FALSE;

					}
				}, 20 * 7L);

			}
		}
	}

	public void daRecompensas(List<Player> ganadores, List<Player> playersSpectators) {
		if (getPlaying()) {
			setPlaying(Boolean.FALSE);
			UtilsRandomEvents.playSound(playersSpectators, XSound.ENTITY_ENDER_DRAGON_DEATH);

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

				play.sendMessage(plugin.getLanguage().getTagPlugin() + " "
						+ plugin.getLanguage().getWinnersTournament().replace("%players%", cadenaGanadores));

			}
			Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
				public void run() {
					finalizaPartida(ganadores, playersSpectators, Boolean.FALSE, Boolean.FALSE);
				}
			}, 20 * 5L);
		}
	}

	public void finalizaPartida(List<Player> ganadores, List<Player> playersSpectators, Boolean abrupto,
			Boolean cancelled) {
		List<Player> spectatorAux = new ArrayList<Player>();
		spectatorAux.addAll(playersSpectators);
		for (Player p : spectatorAux) {

			// UtilsRandomEvents.borraInventario(p);
			//
			// p.teleport(plugin.getSpawn());
			//
			// UtilsRandomEvents.sacaInventario(plugin, p);
			echaDePartida(p, playersSpectators, true);
		}
		for (Player player : ganadores) {
			// UtilsStats.aumentaStats(player.getName(), getMatch().getName(),
			// StatsEnum.PARTIDAS_SUPERADAS, plugin);
			for (String comando : tournament.getRewards()) {
				Boolean ejecutaComando = Boolean.TRUE;
				String[] trozosComandos = comando.split(" ");

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
			UtilsRandomEvents.mandaMensaje(plugin, playersOnline, plugin.getLanguage().getTournamentCancelled(), true);

		}

		reiniciaValoresPartida();
	}

	private void reiniciaValoresPartida() {
		this.players.clear();
		this.playersObj.clear();
		this.playersSpectators.clear();
		setPlaying(Boolean.FALSE);
		plugin.reiniciaPartida(forzada);
	}

	public void echaDePartida(Player player, List<Player> playersSpectators, Boolean sacaInv) {
		if (playersSpectators.contains(player)) {

			UtilsRandomEvents.borraInventario(player, plugin);

			try {
				getPlayersSpectators().remove(player);
				playersSpectators.remove(player);
				getPlayers().remove(player.getName());
				getPlayersObj().remove(player);
				UtilsRandomEvents.teleportaPlayer(player, plugin.getSpawn(), plugin);

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
			if (sacaInv) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		}

	}

	public void uneAPlayer(Player player) {

		if (!getPlayers().contains(player.getName())) {

			if (getPlayers().size() < tournament.getMaxPlayers()) {
				if (!plugin.isForceEmptyInventoryToJoin()) {

					if (UtilsRandomEvents.checkLeatherItems(player)) {
						procesoUnirPlayer(player);

					} else {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
								+ plugin.getLanguage().getDisposeLeatherItems());

					}
				} else {
					if (UtilsRandomEvents.checkInventoryVacio(player)) {
						procesoUnirPlayer(player);
					} else {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getClearInventory());

					}
				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchFull());
			}

		}
	}

	private void procesoUnirPlayer(Player player) {
		if (UtilsRandomEvents.guardaInventario(plugin, player)) {

			UtilsRandomEvents.borraInventario(player, plugin);

			if (UtilsRandomEvents.teleportaPlayer(player, tournament.getPlayerSpawn(), plugin)) {
				hazComandosDeUnion(player);

				getPlayers().add(player.getName());
				getPlayersObj().add(player);
				getPlayersSpectators().add(player);

				UtilsRandomEvents.playSound(player, XSound.ENTITY_BAT_HURT);
			} else {
				UtilsRandomEvents.sacaInventario(plugin, player);

			}

		} else {
			player.sendMessage(
					plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getErrorSavingInventory());

		}

	}

	private void hazComandosDeUnion(Player player) {
		for (String cmd : plugin.getCommandsOnUserJoin()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}

	}

	public Map<RangePlayers, Match> getMatchPerRound() {
		return matchPerRound;
	}

	public void setMatchPerRound(Map<RangePlayers, Match> matchPerRound) {
		this.matchPerRound = matchPerRound;
	}

	public MatchActive getPartida() {
		return partida;
	}

	public void setPartida(MatchActive partida) {
		this.partida = partida;
	}

	public RangePlayers getRange() {
		return range;
	}

	public void setRange(RangePlayers range) {
		this.range = range;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public List<Player> getPlayersObj() {
		return playersObj;
	}

	public void setPlayersObj(List<Player> playersObj) {
		this.playersObj = playersObj;
	}

	public Boolean getPlaying() {
		return playing;
	}

	public void setPlaying(Boolean playing) {
		this.playing = playing;
	}

	public TournamentActive getInstance() {
		return this;
	}

	public Boolean getFirstAnnounce() {
		return firstAnnounce;
	}

	public void setFirstAnnounce(Boolean firstAnnounce) {
		this.firstAnnounce = firstAnnounce;
	}

	public List<Player> getPlayersSpectators() {
		return playersSpectators;
	}

	public void setPlayersSpectators(List<Player> playersSpectators) {
		this.playersSpectators = playersSpectators;
	}

	public Boolean getForzada() {
		return forzada;
	}

	public void setForzada(Boolean forzada) {
		this.forzada = forzada;
	}

	public boolean isSiguiente() {
		return siguiente;
	}

	public void setSiguiente(boolean siguiente) {
		this.siguiente = siguiente;
	}

}
