package com.adri1711.randomevents.match.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.util.enums.XMaterial;

public enum MinigameType {
	BATTLE_ROYALE("BR", XMaterial.STONE_SWORD.parseMaterial(), "Battle Royale"),

	BATTLE_ROYALE_TEAM_2("BRT2", XMaterial.STONE_SWORD.parseMaterial(), "Team Battle Royale"),

	BATTLE_ROYALE_CABALLO("LJ", XMaterial.IRON_HORSE_ARMOR.parseMaterial(), "Knights Battle"),

	TOP_KILLER("TKLL", XMaterial.IRON_SWORD.parseMaterial(), "Top Killer"),

	TOP_KILLER_TEAM_2("TKLLT2", XMaterial.IRON_SWORD.parseMaterial(), "Team Top Killer"),

	KNOCKBACK_DUEL("KBD", XMaterial.WOODEN_SWORD.parseMaterial(), "Knockback Duel"),

	ESCAPE_ARROW("EARR", XMaterial.ARROW.parseMaterial(), "Arrow Rain"),

	ANVIL_SPLEEF("ANVIL_SPLEEF", XMaterial.ANVIL.parseMaterial(), "Anvil Spleef"),

	GEM_CRAWLER("GEMC", XMaterial.EMERALD.parseMaterial(), "Gem Carrier"),

	BOMB_TAG("BOMB", XMaterial.TNT.parseMaterial(), "TNT Tag"),

	BOAT_RUN("BOAT_RUN", XMaterial.OAK_BOAT.parseMaterial(), "Boat Race"),

	HORSE_RUN("HORSE_RUN", XMaterial.IRON_HORSE_ARMOR.parseMaterial(), "Horse Race"),

	ESCAPE_FROM_BEAST("ESCAPE_FROM_BEAST", XMaterial.SKELETON_SKULL.parseMaterial(), "Escape from the beast"),

	RACE("RACE", XMaterial.IRON_BOOTS.parseMaterial(), "Race"),

	TNT_RUN("TNTRUN", XMaterial.TNT.parseMaterial(), "TNT Run"),

	SPLEEF("SPLEEF", XMaterial.IRON_SHOVEL.parseMaterial(), "Spleef"),

	SPLEGG("SPLEGG", XMaterial.EGG.parseMaterial(), "Splegg"),

	OITC("OITC", XMaterial.BOW.parseMaterial(), "One in the chamber"),

	SG("SG", XMaterial.BEEF.parseMaterial(), "Survival Games"),

	TSG("TSG", XMaterial.COOKED_BEEF.parseMaterial(), "Team Survival Games"),

	SW("SW", XMaterial.DIRT.parseMaterial(), "Skywars"),

	TSW("TSW", XMaterial.STONE.parseMaterial(), "Team Skywars"),

	WDROP("WDROP", XMaterial.WATER_BUCKET.parseMaterial(), "Water Drop"),

	QUAKECRAFT("QUAKE", XMaterial.STONE_HOE.parseMaterial(), "QuakeCraft"),

	PAINTBALL("PBALL", XMaterial.SNOWBALL.parseMaterial(), "Paintball"),

	KOTH("KOTH", XMaterial.GOLDEN_HELMET.parseMaterial(), "King of the Hill"),

	FISH_SLAP("FISHSLAP", XMaterial.TROPICAL_FISH.parseMaterial(), "Fish slap"),

	HOEHOEHOE("HOE", XMaterial.WOODEN_HOE.parseMaterial(), "Hoe hoe hoe"),

	SPLATOON("SPLATOON", XMaterial.RED_DYE.parseMaterial(), "Splatoon"),

	BOMBARDMENT("BOMBARDMENT", XMaterial.COAL_BLOCK.parseMaterial(), "Bombardment");

	

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

	public String getMessage() {
		return message;
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
