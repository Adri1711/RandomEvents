package com.adri1711.randomevents.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;

public class Use implements Listener {

	private RandomEvents plugin;

	public Use(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(player.getItemInHand()!=null && player.getItemInHand().getType()!=Material.AIR){
				if(player.getItemInHand().equals(plugin.getPowerUpItem())){
					if(player.getItemInHand().getAmount()>1){
						player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
						player.updateInventory();
						
					}else{
						player.getInventory().remove(player.getItemInHand());
						player.updateInventory();
					}
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60	, 5));
					player.sendMessage(Constantes.TAG_PLUGIN +" "+Constantes.NOW_PROTECTED);
				}
			}
		}
		
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}
	
	
	
	
	
}
