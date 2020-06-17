package com.adri1711.randomevents.match.enums;

public enum Creacion {
	BATTLE_NAME(0, "§6§lYou began the creation of the BossBattle. Write the match's name"),

	PERMISSION_NEEDED(1, "§6§lWrite the permission needed to join the BossBattle"),

	AMOUNT_PLAYERS(2, "§6§lWrite the number of players allowed to enter the BossBattle"),

	COOLDOWN(3, "§6§lSet a cooldown to reenter to the battle in seconds (Minimum 1)"),

	ITEM_READY(4, "§6§lPut the item you want to represent the battle when it's waiting for players and say 'Done'"),

	ITEM_PLAYING(5, "§6§lPut the item you want to represent the battle when it's being played and say 'Done'"),

	SPAWN_PLAYER(6, "§6§lGo to the arena you created and write 'Done' to set the spawn location for the players"),

	REWARDS(7,
			"§6§lWrite a command to reward the winners. Use %player% and it will be replaced for the player's name. Put bbprob <probability from 1 to 100> before the command to set a prob to execute the reward"),

	ANOTHER_REWARDS(8, "§6§lPut another reward for the winners or just say 'Done' to begin the next step"),

	WAVES(9, "§6§lYou began the creation of the Waves!");

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
