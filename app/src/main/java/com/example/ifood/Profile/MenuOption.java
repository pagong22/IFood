package com.example.ifood.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.Maps.googleMaps2;
import com.example.ifood.R;
import com.example.ifood.Recipe.RecipeMain;
import com.example.ifood.ShoppingList.ShoppingList_Main;

public class MenuOption extends AppCompatActivity {

    /****
     * This is just the menu option
     *
     * Currently settings has no functionality (future implementation)
     *
     * ***/

    ConstraintLayout profile,recipe,settings,sold,currentItems;

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

        currentItems = findViewById(R.id.mainMeni_itemOnMarket);
        currentItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuOption.this, itemOnMarket.class);
                startActivity(intent);
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
            Intent intent = new Intent(MenuOption.this, MainFeed.class);
            startActivity(intent);
        });

        reminderButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuOption.this, ShoppingList_Main.class);
            startActivity(intent);
        });

        mapsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuOption.this, googleMaps2.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuOption.this, MenuOption.class);
            startActivity(intent);
        });
    }

}