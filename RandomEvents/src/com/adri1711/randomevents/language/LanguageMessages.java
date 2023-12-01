
package com.adri1711.randomevents.language;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;
import com.google.common.io.Files;

import net.md_5.bungee.api.ChatColor;

public class LanguageMessages {
	private File file;
	private FileConfiguration fileConfig;
	private RandomEvents plugin;
	private Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

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
	private String announcementDisabled;
	private String announcementEnabled;
	private String toggleAnnouncement;
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
	private String resetCoinsAll;
	private String resetTriesAll;
	private String resetWinsAll;
	private String resetCoinsPlayer;
	private String resetTriesPlayer;
	private String resetWinsPlayer;

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
	private String nextTimer;
	
	private String nextEventIsRandom;

	private String nextEventName;
	private String startingMatch;
	private String cancelOfMatchCreation;

	private String matchJoin;
	private String matchLeave;
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
	private String tp;
	private String cancel;
	private String reload;
	private String checkpoint;
	private String forcebegin;
	private String kitsCmd;
	private String kitsEditCmd;

	private String refillTime;
	private String shrinkTime;
	private String refill;

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
	private String scoreboardSeekers;
	private String scoreboardHolder;
	private String scoreboardStep;
	private String scoreboardTeamAlive;
	private String scoreboardTeamDeath;
	private String scoreboardTeam;
	private String scoreboardPointsTeam;
	private String scoreboardTeamPoints;

	private String resetCoins;
	private String resetCoinsPlayerCMD;
	private String resetTries;
	private String resetTriesPlayerCMD;
	private String resetTriesGame;
	private String resetTriesGamePlayer;
	private String resetWins;
	private String resetWinsPlayerCMD;
	private String resetWinsGame;
	private String resetWinsGamePlayer;

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
	private String disableSchedule;
	private String enableSchedule;

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

	private String greenRedLightMove;
	private String greenRedLightStop;

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

	private String daysFormat;
	private String hoursFormat;
	private String minutesFormat;
	private String secondsFormat;

	private String scoreboardRound;
	private String nameBattleRoyale;
	private String nameTeamBattleRoyale;
	private String nameKnightsBattle;
	private String nameTopKiller;
	private String nameTeamTopKiller;
	private String nameKnockbackDuel;
	private String nameArrowRain;
	private String nameAnvilSpleef;
	private String nameGemCarrier;
	private String nameTNTTag;
	private String nameBoatRace;
	private String nameHorseRace;
	private String nameEscapeBeast;
	private String nameRace;
	private String nameTNTRun;
	private String nameSpleef;
	private String nameSplegg;
	private String nameOITC;
	private String nameSurvivalGames;
	private String nameTeamSurvivalGames;
	private String nameSkywars;
	private String nameTeamSkywars;
	private String nameWaterDrop;
	private String nameQuakeCraft;
	private String namePaintball;
	private String namePaintballTopKill;
	private String nameKingHill;
	private String nameFishSlap;
	private String nameHoehoehoe;
	private String nameBlockParty;
	private String nameHideAndSeek;
	private String nameSplatoon;
	private String nameBombardment;
	private String nameRedGreenLight;
	private String nameRealTeamBattleRoyale;
	private String nameRealTeamTopKiller;
	private String nameRealTeamSkywars;
	private String nameRealTeamSurvivalGames;
	private String nameGlassWalk;

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
	private List<String> minigameDescriptionREDGREEN;
	private List<String> minigameDescriptionGLASSWALK;

	private List<String> minigameDescriptionTSGTEAMS;
	private List<String> minigameDescriptionTSWTEAMS;
	private List<String> minigameDescriptionTKLLTEAMS;
	private List<String> minigameDescriptionBRTEAMS;
	private List<String> minigameDescriptionPBALLTK;
	private List<String> minigameDescriptionBLOCKPARTY;
	private List<String> minigameDescriptionHIDEANDSEEK;

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
					try {
						if (fileConfig.getStringList(m.getYmlField()) == null
								|| fileConfig.getStringList(m.getYmlField()).isEmpty()) {
							String[] cadena = fileConfig.getString(m.getYmlField()).split(";");
							List<String> listaString = new ArrayList<String>();
							for (String s : cadena) {
								listaString.add(s);
							}
							method.invoke(this, listaString);
						} else {
							method.invoke(this, fileConfig.getStringList(m.getYmlField()));

						}
					} catch (Throwable e) {
						method.invoke(this, fileConfig.getStringList(m.getYmlField()));
					}
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
		String s = invalidInput;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setInvalidInput(String invalidInput) {
		this.invalidInput = invalidInput;
	}

	public String getShrinkTime() {
		String s = shrinkTime;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setShrinkTime(String shrinkTime) {
		this.shrinkTime = shrinkTime;
	}

	public String getRefill() {
		String s = refill;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setRefill(String refill) {
		this.refill = refill;
	}

	public String getRefillTime() {
		String s = refillTime;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;

	}

	public void setRefillTime(String refillTime) {
		this.refillTime = refillTime;
	}

	public String getError() {
		String s = error;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getInvalidCmd() {
		String s = invalidCmd;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setInvalidCmd(String invalidCmd) {
		this.invalidCmd = invalidCmd;
	}

	public String getNoPermission() {
		String s = noPermission;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNoPermission(String noPermission) {
		this.noPermission = noPermission;
	}

	public String getEndOfKitCreation() {
		String s = endOfKitCreation;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEndOfKitCreation(String endOfKitCreation) {
		this.endOfKitCreation = endOfKitCreation;
	}

	public String getEndOfArenaCreation() {
		String s = endOfArenaCreation;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEndOfArenaCreation(String endOfArenaCreation) {
		this.endOfArenaCreation = endOfArenaCreation;
	}

	public String getCancelOfArenaCreation() {
		String s = cancelOfArenaCreation;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCancelOfArenaCreation(String cancelOfArenaCreation) {
		this.cancelOfArenaCreation = cancelOfArenaCreation;
	}

	public String getPluginReload() {
		String s = pluginReload;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPluginReload(String pluginReload) {
		this.pluginReload = pluginReload;
	}

	public String getMatchFull() {
		String s = matchFull;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setMatchFull(String matchFull) {
		this.matchFull = matchFull;
	}

	public String getAlreadyPlayingMatch() {
		String s = alreadyPlayingMatch;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAlreadyPlayingMatch(String alreadyPlayingMatch) {
		this.alreadyPlayingMatch = alreadyPlayingMatch;
	}

	public String getMatchBegun() {
		String s = matchBegun;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setMatchBegun(String matchBegun) {
		this.matchBegun = matchBegun;
	}

	public String getMatches() {
		String s = matches;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);

		return s;
	}

	public void setMatches(String matches) {
		this.matches = matches;
	}

	public String getAlreadyMatch() {
		String s = alreadyMatch;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAlreadyMatch(String alreadyMatch) {
		this.alreadyMatch = alreadyMatch;
	}

	public String getWaitingForPlayers() {
		String s = waitingForPlayers;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setWaitingForPlayers(String waitingForPlayers) {
		this.waitingForPlayers = waitingForPlayers;
	}

	public String getSpawnSet() {
		String s = spawnSet;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSpawnSet(String spawnSet) {
		this.spawnSet = spawnSet;
	}

	public String getNotInMatch() {
		String s = notInMatch;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNotInMatch(String notInMatch) {
		this.notInMatch = notInMatch;
	}

	public String getWinRandomEvents() {
		String s = winRandomEvents;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setWinRandomEvents(String winRandomEvents) {
		this.winRandomEvents = winRandomEvents;
	}

	public String getEventAnnounce() {
		String s = eventAnnounce;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventAnnounce(String eventAnnounce) {
		this.eventAnnounce = eventAnnounce;
	}

	public String getInvalidPassword() {
		String s = invalidPassword;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setInvalidPassword(String invalidPassword) {
		this.invalidPassword = invalidPassword;
	}

	public String getFirstAnnounce() {
		String s = firstAnnounce;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setFirstAnnounce(String firstAnnounce) {
		this.firstAnnounce = firstAnnounce;
	}

	public String getNextAnnounce() {
		String s = nextAnnounce;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNextAnnounce(String nextAnnounce) {
		this.nextAnnounce = nextAnnounce;
	}

	public String getClickHere() {
		String s = clickHere;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setClickHere(String clickHere) {
		this.clickHere = clickHere;
	}

	public String getLastPart() {
		String s = lastPart;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setLastPart(String lastPart) {
		this.lastPart = lastPart;
	}

	public String getWinners() {
		String s = winners;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setWinners(String winners) {
		this.winners = winners;
	}

	public String getWinnersPoints() {
		String s = winnersPoints;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setWinnersPoints(String winnersPoints) {
		this.winnersPoints = winnersPoints;
	}

	public String getAnnouncementDisabled() {
		String s = announcementDisabled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAnnouncementDisabled(String announcementDisabled) {
		this.announcementDisabled = announcementDisabled;
	}

	public String getAnnouncementEnabled() {
		String s = announcementEnabled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAnnouncementEnabled(String announcementEnabled) {
		this.announcementEnabled = announcementEnabled;
	}

	public String getToggleAnnouncement() {
		String s = toggleAnnouncement;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setToggleAnnouncement(String toggleAnnouncement) {
		this.toggleAnnouncement = toggleAnnouncement;
	}

	public String getDisposeLeatherItems() {
		String s = disposeLeatherItems;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setDisposeLeatherItems(String disposeLeatherItems) {
		this.disposeLeatherItems = disposeLeatherItems;
	}

	public String getShowAlone() {
		String s = showAlone;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setShowAlone(String showAlone) {
		this.showAlone = showAlone;
	}

	public String getShowTeam() {
		String s = showTeam;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setShowTeam(String showTeam) {
		this.showTeam = showTeam;
	}

	public String getTimeRemaining() {
		String s = timeRemaining;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTimeRemaining(String timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public String getNowPoints() {
		String s = nowPoints;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNowPoints(String nowPoints) {
		this.nowPoints = nowPoints;
	}

	public String getNowGems() {
		String s = nowGems;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNowGems(String nowGems) {
		this.nowGems = nowGems;
	}

	public String getNowProtected() {
		String s = nowProtected;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNowProtected(String nowProtected) {
		this.nowProtected = nowProtected;
	}

	public String getMatchBeginSoon() {
		String s = matchBeginSoon;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setMatchBeginSoon(String matchBeginSoon) {
		this.matchBeginSoon = matchBeginSoon;
	}

	public String getLostGems() {
		String s = lostGems;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setLostGems(String lostGems) {
		this.lostGems = lostGems;
	}

	public String getPlayerWinning() {
		String s = playerWinning;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPlayerWinning(String playerWinning) {
		this.playerWinning = playerWinning;
	}

	public String getPlayerWinningSeconds() {
		String s = playerWinningSeconds;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPlayerWinningSeconds(String playerWinningSeconds) {
		this.playerWinningSeconds = playerWinningSeconds;
	}

	public String getBombSeconds() {
		String s = bombSeconds;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBombSeconds(String bombSeconds) {
		this.bombSeconds = bombSeconds;
	}

	public String getPlayerHasBomb() {
		String s = playerHasBomb;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPlayerHasBomb(String playerHasBomb) {
		this.playerHasBomb = playerHasBomb;
	}

	public String getBombExplode() {
		String s = bombExplode;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBombExplode(String bombExplode) {
		this.bombExplode = bombExplode;
	}

	public String getLeaveCommand() {
		String s = leaveCommand;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setLeaveCommand(String leaveCommand) {
		this.leaveCommand = leaveCommand;
	}

	public String getErrorSavingInventory() {
		String s = errorSavingInventory;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setErrorSavingInventory(String errorSavingInventory) {
		this.errorSavingInventory = errorSavingInventory;
	}

	public void setFileConfig(FileConfiguration fileConfig) {
		this.fileConfig = fileConfig;
	}

	public String getLastPartTournament() {
		String s = lastPartTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setLastPartTournament(String lastPartTournament) {
		this.lastPartTournament = lastPartTournament;
	}

	public String getFirstAnnounceTournament() {
		String s = firstAnnounceTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setFirstAnnounceTournament(String firstAnnounceTournament) {
		this.firstAnnounceTournament = firstAnnounceTournament;
	}

	public String getNextAnnounceTournament() {
		String s = nextAnnounceTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNextAnnounceTournament(String nextAnnounceTournament) {
		this.nextAnnounceTournament = nextAnnounceTournament;
	}

	public String getRaceTournament() {
		String s = raceTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setRaceTournament(String raceTournament) {
		this.raceTournament = raceTournament;
	}

	public String getWinnersTournament() {
		String s = winnersTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setWinnersTournament(String winnersTournament) {
		this.winnersTournament = winnersTournament;
	}

	public String getTournamentAlive() {
		String s = tournamentAlive;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTournamentAlive(String tournamentAlive) {
		this.tournamentAlive = tournamentAlive;
	}

	public String getYouBeast() {
		String s = youBeast;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setYouBeast(String youBeast) {
		this.youBeast = youBeast;
	}

	public String getEndOfScheduleCreation() {
		String s = endOfScheduleCreation;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEndOfScheduleCreation(String endOfScheduleCreation) {
		this.endOfScheduleCreation = endOfScheduleCreation;
	}

	public String getResetCoins() {
		String s = resetCoins;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetCoinsPlayerCMD() {
		String s = resetCoinsPlayerCMD;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetTries() {
		String s = resetTries;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetTriesPlayerCMD() {
		String s = resetTriesPlayerCMD;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetTriesGame() {
		String s = resetTriesGame;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetTriesGamePlayer() {
		String s = resetTriesGamePlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetWins() {
		String s = resetWins;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetWinsPlayerCMD() {
		String s = resetWinsPlayerCMD;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetWinsGame() {
		String s = resetWinsGame;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetWinsGamePlayer() {
		String s = resetWinsGamePlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getTagPlugin() {
		String s = tagPlugin;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTagPlugin(String tagPlugin) {
		this.tagPlugin = tagPlugin;
	}

	public String getEventCancelled() {
		String s = eventCancelled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventCancelled(String eventCancelled) {
		this.eventCancelled = eventCancelled;
	}

	public String getTournamentCancelled() {
		String s = tournamentCancelled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTournamentCancelled(String tournamentCancelled) {
		this.tournamentCancelled = tournamentCancelled;
	}

	public String getCmdNotAllowed() {
		String s = cmdNotAllowed;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCmdNotAllowed(String cmdNotAllowed) {
		this.cmdNotAllowed = cmdNotAllowed;
	}

	public String getStatsWins() {
		String s = statsWins;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStatsWins(String statsWins) {
		this.statsWins = statsWins;
	}

	public String getStatsTries() {
		String s = statsTries;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStatsTries(String statsTries) {
		this.statsTries = statsTries;
	}

	public String getStatsDisabled() {
		String s = statsDisabled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getResetCoinsAll() {
		String s = resetCoinsAll;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetTriesAll() {
		String s = resetTriesAll;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetWinsAll() {
		String s = resetWinsAll;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetCoinsPlayer() {
		String s = resetCoinsPlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetTriesPlayer() {
		String s = resetTriesPlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getResetWinsPlayer() {
		String s = resetWinsPlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStatsDisabled(String statsDisabled) {
		this.statsDisabled = statsDisabled;
	}

	public String getDisableSchedule() {
		String s = disableSchedule;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setDisableSchedule(String disableSchedule) {
		this.disableSchedule = disableSchedule;
	}

	public String getEnableSchedule() {
		String s = enableSchedule;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEnableSchedule(String enableSchedule) {
		this.enableSchedule = enableSchedule;
	}

	public String getStatsWinsRatio() {
		String s = statsWinsRatio;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
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
		String s = eventStopped;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventStopped(String eventStopped) {
		this.eventStopped = eventStopped;
	}

	public String getClearInventory() {
		String s = clearInventory;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setClearInventory(String clearInventory) {
		this.clearInventory = clearInventory;
	}

	public String getSecondsRemaining3() {
		String s = secondsRemaining3;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSecondsRemaining3(String secondsRemaining3) {
		this.secondsRemaining3 = secondsRemaining3;
	}

	public String getSecondsRemaining2() {
		String s = secondsRemaining2;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSecondsRemaining2(String secondsRemaining2) {
		this.secondsRemaining2 = secondsRemaining2;
	}

	public String getSecondsRemaining1() {
		String s = secondsRemaining1;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSecondsRemaining1(String secondsRemaining1) {
		this.secondsRemaining1 = secondsRemaining1;
	}

	public String getYouAreBanned() {
		String s = youAreBanned;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setYouAreBanned(String youAreBanned) {
		this.youAreBanned = youAreBanned;
	}

	public String getBanPlayer() {
		String s = banPlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBanPlayer(String banPlayer) {
		this.banPlayer = banPlayer;
	}

	public String getUnbanPlayer() {
		String s = unbanPlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setUnbanPlayer(String unbanPlayer) {
		this.unbanPlayer = unbanPlayer;
	}

	public String getPlayerNotBanned() {
		String s = playerNotBanned;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPlayerNotBanned(String playerNotBanned) {
		this.playerNotBanned = playerNotBanned;
	}

	public String getIdleDamage() {
		String s = idleDamage;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setIdleDamage(String idleDamage) {
		this.idleDamage = idleDamage;
	}

	public String getNoScheduledEvents() {
		String s = noScheduledEvents;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNoScheduledEvents(String noScheduledEvents) {
		this.noScheduledEvents = noScheduledEvents;
	}

	public String getNextEvent() {
		String s = nextEvent;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNextEvent(String nextEvent) {
		this.nextEvent = nextEvent;
	}

	public String getNextEventIsRandom() {
		String s = nextEventIsRandom;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNextEventIsRandom(String nextEventIsRandom) {
		this.nextEventIsRandom = nextEventIsRandom;
	}

	public String getNextEventName() {
		String s = nextEventName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNextEventName(String nextEventName) {
		this.nextEventName = nextEventName;
	}

	public String getStartingMatch() {
		String s = startingMatch;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStartingMatch(String startingMatch) {
		this.startingMatch = startingMatch;
	}

	public String getCancelOfMatchCreation() {
		String s = cancelOfMatchCreation;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCancelOfMatchCreation(String cancelOfMatchCreation) {
		this.cancelOfMatchCreation = cancelOfMatchCreation;
	}

	public String getLacksInfoCreation() {
		String s = lacksInfoCreation;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setLacksInfoCreation(String lacksInfoCreation) {
		this.lacksInfoCreation = lacksInfoCreation;
	}

	public String getShrink() {
		String s = shrink;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
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
		String s = warmupEnd;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setWarmupEnd(String warmupEnd) {
		this.warmupEnd = warmupEnd;
	}

	public String getPlayersMove() {
		String s = playersMove;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPlayersMove(String playersMove) {
		this.playersMove = playersMove;
	}

	public String getHelpMenu() {
		String s = helpMenu;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setHelpMenu(String helpMenu) {
		this.helpMenu = helpMenu;
	}

	public String getShowMenu() {
		String s = showMenu;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setShowMenu(String showMenu) {
		this.showMenu = showMenu;
	}

	public String getJoin() {
		String s = join;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public String getTjoin() {
		String s = tjoin;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTjoin(String tjoin) {
		this.tjoin = tjoin;
	}

	public String getLeave() {
		String s = leave;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getTleave() {
		String s = tleave;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTleave(String tleave) {
		this.tleave = tleave;
	}

	public String getStats() {
		String s = stats;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getStatsOther() {
		String s = statsOther;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStatsOther(String statsOther) {
		this.statsOther = statsOther;
	}

	public String getForcestop() {
		String s = forcestop;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setForcestop(String forcestop) {
		this.forcestop = forcestop;
	}

	public String getSpawnset() {
		String s = spawnset;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSpawnset(String spawnset) {
		this.spawnset = spawnset;
	}

	public String getTspawnset() {
		String s = tspawnset;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTspawnset(String tspawnset) {
		this.tspawnset = tspawnset;
	}

	public String getMenuMatches() {
		String s = menuMatches;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setMenuMatches(String menuMatches) {
		this.menuMatches = menuMatches;
	}

	public String getNext() {
		String s = next;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getDate() {
		String s = date;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSchedule() {
		String s = schedule;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getScheduleSpecific() {
		String s = scheduleSpecific;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScheduleSpecific(String scheduleSpecific) {
		this.scheduleSpecific = scheduleSpecific;
	}

	public String getBegin() {
		String s = begin;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getTbegin() {
		String s = tbegin;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTbegin(String tbegin) {
		this.tbegin = tbegin;
	}

	public String getBeginSpecific() {
		String s = beginSpecific;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBeginSpecific(String beginSpecific) {
		this.beginSpecific = beginSpecific;
	}

	public String getBan() {
		String s = ban;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public String getUnban() {
		String s = unban;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setUnban(String unban) {
		this.unban = unban;
	}

	public String getCreate() {
		String s = create;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getEdit() {
		String s = edit;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getTp() {
		String s = tp;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public String getCancel() {
		String s = cancel;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getReload() {
		String s = reload;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setReload(String reload) {
		this.reload = reload;
	}

	public String getCheckpoint() {
		String s = checkpoint;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}

	public String getScoreboardTitle() {
		String s = scoreboardTitle;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTitle(String scoreboardTitle) {
		this.scoreboardTitle = scoreboardTitle;
	}

	public String getScoreboardPoints() {
		String s = scoreboardPoints;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardPoints(String scoreboardPoints) {
		this.scoreboardPoints = scoreboardPoints;
	}

	public String getScoreboardAlive() {
		String s = scoreboardAlive;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardAlive(String scoreboardAlive) {
		this.scoreboardAlive = scoreboardAlive;
	}

	public String getScoreboardDeath() {
		String s = scoreboardDeath;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardDeath(String scoreboardDeath) {
		this.scoreboardDeath = scoreboardDeath;
	}

	public String getScoreboardTeammate() {
		String s = scoreboardTeammate;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTeammate(String scoreboardTeammate) {
		this.scoreboardTeammate = scoreboardTeammate;
	}

	public String getScoreboardTeamAlive() {
		String s = scoreboardTeamAlive;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTeamAlive(String scoreboardTeamAlive) {
		this.scoreboardTeamAlive = scoreboardTeamAlive;
	}

	public String getScoreboardTeamDeath() {
		String s = scoreboardTeamDeath;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTeamDeath(String scoreboardTeamDeath) {
		this.scoreboardTeamDeath = scoreboardTeamDeath;
	}

	public String getScoreboardTeam() {
		String s = scoreboardTeam;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTeam(String scoreboardTeam) {
		this.scoreboardTeam = scoreboardTeam;
	}

	public String getScoreboardTime() {
		String s = scoreboardTime;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTime(String scoreboardTime) {
		this.scoreboardTime = scoreboardTime;
	}

	public String getScoreboardBeast() {
		String s = scoreboardBeast;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardBeast(String scoreboardBeast) {
		this.scoreboardBeast = scoreboardBeast;
	}

	public String getScoreboardSeekers() {
		String s = scoreboardSeekers;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardSeekers(String scoreboardSeekers) {
		this.scoreboardSeekers = scoreboardSeekers;
	}

	public String getScoreboardHolder() {
		String s = scoreboardHolder;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardHolder(String scoreboardHolder) {
		this.scoreboardHolder = scoreboardHolder;
	}

	public String getPvpDeath() {
		String s = pvpDeath;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPvpDeath(String pvpDeath) {
		this.pvpDeath = pvpDeath;
	}

	public String getPvpKill() {
		String s = pvpKill;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setPvpKill(String pvpKill) {
		this.pvpKill = pvpKill;
	}

	public String getForcebegin() {
		String s = forcebegin;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setForcebegin(String forcebegin) {
		this.forcebegin = forcebegin;
	}

	public String getKitsCmd() {
		String s = kitsCmd;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setKitsCmd(String kitsCmd) {
		this.kitsCmd = kitsCmd;
	}

	public String getKitsEditCmd() {
		String s = kitsEditCmd;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setKitsEditCmd(String kitsEditCmd) {
		this.kitsEditCmd = kitsEditCmd;
	}

	public String getEventDeleted() {
		String s = eventDeleted;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventDeleted(String eventDeleted) {
		this.eventDeleted = eventDeleted;
	}

	public String getEventDisabled() {
		String s = eventDisabled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventDisabled(String eventDisabled) {
		this.eventDisabled = eventDisabled;
	}

	public String getEventEnabled() {
		String s = eventEnabled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventEnabled(String eventEnabled) {
		this.eventEnabled = eventEnabled;
	}

	public String getEventIsDisabled() {
		String s = eventIsDisabled;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEventIsDisabled(String eventIsDisabled) {
		this.eventIsDisabled = eventIsDisabled;
	}

	public String getDelete() {
		String s = delete;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getDisable() {
		String s = disable;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getEnable() {
		String s = enable;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getStatsGuiName() {
		String s = statsGuiName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setStatsGuiName(String statsGuiName) {
		this.statsGuiName = statsGuiName;
	}

	public void setMatchJoin(String matchJoin) {
		this.matchJoin = matchJoin;
	}

	public void setMatchLeave(String matchLeave) {
		this.matchLeave = matchLeave;
	}

	public void setResetCoins(String resetCoins) {
		this.resetCoins = resetCoins;
	}

	public void setResetCoinsPlayerCMD(String resetCoinsPlayerCMD) {
		this.resetCoinsPlayerCMD = resetCoinsPlayerCMD;
	}

	public void setResetTries(String resetTries) {
		this.resetTries = resetTries;
	}

	public void setResetTriesPlayerCMD(String resetTriesPlayerCMD) {
		this.resetTriesPlayerCMD = resetTriesPlayerCMD;
	}

	public void setResetTriesGame(String resetTriesGame) {
		this.resetTriesGame = resetTriesGame;
	}

	public void setResetTriesGamePlayer(String resetTriesGamePlayer) {
		this.resetTriesGamePlayer = resetTriesGamePlayer;
	}

	public void setResetWins(String resetWins) {
		this.resetWins = resetWins;
	}

	public void setResetWinsPlayerCMD(String resetWinsPlayerCMD) {
		this.resetWinsPlayerCMD = resetWinsPlayerCMD;
	}

	public void setResetWinsGame(String resetWinsGame) {
		this.resetWinsGame = resetWinsGame;
	}

	public void setResetWinsGamePlayer(String resetWinsGamePlayer) {
		this.resetWinsGamePlayer = resetWinsGamePlayer;
	}

	public String getMatchJoin() {
		String s = matchJoin;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getMatchLeave() {
		String s = matchLeave;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsGuiName() {
		String s = creditsGuiName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCreditsGuiName(String creditsGuiName) {
		this.creditsGuiName = creditsGuiName;
	}

	public String getAnnounceFirst() {
		String s = announceFirst;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAnnounceFirst(String announceFirst) {
		this.announceFirst = announceFirst;
	}

	public String getAnnounceNext() {
		String s = announceNext;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAnnounceNext(String announceNext) {
		this.announceNext = announceNext;
	}

	public String getAnnounceFirstTournament() {
		String s = announceFirstTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAnnounceFirstTournament(String announceFirstTournament) {
		this.announceFirstTournament = announceFirstTournament;
	}

	public String getAnnounceNextTournament() {
		String s = announceNextTournament;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setAnnounceNextTournament(String announceNextTournament) {
		this.announceNextTournament = announceNextTournament;
	}

	public String getScoreboardStep() {
		String s = scoreboardStep;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
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
		String s = bowKill;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setBowKill(String bowKill) {
		this.bowKill = bowKill;
	}

	public String getGiveCreditsSpecific() {
		String s = giveCreditsSpecific;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getGiveCreditsRandom() {
		String s = giveCreditsRandom;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsBalOther() {
		String s = creditsBalOther;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsBalOwn() {
		String s = creditsBalOwn;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getPlayerOffline() {
		String s = playerOffline;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getEventInvalid() {
		String s = eventInvalid;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
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
		String s = creditsAddedOther;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsAdded() {
		String s = creditsAdded;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsBal() {
		String s = creditsBal;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsCooldown() {
		String s = creditsCooldown;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getCreditsReady() {
		String s = creditsReady;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
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
		String s = creditsEventRunning;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCreditsEventRunning(String creditsEventRunning) {
		this.creditsEventRunning = creditsEventRunning;
	}

	public String getGuiCMD() {
		String s = guiCMD;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setGuiCMD(String guiCMD) {
		this.guiCMD = guiCMD;
	}

	public String getCreditsGuiPage() {
		String s = creditsGuiPage;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setCreditsGuiPage(String creditsGuiPage) {
		this.creditsGuiPage = creditsGuiPage;
	}

	public String getSpec() {
		String s = spec;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getItemReturnCheckpoint() {
		String s = itemReturnCheckpoint;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setItemReturnCheckpoint(String itemReturnCheckpoint) {
		this.itemReturnCheckpoint = itemReturnCheckpoint;
	}

	public String getScoreboardTeamPoints() {
		String s = scoreboardTeamPoints;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardTeamPoints(String scoreboardTeamPoints) {
		this.scoreboardTeamPoints = scoreboardTeamPoints;
	}

	public String getItemHidePlayer() {
		String s = itemHidePlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setItemHidePlayer(String itemHidePlayer) {
		this.itemHidePlayer = itemHidePlayer;
	}

	public String getItemShowPlayer() {
		String s = itemShowPlayer;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setItemShowPlayer(String itemShowPlayer) {
		this.itemShowPlayer = itemShowPlayer;
	}

	public String getKitItemName() {
		String s = kitItemName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setKitItemName(String kitItemName) {
		this.kitItemName = kitItemName;
	}

	public String getKitDefaultName() {
		String s = kitDefaultName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setKitDefaultName(String kitDefaultName) {
		this.kitDefaultName = kitDefaultName;
	}

	public List<String> getKitDefaultLore() {
		List<String> defaultLore = new ArrayList<String>();
		for (String s : kitDefaultLore) {
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
			defaultLore.add(s);
		}
		return defaultLore;
	}

	public void setKitDefaultLore(List<String> kitDefaultLore) {
		this.kitDefaultLore = kitDefaultLore;
	}

	public String getKitGuiName() {
		String s = kitGuiName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setKitGuiName(String kitGuiName) {
		this.kitGuiName = kitGuiName;
	}

	public String getKitChosen() {
		String s = kitChosen;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getScoreboardPointsTeam() {
		String s = scoreboardPointsTeam;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getTagChat() {
		String s = tagChat;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
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
		String s = teamItemName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTeamItemName(String teamItemName) {
		this.teamItemName = teamItemName;
	}

	public String getTeamGuiName() {
		String s = teamGuiName;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setTeamGuiName(String teamGuiName) {
		this.teamGuiName = teamGuiName;
	}

	public String getNameBattleRoyale() {
		String s = nameBattleRoyale;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTeamBattleRoyale() {
		String s = nameTeamBattleRoyale;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameKnightsBattle() {
		String s = nameKnightsBattle;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTopKiller() {
		String s = nameTopKiller;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTeamTopKiller() {
		String s = nameTeamTopKiller;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameKnockbackDuel() {
		String s = nameKnockbackDuel;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameArrowRain() {
		String s = nameArrowRain;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameAnvilSpleef() {
		String s = nameAnvilSpleef;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameGemCarrier() {
		String s = nameGemCarrier;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTNTTag() {
		String s = nameTNTTag;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameBoatRace() {
		String s = nameBoatRace;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameHorseRace() {
		String s = nameHorseRace;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameEscapeBeast() {
		String s = nameEscapeBeast;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameRace() {
		String s = nameRace;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTNTRun() {
		String s = nameTNTRun;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameSpleef() {
		String s = nameSpleef;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameSplegg() {
		String s = nameSplegg;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameOITC() {
		String s = nameOITC;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameSurvivalGames() {
		String s = nameSurvivalGames;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTeamSurvivalGames() {
		String s = nameTeamSurvivalGames;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameSkywars() {
		String s = nameSkywars;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameTeamSkywars() {
		String s = nameTeamSkywars;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameWaterDrop() {
		String s = nameWaterDrop;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameQuakeCraft() {
		String s = nameQuakeCraft;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNamePaintball() {
		String s = namePaintball;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNamePaintballTopKill() {
		String s = namePaintballTopKill;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNamePaintballTopKill(String namePaintballTopKill) {
		this.namePaintballTopKill = namePaintballTopKill;
	}

	public String getNameKingHill() {
		String s = nameKingHill;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameFishSlap() {
		String s = nameFishSlap;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameHoehoehoe() {
		String s = nameHoehoehoe;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameSplatoon() {
		String s = nameSplatoon;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameBombardment() {
		String s = nameBombardment;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameRedGreenLight() {
		String s = nameRedGreenLight;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNameRedGreenLight(String nameRedGreenLight) {
		this.nameRedGreenLight = nameRedGreenLight;
	}

	public String getNameRealTeamBattleRoyale() {
		String s = nameRealTeamBattleRoyale;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameRealTeamTopKiller() {
		String s = nameRealTeamTopKiller;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameRealTeamSkywars() {
		String s = nameRealTeamSkywars;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameRealTeamSurvivalGames() {
		String s = nameRealTeamSurvivalGames;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameGlassWalk() {
		String s = nameGlassWalk;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getHoursFormat() {
		String s = hoursFormat;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getDaysFormat() {
		String s = daysFormat;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setDaysFormat(String daysFormat) {
		this.daysFormat = daysFormat;
	}

	public String getMinutesFormat() {
		String s = minutesFormat;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getScoreboardRound() {
		String s = scoreboardRound;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setScoreboardRound(String scoreboardRound) {
		this.scoreboardRound = scoreboardRound;
	}

	public String getSecondsFormat() {
		String s = secondsFormat;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public List<String> getMinigameDescriptionTSGTEAMS() {
		return minigameDescriptionTSGTEAMS;
	}

	public List<String> getMinigameDescriptionGLASSWALK() {
		return minigameDescriptionGLASSWALK;
	}

	public void setMinigameDescriptionGLASSWALK(List<String> minigameDescriptionGLASSWALK) {
		this.minigameDescriptionGLASSWALK = minigameDescriptionGLASSWALK;
	}

	public void setNameGlassWalk(String nameGlassWalk) {
		this.nameGlassWalk = nameGlassWalk;
	}

	public List<String> getMinigameDescriptionTSWTEAMS() {
		return minigameDescriptionTSWTEAMS;
	}

	public List<String> getMinigameDescriptionTKLLTEAMS() {
		return minigameDescriptionTKLLTEAMS;
	}

	public List<String> getMinigameDescriptionBRTEAMS() {
		return minigameDescriptionBRTEAMS;
	}

	public void setNameBattleRoyale(String nameBattleRoyale) {
		this.nameBattleRoyale = nameBattleRoyale;
	}

	public void setNameTeamBattleRoyale(String nameTeamBattleRoyale) {
		this.nameTeamBattleRoyale = nameTeamBattleRoyale;
	}

	public void setNameKnightsBattle(String nameKnightsBattle) {
		this.nameKnightsBattle = nameKnightsBattle;
	}

	public void setNameTopKiller(String nameTopKiller) {
		this.nameTopKiller = nameTopKiller;
	}

	public void setNameTeamTopKiller(String nameTeamTopKiller) {
		this.nameTeamTopKiller = nameTeamTopKiller;
	}

	public void setNameKnockbackDuel(String nameKnockbackDuel) {
		this.nameKnockbackDuel = nameKnockbackDuel;
	}

	public void setNameArrowRain(String nameArrowRain) {
		this.nameArrowRain = nameArrowRain;
	}

	public void setNameAnvilSpleef(String nameAnvilSpleef) {
		this.nameAnvilSpleef = nameAnvilSpleef;
	}

	public void setNameGemCarrier(String nameGemCarrier) {
		this.nameGemCarrier = nameGemCarrier;
	}

	public void setNameTNTTag(String nameTNTTag) {
		this.nameTNTTag = nameTNTTag;
	}

	public void setNameBoatRace(String nameBoatRace) {
		this.nameBoatRace = nameBoatRace;
	}

	public void setNameHorseRace(String nameHorseRace) {
		this.nameHorseRace = nameHorseRace;
	}

	public void setNameEscapeBeast(String nameEscapeBeast) {
		this.nameEscapeBeast = nameEscapeBeast;
	}

	public void setNameRace(String nameRace) {
		this.nameRace = nameRace;
	}

	public void setNameTNTRun(String nameTNTRun) {
		this.nameTNTRun = nameTNTRun;
	}

	public void setNameSpleef(String nameSpleef) {
		this.nameSpleef = nameSpleef;
	}

	public void setNameSplegg(String nameSplegg) {
		this.nameSplegg = nameSplegg;
	}

	public void setNameOITC(String nameOITC) {
		this.nameOITC = nameOITC;
	}

	public void setNameSurvivalGames(String nameSurvivalGames) {
		this.nameSurvivalGames = nameSurvivalGames;
	}

	public void setNameTeamSurvivalGames(String nameTeamSurvivalGames) {
		this.nameTeamSurvivalGames = nameTeamSurvivalGames;
	}

	public void setNameSkywars(String nameSkywars) {
		this.nameSkywars = nameSkywars;
	}

	public void setNameTeamSkywars(String nameTeamSkywars) {
		this.nameTeamSkywars = nameTeamSkywars;
	}

	public void setNameWaterDrop(String nameWaterDrop) {
		this.nameWaterDrop = nameWaterDrop;
	}

	public void setNameQuakeCraft(String nameQuakeCraft) {
		this.nameQuakeCraft = nameQuakeCraft;
	}

	public void setNamePaintball(String namePaintball) {
		this.namePaintball = namePaintball;
	}

	public void setNameKingHill(String nameKingHill) {
		this.nameKingHill = nameKingHill;
	}

	public void setNameFishSlap(String nameFishSlap) {
		this.nameFishSlap = nameFishSlap;
	}

	public void setNameHoehoehoe(String nameHoehoehoe) {
		this.nameHoehoehoe = nameHoehoehoe;
	}

	public void setNameSplatoon(String nameSplatoon) {
		this.nameSplatoon = nameSplatoon;
	}

	public void setNameBombardment(String nameBombardment) {
		this.nameBombardment = nameBombardment;
	}

	public void setNameRealTeamBattleRoyale(String nameRealTeamBattleRoyale) {
		this.nameRealTeamBattleRoyale = nameRealTeamBattleRoyale;
	}

	public void setNameRealTeamTopKiller(String nameRealTeamTopKiller) {
		this.nameRealTeamTopKiller = nameRealTeamTopKiller;
	}

	public void setNameRealTeamSkywars(String nameRealTeamSkywars) {
		this.nameRealTeamSkywars = nameRealTeamSkywars;
	}

	public void setNameRealTeamSurvivalGames(String nameRealTeamSurvivalGames) {
		this.nameRealTeamSurvivalGames = nameRealTeamSurvivalGames;
	}

	public void setMinigameDescriptionTSGTEAMS(List<String> minigameDescriptionTSGTEAMS) {
		this.minigameDescriptionTSGTEAMS = minigameDescriptionTSGTEAMS;
	}

	public void setMinigameDescriptionTSWTEAMS(List<String> minigameDescriptionTSWTEAMS) {
		this.minigameDescriptionTSWTEAMS = minigameDescriptionTSWTEAMS;
	}

	public void setMinigameDescriptionTKLLTEAMS(List<String> minigameDescriptionTKLLTEAMS) {
		this.minigameDescriptionTKLLTEAMS = minigameDescriptionTKLLTEAMS;
	}

	public void setMinigameDescriptionBRTEAMS(List<String> minigameDescriptionBRTEAMS) {
		this.minigameDescriptionBRTEAMS = minigameDescriptionBRTEAMS;
	}

	public void setHoursFormat(String hoursFormat) {
		this.hoursFormat = hoursFormat;
	}

	public void setMinutesFormat(String minutesFormat) {
		this.minutesFormat = minutesFormat;
	}

	public void setSecondsFormat(String secondsFormat) {
		this.secondsFormat = secondsFormat;
	}

	public List<String> getMinigameDescriptionPBALLTK() {
		return minigameDescriptionPBALLTK;
	}

	public void setMinigameDescriptionPBALLTK(List<String> minigameDescriptionPBALLTK) {
		this.minigameDescriptionPBALLTK = minigameDescriptionPBALLTK;
	}

	public List<String> getMinigameDescriptionBLOCKPARTY() {
		return minigameDescriptionBLOCKPARTY;
	}

	public void setMinigameDescriptionBLOCKPARTY(List<String> minigameDescriptionBLOCKPARTY) {
		this.minigameDescriptionBLOCKPARTY = minigameDescriptionBLOCKPARTY;
	}

	public List<String> getMinigameDescriptionHIDEANDSEEK() {
		return minigameDescriptionHIDEANDSEEK;
	}

	public void setMinigameDescriptionHIDEANDSEEK(List<String> minigameDescriptionHIDEANDSEEK) {
		this.minigameDescriptionHIDEANDSEEK = minigameDescriptionHIDEANDSEEK;
	}

	public List<String> getMinigameDescriptionREDGREEN() {
		return minigameDescriptionREDGREEN;
	}

	public void setMinigameDescriptionREDGREEN(List<String> minigameDescriptionREDGREEN) {
		this.minigameDescriptionREDGREEN = minigameDescriptionREDGREEN;
	}

	public String getGreenRedLightMove() {
		String s = greenRedLightMove;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameBlockParty() {
		String s = nameBlockParty;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNameHideAndSeek() {
		String s = nameHideAndSeek;
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
		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNameBlockParty(String nameBlockParty) {
		this.nameBlockParty = nameBlockParty;
	}

	public void setNameHideAndSeek(String nameHideAndSeek) {
		this.nameHideAndSeek = nameHideAndSeek;
	}

	public void setGreenRedLightMove(String greenRedLightMove) {
		this.greenRedLightMove = greenRedLightMove;
	}

	public String getGreenRedLightStop() {
		String s = greenRedLightStop;
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

		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public String getNextTimer() {
		String s = nextTimer;
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

		s = s.replaceAll("\\\\n", Constantes.SALTO_LINEA);
		return s;
	}

	public void setNextTimer(String nextTimer) {
		this.nextTimer = nextTimer;
	}

	public void setGreenRedLightStop(String greenRedLightStop) {
		this.greenRedLightStop = greenRedLightStop;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public void setResetCoinsAll(String resetCoinsAll) {
		this.resetCoinsAll = resetCoinsAll;
	}

	public void setResetTriesAll(String resetTriesAll) {
		this.resetTriesAll = resetTriesAll;
	}

	public void setResetWinsAll(String resetWinsAll) {
		this.resetWinsAll = resetWinsAll;
	}

	public void setResetCoinsPlayer(String resetCoinsPlayer) {
		this.resetCoinsPlayer = resetCoinsPlayer;
	}

	public void setResetTriesPlayer(String resetTriesPlayer) {
		this.resetTriesPlayer = resetTriesPlayer;
	}

	public void setResetWinsPlayer(String resetWinsPlayer) {
		this.resetWinsPlayer = resetWinsPlayer;
	}

}
