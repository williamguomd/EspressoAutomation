package com.automation.idling;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Manager for Idling Resources
 * Provides centralized management of idling resources for Espresso tests.
 * 
 * This singleton class manages CountingIdlingResource instances to handle async operations
 * in Espresso tests. It automatically registers/unregisters resources with Espresso.
 * 
 * Usage:
 * - Call increment() when starting an async operation (e.g., network call)
 * - Call decrement() when the operation completes
 * - Espresso will automatically wait until all registered resources are idle
 */
public class IdlingResourceManager {
    
    private static final IdlingResourceManager instance = new IdlingResourceManager();
    private final Map<String, CountingIdlingResource> idlingResources = new ConcurrentHashMap<>();
    
    /**
     * Private constructor for singleton pattern
     */
    private IdlingResourceManager() {}

    /**
     * Get the singleton instance of IdlingResourceManager
     * 
     * @return The singleton instance
     */
    public static IdlingResourceManager getInstance() {
        return instance;
    }

    /**
     * Register a new idling resource with Espresso.
     * If a resource with the same name already exists, this method does nothing.
     * 
     * @param name The name identifier for the idling resource (e.g., "network", "database")
     */
    public synchronized void registerIdlingResource(String name) {
        if (!idlingResources.containsKey(name)) {
            CountingIdlingResource resource = new CountingIdlingResource(name);
            idlingResources.put(name, resource);
            Espresso.registerIdlingResources(resource);
        }
    }

    /**
     * Get an idling resource by name.
     * If the resource doesn't exist, it will be created and registered automatically.
     * 
     * @param name The name identifier for the idling resource
     * @return The CountingIdlingResource instance
     */
    public CountingIdlingResource getIdlingResource(String name) {
        registerIdlingResource(name);
        return idlingResources.get(name);
    }

    /**
     * Increment the counter for an idling resource.
     * Call this when starting an async operation (e.g., before making a network request).
     * 
     * @param name The name identifier for the idling resource
     */
    public void increment(String name) {
        getIdlingResource(name).increment();
    }

    /**
     * Decrement the counter for an idling resource.
     * Call this when an async operation completes (e.g., after network response).
     * Only decrements if the resource is not already idle to prevent negative counts.
     * 
     * @param name The name identifier for the idling resource
     */
    public void decrement(String name) {
        CountingIdlingResource resource = getIdlingResource(name);
        if (!resource.isIdleNow()) {
            resource.decrement();
        }
    }

    /**
     * Check if an idling resource is currently idle.
     * 
     * @param name The name identifier for the idling resource
     * @return true if the resource is idle or doesn't exist, false otherwise
     */
    public boolean isIdle(String name) {
        CountingIdlingResource resource = idlingResources.get(name);
        return resource == null || resource.isIdleNow();
    }

    /**
     * Unregister an idling resource from Espresso and remove it from the manager.
     * 
     * @param name The name identifier for the idling resource to unregister
     */
    public synchronized void unregisterIdlingResource(String name) {
        CountingIdlingResource resource = idlingResources.remove(name);
        if (resource != null) {
            Espresso.unregisterIdlingResources(resource);
        }
    }

    /**
     * Clear all registered idling resources.
     * Unregisters all resources from Espresso and removes them from the manager.
     * Typically called in test teardown methods.
     */
    public synchronized void clearAll() {
        for (CountingIdlingResource resource : idlingResources.values()) {
            Espresso.unregisterIdlingResources(resource);
        }
        idlingResources.clear();
    }

    /**
     * Get all registered idling resources as an array.
     * Useful for bulk operations or cleanup.
     * 
     * @return Array of all registered IdlingResource instances
     */
    public IdlingResource[] getAllIdlingResources() {
        return idlingResources.values().toArray(new IdlingResource[0]);
    }
}


