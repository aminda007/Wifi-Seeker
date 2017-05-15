package com.codemo.www.wifiseeker.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;


import com.codemo.www.wifiseeker.view.HomeFragment;
import com.codemo.www.wifiseeker.view.MainActivity;
import com.codemo.www.wifiseeker.view.MapOptionsFragment;
import com.codemo.www.wifiseeker.view.MapsFragment;
import com.codemo.www.wifiseeker.view.WifiOptionsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;


/**
 * Created by root on 5/2/17.
 */

public class OnlineDatabaseController extends AsyncTask<String,String,String> {


    String operation;
    boolean empty;
    private ProgressDialog dialog;
    private static  MainActivity activity;

    public OnlineDatabaseController(String operation){
        this.operation=operation;
    }

    public static void setActivity(MainActivity activity) {
        OnlineDatabaseController.activity = activity;
    }


    @Override
    protected String doInBackground(String... params) {
        String login_url;


        login_url= "http://wifiseeker.eu.pn/";
//        login_url= "http://10.42.0.1/test/userNames.php";


        URL url = null;
        try {
            url = new URL(login_url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//            String post_data= URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8");
            String post_data="";
//            Log.v("gfhjk",params[4]);
            if(operation=="share"){
                Log.v("gfhjk","dfghjk inside share llllllllllllllllllllllllll for "+params[7]+"sdfg"+params[8]);
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode(operation,"UTF-8")+
                        "&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8")+
                        "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+
                        "&"+URLEncoder.encode("lat","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+
                        "&"+URLEncoder.encode("lng","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+
                        "&"+URLEncoder.encode("open","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8")+
                        "&"+URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(params[5],"UTF-8")+
                        "&"+URLEncoder.encode("report","UTF-8")+"="+URLEncoder.encode(params[6],"UTF-8")+
                        "&"+URLEncoder.encode("ipaddress","UTF-8")+"="+URLEncoder.encode(params[7],"UTF-8")+
                        "&"+URLEncoder.encode("linkspeed","UTF-8")+"="+URLEncoder.encode(params[8],"UTF-8")
                ;

            }
            if(operation=="getDetails"){
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode(operation,"UTF-8")+
                        "&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8");
            }
            if(operation=="getAll"){
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode(operation,"UTF-8");
            }
            if(operation=="getData"){
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode("getAll","UTF-8");
            }
            if(operation=="getNameList"){

                Log.v("gfhjk","dfghjk inside getName list llllllllllllllllllllllllll for "+params[0]);
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode(operation,"UTF-8")+
                        "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8");
            }
            if(operation=="updateRating"){

                Log.v("gfhjk","dfghjk inside update ratingggg list llllllllllllllllllllllllll for "+params[0]);
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode("getNameList","UTF-8")+
                        "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8");
            }
            if(operation=="updateReport"){

                Log.v("gfhjk","dfghjk inside update ratingggg list llllllllllllllllllllllllll for "+params[0]);
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode("getNameList","UTF-8")+
                        "&"+URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8");
            }
            if(operation=="saveRating"){
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode(operation,"UTF-8")+
                        "&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8")+
                        "&"+URLEncoder.encode("rating","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8");
            }
            if(operation=="saveReport"){
                post_data= URLEncoder.encode("operation","UTF-8")+"="+URLEncoder.encode(operation,"UTF-8")+
                        "&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8")+
                        "&"+URLEncoder.encode("report","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8");
            }
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
//            String[] mainResult = new String[10];
//            int i= 0;
            while ((line=bufferedReader.readLine())!=null){
//                mainResult[i]=line;
//                i++;
                    result+=line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
//            String[] mainResult = {result};
            if(result.contains("[]")){
                empty=true;
                Log.v("bn**d","iiiiiiiiiiiiii  no resulsts iiiiiiiiiiiiiiiiiiiiiii");
            }else{
                empty=false;
                Log.v("bn**d","iiiiiiiiiiiiii  results avaialble  iiiiiiiiiiiiiiiiiiiiiii");
            }
            Log.v("gfhjk",result);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }






        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        this.dialog.setMessage("Please Wait!!");
        this.dialog.setCancelable(false);
        this.dialog.show();
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        JSONArray ja=null;
        try {
            if(empty){
                Log.v("bn**d","iiiiiiiiiiiiii  connection lost or no resulsts iiiiiiiiiiiiiiiiiiiiiii");
            }else{
                JSONObject j = new JSONObject(s);
                ja= j.getJSONArray("data");
                JSONObject jj = ja.getJSONObject(0);
            }

            if(operation=="shared"){
//                Toast.makeText(main2Activity.getApplicationContext(),"shared", Toast.LENGTH_SHORT).show();
            }else if(operation=="getAll"){
                ArrayList<String[]> locationList = new ArrayList<>();

                for(int i=0; i<ja.length();i++){
                    String[] locationDetails = new String[7];
                    locationDetails[0]=ja.getJSONObject(i).getString("id");
                    locationDetails[1]=ja.getJSONObject(i).getString("name");
                    locationDetails[2]=ja.getJSONObject(i).getString("lat");
                    locationDetails[3]=ja.getJSONObject(i).getString("lng");
                    locationDetails[4]=ja.getJSONObject(i).getString("open");
                    locationDetails[5]=ja.getJSONObject(i).getString("rating");
                    locationDetails[6]=ja.getJSONObject(i).getString("report");
                    locationList.add(locationDetails);
                    Log.v("bn**d","iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+ja.getJSONObject(i).getString("name"));
                }
                MapsFragment homeFragment =(MapsFragment) manager.findFragmentByTag("MapsFragment");
//                homeFragment.setlocationList(locationList);
                for (String[] i:locationList){
                    Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....marker located ....aaaaaaaaaaaaaaaaaaaaaa*************** " + i[0]);
                    homeFragment.showLocations(i);
                }
//                main2Activity.testTxt.setText(jj.getString("name")+jj.getString("open"));
//                Log.v("bnd",jj.getString("name"));
            }else if(operation=="getData"){
                ArrayList<String[]> locationList = new ArrayList<>();

                for(int i=0; i<ja.length();i++){
                    String[] locationDetails = new String[9];
                    locationDetails[0]=ja.getJSONObject(i).getString("id");
                    locationDetails[1]=ja.getJSONObject(i).getString("name");
                    locationDetails[2]=ja.getJSONObject(i).getString("lat");
                    locationDetails[3]=ja.getJSONObject(i).getString("lng");
                    locationDetails[4]=ja.getJSONObject(i).getString("open");
                    locationDetails[5]=ja.getJSONObject(i).getString("rating");
                    locationDetails[6]=ja.getJSONObject(i).getString("report");
                    locationDetails[7]=ja.getJSONObject(i).getString("linkspeed");
                    locationList.add(locationDetails);
                    Log.v("bn**d","iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+ja.getJSONObject(i).getString("name"));
                }
                HomeFragment homeFragment =(HomeFragment) manager.findFragmentByTag("HomeFragment");
                homeFragment.setLocations(locationList);
            }
            else if(operation=="getNameList"){
                WifiOptionsFragment fragment =(WifiOptionsFragment) manager.findFragmentByTag("wifiOptionsFragment");

                Log.v("gfhjk","fffffffffffffffffffffff inside get name listfffffffffffffffffffff");
                if(empty){
                    fragment.setExist(false);
                    fragment.share();
                }else{
                    ArrayList<String[]> locationList = new ArrayList<>();
                    for(int i=0; i<ja.length();i++){
                        String[] locationDetails = new String[3];
                        locationDetails[0]=ja.getJSONObject(i).getString("id");
                        locationDetails[1]=ja.getJSONObject(i).getString("lat");
                        locationDetails[2]=ja.getJSONObject(i).getString("lng");
                        locationList.add(locationDetails);
                        Log.v("bn**d","iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+ja.getJSONObject(i).getString("name"));
                    }
                    fragment.setWifiNameList(locationList);
                    fragment.setExist(true);
                    fragment.share();
                }


            }
            else if(operation=="updateRating"){
                WifiOptionsFragment fragment =(WifiOptionsFragment) manager.findFragmentByTag("wifiOptionsFragment");
                if(empty){
                    fragment.setExist(false);
                    fragment.rate();
                }else{
                    Log.v("gfhjk","fffffffffffffffffffffff inside update rating fffffffffffffffffffff");
                    ArrayList<String[]> locationList = new ArrayList<>();
                    for(int i=0; i<ja.length();i++){
                        String[] locationDetails = new String[4];
                        locationDetails[0]=ja.getJSONObject(i).getString("id");
                        locationDetails[1]=ja.getJSONObject(i).getString("lat");
                        locationDetails[2]=ja.getJSONObject(i).getString("lng");
                        locationDetails[3]=ja.getJSONObject(i).getString("rating");
                        locationList.add(locationDetails);
                        Log.v("bn**d","iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+ja.getJSONObject(i).getString("name"));
                    }
//                    WifiOptionsFragment fragment =(WifiOptionsFragment) manager.findFragmentByTag("wifiOptionsFragment");
                    fragment.setWifiNameList(locationList);
                    fragment.setExist(true);
                    fragment.rate();
                }



            }else if(operation=="updateReport"){
                WifiOptionsFragment fragment =(WifiOptionsFragment) manager.findFragmentByTag("wifiOptionsFragment");
                if(empty){
                    fragment.setExist(false);
                    fragment.rate();
                }else{
                    Log.v("gfhjk","fffffffffffffffffffffff inside updatereporting fffffffffffffffffffff");
                    ArrayList<String[]> locationList = new ArrayList<>();
                    for(int i=0; i<ja.length();i++){
                        String[] locationDetails = new String[4];
                        locationDetails[0]=ja.getJSONObject(i).getString("id");
                        locationDetails[1]=ja.getJSONObject(i).getString("lat");
                        locationDetails[2]=ja.getJSONObject(i).getString("lng");
                        locationDetails[3]=ja.getJSONObject(i).getString("report");
                        locationList.add(locationDetails);
                        Log.v("bn**d","iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+ja.getJSONObject(i).getString("name"));
                    }

                    fragment.setWifiNameList(locationList);
                    fragment.setExist(true);
                    fragment.report();
                }


            }
//            else if(operation=="getDetails"){
//                String[] locationDetails = new String[7];
//                for(int i=0; i<ja.length();i++){
//
//                    locationDetails[0]=ja.getJSONObject(i).getString("id");
//                    locationDetails[1]=ja.getJSONObject(i).getString("name");
//                    locationDetails[2]=ja.getJSONObject(i).getString("lat");
//                    locationDetails[3]=ja.getJSONObject(i).getString("lng");
//                    locationDetails[4]=ja.getJSONObject(i).getString("open");
//                    locationDetails[5]=ja.getJSONObject(i).getString("rating");
//                    locationDetails[6]=ja.getJSONObject(i).getString("report");
//                    Log.v("bn**d","iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+ja.getJSONObject(i).getString("name"));
//                }
//                MapOptionsFragment fragment = (MapOptionsFragment) manager.findFragmentByTag("MapOptionsFragment");
//                fragment.setDetails(locationDetails);
////                MapOptionsFragment mapOptionsFragment = (MapOptionsFragment)manager.findFragmentByTag("MapOptionsFragment") ;
////                Log.v("rht","aaaaaaaaaaaaaaaaaaaa....3 clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());
////                mapOptionsFragment.setId(myItem.getId());
////                mapOptionsFragment.setDetails();
////                Log.v("rht","aaaaaaaaaaaaaaaaaaaa...4.clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());
////                NavigationContoller.navigateTo("MapOptionsFragment",manager);
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.hide(manager.findFragmentByTag("MapsFragment"));
//                transaction.show(manager.findFragmentByTag("MapOptionsFragment"));
//                transaction.commit();
//            }

        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            Log.v("bn**d","iiiiiiiiiiiiiii///////////////////////*************////////////////////////////////iiiiiiiiiiiiiiiiiiiiii");
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }


    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
