package com.example.ifood.Recipe.popUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<IngredientModel> ingredientList;

    public IngredientAdapter(List<IngredientModel> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientModel ingredientModel = ingredientList.get(position);
        holder.tvIngredient.setText(ingredientModel.getIngredient());
        holder.tvMeasurement.setText(ingredientModel.getMeasurement());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredient, tvMeasurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredient = itemView.findViewById(R.id.tvIngredient);
            tvMeasurement = itemView.findViewById(R.id.tvMeasurement);
        }
    }
}

