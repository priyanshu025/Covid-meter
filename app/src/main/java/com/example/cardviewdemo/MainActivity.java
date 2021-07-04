package com.example.cardviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView totalcasesTextview;
    TextView activecasesTextView;
    TextView recoveredcasesTextView;
    TextView deathsTextView;
    TextView PreviousDayTests;
    TextView lastUpdatedTextview;
    TextView newDeathsTextiew;
    TextView newRecoveredTextview;
    ListView regionListView;
    ArrayAdapter<String> arrayAdapter1;
    static ArrayList<String> regiondata1=new ArrayList<String>();
    static ArrayList<String> activeCases=new ArrayList<String>();
    static ArrayList<String> newInfected=new ArrayList<String>();
    static ArrayList<String> recovered=new ArrayList<String>();
    static ArrayList<String> newRecovered=new ArrayList<String>();
    static ArrayList<String> deceased=new ArrayList<String>();
    static ArrayList<String> newDeceased=new ArrayList<String>();
    static ArrayList<String> totalInfected=new ArrayList<String>();




    // ArrayList<String> cases=new ArrayList<String>();
    public class downloadtask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = " ";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.i("covid cases",s);
            try{
                JSONObject jsonObject=new JSONObject(s);
                String cases=jsonObject.getString("totalCases");
                String active=jsonObject.getString("activeCases");
                String recoveredTotal=jsonObject.getString("recovered");
                //Log.i("recovered",recovered);
                String totaldeaths=jsonObject.getString("deaths");
               // Log.i("total deaths",totaldeaths);
                String newDeaths=jsonObject.getString("deathsNew");
                String previousDayTests=jsonObject.getString("previousDayTests");
                String recoveredNew=jsonObject.getString("recoveredNew");

                totalcasesTextview.setText(cases);
                activecasesTextView.setText(active);
                recoveredcasesTextView.setText(recoveredTotal);
                deathsTextView.setText(totaldeaths);

                String regionData=jsonObject.getString("regionData");
                JSONArray jsonArray=new JSONArray(regionData);
                arrayAdapter1.notifyDataSetChanged();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    regiondata1.add(jsonObject1.getString("region"));
                    arrayAdapter1.notifyDataSetChanged();
                    activeCases.add(jsonObject1.getString("activeCases"));
                    newInfected.add(jsonObject1.getString("newInfected"));
                    recovered.add(jsonObject1.getString("recovered"));
                    newRecovered.add(jsonObject1.getString("newRecovered"));
                    deceased.add(jsonObject1.getString("deceased"));
                    newDeceased.add(jsonObject1.getString("newDeceased"));
                    totalInfected.add(jsonObject1.getString("totalInfected"));

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalcasesTextview=(TextView)findViewById(R.id.totalcasesTextView);
        activecasesTextView=(TextView)findViewById(R.id.activecasesTextView);
        recoveredcasesTextView=(TextView)findViewById(R.id.recoveredTextView);
        deathsTextView=(TextView)findViewById(R.id.totalDeathsTextView);
        regionListView=findViewById(R.id.regionlistView1);
        arrayAdapter1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,regiondata1);
        arrayAdapter1.notifyDataSetChanged();
        regionListView.setAdapter(arrayAdapter1);
        try{
            downloadtask task=new downloadtask();
             task.execute("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");
             //Log.i("info",result);
        }catch (Exception e){
            e.printStackTrace();
        }
        regionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,StateWise.class);
                intent.putExtra("region",i);
                startActivity(intent);
            }
        });

    }
}