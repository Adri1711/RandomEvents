package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.TournamentActive;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.schedule.DayWeek;
import com.adri1711.randomevents.match.schedule.Schedule;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;

public class ComandosExecutor {

	public ComandosExecutor() {
		super();
	}

	public void joinRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getMatchActive() != null) {

			if (!plugin.getMatchActive().getPlaying()) {
				if (plugin.getMatchActive().getPlayerHandler().getPlayers().size() < plugin.getMatchActive().getMatch()
						.getAmountPlayers()) {
					if (plugin.getMatchActive().getPassword().equals(password)) {
						if (UtilsRandomEvents.checkBanned(player, plugin)) {
							player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getYouAreBanned().replaceAll("%time%",
											UtilsRandomEvents.calculateTime(
													(plugin.getBannedPlayers().getBannedPlayers().get(player.getName())
															- (new Date()).getTime()) / 1000)));
						} else {
							plugin.getMatchActive().uneAPlayer(player);
						}
					} else {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidPassword());
					}
				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchFull());

				}
			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBegun());
			}

		}

		// UtilsGUI.showGUI(player, plugin);
	}

	public void joinTournamentRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getTournamentActive() != null) {
			if (!plugin.getTournamentActive().getPlaying()) {

				if (plugin.getTournamentActive().getPassword().equals(password)) {

					if (UtilsRandomEvents.checkBanned(player, plugin)) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
								+ plugin.getLanguage().getYouAreBanned().replaceAll("%time%",
										UtilsRandomEvents.calculateTime(
												(plugin.getBannedPlayers().getBannedPlayers().get(player.getName())
														- (new Date()).getTime()) / 1000)));
					} else {
						plugin.getTournamentActive().uneAPlayer(player);
					}

				} else {
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidPassword());
				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBegun());
			}

		}

		// UtilsGUI.showGUI(player, plugin);
	}

	public void banPlayer(RandomEvents plugin, Player player, String namePlayer, String time) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			time = time.toLowerCase();
			if (time.contains("d")) {
				c.add(Calendar.DAY_OF_YEAR, Integer.valueOf(time.split("d")[0]));
			} else if (time.contains("h")) {
				c.add(Calendar.HOUR_OF_DAY, Integer.valueOf(time.split("h")[0]));

			} else if (time.contains("m")) {
				c.add(Calendar.MINUTE, Integer.valueOf(time.split("m")[0]));

			} else if (time.contains("s")) {
				c.add(Calendar.SECOND, Integer.valueOf(time.split("s")[0]));

			} else {
				c.add(Calendar.SECOND, Integer.valueOf(time));
			}
			plugin.getBannedPlayers().getBannedPlayers().put(namePlayer, c.getTime().getTime());
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
						+ plugin.getLanguage().getBanPlayer().replaceAll("%time%", UtilsRandomEvents
								.calculateTime((c.getTime().getTime() - (new Date()).getTime()) / 1000)));
		} catch (Exception e) {

			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void unbanPlayer(RandomEvents plugin, Player player, String namePlayer) {
		try {
			if (plugin.getBannedPlayers().getBannedPlayers().containsKey(namePlayer)) {
				plugin.getBannedPlayers().getBannedPlayers().remove(namePlayer);
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getUnbanPlayer());
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getPlayerNotBanned());
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void deleteRandomEvent(RandomEvents plugin, Player player, String number) {
		try {
			Match match = plugin.getMatches().get(Integer.valueOf(number));
			if (match != null) {
				UtilsRandomEvents.borraMatch(plugin, match, player);
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void disableRandomEvent(RandomEvents plugin, Player player, String number) {
		try {
			Match match = plugin.getMatches().get(Integer.valueOf(number));
			if (match != null) {
				UtilsRandomEvents.disableMatch(plugin, match, player);
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void enableRandomEvent(RandomEvents plugin, Player player, String number) {
		try {
			Match match = plugin.getMatches().get(Integer.valueOf(number));
			if (match != null) {
				UtilsRandomEvents.enableMatch(plugin, match, player);
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void showRandomEvents(RandomEvents plugin, Player player) {
		player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatches());
		for (Match m : plugin.getMatches()) {
			if (player != null) {

				player.sendMessage(((m.getEnabled() == null || m.getEnabled()) ? "§6" : "§c") + "§l"
						+ plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage() + " -> " + m.getName());
			} else {
				System.out.println(
						plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage() + " -> " + m.getName());
			}
		}

	}

	public void nextRandomEvents(RandomEvents plugin, Player player) {
		if (plugin.getSchedules() != null && !plugin.getSchedules().isEmpty()) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNextEvent());
			Schedule s = UtilsRandomEvents.nextEvent(plugin.getSchedules(), player, plugin);
			if (s != null) {
				if (s.getMatchName() == null) {
					player.sendMessage(plugin.getLanguage().getNextEventIsRandom() + " "
							+ DayWeek.getByValues(s.getDay().toString()) + " "
							+ (s.getHour() > 10 ? s.getHour() : ("0" + s.getHour())) + ":"
							+ (s.getMinute() > 10 ? s.getMinute() : ("0" + s.getMinute())));

				} else {
					player.sendMessage(plugin.getLanguage().getNextEventName() + s.getMatchName() + " "
							+ DayWeek.getByValues(s.getDay().toString()) + " "
							+ (s.getHour() > 10 ? s.getHour() : ("0" + s.getHour())) + ":"
							+ (s.getMinute() > 10 ? s.getMinute() : ("0" + s.getMinute())));
				}
			}
		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNoScheduledEvents());

		}

	}
	// TODO max players

	public void currentDate(RandomEvents plugin, Player player) {
		player.sendMessage(plugin.getLanguage().getTagPlugin() + new Date());

	}

	public void forceBeginRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null) {
			plugin.getMatchActive().matchBegin();
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setMatchActive(
					UtilsRandomEvents.escogeMatchActiveAleatoria(plugin, plugin.getMatchesAvailable(), true));
			if (player != null)
				player.sendMessage(
						plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBeginSoon());
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBegun());
		}

	}

	public void forceTournamentRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getTournamentActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setTournamentActive(new TournamentActive(plugin.getTournament(), plugin, true));
			if (player != null)
				player.sendMessage(
						plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBeginSoon());
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBegun());
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player, String number) {
		if (plugin.getMatchActive() == null) {
			try {

				Match m = plugin.getMatches().get(Integer.valueOf(number));
				if (m.getEnabled() == null || m.getEnabled()) {
					plugin.setForzado(Boolean.TRUE);
					plugin.setMatchActive(new MatchActive(m, plugin, true));
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBeginSoon());
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventIsDisabled());
				}

			} catch (Exception e) {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
			}
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBegun());
		}

	}

	public void cancelCreationRandomEvent(RandomEvents plugin, Player player) {
		plugin.getPlayerMatches().remove(player.getName());
		plugin.getPlayersCreation().remove(player.getName());
		player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getCancelOfArenaCreation());

	}

	public void createRandomEvent(RandomEvents plugin, Player player) {
		Match m = new Match();
		plugin.getPlayerMatches().put(player.getName(), m);
		player.sendMessage(UtilsRandomEvents.enviaInfoCreacion(m, player, plugin));
	}

	public void editRandomEvent(RandomEvents plugin, Player player, String number) {
		try {

			Match m = plugin.getMatches().get(Integer.valueOf(number));
			plugin.getPlayerMatches().put(player.getName(), m);
			plugin.getEditando().add(player.getName());
			player.sendMessage(UtilsRandomEvents.enviaInfoCreacion(m, player, plugin));
		} catch (Exception e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());

		}

	}

	public void reloadPlugin(RandomEvents plugin, Player player) {
		plugin.doingReload();
		if (player != null)
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getPluginReload());
	}

	public void spawnSet(RandomEvents plugin, Player player) {
		plugin.setSpawn(player.getLocation());
		plugin.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
		plugin.getConfig().set("spawn.x", player.getLocation().getX());
		plugin.getConfig().set("spawn.y", player.getLocation().getY());
		plugin.getConfig().set("spawn.z", player.getLocation().getZ());
		plugin.getConfig().set("spawn.yaw", player.getLocation().getYaw());
		plugin.getConfig().set("spawn.pitch", player.getLocation().getPitch());

		plugin.saveConfig();
		player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getSpawnSet());
	}

	public void tournamentSpawnSet(RandomEvents plugin, Player player) {
		plugin.getTournament().setPlayerSpawn(player.getLocation());
		plugin.getConfig().set("tournament.spawn.world", player.getLocation().getWorld().getName());
		plugin.getConfig().set("tournament.spawn.x", player.getLocation().getX());
		plugin.getConfig().set("tournament.spawn.y", player.getLocation().getY());
		plugin.getConfig().set("tournament.spawn.z", player.getLocation().getZ());
		plugin.getConfig().set("tournament.spawn.yaw", player.getLocation().getYaw());
		plugin.getConfig().set("tournament.spawn.pitch", player.getLocation().getPitch());

		plugin.saveConfig();
		player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getSpawnSet());
	}

	public void forceStop(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlaying()) {
			plugin.getMatchActive().finalizaPartida(new ArrayList<Player>(), true, false);
		}
	}

	public void leaveRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null) {
			if (plugin.getMatchActive().getPlayerHandler().getPlayersObj().contains(player)) {
				plugin.getMatchActive().echaDePartida(player, true, true, true);

//				plugin.getMatchActive().dejarPartida(player, false);
			} else if (plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
				plugin.getMatchActive().echaDePartida(player, false, true, true);

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNotInMatch());
			}
		}
	}

	public void leaveTournamentRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getTournamentActive() != null) {
			if (plugin.getTournamentActive().getPlayersObj().contains(player)) {
				plugin.getTournamentActive().dejarPartida(player, false);
			} else if (plugin.getTournamentActive().getPlayersSpectators().contains(player)) {
				plugin.getTournamentActive().echaDePartida(player, plugin.getTournamentActive().getPlayersSpectators(),
						true);

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNotInMatch());
			}
		}
	}

	public void checkpointRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null) {
			if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.RACE)) {
				if (plugin.getMatchActive().getPlayerHandler().getPlayersObj().contains(player)) {

					if (plugin.getMatchActive().getMapHandler().getCheckpoints().containsKey(player.getName())) {

						UtilsRandomEvents.teleportaPlayer(player,
								plugin.getMatchActive().getMapHandler().getCheckpoints().get(player.getName()), plugin);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 99));

					}
				} else {
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNotInMatch());
				}
			}
		}
	}

	public void statsRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.isMysqlEnabled()) {
			UtilsSQL.getStats(player, plugin);

		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getStatsDisabled());
		}
	}

	public void statsRandomEvent(RandomEvents plugin, Player player, String name) {
		if (plugin.isMysqlEnabled()) {
			UtilsSQL.getStats(player, name, plugin);

		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getStatsDisabled());
		}
	}

	public void scheduleRandomEvent(RandomEvents plugin, Player player, String day, String hour, String minute) {
		try {
			Schedule s = new Schedule(DayWeek.getByValues(day).getPosition(), Integer.valueOf(hour),
					Integer.valueOf(minute), "");

			UtilsRandomEvents.terminaCreacionSchedule(plugin, player, s);
		} catch (Exception e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}
	}

	public void scheduleRandomEvent(RandomEvents plugin, Player player, String day, String hour, String minute,
			String idMatch) {
		try {
			Schedule s = new Schedule(DayWeek.getByValues(day).getPosition(), Integer.valueOf(hour),
					Integer.valueOf(minute), plugin.getMatches().get(Integer.valueOf(idMatch)).getName());

			UtilsRandomEvents.terminaCreacionSchedule(plugin, player, s);

		} catch (Exception e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}
	}

	// public void statsRandomEvent(RandomEvents plugin, Player player) {
	// UtilsGUI.showGUIStats(player, plugin);
	// }
}
