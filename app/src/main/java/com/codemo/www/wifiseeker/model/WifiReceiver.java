package com.codemo.www.wifiseeker.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;

import com.codemo.www.wifiseeker.controller.HomeController;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;

import java.util.ArrayList;
import java.util.List;

import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;

/**
 * Created by root on 3/24/17.
 */

public class WifiReceiver extends BroadcastReceiver {
    static Boolean locked;


    List<ScanResult> wifiList;
    private StringBuilder  wifiAccessList;
    ArrayList<String> openWifiNames;
    ArrayList<String> closeWifiNames;
    ArrayList<String> openWifiCapabilities;
    ArrayList<String> closeWifiCapabilities;
    ArrayList<Integer> openWifiFrequency;
    ArrayList<Integer> closeWifiFrequency;
    ArrayList<Integer> openWifiLevel;
    ArrayList<Integer> closeWifiLevel;

    String[] wifiCloseNames;
    String[] wifiOpenNames;
//    String[] wifiClosedNames;
    String[] wifiCloseCapabilities;
    String[] wifiOpenCapabilities;
    Integer[] wifiCloseFrequency;
    Integer[] wifiOpenFrequency;
    Integer[] wifiCloseLevel;
    Integer[] wifiOpenLevel;
    Integer noOfOpenWifi=0;
    Integer noOfClosedWifi=0;
    @Override
        public void onReceive(Context context, Intent intent) {
        Integer noOfOpenWifi=0;
        Integer noOfClosedWifi=0;
        openWifiNames = new ArrayList<>();
        closeWifiNames = new ArrayList<>();
        openWifiCapabilities = new ArrayList<>();
        closeWifiCapabilities = new ArrayList<>();
        openWifiFrequency= new ArrayList<>();
        closeWifiFrequency= new ArrayList<>();
        openWifiLevel= new ArrayList<>();
        closeWifiLevel= new ArrayList<>();
//        Toast.makeText(, "Wifi list updated", Toast.LENGTH_SHORT).show();
        wifiList = wifiManager.getScanResults();
//        Toast.makeText(null, "count is "+wifiList.size(), Toast.LENGTH_SHORT).show();
//        wifiNames =new String[wifiList.size()];
//        wifiCapabilities = new String[wifiList.size()];
//        wifiFrequency = new Integer[wifiList.size()];
//        wifiLevel = new Integer[wifiList.size()];
        wifiAccessList = new StringBuilder();
        wifiAccessList.append("\n wifi connections : "+ wifiList.size()+"\n \n");
/////////////////////         creating the wifi access point list
        for(int i = 0;i<wifiList.size();i++){
//            wifiNames[i]=wifiList.get(i).SSID;
//            wifiCapabilities[i]=wifiList.get(i).capabilities;
            if(wifiList.get(i).capabilities.toUpperCase().contains("WEP") || wifiList.get(i).capabilities.toUpperCase().contains("WPA")){
                noOfClosedWifi++;
                closeWifiNames.add(wifiList.get(i).SSID);
                closeWifiCapabilities.add(wifiList.get(i).capabilities);
                closeWifiFrequency.add(wifiList.get(i).frequency);
                closeWifiLevel.add(wifiList.get(i).level);
            }else{
                noOfOpenWifi++;
                openWifiNames.add(wifiList.get(i).SSID);
                openWifiCapabilities.add(wifiList.get(i).capabilities);
                openWifiFrequency.add(wifiList.get(i).frequency);
                openWifiLevel.add(wifiList.get(i).level);
            }
//            wifiFrequency[i]=wifiList.get(i).frequency;
//            wifiLevel[i]=wifiList.get(i).level;
//                Toast.makeText(getApplicationContext(), "wifi updated ", Toast.LENGTH_SHORT).show();
            wifiAccessList.append(new Integer(i+1).toString()+". ");
            wifiAccessList.append(
                    "Name : "+wifiList.get(i).SSID+"\n"+
                    "Authentication : "+wifiList.get(i).capabilities+"\n"+
                    "Frequency(MHz) : "+wifiList.get(i).frequency+ "\n"+
                    "Signal level(dBm) : "+wifiList.get(i).level+ "\n"
            );
//                wifiAccessList.append("\n \n");
        }
        wifiOpenNames =new String[openWifiNames.size()];
        wifiCloseNames =new String[closeWifiNames.size()];
        wifiCloseCapabilities =new String[closeWifiCapabilities.size()];
        wifiOpenCapabilities =new String[openWifiCapabilities.size()];
        wifiCloseFrequency = new Integer[closeWifiFrequency.size()];
        wifiOpenFrequency = new Integer[openWifiFrequency.size()];
        wifiCloseLevel = new Integer[closeWifiLevel.size()];
        wifiOpenLevel = new Integer[openWifiLevel.size()];

        for(int i = 0; i< openWifiNames.size(); i++){
            wifiOpenNames[i]= openWifiNames.get(i);
            wifiOpenCapabilities [i]= openWifiCapabilities.get(i);
            wifiOpenFrequency[i]= openWifiFrequency.get(i);
            wifiOpenLevel[i]= openWifiLevel.get(i);
        }

        for(int i = 0; i< closeWifiNames.size(); i++){
            wifiCloseNames[i]= closeWifiNames.get(i);
            wifiCloseCapabilities [i]= closeWifiCapabilities.get(i);
            wifiCloseFrequency[i]= closeWifiFrequency.get(i);
            wifiCloseLevel[i]= closeWifiLevel.get(i);
        }

//            HomeFragment homeFragment =(HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
//            HomeController.showWifiDetails(wifiAccessList.toString());
//            String count = String.valueOf(wifiList.size());
//            Toast.makeText(getApplicationContext(), "count is "+count, Toast.LENGTH_SHORT).show();
//            homeFragment.showWifiNO(count);
        if(!locked) {
            new HomeController().showOpenWifiNames(wifiOpenNames, wifiList.size());
            new HomeController().showWifiNames(wifiCloseNames, wifiList.size());


            ///////////************
//            homeFragment.setScanResults(wifiList);
            WifiConnectionController.setData(wifiCloseNames, wifiCloseCapabilities, wifiCloseFrequency, wifiCloseLevel);
            WifiConnectionController.setOpenData(wifiOpenNames, wifiOpenCapabilities, wifiOpenFrequency, wifiOpenLevel);
        }
//            wifiList.clear();
//            wifiAccessList.delete(0,wifiAccessList.length());
        }
    public static void setLock(Boolean lock){
        locked =lock;

    }
}
