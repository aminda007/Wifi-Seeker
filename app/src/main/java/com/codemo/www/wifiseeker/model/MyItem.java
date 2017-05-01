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
    private Integer mid;


    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MyItem( double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;

//        mopen=o
    }

    public MyItem(Integer id,double lat, double lng, boolean opens) {
        mPosition = new LatLng(lat, lng);
        mid= id;
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
}
