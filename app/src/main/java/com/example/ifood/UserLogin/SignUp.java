package com.example.ifood.UserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ifood.MainActivity;
import com.example.ifood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText Email = findViewById(R.id.SignUpEmail);
        EditText Password = findViewById(R.id.SignUpPassword);
        Button Confirm = findViewById(R.id.signUpButton);


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = Email.getText().toString();
                String userPassword = Password.getText().toString();
                signUpFirebase(userEmail,userPassword);



            }
        });
    }



    //Firebase method to create new user
    public void signUpFirebase(String userEmail, String userPassword){

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignUp.this,
                                    "Account succesfully created", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignUp.this, SignUpExtension.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this,
                                    "Account failed or email is already used", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }




}