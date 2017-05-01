package com.codemo.www.wifiseeker.controller;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.view.MainActivity;
import com.codemo.www.wifiseeker.view.WifiConnectFragment;
import android.support.v7.app.AppCompatActivity;
import static com.codemo.www.wifiseeker.view.MainActivity.manager;
import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;

/**
 * Created by root on 3/24/17.
 */

public class WifiController extends AppCompatActivity {
    static MainActivity Activity;

    public static void showEnableWifi(FragmentManager manager){
        FragmentTransaction transaction = manager.beginTransaction();
        String backStateName = "wifiConnnectFragment";
        if (manager.findFragmentByTag(backStateName) == null) {
            transaction.add(R.id.activity_main, new WifiConnectFragment(), backStateName);
        } else {
            transaction.show(manager.findFragmentByTag(backStateName));
        }
        if(manager.findFragmentByTag("wifiOptionsFragment") != null) {
            transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
        }
        if(manager.findFragmentByTag("MapsFragment") != null){
            transaction.hide(manager.findFragmentByTag("MapsFragment"));
        }if(manager.findFragmentByTag("SettingsFragment") != null){
            transaction.hide(manager.findFragmentByTag("SettingsFragment"));
        }
        if(manager.findFragmentByTag("HomeFragment") != null){
            transaction.hide(manager.findFragmentByTag("HomeFragment"));
        }
        if(manager.findFragmentByTag("MapOptionsFragment") != null){
            transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
        }
        transaction.commit();
    }


    public static void enableWifi(){
        wifiManager.setWifiEnabled(true);
        NavigationContoller.navigateTo("HomeFragment",manager);
        Activity.startScan();

    }
    public static void disableWifi(){
        wifiManager.setWifiEnabled(false);
    }

    public static void setActivity(MainActivity activity){
        Activity=activity;
    }
}
