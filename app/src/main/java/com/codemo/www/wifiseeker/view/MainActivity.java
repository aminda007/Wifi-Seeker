package com.codemo.www.wifiseeker.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.NavigationContoller;
import com.codemo.www.wifiseeker.model.WifiLocator;
import com.codemo.www.wifiseeker.model.WifiNetwork;
import com.codemo.www.wifiseeker.model.WifiReceiver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


import com.codemo.www.wifiseeker.controller.*;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private WifiReceiver receiver;
    private boolean scaned=false;

    private GoogleMap mgoogleMap;
    private MapFragment mapFragment;

    public static FragmentManager manager;
    public static WifiManager wifiManager;

    public static DatabaseController dbController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initiate Fragment Manager
        manager = getSupportFragmentManager();

        // checks google play services availability
        if(!googleServicesAvailable()){
            Toast.makeText(this, "Can not connect to Google Play Services" ,Toast.LENGTH_LONG).show();
        }

        // crate options fragment
        FragmentTransaction transaction = manager.beginTransaction();


        transaction.add(R.id.activity_main, new WifiOptionsFragment(), "wifiOptionsFragment");
        transaction.add(R.id.activity_main, new MapOptionsFragment(), "MapOptionsFragment");
        transaction.add(R.id.activity_main, new MapsFragment(), "MapsFragment");
        transaction.add(R.id.activity_main, new SettingsFragment(), "SettingsFragment");
        transaction.commit();
//
//        // create mapsFragment
//        transaction.add(R.id.activity_main, new WifiOptionsFragment(), "MapsFragment");
//        transaction.commit();

        // initiate WifiManager
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // start scanning for wifi
        if(wifiManager.isWifiEnabled()){
            startScan();
        }
        // check internet connection
        MapsFragment.setInternet(isNetworkAvailable());
        //send reference of this activity to others
        HomeFragment.setActivity(this);
        WifiController.setActivity(this);
        MapsFragment.setActivity(this);
        WifiLocator.setActivity(this);
        NavigationContoller.setActivity(this);
        SettingsFragment.setActivity(this);
        MapOptionsFragment.setActivity(this);
        WifiOptionsFragment.setActivity(this);
        WifiNetwork.setActivity(this);
        OnlineDatabaseController.setActivity(this);
        // create an DatabaseController object
        dbController = new DatabaseController(this,null,null,1);

        // bottom navigation implementation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        // switching tabs in bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ////////////////////////////////     switch to home fragment
                if(item.getItemId()==R.id.tab_home && wifiManager.isWifiEnabled()){
                    item.setChecked(true);
                    NavigationContoller.navigateTo("HomeFragment",manager);

                    ////////////////////////////////     switch to map fragment
                }else if(item.getItemId()==R.id.tab_map){
//                    item.setIcon(R.drawable.ic_map_selected);
                    item.setChecked(true);
                    NavigationContoller.navigateTo("MapsFragment",manager);

                    ////////////////////////////////     switch to settings fragment
                }else if(item.getItemId()==R.id.tab_settings){
                    item.setChecked(true);
                    NavigationContoller.navigateTo("SettingsFragment",manager);

                    ////////////////////////////////      show enable wifi option
                }else{
                    WifiController.showEnableWifi(manager);
                    item.setChecked(true);
                }
                return false;
            }
        });

        // create Home if wifi is enabled
        if(wifiManager.isWifiEnabled()) {
            NavigationContoller.navigateTo("HomeFragment", manager);
            //      show enable wifi option
        }else{
            WifiController.showEnableWifi(manager);
        }
    }///////////////////////////// end of onCrate method


    // start scanning for wifi
    public void startScan(){
        receiver = new WifiReceiver();
        HomeController.setLock(false);
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hideOptions();
    }
        @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        super.onResume();
    }

    @Override
    protected void onPause() {
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        super.onPause();
    }

    @Override
    public void onBackPressed() {

//        View view2= LayoutInflater.from(this).inflate(R.layout.wifi_rate,null);
        final AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(this);
//        alertBuilder.setView(view2);
        alertBuilder.setIcon(R.drawable.ic_exit);
        alertBuilder.setMessage("Are you sure you want to exit?");
        alertBuilder.setTitle("Confirm Exit!");

//        ratingBar = (RatingBar) view2.findViewById(R.id.ratingBar);
        alertBuilder.setCancelable(true).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                                    DatabaseController dbc= MainActivity.dbController;

            }
        });
        alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                                    DatabaseController dbc= MainActivity.dbController;
                finish();
            }
        });
        Dialog dialog =alertBuilder.create();
        dialog.show();

    }

    // hide wifiOptions Fragment
    private void hideOptions(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//
        transaction.hide(manager.findFragmentByTag("MapOptionsFragment"));
        transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
        transaction.hide(manager.findFragmentByTag("MapsFragment"));
        transaction.hide(manager.findFragmentByTag("SettingsFragment"));
        transaction.commit();
    }

//    // method for checking google play services availability
    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog=api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }else{
            Toast.makeText(this, "Can not connected to Google Play Services" ,Toast.LENGTH_LONG).show();
        }
        return false;
    }

//

    public MapFragment  initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        return mapFragment;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
//    public boolean isNetworkAvailable(){
//        try{
//            InetAddress address = InetAddress.getByName("www.google.com");
//            return !address.equals("");
//        }catch (UnknownHostException e){
//
//        }
//        return false;
//    }
//    public boolean isNetworkAvailable(){
//        String command ="ping -c 1 google.com";
//        return Runtime.getRuntime().exec(command).waitFor()==0;
//    }

    public boolean isGpsAvailable(){
        LocationManager lm =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
////    @Override
//    public String getwifiDetails() {
//        wifiInfo =wifiManager.getConnectionInfo();
//        wifiDetails=" SSID "+wifiInfo.getSSID().toString()+
//                "\n Mac-address "+ wifiInfo.getMacAddress().toString()+
//                "\n Frequency "+wifiInfo.getFrequency() +
//                "\n Link speed (Mbps) "+wifiInfo.getLinkSpeed()+
//                "\n IP address "+wifiInfo.getIpAddress()+
//                "\n network id "+wifiInfo.getNetworkId()+
//                "\n Rssi "+wifiInfo.getRssi()+
//                "\n BSSID "+wifiInfo.getBSSID();
//    return wifiDetails;
//    }
//



}
