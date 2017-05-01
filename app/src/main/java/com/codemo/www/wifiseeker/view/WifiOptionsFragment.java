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
import com.codemo.www.wifiseeker.controller.DatabaseController;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;
import com.codemo.www.wifiseeker.controller.WifiOptionsController;
import com.codemo.www.wifiseeker.model.Locations;
import com.codemo.www.wifiseeker.model.WifiLocation;
import com.codemo.www.wifiseeker.model.WifiNetwork;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiOptionsFragment extends Fragment {



    ListView optionsList;
    Integer index;
    Integer positions;
    Integer positionsOpen;
    TextView Header;
    Boolean open;
    private String pasword;

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
                        if(position==1){
                            WifiOptionsController.showWifiDialog(index,open);
                        }else if(position==0){
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

                            final RatingBar ratingBar = (RatingBar) view2.findViewById(R.id.ratingBar);
                            alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseController dbc= MainActivity.dbControlller;
                                    float[] results = new float[1];
                                    String dbId= new String();
                                    String dbRating= new String();

                                    if(!open) {
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                        ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getName(index));
                                        for(String[] i : list){
                                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search closed networks ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                            Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
                                            if(results[0]<50.0){
                                                dbId=i[0];
                                                dbRating=i[5];
                                                break;
                                            }
                                        }
                                    }else{
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open network ....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                        dbc.getWifiList(WifiNetwork.getOpenName(index));
                                        ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getOpenName(index));
                                        for(String[] i : list){
                                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search open networks ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                            Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
                                            if(results[0]<50.0){
                                                dbId=i[0];
                                                dbRating=i[5];
                                                break;
                                            }
                                        }

                                    }
                                    if(results[0]<50.0){
//                                        Toast.makeText(getContext(),"available", Toast.LENGTH_SHORT).show();
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network is updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                        dbc.updateRating(dbId, Double.parseDouble(String.valueOf(ratingBar.getRating())));
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....finished updating ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                        Toast.makeText(getContext(), "Rated as "+ratingBar.getRating(), Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(getContext(),"Please share this location before rating", Toast.LENGTH_SHORT).show();
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....network not shared ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                    }

//                                    setPasword(password.getText().toString());
//                                    WifiConnectionController.connectWifiPoint(WifiNetwork.getName(index), WifiNetwork.getCapabilities(index),getPasword()) ;

                                }
                            });
                            Dialog dialog =alertBuilder.create();
                            dialog.show();

                        }
                        else if(position==4){
                            Toast.makeText(getContext(), "Latitute : "+ WifiLocation.getLat()+"Longitude : "+WifiLocation.getLng(), Toast.LENGTH_SHORT).show();
                            Locations location;
                            float[] results = new float[1];
                            results[0]=100;
                            String dbId= new String();
                            DatabaseController dbc= MainActivity.dbControlller;
                            if(!open) {
                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getName(index));
                                for(String[] i : list){
                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search closed network ....aaaaaaaaaaaaaaaaaaaaaa***************");
                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
                                    if(results[0]<50.0){
                                        dbId=i[0];
                                        break;
                                    }
                                }
                            }else{
                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open network ....aaaaaaaaaaaaaaaaaaaaaa***************");

                                ArrayList<String[]> list = dbc.getWifiList(WifiNetwork.getOpenName(index));
                                for(String[] i : list){
                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....search open networks....aaaaaaaaaaaaaaaaaaaaaa***************"+i[0]);
                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
                                    if(results[0]<50.0){
                                        dbId=i[0];
                                        break;
                                    }
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
//                            DatabaseController dbc= MainActivity.dbControlller;
                                dbc.addLocation(location);
                                Toast.makeText(getContext(),"Location saved successfully", Toast.LENGTH_SHORT).show();
                                dbc.databaseTOString();
                            }
//                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....start print....aaaaaaaaaaaaaaaaaaaaaa***************");
//                            Toast.makeText(getContext(), "Database : "+dbc.databaseTOString(), Toast.LENGTH_SHORT).show();
//                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....print....aaaaaaaaaaaaaaaaaaaaaa***************"+);
                        }
                        else if(position==5){ // report the network ///////
                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....reporting....aaaaaaaaaaaaaaaaaaaaaa***************");
                            ArrayList<String[]> list;
                            DatabaseController dbc= MainActivity.dbControlller;
                            float[] results = new float[1];
                            String dbId= new String();
                            String dbReport= new String();
                            float distance = 50.0f;
                            if(!open) {
                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside closed networks....aaaaaaaaaaaaaaaaaaaaaa***************");

                                list = dbc.getWifiList(WifiNetwork.getName(index));
                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.... wifi list lenght....aaaaaaaaaaaaaaaaaaaaaa***************"+list.size());
                                for(String[] i : list){
                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed current reports....aaaaaaaaaaaaaaaaaaaaaa***************"+i[6]);
                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
                                    if(results[0]<distance){
                                        dbId=i[0];
                                        distance=results[0];
                                        dbReport=i[6];

                                    }
                                }
                            }else{
                                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside open networks....aaaaaaaaaaaaaaaaaaaaaa***************");
//                                dbc.getWifiList(WifiNetwork.getOpenName(index));
                                list = dbc.getWifiList(WifiNetwork.getOpenName(index));
                                for(String[] i : list){
                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....open current reports....aaaaaaaaaaaaaaaaaaaaaa***************"+i[6]);
                                    Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
                                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....distance is....aaaaaaaaaaaaaaaaaaaaaa***************"+results[0]);
                                    if(results[0]<distance){
                                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....distance updated to....aaaaaaaaaaaaaaaaaaaaaa***************"+results[0]);
                                        distance=results[0];
                                        dbId=i[0];
                                        dbReport=i[6];

                                    }
                                }

                            }
                            if(distance<50.0 && list.size()!=0){
                                Toast.makeText(getContext(),"available", Toast.LENGTH_SHORT).show();
                                if(Integer.parseInt(dbReport)>4){
                                    dbc.deleteLocation(dbId);
                                    Toast.makeText(getContext(),"Location removed successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    dbc.updateReport(dbId,Integer.parseInt(dbReport));
                                    Toast.makeText(getContext(),"Location reported successfully", Toast.LENGTH_SHORT).show();
                                }


                            }else{
                                Toast.makeText(getContext(),"not available", Toast.LENGTH_SHORT).show();
                            }
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
    }
    public void setOpenPosition(Integer position){
        this.positionsOpen=position;
        this.open=true;
    }

}
