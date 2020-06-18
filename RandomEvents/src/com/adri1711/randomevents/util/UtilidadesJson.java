package com.adri1711.randomevents.util;

import java.io.BufferedReader;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.json.GsonFactory;
import com.adri1711.randomevents.match.InventoryPers;
import com.adri1711.randomevents.match.Match;

public class UtilidadesJson {

	public static String fromMatchToJSON(RandomEvents plugin, Match match) {
		String resultado = null;

		try {
			resultado = plugin.getApi().toJson(match);

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

	public static Match fromJSONToMatch(RandomEvents plugin, BufferedReader br) {
		Match match = null;
		try {
			match = (Match) plugin.getApi().fromJson(br, Match.class);
			UtilsRandomEvents.normalizaColorsMatch(match);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading match: " + br);
		}
		return match;
	}

	public static String fromInventoryToJSON(RandomEvents plugin, InventoryPers inventory) {
		String resultado = null;

		try {
			resultado = plugin.getApi().toJson(inventory);

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

	public static InventoryPers fromJSONToInventory(RandomEvents plugin, BufferedReader br) {
		InventoryPers inventory = null;
		try {
			inventory = (InventoryPers) plugin.getApi().fromJson(br, InventoryPers.class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading inventory: " + br);
		}
		return inventory;
	}

	// public static String fromBossStatsToJSON(BossBattleStats stats) {
	// String resultado = null;
	//
	// try {
	// resultado = GsonFactory.getPrettyGson().toJson(stats);
	//
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// System.out.println("Error :: Error at creating stats");
	// }
	// return resultado;
	//
	// }

	// public static BossBattleStats fromJSONToBossStats(String stats) {
	// BossBattleStats bossMatch = null;
	// try {
	// bossMatch = GsonFactory.getPrettyGson().fromJson(stats,
	// BossBattleStats.class);
	//
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// System.out.println("Error :: Error at loading match: " + stats);
	// }
	// return bossMatch;
	//
	// }

	// public static BossBattleStats fromJSONToBossStats(BufferedReader br) {
	// BossBattleStats bossMatch = null;
	// try {
	// bossMatch = GsonFactory.getPrettyGson().fromJson(br,
	// BossBattleStats.class);
	//
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// System.out.println("Error :: Error at loading match: " + br);
	// }
	// return bossMatch;
	// }

}
