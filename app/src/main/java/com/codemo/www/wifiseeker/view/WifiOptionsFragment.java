package com.codemo.www.wifiseeker.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;
import com.codemo.www.wifiseeker.controller.WifiOptionsController;
import com.codemo.www.wifiseeker.model.WifiNetwork;


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
                        }if(position==0){
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
