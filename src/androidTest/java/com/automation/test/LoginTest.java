package com.automation.test;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.automation.data.TestDataProvider;
import com.automation.pages.LoginPage;
import com.automation.utils.EspressoIntentsHelper;
import com.automation.utils.ViewActionsHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Example test class demonstrating:
 * - Page Object Model
 * - Idling Resources
 * - Espresso-Intents
 * - Data-driven testing
 * - Utility classes
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest extends BaseTest {
    
    private LoginPage loginPage;
    
    
    @Test
    public void testLoginWithValidCredentials() {
        loginPage = new LoginPage();
        
        // Verify login page is displayed
        loginPage.verifyLoginPageDisplayed();
        
        // Mark network operation as busy
        idlingResourceManager.increment("network");
        
        // Perform login using Page Object Model
        String username = TestDataProvider.getString("login_data.json", "valid_username");
        String password = TestDataProvider.getString("login_data.json", "valid_password");
        loginPage.login(username, password);
        
        // Mark network operation as idle
        idlingResourceManager.decrement("network");
        
        
        // Verify intent was sent (if applicable)
        // EspressoIntentsHelper.verifyIntentWithComponent("com.example.HomeActivity");
    }
    
    @Test
    public void testLoginWithInvalidCredentials() {
        loginPage = new LoginPage();
        
        // Load test data
        String username = TestDataProvider.getString("login_data.json", "invalid_username");
        String password = TestDataProvider.getString("login_data.json", "invalid_password");
        
        // Perform login
        loginPage.login(username, password);
        
        // Verify error message
        loginPage.verifyErrorMessage();
    }
    
    @Test
    public void testLoginUsingUtilityMethods() {
        // Example using utility methods for text-based operations
        // Note: For ID-based operations, use BasePage methods in page objects
        String username = TestDataProvider.getString("login_data.json", "valid_username");
        String password = TestDataProvider.getString("login_data.json", "valid_password");
        
        // Example: Using text-based utilities (not available in BasePage)
        ViewActionsHelper.clickByText("Login");
        ViewActionsHelper.verifyTextDisplayed("Welcome");
        
        // For ID-based operations, prefer using page objects:
        // loginPage.typeText(R.id.username, username);
        // loginPage.click(R.id.loginButton);
    }
}

