package com.example.pointstracker;

public class DriverInfo {
    public String name;
    public double latitude;
    public double longitude;
    public DriverInfo(){}
    public DriverInfo(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getname() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

