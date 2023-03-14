package com.adri1711.randomevents.match.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.util.Constantes;

public enum Creacion {

	MINIGAME_TYPE(0,"Minigame", "§6§lChoose Minigame type (Put 0 in chat)", "ALLMINIGAMES"),

	BATTLE_NAME(1,"Event Name", "§6§lYou began the creation of the RandomEvent. Write the match's name", "ALLMINIGAMES"),
	
	PERMISSION_OPTIONAL(2,"Permission (Optional)", "§6§ Write the match's permission", "ALLMINIGAMES"),

	AMOUNT_PLAYERS(3,"Maximum Player Amount", "§6§lWrite the number of players allowed to enter the RandomEvent", "ALLMINIGAMES"),

	AMOUNT_PLAYERS_MIN(4,"Minimum Player Amount", "§6§lWrite the minimum number of players to begin the RandomEvent", "ALLMINIGAMES"),

	SPAWN_PLAYER(5,"Player Spawn", "§6§lGo to the arena you created and write 'Done' to set the lobby spawn for the players ",
			"ALLMINIGAMES"),

	ARENA_SPAWNS(6, "Arena Spawns",
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the players (%players%/%maxPlayers%) or 'Next' to end the spawns",
			"ALLMINIGAMES", "PBALL,HOE,SPLATOON,TKLLTEAMS,BRTEAMS,TSGTEAMS,TSWTEAMS,PBALLTK"),

	ANOTHER_ARENA_SPAWNS(7, "Another Arena Spawns", "§6§lPut another player spawn, say 'Done' (%players%/%maxPlayers%) ", ""),

	SPECTATOR_SPAWNS(8,"Spectator Spawns", "§6§lSay 'Done' to put a spectator spawn and 'Next' to go to the next step", "ALLMINIGAMES"),

	REWARDS(9, "Rewards commands",
			"§6§lWrite a command to reward the winners. Use %player% and it will be replaced for the player's name. Put reprob <probability from 1 to 100> before the command to set a prob to execute the reward",
			"ALLMINIGAMES"),

	ANOTHER_REWARDS(10,"Another Rewards commands", "§6§lPut another reward for the winners or just say 'Done' to begin the next step", ""),

	KITS(11, "Kits","§6§lChoose a Kit to add to the match ", "ALLMINIGAMES"),

	TIMER_MOB_SPAWN(12,"Mob Spawn Timer", "§6§lWrite the number of seconds (it can have decimals) to respawn each mob", ""),

	MOB_NAME(13,"Mob Name", "§6§lChoose Mob", ""),

	MOB_SPAWN(14,"Mob Spawns",
			"§6§lGo to the arena you created and write 'Done' to set the spawn location of the mob, 'New' to choose another mob or 'End' to end the creation of the RandomEvent",
			""),

	PLAY_TIME(15,"Match Time", "§6§lChoose the play time in seconds",
			"TKLL,QUAKE,OITC,TKLLT2,KOTH,FISHSLAP,HOE,SPLATOON,TKLLTEAMS,PBALLTK,HIDEANDSEEK"),

	ARROW_LOCATION1(16,"Arrow Region Location 1", "§6§lSet the first location of the cuboid of spawn of arrows, say 'Done'", "EARR"),

	ARROW_LOCATION2(17,"Arrow Region Location 2", "§6§lSet the second location of the cuboid of spawn of arrows, say 'Done'", "EARR"),

	TIMER_ARROW_SPAWN(18,"Arrow Throw timer", "§6§lWrite the number of seconds (it can have decimals) to respawn each arrow", "EARR"),

	GEM_LOCATION1(19,"Gem Region Location 1", "§6§lSet the first location of the cuboid of spawn of gems, say 'Done'", "GEMC"),

	GEM_LOCATION2(20,"Gem Region Location 2", "§6§lSet the second location of the cuboid of spawn of gems, say 'Done'", "GEMC"),

	TIMER_GEM_SPAWN(21,"Gem Spawn timer", "§6§lWrite the number of seconds (it can have decimals) to respawn each gem", "GEMC"),

	TIMER_BOMB(22,"Bomb timer", "§6§lWrite the number of seconds to fire the bomb", "BOMB,BOMBARDMENT"),

	SECONDS_TO_SPAWN_BEAST(23,"Beast/Seekers  Spawn seconds", "§6§lWrite the number of seconds to spawn the beast", "ESCAPE_FROM_BEAST,HIDEANDSEEK"),

	SPAWN_BEAST(24,"Beast/Seekers Spawn", "§6§lGo to the arena you created and write 'Done' to set the beast spawn.", "ESCAPE_FROM_BEAST,HIDEANDSEEK"),

	INVENTORY_BEAST(25,"Beast/Seekers Inventory", "§6§lTake the inventory for the beast and say 'Done' ", "ESCAPE_FROM_BEAST,HIDEANDSEEK"),

	INVENTORY_RUNNERS(26,"Runners Inventory (After reaching goal)", "§6§lTake the inventory for the players when they reach the goal and say 'Done' ",
			"ESCAPE_FROM_BEAST"),

	ENTITY_SPAWNS(27,"Entity Spawns",
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the entities or 'Next' to make the entities appear on player location. (%entities%/%maxPlayers%)",
			"BOAT_RUN,HORSE_RUN"),

	ANOTHER_ENTITY_SPAWNS(28,"Another Entity Spawns", "§6§lPut another entity spawn, say 'Done' . (%entities%/%maxPlayers%)", ""),

	GOAL_LOCATION1(29,"Goal Region Location 1", "§6§lSet the first location of the cuboid of the goal, say 'Done'",
			"BOAT_RUN,HORSE_RUN,ESCAPE_FROM_BEAST,RACE,REDGREEN"),

	GOAL_LOCATION2(30,"Goal Region Location 2", "§6§lSet the second location of the cuboid of the goal, say 'Done'",
			"BOAT_RUN,HORSE_RUN,ESCAPE_FROM_BEAST,RACE,REDGREEN"),

	MATERIAL_SPLEEF(31,"Spleef Material", "§6§lGet the block you want to set mineable for spleef on your hand and say 'Done'",
			"SPLEEF,SPLEGG,ANVIL_SPLEEF"),

	ANOTHER_MATERIAL_SPLEEF(32,"Another Spleef Material",
			"§6§lGet another block you want to set mineable for spleef on your hand and say 'Done' or say 'Next' to end the spleef materials",
			""),

	MAP_LOCATION1(33,"Map Region Location 1", "§6§lSet the first location of the cuboid of the map, say 'Done'",
			"SG,TSG,SW,TSW,TSGTEAMS,TSWTEAMS,ANVIL_SPLEEF,BLOCKPARTY"),

	MAP_LOCATION2(34,"Map Region Location 2", "§6§lSet the second location of the cuboid of the map, say 'Done'",
			"SG,TSG,SW,TSW,TSGTEAMS,TSWTEAMS,ANVIL_SPLEEF,BLOCKPARTY"),

	INVENTORY_CHESTS(35, "Chests Inventory",
			"§6§lTake the inventory that will have a probability to appear in chests and say 'Done' to set it or 'Add' to add the items",
			"SG,TSG,SW,TSW,TSGTEAMS,TSWTEAMS"),

	WARMUP_TIME(36,"Warmup Time", "§6§lChoose the warmup time in seconds", "SG,TSG,SW,TSW,TSGTEAMS,TSWTEAMS"),

	SHRINK_TIME(37,"Shrink Time", "§6§lChoose to shrink the border in seconds", "SG,TSG,TSGTEAMS"),

	BLOCKS_ALLOWED(38,"Allowed Blocks", "§6§lPut in your hand a block allowed to break/place in the map and say 'Done'",
			"SG,TSG,SW,TSW,HOE,SPLATOON,TSWTEAMS,TSGTEAMS"),

	NO_MOVE_TIME(39,"Player Stuck Time", "§6§lChoose to allow players to move in seconds", "SG,TSG,SW,TSW,TSWTEAMS,TSGTEAMS,REDGREEN"),

	TIMER_ANVIL_SPAWN(40,"Anvil Spawn timer", "§6§lWrite the number of seconds (it can have decimals) to respawn each anvil",
			"ANVIL_SPLEEF"),

	WATER_DROP_SCENES(41,"Water Drop Scenes", "§6§lChoose a scene to add to the event", "WDROP"),

	NUMBER_OF_TEAMS(42,"Number of teams", "§6§lChoose a number of teams between 2 and 8",
			"PBALL,HOE,SPLATOON,TKLLTEAMS,BRTEAMS,TSGTEAMS,TSWTEAMS,PBALLTK"),

	TEAM_SPAWNS(43,"Team Spawns",
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the team (%players%/%maxTeams%)",
			"PBALL,HOE,SPLATOON,TKLLTEAMS,BRTEAMS,TSGTEAMS,TSWTEAMS,PBALLTK"),

	ANOTHER_TEAM_SPAWNS(44, "Another Team Spawns","§6§lPut another team spawn, say 'Done' (%players%/%maxTeams%) ", ""),

	KOTH_LOCATION1(45,"KoTH Region Location 1", "§6§lSet the first location of the cuboid of the Koth, say 'Done'", "KOTH,FISHSLAP"),

	KOTH_LOCATION2(46,"KoTH Region Location 2", "§6§lSet the second location of the cuboid of the Koth, say 'Done'", "KOTH,FISHSLAP"),

	CANNON_SPAWNS(47,"Cannon Spawns",
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for cannons or 'Next' to stop. ",
			"BOMBARDMENT,REDGREEN"),

	TNT_TAG_HEAD(48,"TNT Head", "§6§lTake the item to put in the head and say 'Done'. ", "BOMB"),

	ID_NPC(49,"NPC ID (Citizens Required)", "§6§lPut the Citizens ID of the NPC for the game. ", "REDGREEN"),

	USE_OWN_INVENTORY(50,"Use own inventory?", "", "ALLMINIGAMES"),

	ALL_BLOCKS_ALLOWED(51,"Allow all blocks?", "§6§lChanged blocks allowed", "SG,TSG,SW,TSW,HOE,SPLATOON,TSWTEAMS,TSGTEAMS"),
	
	GAMEMODE(52,"Forced gamemode (Optional)", "§6§lPut the gamemode (SURVIVAL, CREATIVE, SPECTATOR, ADVENTURE) or put 'N'", "ALLMINIGAMES"),

	SHRINK_BLOCKS(53,"Blocks Shrinked", "§6§lChoose how many blocks to shrink each time", "SG,TSG,TSGTEAMS"),
	
	REFILL_CHEST(54,"Chest Refill time", "§6§lChoose time to refill chests in seconds", "SG,TSG,TSGTEAMS,SW,TSW,TSWTEAMS"),
	
	TIMER_BLOCK_DISAPPEAR(55,"Block Disappear timer","§6§lChoose time to make the blocks disappear in seconds", "BLOCKPARTY"),
	
	TIMER_DECREASE_TIME(56,"Decrease timer","§6§lChoose the §e§lticks §6§lto decrease each round (20 ticks is 1 second)", "BLOCKPARTY"),
	
	COLOR_APPEAR_TIME(57,"Color timer","§6§lChoose the seconds for the color to show", "BLOCKPARTY"),
	
	COLOR_APPEAR_DECREASE_TIME(58,"Decrease Color timer","§6§lChoose the §e§lticks §6§lto decrease each round (20 ticks is 1 second)", "BLOCKPARTY"),
	
	NUMBER_OF_SEEKERS(59,"Amount of Seekers","§6§lWrite the number of players that will be seekers", "HIDEANDSEEK"),
	
	
	COMMANDS_ON_START_OPTIONAL(900,"Commands on start (Optional)", "§6§lSet a command for the start of the game or just put 'Done' to stop setting commands", "ALLMINIGAMES"),

	COMMANDS_ON_KILL_OPTIONAL(901,"Commands on kill (Optional)", "§6§lSet a command for kills on the game or just put 'Done' to stop setting commands ( use %player% (killer) or %victim% (killed) )", "ALLMINIGAMES"),

	COMMANDS_ON_ELIMINATION_OPTIONAL(902,"Commands on elimination (Optional)", "§6§lSet a command for the elimination of a player of the game or just put 'Done' to stop setting commands", "ALLMINIGAMES"),

	SAVE(997,"Save", "§6§lYou are about to save a Random Event, put 'Y' to confirm or 'N' to continue creating",
			"ALLMINIGAMES"),

	DELETE(998,"Clear a field", "§6§lChoose the field you want to remove", "ALLMINIGAMES"),

	CANCEL(999,"Cancel", "§6§lYou are about to cancel a Random Event, put 'Y' to confirm or 'N' to continue creating",
			"ALLMINIGAMES");

	private Integer position;
	private String name;
	private String message;

	private String allowed;

	private String blacklist;

	private Creacion(Integer position,String name, String message, String allowed) {
		this.position = position;
		this.name=name;
		this.message = message;
		this.allowed = allowed;
		this.blacklist = "";
	}

	private Creacion(Integer position,String name, String message, String allowed, String blacklist) {
		this.position = position;
		this.name=name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
