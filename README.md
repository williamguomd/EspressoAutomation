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
- `ScreenshotHelper` - Automatic screenshot capture on test failures
- Reusable methods for common test operations

### ✅ Screenshot on Failure
- Automatic screenshot capture when tests fail
- Screenshots saved with test class and method name
- Manual screenshot capture support for debugging
- Screenshots saved to device storage (accessible via ADB)

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
│   │               │   ├── WaitHelper.java
│   │               │   └── ScreenshotHelper.java
│   │               ├── data/            # Data-driven testing
│   │               │   ├── TestDataProvider.java
│   │               │   └── TestDataModel.java
│   ├── androidTest/                     # Android instrumentation tests
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── automation/
│   │   │           └── test/
│   │   │               ├── BaseTest.java
│   │   │               ├── LoginTest.java
│   │   │               └── DataDrivenLoginTest.java
│   │   └── resources/
│   │       └── testdata/
│   │           └── login_data.json
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
- Android SDK (API 21+)
- Android Build Tools
- Gradle 7.0+ (or use Gradle Wrapper)
- Java 17+
- Connected Android device or emulator (for running instrumentation tests)

### Building the Project

#### Quick Start

```bash
# If Gradle Wrapper exists
./gradlew build

# If using Gradle directly
gradle build

# Clean and rebuild
./gradlew clean build
```

#### Build Output

After building, the AAR library file will be in:
- `build/outputs/aar/EspressoAutomation-release.aar` (release variant)
- `build/outputs/aar/EspressoAutomation-debug.aar` (debug variant)

#### Common Build Commands

```bash
# Build the library
./gradlew assemble

# Build and run unit tests
./gradlew build test

# Build and run all tests (requires device/emulator for instrumentation tests)
./gradlew build allTests

# Clean build artifacts
./gradlew clean
```

**For detailed build instructions, see [BUILD.md](BUILD.md)**

### Setup

1. **Update View IDs**: Replace placeholder view IDs (0) in page objects with actual view IDs from your app.

2. **Update Activity Classes**: Replace placeholder Activity classes in test classes with your actual Activity classes.

3. **Add Test Data**: Add your test data files to `src/test/resources/testdata/`

## Screenshot on Failure

The framework automatically captures screenshots when tests fail, making debugging easier.

### Automatic Screenshot Capture

Screenshots are automatically taken when a test fails using JUnit's `TestWatcher` rule in `BaseTest`. No additional code is needed in your test classes.

**Features:**
- Automatic capture on test failure
- Screenshots named with test class and method name
- Timestamp included in filename
- Saved to device storage (accessible via ADB)

### Screenshot Location

Screenshots are saved to:
- **Primary location**: `/sdcard/Pictures/screenshots/` (external storage)
- **Fallback**: App's files directory if external storage is unavailable

### Accessing Screenshots

```bash
# Pull screenshots from device/emulator
adb pull /sdcard/Pictures/screenshots/ ./screenshots/

# List screenshots on device
adb shell ls -la /sdcard/Pictures/screenshots/

# View screenshot directory path (from test output)
# Check test logs for: "Screenshot saved: /path/to/screenshot.png"
```

### Manual Screenshot Capture

You can also take screenshots manually during tests for debugging:

```java
import com.automation.utils.ScreenshotHelper;

@Test
public void testExample() {
    // Take a screenshot with custom name
    ScreenshotHelper.takeScreenshot("before_login");
    
    // Perform test actions
    loginPage.login("user", "pass");
    
    // Take another screenshot
    ScreenshotHelper.takeScreenshot("after_login");
}
```

### Screenshot Naming

Automatic screenshots are named with the pattern:
```
<TestClassName>_<TestMethodName>_<timestamp>.png
```

Example: `LoginTest_testLoginWithValidCredentials_2024-01-15_14-30-45.png`

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

### Basic Test Commands

```bash
# Run all unit tests (from src/test/java)
./gradlew test

# Run automation tests (Android instrumentation tests)
# Requires a connected device or emulator
./gradlew connectedAndroidTest

# Run automation tests via custom task
./gradlew automationTest
```

### Running Specific Tests

```bash
# Run a specific unit test class
./gradlew test --tests "com.automation.test.LoginTest"

# Run a specific instrumentation test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.automation.test.LoginTest

# Run tests in a specific package
./gradlew test --tests "com.automation.test.*"

# Run tests matching a pattern
./gradlew test --tests "*LoginTest"

# Run tests and generate HTML reports
./gradlew test testReport
```

### Automation Tests (Android Instrumentation)

#### Using Connected Devices/Emulators

```bash
# Run all automation tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.automation.test.LoginTest

# Run tests on specific device
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.automation.test.LoginTest -Pandroid.testInstrumentationRunnerArguments.package=com.automation.test

# List connected devices
./gradlew listDevices

# Run on specific device by serial
ANDROID_SERIAL=emulator-5554 ./gradlew connectedAndroidTest
```

#### Using Gradle-Managed Devices (GMD)

GMD automatically creates, launches, and manages emulators for testing. No manual device setup required!

```bash
# Show GMD information and available devices
./gradlew gmdInfo

# Run tests on specific GMD device
./gradlew testOnPixel2Api30    # Pixel 2, API 30
./gradlew testOnPixel4Api33    # Pixel 4, API 33
./gradlew testOnPixel6Api34     # Pixel 6, API 34

# Run tests on all GMD devices in parallel
./gradlew testOnAllGMDDevices

# Run on device group (all devices)
./gradlew allDevicesDebugAndroidTest

# Run SPECIFIC tests on SPECIFIC devices (using parameters)
./gradlew pixel6api34DebugAndroidTest -PtestClass=LoginTest
./gradlew pixel4api33DebugAndroidTest -PtestClass=com.automation.test.LoginTest
./gradlew pixel6api34DebugAndroidTest -PtestClass=LoginTest -PtestMethod=testLoginWithValidCredentials
./gradlew pixel2api30DebugAndroidTest -PtestPackage=com.automation.test

# Show usage information
./gradlew runTestOnDevice
```

**GMD Benefits:**
- ✅ No manual emulator setup required
- ✅ Consistent device configurations across team/CI
- ✅ Parallel testing on multiple devices
- ✅ Automatic device lifecycle management
- ✅ Works great in CI/CD pipelines

**GMD Requirements:**
- Android Gradle Plugin 7.3.0+ (current: 8.2.0 ✅)
- Hardware acceleration (for local development)
- For CI without GPU, use: `-Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect`

**Test Sharding (split tests across instances):**
Add to `gradle.properties`:
```properties
android.experimental.androidTest.numManagedDeviceShards=2
```

### Test Reports

Test reports are generated in:
- HTML: `build/reports/tests/test/index.html`
- XML: `build/test-results/test/TEST-*.xml`

## Notes

- This is an **Android library project** that can run Espresso instrumentation tests
- **Framework code** (BasePage, utilities, etc.) is in `src/main/java/com/automation/` - this gets compiled into the AAR library
- **Test code** (BaseTest, LoginTest, etc.) is in `src/androidTest/java/com/automation/test/` - these are instrumentation tests that run on devices
- View IDs and Activity classes need to be updated to match your actual app
- Test data files should be placed in `src/androidTest/resources/testdata/` or `src/test/resources/testdata/`
- The framework uses Java 17, ensure your project is configured accordingly
- **To run instrumentation tests**: Connect an Android device or start an emulator, then run `./gradlew connectedAndroidTest`

**For more details on project structure, see [docs/PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md)**


