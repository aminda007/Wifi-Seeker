package com.codemo.www.wifiseeker.model;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.codemo.www.wifiseeker.controller.NavigationContoller;
import com.codemo.www.wifiseeker.view.MainActivity;
import com.codemo.www.wifiseeker.view.MapsFragment;

import java.util.ArrayList;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;

/**
 * Created by root on 5/13/17.
 */

public class WifiLocator {
    private ArrayList<String[]> fullList;
    private static float range;
    private static boolean dataSet;
    private static MainActivity Activity;

    public static void setActivity(MainActivity activity){
        Activity=activity;
    }

    public static boolean isDataSet() {
        return dataSet;
    }

    public static void setDataSet(boolean dataSet) {
        WifiLocator.dataSet = dataSet;
    }

    public static void setRange(float f){
        range=f;
        Log.v("rht","aaaaaaa ///////////////////////// search range is set to aaaaaaaaaa"+f);
    }


    public void setData(ArrayList<String[]> locationList){
        setFullList(locationList);
        Log.v("rht","aaaaaaa /////////////////////////all location data is set aaaaaaaaaa"+ getFullList().size());

    }

    public void getWifi(){
        ArrayList<String[]> inRangeList =getInRangeList();
        if(inRangeList.isEmpty()){
            Toast.makeText(Activity.getApplicationContext(),"Try again after increasing the search range", Toast.LENGTH_SHORT).show();
        }else{
            ArrayList<Double[]> scoredList = getScoredList(inRangeList);
            ArrayList<Double[]> sortedList = getSortedList(scoredList);
            String[] result = getResult(sortedList);
            NavigationContoller.navigateTo("MapsFragment",manager);
            MapsFragment fragment =(MapsFragment) manager.findFragmentByTag("MapsFragment");
            fragment.gotoLocation(Double.parseDouble(result[2]),Double.parseDouble(result[3]),25);
        }

    }

    private ArrayList<String[]> getInRangeList(){

        float[] results = new float[1];
        results[0]=100;
        ArrayList<String[]> inRange= new ArrayList<>();
        for(String[] i : getFullList()){
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside get in range....aaaaaaaaaaaaaaaaaaaaaa***************"+i[0]);
            Location.distanceBetween(WifiLocation.getLat(),WifiLocation.getLng(),Double.parseDouble(i[2]),Double.parseDouble(i[3]),results);
            if(results[0]<range){
                i[8]=String.valueOf(results[0]);
                inRange.add(i);
//                break;
            }
        }
        return  inRange;
    }

    private ArrayList<Double[]> getScoredList(ArrayList<String[]> list) {
        ArrayList<Double[]> scoredList = new ArrayList<>(list.size());
        Double[] scoredItem = new Double[2];
        for(String[] i :list){
            scoredItem[0]=Double.parseDouble(i[0]);
            scoredItem[1]=Double.parseDouble(i[8])*(-10.0)+    // distance
                    Double.parseDouble(i[5])*(5.0)+           //  rating
                    Double.parseDouble(i[6])*(-5.0)+          //  report
                    Double.parseDouble(i[7])*(2.0);           // link speed
            scoredList.add(scoredItem);
        }
        return scoredList;
    }

    private ArrayList<Double[]> getSortedList(ArrayList<Double[]> list){
        int i, j;
        double temp;
        double tempId;
        for(i=1; i<list.size();i++ ){
            j = i - 1;
            while (j >= 0 && list.get(j)[1] < list.get(i)[1] )
            {
                temp = list.get(i)[1];
                tempId = list.get(i)[0];

                list.get(i)[1] = list.get(j)[1];
                list.get(i)[0] = list.get(j)[0];

                list.get(j)[1] = temp;
                list.get(j)[0] = tempId;

                i=j;
                j--;

            }
        }
        for (Double[] x :list){
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....sorted array...aaaaaaaaaaaaaaaaaaaaaa***************"+x[0]);
        }
        return list;
    }

    private String[] getResult(ArrayList<Double[]> list){
        String[] result =null;
        for(Double[] j : list){
            for(String[] i : getInRangeList()){
                if(j[0]==Double.parseDouble(i[0])&& i[4]=="1"){
                    result=i;
                    break;
                }
                else if(j[0]==Double.parseDouble(i[0]) && list.indexOf(j)==0){
                    result=i;
                }
            }
        }

        return result;
    }

    public ArrayList<String[]> getFullList() {
        return fullList;
    }

    public void setFullList(ArrayList<String[]> fullList) {
        this.fullList = fullList;
    }
}
