package com.example.ifood.ShoppingList;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.Maps.googleMaps;
import com.example.ifood.Profile.MenuOption;
import com.example.ifood.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList_Main extends AppCompatActivity {

    private RecyclerView recyclerView;
    private shoppingList_Adapter adapter;
    private List<shoppingList_Model> itemList = new ArrayList<>();
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_main);

//        itemList.add(new shoppingList_Model("lemon","2"));
//        itemList.add(new shoppingList_Model("grapes","5"));
//        itemList.add(new shoppingList_Model("orange","3"));

        //Add to RTDB
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        //Get user display name using UID
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = mDatabaseReference.child("Users");
        userReference.child(uid).child("shoppingList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                String displayName = dataSnapshot.getValue(String.class);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String ingredient = snapshot.getKey();
                    String measurement = snapshot.getValue(String.class);
                    itemList.add(new shoppingList_Model(ingredient, measurement));

                    recyclerView = findViewById(R.id.recyclerViewShoppingList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ShoppingList_Main.this));

                    //pass uid value as well to allow recycler to identify user
                    adapter = new shoppingList_Adapter(itemList, uid);
                    recyclerView.setAdapter(adapter);
                }






            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });







        EditText insertProduct = findViewById(R.id.getProductName);
        EditText insertQuantity = findViewById(R.id.getQuantity);
        //Add new Item to Shopping List
        Button addShoppingList = findViewById(R.id.shoppingList_confirm);
        addShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productNameTemp = insertProduct.getText().toString();
                String insertQuantityTemp = insertQuantity.getText().toString();

                // Push the data to Firebase

                userReference.child(uid).child("shoppingList").child(productNameTemp).setValue(insertQuantityTemp);

                itemList.add(new shoppingList_Model(productNameTemp, insertQuantityTemp));
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });


        navigationBar();

    }


    public void navigationBar() {
        ImageView homeButton = findViewById(R.id.nav_home);
        ImageView reminderButton = findViewById(R.id.nav_reminder);
        ImageView mapsButton = findViewById(R.id.nav_maps);
        ImageView profileButton = findViewById(R.id.nav_profile);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingList_Main.this, MainFeed.class);
            startActivity(intent);
        });

        reminderButton.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingList_Main.this, ShoppingList_Main.class);
            startActivity(intent);
        });

        mapsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingList_Main.this, googleMaps.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ShoppingList_Main.this, MenuOption.class);
            startActivity(intent);
        });
    }

}