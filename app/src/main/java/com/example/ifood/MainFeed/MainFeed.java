package com.example.ifood.MainFeed;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ifood.Maps.Confirmation;
import com.example.ifood.Maps.InformationPopUp;
import com.example.ifood.Maps.googleMaps;
import com.example.ifood.Profile.Profile;
import com.example.ifood.R;
import com.example.ifood.ShoppingList.ShoppingList_Main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainFeed extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainFeedAdapter adapter;
    private List<MainFeedModel> itemList = new ArrayList<>();

    private DatabaseReference mDatabase;


    String uid;
    String displayName;
    String post;
    String userImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);



        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference feedReference = mDatabaseReference.child("Feed");
        feedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Data is available in dataSnapshot
                // Do something with the data

                //loop through feed getting all the post
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    System.out.println(i);

                     uid = dataSnapshot.child(String.valueOf(i)).child("UID").getValue(String.class);
                     post = dataSnapshot.child(String.valueOf(i)).child("post").getValue(String.class);



                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    // Create a reference to the specific location you want to read from
                    DatabaseReference userRef = mDatabase.child("Users").child(uid);
                    // Set up a listener to read the data
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get the data from the DataSnapshot
                            String displayName = dataSnapshot.child("DisplayName").getValue(String.class);



                            //cloud storage
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            storageRef.child("Users/"+uid+"/"+"UserProfie.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.jpg'
//                                    ImageView imageHolder = findViewById(R.id.Profile_UserIcon);
//                                    Picasso.get().load(uri.toString()).into(imageHolder);

                                    itemList.add(new MainFeedModel(displayName,uri.toString(),post));
                                    recyclerView = findViewById(R.id.mainFeed_recyclerview);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(MainFeed.this));

                                    adapter = new MainFeedAdapter(itemList);
                                    recyclerView.setAdapter(adapter);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle any errors
                            Log.e(TAG, "Error reading data: ", databaseError.toException());
                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors, e.g., no permission to read data
                Log.e("TAG", "Error reading data: ", databaseError.toException());
            }
        });












        // Dummy data
//        itemList.add(new MainFeedModel("Jerome","IMG1","This is my first post"));
//        itemList.add(new MainFeedModel("Jobi","PosterIMG1","This is a secondary post"));
//


        //navigationBar
        navigationBar();
    }



    public void navigationBar(){

        ImageView homeButton = findViewById(R.id.nav_home);
        ImageView reminderButton = findViewById(R.id.nav_reminder);
        ImageView mapsButton = findViewById(R.id.nav_maps);
        ImageView profileButton = findViewById(R.id.nav_profile);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, Confirmation.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, ShoppingList_Main.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, googleMaps.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainFeed.this, Profile.class);
                // Start the TargetActivity
                startActivity(intent);

            }
        });



    }
}