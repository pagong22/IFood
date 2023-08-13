package com.example.ifood.MainFeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ifood.Maps.Confirmation;
import com.example.ifood.Maps.InformationPopUp;
import com.example.ifood.Maps.googleMaps;
import com.example.ifood.Profile.Profile;
import com.example.ifood.R;

public class MainFeed extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);




        //navigationBar
        navigationBar();




    }



    public void navigationBar(){

        ImageView homeButton = findViewById(R.id.nav_home);
        ImageView reminderButton = findViewById(R.id.nav_reminder);
        ImageView mapsButton = findViewById(R.id.nav_maps);
        ImageView profileButton = findViewById(R.id.nav_profile);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, Confirmation.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, Confirmation.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, googleMaps.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, Profile.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });



    }
}