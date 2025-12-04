package com.automation.utils;

import android.content.Intent;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.intent.matcher.UriMatchers.hasHost;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;

import org.hamcrest.Matcher;

/**
 * Helper class for Espresso-Intents
 * Provides utilities for intent verification and stubbing
 */
public class EspressoIntentsHelper {
    
    /**
     * Initialize Intents
     */
    public static void init() {
        Intents.init();
    }
    
    /**
     * Release Intents
     */
    public static void release() {
        Intents.release();
    }
    
    /**
     * Verify an intent was sent with specific action
     */
    public static void verifyIntentWithAction(String action) {
        intended(hasAction(action));
    }
    
    /**
     * Verify an intent was sent with specific component
     */
    public static void verifyIntentWithComponent(String componentName) {
        intended(hasComponent(componentName));
    }
    
    /**
     * Verify an intent was sent with specific data
     */
    public static void verifyIntentWithData(String data) {
        intended(hasData(data));
    }
    
    /**
     * Verify an intent was sent with specific extra
     */
    public static void verifyIntentWithExtra(String key, Object value) {
        intended(hasExtra(key, value));
    }
    
    /**
     * Verify an intent was sent with specific type
     */
    public static void verifyIntentWithType(String type) {
        intended(hasType(type));
    }
    
    /**
     * Verify an intent was sent with specific host
     */
    public static void verifyIntentWithHost(String host) {
        intended(hasData(hasHost(host)));
    }
    
    /**
     * Verify an intent with multiple matchers
     */
    public static void verifyIntent(Matcher<Intent>... matchers) {
        intended(allOf(matchers));
    }
    
    /**
     * Stub an intent result
     */
    public static void stubIntent(Matcher<Intent> matcher, Intent result) {
        intending(matcher).respondWith(result);
    }
    
    /**
     * Stub all intents with a default result
     */
    public static void stubAllIntents(Intent result) {
        intending(any(Intent.class)).respondWith(result);
    }
    
    /**
     * Create a result intent
     */
    public static Intent createResultIntent(int resultCode) {
        Intent result = new Intent();
        result.putExtra("resultCode", resultCode);
        return result;
    }
}

