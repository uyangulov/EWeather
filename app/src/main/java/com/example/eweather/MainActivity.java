package com.example.eweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView temp_text;
    TextView pressureView;
    TextView humidityView;
    Button refresher;

    // OpenWeatherMap API URL --- Data Source
    String weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=Yekaterinburg,{country%20code}&appid=53f63e6a4d34ba654b1b82cc8e490712";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Load data on first launch
        FetchData initLoad = new FetchData();
        initLoad.execute();

        //Initialize Views
        temp_text = (TextView) findViewById(R.id.text);
        pressureView = (TextView) findViewById(R.id.pressureView);
        humidityView = (TextView) findViewById(R.id.humidityView);
        refresher = (Button) findViewById(R.id.button);

        //Update weather on key press
        refresher.setOnClickListener(myButtonClickListener);
    }

    class FetchData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            //Fetch data from URL
            String json_data = com.example.eweather.DownloadData.executeGet(weatherURL);
            return json_data;
        }

        @Override
        protected void onPostExecute (String json_data){
            try {
                //initialize json objects
                JSONObject reader = new JSONObject(json_data);
                JSONObject weatherData  = reader.getJSONObject("main");

                //extract particular data from objects
                Double temperature = Double.parseDouble(weatherData.getString("temp"));
                Double humidity = Double.parseDouble(weatherData.getString("humidity"));
                Double pressure = Double.parseDouble(weatherData.getString("pressure"));

                //convert data into standard units
                temperature -= 273.15;
                humidity *= 0.75;


                temp_text.setText(String.valueOf(temperature) + " °С");
                humidityView.setText(String.valueOf(humidity) + " %");
                pressureView.setText(String.valueOf(pressure) + " mmHg");


            } catch (Exception e) {
                //if something wrong, make a Toast message
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Refresh data on button click
    View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FetchData dtask = new FetchData();
            dtask.execute();
            Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_LONG).show();
        }
    };


}
