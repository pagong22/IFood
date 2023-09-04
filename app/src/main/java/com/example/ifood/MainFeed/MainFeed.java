package com.example.ifood.MainFeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ifood.MainFeed.Post.MainFeedPost;
import com.example.ifood.Maps.Confirmation;
import com.example.ifood.Maps.googleMaps;
import com.example.ifood.Maps.googleMaps2;
import com.example.ifood.Profile.MenuOption;
import com.example.ifood.Profile.Profile;
import com.example.ifood.R;
import com.example.ifood.ShoppingList.ShoppingList_Main;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainFeed extends AppCompatActivity {

    private static final String TAG = "MainFeed";
    private RecyclerView recyclerView;
    private MainFeedAdapter adapter;
    private List<MainFeedModel> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        recyclerView = findViewById(R.id.mainFeed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainFeed.this));
        adapter = new MainFeedAdapter(itemList);
        recyclerView.setAdapter(adapter);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference feedReference = mDatabaseReference.child("Feed");
        feedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear(); // Clear the list to prevent repetition

                //loop through Feed children
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String uid = postSnapshot.child("UID").getValue(String.class);
                    String post = postSnapshot.child("post").getValue(String.class);

                    //Each loop get users display name and photo
                    DatabaseReference userRef = mDatabaseReference.child("Users").child(uid);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot userSnapshot) {
                            //get display name of
                            String displayName = userSnapshot.child("DisplayName").getValue(String.class);

                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            storageRef.child("Users/" + uid + "/" + "UserProfie.jpg").getDownloadUrl().addOnSuccessListener(uri -> {
                                itemList.add(new MainFeedModel(displayName, uri.toString(), post));


                                // Reverse the list to place newest post at the top of recycler view
                                Collections.reverse(itemList);
                                adapter.notifyDataSetChanged();


                            }).addOnFailureListener(exception -> {
                                // Handle any errors
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "Error reading user data: ", databaseError.toException());
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error reading feed data: ", databaseError.toException());
            }
        });

        ImageView addPost = findViewById(R.id.mainFeed_AddBtn);
        addPost.setOnClickListener(view -> {
            Intent intent = new Intent(MainFeed.this, MainFeedPost.class);
            startActivity(intent);
        });

        navigationBar();

    }


    private BroadcastReceiver dataDeletedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Data has been deleted!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // Register the receiver
        registerReceiver(dataDeletedReceiver, new IntentFilter("DATA_DELETED_ACTION"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the receiver
        unregisterReceiver(dataDeletedReceiver);
    }



    public void navigationBar() {
        ImageView homeButton = findViewById(R.id.nav_home);
        ImageView reminderButton = findViewById(R.id.nav_reminder);
        ImageView mapsButton = findViewById(R.id.nav_maps);
        ImageView profileButton = findViewById(R.id.nav_profile);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainFeed.this, MainFeed.class);
            startActivity(intent);
        });

        reminderButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainFeed.this, ShoppingList_Main.class);
            startActivity(intent);
        });

        mapsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainFeed.this, googleMaps2.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainFeed.this, MenuOption.class);
            startActivity(intent);
        });
    }
}
