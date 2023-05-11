package com.example.orai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Weather> weathers;
    private WeatherDAO weatherDAO;
    Button add;
    PopupWindow popupWindow;
    Activity activity;
    ListView listView;
    CustomWeatherList customWeatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherDAO = new WeatherDAO(getApplicationContext());
        listView = findViewById(R.id.list);
        add = findViewById(R.id.create_button);
        activity = this;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopup();
            }
        });

        weathers = (ArrayList<Weather>) weatherDAO.readAll();
        //sukuriamas adapteris klase sujungianti 2 skirtingas klases
        customWeatherList = new CustomWeatherList(activity, weathers, weatherDAO);
        listView.setAdapter(customWeatherList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity, "Pasirinkote:" +
                        weathers.get(position).getCountryName() +
                        " valstybę", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void addPopup() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.edit_popup, MainActivity.this.findViewById(R.id.popup));
        popupWindow = new PopupWindow(layout, 600, 670, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        //susiejamas kodas su vaizdu
        EditText country = layout.findViewById(R.id.edit_country);
        EditText degrees = layout.findViewById(R.id.edit_degrees);
        Button save = layout.findViewById(R.id.edit_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String country2 = country.getText().toString();
                String degrees2 = degrees.getText().toString();

                Weather weather = new Weather(country2, Double.parseDouble(degrees2));


                weatherDAO.create(weather);

                if(customWeatherList == null){
                    customWeatherList = new CustomWeatherList((Activity) getApplicationContext(), weathers, weatherDAO);
                    listView.setAdapter(customWeatherList);
                }

                customWeatherList.setWeathers((ArrayList<Weather>) weatherDAO.readAll());

                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();

                popupWindow.dismiss();

            }
        });



    }


}