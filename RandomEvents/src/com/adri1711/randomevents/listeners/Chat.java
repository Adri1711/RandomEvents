package com.adri1711.randomevents.listeners;

import java.util.Arrays;
import java.util.List;

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

public class Chat implements Listener {

	private RandomEvents plugin;

	public Chat(RandomEvents plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (plugin.getPlayersCreation().containsKey(player)) {
			checkMessageCreation(event.getMessage(), player, plugin.getPlayersCreation().get(player));
			event.setCancelled(Boolean.TRUE);

		}
	}

	private void checkMessageCreation(String message, Player player, Integer position) {
		Match match = plugin.getPlayerMatches().get(player);
		if (match == null)
			match = new Match();

		switch (Creacion.getByPosition(position)) {
		case BATTLE_NAME:
			match.setName(UtilsRandomEvents.cambiarMensajeConEtiqueta(message).trim());
			UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			break;

		case AMOUNT_PLAYERS:
			try {
				match.setAmountPlayers(Integer.valueOf(message.trim()));
				UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		case AMOUNT_PLAYERS_MIN:
			try {
				match.setAmountPlayersMin(Integer.valueOf(message.trim()));
				UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		case SPAWN_PLAYER:
			if (message.equals(Constantes.DONE)) {
				match.setPlayerSpawn(player.getLocation());
				UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			}
			break;
		case ARENA_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getSpawns().add(player.getLocation());
				if (match.getSpawns().size() == match.getAmountPlayers()) {
					UtilsRandomEvents.pasaACreation(plugin, player, position + 2);
				} else {
					UtilsRandomEvents.pasaACreation(plugin, player, position + 1);

				}
			}
			break;
		case ANOTHER_ARENA_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getSpawns().add(player.getLocation());
				if (match.getSpawns().size() == match.getAmountPlayers()) {
					UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
				} else {
					UtilsRandomEvents.pasaACreation(plugin, player, position);

				}
			}
			break;
		case INVENTORY:
			if (message.equals(Constantes.DONE)) {
				
				ItemStack[] contenido= player.getInventory().getContents();
				List<ItemStack> contenidoList=Arrays.asList(contenido);
				contenidoList.removeAll(Arrays.asList(player.getInventory().getArmorContents()));
			
				match.getInventory().setContents((ItemStack[])contenidoList.toArray());
				match.getInventory().setHelmet(player.getInventory().getHelmet());
				match.getInventory().setBoots(player.getInventory().getBoots());
				match.getInventory().setLeggings(player.getInventory().getLeggings());
				match.getInventory().setChestplate(player.getInventory().getChestplate());

				
				UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			}
			break;
		case REWARDS:
			match.getRewards().add(message);
			UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			break;
		case ANOTHER_REWARDS:
			if (message.equals(Constantes.DONE)) {
				UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			} else {
				match.getRewards().add(message);
				UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		case MINIGAME_TYPE:
			try {
				MinigameType minigame = MinigameType.values()[Integer.valueOf(message)];
				if (minigame != null) {
					match.setMinigame(minigame);
					switch (minigame) {
					case BATTLE_ROYALE:
					case BATTLE_ROYALE_CABALLO:
					case BATTLE_ROYALE_TEAM_2:
						UtilsRandomEvents.pasaACreation(plugin, player, 999);
						break;
					}
				} else {
					player.sendMessage(Constantes.INVALID_INPUT);
					UtilsRandomEvents.pasaACreation(plugin, player, position);
				}
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		case MOB_NAME:
			break;
		case END:
			break;
		case EVENT_SPAWN:
			break;
		case MOB_SPAWN:
			break;
		case TIMER_MOB_SPAWN:
			break;
			
		default:
			break;
		}
		plugin.getPlayerMatches().put(player, match);
	}

}
