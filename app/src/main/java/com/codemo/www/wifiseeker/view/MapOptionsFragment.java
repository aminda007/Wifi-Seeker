package com.codemo.www.wifiseeker.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.codemo.www.wifiseeker.controller.OnlineDatabaseController;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;
import com.codemo.www.wifiseeker.controller.WifiOptionsController;
import com.codemo.www.wifiseeker.model.Locations;
import com.codemo.www.wifiseeker.model.WifiLocation;
import com.codemo.www.wifiseeker.model.WifiNetwork;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapOptionsFragment extends Fragment {



    ListView optionsList;
    String frequency;
    Integer index;
    Integer id;
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
//        DatabaseController dbc= MainActivity.dbControlller;
//        ratingBar.setRating(dbc.getRating(id.toString()));

        final String pasword;

        // chosing an option from wifi options
        optionsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(position==0){

                        }else{

                        }
                    }
                }
        );
        return  view;
    }

//    public void setId(Integer idd){
//        id=idd;
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....inside setIdddd....aaaaaaaaaaaaaaaaaaaaaa***************"+ idd);
////        DatabaseController dbc= MainActivity.dbControlller;
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

    ratingBar.setRating(dbRating);
    }

    public void setDetails(String[] details){
//      DatabaseController dbc= MainActivity.dbControlller;
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa... in side get details....aaaaaaaaaaaaaaaaaaaaaa***************");
//        id=details[0];
        wifiName.setText(details[1]);
        setRating(details[5]);
    }

}
