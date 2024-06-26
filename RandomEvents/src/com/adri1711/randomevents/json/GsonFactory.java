package com.adri1711.randomevents.json;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
 
/* *
* Created by Joshua Bell (RingOfStorms)
*
* Post explaining here: [URL]http://bukkit.org/threads/gsonfactory-gson-that-works-on-itemstack-potioneffect-location-objects.331161/[/URL]
* */
public class GsonFactory {
 
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public static @interface Ignore {}
 
    /*
    - I want to not use Bukkit parsing for most objects... it's kind of clunky
    - Instead... I want to start using any of Mojang's tags
    - They're really well documented + built into MC, and handled by them.
    - Rather than kill your old code, I'm going to write TypeAdapaters using Mojang's stuff.
     */
 
    private static Gson g = new Gson();
 
    private final static String CLASS_KEY = "SERIAL-ADAPTER-CLASS-KEY";
 
    private static Gson prettyGson;
    private static Gson compactGson;
 
    /**
     * Returns a Gson instance for use anywhere with new line pretty printing
     * <p>
     *    Use @GsonIgnore in order to skip serialization and deserialization
     * </p>
     * @return a Gson instance
     */
    public static Gson getPrettyGson () {
        if (prettyGson == null)
            prettyGson = new GsonBuilder().enableComplexMapKeySerialization()
            .addSerializationExclusionStrategy(new ExposeExlusion())
                    .addDeserializationExclusionStrategy(new ExposeExlusion())
                    .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
                    .registerTypeAdapter(PotionEffect.class, new PotionEffectGsonAdapter())
                    .registerTypeAdapter(Location.class, new LocationGsonAdapter())
                    .registerTypeAdapter(Date.class, new DateGsonAdapter())
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();
        return prettyGson;
    }
 
    /**
     * Returns a Gson instance for use anywhere with one line strings
     * <p>
     *    Use @GsonIgnore in order to skip serialization and deserialization
     * </p>
     * @return a Gson instance
     */
    public static Gson getCompactGson () {
        if(compactGson == null)
            compactGson = new GsonBuilder().enableComplexMapKeySerialization()
            .addSerializationExclusionStrategy(new ExposeExlusion())
                    .addDeserializationExclusionStrategy(new ExposeExlusion())
                    .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackGsonAdapter())
                    .registerTypeAdapter(PotionEffect.class, new PotionEffectGsonAdapter())
                    .registerTypeAdapter(Location.class, new LocationGsonAdapter())
                    .registerTypeAdapter(Date.class, new DateGsonAdapter())
                    .disableHtmlEscaping()
                    .create();
        return compactGson;
    }
 
    /**
     * Creates a new instance of Gson for use anywhere
     * <p>
     *    Use @GsonIgnore in order to skip serialization and deserialization
     * </p>
     * @return a Gson instance
     */
    public static Gson getNewGson(boolean prettyPrinting) {
        GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(new ExposeExlusion())
                .addDeserializationExclusionStrategy(new ExposeExlusion())
                .registerTypeHierarchyAdapter(ItemStack.class, new NewItemStackAdapter())
                .disableHtmlEscaping();
        if (prettyPrinting)
            builder.setPrettyPrinting();
        return builder.create();
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
 
    private static class ExposeExlusion implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            final Ignore ignore = fieldAttributes.getAnnotation(Ignore.class);
            if(ignore != null)
                return true;
            final Expose expose = fieldAttributes.getAnnotation(Expose.class);
            return expose != null && (!expose.serialize() || !expose.deserialize());
        }
 
        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }
 
 
    private static class NewItemStackAdapter extends TypeAdapter<ItemStack> {
        @Override
        public void write(JsonWriter jsonWriter, ItemStack itemStack) throws IOException {
            if (itemStack == null) {
                jsonWriter.nullValue();
                return;
            }
            ItemStack item = itemStack;
           
            try {
                jsonWriter.beginObject();
                jsonWriter.name("type");
 
                jsonWriter.value(itemStack.getType().toString()); //I hate using this - but
                jsonWriter.name("amount");
 
                jsonWriter.value(itemStack.getAmount());
                jsonWriter.name("data");
 
 
                jsonWriter.value(itemStack.getDurability());
               
                jsonWriter.endObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
 
        @Override
        public ItemStack read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                return null;
            }
            jsonReader.beginObject();
            jsonReader.nextName();
            Material type = Material.getMaterial(jsonReader.nextString());
            jsonReader.nextName();
            int amount = jsonReader.nextInt();
            jsonReader.nextName();
            int data = jsonReader.nextInt();
            ItemStack item= new ItemStack(type);
            item.setAmount(amount);
            item.setDurability(Integer.valueOf(data).shortValue());
//            ItemStack item = new ItemStack(type, amount, data);
            jsonReader.nextName();
            String next = jsonReader.nextString();
            
            jsonReader.endObject();
            return item;
        }
    }
 
    private static class ItemStackGsonAdapter extends TypeAdapter<ItemStack> {
 
        private static Type seriType = new TypeToken<Map<String, Object>>(){}.getType();
 
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
    }
    
    
    
    
    
    
    
    private static class ItemStackAdapter extends TypeAdapter<ItemStack>{

    	private static Type seriType = new TypeToken<Map<String, Object>>(){}.getType();
    	
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

    	@SuppressWarnings("unchecked")
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
    	
    	
    	@SuppressWarnings("unchecked")
    	private static Map<String, Object> recursiveDoubleToInteger(Map<String, Object> originalMap) {
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
    	
    	private static Map<String, Object> recursiveSerialization(ConfigurationSerializable o) {
    		Map<String, Object> originalMap = o.serialize();
    		Map<String, Object> map = new HashMap<String, Object>();
    		for(Entry<String, Object> entry : originalMap.entrySet()) {
    			Object o2 = entry.getValue();
    			if(o2 instanceof ConfigurationSerializable) {
    				ConfigurationSerializable serializable = (ConfigurationSerializable) o2;
    				Map<String, Object> newMap = recursiveSerialization(serializable);
    				newMap.put("SERIAL-ADAPTER-CLASS-KEY", ConfigurationSerialization.getAlias(serializable.getClass()));
    				map.put(entry.getKey(), newMap);
    			}
    		}
    		map.put("SERIAL-ADAPTER-CLASS-KEY", ConfigurationSerialization.getAlias(o.getClass()));
    		return map;
    	}
    	
    }
 
    private static class PotionEffectGsonAdapter extends TypeAdapter<PotionEffect> {
 
        private static Type seriType = new TypeToken<Map<String, Object>>(){}.getType();
 
        private static String TYPE = "effect";
        private static String DURATION = "duration";
        private static String AMPLIFIER = "amplifier";
        private static String AMBIENT = "ambient";
 
        @Override
        public void write(JsonWriter jsonWriter, PotionEffect potionEffect) throws IOException {
            if(potionEffect == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(getRaw(potionEffect));
        }
 
        @Override
        public PotionEffect read(JsonReader jsonReader) throws IOException {
            if(jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return fromRaw(jsonReader.nextString());
        }
 
        private String getRaw (PotionEffect potion) {
            Map<String, Object> serial = potion.serialize();
 
            return g.toJson(serial);
        }
 
        private PotionEffect fromRaw (String raw) {
            Map<String, Object> keys = g.fromJson(raw, seriType);
            return new PotionEffect(PotionEffectType.getById(((Double) keys.get(TYPE)).intValue()), ((Double) keys.get(DURATION)).intValue(), ((Double) keys.get(AMPLIFIER)).intValue(),  (Boolean) keys.get(AMBIENT));
        }
    }
 
    private static class LocationGsonAdapter extends TypeAdapter<Location> {
 
        private static Type seriType = new TypeToken<Map<String, Object>>(){}.getType();
 
        private static String UUID = "uuid";
        private static String X = "x";
        private static String Y = "y";
        private static String Z = "z";
        private static String YAW = "yaw";
        private static String PITCH = "pitch";
 
        @Override
        public void write(JsonWriter jsonWriter, Location location) throws IOException {
            if(location == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(getRaw(location));
        }
 
        @Override
        public Location read(JsonReader jsonReader) throws IOException {
            if(jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return fromRaw(jsonReader.nextString());
        }
 
        private String getRaw (Location location) {
            Map<String, Object> serial = new HashMap<String, Object>();
            
            serial.put(UUID, location.getWorld()!=null && location.getWorld().getUID()!=null ?location.getWorld().getUID().toString():null);
            serial.put(X, Double.toString(location.getX()));
            serial.put(Y, Double.toString(location.getY()));
            serial.put(Z, Double.toString(location.getZ()));
            serial.put(YAW, Float.toString(location.getYaw()));
            serial.put(PITCH, Float.toString(location.getPitch()));
            return g.toJson(serial);
        }
 
        private Location fromRaw (String raw) {
            Map<String, Object> keys = g.fromJson(raw, seriType);
            World w =	Bukkit.getWorld(java.util.UUID.fromString((String) keys.get(UUID))); 
            return new Location(w, Double.parseDouble((String) keys.get(X)), Double.parseDouble((String) keys.get(Y)), Double.parseDouble((String) keys.get(Z)),
                    Float.parseFloat((String) keys.get(YAW)), Float.parseFloat((String) keys.get(PITCH)));
        }
    }
 
    private static class DateGsonAdapter extends TypeAdapter<Date> {
        @Override
        public void write(JsonWriter jsonWriter, Date date) throws IOException {
            if(date == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(date.getTime());
        }
 
        @Override
        public Date read(JsonReader jsonReader) throws IOException {
            if(jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return new Date(jsonReader.nextLong());
        }
    }
}
 
 
