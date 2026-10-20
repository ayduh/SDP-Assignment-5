package com.aidar.quality;

import com.aidar.model.StreamRequest;

public interface UserPreferencesListener {
    void onPreferencesChanged(StreamRequest newRequest);
}
