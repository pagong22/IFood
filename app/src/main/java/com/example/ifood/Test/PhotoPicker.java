package com.example.ifood.Test;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ifood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PhotoPicker extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private ActivityResultLauncher<Intent> galleryLauncher;
    String uid;
    Uri selectedImageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);


        Button button = findViewById(R.id.PhotoPicker);
        Button confirmButton = findViewById(R.id.conFirmButton);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            uid = user.getUid();

        }

        TextView userUID = findViewById(R.id.UIDText);
        userUID.setText(uid);


        LaunchGallery();


        // Other initialization code

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Uploads the selected image to firebase
                uploadImageToFirebase(selectedImageUri);

            }
        });
    }


    private void uploadImageToFirebase(Uri contentUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("Users/").child(uid +"/" + "UserProfie" + ".jpg");

        TextView code = findViewById(R.id.textView13);
        code.setText(uid);

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
                                uriCode.setText(uriString);
                                System.out.println(uriString + "==@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                imageView.setImageURI(selectedImageUri);



                            }
                        }
                    }
                });

    }


}







