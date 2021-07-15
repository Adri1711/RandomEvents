package com.adri1711.randomevents.match.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import com.adri1711.randomevents.RandomEvents;

public enum MinigameTypeAux {
	BATTLE_ROYALE("BR", "XMaterial.STONE_SWORD.", "getNameBattleRoyale"),

	BATTLE_ROYALE_TEAM_2("BRT2", "XMaterial.STONE_SWORD.", "getNameTeamBattleRoyale"),

	BATTLE_ROYALE_CABALLO("LJ", "XMaterial.IRON_HORSE_ARMOR.", "getNameKnightsBattle"),

	TOP_KILLER("TKLL", "XMaterial.IRON_SWORD.", "getNameTopKiller"),

	TOP_KILLER_TEAM_2("TKLLT2", "XMaterial.IRON_SWORD.", "getNameTeamTopKiller"),

	KNOCKBACK_DUEL("KBD", "XMaterial.WOODEN_SWORD.", "getNameKnockbackDuel"),

	ESCAPE_ARROW("EARR", "XMaterial.ARROW.", "getNameArrowRain"),

	ANVIL_SPLEEF("ANVIL_SPLEEF", "XMaterial.ANVIL.", "getNameAnvilSpleef"),

	GEM_CRAWLER("GEMC", "XMaterial.EMERALD.", "getNameGemCarrier"),

	BOMB_TAG("BOMB", "XMaterial.TNT.", "getNameTNTTag"),

	BOAT_RUN("BOAT_RUN", "XMaterial.OAK_BOAT.", "getNameBoatRace"),

	HORSE_RUN("HORSE_RUN", "XMaterial.IRON_HORSE_ARMOR.", "getNameHorseRace"),

	ESCAPE_FROM_BEAST("ESCAPE_FROM_BEAST", "XMaterial.SKELETON_SKULL.", "getNameEscapeBeast"),

	RACE("RACE", "XMaterial.IRON_BOOTS.", "getNameRace"),

	TNT_RUN("TNTRUN", "XMaterial.TNT.", "getNameTNTRun"),

	SPLEEF("SPLEEF", "XMaterial.IRON_SHOVEL.", "getNameSpleef"),

	SPLEGG("SPLEGG", "XMaterial.EGG.", "getNameSplegg"),

	OITC("OITC", "XMaterial.BOW.", "getNameOITC"),

	SG("SG", "XMaterial.BEEF.", "getNameSurvivalGames"),

	TSG("TSG", "XMaterial.COOKED_BEEF.", "getNameTeamSurvivalGames"),

	SW("SW", "XMaterial.DIRT.", "getNameSkywars"),

	TSW("TSW", "XMaterial.STONE.", "getNameTeamSkywars"),

	WDROP("WDROP", "XMaterial.WATER_BUCKET.", "getNameWaterDrop"),

	QUAKECRAFT("QUAKE", "XMaterial.STONE_HOE.", "getNameQuakeCraft"),

	PAINTBALL("PBALL", "XMaterial.SNOWBALL.", "getNamePaintball"),

	KOTH("KOTH", "XMaterial.GOLDEN_HELMET.", "getNameKingHill"),

	FISH_SLAP("FISHSLAP", "XMaterial.TROPICAL_FISH.", "getNameFishSlap"),

	HOEHOEHOE("HOE", "XMaterial.WOODEN_HOE.", "getNameHoehoehoe"),

	SPLATOON("SPLATOON", "XMaterial.RED_DYE.", "getNameSplatoon"),

	BOMBARDMENT("BOMBARDMENT", "XMaterial.COAL_BLOCK.", "getNameBombardment");

	

	private String codigo;
	private String material;
	private String message;

	private MinigameTypeAux(String codigo, String material, String message) {
		this.codigo = codigo;
		this.material = material;
		this.message = message;
	}

	public static MinigameTypeAux getByCodigo(String codigo) {
		MinigameTypeAux creation = null;
		for (MinigameTypeAux w : MinigameTypeAux.values()) {
			if (w.getCodigo().equalsIgnoreCase(codigo)) {
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

	
	public String getMessage(RandomEvents plugin){
		String menu="";
		try {
			Method method = plugin.getLanguage().getClass().getDeclaredMethod(message);
			menu += method.invoke(plugin.getLanguage());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return menu;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

}
