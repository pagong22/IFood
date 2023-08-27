package com.example.ifood.Maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.example.ifood.R;

import java.util.ArrayList;
import java.util.List;

public class googleMaps extends AppCompatActivity implements OnMapReadyCallback, OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private List<Marker> markers = new ArrayList<Marker>();


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

            Intent intent = new Intent(googleMaps.this, addFood.class);

            // Start the TargetActivity
            startActivity(intent);

        });
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

        // Add a marker in Sydney and move the camera
//        LatLng kyoto = new LatLng(35.00116, 135.7681);
//        LatLng sydney = new LatLng(-34, 151);
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//       mMap.addMarker(new MarkerOptions().position(kyoto).title("Marker in kyoto"));
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(kyoto));


//        final LatLng melbourneLocation = new LatLng(-37.813, 144.962);
//        Marker melbourne = mMap.addMarker(
//                new MarkerOptions()
//                        .position(melbourneLocation)
//                        .title("Melbourne")
//                        .snippet("Population: 4,137,400")
//                        .melbourne.showInfoWindow()
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_2)).flat(true));




        final LatLng melbourneLatLng = new LatLng(54.571975, -1.229416);
        Marker melbourne = mMap.addMarker(
                new MarkerOptions()
                        .position(melbourneLatLng)
                        .title("Melbourne")
                        .snippet("Jerome\n" + "Milk\n" + "Potato")
                       .icon(BitmapDescriptorFactory.fromResource(R.drawable.maps_userlocation)).flat(true));


                        //melbourne.showInfoWindow();


        final LatLng milk = new LatLng(54.56993475841551, -1.227570916709964);
        Marker milkMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(milk)
                        .title("Milk")
                        .snippet("asdasd " + " ; asdasdd ")
                        .alpha(0.8f)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        markers.add(milkMarker);



        final LatLng salt = new LatLng(54.57124085365406, -1.2329138769846424);
        Marker saltMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(salt)
                        .title("Salt")
                        .alpha(0.2f)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


        final LatLng potato = new LatLng(54.57612901016541, -1.2330211653532375);
        Marker potatoMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(potato)
                        .title("potato")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


        final LatLng carrot = new LatLng(54.57718616791892, -1.2177003876561414);
        Marker carrotMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(carrot)
                        .title("carrot")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));




        mMap.moveCamera(CameraUpdateFactory.newLatLng(melbourneLatLng));

        googleMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }



    /*
    *
    * On marker click change to pop up activity
    * Send marker information
    *
    * */
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this, "clicked to next activity",
                Toast.LENGTH_SHORT).show();

        // Create the Intent to launch the TargetActivity
        Intent intent = new Intent(googleMaps.this, InformationPopUp.class);

        //Send marker information to pop up activity
        intent.putExtra("LATITUDE",marker.getPosition().latitude);
        intent.putExtra("LONGITUDE",marker.getPosition().longitude);
        intent.putExtra("TITLE",marker.getTitle());
        intent.putExtra("SNIPPET", marker.getSnippet());

        // Start the TargetActivity
        startActivity(intent);
        return false;
    }
}