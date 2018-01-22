package com.example.daomy.foodguide.ultil;

/**
 * Created by PDNghiaDev on 4/15/2015.
 */
public class ContractsDatabase {

    public static final String TABLE_RECIPES = "recipes";
    // Recipes columns names

    public static final String KEY_RECIPES_ID = "id_recipes";
    public static final String KEY_RECIPES_NAME = "name";
    public static final String KEY_RECIPES_TAGNAME = "tagName";
    public static final String KEY_RECIPES_IMAGE = "image";
    public static final String KEY_RECIPES_TIME = "time";
    public static final String KEY_RECIPES_SERVING = "serving";
    public static final String KEY_RECIPES_KCAL = "kcal";
    public static final String KEY_RECIPES_INGREDIENTS = "ingredients";
    public static final String KEY_RECIPES_INSTRUCTION = "instruction";
    public static final String KEY_RECIPES_ID_CATEGORY = "id_category";
    public static final String KEY_RECIPES_CODE_YOUTUBE = "code_youtube";
    // ------------Table Restaurant-------------
    public static final String TABLE_RESTAURANT = "restaurant";
    // Restaurant columns names
    public static final String KEY_RESTAURANT_ID = "id_restaurant";
    public static final String KEY_RESTAURANT_NAME = "name";
    public static final String KEY_RESTAURANT_IMAGE = "image";
    public static final String KEY_RESTAURANT_ADDRESS = "address";
    public static final String KEY_RESTAURANT_PRICE = "price";
    public static final String KEY_RESTAURANT_PHONE = "phone";
    public static final String KEY_RESTAURANT_DISTRICT = "district";
    public static final String KEY_RESTAURANT_LOCATION = "location";
    public static final String KEY_RESTAURANT_ID_CATEGORY = "id_category";
    public static final String KEY_RESTAURANT_TIME = "time";
    public static final String KEY_RESTAURANT_MINPRICE = "min_price";
    public static final String KEY_RESTAURANT_MAXPRICE = "max_price";
    public static final String KEY_RESTAURANT_TAGNAME = "tag_name";
    public static final String KEY_RESTAURANT_TAGDISTRICT = "tag_district";

    // ----------Table Categories-------------
    public static final String TABLE_CATEGORIES = "categories";
    // Categories columns names
    public static final String KEY_CATEGORY_ID = "id_category";
    public static final String KEY_CATEGORY_NAME = "name";
    public static final String KEY_CATEGORY_IMAGE = "image";

    // -----------Table Day---------------
    public static final String TABLE_DAY = "day";
    // Day columns names
    public static final String KEY_DAY_ID = "id_day";
    public static final String KEY_DAY_NAME = "name";

    // -----------Table Linked------------
    public static final String TABLE_DAY_CATEGORIES = "day_categories";
    // Linked columns names
    public static final String KEY_LINKED_ID_DAY = "id_day";
    public static final String KEY_LINKED_ID_CATEGORY = "id_category";


    //-----------Table Calendar---------------
    public static final String TABLE_CALENDAR = "Calendar";
    public static final String KEY_CALENDAR_ID_CATEGORY = "idRecipes";
    public static final String KEY_CALENDAR_NAME = "name";
    public static final String KEY_CALENDAR_DAY = "day";
    public static final String KEY_CALENDAR_MONTH = "month";
    public static final String KEY_CALENDAR_YEAR = "year";
    public static final String KEY_CALENDAR_HOURS = "hours";
    public static final String KEY_CALENDAR_MINUTE = "minute";
    public static final String KEY_CALENDAR_END_TIME = "endTime";
    public static final String KEY_CALENDAR_ID = "id";

    //-----------Table Favourite---------------
    public static final String TABLE_FAVOURITE_RESTAURANT = "FavouriteRestaurant";
    public static final String KEY_FAVOURITE_ID_RESTAURANT = "idRestaurant";
    public static final String KEY_FAVOURITE_NAME = "name";
    public static final String KEY_FAVOURITE_IMAGE = "image";
    public static final String KEY_FAVOURITE_ADDRESS = "address";
    public static final String KEY_FAVOURITE_PRICE = "price";
    public static final String TABLE_FAVOURITE_RECIPES = "FavouriteRecipes";
    public static final String KEY_FAVOURITE_ID_RECIPES = "idRecipes";
    public static final String KEY_FAVOURITE_TIME = "time";
    public static final String KEY_FAVOURITE_SERVING = "serving";

    //DataJson
    public static final String HOST =" http://192.168.2.102" ;
    public static final String POST =  ":8080/";
    public static final String GET_CATEGORIES_BY_DAY = "/categories/getbyday";
}
