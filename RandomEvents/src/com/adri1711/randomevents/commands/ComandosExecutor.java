package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.DayWeek;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.Schedule;
import com.adri1711.randomevents.match.TournamentActive;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;

public class ComandosExecutor {

	public ComandosExecutor() {
		super();
	}

	public void joinRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getMatchActive() != null) {
			if (!plugin.getMatchActive().getPlaying()) {
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
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidPassword());
				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBegun());
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
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidPassword());
				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBegun());
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
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidInput());
		}

	}

	public void unbanPlayer(RandomEvents plugin, Player player, String namePlayer) {
		try {
			if (plugin.getBannedPlayers().getBannedPlayers().containsKey(namePlayer)) {
				plugin.getBannedPlayers().getBannedPlayers().remove(namePlayer);
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getUnbanPlayer());
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getPlayerNotBanned());
			}
		} catch (Exception e) {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidInput());
		}

	}

	public void showRandomEvents(RandomEvents plugin, Player player) {
		player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatches());
		for (Match m : plugin.getMatches()) {
			if (player != null) {
				player.sendMessage("§6§l" + plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage()
						+ " -> " + m.getName());
			} else {
				System.out.println(
						plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage() + " -> " + m.getName());
			}
		}

	}

	public void currentDate(RandomEvents plugin, Player player) {
		player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + new Date());

	}

	public void forceRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setMatchActive(UtilsRandomEvents.escogeMatchActiveAleatoria(plugin, plugin.getMatches(), true));
			if (player != null)
				player.sendMessage(
						plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBeginSoon());
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBegun());
		}

	}

	public void forceTournamentRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getTournamentActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setTournamentActive(new TournamentActive(plugin.getTournament(), plugin, true));
			if (player != null)
				player.sendMessage(
						plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBeginSoon());
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBegun());
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player, String number) {
		if (plugin.getMatchActive() == null) {
			try {
				plugin.setForzado(Boolean.TRUE);
				plugin.setMatchActive(new MatchActive(plugin.getMatches().get(Integer.valueOf(number)), plugin, true));
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBeginSoon());
			} catch (Exception e) {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidInput());
			}
		} else {
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getMatchBegun());
		}

	}

	public void cancelCreationRandomEvent(RandomEvents plugin, Player player) {
		plugin.getPlayerMatches().remove(player.getName());
		plugin.getPlayersCreation().remove(player.getName());
		player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getCancelOfArenaCreation());

	}

	public void createRandomEvent(RandomEvents plugin, Player player) {
		Integer position = 0;
		plugin.getPlayersCreation().put(player.getName(), position);
		UtilsRandomEvents.pasaACreation(plugin, player, position, plugin.getPlayerMatches().get(player.getName()));
	}

	public void reloadPlugin(RandomEvents plugin, Player player) {
		plugin.doingReload();
		if (player != null)
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getPluginReload());
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
		player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getSpawnSet());
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
		player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getSpawnSet());
	}

	public void forceStop(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlaying()) {
			plugin.getMatchActive().finalizaPartida(new ArrayList<Player>(), true, false);
		}
	}

	public void leaveRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null) {
			if (plugin.getMatchActive().getPlayersObj().contains(player)) {
				plugin.getMatchActive().dejarPartida(player, false);
			} else if (plugin.getMatchActive().getPlayersSpectators().contains(player)) {
				plugin.getMatchActive().echaDePartida(player, false, true, true);

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNotInMatch());
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
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNotInMatch());
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
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidInput());
		}
	}

	public void scheduleRandomEvent(RandomEvents plugin, Player player, String day, String hour, String minute,
			String idMatch) {
		try {
			Schedule s = new Schedule(DayWeek.getByValues(day).getPosition(), Integer.valueOf(hour),
					Integer.valueOf(minute), plugin.getMatches().get(Integer.valueOf(idMatch)).getName());

			UtilsRandomEvents.terminaCreacionSchedule(plugin, player, s);

		} catch (Exception e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidInput());
		}
	}

	// public void statsRandomEvent(RandomEvents plugin, Player player) {
	// UtilsGUI.showGUIStats(player, plugin);
	// }
}
