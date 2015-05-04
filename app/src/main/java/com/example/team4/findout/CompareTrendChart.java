package com.example.team4.findout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;


public class CompareTrendChart extends ActionBarActivity {

    private String candidate1,candidate2;
    private TextView text;
     String res1,res2,name1,name2;
    AnimationDrawable frameAnimation;
    private  TextView text23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_trend_chart);


        Intent intent = getIntent();
        candidate1 = intent.getStringExtra("candidate1");
        candidate2 = intent.getStringExtra("candidate2");
        name1 = intent.getStringExtra("name1");
        name2 = intent.getStringExtra("name2");
        getSupportActionBar().setTitle("Trend Comparison");

        /*Toast.makeText(CompareTrendChart.this, candidate1, Toast.LENGTH_SHORT).show();
        Toast.makeText(CompareTrendChart.this, candidate2, Toast.LENGTH_SHORT).show();
        candidate1 = getIntent().getExtras().getString("result");
        */
         final String[] list = getResources().getStringArray(R.array.facts);
        final Random r = new Random();


        final ProgressDialog dialog = ProgressDialog.show(this, "Did You know", list[r.nextInt(38)], true);

        new HttpHandler() {

            public HttpUriRequest getHttpRequestMethod() {
                return new HttpGet("http://54.69.180.213/findout/" + candidate1);

            }

            public void onResponse(String result) {
                res1 = result;




                try {
                    if (result.equals("Please enter a valid candidate for 2016 Presidential Elections")) {
                        Toast.makeText(CompareTrendChart.this, "Not enough Tweets for First Candidate", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CompareTrendChart.this, TrendComparison.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();


        new HttpHandler() {

            public HttpUriRequest getHttpRequestMethod() {
                return new HttpGet("http://54.69.180.213/findout/" + candidate2);

            }

            public void onResponse(String result2) {
                res2 = result2;
                dialog.dismiss();


                // Toast.makeText(CompareTrendChart.this, res2, Toast.LENGTH_SHORT).show();
                try {
                    if (result2.equals("Please enter a valid candidate for 2016 Presidential Elections")) {
                        Toast.makeText(CompareTrendChart.this, "Not enough Tweets for Second Candidate", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CompareTrendChart.this, TrendComparison.class);
                        startActivity(intent);
                    }
                    Comparisonchart(res1,res2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();


    }

         public void   Comparisonchart(String res1,String res2)
    {

        try{
            JSONArray arr = new JSONArray(res1);
            JSONObject json = arr.getJSONObject(1);
            JSONArray keys = json.names();
            // Creating an  XYSeries for Days
            JSONArray arr1 = new JSONArray(res2);
            JSONObject json1 = arr1.getJSONObject(1);
            JSONArray keys1 = json.names();


            XYSeries daysSeries = new XYSeries(name1);
            XYSeries compare = new XYSeries(name2);

            for(int j=0;j<keys.length();j++){
                int k =Integer.parseInt(keys.getString(j));
                String t = ""+(j+1);
                // Adding data to Days Series
                daysSeries.add((j+1),json.getDouble(t));

            }
            for(int j=0;j<keys1.length();j++){
                int k =Integer.parseInt(keys1.getString(j));
                String t = ""+(j+1);
                // Adding data to Days Series
                compare.add((j+1),json1.getDouble(t));

            }

            for(int j=0;j<daysSeries.getItemCount();j++){
                String t = ""+daysSeries.getY(j);
            }


            // Creating a dataset to hold each series
            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
            // Adding dayseries Series to the dataset
            dataset.addSeries(daysSeries);
            dataset.addSeries(compare);


            // Creating XYSeriesRenderer to customize incomeSeries
            XYSeriesRenderer daysRenderer = new XYSeriesRenderer();
            daysRenderer.setColor(Color.YELLOW);
            daysRenderer.setPointStyle(PointStyle.CIRCLE);
            daysRenderer.setFillPoints(true);
            daysRenderer.setLineWidth(5);
            daysRenderer.setDisplayChartValues(true);


            XYSeriesRenderer compareRenderer = new XYSeriesRenderer();
            compareRenderer.setColor(Color.GREEN);
            compareRenderer.setPointStyle(PointStyle.CIRCLE);
            compareRenderer.setFillPoints(true);
            compareRenderer.setLineWidth(5);
            compareRenderer.setDisplayChartValues(true);

            //Creating a XYMultipleSeriesRenderer
            XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
            multiRenderer.setXLabels(0);
            multiRenderer.setChartTitle(name1 + " V/S " +name2);
            multiRenderer.setXTitle("Days");
            multiRenderer.setYTitle("Percentage");
            multiRenderer.setZoomButtonsVisible(false);
            multiRenderer.setPanEnabled(false);
            multiRenderer.setFitLegend(true);
            multiRenderer.setAxisTitleTextSize(30);
            multiRenderer.setLegendTextSize(25);
            multiRenderer.setChartTitleTextSize(45);
            multiRenderer.setPointSize(10);
            multiRenderer.setZoomEnabled(false);
            multiRenderer.setApplyBackgroundColor(true);
            multiRenderer.setBackgroundColor(Color.BLACK);
            multiRenderer.setMarginsColor(Color.BLACK);
            multiRenderer.setFitLegend(true);
            multiRenderer.setYAxisMin(0);
            multiRenderer.setYAxisMax(100);
            multiRenderer.setAxisTitleTextSize(20);
            multiRenderer.setLabelsTextSize(20);


            for(int i=0;i<keys.length();i++){
                multiRenderer.addXTextLabel(i+1, ""+(i+1));
            }

            // Adding incomeRenderer and expenseRenderer to multipleRenderer
            // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
            // should be same
            multiRenderer.addSeriesRenderer(daysRenderer);
            multiRenderer.addSeriesRenderer(compareRenderer);



            View trend = ChartFactory.getLineChartView(this, dataset, multiRenderer);
            LinearLayout comparetrend = (LinearLayout) findViewById(R.id.comparetrend);
            trend.setBackgroundColor(2);
            comparetrend.addView(trend);
        }
        catch(Exception e){
            e.printStackTrace();
        }



















    }
    }

