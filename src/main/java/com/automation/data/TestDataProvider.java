package com.automation.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test Data Provider for data-driven testing
 * Supports JSON, CSV, and in-memory data
 */
public class TestDataProvider {
    
    private static final Gson gson = new Gson();
    private static final Map<String, JsonObject> dataCache = new HashMap<>();
    
    /**
     * Load test data from JSON file
     */
    public static JsonObject loadJsonData(String fileName) {
        if (dataCache.containsKey(fileName)) {
            return dataCache.get(fileName);
        }
        
        try {
            InputStream inputStream = TestDataProvider.class.getClassLoader()
                    .getResourceAsStream("testdata/" + fileName);
            if (inputStream == null) {
                throw new RuntimeException("Test data file not found: " + fileName);
            }
            
            JsonObject jsonObject = JsonParser.parseReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8)
            ).getAsJsonObject();
            
            dataCache.put(fileName, jsonObject);
            return jsonObject;
        } catch (Exception e) {
            throw new RuntimeException("Error loading test data from " + fileName, e);
        }
    }
    
    /**
     * Get test data as a specific type
     */
    public static <T> T getData(String fileName, String key, Class<T> type) {
        JsonObject jsonObject = loadJsonData(fileName);
        JsonElement element = jsonObject.get(key);
        return gson.fromJson(element, type);
    }
    
    /**
     * Get test data as a list
     */
    public static <T> List<T> getDataList(String fileName, String key, Class<T> type) {
        JsonObject jsonObject = loadJsonData(fileName);
        JsonElement element = jsonObject.get(key);
        List<T> result = new ArrayList<>();
        
        if (element.isJsonArray()) {
            for (JsonElement item : element.getAsJsonArray()) {
                result.add(gson.fromJson(item, type));
            }
        }
        
        return result;
    }
    
    /**
     * Get string value from test data
     */
    public static String getString(String fileName, String key) {
        JsonObject jsonObject = loadJsonData(fileName);
        return jsonObject.get(key).getAsString();
    }
    
    /**
     * Get integer value from test data
     */
    public static int getInt(String fileName, String key) {
        JsonObject jsonObject = loadJsonData(fileName);
        return jsonObject.get(key).getAsInt();
    }
    
    /**
     * Get boolean value from test data
     */
    public static boolean getBoolean(String fileName, String key) {
        JsonObject jsonObject = loadJsonData(fileName);
        return jsonObject.get(key).getAsBoolean();
    }
    
    /**
     * Create test data object from map
     */
    public static <T> T createFromMap(Map<String, Object> data, Class<T> type) {
        return gson.fromJson(gson.toJsonTree(data), type);
    }
    
    /**
     * Clear data cache
     */
    public static void clearCache() {
        dataCache.clear();
    }
}

