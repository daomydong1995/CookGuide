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

import com.example.daomy.foodguide.api.APIClient;
import com.example.daomy.foodguide.api.APIService;
import com.github.amlcurran.showcaseview.ApiUtils;
import com.github.amlcurran.showcaseview.ShowcaseView;

import java.util.ArrayList;
import java.util.List;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.RecipesAdapter;
import com.example.daomy.foodguide.adapter.RestaurantAdapter;
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.model.Restaurant;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryActivity extends AppCompatActivity {
    private GridView mGridView;
    private List<Recipes> mListRecipes = new ArrayList<Recipes>();
    private Recipes mRecipes;
    private RecipesAdapter mAdapterRecipes;
    private ControllerDatabase db;
    private String IDCategory;
    private List<Restaurant> mListRestaurant = new ArrayList<>();
    private Restaurant mRestaurant;
    private RestaurantAdapter mAdapterRestaurant;
    private TextView mMessage;
    private final ApiUtils apiUtils = new ApiUtils();
    APIService service = APIClient.getClient().create(APIService.class);

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
        findViewById(R.id.btnRecipes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListRecipes.clear();
                getRecipes();
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mGridView.removeAllViews();
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

            Call<List<Restaurant>> listCall = service.getRestaurantCa(IDCategory);
            listCall.enqueue(new Callback<List<Restaurant>>() {
                @Override
                public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                        mListRestaurant = response.body();
                        mMessage.setVisibility(View.INVISIBLE);
                        mAdapterRestaurant = new RestaurantAdapter(CategoryActivity.this, mListRestaurant);
                        mGridView.setAdapter(mAdapterRestaurant);
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

                @Override
                public void onFailure(Call<List<Restaurant>> call, Throwable t) {

                }
            });

    }

    private void getRecipes() {
            Call<List<Recipes>> listCall = service.getRecipesByCategory(IDCategory);
            listCall.enqueue(new Callback<List<Recipes>>() {
                @Override
                public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                    mListRecipes =response.body();
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

                @Override
                public void onFailure(Call<List<Recipes>> call, Throwable t) {

                }
            });

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
