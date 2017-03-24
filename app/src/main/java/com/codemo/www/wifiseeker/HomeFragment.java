package com.codemo.www.wifiseeker;


import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

   // private WifiManager wifiManager;
    TextView wifiDetailText;
    TextView wifiListed;
    TextView wifiNo;
    ListView wifiList;
    String[] Names;
    String[] Capabilities;
    Integer[] frequency;
    Integer[] level;

    List<ScanResult> scanResults;

//    private List<String> SSID;


    public HomeFragment() {
        // Required empty public constructor

    }
    HomeFragmentListener activityCommander;

//    public void setScanResults(List<ScanResult> scanResult) {
//
//        this.scanResults = scanResult;
//        Toast.makeText(getContext(), "wifi updated "+ scanResults.get(0).SSID, Toast.LENGTH_SHORT).show();
//    }

//    public void setSSID(List<String> SSID) {
//        this.SSID = SSID;
//    }

    public  interface  HomeFragmentListener{
        public String getwifiDetails();
//        public void showOptions(View view);
        public void showWifiOptions(String name, String capablity, Integer frequency, Integer level);
        public void sendWifiData();
     //   public StringBuffer getWifiList();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityCommander=(HomeFragmentListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        final Button wifiDetailBtn = (Button)view.findViewById(R.id.wifiDetailBtn);
        wifiDetailText = (TextView) view.findViewById(R.id.wifiDetailText);
        wifiListed = (TextView) view.findViewById(R.id.wifiListed);
        wifiNo = (TextView) view.findViewById(R.id.wifiNo);
        wifiList = (ListView) view.findViewById(R.id.wifiList);
        // get details on connected network
        wifiDetailBtn.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        buttonClicked();
                    }
                }
        );
        // get wifi options
        wifiList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getContext(), "clickeeed "+position+" "+scanResults.get(0).SSID.toString(), Toast.LENGTH_SHORT).show();
////                        getOptions(view);
//                        Toast.makeText(getContext(), "clickrrd "+scanResults.get(position).SSID, Toast.LENGTH_SHORT).show();
//
//                        actvtyCommander.showWifiOptions(scanResults.get(position));
                        itemClicked(position);
                    }
                }
        );
        return view;

    }

    public void itemClicked(int position) {
//        Toast.makeText(getContext(),Names[position]+Capabilities[position]+frequency[position]+level[position], Toast.LENGTH_SHORT).show();
        activityCommander.showWifiOptions(Names[position],Capabilities[position],frequency[position],level[position]);
        activityCommander.sendWifiData();
    }

    public void buttonClicked(){
        wifiDetailText.setText(activityCommander.getwifiDetails());

    }
    public void showWifiDetails(String details){
        wifiListed.setText(details);

    }
    public void showWifiNO(String count){
        wifiNo.setText(count);

    }

    public void showWifiNames(String[] wifiNames){
        ListAdapter listAdapter = new CustomAdapter(getContext(),wifiNames);
        wifiList.setAdapter(listAdapter);

    }

    public void setData(String[] wifiNames,String[] wifiCapabilities, Integer[] wififrequency, Integer[] wifiLevel){
        Names=wifiNames;
        Capabilities= wifiCapabilities;
        frequency = wififrequency;
        level=wifiLevel;
    }



//    public void getOptions(View view){
//        actvtyCommander.showOptions(view);
//    }


}
