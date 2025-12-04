package com.automation.test;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.automation.idling.IdlingResourceManager;
import com.automation.utils.EspressoIntentsHelper;
import com.automation.utils.ScreenshotHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Base test class for all Espresso tests
 * Provides common setup and teardown methods
 * Automatically takes screenshots on test failure
 */
public abstract class BaseTest {
    
    protected IdlingResourceManager idlingResourceManager;
    
    /**
     * TestWatcher rule that automatically takes screenshots when tests fail.
     * Screenshots are saved with the test class and method name.
     */
    @Rule
    public TestWatcher screenshotWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
            // Take screenshot on test failure
            String testClassName = description.getClassName();
            String testMethodName = description.getMethodName();
            String screenshotPath = ScreenshotHelper.takeScreenshot(testClassName, testMethodName);
            
            if (screenshotPath != null) {
                System.out.println("Test failed. Screenshot saved: " + screenshotPath);
            } else {
                System.err.println("Test failed but screenshot could not be saved");
            }
        }
    };
    
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
            IdlingRegistry.getInstance().unregister(resource);
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
        IdlingRegistry.getInstance().register(resource);
    }
    
    /**
     * Unregister an idling resource
     */
    protected void unregisterIdlingResource(String name) {
        CountingIdlingResource resource = idlingResourceManager.getIdlingResource(name);
        if (resource != null) {
            IdlingRegistry.getInstance().unregister(resource);
        }
    }
}

