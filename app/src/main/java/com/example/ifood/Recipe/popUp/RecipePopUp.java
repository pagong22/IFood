package com.example.ifood.Recipe.popUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ifood.R;
import com.example.ifood.Recipe.Meal;
import com.example.ifood.Recipe.MealResponse;
import com.example.ifood.ShoppingList.shoppingList_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipePopUp extends AppCompatActivity {

    TextView foodName, description;
    ImageView foodThumb;
    RecyclerView popUpRecycleView;
    List<IngredientModel> ingredientList = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String uid;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_pop_up);


        //recieve data from recipeMain

        String MealID = getIntent().getStringExtra("FOOD_ID");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MealAPI api = retrofit.create(MealAPI.class);
        Call<MealResponse> call = api.getMealDetail(MealID);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Meal meal = response.body().getMeals().get(0);


                    foodName = findViewById(R.id.recipePop_FoodName);
                    foodName.setText(meal.getStrMeal());

                    description = findViewById(R.id.recipePop_Description);
                    description.setText(meal.getStrInstructions());

                    foodThumb = findViewById(R.id.recipePop_Image);
                    // Load image using Picasso
                    Picasso.get()
                            .load(meal.getStrMealThumb())
                            .into(foodThumb);



                    for (int i = 1; i <= 10; i++) {
                        try {
                            Method ingredientMethod = meal.getClass().getMethod("getStrIngredient" + i);
                            Method measurementMethod = meal.getClass().getMethod("getStrMeasure" + i);

                            String ingredient = (String) ingredientMethod.invoke(meal);
                            String measurement = (String) measurementMethod.invoke(meal);

                            if (ingredient != null && !ingredient.trim().isEmpty()) {
                                ingredientList.add(new IngredientModel(ingredient, measurement));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    //Recycler view adapter
                    RecyclerView ingredientRecyclerView = findViewById(R.id.ingredientRecyclerView);
                    IngredientAdapter adapter = new IngredientAdapter(ingredientList);  // Your list from earlier
                    ingredientRecyclerView.setAdapter(adapter);
                    ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(RecipePopUp.this));


                    btns(meal);



                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Toast.makeText(RecipePopUp.this, "Error fetching meal details", Toast.LENGTH_SHORT).show();
            }
        });


        Button saveToShopping = findViewById(R.id.recipePop_addShoppingList);
        saveToShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save list to RTDB

                for (IngredientModel ingredient : ingredientList){
                    Toast.makeText(RecipePopUp.this, ingredient.getIngredient(), Toast.LENGTH_SHORT).show();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        uid = user.getUid();
                    }


                    DatabaseReference recipeIngredients = mDatabase.child("Users").child(uid).child("shoppingList");
                    recipeIngredients.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            recipeIngredients.child(ingredient.getIngredient()).setValue(ingredient.getMeasurement());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RecipePopUp.this, "Failed to add", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });



    }


    public void btns(Meal meal){
        Button youtubeBtn = findViewById(R.id.recipePop_Youtube);
        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opens youtube

                String youtubeUrl = meal.getStrYoutube();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });

    }


}
