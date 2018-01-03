package com.example.daomy.foodguide.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.model.ListViewItem;

/**
 * Created by PDNghiaDev on 5/5/2015.
 */
public class ResultListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ListViewItem listViewItem;
    private ArrayList<ListViewItem> lvdata;

    public ResultListViewAdapter(Context context, ArrayList<ListViewItem> lvdata) {
        this.context = context;
        this.lvdata = lvdata;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lvdata.size();
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
            convertView = inflater.inflate(R.layout.list_view_result_item, null);
            viewHolder.imvItem = (ImageView) convertView.findViewById(R.id.lvItemImage);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.lvItemName);
            viewHolder.tvDes = (TextView) convertView.findViewById(R.id.lvItemDescription);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.lvItemPrice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        listViewItem = lvdata.get(position);
        //viewHolder.imvItem.setImageResource(item.lvImageID);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(3)
                .cornerRadiusDp(50)
                .oval(false)
                .build();

        Picasso.with(context)
                .load(listViewItem.getLvImageID())
                .fit()
                .transform(transformation)
                .into(viewHolder.imvItem);

        viewHolder.tvName.setText(listViewItem.getLvTextName());
        viewHolder.tvDes.setText(listViewItem.getLvTextDescription());
        viewHolder.tvPrice.setText(listViewItem.getLvTextPrice());

        //viewHolder.tvNameRestaurant.setText(mRestaurant.getName());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imvItem;
        private TextView tvName;
        private TextView tvDes;
        private TextView tvPrice;

    }
}
