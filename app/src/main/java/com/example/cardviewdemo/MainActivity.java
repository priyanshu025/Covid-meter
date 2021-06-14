package com.example.cardviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView totalcases;
    TextView activecases;
    TextView recoveredcases;
    TextView deaths;
    GraphView bargraph;
    String resulttotal="5";
    int a,b,c;
    int d;

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
                a=Integer.parseInt(cases);
                String active=jsonObject.getString("activeCases");
                b=Integer.parseInt(active);
                String recovered=jsonObject.getString("recovered");
                c=Integer.parseInt(recovered);
                String totaldeaths=jsonObject.getString("deaths");
                d=Integer.parseInt(totaldeaths);

                totalcases.setText(cases);
                activecases.setText(active);
                recoveredcases.setText(recovered);
                deaths.setText(totaldeaths);
                BarGraphSeries<DataPoint> series=new BarGraphSeries<DataPoint>(getDataPoint());
                bargraph.addSeries(series);
                series.setSpacing(10);


                //Toast.makeText(MainActivity.this, totalcases, Toast.LENGTH_SHORT).show();
                //Log.i("totalcases",totalcases);




            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalcases=(TextView)findViewById(R.id.totalcasesTextView);
        activecases=(TextView)findViewById(R.id.activecasesTextView);
        recoveredcases=(TextView)findViewById(R.id.recoveredTextView);
        deaths=(TextView)findViewById(R.id.totalDeathsTextView);
        bargraph=findViewById(R.id.bargraph);

        String result=null;
        try{
            downloadtask task=new downloadtask();
             result=task.execute("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true").get();
             Log.i("info",result);
             JSONObject jsonObject=new JSONObject(result);
             resulttotal=jsonObject.getString("totalCases");
             Log.i("total",resulttotal);


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private DataPoint[] getDataPoint() {
        DataPoint[] dp=new DataPoint[]{
                new DataPoint(0,Integer.parseInt(resulttotal)),
                new DataPoint(1,b),
                new DataPoint(2,c),
                new DataPoint(3,d)
        };
        return dp;
    }
}