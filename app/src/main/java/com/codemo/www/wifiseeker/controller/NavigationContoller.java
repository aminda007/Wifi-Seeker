package com.codemo.www.wifiseeker.controller;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.view.*;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;
import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;

/**
 * Created by root on 3/24/17.
 */

public class NavigationContoller {
    private static MainActivity activity;


    public static void navigateTo(String backStateName ,FragmentManager manager){
        FragmentTransaction transaction = manager.beginTransaction();
        if(backStateName=="HomeFragment") {
            HomeController.setLock(false);
            wifiManager.startScan();
            if (manager.findFragmentByTag(backStateName) == null) {
                transaction.add(R.id.activity_main, new HomeFragment(), backStateName);
            } else {
                transaction.show(manager.findFragmentByTag(backStateName));
            }
            if(manager.findFragmentByTag("MapsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapsFragment"));
            }if(manager.findFragmentByTag("SettingsFragment") != null){
                transaction.hide(manager.findFragmentByTag("SettingsFragment"));
            }
            if(manager.findFragmentByTag("wifiConnnectFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("wifiOptionsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
            }
            if(manager.findFragmentByTag("MapOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
            }

        }
        if(backStateName=="MapsFragment") {
            HomeController.setLock(false);
//            wifiManager.startScan();
            if (manager.findFragmentByTag(backStateName) == null) {
                transaction.add(R.id.activity_main, new MapsFragment(), backStateName);
            }else{
                if(MapsFragment.isMarkerSet()){

                }else{
                    if(activity.isNetworkAvailable()){
                        MapsFragment fragment =(MapsFragment) manager.findFragmentByTag("MapsFragment");
                        fragment.getLocations();
                    }else{
                        Toast.makeText(activity.getApplicationContext(), "Connect to internet to see networks" ,Toast.LENGTH_LONG).show();
                    }
                }
                transaction.show(manager.findFragmentByTag(backStateName));
            }
            if(manager.findFragmentByTag("HomeFragment") != null){
                transaction.hide(manager.findFragmentByTag("HomeFragment"));
            }if(manager.findFragmentByTag("SettingsFragment") != null){
                transaction.hide(manager.findFragmentByTag("SettingsFragment"));
            }
            if(manager.findFragmentByTag("wifiConnnectFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("wifiOptionsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
            }
            if(manager.findFragmentByTag("MapOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
            }
        }
        if(backStateName=="SettingsFragment") {
            HomeController.setLock(false);
//            wifiManager.startScan();
            if (manager.findFragmentByTag(backStateName) == null) {
                transaction.add(R.id.activity_main, new SettingsFragment(), backStateName);
                SettingsFragment fragment =(SettingsFragment) manager.findFragmentByTag("SettingsFragment");
                fragment.setSwitches();
            } else {
                transaction.show(manager.findFragmentByTag(backStateName));
                SettingsFragment fragment =(SettingsFragment) manager.findFragmentByTag("SettingsFragment");
                fragment.setSwitches();
            }
            if(manager.findFragmentByTag("HomeFragment") != null){
                transaction.hide(manager.findFragmentByTag("HomeFragment"));
            }if(manager.findFragmentByTag("MapsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapsFragment"));
            }
            if(manager.findFragmentByTag("wifiConnnectFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("wifiOptionsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
            }
            if(manager.findFragmentByTag("MapOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
            }
        }
        if(backStateName=="wifiConnnectFragment") {
            HomeController.setLock(false);
//            wifiManager.startScan();
            if (manager.findFragmentByTag(backStateName) == null) {
                transaction.add(R.id.activity_main, new WifiConnectFragment(), backStateName);
            } else {
                transaction.show(manager.findFragmentByTag(backStateName));
            }
            if(manager.findFragmentByTag("HomeFragment") != null){
                transaction.hide(manager.findFragmentByTag("HomeFragment"));
            }if(manager.findFragmentByTag("MapsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapsFragment"));
            }
            if(manager.findFragmentByTag("wifiOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("SettingsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("SettingsFragment"));
            }
            if(manager.findFragmentByTag("MapOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
            }
        }
        if(backStateName=="wifiOptionsFragment") {
            if (manager.findFragmentByTag(backStateName) == null) {
                transaction.add(R.id.activity_main, new WifiOptionsFragment(), backStateName);
            } else {
                transaction.show(manager.findFragmentByTag(backStateName));
            }
            if(manager.findFragmentByTag("HomeFragment") != null){
                transaction.hide(manager.findFragmentByTag("HomeFragment"));
            }if(manager.findFragmentByTag("MapsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapsFragment"));
            }
            if(manager.findFragmentByTag("wifiConnnectFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("SettingsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("SettingsFragment"));
            }
            if(manager.findFragmentByTag("MapOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
            }
        }
        if(backStateName=="MapOptionsFragment") {
            HomeController.setLock(false);
//            wifiManager.startScan();
            if (manager.findFragmentByTag(backStateName) == null) {
                transaction.add(R.id.activity_main, new MapOptionsFragment(), backStateName);
            } else {
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa... showing map options...aaaaaaaaaaaaaaaaaaaaaa**********");
                transaction.show(manager.findFragmentByTag(backStateName));
            }
            if(manager.findFragmentByTag("HomeFragment") != null){
                transaction.hide(manager.findFragmentByTag("HomeFragment"));
            }if(manager.findFragmentByTag("MapsFragment") != null){
                transaction.hide(manager.findFragmentByTag("MapsFragment"));
            }
            if(manager.findFragmentByTag("wifiConnnectFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("wifiOptionsFragment") != null){
                transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
            }
            if(manager.findFragmentByTag("SettingsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("SettingsFragment"));
            }
        }
        transaction.commit();

    }

    public static void setActivity(MainActivity activity) {
        NavigationContoller.activity = activity;
    }
}
