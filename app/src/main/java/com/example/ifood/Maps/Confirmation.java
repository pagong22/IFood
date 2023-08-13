package com.example.ifood.Maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ifood.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Random random = new Random();
        int code = random.nextInt(9000) + 1000;

        TextView myTextView = (TextView) findViewById(R.id.confirmation_Code);
        myTextView.setText(String.valueOf(code));




        Button recievedBtn = (Button) findViewById(R.id.confirmation_recieved);

        recievedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Confirmation.this, addFood.class);

            // Start the TargetActivity
            startActivity(intent);


        });


        Button navigateBtn = (Button) findViewById(R.id.confirmation_navigate);

        navigateBtn.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=54.57612901016541,-1.2330211653532375");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }

        });






    }
}