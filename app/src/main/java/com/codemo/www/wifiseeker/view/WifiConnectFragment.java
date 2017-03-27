package com.codemo.www.wifiseeker.view;


import android.app.Activity;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.WifiController;
import com.codemo.www.wifiseeker.model.WifiReceiver;


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiConnectFragment extends Fragment {


    public WifiConnectFragment() {
        // Required empty public constructor
    }

//    WifiConnectListener activityCommander;
//    public  interface  WifiConnectListener{
////        public void enableWifi();
//    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try{
//            activityCommander=(WifiConnectListener) activity;
//        }catch (ClassCastException e){
//            throw new ClassCastException(activity.toString());
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wifi_connect, container, false);
        final Button wifiButton = (Button)view.findViewById(R.id.wifiBtn);
        wifiButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        buttonClicked();
                    }
                }
        );
        return view;

    }

    public void buttonClicked(){
//        activityCommander.enableWifi();
        WifiController.enableWifi();
//        WifiReceiver receiver = new WifiReceiver();
//        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }
}
