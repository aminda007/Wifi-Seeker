package com.codemo.www.wifiseeker.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.OnlineDatabaseController;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;
import com.codemo.www.wifiseeker.controller.WifiOptionsController;
import com.codemo.www.wifiseeker.model.Locations;
import com.codemo.www.wifiseeker.model.WifiLocation;
import com.codemo.www.wifiseeker.model.WifiNetwork;

import java.util.ArrayList;

import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiOptionsFragment extends Fragment {



    ListView optionsList;
    Integer index;
    private static MainActivity activity;
    String connectionName;
    String connectionIpAddress;
    String connectionLinkSpeed;
    Integer positions;
    Integer positionsOpen;
    RatingBar ratingBar;
    TextView Header;
    private boolean exist;
    Boolean open;
    private String pasword;
    private ArrayList<String[]> wifiNameList;
    String[] itemname ={
            "Connect",
            "Details",
//            "Navigate",
            "Rate",
            "Share",
            "Report"

    };

    Integer[] imgid={
            R.drawable.ic_connect,
            R.drawable.ic_details,
//            R.drawable.ic_navigate,
            R.drawable.ic_rate,
            R.drawable.ic_share,
            R.drawable.ic_report

    };


    public WifiOptionsFragment() {

    }

    public static void setActivity(MainActivity activity) {
        WifiOptionsFragment.activity = activity;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_wifi_options, container, false);
        Header=(TextView)view.findViewById(R.id.header);

        ListAdapter adapter=new WifiListAdapter(getContext(), itemname, imgid);
        optionsList=(ListView)view.findViewById(R.id.optionsList);
        optionsList.setAdapter(adapter);

        final String pasword;

        // chosing an option from wifi options
        optionsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(open){
                            index= positionsOpen;
                        }else{
                            index= positions;
                        }
                        if(position==1){ // show details  //////////////////////////////////////////////////
                            Log.v("rht","aaaaaaaaaaa  inside Wifioptions fragment aaaaaaaaaaaaaaaa"+index+"swdd"+open );

                            WifiOptionsController.showWifiDialog(index,open);


                        }else if(position==0){  // connect to the network ////////////////////////////////////////////
                            if(!open){
                                View view1=LayoutInflater.from(getActivity()).inflate(R.layout.wifi_enter_password,null);
                                AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(getActivity());
                                alertBuilder.setView(view1);
                                alertBuilder.setIcon(R.drawable.ic_wifi_locked);
                                alertBuilder.setTitle("Enter Password");

                                final EditText password = (EditText)view1.findViewById(R.id.password);
                                alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        setPasword(password.getText().toString());
                                        WifiConnectionController.connectWifiPoint(WifiNetwork.getName(index), WifiNetwork.getCapabilities(index),getPasword()) ;
                                        Toast.makeText(getContext(), "Connecting to "+WifiNetwork.getName(index), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Dialog dialog =alertBuilder.create();
                                dialog.show();
                            }
                            else{
                                WifiConnectionController.connectWifiPoint(WifiNetwork.getOpenName(index), WifiNetwork.getOpenCapabilities(index),"") ;
                                Toast.makeText(getContext(), "Connecting to "+WifiNetwork.getOpenName(index), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(position==2){  ///  rating the network  ////////////////////////////////////////
                            if(activity.isGpsAvailable() && activity.isNetworkAvailable()){

                                View view2=LayoutInflater.from(getActivity()).inflate(R.layout.wifi_rate,null);
                                AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(getActivity());
                                alertBuilder.setView(view2);
                                alertBuilder.setIcon(R.drawable.ic_rate);
                                alertBuilder.setTitle("Give a rating!");

                                ratingBar = (RatingBar) view2.findViewById(R.id.ratingBar);
                                alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        OnlineDatabaseController network=new OnlineDatabaseController("updateRating");
                                        if(!open) {
                                            network.execute(WifiNetwork.getName(index));
                                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                        }else{
                                            network.execute(WifiNetwork.getOpenName(index));
                                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open network ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                        }
                                    }
                                });
                                Dialog dialog =alertBuilder.create();
                                dialog.show();
                            }else{
                                Toast.makeText(getContext(),"Connect to internet and enable location services before rating", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if(position==3){  // share network ///////////////////////////////////////
                           if(open){
                               if(WifiNetwork.getOpenLevel(index)>-65){
                                   initShare();
                               }else{
                                   Toast.makeText(getContext(),"Get good signal strength to share", Toast.LENGTH_SHORT).show();
                               }
                           }
                            else{
                               if(WifiNetwork.getLevel(index)>-65){
                                   initShare();
                               }else{
                                   Toast.makeText(getContext(),"Get good signal strength to share", Toast.LENGTH_SHORT).show();
                               }
                           }
                        }
                        else if(position==4){ // report the network ///////
                            if(activity.isGpsAvailable() && activity.isNetworkAvailable()){
                                connectionName =String.valueOf(wifiManager.getConnectionInfo().getSSID());
                                connectionIpAddress =String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....reporting....aaaaaaaaaaaaaaaaaaaaaa***************");
                                OnlineDatabaseController network=new OnlineDatabaseController("updateReport");
                                if(!open) {
                                    network.execute(WifiNetwork.getName(index));
                                }else{
                                    network.execute(WifiNetwork.getOpenName(index));
                                }
                            }else{
                                Toast.makeText(getContext(),"Connect to internet and enable location services before reporting", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
        return  view;
    }

    public void setPosition(Integer position){
        this.positions=position;
        this.open=false;
        Header.setText(WifiNetwork.getName(position));
    }
    public void setOpenPosition(Integer position){
        this.positionsOpen=position;
        this.open=true;
        Header.setText(WifiNetwork.getOpenName(position));
    }

    public ArrayList<String[]> getWifiNameList() {
        return wifiNameList;
    }

    public void setWifiNameList(ArrayList<String[]> wifiNameList) {
        this.wifiNameList = wifiNameList;
    }
    public void share(){
        Locations location;

        if(!isExist()){
            if(!open) {
                location = new Locations(WifiNetwork.getName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
            }else{
                location = new Locations(WifiNetwork.getOpenName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
            }
            OnlineDatabaseController add=new OnlineDatabaseController("share");
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....rsfdgh..aaaaaaaaaaaaaaaaaaaaaa***************"+location.isOpen());
            add.execute("null",location.getName(),
                    Double.toString(location.getLat()),
                    Double.toString(location.getLng()),
                    Boolean.toString(location.isOpen()),
                    Double.toString(location.getRating()),
                    Double.toString(location.getReport()),
                    connectionIpAddress.substring(1),
                    connectionLinkSpeed
            );
            Toast.makeText(getContext(),"Location saved successfully", Toast.LENGTH_SHORT).show();
        }else{
//////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            float[] results = new float[1];
            results[0]=100;
            String dbId= new String();
            ArrayList<String[]> list = getWifiNameList();
            for(String[] i : list){
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search open networks....aaaaaaaaaaaaaaaaaaaaaa***************"+i[0]);
                Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[1]),Double.parseDouble(i[2]),results);
                if(results[0]<50.0){
                    dbId=i[0];
                    break;
                }
            }
            if(results[0]<50.0){
                Toast.makeText(getContext(),"Location is already available", Toast.LENGTH_SHORT).show();
            }else{
                if(!open) {
                    location = new Locations(WifiNetwork.getName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
                }else{
                    location = new Locations(WifiNetwork.getOpenName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
                }
                OnlineDatabaseController add=new OnlineDatabaseController("share");
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....rsfdgh..aaaaaaaaaaaaaaaaaaaaaa***************"+location.isOpen());
                add.execute("null",location.getName(),
                        Double.toString(location.getLat()),
                        Double.toString(location.getLng()),
                        Boolean.toString(location.isOpen()),
                        Double.toString(location.getRating()),
                        Double.toString(location.getReport()),
                        connectionIpAddress.substring(1),
                        connectionLinkSpeed
                );
                Toast.makeText(getContext(),"Location saved successfully", Toast.LENGTH_SHORT).show();
            }
            ///////////////////////////////////////////////
        }
    }
    public void rate(){
        if(exist){
            float[] results = new float[1];
            String dbId= new String();
            String dbRating= new String();
            ArrayList<String[]> list = getWifiNameList();
            for(String[] i : list){
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search networks ....aaaaaaaaaaaaaaaaaaaaaa***************");
                Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[1]),Double.parseDouble(i[2]),results);
                if(results[0]<50.0){
                    dbId=i[0];
                    dbRating=i[3];
                    break;
                }
            }
            if(results[0]<50.0){
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network is updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
                OnlineDatabaseController network=new OnlineDatabaseController("saveRating");
                if(Double.parseDouble(dbRating)==0 || Double.parseDouble(dbRating)==0.0){
                    network.execute(dbId,String.valueOf(ratingBar.getRating()));
                }else{
                    dbRating=String.valueOf((Double.parseDouble(String.valueOf(ratingBar.getRating()))+Double.parseDouble(dbRating))/2.0);
                    network.execute(dbId,dbRating);
                }
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....finished updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
                Toast.makeText(getContext(), "Rated as "+ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"Please share this location before rating", Toast.LENGTH_SHORT).show();
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network not shared ....aaaaaaaaaaaaaaaaaaaaaa***************");
            }
        }else{
            Toast.makeText(getContext(),"Please share this location before rating", Toast.LENGTH_SHORT).show();
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network not shared ....aaaaaaaaaaaaaaaaaaaaaa***************");
        }
    }
    public void report(){
        if(exist){
            ArrayList<String[]> list;
            float[] results = new float[1];
            String dbId= new String();
            String dbReport= new String();
            float distance = 50.0f;
            list = getWifiNameList();
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.... wifi list lenght....aaaaaaaaaaaaaaaaaaaaaa***************"+list.size());
            for(String[] i : list){
                Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[1]),Double.parseDouble(i[2]),results);
                if(results[0]<distance){
                    dbId=i[0];
                    distance=results[0];
                    dbReport=i[3];
                }
            }
            if(distance<50.0 && list.size()!=0){
                OnlineDatabaseController network=new OnlineDatabaseController("saveReport");
                if(Integer.parseInt(dbReport)==4){
                    network.execute(dbId,"5");
                    Toast.makeText(getContext(),"Location removed successfully", Toast.LENGTH_SHORT).show();
                }else{
                    network.execute(dbId, String.valueOf((Integer.parseInt(dbReport)+1)));
                    Toast.makeText(getContext(),"Location reported successfully", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(),"Location is already removed or not shared", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(),"Location is already removed or not shared", Toast.LENGTH_SHORT).show();
        }
    }
    public void initShare(){
        if(activity.isGpsAvailable() && activity.isNetworkAvailable()){
            connectionName =String.valueOf(wifiManager.getConnectionInfo().getSSID());
            connectionIpAddress =String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
            connectionLinkSpeed =String.valueOf(wifiManager.getConnectionInfo().getLinkSpeed());
            OnlineDatabaseController network=new OnlineDatabaseController("getNameList");
            if(!open) {
                if(true){
                    network.execute(WifiNetwork.getName(index));
                }else {
                    Toast.makeText(getContext(),"Connect to "+WifiNetwork.getName(index)+ " before sharing", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(true){
//                if(connectionName.contains(WifiNetwork.getOpenName(index))){      //   use this when need to connect before sharing
                    network.execute(WifiNetwork.getOpenName(index));
                }else {
                    Toast.makeText(getContext(),"Connect to "+WifiNetwork.getOpenName(index)+ " before sharing", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getContext(),"Connect to internet and enable location services before sharing", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
