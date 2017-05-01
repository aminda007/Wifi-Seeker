package com.codemo.www.wifiseeker.model;

/**
 * Created by root on 4/2/17.
 */

public class WifiLocation {
    private static double lat;
    private static double lng;
    public static void showLocation(){

    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        WifiLocation.lat = lat;
    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        WifiLocation.lng = lng;
    }
}
