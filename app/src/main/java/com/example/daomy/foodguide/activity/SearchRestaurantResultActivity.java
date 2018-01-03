package com.example.daomy.foodguide.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.ResultListViewAdapter;
import com.example.daomy.foodguide.model.ListViewItem;
import com.example.daomy.foodguide.model.Restaurant;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;


public class SearchRestaurantResultActivity extends AppCompatActivity {
    private ListView resultListView;
    private ResultListViewAdapter adapter;
    private ArrayList<ListViewItem> mList = new ArrayList<ListViewItem>();
    private ListViewItem lvItem;
    private ControllerDatabase db;
    private Restaurant mRestaurant;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurant_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        resultListView = (ListView) findViewById(R.id.lvResult);
        empty = (TextView) findViewById(android.R.id.empty);

        db = new ControllerDatabase(this);
        db.open();


        Intent intent = getIntent();
        String search = intent.getStringExtra("Search");
        String searchDistrict = intent.getStringExtra("SearchDistrict");
        int searchPrice = intent.getIntExtra("SearchPrice", 0);
        Cursor cursor = db.getRestaurantByNameDistrictPrice(search, searchDistrict, searchPrice);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_IMAGE));
            String address = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_ADDRESS));
            String price = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_PRICE));

            lvItem = new ListViewItem(id, image, name, address, price);
            mList.add(lvItem);
            cursor.moveToNext();
        }

        if (mList.size() <= 0){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.INVISIBLE);
            adapter = new ResultListViewAdapter(this, mList);
            resultListView.setAdapter(adapter);
        }
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem lt = mList.get(position);
                getRestaurantById(lt);
            }
        });

    }



    private void getRestaurantById(ListViewItem lt) {
        Cursor c = db.getRestaurantById(lt.getId());
        if (c != null && c.moveToFirst()) {
            int idR = c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_ID));
            String name = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_NAME));
            String image = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_IMAGE));
            String address = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_ADDRESS));
            String price = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_PRICE));
            String phone = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_PHONE));
            String district = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_DISTRICT));
            String location = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_LOCATION));
            String time = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_TIME));


            mRestaurant = new Restaurant(idR, name, image, address, price, phone, district, location, time);
        }

        Intent intent = new Intent(SearchRestaurantResultActivity.this, RestaurantDetailActivity.class);
        intent.putExtra("Diadiem", mRestaurant);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_restaurant_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
