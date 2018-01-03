package com.example.daomy.foodguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.model.Restaurant;

/**
 * Created by PDNghiaDev on 4/17/2015.
 */
public class RestaurantAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private Restaurant mRestaurant;
    private ArrayList<Restaurant> mList;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> list) {
        this.mContext = context;
        this.mList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.restaurant_grid_item, null);
            viewHolder.imgRestaurant = (ImageView) convertView.findViewById(R.id.imageRestaurant);
            viewHolder.tvNameRestaurant = (TextView) convertView.findViewById(R.id.textNameRestaurant);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mRestaurant = mList.get(position);
        Picasso.with(mContext).load(mRestaurant.getImage()).into(viewHolder.imgRestaurant);
        viewHolder.tvNameRestaurant.setText(mRestaurant.getName());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imgRestaurant;
        private TextView tvNameRestaurant;
    }
}
