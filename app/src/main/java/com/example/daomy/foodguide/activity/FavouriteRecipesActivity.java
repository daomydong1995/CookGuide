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
import com.example.daomy.foodguide.model.ListViewItem;
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

public class FavouriteRecipesActivity extends AppCompatActivity {
    private ListView resultListView;
    private FavoriteAdapter adapter;
    private ArrayList<ListViewItem> mList = new ArrayList<ListViewItem>();
    private ListViewItem lvItem;
    private ControllerDatabase db;
    private Recipes mRecipes;
    private int itemBeDelete;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_recipes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        db = new ControllerDatabase(this);
        db.open();

        resultListView = (ListView) findViewById(R.id.lvFavouriteResult);
        empty = (TextView) findViewById(android.R.id.empty);

        Intent intent = getIntent();
        itemBeDelete = intent.getIntExtra("Delete", 0);
        if(itemBeDelete > 0){
            db.DelFavouriteRecipesItem(itemBeDelete);
        }

        Cursor cursor = db.getAllFavouriteRecipes();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_ID_RECIPES));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_IMAGE));
            String time = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_TIME));
            String serving = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_SERVING));

            lvItem = new ListViewItem(id, image, name, getString(R.string.tgcb) + time + getString(R.string.phut), serving + getString(R.string.nguoidung));
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
                getRecipesById(lt);
            }
        });
    }

    private void getRecipesById(ListViewItem lt) {
        Cursor cursor = db.getFavouriteRecipesById(lt.getId());
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_IMAGE));
            int time = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_TIME));
            int serving = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_SERVING));
            int kcal = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_KCAL));
            String ingredients = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_INGREDIENTS));
            String instruction = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_INSTRUCTION));
            String code = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_CODE_YOUTUBE));

            mRecipes = new Recipes(id, name, image, time, serving, kcal, ingredients, instruction,code);
        }

        Intent intent = new Intent(FavouriteRecipesActivity.this, RecipesDetailActivity.class);
        intent.putExtra("Congthuc", mRecipes);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite_recipes, menu);
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
                Intent intent = new Intent(FavouriteRecipesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(itemBeDelete == 0) {
                finish();
            }else{
                Intent intent = new Intent(FavouriteRecipesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
