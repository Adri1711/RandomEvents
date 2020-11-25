package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.List;

public enum ComandosEnum {

	CMD_JOIN("join", 2, "randomevent.join", "   §6/revent join:\n          §e-> Join the Random Event",
			"joinRandomEvent", false, false),

	CMD_JOIN_TOURNAMENT("tjoin", 2, "randomevent.tjoin",
			"   §6/revent tjoin:\n          §e-> Join the Tournament Random Event", "joinTournamentRandomEvent", false,
			false),

	CMD_LEAVE("leave", 1, "randomevent.leave", "   §6/revent leave:\n          §e-> Leave the Random Event",
			"leaveRandomEvent", true, false),

	CMD_LEAVE_TOURNAMENT("tleave", 1, "randomevent.tleave",
			"   §6/revent tleave:\n          §e-> Leave the Tournament Random Event", "leaveTournamentRandomEvent",
			true, false),

	CMD_STATS("stats", 1, "randomevent.stats", "   §6/revent stats:\n          §e-> Show the stats", "statsRandomEvent",
			true, false),

	CMD_STATS_OTHER("stats", 2, "randomevent.stats.other",
			"   §6/revent stats <player>:\n          §e-> Show the stats of a player", "statsRandomEvent", true, false),

	CMD_FORCESTOP("forcestop", 1, "randomevent.admin.forcestop",
			"   §6/revent forcestop:\n          §e-> Force the stop of the Random Event", "forceStop", true, true),

	CMD_SPAWNSET("spawnset", 1, "randomevent.admin.spawnset",
			"   §6/revent spawnset:\n          §e-> Set the spawn, where players will be teleported after finishing a Random Event",
			"spawnSet", true, false),

	CMD_SPAWNSET_TOURNAMENT("tspawnset", 1, "randomevent.admin.tspawnset",
			"   §6/revent tspawnset:\n          §e-> Set the spawn where players will be teleported after joining a Tournament Random Event",
			"tournamentSpawnSet", true, false),

	CMD_MATCHES("matches", 1, "randomevent.admin.matches",
			"   §6/revent matches:\n          §e-> Show all Random Events available", "showRandomEvents", true, true),
	
	CMD_NEXT("next", 1, "randomevent.next",
			"   §6/revent next:\n          §e-> Show next scheduled Random Events", "nextRandomEvents", true, true),

	CMD_CURRENT_DATE("date", 1, "randomevent.admin.schedule",
			"   §6/revent date:\n          §e-> Shows the current date for the schedule", "currentDate", true, false),

	CMD_SCHEDULE("schedule", 4, "randomevent.admin.schedule",
			"   §6/revent schedule <monday:tuesday...> <hour> <minute>:\n          §e-> Create a schedule for a Random Event",
			"scheduleRandomEvent", true, false),

	CMD_SCHEDULE_SPECIFIC("schedule", 5, "randomevent.admin.schedule",
			"   §6/revent schedule <monday:tuesday...> <hour> <minute> <idmatch>:\n          §e-> Create a schedule for a Random Event and specific match",
			"scheduleRandomEvent", true, false),

	CMD_BEGIN("begin", 1, "randomevent.admin.force", "   §6/revent begin:\n          §e-> Begins a Random Event",
			"forceRandomEvent", true, true),

	CMD_BEGIN_TOURNAMENT("tbegin", 1, "randomevent.admin.tforce",
			"   §6/revent tbegin:\n          §e-> Begins a Tournament Random Event", "forceTournamentRandomEvent", true,
			true),

	CMD_BEGIN_SPECIFIC("begin", 2, "randomevent.admin.force",
			"   §6/revent begin <number>:\n          §e-> Begins a specific random event", "forceRandomEvent", true,
			true),

	CMD_BAN_PLAYER("ban", 3, "randomevent.admin.ban",
			"   §6/revent ban <player> <time>:\n          §e-> Ban a player from RandomEvents", "banPlayer", true,
			true),

	CMD_UNBAN_PLAYER("unban", 2, "randomevent.admin.unban",
			"   §6/revent unban <player>:\n          §e-> Unban a player from RandomEvents", "unbanPlayer", true, true),

	CMD_CREATE("create", 1, "randomevent.admin.create",
			"   §6/revent create:\n          §e-> Begin the creation of a Random Event", "createRandomEvent", true,
			false),

	CMD_CANCEL("cancel", 1, "randomevent.admin.cancel",
			"   §6/revent cancel:\n          §e-> Cancels the creation of a Random Event", "cancelCreationRandomEvent",
			true, false),

	CMD_RELOAD("reload", 1, "randomevent.admin.reload", "   §6/revent reload:\n          §e-> Reload the plugin",
			"reloadPlugin", true, true);

	private String aliase;

	private Integer size;

	private String permission;

	private String description;

	private String metodo;

	private Boolean showOnMenu;

	private Boolean canConsole;

	private ComandosEnum(String aliase, Integer size, String permission, String description, String metodo,
			Boolean showOnMenu, Boolean canConsole) {
		this.aliase = aliase;
		this.size = size;
		this.permission = permission;
		this.description = description;
		this.metodo = metodo;
		this.showOnMenu = showOnMenu;
		this.canConsole = canConsole;
	}

	public static ComandosEnum getByAliase(String aliase) {
		ComandosEnum cmd = null;
		for (ComandosEnum comando : ComandosEnum.values()) {
			if (comando.getAliase().equals(aliase)) {
				cmd = comando;
			}
		}
		return cmd;
	}

	public static ComandosEnum getByAliaseAndSize(String aliase, Integer size) {
		ComandosEnum cmd = null;
		for (ComandosEnum comando : ComandosEnum.values()) {
			if (comando.getAliase().equals(aliase) && comando.getSize().equals(size)) {
				cmd = comando;
			}
		}
		return cmd;
	}

	public static List<ComandosEnum> getBySize(Integer size) {
		List<ComandosEnum> cmd = new ArrayList<ComandosEnum>();
		for (ComandosEnum comando : ComandosEnum.values()) {
			if (comando.getSize().equals(size)) {
				cmd.add(comando);
			}
		}
		return cmd;
	}

	public String getAliase() {
		return aliase;
	}

	public void setAliase(String aliase) {
		this.aliase = aliase;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public Boolean getShowOnMenu() {
		return showOnMenu;
	}

	public void setShowOnMenu(Boolean showOnMenu) {
		this.showOnMenu = showOnMenu;
	}

	public Boolean getCanConsole() {
		return canConsole;
	}

	public void setCanConsole(Boolean canConsole) {
		this.canConsole = canConsole;
	}
	
	

}
