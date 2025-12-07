package com.automation.base;

import androidx.annotation.IdRes;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Base Page Object Model class
 * All page objects should extend this class
 */
public abstract class BasePage {
    
    /**
     * Get ViewInteraction for a view by ID
     */
    protected ViewInteraction getView(@IdRes int viewId) {
        return onView(withId(viewId));
    }
    
    /**
     * Click on a view by ID
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction click(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(ViewActions.click());
    }
    
    /**
     * Type text into a view by ID
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction typeText(@IdRes int viewId, String text) {
        return onView(withId(viewId))
                .perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard());
    }
    
    /**
     * Clear text from a view by ID
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction clearText(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(ViewActions.clearText());
    }
    
    /**
     * Verify view is displayed
     * This is a convenience method for common assertion pattern
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction verifyDisplayed(@IdRes int viewId) {
        return onView(withId(viewId))
                .check(ViewAssertions.matches(isDisplayed()));
    }
    
    /**
     * Scroll to view
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction scrollTo(@IdRes int viewId) {
        return onView(withId(viewId))
                .perform(ViewActions.scrollTo());
    }
    
    /**
     * Scroll to a specific position in a RecyclerView
     * 
     * @param recyclerViewId The ID of the RecyclerView
     * @param position The position to scroll to (0-based index)
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction scrollToRecyclerView(@IdRes int recyclerViewId, int position) {
        return onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(position));
    }
    
    /**
     * Scroll to a specific position in a ListView
     * Uses onData() which is safer for AdapterView widgets
     * 
     * @param listViewId The ID of the ListView
     * @param position The position to scroll to (0-based index)
     * @return DataInteraction for method chaining
     */
    protected DataInteraction scrollToListView(@IdRes int listViewId, int position) {
        return onData(instanceOf(Object.class))
                .inAdapterView(withId(listViewId))
                .atPosition(position);
    }

    /**
     * Scroll to a ListView item by matching its text value
     * Uses onData() which is safer for AdapterView widgets
     * Assumes the adapter data is of type String
     * 
     * @param listViewId The ID of the ListView
     * @param value The text value to match
     * @return DataInteraction for method chaining
     */
    protected DataInteraction scrollToListViewItem(@IdRes int listViewId, String value) {
        return onData(allOf(is(instanceOf(String.class)), is(value)))
                .inAdapterView(withId(listViewId));
    }
    
    
    /**
     * Verify view is not displayed
     * @return ViewInteraction for method chaining
     */
    protected ViewInteraction verifyNotDisplayed(@IdRes int viewId) {
        return onView(withId(viewId))
                .check(ViewAssertions.matches(not(isDisplayed())));
    }
    
}

