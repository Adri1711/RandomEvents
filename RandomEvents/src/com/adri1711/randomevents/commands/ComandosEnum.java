package com.adri1711.randomevents.commands;

import java.util.ArrayList;
import java.util.List;

public enum ComandosEnum {

	CMD_JOIN("join", 2, "randomevent.join",
			"   §6/revent join:\n          §9-> §6Shows the GUI to join the Random Event", "joinRandomEvent",false),
	
	CMD_LEAVE("leave", 1, "randomevent.leave",
			"   §6/revent leave:\n          §9-> §6Leave the Random Event", "leaveRandomEvent",true),
	
//	CMD_STATS("stats", 1, "randomevent.stats",
//			"   §6/revent stats:\n          §9-> §6Show the stats", "statsRandomEvent",true),

	CMD_SPAWNSET("spawnset", 1, "randomevent.admin.spawnset",
			"   §6/revent spawnset:\n          §9-> §6Set the spawn, where players will be teleported after finishing a Random Event", "spawnSet",true),
	
	CMD_CREATE("create", 1, "randomevent.admin.create",
			"   §6/revent create:\n          §9-> §6Begin the creation of a Random Event", "createRandomEvent",true),

	CMD_CANCEL("cancel", 1, "randomevent.admin.cancel",
			"   §6/revent cancel:\n          §9-> §6Cancels the creation of a Random Event", "cancelCreationRandomEvent",true),
	
	CMD_RELOAD("reload", 1, "randomevent.admin.reload", "   §6/revent reload:\n          §9-> §6Reload the plugin",
			"reloadPlugin",true);

	private String aliase;

	private Integer size;

	private String permission;

	private String description;

	private String metodo;
	
	private Boolean showOnMenu;

	private ComandosEnum(String aliase, Integer size, String permission, String description, String metodo, Boolean showOnMenu) {
		this.aliase = aliase;
		this.size = size;
		this.permission = permission;
		this.description = description;
		this.metodo = metodo;
		this.showOnMenu=showOnMenu;
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

}
