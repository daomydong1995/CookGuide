package com.example.daomy.foodguide.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.daomy.foodguide.activity.R;

public class ViewPagerAdapter extends PagerAdapter {
	Context context;
	int[] hinh;
	LayoutInflater inflater;
	private View.OnClickListener mOnButtonClick;
 
	public ViewPagerAdapter(Context context, int[] hinh) {
		this.context = context;
		this.hinh = hinh;
	}
 
	@Override
	public int getCount() {
		return hinh.length;
	}
 
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	// Method to set button click listener variable
	public void setOnButtonClickListener(View.OnClickListener listener) {
		mOnButtonClick = listener;
	}

	@Override
	public Object instantiateItem(final ViewGroup container, int position) {
 
		// Declare Variables
		ImageButton nutok;
		ImageView hinhBackGround;
		Button btnOK;
		ImageButton bt2;

 
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.viewpager_item, container,
				false);

		btnOK = (Button) itemView.findViewById(R.id.btnOk);
		if(position < 4){
			btnOK.setVisibility(View.INVISIBLE);
		}

		btnOK.setOnClickListener(mOnButtonClick);

		hinhBackGround = (ImageView) itemView.findViewById(R.id.imgBackground);
		
		hinhBackGround.setImageResource(hinh[position]);

		((ViewPager) container).addView(itemView);
		return itemView;
	}
 
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
				((ViewPager) container).removeView((RelativeLayout) object);
	}
}