package com.example.ifood.Recipe;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {
    @SerializedName("meals")
    private List<Meal> meals;

    // Getters and setters


    public List<Meal> getMeals() {
        return meals;
    }
}

