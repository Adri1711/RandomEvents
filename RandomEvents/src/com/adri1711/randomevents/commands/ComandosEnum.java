package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.List;

public enum ComandosEnum {

	CMD_JOIN("join", 2, "randomevent.join", "getJoin", "joinRandomEvent", false, false),

	CMD_JOIN_TOURNAMENT("tjoin", 2, "randomevent.tjoin", "getTjoin", "joinTournamentRandomEvent", false, false),

	CMD_LEAVE("leave", 1, "randomevent.leave", "getLeave", "leaveRandomEvent", true, false),

	CMD_LEAVE_TOURNAMENT("tleave", 1, "randomevent.tleave", "getTleave", "leaveTournamentRandomEvent", true, false),
	
	CMD_CHECKPOINT("checkpoint", 1, "randomevent.checkpoint", "getCheckpoint", "checkpointRandomEvent", true, false),

	CMD_STATS("stats", 1, "randomevent.stats", "getStats", "statsRandomEvent", true, false),

	CMD_STATS_OTHER("stats", 2, "randomevent.stats.other", "getStatsOther", "statsRandomEvent", true, false),

	CMD_FORCESTOP("forcestop", 1, "randomevent.admin.forcestop", "getForcestop", "forceStop", true, true),

	CMD_SPAWNSET("spawnset", 1, "randomevent.admin.spawnset", "getSpawnset", "spawnSet", true, false),

	CMD_SPAWNSET_TOURNAMENT("tspawnset", 1, "randomevent.admin.tspawnset", "getTspawnset", "tournamentSpawnSet", true,
			false),

	CMD_MATCHES("matches", 1, "randomevent.admin.matches", "getMenuMatches", "showRandomEvents", true, true),

	CMD_NEXT("next", 1, "randomevent.next", "getNext", "nextRandomEvents", true, true),

	CMD_CURRENT_DATE("date", 1, "randomevent.admin.schedule", "getDate", "currentDate", true, false),

	CMD_SCHEDULE("schedule", 4, "randomevent.admin.schedule", "getSchedule", "scheduleRandomEvent", true, false),

	CMD_SCHEDULE_SPECIFIC("schedule", 5, "randomevent.admin.schedule", "getScheduleSpecific", "scheduleRandomEvent",
			true, false),

	CMD_BEGIN("begin", 1, "randomevent.admin.force", "getBegin", "forceRandomEvent", true, true),

	CMD_BEGIN_TOURNAMENT("tbegin", 1, "randomevent.admin.tforce", "getTbegin", "forceTournamentRandomEvent", true,
			true),

	CMD_BEGIN_SPECIFIC("begin", 2, "randomevent.admin.force", "getBeginSpecific", "forceRandomEvent", true, true),

	CMD_BAN_PLAYER("ban", 3, "randomevent.admin.ban", "getBan", "banPlayer", true, true),

	CMD_UNBAN_PLAYER("unban", 2, "randomevent.admin.unban", "getUnban", "unbanPlayer", true, true),

	CMD_CREATE("create", 1, "randomevent.admin.create", "getCreate", "createRandomEvent", true, false),

	CMD_EDIT("edit", 2, "randomevent.admin.edit", "getEdit", "editRandomEvent", true, true),

	CMD_CANCEL("cancel", 1, "randomevent.admin.cancel", "getCancel", "cancelCreationRandomEvent", true, false),

	CMD_RELOAD("reload", 1, "randomevent.admin.reload", "getReload", "reloadPlugin", true, true);

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
