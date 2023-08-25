package com.example.ifood.Test;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ifood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestFeed extends AppCompatActivity {







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_feed);

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

                    String test = dataSnapshot.child(String.valueOf(i)).child("UID").getValue(String.class);
                    System.out.println(test);

                    String test2 = dataSnapshot.child(String.valueOf(i)).child("post").getValue(String.class);
                    System.out.println(test2);







                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors, e.g., no permission to read data
                Log.e("TAG", "Error reading data: ", databaseError.toException());
            }
        });










    }
}