package com.example.daomy.foodguide.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.ResultListViewAdapter;
import com.example.daomy.foodguide.api.APIClient;
import com.example.daomy.foodguide.api.APIService;
import com.example.daomy.foodguide.model.ListViewItem;
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchRecipesResultActivity extends AppCompatActivity {
    private ListView resultListView;
    private ResultListViewAdapter adapter;
    private List<ListViewItem> mList = new ArrayList<>();
    private ListViewItem lvItem;
    private ControllerDatabase db;
    private Recipes mRecipes;
    private TextView empty;
    private List<Recipes> recipes = new ArrayList<>();

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
        final APIService service = APIClient.getClient().create(APIService.class);
        Call<List<Recipes>> listCall = service.getRecipesByTimeServing(search,searchTime,searchServing);
        listCall.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, final Response<List<Recipes>> response) {
                for(Recipes r:response.body()) {
                    int id = r.getmId();
                    String name = r.getmName();
                    String image = r.getmImage();
                    int time = r.getmTime();
                    int serving = r.getmServing();
                    lvItem = new ListViewItem(id, image, name, "Thời gian chế biến " + time + " phút", serving + " người dùng");
                    Log.d("Stringname",name);
                    mList.add(lvItem);
                    recipes.add(r);
                    }
                    if (mList.size() <= 0) {
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.INVISIBLE);
                        adapter = new ResultListViewAdapter(getApplicationContext(), mList);
                        resultListView.setAdapter(adapter);
                    }




            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {

            }
        });

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RecipesDetailActivity.class);
                intent.putExtra("Congthuc",recipes.get(position));
                startActivity(intent);
            }
        });


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
