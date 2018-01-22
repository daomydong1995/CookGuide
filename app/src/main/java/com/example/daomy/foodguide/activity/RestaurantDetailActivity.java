package com.example.daomy.foodguide.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daomy.foodguide.api.APIClient;
import com.example.daomy.foodguide.api.APIService;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import com.example.daomy.foodguide.activity.R;
import com.example.daomy.foodguide.adapter.MoreRestaurantAdapter;
import com.example.daomy.foodguide.model.Restaurant;
import com.example.daomy.foodguide.ultil.ContractsDatabase;
import com.example.daomy.foodguide.ultil.ControllerDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantDetailActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    ImageView avatar;
    TextView tenMonAn;
    TextView diaChi;
    TextView gioMoCua;
    TextView giaCa;
    TextView soDienThoai;
    ImageView banDo, btnSaveContact;
    Restaurant mRestaurant;
    CallbackManager mCallBackManager;
    ShareDialog shareDialog;

    private FacebookCallback<LoginResult> mCallBack;

    Boolean pressed = false;

    List<Restaurant> mList ;
    MoreRestaurantAdapter mMoreRestaurantAdapter;
    TwoWayView mTwoWayView;
    ControllerDatabase db;
    int itemAlready;
    FloatingActionButton mFloatingActionButton;

    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    protected boolean signedInUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail_layout);
        mList= new ArrayList<>();
        db = new ControllerDatabase(this);
        db.open();
        addListRetaurent();
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

        loadComponent();
        Intent it = getIntent();
        mRestaurant = (Restaurant) it.getParcelableExtra("Diadiem");


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        getDetailRestaurant(mRestaurant);

        btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                        Uri.parse("tel:" + mRestaurant.getmPhone()));
                intent.putExtra(ContactsContract.Intents.EXTRA_FORCE_CREATE, true);
                startActivity(intent);
            }
        });

        // Listview horizontal

    }

    private void addListRetaurent() {

        final APIService service = APIClient.getClient().create(APIService.class);
        Call<List<Restaurant>> listCall = service.getRestaurants(5);
        final TwoWayView listView = (TwoWayView) findViewById(R.id.lvItems);
        listCall.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {

                mList = response.body();
                mMoreRestaurantAdapter = new MoreRestaurantAdapter(RestaurantDetailActivity.this, mList);
                listView.setAdapter(mMoreRestaurantAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        getDetailRestaurant(mList.get(position));


                    }
                });
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });

    }

    private void getDetailRestaurant(final Restaurant restaurant) {
        itemAlready = 0;
        tenMonAn.setText(restaurant.getmName());
        diaChi.setText(restaurant.getmAddress());
        gioMoCua.setText(restaurant.getmTime());
        giaCa.setText(restaurant.getmPrice());
        soDienThoai.setText(restaurant.getmPhone());

        getSupportActionBar().setTitle(restaurant.getmName());

        Picasso.with(this)
                .load(restaurant.getmLocation())
                .skipMemoryCache()
                .into(banDo);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(3)
                .cornerRadiusDp(100)
                .oval(false)
                .build();

        Picasso.with(this)
                .load(restaurant.getmImage())
                .skipMemoryCache()
                .fit()
                .transform(transformation)
                .into(avatar);

        //////check item already//////
        Cursor cursor = db.checkFavouriteRestaurantAlready(restaurant.getmId());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            itemAlready = cursor.getInt(cursor.getColumnIndex(ContractsDatabase.KEY_FAVOURITE_ID_RESTAURANT));
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
                    db.addFavouriteRestaurant(restaurant.getmId(), restaurant.getmName(), restaurant.getmImage(), restaurant.getmAddress(), restaurant.getmPrice());
                } else {
                    // Khi bo mon an
                    mFloatingActionButton.setIcon(R.mipmap.ic_action_favorite);
                    db.DelFavouriteRestaurantItem(restaurant.getmId());
                }

            }
        });
    }

    private void loadComponent() {
        tenMonAn = (TextView) findViewById(R.id.textTitle);
        diaChi = (TextView) findViewById(R.id.address);
        gioMoCua = (TextView) findViewById(R.id.textOpenTime);
        giaCa = (TextView) findViewById(R.id.textPrice);
        soDienThoai = (TextView) findViewById(R.id.textPhoneNumber);
        avatar = (ImageView) findViewById(R.id.avatar);
        banDo = (ImageView) findViewById(R.id.map);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.btnFavorite);
        btnSaveContact = (ImageView) findViewById(R.id.btnSaveContact);
    }

    public void buildShareLinkContent() {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=alarm.weather.pdnghiadev.com.weatheralarm"))
                    .setImageUrl(Uri.parse(mRestaurant.getmImage()))
                    .setContentTitle(mRestaurant.getmName())
                    .setContentDescription("Click để xem địa điểm quán ở đâu")
                    .build();
            shareDialog.show(content);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_detail, menu);
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
                    final Dialog dialog = new Dialog(RestaurantDetailActivity.this);
                    dialog.setContentView(R.layout.share_dialog_custom_view);
                    dialog.setTitle("Ban muon dang nhap bang:");
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
                        dialog.dismiss();
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
}
