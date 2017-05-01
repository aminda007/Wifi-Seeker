package com.codemo.www.wifiseeker.model;

/**
 * Created by root on 4/14/17.
 */

public class Locations {
    private int id;
    private int report;
    private double rating;
    private double lat;
    private double lng;
    private boolean open;
    private String name;

    public Locations() {
    }

    public Locations(String name, double lat, double lng, boolean open, double rating, int report) {
        this.setName(name);
        this.setLat(lat);
        this.setLng(lng);
        this.setOpen(open);
        this.setRating(rating);
        this.setReport(report);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
