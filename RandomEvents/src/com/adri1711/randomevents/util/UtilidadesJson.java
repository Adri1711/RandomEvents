package com.adri1711.randomevents.util;

import java.io.BufferedReader;

import org.bukkit.inventory.ItemStack;

import com.adri1711.randomevents.json.GsonFactory;
import com.adri1711.randomevents.match.Match;

public class UtilidadesJson {

	public static String fromMatchToJSON(Match match) {
		String resultado = null;

		try {
			resultado = GsonFactory.getPrettyGson().toJson(match);

			// JsonSerializerDeserializer<BossMatch> jsonSerializer = new
			// JsonSerializerDeserializer<BossMatch>();
			//
			// resultado = jsonSerializer.serialize(match);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at creating match");
		}
		return resultado;

	}

	
	public static Match fromJSONToMatch(BufferedReader br) {
		Match match = null;
		try {
			match = GsonFactory.getPrettyGson().fromJson(br, Match.class);
			UtilsRandomEvents.normalizaColorsMatch(match);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading match: " + br);
		}
		return match;
	}
	
	
	public static String fromInventoryToJSON(ItemStack[] inventory) {
		String resultado = null;

		try {
			resultado = GsonFactory.getPrettyGson().toJson(inventory);

			// JsonSerializerDeserializer<BossMatch> jsonSerializer = new
			// JsonSerializerDeserializer<BossMatch>();
			//
			// resultado = jsonSerializer.serialize(match);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at creating inventory");
		}
		return resultado;

	}

	
	public static ItemStack[] fromJSONToInventory(BufferedReader br) {
		ItemStack[] inventory = null;
		try {
			inventory = GsonFactory.getPrettyGson().fromJson(br, ItemStack[].class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading inventory: " + br);
		}
		return inventory;
	}

//	public static String fromBossStatsToJSON(BossBattleStats stats) {
//		String resultado = null;
//
//		try {
//			resultado = GsonFactory.getPrettyGson().toJson(stats);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println("Error :: Error at creating stats");
//		}
//		return resultado;
//
//	}

//	public static BossBattleStats fromJSONToBossStats(String stats) {
//		BossBattleStats bossMatch = null;
//		try {
//			bossMatch = GsonFactory.getPrettyGson().fromJson(stats, BossBattleStats.class);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println("Error :: Error at loading match: " + stats);
//		}
//		return bossMatch;
//
//	}

//	public static BossBattleStats fromJSONToBossStats(BufferedReader br) {
//		BossBattleStats bossMatch = null;
//		try {
//			bossMatch = GsonFactory.getPrettyGson().fromJson(br, BossBattleStats.class);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println("Error :: Error at loading match: " + br);
//		}
//		return bossMatch;
//	}

	

}
