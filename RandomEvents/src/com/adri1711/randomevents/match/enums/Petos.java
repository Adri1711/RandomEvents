package com.adri1711.randomevents.match.enums;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.adri1711.util.enums.XMaterial;

import net.md_5.bungee.api.ChatColor;

public enum Petos {

	RED(0, Color.RED, DyeColor.RED, XMaterial.RED_WOOL, XMaterial.RED_TERRACOTTA, ChatColor.RED, "Red"),

	BLUE(1, Color.BLUE, DyeColor.BLUE, XMaterial.BLUE_WOOL, XMaterial.BLUE_TERRACOTTA, ChatColor.BLUE, "Blue"),

	GREEN(2, Color.GREEN, DyeColor.GREEN, XMaterial.GREEN_WOOL, XMaterial.GREEN_TERRACOTTA, ChatColor.GREEN, "Green"),

	YELLOW(3, Color.YELLOW, DyeColor.YELLOW, XMaterial.YELLOW_WOOL, XMaterial.YELLOW_TERRACOTTA, ChatColor.YELLOW,
			"Yellow"),

	GRAY(4, Color.GRAY, DyeColor.GRAY, XMaterial.GRAY_WOOL, XMaterial.GRAY_TERRACOTTA, ChatColor.GRAY, "Gray"),

	PURPLE(5, Color.PURPLE, DyeColor.PURPLE, XMaterial.PURPLE_WOOL, XMaterial.PURPLE_TERRACOTTA, ChatColor.LIGHT_PURPLE,
			"Purple"),

	BLACK(6, Color.BLACK, DyeColor.BLACK, XMaterial.BLACK_WOOL, XMaterial.BLACK_TERRACOTTA, ChatColor.BLACK, "Black"),

	WHITE(7, Color.WHITE, DyeColor.WHITE, XMaterial.WHITE_WOOL, XMaterial.WHITE_TERRACOTTA, ChatColor.WHITE, "White");

	private Integer team;

	private Color color;
	private DyeColor dye;
	private XMaterial wool;
	private XMaterial clay;
	private ChatColor chatColor;
	private String name;

	private Petos(Integer team, Color color, DyeColor dye, XMaterial wool, XMaterial clay, ChatColor chatColor,
			String name) {
		this.team = team;
		this.color = color;
		this.dye = dye;
		this.wool = wool;
		this.clay = clay;
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

	
	public XMaterial getClay() {
		return clay;
	}

	public void setClay(XMaterial clay) {
		this.clay = clay;
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

	public ItemStack getWoolItem() {
		ItemStack wool = getWool().parseItem();
		try {
			wool.setDurability(getDye().getWoolData());
			wool.getData().setData(getDye().getWoolData());
		} catch (Throwable e) {

		}
		return wool;
	}
	
	public ItemStack getClayItem() {
		ItemStack wool = getClay().parseItem();
		try {
			wool.setDurability(getDye().getWoolData());
			wool.getData().setData(getDye().getWoolData());
		} catch (Throwable e) {

		}
		return wool;
	}

}
