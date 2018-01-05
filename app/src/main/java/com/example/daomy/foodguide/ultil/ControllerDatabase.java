package com.example.daomy.foodguide.ultil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.daomy.foodguide.api.APIClient;
import com.example.daomy.foodguide.api.APIService;
import com.example.daomy.foodguide.model.Categories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PDNghiaDev on 4/15/2015.
 */
public class ControllerDatabase {
    private Context mContext;
    private Database mDatabase;
    private SQLiteDatabase mSQLiteDatabase;
    List<Categories> categories;
    APIService service ;
    public ControllerDatabase(Context context) {
        mContext = context;
    }

    public ControllerDatabase open() {
        mDatabase = new Database(mContext);
        categories = new ArrayList<>();
        service = APIClient.getClient().create(APIService.class);
        try {
            mDatabase.CopyDataBaseFromAsset();
            //mDatabase.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();
       // } catch (SQLException e) {
         //   e.printStackTrace();
        }
        mSQLiteDatabase = mDatabase.getWritableDatabase();
        return null;
    }

    public void close() {
        mDatabase.close();
    }

    public List<Categories> getCategoriesByDay(String day) {
        Call<List<Categories>> listCall = service.getCateByDay(day);
        listCall.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if(response.code() == 200){
                    categories = response.body();
                    for(Categories c:categories)
                        Log.d("Requas",c.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });
        for(Categories c:categories)
            Log.d("Requas",c.getName());
        return categories;
    }

    // Lấy danh sách công thức món ăn theo món ăn
    public Cursor getRecipesByCategory(String category) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RECIPES_ID + ", "
                + ContractsDatabase.KEY_RECIPES_NAME + ", "
                + ContractsDatabase.KEY_RECIPES_IMAGE + ", "
                + ContractsDatabase.KEY_RECIPES_TIME + ", "
                + ContractsDatabase.KEY_RECIPES_SERVING + ", "
                + ContractsDatabase.KEY_RECIPES_KCAL + ", "
                + ContractsDatabase.KEY_RECIPES_INGREDIENTS + ", "
                + ContractsDatabase.KEY_RECIPES_INSTRUCTION+ ", "
                + ContractsDatabase.KEY_RECIPES_CODE_YOUTUBE
                + " FROM " + ContractsDatabase.TABLE_RECIPES
                + " WHERE " + ContractsDatabase.KEY_RECIPES_ID_CATEGORY + " = \"" + category + "\"";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    // Lấy danh sách địa điểm quán ăn theo món ăn
    public Cursor getRestaurantByCategory(String category) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RESTAURANT_ID + ", "
                + ContractsDatabase.KEY_RESTAURANT_NAME + ", "
                + ContractsDatabase.KEY_RESTAURANT_IMAGE + ", "
                + ContractsDatabase.KEY_RESTAURANT_ADDRESS + ", "
                + ContractsDatabase.KEY_RESTAURANT_PRICE + ", "
                + ContractsDatabase.KEY_RESTAURANT_PHONE + ", "
                + ContractsDatabase.KEY_RESTAURANT_DISTRICT + ", "
                + ContractsDatabase.KEY_RESTAURANT_LOCATION + ", "
                + ContractsDatabase.KEY_RESTAURANT_TIME
                + " FROM " + ContractsDatabase.TABLE_RESTAURANT
                + " WHERE " + ContractsDatabase.KEY_RESTAURANT_ID_CATEGORY + " = \"" + category + "\"";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    // Lấy danh sách công thức tìm kiếm theo tên
    public Cursor getRecipesByName(String name) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RECIPES_ID + ", "
                + ContractsDatabase.KEY_RECIPES_NAME + ", "
                + ContractsDatabase.KEY_RECIPES_IMAGE + ", "
                + ContractsDatabase.KEY_RECIPES_TIME + ", "
                + ContractsDatabase.KEY_RECIPES_SERVING + ", "
                + ContractsDatabase.KEY_RECIPES_KCAL + ", "
                + ContractsDatabase.KEY_RECIPES_INGREDIENTS + ", "
                + ContractsDatabase.KEY_RECIPES_INSTRUCTION
                + " FROM " + ContractsDatabase.TABLE_RECIPES
                + " WHERE " + ContractsDatabase.KEY_RECIPES_NAME
                + " LIKE \"%" + name + "%\"";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    // Lấy danh sách địa điểm tìm kiếm theo tên
    public Cursor getRestaurantByName(String name) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RESTAURANT_ID + ", "
                + ContractsDatabase.KEY_RESTAURANT_NAME + ", "
                + ContractsDatabase.KEY_RESTAURANT_IMAGE + ", "
                + ContractsDatabase.KEY_RESTAURANT_ADDRESS + ", "
                + ContractsDatabase.KEY_RESTAURANT_PRICE + ", "
                + ContractsDatabase.KEY_RESTAURANT_PHONE + ", "
                + ContractsDatabase.KEY_RESTAURANT_DISTRICT + ", "
                + ContractsDatabase.KEY_RESTAURANT_LOCATION + ", "
                + ContractsDatabase.KEY_RESTAURANT_TIME
                + " FROM " + ContractsDatabase.TABLE_RESTAURANT
                + " WHERE " + ContractsDatabase.KEY_RESTAURANT_NAME
                + " LIKE \"%" + name + "%\"";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getRecipesById(int id) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RECIPES_ID + ", "
                + ContractsDatabase.KEY_RECIPES_NAME + ", "
                + ContractsDatabase.KEY_RECIPES_IMAGE + ", "
                + ContractsDatabase.KEY_RECIPES_TIME + ", "
                + ContractsDatabase.KEY_RECIPES_SERVING + ", "
                + ContractsDatabase.KEY_RECIPES_KCAL + ", "
                + ContractsDatabase.KEY_RECIPES_INGREDIENTS + ", "
                + ContractsDatabase.KEY_RECIPES_INSTRUCTION
                + " FROM " + ContractsDatabase.TABLE_RECIPES
                + " WHERE " + ContractsDatabase.KEY_RECIPES_ID + " = " + id;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }
    public Cursor getRestaurantById(int id) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RESTAURANT_ID + ", "
                + ContractsDatabase.KEY_RESTAURANT_NAME + ", "
                + ContractsDatabase.KEY_RESTAURANT_IMAGE + ", "
                + ContractsDatabase.KEY_RESTAURANT_ADDRESS + ", "
                + ContractsDatabase.KEY_RESTAURANT_PRICE + ", "
                + ContractsDatabase.KEY_RESTAURANT_PHONE + ", "
                + ContractsDatabase.KEY_RESTAURANT_DISTRICT + ", "
                + ContractsDatabase.KEY_RESTAURANT_LOCATION + ", "
                + ContractsDatabase.KEY_RESTAURANT_TIME
                + " FROM " + ContractsDatabase.TABLE_RESTAURANT
                + " WHERE " + ContractsDatabase.KEY_RESTAURANT_ID + " = " + id;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getRestaurantByNameDistrictPrice(String name, String district, int price) {
        String sql = "SELECT *"
                + " FROM " + ContractsDatabase.TABLE_RESTAURANT
                + " WHERE " + ContractsDatabase.KEY_RESTAURANT_TAGNAME
                + " LIKE \"%" + name + "%\""
                + " AND " + ContractsDatabase.KEY_RESTAURANT_TAGDISTRICT
                + " LIKE \"%" + district + "%\""
                                /*+ " AND " + ContractsDatabase.KEY_RESTAURANT_MINPRICE
+                + " <= " + price*/
                + " AND " + ContractsDatabase.KEY_RESTAURANT_MAXPRICE
                + " >= " + price;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getRecipesByTimeServing(String name, int time, int serving) {
        String sql;
        if (serving == 0) {
            String sql1 = "SELECT *"
                    + " FROM " + ContractsDatabase.TABLE_RECIPES
                    + " WHERE " + ContractsDatabase.KEY_RECIPES_TAGNAME
                    + " LIKE \"%" + name + "%\""
                    + " AND " + ContractsDatabase.KEY_RECIPES_TIME
                    + " <= " + time
                                        /*+ " AND " + ContractsDatabase.KEY_RECIPES_SERVING
+                    + " <= " + serving*/;
            sql = sql1;
        } else {
            String sql2 = "SELECT *"
                    + " FROM " + ContractsDatabase.TABLE_RECIPES
                    + " WHERE " + ContractsDatabase.KEY_RECIPES_TAGNAME
                    + " LIKE \"%" + name + "%\""
                    + " AND " + ContractsDatabase.KEY_RECIPES_TIME
                    + " <= " + time
                    + " AND " + ContractsDatabase.KEY_RECIPES_SERVING
                    + " = " + serving;
            sql = sql2;
        }
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getListRecipesToCalendar(){
        String sql = "SELECT * "
                + " FROM " + ContractsDatabase.TABLE_CALENDAR;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getListRecipes(){
        String sql = "SELECT * "
                + " FROM " + ContractsDatabase.TABLE_RECIPES + " ORDER BY RANDOM() LIMIT 4";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getListRestaurant(){
        String sql = "SELECT * "
                + " FROM " + ContractsDatabase.TABLE_RESTAURANT + " ORDER BY RANDOM() LIMIT 4";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public long insertRecipesToCalendar(com.example.daomy.foodguide.model.Calendar calendar){
        try {
            ContentValues values = new ContentValues();
            values.put(ContractsDatabase.KEY_CALENDAR_ID, calendar.getId());
            values.put(ContractsDatabase.KEY_CALENDAR_NAME, calendar.getName());
            values.put(ContractsDatabase.KEY_CALENDAR_DAY, calendar.getDay());
            values.put(ContractsDatabase.KEY_CALENDAR_MONTH, calendar.getMonth());
            values.put(ContractsDatabase.KEY_CALENDAR_YEAR, calendar.getYear());
            values.put(ContractsDatabase.KEY_CALENDAR_HOURS, calendar.getHours());
            values.put(ContractsDatabase.KEY_CALENDAR_MINUTE, calendar.getMinute());
            values.put(ContractsDatabase.KEY_CALENDAR_END_TIME, calendar.getEndTime());
            long r = mSQLiteDatabase.insert(ContractsDatabase.TABLE_CALENDAR, null, values);
            mSQLiteDatabase.close();
            return r;
        }catch (Exception e){
            return -1;
        }
    }

    public void deleteCalendar(int id){
        mSQLiteDatabase.delete(ContractsDatabase.TABLE_CALENDAR, ContractsDatabase.KEY_CALENDAR_ID + " = " + id, null);
    }

    public void DelFavouriteRestaurantItem(int CurrentItem){
        mSQLiteDatabase.delete(ContractsDatabase.TABLE_FAVOURITE_RESTAURANT, ContractsDatabase.KEY_FAVOURITE_ID_RESTAURANT + " = " + CurrentItem, null);
    }

    public void addFavouriteRestaurant(int idRestaurant, String name, String image, String address, String price) {
        ContentValues values = new ContentValues();
        values.put(ContractsDatabase.KEY_FAVOURITE_ID_RESTAURANT, idRestaurant);
        values.put(ContractsDatabase.KEY_FAVOURITE_NAME, name);
        values.put(ContractsDatabase.KEY_FAVOURITE_IMAGE, image);
        values.put(ContractsDatabase.KEY_FAVOURITE_ADDRESS, address);
        values.put(ContractsDatabase.KEY_FAVOURITE_PRICE, price);
        String msg = "";
        if(mSQLiteDatabase.insert(ContractsDatabase.TABLE_FAVOURITE_RESTAURANT, null, values) == -1){
            msg = "Fail to add to Favourite!";
        }else{
            msg = "Add in Favourite Success!";
        }
        Log.i("alert", msg);
    }

    public Cursor getAllFavouriteRestaurant() {
        String sql = "SELECT *"
                + " FROM " + ContractsDatabase.TABLE_FAVOURITE_RESTAURANT;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public void addFavouriteRecipes(int idRecipes, String name, String image, String time, String serving) {
        ContentValues values = new ContentValues();
        values.put(ContractsDatabase.KEY_FAVOURITE_ID_RECIPES, idRecipes);
        values.put(ContractsDatabase.KEY_FAVOURITE_NAME, name);
        values.put(ContractsDatabase.KEY_FAVOURITE_IMAGE, image);
        values.put(ContractsDatabase.KEY_FAVOURITE_TIME, time);
        values.put(ContractsDatabase.KEY_FAVOURITE_SERVING, serving);
        String msg = "";
        if(mSQLiteDatabase.insert(ContractsDatabase.TABLE_FAVOURITE_RECIPES, null, values) == -1){
            msg = "Fail to add to Favourite!";
        }else{
            msg = "Add in Favourite Success!";
        }
        Log.i("alert", msg);
    }

    public void DelFavouriteRecipesItem(int CurrentItem){
        mSQLiteDatabase.delete(ContractsDatabase.TABLE_FAVOURITE_RECIPES, ContractsDatabase.KEY_FAVOURITE_ID_RECIPES + " = " + CurrentItem, null );
    }

    public Cursor getAllFavouriteRecipes() {
        String sql = "SELECT *"
                + " FROM " + ContractsDatabase.TABLE_FAVOURITE_RECIPES;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getFavouriteRestaurantById(int id) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RESTAURANT_ID + ", "
                + ContractsDatabase.KEY_RESTAURANT_NAME + ", "
                + ContractsDatabase.KEY_RESTAURANT_IMAGE + ", "
                + ContractsDatabase.KEY_RESTAURANT_ADDRESS + ", "
                + ContractsDatabase.KEY_RESTAURANT_PRICE + ", "
                + ContractsDatabase.KEY_RESTAURANT_PHONE + ", "
                + ContractsDatabase.KEY_RESTAURANT_DISTRICT + ", "
                + ContractsDatabase.KEY_RESTAURANT_LOCATION + ", "
                + ContractsDatabase.KEY_RESTAURANT_TIME
                + " FROM " + ContractsDatabase.TABLE_RESTAURANT
                + " WHERE " + ContractsDatabase.KEY_RESTAURANT_ID + " = " + id;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getFavouriteRecipesById(int id) {
        String sql = "SELECT "
                + ContractsDatabase.KEY_RECIPES_ID + ", "
                + ContractsDatabase.KEY_RECIPES_NAME + ", "
                + ContractsDatabase.KEY_RECIPES_IMAGE + ", "
                + ContractsDatabase.KEY_RECIPES_TIME + ", "
                + ContractsDatabase.KEY_RECIPES_SERVING + ", "
                + ContractsDatabase.KEY_RECIPES_KCAL + ", "
                + ContractsDatabase.KEY_RECIPES_INGREDIENTS + ", "
                + ContractsDatabase.KEY_RECIPES_INSTRUCTION
                + " FROM " + ContractsDatabase.TABLE_RECIPES
                + " WHERE " + ContractsDatabase.KEY_RECIPES_ID + " = " + id;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    /////////////////check item already/////////////////////////
    public Cursor checkFavouriteRestaurantAlready(int id) {
        String sql = "SELECT *"
                + " FROM " + ContractsDatabase.TABLE_FAVOURITE_RESTAURANT
                + " WHERE " + ContractsDatabase.KEY_FAVOURITE_ID_RESTAURANT + " = " + id;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    public Cursor checkFavouriteRecipesAlready(int id) {
        String sql = "SELECT *"
                + " FROM " + ContractsDatabase.TABLE_FAVOURITE_RECIPES
                + " WHERE " + ContractsDatabase.KEY_FAVOURITE_ID_RECIPES + " = " + id;
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

}
