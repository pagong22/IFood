package com.example.ifood.Maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserReview extends AppCompatActivity {
    float userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        //Recieve seller ID
        Intent intent = getIntent();
        String SELLER_UID = intent.getStringExtra("SELLER_UID");



        RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                userRating = v;

            }
        });

        Button submitReview = findViewById(R.id.review_SubmitBtn);
        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userReviewReference = mDatabaseReference.child("Users").child(SELLER_UID).child("Reviews");

                //save review into Users -> Review
                userReviewReference.addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Get the current count of reviews
                        Long childrencount = snapshot.getChildrenCount();

                        // Use the children count as the key for the new rating
                        userReviewReference.child(childrencount.toString()).setValue(userRating, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    System.out.println("Data could not be saved. " + databaseError.getMessage());
                                    Intent intent = new Intent(UserReview.this, MainFeed.class);
                                    startActivity(intent);
                                } else {
                                    System.out.println("Data saved successfully.");
                                    Intent intent = new Intent(UserReview.this, MainFeed.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }


        });


    }
}