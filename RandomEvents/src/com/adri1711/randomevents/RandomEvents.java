package com.adri1711.randomevents;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.adri1711.api.API1711;
import com.adri1711.randomevents.bbdd.HikariCP;
import com.adri1711.randomevents.commands.Comandos;
import com.adri1711.randomevents.commands.ComandosExecutor;
import com.adri1711.randomevents.language.LanguageMessages;
import com.adri1711.randomevents.listeners.Chat;
import com.adri1711.randomevents.listeners.Death;
import com.adri1711.randomevents.listeners.GUI;
import com.adri1711.randomevents.listeners.Join;
import com.adri1711.randomevents.listeners.Move;
import com.adri1711.randomevents.listeners.PickUp;
import com.adri1711.randomevents.listeners.Quit;
import com.adri1711.randomevents.listeners.Use;
import com.adri1711.randomevents.listeners.WeaponShoot;
import com.adri1711.randomevents.match.Kit;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.Tournament;
import com.adri1711.randomevents.match.TournamentActive;
import com.adri1711.randomevents.match.WaterDropStep;
import com.adri1711.randomevents.match.schedule.Schedule;
import com.adri1711.randomevents.match.utils.BannedPlayers;
import com.adri1711.randomevents.metrics.Metrics;
import com.adri1711.randomevents.placeholders.ReventPlaceholder;
import com.adri1711.randomevents.util.NameTagHook;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;
import com.adri1711.util.enums.XMaterial;

public class RandomEvents extends JavaPlugin {

	private Location spawn;

	private Integer secondsTimer;

	private Integer minPlayers;

	private ComandosExecutor comandosExecutor;

	private API1711 api;

	private Integer probabilityRandomEvent;

	private Integer probabilityRandomEventTournament;

	private Integer probabilityPowerUp;

	private Random random;

	private MatchActive matchActive;

	private TournamentActive tournamentActive;

	private List<Match> matches;
	private List<WaterDropStep> waterDrops;
	private List<Kit> kits;
	private List<Match> matchesAvailable;

	private Tournament tournament;

	private Map<String, Date> cooldowns;

	private Map<String, Match> playerMatches;

	private Map<String, Integer> playersCreation;

	private Map<String, WaterDropStep> playerWaterDrop;

	private Map<String, Integer> playersCreationWaterDrop;

	private Map<String, Kit> playerKit;

	private Map<String, Integer> playersCreationKit;

	private List<String> editando;

	private Map<String, EntityType> playersEntity;

	private List<String> commandsOnUserJoin;

	private List<String> commandsOnMatchBegin;

	private Boolean forzado = Boolean.FALSE;

	private ItemStack powerUpItem;
	private ItemStack checkpointItem;

	private LanguageMessages language;

	private List<Schedule> schedules;

	private Boolean useLastLocation;

	private Integer numberOfTriesBeforeCancelling;

	private boolean mysqlEnabled;

	private String mysqlHost;

	private String mysqlDatabase;

	private String mysqlUsername;

	private String mysqlPassword;

	private Integer mysqlPort;

	private HikariCP hikari;

	private boolean mysqlUUIDMode;

	private boolean debugMode;

	private Integer secondsCheckPlayers;

	private List<String> allowedCmds;

	private boolean forceEmptyInventoryToJoin;

	private BannedPlayers bannedPlayers;

	private Integer idleTimeForDamage;

	private boolean inventoryManagement;

	private boolean dropItemsAfterDie;

	private Integer numberOfSecondsWithGems;

	private Integer numberOfGems;

	private int warmupTimeTNTRUN;

	private Integer secondsToStartMatch;

	private boolean highestPriorityDamageEvents;

	private boolean optionalTitles;

	private Integer maxItemOnChests;

	private Integer minItemOnChests;

	private boolean showBorders;

	private boolean useParticles;

	private Double particleSize;

	private String particleDeath;

	private String particleTNTTag;

	private String particleType;

	private Double particleRadius;

	private Double particleRadiusRate;

	private Double particleRadius2;

	private Double particleRate;

	private Double particleRateChange;

	private Double particleHeight;

	private Double particleExtension;

	private boolean useScoreboard;

	private List<String> commandsOnMatchEnd;

	private List<String> commandsOnUserLeave;

	private boolean advancedSpectatorMode;

	private ItemStack statsFill;

	private int statsBR;

	private int statsBRT2;

	private int statsLJ;

	private int statsTKLL;

	private int statsTKLLT2;

	private int statsKBD;

	private int statsEARR;

	private int statsGEMC;

	private int statsBOMB;

	private int statsBOAT_RUN;

	private int statsHORSE_RUN;

	private int statsESCAPE_FROM_BEAST;

	private int statsRACE;

	private int statsTNTRUN;

	private int statsSPLEEF;

	private int statsSPLEGG;

	private int statsOITC;

	private int statsSG;

	private int statsTSG;

	private int statsSW;

	private int statsTSW;

	private int statsALLTIME;

	private int statsANVIL_SPLEEF;
	private int statsWDROP;

	private int arrowRainDamage;

	private int tntTagSpeedRunners;

	private int tntTagSpeedHolder;

	private boolean waterKillKnockbackDuel;

	private boolean oitcHealAfterKill;

	private boolean cooldownAfterDeath;

	private boolean forcePlayersToEnter;

	private boolean topKillerHealAfterKill;

	private int cooldownUsersBeginEvents;

	private boolean forcePlayersToSpectate;

	private ItemStack vanishItem;
	private ItemStack endVanishItem;

	private Double sgAreaDamage;

	private boolean globalCooldown;

	private String cmdAlias;

	private boolean needPasswordToJoin;

	private String useEncoding;

	private boolean snowballSpleef;

	private boolean waterKillSG;

	private boolean waterKillSW;

	private int speedDuration;

	private int mysqlMaxLifeTime;

	private double quakeShootCooldown;

	private double quakeJumpCooldown;

	private int quakeShootDistance;

	private boolean quakeGiveDefaultWeapon;
	private boolean paintGiveDefaultWeapon;

	private int statsQUAKE;

	private int statsPBALL;

	private int statsKOTH;

	private int statsFISHSLAP;

	private int statsHOE;

	private int splatoonPaint;

	private int splatoonRadius;

	private int statsSPLATOON;

	private int statsBOMBARDMENT;

	private int statsSize;

	private Double bombardmentBombSpeed;

	private Double bombardmentBombDirection;

	private int cooldownAfterDeathSeconds;

	private int splatoonEggDamage;

	private Scoreboard colorBoard;

	private boolean matchPrivateMatch;
	
	private NameTagHook nametagHook;

	public void onEnable() {
		this.api = new API1711("%%__USER__%%", "RandomEvents");
		loadConfig();
		this.editando = new ArrayList<String>();
		this.comandosExecutor = new ComandosExecutor();
		this.cooldowns = new HashMap<String, Date>();

		this.powerUpItem = new ItemStack(XMaterial.EMERALD.parseMaterial());
		ItemMeta itemMeta = this.powerUpItem.getItemMeta();
		itemMeta.setDisplayName("§2§lPowerUP");
		this.powerUpItem.setItemMeta(itemMeta);

		inicializaVariables();

		if (mysqlEnabled) {
			hikari = new HikariCP(mysqlHost, mysqlPort.toString(), mysqlDatabase, mysqlUsername, mysqlPassword,
					mysqlMaxLifeTime);
			UtilsSQL.checkTables(this);
		}

		getServer().getPluginManager().registerEvents((Listener) new Quit(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Chat(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new GUI(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Death(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Join(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Use(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new PickUp(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Move(this), (Plugin) this);

		if (getServer().getPluginManager().getPlugin("CrackShot") != null) {
			getServer().getPluginManager().registerEvents((Listener) new WeaponShoot(this), (Plugin) this);
			System.out.println("[RandomEvents] CrackShot hooked succesfully!");

		}
		
		if (getServer().getPluginManager().getPlugin("NametagEdit") != null) {
			nametagHook=new NameTagHook(this);
		}

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			try {
				new ReventPlaceholder(this).register();
				System.out.println("[RandomEvents] PlaceholderAPI hooked succesfully!");

			} catch (Exception e) {
				System.out.println("[RandomEvents] PlaceholderAPI hook failed!");
			}
		}

		getLogger().info(" Author adri1711- activado");
		comienzaTemporizador();

		Bukkit.getServer().getScheduler().runTaskLaterAsynchronously((Plugin) this, new Runnable() {

			@Override
			public void run() {
				setWaterDrops(UtilsRandomEvents.cargarWaterDrops(getPlugin()));
				setKits(UtilsRandomEvents.cargarKits(getPlugin()));
				setMatches(UtilsRandomEvents.cargarPartidas(getPlugin()));
				matchesAvailable = new ArrayList<Match>();
				for (Match match : matches) {
					if (match.getEnabled() == null || match.getEnabled()) {
						matchesAvailable.add(match);
					}
				}
				setSpawn(new Location(Bukkit.getWorld(getConfig().getString("spawn.world")),
						getConfig().getDouble("spawn.x"), getConfig().getDouble("spawn.y"),
						getConfig().getDouble("spawn.z"),
						Double.valueOf(getConfig().getDouble("spawn.yaw")).floatValue(),
						Double.valueOf(getConfig().getDouble("spawn.pitch")).floatValue()));

				getTournament().setPlayerSpawn(new Location(
						Bukkit.getWorld(getConfig().getString("tournament.spawn.world")),
						getConfig().getDouble("tournament.spawn.x"), getConfig().getDouble("tournament.spawn.y"),
						getConfig().getDouble("tournament.spawn.z"),
						Double.valueOf(getConfig().getDouble("tournament.spawn.yaw")).floatValue(),
						Double.valueOf(getConfig().getDouble("tournament.spawn.pitch")).floatValue()));
			}

		}, 1200);
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
        colorBoard = manager.getMainScoreboard();

	}

	public void onDisable() {
		UtilsRandomEvents.terminaCreacionBannedPlayers(this, bannedPlayers);
		if (mysqlEnabled && hikari != null) {
			hikari.close();
		}
		if (matchActive != null) {
			matchActive.reiniciaValoresPartida(false);
		}
		getServer().getScheduler().cancelTasks((Plugin) this);
		getLogger().info(" Author adri1711 - desactivado");
	}

	public void inicializaVariables() {
		updateConfig();

		this.language = new LanguageMessages(this);

		this.random = new Random();

		this.tournament = new Tournament();

		this.warmupTimeTNTRUN = getConfig().getInt("warmupTimeTNTRUN");
		this.numberOfTriesBeforeCancelling = getConfig().getInt("numberOfTriesBeforeCancelling");
		tournament.setMaxPlayers(getConfig().getInt("tournament.maxPlayers"));
		tournament.setMinPlayers(getConfig().getInt("tournament.minPlayers"));
		tournament.setNumberOfRounds(getConfig().getInt("tournament.numberOfRounds"));
		tournament.setRewards(getConfig().getStringList("tournament.rewards"));

		this.spawn = new Location(Bukkit.getWorld(getConfig().getString("spawn.world")),
				getConfig().getDouble("spawn.x"), getConfig().getDouble("spawn.y"), getConfig().getDouble("spawn.z"),
				Double.valueOf(getConfig().getDouble("spawn.yaw")).floatValue(),
				Double.valueOf(getConfig().getDouble("spawn.pitch")).floatValue());

		tournament.setPlayerSpawn(new Location(Bukkit.getWorld(getConfig().getString("tournament.spawn.world")),
				getConfig().getDouble("tournament.spawn.x"), getConfig().getDouble("tournament.spawn.y"),
				getConfig().getDouble("tournament.spawn.z"),
				Double.valueOf(getConfig().getDouble("tournament.spawn.yaw")).floatValue(),
				Double.valueOf(getConfig().getDouble("tournament.spawn.pitch")).floatValue()));

		this.minPlayers = Integer.valueOf(getConfig().getInt("minPlayers"));
		this.idleTimeForDamage = Integer.valueOf(getConfig().getInt("idleTimeForDamage"));
		this.sgAreaDamage = Double.valueOf(getConfig().getDouble("sgAreaDamage"));

		this.bombardmentBombSpeed = Double.valueOf(getConfig().getDouble("bombardmentBombSpeed"));
		this.bombardmentBombDirection = Double.valueOf(getConfig().getDouble("bombardmentBombDirection"));

		this.needPasswordToJoin = getConfig().getBoolean("needPasswordToJoin");
		this.globalCooldown = getConfig().getBoolean("globalCooldown");
		this.inventoryManagement = getConfig().getBoolean("inventoryManagement");
		this.dropItemsAfterDie = getConfig().getBoolean("dropItemsAfterDie");
		this.advancedSpectatorMode = getConfig().getBoolean("advancedSpectatorMode");
		this.commandsOnUserJoin = (List<String>) getConfig().getStringList("commandsOnUserJoin");
		this.commandsOnMatchBegin = (List<String>) getConfig().getStringList("commandsOnMatchBegin");
		this.commandsOnMatchEnd = (List<String>) getConfig().getStringList("commandsOnMatchEnd");
		this.commandsOnUserLeave = (List<String>) getConfig().getStringList("commandsOnUserLeave");
		this.cooldownUsersBeginEvents = getConfig().getInt("cooldownUsersBeginEvents");
		this.forcePlayersToEnter = getConfig().getBoolean("forcePlayersToEnter");
		this.forcePlayersToSpectate = getConfig().getBoolean("forcePlayersToSpectate");
		this.topKillerHealAfterKill = getConfig().getBoolean("topKillerHealAfterKill");
		this.quakeGiveDefaultWeapon = getConfig().getBoolean("quakeGiveDefaultWeapon");
		this.paintGiveDefaultWeapon = getConfig().getBoolean("paintGiveDefaultWeapon");
		this.matchPrivateMatch = getConfig().getBoolean("matchPrivateMatch");
		
		

		this.quakeShootCooldown = getConfig().getDouble("quakeShootCooldown");
		this.quakeJumpCooldown = getConfig().getDouble("quakeJumpCooldown");
		this.quakeShootDistance = getConfig().getInt("quakeShootDistance");
		this.splatoonPaint = getConfig().getInt("splatoonPaint");
		this.splatoonRadius = getConfig().getInt("splatoonRadius");
		this.cooldownAfterDeathSeconds = getConfig().getInt("cooldownAfterDeathSeconds");
		this.splatoonEggDamage = getConfig().getInt("splatoonEggDamage");

		this.useEncoding = getConfig().getString("useEncoding");
		if (useEncoding.equals("UTF_8")) {
			useEncoding = "UTF-8";
		}
		this.cmdAlias = getConfig().getString("cmdAlias");

		Material mat = null;
		String statsMenuFill = getConfig().getString("statsmenu.fill");
		Integer data = null;
		if (statsMenuFill.contains(":")) {
			data = Integer.valueOf(statsMenuFill.split(":")[1]);
			statsMenuFill = statsMenuFill.split(":")[0];
		}
		try {
			mat = Material.valueOf(statsMenuFill);
		} catch (Exception e) {
			if (mat == null) {
				mat = XMaterial.WHITE_STAINED_GLASS_PANE.parseMaterial();
			}
		}

		Material checkpointMat = null;
		try {
			checkpointMat = Material.valueOf(getConfig().getString("checkpointItem"));
		} catch (Exception e) {
			if (checkpointMat == null) {
				checkpointMat = XMaterial.BLAZE_ROD.parseMaterial();
			}
		}
		this.checkpointItem = new ItemStack(checkpointMat);

		Material matVanish = null;
		String vanishMaterial = getConfig().getString("vanishItem");
		Integer dataVanish = null;
		if (vanishMaterial.contains(":")) {
			dataVanish = Integer.valueOf(vanishMaterial.split(":")[1]);
			vanishMaterial = vanishMaterial.split(":")[0];
		}
		try {
			matVanish = Material.valueOf(vanishMaterial);
		} catch (Exception e) {
			if (matVanish == null) {
				matVanish = XMaterial.ENDER_EYE.parseMaterial();
			}
		}
		ItemMeta itemMetaCheck = this.checkpointItem.getItemMeta();
		itemMetaCheck.setDisplayName(language.getItemReturnCheckpoint());
		this.checkpointItem.setItemMeta(itemMetaCheck);

		this.vanishItem = new ItemStack(matVanish);
		if (dataVanish != null) {
			vanishItem.setDurability(dataVanish.shortValue());
			try {
				vanishItem.getData().setData(dataVanish.byteValue());
			} catch (Throwable e) {

			}
		}
		this.endVanishItem = new ItemStack(matVanish);
		if (dataVanish != null) {
			endVanishItem.setDurability(dataVanish.shortValue());
			try {
				endVanishItem.getData().setData(dataVanish.byteValue());
			} catch (Throwable e) {

			}
		}
		ItemMeta itemVanishItem = this.vanishItem.getItemMeta();
		itemVanishItem.setDisplayName(language.getItemHidePlayer());
		this.vanishItem.setItemMeta(itemVanishItem);

		ItemMeta itemEndVanishMeta = this.endVanishItem.getItemMeta();
		itemEndVanishMeta.setDisplayName(language.getItemShowPlayer());
		this.endVanishItem.setItemMeta(itemEndVanishMeta);

		this.oitcHealAfterKill = getConfig().getBoolean("oitcHealAfterKill");
		this.cooldownAfterDeath = getConfig().getBoolean("cooldownAfterDeath");
		this.tntTagSpeedRunners = getConfig().getInt("tntTagSpeedRunners");

		this.tntTagSpeedHolder = getConfig().getInt("tntTagSpeedHolder");
		this.arrowRainDamage = getConfig().getInt("arrowRainDamage");
		this.waterKillKnockbackDuel = getConfig().getBoolean("waterKillKnockbackDuel");
		this.snowballSpleef = getConfig().getBoolean("snowballSpleef");
		this.waterKillSG = getConfig().getBoolean("waterKillSG");
		this.waterKillSW = getConfig().getBoolean("waterKillSW");
		this.speedDuration = getConfig().getInt("speedDuration");

		this.statsFill = new ItemStack(mat);
		if (data != null) {
			statsFill.setDurability(data.shortValue());
			try {
				statsFill.getData().setData(data.byteValue());
			} catch (Throwable e) {

			}
		}

		this.statsALLTIME = getConfig().getInt("statsmenu.ALLTIME");
		this.statsBR = getConfig().getInt("statsmenu.BR");
		this.statsBRT2 = getConfig().getInt("statsmenu.BRT2");
		this.statsLJ = getConfig().getInt("statsmenu.LJ");
		this.statsTKLL = getConfig().getInt("statsmenu.TKLL");
		this.statsTKLLT2 = getConfig().getInt("statsmenu.TKLLT2");
		this.statsKBD = getConfig().getInt("statsmenu.KBD");
		this.statsEARR = getConfig().getInt("statsmenu.EARR");
		this.statsGEMC = getConfig().getInt("statsmenu.GEMC");
		this.statsBOMB = getConfig().getInt("statsmenu.BOMB");
		this.statsBOAT_RUN = getConfig().getInt("statsmenu.BOAT_RUN");
		this.statsHORSE_RUN = getConfig().getInt("statsmenu.HORSE_RUN");
		this.statsESCAPE_FROM_BEAST = getConfig().getInt("statsmenu.ESCAPE_FROM_BEAST");
		this.statsRACE = getConfig().getInt("statsmenu.RACE");
		this.statsTNTRUN = getConfig().getInt("statsmenu.TNTRUN");
		this.statsSPLEEF = getConfig().getInt("statsmenu.SPLEEF");
		this.statsSPLEGG = getConfig().getInt("statsmenu.SPLEGG");
		this.statsOITC = getConfig().getInt("statsmenu.OITC");
		this.statsSG = getConfig().getInt("statsmenu.SG");
		this.statsTSG = getConfig().getInt("statsmenu.TSG");
		this.statsSW = getConfig().getInt("statsmenu.SW");
		this.statsTSW = getConfig().getInt("statsmenu.TSW");
		this.statsANVIL_SPLEEF = getConfig().getInt("statsmenu.ANVIL_SPLEEF");
		this.statsWDROP = getConfig().getInt("statsmenu.WDROP");
		this.statsQUAKE = getConfig().getInt("statsmenu.QUAKE");
		this.statsPBALL = getConfig().getInt("statsmenu.PBALL");
		this.statsKOTH = getConfig().getInt("statsmenu.KOTH");
		this.statsFISHSLAP = getConfig().getInt("statsmenu.FISHSLAP");
		this.statsHOE = getConfig().getInt("statsmenu.HOE");
		this.statsSPLATOON = getConfig().getInt("statsmenu.SPLATOON");
		this.statsBOMBARDMENT = getConfig().getInt("statsmenu.BOMBARDMENT");
		this.statsSize = getConfig().getInt("statsmenu.size");

		this.allowedCmds = (List<String>) getConfig().getStringList("allowedCmds");

		this.maxItemOnChests = Integer.valueOf(getConfig().getInt("maxItemOnChests"));
		this.minItemOnChests = Integer.valueOf(getConfig().getInt("minItemOnChests"));
		this.secondsToStartMatch = Integer.valueOf(getConfig().getInt("secondsToStartMatch"));
		if (secondsToStartMatch == null) {
			secondsToStartMatch = 0;
		} else {
			secondsToStartMatch = secondsToStartMatch - 3;
			if (secondsToStartMatch < 0) {
				secondsToStartMatch = 0;
			}
		}

		this.particleDeath = (getConfig().getString("particleDeath"));
		this.particleTNTTag = (getConfig().getString("particleTNTTag"));

		this.particleType = (getConfig().getString("particle.type"));

		this.particleSize = Double.valueOf(getConfig().getDouble("particle.size"));
		this.particleRadius = Double.valueOf(getConfig().getDouble("particle.radius"));
		this.particleRadiusRate = Double.valueOf(getConfig().getDouble("particle.radiusRate"));
		this.particleRadius2 = Double.valueOf(getConfig().getDouble("particle.radius2"));
		this.particleRate = Double.valueOf(getConfig().getDouble("particle.rate"));
		this.particleRateChange = Double.valueOf(getConfig().getDouble("particle.rateChange"));
		this.particleHeight = Double.valueOf(getConfig().getDouble("particle.height"));
		this.particleExtension = Double.valueOf(getConfig().getDouble("particle.extension"));

		this.secondsTimer = Integer.valueOf(getConfig().getInt("secondsTimer"));
		this.secondsCheckPlayers = Integer.valueOf(getConfig().getInt("secondsCheckPlayers"));
		if (secondsCheckPlayers == null) {
			secondsCheckPlayers = 15;
		}

		this.numberOfSecondsWithGems = Integer.valueOf(getConfig().getInt("numberOfSecondsWithGems"));
		this.numberOfGems = Integer.valueOf(getConfig().getInt("numberOfGems"));

		this.probabilityRandomEventTournament = Integer.valueOf(getConfig().getInt("probabilityRandomEventTournament"));
		this.probabilityRandomEvent = Integer.valueOf(getConfig().getInt("probabilityRandomEvent"));

		this.highestPriorityDamageEvents = getConfig().getBoolean("highestPriorityDamageEvents");

		this.debugMode = getConfig().getBoolean("debugMode");

		this.useLastLocation = getConfig().getBoolean("useLastLocation");
		this.optionalTitles = getConfig().getBoolean("optionalTitles");
		this.showBorders = getConfig().getBoolean("showBorders");
		this.useParticles = getConfig().getBoolean("useParticles");
		this.useScoreboard = getConfig().getBoolean("useScoreboard");
		this.setProbabilityPowerUp(Integer.valueOf(getConfig().getInt("probabilityPowerUp")));

		this.waterDrops = UtilsRandomEvents.cargarWaterDrops(this);
		this.kits = UtilsRandomEvents.cargarKits(this);
		this.matches = UtilsRandomEvents.cargarPartidas(this);

		matchesAvailable = new ArrayList<Match>();
		for (Match match : matches) {
			if (match.getEnabled() == null || match.getEnabled()) {
				matchesAvailable.add(match);
			}
		}

		this.bannedPlayers = UtilsRandomEvents.cargarBannedPlayers(this);
		if (bannedPlayers == null || bannedPlayers.getBannedPlayers() == null) {
			bannedPlayers = new BannedPlayers();
		}
		this.schedules = UtilsRandomEvents.cargarSchedules(this);

		Date now = new Date();
		long nowLong = now.getTime();
		List<String> pl = new ArrayList<String>();
		for (Entry<String, Long> entrada : bannedPlayers.getBannedPlayers().entrySet()) {
			if (entrada.getValue() < nowLong) {
				pl.add(entrada.getKey());
			}
		}
		for (String p : pl) {
			bannedPlayers.getBannedPlayers().remove(p);
		}

		this.playerMatches = new HashMap<String, Match>();
		this.playersCreation = new HashMap<String, Integer>();
		this.playerWaterDrop = new HashMap<String, WaterDropStep>();
		this.playersCreationWaterDrop = new HashMap<String, Integer>();

		this.playerKit = new HashMap<String, Kit>();
		this.playersCreationKit = new HashMap<String, Integer>();

		this.playersEntity = new HashMap<String, EntityType>();
		this.forceEmptyInventoryToJoin = getConfig().getBoolean("forceEmptyInventoryToJoin");
		this.mysqlEnabled = getConfig().getBoolean("mysql.enabled");
		this.mysqlUUIDMode = getConfig().getBoolean("mysql.UUIDMode");
		this.mysqlHost = getConfig().getString("mysql.host");
		this.mysqlDatabase = getConfig().getString("mysql.database");
		this.mysqlUsername = getConfig().getString("mysql.username");
		this.mysqlPassword = getConfig().getString("mysql.password");
		this.mysqlPort = getConfig().getInt("mysql.port");
		this.mysqlMaxLifeTime = getConfig().getInt("mysql.maxLifeTime");

		int pluginId = 8944;
		Metrics metrics = new Metrics(this, pluginId);

	}

	public void comienzaTemporizador() {
		if (!forzado) {
			matchActive = null;
			tournamentActive = null;
			Bukkit.getServer().getScheduler().runTaskLater((Plugin) this, new Runnable() {
				public void run() {

					if (matchesAvailable != null && !matchesAvailable.isEmpty()
							&& Bukkit.getOnlinePlayers().size() >= minPlayers) {
						if (!forzado) {

							if (probabilityRandomEvent > random.nextInt(100)) {
								if (probabilityRandomEventTournament > random.nextInt(100)) {
									tournamentActive = new TournamentActive(tournament, getPlugin(), false);
								} else {
									matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
											matchesAvailable, false);
								}
							} else {
								if (getSchedules() != null && !getSchedules().isEmpty()) {
									Calendar c = Calendar.getInstance();
									c.setTime(new Date());
									Schedule sched = UtilsRandomEvents.findSchedule(getPlugin(),
											c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.HOUR_OF_DAY),
											c.get(Calendar.MINUTE));
									if (sched != null) {
										if (sched.getMatchName() != null && !sched.getMatchName().isEmpty()) {
											Match match = UtilsRandomEvents.findMatch(getPlugin(),
													sched.getMatchName());
											if (match != null) {
												matchActive = new MatchActive(match, getPlugin(), false);
											} else {
												matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
														matchesAvailable, false);
											}
										} else {
											matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
													matchesAvailable, false);
										}
									} else {
										comienzaTemporizador();

									}
								} else {

									comienzaTemporizador();
								}
							}
						}
					} else {
						comienzaTemporizador();
					}
				}
			}, 20 * secondsTimer);
		}
	}

	protected RandomEvents getPlugin() {

		return this;
	}

	public void doingReload() {
		loadConfig();
		inicializaVariables();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (label.equalsIgnoreCase(Comandos.COMANDO_ALIASE1) || label.equalsIgnoreCase(Comandos.COMANDO_ALIASE2)) {
			switch (args.length) {
			case 0:
				Comandos.muestraMenu(this, player);
				break;
			case 1:
				Comandos.ejecutaComandoSimple(this, player, args);
				break;
			case 2:
				Comandos.ejecutaComandoDosArgumentos(this, player, args);
				break;
			case 3:
				Comandos.ejecutaComandoTresArgumentos(this, player, args);
				break;
			case 4:
				Comandos.ejecutaComandoCuatroArgumentos(this, player, args);
				break;
			case 5:
				Comandos.ejecutaComandoCincoArgumentos(this, player, args);
				break;
			default:
				if (player != null) {
					player.sendMessage(getLanguage().getTagPlugin() + language.getInvalidCmd());
				} else {
					System.out.println(getLanguage().getTagPlugin() + language.getInvalidCmd());
				}
				break;

			}
		}

		return true;
	}

	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

	public void defaultConfiguration() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public boolean loadConfig() {
		if (!(new File(getDataFolder() + File.separator + "config.yml")).exists()) {
			saveDefaultConfig();
		}

		try {
			(new YamlConfiguration()).load(new File(getDataFolder() + File.separator + "config.yml"));
		} catch (Exception e) {
			System.out.println("--- --- RandomEvents --- ---");
			System.out.println("There was an error loading your configuration.");
			System.out.println("A detailed description of your error is shown below.");
			System.out.println("--- --- --- ---");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin((Plugin) this);

			return false;
		}
		reloadConfig();

		loadConfiguration();
		return true;
	}

	public void updateConfig() {

		saveResource("defaultConfig.yml", false);

		try {
			if (new File(this.getDataFolder() + File.separator + "defaultConfig.yml").exists()) {

				YamlConfiguration cfg = new YamlConfiguration();
				cfg.load(new File(this.getDataFolder() + File.separator + "defaultConfig.yml"));

				if (new File(this.getDataFolder() + File.separator + "config.yml").exists()) {
					boolean changesMade = false;
					YamlConfiguration tmp = new YamlConfiguration();
					tmp.load(this.getDataFolder() + File.separator + "config.yml");
					for (String str : cfg.getKeys(true)) {
						if (!tmp.getKeys(true).contains(str)) {
							tmp.set(str, cfg.get(str));
							changesMade = true;
						}
					}
					if (changesMade)
						tmp.save(this.getDataFolder() + File.separator + "config.yml");
				}
				File defaultConfig = new File(getDataFolder() + File.separator + "defaultConfig.yml");
				if (defaultConfig.exists()) {
					defaultConfig.delete();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public ComandosExecutor getComandosExecutor() {
		return comandosExecutor;
	}

	public void setComandosExecutor(ComandosExecutor comandosExecutor) {
		this.comandosExecutor = comandosExecutor;
	}

	public API1711 getApi() {
		return api;
	}

	public void setApi(API1711 api) {
		this.api = api;
	}

	public MatchActive getMatchActive() {
		return matchActive;
	}

	public void setMatchActive(MatchActive matchActive) {
		this.matchActive = matchActive;
	}

	public Map<String, Match> getPlayerMatches() {
		return playerMatches;
	}

	public void setPlayerMatches(Map<String, Match> playerMatches) {
		this.playerMatches = playerMatches;
	}

	public Map<String, Integer> getPlayersCreation() {
		return playersCreation;
	}

	public void setPlayersCreation(Map<String, Integer> playersCreation) {
		this.playersCreation = playersCreation;
	}

	public Map<String, WaterDropStep> getPlayerWaterDrop() {
		return playerWaterDrop;
	}

	public void setPlayerWaterDrop(Map<String, WaterDropStep> playerWaterDrop) {
		this.playerWaterDrop = playerWaterDrop;
	}

	public Map<String, Integer> getPlayersCreationWaterDrop() {
		return playersCreationWaterDrop;
	}

	public void setPlayersCreationWaterDrop(Map<String, Integer> playersCreationWaterDrop) {
		this.playersCreationWaterDrop = playersCreationWaterDrop;
	}

	public Integer getSecondsTimer() {
		return secondsTimer;
	}

	public void setSecondsTimer(Integer secondsTimer) {
		this.secondsTimer = secondsTimer;
	}

	public Integer getProbabilityRandomEvent() {
		return probabilityRandomEvent;
	}

	public void setProbabilityRandomEvent(Integer probabilityRandomEvent) {
		this.probabilityRandomEvent = probabilityRandomEvent;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public Map<String, EntityType> getPlayersEntity() {
		return playersEntity;
	}

	public void setPlayersEntity(Map<String, EntityType> playersEntity) {
		this.playersEntity = playersEntity;
	}

	public Integer getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(Integer minPlayers) {
		this.minPlayers = minPlayers;
	}

	public List<String> getCommandsOnUserJoin() {
		return commandsOnUserJoin;
	}

	public void setCommandsOnUserJoin(List<String> commandsOnUserJoin) {
		this.commandsOnUserJoin = commandsOnUserJoin;
	}

	public List<String> getCommandsOnMatchBegin() {
		return commandsOnMatchBegin;
	}

	public void setCommandsOnMatchBegin(List<String> commandsOnMatchBegin) {
		this.commandsOnMatchBegin = commandsOnMatchBegin;
	}

	public void reiniciaPartida(Boolean forzada) {
		if (forzada) {
			forzado = Boolean.FALSE;
		}
		comienzaTemporizador();
	}

	public Boolean getForzado() {
		return forzado;
	}

	public void setForzado(Boolean forzado) {
		this.forzado = forzado;
	}

	public Integer getProbabilityPowerUp() {
		return probabilityPowerUp;
	}

	public void setProbabilityPowerUp(Integer probabilityPowerUp) {
		this.probabilityPowerUp = probabilityPowerUp;
	}

	public ItemStack getPowerUpItem() {
		return powerUpItem;
	}

	public void setPowerUpItem(ItemStack powerUpItem) {
		this.powerUpItem = powerUpItem;
	}

	public LanguageMessages getLanguage() {
		return language;
	}

	public void setLanguage(LanguageMessages language) {
		this.language = language;
	}

	public Integer getProbabilityRandomEventTournament() {
		return probabilityRandomEventTournament;
	}

	public void setProbabilityRandomEventTournament(Integer probabilityRandomEventTournament) {
		this.probabilityRandomEventTournament = probabilityRandomEventTournament;
	}

	public TournamentActive getTournamentActive() {
		return tournamentActive;
	}

	public void setTournamentActive(TournamentActive tournamentActive) {
		this.tournamentActive = tournamentActive;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Boolean getUseLastLocation() {
		return useLastLocation;
	}

	public void setUseLastLocation(Boolean useLastLocation) {
		this.useLastLocation = useLastLocation;
	}

	public Integer getNumberOfTriesBeforeCancelling() {
		return numberOfTriesBeforeCancelling;
	}

	public void setNumberOfTriesBeforeCancelling(Integer numberOfTriesBeforeCancelling) {
		this.numberOfTriesBeforeCancelling = numberOfTriesBeforeCancelling;
	}

	public boolean isMysqlEnabled() {
		return mysqlEnabled;
	}

	public void setMysqlEnabled(boolean mysqlEnabled) {
		this.mysqlEnabled = mysqlEnabled;
	}

	public String getMysqlHost() {
		return mysqlHost;
	}

	public void setMysqlHost(String mysqlHost) {
		this.mysqlHost = mysqlHost;
	}

	public String getMysqlDatabase() {
		return mysqlDatabase;
	}

	public void setMysqlDatabase(String mysqlDatabase) {
		this.mysqlDatabase = mysqlDatabase;
	}

	public String getMysqlUsername() {
		return mysqlUsername;
	}

	public void setMysqlUsername(String mysqlUsername) {
		this.mysqlUsername = mysqlUsername;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}

	public Integer getMysqlPort() {
		return mysqlPort;
	}

	public void setMysqlPort(Integer mysqlPort) {
		this.mysqlPort = mysqlPort;
	}

	public HikariCP getHikari() {
		return hikari;
	}

	public void setHikari(HikariCP hikari) {
		this.hikari = hikari;
	}

	public boolean isMysqlUUIDMode() {
		return mysqlUUIDMode;
	}

	public void setMysqlUUIDMode(boolean mysqlUUIDMode) {
		this.mysqlUUIDMode = mysqlUUIDMode;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public Integer getSecondsCheckPlayers() {

		return secondsCheckPlayers;
	}

	public void setSecondsCheckPlayers(Integer secondsCheckPlayers) {
		this.secondsCheckPlayers = secondsCheckPlayers;
	}

	public List<String> getAllowedCmds() {
		return allowedCmds;
	}

	public void setAllowedCmds(List<String> allowedCmds) {
		this.allowedCmds = allowedCmds;
	}

	public boolean isForceEmptyInventoryToJoin() {
		return forceEmptyInventoryToJoin;
	}

	public void setForceEmptyInventoryToJoin(boolean forceEmptyInventoryToJoin) {
		this.forceEmptyInventoryToJoin = forceEmptyInventoryToJoin;
	}

	public BannedPlayers getBannedPlayers() {
		return bannedPlayers;
	}

	public void setBannedPlayers(BannedPlayers bannedPlayers) {
		this.bannedPlayers = bannedPlayers;
	}

	public Integer getIdleTimeForDamage() {
		return idleTimeForDamage;
	}

	public void setIdleTimeForDamage(Integer idleTimeForDamage) {
		this.idleTimeForDamage = idleTimeForDamage;
	}

	public boolean isInventoryManagement() {
		return inventoryManagement;
	}

	public void setInventoryManagement(boolean inventoryManagement) {
		this.inventoryManagement = inventoryManagement;
	}

	public boolean isDropItemsAfterDie() {
		return dropItemsAfterDie;
	}

	public void setDropItemsAfterDie(boolean dropItemsAfterDie) {
		this.dropItemsAfterDie = dropItemsAfterDie;
	}

	public Integer getNumberOfSecondsWithGems() {
		return numberOfSecondsWithGems;
	}

	public void setNumberOfSecondsWithGems(Integer numberOfSecondsWithGems) {
		this.numberOfSecondsWithGems = numberOfSecondsWithGems;
	}

	public Integer getNumberOfGems() {
		return numberOfGems;
	}

	public void setNumberOfGems(Integer numberOfGems) {
		this.numberOfGems = numberOfGems;
	}

	public int getWarmupTimeTNTRUN() {
		return warmupTimeTNTRUN;
	}

	public void setWarmupTimeTNTRUN(int warmupTimeTNTRUN) {
		this.warmupTimeTNTRUN = warmupTimeTNTRUN;
	}

	public Integer getSecondsToStartMatch() {
		return secondsToStartMatch;
	}

	public void setSecondsToStartMatch(Integer secondsToStartMatch) {
		this.secondsToStartMatch = secondsToStartMatch;
	}

	public boolean isHighestPriorityDamageEvents() {
		return highestPriorityDamageEvents;
	}

	public void setHighestPriorityDamageEvents(boolean highestPriorityDamageEvents) {
		this.highestPriorityDamageEvents = highestPriorityDamageEvents;
	}

	public List<String> getEditando() {
		return editando;
	}

	public void setEditando(List<String> editando) {
		this.editando = editando;
	}

	public boolean isOptionalTitles() {
		return optionalTitles;
	}

	public void setOptionalTitles(boolean optionalTitles) {
		this.optionalTitles = optionalTitles;
	}

	public Integer getMaxItemOnChests() {
		return maxItemOnChests;
	}

	public void setMaxItemOnChests(Integer maxItemOnChests) {
		this.maxItemOnChests = maxItemOnChests;
	}

	public boolean isShowBorders() {
		return showBorders;
	}

	public void setShowBorders(boolean showBorders) {
		this.showBorders = showBorders;
	}

	public Integer getMinItemOnChests() {
		return minItemOnChests;
	}

	public void setMinItemOnChests(Integer minItemOnChests) {
		this.minItemOnChests = minItemOnChests;
	}

	public boolean isUseParticles() {
		return useParticles;
	}

	public void setUseParticles(boolean useParticles) {
		this.useParticles = useParticles;
	}

	public Double getParticleSize() {
		return particleSize;
	}

	public void setParticleSize(Double particleSize) {
		this.particleSize = particleSize;
	}

	public String getParticleDeath() {
		return particleDeath;
	}

	public void setParticleDeath(String particleDeath) {
		this.particleDeath = particleDeath;
	}

	public String getParticleTNTTag() {
		return particleTNTTag;
	}

	public void setParticleTNTTag(String particleTNTTag) {
		this.particleTNTTag = particleTNTTag;
	}

	public String getParticleType() {
		return particleType;
	}

	public void setParticleType(String particleType) {
		this.particleType = particleType;
	}

	public Double getParticleRadius() {
		return particleRadius;
	}

	public void setParticleRadius(Double particleRadius) {
		this.particleRadius = particleRadius;
	}

	public Double getParticleRadiusRate() {
		return particleRadiusRate;
	}

	public void setParticleRadiusRate(Double particleRadiusRate) {
		this.particleRadiusRate = particleRadiusRate;
	}

	public Double getParticleRadius2() {
		return particleRadius2;
	}

	public void setParticleRadius2(Double particleRadius2) {
		this.particleRadius2 = particleRadius2;
	}

	public Double getParticleRate() {
		return particleRate;
	}

	public void setParticleRate(Double particleRate) {
		this.particleRate = particleRate;
	}

	public Double getParticleRateChange() {
		return particleRateChange;
	}

	public void setParticleRateChange(Double particleRateChange) {
		this.particleRateChange = particleRateChange;
	}

	public Double getParticleHeight() {
		return particleHeight;
	}

	public void setParticleHeight(Double particleHeight) {
		this.particleHeight = particleHeight;
	}

	public Double getParticleExtension() {
		return particleExtension;
	}

	public void setParticleExtension(Double particleExtension) {
		this.particleExtension = particleExtension;
	}

	public boolean isUseScoreboard() {
		return useScoreboard;
	}

	public void setUseScoreboard(boolean useScoreboard) {
		this.useScoreboard = useScoreboard;
	}

	public ItemStack getCheckpointItem() {
		return checkpointItem;
	}

	public int getArrowRainDamage() {
		return arrowRainDamage;
	}

	public void setArrowRainDamage(int arrowRainDamage) {
		this.arrowRainDamage = arrowRainDamage;
	}

	public void setCheckpointItem(ItemStack checkpointItem) {
		this.checkpointItem = checkpointItem;
	}

	public List<String> getCommandsOnMatchEnd() {
		return commandsOnMatchEnd;
	}

	public void setCommandsOnMatchEnd(List<String> commandsOnMatchEnd) {
		this.commandsOnMatchEnd = commandsOnMatchEnd;
	}

	public List<String> getCommandsOnUserLeave() {
		return commandsOnUserLeave;
	}

	public void setCommandsOnUserLeave(List<String> commandsOnUserLeave) {
		this.commandsOnUserLeave = commandsOnUserLeave;
	}

	public List<Match> getMatchesAvailable() {
		return matchesAvailable;
	}

	public void setMatchesAvailable(List<Match> matchesAvailable) {
		this.matchesAvailable = matchesAvailable;
	}

	public boolean isAdvancedSpectatorMode() {
		return advancedSpectatorMode;
	}

	public void setAdvancedSpectatorMode(boolean advancedSpectatorMode) {
		this.advancedSpectatorMode = advancedSpectatorMode;
	}

	public ItemStack getStatsFill() {
		return statsFill;
	}

	public void setStatsFill(ItemStack statsFill) {
		this.statsFill = statsFill;
	}

	public int getStatsBR() {
		return statsBR;
	}

	public void setStatsBR(int statsBR) {
		this.statsBR = statsBR;
	}

	public int getStatsBRT2() {
		return statsBRT2;
	}

	public void setStatsBRT2(int statsBRT2) {
		this.statsBRT2 = statsBRT2;
	}

	public int getStatsLJ() {
		return statsLJ;
	}

	public void setStatsLJ(int statsLJ) {
		this.statsLJ = statsLJ;
	}

	public int getStatsTKLL() {
		return statsTKLL;
	}

	public void setStatsTKLL(int statsTKLL) {
		this.statsTKLL = statsTKLL;
	}

	public int getStatsTKLLT2() {
		return statsTKLLT2;
	}

	public void setStatsTKLLT2(int statsTKLLT2) {
		this.statsTKLLT2 = statsTKLLT2;
	}

	public int getStatsKBD() {
		return statsKBD;
	}

	public void setStatsKBD(int statsKBD) {
		this.statsKBD = statsKBD;
	}

	public int getStatsEARR() {
		return statsEARR;
	}

	public void setStatsEARR(int statsEARR) {
		this.statsEARR = statsEARR;
	}

	public int getStatsGEMC() {
		return statsGEMC;
	}

	public void setStatsGEMC(int statsGEMC) {
		this.statsGEMC = statsGEMC;
	}

	public int getStatsBOMB() {
		return statsBOMB;
	}

	public void setStatsBOMB(int statsBOMB) {
		this.statsBOMB = statsBOMB;
	}

	public int getStatsBOAT_RUN() {
		return statsBOAT_RUN;
	}

	public void setStatsBOAT_RUN(int statsBOAT_RUN) {
		this.statsBOAT_RUN = statsBOAT_RUN;
	}

	public int getStatsHORSE_RUN() {
		return statsHORSE_RUN;
	}

	public void setStatsHORSE_RUN(int statsHORSE_RUN) {
		this.statsHORSE_RUN = statsHORSE_RUN;
	}

	public int getStatsESCAPE_FROM_BEAST() {
		return statsESCAPE_FROM_BEAST;
	}

	public void setStatsESCAPE_FROM_BEAST(int statsESCAPE_FROM_BEAST) {
		this.statsESCAPE_FROM_BEAST = statsESCAPE_FROM_BEAST;
	}

	public int getStatsRACE() {
		return statsRACE;
	}

	public void setStatsRACE(int statsRACE) {
		this.statsRACE = statsRACE;
	}

	public int getStatsTNTRUN() {
		return statsTNTRUN;
	}

	public void setStatsTNTRUN(int statsTNTRUN) {
		this.statsTNTRUN = statsTNTRUN;
	}

	public int getStatsSPLEEF() {
		return statsSPLEEF;
	}

	public void setStatsSPLEEF(int statsSPLEEF) {
		this.statsSPLEEF = statsSPLEEF;
	}

	public int getStatsSPLEGG() {
		return statsSPLEGG;
	}

	public void setStatsSPLEGG(int statsSPLEGG) {
		this.statsSPLEGG = statsSPLEGG;
	}

	public int getStatsOITC() {
		return statsOITC;
	}

	public void setStatsOITC(int statsOITC) {
		this.statsOITC = statsOITC;
	}

	public int getStatsSG() {
		return statsSG;
	}

	public void setStatsSG(int statsSG) {
		this.statsSG = statsSG;
	}

	public int getStatsTSG() {
		return statsTSG;
	}

	public void setStatsTSG(int statsTSG) {
		this.statsTSG = statsTSG;
	}

	public int getStatsSW() {
		return statsSW;
	}

	public void setStatsSW(int statsSW) {
		this.statsSW = statsSW;
	}

	public int getStatsTSW() {
		return statsTSW;
	}

	public void setStatsTSW(int statsTSW) {
		this.statsTSW = statsTSW;
	}

	public int getStatsALLTIME() {
		return statsALLTIME;
	}

	public void setStatsALLTIME(int statsALLTIME) {
		this.statsALLTIME = statsALLTIME;
	}

	public int getStatsANVIL_SPLEEF() {
		return statsANVIL_SPLEEF;
	}

	public void setStatsANVIL_SPLEEF(int statsANVIL_SPLEEF) {
		this.statsANVIL_SPLEEF = statsANVIL_SPLEEF;
	}

	public List<WaterDropStep> getWaterDrops() {
		return waterDrops;
	}

	public void setWaterDrops(List<WaterDropStep> waterDrops) {
		this.waterDrops = waterDrops;
	}

	public int getStatsWDROP() {
		return statsWDROP;
	}

	public void setStatsWDROP(int statsWDROP) {
		this.statsWDROP = statsWDROP;
	}

	public int getTntTagSpeedRunners() {
		return tntTagSpeedRunners;
	}

	public void setTntTagSpeedRunners(int tntTagSpeedRunners) {
		this.tntTagSpeedRunners = tntTagSpeedRunners;
	}

	public int getTntTagSpeedHolder() {
		return tntTagSpeedHolder;
	}

	public void setTntTagSpeedHolder(int tntTagSpeedHolder) {
		this.tntTagSpeedHolder = tntTagSpeedHolder;
	}

	public boolean isWaterKillKnockbackDuel() {
		return waterKillKnockbackDuel;
	}

	public void setWaterKillKnockbackDuel(boolean waterKillKnockbackDuel) {
		this.waterKillKnockbackDuel = waterKillKnockbackDuel;
	}

	public boolean isOitcHealAfterKill() {
		return oitcHealAfterKill;
	}

	public void setOitcHealAfterKill(boolean oitcHealAfterKill) {
		this.oitcHealAfterKill = oitcHealAfterKill;
	}

	public boolean isCooldownAfterDeath() {
		return cooldownAfterDeath;
	}

	public void setCooldownAfterDeath(boolean cooldownAfterDeath) {
		this.cooldownAfterDeath = cooldownAfterDeath;
	}

	public boolean isForcePlayersToEnter() {
		return forcePlayersToEnter;
	}

	public void setForcePlayersToEnter(boolean forcePlayersToEnter) {
		this.forcePlayersToEnter = forcePlayersToEnter;
	}

	public boolean isTopKillerHealAfterKill() {
		return topKillerHealAfterKill;
	}

	public void setTopKillerHealAfterKill(boolean topKillerHealAfterKill) {
		this.topKillerHealAfterKill = topKillerHealAfterKill;
	}

	public Map<String, Date> getCooldowns() {
		return cooldowns;
	}

	public void setCooldowns(Map<String, Date> cooldowns) {
		this.cooldowns = cooldowns;
	}

	public int getCooldownUsersBeginEvents() {
		return cooldownUsersBeginEvents;
	}

	public void setCooldownUsersBeginEvents(int cooldownUsersBeginEvents) {
		this.cooldownUsersBeginEvents = cooldownUsersBeginEvents;
	}

	public boolean isForcePlayersToSpectate() {
		return forcePlayersToSpectate;
	}

	public void setForcePlayersToSpectate(boolean forcePlayersToSpectate) {
		this.forcePlayersToSpectate = forcePlayersToSpectate;
	}

	public ItemStack getVanishItem() {
		return vanishItem;
	}

	public void setVanishItem(ItemStack vanishItem) {
		this.vanishItem = vanishItem;
	}

	public ItemStack getEndVanishItem() {
		return endVanishItem;
	}

	public void setEndVanishItem(ItemStack endVanishItem) {
		this.endVanishItem = endVanishItem;
	}

	public Double getSgAreaDamage() {
		return sgAreaDamage;
	}

	public void setSgAreaDamage(Double sgAreaDamage) {
		this.sgAreaDamage = sgAreaDamage;
	}

	public boolean isGlobalCooldown() {
		return globalCooldown;
	}

	public void setGlobalCooldown(boolean globalCooldown) {
		this.globalCooldown = globalCooldown;
	}

	public String getCmdAlias() {
		return cmdAlias;
	}

	public void setCmdAlias(String cmdAlias) {
		this.cmdAlias = cmdAlias;
	}

	public boolean isNeedPasswordToJoin() {
		return needPasswordToJoin;
	}

	public void setNeedPasswordToJoin(boolean needPasswordToJoin) {
		this.needPasswordToJoin = needPasswordToJoin;
	}

	public String getUseEncoding() {
		return useEncoding;
	}

	public void setUseEncoding(String useEncoding) {
		this.useEncoding = useEncoding;
	}

	public boolean isSnowballSpleef() {
		return snowballSpleef;
	}

	public void setSnowballSpleef(boolean snowballSpleef) {
		this.snowballSpleef = snowballSpleef;
	}

	public boolean isWaterKillSG() {
		return waterKillSG;
	}

	public void setWaterKillSG(boolean waterKillSG) {
		this.waterKillSG = waterKillSG;
	}

	public boolean isWaterKillSW() {
		return waterKillSW;
	}

	public void setWaterKillSW(boolean waterKillSW) {
		this.waterKillSW = waterKillSW;
	}

	public int getSpeedDuration() {
		return speedDuration;
	}

	public void setSpeedDuration(int speedDuration) {
		this.speedDuration = speedDuration;
	}

	public int getMysqlMaxLifeTime() {
		return mysqlMaxLifeTime;
	}

	public void setMysqlMaxLifeTime(int mysqlMaxLifeTime) {
		this.mysqlMaxLifeTime = mysqlMaxLifeTime;
	}

	public List<Kit> getKits() {
		return kits;
	}

	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}

	public Map<String, Kit> getPlayerKit() {
		return playerKit;
	}

	public void setPlayerKit(Map<String, Kit> playerKit) {
		this.playerKit = playerKit;
	}

	public Map<String, Integer> getPlayersCreationKit() {
		return playersCreationKit;
	}

	public void setPlayersCreationKit(Map<String, Integer> playersCreationKit) {
		this.playersCreationKit = playersCreationKit;
	}

	public double getQuakeShootCooldown() {
		return quakeShootCooldown;
	}

	public void setQuakeShootCooldown(double quakeShootCooldown) {
		this.quakeShootCooldown = quakeShootCooldown;
	}

	public double getQuakeJumpCooldown() {
		return quakeJumpCooldown;
	}

	public void setQuakeJumpCooldown(double quakeJumpCooldown) {
		this.quakeJumpCooldown = quakeJumpCooldown;
	}

	public int getQuakeShootDistance() {
		return quakeShootDistance;
	}

	public void setQuakeShootDistance(int quakeShootDistance) {
		this.quakeShootDistance = quakeShootDistance;
	}

	public boolean isQuakeGiveDefaultWeapon() {
		return quakeGiveDefaultWeapon;
	}

	public void setQuakeGiveDefaultWeapon(boolean quakeGiveDefaultWeapon) {
		this.quakeGiveDefaultWeapon = quakeGiveDefaultWeapon;
	}

	public boolean isPaintGiveDefaultWeapon() {
		return paintGiveDefaultWeapon;
	}

	public void setPaintGiveDefaultWeapon(boolean hoeGiveDefaultWeapon) {
		this.paintGiveDefaultWeapon = hoeGiveDefaultWeapon;
	}

	public int getStatsQUAKE() {
		return statsQUAKE;
	}

	public void setStatsQUAKE(int statsQUAKE) {
		this.statsQUAKE = statsQUAKE;
	}

	public int getStatsPBALL() {
		return statsPBALL;
	}

	public void setStatsPBALL(int statsPBALL) {
		this.statsPBALL = statsPBALL;
	}

	public int getStatsKOTH() {
		return statsKOTH;
	}

	public void setStatsKOTH(int statsKOTH) {
		this.statsKOTH = statsKOTH;
	}

	public int getStatsFISHSLAP() {
		return statsFISHSLAP;
	}

	public void setStatsFISHSLAP(int statsFISHSLAP) {
		this.statsFISHSLAP = statsFISHSLAP;
	}

	public int getStatsHOE() {
		return statsHOE;
	}

	public void setStatsHOE(int statsHOE) {
		this.statsHOE = statsHOE;
	}

	public int getSplatoonPaint() {
		return splatoonPaint;
	}

	public void setSplatoonPaint(int splatoonPaint) {
		this.splatoonPaint = splatoonPaint;
	}

	public int getSplatoonRadius() {
		return splatoonRadius;
	}

	public void setSplatoonRadius(int splatoonRadius) {
		this.splatoonRadius = splatoonRadius;
	}

	public int getStatsSPLATOON() {
		return statsSPLATOON;
	}

	public void setStatsSPLATOON(int statsSPLATOON) {
		this.statsSPLATOON = statsSPLATOON;
	}

	public int getStatsBOMBARDMENT() {
		return statsBOMBARDMENT;
	}

	public void setStatsBOMBARDMENT(int statsBOMBARDMENT) {
		this.statsBOMBARDMENT = statsBOMBARDMENT;
	}

	public int getStatsSize() {
		return statsSize;
	}

	public void setStatsSize(int statsSize) {
		this.statsSize = statsSize;
	}

	public Double getBombardmentBombSpeed() {
		return bombardmentBombSpeed;
	}

	public void setBombardmentBombSpeed(Double bombardmentBombSpeed) {
		this.bombardmentBombSpeed = bombardmentBombSpeed;
	}

	public Double getBombardmentBombDirection() {
		return bombardmentBombDirection;
	}

	public void setBombardmentBombDirection(Double bombardmentBombDirection) {
		this.bombardmentBombDirection = bombardmentBombDirection;
	}

	public int getCooldownAfterDeathSeconds() {
		return cooldownAfterDeathSeconds;
	}

	public void setCooldownAfterDeathSeconds(int cooldownAfterDeathSeconds) {
		this.cooldownAfterDeathSeconds = cooldownAfterDeathSeconds;
	}

	public int getSplatoonEggDamage() {
		return splatoonEggDamage;
	}

	public void setSplatoonEggDamage(int splatoonEggDamage) {
		this.splatoonEggDamage = splatoonEggDamage;
	}

	public Scoreboard getColorBoard() {
		return colorBoard;
	}

	public void setColorBoard(Scoreboard colorBoard) {
		this.colorBoard = colorBoard;
	}

	public boolean isMatchPrivateMatch() {
		return matchPrivateMatch;
	}

	public void setMatchPrivateMatch(boolean matchPrivateMatch) {
		this.matchPrivateMatch = matchPrivateMatch;
	}

	public NameTagHook getNametagHook() {
		return nametagHook;
	}

	public void setNametagHook(NameTagHook nametagHook) {
		this.nametagHook = nametagHook;
	}
	
	

}
