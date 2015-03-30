package com.example.sagar.findout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ResultPercentage extends ActionBarActivity {

    private Button trendsViewButton;
    public static final String TAG = ResultPercentage.class.getSimpleName();
    String query1 = "";
    String res = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_percentage);
        Intent intent = getIntent();
        String query = intent.getStringExtra(getString(R.string.key_query));
        query1 = query;
        if (query.contains(" ")) {
            query= query.replace(" ","%20");
        }
        query = query.toLowerCase();
        String url_query = "54.69.180.213/findout/" + query;
        if (query == null) {
            query = "gadget";
        }
        Log.d(TAG, query);
        final String finalQuery = query;
        new HttpHandler() {
            @Override
            public HttpUriRequest getHttpRequestMethod() {
                String str = "54.69.180.213/findout/"+finalQuery;
                return new HttpGet("http://54.69.180.213/findout/"+finalQuery);
            }

            @Override
            public void onResponse(String result) {
                res = result;
                try {
                    if(result.equals("Please enter a valid candidate for 2016 Presidential Elections")){
                        Toast.makeText(ResultPercentage.this,"Invalid Search Query",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResultPercentage.this,MainActivity.class);
                        startActivity(intent);
                    }
                    JSONArray arr = new JSONArray(result);
                    JSONObject json = arr.getJSONObject(0);
                    double percents[] = new double[2];
                    percents[0] = json.getDouble("neg");
                    percents[1] = json.getDouble("pos");
                    openChart(percents);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();
        trendsViewButton = (Button) findViewById(R.id.trendsButton);
        trendsViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                trendinggraph(res);
            }
        });
    }
    public void openChart(double[] input) {
        // Pie Chart Section Names
        String[] code = new String[]{"positive"+input[1],"negative"+input[0], "neutral"+(100-input[1]-input[0])};
        // Pie Chart Section Value
        double[] distribution = {input[1], input[0], 100-input[0]-input[1]};
        // Color of each Pie Chart Sections
        int[] colors = {Color.GREEN, Color.RED, Color.CYAN,};
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Sentiment Analysis");
        for (int i = 0; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
            defaultRenderer.setLabelsTextSize((float) 40.05);
            defaultRenderer.setPanEnabled(false);
        }

        defaultRenderer.setChartTitle("Sentiment Analysis of  " + query1);
        defaultRenderer.setChartTitleTextSize(60);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setLegendTextSize((float) 30);
        defaultRenderer.setFitLegend(true);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);

        View chart = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);

        LinearLayout chartview = (LinearLayout) findViewById(R.id.chart);
        chart.setBackgroundColor(2);
        chartview.addView(chart, 0);
    }

    public void trendinggraph(String res)
    {
        Intent intent1 = new Intent(ResultPercentage.this, trending.class);
        intent1.putExtra("result",res);
        startActivity(intent1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_percentage, menu);
        return true;
    }
}