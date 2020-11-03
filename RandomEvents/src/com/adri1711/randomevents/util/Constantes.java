package com.adri1711.randomevents.util;

import com.adri1711.randomevents.match.enums.Creacion;
import com.adri1711.util.enums.XMaterial;

public class Constantes {

	public static String GUI_NAME = "RandomEvents_Stats";
	public static String DONE = "Done";
	public static String NEXT = "Next";
	public static String SAME = "Same";
	public static String NEW = "New";
	public static String CADENA_PLAYER = "%player%";
	public static String SALTO_LINEA = "\n";
	public static String INVENTORY_NAME = "§9RandomEvents";
	public static String INVENTORY_NAME_STATS = "§9RandomEvents Stats";
	public static String INVENTORY_INFO_WAVE = "§9RandomEvents Wave Info";
	public static String INVENTORY_NAME_STATS_WITHOUT = "§9Stats";
	public static String WAITING = "§2Waiting";
	public static String PLAYING = "§cPlaying";
	public static String PROBABILITY_CMD = "reprob";
	public static String MATCH_PLAYED = "§6§lNumber of tries";
	public static String MATCH_WIN = "§6§lNumber of wins";
	public static String TOP_1 = "§6§lTop 1";
	public static String TOP_2 = "§6§lTop 2";
	public static String TOP_3 = "§6§lTop 3";
	public static String COOLDOWN_OUT = "§6You can play again:";
	public static String PERMISO_TOP = "bossbattle.see.top";
	public static String CHOOSE_MOB = "§6§lChoose Mob";
	public static String SPAWN_MOB = "§6§lSpawn Mob";
	public static String SEE_INFO_WAVE = "§6§lSee Info Wave";
	public static String NEXT_WAVE = "§2§lNext Wave";
	public static String END_CREATION = "§c§lEnd RandomEvent";
	public static String CONTENTS = "contents";
	public static String EQUIPMENT = "equipment";
	public static String GREEN = "§2§l";
	public static String SPLEGG_EGG = "Splegg Egg Code 001";

	public enum Messages {

		TAG_PLUGIN("tagPlugin", "tagPlugin", "&9&l[RandomEvents]"),

		INVALID_INPUT("invalidInput", "creation.invalidInput", "&c&lInvalid input, try another one"),

		SECONDS_3_REMAINING("secondsRemaining3", "comun.secondsRemaining3", "&6&l3"),

		SECONDS_2_REMAINING("secondsRemaining2", "comun.secondsRemaining2", "&6&l2"),

		SECONDS_1_REMAINING("secondsRemaining1", "comun.secondsRemaining1", "&6&l1"),

		ERROR("error", "comun.error", "&c&lSome error ocurred, check the server log."),

		CMD_NOT_ALLOWED("cmdNotAllowed", "comun.cmdNotAllowed", "&c&lYou cant use this command here"),

		INVALID_CMD("invalidCmd", "comun.invalidCmd",
				"&c&lInvalid command, do /randomevent or /revent to see the valid commands"),

		NO_PERMISSION("noPermission", "comun.noPermission", "&c&lYou don't have permission to do that!"),

		END_OF_ARENA_CREATION("endOfArenaCreation", "creation.endOfArenaCreation",
				"&6&lYou successfully created a RandomEvents!"),

		END_OF_SCHEDULE_CREATION("endOfScheduleCreation", "creation.endOfScheduleCreation",
				"&6&lYou successfully created a Schedule!"),

		CANCEL_OF_ARENA_CREATION("cancelOfArenaCreation", "creation.cancelOfArenaCreation",
				"&6&lYou canceled the creation of a RandomEvents!"),

		PLUGIN_RELOAD("pluginReload", "comun.pluginReload", "&6&lPlugin reloaded!"),

		MATCH_FULL("matchFull", "match.matchFull", "&c&lThe match is already full"),

		ALREADY_PLAYING_MATCH("alreadyPlayingMatch", "match.alreadyPlayingMatch",
				"&c&lYou can't leave the match if it has begun"),

		MATCH_BEGUN("matchBegun", "match.matchBegun", "&c&lThe event has begun, you cant join now"),

		MATCHES("matches", "match.matches", "&6&lMatches:"),

		ALREADY_MATCH("alreadyMatch", "match.alreadyMatch",
				"&c&lYou can't create another event while one is still running"),

		WAITING_FOR_PLAYERS("waitingForPlayers", "match.waitingForPlayers",
				"&6&lWaiting for players. ( %players% / %neededPlayers% )"),

		SPAWN_SET("spawnSet", "comun.spawnSet", "&6&lYou have set the spawn succesfully"),

		NOT_IN_MATCH("notInMatch", "match.notInMatch", "&c&lYou are not in an event"),

		WIN_RANDOM_EVENTS("winRandomEvents", "match.winRandomEvents", "&6&lCongratulations! You won the event"),

		EVENT_ANNOUNCE("eventAnnounce", "announce.eventAnnounce",
				"&6&lThe event &e&l'%event%'&6&l of type  &e&l'%type%'&6&l, begins in: "),
		
		EVENT_STOPPED("eventStopped", "announce.eventStopped",
				"&6&lThe event &e&l'%event%'&6&l of type  &e&l'%type%'&6&l, was forced to stop."),

		EVENT_CANCELLED("eventCancelled", "announce.eventCancelled",
				"&6&lThe event &e&l'%event%'&6&l of type  &e&l'%type%'&6&l, was cancelled for lack of players."),

		TOURNAMENT_CANCELLED("tournamentCancelled", "announce.tournamentCancelled",
				"&6&lThe tournament was cancelled for lacking players."),

		INVALID_PASSWORD("invalidPassword", "comun.invalidPassword", "&c&lInvalid password for the event"),

		FIRST_ANNOUNCE("firstAnnounce", "announce.firstAnnounce", "&6&lA Random Event has just begun, "),

		NEXT_ANNOUNCE("nextAnnounce", "announce.nextAnnounce", "&6&lA Random Event is still waiting for players, "),

		FIRST_ANNOUNCE_TOURNAMENT("firstAnnounceTournament", "announce.firstAnnounceTournament",
				"&6&lA Tournament Random Event has just begun, "),

		NEXT_ANNOUNCE_TOURNAMENT("nextAnnounceTournament", "announce.nextAnnounceTournament",
				"&6&lA Tournament Random Event is still waiting for players, "),

		CLICK_HERE("clickHere", "announce.clickHere", "&9&l[Click here to join]"),

		LAST_PART("lastPart", "announce.lastPart", "&6&l ( %players% / %neededPlayers% )"),

		LAST_PART_TOURNAMENT("lastPartTournament", "announce.lastPartTournament",
				"&6&l ( %players% / %neededPlayers% )"),

		RACE_TOURNAMENT("raceTournament", "announce.raceTournament",
				"&6&lThe player %player% has reached the goal ( %players% / %neededPlayers% )"),

		YOU_BEAST("youBeast", "announce.youBeast", "&6&lYou are the beast, wait until you get teleported to the arena"),

		TOURNAMENT_ALIVE("tournamentAlive", "match.tournamentAlive",
				"&e&lThese players still survive on the tournament :"),

		WINNERS("winners", "match.winners",
				"&e&l%players% &6&lwon the Random Event &e&l'%event%'&6&l ! Congratulations!"),

		WINNERS_TOURNAMENT("winnersTournament", "match.winnersTournament",
				"&e&l%players% &6&lwon the Tournament Random Event ! Congratulations!"),

		WINNERS_POINTS("winnersPoints", "match.winnersPoints",
				"&e&l%players% &6&lwon the Random Event '%event%' with %points% points ! Congratulations!"),

		DISPOSE_LEATHER_ITEMS("disposeLeatherItems", "comun.disposeLeatherItems",
				"&c&lFor security reasons please leave your leather items before entering the event"),
		
		CLEAR_INVENTORY("clearInventory", "comun.clearInventory",
				"&c&lClear the inventory before joining the RandomEvent"),

		SHOW_ALONE("showAlone", "match.showAlone", "&6&lYou are playing alone, good luck!"),

		SHOW_TEAM("showTeam", "match.showTeam", "&6&lYour team is: &e&l%players%"),

		TIME_REMAINING("timeRemaining", "match.timeRemaining", "&6&l%minutes% remaining"),

		NOW_POINTS("nowPoints", "match.nowPoints", "&6&lYou have now &e&l%points% &6&lpoints"),

		NOW_GEMS("nowGems", "match.nowGems", "&6&lThe player &e&l%player% &6&lhas now &e&l%points% &6&lgems"),

		NOW_PROTECTED("nowProtected", "match.nowProtected", "&6&lYou are now protected for 3 seconds"),

		MATCH_BEGIN_SOON("matchBeginSoon", "match.matchBeginSoon", "&6&lThe Random Event will begin soon"),

		LOST_GEMS("lostGems", "match.lostGems", "&e&l%player% &6&llost all gems"),

		PLAYER_WINNING("playerWinning", "match.playerWinning",
				"&6&lThe player &e&l%player% &6&lhas enough gems, kill him or the match will end"),

		PLAYER_WINNING_SECONDS("playerWinningSeconds", "match.playerWinningSeconds",
				"&e&l%player% &6&lwins in %seconds% seconds"),

		BOMB_SECONDS("bombSeconds", "match.bombSeconds", "&6&lBomb explodes in %seconds% seconds"),

		PLAYER_HAS_BOMB("playerHasBomb", "match.playerHasBomb", "&6&lThe player &e&l%player% &6&lhas the bomb"),

		BOMB_EXPLODE("bombExplode", "match.bombExplode", "&6&lThe player &e&l%player% &6&lblew up"),

		LEAVE_COMMAND("leaveCommand", "comun.leaveCommand",
				"&6&lYou are now a spectator. Use &e&l/revent leave &6&lto leave spectator mode"),

		ERROR_SAVING_INVENTORY("errorSavingInventory", "comun.errorSavingInventory",
				"&c&lYour inventory failed on save, please save your items and try again"),

		STATS_DISABLED("statsDisabled", "stats.statsDisabled", "&c&lThe stats are disabled"),

		STATS_TRIES("statsTries", "stats.statsTries", "&cTries: "),

		STATS_WINS("statsWins", "stats.statsWins", "&2Wins: "),

		STATS_WIN_RATIO("statsWinsRatio", "stats.statsWinsRatio", "&eWin Ratio: "),

		MINIGAME_DESC_BR("minigameDescriptionBR", "minigame.description.BR",
				"&eA fierce battle where just 1 player;&e or team can remain alive."),

		MINIGAME_DESC_BRT2("minigameDescriptionBRT2", "minigame.description.BRT2",
				"&eA fierce battle where just 1 player;&e or team can remain alive."),

		MINIGAME_DESC_LJ("minigameDescriptionLJ", "minigame.description.LJ",
				"&eA fierce battle where just 1 player;&e with his horse can remain alive."),

		MINIGAME_DESC_TKLL("minigameDescriptionTKLL", "minigame.description.TKLL",
				"&eOnly the most powerful assasin can win this event, kill players to get points,;&e the player with more points will get the rewards"),

		MINIGAME_DESC_TKLLT2("minigameDescriptionTKLLT2", "minigame.description.TKLLT2",
				"&eOnly the most powerful assasin can win this event, kill players to get points,;&e the team with more points will get the rewards"),

		MINIGAME_DESC_KBD("minigameDescriptionKBD", "minigame.description.KBD",
				"&eA battle where your attack power does not matter, you will have to make an strategy;&e to get the enemies thrown away from the arena to get them killed!;&e Be the last one who stands!"),

		MINIGAME_DESC_EARR("minigameDescriptionEARR", "minigame.description.EARR",
				"&eAvoid the arrows coming from the sky if you;&e dont want to lose your head!"),

		MINIGAME_DESC_GEMC("minigameDescriptionGEMC", "minigame.description.GEMC",
				"&eRandom gems will spawn in the arena. Try to get all of them until you reach 10 gems;&e or steal it from the enemies that has them. Stand 10 seconds;&e more carrying your 10 gems and you will get your hands on the reward!"),

		MINIGAME_DESC_BOMB("minigameDescriptionBOMB", "minigame.description.BOMB",
				"&eRun with the bomb and hit enemies to give it to them and avoid your own death.; &e From time to time the bomb will explode and will get the player that had it;&e and everyone near him dead."),

		MINIGAME_DESC_BOAT_RUN("minigameDescriptionBOAT_RUN", "minigame.description.BOAT_RUN",
				"&eBe the fastest sailor and reach the finish line before;&e your pursuers!"),

		MINIGAME_DESC_ESCAPE_FROM_BEAST("minigameDescriptionESCAPE_FROM_BEAST",
				"minigame.description.ESCAPE_FROM_BEAST",
				"&eThe classic Run from the beast minigame!;&e Beat the beast by completing all the scenario and get enough equipment;&e to make damage to the beast!"),

		MINIGAME_DESC_RACE("minigameDescriptionRACE", "minigame.description.RACE",
				"&eRun and be the first to reach the goal;&e to get your reward!"),

		MINIGAME_DESC_TNTRUN("minigameDescriptionTNTRUN", "minigame.description.TNTRUN",
				"&eRun for your life, the tnt behind you will disappear,;&e try to stay over them to win the match!"),

		MINIGAME_DESC_SPLEEF("minigameDescriptionSPLEEF", "minigame.description.SPLEEF",
				"&eMake the other players fall by breaking the blocks on their feets,;&e be the one to stay safe to win the reward!"),

		MINIGAME_DESC_SPLEGG("minigameDescriptionSPLEGG", "minigame.description.SPLEGG",
				"&eAnother type of Spleef but this time use your pistol to throw eggs;&e that will break the floor!");

		private String javaField;
		private String ymlField;
		private String messageDefault;

		private Messages(String javaField, String ymlField, String message) {
			this.javaField = javaField;
			this.ymlField = ymlField;
			this.messageDefault = message;
		}

		public String getJavaField() {
			return javaField;
		}

		public void setJavaField(String javaField) {
			this.javaField = javaField;
		}

		public String getYmlField() {
			return ymlField;
		}

		public void setYmlField(String ymlField) {
			this.ymlField = ymlField;
		}

		public String getMessageDefault() {
			return messageDefault;
		}

		public void setMessageDefault(String messageDefault) {
			this.messageDefault = messageDefault;
		}

	}

}
