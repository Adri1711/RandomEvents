package com.adri1711.randomevents.match;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.api.events.ReventBeginEvent;
import com.adri1711.randomevents.api.events.ReventCountdownEvent;
import com.adri1711.randomevents.api.events.ReventEndEvent;
import com.adri1711.randomevents.commands.ComandosEnum;
import com.adri1711.randomevents.match.data.MatchMapDataHandler;
import com.adri1711.randomevents.match.data.MatchPlayerHandler;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.enums.Petos;
import com.adri1711.randomevents.match.utils.Cuboid;
import com.adri1711.randomevents.match.utils.InventoryPers;
import com.adri1711.randomevents.match.utils.SongUtils;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.FeatherBoardUtils;
import com.adri1711.randomevents.util.UtilsCitizen;
import com.adri1711.randomevents.util.UtilsDisguises;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;
import com.adri1711.util.FastBoard;
import com.adri1711.util.enums.Particle1711;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XSound;

public class MatchActive {

	private RandomEvents plugin;

	private Match match;

	private MatchPlayerHandler playerHandler;

	private MatchMapDataHandler mapHandler;

	private String password;

	private Boolean canBreak;

	private List<Entity> mobs;

	private Map<String, Entity> pets;

	private Map<String, Integer> puntuacion;

	private List<WaterDropStepActive> waterDrops;

	private Map<Player, Integer> step;

	private Boolean playing;

	private Boolean started;

	private Random random;

	private boolean firstAnnounce;

	private Integer tiempoPartida;

	private Integer maximo;

	private Integer numeroSegRestantes;

	private ItemStack gema;

	private Integer timerPassword;

	private Boolean forzada;

	private Boolean tournament;

	private TournamentActive tournamentObj;

	private Integer limitPlayers;

	private Integer tries;
	private Integer counter;
	private BukkitRunnable task;
	private BukkitRunnable task2;
	private BukkitRunnable task3;

	private Integer damageCounter;

	private Boolean activated;

	private Boolean allowDamage;
	private Boolean allowDamagePVP;

	private Boolean allowMove;

	private Map<Player, Long> cooldownJump;
	private Map<Player, Long> cooldownShoot;

	private long endDate;
	private long refillDate;

	private long checkDate;

	private Boolean teams;

	public MatchActive(Match match, RandomEvents plugin, Boolean forzada) {
		super();
		this.mapHandler = new MatchMapDataHandler();
		this.allowDamage = false;
		this.allowDamagePVP = false;
		this.allowMove = true;
		this.match = match;
		this.maximo = 0;
		this.setTiempoPartida(match.getTiempoPartida());
		this.plugin = plugin;
		this.random = new Random();
		this.tournament = false;
		this.playerHandler = new MatchPlayerHandler();

		this.mobs = new ArrayList<Entity>();

		this.pets = new HashMap<String, Entity>();
		this.puntuacion = new HashMap<String, Integer>();
		switch (match.getMinigame()) {
		case BOMB_TAG:
			this.numeroSegRestantes = match.getSecondsMobSpawn().intValue();
			break;
		case BOAT_RUN:
		case HORSE_RUN:
		case RACE:
		case RED_GREEN_LIGHT:
		case ESCAPE_FROM_BEAST:
			getMapHandler().setCuboid(new Cuboid(match.getLocation1(), match.getLocation2()));
			break;
		case GEM_CRAWLER:
			this.numeroSegRestantes = plugin.getReventConfig().getNumberOfSecondsWithGems();
			break;
		default:
			this.numeroSegRestantes = 10;
			break;
		}
		if (match.getLocation1() != null && match.getLocation2() != null)
			getMapHandler().setCuboid(new Cuboid(match.getLocation1(), match.getLocation2()));
		this.limitPlayers = 1;
		this.playing = Boolean.FALSE;
		this.started = Boolean.FALSE;
		this.canBreak = Boolean.FALSE;
		this.activated = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		this.gema = new ItemStack(XMaterial.EMERALD.parseMaterial());
		this.setForzada(forzada);

		if (getMapHandler().getCuboid() != null) {
			getMapHandler().setActualCuboid(new Cuboid(
					new Location(getMapHandler().getCuboid().getWorld(), getMapHandler().getCuboid().getMaxX(),
							getMapHandler().getCuboid().getMaxY(), getMapHandler().getCuboid().getMaxZ()),
					new Location(getMapHandler().getCuboid().getWorld(), getMapHandler().getCuboid().getMinX(),
							getMapHandler().getCuboid().getMinY(), getMapHandler().getCuboid().getMinZ())));
		}
		tries = 0;
		damageCounter = 0;
		this.waterDrops = new ArrayList<WaterDropStepActive>();

		this.step = new HashMap<Player, Integer>();
		for (String s : match.getScenes()) {
			for (WaterDropStep wd : plugin.getWaterDrops()) {
				if (wd.getName().equals(s)) {
					waterDrops.add(new WaterDropStepActive(wd));
				}

			}
		}
		this.cooldownJump = new HashMap<Player, Long>();
		this.cooldownShoot = new HashMap<Player, Long>();
		counter = 0;
		teams = Boolean.FALSE;

		for (String cmd : plugin.getReventConfig().getCommandsOnEventFire()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);

		}

		matchWaitingPlayers();
	}

	public MatchActive(Match match, RandomEvents plugin, Boolean forzada, Boolean tournament,
			TournamentActive tournamentObj, List<String> players, List<Player> playersGanadores,
			List<Player> playersSpectators) {
		super();
		counter = 0;

		this.mapHandler = new MatchMapDataHandler();
		this.allowDamage = false;
		this.allowDamagePVP = false;
		this.allowMove = true;
		this.playerHandler = new MatchPlayerHandler(players, playersGanadores, playersSpectators);

		this.match = match;
		this.maximo = 0;
		this.setTiempoPartida(match.getTiempoPartida());
		this.plugin = plugin;
		this.random = new Random();

		this.tournament = tournament;
		this.tournamentObj = tournamentObj;
		damageCounter = 0;
		this.limitPlayers = tournamentObj.getRange().getTo() - 1;
		this.mobs = new ArrayList<Entity>();

		this.pets = new HashMap<String, Entity>();

		this.puntuacion = new HashMap<String, Integer>();
		switch (match.getMinigame()) {
		case BOMB_TAG:
			this.numeroSegRestantes = match.getSecondsMobSpawn().intValue();
			break;
		case BOAT_RUN:
		case HORSE_RUN:
		case RACE:
		case RED_GREEN_LIGHT:
		case ESCAPE_FROM_BEAST:
			getMapHandler().setCuboid(new Cuboid(match.getLocation1(), match.getLocation2()));
			break;
		case GEM_CRAWLER:
			this.numeroSegRestantes = plugin.getReventConfig().getNumberOfSecondsWithGems();
			break;
		default:
			this.numeroSegRestantes = 10;
			break;
		}
		this.playing = Boolean.FALSE;
		this.started = Boolean.FALSE;
		this.canBreak = Boolean.FALSE;
		this.activated = Boolean.FALSE;
		this.password = "" + random.nextInt(10000);
		this.firstAnnounce = Boolean.TRUE;
		this.gema = new ItemStack(XMaterial.EMERALD.parseMaterial());
		this.waterDrops = new ArrayList<WaterDropStepActive>();

		this.step = new HashMap<Player, Integer>();
		for (String s : match.getScenes()) {
			for (WaterDropStep wd : plugin.getWaterDrops()) {
				if (wd.getName().equals(s)) {
					waterDrops.add(new WaterDropStepActive(wd));
				}

			}
		}
		this.setForzada(forzada);

		if (getMapHandler().getCuboid() != null) {
			getMapHandler().setActualCuboid(new Cuboid(
					new Location(getMapHandler().getCuboid().getWorld(), getMapHandler().getCuboid().getMaxX(),
							getMapHandler().getCuboid().getMaxY(), getMapHandler().getCuboid().getMaxZ()),
					new Location(getMapHandler().getCuboid().getWorld(), getMapHandler().getCuboid().getMinX(),
							getMapHandler().getCuboid().getMinY(), getMapHandler().getCuboid().getMinZ())));
		}
		this.cooldownJump = new HashMap<Player, Long>();
		this.cooldownShoot = new HashMap<Player, Long>();
		teams = Boolean.FALSE;
		tries = 0;

	}

	public void uneAPlayer(Player player) {
		if (!getPlayerHandler().getPlayers().contains(player.getName())) {
			if (getPlayerHandler().getPlayers().size() < match.getAmountPlayers()) {
				if (!plugin.getReventConfig().isForceEmptyInventoryToJoin()) {
					// if (UtilsRandomEvents.checkLeatherItems(player)) {
					procesoUnirPlayer(player);
					// } else {
					// player.sendMessage(plugin.getLanguage().getTagPlugin() +
					// " "
					// + plugin.getLanguage().getDisposeLeatherItems());
					//
					// }
				} else {
					if (UtilsRandomEvents.checkInventoryVacio(player)) {
						procesoUnirPlayer(player);
					} else {
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getClearInventory());

					}
				}

			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getMatchFull());
			}

		}
	}

	public void uneAPlayerSpec(Player player) {
		if (!getPlayerHandler().getPlayersSpectators().contains(player)) {
			if (!plugin.getReventConfig().isForceEmptyInventoryToJoin()) {
				// if (UtilsRandomEvents.checkLeatherItems(player)) {
				procesoUnirPlayerSpec(player);
				// } else {
				// player.sendMessage(plugin.getLanguage().getTagPlugin() +
				// " "
				// + plugin.getLanguage().getDisposeLeatherItems());
				//
				// }
			} else {
				if (UtilsRandomEvents.checkInventoryVacio(player)) {
					procesoUnirPlayerSpec(player);
				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getClearInventory());

				}
			}

		}
	}

	private void procesoUnirPlayer(Player player) {
		if (UtilsRandomEvents.guardaInventario(plugin, player)) {
			hazComandosDeUnion(player);
			if (plugin.getReventConfig().isShowInfoMinigameOnJoin()) {
				mandaDescripcion(player);
			}
			UtilsRandomEvents.borraInventario(player, plugin);
			if (UtilsRandomEvents.teleportaPlayer(player, match.getPlayerSpawn(), plugin)) {

				getPlayerHandler().getPlayers().add(player.getName());
				getPlayerHandler().getPlayersObj().add(player);
				UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_BAT_HURT);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getMatchJoin().replace("%player%", player.getName()), false);
				getPlayerHandler().getPlayersSpectators().add(player);

				List<Kit> kits = UtilsRandomEvents.kitsAvailable(player, match.getKits(), plugin);
				if (kits.size() > 1) {

					player.getInventory().addItem(plugin.getReventConfig().getKitsItem());
					player.updateInventory();
				} else if (kits.size() == 1) {
					getPlayerHandler().getPlayerKits().put(player, kits.get(0));
				}

				if (match.getNumberOfTeams() != null && match.getNumberOfTeams() > 1) {

					player.getInventory().addItem(plugin.getReventConfig().getTeamItem());
					player.updateInventory();
				}

			} else {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}

		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getErrorSavingInventory());

		}

	}

	private void procesoUnirPlayerSpec(Player player) {
		Location loc = null;
		if (plugin.getReventConfig().isAdvancedSpectatorMode()) {
			if (!getPlayerHandler().getPlayersObj().isEmpty()) {
				loc = getPlayerHandler().getPlayersObj()
						.get(plugin.getRandom().nextInt(getPlayerHandler().getPlayersObj().size())).getLocation();

			}
		} else {
			if (!match.getSpectatorSpawns().isEmpty()) {
				loc = match.getSpectatorSpawns().get(plugin.getRandom().nextInt(match.getSpectatorSpawns().size()));

			}

		}
		if (loc != null) {
			if (UtilsRandomEvents.guardaInventario(plugin, player)) {
				UtilsRandomEvents.borraInventario(player, plugin);
				if (UtilsRandomEvents.teleportaPlayer(player, loc, plugin)) {

					hazComandosDeUnion(player);
					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getMatchJoin().replace("%player%", player.getName()), false);
					getPlayerHandler().getPlayersSpectators().add(player);
					if (plugin.getReventConfig().isAdvancedSpectatorMode()) {
						player.setGameMode(GameMode.SPECTATOR);
					}
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_BAT_HURT);
				} else {
					UtilsRandomEvents.sacaInventario(plugin, player);
				}

			} else {
				player.sendMessage(
						plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getErrorSavingInventory());

			}
		}
	}

	private void hazComandosDeUnion(Player player) {
		for (String cmd : plugin.getReventConfig().getCommandsOnUserJoin()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}
	}

	private void hazComandosDeSalir(Player player) {
		for (String cmd : plugin.getReventConfig().getCommandsOnUserLeave()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}
	}

	private void hazComandosDeComienzo(Player player) {
		for (String cmd : plugin.getReventConfig().getCommandsOnMatchBegin()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%player%", player.getName()));

		}
	}

	private void hazComandosDeFin() {
		for (String cmd : plugin.getReventConfig().getCommandsOnMatchEnd()) {

			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					cmd.replaceAll("%event%", this.getMatch().getName()));

		}
	}

	public void echaDePartida(Player player, Boolean comprueba, Boolean sacaInv, Boolean sacaSpectator) {
		echaDePartida(player, comprueba, sacaInv, sacaSpectator, true, false);
	}

	public void echaDePartida(Player player, Boolean comprueba, Boolean sacaInv, Boolean sacaSpectator,
			Boolean compruebaSpectator, Boolean forzado) {
		Location lastLocation = player.getLocation();
		getPlayerHandler().getLocationsBeforeExitting().add(lastLocation);
		dropItems(player, forzado);
		unregisterTeam(player);
		if (plugin.getReventConfig().getIsNoteBlockAPI())
			SongUtils.playRecord(player, false, plugin);
		if (plugin.getReventConfig().isRandomDisguisePlayers()) {
			UtilsDisguises.undisguisePlayer(player, plugin);
		}
		if (plugin.getReventConfig().isDebugMode()) {
			plugin.getLogger().info("Testing to kick from match: " + player.getName());

		}
		if (getPlayerHandler().getPlayersSpectators().contains(player)
				|| getPlayerHandler().getPlayers().contains(player.getName())) {
			if (plugin.getReventConfig().isDebugMode()) {
				plugin.getLogger().info("Kicking from match: " + player.getName());

			}
			if (playerHandler.getEnderpearlsFlying().containsKey(player)) {
				List<Entity> pearls = playerHandler.getEnderpearlsFlying().get(player);
				if (pearls != null) {
					for (Entity e : pearls) {
						if (e != null) {
							try {
								e.remove();
							} catch (Exception e1) {

							}
						}
					}
				}
			}
			if (comprueba) {

				getPlayerHandler().getPlayersObj().remove(player);
				getPlayerHandler().setPlayersObj(
						UtilsRandomEvents.borraPlayerPorName(getPlayerHandler().getPlayersObj(), player));

				getPlayerHandler().getPlayers().remove(player.getName());
				if (plugin.getReventConfig().isDebugMode()) {
					plugin.getLogger().info("Already kicked from match: " + player.getName());
					for (Player pl : getPlayerHandler().getPlayersObj()) {
						plugin.getLogger().info("    - " + pl.getName());
					}
				}

			}
			for (Player p : getPlayerHandler().getPlayersVanish()) {
				p.showPlayer(player);
			}

			if (compruebaSpectator
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getMatchLeave().replace("%player%", player.getName()), false);
				getPlayerHandler().getPlayersSpectators().remove(player);
				getPlayerHandler().setPlayersSpectators(
						UtilsRandomEvents.borraPlayerPorName(getPlayerHandler().getPlayersSpectators(), player));

			}
			UtilsRandomEvents.borraInventario(player, plugin);
			Boolean res = true;
			if (pets.containsKey(player)) {
				pets.get(player).remove();
				pets.remove(player);
			}
			if (comprueba) {
				Integer equipo = getEquipo(player);
				if (equipo != null) {
					echaDeEquipo(equipo, player);
				}
			}
			try {
				if (plugin.getReventConfig().isDebugMode()) {
					plugin.getLogger().info("Teleporting players.");
				}

				if (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty()) {
					UtilsRandomEvents.invinciblePlayer(player, plugin);
					res = UtilsRandomEvents.teleportaPlayer(player, plugin.getSpawn(), plugin, true);
				} else {
					Location l = null;
					Integer i = 0;
					while (l == null && i < 20) {
						l = match.getSpectatorSpawns().get(getRandom().nextInt(match.getSpectatorSpawns().size()));
						i++;
					}
					if (l != null) {
						UtilsRandomEvents.invinciblePlayer(player, plugin);
						UtilsRandomEvents.teleportaPlayer(player, l, plugin);
					}
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getLeaveCommand());
					if (plugin.getReventConfig().isAdvancedSpectatorMode()) {
						player.setGameMode(GameMode.SPECTATOR);
					}
				}
				player.setHealth(player.getMaxHealth());
				player.setFoodLevel(20);
				player.setFireTicks(0);
			} catch (Exception e) {
				if (player != null) {
					plugin.getLoggerP().info("[RandomEvents] The player " + player.getName() + " failed on teleport");
					if (plugin.getReventConfig().isDebugMode()) {
						plugin.getLoggerP().info("RandomEvents::DebugMode:: " + e);
					}
				}
			}

			if (sacaInv
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				hazComandosDeSalir(player);
				borraScoreboard(player);
				if (plugin.getReventConfig().isInventoryManagement() && !getMatch().getUseOwnInventory() && res)
					UtilsRandomEvents.sacaInventario(plugin, player);
			}
		}
		if (plugin.getReventConfig().isShowBorders() && (getMatch().getMinigame().equals(MinigameType.SG)
				|| getMatch().getMinigame().equals(MinigameType.TSG)
				|| getMatch().getMinigame().equals(MinigameType.TSG_REAL))) {
			UtilsRandomEvents.setWorldBorder(plugin,
					new Location(getMapHandler().getActualCuboid().getWorld(), 0, 0, 0), Double.MAX_VALUE, player);
		}
		updateScoreboards();
		if (player.getActivePotionEffects() != null) {
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}

		}
		UtilsRandomEvents.spawnParticles(Particle1711.valueOf(plugin.getReventConfig().getParticleDeath()), plugin,
				lastLocation);
		if (comprueba)
			compruebaPartida();

	}

	public void echaDePartida(List<Player> players, Boolean comprueba, Boolean sacaInv, Boolean sacaSpectator,
			Boolean compruebaSpectator) {

		if (comprueba) {
			if (!getPlayerHandler().getPlayersObj().remove(players)) {
				for (Player p : players) {
					getPlayerHandler()
							.setPlayersObj(UtilsRandomEvents.borraPlayerPorName(getPlayerHandler().getPlayersObj(), p));
				}
			}
			for (Player p : players) {
				getPlayerHandler().getLocationsBeforeExitting().add(p.getLocation());

				if (plugin.getReventConfig().isShowBorders() && (getMatch().getMinigame().equals(MinigameType.SG)
						|| getMatch().getMinigame().equals(MinigameType.TSG)
						|| getMatch().getMinigame().equals(MinigameType.TSG_REAL))) {
					UtilsRandomEvents.setWorldBorder(plugin,
							new Location(getMapHandler().getActualCuboid().getWorld(), 0, 0, 0), Double.MAX_VALUE, p);
				}
				getPlayerHandler().getPlayers().remove(p.getName());
				unregisterTeam(p);

			}
		}
		if (plugin.getReventConfig().getIsNoteBlockAPI())
			SongUtils.playRecord(players, false, plugin);
		for (Player player : players) {
			if (plugin.getReventConfig().isRandomDisguisePlayers()) {

				UtilsDisguises.undisguisePlayer(player, plugin);
			}
			for (Player pla : getPlayerHandler().getPlayersVanish()) {
				pla.showPlayer(player);
			}
			if (playerHandler.getEnderpearlsFlying().containsKey(player)) {
				List<Entity> pearls = playerHandler.getEnderpearlsFlying().get(player);
				for (Entity e : pearls) {
					if (e != null) {
						try {
							e.remove();
						} catch (Exception e1) {

						}
					}
				}
			}
			if (compruebaSpectator
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				if (!getPlayerHandler().getPlayersSpectators().remove(player)) {
					getPlayerHandler().setPlayersSpectators(
							UtilsRandomEvents.borraPlayerPorName(getPlayerHandler().getPlayersSpectators(), player));
				}
			}

			UtilsRandomEvents.borraInventario(player, plugin);
			if (pets.containsKey(player)) {
				pets.get(player).remove();
				pets.remove(player);
			}
			if (comprueba) {
				Integer equipo = getEquipo(player);
				if (equipo != null) {
					echaDeEquipo(equipo, player);
				}
			}
			if (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty()) {
				UtilsRandomEvents.invinciblePlayer(player, plugin);
				UtilsRandomEvents.teleportaPlayer(player, plugin.getSpawn(), plugin, true);
			} else {
				UtilsRandomEvents.teleportaPlayer(player,
						match.getSpectatorSpawns().get(getRandom().nextInt(match.getSpectatorSpawns().size())), plugin);
				player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getLeaveCommand());
				if (plugin.getReventConfig().isAdvancedSpectatorMode()) {
					player.setGameMode(GameMode.SPECTATOR);
				}

			}
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.setFireTicks(0);

			if (sacaInv
					&& (sacaSpectator || match.getSpectatorSpawns() == null || match.getSpectatorSpawns().isEmpty())) {
				hazComandosDeSalir(player);
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		}
		updateScoreboards();
		if (sacaSpectator) {
			for (Player p : players) {
				borraScoreboard(p);
			}
		}
		if (comprueba)
			compruebaPartida();
	}

	public void echaDeEquipo(Integer equipo, Player player) {
		getPlayerHandler().getEquipos().get(equipo).remove(player);
		if (getPlayerHandler().getEquipos().get(equipo).isEmpty()) {
			getPlayerHandler().getEquipos().remove(equipo);
		}

	}

	public Integer getEquipo(Player player) {
		Integer equipo = null;

		for (Integer numeroEquipo : getPlayerHandler().getEquipos().keySet()) {
			if (getPlayerHandler().getEquipos().get(numeroEquipo).contains(player)) {
				equipo = numeroEquipo;
			}
		}
		return equipo;

	}

	public Integer getEquipoCopy(Player player) {
		Integer equipo = null;

		for (Integer numeroEquipo : getPlayerHandler().getTeamsCopy().keySet()) {
			if (getPlayerHandler().getTeamsCopy().get(numeroEquipo).contains(player)) {
				equipo = numeroEquipo;
			}
		}
		return equipo;

	}

	public void compruebaPartida() {
		if (getPlaying()) {
			if (getPlayerHandler().getPlayersObj().isEmpty()) {
				if (getMatch().getMinigame() == MinigameType.RED_GREEN_LIGHT)
					UtilsCitizen.forceLastTurnAroundNPC(getMatch().getNPCId(), plugin);
				for (Entity entity : getMobs()) {
					entity.remove();
				}
				if (tournamentObj != null)
					tournamentObj.finalizaPartida(getCopiaP(getPlayerHandler().getPlayersObj()),
							getCopiaP(getPlayerHandler().getPlayersSpectators()), Boolean.FALSE, Boolean.FALSE);
				finalizaPartida(getPlayerHandler().getPlayersObj(), Boolean.FALSE, Boolean.FALSE);

			}
			if (getPlayerHandler().getPlayersObj().size() == 1
					&& getMatch().getMinigame() != MinigameType.RED_GREEN_LIGHT) {
				if (getTournament()) {
					tournamentObj.nextGame(getCopia(getPlayerHandler().getPlayers()),
							getCopiaP(getPlayerHandler().getPlayersObj()),
							getCopiaP(getPlayerHandler().getPlayersSpectators()));

				} else {
					daRecompensas(false);
				}
			} else if (teamsWithPlayers() == 1) {
				if (getTournament()) {
					tournamentObj.nextGame(getCopia(getPlayerHandler().getPlayers()),
							getCopiaP(getPlayerHandler().getPlayersObj()),
							getCopiaP(getPlayerHandler().getPlayersSpectators()));

				} else {
					daRecompensas(false);
				}
			} else if (getTournament() && (getPlayerHandler().getPlayersObj().size() <= limitPlayers

					|| getPlayerHandler().getPlayersGanadores().size() >= limitPlayers)) {
				if (limitPlayers == 1) {
					tournamentObj.nextGame(getCopia(getPlayerHandler().getPlayers()),
							getCopiaP(getPlayerHandler().getPlayersObj()),
							getCopiaP(getPlayerHandler().getPlayersSpectators()));
				} else {
					if (!getPlayerHandler().getPlayersGanadores().isEmpty()) {
						List<String> jugadores = new ArrayList<String>();
						for (Player p : getPlayerHandler().getPlayersGanadores()) {
							jugadores.add(p.getName());
						}
						tournamentObj.nextGame(getCopia(jugadores), getCopiaP(getPlayerHandler().getPlayersGanadores()),
								getCopiaP(getPlayerHandler().getPlayersSpectators()));
					} else {
						tournamentObj.nextGame(getCopia(getPlayerHandler().getPlayers()),
								getCopiaP(getPlayerHandler().getPlayersObj()),
								getCopiaP(getPlayerHandler().getPlayersSpectators()));

					}
					reiniciaValoresPartida();

				}
			} else if (!getTournament() && getPlayerHandler().getPlayersGanadores().size() >= limitPlayers) {
				daRecompensas(false);

			} else {
				if (getMatch().getMinigame().equals(MinigameType.ESCAPE_FROM_BEAST)
						&& !getPlayerHandler().getPlayersObj().contains(getPlayerHandler().getBeast())) {
					daRecompensas(false);
				}

				if (playerHandler.getEquipos().keySet().size() == 1) {
					if (getTournament()) {
						tournamentObj.nextGame(getCopia(getPlayerHandler().getPlayers()),
								getCopiaP(getPlayerHandler().getPlayersObj()),
								getCopiaP(getPlayerHandler().getPlayersSpectators()));

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

	public int teamsWithPlayers() {
		Integer i = 0;
		for (Entry<Integer, Set<Player>> entrada : getPlayerHandler().getEquipos().entrySet()) {
			if (entrada.getValue() != null && !entrada.getValue().isEmpty()) {
				i++;
			}
		}
		return i;
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
		if (maximo >= plugin.getReventConfig().getNumberOfGems()) {

			for (Player p : getPlayerHandler().getPlayersObj()) {
				if (getPlayerHandler().getPlayerContador() == null && getPuntuacion().containsKey(p.getName())
						&& getPuntuacion().get(p.getName()) == maximo) {
					getPlayerHandler().setPlayerContador(p);
					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getPlayerWinning().replace("%player%", p.getName()), true);
					setTimerPassword(getRandom().nextInt(100000));
				}
			}
			empiezaTemporizadorGemas(getTimerPassword());
		}
	}

	private void empiezaTemporizadorGemas(Integer timerPass) {
		if (getPlaying() && getPlayerHandler().getPlayerContador() != null && getTimerPassword() != null
				&& getTimerPassword().equals(timerPass)) {
			if (numeroSegRestantes == 0) {
				if (getTournament()) {
					getPlayerHandler().getPlayersGanadores().add(getPlayerHandler().getPlayerContador());
					tournamentObj.nextGame(getCopia(getPlayerHandler().getPlayers()),
							getCopiaP(getPlayerHandler().getPlayersGanadores()),
							getCopiaP(getPlayerHandler().getPlayersSpectators()));

				} else {
					daRecompensas(false);
				}
			} else {
				Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
					public void run() {
						numeroSegRestantes--;
						if (numeroSegRestantes > 0 && getTimerPassword() != null && getTimerPassword().equals(timerPass)
								&& getPlayerHandler().getPlayerContador() != null)
							UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
									plugin.getLanguage().getPlayerWinningSeconds()
											.replace("%seconds%", numeroSegRestantes.toString()).replace("%player%",
													getPlayerHandler().getPlayerContador().getName()),
									true);

						empiezaTemporizadorGemas(timerPass);

					}
				}, 20);
			}
		}
	}

	public void dejarPartida(Player player, Boolean muerto) {
		if (!getPlaying()) {
			if (!getPlayerHandler().getPlayersObj().remove(player)) {
				getPlayerHandler().setPlayersObj(
						UtilsRandomEvents.borraPlayerPorName(getPlayerHandler().getPlayersObj(), player));
			}

			if (!getPlayerHandler().getPlayersSpectators().remove(player)) {
				getPlayerHandler().setPlayersSpectators(
						UtilsRandomEvents.borraPlayerPorName(getPlayerHandler().getPlayersSpectators(), player));
			}

			getPlayerHandler().getPlayers().remove(player.getName());

			UtilsRandomEvents.borraInventario(player, plugin);

			UtilsRandomEvents.teleportaPlayer(player, plugin.getSpawn(), plugin, true);

			if (!muerto) {
				UtilsRandomEvents.sacaInventario(plugin, player);
			}
		} else {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getAlreadyPlayingMatch());
		}
	}

	public void daRecompensas(Boolean tiempo) {
		setPlaying(Boolean.FALSE);
		UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
				XSound.ENTITY_ENDER_DRAGON_DEATH);
		List<Player> ganadores = new ArrayList<Player>();
		switch (getMatch().getMinigame()) {
		case TSW:
		case TSW_REAL:
		case TSG_REAL:
		case TSG:
		case BATTLE_ROYALE_TEAMS:
		case BATTLE_ROYALE_TEAM_2:
		case PAINTBALL:
			if (getPlayerHandler().getPlayersObj().size() > 0) {
				Player p = getPlayerHandler().getPlayersObj().get(0);
				Integer equipo = getEquipo(p);
				if (equipo != null && getPlayerHandler().getTeamsCopy().containsKey(equipo)) {
					ganadores.addAll(getPlayerHandler().getTeamsCopy().get(equipo));

				} else {
					ganadores.addAll(getPlayerHandler().getPlayersObj());

				}
			}
			break;
		case BATTLE_ROYALE:
		case SG:
		case SW:
		case KNOCKBACK_DUEL:
		case BATTLE_ROYALE_CABALLO:
		case ESCAPE_ARROW:
		case ANVIL_SPLEEF:
		case BOMBARDMENT:
		case BOMB_TAG:
		case TNT_RUN:
		case SPLEEF:
		case SPLEGG:
			ganadores.addAll(getPlayerHandler().getPlayersObj());
			break;
		case TOP_KILLER:
		case KOTH:
		case FISH_SLAP:
		case QUAKECRAFT:
		case OITC:
		case TOP_KILLER_TEAM_2:
		case TOP_KILLER_TEAMS:
		case PAINTBALL_TOP_KILL:
		case HOEHOEHOE:
		case SPLATOON:
			ganadores.addAll(sacaGanadoresPartidaTiempo());
			break;
		case GEM_CRAWLER:
			if (getPlayerHandler().getPlayersObj().size() == 1) {
				ganadores.addAll(getPlayerHandler().getPlayersObj());

			} else {
				ganadores.add(getPlayerHandler().getPlayerContador());
			}
			break;
		case BOAT_RUN:
		case HORSE_RUN:
		case RACE:
		case RED_GREEN_LIGHT:
		case WDROP:
			if (getPlayerHandler().getPlayerContador() != null) {
				ganadores.add(getPlayerHandler().getPlayerContador());
			}
			break;
		case ESCAPE_FROM_BEAST:
			if (getPlayerHandler().getPlayerContador() != null
					&& getPlayerHandler().getPlayers().contains(getPlayerHandler().getPlayerContador().getName()))
				ganadores.add(getPlayerHandler().getPlayerContador());
			break;
		}
		if (getMobs() != null) {
			for (Entity m : getMobs()) {
				if (m != null && m.getPassenger() != null) {
					m.getPassenger().eject();
					m.remove();
				}
			}
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
								.replace("%type%", match.getMinigame().getMessage(plugin))
								.replace("%players%", cadenaGanadores).replace("%event%", match.getName()));

			} else {
				play.sendMessage(plugin.getLanguage().getTagPlugin()
						+ plugin.getLanguage().getWinners().replace("%type%", match.getMinigame().getMessage(plugin))
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
		if (getPlayerHandler().getEquipos() == null || getPlayerHandler().getEquipos().isEmpty()) {
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
				for (Player p : getPlayerHandler().getPlayersObj()) {
					if (gana.contains(p.getName())) {
						winners.add(p);
					}
				}

			} else {
				for (Player p : getPlayerHandler().getPlayersObj()) {
					if (puntuacion.containsKey(p.getName()) && puntuacion.get(p.getName()).equals(maximo)) {
						if (!plugin.getReventConfig().isDisableMultipleWinners() || winners.isEmpty()) {
							winners.add(p);
						}
					}
				}
			}
		} else {
			Map<Integer, Integer> puntuacionesEquipo = createTeamPoints();

			maximo = 0;

			if (getTournament()) {
				puntuacionesEquipo = UtilsRandomEvents.sortByValue(puntuacionesEquipo);
				for (Integer s : puntuacionesEquipo.keySet()) {
					if (winners.size() < limitPlayers) {
						winners.addAll(getPlayerHandler().getEquipos().get(s));
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
						if (!plugin.getReventConfig().isDisableMultipleWinners() || winners.isEmpty()) {
							winners.addAll(getPlayerHandler().getEquipos().get(equipo));
						}
					}
				}
			}
		}

		return winners;
	}

	public Map<Integer, Integer> createTeamPoints() {
		Map<Integer, Integer> puntuacionesEquipo = new HashMap<Integer, Integer>();

		for (Integer e : getPlayerHandler().getEquipos().keySet()) {
			Set<Player> players = getPlayerHandler().getEquipos().get(e);
			Integer puntuacionEquipo = 0;
			for (Player p : players) {

				if (puntuacion.containsKey(p.getName())) {
					puntuacionEquipo += puntuacion.get(p.getName());
				}
			}
			puntuacionesEquipo.put(e, puntuacionEquipo);
		}
		return puntuacionesEquipo;
	}

	public void finalizaPartida(List<Player> ganadores, Boolean abrupto, Boolean cancelled) {
		setPlaying(Boolean.FALSE);
		setCanBreak(Boolean.FALSE);
		if (tournamentObj == null) {

			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				try {
					List<Entity> entities = p.getNearbyEntities(
							plugin.getReventConfig().getDistanceClearEntities() * 1.0,
							plugin.getReventConfig().getDistanceClearEntities() * 1.0,
							plugin.getReventConfig().getDistanceClearEntities() * 1.0);
					if (entities != null) {
						for (Entity e : entities) {
							if (e != null && (e instanceof Horse || e instanceof Boat)
									|| (getPlugin().getApi().isCraftBoat(e) || getPlugin().getApi().isCraftEnderPearl(e)
											|| getPlugin().getApi().isCraftHorse(e))) {
								e.remove();
							}
						}
					}
				} catch (Exception e) {

				}
				echaDePartida(p, false, true, true, false, false);
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
			if (getPlayerHandler().getPlayerContador() != null) {
				UtilsRandomEvents.removeGlow(plugin, getPlayerHandler().getPlayerContador(),
						getPlayerHandler().getPlayersTotalObj());
			}
			if (cancelled) {
				List<Player> playersOnline = new ArrayList<Player>();
				playersOnline.addAll(Bukkit.getOnlinePlayers());
				UtilsRandomEvents.mandaMensaje(plugin, playersOnline,
						plugin.getLanguage().getEventCancelled().replaceAll("%event%", match.getName())
								.replaceAll("%type%", match.getMinigame().getMessage(plugin)),
						true);

			} else if (abrupto) {
				List<Player> playersOnline = new ArrayList<Player>();
				playersOnline.addAll(Bukkit.getOnlinePlayers());
				UtilsRandomEvents.mandaMensaje(plugin, playersOnline,
						plugin.getLanguage().getEventStopped().replaceAll("%event%", match.getName())
								.replaceAll("%type%", match.getMinigame().getMessage(plugin)),
						true);

			}
			try {
				Bukkit.getPluginManager().callEvent(new ReventEndEvent(this, ganadores));
			} catch (Exception e) {
				plugin.getLoggerP().info("[RandomEvents] WARN :: Couldnt fire the ReventEndEvent.");
			}
			reiniciaValoresPartida();
		}
	}

	public void reiniciaValoresPartida() {
		reiniciaValoresPartida(true);
	}

	public void unregisterTeam(Player p) {
		if (teams) {
			if (plugin.getNametagHook() == null) {
				if (plugin.getColorBoard().getTeam(p.getName()) != null) {
					plugin.getColorBoard().getTeam(p.getName()).unregister();
				}
			} else {
				// getPlayerHandler().getPlayersPrefix().put(p,
				// plugin.getNametagHook().getApi().getNametag(p).getPrefix());
				plugin.getNametagHook().getApi().setPrefix(p, getPlayerHandler().getPlayersPrefix().get(p.getName()));

			}
		}
	}

	public void reiniciaValoresPartida(Boolean reinicia) {
		try {
			if (task != null) {

				task.cancel();
			}
			if (task2 != null) {
				task2.cancel();
			}
			if (task3 != null) {
				task3.cancel();
			}
		} catch (Exception e) {
		}
		switch (match.getMinigame()) {
		case SG:
		case SW:
		case TSG:
		case TSW:
		case TSG_REAL:
		case TSW_REAL:
		case GEM_CRAWLER:
			if (getMapHandler() != null && getMapHandler().getCuboid() != null) {
				Location center = getMapHandler().getCuboid().getCenter();
				if (center != null) {

					Collection<Entity> entities = center.getWorld().getNearbyEntities(center,
							center.distance(match.getLocation1()), 400, center.distance(match.getLocation1()));
					for (Entity e : entities) {
						if (e != null) {
							if ((e instanceof EnderPearl || e instanceof Item || e instanceof Boat || e instanceof Horse
									|| getPlugin().getApi().isCraftBoat(e) || getPlugin().getApi().isCraftEnderPearl(e)
									|| getPlugin().getApi().isCraftHorse(e) || getPlugin().getApi().isCraftItem(e))) {
								e.remove();
							}
						}
					}

				}
			}
			for (Location p : getPlayerHandler().getLocationsBeforeExitting()) {
				try {
					Collection<Entity> entities = p.getWorld().getNearbyEntities(p,
							plugin.getReventConfig().getDistanceClearEntities() * 1.0,
							plugin.getReventConfig().getDistanceClearEntities() * 1.0,
							plugin.getReventConfig().getDistanceClearEntities() * 1.0);
					if (entities != null) {
						for (Entity e : entities) {
							if (e != null && (e instanceof EnderPearl || e instanceof Item
									|| getPlugin().getApi().isCraftBoat(e) || getPlugin().getApi().isCraftEnderPearl(e)
									|| getPlugin().getApi().isCraftHorse(e) || getPlugin().getApi().isCraftItem(e))) {
								e.remove();
							}
						}
					}
				} catch (Exception e) {

				}

			}
			break;
		case RED_GREEN_LIGHT:
			UtilsCitizen.forceLastTurnAroundNPC(getMatch().getNPCId(), plugin);

		default:
			break;
		}

		for (Entry<Location, MaterialData> entrada : getMapHandler().getBlockDisappeared().entrySet()) {
			plugin.getApi().convertBlock(entrada.getKey(), entrada.getValue());
		}

		for (Entry<Location, Material> entrada : getMapHandler().getBlockDisappearedType().entrySet()) {
			entrada.getKey().getBlock().setType(entrada.getValue());
		}

		hazComandosDeFin();
		for (Entity ent : getMobs()) {
			if (ent != null) {
				ent.remove();
			}
		}
		List<FastBoard> listaRecorrer = new ArrayList<FastBoard>();
		listaRecorrer.addAll(getPlayerHandler().getScoreboards().values());
		for (FastBoard f : listaRecorrer) {
			Player p = f.getPlayer();
			borraScoreboard(p);

		}

		for (Location chest : getMapHandler().getChests()) {
			if (chest.getBlock() != null && chest.getBlock().getType() == XMaterial.CHEST.parseMaterial()) {
				if (chest.getBlock().getState() instanceof Chest) {
					Chest c = (Chest) chest.getBlock().getState();
					c.getBlockInventory().clear();
				}
			} else {
				chest.getBlock().setType(XMaterial.CHEST.parseMaterial());
			}
		}
		this.getPlayerHandler().getPlayers().clear();
		this.getPlayerHandler().getPlayersObj().clear();
		this.getPlayerHandler().getPlayersGanadores().clear();
		this.getPlayerHandler().getPlayersSpectators().clear();
		this.activated = Boolean.FALSE;

		for (Location l : getMapHandler().getBlockPlaced().keySet()) {
			l.getBlock().setType(XMaterial.AIR.parseMaterial());
		}

		setPlaying(Boolean.FALSE);
		if (tournamentObj == null && reinicia)
			plugin.reiniciaPartida(forzada);
	}

	public void matchBegin() {

		// for (Player player : getPlayersVivos()) {
		// UtilsStats.aumentaStats(player.getName(), getMatch().getName(),
		// StatsEnum.PARTIDAS_JUGADAS, plugin);
		// }
		if (!plugin.getReventConfig().isShowInfoMinigameOnJoin()) {
			mandaDescripcion();
		}
		try {
			Bukkit.getPluginManager().callEvent(new ReventBeginEvent(this));
		} catch (Exception e) {
			plugin.getLoggerP().info("[RandomEvents] WARN :: Couldnt fire the ReventBeginEvent.");
		}
		cuentaAtras(Boolean.TRUE);
		if (plugin.getReventConfig().isRandomDisguisePlayers()) {
			for (Player p : getPlayerHandler().getPlayersObj()) {
				UtilsDisguises.disguisePlayer(p, plugin);
			}
		}
	}

	private void mandaDescripcion() {
		List<String> desc = getField("minigameDescription" + match.getMinigame().getCodigo());
		Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

		for (Player p : getPlayerHandler().getPlayersSpectators()) {
			for (String s : desc) {
				try {
					Matcher match = pattern.matcher(s);
					Map<String, ChatColor> mapa = new HashMap<String, ChatColor>();
					while (match.find()) {
						String color = s.substring(match.start() + 1, match.end());
						Method method = ChatColor.class.getMethod("of", String.class);
						ChatColor chatc = (ChatColor) method.invoke(null, color);
						mapa.put("&" + color, chatc);
					}
					for (Entry<String, ChatColor> ent : mapa.entrySet()) {
						s = s.replaceAll(ent.getKey(), ent.getValue() + "");
					}
					s = ChatColor.translateAlternateColorCodes('&', s);
				} catch (Exception e) {
					s = s.replaceAll("&", "§");
				}
				p.sendMessage(s);
			}
		}

	}

	private void mandaDescripcion(Player p) {
		List<String> desc = getField("minigameDescription" + match.getMinigame().getCodigo());
		Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
		for (String s : desc) {

			try {
				Matcher match = pattern.matcher(s);
				Map<String, ChatColor> mapa = new HashMap<String, ChatColor>();
				while (match.find()) {
					String color = s.substring(match.start() + 1, match.end());
					Method method = ChatColor.class.getMethod("of", String.class);
					ChatColor chatc = (ChatColor) method.invoke(null, color);
					mapa.put("&" + color, chatc);
				}
				for (Entry<String, ChatColor> ent : mapa.entrySet()) {
					s = s.replaceAll(ent.getKey(), ent.getValue() + "");
				}
				s = ChatColor.translateAlternateColorCodes('&', s);

			} catch (Exception e) {
				s = s.replaceAll("&", "§");
			}
			p.sendMessage(s);
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

		UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
				plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getEventAnnounce()
						.replace("%event%", match.getName()).replace("%type%", match.getMinigame().getMessage(plugin)),
				Boolean.FALSE);

		UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(), XSound.ENTITY_PLAYER_LEVELUP);
		UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
				plugin.getLanguage().getSecondsRemaining3(), Boolean.FALSE, true, true);
		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
						XSound.ENTITY_PLAYER_LEVELUP);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getSecondsRemaining2(), Boolean.FALSE, true, true);
			}
		}, 20 * 1L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
						XSound.ENTITY_PLAYER_LEVELUP);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getSecondsRemaining1(), Boolean.FALSE, true, true);
			}
		}, 20 * 2L);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (playSound) {
					UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
							XSound.ENTITY_ENDER_DRAGON_GROWL);
				}
				comienzaPartida();
				// spawnMobs(bWave.getMobs(), getPlugin());
			}

		}, 20 * 3L);

	}

	public void comienzaPartida() {
		setPlaying(Boolean.TRUE);
		setStarted(Boolean.TRUE);
		getPlayerHandler().getPlayersTotal().clear();
		getPlayerHandler().getPlayersTotal().addAll(getPlayerHandler().getPlayers());
		getPlayerHandler().getPlayersTotalObj().clear();
		getPlayerHandler().getPlayersTotalObj().addAll(getPlayerHandler().getPlayersObj());
		hazComandosDeStart(null);

		for (Player p : getPlayerHandler().getPlayersObj()) {
			hazComandosDeComienzo(p);
			hazComandosDeStart(p);
			puntuacion.put(p.getName(), 0);

		}
		Double jumpStrength = null;
		Double speed = null;
		switch (match.getMinigame()) {
		case SG:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

			}

			doMoveAndWarmup();

			if (plugin.getReventConfig().isShowBorders()) {
				Double distance = Math.min(
						(getMapHandler().getActualCuboid().getMaxX() - getMapHandler().getActualCuboid().getMinX())
								/ 1.,
						(getMapHandler().getActualCuboid().getMaxZ() - getMapHandler().getActualCuboid().getMinZ())
								/ 1.);
				for (Player p : getPlayerHandler().getPlayersObj()) {

					UtilsRandomEvents.setWorldBorder(getPlugin(), getMapHandler().getActualCuboid().getCenter(),
							distance, p);
				}

			}
			partidaSG();
			break;
		case SW:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

			}
			doMoveAndWarmup();

			partidaSW();

			break;
		case BATTLE_ROYALE:
		case KNOCKBACK_DUEL:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

			}

			this.allowDamage = false;
			this.allowDamagePVP = false;

			UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
					plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "" + 5), true);

			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "3"), true);
				}
			}, 20 * (2));
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "2"), true);
				}
			}, 20 * (3));
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "1"), true);
				}
			}, 20 * (4));

			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

				public void run() {
					setAllowDamage(true);
					setAllowDamagePVP(true);
				}

			}, 20 * 5);

			task = new BukkitRunnable() {
				public void run() {

					UtilsRandomEvents.checkDamageCounter(plugin, getMatchActive());
				}
			};
			task.runTaskTimer(plugin, 100L, 20L);

			break;
		case TNT_RUN:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {

				iniciaPlayer(p);

			}
			if (plugin.getReventConfig().getWarmupTimeTNTRUN() > 2) {
				Bukkit.getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
					@Override
					public void run() {
						UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
								XSound.ENTITY_PLAYER_LEVELUP);
						UtilsRandomEvents.mandaMensaje(getPlugin(), getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getSecondsRemaining3(), Boolean.FALSE, true, true);
					}
				}, 20 * plugin.getReventConfig().getWarmupTimeTNTRUN() - 60);
			}
			if (plugin.getReventConfig().getWarmupTimeTNTRUN() > 1) {
				Bukkit.getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
					@Override
					public void run() {
						UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
								XSound.ENTITY_PLAYER_LEVELUP);
						UtilsRandomEvents.mandaMensaje(getPlugin(), getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getSecondsRemaining2(), Boolean.FALSE, true, true);
					}
				}, 20 * plugin.getReventConfig().getWarmupTimeTNTRUN() - 40);
			}
			if (plugin.getReventConfig().getWarmupTimeTNTRUN() > 0) {
				Bukkit.getServer().getScheduler().runTaskLater(getPlugin(), new Runnable() {
					@Override
					public void run() {
						UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
								XSound.ENTITY_PLAYER_LEVELUP);
						UtilsRandomEvents.mandaMensaje(getPlugin(), getPlayerHandler().getPlayersSpectators(),
								plugin.getLanguage().getSecondsRemaining1(), Boolean.FALSE, true, true);
					}
				}, 20 * plugin.getReventConfig().getWarmupTimeTNTRUN() - 20);
			}

			task = new BukkitRunnable() {
				public void run() {
					if (!activated) {
						for (Player pl : getPlayerHandler().getPlayersObj()) {
							UtilsRandomEvents.queueTNT(plugin, plugin.getMatchActive(), pl.getLocation(), 0.01, true);
						}
					}
					setActivated(true);
					UtilsRandomEvents.checkBlocksDisappear(plugin, getMatchActive(), new Date());
				}
			};
			task.runTaskTimer(plugin, plugin.getReventConfig().getWarmupTimeTNTRUN() != -1
					? 20 * plugin.getReventConfig().getWarmupTimeTNTRUN() : 0, 1L);
			break;
		case WDROP:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = false;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				getStep().put(p, 0);
				iniciaPlayer(p);
				p.getInventory().addItem(plugin.getReventConfig().getVanishItem());
				p.updateInventory();
			}

			setActivated(true);

			break;
		case SPLEEF:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

			}
			PotionEffect pot = new PotionEffect(PotionEffectType.FAST_DIGGING, 240, 99);
			UtilsRandomEvents.applyPotionEffects(pot, getPlayerHandler().getPlayersObj());

			task = new BukkitRunnable() {
				public void run() {

					UtilsRandomEvents.applyPotionEffects(pot, getPlayerHandler().getPlayersObj());
				}
			};
			task.runTaskTimer(plugin, 0, 120L);
			if (plugin.getReventConfig().isActivateIdleDamageSpleef()) {
				task2 = new BukkitRunnable() {
					public void run() {

						UtilsRandomEvents.checkDamageCounter(plugin, getMatchActive());
					}
				};
				task2.runTaskTimer(plugin, 100L, 20L);
			}
			break;
		case SPLEGG:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			ItemStack item = new ItemStack(XMaterial.STONE_HOE.parseMaterial());
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

				p.getInventory().setItem(0, item);
				p.updateInventory();

			}

			break;
		case ESCAPE_FROM_BEAST:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			getPlayerHandler().setBeast(getPlayerHandler().getPlayersObj()
					.get(plugin.getRandom().nextInt(getPlayerHandler().getPlayersObj().size())));
			getPlayerHandler().setPlayerContador(getPlayerHandler().getBeast());
			for (Player p : getPlayerHandler().getPlayersObj()) {
				if (p.getName().equals(getPlayerHandler().getBeast().getName())) {
					iniciaPlayerBeast(p);

				} else {
					iniciaPlayer(p);
				}

			}

			break;
		case QUAKECRAFT:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
				if (plugin.getReventConfig().isQuakeGiveDefaultWeapon()) {
					p.getInventory().addItem(XMaterial.STONE_HOE.parseItem());
					p.updateInventory();
				}
			}
			partidaPorTiempo();
			checkCooldowns();

			break;
		case TOP_KILLER:
		case OITC:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
			}
			partidaPorTiempo();

			break;
		case KOTH:
		case FISH_SLAP:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			this.allowMove = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
			}
			partidaPorTiempo();

			break;

		case TOP_KILLER_TEAM_2:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				Integer indice = getPlayerHandler().getPlayersObj().indexOf(p);

				iniciaPlayer(p);
				if (indice % 2 == 0) {
					Set<Player> players = new HashSet<Player>();
					players.add(p);
					getPlayerHandler().getEquipos().put(indice / 2, players);
				} else {
					getPlayerHandler().getEquipos().get((indice - 1) / 2).add(p);
				}
			}

			mandaMensajesEquipo(getPlayerHandler().getEquipos());

			partidaPorTiempo();
			break;

		case TOP_KILLER_TEAMS:
		case PAINTBALL_TOP_KILL:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				sortTeams(p);
			}
			equilibrateTeams();
			for (Player p : getPlayerHandler().getPlayersObj()) {

				iniciaPlayerTeam(p);

			}

			mandaMensajesEquipo(getPlayerHandler().getEquipos());

			partidaPorTiempo();
			break;
		case TSG:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				Integer indice = getPlayerHandler().getPlayersObj().indexOf(p);
				iniciaPlayer(p);

				if (indice % 2 == 0) {
					Set<Player> players = new HashSet<Player>();
					players.add(p);
					getPlayerHandler().getEquipos().put(indice / 2, players);
				} else {
					getPlayerHandler().getEquipos().get((indice - 1) / 2).add(p);
				}

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			doMoveAndWarmup();
			if (plugin.getReventConfig().isShowBorders()) {
				Double distance = Math.min(
						(getMapHandler().getActualCuboid().getMaxX() - getMapHandler().getActualCuboid().getMinX())
								/ 1.,
						(getMapHandler().getActualCuboid().getMaxZ() - getMapHandler().getActualCuboid().getMinZ())
								/ 1.);
				for (Player p : getPlayerHandler().getPlayersObj()) {
					UtilsRandomEvents.setWorldBorder(getPlugin(), getMapHandler().getActualCuboid().getCenter(),
							distance, p);
				}

			}
			partidaSG();
			break;
		case TSG_REAL:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				sortTeams(p);
			}
			equilibrateTeams();
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayerTeam(p);
			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			doMoveAndWarmup();
			if (plugin.getReventConfig().isShowBorders()) {
				Double distance = Math.min(
						(getMapHandler().getActualCuboid().getMaxX() - getMapHandler().getActualCuboid().getMinX())
								/ 1.,
						(getMapHandler().getActualCuboid().getMaxZ() - getMapHandler().getActualCuboid().getMinZ())
								/ 1.);
				for (Player p : getPlayerHandler().getPlayersObj()) {
					UtilsRandomEvents.setWorldBorder(getPlugin(), getMapHandler().getActualCuboid().getCenter(),
							distance, p);
				}

			}
			partidaSG();
			break;
		case TSW_REAL:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				sortTeams(p);
			}
			equilibrateTeams();
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayerTeam(p);

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			doMoveAndWarmup();

			break;
		case TSW:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				Integer indice = getPlayerHandler().getPlayersObj().indexOf(p);
				iniciaPlayer(p);

				if (indice % 2 == 0) {
					Set<Player> players = new HashSet<Player>();
					players.add(p);
					getPlayerHandler().getEquipos().put(indice / 2, players);
				} else {
					getPlayerHandler().getEquipos().get((indice - 1) / 2).add(p);
				}

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			doMoveAndWarmup();

			break;
		case PAINTBALL:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				sortTeams(p);
			}

			equilibrateTeams();
			for (Player p : getPlayerHandler().getPlayersObj()) {

				iniciaPlayerTeam(p);

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());

			break;
		case HOEHOEHOE:
		case SPLATOON:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;

			for (Player p : getPlayerHandler().getPlayersObj()) {
				sortTeams(p);
			}
			equilibrateTeams();

			for (Player p : getPlayerHandler().getPlayersObj()) {

				iniciaPlayerTeam(p);
				if (plugin.getReventConfig().isPaintGiveDefaultWeapon()) {
					p.getInventory().addItem(XMaterial.STONE_HOE.parseItem());
					p.updateInventory();
				}

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			partidaPorTiempo();

			break;
		case BATTLE_ROYALE_TEAMS:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				sortTeams(p);
			}
			equilibrateTeams();
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayerTeam(p);

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			task = new BukkitRunnable() {
				public void run() {

					UtilsRandomEvents.checkDamageCounter(plugin, getMatchActive());
				}
			};
			task.runTaskTimer(plugin, 0, 20L);
			break;
		case BATTLE_ROYALE_TEAM_2:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				Integer indice = getPlayerHandler().getPlayersObj().indexOf(p);
				iniciaPlayer(p);

				if (indice % 2 == 0) {
					Set<Player> players = new HashSet<Player>();
					players.add(p);
					getPlayerHandler().getEquipos().put(indice / 2, players);
				} else {
					getPlayerHandler().getEquipos().get((indice - 1) / 2).add(p);
				}

			}
			mandaMensajesEquipo(getPlayerHandler().getEquipos());
			task = new BukkitRunnable() {
				public void run() {

					UtilsRandomEvents.checkDamageCounter(plugin, getMatchActive());
				}
			};
			task.runTaskTimer(plugin, 0, 20L);
			break;
		case BATTLE_ROYALE_CABALLO:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;

			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

				Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE); // Spawns

				if (jumpStrength == null) {
					jumpStrength = horse.getJumpStrength();
				} else {
					horse.setJumpStrength(jumpStrength);
				}
				if (speed == null) {
					speed = plugin.getApi().getHorseSpeed(horse);
				} else {
					plugin.getApi().setHorseSpeed(horse, speed);
				}

				horse.getInventory().setSaddle(new ItemStack(XMaterial.SADDLE.parseMaterial(), 1)); // Gives
				// horse
				// saddle
				horse.setTamed(true); // Sets horse to tamed
				horse.setOwner(p); // Makes the horse the players
				horse.setPassenger(p);

				getMobs().add(horse);
				getPets().put(p.getName(), horse);

				horse.setPassenger(p);

			}
			break;
		case HORSE_RUN:

			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

				if (getMatch().getEntitySpawns() == null || getMatch().getEntitySpawns().isEmpty()) {
					Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE); // Spawns
					if (jumpStrength == null) {
						jumpStrength = horse.getJumpStrength();
					} else {
						horse.setJumpStrength(jumpStrength);
					}
					if (speed == null) {
						speed = plugin.getApi().getHorseSpeed(horse);
					} else {
						plugin.getApi().setHorseSpeed(horse, speed);
					}
					// the
					// horse
					horse.getInventory().setSaddle(new ItemStack(XMaterial.SADDLE.parseMaterial(), 1)); // Gives
					// horse
					// saddle
					horse.setTamed(true); // Sets horse to tamed
					horse.setOwner(p); // Makes the horse the players
					horse.setPassenger(p);

					getMobs().add(horse);
					getPets().put(p.getName(), horse);

					horse.setPassenger(p);

				} else {
					Horse horse = (Horse) p.getWorld().spawnEntity(
							match.getEntitySpawns().get(getPlayerHandler().getPlayersObj().indexOf(p)),
							EntityType.HORSE); // Spawns
					if (jumpStrength == null) {
						jumpStrength = horse.getJumpStrength();
					} else {
						horse.setJumpStrength(jumpStrength);
					}
					if (speed == null) {
						speed = plugin.getApi().getHorseSpeed(horse);
					} else {
						plugin.getApi().setHorseSpeed(horse, speed);
					}
					// the
					// horse
					horse.getInventory().setSaddle(new ItemStack(XMaterial.SADDLE.parseMaterial(), 1)); // Gives
					// horse
					// saddle
					horse.setTamed(true); // Sets horse to tamed
					horse.setOwner(p); // Makes the horse the players

					getMobs().add(horse);
					getPets().put(p.getName(), horse);

				}
				p.getInventory().addItem(plugin.getReventConfig().getVanishItem());
				p.updateInventory();
			}
			break;
		case BOAT_RUN:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);

				Boat boat = null;
				if (getMatch().getEntitySpawns() == null || getMatch().getEntitySpawns().isEmpty()) {
					boat = (Boat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BOAT);
					boat.setPassenger(p);

				} else {
					boat = (Boat) p.getWorld().spawnEntity(
							match.getEntitySpawns().get(getPlayerHandler().getPlayersObj().indexOf(p)),
							EntityType.BOAT);
				}

				getMobs().add(boat);
				getPets().put(p.getName(), boat);
				p.getInventory().addItem(plugin.getReventConfig().getVanishItem());
				p.updateInventory();
			}
			break;
		case RACE:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}

			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
				p.getInventory().addItem(plugin.getReventConfig().getCheckpointItem());
				p.getInventory().addItem(plugin.getReventConfig().getVanishItem());
				p.updateInventory();

			}
			break;
		case RED_GREEN_LIGHT:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}

			this.allowDamage = false;
			this.allowDamagePVP = false;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
				p.updateInventory();

			}
			if (plugin.getReventConfig().getIsNoteBlockAPI())
				SongUtils.playRecord(getPlayerHandler().getPlayersObj(), true, plugin);

			partidaRedGreen(0);

			break;
		case ESCAPE_ARROW:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
			}
			partidaEscapeArrow();
			break;
		case ANVIL_SPLEEF:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
			}
			partidaAnvilSpleef();
			break;
		case BOMBARDMENT:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
			}
			partidaBombardment();
			break;
		case GEM_CRAWLER:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				iniciaPlayer(p);
			}
			spawneaGemas();
			break;
		case BOMB_TAG:
			for (Player p : getPlayerHandler().getPlayersSpectators()) {
				if (!getPlayerHandler().getPlayersObj().contains(p)) {
					mandaSpectatorPlayer(p);
				}
			}
			this.allowDamage = true;
			this.allowDamagePVP = true;
			for (Player p : getPlayerHandler().getPlayersObj()) {
				teleportaPlayer(p);
			}
			bombRandom();
			partidaBombTag();
			task = new BukkitRunnable() {
				public void run() {

					if (getPlayerHandler().getPlayerContador() != null) {
						UtilsRandomEvents.spawnParticles(
								Particle1711.valueOf(plugin.getReventConfig().getParticleTNTTag()), plugin,
								getPlayerHandler().getPlayerContador().getLocation());
					}
				}
			};
			task.runTaskTimer(plugin, 0, 5L);

			break;

		default:
			break;
		}

		for (Player p : getPlayerHandler().getPlayersObj()) {
			if (getMatch().getGamemode() != null) {
				p.setGameMode(getMatch().getGamemode());
			}
			prepareScoreboards(p);

		}
		for (Entry<Integer, Set<Player>> entrada : getPlayerHandler().getEquipos().entrySet()) {
			Set<Player> listaPlayers = new HashSet<Player>();
			listaPlayers.addAll(entrada.getValue());
			getPlayerHandler().getTeamsCopy().put(entrada.getKey(), listaPlayers);
		}

		for (Entry<Integer, Set<Player>> entrada : getPlayerHandler().getTeamsCopy().entrySet()) {
			Set<Player> listaPlayers = new HashSet<Player>();
			listaPlayers.addAll(entrada.getValue());
			getPlayerHandler().getEquipos().put(entrada.getKey(), listaPlayers);
		}
		setCanBreak(true);

	}

	private void partidaSW() {
		if (getMatch().getTimeRefill() != null && getMatch().getTimeRefill() > 0) {
			refillDate = new Date().getTime() + 1000 * getMatch().getTimeRefill();

			task = new BukkitRunnable() {
				public void run() {
					updateScoreboards();
					checkRefill();
				}
			};
			task.runTaskTimerAsynchronously(plugin, 20L, 20L);
		}

	}

	private void hazComandosDeStart(Player player) {
		for (String cmd : match.getCommandsOnStart()) {
			if (player != null && cmd.contains("%player%")) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
						cmd.replaceAll("%player%", player.getName()));
			} else if (player == null && !cmd.contains("%player%")) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd);
			}

		}

	}

	private void partidaRedGreen(int i) {

		if (getPlaying()) {
			Double timer = 20. * plugin.getReventConfig().getSecondsCheckStopSong();

			Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
				public void run() {
					if (!allowDamage) {

						Integer random = getRandom().nextInt(100);
						if (random < plugin.getReventConfig().getProbabilityPerCheckToStopSound()) {
							UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
									plugin.getLanguage().getGreenRedLightStop(), false, true, false);

							Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
								public void run() {
									allowDamage = true;
								}
							}, plugin.getReventConfig().getTicksAfterMusicStopToKill());
							if (plugin.getReventConfig().getIsNoteBlockAPI())
								SongUtils.playRecord(getPlayerHandler().getPlayersObj(), false, plugin);
							UtilsCitizen.turnAroundNPC(getMatch().getNPCId(), plugin);
							UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersObj(),
									XSound.ENTITY_BAT_HURT);
						}
						partidaRedGreen(0);

					} else {
						if (i == 1) {
							if (getPlayerHandler().getPlayerToKill().isEmpty()) {
								allowDamage = false;
								UtilsCitizen.turnAroundNPC(getMatch().getNPCId(), plugin);
								UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
										plugin.getLanguage().getGreenRedLightMove(), false, true, false);
								if (plugin.getReventConfig().getIsNoteBlockAPI())
									SongUtils.playRecord(getPlayerHandler().getPlayersObj(), true, plugin);

							}
							partidaRedGreen(0);
						} else {
							partidaRedGreen(1);
						}
					}
				}

			}, timer.longValue());
		}

	}

	private void sortTeams(Player p) {

		Integer equipo = getEquipo(p);
		if (equipo == null) {
			Integer team = UtilsRandomEvents.getTeamLessPlayers(plugin, this, getPlayerHandler().getEquipos(),
					getMatch().getNumberOfTeams());
			if (!getPlayerHandler().getEquipos().containsKey(team)) {
				Set<Player> players = new HashSet<Player>();
				players.add(p);
				getPlayerHandler().getEquipos().put(team, players);
				getPlayerHandler().getTeamsCopy().put(team, players);
			} else {
				getPlayerHandler().getEquipos().get(team).add(p);
				getPlayerHandler().getTeamsCopy().get(team).add(p);
			}
		}
	}

	private void equilibrateTeams() {
		if (plugin.getReventConfig().isEquilibrateTeams() || (teamsWithPlayers() < 2)) {
			Integer numberOfTeams = getMatch().getNumberOfTeams();
			Integer minTeam = UtilsRandomEvents.getTeamLessPlayers(plugin, this, getPlayerHandler().getEquipos(),
					numberOfTeams);
			Integer maxTeam = UtilsRandomEvents.getTeamMorePlayers(plugin, getPlayerHandler().getEquipos());
			Integer min = getPlayerHandler().getEquipos().containsKey(minTeam)
					? getPlayerHandler().getEquipos().get(minTeam).size() : 0;
			Integer max = getPlayerHandler().getEquipos().containsKey(maxTeam)
					? getPlayerHandler().getEquipos().get(maxTeam).size() : 0;

			while ((max - min) > 1) {
				Player fuera = null;
				for (Player p : getPlayerHandler().getEquipos().get(maxTeam)) {
					fuera = p;
				}
				if (fuera != null) {
					getPlayerHandler().getEquipos().get(maxTeam).remove(fuera);
					if (getPlayerHandler().getEquipos().containsKey(minTeam)) {
						getPlayerHandler().getEquipos().get(minTeam).add(fuera);
					} else {
						getPlayerHandler().getEquipos().put(minTeam, new HashSet<Player>());
						getPlayerHandler().getEquipos().get(minTeam).add(fuera);

					}

				}

				minTeam = UtilsRandomEvents.getTeamLessPlayers(plugin, this, getPlayerHandler().getEquipos(),
						numberOfTeams);
				maxTeam = UtilsRandomEvents.getTeamMorePlayers(plugin, getPlayerHandler().getEquipos());
				min = getPlayerHandler().getEquipos().containsKey(minTeam)
						? getPlayerHandler().getEquipos().get(minTeam).size() : 0;
				max = getPlayerHandler().getEquipos().containsKey(maxTeam)
						? getPlayerHandler().getEquipos().get(maxTeam).size() : 0;
			}
		}
	}

	private void checkCooldowns() {

		task3 = new BukkitRunnable() {
			public void run() {
				long now = new Date().getTime();
				List<Player> listaJump = new ArrayList<Player>();
				List<Player> listaShoot = new ArrayList<Player>();
				for (Entry<Player, Long> entrada : getCooldownJump().entrySet()) {
					if (entrada.getValue() <= now) {
						listaJump.add(entrada.getKey());
						entrada.getKey().setExp(0);
					} else {
						Long diferencia = entrada.getValue() - now;
						Long segundos = diferencia / 1000;
						if (segundos > 0) {
							entrada.getKey().setLevel(segundos.intValue());
						} else {
							entrada.getKey().setLevel(0);

							Double percent = diferencia / 1000.;
							entrada.getKey().setExp(percent.floatValue());
						}
					}
				}
				for (Entry<Player, Long> entrada : getCooldownShoot().entrySet()) {
					if (entrada.getValue() < now) {
						listaShoot.add(entrada.getKey());
					}
				}

				try {
					for (Player s : listaJump) {
						getCooldownJump().remove(s);
					}
				} catch (Throwable e) {

				}
				try {
					for (Player s : listaShoot) {
						UtilsRandomEvents.playSound(plugin, s, XSound.BLOCK_WOODEN_DOOR_CLOSE, 3.0F, 2.0F);
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							public void run() {
								UtilsRandomEvents.playSound(plugin, s, XSound.BLOCK_WOODEN_DOOR_CLOSE, 3.0F, 2.0F);

							}
						}, 2L);

						getCooldownShoot().remove(s);
					}
				} catch (Throwable e) {

				}
			}
		};
		task3.runTaskTimer(plugin, 0, 1L);

	}

	private void partidaSG() {
		endDate = new Date().getTime() + 1000 * getTiempoPartida();
		if (getMatch().getTimeRefill() != null && getMatch().getTimeRefill() > 0) {
			refillDate = new Date().getTime() + 1000 * getMatch().getTimeRefill();
		}

		squareMap();

		task = new BukkitRunnable() {
			public void run() {
				updateScoreboards();
				checkSG();
				if (getMatch().getTimeRefill() != null && getMatch().getTimeRefill() > 0) {
					checkRefill();
				}
			}
		};
		task.runTaskTimerAsynchronously(plugin, 20L, 20L);

		task3 = new BukkitRunnable() {
			public void run() {

				checkDamageSG();
			}
		};
		task3.runTaskTimer(plugin, 20L, 20L);

	}

	private void squareMap() {
		getMapHandler().getActualCuboid().square();

	}

	protected void checkDamageSG() {
		List<Player> playersEchar = new ArrayList<Player>();
		for (Player p : getPlayerHandler().getPlayersObj()) {
			if (!getMapHandler().getActualCuboid().contains(p.getLocation())) {
				if (p.getHealth() <= plugin.getReventConfig().getSgAreaDamage()) {
					playersEchar.add(p);
				} else {
					p.damage(plugin.getReventConfig().getSgAreaDamage());

				}
			}
		}
		for (Player p : playersEchar) {
			echaDePartida(p, true, true, false, true, true);

		}
	}

	public void checkSG() {
		if (getPlaying()) {
			Date now = new Date();
			long dif = (endDate - now.getTime()) / 1000;

			if (dif <= 0) {
				endDate = new Date().getTime() + 1000 * getTiempoPartida();
				Cuboid cubo = new Cuboid(getMapHandler().getActualCuboid());
				if (match.getShrinkBlocks() != null)
					cubo.withdraw(1.0 * match.getShrinkBlocks());
				else
					cubo.shrink(0.2);
				shrinkMap(cubo);

			} else if (dif == 1) {
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
						plugin.getLanguage().getShrink().replaceAll("%time%", "1"), true);
			} else if (dif == 2) {
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
						plugin.getLanguage().getShrink().replaceAll("%time%", "2"), true);
			} else if (dif == 3) {
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
						plugin.getLanguage().getShrink().replaceAll("%time%", "3"), true);
			} else if (dif == 4) {
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
						plugin.getLanguage().getShrink().replaceAll("%time%", "4"), true);
			} else if (dif == 5) {
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
						plugin.getLanguage().getShrink().replaceAll("%time%", "5"), true);
			}

		}

	}

	public void checkRefill() {
		if (getPlaying()) {
			Date now = new Date();
			long dif2 = (refillDate - now.getTime()) / 1000;

			if (dif2 <= 0) {
				refillDate = new Date().getTime() + 1000 * getMatch().getTimeRefill();
				if (getMapHandler().getChests() != null) {
					getMapHandler().getChests().clear();
				}
				UtilsRandomEvents.mandaMensaje(plugin,
						plugin.getMatchActive().getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getRefill(), false);

			}
		}
	}

	private void shrinkMap(Cuboid cubo) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				getMapHandler().getActualCuboid().withdraw(0.5);
				for (Player p : getPlayerHandler().getPlayersObj()) {
					Double distance = Math.min(
							(getMapHandler().getActualCuboid().getMaxX() - getMapHandler().getActualCuboid().getMinX())
									/ 1.,
							(getMapHandler().getActualCuboid().getMaxZ() - getMapHandler().getActualCuboid().getMinZ())
									/ 1.);

					UtilsRandomEvents.setWorldBorder(getPlugin(), getMapHandler().getActualCuboid().getCenter(),
							distance, p);

				}
				if (cubo.getMaxX() < getMapHandler().getActualCuboid().getMaxX()) {

					shrinkMap(cubo);
				}

			}

		}, 1L);

	}

	private void doMoveAndWarmup() {

		setAllowMove(false);

		UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(), plugin.getLanguage().getPlayersMove()
				.replaceAll("%time%", "" + getMatch().getSecondsToBegin().longValue()), true);

		if (getMatch().getSecondsToBegin() > 3) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getPlayersMove().replaceAll("%time%", "3"), true);
				}
			}, 20 * (getMatch().getSecondsToBegin().longValue() - 3));
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getPlayersMove().replaceAll("%time%", "2"), true);
				}
			}, 20 * (getMatch().getSecondsToBegin().longValue() - 2));
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
				public void run() {

					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
							plugin.getLanguage().getPlayersMove().replaceAll("%time%", "1"), true);
				}
			}, 20 * (getMatch().getSecondsToBegin().longValue() - 1));

		}

		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

			public void run() {

				setAllowMove(true);

				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(), plugin.getLanguage()
						.getWarmupEnd().replaceAll("%time%", "" + getMatch().getSecondsMobSpawn().longValue()), true);

				if (getMatch().getSecondsMobSpawn() > 3) {
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
						public void run() {

							UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
									plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "3"), true);
						}
					}, 20 * (getMatch().getSecondsMobSpawn().longValue() - 3));
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
						public void run() {

							UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
									plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "2"), true);
						}
					}, 20 * (getMatch().getSecondsMobSpawn().longValue() - 2));
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
						public void run() {

							UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersObj(),
									plugin.getLanguage().getWarmupEnd().replaceAll("%time%", "1"), true);
						}
					}, 20 * (getMatch().getSecondsMobSpawn().longValue() - 1));

				}

				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

					public void run() {

						setAllowDamage(true);
						setAllowDamagePVP(true);
					}

				}, 20 * getMatch().getSecondsMobSpawn().longValue());

			}
		}, 20 * getMatch().getSecondsToBegin().longValue());

	}

	public void bombRandom() {
		if (getPlayerHandler().getPlayerContador() != null) {
			UtilsRandomEvents.removeGlow(plugin, getPlayerHandler().getPlayerContador(),
					getPlayerHandler().getPlayersTotalObj());
		}
		getPlayerHandler().setPlayerContador(
				getPlayerHandler().getPlayersObj().get(getRandom().nextInt(getPlayerHandler().getPlayersObj().size())));
		if (getPlayerHandler().getPlayerContador() != null) {
			UtilsRandomEvents.addGlow(plugin, getPlayerHandler().getPlayerContador(),
					getPlayerHandler().getPlayersTotalObj());
		}
		ponInventarioMatch(getPlayerHandler().getPlayerContador());
		removePotionsEffects(getPlayerHandler().getPlayerContador());
		if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
			if (getPlayerHandler().getPlayerContador().hasPotionEffect(PotionEffectType.SPEED)) {
				getPlayerHandler().getPlayerContador().removePotionEffect(PotionEffectType.SPEED);
			}
			getPlayerHandler().getPlayerContador().addPotionEffect(
					new PotionEffect(PotionEffectType.SPEED, 20 * plugin.getReventConfig().getSpeedDuration(),
							plugin.getReventConfig().getTntTagSpeedHolder() - 1));
		}
		UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(), plugin.getLanguage()
				.getPlayerHasBomb().replace("%player%", getPlayerHandler().getPlayerContador().getName()), true);
		if (plugin.getReventConfig().isUseTitleWhenGetBomb()) {
			plugin.getApi().usaTitle(getPlayerHandler().getPlayerContador(), "", plugin.getLanguage().getPlayerHasBomb()
					.replace("%player%", getPlayerHandler().getPlayerContador().getName()));
		}
		UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayerContador(), XSound.ENTITY_VILLAGER_HURT);

	}

	private void partidaBombTag() {

		if (getPlaying()) {
			endDate = Double.valueOf(new Date().getTime() + 1000 * getMatch().getSecondsMobSpawn()).longValue();
			for (Player p : getPlayerHandler().getPlayersObj()) {
				if (p.equals(getPlayerHandler().getPlayerContador())) {
					if (plugin.getReventConfig().getTntTagSpeedHolder() > 0) {
						if (p.hasPotionEffect(PotionEffectType.SPEED)) {
							p.removePotionEffect(PotionEffectType.SPEED);
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
								20 * plugin.getReventConfig().getSpeedDuration(),
								plugin.getReventConfig().getTntTagSpeedHolder() - 1));
					}

				} else {
					if (plugin.getReventConfig().getTntTagSpeedRunners() > 0) {
						if (p.hasPotionEffect(PotionEffectType.SPEED)) {
							p.removePotionEffect(PotionEffectType.SPEED);
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
								20 * plugin.getReventConfig().getSpeedDuration(),
								plugin.getReventConfig().getTntTagSpeedRunners() - 1));
					}
				}
			}
			task2 = new BukkitRunnable() {
				public void run() {
					checkTNTTag();
				}
			};
			task2.runTaskTimer(plugin, 0, 20L);

			task3 = new BukkitRunnable() {
				public void run() {
					updateScoreboards();
				}
			};
			task3.runTaskTimerAsynchronously(plugin, 0, 20L);

		}

	}

	public void checkTNTTag() {
		if (getPlaying()) {
			Date now = new Date();
			long dif = (endDate - now.getTime()) / 1000;

			if (dif <= 0) {

				UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
						XSound.ENTITY_GENERIC_EXPLODE);

				List<Player> muertos = new ArrayList<Player>();
				if (plugin.getReventConfig().isMultipleKillOnExplosion()) {
					muertos = UtilsRandomEvents.getPlayersWithin(getPlayerHandler().getPlayerContador(),
							getPlayerHandler().getPlayersObj(), plugin.getReventConfig().getRadiusOfTNTTagExplosion());
				} else {
					muertos.add(getPlayerHandler().getPlayerContador());
				}
				if (muertos.size() == getPlayerHandler().getPlayersObj().size()) {
					muertos.clear();
					muertos.add(getPlayerHandler().getPlayerContador());
				}

				for (Player p : muertos) {
					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getBombExplode().replace("%player%", p.getName()), true);
				}
				echaDePartida(muertos, true, true, false, true);
				if (getPlayerHandler().getPlayersObj().size() > limitPlayers) {
					bombRandom();
				} else {
					compruebaPartida();
				}
				endDate = Double.valueOf(new Date().getTime() + 1000 * getMatch().getSecondsMobSpawn()).longValue();
			} else {

				if (dif > 5 && dif % 5 == 0) {
					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getBombSeconds().replace("%seconds%", dif + ""), true);
				} else if (dif > 0 && dif <= 5) {
					UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
							XSound.BLOCK_NOTE_BLOCK_BANJO);
					UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
							plugin.getLanguage().getBombSeconds().replace("%seconds%", dif + ""), true);
				}

			}
		}
	}

	private void spawneaGemas() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {

					Location l = UtilsRandomEvents.getRandomLocation(plugin, getMapHandler().getCuboid(),
							getMatchActive());

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

	public void partidaAnvilSpleef() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {

					Location l = UtilsRandomEvents.getRandomLocation(plugin, getMapHandler().getCuboid(),
							getMatchActive());
					l.setY(getMapHandler().getCuboid().getMaxY());

					if (l.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {

						Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
							public void run() {
								l.getBlock().setType(XMaterial.ANVIL.parseMaterial());
							}
						});
					}
					if (getPlayerHandler().getPlayersObj().size() > 1) {
						if (getRandom().nextInt(5 + 0) < 2) {
							Location l2 = getPlayerHandler().getPlayersObj()
									.get(getRandom().nextInt(getPlayerHandler().getPlayersObj().size())).getLocation();
							l2.setY(getMapHandler().getCuboid().getMaxY());
							if (l2.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {

								Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
									public void run() {
										l2.getBlock().setType(XMaterial.ANVIL.parseMaterial());
									}
								});
							}
						}
					}

					partidaAnvilSpleef();
				}

			}, timer.longValue());
		}

	}

	public void partidaBombardment() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {
					Double spawns = getCounter().doubleValue() / 4.0;
					Integer spawnsInt = spawns.intValue() + 1;
					setCounter(getCounter() + 1);
					for (int i = 0; i < spawnsInt; i++) {

						Location l = getMatch().getEntitySpawns()
								.get(getRandom().nextInt(getMatch().getEntitySpawns().size()));
						Location locPlayer = null;
						if (getPlayerHandler().getPlayersObj().size() >= 1) {
							locPlayer = getPlayerHandler().getPlayersObj()
									.get(getRandom().nextInt(getPlayerHandler().getPlayersObj().size())).getLocation();
						}

						if (l != null && locPlayer != null) {

							Vector v = new Vector(locPlayer.getX() - l.getX(),
									locPlayer.getY() - l.getY() - 1 + plugin.getReventConfig().getOffSetYBombardment(),
									locPlayer.getZ() - l.getZ());
							Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {

								@Override
								public void run() {

									Entity ent = l.getWorld().spawnEntity(l, EntityType.FIREBALL);
									Fireball fireball = (Fireball) ent;
									getMapHandler().getFireballs().add(fireball);
									plugin.getApi().correctDirectionFireball(fireball, v);

								}

							}, i * 3L);

						}

					}
					partidaBombardment();
				}

			}, timer.longValue());
		}

	}

	public void partidaEscapeArrow() {
		if (getPlaying()) {
			Double timer = 20 * match.getSecondsMobSpawn();

			Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
				public void run() {

					Location l = UtilsRandomEvents.getRandomLocation(plugin, getMapHandler().getCuboid(),
							getMatchActive());
					if (getRandom().nextInt(1000) <= plugin.getReventConfig().getProbabilityPowerUp()) {

						Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
							public void run() {
								UtilsRandomEvents.spawnPowerUp(l, plugin);
							}
						});
					} else {
						Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
							public void run() {
								UtilsRandomEvents.spawnEntity(getMatchActive(), l, match.getMob(), plugin);
							}
						});
					}
					partidaEscapeArrow();
				}

			}, timer.longValue());
		}

	}

	private void partidaPorTiempo() {
		endDate = new Date().getTime() + 1000 * getTiempoPartida();
		task = new BukkitRunnable() {
			public void run() {
				updateScoreboards();
			}
		};
		task.runTaskTimerAsynchronously(plugin, 0, 20L);

		task2 = new BukkitRunnable() {
			public void run() {
				checkTimeMatch();
			}
		};
		task2.runTaskTimer(plugin, 0, 20L);

	}

	private void checkTimeMatch() {
		if (getPlaying()) {
			Date now = new Date();
			long nowLong = now.getTime();
			long dif = (endDate - now.getTime()) / 1000;

			switch (getMatch().getMinigame()) {
			case FISH_SLAP:
				List<Player> listaPlayer = new ArrayList<Player>(getPlayerHandler().getPlayersContadores().keySet());
				for (Player p : listaPlayer) {
					if (getPlayerHandler().getPlayersContadores().containsKey(p)) {
						if ((nowLong - getPlayerHandler().getPlayersContadores().get(p)) >= 1000) {
							if (getPuntuacion().containsKey(p.getName())) {
								getPuntuacion().put(p.getName(), getPuntuacion().get(p.getName()) + 1);
							} else {
								getPuntuacion().put(p.getName(), +1);
							}
							getPlayerHandler().getPlayersContadores().put(p,
									getPlayerHandler().getPlayersContadores().get(p) + 1000);
						}
					}
				}
				break;
			default:
				break;
			}

			if (dif <= 0) {

				if (getTournament()) {
					getPlayerHandler().getPlayersGanadores().addAll(sacaGanadoresPartidaTiempo());
					List<String> jugadores = new ArrayList<String>();
					for (Player p : getPlayerHandler().getPlayersGanadores()) {
						jugadores.add(p.getName());
					}
					tournamentObj.nextGame(getCopia(jugadores), getCopiaP(getPlayerHandler().getPlayersGanadores()),
							getCopiaP(getPlayerHandler().getPlayersSpectators()));
				} else {
					if (getPlayerHandler().getPlayerContador() != null) {
						outKoth(getPlayerHandler().getPlayerContador());
					}
					daRecompensas(true);
				}
				// spawnMobs(bWave.getMobs(), getPlugin());

			} else if (dif == 1) {
				UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
						XSound.ENTITY_PLAYER_LEVELUP);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getSecondsRemaining1(), Boolean.FALSE, true, true);
			} else if (dif == 2) {
				UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
						XSound.ENTITY_PLAYER_LEVELUP);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getSecondsRemaining2(), Boolean.FALSE, true, true);
			} else if (dif == 3) {
				UtilsRandomEvents.playSound(plugin, getPlayerHandler().getPlayersSpectators(),
						XSound.ENTITY_PLAYER_LEVELUP);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getSecondsRemaining3(), Boolean.FALSE, true, true);
			}
		}

	}

	public void iniciaPlayer(Player p) {
		teleportaPlayer(p);
		ponInventarioMatch(p);
		UtilsSQL.updateTries(p, match.getMinigame(), plugin);
	}

	public void iniciaPlayerTeam(Player p) {
		teleportaPlayer(p);
		ponInventarioMatch(p);

		p.getInventory().setChestplate(Petos.getPeto(getEquipo(p)).getPeto());
		p.updateInventory();
		crearTeam(p);
		UtilsSQL.updateTries(p, match.getMinigame(), plugin);
	}

	private void crearTeam(Player p) {
		teams = Boolean.TRUE;
		if (plugin.getNametagHook() == null) {
			if (plugin.getColorBoard().getTeam(p.getName()) != null) {
				plugin.getColorBoard().getTeam(p.getName()).unregister();
			}

			Team t = plugin.getColorBoard().registerNewTeam(p.getName());

			t.setPrefix(Petos.getPeto(getEquipo(p)).getChatColor() + "");
			t.addEntry(p.getName());
		} else {
			getPlayerHandler().getPlayersPrefix().put(p.getName(),
					plugin.getNametagHook().getApi().getNametag(p).getPrefix());
			plugin.getNametagHook().getApi().setPrefix(p, "" + Petos.getPeto(getEquipo(p)).getChatColor() + "");

		}

	}

	public void iniciaPlayerBeast(Player p) {
		UtilsRandomEvents.teleportaPlayer(p, getMatch().getBeastSpawn(), plugin);
		p.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getYouBeast());
		UtilsSQL.updateTries(p, match.getMinigame(), plugin);

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {
			public void run() {
				teleportaPlayer(p);
				ponInventarioBeast(p);
			}
		}, Double.valueOf(20 * plugin.getMatchActive().getMatch().getSecondsMobSpawn()).longValue());

	}

	public void mandaSpectatorPlayer(Player p) {
		if (!match.getSpectatorSpawns().isEmpty()) {
			Location loc = match.getSpectatorSpawns().get(getRandom().nextInt(match.getSpectatorSpawns().size()));
			UtilsRandomEvents.teleportaPlayer(p, loc, plugin);
		}

	}

	private void teleportaPlayer(Player p) {
		Location loc = null;
		switch (match.getMinigame()) {
		case WDROP:
			loc = getWaterDrops().get(getStep().get(p)).getSpawn();
			getMapHandler().getCheckpoints().put(p.getName(), loc);
			UtilsRandomEvents.teleportaPlayer(p, loc, plugin);
			break;
		case PAINTBALL:
		case HOEHOEHOE:
		case SPLATOON:
		case TSW_REAL:
		case TSG_REAL:
		case TOP_KILLER_TEAMS:
		case PAINTBALL_TOP_KILL:
		case BATTLE_ROYALE_TEAMS:
			loc = match.getSpawns().get(getEquipo(p));
			getMapHandler().getCheckpoints().put(p.getName(), loc);
			UtilsRandomEvents.teleportaPlayer(p, loc, plugin);
			break;
		default:
			loc = getSpawnPoint(getPlayerHandler().getPlayersObj().indexOf(p));
			// loc =
			// match.getSpawns().get(getPlayerHandler().getPlayersObj().indexOf(p));
			getMapHandler().getCheckpoints().put(p.getName(), loc);
			UtilsRandomEvents.teleportaPlayer(p, loc, plugin);
			break;
		}

	}

	private Location getSpawnPoint(int index) {
		while (index >= match.getSpawns().size()) {
			index -= match.getSpawns().size();
		}

		return match.getSpawns().get(index);
	}

	public void reiniciaPlayer(Player p) {

		switch (match.getMinigame()) {
		case GEM_CRAWLER:
			dropItems(p);
			Location location = p.getLocation();

			Integer amount = 0;
			if (getPuntuacion().containsKey(p.getName())) {
				amount = getPuntuacion().get(p.getName());
			}

			if (amount > 0) {
				ItemStack puntos = new ItemStack(XMaterial.EMERALD.parseMaterial());
				puntos.setAmount(amount);
				location.getWorld().dropItem(location, puntos);
				getPuntuacion().put(p.getName(), 0);
				UtilsRandomEvents.mandaMensaje(plugin, getPlayerHandler().getPlayersSpectators(),
						plugin.getLanguage().getLostGems().replace("%player%", p.getName()), true);
				if (getPlayerHandler().getPlayerContador() != null
						&& getPlayerHandler().getPlayerContador().equals(p)) {
					getPlayerHandler().setPlayerContador(null);
					setNumeroSegRestantes(plugin.getReventConfig().getNumberOfSecondsWithGems());
				}
				compruebaPartida();
			}
			if (plugin.getReventConfig().isCooldownAfterDeath()) {
				p.getInventory().clear();
				p.updateInventory();
				p.setGameMode(GameMode.SPECTATOR);
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if (getPlayerHandler().getPlayersObj().contains(p) && getPlaying()) {
							reiniciaGemCrawler(p);
							invincibility(p);

						}
					}

				}, 20L * plugin.getReventConfig().getCooldownAfterDeathSeconds());
			} else {
				reiniciaGemCrawler(p);
				invincibility(p);

			}
			break;
		case WDROP:
			Integer actual = getStep().get(p);
			if (actual != null) {
				WaterDropStepActive wd = getWaterDrops().get(actual);
				if (wd != null)
					UtilsRandomEvents.teleportaPlayer(p, wd.getSpawn(), plugin);
			}
			break;
		case QUAKECRAFT:

			dropItems(p);

			if (plugin.getReventConfig().isCooldownAfterDeath()) {
				p.getInventory().clear();
				p.updateInventory();
				p.setGameMode(GameMode.SPECTATOR);
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if (getPlayerHandler().getPlayersObj().contains(p) && getPlaying()) {

							reiniciaDefault(p);
							invincibility(p);

							if (plugin.getReventConfig().isQuakeGiveDefaultWeapon()) {
								p.getInventory().addItem(XMaterial.STONE_HOE.parseItem());
								p.updateInventory();
							}
						}
					}

				}, 20L * plugin.getReventConfig().getCooldownAfterDeathSeconds());
			} else {
				reiniciaDefault(p);
				invincibility(p);

				if (plugin.getReventConfig().isQuakeGiveDefaultWeapon()) {
					p.getInventory().addItem(XMaterial.STONE_HOE.parseItem());
					p.updateInventory();
				}
			}

			break;
		case SPLATOON:
			dropItems(p);

			if (plugin.getReventConfig().isCooldownAfterDeath()) {
				p.getInventory().clear();
				p.updateInventory();
				p.setGameMode(GameMode.SPECTATOR);
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if (getPlayerHandler().getPlayersObj().contains(p) && getPlaying()) {

							reiniciaDefault(p);
							invincibility(p);

							if (getEquipo(p) != null)
								p.getInventory().setChestplate(Petos.getPeto(getEquipo(p)).getPeto());
							if (plugin.getReventConfig().isQuakeGiveDefaultWeapon()) {
								p.getInventory().addItem(XMaterial.STONE_HOE.parseItem());

							}
							p.updateInventory();
						}
					}

				}, 20L * plugin.getReventConfig().getCooldownAfterDeathSeconds());
			} else {
				reiniciaDefault(p);
				invincibility(p);

				if (getEquipo(p) != null)
					p.getInventory().setChestplate(Petos.getPeto(getEquipo(p)).getPeto());
				if (plugin.getReventConfig().isQuakeGiveDefaultWeapon()) {
					p.getInventory().addItem(XMaterial.STONE_HOE.parseItem());

				}
				p.updateInventory();
			}

			break;
		default:
			outKoth(p);
			dropItems(p);

			if (plugin.getReventConfig().isCooldownAfterDeath()) {
				p.getInventory().clear();
				p.updateInventory();
				p.setGameMode(GameMode.SPECTATOR);
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if (getPlayerHandler().getPlayersObj().contains(p) && getPlaying()) {

							reiniciaDefault(p);
							invincibility(p);
						}
					}

				}, 20L * plugin.getReventConfig().getCooldownAfterDeathSeconds());
			} else {
				reiniciaDefault(p);
				invincibility(p);

			}

			break;
		}
		updateScoreboards();

	}

	public void invincibility(Player p) {
		if (plugin.getReventConfig().getInvincibleAfterRespawn() > 0) {
			Long now = (new Date()).getTime();
			now += plugin.getReventConfig().getInvincibleAfterRespawn() * 1000;

			getPlayerHandler().getPlayersInvincible().put(p.getName(), now);
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					getPlayerHandler().getPlayersInvincible().remove(p.getName());

				}

			}, 20L * plugin.getReventConfig().getInvincibleAfterRespawn());
		}
	}

	private void reiniciaDefault(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		if (getMatch().getGamemode() != null) {
			p.setGameMode(getMatch().getGamemode());

		}

		Location loc2 = null;

		if (getPlayerHandler().getEquipos() != null && !getPlayerHandler().getEquipos().isEmpty()
				&& !plugin.getReventConfig().getTeamMatchRandomRespawn()) {
			loc2 = match.getSpawns().get(getEquipo(p));

		} else {
			loc2 = match.getSpawns().get(getRandom().nextInt(match.getSpawns().size()));
		}
		UtilsRandomEvents.teleportaPlayer(p, loc2, plugin);

		ponInventarioMatch(p);

	}

	private void reiniciaGemCrawler(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		if (getMatch().getGamemode() != null) {
			p.setGameMode(getMatch().getGamemode());

		}
		Location loc = match.getSpawns().get(getRandom().nextInt(match.getSpawns().size()));
		UtilsRandomEvents.teleportaPlayer(p, loc, plugin);

		ponInventarioMatch(p);

	}

	private void dropItems(Player p, Boolean forzar) {
		Location location = p.getLocation();

		if (plugin.getReventConfig().isDropItemsAfterDie() || forzar) {
			List<ItemStack> contents = new ArrayList<ItemStack>();

			for (ItemStack s : p.getInventory().getContents()) {
				if (s != null) {
					contents.add(s);
					location.getWorld().dropItemNaturally(location, s);
				}
			}
			try {
				for (ItemStack s : p.getInventory().getArmorContents()) {
					if (s != null && !contents.contains(s)) {
						location.getWorld().dropItemNaturally(location, s);
					}
				}
			} catch (Exception e) {

			}
		}

	}

	private void dropItems(Player p) {
		dropItems(p, false);

	}

	private void mandaMensajesEquipo(Map<Integer, Set<Player>> equipos2) {
		List<Player> restoEquipo = new ArrayList<Player>();
		for (Set<Player> players : equipos2.values()) {
			for (Player player : players) {
				restoEquipo.clear();
				restoEquipo.addAll(players);
				restoEquipo.remove(player);

				if (restoEquipo.size() == 0) {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getShowAlone());
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
		if (plugin.getReventConfig().isInventoryManagement() && !getMatch().getUseOwnInventory()) {

			p.getInventory().clear();

			InventoryPers inventory = null;

			if (getPlayerHandler().getPlayerKits().containsKey(p)) {
				inventory = getPlayerHandler().getPlayerKits().get(p).getInventory();
			} else {
				List<Kit> kits = UtilsRandomEvents.kitsAvailable(p, match.getKits(), plugin);
				if (!kits.isEmpty()) {
					Kit kitRandom = kits.get(random.nextInt(kits.size()));
					inventory = kitRandom.getInventory();
					getPlayerHandler().getPlayerKits().put(p, kitRandom);

					p.sendMessage(plugin.getLanguage().getTagPlugin()
							+ plugin.getLanguage().getKitChosen().replaceAll("%kit_name%", kitRandom.getName()));
				}
			}
			if (inventory != null) {
				p.getInventory().setContents(inventory.getContents());
				p.getInventory().setHelmet(inventory.getHelmet());
				p.getInventory().setLeggings(inventory.getLeggings());
				p.getInventory().setBoots(inventory.getBoots());
				p.getInventory().setChestplate(inventory.getChestplate());
				if (match.getMinigame().equals(MinigameType.BOMB_TAG)) {
					if (match.getHead() != null) {
						p.getInventory().setHelmet(match.getHead());

					} else {
						p.getInventory().setHelmet(XMaterial.TNT.parseItem());
					}
				}
			}
			if (getEquipo(p) != null)
				p.getInventory().setChestplate(Petos.getPeto(getEquipo(p)).getPeto());
			if (plugin.getReventConfig().isForceGamemodeSurvival())
				p.setGameMode(GameMode.SURVIVAL);
			p.updateInventory();
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			p.setFireTicks(0);

			removePotionsEffects(p);

			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2));

		}

	}

	public void removePotionsEffects(Player p) {

		if (p.getActivePotionEffects() != null) {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}

		}
	}

	public void ponInventarioRunner(Player p) {
		if (plugin.getReventConfig().isInventoryManagement() && !getMatch().getUseOwnInventory()) {

			p.getInventory().setContents(match.getInventoryRunners().getContents());
			p.getInventory().setHelmet(match.getInventoryRunners().getHelmet());
			p.getInventory().setLeggings(match.getInventoryRunners().getLeggings());
			p.getInventory().setBoots(match.getInventoryRunners().getBoots());
			p.getInventory().setChestplate(match.getInventoryRunners().getChestplate());
			if (plugin.getReventConfig().isForceGamemodeSurvival())
				p.setGameMode(GameMode.SURVIVAL);
			p.updateInventory();
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			p.setFireTicks(0);

			removePotionsEffects(p);

			// p.addPotionEffect(new
			// PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2));
		}
	}

	public void ponInventarioBeast(Player p) {
		if (plugin.getReventConfig().isInventoryManagement() && !getMatch().getUseOwnInventory()) {

			p.getInventory().setContents(match.getInventoryBeast().getContents());
			p.getInventory().setHelmet(match.getInventoryBeast().getHelmet());
			p.getInventory().setLeggings(match.getInventoryBeast().getLeggings());
			p.getInventory().setBoots(match.getInventoryBeast().getBoots());
			p.getInventory().setChestplate(match.getInventoryBeast().getChestplate());
			if (plugin.getReventConfig().isForceGamemodeSurvival())
				p.setGameMode(GameMode.SURVIVAL);
			p.updateInventory();
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			p.setFireTicks(0);

			if (p.getActivePotionEffects() != null) {
				for (PotionEffect effect : p.getActivePotionEffects()) {
					p.removePotionEffect(effect.getType());
				}

			}
		}
		// p.addPotionEffect(new
		// PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2));

	}

	public void matchWaitingPlayers() {
		Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (!getStarted()) {
					if (plugin.getReventConfig().isForcePlayersToEnter()) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())
									&& (getMatch().getPermission() == null
											|| p.hasPermission(getMatch().getPermission()))
									&& !getPlayerHandler().getPlayersObj().contains(p)) {
								Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
									public void run() {
										plugin.getComandosExecutor().joinRandomEvent(plugin, p, getPassword());
									}
								});
							}
						}
					}
					tries++;
					Boolean playSound = Boolean.FALSE;

					if (tries > plugin.getReventConfig().getMinNumberOfTriesBeforeBeginning()
							&& match.getAmountPlayersMin() <= (getPlayerHandler().getPlayers().size())) {
						if (!getStarted()) {
							Integer startTime = plugin.getReventConfig().getSecondsToStartMatch() + 3;
							String startingMatch = plugin.getLanguage().getStartingMatch().replaceAll("%time%",
									startTime.toString());
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())
										&& (getMatch().getPermission() == null
												|| p.hasPermission(getMatch().getPermission()))
										&& (!plugin.getReventConfig().getRestrictWorlds() || (p.getWorld() != null
												&& p.getWorld().getName() != null && plugin.getReventConfig()
														.getAllowedWorlds().contains(p.getWorld().getName())))) {

									p.sendMessage(plugin.getLanguage().getTagPlugin() + startingMatch);
								}
							}
							Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {

								@Override
								public void run() {
									matchBegin();

								}
							}, 20 * plugin.getReventConfig().getSecondsToStartMatch());
						}
					} else {
						// if (!getPlayerHandler().getPlayers().isEmpty()) {
						// String waiting = Constantes.WAITING_FOR_PLAYERS;
						// UtilsRandomEvents
						// .mandaMensaje(plugin,getPlayerHandler().getPlayersSpectators(),
						// waiting.replaceAll("%players%", "" +
						// getPlayerHandler().getPlayers().size())
						// .replaceAll("%neededPlayers%",
						// match.getAmountPlayersMin().toString()),
						// Boolean.TRUE);
						// }
						String firstPart = "";
						String[] totalPart;
						if (firstAnnounce) {
							playSound = Boolean.TRUE;
							totalPart = plugin.getLanguage().getAnnounceFirst().split("%clickhere%");
							firstAnnounce = Boolean.FALSE;
						} else {
							totalPart = plugin.getLanguage().getAnnounceNext().split("%clickhere%");

						}
						firstPart = totalPart[0];
						firstPart = StringEscapeUtils.unescapeJava(firstPart.toString());
						firstPart = firstPart.replaceAll("%event%", match.getName())
								.replaceAll("%type%", match.getMinigame().getMessage(plugin))
								.replaceAll("\\n", "<jump>")
								.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
								.replaceAll("%maxPlayers%", match.getAmountPlayers().toString())
								.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size());

						List<String> primerasPartes = Arrays.asList(firstPart.split("<jump>"));
						// plugin.getLoggerP().info(primerasPartes);

						firstPart = primerasPartes.get(primerasPartes.size() - 1);

						// primerasPartes.remove(primerasPartes.size() - 1);
						primerasPartes = primerasPartes.subList(0, primerasPartes.size() - 1);

						String lastPart = totalPart[1];
						lastPart = StringEscapeUtils.unescapeJava(lastPart.toString());

						lastPart = lastPart.replaceAll("%event%", match.getName())
								.replaceAll("%type%", match.getMinigame().getMessage(plugin))
								.replaceAll("\\n", "<jump>")
								.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
								.replaceAll("%maxPlayers%", match.getAmountPlayers().toString())
								.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size());

						List<String> ultimasPartes = Arrays.asList(lastPart.split("<jump>"));
						// plugin.getLoggerP().info(ultimasPartes);

						lastPart = ultimasPartes.get(0);

						// ultimasPartes = ultimasPartes.subList(1,
						// ultimasPartes.size());

						// ultimasPartes.remove(0);

						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())
									&& (getMatch().getPermission() == null
											|| p.hasPermission(getMatch().getPermission()))
									&& (!plugin.getReventConfig().getRestrictWorlds() || (p.getWorld() != null
											&& p.getWorld().getName() != null && plugin.getReventConfig()
													.getAllowedWorlds().contains(p.getWorld().getName())))) {
								if (playSound) {
									Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
										public void run() {
											UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_VILLAGER_HURT);
										}
									});
								}
								for (String pri : primerasPartes) {
									p.sendMessage(pri);
								}
								plugin.getApi().send(p,
										firstPart.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size())
												.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
												.replaceAll("%maxPlayers%", match.getAmountPlayers().toString()),
										plugin.getLanguage().getClickHere(), new ArrayList<String>(),
										"/revent join " + password,
										lastPart.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size())
												.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
												.replaceAll("%maxPlayers%", match.getAmountPlayers().toString()));
								for (int i = 0; i < ultimasPartes.size(); i++) {
									if (i != 0)
										p.sendMessage(ultimasPartes.get(i));
								}
							}
						}
						if (tries <= plugin.getReventConfig().getNumberOfTriesBeforeCancelling()) {
							Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
								@Override
								public void run() {
									Bukkit.getPluginManager()
											.callEvent(new ReventCountdownEvent(getMatchActive(), getForzada()));
								}
							});

							matchWaitingPlayers(true);
						} else {
							Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
								public void run() {
									finalizaPartida(new ArrayList<Player>(), Boolean.FALSE, Boolean.TRUE);
								}
							});

						}
					}
				}
			}
		}, 5L);

	}

	public void matchWaitingPlayers(Boolean segundo) {

		Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) getPlugin(), new Runnable() {
			public void run() {
				if (plugin.getMatchActive() != null && plugin.getMatchActive().getPassword().equals(getPassword())) {
					if (!getStarted()) {
						if (plugin.getReventConfig().isForcePlayersToEnter()) {
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())
										&& (getMatch().getPermission() == null
												|| p.hasPermission(getMatch().getPermission()))
										&& !getPlayerHandler().getPlayersObj().contains(p)) {
									Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
										public void run() {
											plugin.getComandosExecutor().joinRandomEvent(plugin, p, getPassword());
										}
									});
								}
							}
						}
						tries++;
						Boolean playSound = Boolean.FALSE;

						if (tries > plugin.getReventConfig().getMinNumberOfTriesBeforeBeginning()
								&& match.getAmountPlayersMin() <= (getPlayerHandler().getPlayers().size())) {
							if (!getStarted()) {
								Integer startTime = plugin.getReventConfig().getSecondsToStartMatch() + 3;
								String startingMatch = plugin.getLanguage().getStartingMatch().replaceAll("%time%",
										startTime.toString());
								for (Player p : Bukkit.getOnlinePlayers()) {
									if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())
											&& (getMatch().getPermission() == null
													|| p.hasPermission(getMatch().getPermission()))
											&& (!plugin.getReventConfig().getRestrictWorlds() || (p.getWorld() != null
													&& p.getWorld().getName() != null && plugin.getReventConfig()
															.getAllowedWorlds().contains(p.getWorld().getName())))) {

										p.sendMessage(plugin.getLanguage().getTagPlugin() + startingMatch);
									}
								}
								Bukkit.getServer().getScheduler().runTaskLater((Plugin) getPlugin(), new Runnable() {

									@Override
									public void run() {

										matchBegin();

									}
								}, 20 * plugin.getReventConfig().getSecondsToStartMatch());
							}
						} else {
							// if (!getPlayerHandler().getPlayers().isEmpty()) {
							// String waiting = Constantes.WAITING_FOR_PLAYERS;
							// UtilsRandomEvents
							// .mandaMensaje(plugin,getPlayerHandler().getPlayersSpectators(),
							// waiting.replaceAll("%players%", "" +
							// getPlayerHandler().getPlayers().size())
							// .replaceAll("%neededPlayers%",
							// match.getAmountPlayersMin().toString()),
							// Boolean.TRUE);
							// }
							String firstPart = "";
							String[] totalPart;
							if (firstAnnounce) {
								playSound = Boolean.TRUE;
								totalPart = plugin.getLanguage().getAnnounceFirst().split("%clickhere%");
								firstAnnounce = Boolean.FALSE;
							} else {
								totalPart = plugin.getLanguage().getAnnounceNext().split("%clickhere%");

							}
							firstPart = totalPart[0];
							firstPart = StringEscapeUtils.unescapeJava(firstPart.toString());
							firstPart = firstPart.replaceAll("%event%", match.getName())
									.replaceAll("%type%", match.getMinigame().getMessage(plugin))
									.replaceAll("\\n", "<jump>")
									.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
									.replaceAll("%maxPlayers%", match.getAmountPlayers().toString())
									.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size());

							List<String> primerasPartes = Arrays.asList(firstPart.split("<jump>"));
							// plugin.getLoggerP().info(primerasPartes);

							firstPart = primerasPartes.get(primerasPartes.size() - 1);

							// primerasPartes.remove(primerasPartes.size() - 1);
							primerasPartes = primerasPartes.subList(0, primerasPartes.size() - 1);

							String lastPart = "";
							if (totalPart.length > 1) {
								lastPart = totalPart[1];
							}
							lastPart = StringEscapeUtils.unescapeJava(lastPart.toString());

							lastPart = lastPart.replaceAll("%event%", match.getName())
									.replaceAll("%type%", match.getMinigame().getMessage(plugin))
									.replaceAll("\\n", "<jump>")
									.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
									.replaceAll("%maxPlayers%", match.getAmountPlayers().toString())
									.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size());

							List<String> ultimasPartes = Arrays.asList(lastPart.split("<jump>"));
							// plugin.getLoggerP().info(ultimasPartes);

							lastPart = ultimasPartes.get(0);

							// ultimasPartes.remove(0);

							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p.hasPermission(ComandosEnum.CMD_JOIN.getPermission())
										&& (getMatch().getPermission() == null
												|| p.hasPermission(getMatch().getPermission()))
										&& (!plugin.getReventConfig().getRestrictWorlds() || (p.getWorld() != null
												&& p.getWorld().getName() != null && plugin.getReventConfig()
														.getAllowedWorlds().contains(p.getWorld().getName())))) {
									if (playSound) {
										Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
											public void run() {
												UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_VILLAGER_HURT);
											}
										});
									}
									for (String pri : primerasPartes) {
										p.sendMessage(pri);
									}
									plugin.getApi().send(p, firstPart
											.replaceAll("%players%", "" + getPlayerHandler().getPlayers().size())
											.replaceAll("%neededPlayers%", match.getAmountPlayersMin().toString())
											.replaceAll("%maxPlayers%",
													match.getAmountPlayers().toString()),
											plugin.getLanguage().getClickHere(), new ArrayList<String>(),
											"/revent join " + password,
											lastPart.replaceAll("%players%",
													"" + getPlayerHandler().getPlayers().size())
													.replaceAll("%neededPlayers%",
															match.getAmountPlayersMin().toString())
													.replaceAll("%maxPlayers%", match.getAmountPlayers().toString()));
									for (int i = 0; i < ultimasPartes.size(); i++) {
										if (i != 0)
											p.sendMessage(ultimasPartes.get(i));
									}
								}
							}
							if (tries <= plugin.getReventConfig().getNumberOfTriesBeforeCancelling()) {
								matchWaitingPlayers(true);
							} else {
								Bukkit.getServer().getScheduler().runTask((Plugin) getPlugin(), new Runnable() {
									public void run() {
										finalizaPartida(new ArrayList<Player>(), Boolean.FALSE, Boolean.TRUE);
									}
								});

							}
						}

					}
				}
			}
		}, 20 * plugin.getReventConfig().getSecondsCheckPlayers());

	}

	public void prepareScoreboards(Player p) {
		if (plugin.getReventConfig().isUseScoreboard()) {
			switch (match.getMinigame()) {
			case BATTLE_ROYALE:
			case BATTLE_ROYALE_CABALLO:
			case BATTLE_ROYALE_TEAM_2:
			case BATTLE_ROYALE_TEAMS:
			case BOMB_TAG:
			case ESCAPE_ARROW:
			case ANVIL_SPLEEF:
			case BOMBARDMENT:
			case ESCAPE_FROM_BEAST:
			case GEM_CRAWLER:
			case KNOCKBACK_DUEL:
			case OITC:
			case SG:
			case SPLEEF:
			case SPLEGG:
			case SW:
			case TNT_RUN:
			case TOP_KILLER:
			case KOTH:
			case FISH_SLAP:
			case QUAKECRAFT:
			case TOP_KILLER_TEAM_2:
			case TOP_KILLER_TEAMS:
			case PAINTBALL_TOP_KILL:
			case HOEHOEHOE:
			case SPLATOON:
			case TSG:
			case TSW:
			case TSG_REAL:
			case TSW_REAL:
			case WDROP:
			case PAINTBALL:
				getPlayerHandler().getOldScoreboards().put(p.getName(), p.getScoreboard());
				FastBoard fBoard = plugin.getApi().createFastBoard(p);

				fBoard.updateTitle(plugin.getLanguage().getScoreboardTitle());

				fBoard.updateLines(UtilsRandomEvents.prepareLines(plugin, this, p));

				getPlayerHandler().getScoreboards().put(p.getName(), fBoard);
				break;
			default:
				break;
			}

		}

	}

	public void updateScoreboards() {
		if (plugin.getReventConfig().isUseScoreboard()) {
			for (FastBoard fBoard : getPlayerHandler().getScoreboards().values()) {
				try {
					fBoard.updateLines(UtilsRandomEvents.prepareLines(plugin, this, fBoard.getPlayer()));
				} catch (Throwable e) {
					plugin.getLoggerP().info(e.toString());
				}
			}
		}

	}

	public void givePoint(Player p, Integer point) {
		if (getPuntuacion().containsKey(p.getName())) {
			getPuntuacion().put(p.getName(), getPuntuacion().get(p.getName()) + point);

		} else {
			getPuntuacion().put(p.getName(), point);
		}

	}

	public void addPainted(Player p, List<Location> locations) {
		if (getPlayerHandler().getPaintedLocations().containsKey(p)) {
			getPlayerHandler().getPaintedLocations().get(p).addAll(locations);

		} else {
			getPlayerHandler().getPaintedLocations().put(p, new HashSet<Location>(locations));
		}

	}

	public void outKoth(Player p) {
		Long now = new Date().getTime();

		switch (getMatch().getMinigame()) {
		case KOTH:
			if (getPlayerHandler().getPlayerContador() != null && getPlayerHandler().getPlayerContador().equals(p)) {
				if (checkDate != -1L) {
					Long diff = now - checkDate;

					if (diff >= 1000) {
						diff = diff - (diff % 1000);
						Double difere = diff / 1000.0;
						if (getPuntuacion().containsKey(p.getName())) {
							getPuntuacion().put(p.getName(), getPuntuacion().get(p.getName()) + difere.intValue());

						} else {
							getPuntuacion().put(p.getName(), difere.intValue());
						}
					}
				}
				getPlayerHandler().setPlayerContador(null);
				UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_BAT_HURT);
			}

			break;

		case FISH_SLAP:
			if (getPlayerHandler().getPlayersContadores().containsKey(p)) {
				Long diff = now - getPlayerHandler().getPlayersContadores().get(p);

				if (diff >= 1000) {
					diff = diff - (diff % 1000);
					Double difere = diff / 1000.0;
					if (getPuntuacion().containsKey(p.getName())) {
						getPuntuacion().put(p.getName(), getPuntuacion().get(p.getName()) + difere.intValue());

					} else {
						getPuntuacion().put(p.getName(), difere.intValue());
					}
				}
			}
			getPlayerHandler().getPlayersContadores().remove(p);
			UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_BAT_HURT);

			break;
		default:
			break;
		}
	}

	public void inKoth(Player p) {
		Long now = new Date().getTime();

		switch (getMatch().getMinigame()) {
		case KOTH:
			checkDate = now;
			getPlayerHandler().setPlayerContador(p);
			UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_PLAYER_LEVELUP);
			break;
		case FISH_SLAP:
			getPlayerHandler().getPlayersContadores().put(p, now);
			UtilsRandomEvents.playSound(plugin, p, XSound.ENTITY_PLAYER_LEVELUP);

			break;
		default:
			break;
		}
	}

	private void borraScoreboard(Player player) {
		if (plugin.getReventConfig().isUseScoreboard()) {
			if (player != null) {
				if (getPlayerHandler().getScoreboards().containsKey(player.getName())) {
					getPlayerHandler().getScoreboards().get(player.getName()).delete();
					FeatherBoardUtils.recoverFeatherBoard(plugin, player);
					getPlayerHandler().getScoreboards().remove(player.getName());
				}
				if (getPlayerHandler().getOldScoreboards().get(player.getName()) != null) {

					player.setScoreboard(getPlayerHandler().getOldScoreboards().get(player.getName()));

				}
			}
		}

	}

	public void avanzaWaterDrop(Player player) {

		if (getStep().get(player).equals(getWaterDrops().size() - 1)) {

			if (plugin.getTournamentActive() == null) {
				getPlayerHandler().setPlayerContador(player);
				plugin.getMatchActive().daRecompensas(false);
			} else {
				if (!plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().contains(player)) {
					plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().add(player);
					for (Player p : plugin.getMatchActive().getPlayerHandler().getPlayersSpectators()) {
						p.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage()
								.getRaceTournament().replaceAll("%player%", player.getName())
								.replaceAll("%players%",
										"" + plugin.getMatchActive().getPlayerHandler().getPlayersGanadores().size())
								.replaceAll("%neededPlayers%", plugin.getMatchActive().getLimitPlayers().toString()));
					}
					plugin.getMatchActive().compruebaPartida();
				}
			}

		} else {
			Integer actual = getStep().get(player);
			actual++;
			getStep().put(player, actual);
			UtilsRandomEvents.teleportaPlayer(player, getWaterDrops().get(actual).getSpawn(), plugin);
			UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_PLAYER_LEVELUP);
			updateScoreboards();
		}
	}

	public void updateTeamItem(Player p) {
		ItemStack teamItem = null;
		for (ItemStack item : p.getInventory().getContents()) {
			if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName() != null
					&& item.getItemMeta().getDisplayName().equals(plugin.getLanguage().getTeamItemName())) {
				teamItem = item;
			}
		}
		if (teamItem != null) {
			Integer pos = p.getInventory().first(teamItem);
			if (pos >= 0) {
				Petos peto = Petos.getPeto(getEquipo(p));
				if (peto != null) {
					teamItem.setType(peto.getClay().parseMaterial());
					try {
						teamItem.setDurability(peto.getDye().getWoolData());
						teamItem.getData().setData(peto.getDye().getWoolData());
					} catch (Throwable e) {

					}
				}
				p.getInventory().setItem(pos, teamItem);
				p.updateInventory();
			}
		}

	}

	// GETTERS Y SETTERS

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

	public List<Entity> getMobs() {
		return mobs;
	}

	public void setMobs(List<Entity> mobs) {
		this.mobs = mobs;
	}

	public Map<String, Entity> getPets() {
		return pets;
	}

	public void setPets(Map<String, Entity> pets) {
		this.pets = pets;
	}

	public Map<String, Integer> getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Map<String, Integer> puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Boolean getPlaying() {
		return playing;
	}

	public void setPlaying(Boolean playing) {
		this.playing = playing;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getStarted() {
		return started;
	}

	public void setStarted(Boolean started) {
		this.started = started;
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

	public Boolean getTournament() {
		return tournament;
	}

	public void setTournament(Boolean tournament) {
		this.tournament = tournament;
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
		return "MatchActive [match=" + match + ", players=" + getPlayerHandler().getPlayers()
				+ ", getPlayerHandler().getPlayers()=" + getPlayerHandler().getPlayersObj() + ", playersGanadores="
				+ getPlayerHandler().getPlayersGanadores() + ", playersSpectators="
				+ getPlayerHandler().getPlayersSpectators() + "]";
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

	public Integer getDamageCounter() {
		return damageCounter;
	}

	public void setDamageCounter(Integer damageCounter) {
		this.damageCounter = damageCounter;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getAllowDamage() {
		return allowDamage;
	}

	public void setAllowDamage(Boolean allowDamage) {
		this.allowDamage = allowDamage;
	}

	public Boolean getAllowMove() {
		return allowMove;
	}

	public void setAllowMove(Boolean allowMove) {
		this.allowMove = allowMove;
	}

	public MatchMapDataHandler getMapHandler() {
		return mapHandler;
	}

	public void setMapHandler(MatchMapDataHandler mapHandler) {
		this.mapHandler = mapHandler;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public MatchPlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	public void setPlayerHandler(MatchPlayerHandler playerHandler) {
		this.playerHandler = playerHandler;
	}

	public BukkitRunnable getTask2() {
		return task2;
	}

	public void setTask2(BukkitRunnable task2) {
		this.task2 = task2;
	}

	public BukkitRunnable getTask3() {
		return task3;
	}

	public void setTask3(BukkitRunnable task3) {
		this.task3 = task3;
	}

	public List<WaterDropStepActive> getWaterDrops() {
		return waterDrops;
	}

	public void setWaterDrops(List<WaterDropStepActive> waterDrops) {
		this.waterDrops = waterDrops;
	}

	public Map<Player, Integer> getStep() {
		return step;
	}

	public void setStep(Map<Player, Integer> step) {
		this.step = step;
	}

	public Map<Player, Long> getCooldownJump() {
		return cooldownJump;
	}

	public void setCooldownJump(Map<Player, Long> cooldownJump) {
		this.cooldownJump = cooldownJump;
	}

	public Map<Player, Long> getCooldownShoot() {
		return cooldownShoot;
	}

	public void setCooldownShoot(Map<Player, Long> cooldownShoot) {
		this.cooldownShoot = cooldownShoot;
	}

	public long getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(long checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public Boolean getCanBreak() {
		return canBreak;
	}

	public void setCanBreak(Boolean canBreak) {
		this.canBreak = canBreak;
	}

	public Boolean getTeams() {
		return teams;
	}

	public void setTeams(Boolean teams) {
		this.teams = teams;
	}

	public Boolean getAllowDamagePVP() {
		return allowDamagePVP;
	}

	public void setAllowDamagePVP(Boolean allowDamagePVP) {
		this.allowDamagePVP = allowDamagePVP;
	}

	public long getRefillDate() {
		return refillDate;
	}

	public void setRefillDate(long refillDate) {
		this.refillDate = refillDate;
	}

}
