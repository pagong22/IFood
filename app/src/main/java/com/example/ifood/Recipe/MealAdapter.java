package com.example.ifood.Recipe;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.Maps.Confirmation;
import com.example.ifood.R;
import com.example.ifood.Recipe.popUp.RecipePopUp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;

    public MealAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.tvMealName.setText(meal.getStrMeal());

        // Load image using Picasso
        Picasso.get()
                .load(meal.getStrMealThumb())  // Assuming you have a method in Meal class that returns the image URL
                .into(holder.MealImage);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecipePopUp.class);

                intent.putExtra("FOOD_ID", meal.getIdMeal());





                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }


    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealName;
        ImageView MealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            MealImage = itemView.findViewById(R.id.recipe_Image);
            tvMealName = itemView.findViewById(R.id.recipe_mealName);
        }
    }
}

