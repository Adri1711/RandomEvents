
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
import com.adri1711.randomevents.match.PlayersDisabled;
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
	private Integer sgAreaShrinkBlocks;

	private ItemStack powerUpItem;
	private ItemStack checkpointItem;

	private Boolean raceSlowEffect;

	private Boolean needSpecificPermissionStartRevent;

	private Integer invincibleAfterGame;

	private Boolean avoidSpectatorTp;

	private Boolean useLastLocation;

	private Integer numberOfTriesBeforeCancelling;

	private boolean avoidSnowballKnockback;
	private boolean avoidEggKnockback;
	private boolean allowGlassWalkPvP;
	private boolean allowBlockPartyPvP;
	private boolean biggerPlatform;
	private boolean useTeamChestplate;
	

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
	private List<String> minPlayersBlackList;

	private boolean forceEmptyInventoryToJoin;

	private BannedPlayers bannedPlayers;

	private PlayersDisabled disabledPlayers;

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
	private int statsQUAKE;
	private int statsPBALL;
	private int statsKOTH;
	private int statsFISHSLAP;
	private int statsHOE;
	private int statsSPLATOON;
	private int statsBOMBARDMENT;
	private int statsBRTEAMS;
	private int statsTKLLTEAMS;
	private int statsTSGTEAMS;
	private int statsTSWTEAMS;
	private int statsBLOCKPARTY;
	private int statsHIDEANDSEEK;
	private int statsPBALLTK;
	private int statsGLASSWALK;

	private ItemStack blockBR                  ;
	private ItemStack blockBRT2                ;
	private ItemStack blockLJ                  ;
	private ItemStack blockTKLL                ;
	private ItemStack blockTKLLT2              ;
	private ItemStack blockKBD                 ;
	private ItemStack blockEARR                ;
	private ItemStack blockGEMC                ;
	private ItemStack blockBOMB                ;
	private ItemStack blockBOAT_RUN            ;
	private ItemStack blockHORSE_RUN           ;
	private ItemStack blockESCAPE_FROM_BEAST   ;
	private ItemStack blockRACE                ;
	private ItemStack blockTNTRUN              ;
	private ItemStack blockSPLEEF              ;
	private ItemStack blockSPLEGG              ;
	private ItemStack blockOITC                ;
	private ItemStack blockSG                  ;
	private ItemStack blockTSG                 ;
	private ItemStack blockSW                  ;
	private ItemStack blockTSW                 ;
	private ItemStack blockREDGREEN            ;
	private ItemStack blockALLTIME             ;
	private ItemStack blockANVIL_SPLEEF        ;
	private ItemStack blockWDROP               ;
	private ItemStack blockQUAKE               ;
	private ItemStack blockPBALL               ;
	private ItemStack blockKOTH                ;
	private ItemStack blockFISHSLAP            ;
	private ItemStack blockHOE                 ;
	private ItemStack blockSPLATOON            ;
	private ItemStack blockBOMBARDMENT         ;
	private ItemStack blockBRTEAMS             ;
	private ItemStack blockTKLLTEAMS           ;
	private ItemStack blockTSGTEAMS            ;
	private ItemStack blockTSWTEAMS            ;
	private ItemStack blockBLOCKPARTY          ;
	private ItemStack blockHIDEANDSEEK         ;
	private ItemStack blockPBALLTK             ;
	private ItemStack blockGLASSWALK           ;

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

	private int splatoonPaint;

	private int splatoonRadius;

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

	private int distanceClearEntities;

	private int secondsCheckStopSong;

	private int probabilityPerCheckToStopSound;

	private int ticksAfterMusicStopToKill;

	private int invincibleAfterRespawn;

	private Boolean restrictWorlds;

	private List<String> allowedWorlds;

	private Boolean teamMatchRandomRespawn;

	private boolean activateGlow;

	private boolean activateIdleDamageSpleef;

	private Material materialBlockParty;
	private List<XMaterial> blocksBlockParty;

	private XMaterial waitItemPartyMaterial;

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
		blocksBlockParty = new ArrayList<XMaterial>();
		List<String> bpBlocks = plugin.getConfig().getStringList("blockPartyBlocks");
		if (bpBlocks != null) {
			for (String s : bpBlocks) {
				XMaterial xmat = XMaterial.valueOf(s);
				if (xmat != null) {
					blocksBlockParty.add(xmat);
				}
			}
		}

		this.bombardmentBombSpeed = Double.valueOf(plugin.getConfig().getDouble("bombardmentBombSpeed"));
		this.bombardmentBombDirection = Double.valueOf(plugin.getConfig().getDouble("bombardmentBombDirection"));

		this.deactivateSounds = plugin.getConfig().getBoolean("deactivateSounds");
		this.needPasswordToJoin = plugin.getConfig().getBoolean("needPasswordToJoin");
		this.globalCooldown = plugin.getConfig().getBoolean("globalCooldown");
		this.inventoryManagement = plugin.getConfig().getBoolean("inventoryManagement");
		this.avoidSnowballKnockback = plugin.getConfig().getBoolean("avoidSnowballKnockback");
		this.avoidEggKnockback = plugin.getConfig().getBoolean("avoidEggKnockback");
		this.allowGlassWalkPvP = plugin.getConfig().getBoolean("allowGlassWalkPvP");
		this.allowBlockPartyPvP = plugin.getConfig().getBoolean("allowBlockPartyPvP");
		
		this.biggerPlatform = plugin.getConfig().getBoolean("biggerPlatform");
		this.useTeamChestplate = plugin.getConfig().getBoolean("useTeamChestplate");
		
		this.dropItemsAfterDie = plugin.getConfig().getBoolean("dropItemsAfterDie");
		this.advancedSpectatorMode = plugin.getConfig().getBoolean("advancedSpectatorMode");
		this.materialBlockParty = Material.getMaterial(plugin.getConfig().getString("blockPartyMaterial"));
		this.waitItemPartyMaterial = XMaterial.valueOf(plugin.getConfig().getString("waitItemPartyMaterial"));
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
		this.activateGlow = plugin.getConfig().getBoolean("activateGlow");
		this.activateIdleDamageSpleef = plugin.getConfig().getBoolean("activateIdleDamageSpleef");
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
		Material matBR = null;
		String blockMenuBR = plugin.getConfig().getString("guigameblocks.BR");
		Integer dataBR = null;
		if (blockMenuBR.contains(":")) {
			dataBR = Integer.valueOf(blockMenuBR.split(":")[1]);
			blockMenuBR = blockMenuBR.split(":")[0];
		}
		try {
			matBR = Material.valueOf(blockMenuBR);
		} catch (Exception e) {
			if (matBR == null) {
				matBR = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBR = new ItemStack(matBR);
		if (dataBR != null) {
			blockBR.setDurability(dataBR.shortValue());
			try {
				blockBR.getData().setData(dataBR.byteValue());
			} catch (Throwable eBR) {
			}
		}
		Material matBRT2 = null;
		String blockMenuBRT2 = plugin.getConfig().getString("guigameblocks.BRT2");
		Integer dataBRT2 = null;
		if (blockMenuBRT2.contains(":")) {
			dataBRT2 = Integer.valueOf(blockMenuBRT2.split(":")[1]);
			blockMenuBRT2 = blockMenuBRT2.split(":")[0];
		}
		try {
			matBRT2 = Material.valueOf(blockMenuBRT2);
		} catch (Exception e) {
			if (matBRT2 == null) {
				matBRT2 = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBRT2 = new ItemStack(matBRT2);
		if (dataBRT2 != null) {
			blockBRT2.setDurability(dataBRT2.shortValue());
			try {
				blockBRT2.getData().setData(dataBRT2.byteValue());
			} catch (Throwable eBRT2) {
			}
		}
		Material matLJ = null;
		String blockMenuLJ = plugin.getConfig().getString("guigameblocks.LJ");
		Integer dataLJ = null;
		if (blockMenuLJ.contains(":")) {
			dataLJ = Integer.valueOf(blockMenuLJ.split(":")[1]);
			blockMenuLJ = blockMenuLJ.split(":")[0];
		}
		try {
			matLJ = Material.valueOf(blockMenuLJ);
		} catch (Exception e) {
			if (matLJ == null) {
				matLJ = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockLJ = new ItemStack(matLJ);
		if (dataLJ != null) {
			blockLJ.setDurability(dataLJ.shortValue());
			try {
				blockLJ.getData().setData(dataLJ.byteValue());
			} catch (Throwable eLJ) {
			}
		}
		Material matTKLL = null;
		String blockMenuTKLL = plugin.getConfig().getString("guigameblocks.TKLL");
		Integer dataTKLL = null;
		if (blockMenuTKLL.contains(":")) {
			dataTKLL = Integer.valueOf(blockMenuTKLL.split(":")[1]);
			blockMenuTKLL = blockMenuTKLL.split(":")[0];
		}
		try {
			matTKLL = Material.valueOf(blockMenuTKLL);
		} catch (Exception e) {
			if (matTKLL == null) {
				matTKLL = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTKLL = new ItemStack(matTKLL);
		if (dataTKLL != null) {
			blockTKLL.setDurability(dataTKLL.shortValue());
			try {
				blockTKLL.getData().setData(dataTKLL.byteValue());
			} catch (Throwable eTKLL) {
			}
		}
		Material matTKLLT2 = null;
		String blockMenuTKLLT2 = plugin.getConfig().getString("guigameblocks.TKLLT2");
		Integer dataTKLLT2 = null;
		if (blockMenuTKLLT2.contains(":")) {
			dataTKLLT2 = Integer.valueOf(blockMenuTKLLT2.split(":")[1]);
			blockMenuTKLLT2 = blockMenuTKLLT2.split(":")[0];
		}
		try {
			matTKLLT2 = Material.valueOf(blockMenuTKLLT2);
		} catch (Exception e) {
			if (matTKLLT2 == null) {
				matTKLLT2 = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTKLLT2 = new ItemStack(matTKLLT2);
		if (dataTKLLT2 != null) {
			blockTKLLT2.setDurability(dataTKLLT2.shortValue());
			try {
				blockTKLLT2.getData().setData(dataTKLLT2.byteValue());
			} catch (Throwable eTKLLT2) {
			}
		}
		Material matKBD = null;
		String blockMenuKBD = plugin.getConfig().getString("guigameblocks.KBD");
		Integer dataKBD = null;
		if (blockMenuKBD.contains(":")) {
			dataKBD = Integer.valueOf(blockMenuKBD.split(":")[1]);
			blockMenuKBD = blockMenuKBD.split(":")[0];
		}
		try {
			matKBD = Material.valueOf(blockMenuKBD);
		} catch (Exception e) {
			if (matKBD == null) {
				matKBD = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockKBD = new ItemStack(matKBD);
		if (dataKBD != null) {
			blockKBD.setDurability(dataKBD.shortValue());
			try {
				blockKBD.getData().setData(dataKBD.byteValue());
			} catch (Throwable eKBD) {
			}
		}
		Material matEARR = null;
		String blockMenuEARR = plugin.getConfig().getString("guigameblocks.EARR");
		Integer dataEARR = null;
		if (blockMenuEARR.contains(":")) {
			dataEARR = Integer.valueOf(blockMenuEARR.split(":")[1]);
			blockMenuEARR = blockMenuEARR.split(":")[0];
		}
		try {
			matEARR = Material.valueOf(blockMenuEARR);
		} catch (Exception e) {
			if (matEARR == null) {
				matEARR = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockEARR = new ItemStack(matEARR);
		if (dataEARR != null) {
			blockEARR.setDurability(dataEARR.shortValue());
			try {
				blockEARR.getData().setData(dataEARR.byteValue());
			} catch (Throwable eEARR) {
			}
		}
		Material matGEMC = null;
		String blockMenuGEMC = plugin.getConfig().getString("guigameblocks.GEMC");
		Integer dataGEMC = null;
		if (blockMenuGEMC.contains(":")) {
			dataGEMC = Integer.valueOf(blockMenuGEMC.split(":")[1]);
			blockMenuGEMC = blockMenuGEMC.split(":")[0];
		}
		try {
			matGEMC = Material.valueOf(blockMenuGEMC);
		} catch (Exception e) {
			if (matGEMC == null) {
				matGEMC = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockGEMC = new ItemStack(matGEMC);
		if (dataGEMC != null) {
			blockGEMC.setDurability(dataGEMC.shortValue());
			try {
				blockGEMC.getData().setData(dataGEMC.byteValue());
			} catch (Throwable eGEMC) {
			}
		}
		Material matBOMB = null;
		String blockMenuBOMB = plugin.getConfig().getString("guigameblocks.BOMB");
		Integer dataBOMB = null;
		if (blockMenuBOMB.contains(":")) {
			dataBOMB = Integer.valueOf(blockMenuBOMB.split(":")[1]);
			blockMenuBOMB = blockMenuBOMB.split(":")[0];
		}
		try {
			matBOMB = Material.valueOf(blockMenuBOMB);
		} catch (Exception e) {
			if (matBOMB == null) {
				matBOMB = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBOMB = new ItemStack(matBOMB);
		if (dataBOMB != null) {
			blockBOMB.setDurability(dataBOMB.shortValue());
			try {
				blockBOMB.getData().setData(dataBOMB.byteValue());
			} catch (Throwable eBOMB) {
			}
		}
		Material matBOAT_RUN = null;
		String blockMenuBOAT_RUN = plugin.getConfig().getString("guigameblocks.BOAT_RUN");
		Integer dataBOAT_RUN = null;
		if (blockMenuBOAT_RUN.contains(":")) {
			dataBOAT_RUN = Integer.valueOf(blockMenuBOAT_RUN.split(":")[1]);
			blockMenuBOAT_RUN = blockMenuBOAT_RUN.split(":")[0];
		}
		try {
			matBOAT_RUN = Material.valueOf(blockMenuBOAT_RUN);
		} catch (Exception e) {
			if (matBOAT_RUN == null) {
				matBOAT_RUN = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBOAT_RUN = new ItemStack(matBOAT_RUN);
		if (dataBOAT_RUN != null) {
			blockBOAT_RUN.setDurability(dataBOAT_RUN.shortValue());
			try {
				blockBOAT_RUN.getData().setData(dataBOAT_RUN.byteValue());
			} catch (Throwable eBOAT_RUN) {
			}
		}
		Material matHORSE_RUN = null;
		String blockMenuHORSE_RUN = plugin.getConfig().getString("guigameblocks.HORSE_RUN");
		Integer dataHORSE_RUN = null;
		if (blockMenuHORSE_RUN.contains(":")) {
			dataHORSE_RUN = Integer.valueOf(blockMenuHORSE_RUN.split(":")[1]);
			blockMenuHORSE_RUN = blockMenuHORSE_RUN.split(":")[0];
		}
		try {
			matHORSE_RUN = Material.valueOf(blockMenuHORSE_RUN);
		} catch (Exception e) {
			if (matHORSE_RUN == null) {
				matHORSE_RUN = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockHORSE_RUN = new ItemStack(matHORSE_RUN);
		if (dataHORSE_RUN != null) {
			blockHORSE_RUN.setDurability(dataHORSE_RUN.shortValue());
			try {
				blockHORSE_RUN.getData().setData(dataHORSE_RUN.byteValue());
			} catch (Throwable eHORSE_RUN) {
			}
		}
		Material matESCAPE_FROM_BEAST = null;
		String blockMenuESCAPE_FROM_BEAST = plugin.getConfig().getString("guigameblocks.ESCAPE_FROM_BEAST");
		Integer dataESCAPE_FROM_BEAST = null;
		if (blockMenuESCAPE_FROM_BEAST.contains(":")) {
			dataESCAPE_FROM_BEAST = Integer.valueOf(blockMenuESCAPE_FROM_BEAST.split(":")[1]);
			blockMenuESCAPE_FROM_BEAST = blockMenuESCAPE_FROM_BEAST.split(":")[0];
		}
		try {
			matESCAPE_FROM_BEAST = Material.valueOf(blockMenuESCAPE_FROM_BEAST);
		} catch (Exception e) {
			if (matESCAPE_FROM_BEAST == null) {
				matESCAPE_FROM_BEAST = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockESCAPE_FROM_BEAST = new ItemStack(matESCAPE_FROM_BEAST);
		if (dataESCAPE_FROM_BEAST != null) {
			blockESCAPE_FROM_BEAST.setDurability(dataESCAPE_FROM_BEAST.shortValue());
			try {
				blockESCAPE_FROM_BEAST.getData().setData(dataESCAPE_FROM_BEAST.byteValue());
			} catch (Throwable eESCAPE_FROM_BEAST) {
			}
		}
		Material matRACE = null;
		String blockMenuRACE = plugin.getConfig().getString("guigameblocks.RACE");
		Integer dataRACE = null;
		if (blockMenuRACE.contains(":")) {
			dataRACE = Integer.valueOf(blockMenuRACE.split(":")[1]);
			blockMenuRACE = blockMenuRACE.split(":")[0];
		}
		try {
			matRACE = Material.valueOf(blockMenuRACE);
		} catch (Exception e) {
			if (matRACE == null) {
				matRACE = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockRACE = new ItemStack(matRACE);
		if (dataRACE != null) {
			blockRACE.setDurability(dataRACE.shortValue());
			try {
				blockRACE.getData().setData(dataRACE.byteValue());
			} catch (Throwable eRACE) {
			}
		}
		Material matTNTRUN = null;
		String blockMenuTNTRUN = plugin.getConfig().getString("guigameblocks.TNTRUN");
		Integer dataTNTRUN = null;
		if (blockMenuTNTRUN.contains(":")) {
			dataTNTRUN = Integer.valueOf(blockMenuTNTRUN.split(":")[1]);
			blockMenuTNTRUN = blockMenuTNTRUN.split(":")[0];
		}
		try {
			matTNTRUN = Material.valueOf(blockMenuTNTRUN);
		} catch (Exception e) {
			if (matTNTRUN == null) {
				matTNTRUN = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTNTRUN = new ItemStack(matTNTRUN);
		if (dataTNTRUN != null) {
			blockTNTRUN.setDurability(dataTNTRUN.shortValue());
			try {
				blockTNTRUN.getData().setData(dataTNTRUN.byteValue());
			} catch (Throwable eTNTRUN) {
			}
		}
		Material matSPLEEF = null;
		String blockMenuSPLEEF = plugin.getConfig().getString("guigameblocks.SPLEEF");
		Integer dataSPLEEF = null;
		if (blockMenuSPLEEF.contains(":")) {
			dataSPLEEF = Integer.valueOf(blockMenuSPLEEF.split(":")[1]);
			blockMenuSPLEEF = blockMenuSPLEEF.split(":")[0];
		}
		try {
			matSPLEEF = Material.valueOf(blockMenuSPLEEF);
		} catch (Exception e) {
			if (matSPLEEF == null) {
				matSPLEEF = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockSPLEEF = new ItemStack(matSPLEEF);
		if (dataSPLEEF != null) {
			blockSPLEEF.setDurability(dataSPLEEF.shortValue());
			try {
				blockSPLEEF.getData().setData(dataSPLEEF.byteValue());
			} catch (Throwable eSPLEEF) {
			}
		}
		Material matSPLEGG = null;
		String blockMenuSPLEGG = plugin.getConfig().getString("guigameblocks.SPLEGG");
		Integer dataSPLEGG = null;
		if (blockMenuSPLEGG.contains(":")) {
			dataSPLEGG = Integer.valueOf(blockMenuSPLEGG.split(":")[1]);
			blockMenuSPLEGG = blockMenuSPLEGG.split(":")[0];
		}
		try {
			matSPLEGG = Material.valueOf(blockMenuSPLEGG);
		} catch (Exception e) {
			if (matSPLEGG == null) {
				matSPLEGG = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockSPLEGG = new ItemStack(matSPLEGG);
		if (dataSPLEGG != null) {
			blockSPLEGG.setDurability(dataSPLEGG.shortValue());
			try {
				blockSPLEGG.getData().setData(dataSPLEGG.byteValue());
			} catch (Throwable eSPLEGG) {
			}
		}
		Material matOITC = null;
		String blockMenuOITC = plugin.getConfig().getString("guigameblocks.OITC");
		Integer dataOITC = null;
		if (blockMenuOITC.contains(":")) {
			dataOITC = Integer.valueOf(blockMenuOITC.split(":")[1]);
			blockMenuOITC = blockMenuOITC.split(":")[0];
		}
		try {
			matOITC = Material.valueOf(blockMenuOITC);
		} catch (Exception e) {
			if (matOITC == null) {
				matOITC = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockOITC = new ItemStack(matOITC);
		if (dataOITC != null) {
			blockOITC.setDurability(dataOITC.shortValue());
			try {
				blockOITC.getData().setData(dataOITC.byteValue());
			} catch (Throwable eOITC) {
			}
		}
		Material matSG = null;
		String blockMenuSG = plugin.getConfig().getString("guigameblocks.SG");
		Integer dataSG = null;
		if (blockMenuSG.contains(":")) {
			dataSG = Integer.valueOf(blockMenuSG.split(":")[1]);
			blockMenuSG = blockMenuSG.split(":")[0];
		}
		try {
			matSG = Material.valueOf(blockMenuSG);
		} catch (Exception e) {
			if (matSG == null) {
				matSG = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockSG = new ItemStack(matSG);
		if (dataSG != null) {
			blockSG.setDurability(dataSG.shortValue());
			try {
				blockSG.getData().setData(dataSG.byteValue());
			} catch (Throwable eSG) {
			}
		}
		Material matTSG = null;
		String blockMenuTSG = plugin.getConfig().getString("guigameblocks.TSG");
		Integer dataTSG = null;
		if (blockMenuTSG.contains(":")) {
			dataTSG = Integer.valueOf(blockMenuTSG.split(":")[1]);
			blockMenuTSG = blockMenuTSG.split(":")[0];
		}
		try {
			matTSG = Material.valueOf(blockMenuTSG);
		} catch (Exception e) {
			if (matTSG == null) {
				matTSG = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTSG = new ItemStack(matTSG);
		if (dataTSG != null) {
			blockTSG.setDurability(dataTSG.shortValue());
			try {
				blockTSG.getData().setData(dataTSG.byteValue());
			} catch (Throwable eTSG) {
			}
		}
		Material matSW = null;
		String blockMenuSW = plugin.getConfig().getString("guigameblocks.SW");
		Integer dataSW = null;
		if (blockMenuSW.contains(":")) {
			dataSW = Integer.valueOf(blockMenuSW.split(":")[1]);
			blockMenuSW = blockMenuSW.split(":")[0];
		}
		try {
			matSW = Material.valueOf(blockMenuSW);
		} catch (Exception e) {
			if (matSW == null) {
				matSW = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockSW = new ItemStack(matSW);
		if (dataSW != null) {
			blockSW.setDurability(dataSW.shortValue());
			try {
				blockSW.getData().setData(dataSW.byteValue());
			} catch (Throwable eSW) {
			}
		}
		Material matTSW = null;
		String blockMenuTSW = plugin.getConfig().getString("guigameblocks.TSW");
		Integer dataTSW = null;
		if (blockMenuTSW.contains(":")) {
			dataTSW = Integer.valueOf(blockMenuTSW.split(":")[1]);
			blockMenuTSW = blockMenuTSW.split(":")[0];
		}
		try {
			matTSW = Material.valueOf(blockMenuTSW);
		} catch (Exception e) {
			if (matTSW == null) {
				matTSW = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTSW = new ItemStack(matTSW);
		if (dataTSW != null) {
			blockTSW.setDurability(dataTSW.shortValue());
			try {
				blockTSW.getData().setData(dataTSW.byteValue());
			} catch (Throwable eTSW) {
			}
		}
		Material matREDGREEN = null;
		String blockMenuREDGREEN = plugin.getConfig().getString("guigameblocks.REDGREEN");
		Integer dataREDGREEN = null;
		if (blockMenuREDGREEN.contains(":")) {
			dataREDGREEN = Integer.valueOf(blockMenuREDGREEN.split(":")[1]);
			blockMenuREDGREEN = blockMenuREDGREEN.split(":")[0];
		}
		try {
			matREDGREEN = Material.valueOf(blockMenuREDGREEN);
		} catch (Exception e) {
			if (matREDGREEN == null) {
				matREDGREEN = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockREDGREEN = new ItemStack(matREDGREEN);
		if (dataREDGREEN != null) {
			blockREDGREEN.setDurability(dataREDGREEN.shortValue());
			try {
				blockREDGREEN.getData().setData(dataREDGREEN.byteValue());
			} catch (Throwable eREDGREEN) {
			}
		}
		Material matALLTIME = null;
		String blockMenuALLTIME = plugin.getConfig().getString("guigameblocks.ALLTIME");
		Integer dataALLTIME = null;
		if (blockMenuALLTIME.contains(":")) {
			dataALLTIME = Integer.valueOf(blockMenuALLTIME.split(":")[1]);
			blockMenuALLTIME = blockMenuALLTIME.split(":")[0];
		}
		try {
			matALLTIME = Material.valueOf(blockMenuALLTIME);
		} catch (Exception e) {
			if (matALLTIME == null) {
				matALLTIME = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockALLTIME = new ItemStack(matALLTIME);
		if (dataALLTIME != null) {
			blockALLTIME.setDurability(dataALLTIME.shortValue());
			try {
				blockALLTIME.getData().setData(dataALLTIME.byteValue());
			} catch (Throwable eALLTIME) {
			}
		}
		Material matANVIL_SPLEEF = null;
		String blockMenuANVIL_SPLEEF = plugin.getConfig().getString("guigameblocks.ANVIL_SPLEEF");
		Integer dataANVIL_SPLEEF = null;
		if (blockMenuANVIL_SPLEEF.contains(":")) {
			dataANVIL_SPLEEF = Integer.valueOf(blockMenuANVIL_SPLEEF.split(":")[1]);
			blockMenuANVIL_SPLEEF = blockMenuANVIL_SPLEEF.split(":")[0];
		}
		try {
			matANVIL_SPLEEF = Material.valueOf(blockMenuANVIL_SPLEEF);
		} catch (Exception e) {
			if (matANVIL_SPLEEF == null) {
				matANVIL_SPLEEF = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockANVIL_SPLEEF = new ItemStack(matANVIL_SPLEEF);
		if (dataANVIL_SPLEEF != null) {
			blockANVIL_SPLEEF.setDurability(dataANVIL_SPLEEF.shortValue());
			try {
				blockANVIL_SPLEEF.getData().setData(dataANVIL_SPLEEF.byteValue());
			} catch (Throwable eANVIL_SPLEEF) {
			}
		}
		Material matWDROP = null;
		String blockMenuWDROP = plugin.getConfig().getString("guigameblocks.WDROP");
		Integer dataWDROP = null;
		if (blockMenuWDROP.contains(":")) {
			dataWDROP = Integer.valueOf(blockMenuWDROP.split(":")[1]);
			blockMenuWDROP = blockMenuWDROP.split(":")[0];
		}
		try {
			matWDROP = Material.valueOf(blockMenuWDROP);
		} catch (Exception e) {
			if (matWDROP == null) {
				matWDROP = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockWDROP = new ItemStack(matWDROP);
		if (dataWDROP != null) {
			blockWDROP.setDurability(dataWDROP.shortValue());
			try {
				blockWDROP.getData().setData(dataWDROP.byteValue());
			} catch (Throwable eWDROP) {
			}
		}
		Material matQUAKE = null;
		String blockMenuQUAKE = plugin.getConfig().getString("guigameblocks.QUAKE");
		Integer dataQUAKE = null;
		if (blockMenuQUAKE.contains(":")) {
			dataQUAKE = Integer.valueOf(blockMenuQUAKE.split(":")[1]);
			blockMenuQUAKE = blockMenuQUAKE.split(":")[0];
		}
		try {
			matQUAKE = Material.valueOf(blockMenuQUAKE);
		} catch (Exception e) {
			if (matQUAKE == null) {
				matQUAKE = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockQUAKE = new ItemStack(matQUAKE);
		if (dataQUAKE != null) {
			blockQUAKE.setDurability(dataQUAKE.shortValue());
			try {
				blockQUAKE.getData().setData(dataQUAKE.byteValue());
			} catch (Throwable eQUAKE) {
			}
		}
		Material matPBALL = null;
		String blockMenuPBALL = plugin.getConfig().getString("guigameblocks.PBALL");
		Integer dataPBALL = null;
		if (blockMenuPBALL.contains(":")) {
			dataPBALL = Integer.valueOf(blockMenuPBALL.split(":")[1]);
			blockMenuPBALL = blockMenuPBALL.split(":")[0];
		}
		try {
			matPBALL = Material.valueOf(blockMenuPBALL);
		} catch (Exception e) {
			if (matPBALL == null) {
				matPBALL = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockPBALL = new ItemStack(matPBALL);
		if (dataPBALL != null) {
			blockPBALL.setDurability(dataPBALL.shortValue());
			try {
				blockPBALL.getData().setData(dataPBALL.byteValue());
			} catch (Throwable ePBALL) {
			}
		}
		Material matKOTH = null;
		String blockMenuKOTH = plugin.getConfig().getString("guigameblocks.KOTH");
		Integer dataKOTH = null;
		if (blockMenuKOTH.contains(":")) {
			dataKOTH = Integer.valueOf(blockMenuKOTH.split(":")[1]);
			blockMenuKOTH = blockMenuKOTH.split(":")[0];
		}
		try {
			matKOTH = Material.valueOf(blockMenuKOTH);
		} catch (Exception e) {
			if (matKOTH == null) {
				matKOTH = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockKOTH = new ItemStack(matKOTH);
		if (dataKOTH != null) {
			blockKOTH.setDurability(dataKOTH.shortValue());
			try {
				blockKOTH.getData().setData(dataKOTH.byteValue());
			} catch (Throwable eKOTH) {
			}
		}
		Material matFISHSLAP = null;
		String blockMenuFISHSLAP = plugin.getConfig().getString("guigameblocks.FISHSLAP");
		Integer dataFISHSLAP = null;
		if (blockMenuFISHSLAP.contains(":")) {
			dataFISHSLAP = Integer.valueOf(blockMenuFISHSLAP.split(":")[1]);
			blockMenuFISHSLAP = blockMenuFISHSLAP.split(":")[0];
		}
		try {
			matFISHSLAP = Material.valueOf(blockMenuFISHSLAP);
		} catch (Exception e) {
			if (matFISHSLAP == null) {
				matFISHSLAP = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockFISHSLAP = new ItemStack(matFISHSLAP);
		if (dataFISHSLAP != null) {
			blockFISHSLAP.setDurability(dataFISHSLAP.shortValue());
			try {
				blockFISHSLAP.getData().setData(dataFISHSLAP.byteValue());
			} catch (Throwable eFISHSLAP) {
			}
		}
		Material matHOE = null;
		String blockMenuHOE = plugin.getConfig().getString("guigameblocks.HOE");
		Integer dataHOE = null;
		if (blockMenuHOE.contains(":")) {
			dataHOE = Integer.valueOf(blockMenuHOE.split(":")[1]);
			blockMenuHOE = blockMenuHOE.split(":")[0];
		}
		try {
			matHOE = Material.valueOf(blockMenuHOE);
		} catch (Exception e) {
			if (matHOE == null) {
				matHOE = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockHOE = new ItemStack(matHOE);
		if (dataHOE != null) {
			blockHOE.setDurability(dataHOE.shortValue());
			try {
				blockHOE.getData().setData(dataHOE.byteValue());
			} catch (Throwable eHOE) {
			}
		}
		Material matSPLATOON = null;
		String blockMenuSPLATOON = plugin.getConfig().getString("guigameblocks.SPLATOON");
		Integer dataSPLATOON = null;
		if (blockMenuSPLATOON.contains(":")) {
			dataSPLATOON = Integer.valueOf(blockMenuSPLATOON.split(":")[1]);
			blockMenuSPLATOON = blockMenuSPLATOON.split(":")[0];
		}
		try {
			matSPLATOON = Material.valueOf(blockMenuSPLATOON);
		} catch (Exception e) {
			if (matSPLATOON == null) {
				matSPLATOON = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockSPLATOON = new ItemStack(matSPLATOON);
		if (dataSPLATOON != null) {
			blockSPLATOON.setDurability(dataSPLATOON.shortValue());
			try {
				blockSPLATOON.getData().setData(dataSPLATOON.byteValue());
			} catch (Throwable eSPLATOON) {
			}
		}
		Material matBOMBARDMENT = null;
		String blockMenuBOMBARDMENT = plugin.getConfig().getString("guigameblocks.BOMBARDMENT");
		Integer dataBOMBARDMENT = null;
		if (blockMenuBOMBARDMENT.contains(":")) {
			dataBOMBARDMENT = Integer.valueOf(blockMenuBOMBARDMENT.split(":")[1]);
			blockMenuBOMBARDMENT = blockMenuBOMBARDMENT.split(":")[0];
		}
		try {
			matBOMBARDMENT = Material.valueOf(blockMenuBOMBARDMENT);
		} catch (Exception e) {
			if (matBOMBARDMENT == null) {
				matBOMBARDMENT = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBOMBARDMENT = new ItemStack(matBOMBARDMENT);
		if (dataBOMBARDMENT != null) {
			blockBOMBARDMENT.setDurability(dataBOMBARDMENT.shortValue());
			try {
				blockBOMBARDMENT.getData().setData(dataBOMBARDMENT.byteValue());
			} catch (Throwable eBOMBARDMENT) {
			}
		}
		Material matBRTEAMS = null;
		String blockMenuBRTEAMS = plugin.getConfig().getString("guigameblocks.BRTEAMS");
		Integer dataBRTEAMS = null;
		if (blockMenuBRTEAMS.contains(":")) {
			dataBRTEAMS = Integer.valueOf(blockMenuBRTEAMS.split(":")[1]);
			blockMenuBRTEAMS = blockMenuBRTEAMS.split(":")[0];
		}
		try {
			matBRTEAMS = Material.valueOf(blockMenuBRTEAMS);
		} catch (Exception e) {
			if (matBRTEAMS == null) {
				matBRTEAMS = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBRTEAMS = new ItemStack(matBRTEAMS);
		if (dataBRTEAMS != null) {
			blockBRTEAMS.setDurability(dataBRTEAMS.shortValue());
			try {
				blockBRTEAMS.getData().setData(dataBRTEAMS.byteValue());
			} catch (Throwable eBRTEAMS) {
			}
		}
		Material matTKLLTEAMS = null;
		String blockMenuTKLLTEAMS = plugin.getConfig().getString("guigameblocks.TKLLTEAMS");
		Integer dataTKLLTEAMS = null;
		if (blockMenuTKLLTEAMS.contains(":")) {
			dataTKLLTEAMS = Integer.valueOf(blockMenuTKLLTEAMS.split(":")[1]);
			blockMenuTKLLTEAMS = blockMenuTKLLTEAMS.split(":")[0];
		}
		try {
			matTKLLTEAMS = Material.valueOf(blockMenuTKLLTEAMS);
		} catch (Exception e) {
			if (matTKLLTEAMS == null) {
				matTKLLTEAMS = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTKLLTEAMS = new ItemStack(matTKLLTEAMS);
		if (dataTKLLTEAMS != null) {
			blockTKLLTEAMS.setDurability(dataTKLLTEAMS.shortValue());
			try {
				blockTKLLTEAMS.getData().setData(dataTKLLTEAMS.byteValue());
			} catch (Throwable eTKLLTEAMS) {
			}
		}
		Material matTSGTEAMS = null;
		String blockMenuTSGTEAMS = plugin.getConfig().getString("guigameblocks.TSGTEAMS");
		Integer dataTSGTEAMS = null;
		if (blockMenuTSGTEAMS.contains(":")) {
			dataTSGTEAMS = Integer.valueOf(blockMenuTSGTEAMS.split(":")[1]);
			blockMenuTSGTEAMS = blockMenuTSGTEAMS.split(":")[0];
		}
		try {
			matTSGTEAMS = Material.valueOf(blockMenuTSGTEAMS);
		} catch (Exception e) {
			if (matTSGTEAMS == null) {
				matTSGTEAMS = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTSGTEAMS = new ItemStack(matTSGTEAMS);
		if (dataTSGTEAMS != null) {
			blockTSGTEAMS.setDurability(dataTSGTEAMS.shortValue());
			try {
				blockTSGTEAMS.getData().setData(dataTSGTEAMS.byteValue());
			} catch (Throwable eTSGTEAMS) {
			}
		}
		Material matTSWTEAMS = null;
		String blockMenuTSWTEAMS = plugin.getConfig().getString("guigameblocks.TSWTEAMS");
		Integer dataTSWTEAMS = null;
		if (blockMenuTSWTEAMS.contains(":")) {
			dataTSWTEAMS = Integer.valueOf(blockMenuTSWTEAMS.split(":")[1]);
			blockMenuTSWTEAMS = blockMenuTSWTEAMS.split(":")[0];
		}
		try {
			matTSWTEAMS = Material.valueOf(blockMenuTSWTEAMS);
		} catch (Exception e) {
			if (matTSWTEAMS == null) {
				matTSWTEAMS = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockTSWTEAMS = new ItemStack(matTSWTEAMS);
		if (dataTSWTEAMS != null) {
			blockTSWTEAMS.setDurability(dataTSWTEAMS.shortValue());
			try {
				blockTSWTEAMS.getData().setData(dataTSWTEAMS.byteValue());
			} catch (Throwable eTSWTEAMS) {
			}
		}
		Material matBLOCKPARTY = null;
		String blockMenuBLOCKPARTY = plugin.getConfig().getString("guigameblocks.BLOCKPARTY");
		Integer dataBLOCKPARTY = null;
		if (blockMenuBLOCKPARTY.contains(":")) {
			dataBLOCKPARTY = Integer.valueOf(blockMenuBLOCKPARTY.split(":")[1]);
			blockMenuBLOCKPARTY = blockMenuBLOCKPARTY.split(":")[0];
		}
		try {
			matBLOCKPARTY = Material.valueOf(blockMenuBLOCKPARTY);
		} catch (Exception e) {
			if (matBLOCKPARTY == null) {
				matBLOCKPARTY = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockBLOCKPARTY = new ItemStack(matBLOCKPARTY);
		if (dataBLOCKPARTY != null) {
			blockBLOCKPARTY.setDurability(dataBLOCKPARTY.shortValue());
			try {
				blockBLOCKPARTY.getData().setData(dataBLOCKPARTY.byteValue());
			} catch (Throwable eBLOCKPARTY) {
			}
		}
		Material matHIDEANDSEEK = null;
		String blockMenuHIDEANDSEEK = plugin.getConfig().getString("guigameblocks.HIDEANDSEEK");
		Integer dataHIDEANDSEEK = null;
		if (blockMenuHIDEANDSEEK.contains(":")) {
			dataHIDEANDSEEK = Integer.valueOf(blockMenuHIDEANDSEEK.split(":")[1]);
			blockMenuHIDEANDSEEK = blockMenuHIDEANDSEEK.split(":")[0];
		}
		try {
			matHIDEANDSEEK = Material.valueOf(blockMenuHIDEANDSEEK);
		} catch (Exception e) {
			if (matHIDEANDSEEK == null) {
				matHIDEANDSEEK = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockHIDEANDSEEK = new ItemStack(matHIDEANDSEEK);
		if (dataHIDEANDSEEK != null) {
			blockHIDEANDSEEK.setDurability(dataHIDEANDSEEK.shortValue());
			try {
				blockHIDEANDSEEK.getData().setData(dataHIDEANDSEEK.byteValue());
			} catch (Throwable eHIDEANDSEEK) {
			}
		}
		Material matPBALLTK = null;
		String blockMenuPBALLTK = plugin.getConfig().getString("guigameblocks.PBALLTK");
		Integer dataPBALLTK = null;
		if (blockMenuPBALLTK.contains(":")) {
			dataPBALLTK = Integer.valueOf(blockMenuPBALLTK.split(":")[1]);
			blockMenuPBALLTK = blockMenuPBALLTK.split(":")[0];
		}
		try {
			matPBALLTK = Material.valueOf(blockMenuPBALLTK);
		} catch (Exception e) {
			if (matPBALLTK == null) {
				matPBALLTK = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockPBALLTK = new ItemStack(matPBALLTK);
		if (dataPBALLTK != null) {
			blockPBALLTK.setDurability(dataPBALLTK.shortValue());
			try {
				blockPBALLTK.getData().setData(dataPBALLTK.byteValue());
			} catch (Throwable ePBALLTK) {
			}
		}
		Material matGLASSWALK = null;
		String blockMenuGLASSWALK = plugin.getConfig().getString("guigameblocks.GLASSWALK");
		Integer dataGLASSWALK = null;
		if (blockMenuGLASSWALK.contains(":")) {
			dataGLASSWALK = Integer.valueOf(blockMenuGLASSWALK.split(":")[1]);
			blockMenuGLASSWALK = blockMenuGLASSWALK.split(":")[0];
		}
		try {
			matGLASSWALK = Material.valueOf(blockMenuGLASSWALK);
		} catch (Exception e) {
			if (matGLASSWALK == null) {
				matGLASSWALK = XMaterial.REDSTONE_BLOCK.parseMaterial();
			}
		}
		this.blockGLASSWALK = new ItemStack(matGLASSWALK);
		if (dataGLASSWALK != null) {
			blockGLASSWALK.setDurability(dataGLASSWALK.shortValue());
			try {
				blockGLASSWALK.getData().setData(dataGLASSWALK.byteValue());
			} catch (Throwable eGLASSWALK) {
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
		this.statsBLOCKPARTY = plugin.getConfig().getInt("statsmenu.BLOCKPARTY");
		this.statsHIDEANDSEEK = plugin.getConfig().getInt("statsmenu.HIDEANDSEEK");
		this.statsPBALLTK = plugin.getConfig().getInt("statsmenu.PBALLTK");
		this.statsREDGREEN = plugin.getConfig().getInt("statsmenu.REDGREEN");
		this.statsGLASSWALK = plugin.getConfig().getInt("statsmenu.GLASSWALK");
		this.distanceClearEntities = plugin.getConfig().getInt("distanceClearEntities");
		this.statsSize = plugin.getConfig().getInt("statsmenu.size");

		this.allowedCmds = (List<String>) plugin.getConfig().getStringList("allowedCmds");
		this.minPlayersBlackList = (List<String>) plugin.getConfig().getStringList("minPlayersBlackList");

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
		this.sgAreaShrinkBlocks = Integer.valueOf(plugin.getConfig().getInt("sgAreaShrinkBlocks"));

		this.randomDisguisePlayers = plugin.getConfig().getBoolean("randomDisguisePlayers");
		this.useLastLocation = plugin.getConfig().getBoolean("useLastLocation");
		this.avoidSpectatorTp = plugin.getConfig().getBoolean("avoidSpectatorTp");

		this.raceSlowEffect = plugin.getConfig().getBoolean("raceSlowEffect");
		this.needSpecificPermissionStartRevent = plugin.getConfig().getBoolean("needSpecificPermissionStartRevent");
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

		this.disabledPlayers = UtilsRandomEvents.cargarDisabledPlayers(plugin);
		if (disabledPlayers == null || disabledPlayers.getPlayersDisabled() == null) {
			disabledPlayers = new PlayersDisabled();
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

	public PlayersDisabled getDisabledPlayers() {
		return disabledPlayers;
	}

	public void setDisabledPlayers(PlayersDisabled disabledPlayers) {
		this.disabledPlayers = disabledPlayers;
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

	public boolean isActivateGlow() {
		return activateGlow;
	}

	public void setActivateGlow(boolean activateGlow) {
		this.activateGlow = activateGlow;
	}

	public boolean isActivateIdleDamageSpleef() {
		return activateIdleDamageSpleef;
	}

	public void setActivateIdleDamageSpleef(boolean activateIdleDamageSpleef) {
		this.activateIdleDamageSpleef = activateIdleDamageSpleef;
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

	public int getStatsGLASSWALK() {
		return statsGLASSWALK;
	}

	public void setStatsTSWTEAMS(int statsTSWTEAMS) {
		this.statsTSWTEAMS = statsTSWTEAMS;
	}

	public int getStatsBLOCKPARTY() {
		return statsBLOCKPARTY;
	}

	public void setStatsBLOCKPARTY(int statsBLOCKPARTY) {
		this.statsBLOCKPARTY = statsBLOCKPARTY;
	}

	public int getStatsHIDEANDSEEK() {
		return statsHIDEANDSEEK;
	}

	public void setStatsHIDEANDSEEK(int statsHIDEANDSEEK) {
		this.statsHIDEANDSEEK = statsHIDEANDSEEK;
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

	public Boolean getAvoidSpectatorTp() {
		return avoidSpectatorTp;
	}

	public void setAvoidSpectatorTp(Boolean avoidSpectatorTp) {
		this.avoidSpectatorTp = avoidSpectatorTp;
	}

	public Boolean getRaceSlowEffect() {
		return raceSlowEffect;
	}

	public void setRaceSlowEffect(Boolean raceSlowEffect) {
		this.raceSlowEffect = raceSlowEffect;
	}

	public Boolean getNeedSpecificPermissionStartRevent() {
		return needSpecificPermissionStartRevent;
	}

	public void setNeedSpecificPermissionStartRevent(Boolean needSpecificPermissionStartRevent) {
		this.needSpecificPermissionStartRevent = needSpecificPermissionStartRevent;
	}

	public Integer getSgAreaShrinkBlocks() {
		return sgAreaShrinkBlocks;
	}

	public void setSgAreaShrinkBlocks(Integer sgAreaShrinkBlocks) {
		this.sgAreaShrinkBlocks = sgAreaShrinkBlocks;
	}

	public Material getMaterialBlockParty() {
		return materialBlockParty;
	}

	public void setMaterialBlockParty(Material materialBlockParty) {
		this.materialBlockParty = materialBlockParty;
	}

	public List<XMaterial> getBlocksBlockParty() {
		return blocksBlockParty;
	}

	public void setBlocksBlockParty(List<XMaterial> blocksBlockParty) {
		this.blocksBlockParty = blocksBlockParty;
	}

	public XMaterial getWaitItemPartyMaterial() {
		return waitItemPartyMaterial;
	}

	public void setWaitItemPartyMaterial(XMaterial waitItemPartyMaterial) {
		this.waitItemPartyMaterial = waitItemPartyMaterial;
	}

	public void setStatsGLASSWALK(int statsGLASSWALK) {
		this.statsGLASSWALK = statsGLASSWALK;
	}

	public boolean isAllowGlassWalkPvP() {
		return allowGlassWalkPvP;
	}

	public void setAllowGlassWalkPvP(boolean allowGlassWalkPvP) {
		this.allowGlassWalkPvP = allowGlassWalkPvP;
	}

	public boolean isAllowBlockPartyPvP() {
		return allowBlockPartyPvP;
	}

	public void setAllowBlockPartyPvP(boolean allowBlockPartyPvP) {
		this.allowBlockPartyPvP = allowBlockPartyPvP;
	}

	public boolean isBiggerPlatform() {
		return biggerPlatform;
	}

	public boolean isUseTeamChestplate() {
		return useTeamChestplate;
	}

	public void setUseTeamChestplate(boolean useTeamChestplate) {
		this.useTeamChestplate = useTeamChestplate;
	}

	public void setBiggerPlatform(boolean biggerPlatform) {
		this.biggerPlatform = biggerPlatform;
	}

	public List<String> getMinPlayersBlackList() {
		return minPlayersBlackList;
	}

	public void setMinPlayersBlackList(List<String> minPlayersBlackList) {
		this.minPlayersBlackList = minPlayersBlackList;
	}

	public ItemStack getBlockBR() {
		return blockBR;
	}

	public void setBlockBR(ItemStack blockBR) {
		this.blockBR = blockBR;
	}

	public ItemStack getBlockBRT2() {
		return blockBRT2;
	}

	public void setBlockBRT2(ItemStack blockBRT2) {
		this.blockBRT2 = blockBRT2;
	}

	public ItemStack getBlockLJ() {
		return blockLJ;
	}

	public void setBlockLJ(ItemStack blockLJ) {
		this.blockLJ = blockLJ;
	}

	public ItemStack getBlockTKLL() {
		return blockTKLL;
	}

	public void setBlockTKLL(ItemStack blockTKLL) {
		this.blockTKLL = blockTKLL;
	}

	public ItemStack getBlockTKLLT2() {
		return blockTKLLT2;
	}

	public void setBlockTKLLT2(ItemStack blockTKLLT2) {
		this.blockTKLLT2 = blockTKLLT2;
	}

	public ItemStack getBlockKBD() {
		return blockKBD;
	}

	public void setBlockKBD(ItemStack blockKBD) {
		this.blockKBD = blockKBD;
	}

	public ItemStack getBlockEARR() {
		return blockEARR;
	}

	public void setBlockEARR(ItemStack blockEARR) {
		this.blockEARR = blockEARR;
	}

	public ItemStack getBlockGEMC() {
		return blockGEMC;
	}

	public void setBlockGEMC(ItemStack blockGEMC) {
		this.blockGEMC = blockGEMC;
	}

	public ItemStack getBlockBOMB() {
		return blockBOMB;
	}

	public void setBlockBOMB(ItemStack blockBOMB) {
		this.blockBOMB = blockBOMB;
	}

	public ItemStack getBlockBOAT_RUN() {
		return blockBOAT_RUN;
	}

	public void setBlockBOAT_RUN(ItemStack blockBOAT_RUN) {
		this.blockBOAT_RUN = blockBOAT_RUN;
	}

	public ItemStack getBlockHORSE_RUN() {
		return blockHORSE_RUN;
	}

	public void setBlockHORSE_RUN(ItemStack blockHORSE_RUN) {
		this.blockHORSE_RUN = blockHORSE_RUN;
	}

	public ItemStack getBlockESCAPE_FROM_BEAST() {
		return blockESCAPE_FROM_BEAST;
	}

	public void setBlockESCAPE_FROM_BEAST(ItemStack blockESCAPE_FROM_BEAST) {
		this.blockESCAPE_FROM_BEAST = blockESCAPE_FROM_BEAST;
	}

	public ItemStack getBlockRACE() {
		return blockRACE;
	}

	public void setBlockRACE(ItemStack blockRACE) {
		this.blockRACE = blockRACE;
	}

	public ItemStack getBlockTNTRUN() {
		return blockTNTRUN;
	}

	public void setBlockTNTRUN(ItemStack blockTNTRUN) {
		this.blockTNTRUN = blockTNTRUN;
	}

	public ItemStack getBlockSPLEEF() {
		return blockSPLEEF;
	}

	public void setBlockSPLEEF(ItemStack blockSPLEEF) {
		this.blockSPLEEF = blockSPLEEF;
	}

	public ItemStack getBlockSPLEGG() {
		return blockSPLEGG;
	}

	public void setBlockSPLEGG(ItemStack blockSPLEGG) {
		this.blockSPLEGG = blockSPLEGG;
	}

	public ItemStack getBlockOITC() {
		return blockOITC;
	}

	public void setBlockOITC(ItemStack blockOITC) {
		this.blockOITC = blockOITC;
	}

	public ItemStack getBlockSG() {
		return blockSG;
	}

	public void setBlockSG(ItemStack blockSG) {
		this.blockSG = blockSG;
	}

	public ItemStack getBlockTSG() {
		return blockTSG;
	}

	public void setBlockTSG(ItemStack blockTSG) {
		this.blockTSG = blockTSG;
	}

	public ItemStack getBlockSW() {
		return blockSW;
	}

	public void setBlockSW(ItemStack blockSW) {
		this.blockSW = blockSW;
	}

	public ItemStack getBlockTSW() {
		return blockTSW;
	}

	public void setBlockTSW(ItemStack blockTSW) {
		this.blockTSW = blockTSW;
	}

	public ItemStack getBlockREDGREEN() {
		return blockREDGREEN;
	}

	public void setBlockREDGREEN(ItemStack blockREDGREEN) {
		this.blockREDGREEN = blockREDGREEN;
	}

	public ItemStack getBlockALLTIME() {
		return blockALLTIME;
	}

	public void setBlockALLTIME(ItemStack blockALLTIME) {
		this.blockALLTIME = blockALLTIME;
	}

	public ItemStack getBlockANVIL_SPLEEF() {
		return blockANVIL_SPLEEF;
	}

	public void setBlockANVIL_SPLEEF(ItemStack blockANVIL_SPLEEF) {
		this.blockANVIL_SPLEEF = blockANVIL_SPLEEF;
	}

	public ItemStack getBlockWDROP() {
		return blockWDROP;
	}

	public void setBlockWDROP(ItemStack blockWDROP) {
		this.blockWDROP = blockWDROP;
	}

	public ItemStack getBlockQUAKE() {
		return blockQUAKE;
	}

	public void setBlockQUAKE(ItemStack blockQUAKE) {
		this.blockQUAKE = blockQUAKE;
	}

	public ItemStack getBlockPBALL() {
		return blockPBALL;
	}

	public void setBlockPBALL(ItemStack blockPBALL) {
		this.blockPBALL = blockPBALL;
	}

	public ItemStack getBlockKOTH() {
		return blockKOTH;
	}

	public void setBlockKOTH(ItemStack blockKOTH) {
		this.blockKOTH = blockKOTH;
	}

	public ItemStack getBlockFISHSLAP() {
		return blockFISHSLAP;
	}

	public void setBlockFISHSLAP(ItemStack blockFISHSLAP) {
		this.blockFISHSLAP = blockFISHSLAP;
	}

	public ItemStack getBlockHOE() {
		return blockHOE;
	}

	public void setBlockHOE(ItemStack blockHOE) {
		this.blockHOE = blockHOE;
	}

	public ItemStack getBlockSPLATOON() {
		return blockSPLATOON;
	}

	public void setBlockSPLATOON(ItemStack blockSPLATOON) {
		this.blockSPLATOON = blockSPLATOON;
	}

	public ItemStack getBlockBOMBARDMENT() {
		return blockBOMBARDMENT;
	}

	public void setBlockBOMBARDMENT(ItemStack blockBOMBARDMENT) {
		this.blockBOMBARDMENT = blockBOMBARDMENT;
	}

	public ItemStack getBlockBRTEAMS() {
		return blockBRTEAMS;
	}

	public void setBlockBRTEAMS(ItemStack blockBRTEAMS) {
		this.blockBRTEAMS = blockBRTEAMS;
	}

	public ItemStack getBlockTKLLTEAMS() {
		return blockTKLLTEAMS;
	}

	public void setBlockTKLLTEAMS(ItemStack blockTKLLTEAMS) {
		this.blockTKLLTEAMS = blockTKLLTEAMS;
	}

	public ItemStack getBlockTSGTEAMS() {
		return blockTSGTEAMS;
	}

	public void setBlockTSGTEAMS(ItemStack blockTSGTEAMS) {
		this.blockTSGTEAMS = blockTSGTEAMS;
	}

	public ItemStack getBlockTSWTEAMS() {
		return blockTSWTEAMS;
	}

	public void setBlockTSWTEAMS(ItemStack blockTSWTEAMS) {
		this.blockTSWTEAMS = blockTSWTEAMS;
	}

	public ItemStack getBlockBLOCKPARTY() {
		return blockBLOCKPARTY;
	}

	public void setBlockBLOCKPARTY(ItemStack blockBLOCKPARTY) {
		this.blockBLOCKPARTY = blockBLOCKPARTY;
	}

	public ItemStack getBlockHIDEANDSEEK() {
		return blockHIDEANDSEEK;
	}

	public void setBlockHIDEANDSEEK(ItemStack blockHIDEANDSEEK) {
		this.blockHIDEANDSEEK = blockHIDEANDSEEK;
	}

	public ItemStack getBlockPBALLTK() {
		return blockPBALLTK;
	}

	public void setBlockPBALLTK(ItemStack blockPBALLTK) {
		this.blockPBALLTK = blockPBALLTK;
	}

	public ItemStack getBlockGLASSWALK() {
		return blockGLASSWALK;
	}

	public void setBlockGLASSWALK(ItemStack blockGLASSWALK) {
		this.blockGLASSWALK = blockGLASSWALK;
	}


}
