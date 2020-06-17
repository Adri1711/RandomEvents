package com.adri1711.randomevents.json;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;



 public class XMLGregorianCalendarAdapter
 {
  public static class Serializer implements JsonSerializer<XMLGregorianCalendar>
  {
   public Serializer()
   {
    super();
   }

   public JsonElement serialize(XMLGregorianCalendar t, Type type,
                            JsonSerializationContext jsonSerializationContext)
   {	
	   Date date = t.toGregorianCalendar().getTime();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
 
    return new JsonPrimitive(sdf.format( date ));
   }
 }
   public static class Deserializer implements JsonDeserializer<XMLGregorianCalendar>
   {
	   
     public XMLGregorianCalendar deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext)
     {
         try
          {
             return DatatypeFactory.newInstance().newXMLGregorianCalendar(jsonElement.getAsString());
          }
          catch(Exception ex)
          {
              ex.printStackTrace();
              return null;
          }
        }
      }
    }
