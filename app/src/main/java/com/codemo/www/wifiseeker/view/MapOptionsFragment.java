package com.codemo.www.wifiseeker.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.DatabaseController;
import com.codemo.www.wifiseeker.controller.OnlineDatabaseController;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;
import com.codemo.www.wifiseeker.controller.WifiOptionsController;
import com.codemo.www.wifiseeker.model.Locations;
import com.codemo.www.wifiseeker.model.WifiLocation;
import com.codemo.www.wifiseeker.model.WifiNetwork;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapOptionsFragment extends Fragment {

    private static MainActivity Activity;

    ListView optionsList;
    String frequency;
    Integer index;
    private String lat;
    private String lng;
    private String id;
    private String name;
    private String rating;
    private String report;
    private String open;
//    Integer id;
    TextView Header;
    TextView wifiName;
    RatingBar ratingBar;
    String[] details = new String[3];
    String[] itemname ={
            "Details",
            "Navigate",

    };

    Integer[] imgid={
            R.drawable.ic_details,
            R.drawable.ic_navigate,
    };


    public MapOptionsFragment() {
    }

    public static void setActivity(MainActivity activity) {
        Activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map_options, container, false);
        Header=(TextView)view.findViewById(R.id.header);
        wifiName = (TextView) view.findViewById(R.id.nameWifi);
        ListAdapter adapter=new WifiListAdapter(getContext(), itemname, imgid);
        optionsList=(ListView)view.findViewById(R.id.optionsList);
        optionsList.setAdapter(adapter);

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBarMap);
//        DatabaseController dbc= MainActivity.dbController;
//        ratingBar.setRating(dbc.getRating(id.toString()));

        final String pasword;

        // chosing an option from wifi options
        optionsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(position==0){
                            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.... show details ....aaaaaaaaa");
                            AlertDialog.Builder builder = new AlertDialog.Builder(manager.findFragmentByTag("MapOptionsFragment").getActivity());
                            View mview =  LayoutInflater.from(manager.findFragmentByTag("MapOptionsFragment").getActivity()).inflate(R.layout.wifi_option_item,null);
                            ListAdapter listAdapter;
                            String[] item = {
                                    "Name                      :  "+getName(),
                                    "Security Type         :  "+getOpen(),
                                    "Rating                     :  "+getRating(),
                                    "Report                     :  "+getReport()};
                            listAdapter = new ArrayAdapter<String>(manager.findFragmentByTag("MapOptionsFragment").getActivity(),android.R.layout.simple_list_item_1, item);
                            ListView detailList = (ListView)mview.findViewById(R.id.detailList);
                            detailList.setAdapter(listAdapter);
                            builder.setView(mview);
                            AlertDialog mdiaDialog = builder.create();
                            mdiaDialog.show();

                        }else{
                            if(Activity.isGpsAvailable() && Activity.isNetworkAvailable()){
                                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+","+lng);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }else{
                                Toast.makeText(getContext(),"enable location services and connect to internet", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                }
        );
        return  view;
    }

//    public void setId(Integer idd){
//        id=idd;
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....inside setIdddd....aaaaaaaaaaaaaaaaaaaaaa***************"+ idd);
////        DatabaseController dbc= MainActivity.dbController;
//        OnlineDatabaseController network=new OnlineDatabaseController("getNameList");
////        ratingBar.setRating(dbc.getRating(id.toString()));
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....set rating to ....aaaaaaaaaaaaaaaaaaaaaa*************** ");
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....last of  setId....aaaaaaaaaaaaaaaaaaaaaa***************");
////        this.open=false;
//    }
    public void setRating(String rating){
        Float dbRating = Float.parseFloat(rating);

        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....before rating....aaaaaaaaaaaaaaaaaaaaaa***************"+dbRating);

        if(dbRating>=5){
            dbRating= 5.0f;
        }else if(dbRating>=4){
            dbRating= 4.0f;
        }else if(dbRating>=3){
            dbRating= 3.0f;
        }else if(dbRating>=2){
            dbRating= 2.0f;
        }else if(dbRating>=1){
            dbRating= 1.0f;
        }else{
            dbRating= Float.valueOf(0f);
        }
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....  after asigning rating....aaaaaaaaaaaaaaaaaaaaaa***************"+dbRating);
        this.rating= rating;
    ratingBar.setRating(dbRating);
    }

//    public void setDetails(String[] details){
////      DatabaseController dbc= MainActivity.dbController;
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa... in side get details....aaaaaaaaaaaaaaaaaaaaaa***************");
////        id=details[0];
//
//        setLat(details[2]);
//        setLng(details[3]);
//        setRating(details[5]);
//    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        wifiName.setText(name);
    }

    public String getRating() {
        return rating;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setId(String id) {
        this.id = id;
    }
}
