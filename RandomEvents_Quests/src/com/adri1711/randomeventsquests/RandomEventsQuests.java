package com.adri1711.randomeventsquests;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomEventsQuests extends JavaPlugin {

	public void onEnable() {

		// if (getServer().getPluginManager().getPlugin("CrackShot") != null) {
		// getServer().getPluginManager().registerEvents((Listener) new
		// WeaponShoot(this), (Plugin) this);
		// System.out.println("[RandomEvents] CrackShot hooked succesfully!");
		//
		// }

		getLogger().info(" Author adri1711- activado");

	}

	public void onDisable() {

		getServer().getScheduler().cancelTasks((Plugin) this);
		getLogger().info(" Author adri1711 - desactivado");
	}

	protected RandomEventsQuests getPlugin() {

		return this;
	}

}
