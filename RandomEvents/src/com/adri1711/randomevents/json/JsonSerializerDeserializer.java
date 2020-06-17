package com.adri1711.randomevents.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.datatype.XMLGregorianCalendar;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class JsonSerializerDeserializer<T> {
	/**
	 * Componente Json de Google
	 */
	private Gson gson;

	/**
	 * Constructor
	 */

	public JsonSerializerDeserializer() {
		// Se inicializa proporcionadole un adaptador para la clase
		// XMLGregorianCalendar
		//

		gson = new GsonBuilder()
				.registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter.Serializer())
				.serializeNulls()
				.registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarAdapter.Deserializer())
				.registerTypeAdapter(BigDecimal.class, new BigDecimalAdapter())
				.registerTypeAdapter(Location.class, new LocationTypeAdapter())
				.registerTypeAdapter(ItemStack.class, new ItemStackTypeAdapter()).create();

	}

	/**
	 * Serializa a Json una lista de objetos generica
	 * 
	 * @param objectListToSerialize
	 * @return
	 */

	public String serialize(List<T> objectListToSerialize) {

		String serializedString = null;

		if (objectListToSerialize != null && !objectListToSerialize.isEmpty()) {
			Type listOfTestObject = new TypeToken<List<T>>() {
			}.getType();

			serializedString = gson.toJson(objectListToSerialize, listOfTestObject);
		}

		return serializedString;

	}

	public String serialize(T objectToSerialize) {

		String serializedString = null;

		if (objectToSerialize != null) {

			serializedString = gson.toJson(objectToSerialize);
		}

		return serializedString;

	}

	/**
	 * Deserializa de Json una lista de objetos generica
	 * 
	 * @param stringToDeserialize
	 * @return
	 */

	public List<T> deserialize(String stringToDeserialize, Type listType) {

		List<T> deserializedList = null;

		deserializedList = gson.fromJson(stringToDeserialize, listType);

		return deserializedList;

	}

	public T deserializeSingleObject(String stringToDeserialize, Class<T> clazz) {

		T deserializedObject = null;

		deserializedObject = gson.fromJson(stringToDeserialize, clazz);

		return deserializedObject;

	}
	
	private static class ItemStackGsonAdapter extends TypeAdapter<ItemStack> {
		 
        private static Type seriType = new TypeToken<Map<String, Object>>(){}.getType();
        private final static String CLASS_KEY = "SERIAL-ADAPTER-CLASS-KEY";
        private static Gson g = new Gson();

        @Override
        public void write(JsonWriter jsonWriter, ItemStack itemStack) throws IOException {
            if(itemStack == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(getRaw(itemStack));
        }
 
        @Override
        public ItemStack read(JsonReader jsonReader) throws IOException {
            if(jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return fromRaw(jsonReader.nextString());
        }
 
        private String getRaw (ItemStack item) {
            Map<String, Object> serial = item.serialize();
 
            if(serial.get("meta") != null) {
                ItemMeta itemMeta = item.getItemMeta();
 
                Map<String, Object> originalMeta = itemMeta.serialize();
                Map<String, Object> meta = new HashMap<String, Object>();
                for(Entry<String, Object> entry : originalMeta.entrySet())
                    meta.put(entry.getKey(), entry.getValue());
                Object o;
                for(Entry<String, Object> entry : meta.entrySet()) {
                    o = entry.getValue();
                    if(o instanceof ConfigurationSerializable) {
                        ConfigurationSerializable serializable = (ConfigurationSerializable) o;
                        Map<String, Object> serialized = recursiveSerialization(serializable);
                        meta.put(entry.getKey(), serialized);
                    }
                }
                serial.put("meta", meta);
            }
 
            return g.toJson(serial);
        }
 
        private ItemStack fromRaw (String raw) {
            Map<String, Object> keys = g.fromJson(raw, seriType);
 
            if(keys.get("amount") != null) {
                Double d = (Double) keys.get("amount");
                Integer i = d.intValue();
                keys.put("amount", i);
            }
 
            ItemStack item;
            try {
                item = ItemStack.deserialize(keys);
            }catch(Exception e) {
                return null;
            }
 
            if(item == null)
                return null;
 
            if(keys.containsKey("meta")) {
                Map<String, Object> itemmeta = (Map<String, Object>) keys.get("meta");
                itemmeta = recursiveDoubleToInteger(itemmeta);
                ItemMeta meta = (ItemMeta) ConfigurationSerialization.deserializeObject(itemmeta, ConfigurationSerialization.getClassByAlias("ItemMeta"));
                item.setItemMeta(meta);
            }
 
            return item;
        }
        private static Map<String, Object> recursiveDoubleToInteger (Map<String, Object> originalMap) {
            Map<String, Object> map = new HashMap<String, Object>();
            for(Entry<String, Object> entry : originalMap.entrySet()) {
                Object o = entry.getValue();
                if(o instanceof Double) {
                    Double d = (Double) o;
                    Integer i = d.intValue();
                    map.put(entry.getKey(), i);
                }else if(o instanceof Map) {
                    Map<String, Object> subMap = (Map<String, Object>) o;
                    map.put(entry.getKey(), recursiveDoubleToInteger(subMap));
                }else{
                    map.put(entry.getKey(), o);
                }
            }
            return map;
        }
        private static Map<String, Object> recursiveSerialization (ConfigurationSerializable o) {
            Map<String, Object> originalMap = o.serialize();
            Map<String, Object> map = new HashMap<String, Object>();
            for(Entry<String, Object> entry : originalMap.entrySet()) {
                Object o2 = entry.getValue();
                if(o2 instanceof ConfigurationSerializable) {
                    ConfigurationSerializable serializable = (ConfigurationSerializable) o2;
                    Map<String, Object> newMap = recursiveSerialization(serializable);
                    newMap.put(CLASS_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
                    map.put(entry.getKey(), newMap);
                }
            }
            map.put(CLASS_KEY, ConfigurationSerialization.getAlias(o.getClass()));
            return map;
        }
    }

}
