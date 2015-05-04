package com.example.team4.findout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity  {

    private EditText mSearchQuery;
    private Button mAnalyzeButton;
    private Button mTrendCompare;
    private Spinner spinner;
    String query1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("FindOut");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

       // mSearchQuery = (EditText) findViewById(R.id.searchQuery);
        mAnalyzeButton = (Button) findViewById(R.id.analyzeButton);
        mTrendCompare = (Button) findViewById(R.id.trendcompare);

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        String[] list = getResources().getStringArray(R.array.candidates);
        Arrays.sort(list);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
               R.array.planets_array, android.R.layout.simple_spinner_item);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                query1 = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        mAnalyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  graphResult(query1);

            }
        });
        mTrendCompare.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),TrendComparison.class);
                startActivity(intent);

            }
        });
    }




                   public void graphResult(String Query1){
                       Intent intent = new Intent(this,ResultPercentage.class);
                       intent.putExtra(getString(R.string.key_query), Query1);//To pass the query to the Result Percentage activity
                       startActivity(intent);
                   }


}
