package com.veryworks.android.airbnbsearch;

public class Search {
    public static final int TYPE_ONE = 10;
    public static final int TYPE_TWO = 20;
    public static final int TYPE_ENTIRE = 30;

    public static final String AM_WIFI     = "Wifi";
    public static final String AM_AIRCON   = "Air Conditioner";
    public static final String AM_REFRIGE  = "Refrigerator";
    public static final String AM_PARKING  = "Parking";
    public static final String AM_ELEVATOR = "Elevator";

    public String checkinDate = null;
    public String checkoutDate = null;
    private int guests = 1;
    public void setGuests(int count){
        if(count > 1) guests = count;
    }
    public int getGuests(){
        return guests;
    }
    public int type;
    public int price_min;
    public int price_max;
    public String amenities[];
}
