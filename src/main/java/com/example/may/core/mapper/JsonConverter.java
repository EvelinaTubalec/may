package com.example.may.core.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@Component
public class JsonConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * convert string to model by objectName
     */
    public static <T> T stringToModel(final Class<T> type, final String someString, final String objectName){
        final JSONObject jsonObject = getJsonObjectFromString(someString, objectName);
        return jsonObjectToModel(type, jsonObject);
    }

    public static JSONObject getJsonObjectFromString(final String someString, final String objectName) {
        final JSONObject jsonResponse = new JSONObject(someString);
        return jsonResponse.getJSONObject(objectName);
    }

    public static <T> T jsonObjectToModel(final Class<T> type, final JSONObject jsonObject) {
        final byte[] jsonData = jsonObject.toString().getBytes();
        try {
            return OBJECT_MAPPER.readValue(jsonData, type);
        } catch (IOException e){
            log.error("Error during parsing response");
            throw new RuntimeException();
        }
    }

    /**
     * return value by name of json key.
     */
    public static String getValue(final String someString, final String keyName) {
        final JSONObject jsonResponse = new JSONObject(someString);
        return jsonResponse.getString(keyName);
    }
}
