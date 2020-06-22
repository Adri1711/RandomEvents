package com.adri1711.randomevents.commands;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
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
					player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.INVALID_PASSWORD);
				}

			} else {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCH_BEGUN);
			}

		}

		// UtilsGUI.showGUI(player, plugin);
	}

	public void showRandomEvents(RandomEvents plugin, Player player) {
		player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCHES);
		for (Match m : plugin.getMatches()) {
			player.sendMessage("§6§l" + plugin.getMatches().indexOf(m) + " - " + m.getName());
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive() == null) {
			plugin.setForzado(Boolean.TRUE);
			plugin.setMatchActive(UtilsRandomEvents.escogeMatchActiveAleatoria(plugin, plugin.getMatches(),true));
			player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCH_BEGIN_SOON);
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCH_BEGUN);
		}

	}

	public void forceRandomEvent(RandomEvents plugin, Player player, String number) {
		if (plugin.getMatchActive() == null) {
			try {
				plugin.setForzado(Boolean.TRUE);
				plugin.setMatchActive(new MatchActive(plugin.getMatches().get(Integer.valueOf(number)), plugin,true));
				player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCH_BEGIN_SOON);
			} catch (Exception e) {
				player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.INVALID_INPUT);
			}
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.MATCH_BEGUN);
		}

	}

	public void cancelCreationRandomEvent(RandomEvents plugin, Player player) {
		plugin.getPlayerMatches().remove(player.getName());
		plugin.getPlayersCreation().remove(player.getName());
		player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.CANCEL_OF_ARENA_CREATION);

	}

	public void createRandomEvent(RandomEvents plugin, Player player) {
		Integer position = 0;
		plugin.getPlayersCreation().put(player.getName(), position);
		UtilsRandomEvents.pasaACreation(plugin, player, position);
	}

	public void reloadPlugin(RandomEvents plugin, Player player) {
		plugin.doingReload();
		player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.PLUGIN_RELOAD);
	}

	public void spawnSet(RandomEvents plugin, Player player) {
		plugin.setSpawn(player.getLocation());
		plugin.getConfig().set("spawn", player.getLocation());
		plugin.saveConfig();
		player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.SPAWN_SET);
	}

	public void leaveRandomEvent(RandomEvents plugin, Player player) {
		if (plugin.getMatchActive().getPlayers().contains(player.getName())) {
			plugin.getMatchActive().dejarPartida(player, false);
		} else {
			player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.NOT_IN_MATCH);
		}
	}

	// public void statsRandomEvent(RandomEvents plugin, Player player) {
	// UtilsGUI.showGUIStats(player, plugin);
	// }
}
