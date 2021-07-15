package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Kit;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.TournamentActive;
import com.adri1711.randomevents.match.WaterDropStep;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.schedule.DayWeek;
import com.adri1711.randomevents.match.schedule.Schedule;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;

public class ComandosExecutor {

	public ComandosExecutor() {
		super();
	}

	public void joinRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getMatchActive() != null && player.getVehicle() == null && !player.isSleeping()) {

			if (!plugin.getMatchActive().getPlaying()) {
				if (plugin.getMatchActive().getPlayerHandler().getPlayers().size() < plugin.getMatchActive().getMatch()
						.getAmountPlayers()) {
					if (plugin.getMatchActive().getPassword().equals(password)) {
						if (UtilsRandomEvents.checkBanned(player, plugin)) {
							player.sendMessage(
									plugin.getLanguage().getTagPlugin() + " "
											+ plugin.getLanguage().getYouAreBanned().replaceAll("%time%",
													UtilsRandomEvents.calculateTime((plugin.getReventConfig()
															.getBannedPlayers().getBannedPlayers().get(player.getName())
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

	public void joinRandomEvent(RandomEvents plugin, Player player) {

		if (plugin.getMatchActive() != null && player.getVehicle() == null && !player.isSleeping()) {

			if (!plugin.getMatchActive().getPlaying()) {
				if (plugin.getMatchActive().getPlayerHandler().getPlayers().size() < plugin.getMatchActive().getMatch()
						.getAmountPlayers()) {
					if (!plugin.getReventConfig().isNeedPasswordToJoin()) {
						if (UtilsRandomEvents.checkBanned(player, plugin)) {
							player.sendMessage(
									plugin.getLanguage().getTagPlugin() + " "
											+ plugin.getLanguage().getYouAreBanned().replaceAll("%time%",
													UtilsRandomEvents.calculateTime((plugin.getReventConfig()
															.getBannedPlayers().getBannedPlayers().get(player.getName())
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

	public void specRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null) {

			if (plugin.getMatchActive().getPlaying()) {

				if (UtilsRandomEvents.checkBanned(player, plugin)) {
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " "
									+ plugin.getLanguage().getYouAreBanned()
											.replaceAll("%time%",
													UtilsRandomEvents.calculateTime((plugin.getReventConfig()
															.getBannedPlayers().getBannedPlayers().get(player.getName())
															- (new Date()).getTime()) / 1000)));
				} else {
					plugin.getMatchActive().uneAPlayerSpec(player);
				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBeginSoon());
			}

		}

		// UtilsGUI.showGUI(player, plugin);
	}

	public void joinTournamentRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getTournamentActive() != null && player.getVehicle() == null && !player.isSleeping()) {
			if (!plugin.getTournamentActive().getPlaying()) {

				if (plugin.getTournamentActive().getPassword().equals(password)) {

					if (UtilsRandomEvents.checkBanned(player, plugin)) {
						player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
								+ plugin.getLanguage().getYouAreBanned().replaceAll("%time%",
										UtilsRandomEvents.calculateTime(
												(plugin.getReventConfig().getBannedPlayers().getBannedPlayers()
														.get(player.getName()) - (new Date()).getTime()) / 1000)));
					} else {
						plugin.getTournamentActive().uneAPlayer(player);
					}

				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidPassword());
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
			plugin.getReventConfig().getBannedPlayers().getBannedPlayers().put(namePlayer, c.getTime().getTime());
			for (Player pla : Bukkit.getOnlinePlayers()) {
				pla.sendMessage(plugin.getLanguage().getTagPlugin() + " "
						+ plugin.getLanguage().getBanPlayer()
								.replaceAll("%time%",
										UtilsRandomEvents
												.calculateTime((c.getTime().getTime() - (new Date()).getTime()) / 1000))
								.replaceAll("%player%", namePlayer));
			}
		} catch (Exception e) {

			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void banPlayer(RandomEvents plugin, Player player, String namePlayer, String time, String reason) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			time = time.toLowerCase();
			if (time.contains("d")) {
				c.add(Calendar.DAY_OF_YEAR, Integer.valueOf(time.split("d")[0]));
			} else if (time.contains("h")) {
				c.add(Calendar.HOUR, Integer.valueOf(time.split("h")[0]));

			} else if (time.contains("m")) {
				c.add(Calendar.MINUTE, Integer.valueOf(time.split("m")[0]));

			} else if (time.contains("s")) {
				c.add(Calendar.SECOND, Integer.valueOf(time.split("s")[0]));

			} else {
				c.add(Calendar.SECOND, Integer.valueOf(time));
			}
			plugin.getReventConfig().getBannedPlayers().getBannedPlayers().put(namePlayer, c.getTime().getTime());
			for (Player pla : Bukkit.getOnlinePlayers()) {
				pla.sendMessage(plugin.getLanguage().getTagPlugin() + " "
						+ plugin.getLanguage().getBanPlayer()
								.replaceAll("%time%",
										UtilsRandomEvents
												.calculateTime((c.getTime().getTime() - (new Date()).getTime()) / 1000))
								.replaceAll("%player%", namePlayer)
						+ ". Reason: " + reason);
			}
		} catch (Exception e) {

			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}

	}

	public void banPlayer(RandomEvents plugin, Player player, String[] args) {
		String namePlayer = args[1];
		String time = args[2];
		String reason = "";
		int i = 0;
		for (String s : args) {
			if (i > 2) {
				if (s != null)
					reason += s + " ";
			}
			i++;
		}
		reason = reason.substring(0, reason.length() - 1);
		banPlayer(plugin, player, namePlayer, time, reason);

	}

	public void unbanPlayer(RandomEvents plugin, Player player, String namePlayer) {
		try {
			if (plugin.getReventConfig().getBannedPlayers().getBannedPlayers().containsKey(namePlayer)) {
				plugin.getReventConfig().getBannedPlayers().getBannedPlayers().remove(namePlayer);
				for (Player pla : Bukkit.getOnlinePlayers()) {
					pla.sendMessage(plugin.getLanguage().getTagPlugin()
							+ plugin.getLanguage().getUnbanPlayer().replaceAll("%player%", namePlayer));
				}
			} else {
				if (player != null)
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getPlayerNotBanned());
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
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
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
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
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
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
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
						+ plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage(plugin) + " -> "
						+ m.getName().replaceAll(" ", "_"));
			} else {
				System.out.println(plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage(plugin) + " -> "
						+ m.getName().replaceAll(" ", "_"));
			}
		}

	}

	public void showKits(RandomEvents plugin, Player player) {
		player.sendMessage(plugin.getLanguage().getTagPlugin() + "§e§lKits");
		for (Kit m : plugin.getKits()) {
			if (player != null) {

				player.sendMessage("§6§l" + plugin.getKits().indexOf(m) + " - " + m.getName());
			} else {
				System.out.println(plugin.getKits().indexOf(m) + " - " + m.getName());
			}
		}

	}

	public void nextRandomEvents(RandomEvents plugin, Player player) {
		if (plugin.getSchedules() != null && !plugin.getSchedules().isEmpty()) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNextEvent());
			Map<Schedule, Date> sMap = UtilsRandomEvents.nextEvent(plugin.getSchedules(), player, plugin);
			Schedule s = null;
			Date d = null;
			for (Entry<Schedule, Date> entrada : sMap.entrySet()) {
				s = entrada.getKey();
				d = entrada.getValue();
			}
			if (s != null) {
				if (s.getMatchName() == null) {
					// player.sendMessage(plugin.getLanguage().getNextEventIsRandom()
					// + " "
					// + DayWeek.getByValues(s.getDay().toString()) + " "
					// + (s.getHour() > 10 ? s.getHour() : ("0" + s.getHour()))
					// + ":"
					// + (s.getMinute() > 10 ? s.getMinute() : ("0" +
					// s.getMinute())));
					player.sendMessage(plugin.getLanguage().getNextEventIsRandom() + " "
							+ UtilsRandomEvents.calculateTime((d.getTime() - new Date().getTime()) / 1000));

				} else {
					// player.sendMessage(plugin.getLanguage().getNextEventName()
					// + s.getMatchName() + " "
					// + DayWeek.getByValues(s.getDay().toString()) + " "
					// + (s.getHour() > 10 ? s.getHour() : ("0" + s.getHour()))
					// + ":"
					// + (s.getMinute() > 10 ? s.getMinute() : ("0" +
					// s.getMinute())));
					player.sendMessage(plugin.getLanguage().getNextEventName() + s.getMatchName() + " "
							+ UtilsRandomEvents.calculateTime((d.getTime() - new Date().getTime()) / 1000));
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
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBeginSoon());
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
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBeginSoon());
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
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
			}
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchBegun());
		}

	}

	public void cancelCreationRandomEvent(RandomEvents plugin, Player player) {
		plugin.getPlayerMatches().remove(player.getName());
		plugin.getPlayersCreation().remove(player.getName());
		plugin.getPlayersCreationWaterDrop().remove(player.getName());
		plugin.getPlayerKit().remove(player.getName());
		plugin.getPlayerWaterDrop().remove(player.getName());
		plugin.getPlayersEntity().remove(player.getName());
		plugin.getPlayersCreationKit().remove(player.getName());

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

	public void editKit(RandomEvents plugin, Player player, String number) {
		try {

			Kit m = plugin.getKits().get(Integer.valueOf(number));
			plugin.getPlayerKit().put(player.getName(), m);
			player.sendMessage(UtilsRandomEvents.enviaInfoCreacionKit(m, player, plugin));
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

		plugin.getConfig().saveData();
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

		plugin.getConfig().saveData();
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

				// plugin.getMatchActive().dejarPartida(player, false);
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
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNotInMatch());
				}
			}
		}
	}

	public void statsRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getReventConfig().isMysqlEnabled()) {
			UtilsSQL.getStats(player, plugin);

		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getStatsDisabled());
		}
	}

	public void statsRandomEvent(RandomEvents plugin, Player player, String name) {
		if (plugin.getReventConfig().isMysqlEnabled()) {
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

	public void giveCredits(RandomEvents plugin, Player player, String event, String playerName, String amount) {
		try {
			Match m = null;
			for (Match match : plugin.getMatchesAvailable()) {
				if (match.getName().replaceAll(" ", "_").equalsIgnoreCase(event)) {
					m = match;
				}
			}
			if (m != null) {
				if (Bukkit.getPlayer(playerName) != null) {
					Player p = Bukkit.getPlayer(playerName);
					UtilsSQL.addCredits(p, m.getName(), Integer.valueOf(amount), plugin);
					if (player != null)
						player.sendMessage(plugin.getLanguage().getTagPlugin()
								+ plugin.getLanguage().getCreditsAddedOther().replaceAll("%credits%", amount)
										.replaceAll("%event%", m.getName()).replaceAll("%player%", playerName));
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getPlayerOffline());

				}

			} else {
				if (player != null) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventInvalid());
				}
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}
	}

	public void giveCredits(RandomEvents plugin, Player player, String playerName, String amount) {
		try {
			Match m = null;
			if (plugin.getMatchesAvailable().size() > 0) {
				m = plugin.getMatchesAvailable().get(plugin.getRandom().nextInt(plugin.getMatchesAvailable().size()));
			}
			if (m != null) {
				if (Bukkit.getPlayer(playerName) != null) {
					Player p = Bukkit.getPlayer(playerName);
					UtilsSQL.addCredits(p, m.getName(), Integer.valueOf(amount), plugin);
					if (player != null)
						player.sendMessage(plugin.getLanguage().getTagPlugin()
								+ plugin.getLanguage().getCreditsAddedOther().replaceAll("%credits%", amount)
										.replaceAll("%event%", m.getName()).replaceAll("%player%", playerName));
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getPlayerOffline());

				}

			} else {
				if (player != null) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventInvalid());
				}
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}
	}

	public void balCredits(RandomEvents plugin, Player player, String playerName) {
		try {
			if (Bukkit.getPlayer(playerName) != null) {
				Player p = Bukkit.getPlayer(playerName);
				UtilsSQL.getCreditsText(player, p, plugin);

			} else {
				if (player != null)
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getPlayerOffline());

			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}
	}

	public void balCredits(RandomEvents plugin, Player player) {
		try {
			UtilsSQL.getCreditsText(player, player, plugin);

		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getInvalidInput());
		}
	}

	public void openGUIEvent(RandomEvents plugin, Player player) {
		if (player.hasPermission(Constantes.PERM_COOLDOWN) || player.hasPermission(Constantes.PERM_COOLDOWN_BYPASS)
				|| plugin.getReventConfig().isMysqlEnabled()) {

			UtilsSQL.getCreditsGUI(player, plugin);

		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNoPermission());
		}

	}

	// public void statsRandomEvent(RandomEvents plugin, Player player) {
	// UtilsGUI.showGUIStats(player, plugin);
	// }
}
