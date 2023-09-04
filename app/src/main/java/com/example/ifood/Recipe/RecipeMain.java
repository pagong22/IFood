package com.example.ifood.Recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.Maps.googleMaps2;
import com.example.ifood.Profile.MenuOption;
import com.example.ifood.R;
import com.example.ifood.Recipe.popUp.RecipeAdd;
import com.example.ifood.ShoppingList.ShoppingList_Main;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeMain extends AppCompatActivity {

    Button chickenButton, beefButton, seafoodButton, userButton;
    String PassID;
    List<Meal> meals = new ArrayList<>();
    RecyclerView recyclerView;
    private MealAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);




        recyclerView = findViewById(R.id.recipe_displayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecipeMain.this));
        adapter = new MealAdapter(meals);
        recyclerView.setAdapter(adapter);

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
                    //store meals in meals list
                    meals = response.body().getMeals();

                    // Update the adapter's data source
                    adapter.setMeals(meals);

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
            }
        });


        navigationBar();



    }

    public void navigationBar() {
        ImageView homeButton = findViewById(R.id.nav_home);
        ImageView reminderButton = findViewById(R.id.nav_reminder);
        ImageView mapsButton = findViewById(R.id.nav_maps);
        ImageView profileButton = findViewById(R.id.nav_profile);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeMain.this, MainFeed.class);
            startActivity(intent);
        });

        reminderButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeMain.this, ShoppingList_Main.class);
            startActivity(intent);
        });

        mapsButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeMain.this, googleMaps2.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecipeMain.this, MenuOption.class);
            startActivity(intent);
        });
    }







}