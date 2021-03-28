package com.adri1711.randomevents.match;

import org.bukkit.inventory.ItemStack;

import com.adri1711.randomevents.match.utils.InventoryPers;
import com.adri1711.util.enums.XMaterial;

public class Kit {

	private String name;

	private String permission;

	private ItemStack item;

	private InventoryPers inventory;

	public Kit() {
		super();
		this.item = XMaterial.CHEST.parseItem();

	}

	public Kit(String name, String permission, ItemStack item, InventoryPers inventory) {
		super();
		this.name = name;
		this.permission = permission;
		this.item = item;
		this.inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public InventoryPers getInventory() {
		return inventory;
	}

	public void setInventory(InventoryPers inventory) {
		this.inventory = inventory;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "Kit [name=" + name + ", permission=" + permission + ", inventory=" + inventory + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kit other = (Kit) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
