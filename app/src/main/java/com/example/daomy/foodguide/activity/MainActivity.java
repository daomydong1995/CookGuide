package com.example.daomy.foodguide.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.daomy.foodguide.api.APIClient;
import com.example.daomy.foodguide.api.APIService;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.MainAdapter;
import com.example.daomy.foodguide.model.Categories;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private LinearLayout mMessage;
    APIService service = APIClient.getClient().create(APIService.class);
    private ControllerDatabase db;
    private Categories mCategories;
    private List<Categories> mListCategories = new ArrayList<>();
    private MainAdapter mAdapter;

    private String day;
    private ImageView mFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ControllerDatabase(this);
        db.open();

        loadComponents();
        getCurrentDay();
        checkFirstLaunchApp();
        functionButton();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCategories = mListCategories.get(position);
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra("IDCategory", mCategories.getIDCategory());
                intent.putExtra("NameCategory", mCategories.getName());
                startActivity(intent);
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.admin.testfacebook", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void functionButton() {
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Thông Báo!");
                alert.setMessage("Lựa chọn danh sách món yêu thích của bạn:");
                alert.setNegativeButton("CÔNG THỨC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, FavouriteRecipesActivity.class);
                        intent.putExtra("Key", "CongThuc");
                        startActivity(intent);

                    }
                });
                alert.setPositiveButton("ĐỊA ĐIỂM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, FavouriteRestaurantActivity.class);
                        intent.putExtra("Key", "DiaDiem");
                        startActivity(intent);

                    }
                });

                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    private void checkFirstLaunchApp() {
        final String HAS_RUN_BEFORE = "hasRunBefore";
        final String HAS_RUN = "hasRun";

        SharedPreferences runCheck = getSharedPreferences(HAS_RUN_BEFORE, 0); //load the preferences
        Boolean hasRun = runCheck.getBoolean(HAS_RUN, false); //see if it's run before, default no
        if (!hasRun) {
            SharedPreferences settings = getSharedPreferences(HAS_RUN_BEFORE, 0);
            SharedPreferences.Editor edit = settings.edit();
            edit.putBoolean(HAS_RUN, true); //set to has run
            edit.apply(); //apply


            //code for if this is the first time the app has run


            if (isNetworkAvailabel()) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                mFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.favorite1));
                LoadingData();
            }


        } else {
            //code if the app HAS run before
            mFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.favorite1));
                LoadingData();
        }
    }

    @SuppressLint("WrongViewCast")
    private void loadComponents() {
        mListView = (ListView) findViewById(R.id.gvMain);
        mMessage = (LinearLayout) findViewById(android.R.id.empty);
        mFavorite = (ImageView) findViewById(R.id.action_favorite);
    }

    private void getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay <= 10) {
            day = "s";
        } else if (hourOfDay <= 14) {
            day = "tr";
        } else {
            day = "t";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // Check for connect network
    private boolean isNetworkAvailabel() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailabel = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailabel = true;
        }

        return isAvailabel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Thông Báo!");
                alert.setMessage("Vui lòng lựa chọn kiểu bạn muốn tìm kiếm.");
                alert.setNegativeButton("CÔNG THỨC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("Key", "CongThuc");
                        startActivity(intent);

                    }
                });
                alert.setPositiveButton("ĐỊA ĐIỂM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra("Key", "DiaDiem");
                        startActivity(intent);
                    }
                });

                AlertDialog dialog = alert.create();
                dialog.show();
                return true;
            case R.id.action_calendar:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                return true;
            case R.id.action_help:
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void LoadingData()  {
       Call<List<Categories>> listCall = service.getCateByDay(day);
       listCall.enqueue(new Callback<List<Categories>>() {
           @Override
           public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
               mMessage.setVisibility(View.INVISIBLE);
               mListCategories = response.body();
               mAdapter = new MainAdapter(MainActivity.this,mListCategories);
               mListView.setAdapter(mAdapter);

           }

           @Override
           public void onFailure(Call<List<Categories>> call, Throwable t) {

           }
       });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thoát").setMessage("Bạn có muốn thoát ứng dụng?")
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}

