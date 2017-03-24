package com.codemo.www.wifiseeker;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiOptionsFragment extends Fragment {



    ListView optionsList;
//    private ScanResult scanResult;
    String Names;
    String Capabilities;
    Integer frequency;
    Integer level;
    TextView Header;
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
    WifiOptionsFragmentListener actvtyCommander;

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public  interface  WifiOptionsFragmentListener{
        public void showWifiDialog();
        public void connectWifiPoint(String name, String capabilities,String password);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            actvtyCommander =(WifiOptionsFragmentListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_wifi_options, container, false);
        Header=(TextView)view.findViewById(R.id.header);

        ListAdapter adapter=new CustomOptionAdapter(getContext(), itemname, imgid);
        optionsList=(ListView)view.findViewById(R.id.optionsList);
        optionsList.setAdapter(adapter);

        final String pasword;

        // chosing an option from wifi options
        optionsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getContext(), "clicked "+position, Toast.LENGTH_SHORT).show();
//                        getActivity().getSupportFragmentManager().beginTransaction().remove(getParentFragment()).commit();
                        Toast.makeText(getContext(), "wifi name"+Names, Toast.LENGTH_SHORT).show();
                        if(position==1){
                            actvtyCommander.showWifiDialog();
                        }if(position==0){
                            if(Capabilities.toUpperCase().contains("WEP") || Capabilities.toUpperCase().contains("WPA")){
                                Toast.makeText(getContext(), "protected"+Names, Toast.LENGTH_SHORT).show();
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
                                        actvtyCommander.connectWifiPoint(Names,Capabilities,getPasword()) ;
                                    }
                                });
                                Dialog dialog =alertBuilder.create();
                                dialog.show();
                            }
                            else{
                                actvtyCommander.connectWifiPoint(Names,Capabilities,"");
                                Toast.makeText(getContext(), "open "+Names, Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                }
        );
        return  view;
    }

    public void setDetails(String wifiNames,String wifiCapabilities, Integer wifiFrequency, Integer wifiLevel){
        this.Names=wifiNames;
        this.Capabilities= wifiCapabilities;
        this.frequency = wifiFrequency;
        this.level=wifiLevel;
    }
}
