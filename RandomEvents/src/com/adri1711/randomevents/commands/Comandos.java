package com.adri1711.randomevents.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;

public class Comandos {

	public static final String COMANDO_ALIASE1 = "revent";

	public static final String COMANDO_ALIASE2 = "randomevent";

	public static void muestraMenu(Player player) {
		String menu = "§6----------------- §9§lRandomEvents §6---------------------";
		menu += Constantes.SALTO_LINEA;
		menu += "   §6/revent:\n          §e-> Shows this menu";

		for (ComandosEnum cmd : ComandosEnum.values()) {

			if (cmd.getShowOnMenu() && player.hasPermission(cmd.getPermission())) {
				menu += Constantes.SALTO_LINEA;
				menu += cmd.getDescription();
			}
		}
		player.sendMessage(menu);
	}

	public static void ejecutaComandoSimple(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 1);
			if (comando != null) {
				if (player.hasPermission(comando.getPermission())) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player);
				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
			System.out.println(e.getMessage());
		}

	}

	public static void ejecutaComandoDosArgumentos(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 2);
			if (comando != null) {
				if (player.hasPermission(comando.getPermission())) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1]);
				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
			System.out.println(e.getMessage());
		}
	}

	public static void ejecutaComandoCuatroArgumentos(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 4);
			if (comando != null) {
				if (player.hasPermission(comando.getPermission())) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class, String.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1], args[2], args[3]);
				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
			System.out.println(e.getMessage());
		}
	}

	public static void ejecutaComandoCincoArgumentos(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 5);
			if (comando != null) {
				if (player.hasPermission(comando.getPermission())) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class, String.class, String.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1], args[2], args[3], args[4]);
				} else {
					player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
			System.out.println(e.getMessage());
		}
	}

}
