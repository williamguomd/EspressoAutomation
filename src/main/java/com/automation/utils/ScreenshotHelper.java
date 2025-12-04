package com.automation.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for taking screenshots during Espresso tests.
 * 
 * Screenshots are automatically saved when tests fail (via TestWatcher in BaseTest).
 * Screenshots can also be taken manually during tests for debugging purposes.
 * 
 * Usage:
 * - Automatic: Screenshots are taken automatically on test failure
 * - Manual: Call ScreenshotHelper.takeScreenshot("custom_name") during tests
 */
public class ScreenshotHelper {
    
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final String SCREENSHOT_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    
    /**
     * Take a screenshot and save it with a custom name.
     * 
     * @param screenshotName The name for the screenshot (without extension)
     * @return The full path to the saved screenshot file, or null if failed
     */
    public static String takeScreenshot(String screenshotName) {
        try {
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            Bitmap screenshot = device.takeScreenshot();
            
            if (screenshot == null) {
                System.err.println("Failed to capture screenshot: Bitmap is null");
                return null;
            }
            
            File screenshotDir = getScreenshotDirectory();
            if (screenshotDir == null) {
                System.err.println("Failed to create screenshot directory");
                return null;
            }
            
            // Create filename with timestamp
            String timestamp = new SimpleDateFormat(SCREENSHOT_FORMAT, Locale.US).format(new Date());
            String filename = String.format("%s_%s.png", screenshotName, timestamp);
            File screenshotFile = new File(screenshotDir, filename);
            
            // Save screenshot
            FileOutputStream fos = new FileOutputStream(screenshotFile);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            
            String filePath = screenshotFile.getAbsolutePath();
            System.out.println("Screenshot saved: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Take a screenshot with automatic naming based on test class and method.
     * 
     * @param testClassName The name of the test class
     * @param testMethodName The name of the test method
     * @return The full path to the saved screenshot file, or null if failed
     */
    public static String takeScreenshot(String testClassName, String testMethodName) {
        String screenshotName = String.format("%s_%s", 
                sanitizeFilename(testClassName), 
                sanitizeFilename(testMethodName));
        return takeScreenshot(screenshotName);
    }
    
    /**
     * Get the screenshot directory.
     * Tries external storage first, then falls back to app's files directory.
     * 
     * @return File object representing the screenshot directory, or null if creation failed
     */
    private static File getScreenshotDirectory() {
        File screenshotDir = null;
        
        // Try external storage first (usually /sdcard/Pictures/screenshots)
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalPicturesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            screenshotDir = new File(externalPicturesDir, SCREENSHOT_DIR);
        }
        
        // Fallback to app's files directory if external storage is not available
        if (screenshotDir == null || !screenshotDir.exists()) {
            File appFilesDir = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext()
                    .getFilesDir();
            screenshotDir = new File(appFilesDir, SCREENSHOT_DIR);
        }
        
        // Create directory if it doesn't exist
        if (screenshotDir != null && !screenshotDir.exists()) {
            boolean created = screenshotDir.mkdirs();
            if (!created) {
                System.err.println("Failed to create screenshot directory: " + screenshotDir.getAbsolutePath());
                return null;
            }
        }
        
        return screenshotDir;
    }
    
    /**
     * Sanitize filename by removing invalid characters.
     * 
     * @param filename The original filename
     * @return Sanitized filename safe for use in file system
     */
    private static String sanitizeFilename(String filename) {
        if (filename == null) {
            return "unknown";
        }
        // Remove package separators and replace with underscores
        // Remove other invalid characters
        return filename.replace('.', '_')
                .replace('/', '_')
                .replace('\\', '_')
                .replace(':', '_')
                .replace('*', '_')
                .replace('?', '_')
                .replace('"', '_')
                .replace('<', '_')
                .replace('>', '_')
                .replace('|', '_');
    }
    
    /**
     * Get the screenshot directory path for reference.
     * Useful for logging or reporting where screenshots are saved.
     * 
     * @return The absolute path to the screenshot directory
     */
    public static String getScreenshotDirectoryPath() {
        File dir = getScreenshotDirectory();
        return dir != null ? dir.getAbsolutePath() : "Unknown";
    }
}

