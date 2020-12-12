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
import com.adri1711.randomevents.match.BannedPlayers;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.Schedule;
import com.adri1711.randomevents.match.Tournament;
import com.adri1711.randomevents.match.TournamentActive;
import com.adri1711.randomevents.metrics.Metrics;
import com.adri1711.randomevents.placeholders.ReventPlaceholder;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;
import com.adri1711.util.enums.AMaterials;

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

	private Tournament tournament;

	private Map<String, Match> playerMatches;

	private Map<String, Integer> playersCreation;
	
	private List<String> editando;

	private Map<String, EntityType> playersEntity;

	private List<String> commandsOnUserJoin;
	
	private List<String> commandsOnMatchBegin;
	

	private Boolean forzado = Boolean.FALSE;

	private ItemStack powerUpItem;

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

	public void onEnable() {
		this.api = new API1711("%%__USER__%%", "RandomEvents");
		loadConfig();
		this.editando=new ArrayList<String>();
		this.comandosExecutor = new ComandosExecutor();

		this.powerUpItem = new ItemStack(getApi().getMaterial(AMaterials.EMERALD));
		ItemMeta itemMeta = this.powerUpItem.getItemMeta();
		itemMeta.setDisplayName("§2§lPowerUP");
		this.powerUpItem.setItemMeta(itemMeta);

		inicializaVariables();

		if (mysqlEnabled) {
			hikari = new HikariCP(mysqlHost, mysqlPort.toString(), mysqlDatabase, mysqlUsername, mysqlPassword);
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
				setMatches(UtilsRandomEvents.cargarPartidas(getPlugin()));

			}

		}, 400);

	}

	public void onDisable() {
		UtilsRandomEvents.terminaCreacionBannedPlayers(this, bannedPlayers);
		if (mysqlEnabled && hikari != null) {
			hikari.close();
		}
		if(matchActive!=null && matchActive.getPlaying()){
			matchActive.reiniciaValoresPartida();
		}
 
		getServer().getScheduler().cancelTasks((Plugin) this);
		getLogger().info(" Author adri1711 - desactivado");
	}

	public void inicializaVariables() {
		updateConfig();
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
		this.inventoryManagement = getConfig().getBoolean("inventoryManagement");
		this.dropItemsAfterDie = getConfig().getBoolean("dropItemsAfterDie");
		this.commandsOnUserJoin = (List<String>) getConfig().getStringList("commandsOnUserJoin");
		this.commandsOnMatchBegin = (List<String>) getConfig().getStringList("commandsOnMatchBegin");
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
		
		this.setProbabilityPowerUp(Integer.valueOf(getConfig().getInt("probabilityPowerUp")));

		this.matches = UtilsRandomEvents.cargarPartidas(this);
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
		this.playersEntity = new HashMap<String, EntityType>();
		this.forceEmptyInventoryToJoin = getConfig().getBoolean("forceEmptyInventoryToJoin");
		this.mysqlEnabled = getConfig().getBoolean("mysql.enabled");
		this.mysqlUUIDMode = getConfig().getBoolean("mysql.UUIDMode");
		this.mysqlHost = getConfig().getString("mysql.host");
		this.mysqlDatabase = getConfig().getString("mysql.database");
		this.mysqlUsername = getConfig().getString("mysql.username");
		this.mysqlPassword = getConfig().getString("mysql.password");
		this.mysqlPort = getConfig().getInt("mysql.port");

		this.language = new LanguageMessages(this);
		int pluginId = 8944;
		Metrics metrics = new Metrics(this, pluginId);

	}

	public void comienzaTemporizador() {
		if (!forzado) {
			matchActive = null;
			tournamentActive = null;
			Bukkit.getServer().getScheduler().runTaskLater((Plugin) this, new Runnable() {
				public void run() {
					if (matches != null && !matches.isEmpty() && Bukkit.getOnlinePlayers().size() >= minPlayers) {
						if (!forzado) {

							if (probabilityRandomEvent > random.nextInt(100)) {
								if (probabilityRandomEventTournament > random.nextInt(100)) {
									tournamentActive = new TournamentActive(tournament, getPlugin(), false);
								} else {
									matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(), matches,
											false);
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
														matches, false);
											}
										} else {
											matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
													matches, false);
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
		this.api = new API1711("%%__USER__%%", "RandomEvents");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (label.equals(Comandos.COMANDO_ALIASE1) || label.equals(Comandos.COMANDO_ALIASE2)) {
			switch (args.length) {
			case 0:
				Comandos.muestraMenu(player);
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
					player.sendMessage(getLanguage().getTagPlugin() + " " + language.getInvalidCmd());
				} else {
					System.out.println(getLanguage().getTagPlugin() + " " + language.getInvalidCmd());
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
	
	
	

	
}
