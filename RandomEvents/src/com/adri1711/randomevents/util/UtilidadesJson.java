package com.adri1711.randomevents.util;

import java.io.BufferedReader;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.WaterDropStep;
import com.adri1711.randomevents.match.schedule.Schedule;
import com.adri1711.randomevents.match.utils.BannedPlayers;
import com.adri1711.randomevents.match.utils.InventoryPers;

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
	
	public static String fromWDToJSON(RandomEvents plugin, WaterDropStep match) {
		String resultado = null;

		try {
			resultado = plugin.getApi().toJson(match);

			// JsonSerializerDeserializer<BossMatch> jsonSerializer = new
			// JsonSerializerDeserializer<BossMatch>();
			//
			// resultado = jsonSerializer.serialize(match);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at creating waterDrop");
		}
		return resultado;

	}

	public static WaterDropStep fromJSONToWD(RandomEvents plugin, BufferedReader br) {
		WaterDropStep match = null;
		try {
			match = (WaterDropStep) plugin.getApi().fromJson(br, WaterDropStep.class);
			UtilsRandomEvents.normalizaColorsWaterDrop(match);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading waterDrop: " + br);
		}
		return match;
	}

	public static String fromScheduleToJSON(RandomEvents plugin, Schedule match) {
		String resultado = null;

		try {
			resultado = plugin.getApi().toJson(match);

			// JsonSerializerDeserializer<BossMatch> jsonSerializer = new
			// JsonSerializerDeserializer<BossMatch>();
			//
			// resultado = jsonSerializer.serialize(match);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at creating schedule");
		}
		return resultado;

	}

	public static Schedule fromJSONToSchedule(RandomEvents plugin, BufferedReader br) {
		Schedule match = null;
		try {
			match = (Schedule) plugin.getApi().fromJson(br, Schedule.class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading schedule: " + br);
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
	
	public static String fromBannedToJSON(RandomEvents plugin, BannedPlayers bp) {
		String resultado = null;

		try {
			resultado = plugin.getApi().toJson(bp);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at creating banned players");
		}
		return resultado;

	}

	public static BannedPlayers fromJSONToBanned(RandomEvents plugin, BufferedReader br) {
		BannedPlayers bp = null;
		try {
			bp = (BannedPlayers) plugin.getApi().fromJson(br, BannedPlayers.class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error :: Error at loading banned players: " + br);
		}
		return bp;
	}

}
