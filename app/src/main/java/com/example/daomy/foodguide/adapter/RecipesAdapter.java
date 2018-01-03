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
import com.example.daomy.foodguide.model.Recipes;

/**
 * Created by PDNghiaDev on 4/17/2015.
 */
public class RecipesAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private Recipes mRecipes;
    private ArrayList<Recipes> mList;

    public RecipesAdapter(Context context, ArrayList<Recipes> list) {
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
            convertView = inflater.inflate(R.layout.recipes_grid_item, null);
            viewHolder.imgRecipes = (ImageView) convertView.findViewById(R.id.imageRecipes);
            viewHolder.tvNameRecipes = (TextView) convertView.findViewById(R.id.textNameRecipes);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mRecipes = mList.get(position);
        Picasso.with(mContext).load(mRecipes.getImage()).into(viewHolder.imgRecipes);
        viewHolder.tvNameRecipes.setText(mRecipes.getName());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imgRecipes;
        private TextView tvNameRecipes;
    }
}
