# Espresso Automation Test Framework

A comprehensive Espresso test framework for Android UI automation testing with support for Page Object Model, Idling Resources, Espresso-Intents, utility classes, and data-driven testing.

## Features

### ✅ Page Object Model (POM)
- Base `BasePage` class for all page objects
- Example pages: `LoginPage`, `HomePage`
- Encapsulates UI interactions and page-specific logic
- Methods return `ViewInteraction` for flexible method chaining
- Fluent API support for readable test code

### ✅ Idling Resources
- `IdlingResourceManager` for centralized idling resource management
- `SimpleIdlingResource` for simple async operations
- Automatic registration/unregistration in base test class

### ✅ Espresso-Intents
- `EspressoIntentsHelper` for intent verification and stubbing
- Support for verifying intents with actions, components, data, extras, etc.
- Intent stubbing capabilities

### ✅ Utility Classes
- `ViewActionsHelper` - Frequently used view actions (click, type, swipe, etc.)
- `WaitHelper` - Wait operations and idling resource management
- Reusable methods for common test operations

### ✅ Data-Driven Testing
- `TestDataProvider` - Load test data from JSON files
- Support for data-driven testing with test data iteration
- Example data files in `src/test/resources/testdata/`

## Project Structure

```
EspressoAutomation/
├── build.gradle                 # Gradle build configuration
├── settings.gradle              # Gradle settings
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── automation/
│   │               ├── base/           # Base classes
│   │               │   └── BasePage.java
│   │               ├── pages/           # Page Object Models
│   │               │   ├── LoginPage.java
│   │               │   └── HomePage.java
│   │               ├── idling/          # Idling Resources
│   │               │   ├── IdlingResourceManager.java
│   │               │   └── SimpleIdlingResource.java
│   │               ├── utils/           # Utility classes
│   │               │   ├── EspressoIntentsHelper.java
│   │               │   ├── ViewActionsHelper.java
│   │               │   └── WaitHelper.java
│   │               ├── data/            # Data-driven testing
│   │               │   ├── TestDataProvider.java
│   │               │   └── TestDataModel.java
│   │               └── test/            # Automation tests
│   │                   ├── BaseTest.java
│   │                   ├── LoginTest.java
│   │                   └── DataDrivenLoginTest.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── automation/          # Unit tests only
│       └── resources/
│           └── testdata/
│               └── login_data.json
└── README.md
```

## Getting Started

### Prerequisites
- Android SDK
- Gradle 7.0+
- Java 17+

### Setup

1. **Update View IDs**: Replace placeholder view IDs (0) in page objects with actual view IDs from your app.

2. **Update Activity Classes**: Replace placeholder Activity classes in test classes with your actual Activity classes.

3. **Add Test Data**: Add your test data files to `src/test/resources/testdata/`

### Usage Examples

#### Using Page Object Model

```java
// Fluent chaining with page objects
LoginPage loginPage = new LoginPage();
loginPage.enterUsername("user@example.com")
         .enterPassword("password123")
         .clickLogin();

// Direct ViewInteraction chaining from BasePage methods
loginPage.typeText(R.id.username, "user@example.com")
         .check(matches(isDisplayed()));
loginPage.click(R.id.button)
         .check(matches(isEnabled()));

// Verification chaining
loginPage.verifyDisplayed(R.id.message)
         .check(matches(withText("Success")));
```

#### Using Idling Resources

```java
// Register idling resource
registerIdlingResource("network");

// Mark operation as busy
idlingResourceManager.increment("network");

// Perform async operation
// ... your async code ...

// Mark operation as idle
idlingResourceManager.decrement("network");
```

#### Using Espresso-Intents

```java
// Initialize intents
EspressoIntentsHelper.init();

// Verify intent was sent
EspressoIntentsHelper.verifyIntentWithAction(Intent.ACTION_VIEW);

// Stub intent result
Intent result = EspressoIntentsHelper.createResultIntent(Activity.RESULT_OK);
EspressoIntentsHelper.stubIntent(hasComponent("com.example.Activity"), result);
```

#### Using Utility Classes

```java
// Click by ID
ViewActionsHelper.clickById(R.id.button);

// Type text
ViewActionsHelper.typeTextById(R.id.editText, "Hello");

// Swipe
ViewActionsHelper.swipeDown(R.id.recyclerView);

// Wait
WaitHelper.waitFor(2000); // 2 seconds
```

#### Data-Driven Testing

```java
// Load test data
String username = TestDataProvider.getString("login_data.json", "valid_username");
String password = TestDataProvider.getString("login_data.json", "valid_password");

// Use in test
loginPage.login(username, password);
```

## Dependencies

- **Espresso Core**: 3.5.1
- **Espresso Intents**: 3.5.1
- **Espresso Idling Resource**: 3.5.1
- **JUnit**: 4.13.2 (for Android instrumentation tests with AndroidJUnit4 runner)
- **Gson**: 2.10.1 (for JSON parsing)
- **Apache POI**: 5.2.4 (for Excel/CSV support)

## Best Practices

### Espresso Testing Best Practices

1. **UI Waits**: Handled automatically by Espresso - no manual waits needed
2. **Network Waits**: Use `IdlingResource` or `CountingIdlingResource` for async network operations
3. **Animations**: Disable animations in test builds or synchronize with IdlingResource
4. **Long Operations**: Use `IdlingResource` or `CountingIdlingResource` for database operations, file I/O, etc.
5. **Avoid Bad Practices**: 
   - ❌ `Thread.sleep()` - Use IdlingResources instead
   - ❌ Manual polling loops - Use IdlingResources instead
   - ✅ Let Espresso handle UI synchronization automatically

### Framework Best Practices

1. **Page Objects**: Keep page objects focused on a single page/screen
2. **Method Chaining**: All `BasePage` methods return `ViewInteraction` for flexible chaining when needed
3. **Idling Resources**: Always register/unregister idling resources properly in `@Before`/`@After` methods
4. **Test Data**: Store test data in JSON files, not hardcoded in tests
5. **Utilities**: Use utility classes for common operations to reduce code duplication
6. **Base Test**: Extend `BaseTest` for common setup/teardown logic

## Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.automation.test.LoginTest"

# Run with Android test runner (when integrated into Android project)
./gradlew connectedAndroidTest
```

## Notes

- This framework is designed to be integrated into an Android project
- View IDs and Activity classes need to be updated to match your actual app
- Test data files should be placed in `src/test/resources/testdata/`
- The framework uses Java 17, ensure your project is configured accordingly

## License

This framework is provided as-is for use in your test automation projects.

