package com.adri1711.randomevents.json;

import java.lang.reflect.Type;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
	
	@Override
	public ItemStack deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject obj = jsonElement.getAsJsonObject();
		ItemStack item = new ItemStack(Material.getMaterial(obj.get("type").getAsString()));
		item.setAmount(obj.get("amount").getAsInt());
		item.getItemMeta().setDisplayName(obj.get("displayName").getAsString());
		item.getItemMeta().setLore(Arrays.asList(obj.get("lore").getAsString().split(";")));
		return item;

	}

	@Override
	public JsonElement serialize(ItemStack item, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonObject obj = new JsonObject();
		obj.add("amount", new JsonPrimitive(item.getAmount()));

		obj.add("displayName", new JsonPrimitive(item.getItemMeta().getDisplayName()));
		String lore = "";
		for (String s : item.getItemMeta().getLore()) {
			lore += s + ";";
		}
		obj.add("lore", new JsonPrimitive(lore.substring(0, lore.length() - 1)));

		obj.add("type", new JsonPrimitive(item.getType().name()));

		return obj;
	}
}