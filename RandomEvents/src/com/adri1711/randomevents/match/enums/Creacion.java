package com.adri1711.randomevents.match.enums;

public enum Creacion {

	BATTLE_NAME(0, "�6�lYou began the creation of the RandomEvent. Write the match's name"),

	AMOUNT_PLAYERS(1, "�6�lWrite the number of players allowed to enter the RandomEvent"),

	AMOUNT_PLAYERS_MIN(2, "�6�lWrite the minimum number of players to begin the RandomEvent"),

	SPAWN_PLAYER(3, "�6�lGo to the arena you created and write 'Done' to set the lobby spawn for the players"),

	ARENA_SPAWNS(4, "�6�lGo to the arena you created and write 'Done' to set each spawn location for the players"),

	ANOTHER_ARENA_SPAWNS(5, "�6�lPut another player spawn, say 'Done' "),

	INVENTORY(6, "�6�lTake the inventory for the players and say 'Done' "),

	REWARDS(7,
			"�6�lWrite a command to reward the winners. Use %player% and it will be replaced for the player's name. Put reprob <probability from 1 to 100> before the command to set a prob to execute the reward"),

	ANOTHER_REWARDS(8, "�6�lPut another reward for the winners or just say 'Done' to begin the next step"),

	MINIGAME_TYPE(9, "�6�lChoose Minigame type"),

	TIMER_MOB_SPAWN(10, "�6�lWrite the number of seconds (it can have decimals) to respawn each mob"),

	MOB_NAME(11, "�6�lChoose Mob"),

	MOB_SPAWN(12,
			"�6�lGo to the arena you created and write 'Done' to set the spawn location of the mob, 'New' to choose another mob or 'End' to end the creation of the RandomEvent"),

	PLAY_TIME(13, "�6�lChoose the play time in seconds"),
	
	ARROW_LOCATION1(14,
			"�6�lSet the first location of the cuboid of spawn of arrows, say 'Done'"),
	
	ARROW_LOCATION2(15,
			"�6�lSet the second location of the cuboid of spawn of arrows, say 'Done'"),
	
	TIMER_ARROW_SPAWN(16, "�6�lWrite the number of seconds (it can have decimals) to respawn each arrow"),
	
	
	GEM_LOCATION1(17,
			"�6�lSet the first location of the cuboid of spawn of gems, say 'Done'"),
	
	GEM_LOCATION2(18,
			"�6�lSet the second location of the cuboid of spawn of gems, say 'Done'"),
	
	TIMER_GEM_SPAWN(19, "�6�lWrite the number of seconds (it can have decimals) to respawn each gem"),


	
	END(999, "�6�lYou finished the creation of the random event!");

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

	public void setMessage(String message) {
		this.message = message;
	}

}
