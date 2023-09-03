package com.example.ifood.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifood.Maps.googleMaps2;
import com.example.ifood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class itemOnMarket extends AppCompatActivity {

    String currentUser;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_on_market);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUser = user.getUid();
        }

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mapReference = mDatabaseReference.child("Maps");

        mapReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                    //Gets UID of looped maps
                    uid = childSnapshot.getKey();

                    if (uid.equals(currentUser)){

                        String latString = (String) childSnapshot.child("lat").getValue();
                        String lngString = (String) childSnapshot.child("lng").getValue();
                        double lat = Double.parseDouble(latString);
                        double lng = Double.parseDouble(lngString);

                        String Expiration = String.valueOf(snapshot.child(uid).child("Expiration").getValue());
                        String ProductName = String.valueOf(snapshot.child(uid).child("Product").getValue());
                        String brand = String.valueOf(snapshot.child(uid).child("brand").getValue());


                        TextView brandName = findViewById(R.id.currentItem_Brand);
                        brandName.setText(brand);

                        TextView expirationDate = findViewById(R.id.currentSeller_Expiration);
                        expirationDate.setText(Expiration);
                        TextView productname = findViewById(R.id.currentItem_ProductName);
                        productname.setText(ProductName);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ImageView backbtn = findViewById(R.id.currentItem_backBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemOnMarket.this, MenuOption.class);
                startActivity(intent);
            }
        });






    }
}