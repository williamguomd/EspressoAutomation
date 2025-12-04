package com.automation.pages;

import com.automation.base.BasePage;

/**
 * Example Login Page using Page Object Model
 * 
 * Note: BasePage methods return ViewInteraction for chaining flexibility.
 * Page object methods can use them directly or chain additional operations.
 */
public class LoginPage extends BasePage {
    
    // View IDs - these should match your actual app's view IDs
    private static final int USERNAME_FIELD = 0; // Replace with actual ID
    private static final int PASSWORD_FIELD = 0; // Replace with actual ID
    private static final int LOGIN_BUTTON = 0; // Replace with actual ID
    private static final int ERROR_MESSAGE = 0; // Replace with actual ID
    
    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        typeText(USERNAME_FIELD, username);
        return this;
    }
    
    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        typeText(PASSWORD_FIELD, password);
        return this;
    }
    
    /**
     * Click login button
     */
    public void clickLogin() {
        click(LOGIN_BUTTON);
    }
    
    /**
     * Perform login
     */
    public void login(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
    
    /**
     * Verify error message is displayed
     */
    public void verifyErrorMessage() {
        verifyDisplayed(ERROR_MESSAGE);
    }
    
    /**
     * Verify login page is displayed
     */
    public void verifyLoginPageDisplayed() {
        verifyDisplayed(USERNAME_FIELD);
        verifyDisplayed(PASSWORD_FIELD);
        verifyDisplayed(LOGIN_BUTTON);
    }
}

