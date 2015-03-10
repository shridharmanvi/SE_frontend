package com.example.sagar.findout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

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
               if(Query1.length()== 0) {

                   Toast.makeText(MainActivity.this, "Enter a Product", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(MainActivity.this, "before"+Query1, Toast.LENGTH_SHORT).show();
                   graphResult(Query1);

               }
           }
       });
    }

                   public void graphResult(String Query1){
                       Toast.makeText(MainActivity.this, "===", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(this,ResultPercentage.class);
                       intent.putExtra(getString(R.string.key_query), Query1);
                       startActivity(intent);

                   }

}
