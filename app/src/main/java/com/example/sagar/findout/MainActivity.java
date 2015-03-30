package com.example.sagar.findout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText mSearchQuery;
    private Button mAnalyzeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchQuery = (EditText) findViewById(R.id.searchQuery);
        mAnalyzeButton = (Button) findViewById(R.id.analyzeButton);

       mAnalyzeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String Query1 = mSearchQuery.getText().toString();
               //Checks if the user has entered a value in the EditText
               if (Query1.length() == 0) {
                   Toast.makeText(MainActivity.this, "Enter the candidate", Toast.LENGTH_SHORT).show();
               } else {
                   graphResult(Query1);
               }
           }
       });
    }

                   public void graphResult(String Query1){
                       Intent intent = new Intent(this,ResultPercentage.class);
                       intent.putExtra(getString(R.string.key_query), Query1);//To pass the query to the Result Percentage activity
                       startActivity(intent);
                   }


}
