package com.example.orai;

public class Weather {
    private int id;
    private String countryName;
    private double degrees;

    public Weather() {
    }

    public Weather(int id, String countryName, double degrees) {
        this.id = id;
        this.countryName = countryName;
        this.degrees = degrees;
    }

    public Weather(String countryName, double degrees) {
        this.countryName = countryName;
        this.degrees = degrees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", degrees=" + degrees +
                '}';
    }
}
