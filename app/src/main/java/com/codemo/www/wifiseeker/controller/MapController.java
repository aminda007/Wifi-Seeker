package com.codemo.www.wifiseeker.controller;

import com.codemo.www.wifiseeker.model.WifiLocation;

/**
 * Created by root on 4/2/17.
 */

public class MapController {
    public static void geoLocate(){

    }

    public static void updateLocation(double lat, double lng){
        WifiLocation.setLat(lat);
        WifiLocation.setLng(lng);
    }
}
