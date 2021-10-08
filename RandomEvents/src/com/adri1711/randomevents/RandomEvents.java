package com.adri1711.randomevents;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.adri1711.api.API1711;
import com.adri1711.randomevents.api.events.ReventSpawnEvent;
import com.adri1711.randomevents.bbdd.HikariCP;
import com.adri1711.randomevents.commands.Comandos;
import com.adri1711.randomevents.commands.ComandosExecutor;
import com.adri1711.randomevents.config.Configuration;
import com.adri1711.randomevents.config.ReventConfig;
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
import com.adri1711.randomevents.util.NameTagHook;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.randomevents.util.UtilsSQL;
import com.google.common.io.Files;

public class RandomEvents extends JavaPlugin {

	private Location spawn;

	private ComandosExecutor comandosExecutor;

	private API1711 api;

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

	private Boolean forzado = Boolean.FALSE;

	private LanguageMessages language;

	private List<Schedule> schedules;

	private Configuration config;

	private ReventConfig reventConfig;

	private HikariCP hikari;

	private NameTagHook nametagHook;

	private Scoreboard colorBoard;

	public void onEnable() {
		this.api = new API1711("%%__USER__%%", "RandomEvents");

		loadConfig();
		this.editando = new ArrayList<String>();
		this.comandosExecutor = new ComandosExecutor();
		this.cooldowns = new HashMap<String, Date>();

		inicializaVariables();

		if (getReventConfig().isMysqlEnabled()) {
			hikari = new HikariCP(getReventConfig().getMysqlHost(), getReventConfig().getMysqlPort().toString(),
					getReventConfig().getMysqlDatabase(), getReventConfig().getMysqlUsername(),
					getReventConfig().getMysqlPassword(), getReventConfig().getMysqlMaxLifeTime());
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
			nametagHook = new NameTagHook(this);
			System.out.println("[RandomEvents] NameTagEdit hooked succesfully!");

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
		UtilsRandomEvents.terminaCreacionBannedPlayers(this, reventConfig.getBannedPlayers());
		if (reventConfig.isMysqlEnabled() && hikari != null) {
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
		this.playerMatches = new HashMap<String, Match>();
		this.playersCreation = new HashMap<String, Integer>();
		this.playerWaterDrop = new HashMap<String, WaterDropStep>();
		this.playersCreationWaterDrop = new HashMap<String, Integer>();
		this.playerKit = new HashMap<String, Kit>();
		this.playersCreationKit = new HashMap<String, Integer>();
		this.playersEntity = new HashMap<String, EntityType>();
		reventConfig = new ReventConfig(this);
		reventConfig.inicializaVariables();

	}

	public void comienzaTemporizador() {
		if (!forzado) {
			matchActive = null;
			tournamentActive = null;
			Bukkit.getServer().getScheduler().runTaskLater((Plugin) this, new Runnable() {
				public void run() {

					if (matchesAvailable != null && !matchesAvailable.isEmpty()
							&& Bukkit.getOnlinePlayers().size() >= reventConfig.getMinPlayers()) {
						if (!forzado) {

							if (reventConfig.getProbabilityRandomEvent() > random.nextInt(100)) {
								if (reventConfig.getProbabilityRandomEventTournament() > random.nextInt(100)) {
									tournamentActive = new TournamentActive(tournament, getPlugin(), false);
								} else {
									matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
											matchesAvailable, false);
									try {
										Bukkit.getPluginManager().callEvent(new ReventSpawnEvent(matchActive,false));
									} catch (Exception e) {
										System.out.println("[RandomEvents] WARN :: Couldnt fire the ReventSpawnEvent.");
									}
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
												try {
													Bukkit.getPluginManager().callEvent(new ReventSpawnEvent(matchActive,false));
												} catch (Exception e) {
													System.out.println("[RandomEvents] WARN :: Couldnt fire the ReventSpawnEvent.");
												}
											} else {
												matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
														matchesAvailable, false);
												try {
													Bukkit.getPluginManager().callEvent(new ReventSpawnEvent(matchActive,false));
												} catch (Exception e) {
													System.out.println("[RandomEvents] WARN :: Couldnt fire the ReventSpawnEvent.");
												}
											}
										} else {
											matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(),
													matchesAvailable, false);
											try {
												Bukkit.getPluginManager().callEvent(new ReventSpawnEvent(matchActive,false));
											} catch (Exception e) {
												System.out.println("[RandomEvents] WARN :: Couldnt fire the ReventSpawnEvent.");
											}
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
			}, 20 * reventConfig.getSecondsTimer());
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
				Comandos.ejecutaComandoInfinitoArgumentos(this, player, args);

				// if (player != null) {
				// player.sendMessage(getLanguage().getTagPlugin() +
				// language.getInvalidCmd());
				// } else {
				// System.out.println(getLanguage().getTagPlugin() +
				// language.getInvalidCmd());
				// }
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
		config = new Configuration(this, "config.yml");

		//
		//
		// if (!(new File(getDataFolder() + File.separator +
		// "config.yml")).exists()) {
		// saveDefaultConfig();
		// }
		//
		// try {
		// (new YamlConfiguration()).load(new File(getDataFolder() +
		// File.separator + "config.yml"));
		// } catch (Exception e) {
		// System.out.println("--- --- RandomEvents --- ---");
		// System.out.println("There was an error loading your configuration.");
		// System.out.println("A detailed description of your error is shown
		// below.");
		// System.out.println("--- --- --- ---");
		// e.printStackTrace();
		// Bukkit.getPluginManager().disablePlugin((Plugin) this);
		//
		// return false;
		// }
		// reloadConfig();
		//
		// loadConfiguration();
		return true;
	}

	public void updateConfig() {

		saveResource("defaultConfig.yml", false);

		try {
			File defaultConfig = new File(this.getDataFolder() + File.separator + "defaultConfig.yml");
			if (defaultConfig.exists()) {
				Configuration cfg = new Configuration(this, "defaultConfig.yml");

				File fileConfig = new File(this.getDataFolder() + File.separator + "config.yml");
				if (fileConfig.exists()) {
					boolean changesMade = false;
					Configuration tmp = new Configuration(this, "config.yml");

					for (String str : cfg.getKeys(true)) {
						if (!tmp.getKeys(true).contains(str)) {
							// tmp.set(str, cfg.get(str));
							changesMade = true;
						}
					}
					if (changesMade) {

						for (String str : tmp.getKeys(true)) {
							cfg.set(str, tmp.get(str));
						}

						cfg.saveData();
						Files.copy(defaultConfig, fileConfig);
						config = new Configuration(this, "config.yml");
					}

				}

				if (defaultConfig.exists()) {
					cfg.delete();

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

	public List<String> getEditando() {
		return editando;
	}

	public void setEditando(List<String> editando) {
		this.editando = editando;
	}

	public TournamentActive getTournamentActive() {
		return tournamentActive;
	}

	public void setTournamentActive(TournamentActive tournamentActive) {
		this.tournamentActive = tournamentActive;
	}

	public List<WaterDropStep> getWaterDrops() {
		return waterDrops;
	}

	public void setWaterDrops(List<WaterDropStep> waterDrops) {
		this.waterDrops = waterDrops;
	}

	public List<Kit> getKits() {
		return kits;
	}

	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}

	public List<Match> getMatchesAvailable() {
		return matchesAvailable;
	}

	public void setMatchesAvailable(List<Match> matchesAvailable) {
		this.matchesAvailable = matchesAvailable;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public Map<String, Date> getCooldowns() {
		return cooldowns;
	}

	public void setCooldowns(Map<String, Date> cooldowns) {
		this.cooldowns = cooldowns;
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

	public LanguageMessages getLanguage() {
		return language;
	}

	public void setLanguage(LanguageMessages language) {
		this.language = language;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public ReventConfig getReventConfig() {
		return reventConfig;
	}

	public void setReventConfig(ReventConfig reventConfig) {
		this.reventConfig = reventConfig;
	}

	public HikariCP getHikari() {
		return hikari;
	}

	public void setHikari(HikariCP hikari) {
		this.hikari = hikari;
	}

	public NameTagHook getNametagHook() {
		return nametagHook;
	}

	public void setNametagHook(NameTagHook nametagHook) {
		this.nametagHook = nametagHook;
	}

	public Scoreboard getColorBoard() {
		return colorBoard;
	}

	public void setColorBoard(Scoreboard colorBoard) {
		this.colorBoard = colorBoard;
	}

}
