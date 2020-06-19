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
		if (plugin.getPlayersCreation().containsKey(player.getName())) {
			checkMessageCreation(event.getMessage(), player, plugin.getPlayersCreation().get(player.getName()));
			event.setCancelled(Boolean.TRUE);

		}
	}

	private void checkMessageCreation(String message, Player player, Integer position) {
		Match match = plugin.getPlayerMatches().get(player.getName());
		Boolean actualiza=Boolean.TRUE;
		if (match == null)
			match = new Match();

		switch (Creacion.getByPosition(position)) {
		case BATTLE_NAME:
			match.setName(UtilsRandomEvents.cambiarMensajeConEtiqueta(message).trim());
			actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			break;

		case AMOUNT_PLAYERS:
			try {
				match.setAmountPlayers(Integer.valueOf(message.trim()));
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		case AMOUNT_PLAYERS_MIN:
			try {
				match.setAmountPlayersMin(Integer.valueOf(message.trim()));
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		case SPAWN_PLAYER:
			if (message.equals(Constantes.DONE)) {
				match.setPlayerSpawn(player.getLocation());
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			}
			break;
		case ARENA_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getSpawns().add(player.getLocation());
				if (match.getSpawns().size() == match.getAmountPlayers()) {
					actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 2);
				} else {
					actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);

				}
			}
			break;
		case ANOTHER_ARENA_SPAWNS:
			if (message.equals(Constantes.DONE)) {
				match.getSpawns().add(player.getLocation());
				if (match.getSpawns().size() == match.getAmountPlayers()) {
					actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
				} else {
					actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);

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

				
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			}
			break;
		case REWARDS:
			match.getRewards().add(message);
			actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			break;
		case ANOTHER_REWARDS:
			if (message.equals(Constantes.DONE)) {
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position + 1);
			} else {
				match.getRewards().add(message);
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);
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
					case KNOCKBACK_DUEL:
						actualiza=UtilsRandomEvents.pasaACreation(plugin, player, 999);
						break;
					case TOP_KILLER:
					case TOP_KILLER_TEAM_2:
						actualiza=UtilsRandomEvents.pasaACreation(plugin, player, 14);
					}
				} else {
					player.sendMessage(Constantes.INVALID_INPUT);
					actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);
				}
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);
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
		case PLAY_TIME:
			try {
				match.setTiempoPartida(Integer.valueOf(message.trim()));
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, 999);
			} catch (Exception e) {
				player.sendMessage(Constantes.INVALID_INPUT);
				actualiza=UtilsRandomEvents.pasaACreation(plugin, player, position);
			}
			break;
		default:
			break;
		}
		if(actualiza){
			System.out.println("paso por el actualiza");

			plugin.getPlayerMatches().put(player.getName(), match);
			plugin.getPlayerMatches().put(player.getName(), match);
		}else{
			System.out.println("paso por el remove");
			plugin.getPlayerMatches().remove(player.getName());
			plugin.getPlayerMatches().remove(player.getName());
		}
	}

}
