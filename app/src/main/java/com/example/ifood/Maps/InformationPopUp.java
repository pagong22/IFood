package com.example.ifood.Maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifood.R;

import org.w3c.dom.Text;

public class InformationPopUp extends AppCompatActivity {
    String UIDseller;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_pop_up);


        TextView SellerName = (TextView) findViewById(R.id.popUp_sellerID);
        TextView ProductTitle = (TextView) findViewById(R.id.popUp_Title);
        TextView ExpirationDate = (TextView) findViewById(R.id.popUp_Expiration);
        TextView BrandName = (TextView) findViewById(R.id.popUp_brand);
        Button BuyButton = (Button) findViewById(R.id.popUp_Buy);
        ImageView BackButton = (ImageView) findViewById(R.id.popUp_BackBtn);





        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = getIntent().getDoubleExtra("LATITUDE", 99999);
            longitude = getIntent().getDoubleExtra("LONGITUDE", 99999);
            String title = getIntent().getStringExtra("TITLE");
            String snippet = getIntent().getStringExtra("SNIPPET");

            System.out.println("This is a test for passing data (Lat) : " + latitude);
            System.out.println("This is a test for passing data (Lng) : " + longitude);
            System.out.println("This is a test for passing data (Title) : " + title);
            System.out.println("This is a test for passing data (snippet) : " + snippet);

            UIDseller = getIntent().getStringExtra("UID");



            System.out.println("This is a test for passing data (Lat) : " + UIDseller);




            //The key argument here must match that used in the other activity



            //Split snippet into different variables
            String[] lines = snippet.split("\n");

            String productName = "";
            String brand = "";
            String expiration = "";

            for (String line : lines) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "ProductName":
                            productName = value;
                            break;
                        case "Brand":
                            brand = value;
                            break;
                        case "Expiration":
                            expiration = value;
                            break;
                    }
                }
            }



            SellerName.setText(title);
            ProductTitle.setText(productName);
            ExpirationDate.setText(expiration);
            BrandName.setText(brand);


        }


        BuyButton.setOnClickListener(view -> {


            Intent intent = new Intent(InformationPopUp.this, Confirmation.class);

            intent.putExtra("SELLER_UID", UIDseller);
            intent.putExtra("LATITUDE",latitude);
            intent.putExtra("LONGITUDE",longitude);




            // Start the TargetActivity
            startActivity(intent);
        });

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(InformationPopUp.this, googleMaps2.class);

            // Start the TargetActivity
            startActivity(intent);


        });



    }
}


