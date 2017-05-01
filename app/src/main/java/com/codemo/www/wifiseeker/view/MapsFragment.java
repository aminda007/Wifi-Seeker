package com.codemo.www.wifiseeker.view;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.codemo.www.wifiseeker.R;
//import com.google.android.gms.identity.intents.Address;
import com.codemo.www.wifiseeker.controller.DatabaseController;
import com.codemo.www.wifiseeker.controller.MapController;
import com.codemo.www.wifiseeker.controller.NavigationContoller;
import com.codemo.www.wifiseeker.model.MyClusterRenderer;
import com.codemo.www.wifiseeker.model.MyItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.List;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;


public class MapsFragment extends Fragment  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mgoogleMap;
    static MainActivity Activity;
    GoogleApiClient mGoogleApiClient;
    Boolean autoLocate;
    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    public MapsFragment() {
        // Required empty public constructor
    }


//    MapFragment mapFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public static void setActivity(MainActivity activity){
        Activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        final SearchView searchBar = (SearchView) view.findViewById(R.id.searchBar);
        Button locateBtn = (Button) view.findViewById(R.id.locateBtn);
        Button refreshBtn = (Button) view.findViewById(R.id.refreshLocationsBtn);

//        searchBar.setOnSearchClickListener(new SearchView.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"search clickedddd", Toast.LENGTH_SHORT).show();
//            }
//        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit (String query) {
                Toast.makeText(getContext(),"search clicked" + query, Toast.LENGTH_SHORT).show();
                try {
                    geoLocate(query);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getContext(),"search changed" + newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        autoLocate=false;
        locateBtn.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(autoLocate){
                            disableAutoLocate();
                        }else {
                            enableAutoLocate();
                        }
                    }
                }
        );
        refreshBtn.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        getLocations();
                    }
                }
        );
//        mgoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                int position = (int)(marker.getTag());
//                //Using position get Value from arraylist
//                Toast.makeText(getContext(),"marker clicked"+marker.getPosition().toString()+marker.getTitle(), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
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
//        gotoLocation(6.7967473,79.8982529,15);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        setUpClusterer();
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Toast.makeText(getContext(),"cluster clicked", Toast.LENGTH_SHORT).show();
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(cluster.getPosition(),mgoogleMap.getCameraPosition().zoom+3);
                mgoogleMap.moveCamera(update);
                return false;
            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem myItem) {
                Toast.makeText(getContext(),"marker clicked", Toast.LENGTH_SHORT).show();
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa....1 clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());

                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.... 2 clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());
                MapOptionsFragment mapOptionsFragment = (MapOptionsFragment)manager.findFragmentByTag("MapOptionsFragment") ;
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa....3 clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());
                mapOptionsFragment.setId(myItem.getId());
                mapOptionsFragment.setDetails();
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa...4.clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());
//                NavigationContoller.navigateTo("MapOptionsFragment",manager);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(manager.findFragmentByTag("MapsFragment"));
                transaction.show(manager.findFragmentByTag("MapOptionsFragment"));
                transaction.commit();

                Log.v("rht","aaaaaaaaaaaaaaaaaaaa...5.clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());

                return false;

            }
        });
//        setUpOpenClusterer();
        getLocations();


    }

    public void getLocations(){
        DatabaseController dbc= MainActivity.dbControlller;
//        dbc.addLocation(location);
//        String[] locationList = dbc.databaseTOString();
        for (String[] i:dbc.databaseTOString()){
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....marker located ....aaaaaaaaaaaaaaaaaaaaaa*************** " + i[0]);
            showLocations(i);
        }
    }

    public void enableAutoLocate(){
        autoLocate = true;
        Toast.makeText(getContext(),"auto locate on", Toast.LENGTH_SHORT).show();
    }
    public void disableAutoLocate(){
        autoLocate = false;
        Toast.makeText(getContext(),"auto locate off", Toast.LENGTH_SHORT).show();
    }

    public void gotoLocation(double lat, double lng, float zoom){
        LatLng ll = new LatLng(lat,lng);
//        LatLng ll1 = new LatLng(6.7975463,79.8995939);
//        LatLng ll2 = new LatLng(6.795755, 79.901080);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mgoogleMap.moveCamera(update);
//        mgoogleMap.addMarker(new MarkerOptions().title("CSE").position(ll).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_blue_full)));
//        mgoogleMap.addMarker(new MarkerOptions().title("CSE").position(ll1).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_red_full)));
//        mgoogleMap.addMarker(new MarkerOptions().title("CSE").position(ll2).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_blue_full)));
    }

    // Add cluster items (markers) to the cluster manager.
    public void showLocations(String[] locationInfo){
        MyItem offsetItem;
//        LatLng ll = new LatLng(Double.parseDouble(locationInfo[2]),Double.parseDouble(locationInfo[3]));
        if(locationInfo[4].contains("1")) {
            offsetItem = new MyItem(Integer.parseInt(locationInfo[0]),Double.parseDouble(locationInfo[2]), Double.parseDouble(locationInfo[3]), true);
        }else{
            offsetItem = new MyItem(Integer.parseInt(locationInfo[0]),Double.parseDouble(locationInfo[2]), Double.parseDouble(locationInfo[3]), false);
        }
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....marker info ....aaaaaaaaaaaaaaaaaaaaaa*************** "+ locationInfo[0]+" "+locationInfo[1]+" "+locationInfo[2]+" "+locationInfo[3]+" "+locationInfo[4]+" "+locationInfo[5]+" "+locationInfo[6]);
        mClusterManager.addItem(offsetItem);
    }

    public  void geoLocate(String location) throws  IOException{
//        String location = query;
        Geocoder gc = new Geocoder(getContext());
        List<Address> list =gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();
        Toast.makeText(getContext(),"locality is " + locality, Toast.LENGTH_SHORT).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        gotoLocation(lat,lng,15);

    }

    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getContext(), mgoogleMap);
        mClusterManager.setRenderer(new MyClusterRenderer(getContext(), mgoogleMap, mClusterManager));
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
//        mgoogleMap.moveCamera();
        mgoogleMap.setOnCameraIdleListener(mClusterManager);
        mgoogleMap.setOnMarkerClickListener(mClusterManager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mapTypeNone:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeTerrain:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeSatellite:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeHybrid:
                mgoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            Toast.makeText(getContext(),"Cannot get current location", Toast.LENGTH_SHORT).show();
        }else{
            //            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....location updated ....aaaaaaaaaaaaaaaaaaaaaa***************"+ );
            MapController.updateLocation(location.getLatitude(),location.getLongitude());
            if(autoLocate){
                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate update =CameraUpdateFactory.newLatLngZoom(ll,15);
                mgoogleMap.animateCamera(update);
            }

        }
    }
}

////////////AIzaSyDwGlZCNSq5mJeXjfjsRsLO3TI5-gEBxlg
