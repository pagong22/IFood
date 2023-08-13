package com.example.ifood.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.ifood.MainActivity;
import com.example.ifood.Maps.MapsFragment;
import com.example.ifood.Maps.googleMaps;
import com.example.ifood.Profile.EditProfile;
import com.example.ifood.Profile.Profile;
import com.example.ifood.R;
import com.example.ifood.RTDBTest;
import com.example.ifood.UserLogin.Login;

public class SplashScreen extends AppCompatActivity {

    Handler h = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 2000);

    }
}