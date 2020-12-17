/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package com.example.project.networking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * An HTTP request.
 */
public class HttpRequest {

    public enum Method {
        GET, POST, PUT, DELETE
    }

    private String url;
    private Method method;
    private String requestBody;
    private String contentType;

    /**
     * Create a request for a specified URL.
     * @param url
     */
    public HttpRequest(String url) {
        this.url = url;
        method = Method.GET;
    }

    /**
     * Specify the URL of the request.
     * @param url
     * @return
     */
    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    /**
     * The request method.
     * @param method
     * @return
     */
    public HttpRequest method(Method method) {
        this.method = method;
        return this;
    }

    /**
     * the request body.
     * @param requestBody
     * @return
     */
    public HttpRequest body(String requestBody) {
        if(contentType == null)
            contentType = "text/plain";
        this.requestBody = requestBody;
        return this;
    }

    /**
     * The request body.
     * @param contentType
     * @return
     */
    public HttpRequest contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Perform the request.
     * @return The response.
     * @throws IOException
     */
    public HttpResponse perform() throws IOException {

        if(method == Method.GET && requestBody != null)
            throw new IllegalStateException("No request body for GET request.");

        // phase 1: configure the request
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        connection.setRequestMethod(method.toString());
        connection.setReadTimeout(1000);
        connection.setConnectTimeout(1000);

        // phase 2: Request - send data to server
        if((method == Method.POST || method == Method.PUT) && requestBody != null) {
            connection.setRequestProperty("Content-Type", contentType);
            connection.setDoOutput(true);
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            writer.print(requestBody);
            writer.close();
        }

        // phase 3: Response - get data from the server
        HttpResponse response = new HttpResponse();

        response.setResponseCode(connection.getResponseCode());

        Map<String, List<String>> headers = connection.getHeaderFields();
        response.setHeaders(headers);
        int contentLength = 100;
        if(headers.containsKey("Content-Length"))
            contentLength = Integer.parseInt(headers.get("Content-Length").get(0));

        try {
            StringBuilder builder = new StringBuilder(contentLength);

            // read the response from the input or error stream
            Scanner scanner;
            if(connection.getResponseCode() >= 400)
                scanner = new Scanner(connection.getErrorStream());
            else
                scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNext()) {
                builder.append(scanner.nextLine() + '\n');
            }
            scanner.close();

            response.setResponseBody(builder.toString());
        }
        catch (FileNotFoundException e) {
            // can occur if there is no request body.
            response.setResponseBody("");
        }

        return response;
    }

}
