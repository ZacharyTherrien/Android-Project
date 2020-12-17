/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package com.example.project.networking;

/**
 * Error listener.
 */
public interface OnErrorListener {
    /**
     * Event handler for errors, provided with the exception representing the error.
     * @param error
     */
    void onError(Exception error);
}
