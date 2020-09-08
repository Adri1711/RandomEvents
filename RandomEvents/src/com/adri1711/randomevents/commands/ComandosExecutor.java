package com.adri1711.randomevents.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.TournamentActive;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class ComandosExecutor {

	public ComandosExecutor() {
		super();
	}

	public void joinRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getMatchActive() != null) {
			if (!plugin.getMatchActive().getPlaying()) {
				if (plugin.getMatchActive().getPassword().equals(password)) {
					plugin.getMatchActive().uneAPlayer(player);
				} else {
					player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getInvalidPassword());
				}

			} else {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBegun());
			}

		}

		// UtilsGUI.showGUI(player, plugin);
	}

	public void joinTournamentRandomEvent(RandomEvents plugin, Player player, String password) {
		if (plugin.getTournamentActive() != null) {
			if (!plugin.getTournamentActive().getPlaying()) {

				if (plugin.getTournamentActive().getPassword().equals(password)) {
					plugin.getTournamentActive().uneAPlayer(player);
				} else {
					player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getInvalidPassword());
				}

			} else {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBegun());
			}

		}

		// UtilsGUI.showGUI(player, plugin);
	}

	public void showRandomEvents(RandomEvents plugin, Player player) {
		player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatches());
		for (Match m : plugin.getMatches()) {
			player.sendMessage("§6§l" + plugin.getMatches().indexOf(m) + " - " + m.getMinigame().getMessage() + " -> "
					+ m.getName());
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setMatchActive(UtilsRandomEvents.escogeMatchActiveAleatoria(plugin, plugin.getMatches(), true));
			player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBeginSoon());
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBegun());
		}

	}

	public void forceTournamentRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getTournamentActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setTournamentActive(new TournamentActive(plugin.getTournament(), plugin, true));
			player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBeginSoon());
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBegun());
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player, String number) {
		if (plugin.getMatchActive() == null) {
			try {
				plugin.setForzado(Boolean.TRUE);
				plugin.setMatchActive(new MatchActive(plugin.getMatches().get(Integer.valueOf(number)), plugin, true));
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBeginSoon());
			} catch (Exception e) {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getInvalidInput());
			}
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getMatchBegun());
		}

	}

	public void cancelCreationRandomEvent(RandomEvents plugin, Player player) {
		plugin.getPlayerMatches().remove(player.getName());
		plugin.getPlayersCreation().remove(player.getName());
		player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getCancelOfArenaCreation());

	}

	public void createRandomEvent(RandomEvents plugin, Player player) {
		Integer position = 0;
		plugin.getPlayersCreation().put(player.getName(), position);
		UtilsRandomEvents.pasaACreation(plugin, player, position,plugin.getPlayerMatches().get(player.getName()));
	}

	public void reloadPlugin(RandomEvents plugin, Player player) {
		plugin.doingReload();
		player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getPluginReload());
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
		player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getSpawnSet());
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
		player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getSpawnSet());
	}

	public void leaveRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() != null) {
			if (plugin.getMatchActive().getPlayersObj().contains(player)) {
				plugin.getMatchActive().dejarPartida(player, false);
			} else if (plugin.getMatchActive().getPlayersSpectators().contains(player)) {
				plugin.getMatchActive().echaDePartida(player, false, true, true);

			} else {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getNotInMatch());
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
				player.sendMessage(Constantes.TAG_PLUGIN + " " + plugin.getLanguage().getNotInMatch());
			}
		}
	}

	// public void statsRandomEvent(RandomEvents plugin, Player player) {
	// UtilsGUI.showGUIStats(player, plugin);
	// }
}
