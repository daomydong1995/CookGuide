package com.example.daomy.foodguide.ultil;

/**
 * Created by PDNghiaDev on 4/15/2015.
 */
public class ContractsDatabase {

    // -------------Table Recipes---------------
    public static final String TABLE_RECIPES = "Recipes";
    // Recipes columns names

    public static final String KEY_RECIPES_ID = "idRecipes";
    public static final String KEY_RECIPES_NAME = "name";
    public static final String KEY_RECIPES_TAGNAME = "tagName";
    public static final String KEY_RECIPES_IMAGE = "image";
    public static final String KEY_RECIPES_TIME = "time";
    public static final String KEY_RECIPES_SERVING = "serving";
    public static final String KEY_RECIPES_KCAL = "kcal";
    public static final String KEY_RECIPES_INGREDIENTS = "ingredients";
    public static final String KEY_RECIPES_INSTRUCTION = "instruction";
    public static final String KEY_RECIPES_ID_CATEGORY = "idCategory";
    public static final String KEY_RECIPES_CODE_YOUTUBE = "codeYoutube";
    // ------------Table Restaurant-------------
    public static final String TABLE_RESTAURANT = "Restaurant";
    // Restaurant columns names
    public static final String KEY_RESTAURANT_ID = "idRestaurant";
    public static final String KEY_RESTAURANT_NAME = "name";
    public static final String KEY_RESTAURANT_IMAGE = "image";
    public static final String KEY_RESTAURANT_ADDRESS = "address";
    public static final String KEY_RESTAURANT_PRICE = "price";
    public static final String KEY_RESTAURANT_PHONE = "phone";
    public static final String KEY_RESTAURANT_DISTRICT = "district";
    public static final String KEY_RESTAURANT_LOCATION = "location";
    public static final String KEY_RESTAURANT_ID_CATEGORY = "idCategory";
    public static final String KEY_RESTAURANT_TIME = "time";
    public static final String KEY_RESTAURANT_MINPRICE = "minPrice";
    public static final String KEY_RESTAURANT_MAXPRICE = "maxPrice";
    public static final String KEY_RESTAURANT_TAGNAME = "tagName";
    public static final String KEY_RESTAURANT_TAGDISTRICT = "tagDistrict";

    // ----------Table Categories-------------
    public static final String TABLE_CATEGORIES = "Categories";
    // Categories columns names
    public static final String KEY_CATEGORY_ID = "idCategory";
    public static final String KEY_CATEGORY_NAME = "name";
    public static final String KEY_CATEGORY_IMAGE = "image";

    // -----------Table Day---------------
    public static final String TABLE_DAY = "Day";
    // Day columns names
    public static final String KEY_DAY_ID = "idDay";
    public static final String KEY_DAY_NAME = "name";

    // -----------Table Linked------------
    public static final String TABLE_LINKED = "Linked";
    // Linked columns names
    public static final String KEY_LINKED_ID_DAY = "idDay";
    public static final String KEY_LINKED_ID_CATEGORY = "idCategory";


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

}
