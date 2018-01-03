package com.example.daomy.foodguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.ultil.SelectionEnabledEditText;


public class SearchActivity extends AppCompatActivity {

    private ImageView buttonSearchRecipes, buttonSearchRestaurant;
    private SelectionEnabledEditText etSearch;
    private SelectionEnabledEditText etDistrict, etPrice;
    private SelectionEnabledEditText etTime, etServing;

    private LinearLayout mLayoutRecipes, mLayoutRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tìm kiếm");

        buttonSearchRecipes = (ImageView) findViewById(R.id.btnRecipes);
        buttonSearchRestaurant = (ImageView) findViewById(R.id.btnRestaurants);

        etSearch = (SelectionEnabledEditText) findViewById(R.id.editSearch);
        etDistrict = (SelectionEnabledEditText) findViewById(R.id.edtSearchDistrict);
        etPrice = (SelectionEnabledEditText) findViewById(R.id.edtSearchPrice);
        etTime = (SelectionEnabledEditText) findViewById(R.id.edtSearchTime);
        etServing = (SelectionEnabledEditText) findViewById(R.id.edtSearchServing);


        //giới hạn nhập vào editText
        etSearch.setOnSelectionChangeListener(new SelectionEnabledEditText.OnSelectionChangeListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                if (selEnd == 30) {
                    if (etSearch.getText().toString().length() == 30)
                        etSearch.setText("");

                    etSearch.setSelection(0);
                }
            }
        });

        etDistrict.setOnSelectionChangeListener(new SelectionEnabledEditText.OnSelectionChangeListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                if (selEnd == 20) {
                    if (etDistrict.getText().toString().length() == 20)
                        etDistrict.setText("");

                    etDistrict.setSelection(0);
                }
            }
        });

        etPrice.setOnSelectionChangeListener(new SelectionEnabledEditText.OnSelectionChangeListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                if (selEnd == 10) {
                    if (etPrice.getText().toString().length() == 10)
                        etPrice.setText("");

                    etPrice.setSelection(0);
                }
            }
        });

        etTime.setOnSelectionChangeListener(new SelectionEnabledEditText.OnSelectionChangeListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                if (selEnd == 4) {
                    if (etTime.getText().toString().length() == 4)
                        etTime.setText("");

                    etTime.setSelection(0);
                }
            }
        });

        etServing.setOnSelectionChangeListener(new SelectionEnabledEditText.OnSelectionChangeListener() {
            @Override
            public void onSelectionChanged(int selStart, int selEnd) {
                if (selEnd == 3) {
                    if (etServing.getText().toString().length() == 3)
                        etServing.setText("");

                    etServing.setSelection(0);
                }
            }
        });

        mLayoutRecipes = (LinearLayout) findViewById(R.id.layoutRecipes);
        mLayoutRestaurant = (LinearLayout) findViewById(R.id.layoutRestaurant);

        String key = getIntent().getStringExtra("Key");
        if (key.equals("CongThuc")){
            mLayoutRecipes.setVisibility(View.VISIBLE);
            buttonSearchRecipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkEmpty()) {
                        Intent intent = new Intent(SearchActivity.this, SearchRecipesResultActivity.class);
                        intent.putExtra("Search", etSearch.getText().toString());
                        if (checkTimeEmpty()) {
                            intent.putExtra("SearchTime", Integer.parseInt(etTime.getText() + ""));
                        }
                        if (checkServingEmpty()) {
                            intent.putExtra("SearchServing", Integer.parseInt(etServing.getText() + ""));
                        }
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }else {
            mLayoutRestaurant.setVisibility(View.VISIBLE);
            buttonSearchRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkEmpty()) {
                        Intent intent = new Intent(SearchActivity.this, SearchRestaurantResultActivity.class);
                        intent.putExtra("Search", etSearch.getText().toString());
                        intent.putExtra("SearchDistrict", etDistrict.getText().toString());
                        if (checkPriceEmpty()) {
                            intent.putExtra("SearchPrice", Integer.parseInt(etPrice.getText() + ""));
                        }
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkEmpty() {
        if (etSearch.getText().toString().equals("")) {
            Toast.makeText(this, "Vui lòng nhập món cần tìm.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPriceEmpty() {
        if (etPrice.getText().toString().equals("")) {
                        /*Toast.makeText(this, etPrice.getText().toString(), Toast.LENGTH_LONG).show();*/
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTimeEmpty() {
        if (etTime.getText().toString().equals("")) {
                        /*Toast.makeText(this, etPrice.getText().toString(), Toast.LENGTH_LONG).show();*/
            return false;
        } else {
            return true;
        }
    }

    public boolean checkServingEmpty() {
        Intent intent = new Intent();
        if (etServing.getText().toString().equals("")) {
            intent.putExtra("SearchPrice", 0);
            return false;
        } else {
            return true;
        }
    }
}
