package com.example.bs00;

public class Venue {
    private String venueId;
    public String venueName;
    private String venueType;
    public double venueLat;
    public double venueLong;


    public Venue(){

    }

    public Venue(String venueId, String venueName, String venueType,double venueLat, double venueLong) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueType = venueType;
        this.venueLat =  venueLat;
        this.venueLong =  venueLong;


    }

    public String getVenueId() {
        return venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueType() {
        return venueType;
    }

    public double getVenueLong() { return venueLong;
    }

    public double getVenueLat() {return venueLat;
    }}
