package com.adri1711.randomevents.match.enums;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.adri1711.util.enums.XMaterial;

import net.md_5.bungee.api.ChatColor;

public enum Petos {

	RED(0, Color.RED, ChatColor.RED, "Red"),

	BLUE(1, Color.BLUE, ChatColor.BLUE, "Blue"),

	GREEN(2, Color.GREEN, ChatColor.GREEN, "Green"),

	YELLOW(3, Color.YELLOW, ChatColor.YELLOW, "Yellow"),

	GRAY(4, Color.GRAY, ChatColor.GRAY, "GRAY"),

	PURPLE(5, Color.PURPLE, ChatColor.LIGHT_PURPLE, "Purple"),

	BLACK(6, Color.BLACK, ChatColor.BLACK, "Black"),

	WHITE(7, Color.WHITE, ChatColor.WHITE, "White");

	private Integer team;

	private Color color;
	private ChatColor chatColor;
	private String name;

	private Petos(Integer team, Color color, ChatColor chatColor, String name) {
		this.team = team;
		this.color = color;
		this.chatColor = chatColor;
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ChatColor getChatColor() {
		return chatColor;
	}

	public void setChatColor(ChatColor chatColor) {
		this.chatColor = chatColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public static Petos getPeto(Integer peto) {
		Petos res = null;
		for (Petos p : Petos.values()) {
			if (p.getTeam().equals(peto)) {
				res = p;
			}
		}
		return res;
	}

	public ItemStack getPeto() {
		ItemStack peto = XMaterial.LEATHER_CHESTPLATE.parseItem();
		LeatherArmorMeta petoMeta = (LeatherArmorMeta) peto.getItemMeta();
		petoMeta.setColor(getColor());
		peto.setItemMeta(petoMeta);
		return peto;
	}

}
