package com.example.ifood.Recipe.popUp;

public class IngredientModel {

    String Ingredient;
    String Measurement;

    public IngredientModel(String ingredient, String measurement) {
        Ingredient = ingredient;
        Measurement = measurement;
    }

    public String getIngredient() {
        return Ingredient;
    }

    public String getMeasurement() {
        return Measurement;
    }
}
