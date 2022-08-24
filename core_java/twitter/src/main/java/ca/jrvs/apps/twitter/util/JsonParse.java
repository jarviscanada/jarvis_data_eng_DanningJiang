package ca.jrvs.apps.twitter.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonParse {
    /**
     * Convert a java object to JSON string
     * @param object java object
     * @param prettyJson boolean
     * @param includeNullValues boolean
     * @return JSON String
     * @throws JsonProcessingException
     */
    public static String toJson(Object object,boolean prettyJson,boolean includeNullValues) throws JsonProcessingException{
        ObjectMapper m = new ObjectMapper();
        if(!includeNullValues){
            m.setSerializationInclusion(Include.NON_NULL);
        }
        if (prettyJson){
            m.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return m.writeValueAsString(object);
    }


    /**
     * Parse JSON string to a java object
     * @param json JSON str
     * @param clazz object class
     * @param <T> Type
     * @return Object
     * @throws IOException
     */

    public static <T> T toObjectFromJson(String json,Class clazz) throws IOException{
        ObjectMapper m = new ObjectMapper();
        return (T) m.readValue(json,clazz);
    }
}