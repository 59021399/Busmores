package com.example.bs00;

public class UserInformation {

    public String name;
    public double latitude;
    public double longitude;
    public int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserInformation() {

    }

    public UserInformation(String name, double latitude, double longtitude, int count) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longtitude;
        this.count = count;
    }
}