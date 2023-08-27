package com.example.ifood.Maps;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.ifood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addFood extends AppCompatActivity {

    EditText pname, brand, expirationDate;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        pname = findViewById(R.id.mapAdd_ProducrName);
        brand = findViewById(R.id.mapsAdd_productBrand);
        expirationDate = findViewById(R.id.mapsAdd_Expiration);

        //get user uid
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = mDatabase.child("Maps");

        Button confirmBtn = findViewById(R.id.mapsAdd_confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.child(uid).child("Product").setValue(String.valueOf(pname.getText()));
                userRef.child(uid).child("brand").setValue(String.valueOf(brand.getText()));
                userRef.child(uid).child("Expiration").setValue(String.valueOf(expirationDate.getText()));
            }
        });
    }
}
