package com.adri1711.randomevents.language;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;

public class LanguageMessages {
	private File file;
	private FileConfiguration fileConfig;
	private RandomEvents plugin;

	private String invalidInput;
	private String error;
	private String invalidCmd;
	private String noPermission;
	private String endOfArenaCreation;
	private String cancelOfArenaCreation;
	private String pluginReload;
	private String matchFull;
	private String alreadyPlayingMatch;
	private String matchBegun;
	private String matches;
	private String alreadyMatch;
	private String waitingForPlayers;
	private String spawnSet;
	private String notInMatch;
	private String winRandomEvents;
	private String eventAnnounce;
	private String invalidPassword;
	private String firstAnnounce;
	private String nextAnnounce;
	private String clickHere;
	private String lastPart;
	private String winners;
	private String winnersPoints;
	private String disposeLeatherItems;
	private String showAlone;
	private String showTeam;
	private String timeRemaining;
	private String nowPoints;
	private String nowGems;
	private String nowProtected;
	private String matchBeginSoon;
	private String lostGems;
	private String playerWinning;
	private String playerWinningSeconds;
	private String bombSeconds;
	private String playerHasBomb;
	private String bombExplode;
	private String leaveCommand;
	private String errorSavingInventory;

	public LanguageMessages(RandomEvents plugin) {
		this.file = new File(plugin.getDataFolder(), "messages.yml");
		this.fileConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);

		this.plugin = plugin;
		if (!this.file.exists()) {

			try {
				this.file.createNewFile();
				// TODO
				// this.fileConfig.set("guiLottery", "&2Lottery");
				fileConfig = setFileConfigDefault(fileConfig);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			fileConfig = compruebaFichero(fileConfig);
		}
		for (int i = 0; i < 20; i++) {
			this.fileConfig.options().copyDefaults(true);
			saveYamls();
		}
		recargaVariables(fileConfig);
		// this.announcement = this.fileConfig.getString("announcement");

	}

	private void recargaVariables(FileConfiguration fileConfig) {
		for (Constantes.Messages m : Constantes.Messages.values()) {
			try {
				Method method = this.getClass().getMethod(getComandoSet(m.getJavaField()), String.class);
				method.invoke(this, fileConfig.getString(m.getYmlField()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public String getComandoSet(String javaField) {
		String campo = javaField;
		String primeraLetra = campo.substring(0, 1);
		String campoFinal = primeraLetra.toUpperCase() + javaField.substring(1);

		return "set" + campoFinal;
	}

	public String getComandoGet(String javaField) {
		String campo = javaField;
		String primeraLetra = campo.substring(0, 1);
		String campoFinal = primeraLetra.toUpperCase() + javaField.substring(1);

		return "get" + campoFinal;
	}

	public String getField(String javaField) {

		Method method;
		String res = "";
		try {
			method = this.getClass().getMethod(getComandoGet(javaField));
			res = (String) method.invoke(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private FileConfiguration compruebaFichero(FileConfiguration fileConfig) {

		for (Constantes.Messages m : Constantes.Messages.values()) {
			if (fileConfig.getString(m.getYmlField()) == null) {
				fileConfig.set(m.getYmlField(), m.getMessageDefault());
			}
		}

		return fileConfig;
	}

	private FileConfiguration setFileConfigDefault(FileConfiguration fileConfig) {
		for (Constantes.Messages m : Constantes.Messages.values()) {
			fileConfig.set(m.getYmlField(), m.getMessageDefault());
		}
		return fileConfig;
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileConfiguration getFileConfig() {
		return this.fileConfig;
	}

	public void saveYamls() {
		try {
			this.fileConfig.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadYamls() {
		try {
			this.fileConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

	public String getInvalidInput() {
		return invalidInput.replaceAll("&", "§");
	}

	public void setInvalidInput(String invalidInput) {
		this.invalidInput = invalidInput;
	}

	public String getError() {
		return error.replaceAll("&", "§");
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getInvalidCmd() {
		return invalidCmd.replaceAll("&", "§");
	}

	public void setInvalidCmd(String invalidCmd) {
		this.invalidCmd = invalidCmd;
	}

	public String getNoPermission() {
		return noPermission.replaceAll("&", "§");
	}

	public void setNoPermission(String noPermission) {
		this.noPermission = noPermission;
	}

	public String getEndOfArenaCreation() {
		return endOfArenaCreation.replaceAll("&", "§");
	}

	public void setEndOfArenaCreation(String endOfArenaCreation) {
		this.endOfArenaCreation = endOfArenaCreation;
	}

	public String getCancelOfArenaCreation() {
		return cancelOfArenaCreation.replaceAll("&", "§");
	}

	public void setCancelOfArenaCreation(String cancelOfArenaCreation) {
		this.cancelOfArenaCreation = cancelOfArenaCreation;
	}

	public String getPluginReload() {
		return pluginReload.replaceAll("&", "§");
	}

	public void setPluginReload(String pluginReload) {
		this.pluginReload = pluginReload;
	}

	public String getMatchFull() {
		return matchFull.replaceAll("&", "§");
	}

	public void setMatchFull(String matchFull) {
		this.matchFull = matchFull;
	}

	public String getAlreadyPlayingMatch() {
		return alreadyPlayingMatch.replaceAll("&", "§");
	}

	public void setAlreadyPlayingMatch(String alreadyPlayingMatch) {
		this.alreadyPlayingMatch = alreadyPlayingMatch;
	}

	public String getMatchBegun() {
		return matchBegun.replaceAll("&", "§");
	}

	public void setMatchBegun(String matchBegun) {
		this.matchBegun = matchBegun;
	}

	public String getMatches() {
		return matches.replaceAll("&", "§");
	}

	public void setMatches(String matches) {
		this.matches = matches;
	}

	public String getAlreadyMatch() {
		return alreadyMatch.replaceAll("&", "§");
	}

	public void setAlreadyMatch(String alreadyMatch) {
		this.alreadyMatch = alreadyMatch;
	}

	public String getWaitingForPlayers() {
		return waitingForPlayers.replaceAll("&", "§");
	}

	public void setWaitingForPlayers(String waitingForPlayers) {
		this.waitingForPlayers = waitingForPlayers;
	}

	public String getSpawnSet() {
		return spawnSet.replaceAll("&", "§");
	}

	public void setSpawnSet(String spawnSet) {
		this.spawnSet = spawnSet;
	}

	public String getNotInMatch() {
		return notInMatch.replaceAll("&", "§");
	}

	public void setNotInMatch(String notInMatch) {
		this.notInMatch = notInMatch;
	}

	public String getWinRandomEvents() {
		return winRandomEvents.replaceAll("&", "§");
	}

	public void setWinRandomEvents(String winRandomEvents) {
		this.winRandomEvents = winRandomEvents;
	}

	public String getEventAnnounce() {
		return eventAnnounce.replaceAll("&", "§");
	}

	public void setEventAnnounce(String eventAnnounce) {
		this.eventAnnounce = eventAnnounce;
	}

	public String getInvalidPassword() {
		return invalidPassword.replaceAll("&", "§");
	}

	public void setInvalidPassword(String invalidPassword) {
		this.invalidPassword = invalidPassword;
	}

	public String getFirstAnnounce() {
		return firstAnnounce.replaceAll("&", "§");
	}

	public void setFirstAnnounce(String firstAnnounce) {
		this.firstAnnounce = firstAnnounce;
	}

	public String getNextAnnounce() {
		return nextAnnounce.replaceAll("&", "§");
	}

	public void setNextAnnounce(String nextAnnounce) {
		this.nextAnnounce = nextAnnounce;
	}

	public String getClickHere() {
		return clickHere.replaceAll("&", "§");
	}

	public void setClickHere(String clickHere) {
		this.clickHere = clickHere;
	}

	public String getLastPart() {
		return lastPart.replaceAll("&", "§");
	}

	public void setLastPart(String lastPart) {
		this.lastPart = lastPart;
	}

	public String getWinners() {
		return winners.replaceAll("&", "§");
	}

	public void setWinners(String winners) {
		this.winners = winners;
	}

	public String getWinnersPoints() {
		return winnersPoints.replaceAll("&", "§");
	}

	public void setWinnersPoints(String winnersPoints) {
		this.winnersPoints = winnersPoints;
	}

	public String getDisposeLeatherItems() {
		return disposeLeatherItems.replaceAll("&", "§");
	}

	public void setDisposeLeatherItems(String disposeLeatherItems) {
		this.disposeLeatherItems = disposeLeatherItems;
	}

	public String getShowAlone() {
		return showAlone.replaceAll("&", "§");
	}

	public void setShowAlone(String showAlone) {
		this.showAlone = showAlone;
	}

	public String getShowTeam() {
		return showTeam.replaceAll("&", "§");
	}

	public void setShowTeam(String showTeam) {
		this.showTeam = showTeam;
	}

	public String getTimeRemaining() {
		return timeRemaining.replaceAll("&", "§");
	}

	public void setTimeRemaining(String timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public String getNowPoints() {
		return nowPoints.replaceAll("&", "§");
	}

	public void setNowPoints(String nowPoints) {
		this.nowPoints = nowPoints;
	}

	public String getNowGems() {
		return nowGems.replaceAll("&", "§");
	}

	public void setNowGems(String nowGems) {
		this.nowGems = nowGems;
	}

	public String getNowProtected() {
		return nowProtected.replaceAll("&", "§");
	}

	public void setNowProtected(String nowProtected) {
		this.nowProtected = nowProtected;
	}

	public String getMatchBeginSoon() {
		return matchBeginSoon.replaceAll("&", "§");
	}

	public void setMatchBeginSoon(String matchBeginSoon) {
		this.matchBeginSoon = matchBeginSoon;
	}

	public String getLostGems() {
		return lostGems.replaceAll("&", "§");
	}

	public void setLostGems(String lostGems) {
		this.lostGems = lostGems;
	}

	public String getPlayerWinning() {
		return playerWinning.replaceAll("&", "§");
	}

	public void setPlayerWinning(String playerWinning) {
		this.playerWinning = playerWinning;
	}

	public String getPlayerWinningSeconds() {
		return playerWinningSeconds.replaceAll("&", "§");
	}

	public void setPlayerWinningSeconds(String playerWinningSeconds) {
		this.playerWinningSeconds = playerWinningSeconds;
	}

	public String getBombSeconds() {
		return bombSeconds.replaceAll("&", "§");
	}

	public void setBombSeconds(String bombSeconds) {
		this.bombSeconds = bombSeconds;
	}

	public String getPlayerHasBomb() {
		return playerHasBomb.replaceAll("&", "§");
	}

	public void setPlayerHasBomb(String playerHasBomb) {
		this.playerHasBomb = playerHasBomb;
	}

	public String getBombExplode() {
		return bombExplode.replaceAll("&", "§");
	}

	public void setBombExplode(String bombExplode) {
		this.bombExplode = bombExplode;
	}

	public String getLeaveCommand() {
		return leaveCommand.replaceAll("&", "§");
	}

	public void setLeaveCommand(String leaveCommand) {
		this.leaveCommand = leaveCommand;
	}

	public String getErrorSavingInventory() {
		return errorSavingInventory.replaceAll("&", "§");
	}

	public void setErrorSavingInventory(String errorSavingInventory) {
		this.errorSavingInventory = errorSavingInventory;
	}

	public void setFileConfig(FileConfiguration fileConfig) {
		this.fileConfig = fileConfig;
	}

}
