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
    String connectionName;
    String connectionIpAddress;
    String connectionLinkSpeed;
    Integer positions;
    Integer positionsOpen;
    RatingBar ratingBar;
    TextView Header;
    Boolean open;
    private String pasword;
    private ArrayList<String[]> wifiNameList;
    String[] itemname ={
            "Connect",
            "Details",
            "Navigate",
            "Rate",
            "Share",
            "Report"

    };

    Integer[] imgid={
            R.drawable.ic_connect,
            R.drawable.ic_details,
            R.drawable.ic_navigate,
            R.drawable.ic_rate,
            R.drawable.ic_share,
            R.drawable.ic_report

    };


    public WifiOptionsFragment() {

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
                        if(position==1){ // show details  //////
                            Log.v("rht","aaaaaaaaaaa  inside Wifioptions fragment aaaaaaaaaaaaaaaa"+index+"swdd"+open );

                            WifiOptionsController.showWifiDialog(index,open);


                        }else if(position==0){  // connect to the network ////
                            if(!open){
//                                Toast.makeText(getContext(), "protected"+WifiNetwork.getName(positions), Toast.LENGTH_SHORT).show();
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
                        else if(position==3){  ///  rating the network  /////
                            View view2=LayoutInflater.from(getActivity()).inflate(R.layout.wifi_rate,null);
                            AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(getActivity());
                            alertBuilder.setView(view2);
                            alertBuilder.setIcon(R.drawable.ic_rate);
                            alertBuilder.setTitle("Give a rating!");

                            ratingBar = (RatingBar) view2.findViewById(R.id.ratingBar);
                            alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    DatabaseController dbc= MainActivity.dbControlller;
                                    OnlineDatabaseController network=new OnlineDatabaseController("updateRating");

//                                    float[] results = new float[1];
//                                    String dbId= new String();
//                                    String dbRating= new String();

                                    if(!open) {
                                        network.execute(WifiNetwork.getName(index));
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                        ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getName(index));
//                                        for(String[] i : list){
//                                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search closed networks ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                            Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
//                                            if(results[0]<50.0){
//                                                dbId=i[0];
//                                                dbRating=i[5];
//                                                break;
//                                            }
//                                        }
                                    }else{
                                        network.execute(WifiNetwork.getOpenName(index));
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open network ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                        dbc.getWifiList(WifiNetwork.getOpenName(index));
//                                        ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getOpenName(index));

//                                        for(String[] i : list){
//                                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search open networks ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                            Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
//                                            if(results[0]<50.0){
//                                                dbId=i[0];
//                                                dbRating=i[5];
//                                                break;
//                                            }
//                                        }

                                    }
//                                    if(results[0]<50.0){
////                                        Toast.makeText(getContext(),"available", Toast.LENGTH_SHORT).show();
//                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network is updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                        dbc.updateRating(dbId, Double.parseDouble(String.valueOf(ratingBar.getRating())));
//                                        OnlineDatabaseController network=new OnlineDatabaseController("getRating");
//
//                                        network.execute(dbId);
//                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....finished updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                        Toast.makeText(getContext(), "Rated as "+ratingBar.getRating(), Toast.LENGTH_SHORT).show();
//
//                                    }else{
//                                        Toast.makeText(getContext(),"Please share this location before rating", Toast.LENGTH_SHORT).show();
//                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network not shared ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                    }

//                                    setPasword(password.getText().toString());
//                                    WifiConnectionController.connectWifiPoint(WifiNetwork.getName(index), WifiNetwork.getCapabilities(index),getPasword()) ;

                                }
                            });
                            Dialog dialog =alertBuilder.create();
                            dialog.show();

                        }
                        else if(position==4){  // share network ///////
                            connectionName =String.valueOf(wifiManager.getConnectionInfo().getSSID());
                            connectionIpAddress =String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
                            connectionLinkSpeed =String.valueOf(wifiManager.getConnectionInfo().getLinkSpeed());
//                            Toast.makeText(getContext(), "edvsfbdbfsrb s"+connectionName+ connectionIpAddress +"faefgegbeadvae"+ connectionLinkSpeed, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(), "Latitute : "+ WifiLocation.getLat()+"Longitude : "+WifiLocation.getLng(), Toast.LENGTH_SHORT).show();

                            // Locations location;
//                            float[] results = new float[1];
//                            results[0]=100;
//                            String dbId= new String();
                           // DatabaseController dbc= MainActivity.dbControlller;
                            OnlineDatabaseController network=new OnlineDatabaseController("getNameList");

                            if(!open) {
                                if(connectionName.contains(WifiNetwork.getName(index))){
                                    network.execute(WifiNetwork.getName(index));
                                }else {
                                    Toast.makeText(getContext(),"Connect to "+connectionName+ " before sharing", Toast.LENGTH_SHORT).show();
                                }
//                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
////                                ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getName(index));
//                                ArrayList<String[]> list = getWifiNameList();
//                                for(String[] i : list){
//                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
//                                    if(results[0]<50.0){
//                                        dbId=i[0];
//                                        break;
//                                    }
//                                }
                            }else{
                                if(connectionName.contains(WifiNetwork.getOpenName(index))){
                                    network.execute(WifiNetwork.getOpenName(index));
                                }else {
                                    Toast.makeText(getContext(),"Connect to "+connectionName+ " before sharing", Toast.LENGTH_SHORT).show();
                                }

//                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open network ....aaaaaaaaaaaaaaaaaaaaaa***************");
//
////                                ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getOpenName(index));
//                                ArrayList<String[]> list = getWifiNameList();
//                                for(String[] i : list){
//                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search open networks....aaaaaaaaaaaaaaaaaaaaaa***************"+i[0]);
//                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
//                                    if(results[0]<50.0){
//                                        dbId=i[0];
//                                        break;
//                                    }
//                                }

                            }
//                            if(results[0]<50.0){
//                                Toast.makeText(getContext(),"Location is already available", Toast.LENGTH_SHORT).show();
//
//                            }else{
//
//                                if(!open) {
//                                    location = new Locations(WifiNetwork.getName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
//                                }else{
//                                    location = new Locations(WifiNetwork.getOpenName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
//                                }
////                            DatabaseController dbc= MainActivity.dbControlller;
////                                dbc.addLocation(location);
//                                OnlineDatabaseController add=new OnlineDatabaseController("share");
////                                network.main2Activity=this;
//                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....rsfdgh..aaaaaaaaaaaaaaaaaaaaaa***************"+location.isOpen());
//                                add.execute("null",location.getName(),
//                                        Double.toString(location.getLat()),
//                                        Double.toString(location.getLng()),
//                                        Boolean.toString(location.isOpen()),
//                                        Double.toString(location.getRating()),
//                                        Double.toString(location.getReport()));
////                                OnlineDatabaseController getList=new OnlineDatabaseController("getList");
////                                getList.main2Activity=this;
////                                getList.execute("1");
//                                Toast.makeText(getContext(),"Location saved successfully", Toast.LENGTH_SHORT).show();
////                                dbc.databaseTOString();
//                            }
////                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....start print....aaaaaaaaaaaaaaaaaaaaaa***************");
////                            Toast.makeText(getContext(), "Database : "+dbc.databaseTOString(), Toast.LENGTH_SHORT).show();
//                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....print....aaaaaaaaaaaaaaaaaaaaaa***************"+);
                        }
                        else if(position==5){ // report the network ///////
                            connectionName =String.valueOf(wifiManager.getConnectionInfo().getSSID());
                            connectionIpAddress =String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
//                            connectionLinkSpeed =String.valueOf(wifiManager.getConnectionInfo().getLinkSpeed());
                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....reporting....aaaaaaaaaaaaaaaaaaaaaa***************");
//                            ArrayList<String[]> list;
//                            DatabaseController dbc= MainActivity.dbControlller;
//                            float[] results = new float[1];
//                            String dbId= new String();
//                            String dbReport= new String();
//                            float distance = 50.0f;
                            OnlineDatabaseController network=new OnlineDatabaseController("updateReport");
                            if(!open) {
                                network.execute(WifiNetwork.getName(index));
//                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside closed networks....aaaaaaaaaaaaaaaaaaaaaa***************");
//
//                                list = dbc.getWifiList(WifiNetwork.getName(index));
//                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.... wifi list lenght....aaaaaaaaaaaaaaaaaaaaaa***************"+list.size());
//                                for(String[] i : list){
//                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed current reports....aaaaaaaaaaaaaaaaaaaaaa***************"+i[6]);
//                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
//                                    if(results[0]<distance){
//                                        dbId=i[0];
//                                        distance=results[0];
//                                        dbReport=i[6];
//
//                                    }
//                                }
                            }else{
                                network.execute(WifiNetwork.getOpenName(index));
//                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside open networks....aaaaaaaaaaaaaaaaaaaaaa***************");
////                                dbc.getWifiList(WifiNetwork.getOpenName(index));
//                                list = dbc.getWifiList(WifiNetwork.getOpenName(index));
//                                for(String[] i : list){
//                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open current reports....aaaaaaaaaaaaaaaaaaaaaa***************"+i[6]);
//                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
//                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....distance is....aaaaaaaaaaaaaaaaaaaaaa***************"+results[0]);
//                                    if(results[0]<distance){
//                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....distance updated to....aaaaaaaaaaaaaaaaaaaaaa***************"+results[0]);
//                                        distance=results[0];
//                                        dbId=i[0];
//                                        dbReport=i[6];
//
//                                    }
                                }
//
//                            }
//                            if(distance<50.0 && list.size()!=0){
//                                Toast.makeText(getContext(),"available", Toast.LENGTH_SHORT).show();
//                                if(Integer.parseInt(dbReport)>4){
//                                    dbc.deleteLocation(dbId);
//                                    Toast.makeText(getContext(),"Location removed successfully", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    dbc.updateReport(dbId,Integer.parseInt(dbReport));
//                                    Toast.makeText(getContext(),"Location reported successfully", Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            }else{
//                                Toast.makeText(getContext(),"not available", Toast.LENGTH_SHORT).show();
//                            }
//                            dbc.updateReport(String id);

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

//        }
        if(results[0]<50.0){
            Toast.makeText(getContext(),"Location is already available", Toast.LENGTH_SHORT).show();

        }else{

            if(!open) {
                location = new Locations(WifiNetwork.getName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
            }else{
                location = new Locations(WifiNetwork.getOpenName(index),WifiLocation.getLat(), WifiLocation.getLng(), open,0.0,0);
            }
//                            DatabaseController dbc= MainActivity.dbControlller;
//                                dbc.addLocation(location);
            OnlineDatabaseController add=new OnlineDatabaseController("share");
//                                network.main2Activity=this;
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
//                                OnlineDatabaseController getList=new OnlineDatabaseController("getList");
//                                getList.main2Activity=this;
//                                getList.execute("1");
            Toast.makeText(getContext(),"Location saved successfully", Toast.LENGTH_SHORT).show();
//                                dbc.databaseTOString();
        }
    }
    public void rate(){
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

//        }
        if(results[0]<50.0){
//                                        Toast.makeText(getContext(),"available", Toast.LENGTH_SHORT).show();
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network is updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
//            dbc.updateRating(dbId, Double.parseDouble(String.valueOf(ratingBar.getRating())));
            OnlineDatabaseController network=new OnlineDatabaseController("saveRating");
            if(Integer.parseInt(dbRating)==0){
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
    }
    public void report(){
        ArrayList<String[]> list;
//        DatabaseController dbc= MainActivity.dbControlller;
        float[] results = new float[1];
        String dbId= new String();
        String dbReport= new String();
        float distance = 50.0f;
//        if(!open) {
//            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside closed networks....aaaaaaaaaaaaaaaaaaaaaa***************");

        list = getWifiNameList();
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.... wifi list lenght....aaaaaaaaaaaaaaaaaaaaaa***************"+list.size());
        for(String[] i : list){
//            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed current reports....aaaaaaaaaaaaaaaaaaaaaa***************"+i[6]);
            Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[1]),Double.parseDouble(i[2]),results);
            if(results[0]<distance){
                dbId=i[0];
                distance=results[0];
                dbReport=i[3];

            }
        }
        if(distance<50.0 && list.size()!=0){
            Toast.makeText(getContext(),"available", Toast.LENGTH_SHORT).show();
            OnlineDatabaseController network=new OnlineDatabaseController("saveReport");
            if(Integer.parseInt(dbReport)==4){
                network.execute(dbId,"5");
//                dbc.deleteLocation(dbId);
                Toast.makeText(getContext(),"Location removed successfully", Toast.LENGTH_SHORT).show();
            }else{
//                dbc.updateReport(dbId,Integer.parseInt(dbReport));
                network.execute(dbId, String.valueOf((Integer.parseInt(dbReport)+1)));
                Toast.makeText(getContext(),"Location reported successfully", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(getContext(),"Location is already removed or not shared", Toast.LENGTH_SHORT).show();
        }
    }

}
