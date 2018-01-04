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
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;


public class SearchRecipesResultActivity extends AppCompatActivity {
    private ListView resultListView;
    private ResultListViewAdapter adapter;
    private ArrayList<ListViewItem> mList = new ArrayList<ListViewItem>();
    private ListViewItem lvItem;
    private ControllerDatabase db;
    private Recipes mRecipes;
    private TextView empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        resultListView = (ListView) findViewById(R.id.lvResult);
        empty = (TextView) findViewById(android.R.id.empty);
        db = new ControllerDatabase(this);
        db.open();


        Intent intent = getIntent();
        String search = intent.getStringExtra("Search");
        int searchTime = intent.getIntExtra("SearchTime", 500);
        int searchServing = intent.getIntExtra("SearchServing", 0);
        Cursor cursor = db.getRecipesByTimeServing(search, searchTime, searchServing);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_NAME));
            String image = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_IMAGE));
            String time = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_TIME));
            String serving = cursor.getString(cursor.getColumnIndex(ContractsDatabase.KEY_RECIPES_SERVING));

            lvItem = new ListViewItem(id, image, name, "Thời gian chế biến " + time + " phút", serving + " người dùng");
            mList.add(lvItem);
            cursor.moveToNext();
        }

        if (mList.size() <= 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.INVISIBLE);
            adapter = new ResultListViewAdapter(this, mList);
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
        Cursor c = db.getRecipesById(lt.getId());
        if (c != null && c.moveToFirst()) {
            int idR = c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_ID));
            String name = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_NAME));
            String image = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_IMAGE));
            int time = c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_TIME));
            int serving = c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_SERVING));
            int kcal = c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_KCAL));
            String ingredients = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_INGREDIENTS));
            String instruction = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_INSTRUCTION));
            String code = c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_CODE_YOUTUBE));

            mRecipes = new Recipes(idR, name, image, time, serving, kcal, ingredients, instruction,code);
        }

        Intent intent = new Intent(this, RecipesDetailActivity.class);
        intent.putExtra("Congthuc", mRecipes);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_recipes_result, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
