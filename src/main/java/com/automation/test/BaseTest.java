package com.automation.test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.automation.idling.IdlingResourceManager;
import com.automation.utils.EspressoIntentsHelper;

import org.junit.After;
import org.junit.Before;

/**
 * Base test class for all Espresso tests
 * Provides common setup and teardown methods
 */
public abstract class BaseTest {
    
    protected IdlingResourceManager idlingResourceManager;
    
    public BaseTest() {
        this.idlingResourceManager = IdlingResourceManager.getInstance();
    }
    
    @Before
    public void setUp() {
        // Initialize Espresso Intents
        EspressoIntentsHelper.init();
        
        // Clear any existing idling resources
        idlingResourceManager.clearAll();
        
        // Register common idling resources if needed
        setupIdlingResources();
    }
    
    @After
    public void tearDown() {
        // Release Espresso Intents
        EspressoIntentsHelper.release();
        
        // Unregister all idling resources
        CountingIdlingResource[] resources = 
                (CountingIdlingResource[]) idlingResourceManager.getAllIdlingResources();
        for (CountingIdlingResource resource : resources) {
            Espresso.unregisterIdlingResources(resource);
        }
        
        idlingResourceManager.clearAll();
    }
    
    /**
     * Set up idling resources for tests
     * Register common idling resources for network calls, database operations, etc.
     * Override in subclasses to add test-specific idling resources
     */
    protected void setupIdlingResources() {
        // Register common idling resources that most tests will need
        registerIdlingResource("network");
        registerIdlingResource("database");
    }
    
    /**
     * Register an idling resource
     */
    protected void registerIdlingResource(String name) {
        CountingIdlingResource resource = idlingResourceManager.getIdlingResource(name);
        Espresso.registerIdlingResources(resource);
    }
    
    /**
     * Unregister an idling resource
     */
    protected void unregisterIdlingResource(String name) {
        CountingIdlingResource resource = idlingResourceManager.getIdlingResource(name);
        if (resource != null) {
            Espresso.unregisterIdlingResources(resource);
        }
    }
}

