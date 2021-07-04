package com.example.cardviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StateWise extends AppCompatActivity {
     int index;
     ArrayList<String> Details=new ArrayList<String>();
     ListView listView;
     TextView region;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise);
        listView=findViewById(R.id.listView);
        region=findViewById(R.id.regionTextView);
        Intent intent=getIntent();
        index=intent.getIntExtra("region",-1);
        if(index!=-1){
            Details.add("Total Infected: " + " \t\t" + MainActivity.totalInfected.get(index));
            Details.add("Active Cases: " + "\t\t" + MainActivity.activeCases.get(index));
            Details.add("New Infected: " + "\t\t" + MainActivity.newInfected.get(index));
            Details.add("Total Recovered: "+ "\t\t" + MainActivity.recovered.get(index));
            Details.add("New Recovered: "+ "\t\t" + MainActivity.newRecovered.get(index));
            Details.add("Total Deceased: " + "\t\t" + MainActivity.deceased.get(index));
            Details.add("New Deceased: " + "\t\t" + MainActivity.newDeceased.get(index));
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Details);
        listView.setAdapter(arrayAdapter);
        region.setText(MainActivity.regiondata1.get(index));
        //Toast.makeText(this, Integer.toString(index), Toast.LENGTH_SHORT).show();
    }
}