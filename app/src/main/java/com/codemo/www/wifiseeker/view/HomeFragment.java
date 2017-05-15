package com.codemo.www.wifiseeker.view;


import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.HomeController;
import com.codemo.www.wifiseeker.controller.OnlineDatabaseController;
import com.codemo.www.wifiseeker.controller.WifiOptionsController;
import com.codemo.www.wifiseeker.model.WifiLocator;

import java.util.ArrayList;
import java.util.List;

import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView textNetworks;
    TextView textAvailable;
    TextView wifiNo;
    ListView wifiList;
    ListView wifiOpenList;
    WifiLocator locator;
    private static MainActivity activity;
    public HomeFragment() {
    }

    public static void setActivity(MainActivity activity) {
        HomeFragment.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // initialize location information
        if(activity.isNetworkAvailable()){
            initLocator();

        }

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
//        create references for the interface components
        final Button WifiScanBtn = (Button)view.findViewById(R.id.WifiScanBtn);
        Button wifiFindBtn = (Button)view.findViewById(R.id.WifiFindBtn);
        wifiNo = (TextView) view.findViewById(R.id.wifiNo);
        textNetworks = (TextView) view.findViewById(R.id.textNetworks);
        textAvailable = (TextView) view.findViewById(R.id.textAvailable);
        wifiList = (ListView) view.findViewById(R.id.wifiList);
        wifiOpenList = (ListView) view.findViewById(R.id.wifiOpenList);

        // get details on setLockconnected network
        WifiScanBtn.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        buttonClicked();
                    }
                }
        );
        // get Best wifi location
        wifiFindBtn.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if(activity.isGpsAvailable()){
                            if(WifiLocator.isDataSet()){
                                findWifi();
                            }else{
                                if(activity.isNetworkAvailable()){
                                    initLocator();
                                    Toast.makeText(getContext(),"Please wait a moment to download data", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getContext(),"Connect to internet before proceeding", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(getContext(),"enable location before proceeding", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );
        // get wifi options
        wifiList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        itemClicked(position);
                        HomeController.setLock(true);
                    }
                }
        );
        wifiOpenList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        itemClickedOpen(position);
                        HomeController.setLock(true);
                    }
                }
        );
        return view;

    }

    public void itemClicked(int position) {
        WifiOptionsController.showWifiOptions();
        WifiOptionsController.setPosition(position);
    }
    public void itemClickedOpen(int position) {
        WifiOptionsController.showWifiOptions();
        WifiOptionsController.setOpenPosition(position);
    }

    // refresh the wifiList
    public void buttonClicked(){
        wifiManager.startScan();
    }

    //Show the password protected wifi networks
    public void showWifiNames(String[] wifiNames, Integer size){
        ListAdapter listAdapter = new WifiClosedAdapter(getContext(),wifiNames);
        wifiList.setAdapter(listAdapter);
        textAvailable.setText("available");
        if(size==0){
            wifiNo.setText("No");
            textNetworks.setText("Wi-Fi networks");
        }
        else if(size==1){
            wifiNo.setText("1");
            textNetworks.setText("Wi-Fi network");
        }else{
            wifiNo.setText(size.toString());
            textNetworks.setText("Wi-Fi networks");
        }
//        Toast.makeText(this.getContext(),"wifi updated", Toast.LENGTH_SHORT).show();
    }

    //Show the open wifi networks
    public void showOpenWifiNames(String[] wifiNames, Integer size){
        ListAdapter listAdapter = new WifiOpenAdapter(getContext(),wifiNames);
        wifiOpenList.setAdapter(listAdapter);
        textAvailable.setText("available");
        if(size==0){
            wifiNo.setText("No");
            textNetworks.setText("Wi-Fi networks");
        }
        else if(size==1){
            wifiNo.setText("1");
            textNetworks.setText("Wi-Fi network");
        }else{
            wifiNo.setText(size.toString());
            textNetworks.setText("Wi-Fi networks");
        }
//        Toast.makeText(this.getContext(),"wifi updated", Toast.LENGTH_SHORT).show();
    }

    public void findWifi(){
        locator.getWifi();
    }

    public void setLocations(ArrayList<String[]> locationList) {
        locator.setData(locationList);
        WifiLocator.setDataSet(true);
    }
    public void initLocator(){
        locator=new WifiLocator();

        WifiLocator.setRange(2000f);
        OnlineDatabaseController network=new OnlineDatabaseController("getData");
        network.execute();
    }
}
