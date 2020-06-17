package com.adri1711.randomevents.json;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BigDecimalAdapter implements JsonSerializer<BigDecimal>{

	@Override
	public JsonElement serialize(BigDecimal src, Type typeOfSrc, JsonSerializationContext context) {
		 
	
		
	 return new JsonPrimitive(src.toPlainString());
		
	}
	
	

}
