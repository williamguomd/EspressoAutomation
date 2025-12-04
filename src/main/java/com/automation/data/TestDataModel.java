package com.automation.data;

import java.util.Map;

/**
 * Base interface for test data models
 */
public interface TestDataModel {
    
    /**
     * Convert to map for easy access
     */
    Map<String, Object> toMap();
    
    /**
     * Validate test data
     */
    boolean isValid();
}

