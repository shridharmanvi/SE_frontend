package com.example.team4.findout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONObject;


public class Trending extends ActionBarActivity {
    private String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_trending);
        getSupportActionBar().setTitle("Popularity Trend");

        res = getIntent().getExtras().getString("result");
        openChart(res);
    }

    private void openChart(String res){
            try{
                JSONArray arr = new JSONArray(res);
                JSONObject json = arr.getJSONObject(1);
                JSONArray keys = json.names();
                // Creating an  XYSeries for Days
                XYSeries daysSeries = new XYSeries("Positive Percent");

                for(int j=0;j<keys.length();j++){
                    int k =Integer.parseInt(keys.getString(j));
                    String t = ""+(j+1);
                    // Adding data to Days Series
                    daysSeries.add((j+1),json.getDouble(t));

                }
                for(int j=0;j<daysSeries.getItemCount();j++){
                    String t = ""+daysSeries.getY(j);
                }
                // Creating a dataset to hold each series
                XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                // Adding dayseries Series to the dataset
                dataset.addSeries(daysSeries);

                // Creating XYSeriesRenderer to customize incomeSeries
                XYSeriesRenderer daysRenderer = new XYSeriesRenderer();
                daysRenderer.setColor(Color.YELLOW);
                daysRenderer.setPointStyle(PointStyle.CIRCLE);
                daysRenderer.setFillPoints(true);
                daysRenderer.setLineWidth(5);
                daysRenderer.setDisplayChartValues(true);


                //Creating a XYMultipleSeriesRenderer
                XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
                multiRenderer.setXLabels(0);
                multiRenderer.setChartTitle("Positive Sentiment Percent Chart");
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

                View trends = ChartFactory.getLineChartView(this,dataset,multiRenderer);
                LinearLayout trendsview = (LinearLayout) findViewById(R.id.trendinggraph);
                trends.setBackgroundColor(2);
                trendsview.addView(trends);
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trending, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
