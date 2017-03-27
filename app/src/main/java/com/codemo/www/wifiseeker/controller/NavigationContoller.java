package com.codemo.www.wifiseeker.controller;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.view.*;

/**
 * Created by root on 3/24/17.
 */

public class NavigationContoller {

    public static void navigateTo(String backStateName ,FragmentManager manager){
        FragmentTransaction transaction = manager.beginTransaction();
        if(backStateName=="HomeFragment") {
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

        }
        if(backStateName=="MapsFragment") {
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
        }
        if(backStateName=="SettingsFragment") {
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
        }
        if(backStateName=="wifiConnnectFragment") {
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
        }
        transaction.commit();

    }
}
