package com.example.orai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeatherDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weather_data";
    public static final String TABLE_NAME = "weathers";

    public static final String KEY_ID = "id";
    public static final String COUNTRY_NAME = "country_name";
    public static final String DEGREES = "degrees";


    public WeatherDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," + COUNTRY_NAME + " TEXT," + DEGREES + " REAL" + ")";

        db.execSQL(CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void create(Weather weather){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COUNTRY_NAME, weather.getCountryName());
        values.put(DEGREES, weather.getDegrees());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<Weather> readAll(){
        List<Weather> weathersList = new ArrayList<>();
        String querry = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querry, null);

        //pradedam nuo pirmo
        if(cursor.moveToFirst()){
            do{
                Weather weather = new Weather();
                weather.setId(Integer.parseInt(cursor.getString(0)));
                weather.setCountryName(cursor.getString(1));
                weather.setDegrees(cursor.getDouble(2));

                weathersList.add(weather);
            }
            while (cursor.moveToNext());
        }
        db.close();

        return weathersList;
    }

    public int update(Weather weather){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COUNTRY_NAME, weather.getCountryName());
        values.put(DEGREES, weather.getDegrees());

        int message = db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{String.valueOf(weather.getId())});

        db.close();

        return message;
    }

    public void delete(Weather weather){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(weather.getId())});

        db.close();

    }


}
