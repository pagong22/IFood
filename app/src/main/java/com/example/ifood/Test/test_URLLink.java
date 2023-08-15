package com.example.ifood.Test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifood.R;
import com.example.ifood.UserLogin.SignUpExtension;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class test_URLLink extends AppCompatActivity {

    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_urllink);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            uid = user.getUid();

        }


        updateProfileRTDB();


//        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//        storageRef.child("Users/"+uid+"/"+"UserProfie.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//        //storageRef.child("Users/EoTqgUBvX6QTyfQawGbOS4PiabG2/UserProfie.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.jpg'
//                TextView urlLink = findViewById(R.id.test_UrlLink);
//                urlLink.setText(uri.toString());
//
//
//
//
//                ImageView imageHolder = findViewById(R.id.test_imageHolder);
//                Picasso.get().load(uri.toString()).into(imageHolder);
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });



    }


    public void updateProfileRTDB(){

        //get edit text and store the value of displayname
        String getDisplayName = "String.valueOf(DisplayNameInput.getText());";

        //get Bio and stores it into Real time database
        String getUserBio ="String.valueOf(BioInput.getText());";

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(uid).child("DisplayName").setValue(getDisplayName).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(test_URLLink.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(test_URLLink.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        mDatabase.child("Users").child(uid).child("Bio").setValue(getUserBio).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(test_URLLink.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(test_URLLink.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }




}