package com.codemo.www.wifiseeker.model;

import android.content.Context;

import com.codemo.www.wifiseeker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by root on 4/17/17.
 */

public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {
    public MyClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        if(item.isOpen()){
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_blue_full));
        }else {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_wifi_red_full));
                }
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
//        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}
