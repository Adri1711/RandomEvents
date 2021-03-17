package com.adri1711.randomevents.match.enums;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.adri1711.util.enums.XMaterial;

import net.md_5.bungee.api.ChatColor;

public enum Petos {

	RED(0, Color.RED, DyeColor.RED, XMaterial.RED_WOOL, ChatColor.RED, "Red"),

	BLUE(1, Color.BLUE, DyeColor.BLUE, XMaterial.BLUE_WOOL, ChatColor.BLUE, "Blue"),

	GREEN(2, Color.GREEN, DyeColor.GREEN, XMaterial.GREEN_WOOL, ChatColor.GREEN, "Green"),

	YELLOW(3, Color.YELLOW, DyeColor.YELLOW, XMaterial.YELLOW_WOOL, ChatColor.YELLOW, "Yellow"),

	GRAY(4, Color.GRAY, DyeColor.GRAY, XMaterial.GRAY_WOOL, ChatColor.GRAY, "Gray"),

	PURPLE(5, Color.PURPLE, DyeColor.PURPLE, XMaterial.PURPLE_WOOL, ChatColor.LIGHT_PURPLE, "Purple"),

	BLACK(6, Color.BLACK, DyeColor.BLACK, XMaterial.BLACK_WOOL, ChatColor.BLACK, "Black"),

	WHITE(7, Color.WHITE, DyeColor.WHITE, XMaterial.WHITE_WOOL, ChatColor.WHITE, "White");

	private Integer team;

	private Color color;
	private DyeColor dye;
	private XMaterial wool;
	private ChatColor chatColor;
	private String name;

	private Petos(Integer team, Color color, DyeColor dye, XMaterial wool, ChatColor chatColor, String name) {
		this.team = team;
		this.color = color;
		this.dye = dye;
		this.wool = wool;
		this.chatColor = chatColor;
		this.name = name;
	}

	public XMaterial getWool() {
		return wool;
	}

	public void setWool(XMaterial wool) {
		this.wool = wool;
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

	public DyeColor getDye() {
		return dye;
	}

	public void setDye(DyeColor dye) {
		this.dye = dye;
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
