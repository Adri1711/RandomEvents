package com.adri1711.randomevents.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.WaterDropStepActive;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.Particle1711;
import com.adri1711.util.enums.ParticleDisplay;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XParticle;
import com.adri1711.util.enums.XSound;

public class Move implements Listener {

	private RandomEvents plugin;

	public Move(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onTp(PlayerTeleportEvent evt) {
		if (evt.getCause() == TeleportCause.SPECTATE) {
			if (evt.getPlayer() != null) {
				Player p = evt.getPlayer();
				if (plugin.getMatchActive() != null
						&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(p)) {
					evt.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		Player player = evt.getPlayer();

		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlaying()
				&& plugin.getMatchActive().getPlayerHandler().getPlayersObj().contains(player)) {
			if (plugin.getMatchActive().getAllowMove()) {

				switch (plugin.getMatchActive().getMatch().getMinigame()) {
				case SPLEEF:
					if (plugin.getReventConfig().isWaterKillSpleef()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;
				case SPLEGG:
					if (plugin.getReventConfig().isWaterKillSplegg()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;
				case ANVIL_SPLEEF:
					if (plugin.getReventConfig().isWaterKillAnvilSpleef()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;
				case KNOCKBACK_DUEL:
					if (plugin.getReventConfig().isWaterKillKnockbackDuel()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;

				case BOMBARDMENT:
					if (plugin.getReventConfig().isWaterKillBombardment()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;

				case SG:
				case TSG:
				case TSG_REAL:
					if (plugin.getReventConfig().isWaterKillSG()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;
				case SW:
				case TSW:
				case TSW_REAL:
					if (plugin.getReventConfig().isWaterKillSW()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;
				case BOAT_RUN:
				case HORSE_RUN:
				case RACE:

					if (plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {

						plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);
						if (plugin.getTournamentActive() == null) {
							plugin.getMatchActive().daRecompensas(false);
						} else {
							if (!plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().contains(player)) {
								plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().add(player);
								for (Player p : plugin.getMatchActive().getPlayerHandler().getPlayersSpectators()) {
									p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
											+ plugin.getLanguage().getRaceTournament()
													.replaceAll("%player%", player.getName())
													.replaceAll("%players%",
															"" + plugin.getMatchActive().getPlayerHandler()
																	.getPlayersGanadores().size())
													.replaceAll("%neededPlayers%",
															plugin.getMatchActive().getLimitPlayers().toString()));
								}
								plugin.getMatchActive().compruebaPartida();
							}
						}
					}
					break;
				case RED_GREEN_LIGHT:

					if (plugin.getMatchActive().getAllowDamage()) {
						redGreenLightKill(player);
					}

					if (plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {

						plugin.getMatchActive().getPlayerHandler().setPlayerContador(player);
						if (plugin.getTournamentActive() == null) {
							plugin.getMatchActive().daRecompensas(false);
						} else {
							if (!plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().contains(player)) {
								plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().add(player);
								for (Player p : plugin.getMatchActive().getPlayerHandler().getPlayersSpectators()) {
									p.sendMessage(plugin.getLanguage().getTagPlugin() + " "
											+ plugin.getLanguage().getRaceTournament()
													.replaceAll("%player%", player.getName())
													.replaceAll("%players%",
															"" + plugin.getMatchActive().getPlayerHandler()
																	.getPlayersGanadores().size())
													.replaceAll("%neededPlayers%",
															plugin.getMatchActive().getLimitPlayers().toString()));
								}
								plugin.getMatchActive().compruebaPartida();
							}
						}
					}

					break;
				case ESCAPE_FROM_BEAST:
					if (plugin.getMatchActive().getPlayerHandler().getBeast() != null
							&& !plugin.getMatchActive().getPlayerHandler().getBeast().getName().equals(player.getName())
							&& plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {
						if (!plugin.getMatchActive().getPlayerHandler().getGoalPlayers().contains(player)) {
							plugin.getMatchActive().getPlayerHandler().getGoalPlayers().add(player);
							plugin.getMatchActive().ponInventarioRunner(player);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_PLAYER_LEVELUP);
						}
					}
					break;
				case WDROP:
					if (plugin.getMatchActive().getStep().containsKey(player)) {
						Integer step = plugin.getMatchActive().getStep().get(player);
						WaterDropStepActive wd = plugin.getMatchActive().getWaterDrops().get(step);
						if (wd.getGoal().contains(player.getLocation())) {
							plugin.getMatchActive().avanzaWaterDrop(player);
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
					if (plugin.getReventConfig().isWaterKillTNTRun()) {
						if (evt.getTo().getBlock() != null && evt.getTo().getBlock().getType() != null
								&& (evt.getTo().getBlock().getType() == XMaterial.WATER.parseMaterial()
										|| evt.getTo().getBlock().getType().toString().equals("STATIONARY_WATER"))) {
							UtilsRandomEvents.mandaMensaje(plugin,
									plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false);
							plugin.getMatchActive().echaDePartida(player, true, true, false);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);
						}
					}
					break;
				case KOTH:
					if (plugin.getMatchActive().getPlayerHandler().getPlayerContador() == null) {
						if (plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {
							plugin.getMatchActive().inKoth(player);
						}
					} else if (plugin.getMatchActive().getPlayerHandler().getPlayerContador().getName()
							.equals(player.getName())) {
						if (!plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {
							plugin.getMatchActive().outKoth(player);
						}

					}
					break;
				case FISH_SLAP:
					if (!plugin.getMatchActive().getPlayerHandler().getPlayersContadores().containsKey(player)) {
						if (plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {
							plugin.getMatchActive().inKoth(player);
						}
					} else {
						if (!plugin.getMatchActive().getMapHandler().getCuboid().contains(player.getLocation())) {
							plugin.getMatchActive().outKoth(player);
						}

					}
					break;
				default:
					break;
				}
			} else {
				Location to = evt.getFrom();
				to.setPitch(evt.getTo().getPitch());
				to.setYaw(evt.getTo().getYaw());
				evt.setTo(to);
			}
		}

	}

	private void redGreenLightKill(Player player) {
		if (!plugin.getMatchActive().getPlayerHandler().getPlayerToKill().contains(player)) {
			plugin.getMatchActive().getPlayerHandler().getPlayerToKill().add(player);
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.playSound(plugin, player, XSound.BLOCK_WOODEN_DOOR_CLOSE, 3.0F, 2.0F);
				}
			}, 10L);
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {
					UtilsRandomEvents.playSound(plugin, player, XSound.BLOCK_WOODEN_DOOR_CLOSE, 3.0F, 2.0F);

				}
			}, 12L);
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR);
					Location loc = plugin.getMatchActive().getMatch().getEntitySpawns().get(
							plugin.getRandom().nextInt(plugin.getMatchActive().getMatch().getEntitySpawns().size()));
					ParticleDisplay pa = ParticleDisplay.display(plugin.getApi(), loc, Particle1711.REDSTONE);
					Location locEnd = player.getLocation();
					locEnd.setY(locEnd.getY() + 1);
					XParticle.line(loc, locEnd, 0.5, pa);
					UtilsRandomEvents.mandaMensaje(plugin,
							plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPvpDeath().replaceAll("%victim%", player.getName()), false, false,
							false);
					plugin.getMatchActive().getPlayerHandler().getPlayerToKill().remove(player);
					plugin.getMatchActive().echaDePartida(player, true, true, false);
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_VILLAGER_DEATH);

				}
			}, 30L);

		}

	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

}
