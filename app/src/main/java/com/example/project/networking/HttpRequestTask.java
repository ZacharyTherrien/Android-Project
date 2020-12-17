/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package com.example.project.networking;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;

/**
 * Task to accomplish HTTP requests asynchronously.
 */
public class HttpRequestTask extends AsyncTask<HttpRequest, Void, Either<Exception, HttpResponse>> {

    private boolean hasExecuted;

    private OnErrorListener onErrorListener;
    private OnResponseListener<HttpResponse> onResponseListener;

    /**
     * Create an HTTP request task.
     */
    public HttpRequestTask() {
        hasExecuted = false;
        onErrorListener = new OnErrorListener() {
            @Override
            public void onError(Exception error) {
                Log.d("DEBUG", error.getMessage());
            }
        };
    }

    /**
     * Set the error listener for the request.
     * @param onErrorListener
     * @return
     */
    public HttpRequestTask setOnErrorListener(OnErrorListener onErrorListener)  {
        if(hasExecuted)
            throw new IllegalStateException("Listeners should be set before executing task.");
        this.onErrorListener = onErrorListener;
        return this;
    }

    /**
     * Set the response listener for the request.
     * @param onResponseListener
     * @return
     */
    public HttpRequestTask setOnResponseListener(OnResponseListener<HttpResponse> onResponseListener) {
        if(hasExecuted)
            throw new IllegalStateException("Listeners should be set before executing task.");
        this.onResponseListener = onResponseListener;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Either<Exception, HttpResponse> doInBackground(HttpRequest... httpRequests) {
        hasExecuted = true;
        HttpRequest request = httpRequests[0];
        try {
            return Either.right(request.perform());
        } catch (IOException e) {
            return Either.left(e);
        }
    }

    @Override
    protected void onPostExecute(Either<Exception, HttpResponse> either) {
        switch (either.getType()) {
            case LEFT:
                if(onErrorListener != null)
                    onErrorListener.onError(either.getLeft());
                break;
            case RIGHT:
                if(onResponseListener != null)
                    onResponseListener.onResponse(either.getRight());
                break;
        }
    }
}
