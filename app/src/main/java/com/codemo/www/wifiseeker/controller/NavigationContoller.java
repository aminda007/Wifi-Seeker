package com.codemo.www.wifiseeker.controller;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.view.*;

import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;

/**
 * Created by root on 3/24/17.
 */

public class NavigationContoller {

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
                transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
            }
            if(manager.findFragmentByTag("SettingsFragment") != null) {
                transaction.hide(manager.findFragmentByTag("SettingsFragment"));
            }
        }
        transaction.commit();

    }
}
