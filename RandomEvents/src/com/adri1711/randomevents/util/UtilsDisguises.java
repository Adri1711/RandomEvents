package com.adri1711.randomevents.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;

public class UtilsDisguises {

	public static void disguisePlayer(Player p, RandomEvents plugin) {
		if (plugin.getReventConfig().getIsLibsDisguises() && plugin.getReventConfig().isRandomDisguisePlayers()) {
			me.libraryaddict.disguise.disguisetypes.PlayerDisguise playerDisguise = new me.libraryaddict.disguise.disguisetypes.PlayerDisguise(
					plugin.getReventConfig().getSkinDisguisePlayers());

			playerDisguise.setEntity(p);
			playerDisguise.setHearSelfDisguise(false);
			playerDisguise.setSelfDisguiseVisible(false);
			playerDisguise.startDisguise();
		}

	}

	public static void undisguisePlayer(Player p, RandomEvents plugin) {
		if (plugin.getReventConfig().getIsLibsDisguises() && plugin.getReventConfig().isRandomDisguisePlayers()) {

			me.libraryaddict.disguise.disguisetypes.Disguise disg = me.libraryaddict.disguise.DisguiseAPI
					.getDisguise(p);
			if (disg != null && disg instanceof me.libraryaddict.disguise.disguisetypes.PlayerDisguise) {
				me.libraryaddict.disguise.disguisetypes.PlayerDisguise playerDisguise = (me.libraryaddict.disguise.disguisetypes.PlayerDisguise) disg;
				playerDisguise.stopDisguise();
			}

		}
	}

	public static void addGlow(Player p, RandomEvents plugin) {
		//DISGUISE
//		me.libraryaddict.disguise.disguisetypes.PlayerDisguise playerDisguise = null;
//
//		me.libraryaddict.disguise.disguisetypes.Disguise disg = me.libraryaddict.disguise.DisguiseAPI.getDisguise(p);
//
//		if (plugin.getReventConfig().getIsLibsDisguises()) {
//			if (disg != null && disg instanceof me.libraryaddict.disguise.disguisetypes.PlayerDisguise) {
//				playerDisguise = (me.libraryaddict.disguise.disguisetypes.PlayerDisguise) disg;
//				playerDisguise.stopDisguise();
//
//			} else {
//				playerDisguise = new me.libraryaddict.disguise.disguisetypes.PlayerDisguise(p.getName());
//			}
//			if (playerDisguise.getWatcher() != null) {
//				playerDisguise.getWatcher().setGlowing(true);
//			}
//			playerDisguise.setEntity(p);
//			playerDisguise.setHearSelfDisguise(false);
//			playerDisguise.setSelfDisguiseVisible(false);
//			playerDisguise.startDisguise();
//			
//			
//		}
		//PRIMITIVE
		PotionEffectType glow= PotionEffectType.getByName("GLOWING");
		p.addPotionEffect(new PotionEffect(glow, 3000, 0));

	}

	public static void removeGlow(Player p, RandomEvents plugin) {
		
		// if (plugin.getReventConfig().getIsLibsDisguises()) {
		//
		// me.libraryaddict.disguise.disguisetypes.Disguise disg =
		// me.libraryaddict.disguise.DisguiseAPI
		// .getDisguise(p);
		// if (disg != null && disg instanceof
		// me.libraryaddict.disguise.disguisetypes.PlayerDisguise) {
		// me.libraryaddict.disguise.disguisetypes.PlayerDisguise playerDisguise
		// = (me.libraryaddict.disguise.disguisetypes.PlayerDisguise) disg;
		// if (playerDisguise.getWatcher() != null) {
		// playerDisguise.getWatcher().setGlowing(false);
		// }
		// disguisePlayer(p,plugin);
		// }
		//
		// }
		PotionEffectType glow= PotionEffectType.getByName("GLOWING");

		if (p.hasPotionEffect(glow)) {
			p.removePotionEffect(glow);
		}
	}
}
