package com.example.team4.findout;

import org.apache.http.client.methods.HttpUriRequest;

public abstract class HttpHandler {

    public abstract HttpUriRequest getHttpRequestMethod();

    public abstract void onResponse(String result);

    public void execute(){

        new AsyncHttpTask(this).execute();
    }
}