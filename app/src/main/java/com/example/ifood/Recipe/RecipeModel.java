package com.example.ifood.Recipe;

public class RecipeModel {
    String urlLink;
    String recipeName;

    public RecipeModel(String urlLink, String recipeName) {
        this.urlLink = urlLink;
        this.recipeName = recipeName;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public String getRecipeName() {
        return recipeName;
    }
}
