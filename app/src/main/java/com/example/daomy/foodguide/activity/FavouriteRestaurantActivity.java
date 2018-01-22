package com.example.daomy.foodguide.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.FavoriteAdapter;
import com.example.daomy.foodguide.api.APIClient;
import com.example.daomy.foodguide.api.APIService;
import com.example.daomy.foodguide.model.ListViewItem;
import com.example.daomy.foodguide.model.Restaurant;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavouriteRestaurantActivity extends AppCompatActivity {
    private ListView resultListView;
    private FavoriteAdapter adapter;
    private ArrayList<ListViewItem> mList = new ArrayList<ListViewItem>();
    private ListViewItem lvItem;
    private ControllerDatabase db;
    private Restaurant mRestaurant;
    private int itemBeDelete;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_restaurant);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        db = new ControllerDatabase(this);
        db.open();

        resultListView = (ListView) findViewById(R.id.lvFavouriteResult);
        empty = (TextView) findViewById(android.R.id.empty);

        Intent intent = getIntent();
        itemBeDelete = intent.getIntExtra("Delete", 0);
        if(itemBeDelete > 0){
            db.DelFavouriteRestaurantItem(itemBeDelete);
        }

        Cursor cursor = db.getAllFavouriteRestaurant();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_ID_RESTAURANT));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_IMAGE));
            String address = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_ADDRESS));
            String price = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_PRICE));

            lvItem = new ListViewItem(id, image, name, address, price);
            mList.add(lvItem);
            cursor.moveToNext();
        }

        if (mList.size() <= 0){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.INVISIBLE);
            adapter = new FavoriteAdapter(this, mList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home){
            if(itemBeDelete == 0) {
                finish();
            }else{
                Intent intent = new Intent(FavouriteRestaurantActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void getRestaurantById(ListViewItem lt) {
        final APIService service = APIClient.getClient().create(APIService.class);
        Call<Restaurant> restaurantCall = service.getRestaurantById(lt.getId());
        restaurantCall.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                mRestaurant = response.body();
                Intent intent = new Intent(FavouriteRestaurantActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("Diadiem", mRestaurant);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(itemBeDelete == 0) {
                finish();
            }else{
                Intent intent = new Intent(FavouriteRestaurantActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
