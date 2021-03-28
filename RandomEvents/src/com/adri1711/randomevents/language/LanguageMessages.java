package com.adri1711.randomevents.language;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;
import com.google.common.io.Files;

public class LanguageMessages {
	private File file;
	private FileConfiguration fileConfig;
	private RandomEvents plugin;

	private String tagChat;
	private String invalidInput;
	private String error;
	private String invalidCmd;
	private String noPermission;
	private String endOfArenaCreation;
	private String endOfKitCreation;
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
	private String lastPartTournament;
	private String firstAnnounceTournament;
	private String nextAnnounceTournament;
	private String winnersTournament;
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
	private String tournamentAlive;
	private String youBeast;
	private String endOfScheduleCreation;
	private String raceTournament;
	private String tagPlugin;
	private String eventCancelled;
	private String eventStopped;
	private String tournamentCancelled;

	private String statsWins;
	private String statsTries;
	private String statsDisabled;
	private String statsWinsRatio;

	private String cmdNotAllowed;

	private String clearInventory;

	private String secondsRemaining3;
	private String secondsRemaining2;
	private String secondsRemaining1;

	private String youAreBanned;
	private String banPlayer;
	private String unbanPlayer;
	private String playerNotBanned;

	private String idleDamage;

	private String noScheduledEvents;
	private String nextEvent;

	private String nextEventIsRandom;

	private String nextEventName;
	private String startingMatch;
	private String cancelOfMatchCreation;

	private String lacksInfoCreation;
	private String shrink;
	private String warmupEnd;
	private String playersMove;
	private String helpMenu;
	private String showMenu;
	private String join;
	private String spec;
	private String tjoin;
	private String leave;
	private String tleave;
	private String stats;
	private String statsOther;
	private String forcestop;
	private String spawnset;
	private String tspawnset;
	private String menuMatches;
	private String next;
	private String date;
	private String schedule;
	private String scheduleSpecific;
	private String begin;
	private String tbegin;
	private String beginSpecific;
	private String ban;
	private String unban;
	private String create;
	private String edit;
	private String cancel;
	private String reload;
	private String checkpoint;
	private String forcebegin;
	private String kitsCmd;
	private String kitsEditCmd;

	private String announceFirst;
	private String announceNext;
	private String announceFirstTournament;
	private String announceNextTournament;

	private String statsGuiName;
	private String creditsGuiName;
	private String teamGuiName;

	private String scoreboardTitle;
	private String scoreboardPoints;
	private String scoreboardAlive;
	private String scoreboardDeath;
	private String scoreboardTeammate;
	private String scoreboardTime;
	private String scoreboardBeast;
	private String scoreboardHolder;
	private String scoreboardStep;
	private String scoreboardTeamAlive;
	private String scoreboardTeamDeath;
	private String scoreboardTeam;
	private String scoreboardPointsTeam;
	private String scoreboardTeamPoints;

	private String pvpDeath;
	private String pvpKill;
	private String bowKill;

	private String eventDeleted;
	private String eventDisabled;
	private String eventEnabled;
	private String eventIsDisabled;

	private String delete;
	private String disable;
	private String enable;

	private String creditsAddedOther;
	private String creditsAdded;
	private String creditsBal;
	private String creditsCooldown;
	private String creditsReady;

	private String giveCreditsSpecific;
	private String giveCreditsRandom;
	private String creditsBalOther;
	private String creditsBalOwn;
	private String playerOffline;
	private String eventInvalid;
	private String creditsEventRunning;

	private String guiCMD;

	private String teamItemName;

	private String creditsGuiPage;

	private String itemReturnCheckpoint;
	private String itemHidePlayer;
	private String itemShowPlayer;

	private String kitItemName;
	private String kitDefaultName;
	private String kitGuiName;
	private String kitChosen;

	private List<String> kitDefaultLore;

	private List<String> minigameDescriptionBR;
	private List<String> minigameDescriptionBRT2;
	private List<String> minigameDescriptionLJ;
	private List<String> minigameDescriptionTKLL;
	private List<String> minigameDescriptionTKLLT2;
	private List<String> minigameDescriptionKBD;
	private List<String> minigameDescriptionEARR;
	private List<String> minigameDescriptionGEMC;
	private List<String> minigameDescriptionBOMB;
	private List<String> minigameDescriptionBOAT_RUN;
	private List<String> minigameDescriptionHORSE_RUN;
	private List<String> minigameDescriptionESCAPE_FROM_BEAST;
	private List<String> minigameDescriptionRACE;
	private List<String> minigameDescriptionTNTRUN;
	private List<String> minigameDescriptionSPLEEF;
	private List<String> minigameDescriptionSPLEGG;
	private List<String> minigameDescriptionOITC;
	private List<String> minigameDescriptionSG;
	private List<String> minigameDescriptionTSG;
	private List<String> minigameDescriptionSW;
	private List<String> minigameDescriptionTSW;
	private List<String> minigameDescriptionANVIL_SPLEEF;
	private List<String> minigameDescriptionWDROP;
	private List<String> minigameDescriptionQUAKE;
	private List<String> minigameDescriptionPBALL;
	private List<String> minigameDescriptionKOTH;
	private List<String> minigameDescriptionFISHSLAP;
	private List<String> minigameDescriptionHOE;
	private List<String> minigameDescriptionSPLATOON;
	private List<String> minigameDescriptionBOMBARDMENT;

	public LanguageMessages(RandomEvents plugin) {
		this.file = new File(plugin.getDataFolder(), "messages.yml");
		File fileOld = new File(plugin.getDataFolder(), "messages_backup.yml");
		this.fileConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);

		this.plugin = plugin;
		if (!this.file.exists()) {

			try {
				this.file.createNewFile();

				fileConfig = setFileConfigDefault(fileConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			fileConfig = compruebaFichero(fileConfig);
			try {
				if (fileOld.exists()) {
					fileOld.delete();
				}
				Files.copy(file, fileOld);
			} catch (Exception e) {

			}
		}
		for (int i = 0; i < 20; i++) {
			this.fileConfig.options().copyDefaults(true);
			saveYamls();
		}

		recargaVariables(fileConfig);

	}

	private void recargaVariables(FileConfiguration fileConfig) {
		for (Constantes.Messages m : Constantes.Messages.values()) {
			try {
				if (!m.getMessageDefault().contains(";")) {
					Method method = this.getClass().getMethod(getComandoSet(m.getJavaField()), String.class);
					method.invoke(this, fileConfig.getString(m.getYmlField()));
				} else {
					Method method = this.getClass().getMethod(getComandoSet(m.getJavaField()), List.class);
					method.invoke(this, fileConfig.getStringList(m.getYmlField()));
				}
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

			if (!m.getMessageDefault().contains(";")) {
				if (fileConfig.getString(m.getYmlField()) == null) {
					fileConfig.set(m.getYmlField(), m.getMessageDefault());
				}
			} else {
				if (!fileConfig.contains(m.getYmlField())) {

					String[] listaString = m.getMessageDefault().split(";");

					fileConfig.set(m.getYmlField(), listaString);
				}
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

	public String getEndOfKitCreation() {
		return endOfKitCreation.replaceAll("&", "§");
	}

	public void setEndOfKitCreation(String endOfKitCreation) {
		this.endOfKitCreation = endOfKitCreation;
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

	public String getLastPartTournament() {
		return lastPartTournament.replaceAll("&", "§");
	}

	public void setLastPartTournament(String lastPartTournament) {
		this.lastPartTournament = lastPartTournament;
	}

	public String getFirstAnnounceTournament() {
		return firstAnnounceTournament.replaceAll("&", "§");
	}

	public void setFirstAnnounceTournament(String firstAnnounceTournament) {
		this.firstAnnounceTournament = firstAnnounceTournament;
	}

	public String getNextAnnounceTournament() {
		return nextAnnounceTournament.replaceAll("&", "§");
	}

	public void setNextAnnounceTournament(String nextAnnounceTournament) {
		this.nextAnnounceTournament = nextAnnounceTournament;
	}

	public String getRaceTournament() {
		return raceTournament.replaceAll("&", "§");
	}

	public void setRaceTournament(String raceTournament) {
		this.raceTournament = raceTournament;
	}

	public String getWinnersTournament() {
		return winnersTournament.replaceAll("&", "§");
	}

	public void setWinnersTournament(String winnersTournament) {
		this.winnersTournament = winnersTournament;
	}

	public String getTournamentAlive() {
		return tournamentAlive.replaceAll("&", "§");
	}

	public void setTournamentAlive(String tournamentAlive) {
		this.tournamentAlive = tournamentAlive;
	}

	public String getYouBeast() {
		return youBeast.replaceAll("&", "§");
	}

	public void setYouBeast(String youBeast) {
		this.youBeast = youBeast;
	}

	public String getEndOfScheduleCreation() {
		return endOfScheduleCreation.replaceAll("&", "§");
	}

	public void setEndOfScheduleCreation(String endOfScheduleCreation) {
		this.endOfScheduleCreation = endOfScheduleCreation;
	}

	public String getTagPlugin() {
		return tagPlugin.replaceAll("&", "§");
	}

	public void setTagPlugin(String tagPlugin) {
		this.tagPlugin = tagPlugin;
	}

	public String getEventCancelled() {
		return eventCancelled.replaceAll("&", "§");
	}

	public void setEventCancelled(String eventCancelled) {
		this.eventCancelled = eventCancelled;
	}

	public String getTournamentCancelled() {
		return tournamentCancelled.replaceAll("&", "§");
	}

	public void setTournamentCancelled(String tournamentCancelled) {
		this.tournamentCancelled = tournamentCancelled;
	}

	public String getCmdNotAllowed() {
		return cmdNotAllowed.replaceAll("&", "§");
	}

	public void setCmdNotAllowed(String cmdNotAllowed) {
		this.cmdNotAllowed = cmdNotAllowed;
	}

	public String getStatsWins() {
		return statsWins.replaceAll("&", "§");
	}

	public void setStatsWins(String statsWins) {
		this.statsWins = statsWins;
	}

	public String getStatsTries() {
		return statsTries.replaceAll("&", "§");
	}

	public void setStatsTries(String statsTries) {
		this.statsTries = statsTries;
	}

	public String getStatsDisabled() {
		return statsDisabled.replaceAll("&", "§");
	}

	public void setStatsDisabled(String statsDisabled) {
		this.statsDisabled = statsDisabled;
	}

	public String getStatsWinsRatio() {
		return statsWinsRatio.replaceAll("&", "§");
	}

	public void setStatsWinsRatio(String statsWinsRatio) {
		this.statsWinsRatio = statsWinsRatio;
	}

	public List<String> getMinigameDescriptionBR() {
		return minigameDescriptionBR;
	}

	public void setMinigameDescriptionBR(List<String> minigameDescriptionBR) {
		this.minigameDescriptionBR = minigameDescriptionBR;
	}

	public List<String> getMinigameDescriptionBRT2() {
		return minigameDescriptionBRT2;
	}

	public void setMinigameDescriptionBRT2(List<String> minigameDescriptionBRT2) {
		this.minigameDescriptionBRT2 = minigameDescriptionBRT2;
	}

	public List<String> getMinigameDescriptionLJ() {
		return minigameDescriptionLJ;
	}

	public void setMinigameDescriptionLJ(List<String> minigameDescriptionLJ) {
		this.minigameDescriptionLJ = minigameDescriptionLJ;
	}

	public List<String> getMinigameDescriptionTKLL() {
		return minigameDescriptionTKLL;
	}

	public void setMinigameDescriptionTKLL(List<String> minigameDescriptionTKLL) {
		this.minigameDescriptionTKLL = minigameDescriptionTKLL;
	}

	public List<String> getMinigameDescriptionTKLLT2() {
		return minigameDescriptionTKLLT2;
	}

	public void setMinigameDescriptionTKLLT2(List<String> minigameDescriptionTKLLT2) {
		this.minigameDescriptionTKLLT2 = minigameDescriptionTKLLT2;
	}

	public List<String> getMinigameDescriptionKBD() {
		return minigameDescriptionKBD;
	}

	public void setMinigameDescriptionKBD(List<String> minigameDescriptionKBD) {
		this.minigameDescriptionKBD = minigameDescriptionKBD;
	}

	public List<String> getMinigameDescriptionEARR() {
		return minigameDescriptionEARR;
	}

	public void setMinigameDescriptionEARR(List<String> minigameDescriptionEARR) {
		this.minigameDescriptionEARR = minigameDescriptionEARR;
	}

	public List<String> getMinigameDescriptionGEMC() {
		return minigameDescriptionGEMC;
	}

	public void setMinigameDescriptionGEMC(List<String> minigameDescriptionGEMC) {
		this.minigameDescriptionGEMC = minigameDescriptionGEMC;
	}

	public List<String> getMinigameDescriptionBOMB() {
		return minigameDescriptionBOMB;
	}

	public void setMinigameDescriptionBOMB(List<String> minigameDescriptionBOMB) {
		this.minigameDescriptionBOMB = minigameDescriptionBOMB;
	}

	public List<String> getMinigameDescriptionBOAT_RUN() {
		return minigameDescriptionBOAT_RUN;
	}

	public void setMinigameDescriptionBOAT_RUN(List<String> minigameDescriptionBOAT_RUN) {
		this.minigameDescriptionBOAT_RUN = minigameDescriptionBOAT_RUN;
	}

	public List<String> getMinigameDescriptionHORSE_RUN() {
		return minigameDescriptionHORSE_RUN;
	}

	public void setMinigameDescriptionHORSE_RUN(List<String> minigameDescriptionHORSE_RUN) {
		this.minigameDescriptionHORSE_RUN = minigameDescriptionHORSE_RUN;
	}

	public List<String> getMinigameDescriptionESCAPE_FROM_BEAST() {
		return minigameDescriptionESCAPE_FROM_BEAST;
	}

	public void setMinigameDescriptionESCAPE_FROM_BEAST(List<String> minigameDescriptionESCAPE_FROM_BEAST) {
		this.minigameDescriptionESCAPE_FROM_BEAST = minigameDescriptionESCAPE_FROM_BEAST;
	}

	public List<String> getMinigameDescriptionRACE() {
		return minigameDescriptionRACE;
	}

	public void setMinigameDescriptionRACE(List<String> minigameDescriptionRACE) {
		this.minigameDescriptionRACE = minigameDescriptionRACE;
	}

	public List<String> getMinigameDescriptionOITC() {
		return minigameDescriptionOITC;
	}

	public void setMinigameDescriptionOITC(List<String> minigameDescriptionOITC) {
		this.minigameDescriptionOITC = minigameDescriptionOITC;
	}

	public List<String> getMinigameDescriptionTNTRUN() {
		return minigameDescriptionTNTRUN;
	}

	public void setMinigameDescriptionTNTRUN(List<String> minigameDescriptionTNTRUN) {
		this.minigameDescriptionTNTRUN = minigameDescriptionTNTRUN;
	}

	public List<String> getMinigameDescriptionSPLEEF() {
		return minigameDescriptionSPLEEF;
	}

	public void setMinigameDescriptionSPLEEF(List<String> minigameDescriptionSPLEEF) {
		this.minigameDescriptionSPLEEF = minigameDescriptionSPLEEF;
	}

	public List<String> getMinigameDescriptionSPLEGG() {
		return minigameDescriptionSPLEGG;
	}

	public void setMinigameDescriptionSPLEGG(List<String> minigameDescriptionSPLEGG) {
		this.minigameDescriptionSPLEGG = minigameDescriptionSPLEGG;
	}

	public String getEventStopped() {
		return eventStopped.replaceAll("&", "§");
	}

	public void setEventStopped(String eventStopped) {
		this.eventStopped = eventStopped;
	}

	public String getClearInventory() {
		return clearInventory.replaceAll("&", "§");
	}

	public void setClearInventory(String clearInventory) {
		this.clearInventory = clearInventory;
	}

	public String getSecondsRemaining3() {
		return secondsRemaining3.replaceAll("&", "§");
	}

	public void setSecondsRemaining3(String secondsRemaining3) {
		this.secondsRemaining3 = secondsRemaining3;
	}

	public String getSecondsRemaining2() {
		return secondsRemaining2.replaceAll("&", "§");
	}

	public void setSecondsRemaining2(String secondsRemaining2) {
		this.secondsRemaining2 = secondsRemaining2;
	}

	public String getSecondsRemaining1() {
		return secondsRemaining1.replaceAll("&", "§");
	}

	public void setSecondsRemaining1(String secondsRemaining1) {
		this.secondsRemaining1 = secondsRemaining1;
	}

	public String getYouAreBanned() {
		return youAreBanned.replaceAll("&", "§");
	}

	public void setYouAreBanned(String youAreBanned) {
		this.youAreBanned = youAreBanned;
	}

	public String getBanPlayer() {
		return banPlayer.replaceAll("&", "§");
	}

	public void setBanPlayer(String banPlayer) {
		this.banPlayer = banPlayer;
	}

	public String getUnbanPlayer() {
		return unbanPlayer.replaceAll("&", "§");
	}

	public void setUnbanPlayer(String unbanPlayer) {
		this.unbanPlayer = unbanPlayer;
	}

	public String getPlayerNotBanned() {
		return playerNotBanned.replaceAll("&", "§");
	}

	public void setPlayerNotBanned(String playerNotBanned) {
		this.playerNotBanned = playerNotBanned;
	}

	public String getIdleDamage() {
		return idleDamage.replaceAll("&", "§");
	}

	public void setIdleDamage(String idleDamage) {
		this.idleDamage = idleDamage;
	}

	public String getNoScheduledEvents() {
		return noScheduledEvents.replaceAll("&", "§");
	}

	public void setNoScheduledEvents(String noScheduledEvents) {
		this.noScheduledEvents = noScheduledEvents;
	}

	public String getNextEvent() {
		return nextEvent.replaceAll("&", "§");
	}

	public void setNextEvent(String nextEvent) {
		this.nextEvent = nextEvent;
	}

	public String getNextEventIsRandom() {
		return nextEventIsRandom.replaceAll("&", "§");
	}

	public void setNextEventIsRandom(String nextEventIsRandom) {
		this.nextEventIsRandom = nextEventIsRandom;
	}

	public String getNextEventName() {
		return nextEventName.replaceAll("&", "§");
	}

	public void setNextEventName(String nextEventName) {
		this.nextEventName = nextEventName;
	}

	public String getStartingMatch() {
		return startingMatch.replaceAll("&", "§");
	}

	public void setStartingMatch(String startingMatch) {
		this.startingMatch = startingMatch;
	}

	public String getCancelOfMatchCreation() {
		return cancelOfMatchCreation.replaceAll("&", "§");
	}

	public void setCancelOfMatchCreation(String cancelOfMatchCreation) {
		this.cancelOfMatchCreation = cancelOfMatchCreation;
	}

	public String getLacksInfoCreation() {
		return lacksInfoCreation.replaceAll("&", "§");
	}

	public void setLacksInfoCreation(String lacksInfoCreation) {
		this.lacksInfoCreation = lacksInfoCreation;
	}

	public String getShrink() {
		return shrink.replaceAll("&", "§");
	}

	public void setShrink(String shrink) {
		this.shrink = shrink;
	}

	public List<String> getMinigameDescriptionSG() {
		return minigameDescriptionSG;
	}

	public void setMinigameDescriptionSG(List<String> minigameDescriptionSG) {
		this.minigameDescriptionSG = minigameDescriptionSG;
	}

	public List<String> getMinigameDescriptionTSG() {
		return minigameDescriptionTSG;
	}

	public void setMinigameDescriptionTSG(List<String> minigameDescriptionTSG) {
		this.minigameDescriptionTSG = minigameDescriptionTSG;
	}

	public List<String> getMinigameDescriptionSW() {
		return minigameDescriptionSW;
	}

	public void setMinigameDescriptionSW(List<String> minigameDescriptionSW) {
		this.minigameDescriptionSW = minigameDescriptionSW;
	}

	public List<String> getMinigameDescriptionTSW() {
		return minigameDescriptionTSW;
	}

	public void setMinigameDescriptionTSW(List<String> minigameDescriptionTSW) {
		this.minigameDescriptionTSW = minigameDescriptionTSW;
	}

	public List<String> getMinigameDescriptionANVIL_SPLEEF() {
		return minigameDescriptionANVIL_SPLEEF;
	}

	public void setMinigameDescriptionANVIL_SPLEEF(List<String> minigameDescriptionANVIL_SPLEEF) {
		this.minigameDescriptionANVIL_SPLEEF = minigameDescriptionANVIL_SPLEEF;
	}

	public String getWarmupEnd() {
		return warmupEnd.replaceAll("&", "§");
	}

	public void setWarmupEnd(String warmupEnd) {
		this.warmupEnd = warmupEnd;
	}

	public String getPlayersMove() {
		return playersMove.replaceAll("&", "§");
	}

	public void setPlayersMove(String playersMove) {
		this.playersMove = playersMove;
	}

	public String getHelpMenu() {
		return helpMenu.replaceAll("&", "§");
	}

	public void setHelpMenu(String helpMenu) {
		this.helpMenu = helpMenu;
	}

	public String getShowMenu() {
		return showMenu.replaceAll("&", "§");
	}

	public void setShowMenu(String showMenu) {
		this.showMenu = showMenu;
	}

	public String getJoin() {
		return join.replaceAll("&", "§");
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public String getTjoin() {
		return tjoin.replaceAll("&", "§");
	}

	public void setTjoin(String tjoin) {
		this.tjoin = tjoin;
	}

	public String getLeave() {
		return leave.replaceAll("&", "§");
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getTleave() {
		return tleave.replaceAll("&", "§");
	}

	public void setTleave(String tleave) {
		this.tleave = tleave;
	}

	public String getStats() {
		return stats.replaceAll("&", "§");
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getStatsOther() {
		return statsOther.replaceAll("&", "§");
	}

	public void setStatsOther(String statsOther) {
		this.statsOther = statsOther;
	}

	public String getForcestop() {
		return forcestop.replaceAll("&", "§");
	}

	public void setForcestop(String forcestop) {
		this.forcestop = forcestop;
	}

	public String getSpawnset() {
		return spawnset.replaceAll("&", "§");
	}

	public void setSpawnset(String spawnset) {
		this.spawnset = spawnset;
	}

	public String getTspawnset() {
		return tspawnset.replaceAll("&", "§");
	}

	public void setTspawnset(String tspawnset) {
		this.tspawnset = tspawnset;
	}

	public String getMenuMatches() {
		return menuMatches.replaceAll("&", "§");
	}

	public void setMenuMatches(String menuMatches) {
		this.menuMatches = menuMatches;
	}

	public String getNext() {
		return next.replaceAll("&", "§");
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getDate() {
		return date.replaceAll("&", "§");
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSchedule() {
		return schedule.replaceAll("&", "§");
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getScheduleSpecific() {
		return scheduleSpecific.replaceAll("&", "§");
	}

	public void setScheduleSpecific(String scheduleSpecific) {
		this.scheduleSpecific = scheduleSpecific;
	}

	public String getBegin() {
		return begin.replaceAll("&", "§");
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getTbegin() {
		return tbegin.replaceAll("&", "§");
	}

	public void setTbegin(String tbegin) {
		this.tbegin = tbegin;
	}

	public String getBeginSpecific() {
		return beginSpecific.replaceAll("&", "§");
	}

	public void setBeginSpecific(String beginSpecific) {
		this.beginSpecific = beginSpecific;
	}

	public String getBan() {
		return ban.replaceAll("&", "§");
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getUnban() {
		return unban.replaceAll("&", "§");
	}

	public void setUnban(String unban) {
		this.unban = unban;
	}

	public String getCreate() {
		return create.replaceAll("&", "§");
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getEdit() {
		return edit.replaceAll("&", "§");
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getCancel() {
		return cancel.replaceAll("&", "§");
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getReload() {
		return reload.replaceAll("&", "§");
	}

	public void setReload(String reload) {
		this.reload = reload;
	}

	public String getCheckpoint() {
		return checkpoint.replaceAll("&", "§");
	}

	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}

	public String getScoreboardTitle() {
		return scoreboardTitle.replaceAll("&", "§");
	}

	public void setScoreboardTitle(String scoreboardTitle) {
		this.scoreboardTitle = scoreboardTitle;
	}

	public String getScoreboardPoints() {
		return scoreboardPoints.replaceAll("&", "§");
	}

	public void setScoreboardPoints(String scoreboardPoints) {
		this.scoreboardPoints = scoreboardPoints;
	}

	public String getScoreboardAlive() {
		return scoreboardAlive.replaceAll("&", "§");
	}

	public void setScoreboardAlive(String scoreboardAlive) {
		this.scoreboardAlive = scoreboardAlive;
	}

	public String getScoreboardDeath() {
		return scoreboardDeath.replaceAll("&", "§");
	}

	public void setScoreboardDeath(String scoreboardDeath) {
		this.scoreboardDeath = scoreboardDeath;
	}

	public String getScoreboardTeammate() {
		return scoreboardTeammate.replaceAll("&", "§");
	}

	public void setScoreboardTeammate(String scoreboardTeammate) {
		this.scoreboardTeammate = scoreboardTeammate;
	}

	public String getScoreboardTeamAlive() {
		return scoreboardTeamAlive.replaceAll("&", "§");
	}

	public void setScoreboardTeamAlive(String scoreboardTeamAlive) {
		this.scoreboardTeamAlive = scoreboardTeamAlive;
	}

	public String getScoreboardTeamDeath() {
		return scoreboardTeamDeath.replaceAll("&", "§");
	}

	public void setScoreboardTeamDeath(String scoreboardTeamDeath) {
		this.scoreboardTeamDeath = scoreboardTeamDeath;
	}

	public String getScoreboardTeam() {
		return scoreboardTeam.replaceAll("&", "§");
	}

	public void setScoreboardTeam(String scoreboardTeam) {
		this.scoreboardTeam = scoreboardTeam;
	}

	public String getScoreboardTime() {
		return scoreboardTime.replaceAll("&", "§");
	}

	public void setScoreboardTime(String scoreboardTime) {
		this.scoreboardTime = scoreboardTime;
	}

	public String getScoreboardBeast() {
		return scoreboardBeast.replaceAll("&", "§");
	}

	public void setScoreboardBeast(String scoreboardBeast) {
		this.scoreboardBeast = scoreboardBeast;
	}

	public String getScoreboardHolder() {
		return scoreboardHolder.replaceAll("&", "§");
	}

	public void setScoreboardHolder(String scoreboardHolder) {
		this.scoreboardHolder = scoreboardHolder;
	}

	public String getPvpDeath() {
		return pvpDeath.replaceAll("&", "§");
	}

	public void setPvpDeath(String pvpDeath) {
		this.pvpDeath = pvpDeath;
	}

	public String getPvpKill() {
		return pvpKill.replaceAll("&", "§");
	}

	public void setPvpKill(String pvpKill) {
		this.pvpKill = pvpKill;
	}

	public String getForcebegin() {
		return forcebegin.replaceAll("&", "§");
	}

	public void setForcebegin(String forcebegin) {
		this.forcebegin = forcebegin;
	}

	public String getKitsCmd() {
		return kitsCmd.replaceAll("&", "§");
	}

	public void setKitsCmd(String kitsCmd) {
		this.kitsCmd = kitsCmd;
	}

	public String getKitsEditCmd() {
		return kitsEditCmd.replaceAll("&", "§");
	}

	public void setKitsEditCmd(String kitsEditCmd) {
		this.kitsEditCmd = kitsEditCmd;
	}

	public String getEventDeleted() {
		return eventDeleted.replaceAll("&", "§");
	}

	public void setEventDeleted(String eventDeleted) {
		this.eventDeleted = eventDeleted;
	}

	public String getEventDisabled() {
		return eventDisabled.replaceAll("&", "§");
	}

	public void setEventDisabled(String eventDisabled) {
		this.eventDisabled = eventDisabled;
	}

	public String getEventEnabled() {
		return eventEnabled.replaceAll("&", "§");
	}

	public void setEventEnabled(String eventEnabled) {
		this.eventEnabled = eventEnabled;
	}

	public String getEventIsDisabled() {
		return eventIsDisabled.replaceAll("&", "§");
	}

	public void setEventIsDisabled(String eventIsDisabled) {
		this.eventIsDisabled = eventIsDisabled;
	}

	public String getDelete() {
		return delete.replaceAll("&", "§");
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getDisable() {
		return disable.replaceAll("&", "§");
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getEnable() {
		return enable.replaceAll("&", "§");
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getStatsGuiName() {
		return statsGuiName.replaceAll("&", "§");
	}

	public void setStatsGuiName(String statsGuiName) {
		this.statsGuiName = statsGuiName;
	}

	public String getCreditsGuiName() {
		return creditsGuiName.replaceAll("&", "§");
	}

	public void setCreditsGuiName(String creditsGuiName) {
		this.creditsGuiName = creditsGuiName;
	}

	public String getAnnounceFirst() {
		return announceFirst.replaceAll("&", "§");
	}

	public void setAnnounceFirst(String announceFirst) {
		this.announceFirst = announceFirst;
	}

	public String getAnnounceNext() {
		return announceNext.replaceAll("&", "§");
	}

	public void setAnnounceNext(String announceNext) {
		this.announceNext = announceNext;
	}

	public String getAnnounceFirstTournament() {
		return announceFirstTournament.replaceAll("&", "§");
	}

	public void setAnnounceFirstTournament(String announceFirstTournament) {
		this.announceFirstTournament = announceFirstTournament;
	}

	public String getAnnounceNextTournament() {
		return announceNextTournament.replaceAll("&", "§");
	}

	public void setAnnounceNextTournament(String announceNextTournament) {
		this.announceNextTournament = announceNextTournament;
	}

	public String getScoreboardStep() {
		return scoreboardStep.replaceAll("&", "§");
	}

	public void setScoreboardStep(String scoreboardStep) {
		this.scoreboardStep = scoreboardStep;
	}

	public List<String> getMinigameDescriptionWDROP() {
		return minigameDescriptionWDROP;
	}

	public void setMinigameDescriptionWDROP(List<String> minigameDescriptionWDROP) {
		this.minigameDescriptionWDROP = minigameDescriptionWDROP;
	}

	public String getBowKill() {
		return bowKill.replaceAll("&", "§");
	}

	public void setBowKill(String bowKill) {
		this.bowKill = bowKill;
	}

	public String getGiveCreditsSpecific() {
		return giveCreditsSpecific.replaceAll("&", "§");
	}

	public String getGiveCreditsRandom() {
		return giveCreditsRandom.replaceAll("&", "§");
	}

	public String getCreditsBalOther() {
		return creditsBalOther.replaceAll("&", "§");
	}

	public String getCreditsBalOwn() {
		return creditsBalOwn.replaceAll("&", "§");
	}

	public String getPlayerOffline() {
		return playerOffline.replaceAll("&", "§");
	}

	public String getEventInvalid() {
		return eventInvalid.replaceAll("&", "§");
	}

	public void setGiveCreditsSpecific(String giveCreditsSpecific) {
		this.giveCreditsSpecific = giveCreditsSpecific;
	}

	public void setGiveCreditsRandom(String giveCreditsRandom) {
		this.giveCreditsRandom = giveCreditsRandom;
	}

	public void setCreditsBalOther(String creditsBalOther) {
		this.creditsBalOther = creditsBalOther;
	}

	public void setCreditsBalOwn(String creditsBalOwn) {
		this.creditsBalOwn = creditsBalOwn;
	}

	public void setPlayerOffline(String playerOffline) {
		this.playerOffline = playerOffline;
	}

	public void setEventInvalid(String eventInvalid) {
		this.eventInvalid = eventInvalid;
	}

	public String getCreditsAddedOther() {
		return creditsAddedOther.replaceAll("&", "§");
	}

	public String getCreditsAdded() {
		return creditsAdded.replaceAll("&", "§");
	}

	public String getCreditsBal() {
		return creditsBal.replaceAll("&", "§");
	}

	public String getCreditsCooldown() {
		return creditsCooldown.replaceAll("&", "§");
	}

	public String getCreditsReady() {
		return creditsReady.replaceAll("&", "§");
	}

	public void setCreditsAddedOther(String creditsAddedOther) {
		this.creditsAddedOther = creditsAddedOther;
	}

	public void setCreditsAdded(String creditsAdded) {
		this.creditsAdded = creditsAdded;
	}

	public void setCreditsBal(String creditsBal) {
		this.creditsBal = creditsBal;
	}

	public void setCreditsCooldown(String creditsCooldown) {
		this.creditsCooldown = creditsCooldown;
	}

	public void setCreditsReady(String creditsReady) {
		this.creditsReady = creditsReady;
	}

	public String getCreditsEventRunning() {
		return creditsEventRunning.replaceAll("&", "§");
	}

	public void setCreditsEventRunning(String creditsEventRunning) {
		this.creditsEventRunning = creditsEventRunning;
	}

	public String getGuiCMD() {
		return guiCMD.replaceAll("&", "§");
	}

	public void setGuiCMD(String guiCMD) {
		this.guiCMD = guiCMD;
	}

	public String getCreditsGuiPage() {
		return creditsGuiPage.replaceAll("&", "§");
	}

	public void setCreditsGuiPage(String creditsGuiPage) {
		this.creditsGuiPage = creditsGuiPage;
	}

	public String getSpec() {
		return spec.replaceAll("&", "§");
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getItemReturnCheckpoint() {
		return itemReturnCheckpoint.replaceAll("&", "§");
	}

	public void setItemReturnCheckpoint(String itemReturnCheckpoint) {
		this.itemReturnCheckpoint = itemReturnCheckpoint;
	}

	public String getScoreboardTeamPoints() {
		return scoreboardTeamPoints.replaceAll("&", "§");
	}

	public void setScoreboardTeamPoints(String scoreboardTeamPoints) {
		this.scoreboardTeamPoints = scoreboardTeamPoints;
	}

	public String getItemHidePlayer() {
		return itemHidePlayer.replaceAll("&", "§");
	}

	public void setItemHidePlayer(String itemHidePlayer) {
		this.itemHidePlayer = itemHidePlayer;
	}

	public String getItemShowPlayer() {
		return itemShowPlayer.replaceAll("&", "§");
	}

	public void setItemShowPlayer(String itemShowPlayer) {
		this.itemShowPlayer = itemShowPlayer;
	}

	public String getKitItemName() {
		return kitItemName.replaceAll("&", "§");
	}

	public void setKitItemName(String kitItemName) {
		this.kitItemName = kitItemName;
	}

	public String getKitDefaultName() {
		return kitDefaultName.replaceAll("&", "§");
	}

	public void setKitDefaultName(String kitDefaultName) {
		this.kitDefaultName = kitDefaultName;
	}

	public List<String> getKitDefaultLore() {
		List<String> defaultLore = new ArrayList<String>();
		for (String s : kitDefaultLore) {
			defaultLore.add(s.replaceAll("&", "§"));
		}
		return defaultLore;
	}

	public void setKitDefaultLore(List<String> kitDefaultLore) {
		this.kitDefaultLore = kitDefaultLore;
	}

	public String getKitGuiName() {
		return kitGuiName.replaceAll("&", "§");
	}

	public void setKitGuiName(String kitGuiName) {
		this.kitGuiName = kitGuiName;
	}

	public String getKitChosen() {
		return kitChosen.replaceAll("&", "§");
	}

	public String getScoreboardPointsTeam() {
		return scoreboardPointsTeam.replaceAll("&", "§");
	}

	public String getTagChat() {
		return tagChat.replaceAll("&", "§");
	}

	public void setTagChat(String tagChat) {
		this.tagChat = tagChat;
	}

	public void setScoreboardPointsTeam(String scoreboardPointsTeam) {
		this.scoreboardPointsTeam = scoreboardPointsTeam;
	}

	public void setKitChosen(String kitChosen) {
		this.kitChosen = kitChosen;
	}

	public List<String> getMinigameDescriptionQUAKE() {
		return minigameDescriptionQUAKE;
	}

	public void setMinigameDescriptionQUAKE(List<String> minigameDescriptionQUAKE) {
		this.minigameDescriptionQUAKE = minigameDescriptionQUAKE;
	}

	public List<String> getMinigameDescriptionPBALL() {
		return minigameDescriptionPBALL;
	}

	public void setMinigameDescriptionPBALL(List<String> minigameDescriptionPBALL) {
		this.minigameDescriptionPBALL = minigameDescriptionPBALL;
	}

	public List<String> getMinigameDescriptionKOTH() {
		return minigameDescriptionKOTH;
	}

	public void setMinigameDescriptionKOTH(List<String> minigameDescriptionKOTH) {
		this.minigameDescriptionKOTH = minigameDescriptionKOTH;
	}

	public List<String> getMinigameDescriptionFISHSLAP() {
		return minigameDescriptionFISHSLAP;
	}

	public void setMinigameDescriptionFISHSLAP(List<String> minigameDescriptionFISHSLAP) {
		this.minigameDescriptionFISHSLAP = minigameDescriptionFISHSLAP;
	}

	public List<String> getMinigameDescriptionHOE() {
		return minigameDescriptionHOE;
	}

	public void setMinigameDescriptionHOE(List<String> minigameDescriptionHOE) {
		this.minigameDescriptionHOE = minigameDescriptionHOE;
	}

	public List<String> getMinigameDescriptionSPLATOON() {
		return minigameDescriptionSPLATOON;
	}

	public void setMinigameDescriptionSPLATOON(List<String> minigameDescriptionSPLATOON) {
		this.minigameDescriptionSPLATOON = minigameDescriptionSPLATOON;
	}

	public List<String> getMinigameDescriptionBOMBARDMENT() {
		return minigameDescriptionBOMBARDMENT;
	}

	public void setMinigameDescriptionBOMBARDMENT(List<String> minigameDescriptionBOMBARDMENT) {
		this.minigameDescriptionBOMBARDMENT = minigameDescriptionBOMBARDMENT;
	}

	public String getTeamItemName() {
		return teamItemName.replaceAll("&", "§");
	}

	public void setTeamItemName(String teamItemName) {
		this.teamItemName = teamItemName;
	}

	public String getTeamGuiName() {
		return teamGuiName.replaceAll("&", "§");
	}

	public void setTeamGuiName(String teamGuiName) {
		this.teamGuiName = teamGuiName;
	}

}
