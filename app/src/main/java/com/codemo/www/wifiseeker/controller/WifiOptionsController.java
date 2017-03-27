package com.codemo.www.wifiseeker.controller;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.model.WifiNetwork;
import com.codemo.www.wifiseeker.view.WifiOptionsFragment;

import static android.app.PendingIntent.getActivity;
import static com.codemo.www.wifiseeker.view.MainActivity.manager;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by root on 3/25/17.
 */

public class WifiOptionsController extends AppCompatActivity {

    public static void showWifiOptions(){
        NavigationContoller.navigateTo("wifiOptionsFragment",manager);
    }

    // record the index of the network from the list view
    public static void setPosition(Integer position){
        WifiOptionsFragment wifiOptionsFragment =(WifiOptionsFragment) manager.findFragmentByTag("wifiOptionsFragment");
        wifiOptionsFragment.setPosition(position);
    }

    // record the index of the network from the list view
    public static void setOpenPosition(Integer position){
        WifiOptionsFragment wifiOptionsFragment =(WifiOptionsFragment) manager.findFragmentByTag("wifiOptionsFragment");
        wifiOptionsFragment.setOpenPosition(position);
    }
    // create the dialog box containing ifi network details
    public static void showWifiDialog(Integer position, Boolean open){
        AlertDialog.Builder builder = new AlertDialog.Builder(manager.findFragmentByTag("wifiOptionsFragment").getActivity());
        View mview =  LayoutInflater.from(manager.findFragmentByTag("wifiOptionsFragment").getActivity()).inflate(R.layout.wifi_option_item,null);
        ListAdapter listAdapter;
        if(open){
            listAdapter = new ArrayAdapter<String>(manager.findFragmentByTag("wifiOptionsFragment").getActivity(),android.R.layout.simple_list_item_1, WifiNetwork.getOpenData(position));
        }else{
            listAdapter = new ArrayAdapter<String>(manager.findFragmentByTag("wifiOptionsFragment").getActivity(),android.R.layout.simple_list_item_1, WifiNetwork.getData(position));
        }
        ListView detailList = (ListView)mview.findViewById(R.id.detailList);
        detailList.setAdapter(listAdapter);
        builder.setView(mview);
        AlertDialog mdiaDialog = builder.create();
        mdiaDialog.show();

    }
}
