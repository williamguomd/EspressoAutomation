package com.automation.test;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.automation.data.TestDataProvider;
import com.automation.pages.LoginPage;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

/**
 * Example data-driven test using AndroidJUnit4 runner
 * This demonstrates how to run the same test with different data sets
 * Note: For parameterized tests with AndroidJUnit4, consider using AndroidJUnitParams library
 * or iterate through test data within test methods as shown below
 */
@RunWith(AndroidJUnit4.class)
public class DataDrivenLoginTest extends BaseTest {
    
    private LoginPage loginPage;
    
    @Test
    public void testLoginWithData() {
        // Load test data from JSON
        List<Map<String, Object>> testUsers = TestDataProvider.getDataList(
                "login_data.json",
                "test_users",
                Map.class
        );
        
        // Iterate through test data
        for (Map<String, Object> user : testUsers) {
            String username = (String) user.get("username");
            String password = (String) user.get("password");
            String expectedResult = (String) user.get("expected_result");
            
            loginPage = new LoginPage();
            
            // Perform login with test data
            loginPage.login(username, password);
            
            // Verify result based on expected outcome
            if ("success".equals(expectedResult)) {
                // Verify successful login (e.g., navigate to home page)
                // Add your verification logic here
            } else if ("failure".equals(expectedResult)) {
                // Verify error message is displayed
                loginPage.verifyErrorMessage();
            }
        }
    }
}

