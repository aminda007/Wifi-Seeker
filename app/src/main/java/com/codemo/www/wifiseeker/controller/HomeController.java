package com.codemo.www.wifiseeker.controller;

import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.codemo.www.wifiseeker.model.WifiReceiver;
import com.codemo.www.wifiseeker.view.HomeFragment;
import com.codemo.www.wifiseeker.view.MainActivity;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;

/**
 * Created by root on 3/24/17.
 */

public class HomeController extends AppCompatActivity {
    private WifiInfo wifiInfo;
    private WifiManager wifiManager;
    private String wifiDetails;

//    @Override
    public String getwifiDetails() {
//        wifiInfo =wifiManager.getConnectionInfo();
//        wifiDetails=" SSID "+wifiInfo.getSSID().toString()+
//                "\n Mac-address "+ wifiInfo.getMacAddress().toString()+
//                "\n Frequency "+wifiInfo.getFrequency() +
//                "\n Link speed (Mbps) "+wifiInfo.getLinkSpeed()+
//                "\n IP address "+wifiInfo.getIpAddress()+
//                "\n network id "+wifiInfo.getNetworkId()+
//                "\n Rssi "+wifiInfo.getRssi()+
//                "\n BSSID "+wifiInfo.getBSSID();
//        return wifiDetails;
        return null;
    }

//    @Override
    public void showWifiOptions(String name, String capablity, Integer frequency, Integer level) {

    }

//    @Override
    public void sendWifiData() {

    }
//    public static void showWifiDetails(String s){
//        HomeFragment.showWifiDetails(s);
//    }
    public void showWifiNames(String[] wifiNames, Integer size){
//        NavigationContoller.navigateTo("HomeFragment",manager);
        if (manager.findFragmentByTag("HomeFragment") == null) {
            Toast.makeText(getApplicationContext(), "xxxxx ", Toast.LENGTH_SHORT).show();
        }
        HomeFragment homeFragment =(HomeFragment) manager.findFragmentByTag("HomeFragment");
        homeFragment.showWifiNames(wifiNames,size);
        Log.v("rht",size.toString()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa***************");
//        Toast.makeText(getApplicationContext(), "Wifi list updated :", Toast.LENGTH_SHORT).show();
    }
    public void showOpenWifiNames(String[] wifiNames, Integer size){
//        NavigationContoller.navigateTo("HomeFragment",manager);
        if (manager.findFragmentByTag("HomeFragment") == null) {
            Toast.makeText(getApplicationContext(), "xxxxx ", Toast.LENGTH_SHORT).show();
        }
        HomeFragment homeFragment =(HomeFragment) manager.findFragmentByTag("HomeFragment");
        homeFragment.showOpenWifiNames(wifiNames,size);
        Log.v("rht",size.toString()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa***************");
//        Toast.makeText(getApplicationContext(), "Wifi list updated :", Toast.LENGTH_SHORT).show();
    }
    public  void startScan(){
//        new MainActivity().startReceive();
//        wifiList = wifiManager.getScanResults();
    }
//    public static void setData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
//        HomeFragment homeFragment =(HomeFragment) manager.findFragmentByTag("HomeFragment");
//        homeFragment.setData(wifiNames,wifiCapabilities,wifiFrequency,wifiLevel);
//    }
}