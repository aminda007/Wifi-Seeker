package com.codemo.www.wifiseeker.model;

/**
 * Created by root on 3/24/17.
 */

public class WifiNetwork {
    static String[] Names;
    static String[] Capabilities;
    static Integer[] frequency;
    static Integer[] level;
    static String[] openNames;
    static String[] openCapabilities;
    static Integer[] openFrequency;
    static Integer[] openLevel;
    public static void setData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
        Names=wifiNames;
        Capabilities= wifiCapabilities;
        frequency = wifiFrequency;
        level=wifiLevel;
    }
    public static void setOpenData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
        openNames=wifiNames;
        openCapabilities= wifiCapabilities;
        openFrequency = wifiFrequency;
        openLevel=wifiLevel;
    }
    public static String[] getData(Integer position){
        String[] wifiInfomation = {
                "Name               : "+Names[position],
                "Authentication     : "+getAuth(Capabilities[position]),
                "Frequency(MHz)     : "+frequency[position],
                "Signal level(dBm)  : "+getSignal(level[position])};


        return wifiInfomation;
    }
    public static String[] getOpenData(Integer position){
        String[] wifiInfomation = {
                "Name               : "+openNames[position],
                "Authentication     : "+"None",
                "Frequency(MHz)     : "+frequency[position],
                "Signal level(dBm)  : "+getSignal(level[position])};


        return wifiInfomation;
    }

    public static String getAuth(String auth){
        if(auth.toUpperCase().contains("WEP")){
            auth="WEP";
        }else if(auth.toUpperCase().contains("WPA")) {
            auth = "WPA";
        }
        return auth;
    }
    public static String getSignal(Integer level){
        String signal;
        if(level>=-55){
            signal="Excellent";
        }else if(level>=-65){
            signal="Good";
        }else if(level>=-75){
            signal="Fair";
        }else{
            signal="Weak";
        }
        return signal;
    }

    public static String getName(Integer position){
        return Names[position];
    }
    public static String getOpenName(Integer position){
        return openNames[position];
    }
    public static String getCapabilities(Integer position){
        return Capabilities[position];
    }
    public static String getOpenCapabilities(Integer position){
        return openCapabilities[position];
    }
}
