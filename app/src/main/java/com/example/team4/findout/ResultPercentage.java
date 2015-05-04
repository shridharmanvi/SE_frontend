package com.example.team4.findout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
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
import org.json.JSONObject;

import java.util.Random;


public class ResultPercentage extends ActionBarActivity {

    private Button trendsViewButton;
    public static final String TAG = ResultPercentage.class.getSimpleName();
    String query1 = "";
    String res = "";
    AnimationDrawable frameAnimation;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_percentage);

        getSupportActionBar().setTitle("Sentiment Analysis");
        String[] list = getResources().getStringArray(R.array.facts);

        Random r = new Random();
      final ProgressDialog dialog = ProgressDialog.show(this, "Did You know", list[r.nextInt(38)], true);


       /* final ImageView animImageView = (ImageView) findViewById(R.id.ivAnimation);
        animImageView.setBackgroundResource(R.drawable.anim);
        animImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation =
                        (AnimationDrawable) animImageView.getBackground();
                frameAnimation.start();




            }
        });
*/
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
                       // Toast.makeText(ResultPercentage.this,"Invalid Search Query",Toast.LENGTH_SHORT).show();
                         Toast.makeText(ResultPercentage.this, "Not Enough Tweets To Analyze", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ResultPercentage.this,MainActivity.class);
                        startActivity(intent);
                    }
                    JSONArray arr = new JSONArray(result);
                    JSONObject json = arr.getJSONObject(0);
                    double percents[] = new double[2];
                    percents[0] = json.getDouble("neg");
                    percents[1] = json.getDouble("pos");
                    openChart(percents);
                    dialog.dismiss();
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
               // frameAnimation.stop();
            }
        });

       Button wordcloud = (Button) findViewById(R.id.wordcloud);
        wordcloud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wordcloud(res);
            }
        });

    }

    public void openChart(double[] input) {
        // Pie Chart Section Names
         String[] code = new String[]{"Positive:"+input[1],"Negative:"+input[0], "Neutral:"+(100-input[1]-input[0])};
       // String[] code = new String[]{"positive","negative", "neutral"};

        // Pie Chart Section Value
        double[] distribution = {input[1], input[0], 100-input[0]-input[1]};
        // Color of each Pie Chart Sections
        int[] colors = {Color.parseColor("#38D54D"), Color.parseColor("#DE3333"), Color.parseColor("#2F77C0")};
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

        defaultRenderer.setChartTitle(query1);
        defaultRenderer.setChartTitleTextSize(40);
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
        Intent intent1 = new Intent(ResultPercentage.this, Trending.class);
        intent1.putExtra("result",res);
        startActivity(intent1);
    }

    public void wordcloud(String res)
    {
        Intent intent1 = new Intent(ResultPercentage.this,WordCloud.class);
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