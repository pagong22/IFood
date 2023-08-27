package com.example.ifood.Maps;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifood.Profile.Profile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.example.ifood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class googleMaps2 extends AppCompatActivity implements OnMapReadyCallback, OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<Marker>();


    String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView addFoodBtn = (ImageView) findViewById(R.id.maps_addFood);
        addFoodBtn.setOnClickListener(view -> {

            Intent intent = new Intent(googleMaps2.this, addFood.class);
            // Start the TargetActivity
            startActivity(intent);

        });

        navigation();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //set map zoom levels
        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(14.0f);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mapReference = mDatabaseReference.child("Maps");
        DatabaseReference userReference = mDatabaseReference.child("Users");

        mapReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //loop through all food in the database
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            currentUser = user.getUid();
                            System.out.println("00000000000000" + currentUser);
                        }

                        String uid = childSnapshot.getKey();
                        System.out.println("11111111111111" + uid);

                        if (uid.equals(currentUser)){

                            Toast.makeText(googleMaps2.this, "SAME USER", Toast.LENGTH_SHORT).show();

                        }else{
                            System.out.println(currentUser + "&&&&&&&&&&&&&&&&&");
                            String latString = (String) childSnapshot.child("lat").getValue();
                            String lngString = (String) childSnapshot.child("lng").getValue();
                            double lat = Double.parseDouble(latString);
                            double lng = Double.parseDouble(lngString);

                            String Expiration = String.valueOf(snapshot.child(uid).child("Expiration").getValue());
                            String ProductName = String.valueOf(snapshot.child(uid).child("Product").getValue());
                            String brand = String.valueOf(snapshot.child(uid).child("brand").getValue());

                            System.out.println(lat);
                            System.out.println(lng);

                            //Get user display name using UID
                            userReference.child(uid).child("DisplayName").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String displayName = dataSnapshot.getValue(String.class);

                                    //display marker
                                    final LatLng markerPosition = new LatLng(lat,lng);
                                    Marker melbourne = mMap.addMarker(
                                            new MarkerOptions()
                                                    .position(markerPosition)
                                                    .title(displayName)
                                                    .snippet("ProductName: " + ProductName + "\n" +
                                                            "Brand: " + brand + "\n" +
                                                            "Expiration: " + Expiration));
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            });

                        }
                    }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;  // Use default window frame (optional)
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the custom layout
                View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                // Reference to the TextViews
                TextView titleView = view.findViewById(R.id.title);
                TextView snippetView = view.findViewById(R.id.snippet);

                // Set the content
                titleView.setText(marker.getTitle());
                snippetView.setText(marker.getSnippet());

                return view;
            }
        });






//
//        final LatLng melbourneLatLng = new LatLng(54.571975, -1.229416);
//        Marker melbourne = mMap.addMarker(
//                new MarkerOptions()
//                        .position(melbourneLatLng)
//                        .title("Melbourne")
//                        .snippet("Jerome\n" + "Milk\n" + "Potato")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_userlocation)).flat(true));
//
//        //melbourne.showInfoWindow();
//
//        final LatLng milk = new LatLng(54.56993475841551, -1.227570916709964);
//        Marker milkMarker = mMap.addMarker(
//                new MarkerOptions()
//                        .position(milk)
//                        .title("Milk")
//                        .snippet("asdasd " + " ; asdasdd ")
//                        .alpha(0.8f)
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//        markers.add(milkMarker);




        final LatLng melbourneLatLng = new LatLng(54.571975, -1.229416);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(melbourneLatLng));


        googleMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "clicked to next activity",
                Toast.LENGTH_SHORT).show();

        // Create the Intent to launch the TargetActivity
        Intent intent = new Intent(googleMaps2.this, InformationPopUp.class);

        //Send marker information to pop up activity
        intent.putExtra("LATITUDE",marker.getPosition().latitude);
        intent.putExtra("LONGITUDE",marker.getPosition().longitude);
        intent.putExtra("TITLE",marker.getTitle());
        intent.putExtra("SNIPPET", marker.getSnippet());

        // Start the TargetActivity
        startActivity(intent);

    }



    /*
     *
     * On marker click change to pop up activity
     * Send marker information
     *
     * */
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
//        Toast.makeText(this, "clicked to next activity",
//                Toast.LENGTH_SHORT).show();
//
//        // Create the Intent to launch the TargetActivity
//        Intent intent = new Intent(googleMaps2.this, InformationPopUp.class);
//
//        //Send marker information to pop up activity
//        intent.putExtra("LATITUDE",marker.getPosition().latitude);
//        intent.putExtra("LONGITUDE",marker.getPosition().longitude);
//        intent.putExtra("TITLE",marker.getTitle());
//        intent.putExtra("SNIPPET", marker.getSnippet());
//
//        // Start the TargetActivity
//        startActivity(intent);
        return false;
    }


    public void navigation(){
        ImageView profile = findViewById(R.id.nav_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(googleMaps2.this, Profile.class);
                startActivity(intent);
            }
        });
    }


}

