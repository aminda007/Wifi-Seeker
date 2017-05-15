package com.codemo.www.wifiseeker.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.codemo.www.wifiseeker.controller.WifiConnectionController;
import com.codemo.www.wifiseeker.view.MainActivity;

import static com.codemo.www.wifiseeker.view.MainActivity.wifiManager;

/**
 * Created by root on 3/24/17.
 */

public class WifiNetwork {
    static String[] Names;
    static String[] Capabilities;
    static Integer[] frequency;
    static Integer[] level;
    static String[] openNames;
    static String[] openCapabilities;
    static Integer[] openFrequency;
    static Integer[] openLevel;
    static String openMax;
    static String closedMax;

    static {
        closedMax = "";
    }

    private static String password;
    private static MainActivity activity;
    public static void setData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
        Names=wifiNames;
        Capabilities= wifiCapabilities;
        frequency = wifiFrequency;
        level=wifiLevel;
    }
    public static void setOpenData(String[] wifiNames,String[] wifiCapabilities, Integer[] wifiFrequency, Integer[] wifiLevel){
        openNames=wifiNames;
        openCapabilities= wifiCapabilities;
        openFrequency = wifiFrequency;
        openLevel=wifiLevel;
    }
    public static String[] getData(Integer position){
        String[] wifiInfomation = {
                "Name                      :  "+Names[position],
                "Security Type         :  "+getAuth(Capabilities[position]),
                "Frequency(MHz)    :  "+frequency[position],
                "Signal Strength      :  "+getSignal(level[position])};


        return wifiInfomation;
    }
    public static String[] getOpenData(Integer position){
        Log.v("rht","aaaaaaaaaaa  inside WifiNetwork getopendata aaaaaaaaaaaaaaaa"+position+"swdd");
        Log.v("rht","aaaaaaaaaaa  inside WifiNetwork getopendata aaaaaaaaaaaaaaaa"+openNames[position]+"swdd");
        String[] wifiInfomation = {
                "Name                      :  "+openNames[position],
                "Security Type         :  "+"None",
                "Frequency(MHz)    :  "+openFrequency[position],
                "Signal Strength      :  "+getSignal(openLevel[position])};
        Log.v("rht","aaaaaaaaaaa  inside WifiNetwork getopendata aaaaaaaaaaaaaaaa"+openNames[position]+"swdd");

        return wifiInfomation;
    }

    public static String getAuth(String auth){
        if(auth.toUpperCase().contains("WEP")){
            auth="Secure";
        }else if(auth.toUpperCase().contains("WPA")) {
            auth = "Secure";
        }
        return auth;
    }
    public static String getSignal(Integer level){
        String signal;
        if(level>=-55){
            signal="Excellent";
        }else if(level>=-65){
            signal="Good";
        }else if(level>=-75){
            signal="Fair";
        }else{
            signal="Weak";
        }
        return signal;
    }

    public static void autoConnection(){
        if(openLevel.length!=0){
            Log.v("rht","aaaaaa....................... inside open auto connect");
            int max=openLevel[0];
            int maxId=0;
            for(int i=1; i <openLevel.length; i++){
                if(openLevel[i]>max){
                    max=openLevel[i];
                    maxId=i;
                }
            }
            Log.v("rht","aaaaaa....................... max id is "+maxId);
            if(wifiManager.getConnectionInfo().getSSID().contains(getOpenName(maxId))){

            }else{
                WifiConnectionController.connectWifiPoint(getOpenName(maxId), getOpenCapabilities(maxId),"") ;
                Toast.makeText(activity, "Connecting to "+getOpenName(maxId), Toast.LENGTH_SHORT).show();
            }

        }
        else if(level.length!=0){
            Log.v("rht","aaaaaa....................... inside closed auto connect");
            int maxx=level[0];
            int maxxId=0;
            for(int i=1; i <level.length; i++){
                if(level[i]>maxx){
                    maxx=level[i];
                    maxxId=i;

                }
            }
            if(wifiManager.getConnectionInfo().getSSID().contains(getName(maxxId)) ){

                Log.v("rht","aaaaaa....................... max closed network..............."+getName(maxxId));
            }else{
                if(!closedMax.contains(getName(maxxId))){
                    closedMax=getName(maxxId);
                    Log.v("rht","aaaaaa....................... maxxx secure is "+getName(maxxId)+closedMax+closedMax.contains(getName(maxxId)));
                    View view1= LayoutInflater.from(activity).inflate(R.layout.wifi_enter_password,null);
                    AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(activity);
                    alertBuilder.setView(view1);
                    alertBuilder.setIcon(R.drawable.ic_wifi_locked);
                    alertBuilder.setTitle("Enter Password");

                    final EditText password = (EditText)view1.findViewById(R.id.password);
                    final int finalMaxxId = maxxId;
                    alertBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            setPassword(password.getText().toString());
                            WifiConnectionController.connectWifiPoint(getName(finalMaxxId), getCapabilities(finalMaxxId),getPassword()) ;
                            Toast.makeText(activity, "Connecting to "+getName(finalMaxxId), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Dialog dialog =alertBuilder.create();
                    dialog.show();
                }

            }

        }else{
            Toast.makeText(activity, "No networks available", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getName(Integer position){
        return Names[position];
    }
    public static String getOpenName(Integer position){
        return openNames[position];
    }
    public static String getCapabilities(Integer position){
        return Capabilities[position];
    }
    public static String getOpenCapabilities(Integer position){
        return openCapabilities[position];
    }
    public static Integer getOpenLevel(Integer position){
        return openLevel[position];
    }
    public static Integer getLevel(Integer position){
        return level[position];
    }

    public static void setActivity(MainActivity activity) {
        WifiNetwork.activity = activity;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        WifiNetwork.password = password;
    }
}
