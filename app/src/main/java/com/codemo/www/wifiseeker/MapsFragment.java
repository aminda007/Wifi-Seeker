package com.codemo.www.wifiseeker;


//import android.app.Fragment;
//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

//import android.support.app.*;

public class MapsFragment extends Fragment {

    public MapsFragment() {
        // Required empty public constructor
    }

    GoogleMap mgoogleMap;
//    MapFragment mapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);


    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
//        mapFragment.getMapAsync(this);
//    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//    }
}
