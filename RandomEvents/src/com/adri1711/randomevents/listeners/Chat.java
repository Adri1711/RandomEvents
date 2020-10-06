package com.adri1711.randomevents.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.match.Match;
import com.adri1711.randomevents.match.MinigameType;
import com.adri1711.randomevents.match.enums.Creacion;
import com.adri1711.randomevents.util.Constantes;
import com.adri1711.randomevents.util.UtilsRandomEvents;
import com.adri1711.util.enums.AMaterials;

public class Chat implements Listener {

	private RandomEvents plugin;

	public Chat(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (plugin.getPlayersCreation().containsKey(player.getName())) {
			checkMessageCreation(event.getMessage(), player, plugin.getPlayersCreation().get(player.getName()));
			event.setCancelled(Boolean.TRUE);

		}
	}

	private void checkMessageCreation(String message, Player player, Integer position) {
		Match match = plugin.getPlayerMatches().get(player.getName());
		Boolean actualiza = Boolean.TRUE;
		if (match == null)
			match = new Match();

		switch (Creacion.getByPosition(position)) {
		case BATTLE_NAME:
			match.setName(UtilsRandomEvents.cambiarMensajeConEtiqueta(message).trim());
			actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			break;

		case AMOUNT_PLAYERS:
			try {
				match.setAmountPlayers(Integer.valueOf(message.trim()));
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case AMOUNT_PLAYERS_MIN:
			try {
				match.setAmountPlayersMin(Integer.valueOf(message.trim()));
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case SPAWN_PLAYER:
			if (message.equals(Constantes.DONE)) {
				match.setPlayerSpawn(player.getLocation());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			}
			break;
		case SPAWN_BEAST:
			if (message.equals(Constantes.DONE)) {
				match.setBeastSpawn(player.getLocation());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			}
			break;
		case ARENA_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getSpawns().add(player.getLocation());
				if (match.getSpawns().size() == match.getAmountPlayers()) {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 2, match);
				} else {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);

				}
			}
			break;
		case ENTITY_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getEntitySpawns().add(player.getLocation());
				if (match.getEntitySpawns().size() == match.getAmountPlayers()) {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 2, match);
				} else {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);

				}
			}
			break;
		case ANOTHER_ENTITY_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getEntitySpawns().add(player.getLocation());
				if (match.getEntitySpawns().size() == match.getAmountPlayers()) {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
				} else {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);

				}
			}
			break;
		case ANOTHER_ARENA_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getSpawns().add(player.getLocation());
				if (match.getSpawns().size() == match.getAmountPlayers()) {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
				} else {
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);

				}
			}
			break;
		case SPECTATOR_SPAWNS:

			if (message.equals(Constantes.DONE)) {
				match.getSpectatorSpawns().add(player.getLocation());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);

			} else if (message.equals(Constantes.NEXT)) {
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);

			}
			break;
		case INVENTORY:
			if (message.equals(Constantes.DONE)) {

				ItemStack[] contenido = player.getInventory().getContents();
				List<ItemStack> contenidoList = Arrays.asList(contenido);
				try {
					contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
				} catch (Exception e) {

				}
				ItemStack[] arrayContenido=new ItemStack[contenidoList.size()];
				arrayContenido=contenidoList.toArray(arrayContenido);
				match.getInventory().setContents(arrayContenido);
				match.getInventory().setHelmet(player.getInventory().getHelmet());
				match.getInventory().setBoots(player.getInventory().getBoots());
				match.getInventory().setLeggings(player.getInventory().getLeggings());
				match.getInventory().setChestplate(player.getInventory().getChestplate());

				// actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
				// position + 1, match);

				try {
					MinigameType minigame = match.getMinigame();
					if (minigame != null) {
						switch (minigame) {
						case BATTLE_ROYALE:
						case BATTLE_ROYALE_CABALLO:
						case BATTLE_ROYALE_TEAM_2:
						case KNOCKBACK_DUEL:
						case TNT_RUN:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.END.getPosition(),
									match);
							break;
						case TOP_KILLER:
						case TOP_KILLER_TEAM_2:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.PLAY_TIME.getPosition(), match);
							break;
						case ESCAPE_ARROW:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.ARROW_LOCATION1.getPosition(), match);
							break;
						case GEM_CRAWLER:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.GEM_LOCATION1.getPosition(), match);
							break;

						case BOMB_TAG:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.TIMER_BOMB.getPosition(), match);
							break;
						case BOAT_RUN:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.ENTITY_SPAWNS.getPosition(), match);
							break;
						case RACE:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.GOAL_LOCATION1.getPosition(), match);
							break;
						case ESCAPE_FROM_BEAST:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.SECONDS_TO_SPAWN_BEAST.getPosition(), match);
							break;
						case SPLEEF:
						case SPLEGG:
							actualiza = UtilsRandomEvents.pasaACreation(plugin, player,
									Creacion.MATERIAL_SPLEEF.getPosition(), match);
							break;
						}
					} else {
						player.sendMessage(plugin.getLanguage().getInvalidInput());
						actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
					}
				} catch (Exception e) {
					player.sendMessage(plugin.getLanguage().getInvalidInput());
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
				}
				break;

			}
			break;
		case INVENTORY_BEAST:
			if (message.equals(Constantes.DONE)) {

				ItemStack[] contenido = player.getInventory().getContents();
				List<ItemStack> contenidoList = Arrays.asList(contenido);
				try {
					contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
				} catch (Exception e) {

				}
				ItemStack[] arrayContenido=new ItemStack[contenidoList.size()];
				arrayContenido=contenidoList.toArray(arrayContenido);
				match.getInventoryBeast().setContents(arrayContenido);
				match.getInventoryBeast().setHelmet(player.getInventory().getHelmet());
				match.getInventoryBeast().setBoots(player.getInventory().getBoots());
				match.getInventoryBeast().setLeggings(player.getInventory().getLeggings());
				match.getInventoryBeast().setChestplate(player.getInventory().getChestplate());

				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			}
			break;
		case INVENTORY_RUNNERS:
			if (message.equals(Constantes.DONE)) {

				ItemStack[] contenido = player.getInventory().getContents();
				List<ItemStack> contenidoList = Arrays.asList(contenido);
				try {
					contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
				} catch (Exception e) {

				}
				ItemStack[] arrayContenido=new ItemStack[contenidoList.size()];
				arrayContenido=contenidoList.toArray(arrayContenido);
				match.getInventoryRunners().setContents(arrayContenido);
				match.getInventoryRunners().setHelmet(player.getInventory().getHelmet());
				match.getInventoryRunners().setBoots(player.getInventory().getBoots());
				match.getInventoryRunners().setLeggings(player.getInventory().getLeggings());
				match.getInventoryRunners().setChestplate(player.getInventory().getChestplate());

				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.GOAL_LOCATION1.getPosition(),
						match);
			}
			break;
		case REWARDS:
			match.getRewards().add(message);
			actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			break;
		case ANOTHER_REWARDS:
			if (message.equals(Constantes.DONE)) {
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			} else {
				match.getRewards().add(message);
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case MINIGAME_TYPE:
			try {
				MinigameType minigame = MinigameType.values()[Integer.valueOf(message)];
				if (minigame != null) {
					match.setMinigame(minigame);
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);

				} else {
					player.sendMessage(plugin.getLanguage().getInvalidInput());
					actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
				}
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case MOB_NAME:
			break;
		case END:
			break;

		case MOB_SPAWN:
			break;
		case TIMER_MOB_SPAWN:
			break;
		case PLAY_TIME:
			try {
				match.setTiempoPartida(Integer.valueOf(message.trim()));
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.END.getPosition(), match);
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case ARROW_LOCATION1:
			if (message.equals(Constantes.DONE)) {
				match.setLocation1(player.getLocation());
				match.setMob(EntityType.ARROW);
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			}
			break;
		case GEM_LOCATION1:
		case GOAL_LOCATION1:
			if (message.equals(Constantes.DONE)) {
				match.setLocation1(player.getLocation());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			}
			break;
		case ARROW_LOCATION2:
		case GEM_LOCATION2:
			if (message.equals(Constantes.DONE)) {
				match.setLocation2(player.getLocation());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			}
			break;
		case GOAL_LOCATION2:
			if (message.equals(Constantes.DONE)) {
				match.setLocation2(player.getLocation());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.END.getPosition(), match);
			}
			break;

		case MATERIAL_SPLEEF:
			if (player.getItemInHand() != null
					&& player.getItemInHand().getType() != (plugin.getApi().getMaterial(AMaterials.AIR))) {
				match.setMaterial(player.getItemInHand().getType().toString());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.END.getPosition(), match);
			} else {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case TIMER_ARROW_SPAWN:
		case TIMER_GEM_SPAWN:
			try {
				match.setSecondsMobSpawn(Double.valueOf(message.trim()));
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.END.getPosition(), match);
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case SECONDS_TO_SPAWN_BEAST:
			try {
				match.setSecondsMobSpawn(Double.valueOf(message.trim()));
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position + 1, match);
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		case TIMER_BOMB:
			try {
				match.setSecondsMobSpawn(Double.valueOf(message.trim()));
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, Creacion.END.getPosition(), match);
			} catch (Exception e) {
				player.sendMessage(plugin.getLanguage().getInvalidInput());
				actualiza = UtilsRandomEvents.pasaACreation(plugin, player, position, match);
			}
			break;
		default:
			break;
		}
		if (actualiza) {
			plugin.getPlayerMatches().put(player.getName(), match);
			plugin.getPlayerMatches().put(player.getName(), match);
		} else {
			plugin.getPlayerMatches().remove(player.getName());
			plugin.getPlayerMatches().remove(player.getName());
		}
	}

}
