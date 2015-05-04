package com.example.team4.findout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;


public class TrendComparison extends ActionBarActivity {

    private Spinner spinner1,spinner2;
    String compare1,compare2;
    String res1,res2,name1,name2;
    private Button mCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_comparison);
        getSupportActionBar().setTitle("Trend Comparison");


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        mCompare = (Button) findViewById(R.id.compareButton);

        String[] list = getResources().getStringArray(R.array.candidates);
        Arrays.sort(list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                compare1 = parent.getItemAtPosition(pos).toString();
                name1=parent.getItemAtPosition(pos).toString();
                if (compare1.contains(" ")) {
                    compare1 = compare1.replace(" ", "%20");
                }
               compare1= compare1.toLowerCase();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            ;



        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                compare2 = parent.getItemAtPosition(position).toString();
                name2 =parent.getItemAtPosition(position).toString();
                if (compare2.contains(" ")) {
                    compare2 = compare2.replace(" ", "%20");
                }
              compare2= compare2.toLowerCase();
               if(!compare2.equalsIgnoreCase(compare1)) {


               }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }

        });








        mCompare.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        if(compare1.equalsIgnoreCase(compare2))
                        {
                            Toast.makeText(TrendComparison.this, "Please Select Different Candidates", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TrendComparison.this,TrendComparison.class);
                            startActivity(intent);
                        }
                        else
                        {



                        Intent intent = new Intent(TrendComparison.this,CompareTrendChart.class);
                        intent.putExtra("candidate1",compare1);
                       intent.putExtra("candidate2",compare2);
                            intent.putExtra("name1",name1);
                            intent.putExtra("name2",name2);

                            startActivity(intent);

                    }
                }


    });


}



}
