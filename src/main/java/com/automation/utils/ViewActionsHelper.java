package com.automation.utils;

import androidx.annotation.IdRes;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Utility class for frequently used Espresso view actions.
 * 
 * Note: Basic operations (click, typeText, clearText, verifyDisplayed, scrollTo, getView by ID)
 * are available in BasePage for page objects. This helper provides additional utilities
 * and text-based operations that are not in BasePage.
 */
public class ViewActionsHelper {
    
    /**
     * Click on a view by text
     * Espresso automatically waits for the view to be in hierarchy and UI thread to be idle
     * 
     * @param text The text content of the view to click
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction clickByText(String text) {
        return onView(withText(text))
                .perform(ViewActions.click());
    }
    
    /**
     * Type text into a view by ID without closing keyboard
     * Useful when you need to type multiple fields without closing keyboard between them
     * Espresso automatically waits for the view to be in hierarchy and UI thread to be idle
     * 
     * @param viewId The resource ID of the view
     * @param text The text to type
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction typeTextByIdNoKeyboardClose(@IdRes int viewId, String text) {
        return onView(withId(viewId))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText(text));
    }
    
    /**
     * Verify text is displayed
     * 
     * @param text The text content to verify
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction verifyTextDisplayed(String text) {
        return onView(withText(text))
                .check(ViewAssertions.matches(isDisplayed()));
    }
    
    /**
     * Scroll to view by text
     * 
     * @param text The text content of the view to scroll to
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction scrollToText(String text) {
        return onView(withText(text))
                .perform(scrollTo());
    }
    
    /**
     * Swipe down on a view
     * 
     * @param viewId The resource ID of the view to swipe
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction swipeDown(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(swipeDown());
    }
    
    /**
     * Swipe up on a view
     * 
     * @param viewId The resource ID of the view to swipe
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction swipeUp(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(swipeUp());
    }
    
    /**
     * Swipe left on a view
     * 
     * @param viewId The resource ID of the view to swipe
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction swipeLeft(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(swipeLeft());
    }
    
    /**
     * Swipe right on a view
     * 
     * @param viewId The resource ID of the view to swipe
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction swipeRight(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(swipeRight());
    }
    
    /**
     * Press back button
     * 
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction pressBack() {
        return onView(ViewMatchers.isRoot())
                .perform(pressBack());
    }
    
    /**
     * Press IME action button (e.g., Done, Next, Search)
     * 
     * @param viewId The resource ID of the view with IME action
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction pressImeAction(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(pressImeActionButton());
    }
    
    /**
     * Close soft keyboard
     * 
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction closeKeyboard() {
        return onView(ViewMatchers.isRoot())
                .perform(closeSoftKeyboard());
    }
    
    /**
     * Get ViewInteraction by text
     * Useful for operations on views identified by text content
     * 
     * @param text The text content of the view
     * @return ViewInteraction for method chaining
     */
    public static ViewInteraction getViewByText(String text) {
        return onView(withText(text));
    }
}

