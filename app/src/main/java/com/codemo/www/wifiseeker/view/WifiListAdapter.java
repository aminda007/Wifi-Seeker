package com.codemo.www.wifiseeker.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codemo.www.wifiseeker.R;

/**
 * Created by root on 3/5/17.
 */

public class WifiListAdapter extends ArrayAdapter<String> {
//    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public WifiListAdapter(Context context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.row_item, itemname);
        // TODO Auto-generated constructor stub

        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.row_item, null,true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.wifiName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.wifiImage);
        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;
    }
}
