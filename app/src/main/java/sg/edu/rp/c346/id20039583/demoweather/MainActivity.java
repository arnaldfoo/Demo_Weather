package sg.edu.rp.c346.id20039583.demoweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.awareness.state.Weather;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends AppCompatActivity {
    ListView lvWeather;
    AsyncHttpClient client; //CLient variable is a an AsyncHttpClient. Allows app to recieve and read JSON data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvWeather = findViewById(R.id.lvWeather);
        client = new AsyncHttpClient();
    }



    @Override
    protected void onResume() {
        super.onResume();

        //Create new empty arraylist of weathr object with variable "alWeather"
        ArrayList<Weather> alWeather = new ArrayList<Weather>();

        //connect AsyncHttpClient to URL where weather data is located
        client.get("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast", new JsonHttpResponseHandler() {
            String area;
            String forecast;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrForecasts = firstObj.getJSONArray("forecasts");
                    for(int i = 0; i < jsonArrForecasts.length(); i++) {
                        JSONObject jsonObjForecast = jsonArrForecasts.getJSONObject(i);
                        area = jsonObjForecast.getString("area");
                        forecast = jsonObjForecast.getString("forecast");
                        weather weather = new weather(area, forecast);
                        alWeather.add((Weather) weather);
                    }
                }
                catch(JSONException e){

                }

                //POINT X – Code to display List View

                ListView listView = (ListView) findViewById(R.id.lvWeather);


            }//end onSuccess
        });
    }//end onResume











}