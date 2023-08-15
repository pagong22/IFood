package com.example.ifood.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SeafoodApi {
    @GET("filter.php?c=Seafood")
    Call<MealResponse> getMeals();
}

interface ChickenApi {
    @GET("filter.php?c=Chicken")
    Call<MealResponse> getMeals();
}


interface BeefApi {
    @GET("filter.php?c=Beef")
    Call<MealResponse> getMeals();
}

