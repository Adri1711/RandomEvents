package com.adri1711.randomevents.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;
import com.adri1711.randomevents.util.Constantes;

public class Comandos {

	public static final String COMANDO_ALIASE1 = "revent";

	public static final String COMANDO_ALIASE2 = "randomevent";

	public static void muestraMenu(RandomEvents plugin, Player player) {

		String menu = plugin.getLanguage().getHelpMenu();
		menu += Constantes.SALTO_LINEA;
		menu += plugin.getLanguage().getShowMenu();

		for (ComandosEnum cmd : ComandosEnum.values()) {
			if (player != null) {
				if (cmd.getShowOnMenu() && player.hasPermission(cmd.getPermission())) {
					menu += Constantes.SALTO_LINEA;
					try {
						Method method = plugin.getLanguage().getClass().getDeclaredMethod(cmd.getDescription());
						menu += method.invoke(plugin.getLanguage());
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (cmd.getShowOnMenu() && cmd.getCanConsole()) {
					menu += Constantes.SALTO_LINEA;
					try {
						Method method = plugin.getLanguage().getClass().getDeclaredMethod(cmd.getDescription());
						menu += method.invoke(plugin.getLanguage());
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}

			}
		}
		if (player != null) {
			player.sendMessage(menu);
		} else {
			System.out.println(menu);
		}
	}

	public static void ejecutaComandoSimple(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 1);
			if (comando != null) {
				if ((player == null && comando.getCanConsole())
						|| (player != null && player.hasPermission(comando.getPermission()))) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player);
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (Throwable e) {
			System.out.println(e);
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
		}

	}

	public static void ejecutaComandoDosArgumentos(RandomEvents plugin, Player player, String[] args) {

		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 2);
			if (comando != null) {
				if ((player == null && comando.getCanConsole())
						|| (player != null && player.hasPermission(comando.getPermission()))) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1]);
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (Throwable e) {
			System.out.println(e);
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
		}

	}

	public static void ejecutaComandoTresArgumentos(RandomEvents plugin, Player player, String[] args) {

		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 3);
			if (comando != null) {
				if ((player == null && comando.getCanConsole())
						|| (player != null && player.hasPermission(comando.getPermission()))) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1], args[2]);
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (Throwable e) {
			System.out.println(e);
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
		}

	}

	public static void ejecutaComandoCuatroArgumentos(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 4);
			if (comando != null) {
				if ((player == null && comando.getCanConsole())
						|| (player != null && player.hasPermission(comando.getPermission()))) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class, String.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1], args[2], args[3]);
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (Throwable e) {
			System.out.println(e);
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
		}

	}

	public static void ejecutaComandoCincoArgumentos(RandomEvents plugin, Player player, String[] args) {
		try {

			ComandosEnum comando = ComandosEnum.getByAliaseAndSize(args[0], 5);
			if (comando != null) {
				if ((player == null && comando.getCanConsole())
						|| (player != null && player.hasPermission(comando.getPermission()))) {

					Method method = plugin.getComandosExecutor().getClass().getMethod(comando.getMetodo(),
							RandomEvents.class, Player.class, String.class, String.class, String.class, String.class);
					method.invoke(plugin.getComandosExecutor(), plugin, player, args[1], args[2], args[3], args[4]);
				} else {
					if (player != null)
						player.sendMessage(
								plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getNoPermission());

				}
			} else {
				if (player != null)
					player.sendMessage(
							plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getInvalidCmd());
			}

		} catch (Throwable e) {
			System.out.println(e);
			if (player != null)
				player.sendMessage(plugin.getLanguage().getTagPlugin() + " " + plugin.getLanguage().getError());
		}

	}

}
