package com.example.wheatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView temp,description,date;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city=findViewById(R.id.city);
        temp=findViewById(R.id.temp);
        description=findViewById(R.id.description);
        date=findViewById(R.id.date);
        b1=findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(city.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Please enter a city name...!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    find_weather();
                }
            }
        });


    }
    public void find_weather(){
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city.getText().toString()+"&appid=981176e49f7abd267f7cd6743a046776&units=metric";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response){
                try {
                    JSONObject main_object=response.getJSONObject("main");     // {...} direct object aapelo che etle
                    JSONArray array=response.getJSONArray("weather");   // [{...}] che etle bahar no array banavyo
                    JSONObject object=array.getJSONObject(0);     // [{...}] array ni andar na 0 index vala object mate...
                    String temp1=String.valueOf(main_object.getDouble("temp"));
                    String description1=object.getString("description");

                    description.setText(description1);

                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    date.setText(currentDate+"   "+currentTime);
                    temp.setText(temp1+"°C");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){

            }
        }
        );
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jor);
    }
}