package com.adri1711.randomevents.commands;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import com.adri1711.randomevents.RandomEvents;

public class GenericCommand extends Command implements PluginIdentifiableCommand {
	private CommandExecutor commandExecutor;
	private TabCompleter tabCompleter;
	private RandomEvents plugin;

	public GenericCommand(RandomEvents plugin, String name, CommandExecutor executor, TabCompleter tabCompleter) {
		super(name);
		setExecutor(executor);
		setTabCompleter(tabCompleter);
		this.plugin = plugin;
	}

	protected GenericCommand(String name) {
		super(name);
	}

	public void setExecutor(CommandExecutor executor) {
		commandExecutor = executor;
	}

	public void setTabCompleter(TabCompleter completer) {
		tabCompleter = completer;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		return tabCompleter != null ? tabCompleter.onTabComplete(sender, this, alias, args) : null;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return commandExecutor.onCommand(sender, this, commandLabel, args);
	}

	public void setProperties(Map<String, Object> properties) {
		for (Map.Entry<String, Object> c : properties.entrySet()) {
			setProperty(c.getKey(), c.getValue());
		}
	}

	public void setProperty(String name, Object value) {
		switch (name) {
		case "aliases":
			@SuppressWarnings("unchecked")
			List<String> aliases = (List<String>) value;
			this.setAliases(aliases);
			break;
		case "usage":
			this.setUsage((String) value);
			break;
		case "description":
			this.setDescription((String) value);
			break;
		case "permission":
			this.setPermission((String) value);
			break;
		}
	}

	public void register() {
		if (commandExecutor == null) {
			throw new CommandNotPreparedException();
		}
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

			commandMap.register(getName(), this);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	public static class CommandNotPreparedException extends RuntimeException {
		public CommandNotPreparedException() {
			super("no CommandExecutor was found");
		}
	}

	@Override
	public Plugin getPlugin() {
		// TODO Auto-generated method stub
		return plugin;
	}
}
