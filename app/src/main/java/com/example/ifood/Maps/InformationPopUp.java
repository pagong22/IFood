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
            double latitude = getIntent().getDoubleExtra("LATITUDE", 99999);
            double longitude = getIntent().getDoubleExtra("LONGITUDE", 99999);
            String title = getIntent().getStringExtra("TITLE");
            String snippet = getIntent().getStringExtra("SNIPPET");
            System.out.println("*********************************");
            UIDseller = getIntent().getStringExtra("UID");
            System.out.println(getIntent().getStringExtra("UID"));
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

            //set snippet into textview
            System.out.println("ProductName: " + productName);
            System.out.println("Brand: " + brand);
            System.out.println("Expiration: " + expiration);

            SellerName.setText(title);
            ProductTitle.setText(productName);
            ExpirationDate.setText(expiration);
            BrandName.setText(brand);


        }


        BuyButton.setOnClickListener(view -> {
            Intent intent = new Intent(InformationPopUp.this, Confirmation.class);


            intent.putExtra("SELLER_UID", UIDseller);



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


