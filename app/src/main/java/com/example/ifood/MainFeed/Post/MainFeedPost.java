package com.example.ifood.MainFeed.Post;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifood.R;
import com.example.ifood.UserLogin.SignUpExtension;
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

import java.util.HashMap;
import java.util.Map;

public class MainFeedPost extends AppCompatActivity {

    ImageView userIcon;
    TextView displayName;
    EditText userPost;
    Button postBtn;
    String uid;


    long count;

    //RTDB reference
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed_post);

        //get user icon
        //Firebase getUser ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }


        //cloud storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("Users/"+uid+"/"+"UserProfie.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.jpg'
                ImageView imageHolder = findViewById(R.id.mainFeed_postImg);
                Picasso.get().load(uri.toString()).into(imageHolder);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        //Create new Post ID
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Feed");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
                Log.d("ChildrenCount", "Number of children: " + count);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error fetching data", databaseError.toException());
            }
        });

        //send post to RTDB
        postBtn = findViewById(R.id.mainFeed_PostButton);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();

                userPost = findViewById(R.id.mainFeed_postEditText);
                String userpost = String.valueOf(userPost.getText());

                // Create a map of values to be updated at once
                Map<String, Object> postValues = new HashMap<>();
                postValues.put("UID", uid);
                postValues.put("post", userpost);
                /*
                * Count increment by itself because when u get children count it retern the number of children
                * However database position starts at 0 therefore it creates a new child similar to incrementation
                * */
                mDatabase.child("Feed")
                        .child(String.valueOf(count))
                        .updateChildren(postValues)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainFeedPost.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainFeedPost.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }

}