package com.example.daomy.foodguide.api;


import com.example.daomy.foodguide.model.Categories;
import com.example.daomy.foodguide.model.Recipes;
import com.example.daomy.foodguide.model.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 4/6/2017.
 */

public interface APIService {

    @GET("/categories/getbyday")
    Call<List<Categories>> getCateByDay(@Query("day") String day);

    @GET("/recipes/getRecipesbyCa")
    Call<List<Recipes>> getRecipesByCategory(@Query("id_category")String id_category);
    @GET("/recipes/getRecipesById/{id}")
    Call<Recipes> getRecipesById(@Path("id") int id);
    @GET("/recipes/getRecipes")
    Call<List<Recipes>> getRecipes(@Query("sum") int sum);

    @GET("/recipes/getRecipesByTimeServing")
    Call<List<Recipes>> getRecipesByTimeServing(@Query("tag_name") String name,@Query("time") int time,@Query("serving") int serving);


    //////Restaurent
    @GET("/restaurant/getRestaurantByCa")
    Call<List<Restaurant>> getRestaurantCa(@Query("id_category")String id_category);
    @GET("/restaurant/getRestaurant")
    Call<List<Restaurant>> getRestaurants (@Query("sum") int sum);
    @GET("/restaurant/getRestaurantById/{id}")
    Call<Restaurant> getRestaurantById(@Path("id") int id);
}
