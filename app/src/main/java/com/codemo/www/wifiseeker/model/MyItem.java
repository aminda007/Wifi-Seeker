package com.codemo.www.wifiseeker.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by root on 4/17/17.
 */

public class MyItem implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private  boolean open;
    private String mname;
    private Integer mid;
    private double mrating;
    private int mreport;


    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem( double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;

//        mopen=o
    }

    public MyItem(Integer id,String name, double lat, double lng, boolean opens,double rating, int report) {
        mPosition = new LatLng(lat, lng);
        mid= id;
        mreport=report;
        mrating=rating;
        setMname(name);
        setOpen(opens);
    }


    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Integer getId() {
        return mid;
    }

    public void setId(Integer id) {
        this.mid = id;
    }

    public double getRating() {
        return mrating;
    }

    public void setRating(double rating) {
        this.mrating = rating;
    }

    public int getReport() {
        return mreport;
    }

    public void setReport(int report) {
        this.mreport = report;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}
