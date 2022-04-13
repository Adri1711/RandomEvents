package com.adri1711.randomevents.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.language.LanguageMessages;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.Tournament;
import com.adri1711.randomevents.match.utils.BannedPlayers;
import com.adri1711.randomevents.metrics.Metrics;
import com.adri1711.randomevents.placeholders.ReventPlaceholder;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.XMaterial;

public class ReventConfig {

	private RandomEvents plugin;

	private Integer secondsTimer;

	private Integer minPlayers;

	private Integer probabilityRandomEvent;

	private Integer probabilityRandomEventTournament;

	private Integer probabilityPowerUp;

	private ItemStack powerUpItem;
	private ItemStack checkpointItem;

	private Integer invincibleAfterGame;

	private Boolean useLastLocation;

	private Integer numberOfTriesBeforeCancelling;
	
	private boolean avoidSnowballKnockback;
	private boolean avoidEggKnockback;

	private boolean mysqlEnabled;

	private String mysqlHost;

	private String mysqlDatabase;

	private String mysqlUsername;

	private String mysqlPassword;

	private Integer mysqlPort;

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
	private boolean forceGamemodeSurvival;
	private boolean showInfoMinigameOnJoin;
	

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
	private int statsREDGREEN;

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

	private List<String> cmdAlias;

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

	private boolean matchPrivateMatch;

	private boolean equilibrateTeams;

	private boolean forceNonEmptyTeams;

	private ItemStack kitsItem;

	private ItemStack teamItem;

	private int minNumberOfTriesBeforeBeginning;

	private List<String> commandsOnEventFire;

	private boolean waterKillBombardment;

	private int offSetYBombardment;

	private boolean disableMultipleWinners;

	private boolean randomDisguisePlayers;

	private Boolean isLibsDisguises;
	private Boolean isProtocolLib;
	private Boolean isCitizens;
	private Boolean isNoteBlockAPI;

	private String skinDisguisePlayers;

	private boolean infiniteSnowballs;

	private List<String> commandsOnUserJoin;
	private List<String> commandsOnKill;
	private List<String> commandsOnMatchBegin;

	private int snowballsDamage;

	private boolean waterKillSpleef;

	private boolean waterKillSplegg;

	private boolean waterKillTNTRun;

	private boolean waterKillAnvilSpleef;

	private boolean multipleKillOnExplosion;

	private Integer radiusOfTNTTagExplosion;

	private boolean useTitleWhenGetBomb;

	private boolean deactivateSounds;

	private int statsBRTEAMS;

	private int statsTKLLTEAMS;

	private int statsTSGTEAMS;

	private int statsTSWTEAMS;

	private int statsPBALLTK;

	private int distanceClearEntities;

	private int secondsCheckStopSong;

	private int probabilityPerCheckToStopSound;

	private int ticksAfterMusicStopToKill;

	private int invincibleAfterRespawn;

	private Boolean restrictWorlds;

	private List<String> allowedWorlds;
	
	private Boolean teamMatchRandomRespawn;

	public ReventConfig(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	public void inicializaVariables() {

		if (plugin.getServer().getPluginManager().getPlugin("LibsDisguises") != null) {
			setIsLibsDisguises(Boolean.TRUE);
			plugin.getLoggerP().info("[RandomEvents] LibsDisguises hooked succesfully!");

		} else {
			setIsLibsDisguises(Boolean.FALSE);

		}
		if (plugin.getServer().getPluginManager().getPlugin("Citizens") != null) {
			setIsCitizens(Boolean.TRUE);
			plugin.getLoggerP().info("[RandomEvents] Citizens hooked succesfully!");

		} else {
			setIsCitizens(Boolean.FALSE);

		}

		setIsNoteBlockAPI(Boolean.FALSE);
		if (Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI")) {
			setIsNoteBlockAPI(Boolean.TRUE);
			plugin.getLoggerP().info("[RandomEvents] NoteBlockAPI hooked succesfully!");
		}

		if (plugin.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			setIsProtocolLib(Boolean.TRUE);
			plugin.getLoggerP().info("[RandomEvents] ProtocolLib hooked succesfully!");

		} else {
			setIsProtocolLib(Boolean.FALSE);

		}

		this.powerUpItem = new ItemStack(XMaterial.EMERALD.parseMaterial());
		ItemMeta itemMeta = this.powerUpItem.getItemMeta();
		itemMeta.setDisplayName("§2§lPowerUP");
		this.powerUpItem.setItemMeta(itemMeta);

		plugin.updateConfig();

		plugin.setLanguage(new LanguageMessages(plugin));

		plugin.setRandom(new Random());
		Tournament tournament = new Tournament();
		tournament.setMaxPlayers(plugin.getConfig().getInt("tournament.maxPlayers"));
		tournament.setMinPlayers(plugin.getConfig().getInt("tournament.minPlayers"));
		tournament.setNumberOfRounds(plugin.getConfig().getInt("tournament.numberOfRounds"));
		tournament.setRewards(plugin.getConfig().getStringList("tournament.rewards"));

		tournament.setPlayerSpawn(new Location(Bukkit.getWorld(plugin.getConfig().getString("tournament.spawn.world")),
				plugin.getConfig().getDouble("tournament.spawn.x"), plugin.getConfig().getDouble("tournament.spawn.y"),
				plugin.getConfig().getDouble("tournament.spawn.z"),
				Double.valueOf(plugin.getConfig().getDouble("tournament.spawn.yaw")).floatValue(),
				Double.valueOf(plugin.getConfig().getDouble("tournament.spawn.pitch")).floatValue()));
		plugin.setTournament(tournament);

		this.warmupTimeTNTRUN = plugin.getConfig().getInt("warmupTimeTNTRUN");
		this.invincibleAfterGame = plugin.getConfig().getInt("invincibleAfterGame");
		this.numberOfTriesBeforeCancelling = plugin.getConfig().getInt("numberOfTriesBeforeCancelling");

		plugin.setSpawn(new Location(Bukkit.getWorld(plugin.getConfig().getString("spawn.world")),
				plugin.getConfig().getDouble("spawn.x"), plugin.getConfig().getDouble("spawn.y"),
				plugin.getConfig().getDouble("spawn.z"),
				Double.valueOf(plugin.getConfig().getDouble("spawn.yaw")).floatValue(),
				Double.valueOf(plugin.getConfig().getDouble("spawn.pitch")).floatValue()));

		this.minPlayers = Integer.valueOf(plugin.getConfig().getInt("minPlayers"));
		this.idleTimeForDamage = Integer.valueOf(plugin.getConfig().getInt("idleTimeForDamage"));
		this.sgAreaDamage = Double.valueOf(plugin.getConfig().getDouble("sgAreaDamage"));
		this.restrictWorlds = plugin.getConfig().getBoolean("restrictWorlds");
		this.teamMatchRandomRespawn = plugin.getConfig().getBoolean("teamMatchRandomRespawn");
		
		this.allowedWorlds = plugin.getConfig().getStringList("allowedWorlds");
		this.bombardmentBombSpeed = Double.valueOf(plugin.getConfig().getDouble("bombardmentBombSpeed"));
		this.bombardmentBombDirection = Double.valueOf(plugin.getConfig().getDouble("bombardmentBombDirection"));

		this.deactivateSounds = plugin.getConfig().getBoolean("deactivateSounds");
		this.needPasswordToJoin = plugin.getConfig().getBoolean("needPasswordToJoin");
		this.globalCooldown = plugin.getConfig().getBoolean("globalCooldown");
		this.inventoryManagement = plugin.getConfig().getBoolean("inventoryManagement");
		this.avoidSnowballKnockback = plugin.getConfig().getBoolean("avoidSnowballKnockback");
		this.avoidEggKnockback = plugin.getConfig().getBoolean("avoidEggKnockback");
		
		this.dropItemsAfterDie = plugin.getConfig().getBoolean("dropItemsAfterDie");
		this.advancedSpectatorMode = plugin.getConfig().getBoolean("advancedSpectatorMode");

		this.commandsOnEventFire = (List<String>) plugin.getConfig().getStringList("commandsOnEventFire");
		this.commandsOnUserJoin = (List<String>) plugin.getConfig().getStringList("commandsOnUserJoin");
		this.commandsOnKill = (List<String>) plugin.getConfig().getStringList("commandsOnKill");

		this.commandsOnMatchBegin = (List<String>) plugin.getConfig().getStringList("commandsOnMatchBegin");
		this.commandsOnMatchEnd = (List<String>) plugin.getConfig().getStringList("commandsOnMatchEnd");
		this.commandsOnUserLeave = (List<String>) plugin.getConfig().getStringList("commandsOnUserLeave");
		this.cooldownUsersBeginEvents = plugin.getConfig().getInt("cooldownUsersBeginEvents");
		this.forcePlayersToEnter = plugin.getConfig().getBoolean("forcePlayersToEnter");
		this.forcePlayersToSpectate = plugin.getConfig().getBoolean("forcePlayersToSpectate");
		this.topKillerHealAfterKill = plugin.getConfig().getBoolean("topKillerHealAfterKill");
		this.quakeGiveDefaultWeapon = plugin.getConfig().getBoolean("quakeGiveDefaultWeapon");
		this.paintGiveDefaultWeapon = plugin.getConfig().getBoolean("paintGiveDefaultWeapon");
		this.matchPrivateMatch = plugin.getConfig().getBoolean("matchPrivateMatch");
		this.waterKillBombardment = plugin.getConfig().getBoolean("waterKillBombardment");
		this.waterKillSpleef = plugin.getConfig().getBoolean("waterKillSpleef");
		this.waterKillSplegg = plugin.getConfig().getBoolean("waterKillSplegg");
		this.waterKillTNTRun = plugin.getConfig().getBoolean("waterKillTNTRun");
		this.waterKillAnvilSpleef = plugin.getConfig().getBoolean("waterKillAnvilSpleef");

		this.useTitleWhenGetBomb = plugin.getConfig().getBoolean("useTitleWhenGetBomb");
		this.disableMultipleWinners = plugin.getConfig().getBoolean("disableMultipleWinners");
		this.offSetYBombardment = plugin.getConfig().getInt("offSetYBombardment");

		this.skinDisguisePlayers = plugin.getConfig().getString("skinDisguisePlayers");

		this.equilibrateTeams = plugin.getConfig().getBoolean("equilibrateTeams");
		this.forceNonEmptyTeams = plugin.getConfig().getBoolean("forceNonEmptyTeams");
		this.infiniteSnowballs = plugin.getConfig().getBoolean("infiniteSnowballs");
		this.snowballsDamage = plugin.getConfig().getInt("snowballsDamage");
		this.quakeShootCooldown = plugin.getConfig().getDouble("quakeShootCooldown");
		this.quakeJumpCooldown = plugin.getConfig().getDouble("quakeJumpCooldown");
		this.quakeShootDistance = plugin.getConfig().getInt("quakeShootDistance");
		this.minNumberOfTriesBeforeBeginning = plugin.getConfig().getInt("minNumberOfTriesBeforeBeginning");
		this.splatoonPaint = plugin.getConfig().getInt("splatoonPaint");
		this.splatoonRadius = plugin.getConfig().getInt("splatoonRadius");
		this.cooldownAfterDeathSeconds = plugin.getConfig().getInt("cooldownAfterDeathSeconds");
		this.splatoonEggDamage = plugin.getConfig().getInt("splatoonEggDamage");
		this.secondsCheckStopSong = plugin.getConfig().getInt("secondsCheckStopSong");
		this.ticksAfterMusicStopToKill = plugin.getConfig().getInt("ticksAfterMusicStopToKill");
		this.probabilityPerCheckToStopSound = plugin.getConfig().getInt("probabilityPerCheckToStopSound");
		this.invincibleAfterRespawn = plugin.getConfig().getInt("invincibleAfterRespawn");

		this.useEncoding = plugin.getConfig().getString("useEncoding");
		if (useEncoding.equals("UTF_8")) {
			useEncoding = "UTF-8";
		}
		this.cmdAlias = new ArrayList<String>();
		for (String ali : plugin.getConfig().getString("cmdAlias").split(";")) {
			cmdAlias.add(ali);
		}

		Material mat = null;
		String statsMenuFill = plugin.getConfig().getString("statsmenu.fill");
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
			checkpointMat = Material.valueOf(plugin.getConfig().getString("checkpointItem"));
		} catch (Exception e) {
			if (checkpointMat == null) {
				checkpointMat = XMaterial.BLAZE_ROD.parseMaterial();
			}
		}
		this.checkpointItem = new ItemStack(checkpointMat);

		Material matVanish = null;
		String vanishMaterial = plugin.getConfig().getString("vanishItem");
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
		itemMetaCheck.setDisplayName(plugin.getLanguage().getItemReturnCheckpoint());
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
		itemVanishItem.setDisplayName(plugin.getLanguage().getItemHidePlayer());
		this.vanishItem.setItemMeta(itemVanishItem);

		ItemMeta itemEndVanishMeta = this.endVanishItem.getItemMeta();
		itemEndVanishMeta.setDisplayName(plugin.getLanguage().getItemShowPlayer());
		this.endVanishItem.setItemMeta(itemEndVanishMeta);

		this.oitcHealAfterKill = plugin.getConfig().getBoolean("oitcHealAfterKill");
		this.cooldownAfterDeath = plugin.getConfig().getBoolean("cooldownAfterDeath");
		this.tntTagSpeedRunners = plugin.getConfig().getInt("tntTagSpeedRunners");

		this.tntTagSpeedHolder = plugin.getConfig().getInt("tntTagSpeedHolder");
		this.arrowRainDamage = plugin.getConfig().getInt("arrowRainDamage");
		this.waterKillKnockbackDuel = plugin.getConfig().getBoolean("waterKillKnockbackDuel");
		this.snowballSpleef = plugin.getConfig().getBoolean("snowballSpleef");
		this.waterKillSG = plugin.getConfig().getBoolean("waterKillSG");
		this.waterKillSW = plugin.getConfig().getBoolean("waterKillSW");
		this.speedDuration = plugin.getConfig().getInt("speedDuration");

		this.statsFill = new ItemStack(mat);
		if (data != null) {
			statsFill.setDurability(data.shortValue());
			try {
				statsFill.getData().setData(data.byteValue());
			} catch (Throwable e) {

			}
		}

		this.statsALLTIME = plugin.getConfig().getInt("statsmenu.ALLTIME");
		this.statsBR = plugin.getConfig().getInt("statsmenu.BR");
		this.statsBRT2 = plugin.getConfig().getInt("statsmenu.BRT2");
		this.statsLJ = plugin.getConfig().getInt("statsmenu.LJ");
		this.statsTKLL = plugin.getConfig().getInt("statsmenu.TKLL");
		this.statsTKLLT2 = plugin.getConfig().getInt("statsmenu.TKLLT2");
		this.statsKBD = plugin.getConfig().getInt("statsmenu.KBD");
		this.statsEARR = plugin.getConfig().getInt("statsmenu.EARR");
		this.statsGEMC = plugin.getConfig().getInt("statsmenu.GEMC");
		this.statsBOMB = plugin.getConfig().getInt("statsmenu.BOMB");
		this.statsBOAT_RUN = plugin.getConfig().getInt("statsmenu.BOAT_RUN");
		this.statsHORSE_RUN = plugin.getConfig().getInt("statsmenu.HORSE_RUN");
		this.statsESCAPE_FROM_BEAST = plugin.getConfig().getInt("statsmenu.ESCAPE_FROM_BEAST");
		this.statsRACE = plugin.getConfig().getInt("statsmenu.RACE");
		this.statsTNTRUN = plugin.getConfig().getInt("statsmenu.TNTRUN");
		this.statsSPLEEF = plugin.getConfig().getInt("statsmenu.SPLEEF");
		this.statsSPLEGG = plugin.getConfig().getInt("statsmenu.SPLEGG");
		this.statsOITC = plugin.getConfig().getInt("statsmenu.OITC");
		this.statsSG = plugin.getConfig().getInt("statsmenu.SG");
		this.statsTSG = plugin.getConfig().getInt("statsmenu.TSG");
		this.statsSW = plugin.getConfig().getInt("statsmenu.SW");
		this.statsTSW = plugin.getConfig().getInt("statsmenu.TSW");
		this.statsANVIL_SPLEEF = plugin.getConfig().getInt("statsmenu.ANVIL_SPLEEF");
		this.statsWDROP = plugin.getConfig().getInt("statsmenu.WDROP");
		this.statsQUAKE = plugin.getConfig().getInt("statsmenu.QUAKE");
		this.statsPBALL = plugin.getConfig().getInt("statsmenu.PBALL");
		this.statsKOTH = plugin.getConfig().getInt("statsmenu.KOTH");
		this.statsFISHSLAP = plugin.getConfig().getInt("statsmenu.FISHSLAP");
		this.statsHOE = plugin.getConfig().getInt("statsmenu.HOE");
		this.statsSPLATOON = plugin.getConfig().getInt("statsmenu.SPLATOON");
		this.statsBOMBARDMENT = plugin.getConfig().getInt("statsmenu.BOMBARDMENT");
		this.statsBRTEAMS = plugin.getConfig().getInt("statsmenu.BRTEAMS");
		this.statsTKLLTEAMS = plugin.getConfig().getInt("statsmenu.TKLLTEAMS");
		this.statsTSGTEAMS = plugin.getConfig().getInt("statsmenu.TSGTEAMS");
		this.statsTSWTEAMS = plugin.getConfig().getInt("statsmenu.TSWTEAMS");
		this.statsPBALLTK = plugin.getConfig().getInt("statsmenu.PBALLTK");
		this.statsREDGREEN = plugin.getConfig().getInt("statsmenu.REDGREEN");
		this.distanceClearEntities = plugin.getConfig().getInt("distanceClearEntities");
		this.statsSize = plugin.getConfig().getInt("statsmenu.size");

		this.allowedCmds = (List<String>) plugin.getConfig().getStringList("allowedCmds");

		this.maxItemOnChests = Integer.valueOf(plugin.getConfig().getInt("maxItemOnChests"));
		this.minItemOnChests = Integer.valueOf(plugin.getConfig().getInt("minItemOnChests"));
		this.secondsToStartMatch = Integer.valueOf(plugin.getConfig().getInt("secondsToStartMatch"));
		if (secondsToStartMatch == null) {
			secondsToStartMatch = 0;
		} else {
			secondsToStartMatch = secondsToStartMatch - 3;
			if (secondsToStartMatch < 0) {
				secondsToStartMatch = 0;
			}
		}

		this.particleDeath = (plugin.getConfig().getString("particleDeath"));
		this.particleTNTTag = (plugin.getConfig().getString("particleTNTTag"));

		this.particleType = (plugin.getConfig().getString("particle.type"));

		this.particleSize = Double.valueOf(plugin.getConfig().getDouble("particle.size"));
		this.particleRadius = Double.valueOf(plugin.getConfig().getDouble("particle.radius"));
		this.particleRadiusRate = Double.valueOf(plugin.getConfig().getDouble("particle.radiusRate"));
		this.particleRadius2 = Double.valueOf(plugin.getConfig().getDouble("particle.radius2"));
		this.particleRate = Double.valueOf(plugin.getConfig().getDouble("particle.rate"));
		this.particleRateChange = Double.valueOf(plugin.getConfig().getDouble("particle.rateChange"));
		this.particleHeight = Double.valueOf(plugin.getConfig().getDouble("particle.height"));
		this.particleExtension = Double.valueOf(plugin.getConfig().getDouble("particle.extension"));

		this.secondsTimer = Integer.valueOf(plugin.getConfig().getInt("secondsTimer"));
		this.secondsCheckPlayers = Integer.valueOf(plugin.getConfig().getInt("secondsCheckPlayers"));
		if (secondsCheckPlayers == null) {
			secondsCheckPlayers = 15;
		}

		this.numberOfSecondsWithGems = Integer.valueOf(plugin.getConfig().getInt("numberOfSecondsWithGems"));
		this.numberOfGems = Integer.valueOf(plugin.getConfig().getInt("numberOfGems"));

		this.probabilityRandomEventTournament = Integer
				.valueOf(plugin.getConfig().getInt("probabilityRandomEventTournament"));
		this.probabilityRandomEvent = Integer.valueOf(plugin.getConfig().getInt("probabilityRandomEvent"));

		this.highestPriorityDamageEvents = plugin.getConfig().getBoolean("highestPriorityDamageEvents");

		this.debugMode = plugin.getConfig().getBoolean("debugMode");

		this.multipleKillOnExplosion = plugin.getConfig().getBoolean("multipleKillOnExplosion");
		this.radiusOfTNTTagExplosion = Integer.valueOf(plugin.getConfig().getInt("radiusOfTNTTagExplosion"));

		this.randomDisguisePlayers = plugin.getConfig().getBoolean("randomDisguisePlayers");
		this.useLastLocation = plugin.getConfig().getBoolean("useLastLocation");
		this.optionalTitles = plugin.getConfig().getBoolean("optionalTitles");
		this.showBorders = plugin.getConfig().getBoolean("showBorders");
		this.useParticles = plugin.getConfig().getBoolean("useParticles");
		this.forceGamemodeSurvival = plugin.getConfig().getBoolean("forceGamemodeSurvival");
		this.showInfoMinigameOnJoin = plugin.getConfig().getBoolean("showInfoMinigameOnJoin");
		
		this.useScoreboard = plugin.getConfig().getBoolean("useScoreboard");
		this.setProbabilityPowerUp(Integer.valueOf(plugin.getConfig().getInt("probabilityPowerUp")));

		plugin.setWaterDrops(UtilsRandomEvents.cargarWaterDrops(plugin));
		plugin.setKits(UtilsRandomEvents.cargarKits(plugin));
		plugin.setMatches(UtilsRandomEvents.cargarPartidas(plugin));

		plugin.setMatchesAvailable(new ArrayList<Match>());
		for (Match match : plugin.getMatches()) {
			if (match.getEnabled() == null || match.getEnabled()) {
				plugin.getMatchesAvailable().add(match);
			}
		}

		this.bannedPlayers = UtilsRandomEvents.cargarBannedPlayers(plugin);
		if (bannedPlayers == null || bannedPlayers.getBannedPlayers() == null) {
			bannedPlayers = new BannedPlayers();
		}
		plugin.setSchedules(UtilsRandomEvents.cargarSchedules(plugin));

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

		this.forceEmptyInventoryToJoin = plugin.getConfig().getBoolean("forceEmptyInventoryToJoin");
		this.mysqlEnabled = plugin.getConfig().getBoolean("mysql.enabled");
		this.mysqlUUIDMode = plugin.getConfig().getBoolean("mysql.UUIDMode");
		this.mysqlHost = plugin.getConfig().getString("mysql.host");
		this.mysqlDatabase = plugin.getConfig().getString("mysql.database");
		this.mysqlUsername = plugin.getConfig().getString("mysql.username");
		this.mysqlPassword = plugin.getConfig().getString("mysql.password");
		this.mysqlPort = plugin.getConfig().getInt("mysql.port");
		this.mysqlMaxLifeTime = plugin.getConfig().getInt("mysql.maxLifeTime");

		kitsItem = XMaterial.CHEST.parseItem();
		ItemMeta kitsMeta = kitsItem.getItemMeta();
		kitsMeta.setDisplayName(plugin.getLanguage().getKitItemName());
		kitsItem.setItemMeta(kitsMeta);

		teamItem = XMaterial.WHITE_TERRACOTTA.parseItem();
		ItemMeta teamMeta = teamItem.getItemMeta();
		teamMeta.setDisplayName(plugin.getLanguage().getTeamItemName());
		teamItem.setItemMeta(teamMeta);

		int pluginId = 8944;
		Metrics metrics = new Metrics(plugin, pluginId);

		try {
			if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
				try {
					new ReventPlaceholder(plugin).register();
					plugin.getLoggerP().info("[RandomEvents] PlaceholderAPI hooked succesfully!");

				} catch (Exception e) {
					plugin.getLoggerP().info("[RandomEvents] PlaceholderAPI hook failed!");
				}
			}
		} catch (Throwable e) {

		}

	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

	public Integer getSecondsTimer() {
		return secondsTimer;
	}

	public void setSecondsTimer(Integer secondsTimer) {
		this.secondsTimer = secondsTimer;
	}

	public Integer getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(Integer minPlayers) {
		this.minPlayers = minPlayers;
	}

	public Integer getProbabilityRandomEvent() {
		return probabilityRandomEvent;
	}

	public void setProbabilityRandomEvent(Integer probabilityRandomEvent) {
		this.probabilityRandomEvent = probabilityRandomEvent;
	}

	public Integer getProbabilityRandomEventTournament() {
		return probabilityRandomEventTournament;
	}

	public void setProbabilityRandomEventTournament(Integer probabilityRandomEventTournament) {
		this.probabilityRandomEventTournament = probabilityRandomEventTournament;
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

	public ItemStack getCheckpointItem() {
		return checkpointItem;
	}

	public void setCheckpointItem(ItemStack checkpointItem) {
		this.checkpointItem = checkpointItem;
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

	public Integer getMinItemOnChests() {
		return minItemOnChests;
	}

	public void setMinItemOnChests(Integer minItemOnChests) {
		this.minItemOnChests = minItemOnChests;
	}

	public boolean isShowBorders() {
		return showBorders;
	}

	public void setShowBorders(boolean showBorders) {
		this.showBorders = showBorders;
	}

	public boolean isUseParticles() {
		return useParticles;
	}

	public boolean isForceGamemodeSurvival() {
		return forceGamemodeSurvival;
	}

	public void setForceGamemodeSurvival(boolean forceGamemodeSurvival) {
		this.forceGamemodeSurvival = forceGamemodeSurvival;
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

	public int getStatsWDROP() {
		return statsWDROP;
	}

	public void setStatsWDROP(int statsWDROP) {
		this.statsWDROP = statsWDROP;
	}

	public int getArrowRainDamage() {
		return arrowRainDamage;
	}

	public void setArrowRainDamage(int arrowRainDamage) {
		this.arrowRainDamage = arrowRainDamage;
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

	public List<String> getCmdAlias() {
		return cmdAlias;
	}

	public void setCmdAlias(List<String> cmdAlias) {
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

	public void setPaintGiveDefaultWeapon(boolean paintGiveDefaultWeapon) {
		this.paintGiveDefaultWeapon = paintGiveDefaultWeapon;
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

	public boolean isMatchPrivateMatch() {
		return matchPrivateMatch;
	}

	public void setMatchPrivateMatch(boolean matchPrivateMatch) {
		this.matchPrivateMatch = matchPrivateMatch;
	}

	public boolean isEquilibrateTeams() {
		return equilibrateTeams;
	}

	public void setEquilibrateTeams(boolean equilibrateTeams) {
		this.equilibrateTeams = equilibrateTeams;
	}

	public boolean isForceNonEmptyTeams() {
		return forceNonEmptyTeams;
	}

	public void setForceNonEmptyTeams(boolean forceNonEmptyTeams) {
		this.forceNonEmptyTeams = forceNonEmptyTeams;
	}

	public ItemStack getKitsItem() {
		return kitsItem;
	}

	public void setKitsItem(ItemStack kitsItem) {
		this.kitsItem = kitsItem;
	}

	public ItemStack getTeamItem() {
		return teamItem;
	}

	public void setTeamItem(ItemStack teamItem) {
		this.teamItem = teamItem;
	}

	public int getMinNumberOfTriesBeforeBeginning() {
		return minNumberOfTriesBeforeBeginning;
	}

	public void setMinNumberOfTriesBeforeBeginning(int minNumberOfTriesBeforeBeginning) {
		this.minNumberOfTriesBeforeBeginning = minNumberOfTriesBeforeBeginning;
	}

	public List<String> getCommandsOnEventFire() {
		return commandsOnEventFire;
	}

	public void setCommandsOnEventFire(List<String> commandsOnEventFire) {
		this.commandsOnEventFire = commandsOnEventFire;
	}

	public boolean isWaterKillBombardment() {
		return waterKillBombardment;
	}

	public void setWaterKillBombardment(boolean waterKillBombardment) {
		this.waterKillBombardment = waterKillBombardment;
	}

	public int getOffSetYBombardment() {
		return offSetYBombardment;
	}

	public void setOffSetYBombardment(int offSetYBombardment) {
		this.offSetYBombardment = offSetYBombardment;
	}

	public boolean isDisableMultipleWinners() {
		return disableMultipleWinners;
	}

	public void setDisableMultipleWinners(boolean disableMultipleWinners) {
		this.disableMultipleWinners = disableMultipleWinners;
	}

	public boolean isRandomDisguisePlayers() {
		return randomDisguisePlayers;
	}

	public void setRandomDisguisePlayers(boolean randomDisguisePlayers) {
		this.randomDisguisePlayers = randomDisguisePlayers;
	}

	public Boolean getIsLibsDisguises() {
		return isLibsDisguises;
	}

	public void setIsLibsDisguises(Boolean isLibsDisguises) {
		this.isLibsDisguises = isLibsDisguises;
	}

	public Boolean getIsProtocolLib() {
		return isProtocolLib;
	}

	public void setIsProtocolLib(Boolean isProtocolLib) {
		this.isProtocolLib = isProtocolLib;
	}

	public String getSkinDisguisePlayers() {
		return skinDisguisePlayers;
	}

	public void setSkinDisguisePlayers(String skinDisguisePlayers) {
		this.skinDisguisePlayers = skinDisguisePlayers;
	}

	public boolean isInfiniteSnowballs() {
		return infiniteSnowballs;
	}

	public void setInfiniteSnowballs(boolean infiniteSnowballs) {
		this.infiniteSnowballs = infiniteSnowballs;
	}

	public List<String> getCommandsOnUserJoin() {
		return commandsOnUserJoin;
	}

	public void setCommandsOnUserJoin(List<String> commandsOnUserJoin) {
		this.commandsOnUserJoin = commandsOnUserJoin;
	}

	public List<String> getCommandsOnKill() {
		return commandsOnKill;
	}

	public void setCommandsOnKill(List<String> commandsOnKill) {
		this.commandsOnKill = commandsOnKill;
	}

	public List<String> getCommandsOnMatchBegin() {
		return commandsOnMatchBegin;
	}

	public void setCommandsOnMatchBegin(List<String> commandsOnMatchBegin) {
		this.commandsOnMatchBegin = commandsOnMatchBegin;
	}

	public int getSnowballsDamage() {
		return snowballsDamage;
	}

	public void setSnowballsDamage(int snowballsDamage) {
		this.snowballsDamage = snowballsDamage;
	}

	public boolean isWaterKillSpleef() {
		return waterKillSpleef;
	}

	public void setWaterKillSpleef(boolean waterKillSpleef) {
		this.waterKillSpleef = waterKillSpleef;
	}

	public boolean isWaterKillSplegg() {
		return waterKillSplegg;
	}

	public void setWaterKillSplegg(boolean waterKillSplegg) {
		this.waterKillSplegg = waterKillSplegg;
	}

	public boolean isWaterKillTNTRun() {
		return waterKillTNTRun;
	}

	public void setWaterKillTNTRun(boolean waterKillTNTRun) {
		this.waterKillTNTRun = waterKillTNTRun;
	}

	public boolean isWaterKillAnvilSpleef() {
		return waterKillAnvilSpleef;
	}

	public void setWaterKillAnvilSpleef(boolean waterKillAnvilSpleef) {
		this.waterKillAnvilSpleef = waterKillAnvilSpleef;
	}

	public boolean isMultipleKillOnExplosion() {
		return multipleKillOnExplosion;
	}

	public void setMultipleKillOnExplosion(boolean multipleKillOnExplosion) {
		this.multipleKillOnExplosion = multipleKillOnExplosion;
	}

	public Integer getRadiusOfTNTTagExplosion() {
		return radiusOfTNTTagExplosion;
	}

	public void setRadiusOfTNTTagExplosion(Integer radiusOfTNTTagExplosion) {
		this.radiusOfTNTTagExplosion = radiusOfTNTTagExplosion;
	}

	public boolean isUseTitleWhenGetBomb() {
		return useTitleWhenGetBomb;
	}

	public void setUseTitleWhenGetBomb(boolean useTitleWhenGetBomb) {
		this.useTitleWhenGetBomb = useTitleWhenGetBomb;
	}

	public boolean isDeactivateSounds() {
		return deactivateSounds;
	}

	public void setDeactivateSounds(boolean deactivateSounds) {
		this.deactivateSounds = deactivateSounds;
	}

	public int getStatsBRTEAMS() {
		return statsBRTEAMS;
	}

	public void setStatsBRTEAMS(int statsBRTEAMS) {
		this.statsBRTEAMS = statsBRTEAMS;
	}

	public int getStatsTKLLTEAMS() {
		return statsTKLLTEAMS;
	}

	public void setStatsTKLLTEAMS(int statsTKLLTEAMS) {
		this.statsTKLLTEAMS = statsTKLLTEAMS;
	}

	public int getStatsTSGTEAMS() {
		return statsTSGTEAMS;
	}

	public void setStatsTSGTEAMS(int statsTSGTEAMS) {
		this.statsTSGTEAMS = statsTSGTEAMS;
	}

	public int getStatsTSWTEAMS() {
		return statsTSWTEAMS;
	}

	public void setStatsTSWTEAMS(int statsTSWTEAMS) {
		this.statsTSWTEAMS = statsTSWTEAMS;
	}

	public int getStatsPBALLTK() {
		return statsPBALLTK;
	}

	public void setStatsPBALLTK(int statsPBALLTK) {
		this.statsPBALLTK = statsPBALLTK;
	}

	public int getDistanceClearEntities() {
		return distanceClearEntities;
	}

	public void setDistanceClearEntities(int distanceClearEntities) {
		this.distanceClearEntities = distanceClearEntities;
	}

	public Boolean getIsNoteBlockAPI() {
		return isNoteBlockAPI;
	}

	public void setIsNoteBlockAPI(Boolean isNoteBlockAPI) {
		this.isNoteBlockAPI = isNoteBlockAPI;
	}

	public Boolean getIsCitizens() {
		return isCitizens;
	}

	public void setIsCitizens(Boolean isCitizens) {
		this.isCitizens = isCitizens;
	}

	public int getSecondsCheckStopSong() {
		return secondsCheckStopSong;
	}

	public void setSecondsCheckStopSong(int secondsCheckStopSong) {
		this.secondsCheckStopSong = secondsCheckStopSong;
	}

	public int getProbabilityPerCheckToStopSound() {
		return probabilityPerCheckToStopSound;
	}

	public void setProbabilityPerCheckToStopSound(int probabilityPerCheckToStopSound) {
		this.probabilityPerCheckToStopSound = probabilityPerCheckToStopSound;
	}

	public int getStatsREDGREEN() {
		return statsREDGREEN;
	}

	public void setStatsREDGREEN(int statsREDGREEN) {
		this.statsREDGREEN = statsREDGREEN;
	}

	public int getTicksAfterMusicStopToKill() {
		return ticksAfterMusicStopToKill;
	}

	public void setTicksAfterMusicStopToKill(int ticksAfterMusicStopToKill) {
		this.ticksAfterMusicStopToKill = ticksAfterMusicStopToKill;
	}

	public int getInvincibleAfterRespawn() {
		return invincibleAfterRespawn;
	}

	public void setInvincibleAfterRespawn(int invincibleAfterRespawn) {
		this.invincibleAfterRespawn = invincibleAfterRespawn;
	}

	public Integer getInvincibleAfterGame() {
		return invincibleAfterGame;
	}

	public void setInvincibleAfterGame(Integer invincibleAfterGame) {
		this.invincibleAfterGame = invincibleAfterGame;
	}

	public Boolean getRestrictWorlds() {
		return restrictWorlds;
	}

	public void setRestrictWorlds(Boolean restrictWorlds) {
		this.restrictWorlds = restrictWorlds;
	}

	public List<String> getAllowedWorlds() {
		return allowedWorlds;
	}

	public void setAllowedWorlds(List<String> allowedWorlds) {
		this.allowedWorlds = allowedWorlds;
	}

	public Boolean getTeamMatchRandomRespawn() {
		return teamMatchRandomRespawn;
	}

	public void setTeamMatchRandomRespawn(Boolean teamMatchRandomRespawn) {
		this.teamMatchRandomRespawn = teamMatchRandomRespawn;
	}

	public boolean isShowInfoMinigameOnJoin() {
		return showInfoMinigameOnJoin;
	}

	public void setShowInfoMinigameOnJoin(boolean showInfoMinigameOnJoin) {
		this.showInfoMinigameOnJoin = showInfoMinigameOnJoin;
	}

	public boolean isAvoidSnowballKnockback() {
		return avoidSnowballKnockback;
	}

	public void setAvoidSnowballKnockback(boolean avoidSnowballKnockback) {
		this.avoidSnowballKnockback = avoidSnowballKnockback;
	}

	public boolean isAvoidEggKnockback() {
		return avoidEggKnockback;
	}

	public void setAvoidEggKnockback(boolean avoidEggKnockback) {
		this.avoidEggKnockback = avoidEggKnockback;
	}

}
