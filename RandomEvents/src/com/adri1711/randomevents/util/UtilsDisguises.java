package com.adri1711.randomevents.util;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;

public class UtilsDisguises {

	public static void disguisePlayer(Player p, RandomEvents plugin) {
		if (plugin.getIsLibsDisguises() && plugin.isRandomDisguisePlayers()) {
			me.libraryaddict.disguise.disguisetypes.PlayerDisguise playerDisguise = new me.libraryaddict.disguise.disguisetypes.PlayerDisguise(
					plugin.getSkinDisguisePlayers());
			playerDisguise.setEntity(p);
			playerDisguise.setHearSelfDisguise(false);
			playerDisguise.setSelfDisguiseVisible(false);
			playerDisguise.startDisguise();
		}

	}

	public static void undisguisePlayer(Player p, RandomEvents plugin) {
		if (plugin.getIsLibsDisguises() && plugin.isRandomDisguisePlayers()) {

			me.libraryaddict.disguise.disguisetypes.Disguise disg = me.libraryaddict.disguise.DisguiseAPI
					.getDisguise(p);
			if (disg != null && disg instanceof me.libraryaddict.disguise.disguisetypes.PlayerDisguise) {
				me.libraryaddict.disguise.disguisetypes.PlayerDisguise playerDisguise = (me.libraryaddict.disguise.disguisetypes.PlayerDisguise) disg;
				playerDisguise.stopDisguise();
			}

		}
	}
}
