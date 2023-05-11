package com.example.orai;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

//BaseAdapter sujungs duomenis is DB su vaizdu(xml)
public class CustomWeatherList extends BaseAdapter {

    //apsirasome kintamuosius
    private Activity context;
    private ArrayList<Weather> weathers;
    private PopupWindow popupWindow;
    private WeatherDAO weatherDAO;

    public CustomWeatherList(Activity context, ArrayList<Weather> weathers, WeatherDAO weatherDAO) {
        this.context = context;
        this.weathers = weathers;
        this.weatherDAO = weatherDAO;
    }

    public void setWeathers(ArrayList<Weather> weathers) {
        this.weathers = weathers;
    }

    //savo viduje turi GUI (xml) elementus (visas iraso vaizdas)
    public static class ViewHolder {
        TextView textViewId;
        TextView textViewCountry;
        TextView textViewDegrees;
        Button editButton;
        Button deleteButton;
    }


    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //susiesime ViewHolder klases kintamuosius su GUI (xml) esančiais elementais
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder viewHolder;

        //jeigu vaizdo dar neturime
        if (convertView == null) {
            viewHolder = new ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);

            //susiejame koda su vaizdu xml
            viewHolder.textViewId = row.findViewById(R.id.id);
            viewHolder.textViewCountry = row.findViewById(R.id.country);
            viewHolder.textViewDegrees = row.findViewById(R.id.degree);

            viewHolder.editButton = row.findViewById(R.id.edit_button);
            viewHolder.deleteButton = row.findViewById(R.id.delete_button);

            //isaugo viewholder kartu su vaizdu
            row.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //uzpildymas vaizdo irasai is db
        //konvertuojam i String ( "" + int/double)
        viewHolder.textViewId.setText("" + weathers.get(position).getId());
        viewHolder.textViewCountry.setText(weathers.get(position).getCountryName());
        viewHolder.textViewDegrees.setText("" + weathers.get(position).getDegrees());

        final int positionPopup = position;

        //aprasomas kodas kas bus paspaudus mygtuka
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopup(positionPopup);
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDAO.delete(weathers.get(positionPopup));
                //kai istriname irasa reikia atnaujinti sarasa
                //turime nurodyti kokio listo norime castinimas
                weathers = (ArrayList<Weather>) weatherDAO.readAll();
                notifyDataSetChanged();
            }
        });

        return row;
    }

    private void editPopup(final int positionPopup) {
        //sukuriamas isokantis langas
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.edit_popup, context.findViewById(R.id.popup));
        popupWindow = new PopupWindow(layout, 600, 670, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        //susiejamas kodas su vaizdu
        EditText country = layout.findViewById(R.id.edit_country);
        EditText degrees = layout.findViewById(R.id.edit_degrees);
        Button save = layout.findViewById(R.id.edit_button);
        country.setText(weathers.get(positionPopup).getCountryName());
        degrees.setText("" + weathers.get(positionPopup).getDegrees());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String country2 = country.getText().toString();
            String degrees2 = degrees.getText().toString();

            Weather weather = weathers.get(positionPopup);
            weather.setCountryName(country2);
            weather.setDegrees(Double.parseDouble(degrees2));

            weatherDAO.update(weather);

            //paredagavus atnaujiname vaizda
                weathers = (ArrayList<Weather>) weatherDAO.readAll();
                notifyDataSetChanged();

                popupWindow.dismiss();
            }
        });

    }


}
