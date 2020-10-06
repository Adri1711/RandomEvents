package com.adri1711.randomevents.match;

public enum MinigameType {
	BATTLE_ROYALE("BR", "Battle Royale"),

	BATTLE_ROYALE_TEAM_2("BRT2", "Team Battle Royale"),

	BATTLE_ROYALE_CABALLO("LJ", "Knights Battle"),

	TOP_KILLER("TKLL", "Top Killer"),

	TOP_KILLER_TEAM_2("TKLLT2", "Team Top Killer"),

	KNOCKBACK_DUEL("KBD", "Knockback Duel"),

	ESCAPE_ARROW("EARR", "Arrow Rain"),

	GEM_CRAWLER("GEMC", "Gem Carrier"),

	BOMB_TAG("BOMB", "TNT Tag"),

	BOAT_RUN("BOAT_RUN", "Boat Race"),

	ESCAPE_FROM_BEAST("ESCAPE_FROM_BEAST", "Escape from the beast"),

	RACE("RACE", "Race"),
	
	TNT_RUN("TNTRUN", "TNT Run"),
	
	SPLEEF("SPLEEF", "Spleef"),
	
	SPLEGG("SPLEGG", "Splegg");


	private String codigo;
	private String message;

	private MinigameType(String codigo, String message) {
		this.codigo = codigo;
		this.message = message;
	}

	public static MinigameType getByCodigo(String codigo) {
		MinigameType creation = null;
		for (MinigameType w : MinigameType.values()) {
			if (w.getCodigo().equals(codigo)) {
				creation = w;
			}
		}
		return creation;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
