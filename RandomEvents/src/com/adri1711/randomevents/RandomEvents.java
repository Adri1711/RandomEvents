package com.adri1711.randomevents;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.adri1711.api.API1711;
import com.adri1711.randomevents.commands.Comandos;
import com.adri1711.randomevents.commands.ComandosExecutor;
import com.adri1711.randomevents.listeners.Chat;
import com.adri1711.randomevents.listeners.Death;
import com.adri1711.randomevents.listeners.Join;
import com.adri1711.randomevents.listeners.Quit;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;

public class RandomEvents extends JavaPlugin {

	private Location spawn;

	private Integer secondsTimer;

	private Integer minPlayers;

	private ComandosExecutor comandosExecutor;

	private API1711 api;

	private Integer probabilityRandomEvent;

	private Random random;

	private MatchActive matchActive;

	private List<Match> matches;

	private Map<Player, Match> playerMatches;

	private Map<Player, Integer> playersCreation;

	private Map<Player, EntityType> playersEntity;

	private List<String> commandsOnUserJoin;

	public void onEnable() {
		loadConfig();
		this.api = new API1711("%%__USER__%%", "RandomEvents");
		this.comandosExecutor = new ComandosExecutor();
		inicializaVariables();

		getServer().getPluginManager().registerEvents((Listener) new Quit(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Chat(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Death(this), (Plugin) this);
		getServer().getPluginManager().registerEvents((Listener) new Join(this), (Plugin) this);

		getLogger().info(" Author adri1711- activado");
		comienzaTemporizador();

	}

	public void onDisable() {

		getServer().getScheduler().cancelTasks((Plugin) this);
		getLogger().info(" Author adri1711 - desactivado");
	}

	public void inicializaVariables() {
		this.random = new Random();

		this.spawn = (Location) getConfig().get("spawn");

		this.minPlayers = Integer.valueOf(getConfig().getInt("minPlayers"));
		this.commandsOnUserJoin = (List<String>) getConfig().getStringList("commandsOnUserJoin");
		this.secondsTimer = Integer.valueOf(getConfig().getInt("secondsTimer"));

		this.probabilityRandomEvent = Integer.valueOf(getConfig().getInt("probabilityRandomEvent"));

		this.matches = UtilsRandomEvents.cargarPartidas(this);

		this.playerMatches = new HashMap<Player, Match>();
		this.playersCreation = new HashMap<Player, Integer>();

	}

	public void comienzaTemporizador() {
		matchActive = null;

		Bukkit.getServer().getScheduler().runTaskLater((Plugin) this, new Runnable() {
			public void run() {
				if (matches != null && !matches.isEmpty() && Bukkit.getOnlinePlayers().size() >= minPlayers) {
					if (probabilityRandomEvent > random.nextInt(100)) {
						matchActive = UtilsRandomEvents.escogeMatchActiveAleatoria(getPlugin(), matches);
					} else {
						comienzaTemporizador();
					}
				} else {
					comienzaTemporizador();
				}
			}
		}, 20 * secondsTimer);

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
		if (sender instanceof Player) {
			Player player = (Player) sender;
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
				default:
					player.sendMessage(Constantes.TAG_PLUGIN + " " + Constantes.COMANDO_NO_VALIDO);
					break;
				}
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

	public Map<Player, Match> getPlayerMatches() {
		return playerMatches;
	}

	public void setPlayerMatches(Map<Player, Match> playerMatches) {
		this.playerMatches = playerMatches;
	}

	public Map<Player, Integer> getPlayersCreation() {
		return playersCreation;
	}

	public void setPlayersCreation(Map<Player, Integer> playersCreation) {
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

	public Map<Player, EntityType> getPlayersEntity() {
		return playersEntity;
	}

	public void setPlayersEntity(Map<Player, EntityType> playersEntity) {
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

}
