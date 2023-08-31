package com.example.ifood.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.R;
import com.example.ifood.Recipe.RecipeMain;
import com.example.ifood.ShoppingList.ShoppingList_Main;

public class MenuOption extends AppCompatActivity {

    ConstraintLayout profile,recipe,settings,sold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);


        profile = findViewById(R.id.mainMenu_Profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuOption.this, Profile.class);
                startActivity(intent);

            }
        });

        recipe = findViewById(R.id.mainMenu_Recipe);
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuOption.this, RecipeMain.class);
                startActivity(intent);
            }
        });

        settings = findViewById(R.id.mainMenu_Settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sold = findViewById(R.id.mainMenu_SoldProducts);
        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuOption.this, soldProduct.class);
                startActivity(intent);

            }
        });




    }
}