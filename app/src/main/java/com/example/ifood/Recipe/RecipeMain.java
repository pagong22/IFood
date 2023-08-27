package com.example.ifood.Recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.MainFeed.MainFeedAdapter;
import com.example.ifood.R;

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

        chickenButton = findViewById(R.id.recipeChicken);
        beefButton = findViewById(R.id.recipeBeef);
        seafoodButton = findViewById(R.id.recipeSeafood);
        userButton = findViewById(R.id.recipeUser);

        recyclerView = findViewById(R.id.recipe_displayRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecipeMain.this));
        adapter = new MealAdapter(meals);
        recyclerView.setAdapter(adapter);







//        RecyclerView  recipeRecyclerView = findViewById(R.id.recipe_recyclerView);
//        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new shoppingList_Adapter(itemList);
//        recyclerView.setAdapter(adapter);


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

                    for (int i = 0; i < meals.size(); i++) {
                        Toast.makeText(RecipeMain.this, meals.get(i).getStrMeal(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
            }
        });




        seafoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }








}