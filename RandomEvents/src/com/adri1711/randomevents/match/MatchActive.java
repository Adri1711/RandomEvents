package com.adri1711.randomevents.match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.commands.ComandosEnum;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class MatchActive {

	private RandomEvents plugin;

	private Match match;

	private String password;

	private List<Location> locations;

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
	private Integer tiempoPartida;

	private Integer maximo;

	private Player playerContador;

	private Integer numeroSegRestantes;

	private ItemStack gema;

	private Integer timerPassword;

	private Boolean forzada;

	public MatchActive(Match match, RandomEvents plugin, Boolean forzada) {
		super();
		this.match = match;
		this.maximo = 0;
		this.setTiempoPartida(match.getTiempoPartida());
		this.plugin = plugin;
		this.random = new Random();

		this.players = new ArrayList<String>();

		this.playersObj = new ArrayList<Player>();

		this.mobs = new ArrayList<Entity>();

		this.equipos = new HashMap<Integer, List<Player>>();

		this.mascotas = new HashMap<String, Entity>();

		this.puntuacion = new HashMap<String, Integer>();
		this.numeroSegRestantes = 10;
		this.playing = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		this.gema = new ItemStack(Material.EMERALD);
		this.setForzada(forzada);

		this.locations = UtilsRandomEvents.getAllPossibleLocations(match.getLocation1(), match.getLocation2());

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
				player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCH_FULL);
			}

		}
	}

	private void hazComandosDeUnion(Player player) {
		for (String cmd : plugin.getCommandsOnUserJoin()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}
	}

	public void echaDePartida(Player player, Boolean comprueba) {
		echaDePartida(player, comprueba, true);
	}

	public void echaDePartida(Player player, Boolean comprueba, Boolean sacaInv) {
		if (getPlayers().contains(player.getName())) {
			if (comprueba) {
				if (!getPlayersObj().remove(player)) {
					UtilsRandomEvents.borraPlayerPorName(getPlayersObj(), player);
				}
				getPlayers().remove(player.getName());
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
			player.teleport(plugin.getSpawn());
			player.setHealth(20);
			player.setFoodLevel(20);
			player.setFireTicks(0);
			if (sacaInv) {
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
				finalizaPartida(getPlayersObj(), Boolean.FALSE);
			}
			if (getPlayersObj().size() == 1) {
				daRecompensas(false);
			} else {
				if (getEquipos().keySet().size() == 1) {
					daRecompensas(false);
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
					UtilsRandomEvents.mandaMensaje(getPlayersObj(),
							Constantes.PLAYER_WINNING.replace("%player%", p.getName()), true);
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
				daRecompensas(false);
			} else {
				Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
					public void run() {
						numeroSegRestantes--;
						if (numeroSegRestantes > 0 && getTimerPassword() != null && getTimerPassword().equals(timerPass)
								&& getPlayerContador() != null)
							UtilsRandomEvents.mandaMensaje(getPlayersObj(),
									Constantes.PLAYER_WINNING_SECONDS
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

	public void daRecompensas(Boolean tiempo) {
		setPlaying(Boolean.FALSE);
		UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("ENDERDRAGON", "DEATH"));
		List<Player> ganadores = new ArrayList<Player>();
		switch (getMatch().getMinigame()) {
		case BATTLE_ROYALE:
		case KNOCKBACK_DUEL:
		case BATTLE_ROYALE_CABALLO:
		case BATTLE_ROYALE_TEAM_2:
		case ESCAPE_ARROW:
			ganadores.addAll(getPlayersObj());
			break;
		case TOP_KILLER:
		case TOP_KILLER_TEAM_2:
			ganadores.addAll(sacaGanadoresPartidaTiempo());
			break;
		case GEM_CRAWLER:
			ganadores.add(getPlayerContador());
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
				play.sendMessage(
						Constantes.TAG_PLUGIN + " " + Constantes.WINNERS_POINTS.replace("%points%", maximo.toString())
								.replace("%players%", cadenaGanadores).replace("%event%", match.getName()));

			} else {
				play.sendMessage(Constantes.TAG_PLUGIN + " "
						+ Constantes.WINNERS.replace("%players%", cadenaGanadores).replace("%event%", match.getName()));
			}
		}
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				finalizaPartida(ganadores, Boolean.FALSE);
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
			for (Player p : playersObj) {
				if (puntuacion.containsKey(p.getName()) && puntuacion.get(p.getName()).equals(maximo)) {
					winners.add(p);
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

		return winners;
	}

	public void finalizaPartida(List<Player> ganadores, Boolean abrupto) {
		for (Entity ent : getMobs()) {
			ent.remove();
		}
		for (Player p : getPlayersObj()) {

			// UtilsRandomEvents.borraInventario(p);
			//
			// p.teleport(plugin.getSpawn());
			//
			// UtilsRandomEvents.sacaInventario(plugin, p);
			echaDePartida(p, false);
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
		plugin.reiniciaPartida(forzada);
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

		UtilsRandomEvents.mandaMensaje(
				getPlayersObj(), Constantes.TAG_PLUGIN + " " + Constantes.EVENT_ANNOUNCE
						.replace("%event%", match.getName()).replace("%type%", match.getMinigame().getMessage()),
				Boolean.FALSE);

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
		case KNOCKBACK_DUEL:
			for (Player p : playersObj) {
				reiniciaPlayer(p);

			}
			break;

		case TOP_KILLER:
			for (Player p : playersObj) {
				reiniciaPlayer(p);
			}
			partidaPorTiempo();
			break;
		case TOP_KILLER_TEAM_2:
			for (Player p : playersObj) {
				Integer indice = playersObj.indexOf(p);

				reiniciaPlayer(p);
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
			for (Player p : playersObj) {
				Integer indice = playersObj.indexOf(p);
				reiniciaPlayer(p);

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
			for (Player p : playersObj) {
				reiniciaPlayer(p);

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
		case ESCAPE_ARROW:
			for (Player p : playersObj) {
				reiniciaPlayer(p);
			}
			partidaEscapeArrow();
			break;
		case GEM_CRAWLER:
			for (Player p : playersObj) {
				reiniciaPlayer(p);
			}
			spawneaGemas();
			break;
		}

	}

	private void spawneaGemas() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {

					Location l = locations.get(getRandom().nextInt(locations.size()));

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

					Location l = locations.get(getRandom().nextInt(locations.size()));
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
					UtilsRandomEvents.mandaMensaje(playersObj, Constantes.TIME_REMAINING.replace("%minutes%",
							UtilsRandomEvents.preparaStringTiempo(tiempoPartida)), true);
					partidaPorTiempo();
				}

			}, 20 * 60);
		}

	}

	public void acabaPartidaEnTiempo() {

		UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
		UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.SECONDS_3_REMAINING, Boolean.TRUE);
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.SECONDS_2_REMAINING, Boolean.TRUE);
			}
		}, 20 * 1L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(getPlayersObj(), UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
				UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.SECONDS_1_REMAINING, Boolean.TRUE);
			}
		}, 20 * 2L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {

				daRecompensas(true);
				// spawnMobs(bWave.getMobs(), getPlugin());
			}

		}, 20 * 3L);
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
				ItemStack puntos = new ItemStack(Material.EMERALD);
				puntos.setAmount(amount);
				location.getWorld().dropItem(location, puntos);
				getPuntuacion().put(p.getName(), 0);
				UtilsRandomEvents.mandaMensaje(getPlayersObj(), Constantes.LOST_GEMS.replace("%player%", p.getName()),
						true);
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
					player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.SHOW_ALONE);
				} else if (restoEquipo.size() == 1) {
					player.sendMessage(Constantes.TAG_PLUGIN + " "
							+ Constantes.SHOW_TEAM.replace("%players%", restoEquipo.get(0).getName()));
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
					player.sendMessage(
							Constantes.TAG_PLUGIN + " " + Constantes.SHOW_TEAM.replace("%players%", cadenaEquipo));

					// player.sendMessage(players.toString());
					// player.sendMessage(Constantes.GREEN +
					// player.getName()+"aaaaa");

					// plugin.getApi().cambiaNombre(player, Constantes.GREEN +
					// player.getName()+"aaaaa", players);

				}
			}

		}

	}

	private void ponInventarioMatch(Player p) {
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

	public void matchWaitingPlayers() {

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (plugin.getMatchActive() != null && plugin.getMatchActive().getPassword().equals(getPassword())) {

					Boolean playSound = Boolean.FALSE;

					if (match.getAmountPlayersMin() <= (getPlayers().size())) {
						if (!getPlaying())
							matchBegin();
					} else {
						// if (!getPlayers().isEmpty()) {
						// String waiting = Constantes.WAITING_FOR_PLAYERS;
						// UtilsRandomEvents
						// .mandaMensaje(getPlayersObj(),
						// waiting.replaceAll("%players%", "" +
						// getPlayers().size())
						// .replaceAll("%neededPlayers%",
						// match.getAmountPlayersMin().toString()),
						// Boolean.TRUE);
						// }
						String firstPart = Constantes.TAG_PLUGIN + " ";

						if (firstAnnounce) {
							playSound = Boolean.TRUE;
							firstPart += Constantes.FIRST_ANNOUNCE;
							firstAnnounce = Boolean.FALSE;
						} else {
							firstPart += Constantes.NEXT_ANNOUNCE;

						}
						String lastPart = Constantes.LAST_PART;
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())) {
								if (playSound) {
									UtilsRandomEvents.playSound(p, UtilsRandomEvents.buscaSonido("VILLAGER", "HIT"));
								}
								plugin.getApi().send(p, firstPart, Constantes.CLICK_HERE, new ArrayList<String>(),
										"/revent join " + password,
										lastPart.replaceAll("%players%", "" + getPlayers().size())
												.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString()));
							}
						}

						matchWaitingPlayers();
					}

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

	public Integer getTiempoPartida() {
		return tiempoPartida;
	}

	public void setTiempoPartida(Integer tiempoPartida) {
		this.tiempoPartida = tiempoPartida;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
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

}
