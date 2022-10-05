package com.example.may.core.web.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author Evelina Tubalets
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class JsonConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T fromJsonString(final Class<T> type, final String jsonString) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, type);
        } catch (IOException e){
            log.error("Error during parsing response");
            throw new RuntimeException();
        }
    }

    public static <T> T fromMapToModel(final Class<T> type, final Map<String, String> properties) {
        return OBJECT_MAPPER.convertValue(properties, type);
    }

    /**
     * return value by name of json key.
     */
    public static String getValueFromJson(final String jsonString, final String keyName) {
        final JSONObject jsonResponse = new JSONObject(jsonString);
        return jsonResponse.getString(keyName);
    }
}
