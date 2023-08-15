package com.example.ifood.Profile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifood.R;
import com.example.ifood.UserLogin.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {


    String uid;
    String email;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        displayUserDetails();



        Button LogoutButton = findViewById(R.id.signoutButton);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                //Removes all activities in the stack prevent users from using back button when logged out
                Intent intent = new Intent(Profile.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });




    }


    public void getUserProfile() {

    }

    public void displayUserDetails(){

        TextView DisplayName = findViewById(R.id.Profile_DisplayName);
        TextView Email = findViewById(R.id.Profile_Email);
        TextView DisplayBio = findViewById(R.id.Profile_Bio);
        ImageView ProfileIcon = findViewById(R.id.Profile_UserIcon);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            uid = user.getUid();
            email = user.getEmail();

        }

        Email.setText(email);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

// Create a reference to the specific location you want to read from
        DatabaseReference userRef = mDatabase.child("Users").child(uid);

// Set up a listener to read the data
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the data from the DataSnapshot
                String displayName = dataSnapshot.child("DisplayName").getValue(String.class);
                String bio = dataSnapshot.child("Bio").getValue(String.class);

                DisplayName.setText(displayName);
                DisplayBio.setText(bio);

                // Use the data (e.g., update the UI)
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
                Log.e(TAG, "Error reading data: ", databaseError.toException());
            }
        });


        //cloud storage
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("Users/"+uid+"/"+"UserProfie.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

        //storageRef.child("Users/EoTqgUBvX6QTyfQawGbOS4PiabG2/UserProfie.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.jpg'

                ImageView imageHolder = findViewById(R.id.Profile_UserIcon);
                Picasso.get().load(uri.toString()).into(imageHolder);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });



    }



}