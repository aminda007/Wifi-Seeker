package com.codemo.www.wifiseeker.controller;

import com.codemo.www.wifiseeker.model.WifiConnect;
import com.codemo.www.wifiseeker.model.WifiNetwork;

/**
 * Created by root on 3/25/17.
 */


public class WifiConnectionController {


    public static void setData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
        WifiNetwork.setData(wifiNames,wifiCapabilities,wifiFrequency,wifiLevel);
    }
    public static void setOpenData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
        WifiNetwork.setOpenData(wifiNames,wifiCapabilities,wifiFrequency,wifiLevel);
    }
    public static void connectWifiPoint(String name, String capabilities,String password){
        WifiConnect.connectWifiPoint(name,capabilities,password);
    }
}
