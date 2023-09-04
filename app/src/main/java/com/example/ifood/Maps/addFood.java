package com.example.ifood.Maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addFood extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1234;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private double latitude;
    private double longitude;

    EditText pname, brand, expirationDate;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        pname = findViewById(R.id.mapAdd_ProducrName);
        brand = findViewById(R.id.mapsAdd_productBrand);
        expirationDate = findViewById(R.id.mapsAdd_Expiration);

        // Get user uid
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        initLocationUpdates();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("Maps");

        Button confirmBtn = findViewById(R.id.mapsAdd_confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.child(uid).child("Product").setValue(String.valueOf(pname.getText()));
                userRef.child(uid).child("brand").setValue(String.valueOf(brand.getText()));
                userRef.child(uid).child("Expiration").setValue(String.valueOf(expirationDate.getText()));
                userRef.child(uid).child("lat").setValue(String.valueOf(latitude));
                userRef.child(uid).child("lng").setValue(String.valueOf(longitude));

                Intent intent = new Intent(addFood.this, googleMaps2.class);
                startActivity(intent);
            }
        });
    }

    private void initLocationUpdates() {
        // Create a location request
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000); // Every 10 seconds
        locationRequest.setFastestInterval(5000); // At most every 5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    latitude = locationResult.getLastLocation().getLatitude();
                    longitude = locationResult.getLastLocation().getLongitude();
                    System.out.println(latitude + "@@@@@@@@@@@@@@@@@@@@@@@");
                    System.out.println(longitude);
                }
            }
        };

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            requestLocationPermission();
        }
    }

    // Request necessary location permission from the user at run time
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
    }

    // This method is used when the user responds to a permission request dialog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLocationUpdates();
            } else {
                // Permission denied
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }
}
