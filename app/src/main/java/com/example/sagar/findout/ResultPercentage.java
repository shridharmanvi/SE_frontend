package com.example.sagar.findout;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.prefs.Preferences;


public class ResultPercentage extends Activity {

        public static final String TAG = ResultPercentage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_percentage);

        final TextView Responseview = (TextView) findViewById(R.id.Response);
        Intent intent = getIntent();
        String query = intent.getStringExtra(getString(R.string.key_query));
        // String url_query = "http://shridharmanvi.pythonanywhere.com/" + query ;
        if (query == null) {
            query = "gadget";
        }
        Log.d(TAG, query);

        final String finalQuery = query;
        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {

                return new HttpGet("http://shridharmanvi.pythonanywhere.com/" + finalQuery);

                // return new HttpPost(url)
            }
            @Override
            public void onResponse(String result) {
                Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
                Responseview.setText(result);
            }

        }.execute();


   }



}
