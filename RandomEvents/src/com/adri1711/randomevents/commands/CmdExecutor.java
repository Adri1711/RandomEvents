package com.adri1711.randomevents.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.adri1711.randomevents.RandomEvents;

public class CmdExecutor implements CommandExecutor {

	private RandomEvents plugin;

	public CmdExecutor(RandomEvents plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (label.equalsIgnoreCase(Comandos.COMANDO_ALIASE1) || label.equalsIgnoreCase(Comandos.COMANDO_ALIASE2)) {
			switch (args.length) {
			case 0:
				Comandos.muestraMenu(plugin, player);
				break;
			case 1:
				Comandos.ejecutaComandoSimple(plugin, player, args);
				break;
			case 2:
				Comandos.ejecutaComandoDosArgumentos(plugin, player, args);
				break;
			case 3:
				Comandos.ejecutaComandoTresArgumentos(plugin, player, args);
				break;
			case 4:
				Comandos.ejecutaComandoCuatroArgumentos(plugin, player, args);
				break;
			case 5:
				Comandos.ejecutaComandoCincoArgumentos(plugin, player, args);
				break;
			default:
				Comandos.ejecutaComandoInfinitoArgumentos(plugin, player, args);

				break;

			}
		}

		return true;
	}

}
