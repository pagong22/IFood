package com.example.ifood.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApi {
    @GET("filter.php")
    static Call<MealResponse> getMeals(@Query("c") String category) {
        return null;
    }
}

