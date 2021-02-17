package com.adri1711.randomevents.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
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
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XSound;

public class Use implements Listener {

	private RandomEvents plugin;

	public Use(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();

		// if (plugin.getMatchActive() != null
		// &&
		// plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)
		// && plugin.getMatchActive().getPlaying()
		// && (evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() ==
		// Action.LEFT_CLICK_BLOCK)
		// &&
		// plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF))
		// {
		//
		// evt.setCancelled(true);
		//
		// } else {

		if (plugin.getMatchActive() != null && plugin.getMatchActive().getPlaying()
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {

			if ((evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.LEFT_CLICK_BLOCK)
					&& evt.getClickedBlock().getType() == XMaterial.CHEST.parseMaterial()) {
				if (plugin.getMatchActive().getMapHandler().getCuboid() != null && plugin.getMatchActive()
						.getMapHandler().getCuboid().contains(evt.getClickedBlock().getLocation())) {
					if (!plugin.getMatchActive().getMapHandler().getChests()
							.contains(evt.getClickedBlock().getLocation())
							&& !plugin.getMatchActive().getMapHandler().getBlockPlaced().keySet()
									.contains(evt.getClickedBlock().getLocation())) {
						if (evt.getClickedBlock().getState() instanceof Chest) {
							plugin.getMatchActive().getMapHandler().getChests()
									.add(evt.getClickedBlock().getLocation());
							Chest chest = (Chest) evt.getClickedBlock().getState();
							chest.getBlockInventory().clear();
							Integer objetos = 0;
							if (plugin.getMatchActive().getMatch().getInventoryChests() != null
									&& plugin.getMatchActive().getMatch().getInventoryChests().getContents() != null
									&& plugin.getMatchActive().getMatch().getInventoryChests()
											.getContents().length != 0) {
								objetos = Math.max(plugin.getMinItemOnChests(), plugin.getRandom().nextInt(Math.min(
										plugin.getMaxItemOnChests(),
										plugin.getMatchActive().getMatch().getInventoryChests().getContents().length)));
							}
							for (int i = 0; i < objetos; i++) {
								chest.getBlockInventory()
										.addItem(plugin.getMatchActive().getMatch().getInventoryChests()
												.getContents()[plugin.getRandom().nextInt(plugin.getMatchActive()
														.getMatch().getInventoryChests().getContents().length)]);
							}
						}
					}
				}
			} else if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if (player.getItemInHand() != null
						&& player.getItemInHand().getType() != XMaterial.AIR.parseMaterial()) {
					if (player.getItemInHand().getItemMeta() != null
							&& player.getItemInHand().getItemMeta().getDisplayName() != null
							&& player.getItemInHand().getItemMeta().getDisplayName()
									.equals(plugin.getPowerUpItem().getItemMeta().getDisplayName())) {

						if (player.getItemInHand().getAmount() > 1) {
							player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
							player.updateInventory();

						} else {
							player.getInventory().remove(player.getItemInHand());
							player.updateInventory();
						}
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 5));
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + plugin.getLanguage().getNowProtected());
					} else if (player.getItemInHand().equals(plugin.getCheckpointItem())) {
						evt.setCancelled(true);

						UtilsRandomEvents.teleportaPlayer(player,
								plugin.getMatchActive().getMapHandler().getCheckpoints().get(player.getName()), plugin);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 99));

					} else if (player.getItemInHand().equals(plugin.getEndVanishItem())) {
						evt.setCancelled(true);
						UtilsRandomEvents.showPlayers(player,
								plugin.getMatchActive().getPlayerHandler().getPlayersObj(), plugin);
						UtilsRandomEvents.playSound(player, XSound.ENTITY_PLAYER_LEVELUP);

					} else if (player.getItemInHand().equals(plugin.getVanishItem())) {
						evt.setCancelled(true);
						UtilsRandomEvents.hidePlayers(player,
								plugin.getMatchActive().getPlayerHandler().getPlayersObj(), plugin);
						UtilsRandomEvents.playSound(player, XSound.ENTITY_PLAYER_LEVELUP);

					} else if (player.getItemInHand().getType() == (XMaterial.STONE_HOE.parseMaterial())
							&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
						player.launchProjectile(Egg.class);

					} else if ((player.getItemInHand().getType() == (XMaterial.WOODEN_SHOVEL.parseMaterial())
							|| player.getItemInHand().getType() == (XMaterial.STONE_SHOVEL.parseMaterial())
							|| player.getItemInHand().getType() == (XMaterial.IRON_SHOVEL.parseMaterial())
							|| player.getItemInHand().getType() == (XMaterial.GOLDEN_SHOVEL.parseMaterial())
							|| player.getItemInHand().getType() == (XMaterial.DIAMOND_SHOVEL.parseMaterial()))
							&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
						player.launchProjectile(Egg.class);
					}
				}

			} else if (evt.getAction().equals(Action.PHYSICAL)) {
				if (evt.getClickedBlock() != null
						&& evt.getClickedBlock().getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
					plugin.getMatchActive().getMapHandler().getCheckpoints().put(player.getName(),
							player.getLocation());

				}
			}
		}
		// }
	}

	@EventHandler
	public void onPlayerEggThrow(PlayerEggThrowEvent evt) {
		Player player = evt.getPlayer();

		if (plugin.getMatchActive() != null
				&& (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)
						|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF))
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
			evt.getEgg().setCustomName(Constantes.SPLEGG_EGG);

		}
	}

	@EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent event) {
		Projectile entity = event.getEntity();

		if (entity != null && entity.getShooter() != null && entity.getShooter() instanceof Player
				&& (plugin.getMatchActive() != null
						&& (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)
								|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)))) {

			Player p = (Player) entity.getShooter();

			if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())) {

				Location hitLoc = entity.getLocation();

				Vector arrowVector = entity.getVelocity();
				BlockIterator b = new BlockIterator(hitLoc.getWorld(), hitLoc.toVector(), arrowVector, 0.0D, 3);

				Block hitBlock = event.getEntity().getLocation().getBlock();

				Block blockBefore = hitBlock;
				Block nextBlock = b.next();

				while (b.hasNext() && nextBlock.getType() == XMaterial.AIR.parseMaterial()) {
					blockBefore = nextBlock;
					nextBlock = b.next();
				}

				BlockFace blockFace = nextBlock.getFace(blockBefore);

				if (blockFace != null) {
					if (nextBlock.getType() != null && plugin.getMatchActive().getMatch().getMaterial() != null
							&& nextBlock.getType().toString()
									.equals(plugin.getMatchActive().getMatch().getMaterial())) {
						plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(nextBlock.getLocation(),
								nextBlock.getState().getData().clone());
						nextBlock.setType(XMaterial.AIR.parseMaterial());

					} else if (plugin.getMatchActive().getMatch().getDatas() != null
							&& !plugin.getMatchActive().getMatch().getDatas().isEmpty() && nextBlock.getType() != null
							&& nextBlock.getState().getData() != null && UtilsRandomEvents.contieneMaterialData(
									nextBlock.getState().getData(), plugin.getMatchActive().getMatch())) {
						plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(nextBlock.getLocation(),
								nextBlock.getState().getData().clone());
						nextBlock.setType(XMaterial.AIR.parseMaterial());
					}
				}
			}
		} else if (entity instanceof Arrow) {
			Arrow arrow = (Arrow) entity;
			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				Player p = (Player) arrow.getShooter();
				if (plugin.getMatchActive() != null
						&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())) {
					arrow.remove();
				}
			}
		}
	}

	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				switch (plugin.getMatchActive().getMatch().getMinigame()) {
				case SG:
				case SW:
				case TSG:
				case TSW:
					break;
				default:
					evt.setCancelled(true);
					break;
				}
			}
		}

	}

	@EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			if (plugin.getMatchActive() != null
					&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
				if (evt.getRegainReason() == RegainReason.SATIATED) {
					switch (plugin.getMatchActive().getMatch().getMinigame()) {
					case SG:
					case SW:
					case TSG:
					case TSW:
						break;
					default:
						evt.setCancelled(true);
						break;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMine(BlockBreakEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)
				&& plugin.getMatchActive().getPlaying()) {
			if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF)) {
				evt.setCancelled(true);
			} else {

				if (plugin.getMatchActive().getMapHandler().getBlockPlaced()
						.containsKey(evt.getBlock().getLocation())) {
					evt.setCancelled(false);
					plugin.getMatchActive().getMapHandler().getBlockPlaced().remove(evt.getBlock().getLocation());
				} else if (plugin.getMatchActive().getMatch().getMaterial() != null

						&& evt.getBlock().getType() != null && evt.getBlock().getType().toString()
								.equals(plugin.getMatchActive().getMatch().getMaterial())) {
					evt.setCancelled(true);
					plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(evt.getBlock().getLocation(),
							evt.getBlock().getState().getData().clone());

					if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
						evt.getBlock().setType(XMaterial.AIR.parseMaterial());

					} else {
						evt.getBlock().breakNaturally();
					}
				} else if (plugin.getMatchActive().getMatch().getDatas() != null
						&& !plugin.getMatchActive().getMatch().getDatas().isEmpty() && evt.getBlock().getType() != null
						&& evt.getBlock().getState().getData() != null && UtilsRandomEvents.contieneMaterialData(
								evt.getBlock().getState().getData(), plugin.getMatchActive().getMatch())) {
					evt.setCancelled(true);
					plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(evt.getBlock().getLocation(),
							evt.getBlock().getState().getData().clone());
					if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
						evt.getBlock().setType(XMaterial.AIR.parseMaterial());

					} else {
						evt.getBlock().breakNaturally();
					}
				} else {
					evt.setCancelled(true);

				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlace(BlockPlaceEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())
				&& plugin.getMatchActive().getPlaying()) {
			if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
				evt.setCancelled(true);
			} else {
				if (plugin.getMatchActive().getMatch().getMaterial() != null

						&& evt.getBlock().getType() != null && evt.getBlock().getType().toString()
								.equals(plugin.getMatchActive().getMatch().getMaterial())) {
					// evt.setCancelled(true);
					evt.setCancelled(false);
					plugin.getMatchActive().getMapHandler().getBlockPlaced().put(evt.getBlock().getLocation(),
							evt.getBlock().getState().getData().clone());
					// evt.getBlock().setType(XMaterial.AIR.parseMaterial());
				} else if (plugin.getMatchActive().getMatch().getDatas() != null
						&& !plugin.getMatchActive().getMatch().getDatas().isEmpty() && evt.getBlock().getType() != null
						&& evt.getBlock().getState().getData() != null && UtilsRandomEvents.contieneMaterialData(
								evt.getBlock().getState().getData(), plugin.getMatchActive().getMatch())) {
					// evt.setCancelled(true);
					evt.setCancelled(false);
					plugin.getMatchActive().getMapHandler().getBlockPlaced().put(evt.getBlock().getLocation(),
							evt.getBlock().getState().getData().clone());
					// evt.getBlock().setType(XMaterial.AIR.parseMaterial());
				} else {
					evt.setCancelled(true);

				}
			}
		}
	}

	@EventHandler
	public void onAnvilFall(EntityChangeBlockEvent event) {
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF)
				&& plugin.getMatchActive().getMapHandler().getCuboid() != null
				&& plugin.getMatchActive().getMapHandler().getCuboid().contains(event.getBlock().getLocation())) {
			if (event.getEntityType().equals(EntityType.FALLING_BLOCK)) {
				FallingBlock fb = (FallingBlock) event.getEntity();
				if (fb.getMaterial().equals(XMaterial.ANVIL.parseMaterial())
						|| fb.getMaterial().equals(XMaterial.CHIPPED_ANVIL.parseMaterial())
						|| fb.getMaterial().equals(XMaterial.DAMAGED_ANVIL.parseMaterial())) {
					if (event.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {
						Location l = event.getBlock().getLocation();
						Location l2 = l.clone();
						l2.setY(l2.getY() - 1);
						if (!l2.getBlock().getType().equals(XMaterial.AIR.parseMaterial())) {
							fb.getWorld().playSound(fb.getLocation(), XSound.BLOCK_ANVIL_BREAK.parseSound(), 1, 1);
							fb.remove();
							event.getBlock().setType(XMaterial.AIR.parseMaterial());
							event.setCancelled(true);

							if (plugin.getMatchActive().getMatch().getDatas() != null
									&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
									&& l2.getBlock().getType() != null && l2.getBlock().getState().getData() != null
									&& (UtilsRandomEvents.contieneMaterialData(l2.getBlock().getState().getData(),
											plugin.getMatchActive().getMatch())
											|| l2.getBlock().getType().equals(XMaterial.ANVIL.parseMaterial())
											|| l2.getBlock().getType().equals(XMaterial.CHIPPED_ANVIL.parseMaterial())
											|| l2.getBlock().getType()
													.equals(XMaterial.DAMAGED_ANVIL.parseMaterial()))) {
								plugin.getMatchActive().getMapHandler().getBlockDisappeared()
										.put(l2.getBlock().getLocation(), l2.getBlock().getState().getData().clone());
								l2.getBlock().setType(XMaterial.AIR.parseMaterial());

							}
						}
					}
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
