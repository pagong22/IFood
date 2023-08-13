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

import com.example.ifood.R;
import com.google.android.gms.tasks.OnCompleteListener;
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






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_extension);

        Button Confirm = findViewById(R.id.signUpButton2);

        Button ChooseProfile = findViewById(R.id.signUp_ChooseProfile);
        EditText DisplayNameInput = findViewById(R.id.signUpName);
        EditText BioInput = findViewById(R.id.signUp_Bio2);
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
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
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
                String getDisplayName = String.valueOf(DisplayNameInput.getText());

                //Saved into firebase AUTHENTICATION
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(getDisplayName)
                        //.setPhotoUri(Uri.parse(selectedImageUri))
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
        StorageReference imageRef = storageRef.child("Users/").child(uid + UUID.randomUUID().toString() + ".jpg");

        TextView code = findViewById(R.id.textView13);
        code.setText(UUID.randomUUID().toString());

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
                                Uri selectedImageUri = data.getData();
                                String uriString = selectedImageUri.toString();
                                uriCode.setText(uriString);
                                System.out.println(uriString + "==@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                imageView.setImageURI(selectedImageUri);

                                //Uploads the selected image to firebase
                                uploadImageToFirebase(selectedImageUri);

                            }
                        }
                    }
                });

    }

}