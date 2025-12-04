package com.automation.utils;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;

/**
 * Helper class for IdlingResource management
 * 
 * Best Practices:
 * - UI waits: Handled automatically by Espresso
 * - Network waits: Use IdlingResource or CountingIdlingResource
 * - Animations: Disable or synchronize with IdlingResource
 * - Long operations: Use IdlingResource or CountingIdlingResource
 * - AVOID: Thread.sleep() and manual polling loops (bad practice)
 */
public class WaitHelper {
    
    /**
     * Register an idling resource with Espresso
     * This allows Espresso to wait for async operations to complete
     * 
     * @param idlingResource The idling resource to register
     */
    public static void registerIdlingResource(IdlingResource idlingResource) {
        Espresso.registerIdlingResources(idlingResource);
    }
    
    /**
     * Unregister an idling resource from Espresso
     * 
     * @param idlingResource The idling resource to unregister
     */
    public static void unregisterIdlingResource(IdlingResource idlingResource) {
        Espresso.unregisterIdlingResources(idlingResource);
    }
    
    /**
     * Register multiple idling resources
     * 
     * @param idlingResources The idling resources to register
     */
    public static void registerIdlingResources(IdlingResource... idlingResources) {
        Espresso.registerIdlingResources(idlingResources);
    }
    
    /**
     * Unregister multiple idling resources
     * 
     * @param idlingResources The idling resources to unregister
     */
    public static void unregisterIdlingResources(IdlingResource... idlingResources) {
        Espresso.unregisterIdlingResources(idlingResources);
    }
}

