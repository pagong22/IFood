package com.example.ifood.Maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifood.R;

public class InformationPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_pop_up);


        TextView SellerName = (TextView) findViewById(R.id.popUp_sellerID);
        TextView ProductTitle = (TextView) findViewById(R.id.popUp_Title);
        TextView ExpirationDate = (TextView) findViewById(R.id.popUp_Expiration);
        Button BuyButton = (Button) findViewById(R.id.popUp_Buy);
        ImageView BackButton = (ImageView) findViewById(R.id.popUp_BackBtn);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double latitude = getIntent().getDoubleExtra("LATITUDE", 99999);
            double longitude = getIntent().getDoubleExtra("LONGITUDE", 99999);
            String title = getIntent().getStringExtra("TITLE");
            String snippet = getIntent().getStringExtra("SNIPPET");
            //The key argument here must match that used in the other activity


            System.out.println(latitude);
            System.out.println(longitude);
            System.out.println(title);
            System.out.println(snippet);


            SellerName.setText("Test");
            ProductTitle.setText(title);
            ExpirationDate.setText("Test2");
        }


        BuyButton.setOnClickListener(view -> {
            Intent intent = new Intent(InformationPopUp.this, Confirmation.class);


            // Start the TargetActivity
            startActivity(intent);
        });

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(InformationPopUp.this, googleMaps.class);

            // Start the TargetActivity
            startActivity(intent);


        });



    }
}


