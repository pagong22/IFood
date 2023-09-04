package com.example.ifood.Maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ifood.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.Random;

public class Confirmation extends AppCompatActivity {

    String UIDseller;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //Create 4 digit randomize number
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;

        TextView myTextView = (TextView) findViewById(R.id.confirmation_Code);
        myTextView.setText(String.valueOf(code));


        UIDseller = getIntent().getStringExtra("SELLER_UID");


        lat = getIntent().getDoubleExtra("LATITUDE", 99999);
        lng = getIntent().getDoubleExtra("LONGITUDE", 99999);

        System.out.println(UIDseller);
        System.out.println(lat);
        System.out.println(lng);

        System.out.println("This is a test for recieving data from Information PopUp (UID) : " + UIDseller);
        System.out.println("This is a test for recieving data from Information PopUp (lat) : " + lat);
        System.out.println("This is a test for recieving data from Information PopUp (lng) : " + lng);






        Button recievedBtn = (Button) findViewById(R.id.confirmation_recieved);

        recievedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Confirmation.this, UserReview.class);

            //send seller ID to review page
            intent.putExtra("SELLER_UID", UIDseller);


            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference sellerReference = mDatabaseReference.child("Maps").child(UIDseller);
            DatabaseReference sellerHistory = mDatabaseReference.child("Users").child(UIDseller).child("soldItem");

            // Fetch the seller's product
            sellerReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String ProductName = snapshot.getValue(String.class);

                    // Get a reference to the SoldProducts node
                    DatabaseReference soldProductsReference = mDatabaseReference.child("Users").child(UIDseller).child("SoldProducts");

                    // Fetch the current count of sold products
                    soldProductsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot innerSnapshot) {
                            long childrenCount = innerSnapshot.getChildrenCount();
                            int productCounter = (int) childrenCount;

                            // Use productCounter to set the value for the next child
                            DatabaseReference newProductReference = soldProductsReference.child(String.valueOf(productCounter));
                            newProductReference.setValue(ProductName);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle possible errors.
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });

            /***
             *
             * Delete sellers item
             * If commented out it was used for testing prevents adding new item every test
             *
             * ***/
            sellerReference.removeValue();

            // Start the TargetActivity
            startActivity(intent);


        });

        /**
         * Opens google maps and navigate to sellers location
         * **/
        Button navigateBtn = (Button) findViewById(R.id.confirmation_navigate);

        navigateBtn.setOnClickListener(view -> {
            // Use the lat and lng values that were passed into this activity
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });





    }
}