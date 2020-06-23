package com.adri1711.randomevents.match;

public enum MinigameType {
	BATTLE_ROYALE("BR","Ultimo en pie"),

	BATTLE_ROYALE_TEAM_2("BRT2","Ultimo equipo en pie"),
	
	BATTLE_ROYALE_CABALLO("LJ","Lucha de jinetes"),
	
	TOP_KILLER("TKLL","Caza Estelar"),
	
	TOP_KILLER_TEAM_2("TKLLT2","Caza Estelar Equipos de 2"),
	
	KNOCKBACK_DUEL("KBD","Duelo de knockback"),
	
	ESCAPE_ARROW("EARR","Evita la flecha"),

	GEM_CRAWLER("GEMC","Atrapa la gema"),
	
	BOMB_TAG("BOMB","Patata caliente");
	
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
