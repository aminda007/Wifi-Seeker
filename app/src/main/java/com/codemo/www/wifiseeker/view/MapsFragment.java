package com.codemo.www.wifiseeker.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;


public class MapsFragment extends Fragment  implements OnMapReadyCallback{
    GoogleMap mgoogleMap;
    static MainActivity Activity;
    public MapsFragment() {
        // Required empty public constructor
    }


//    MapFragment mapFragment;
    public static void setActivity(MainActivity activity){
        Activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SearchView searchBar = (SearchView) view.findViewById(R.id.searchBar);

//        searchBar.setOnSearchClickListener(new SearchView.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"search clickedddd", Toast.LENGTH_SHORT).show();
//            }
//        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(),"search clicked" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getContext(),"search changed" + newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity.initMap().getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap=googleMap;
        gotoLocation(6.7967473,79.8982529,15);


    }
    public void gotoLocation(double lat, double lng, float zoom){
        LatLng ll = new LatLng(lat,lng);
        LatLng ll1 = new LatLng(6.7975463,79.8995939);
        LatLng ll2 = new LatLng(6.795755, 79.901080);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);

        mgoogleMap.moveCamera(update);
        mgoogleMap.addMarker(new MarkerOptions().title("CSE").position(ll).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_blue_full)));
        mgoogleMap.addMarker(new MarkerOptions().title("CSE").position(ll1).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_red_full)));
        mgoogleMap.addMarker(new MarkerOptions().title("CSE").position(ll2).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_blue_full)));
    }

}

////////////AIzaSyDwGlZCNSq5mJeXjfjsRsLO3TI5-gEBxlg
