package com.example.ifood.Recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ifood.R;
import com.google.android.play.integrity.internal.t;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeMain extends AppCompatActivity {

    Button chickenButton, beefButton, seafoodButton, userButton;

    String PassID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);


        chickenButton = findViewById(R.id.recipeChicken);
        beefButton = findViewById(R.id.recipeBeef);
        seafoodButton = findViewById(R.id.recipeSeafood);
        userButton = findViewById(R.id.recipeUser);

        chickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            chickenButton();
            }
        });

        beefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beefButton();

            }
        });

        seafoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seaFoodButton();
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    //methods

    public void chickenButton(){
        // ... [Your Retrofit initialization code]

        Call<MealResponse> call = ChickenApi.getMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    PassID = meals.get(0).getIdMeal();

                    // Initialize RecyclerView and set its adapter
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    MealAdapter adapter = new MealAdapter(meals);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RecipeMain.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }



    public void beefButton(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BeefApi BeefApi = retrofit.create(BeefApi.class);

        Call<MealResponse> call = BeefApi.getMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    PassID = meals.get(0).getIdMeal();
                    for (Meal meal : meals) {
                        Toast.makeText(RecipeMain.this, meal.getStrMeal(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                // Handle failure
            }
        });

    }

    public void seaFoodButton(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SeafoodApi SeafoodApi = retrofit.create(SeafoodApi.class);

        Call<MealResponse> call = SeafoodApi.getMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    PassID = meals.get(0).getIdMeal();
                    for (Meal meal : meals) {
                        Toast.makeText(RecipeMain.this, meal.getStrMeal(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                // Handle failure
            }
        });

    }
}