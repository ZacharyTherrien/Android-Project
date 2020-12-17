/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package com.example.project.networking;

/**
 * Response listener.
 * @param <T>
 */
public interface OnResponseListener<T> {
    /**
     * Event handler for responses.
     * @param response
     */
    void onResponse(T response);
}
