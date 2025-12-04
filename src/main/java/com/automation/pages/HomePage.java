package com.automation.pages;

import com.automation.base.BasePage;

/**
 * Example Home Page using Page Object Model
 */
public class HomePage extends BasePage {
    
    // View IDs - these should match your actual app's view IDs
    private static final int WELCOME_MESSAGE = 0; // Replace with actual ID
    private static final int MENU_BUTTON = 0; // Replace with actual ID
    private static final int PROFILE_BUTTON = 0; // Replace with actual ID
    
    /**
     * Verify home page is displayed
     */
    public void verifyHomePageDisplayed() {
        verifyDisplayed(WELCOME_MESSAGE);
    }
    
    /**
     * Click menu button
     */
    public void clickMenu() {
        click(MENU_BUTTON);
    }
    
    /**
     * Click profile button
     */
    public void clickProfile() {
        click(PROFILE_BUTTON);
    }
    
    /**
     * Verify welcome message
     */
    public void verifyWelcomeMessage() {
        verifyDisplayed(WELCOME_MESSAGE);
    }
}

