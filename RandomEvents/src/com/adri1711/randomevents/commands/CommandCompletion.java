package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;


public class CommandCompletion implements TabCompleter {
	// create a static array of values

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions =new ArrayList<String>();
		List<String> commands =new ArrayList<String>();
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if(player!=null){
			for(ComandosEnum cmd:ComandosEnum.values()){
				if(player.hasPermission(cmd.getPermission())){
					commands.add(cmd.getAliase());
				}
			}
			
		StringUtil.copyPartialMatches(args[0], commands, completions);
		// sort the list
		Collections.sort(completions);
		}
		return completions;
	}
}
