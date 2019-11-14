package com.yujongu.startyourday;

public class CityInfo {

    private int id;
    private String name;
    private String country;
    private double cel;
    private double feh;
    private String desc;
    private double lon;
    private double lat;

    private String imageId;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getCel() {
        return cel;
    }

    public void setCel(double cel) {
        this.cel = cel;
    }

    public double getFeh() {
        return feh;
    }

    public void setFeh(double feh) {
        this.feh = feh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public CityInfo(){

    }

}
