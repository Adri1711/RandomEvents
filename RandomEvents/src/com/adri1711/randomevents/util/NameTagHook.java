package com.adri1711.randomevents.util;

import com.adri1711.randomevents.RandomEvents;
import com.nametagedit.plugin.NametagEdit;
import com.nametagedit.plugin.api.NametagAPI;

public class NameTagHook {

	private RandomEvents plugin;

	private NametagAPI api;

	public NameTagHook(RandomEvents plugin) {
		if (plugin.getServer().getPluginManager().getPlugin("NametagEdit") != null) {
			NametagEdit nametagEdit = (NametagEdit) plugin.getServer().getPluginManager().getPlugin("NametagEdit");
			api = new NametagAPI(nametagEdit.getHandler(), nametagEdit.getManager());
		}

	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

	public NametagAPI getApi() {
		return api;
	}

	public void setApi(NametagAPI api) {
		this.api = api;
	}
	
	

}
