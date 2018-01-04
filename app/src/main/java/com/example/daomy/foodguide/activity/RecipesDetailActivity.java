package com.example.daomy.foodguide.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.common.api.zze;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.lucasr.twowayview.TwoWayView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.MoreRecipesAdapter;
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;


public class RecipesDetailActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    Recipes mRecipes, mRecipesMore;
    String ingredients, cacBuocThucHien,codeYoutube="";
    ImageView imageOnTop, btnCalendar,imgPlay;
    TextView tvTime, tvServing, tvKcal;

    LinearLayout layoutNguyenLieu, layoutHuongDan;
    com.example.daomy.foodguide.model.Calendar mCalendar;
    ControllerDatabase db;
    Boolean pressed = false;

    private FacebookCallback<LoginResult> mCallBack;
    ShareDialog shareDialog;
    CallbackManager mCallBackManager;

    ArrayList<Recipes> mList = new ArrayList<>();
    MoreRecipesAdapter mMoreRecipesAdapter;
    TwoWayView mTwoWayView;
    FloatingActionButton mFloatingActionButton;

    int itemAlready = 0;

    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    protected boolean signedInUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_detail_layout);

        db = new ControllerDatabase(this);
        db.open();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallBackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {

            }
        };

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        Intent it = getIntent();
        mRecipes = (Recipes) it.getSerializableExtra("Congthuc");

        loadComponent();


        //////check item already//////
//        Cursor cursor = db.checkFavouriteRecipesAlready(mRecipes.getId());
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            itemAlready = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_ID_RECIPES));
//            cursor.moveToNext();
//        }
//
//        if (itemAlready != 0){
//            pressed = true;
//            mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite_pressed);
//        }

        getDetailRecipes(mRecipes);



        // Listview horizontal
        TwoWayView listView = (TwoWayView) findViewById(R.id.lvItems);
        Cursor c = db.getListRecipes();
        c.moveToFirst();
        while (!c.isAfterLast()){
            mRecipesMore = new Recipes();
            mRecipesMore.setId(c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_ID)));
            mRecipesMore.setImage(c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_IMAGE)));
            mRecipesMore.setName(c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_NAME)));
            mRecipesMore.setTime(c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_TIME)));
            mRecipesMore.setServing(c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_SERVING)));
            mRecipesMore.setKcal(c.getInt(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_KCAL)));
            mRecipesMore.setIngredients(c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_INGREDIENTS)));
            mRecipesMore.setInstruction(c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_INSTRUCTION)));
            mRecipesMore.setmCodeVideo(c.getString(c.getColumnIndex(ContractsDatabase.KEY_RECIPES_CODE_YOUTUBE)));
            mList.add(mRecipesMore);
            c.moveToNext();
        }
        mMoreRecipesAdapter = new MoreRecipesAdapter(RecipesDetailActivity.this, mList);
        listView.setAdapter(mMoreRecipesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite);
                mRecipesMore = mList.get(position);
                getDetailRecipes(mRecipesMore);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int idCalendar = mRecipes.getId();
                String nameCalendar = mRecipes.getName();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                int endTime = mRecipes.getTime();
                mCalendar = new com.example.daomy.foodguide.model.Calendar(idCalendar, nameCalendar, day, month, year, hours, minute, endTime);
                long save = db.insertRecipesToCalendar(mCalendar);
                if (save <= 0) {
                    Toast.makeText(RecipesDetailActivity.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecipesDetailActivity.this, "Bạn đã thêm thành công món ăn vào lịch.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(YouTubeStandalonePlayer.createVideoIntent(RecipesDetailActivity.this,
                        R.string.DEVELOPER_KEY+"",codeYoutube, 0, true, true));
            }
        });

    }

    private void loadComponent() {
        layoutNguyenLieu = (LinearLayout) findViewById(R.id.NguyenLieuLayout);
        layoutHuongDan = (LinearLayout) findViewById(R.id.HuongDanLayout);
        imageOnTop = (ImageView) findViewById(R.id.imageRecipes);
        imgPlay = (ImageView) findViewById(R.id.imagePlay);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvServing = (TextView) findViewById(R.id.tvServing);
        tvKcal = (TextView) findViewById(R.id.tvKcal);
        btnCalendar = (ImageView) findViewById(R.id.btnCalendar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.btnFavorite);
    }

    private void getDetailRecipes(final Recipes recipes) {
        itemAlready = 0;
        ingredients = recipes.getIngredients();
        cacBuocThucHien = recipes.getInstruction();
        tvTime.setText(getFormatTime(recipes.getTime()));
        tvServing.setText(recipes.getServing() + " người");
        tvKcal.setText(recipes.getKcal() + " kcal");
        codeYoutube = recipes.getmCodeVideo()+"";
        getSupportActionBar().setTitle(recipes.getName());


        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(3)
                .cornerRadiusDp(100)
                .oval(false)
                .build();

        Picasso.with(RecipesDetailActivity.this)
                .load(recipes.getImage())
                .fit()
                .transform(transformation)
                .into(imageOnTop);



        String a[] = ingredients.split(",");
        for (int i = 0; i < a.length; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 0, 0, 0);
            CheckBox cb = new CheckBox(RecipesDetailActivity.this);
            cb.setText(a[i]);
            cb.setLayoutParams(lp);
            cb.setTextColor(getResources().getColor(R.color.tv_recipes));
            layoutNguyenLieu.addView(cb);
        }


        String b[] = cacBuocThucHien.split("#");
        for (int j = 0; j < b.length; j++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 0, 10, 0);
            TextView tv = new TextView(RecipesDetailActivity.this);
            tv.setLayoutParams(lp);
            tv.setText(b[j] + "\n");
            tv.setTextColor(getResources().getColor(R.color.tv_recipes));
            layoutHuongDan.addView(tv);
        }

        Cursor cursor = db.checkFavouriteRecipesAlready(recipes.getId());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            itemAlready = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_ID_RECIPES));
            cursor.moveToNext();
        }

        if (itemAlready != 0){
            mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite_pressed);
        }else {
            mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite);
        }

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed = !pressed;
                if (pressed) {
                    // Khi them mon an
                    mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite_pressed);
                    db.addFavouriteRecipes(recipes.getId(), recipes.getName(), recipes.getImage(), String.valueOf(recipes.getTime()), String.valueOf(recipes.getServing()));
                }else {
                    // Khi bo mon an
                    mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite);
                    db.DelFavouriteRecipesItem(recipes.getId());
                }

            }
        });
    }

    public void buildShareLinkContent() {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://www.facebook.com/daomydong1995"))
                    .setImageUrl(Uri.parse(mRecipes.getImage()))
                    .setContentTitle(mRecipes.getName())
                    .setContentDescription("Click để xem công thức làm món " + mRecipes.getName())
                    .build();
            shareDialog.show(content);
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            //Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            //String personName = currentPerson.getDisplayName();
            Intent shareIntent = new PlusShare.Builder(this)
                    .setType("text/plain")
                    .setText("Welcome to the Google+ platform.")
                    .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                    .getIntent();

            startActivityForResult(shareIntent, 0);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {

                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.

                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RC_SIGN_IN:
                if(resultCode == RESULT_OK){
                    signedInUser = false;
                }
                mIntentInProgress = false;
                if(!mGoogleApiClient.isConnecting()){
                    mGoogleApiClient.connect();
                }
                break;
        }
    }


    public String getFormatTime(int time) {
        String t = "";
        if (time < 60) {
            t = time + " phút";
        } else {
            int hours, minutes;
            hours = time / 60;
            minutes = time % 60;
            if (minutes == 0) {
                t = hours + " giờ";
            } else {
                t = hours + " giờ " + minutes + " phút";
            }
        }

        return t;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipes_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token == null) {
                    final Dialog dialog = new Dialog(RecipesDetailActivity.this);
                    dialog.setContentView(R.layout.share_dialog_custom_view);
                    dialog.setTitle("Login: ");
                    dialog.show();

                    LoginButton loginButton = (LoginButton) dialog.findViewById(R.id.login_button);
                    loginButton.setReadPermissions("user_friends");
                    loginButton.registerCallback(mCallBackManager, mCallBack);

                    SignInButton signInButton = (SignInButton) dialog.findViewById(R.id.signin_button_google);
                    signInButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!mGoogleApiClient.isConnecting()) {
                                dialog.cancel();
                                signedInUser = true;
                                mGoogleApiClient.connect();
                            }
                        }
                    });

                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        buildShareLinkContent();
                    }
                }
                if (token != null) {
                    buildShareLinkContent();
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
