package com.adri1711.randomevents.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class Move implements Listener {

	private RandomEvents plugin;

	public Move(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlaying()
				&& plugin.getMatchActive().getPlayersObj().contains(player)) {

			switch (plugin.getMatchActive().getMatch().getMinigame()) {
			case BOAT_RUN:
			case HORSE_RUN:
			case RACE:

				if (plugin.getMatchActive().getCuboid().contains(player.getLocation())) {

					plugin.getMatchActive().setPlayerContador(player);
					if (plugin.getTournamentActive() == null) {
						plugin.getMatchActive().daRecompensas(false);
					} else {
						if (!plugin.getMatchActive().getPlayersGanadores().contains(player)) {
							plugin.getMatchActive().getPlayersGanadores().add(player);
							for (Player p : plugin.getMatchActive().getPlayersSpectators()) {
								p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
										+ plugin.getLanguage().getRaceTournament()
												.replaceAll("%player%", player.getName())
												.replaceAll("%players%",
														"" + plugin.getMatchActive().getPlayersGanadores().size())
												.replaceAll("%neededPlayers%",
														plugin.getMatchActive().getLimitPlayers().toString()));
							}
							plugin.getMatchActive().compruebaPartida();
						}
					}
				}
				break;
			case ESCAPE_FROM_BEAST:
				if (plugin.getMatchActive().getBeast() != null
						&& !plugin.getMatchActive().getBeast().getName().equals(player.getName())
						&& plugin.getMatchActive().getCuboid().contains(player.getLocation())) {
					if (!plugin.getMatchActive().getGoalPlayers().contains(player)) {
						plugin.getMatchActive().getGoalPlayers().add(player);
						plugin.getMatchActive().ponInventarioRunner(player);
						UtilsRandomEvents.playSound(player, UtilsRandomEvents.buscaSonido("LEVEL", "UP"));
					}
				}
				break;
			case TNT_RUN:
				if (plugin.getMatchActive().getActivated()) {
					Location from = evt.getFrom();
					from.setY(evt.getTo().getY());

					UtilsRandomEvents.queueTNT(plugin, plugin.getMatchActive(), player.getLocation(),
							from.distance(evt.getTo()), true);
				}
				break;
			default:
				break;
			}
		}

	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

}
