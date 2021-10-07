package com.adri1711.randomevents.match.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.util.enums.XMaterial;

public enum MinigameType {
	BATTLE_ROYALE("BR", XMaterial.STONE_SWORD.parseMaterial(), "getNameBattleRoyale"),

	BATTLE_ROYALE_TEAM_2("BRT2", XMaterial.STONE_SWORD.parseMaterial(), "getNameTeamBattleRoyale"),

	BATTLE_ROYALE_TEAMS("BRTEAMS", XMaterial.ORANGE_DYE.parseMaterial(), "getNameRealTeamBattleRoyale"),

	BATTLE_ROYALE_CABALLO("LJ", XMaterial.IRON_HORSE_ARMOR.parseMaterial(), "getNameKnightsBattle"),

	TOP_KILLER("TKLL", XMaterial.IRON_SWORD.parseMaterial(), "getNameTopKiller"),

	TOP_KILLER_TEAM_2("TKLLT2", XMaterial.IRON_SWORD.parseMaterial(), "getNameTeamTopKiller"),

	TOP_KILLER_TEAMS("TKLLTEAMS", XMaterial.GOLDEN_SWORD.parseMaterial(), "getNameRealTeamTopKiller"),

	KNOCKBACK_DUEL("KBD", XMaterial.WOODEN_SWORD.parseMaterial(), "getNameKnockbackDuel"),

	ESCAPE_ARROW("EARR", XMaterial.ARROW.parseMaterial(), "getNameArrowRain"),

	ANVIL_SPLEEF("ANVIL_SPLEEF", XMaterial.ANVIL.parseMaterial(), "getNameAnvilSpleef"),

	GEM_CRAWLER("GEMC", XMaterial.EMERALD.parseMaterial(), "getNameGemCarrier"),

	BOMB_TAG("BOMB", XMaterial.TNT.parseMaterial(), "getNameTNTTag"),

	BOAT_RUN("BOAT_RUN", XMaterial.OAK_BOAT.parseMaterial(), "getNameBoatRace"),

	HORSE_RUN("HORSE_RUN", XMaterial.IRON_HORSE_ARMOR.parseMaterial(), "getNameHorseRace"),

	ESCAPE_FROM_BEAST("ESCAPE_FROM_BEAST", XMaterial.SKELETON_SKULL.parseMaterial(), "getNameEscapeBeast"),

	RACE("RACE", XMaterial.IRON_BOOTS.parseMaterial(), "getNameRace"),

	TNT_RUN("TNTRUN", XMaterial.TNT.parseMaterial(), "getNameTNTRun"),

	SPLEEF("SPLEEF", XMaterial.IRON_SHOVEL.parseMaterial(), "getNameSpleef"),

	SPLEGG("SPLEGG", XMaterial.EGG.parseMaterial(), "getNameSplegg"),

	OITC("OITC", XMaterial.BOW.parseMaterial(), "getNameOITC"),

	SG("SG", XMaterial.BEEF.parseMaterial(), "getNameSurvivalGames"),

	TSG("TSG", XMaterial.COOKED_BEEF.parseMaterial(), "getNameTeamSurvivalGames"),

	TSG_REAL("TSGTEAMS", XMaterial.COOKED_PORKCHOP.parseMaterial(), "getNameRealTeamSurvivalGames"),

	SW("SW", XMaterial.DIRT.parseMaterial(), "getNameSkywars"),

	TSW("TSW", XMaterial.STONE.parseMaterial(), "getNameTeamSkywars"),

	TSW_REAL("TSWTEAMS", XMaterial.BEDROCK.parseMaterial(), "getNameRealTeamSkywars"),

	WDROP("WDROP", XMaterial.WATER_BUCKET.parseMaterial(), "getNameWaterDrop"),

	QUAKECRAFT("QUAKE", XMaterial.STONE_HOE.parseMaterial(), "getNameQuakeCraft"),

	PAINTBALL("PBALL", XMaterial.SNOWBALL.parseMaterial(), "getNamePaintball"),
	
	PAINTBALL_TOP_KILL("PBALLTK", XMaterial.SNOW.parseMaterial(), "getNamePaintballTopKill"),

	KOTH("KOTH", XMaterial.GOLDEN_HELMET.parseMaterial(), "getNameKingHill"),

	FISH_SLAP("FISHSLAP", XMaterial.TROPICAL_FISH.parseMaterial(), "getNameFishSlap"),

	HOEHOEHOE("HOE", XMaterial.WOODEN_HOE.parseMaterial(), "getNameHoehoehoe"),

	SPLATOON("SPLATOON", XMaterial.RED_DYE.parseMaterial(), "getNameSplatoon"),

	BOMBARDMENT("BOMBARDMENT", XMaterial.COAL_BLOCK.parseMaterial(), "getNameBombardment");

	private String codigo;
	private Material material;
	private String message;

	private MinigameType(String codigo, Material material, String message) {
		this.codigo = codigo;
		this.material = material;
		this.message = message;
	}

	public static MinigameType getByCodigo(String codigo) {
		MinigameType creation = null;
		for (MinigameType w : MinigameType.values()) {
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

	public String getMessage(RandomEvents plugin) {
		String menu = "";
		try {
			Method method = plugin.getLanguage().getClass().getDeclaredMethod(message);
			menu += method.invoke(plugin.getLanguage());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
