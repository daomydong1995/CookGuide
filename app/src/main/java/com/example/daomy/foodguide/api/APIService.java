package com.example.daomy.foodguide.api;


import com.example.daomy.foodguide.model.Categories;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 4/6/2017.
 */

public interface APIService {

//    @Headers("Accept: application/json")
//    @GET("ad-listing")
//    Call<ReponseChoTot> GetZoom(@Query("region") String region,
//                                @Query("cg") String cg,
//                                @Query("o") int o,
//                                @Query("st") String st,
//                                @Query("q") String q);
//    @GET("ad-listing/{list_id}")
//    Call<MyPojo> GetMyPojoCall(@Path("list_id") long list_id);
    @GET("/categories/getbyday")
    Call<List<Categories>> getCateByDay(@Query("day") String day);

}
