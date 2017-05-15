package com.codemo.www.wifiseeker.view;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.WifiController;
import com.codemo.www.wifiseeker.model.WifiLocator;
import com.codemo.www.wifiseeker.model.WifiReceiver;

import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    static MainActivity Activity;
    Switch wifiSwitch;
    Switch locationSwitch;
    Switch automaticSwitch;
    Switch dataSwitch;
    Spinner range;
    static boolean active;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        wifiSwitch = (Switch) view.findViewById(R.id.wifiSwitch);
        locationSwitch = (Switch) view.findViewById(R.id.locationSwitch);
        automaticSwitch = (Switch) view.findViewById(R.id.automaticSwitch);
        dataSwitch = (Switch) view.findViewById(R.id.dataSwitch);
        range = (Spinner) view.findViewById(R.id.spinnerRange);
        String[] items = new String[]{"2","5","10","20","30"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,items);
        range.setAdapter(adapter);
        range.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa..."+position);
                if(position==0){

                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa..."+0);
                        WifiLocator.setRange(2000f);
                }else if(position==1) {


                    Log.v("rht", "aaaaaaaaaaaaaaaaaaaa..." + 1);
                    WifiLocator.setRange(5000f);
                }
                else if(position==2) {

                    Log.v("rht", "aaaaaaaaaaaaaaaaaaaa..." + 2);
                    WifiLocator.setRange(10000f);
                }
                else if(position==3) {
                    Log.v("rht", "aaaaaaaaaaaaaaaaaaaa..." + 3);
                    WifiLocator.setRange(20000f);
                }
                else if(position==4) {
                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa..."+4);
                        WifiLocator.setRange(30000f);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        wifiSwitch.setOnCheckedChangeListener( new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(active){
                    if(isChecked){
                        wifiManager.setWifiEnabled(true);
                        //  Activity.startScan();
                    }else{
                        wifiManager.setWifiEnabled(false);
                    }
                }

            }
        });
        automaticSwitch.setOnCheckedChangeListener( new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(active){
                    if(isChecked){
                        WifiReceiver.setAutoConnect(true);
                        //  Activity.startScan();
                    }else{
                        WifiReceiver.setAutoConnect(false);
                    }
                }

            }
        });
        locationSwitch.setOnCheckedChangeListener( new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(active){
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }

            }
        });
        dataSwitch.setOnCheckedChangeListener( new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(active){
                    Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(i);
                }

            }
        });
        return view;
    }

    public static void setActivity(MainActivity activity){
        Activity=activity;
    }
    public void setSwitches(){
        active=false;
        if(wifiManager.isWifiEnabled()){
            wifiSwitch.setChecked(true);
        }else{
            wifiSwitch.setChecked(false);
        }
        if(Activity.isNetworkAvailable()){
            dataSwitch.setChecked(true);
        }else{
            dataSwitch.setChecked(false);
        }
        if(Activity.isGpsAvailable()){
            locationSwitch.setChecked(true);
        }else{
            locationSwitch.setChecked(false);
        }
        active=true;
    }
}
