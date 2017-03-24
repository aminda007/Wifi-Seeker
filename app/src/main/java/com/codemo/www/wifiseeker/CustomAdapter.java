package com.codemo.www.wifiseeker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 2/21/17.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, String[] wifiNames) {
        super(context, R.layout.row_item,wifiNames);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.row_item,parent,false);

        String row_item =getItem(position);
        TextView wifiName = (TextView) customView.findViewById(R.id.wifiName);
        ImageView wifiImage = (ImageView) customView.findViewById(R.id.wifiImage);

        wifiName.setText(row_item);
        wifiImage.setImageResource(R.drawable.ic_wifi_green);
        return customView;

    }
}
