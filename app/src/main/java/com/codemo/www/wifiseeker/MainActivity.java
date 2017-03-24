package com.codemo.www.wifiseeker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

//import android.net.wifi.W

public class MainActivity extends AppCompatActivity implements WifiConnectFragment.WifiConnectListener ,HomeFragment.HomeFragmentListener, OnMapReadyCallback , WifiOptionsFragment.WifiOptionsFragmentListener{
    private BottomNavigationView bottomNavigationView;
//    private TextView infoTextView;
    private WifiManager wifiManager;
//    private WifiR
    private WifiInfo wifiInfo;
    private String wifiDetails;
    private StringBuilder  wifiAccessList;
//    StringBuilder
    private List<ScanResult> wifiList;
    private List<WifiConfiguration> wifiConfig;
    private WifiReceiver receiver;
    private boolean scaned=false;
    GoogleMap mgoogleMap;
    MapFragment mapFragment;
    String[] wifiNames;
    String[] wifiCapabilities;
    Integer[] wifiFrequency;
    Integer[] wifiLevel;

    String Names;
    String Capabilities;
    Integer frequency;
    Integer level;

//    private MenuItem home;
   // GoogleMap mGoogleMap;

    private class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            wifiAccessList = new StringBuilder();

            wifiList=wifiManager.getScanResults();
//            wifiConfig = wifiManager.getConfiguredNetworks();
//            String s="";
//            for(int i = 0; i< wifiConfig.size(); i++){
//                s=s+wifiConfig.get(i).SSID;
//            }
//            Toast.makeText(getApplicationContext(), "connections = "+ s , Toast.LENGTH_SHORT).show();
            wifiNames =new String[wifiList.size()];
            wifiCapabilities = new String[wifiList.size()];
            wifiFrequency = new Integer[wifiList.size()];
            wifiLevel = new Integer[wifiList.size()];

            wifiAccessList.append("\n wifi connections : "+ wifiList.size()+"\n \n");
/////////////////////         creating the wifi access point list
            for(int i = 0;i<wifiList.size();i++){
                wifiNames[i]=wifiList.get(i).SSID;
                wifiCapabilities[i]=wifiList.get(i).capabilities;
                wifiFrequency[i]=wifiList.get(i).frequency;
                wifiLevel[i]=wifiList.get(i).level;
//                Toast.makeText(getApplicationContext(), "wifi updated ", Toast.LENGTH_SHORT).show();
                wifiAccessList.append(new Integer(i+1).toString()+". ");
                wifiAccessList.append(
                        "Name : "+wifiList.get(i).SSID+"\n"+
                        "Authentication : "+wifiList.get(i).capabilities+"\n"+
                        "Frequency(MHz) : "+wifiList.get(i).frequency+ "\n"+
                        "Signal level(dBm) : "+wifiList.get(i).level+ "\n"
                );
                wifiAccessList.append("\n \n");
            }

            HomeFragment homeFragment =(HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
            homeFragment.showWifiDetails(wifiAccessList.toString());
            String count = String.valueOf(wifiList.size());
//            Toast.makeText(getApplicationContext(), "count is "+count, Toast.LENGTH_SHORT).show();
            homeFragment.showWifiNO(count);
            homeFragment.showWifiNames(wifiNames);
//            homeFragment.setScanResults(wifiList);
            homeFragment.setData(wifiNames,wifiCapabilities,wifiFrequency,wifiLevel);
            wifiList.clear();
            wifiAccessList.delete(0,wifiAccessList.length());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checks google play services availability
        if(googleServicesAvailable()){
            Toast.makeText(this, "Connected to Google Play Services" ,Toast.LENGTH_LONG).show();
        }
        // show home fragment
//        initHome();

        // creating references for interface components
//        infoTextView=(TextView)findViewById(R.id.infoTextView);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        // bottom navigation implementation
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        // switching tabs in bottom navigation bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ////////////////////////////////     switch to home fragment
                if(item.getItemId()==R.id.tab_home && wifiManager.isWifiEnabled()){
                    resumeHome();
                    item.setChecked(true);

                    ////////////////////////////////     switch to map fragment
                }else if(item.getItemId()==R.id.tab_map){
//                    item.setIcon(R.drawable.ic_map_selected);
                    item.setChecked(true);
//                    ((MenuItem) findViewById(R.id.tab_settings)).setChecked(false);
                    String backStateName = "MapsFragment";
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (manager.findFragmentByTag(backStateName) == null) {
                        Toast.makeText(getApplicationContext(), "map created", Toast.LENGTH_SHORT).show();
                        transaction.add(R.id.activity_main, new MapsFragment(), backStateName);
//                        initMap();

//                        manager.findFragmentByTag(backStateName).getTargetFragment().

//                        transaction.addToBackStack(backStateName);
                    } else {
                        transaction.show(manager.findFragmentByTag(backStateName));
                        Toast.makeText(getApplicationContext(), "map resumed.", Toast.LENGTH_SHORT).show();
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
                    transaction.commit();
                    ////////////////////////////////     switch to settings fragment
                }else if(item.getItemId()==R.id.tab_settings){
                    item.setChecked(true);
//                    item.setIcon(R.drawable.ic_settings_selected);
//                    ((MenuItem) findViewById(R.id.tab_map)).setIcon(R.drawable.ic_map);
                    String backStateName = "SettingsFragment";
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    if (manager.findFragmentByTag(backStateName) == null) {
                        Toast.makeText(getApplicationContext(), "settings created", Toast.LENGTH_SHORT).show();
                        transaction.add(R.id.activity_main, new SettingsFragment(), backStateName);
//                        transaction.addToBackStack(backStateName);
                    } else {
                        transaction.show(manager.findFragmentByTag(backStateName));
                        Toast.makeText(getApplicationContext(), "settings resumed.", Toast.LENGTH_SHORT).show();

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
                    transaction.commit();
                }else{
                    Toast.makeText(getApplicationContext(), "wifi is not available", Toast.LENGTH_SHORT).show();
                    showEnableWifi();
                    item.setChecked(true);
                }
                return false;
            }
        });

        initHome();
        initOptions();


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hideOptions();
    }
    private void initOptions(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, new WifiOptionsFragment(), "wifiOptionsFragment");
        transaction.commit();
    }
    private void hideOptions(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
        transaction.commit();
    }
    private void initHome() {
        if( wifiManager.isWifiEnabled()){
            String backStateName = "HomeFragment";
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Toast.makeText(getApplicationContext(), "home created", Toast.LENGTH_SHORT).show();
            transaction.add(R.id.activity_main, new HomeFragment(), backStateName);
            transaction.commit();
            receiver = new WifiReceiver();
            registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifiManager.startScan();
        }else{
            Toast.makeText(getApplicationContext(), "wifi is not available", Toast.LENGTH_SHORT).show();
            showEnableWifi();
        }

    }
    public void resumeHome(){
        String backStateName = "HomeFragment";

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //resume home
        transaction.show(manager.findFragmentByTag(backStateName));
        Toast.makeText(getApplicationContext(), "home resumed.", Toast.LENGTH_SHORT).show();
        // hide other fragments
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
        transaction.commit();
        wifiManager.startScan();

    }

    private void initMap() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap=googleMap;
        LatLng ll = new LatLng(39.008224,-76.8984527);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mgoogleMap.moveCamera(update);
    }


    // method for checking google play services availability
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

    public void showEnableWifi(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String backStateName = "wifiConnnectFragment";
        ////////
        if (manager.findFragmentByTag(backStateName) == null) {
            Toast.makeText(getApplicationContext(), "wificonnect created", Toast.LENGTH_SHORT).show();
            transaction.add(R.id.activity_main, new WifiConnectFragment(), backStateName);
//                        transaction.addToBackStack(backStateName);
        } else {
            transaction.show(manager.findFragmentByTag(backStateName));
            Toast.makeText(getApplicationContext(), "wificonnect resumed.", Toast.LENGTH_SHORT).show();

        }
        ////////
//        transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
        if(manager.findFragmentByTag("wifiOptionsFragment") != null) {
            transaction.hide(manager.findFragmentByTag("wifiOptionsFragment"));
        }
        if(manager.findFragmentByTag("MapsFragment") != null){
            transaction.hide(manager.findFragmentByTag("MapsFragment"));
        }if(manager.findFragmentByTag("SettingsFragment") != null){
            transaction.hide(manager.findFragmentByTag("SettingsFragment"));
        }
        transaction.commit();


        // Enable wifi
    }

    public void enableWifi(){
        wifiManager.setWifiEnabled(true);
        receiver = new WifiReceiver();
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String backStateName = "HomeFragment";
        if (manager.findFragmentByTag(backStateName) == null) {
            transaction.add(R.id.activity_main, new HomeFragment(), backStateName);
            Toast.makeText(getApplicationContext(), "home created", Toast.LENGTH_SHORT).show();

        }else {
            transaction.show(manager.findFragmentByTag(backStateName));

            Toast.makeText(getApplicationContext(), "home resumed.", Toast.LENGTH_SHORT).show();
        }
//        transaction.addToBackStack(backStateName);
        if(manager.findFragmentByTag("MapsFragment") != null){
            transaction.hide(manager.findFragmentByTag("MapsFragment"));
        }if(manager.findFragmentByTag("SettingsFragment") != null){
            transaction.hide(manager.findFragmentByTag("SettingsFragment"));
        }
        transaction.remove(manager.findFragmentByTag("wifiConnnectFragment"));
        transaction.commit();
    }

    @Override
    public String getwifiDetails() {
        wifiInfo =wifiManager.getConnectionInfo();
        wifiDetails=" SSID "+wifiInfo.getSSID().toString()+
                "\n Mac-address "+ wifiInfo.getMacAddress().toString()+
                "\n Frequency "+wifiInfo.getFrequency() +
                "\n Link speed (Mbps) "+wifiInfo.getLinkSpeed()+
                "\n IP address "+wifiInfo.getIpAddress()+
                "\n network id "+wifiInfo.getNetworkId()+
                "\n Rssi "+wifiInfo.getRssi()+
                "\n BSSID "+wifiInfo.getBSSID();
    return wifiDetails;
    }


//    @Override
//    public void showOptions(View view) {
//        HomeFragment homeFragment =(HomeFragment) getSupportFragmentManager().findFragmentByTag("HomeFragment");
//        homeFragment.registerForContextMenu(view);
//        (ListView)findViewById(R.id.wifiList).regiontextMenu(view);
//        this.registerForContextMenu(view);
//        this.openContextMenu(view);
//        this.unregisterForContextMenu(view);
//    }

    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        this.getMenuInflater().inflate(R.menu.wifi_options,menu);
//
//
//
//    }

//    public ScanResult getWifiListItem(int position) {
//        return wifiList.get(position);
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int selectedItemId = item.getItemId();
//        switch(selectedItemId){
//            case R.id.connect:
////                connectWifiPoint(getWifiListItem(item.getItemId()));
////                this.closeContextMenu();
//                break;
//            case R.id.share:
////                this.closeContextMenu();
//                break;
//            case R.id.WifiInfo:
////                this.closeContextMenu();
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }
    public void showWifiOptions(String name, String capability, Integer frequency, Integer level){
        this.Names=name;
        this.Capabilities= capability;
        this.frequency = frequency;
        this.level=level;
        Toast.makeText(getApplicationContext(), "inside "+Names, Toast.LENGTH_SHORT).show();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String backStateName = "wifiOptionsFragment";
        ////////
        if (manager.findFragmentByTag(backStateName) == null) {
            Toast.makeText(getApplicationContext(), "options created", Toast.LENGTH_SHORT).show();
            transaction.add(R.id.activity_main, new WifiOptionsFragment(), backStateName);
//                        transaction.addToBackStack(backStateName);
        } else {
            transaction.show(manager.findFragmentByTag(backStateName));
            Toast.makeText(getApplicationContext(), "options resumed.", Toast.LENGTH_SHORT).show();

        }
        ////////
        if(manager.findFragmentByTag("SettingsFragment") != null){
            transaction.hide(manager.findFragmentByTag("SettingsFragment"));
        }
        if(manager.findFragmentByTag("HomeFragment") != null){
            transaction.hide(manager.findFragmentByTag("HomeFragment"));
        }if(manager.findFragmentByTag("MapsFragment") != null){
            transaction.hide(manager.findFragmentByTag("MapsFragment"));
        }
        if(manager.findFragmentByTag("wifiConnnectFragment") != null){
            transaction.hide(manager.findFragmentByTag("wifiConnnectFragment"));
        }
        transaction.commit();

//        WifiOptionsFragment wifiOptionsFragment =(WifiOptionsFragment) getSupportFragmentManager().findFragmentByTag("wifiOptionsFragment");
//        wifiOptionsFragment.setDetails(Names,Capabilities,this.frequency,this.level);
//        wifiOptionsFragment.Names=name;
//        wifiOptionsFragment.setScanResult(scanResult);

        // Enable wifi
    }
    public void sendWifiData(){
        WifiOptionsFragment wifiOptionsFragment =(WifiOptionsFragment) getSupportFragmentManager().findFragmentByTag("wifiOptionsFragment");
        wifiOptionsFragment.setDetails(Names,Capabilities,this.frequency,this.level);

    }
    public void showWifiDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mview =  LayoutInflater.from(this).inflate(R.layout.wifi_option_item,null);


        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getWifiInfo());
        ListView detailList = (ListView)mview.findViewById(R.id.detailList);
        detailList.setAdapter(listAdapter);
        builder.setView(mview);
        AlertDialog mdiaDialog = builder.create();
        mdiaDialog.show();




    }

    public String[] getWifiInfo(){
        String[] wifiInfomation = {
                "Name               : "+Names,
                "Authentication     : "+Capabilities,
                "Frequency(MHz)     : "+this.frequency,
                "Signal level(dBm)  : "+this.level};


        return wifiInfomation;
    }
    public void connectWifiPoint(String name, String capabilities,String password) {
        try {

            Log.v("rht", "Item clicked, SSID " +name + " Security : " + capabilities);

            String networkSSID = name;
            String networkPass = password;

            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain ssid in quotes
            conf.status = WifiConfiguration.Status.ENABLED;
            conf.priority = 40;

            if (capabilities.toUpperCase().contains("WEP")) {
                Log.v("rht", "Configuring WEP");
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

                if (networkPass.matches("^[0-9a-fA-F]+$")) {
                    conf.wepKeys[0] = networkPass;
                } else {
                    conf.wepKeys[0] = "\"".concat(networkPass).concat("\"");
                }

                conf.wepTxKeyIndex = 0;

            } else if (capabilities.toUpperCase().contains("WPA")) {
                Log.v("rht", "Configuring WPA");

                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

                conf.preSharedKey = "\"" + networkPass + "\"";

            } else {
                Log.v("rht", "Configuring OPEN network");
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                conf.allowedAuthAlgorithms.clear();
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            }

//            WifiManager wifiManager = (WifiManager) WiFiApplicationCore.getAppContext().getSystemService(Context.WIFI_SERVICE);
            int networkId = wifiManager.addNetwork(conf);

            Log.v("rht", "Add result " + networkId);

            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                    Log.v("rht", "WifiConfiguration SSID " + i.SSID);

                    boolean isDisconnected = wifiManager.disconnect();
                    Log.v("rht", "isDisconnected : " + isDisconnected);

                    boolean isEnabled = wifiManager.enableNetwork(i.networkId, true);
                    Log.v("rht", "isEnabled : " + isEnabled);

                    boolean isReconnected = wifiManager.reconnect();
                    Log.v("rht", "isReconnected : " + isReconnected);

                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
