package com.example.team4.findout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

public class WordCloud extends ActionBarActivity {
    private String res;
    private String url;
    ProgressDialog dialog=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_cloud);
        dialog = ProgressDialog.show(this, "Loading", "Fetching Word Cloud...Please Wait", true);



        res = getIntent().getExtras().getString("result");
        try{
            JSONArray arr = new JSONArray(res);
            JSONObject json = arr.getJSONObject(2);
            JSONArray keys = json.names();
            String key = keys.getString(0);
            url = json.getString(key);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

       String wordcloud = "<html><body><img src='"+url+"' width=100% height=100%/></body></html>";
        myWebView.getSettings().setSupportZoom(true);
        myWebView.setBackgroundColor(-16777216);
      //  myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadData(wordcloud, "text/html", "UTF-8");
        dialog.dismiss();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_cloud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(item));

    }
}
