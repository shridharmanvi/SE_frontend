package com.example.sagar.findout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;


public class ResultPercentage extends Activity {

        public static final String TAG = ResultPercentage.class.getSimpleName();
        String input= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_percentage);

        Toast.makeText(ResultPercentage.this,"hello!", Toast.LENGTH_LONG).show();

        //final TextView Responseview = (TextView) findViewById(R.id.Response);

       Intent intent = getIntent();
       Toast.makeText(getBaseContext(), "hello!", Toast.LENGTH_LONG).show();
        String query = intent.getStringExtra(getString(R.string.key_query));
         String url_query = "http://shridharmanvi.pythonanywhere.com/" + query ;
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
               //Responseview.setText(result);
                input = result;
                openChart(input);

            }

        }.execute();


    }

    public void openChart(String input){

        // Pie Chart Section Names
        String[] code = new String[] {
                input,"Negative","Neutral"
        };

        // Pie Chart Section Value
        double[] distribution = { 44.6,23.2,32.2 } ;

        // Color of each Pie Chart Sections
        int[] colors = { Color.GREEN, Color.RED,Color.CYAN, };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Sentiment Analysis");
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);

        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
            defaultRenderer.setLabelsTextSize((float) 40.05);
            defaultRenderer.setPanEnabled(false);
        }

        defaultRenderer.setChartTitle("Sentiment Analysis of  "+ input);
        defaultRenderer.setChartTitleTextSize(60);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setLegendTextSize((float)30);
        defaultRenderer.setFitLegend(true);
        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent1 = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "Sentiment Analysis");

        // Start Activity
        startActivity(intent1);


    }


}
