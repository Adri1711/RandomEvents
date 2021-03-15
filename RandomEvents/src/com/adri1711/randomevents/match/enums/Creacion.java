package com.adri1711.randomevents.match.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.util.Constantes;

public enum Creacion {

	MINIGAME_TYPE(0, "§6§lChoose Minigame type", "ALLMINIGAMES"),

	BATTLE_NAME(1, "§6§lYou began the creation of the RandomEvent. Write the match's name", "ALLMINIGAMES"),

	AMOUNT_PLAYERS(2, "§6§lWrite the number of players allowed to enter the RandomEvent", "ALLMINIGAMES"),

	AMOUNT_PLAYERS_MIN(3, "§6§lWrite the minimum number of players to begin the RandomEvent", "ALLMINIGAMES"),

	SPAWN_PLAYER(4, "§6§lGo to the arena you created and write 'Done' to set the lobby spawn for the players ",
			"ALLMINIGAMES"),

	ARENA_SPAWNS(5,
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the players (%players%/%maxPlayers%)",
			"ALLMINIGAMES", "PBALL,HOE"),

	ANOTHER_ARENA_SPAWNS(6, "§6§lPut another player spawn, say 'Done' (%players%/%maxPlayers%) ", ""),

	SPECTATOR_SPAWNS(7, "§6§lSay 'Done' to put a spectator spawn and 'Next' to go to the next step", "ALLMINIGAMES"),

	REWARDS(8,
			"§6§lWrite a command to reward the winners. Use %player% and it will be replaced for the player's name. Put reprob <probability from 1 to 100> before the command to set a prob to execute the reward",
			"ALLMINIGAMES"),

	ANOTHER_REWARDS(9, "§6§lPut another reward for the winners or just say 'Done' to begin the next step", ""),

	KITS(10, "§6§lChoose a Kit to add to the match ", "ALLMINIGAMES"),

	TIMER_MOB_SPAWN(11, "§6§lWrite the number of seconds (it can have decimals) to respawn each mob", ""),

	MOB_NAME(12, "§6§lChoose Mob", ""),

	MOB_SPAWN(13,
			"§6§lGo to the arena you created and write 'Done' to set the spawn location of the mob, 'New' to choose another mob or 'End' to end the creation of the RandomEvent",
			""),

	PLAY_TIME(14, "§6§lChoose the play time in seconds", "TKLL,QUAKE,OITC,TKLLT2,KOTH,FISHSLAP,HOE"),

	ARROW_LOCATION1(15, "§6§lSet the first location of the cuboid of spawn of arrows, say 'Done'", "EARR"),

	ARROW_LOCATION2(16, "§6§lSet the second location of the cuboid of spawn of arrows, say 'Done'", "EARR"),

	TIMER_ARROW_SPAWN(17, "§6§lWrite the number of seconds (it can have decimals) to respawn each arrow", "EARR"),

	GEM_LOCATION1(18, "§6§lSet the first location of the cuboid of spawn of gems, say 'Done'", "GEMC"),

	GEM_LOCATION2(19, "§6§lSet the second location of the cuboid of spawn of gems, say 'Done'", "GEMC"),

	TIMER_GEM_SPAWN(20, "§6§lWrite the number of seconds (it can have decimals) to respawn each gem", "GEMC"),

	TIMER_BOMB(21, "§6§lWrite the number of seconds to fire the bomb", "BOMB"),

	SECONDS_TO_SPAWN_BEAST(22, "§6§lWrite the number of seconds to spawn the beast", "ESCAPE_FROM_BEAST"),

	SPAWN_BEAST(23, "§6§lGo to the arena you created and write 'Done' to set the beast spawn.", "ESCAPE_FROM_BEAST"),

	INVENTORY_BEAST(24, "§6§lTake the inventory for the beast and say 'Done' ", "ESCAPE_FROM_BEAST"),

	INVENTORY_RUNNERS(25, "§6§lTake the inventory for the players when they reach the goal and say 'Done' ",
			"ESCAPE_FROM_BEAST"),

	ENTITY_SPAWNS(26,
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the entities or 'Next' to make the entities appear on player location. (%entities%/%maxPlayers%)",
			"BOAT_RUN,HORSE_RUN"),

	ANOTHER_ENTITY_SPAWNS(27, "§6§lPut another entity spawn, say 'Done' . (%entities%/%maxPlayers%)", ""),

	GOAL_LOCATION1(28, "§6§lSet the first location of the cuboid of the goal, say 'Done'",
			"BOAT_RUN,HORSE_RUN,ESCAPE_FROM_BEAST,RACE"),

	GOAL_LOCATION2(29, "§6§lSet the second location of the cuboid of the goal, say 'Done'",
			"BOAT_RUN,HORSE_RUN,ESCAPE_FROM_BEAST,RACE"),

	MATERIAL_SPLEEF(30, "§6§lGet the block you want to set mineable for spleef on your hand and say 'Done'",
			"SPLEEF,SPLEGG,ANVIL_SPLEEF"),

	ANOTHER_MATERIAL_SPLEEF(31,
			"§6§lGet another block you want to set mineable for spleef on your hand and say 'Done' or say 'Next' to end the spleef materials",
			""),

	MAP_LOCATION1(32, "§6§lSet the first location of the cuboid of the map, say 'Done'", "SG,TSG,SW,TSW,ANVIL_SPLEEF"),

	MAP_LOCATION2(33, "§6§lSet the second location of the cuboid of the map, say 'Done'", "SG,TSG,SW,TSW,ANVIL_SPLEEF"),

	INVENTORY_CHESTS(34,
			"§6§lTake the inventory that will have a probability to appear in chests and say 'Done' to set it or 'Add' to add the items",
			"SG,TSG,SW,TSW"),

	WARMUP_TIME(35, "§6§lChoose the warmup time in seconds", "SG,TSG,SW,TSW"),

	SHRINK_TIME(36, "§6§lChoose to shrink the border in seconds", "SG,TSG"),

	BLOCKS_ALLOWED(37, "§6§lPut in your hand a block allowed to break/place in the map and say 'Done'",
			"SG,TSG,SW,TSW,HOE"),

	NO_MOVE_TIME(38, "§6§lChoose to allow players to move in seconds", "SG,TSG,SW,TSW"),

	TIMER_ANVIL_SPAWN(39, "§6§lWrite the number of seconds (it can have decimals) to respawn each anvil",
			"ANVIL_SPLEEF"),

	WATER_DROP_SCENES(40, "§6§lChoose a scene to add to the event", "WDROP"),

	NUMBER_OF_TEAMS(41, "§6§lChoose a number of teams between 2 and 8", "PBALL,HOE"),

	TEAM_SPAWNS(42,
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the team (%players%/%maxTeams%)",
			"PBALL,HOE"),

	ANOTHER_TEAM_SPAWNS(43, "§6§lPut another team spawn, say 'Done' (%players%/%maxTeams%) ", ""),

	KOTH_LOCATION1(44, "§6§lSet the first location of the cuboid of the Koth, say 'Done'", "KOTH,FISHSLAP"),

	KOTH_LOCATION2(45, "§6§lSet the second location of the cuboid of the Koth, say 'Done'", "KOTH,FISHSLAP"),

	SAVE(997, "§6§lYou are about to save a Random Event, put 'Y' to confirm or 'N' to continue creating",
			"ALLMINIGAMES"),

	DELETE(998, "§6§lChoose the field you want to remove", "ALLMINIGAMES"),

	CANCEL(999, "§6§lYou are about to cancel a Random Event, put 'Y' to confirm or 'N' to continue creating",
			"ALLMINIGAMES");

	private Integer position;
	private String message;

	private String allowed;

	private String blacklist;

	private Creacion(Integer position, String message, String allowed) {
		this.position = position;
		this.message = message;
		this.allowed = allowed;
		this.blacklist = "";
	}

	private Creacion(Integer position, String message, String allowed, String blacklist) {
		this.position = position;
		this.message = message;
		this.allowed = allowed;
		this.blacklist = blacklist;
	}

	public static Creacion getByPosition(Integer position) {
		Creacion creation = null;
		if (position != null) {
			for (Creacion w : Creacion.values()) {

				if (w.getPosition().equals(position)) {
					creation = w;
				}
			}
		}
		return creation;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public String getMessage(Match m) {
		if (m == null || m.getAmountPlayers() == null) {
			return message;
		} else {
			return message.replaceAll("%entities%", "" + m.getEntitySpawns().size())
					.replaceAll("%players%", "" + m.getSpawns().size())
					.replaceAll("%maxPlayers%", "" + m.getAmountPlayers())
					.replaceAll("%maxTeams%", "" + m.getNumberOfTeams());
		}
	}

	public static List<Creacion> getCreaciones(Match m) {
		List<Creacion> creaciones = new ArrayList<Creacion>();
		for (Creacion c : Creacion.values()) {
			String[] minijuegos = c.getAllowed().split(",");
			List<String> list = Arrays.asList(minijuegos);
			String[] minijuegosBlack = c.getBlacklist().split(",");
			List<String> blacklist = Arrays.asList(minijuegosBlack);
			if (m.getMinigame() != null && (!blacklist.contains(m.getMinigame().getCodigo()))
					&& (list.contains(m.getMinigame().getCodigo()) || list.contains(Constantes.ALL))) {
				creaciones.add(c);
			}
		}
		return creaciones;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAllowed() {
		return allowed;
	}

	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

}
