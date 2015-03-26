package com.example.sagar.findout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class trending extends ActionBarActivity {

    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_trending);
        Toast.makeText(trending.this, "hello!", Toast.LENGTH_LONG).show();




        openChart();
    }

    private void openChart(){



            int[] x = { 1,2,3,4,5,6,7,8 };
            int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800};
            int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

            // Creating an  XYSeries for Income
            XYSeries incomeSeries = new XYSeries("Income");
            // Creating an  XYSeries for Expense
            XYSeries expenseSeries = new XYSeries("Expense");
            // Adding data to Income and Expense Series
            for(int i=0;i<x.length;i++){
                incomeSeries.add(x[i], income[i]);
                expenseSeries.add(x[i],expense[i]);
            }

            // Creating a dataset to hold each series
            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
            // Adding Income Series to the dataset
            dataset.addSeries(incomeSeries);
            // Adding Expense Series to dataset
            dataset.addSeries(expenseSeries);

            // Creating XYSeriesRenderer to customize incomeSeries
            XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
            incomeRenderer.setColor(Color.YELLOW);
            incomeRenderer.setPointStyle(PointStyle.CIRCLE);
            incomeRenderer.setFillPoints(true);
            incomeRenderer.setLineWidth(5);
            incomeRenderer.setDisplayChartValues(true);

            // Creating XYSeriesRenderer to customize expenseSeries
            XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
            expenseRenderer.setColor(Color.RED);
            expenseRenderer.setPointStyle(PointStyle.CIRCLE);
            expenseRenderer.setFillPoints(true);
            expenseRenderer.setLineWidth(2);
            expenseRenderer.setDisplayChartValues(true);

            // Creating a XYMultipleSeriesRenderer to customize the whole chart
            XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
            multiRenderer.setXLabels(0);
            multiRenderer.setChartTitle("Income vs Expense Chart");
            multiRenderer.setXTitle("Year 2012");
            multiRenderer.setYTitle("Amount in Dollars");
            multiRenderer.setZoomButtonsVisible(false);
            //   multiRenderer.setBackgroundColor(-16777216);
            multiRenderer.setPanEnabled(false);
            // multiRenderer.setLegendHeight(35);
            multiRenderer.setFitLegend(true);
            // multiRenderer.setChartTitleTextSize(-25);
            multiRenderer.setAxisTitleTextSize(25);
            multiRenderer.setLegendTextSize(35);
            multiRenderer.setChartTitleTextSize(35);
            multiRenderer.setPointSize(10);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.BLACK);
        multiRenderer.setMarginsColor(Color.BLACK);
        multiRenderer.setFitLegend(true);
        for(int i=0;i<x.length;i++){
                multiRenderer.addXTextLabel(i+1, mMonth[i]);
            }

            // Adding incomeRenderer and expenseRenderer to multipleRenderer
            // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
            // should be same
            multiRenderer.addSeriesRenderer(incomeRenderer);
            multiRenderer.addSeriesRenderer(expenseRenderer);


            // Creating an intent to plot line chart using dataset and multipleRenderer
          //  Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
                View trends = ChartFactory.getLineChartView(this,dataset,multiRenderer);
        LinearLayout trendsview = (LinearLayout) findViewById(R.id.trendinggraph);
        trends.setBackgroundColor(2);
        trendsview.addView(trends);
            // Start Activity
            //startActivity(intent);



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
