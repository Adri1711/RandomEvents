package com.adri1711.randomevents.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.MinigameType;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.AMaterials;

public class Use implements Listener {

	private RandomEvents plugin;

	public Use(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {

			if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if (player.getItemInHand() != null
						&& player.getItemInHand().getType() != plugin.getApi().getMaterial(AMaterials.AIR)) {
					if (player.getItemInHand().equals(plugin.getPowerUpItem())) {
						if (player.getItemInHand().getAmount() > 1) {
							player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							player.updateInventory();

						} else {
							player.getInventory().remove(player.getItemInHand());
							player.updateInventory();
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 5));
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNowProtected());
					} else if (player.getItemInHand().getType() == plugin.getApi().getMaterial(AMaterials.STONE_HOE)
							&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
						player.launchProjectile(Egg.class);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerEggThrow(PlayerEggThrowEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)
				&& plugin.getMatchActive().getPlayers().contains(player.getName())) {
			evt.getEgg().setCustomName(Constantes.SPLEGG_EGG);
		}
	}

	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent event) {
		Projectile entity = event.getEntity();

		if (entity instanceof Egg && entity.getCustomName() != null
				&& entity.getCustomName().equals(Constantes.SPLEGG_EGG)) {
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
				Location hitLoc = entity.getLocation();

				Vector arrowVector = entity.getVelocity();
				BlockIterator b = new BlockIterator(hitLoc.getWorld(), hitLoc.toVector(), arrowVector, 0.0D, 3);

				Block hitBlock = event.getEntity().getLocation().getBlock();

				Block blockBefore = hitBlock;
				Block nextBlock = b.next();

				while (b.hasNext() && nextBlock.getType() == plugin.getApi().getMaterial(AMaterials.AIR)) {
					blockBefore = nextBlock;
					nextBlock = b.next();
				}

				BlockFace blockFace = nextBlock.getFace(blockBefore);

				if (blockFace != null) {
					if (nextBlock.getType() != null && plugin.getMatchActive().getMatch().getMaterial() != null
							&& nextBlock.getType().toString()
									.equals(plugin.getMatchActive().getMatch().getMaterial())) {
						plugin.getMatchActive().getBlockDisappeared().put(nextBlock.getLocation(),
								nextBlock.getState().getData().clone());
						nextBlock.setType(plugin.getApi().getMaterial(AMaterials.AIR));

					} else if (plugin.getMatchActive().getMatch().getDatas() != null
							&& !plugin.getMatchActive().getMatch().getDatas().isEmpty() && nextBlock.getType() != null
							&& nextBlock.getState().getData() != null && UtilsRandomEvents.contieneMaterialData(
									nextBlock.getState().getData(), plugin.getMatchActive().getMatch())) {
						plugin.getMatchActive().getBlockDisappeared().put(nextBlock.getLocation(),
								nextBlock.getState().getData().clone());
						nextBlock.setType(plugin.getApi().getMaterial(AMaterials.AIR));
					}
				}
			}
		}
	}

	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				evt.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())) {
				if (evt.getRegainReason() == RegainReason.SATIATED) {
					evt.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMine(BlockBreakEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlayers().contains(player.getName())
				&& plugin.getMatchActive().getPlaying()) {
			if (plugin.getMatchActive().getMatch().getMaterial() != null

					&& evt.getBlock().getType() != null
					&& evt.getBlock().getType().toString().equals(plugin.getMatchActive().getMatch().getMaterial())) {
				evt.setCancelled(true);
				plugin.getMatchActive().getBlockDisappeared().put(evt.getBlock().getLocation(),
						evt.getBlock().getState().getData().clone());
				evt.getBlock().setType(plugin.getApi().getMaterial(AMaterials.AIR));
			} else if (plugin.getMatchActive().getMatch().getDatas() != null
					&& !plugin.getMatchActive().getMatch().getDatas().isEmpty() && evt.getBlock().getType() != null
					&& evt.getBlock().getState().getData() != null && UtilsRandomEvents.contieneMaterialData(
							evt.getBlock().getState().getData(), plugin.getMatchActive().getMatch())) {
				evt.setCancelled(true);
				plugin.getMatchActive().getBlockDisappeared().put(evt.getBlock().getLocation(),
						evt.getBlock().getState().getData().clone());
				evt.getBlock().setType(plugin.getApi().getMaterial(AMaterials.AIR));
			} else {
				evt.setCancelled(true);

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
