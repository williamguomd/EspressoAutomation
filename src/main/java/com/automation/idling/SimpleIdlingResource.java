package com.automation.idling;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simple Idling Resource implementation
 * Useful for simple async operations
 */
public class SimpleIdlingResource implements IdlingResource {
    
    @Nullable
    private volatile ResourceCallback callback;
    private final AtomicBoolean isIdle = new AtomicBoolean(true);
    private final String name;
    
    public SimpleIdlingResource(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }
    
    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
    
    /**
     * Set the idle state
     */
    public void setIdleState(boolean isIdle) {
        this.isIdle.set(isIdle);
        if (isIdle && callback != null) {
            callback.onTransitionToIdle();
        }
    }
    
    /**
     * Mark as busy
     */
    public void setBusy() {
        setIdleState(false);
    }
    
    /**
     * Mark as idle
     */
    public void setIdle() {
        setIdleState(true);
    }
}

