package com.adri1711.randomevents.listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.MatchActive;
import com.adri1711.randomevents.match.enums.MinigameType;
import com.adri1711.randomevents.match.enums.Petos;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.Particle1711;
import com.adri1711.util.enums.ParticleDisplay;
import com.adri1711.util.enums.XMaterial;
import com.adri1711.util.enums.XParticle;
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

			if (plugin.getMatchActive().getAllowMove()) {

				if ((evt.getAction() == Action.LEFT_CLICK_AIR || evt.getAction() == Action.LEFT_CLICK_BLOCK)
						&& (player.getItemInHand().getType() == (XMaterial.WOODEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.STONE_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.IRON_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.GOLDEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.DIAMOND_HOE.parseMaterial()))
						&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.QUAKECRAFT)
						&& !plugin.getMatchActive().getCooldownJump().containsKey(player)) {

					Double time = new Date().getTime() + 1000 * plugin.getReventConfig().getQuakeJumpCooldown();
					plugin.getMatchActive().getCooldownJump().put(player, time.longValue());

					Vector v = player.getEyeLocation().getDirection().normalize();
					v = new Vector(v.getX() * 2.0, v.getY() + 0.5, v.getZ() * 2.0);
					player.setVelocity(v);
					UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_ENDERMAN_TELEPORT);

				} else if ((evt.getAction() == Action.RIGHT_CLICK_BLOCK)
						&& (player.getItemInHand().getType() == (XMaterial.WOODEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.STONE_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.IRON_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.GOLDEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.DIAMOND_HOE.parseMaterial()))
						&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.HOEHOEHOE)) {

					if (plugin.getMatchActive().getMatch().getAllMaterialAllowed()
							|| (plugin.getMatchActive().getMatch().getDatas() != null
									&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
									&& evt.getClickedBlock().getType() != null
									&& evt.getClickedBlock().getState().getData() != null
									&& UtilsRandomEvents.contieneMaterialData(evt.getClickedBlock(),
											plugin.getMatchActive().getMatch()))) {
						Integer equipo = plugin.getMatchActive().getEquipoCopy(player);
						Petos peto = Petos.getPeto(equipo);
						if (peto != null) {
							if (getNearbyWoolTeam(evt.getClickedBlock().getLocation(), peto.getWool().parseMaterial(),
									peto.getDye())
									|| !plugin.getMatchActive().getPlayerHandler().getPaintPlayers().contains(player)) {
								plugin.getMatchActive().getPlayerHandler().getPaintPlayers().add(player);
								plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(
										evt.getClickedBlock().getLocation(),
										evt.getClickedBlock().getState().getData().clone());
								evt.getClickedBlock().setType(peto.getWool().parseMaterial());
								try {
									evt.getClickedBlock().setData(peto.getDye().getWoolData());
								} catch (Throwable e) {

								}
								plugin.getMatchActive().givePoint(player, 1);
							}

						}
					}

				} else if ((evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.RIGHT_CLICK_AIR)
						&& (player.getItemInHand().getType() == (XMaterial.WOODEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.STONE_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.IRON_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.GOLDEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.DIAMOND_HOE.parseMaterial()))
						&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLATOON)) {

					player.launchProjectile(Egg.class);

				} else if ((evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.LEFT_CLICK_BLOCK)
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
									objetos = Math.max(plugin.getReventConfig().getMinItemOnChests(),
											plugin.getRandom()
													.nextInt(Math.min(plugin.getReventConfig().getMaxItemOnChests(),
															plugin.getMatchActive().getMatch().getInventoryChests()
																	.getContents().length)));
								}
								for (int i = 0; i < objetos; i++) {
									Integer position = plugin.getRandom().nextInt(chest.getBlockInventory().getSize());

									chest.getBlockInventory().setItem(position,
											plugin.getMatchActive().getMatch().getInventoryChests().getContents()[plugin
													.getRandom().nextInt(plugin.getMatchActive().getMatch()
															.getInventoryChests().getContents().length)]);
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
										.equals(plugin.getLanguage().getKitItemName())) {
							evt.setCancelled(true);
							player.openInventory(
									UtilsRandomEvents.createGUIKits(player, 0, plugin, plugin.getMatchActive()));
						} else if (player.getItemInHand().getItemMeta() != null
								&& player.getItemInHand().getItemMeta().getDisplayName() != null
								&& player.getItemInHand().getItemMeta().getDisplayName().equals(
										plugin.getReventConfig().getPowerUpItem().getItemMeta().getDisplayName())) {

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
						} else if (player.getItemInHand().equals(plugin.getReventConfig().getCheckpointItem())) {
							evt.setCancelled(true);

							UtilsRandomEvents.teleportaPlayer(player,
									plugin.getMatchActive().getMapHandler().getCheckpoints().get(player.getName()),
									plugin);
							if(plugin.getReventConfig().getRaceSlowEffect())
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 99));

						} else if (player.getItemInHand().equals(plugin.getReventConfig().getEndVanishItem())) {
							evt.setCancelled(true);
							UtilsRandomEvents.showPlayers(player,
									plugin.getMatchActive().getPlayerHandler().getPlayersObj(), plugin);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_PLAYER_LEVELUP);

						} else if (player.getItemInHand().equals(plugin.getReventConfig().getVanishItem())) {
							evt.setCancelled(true);
							UtilsRandomEvents.hidePlayers(player,
									plugin.getMatchActive().getPlayerHandler().getPlayersObj(), plugin);
							UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_PLAYER_LEVELUP);

						} else if (player.getItemInHand().getType() == (XMaterial.STONE_HOE.parseMaterial())
								&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
							player.launchProjectile(Egg.class);

						} else if ((player.getItemInHand().getType() == (XMaterial.WOODEN_SHOVEL.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.STONE_SHOVEL.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.IRON_SHOVEL.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.GOLDEN_SHOVEL.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.DIAMOND_SHOVEL.parseMaterial()))
								&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
							player.launchProjectile(Egg.class);
						} else if ((player.getItemInHand().getType() == (XMaterial.WOODEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.STONE_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.IRON_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.GOLDEN_HOE.parseMaterial())
								|| player.getItemInHand().getType() == (XMaterial.DIAMOND_HOE.parseMaterial()))
								&& plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.QUAKECRAFT)
								&& !plugin.getMatchActive().getCooldownShoot().containsKey(player)) {
							try {
								Double time = new Date().getTime()
										+ 1000 * plugin.getReventConfig().getQuakeShootCooldown();
								plugin.getMatchActive().getCooldownShoot().put(player, time.longValue());
								Boolean hit = Boolean.FALSE;
								UtilsRandomEvents.playSound(plugin, player, XSound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR);
								Location locEnd = player.getLocation();
								ParticleDisplay pa = ParticleDisplay.display(plugin.getApi(), locEnd,
										Particle1711.REDSTONE);
								for (Block loc : player.getLineOfSight((Set<Material>) null,
										plugin.getReventConfig().getQuakeShootDistance())) {
									if (!hit && (loc == null || loc.getType() == XMaterial.AIR.parseMaterial())) {
										Collection<Entity> entities = loc.getLocation().getWorld()
												.getNearbyEntities(loc.getLocation(), 0.5, 0.5, 0.5);

										locEnd = loc.getLocation();

										for (Entity ent : entities) {
											if (ent != null && ent instanceof Player) {
												Player hitted = (Player) ent;
												if (!hitted.getName().equals(player.getName())
														&& plugin.getMatchActive().getPlayerHandler().getPlayersObj()
																.contains(hitted)
														&& hitted.getGameMode() != GameMode.SPECTATOR) {
													hit = Boolean.TRUE;

													Location pLoc = hitted.getLocation();
													Location playerLoc = player.getLocation();
													pLoc.setWorld(playerLoc.getWorld());
													UtilsRandomEvents
															.mandaMensaje(plugin,
																	plugin.getMatchActive().getPlayerHandler()
																			.getPlayersSpectators(),
																	plugin.getLanguage().getBowKill()
																			.replaceAll("%victim%", hitted.getName())
																			.replaceAll("%distance%", "" + Double
																					.valueOf(pLoc.distance(playerLoc))
																					.intValue())
																			.replaceAll("%killer%", player.getName()),
																	false, false, false);
													UtilsRandomEvents.doCommandsKill(player, plugin);
													plugin.getMatchActive().reiniciaPlayer(hitted);
													UtilsRandomEvents.playSound(plugin, hitted,
															XSound.ENTITY_VILLAGER_DEATH);
													UtilsRandomEvents.playSound(plugin, player,
															XSound.ENTITY_PLAYER_LEVELUP);
													if (plugin.getMatchActive().getPuntuacion()
															.containsKey(player.getName())) {
														plugin.getMatchActive().getPuntuacion().put(player.getName(),
																plugin.getMatchActive().getPuntuacion()
																		.get(player.getName()) + 1);

													} else {
														plugin.getMatchActive().getPuntuacion().put(player.getName(),
																1);
													}

													player.sendMessage(plugin.getLanguage().getTagPlugin() + " "
															+ plugin.getLanguage().getNowPoints().replace("%points%",
																	plugin.getMatchActive().getPuntuacion()
																			.get(player.getName()).toString()));

												}
											}
										}
									} else {
										hit = Boolean.TRUE;
									}

								}
								Location loc = player.getLocation();
								loc.setY(loc.getY() + 1);
								XParticle.line(loc, locEnd, 0.5, pa);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				} else if (evt.getAction().equals(Action.PHYSICAL)) {
					if (evt.getClickedBlock() != null
							&& evt.getClickedBlock().getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
						plugin.getMatchActive().getMapHandler().getCheckpoints().put(player.getName(),
								player.getLocation());
						evt.setCancelled(true);

					}
				}
			} else {
				evt.setCancelled(true);
			}
		} else if (plugin.getMatchActive() != null && !plugin.getMatchActive().getPlaying()
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())) {
			if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if (player.getItemInHand() != null
						&& player.getItemInHand().getType() != XMaterial.AIR.parseMaterial()) {
					if (player.getItemInHand().getItemMeta() != null
							&& player.getItemInHand().getItemMeta().getDisplayName() != null && player.getItemInHand()
									.getItemMeta().getDisplayName().equals(plugin.getLanguage().getKitItemName())) {
						evt.setCancelled(true);
						player.openInventory(
								UtilsRandomEvents.createGUIKits(player, 0, plugin, plugin.getMatchActive()));
					} else if (player.getItemInHand().getItemMeta() != null
							&& player.getItemInHand().getItemMeta().getDisplayName() != null && player.getItemInHand()
									.getItemMeta().getDisplayName().equals(plugin.getLanguage().getTeamItemName())) {
						evt.setCancelled(true);
						player.openInventory(
								UtilsRandomEvents.createGUITeams(player, 0, plugin, plugin.getMatchActive()));
					}
				}
			}
		}
		// }
	}

	private boolean getNearbyWoolTeam(Location loc, Material parseMaterial, DyeColor dye) {
		Boolean res = false;
		Location zPlus = loc.clone();
		zPlus.setZ(zPlus.getZ() + 1);
		Location zMinus = loc.clone();
		zMinus.setZ(zMinus.getZ() - 1);

		Location xPlus = loc.clone();
		xPlus.setX(xPlus.getX() + 1);
		Location xMinus = loc.clone();
		xMinus.setX(xMinus.getX() - 1);

		MaterialData data = new MaterialData(parseMaterial);
		try {
			data.setData(dye.getWoolData());
		} catch (Throwable e) {

		}
		MaterialData dataXPlus = new MaterialData((xPlus.getBlock().getType()));
		MaterialData dataZPlus = new MaterialData((zPlus.getBlock().getType()));
		MaterialData dataXMinus = new MaterialData((xMinus.getBlock().getType()));
		MaterialData dataZMinus = new MaterialData((zMinus.getBlock().getType()));

		try {
			dataXPlus.setData(xPlus.getBlock().getData());
			dataZPlus.setData(zPlus.getBlock().getData());
			dataXMinus.setData(xMinus.getBlock().getData());
			dataZMinus.setData(zMinus.getBlock().getData());
		} catch (Throwable e) {

		}

		if ((zPlus.getBlock() != null && zPlus.getBlock().getType() != null && dataZPlus.equals(data))
				|| (zMinus.getBlock() != null && zMinus.getBlock().getType() != null && dataZMinus.equals(data))
				|| (xPlus.getBlock() != null && xPlus.getBlock().getType() != null && dataXPlus.equals(data))
				|| (xMinus.getBlock() != null && xMinus.getBlock().getType() != null && dataXMinus.equals(data))) {
			res = true;
		}
		return res;
	}

	@EventHandler
	public void onPlayerSnowballThrow(ProjectileLaunchEvent evt) {
		if (plugin.getMatchActive() != null) {
			if (evt.getEntity() instanceof Snowball) {
				Snowball snowball = (Snowball) evt.getEntity();
				if (snowball.getShooter() != null && snowball.getShooter() instanceof Player) {
					Player p = (Player) snowball.getShooter();
					if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())) {
						if (plugin.getReventConfig().isInfiniteSnowballs()) {
							p.getInventory().addItem(XMaterial.SNOWBALL.parseItem());
						}
					}
				}
			} else if (evt.getEntity() instanceof EnderPearl) {
				EnderPearl ep = (EnderPearl) evt.getEntity();
				List<Entity> pearls = new ArrayList<Entity>();
				if (ep != null && ep.getShooter() != null) {
					Player p = (Player) ep.getShooter();

					if (plugin.getMatchActive().getPlayerHandler().getPlayers().contains(p.getName())
							&& plugin.getMatchActive().getPlayerHandler().getEnderpearlsFlying().containsKey(p)) {
						pearls = plugin.getMatchActive().getPlayerHandler().getEnderpearlsFlying().get(p);
					}
					if (ep != null && pearls != null) {
						pearls.add(ep);
						plugin.getMatchActive().getPlayerHandler().getEnderpearlsFlying().put(p, pearls);
					}
				}

			}
		}
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

					} else if (plugin.getMatchActive().getMatch().getAllMaterialAllowed()
							|| (plugin.getMatchActive().getMatch().getDatas() != null
									&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
									&& nextBlock.getType() != null && nextBlock.getState().getData() != null
									&& UtilsRandomEvents.contieneMaterialData(nextBlock,
											plugin.getMatchActive().getMatch()))) {
						plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(nextBlock.getLocation(),
								nextBlock.getState().getData().clone());
						nextBlock.setType(XMaterial.AIR.parseMaterial());
					}
				}
			}
		} else if (entity != null && entity.getShooter() != null && entity.getShooter() instanceof Player
				&& (plugin.getMatchActive() != null
						&& (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLATOON)))) {

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

					List<Block> blocks = UtilsRandomEvents.getNearbyBlocks(plugin.getMatchActive().getMatch(),
							nextBlock.getLocation(), plugin.getReventConfig().getSplatoonRadius(),
							plugin.getMatchActive().getMatch().getDatas(), true, plugin);
					List<Location> locations = new ArrayList<Location>();

					List<Location> locTeam = new ArrayList<Location>();
					MatchActive match = plugin.getMatchActive();
					Integer equipo = match.getEquipo(p);
					Set<Player> players = match.getPlayerHandler().getEquipos().get(equipo);
					for (Player pla : players) {
						if (match.getPlayerHandler().getPaintedLocations().containsKey(pla)) {
							locTeam.addAll(match.getPlayerHandler().getPaintedLocations().get(pla));
						}
					}

					for (Block bl : blocks) {
						if (!locTeam.contains(bl.getLocation())) {
							locations.add(bl.getLocation());
						}
					}

					Set<Location> locations2 = new HashSet<Location>();
					if (locations.size() >= plugin.getReventConfig().getSplatoonPaint()) {
						while (locations2.size() < plugin.getReventConfig().getSplatoonPaint()) {
							Integer ra = match.getRandom().nextInt(locations.size());

							locations2.add(locations.get(ra));
						}
					} else {
						locations2.addAll(locations);
					}
					locations.clear();
					locations.addAll(locations2);

					Petos peto = Petos.getPeto(equipo);
					for (Location loc : locations) {
						Boolean addDisappear = Boolean.TRUE;
						if (loc.getBlock().getType().toString().contains("WOOL")) {

							Player painter = null;
							for (Player paint : match.getPlayerHandler().getPaintedLocations().keySet()) {
								if (match.getPlayerHandler().getPaintedLocations().get(paint).contains(loc)) {
									painter = paint;
								}
							}
							if (painter != null) {
								addDisappear = Boolean.FALSE;
								match.givePoint(painter, -1);
								match.getPlayerHandler().getPaintedLocations().get(painter).remove(loc);

							}

						}
						if (peto != null) {
							if (addDisappear) {
								if (!loc.getBlock().getState().getData().getItemType().toString().equals("LEGACY_AIR")
										&& !loc.getBlock().getState().getData().getItemType().toString()
												.equals("AIR")) {
									plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(loc,
											loc.getBlock().getState().getData().clone());
								} else {
									plugin.getMatchActive().getMapHandler().getBlockDisappearedType().put(loc,
											loc.getBlock().getType());
								}
							}
							loc.getBlock().setType(peto.getWool().parseMaterial());
							try {
								loc.getBlock().setData(peto.getDye().getWoolData());
							} catch (Throwable e) {

							}

						}
					}
					match.addPainted(p, locations);
					match.givePoint(p, locations.size());

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
	public void onEntityExplode(EntityExplodeEvent event) {
		if (plugin.getMatchActive() != null) {
			Entity ent = event.getEntity();
			if (plugin.getMatchActive().getMapHandler() != null
					&& plugin.getMatchActive().getMapHandler().getCuboid() != null
					&& plugin.getMatchActive().getMapHandler().getCuboid().contains(ent.getLocation())) {
				List<Block> blockList = new ArrayList<Block>();
				blockList.addAll(event.blockList());
				for (Block block : blockList) {
					if (plugin.getMatchActive().getMapHandler().getBlockPlaced().containsKey(block.getLocation())) {
						plugin.getMatchActive().getMapHandler().getBlockPlaced().remove(block.getLocation());
					} else if (plugin.getMatchActive().getMatch().getMaterial() != null

							&& block.getType() != null
							&& block.getType().toString().equals(plugin.getMatchActive().getMatch().getMaterial())) {
						try {
							plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(block.getLocation(),
									new MaterialData(block.getType(), block.getData()));
						} catch (Throwable eb) {
							plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(block.getLocation(),
									block.getState().getData().clone());
						}

						if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
							event.blockList().remove(block);

						} else {
						}
					} else if (plugin.getMatchActive().getMatch().getAllMaterialAllowed()
							|| (plugin.getMatchActive().getMatch().getDatas() != null
									&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
									&& block.getType() != null && block.getState().getData() != null
									&& UtilsRandomEvents.contieneMaterialData(block,
											plugin.getMatchActive().getMatch()))) {
						try {
							plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(block.getLocation(),
									new MaterialData(block.getType(), block.getData()));
						} catch (Throwable eb) {
							plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(block.getLocation(),
									block.getState().getData().clone());
						}
						if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
							event.blockList().remove(block);
						}
					} else {
						event.blockList().remove(block);

					}

				}
			}
			if (ent instanceof Fireball) {
				Fireball fireball = (Fireball) ent;
				if (plugin.getMatchActive().getMapHandler().getFireballs().contains(fireball)) {
					event.blockList().clear();

				}
			}
		}
	}

	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		if (plugin.getMatchActive() != null) {
			Entity ent = event.getEntity();
			if (ent instanceof Fireball) {
				Fireball fireball = (Fireball) ent;
				if (plugin.getMatchActive().getMapHandler().getFireballs().contains(fireball)) {
					event.setFire(false);
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
				case TSG_REAL:
				case TSW_REAL:
				case BATTLE_ROYALE:
				case BATTLE_ROYALE_TEAM_2:
				case BATTLE_ROYALE_TEAMS:
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
					case TSG_REAL:
					case TSW_REAL:
					case BATTLE_ROYALE:
					case BATTLE_ROYALE_TEAM_2:
					case BATTLE_ROYALE_TEAMS:
					case HIDE_AND_SEEK:
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
				&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)) {
			if (!plugin.getMatchActive().getCanBreak()) {
				evt.setCancelled(true);
			} else {
				if (plugin.getMatchActive().getPlaying()) {
					if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLATOON)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.GLASS_WALK)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.HIDE_AND_SEEK)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ESCAPE_FROM_BEAST)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.RED_GREEN_LIGHT)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.BOAT_RUN)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.HORSE_RUN)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.HOEHOEHOE)
							|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.PAINTBALL)) {
						evt.setCancelled(true);
					} else {

						if (plugin.getMatchActive().getMapHandler().getBlockPlaced()
								.containsKey(evt.getBlock().getLocation())) {
							evt.setCancelled(false);
							plugin.getMatchActive().getMapHandler().getBlockPlaced()
									.remove(evt.getBlock().getLocation());
						} else if (plugin.getMatchActive().getMatch().getMaterial() != null

								&& evt.getBlock().getType() != null && evt.getBlock().getType().toString()
										.equals(plugin.getMatchActive().getMatch().getMaterial())) {
							evt.setCancelled(true);
							try {
								plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(
										evt.getBlock().getLocation(),
										new MaterialData(evt.getBlock().getType(), evt.getBlock().getData()));
							} catch (Throwable eb) {
								plugin.getMatchActive().getMapHandler().getBlockDisappeared()
										.put(evt.getBlock().getLocation(), evt.getBlock().getState().getData().clone());
							}

							if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
								evt.getBlock().setType(XMaterial.AIR.parseMaterial());
								if (plugin.getReventConfig().isSnowballSpleef()) {
									player.getInventory().addItem(XMaterial.SNOWBALL.parseItem());
									player.updateInventory();
								}

							} else {
								evt.getBlock().breakNaturally();
							}
						} else if (plugin.getMatchActive().getMatch().getAllMaterialAllowed()
								|| (plugin.getMatchActive().getMatch().getDatas() != null
										&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
										&& evt.getBlock().getType() != null
										&& evt.getBlock().getState().getData() != null
										&& UtilsRandomEvents.contieneMaterialData(evt.getBlock(),
												plugin.getMatchActive().getMatch()))) {
							evt.setCancelled(true);
							try {
								plugin.getMatchActive().getMapHandler().getBlockDisappeared().put(
										evt.getBlock().getLocation(),
										new MaterialData(evt.getBlock().getType(), evt.getBlock().getData()));
							} catch (Throwable eb) {
								plugin.getMatchActive().getMapHandler().getBlockDisappeared()
										.put(evt.getBlock().getLocation(), evt.getBlock().getState().getData().clone());
							}
							if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)) {
								evt.getBlock().setType(XMaterial.AIR.parseMaterial());

								if (plugin.getReventConfig().isSnowballSpleef()) {
									player.getInventory().addItem(XMaterial.SNOWBALL.parseItem());
									player.updateInventory();
								}

							} else {
								evt.getBlock().breakNaturally();
							}
						} else {
							evt.setCancelled(true);

						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onFill(PlayerBucketFillEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayersSpectators().contains(player)
				&& plugin.getMatchActive().getPlaying()) {
			if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLATOON)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.HOEHOEHOE)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.PAINTBALL)) {
				evt.setCancelled(true);
			} else {
				if (!plugin.getMatchActive().getCanBreak()) {
					evt.setCancelled(true);

				} else {
					Location l = evt.getBlockClicked().getLocation();
					l.setX(l.getX() + evt.getBlockFace().getModX());
					l.setY(l.getY() + evt.getBlockFace().getModY());
					l.setZ(l.getZ() + evt.getBlockFace().getModZ());
					if (plugin.getMatchActive().getMapHandler().getBlockPlaced()
							.containsKey(l.getBlock().getLocation())) {
						evt.setCancelled(false);
						plugin.getMatchActive().getMapHandler().getBlockPlaced().remove(l.getBlock().getLocation());
					} else {
						evt.setCancelled(true);

					}
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
				} else if (plugin.getMatchActive().getMatch().getAllMaterialAllowed()
						|| (plugin.getMatchActive().getMatch().getDatas() != null
								&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
								&& evt.getBlock().getType() != null && evt.getBlock().getState().getData() != null
								&& UtilsRandomEvents.contieneMaterialData(evt.getBlock(),
										plugin.getMatchActive().getMatch()))) {
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlaceBucket(PlayerBucketEmptyEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())
				&& plugin.getMatchActive().getPlaying()) {
			if (plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.ANVIL_SPLEEF)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEEF)
					|| plugin.getMatchActive().getMatch().getMinigame().equals(MinigameType.SPLEGG)) {
				evt.setCancelled(true);
			} else {
				Location l = evt.getBlockClicked().getLocation();
				l.setX(l.getX() + evt.getBlockFace().getModX());
				l.setY(l.getY() + evt.getBlockFace().getModY());
				l.setZ(l.getZ() + evt.getBlockFace().getModZ());

				// evt.setCancelled(true);
				evt.setCancelled(false);
				plugin.getMatchActive().getMapHandler().getBlockPlaced().put(l.getBlock().getLocation(),
						l.getBlock().getState().getData().clone());
				// evt.getBlock().setType(XMaterial.AIR.parseMaterial());

			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onForm(BlockFormEvent evt) {

		if (plugin.getMatchActive() != null && plugin.getMatchActive().getMapHandler() != null
				&& plugin.getMatchActive().getMapHandler().getCuboid() != null && plugin.getMatchActive().getPlaying()
				&& plugin.getMatchActive().getMapHandler().getCuboid().contains(evt.getBlock().getLocation())) {

			// evt.setCancelled(true);
			// evt.setCancelled(false);
			plugin.getMatchActive().getMapHandler().getBlockPlaced().put(evt.getBlock().getLocation(),
					evt.getBlock().getState().getData().clone());
			// evt.getBlock().setType(XMaterial.AIR.parseMaterial());

		}
	}

	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent evt) {
		Player player = evt.getPlayer();
		if (plugin.getMatchActive() != null
				&& plugin.getMatchActive().getPlayerHandler().getPlayers().contains(player.getName())
				&& (!plugin.getMatchActive().getPlaying()
						|| plugin.getMatchActive().getMatch().getMinigame() == MinigameType.BLOCK_PARTY)) {
			evt.setCancelled(true);
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

							if (plugin.getMatchActive().getMatch().getAllMaterialAllowed()
									|| (plugin.getMatchActive().getMatch().getDatas() != null
											&& !plugin.getMatchActive().getMatch().getDatas().isEmpty()
											&& l2.getBlock().getType() != null
											&& l2.getBlock().getState().getData() != null
											&& (UtilsRandomEvents.contieneMaterialData(l2.getBlock(),
													plugin.getMatchActive().getMatch())
													|| l2.getBlock().getType().equals(XMaterial.ANVIL.parseMaterial())
													|| l2.getBlock().getType()
															.equals(XMaterial.CHIPPED_ANVIL.parseMaterial())
													|| l2.getBlock().getType()
															.equals(XMaterial.DAMAGED_ANVIL.parseMaterial())))) {
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

	int timer, id = 0;
	Random gen = new Random();
	private Object[] dataStore = new Object[5];

	public void playFirework(Location loc) throws Exception {
		Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
		if (dataStore[0] == null)
			dataStore[0] = getMethod(loc.getWorld().getClass(), "getHandle");
		if (dataStore[2] == null)
			dataStore[2] = getMethod(fw.getClass(), "getHandle");
		dataStore[3] = ((Method) dataStore[0]).invoke(loc.getWorld(), (Object[]) null);
		dataStore[4] = ((Method) dataStore[2]).invoke(fw, (Object[]) null);
		if (dataStore[1] == null)
			dataStore[1] = getMethod(dataStore[3].getClass(), "addParticle");
		((Method) dataStore[1]).invoke(dataStore[3], new Object[] { "fireworksSpark", loc.getX(), loc.getY(),
				loc.getZ(), gen.nextGaussian() * 0.05D, -(loc.getZ() * 1.15D) * 0.5D, gen.nextGaussian() * 0.05D });
		fw.remove();
	}

	private Method getMethod(Class<?> cl, String method) {
		for (Method m : cl.getMethods())
			if (m.getName().equals(method))
				return m;
		return null;
	}

	public RandomEvents getPlugin() {
		return plugin;
	}

	public void setPlugin(RandomEvents plugin) {
		this.plugin = plugin;
	}

}
