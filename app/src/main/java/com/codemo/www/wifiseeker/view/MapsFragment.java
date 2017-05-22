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
import com.codemo.www.wifiseeker.controller.OnlineDatabaseController;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.codemo.www.wifiseeker.view.MainActivity.manager;


public class MapsFragment extends Fragment  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mgoogleMap;
    Marker myMarker;
    Marker findMarker;
    private static boolean internet;
    private static boolean markerSet;
    static MainActivity Activity;
    GoogleApiClient mGoogleApiClient;
    Boolean autoLocate;
    ArrayList<String[]> locationList;
    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static boolean isInternet() {
        return internet;
    }

    public static void setInternet(boolean internet) {
        MapsFragment.internet = internet;
    }


    public static boolean isMarkerSet() {
        return markerSet;
    }

    public static void setMarkerSet(boolean markerSet) {
        MapsFragment.markerSet = markerSet;
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
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....map fragment created....aaaaaaaaaaaaaaaaaaaaaa***");
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
                        if(Activity.isGpsAvailable()){
                            if(autoLocate){
                                disableAutoLocate();
                            }else {
                                enableAutoLocate();
                            }
                        }else{
                            Toast.makeText(getContext(),"enable location services", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
        refreshBtn.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(Activity.isNetworkAvailable()){
                            HomeFragment homeFragment =(HomeFragment) manager.findFragmentByTag("HomeFragment");
                            homeFragment.initLocator();
                            mClusterManager.clearItems();
                            getLocations();
                        }else{
                            Toast.makeText(getContext(),"Connect to internet and try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
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
        mgoogleMap.getUiSettings().setMapToolbarEnabled(false);
//        gotoLocation(6.7967473,79.8982529,15);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        setUpClusterer();
        mgoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                findMarker.setVisible(false);
            }
        });
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
                MapOptionsFragment mapOptionsFragment = (MapOptionsFragment)manager.findFragmentByTag("MapOptionsFragment") ;
//                Log.v("rht","aaaaaaaaaaaaaaaaaaaa....3 clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());
                mapOptionsFragment.setId(myItem.getId().toString());
           //     mapOptionsFragment.setRating(myItem.get.toString());
                mapOptionsFragment.setLat(String.valueOf(myItem.getPosition().latitude));
                mapOptionsFragment.setLng(String.valueOf(myItem.getPosition().longitude));
                mapOptionsFragment.setRating(String.valueOf(myItem.getRating()));
                mapOptionsFragment.setReport(String.valueOf(myItem.getReport()));
                mapOptionsFragment.setName(myItem.getMname());
                if(myItem.isOpen()){
                    mapOptionsFragment.setOpen("open");
                }else {
                    mapOptionsFragment.setOpen("secured");
                }
                NavigationContoller.navigateTo("MapOptionsFragment",manager);
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa...5.clicked item id....aaaaaaaaaaaaaaaaaaaaaa***************"+myItem.getId());

                return false;

            }
        });
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa... end of on item click ....aaaaaaaaaaaaaaaaaaaaaa**********");
        if(isInternet()){
            getLocations();
            setMarkerSet(true);
        }



    }
    public void setFindMarker(double lat, double lng){
        LatLng ll = new LatLng(lat,lng);
        MarkerOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_find_loc));
        findMarker = mgoogleMap.addMarker(options);
    }

    public void getLocations(){
        setMarkerSet(true);
        OnlineDatabaseController network=new OnlineDatabaseController("getAll");
        network.execute();
    }
    public ArrayList<String[]> getlocationList(){
        return locationList;
    }
    public void setlocationList(ArrayList<String[]> list){
        locationList=list;
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
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mgoogleMap.moveCamera(update);
    }

    // Add cluster items (markers) to the cluster manager.
    public void showLocations(String[] locationInfo){
        MyItem offsetItem;
        if(locationInfo[4].contains("1")) {
            offsetItem = new MyItem(Integer.parseInt(locationInfo[0]),locationInfo[1],Double.parseDouble(locationInfo[2]), Double.parseDouble(locationInfo[3]), true, Double.parseDouble(locationInfo[5]),Integer.parseInt(locationInfo[6]));
        }else{
            offsetItem = new MyItem(Integer.parseInt(locationInfo[0]),locationInfo[1],Double.parseDouble(locationInfo[2]), Double.parseDouble(locationInfo[3]), false, Double.parseDouble(locationInfo[5]),Integer.parseInt(locationInfo[6]));
        }
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....marker info ....aaaaaaaaaaaaaaaaaaaaaa*************** "+ locationInfo[0]+" "+locationInfo[1]+" "+locationInfo[2]+" "+locationInfo[3]+" "+locationInfo[4]+" "+locationInfo[5]+" "+locationInfo[6]);
        mClusterManager.addItem(offsetItem);
    }

    public  void geoLocate(String location) throws  IOException{
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
        mClusterManager = new ClusterManager<MyItem>(getContext(), mgoogleMap);
        mClusterManager.setRenderer(new MyClusterRenderer(getContext(), mgoogleMap, mClusterManager));
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
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....location connected....aaaaaa");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....location failed ....aaaaaa");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"connection failed", Toast.LENGTH_SHORT).show();
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....location failed ....aaaaaa");
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            Toast.makeText(getContext(),"Cannot get current location", Toast.LENGTH_SHORT).show();
        }else{
                        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....location updated ....aaaaaaaaaaaaaaaaaaaaaa***************"+location.getLatitude()+"..."+location.getLongitude());
            MapController.updateLocation(location.getLatitude(),location.getLongitude());
            if(autoLocate){

                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                MarkerOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location));

                if(myMarker == null){
                    myMarker = mgoogleMap.addMarker(options);
                }
                else {
                    myMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                }
                CameraUpdate update =CameraUpdateFactory.newLatLngZoom(ll,20);
                mgoogleMap.animateCamera(update);
            }else{
                if(myMarker!=null){
                    myMarker.setVisible(false);
                }
            }

        }
    }
}

////////////AIzaSyDwGlZCNSq5mJeXjfjsRsLO3TI5-gEBxlg
