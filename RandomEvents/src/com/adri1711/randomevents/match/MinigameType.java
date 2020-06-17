package com.adri1711.randomevents.match;

public enum MinigameType {
	BATTLE_ROYALE("BR","Ultimo en pie"),

	BATTLE_ROYALE_TEAM_2("BRT2","Ultimo equipo en pie"),
	
	BATTLE_ROYALE_CABALLO("LJ","Lucha de jinetes");
	
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
