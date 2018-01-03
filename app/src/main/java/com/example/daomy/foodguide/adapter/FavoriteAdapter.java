package com.example.daomy.foodguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.activity.FavouriteRecipesActivity;
import com.example.daomy.foodguide.model.ListViewItem;
import com.example.daomy.foodguide.model.Restaurant;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

/**
 * Created by WIN7 on 05/19/2015.
 */
public class FavoriteAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private ListViewItem listViewItem;
    private ArrayList<ListViewItem> lvdata;
    ControllerDatabase db;
    Restaurant restaurant;
    int currentItemId;

    public FavoriteAdapter(Context context, ArrayList<ListViewItem> lvdata) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.swipe_listview_item, null);
            viewHolder.imvItem = (ImageView) convertView.findViewById(R.id.lvItemImage);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.lvItemName);
            viewHolder.tvDes = (TextView) convertView.findViewById(R.id.lvItemDescription);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.lvItemPrice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        listViewItem = lvdata.get(position);
        currentItemId = listViewItem.getId();
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

        SwipeLayout swipeLayout = (SwipeLayout)convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });

        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*db.DelFavouriteRestaurantItem(currentItemId);
                db.getAllFavouriteRestaurant();*/
                ListViewItem lt = lvdata.get(position);
                Intent intent = new Intent(context, FavouriteRecipesActivity.class);
                intent.putExtra("Delete", lt.getId());
                context.startActivity(intent);

                //Toast.makeText(context, "current ID : " + lt.getId(), Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private ImageView imvItem;
        private TextView tvName;
        private TextView tvDes;
        private TextView tvPrice;

    }

    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

}
