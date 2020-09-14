package com.adri1711.randomevents.match.enums;

import com.adri1711.randomevents.match.Match;

public enum Creacion {

	BATTLE_NAME(0, "§6§lYou began the creation of the RandomEvent. Write the match's name"),

	AMOUNT_PLAYERS(1, "§6§lWrite the number of players allowed to enter the RandomEvent"),

	AMOUNT_PLAYERS_MIN(2, "§6§lWrite the minimum number of players to begin the RandomEvent"),

	SPAWN_PLAYER(3,
			"§6§lGo to the arena you created and write 'Done' to set the lobby spawn for the players "),

	ARENA_SPAWNS(4, "§6§lGo to the arena you created and write 'Done' to set each spawn location for the players (%players%/%maxPlayers%)"),

	ANOTHER_ARENA_SPAWNS(5, "§6§lPut another player spawn, say 'Done' (%players%/%maxPlayers%) "),

	SPECTATOR_SPAWNS(6, "§6§lSay 'Done' to put a spectator spawn and 'Next' to go to the next step"),

	INVENTORY(7, "§6§lTake the inventory for the players and say 'Done' "),

	REWARDS(8,
			"§6§lWrite a command to reward the winners. Use %player% and it will be replaced for the player's name. Put reprob <probability from 1 to 100> before the command to set a prob to execute the reward"),

	ANOTHER_REWARDS(9, "§6§lPut another reward for the winners or just say 'Done' to begin the next step"),

	MINIGAME_TYPE(10, "§6§lChoose Minigame type"),

	TIMER_MOB_SPAWN(11, "§6§lWrite the number of seconds (it can have decimals) to respawn each mob"),

	MOB_NAME(12, "§6§lChoose Mob"),

	MOB_SPAWN(13,
			"§6§lGo to the arena you created and write 'Done' to set the spawn location of the mob, 'New' to choose another mob or 'End' to end the creation of the RandomEvent"),

	PLAY_TIME(14, "§6§lChoose the play time in seconds"),

	ARROW_LOCATION1(15, "§6§lSet the first location of the cuboid of spawn of arrows, say 'Done'"),

	ARROW_LOCATION2(16, "§6§lSet the second location of the cuboid of spawn of arrows, say 'Done'"),

	TIMER_ARROW_SPAWN(17, "§6§lWrite the number of seconds (it can have decimals) to respawn each arrow"),

	GEM_LOCATION1(18, "§6§lSet the first location of the cuboid of spawn of gems, say 'Done'"),

	GEM_LOCATION2(19, "§6§lSet the second location of the cuboid of spawn of gems, say 'Done'"),

	TIMER_GEM_SPAWN(20, "§6§lWrite the number of seconds (it can have decimals) to respawn each gem"),

	TIMER_BOMB(21, "§6§lWrite the number of seconds to fire the bomb"),
	
	SECONDS_TO_SPAWN_BEAST(22,"§6§lWrite the number of seconds to spawn the beast"),
	
	SPAWN_BEAST(23,"§6§lGo to the arena you created and write 'Done' to set the beast spawn."),

	INVENTORY_BEAST(24,"§6§lTake the inventory for the beast and say 'Done' "),

	INVENTORY_RUNNERS(25,"§6§lTake the inventory for the players when they reach the goal and say 'Done' "),

	ENTITY_SPAWNS(26,
			"§6§lGo to the arena you created and write 'Done' to set each spawn location for the entities. (%entities%/%maxPlayers%)"),
	
	ANOTHER_ENTITY_SPAWNS(27, "§6§lPut another entity spawn, say 'Done' . (%entities%/%maxPlayers%)"),


	GOAL_LOCATION1(28, "§6§lSet the first location of the cuboid of the goal, say 'Done'"),

	GOAL_LOCATION2(29, "§6§lSet the second location of the cuboid of the goal, say 'Done'"),

	END(999, "§6§lYou finished the creation of the random event!");

	private Integer position;
	private String message;

	private Creacion(Integer position, String message) {
		this.position = position;
		this.message = message;
	}

	public static Creacion getByPosition(Integer position) {
		Creacion creation = null;
		for (Creacion w : Creacion.values()) {

			if (w.getPosition().equals(position)) {
				creation = w;
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
					.replaceAll("%maxPlayers%", "" + m.getAmountPlayers());
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
