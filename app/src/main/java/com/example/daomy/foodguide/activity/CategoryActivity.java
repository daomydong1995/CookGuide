package com.example.daomy.foodguide.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ApiUtils;
import com.github.amlcurran.showcaseview.ShowcaseView;

import java.util.ArrayList;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.RecipesAdapter;
import com.example.daomy.foodguide.adapter.RestaurantAdapter;
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.model.Restaurant;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;


public class CategoryActivity extends AppCompatActivity {
    private GridView mGridView;
    private ArrayList<Recipes> mListRecipes = new ArrayList<Recipes>();
    private Recipes mRecipes;
    private RecipesAdapter mAdapterRecipes;
    private ControllerDatabase db;
    private String IDCategory;
    private ArrayList<Restaurant> mListRestaurant = new ArrayList<Restaurant>();
    private Restaurant mRestaurant;
    private RestaurantAdapter mAdapterRestaurant;
    private TextView mMessage;
    private final ApiUtils apiUtils = new ApiUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadComponents();

        db = new ControllerDatabase(this);
        db.open();

        final Intent intent = getIntent();
        IDCategory = intent.getStringExtra("IDCategory");
        String name = intent.getStringExtra("NameCategory");
        getSupportActionBar().setTitle(name);

        getRecipes();
        if (mListRecipes.size() > 0) {
            mMessage.setVisibility(View.INVISIBLE);
            mAdapterRecipes = new RecipesAdapter(CategoryActivity.this, mListRecipes);
            mGridView.setAdapter(mAdapterRecipes);

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mRecipes = mListRecipes.get(position);
                    Intent intent = new Intent(CategoryActivity.this, RecipesDetailActivity.class);
                    intent.putExtra("Congthuc", mRecipes);
                    startActivity(intent);
                }
            });
        }

        findViewById(R.id.btnRecipes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListRecipes.clear();
                getRecipes();
                if (mListRecipes.size() > 0) {
                    mMessage.setVisibility(View.INVISIBLE);
                    mAdapterRecipes = new RecipesAdapter(CategoryActivity.this, mListRecipes);
                    mGridView.setAdapter(mAdapterRecipes);
                } else {
                    mListRestaurant.clear();
                    mAdapterRestaurant = new RestaurantAdapter(CategoryActivity.this, mListRestaurant);
                    mGridView.setAdapter(mAdapterRestaurant);
                    mMessage.setVisibility(View.VISIBLE);
                }
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mRecipes = mListRecipes.get(position);
                        Intent intent = new Intent(CategoryActivity.this, RecipesDetailActivity.class);
                        intent.putExtra("Congthuc", mRecipes);
                        startActivity(intent);
                    }
                });

            }
        });
        findViewById(R.id.btnRestaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListRestaurant.clear();
                getRestaurant();
                if (mListRestaurant.size() > 0) {
                    mMessage.setVisibility(View.INVISIBLE);
                    mAdapterRestaurant = new RestaurantAdapter(CategoryActivity.this, mListRestaurant);
                    mGridView.setAdapter(mAdapterRestaurant);
                } else {
                    mListRecipes.clear();
                    mAdapterRecipes = new RecipesAdapter(CategoryActivity.this, mListRecipes);
                    mGridView.setAdapter(mAdapterRecipes);
                    mMessage.setVisibility(View.VISIBLE);
                }
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mRestaurant = mListRestaurant.get(position);
                        Intent intent = new Intent(CategoryActivity.this, RestaurantDetailActivity.class);
                        intent.putExtra("Diadiem", mRestaurant);
                        startActivity(intent);
                    }
                });

            }
        });

    }

    private void loadComponents() {
        mGridView = (GridView) findViewById(R.id.gvMain);
        mMessage = (TextView) findViewById(android.R.id.empty);
    }


    private void getRestaurant() {
        Cursor cursor = db.getRestaurantByCategory(IDCategory);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_IMAGE));
            String address = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_ADDRESS));
            String price = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_PRICE));
            String phone = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_PHONE));
            String district = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_DISTRICT));
            String location = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_LOCATION));
            String time = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RESTAURANT_TIME));


            mRestaurant = new Restaurant(id, name, image, address, price, phone, district, location, time);
            mListRestaurant.add(mRestaurant);
            cursor.moveToNext();
        }
    }

    private void getRecipes() {
        Cursor cursor = db.getRecipesByCategory(IDCategory);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_IMAGE));
            int time = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_TIME));
            int serving = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_SERVING));
            int kcal = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_KCAL));
            String ingredients = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_INGREDIENTS));
            String instruction = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_INSTRUCTION));


            mRecipes = new Recipes(id, name, image, time, serving, kcal, ingredients, instruction);
            mListRecipes.add(mRecipes);
            cursor.moveToNext();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                AlertDialog.Builder alert = new AlertDialog.Builder(CategoryActivity.this);
                alert.setTitle("Thông Báo!");
                alert.setMessage("Vui lòng lựa chọn kiểu bạn muốn tìm kiếm.");
                alert.setNegativeButton("CÔNG THỨC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                        intent.putExtra("Key", "CongThuc");
                        startActivity(intent);

                    }
                });
                alert.setPositiveButton("ĐỊA ĐIỂM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
                        intent.putExtra("Key", "DiaDiem");
                        startActivity(intent);

                    }
                });

                AlertDialog dialog = alert.create();
                dialog.show();
                return true;
            case R.id.action_calendar:
                startActivity(new Intent(CategoryActivity.this, CalendarActivity.class));
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
