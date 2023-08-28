package com.example.ifood.Recipe.popUp;

import com.example.ifood.Recipe.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealAPI {
    @GET("lookup.php")
    Call<MealResponse> getMealDetail(@Query("i") String mealId);
}

