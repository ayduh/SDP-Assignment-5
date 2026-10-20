package com.aidar.quality;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.aidar.model.StreamRequest;

public class UserPreferences {
    private StreamRequest current;
    private final List<UserPreferencesListener> listeners = new ArrayList<>();

    public UserPreferences(StreamRequest initial) {
        this.current = Objects.requireNonNull(initial, "initial StreamRequest can't be null");
    }

    public void addListener(UserPreferencesListener listener) {
        listeners.add(Objects.requireNonNull(listener, "listener can't be null"));
    }

    public StreamRequest getCurrent() {
        return current;
    }

    public void setDataSaverEnabled(boolean enabled) {
        update(current.withDataSaverEnabled(enabled));
    }

    public void setAccountTier(String tier) {
        update(current.withAccountTier(tier));
    }

    private void update(StreamRequest next) {
        if (next.equals(current)) {
            return; // nothing really changed, nobody needs to recalculate
        }
        current = next;
        for (UserPreferencesListener listener : List.copyOf(listeners)) {
            listener.onPreferencesChanged(next);
        }
    }
}
