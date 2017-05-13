package com.codemo.www.wifiseeker.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.WifiController;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
//        final Button wifiButton = (Button)view.findViewById(R.id.locateBtn);
//        wifiButton.setOnClickListener(
//                new Button.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        buttonClicked();
//                    }
//                }
//        );
        return view;
    }
    public void buttonClicked(){
//        activityCommander.enableWifi();
        WifiController.disableWifi();
//        WifiReceiver receiver = new WifiReceiver();
//        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

}
