package com.example.locations.objects;

public class Locations {

    String id;
    String date;
    String time;
    String longitude;
    String latitude;
    String location;


    public Locations(){

    }

    public Locations(String id, String date, String time, String longitude, String latitude){

        this.id = id;
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;

    }



    public void setId(String id){
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getId(){
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLocation() {
        return location;
    }


}
