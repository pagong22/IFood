package com.example.ifood.UserLogin;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifood.Profile.Profile;
import com.example.ifood.R;
import com.example.ifood.Test.Test_GetInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class SignUpExtension extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ActivityResultLauncher<Intent> galleryLauncher;
    String uid;


    ImageView ImageHolder;
    Uri selectedImageUri;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_extension);

        Button Confirm = findViewById(R.id.signUpButton2);

        Button ChooseProfile = findViewById(R.id.signUp_ChooseProfile);
        ImageHolder = findViewById(R.id.signUp_ImagePlaceholder2);



        //Firebase RTDB
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Firebase Authentication
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        String getBio = String.valueOf(BioInput.getText());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
            uid = user.getUid();

        }

        //Opens gallery when intent used
        LaunchGallery();





        //Upload Image to Firebase Cloud Storage
        //OpenAlbum();
        ChooseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });



        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //On confirm click saves the selected image into the cloud storage
                uploadImageToFirebase(selectedImageUri);
                updateProfileRTDB();


                Intent intent = new Intent(SignUpExtension.this, Profile.class);
                startActivity(intent);
                finish();

            }
        });






    }

    public void OpenAlbum(){
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri selectedImageUri = data.getData();
                                ImageHolder.setImageURI(selectedImageUri);
                            }
                        }
                    }
                });
    }

    private void uploadImageToFirebase(Uri contentUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("Users/").child(uid +"/" + "UserProfie" + ".jpg");

        //Uploading Image to firebase storage
        UploadTask uploadTask = imageRef.putFile(contentUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Handle successful upload
            Log.d(TAG, "Image uploaded successfully.");
        }).addOnFailureListener(e -> {
            // Handle failed upload
            Log.e(TAG, "Image upload failed: " + e.getMessage());
        });



    }

    private void LaunchGallery(){
        TextView uriCode = findViewById(R.id.uri);
        ImageView imageView = findViewById(R.id.showImage);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                selectedImageUri = data.getData();
                                String uriString = selectedImageUri.toString();
                                //uriCode.setText(uriString);
                              //  System.out.println(uriString + "==@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                ImageHolder.setImageURI(selectedImageUri);

                            }
                        }
                    }
                });

    }

    public void updateUserProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //get edit text and store the value of displayname
        EditText DisplayNameInput = findViewById(R.id.signUpName);
        String getDisplayName = String.valueOf(DisplayNameInput.getText());




        //get Bio and stores it into Real time database
        EditText BioInput = findViewById(R.id.signUp_Bio2);
        String getUserBio = String.valueOf(BioInput.getText());




        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(String.valueOf(DisplayNameInput))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void updateProfileRTDB(){

        //get edit text and store the value of displayname
        EditText DisplayNameInput = findViewById(R.id.signUpName);
        String getDisplayName = String.valueOf(DisplayNameInput.getText());

        //get Bio and stores it into Real time database
        EditText BioInput = findViewById(R.id.signUp_Bio2);
        String getUserBio = String.valueOf(BioInput.getText());

        DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(uid).child("DisplayName").setValue(getDisplayName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SignUpExtension.this, "Successful", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpExtension.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

        mDatabase.child("Users").child(uid).child("Bio").setValue(getUserBio).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SignUpExtension.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpExtension.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}