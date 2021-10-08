package com.adri1711.randomevents.util;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.adri1711.randomevents.RandomEvents;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public class UtilsCitizen {

	public static Boolean check = true;

	public static boolean compruebaCitizensId(Integer number, RandomEvents plugin) {
		if (!plugin.getReventConfig().getIsCitizens())
			return false;
		NPC npc = CitizensAPI.getNPCRegistry().getById(number);
		return npc != null;
	}

	public static void turnAroundNPC(Integer number, RandomEvents plugin) {
		if (!plugin.getReventConfig().getIsCitizens() || plugin.getMatchActive() == null
				|| !plugin.getMatchActive().getPlaying())
			return;
		NPC npc = CitizensAPI.getNPCRegistry().getById(number);
		if (npc != null) {
			Location loc = npc.getEntity().getLocation().clone();

			check = !check;
			Location livingLoc = ((LivingEntity) npc.getEntity()).getEyeLocation().multiply(check ? -0.2 : 0.2);
			Location res = loc.subtract(livingLoc);

			res.setPitch(0.0F);
			res.setY(npc.getEntity().getLocation().getY());
			npc.faceLocation(res);

		}
	}

	public static void forceLastTurnAroundNPC(Integer number, RandomEvents plugin) {
		if (!check) {
			if (!plugin.getReventConfig().getIsCitizens())
				return;
			NPC npc = CitizensAPI.getNPCRegistry().getById(number);
			if (npc != null) {
				Location loc = npc.getEntity().getLocation().clone();

				check = !check;
				Location livingLoc = ((LivingEntity) npc.getEntity()).getEyeLocation().multiply(check ? -0.2 : 0.2);
				Location res = loc.subtract(livingLoc);

				res.setPitch(0.0F);
				res.setY(npc.getEntity().getLocation().getY());
				npc.faceLocation(res);

			}
		}
	}

}
