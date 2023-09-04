package com.example.ifood.UserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ifood.MainActivity;
import com.example.ifood.MainFeed.MainFeed;
import com.example.ifood.MainFeed.Post.MainFeedPost;
import com.example.ifood.Profile.Profile;
import com.example.ifood.R;
import com.example.ifood.Test.Test_GetInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {


    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //get current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(Login.this, MainFeed.class);
            startActivity(i);
            finish();

        }


       // getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ImageView Logo1 = findViewById(R.id.LoginLogo1);
        ImageView Logo2 = findViewById(R.id.LoginLogi2);
        Button LoginButton = findViewById(R.id.LoginButton);
        EditText TextuserEmail = findViewById(R.id.LoginUsername);
        EditText TextuserPassword = findViewById(R.id.LoginPassword);


        Logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });

        Logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = TextuserEmail.getText().toString();
                String userPassword = TextuserPassword.getText().toString();

                signIn(userEmail,userPassword);
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Login.this,
                                    "Login Successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Login.this, MainFeed.class);
                            startActivity(i);

                            //finish removes activity from back stack prevent user from going back to login page
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this,
                                    "Wrong Username or Password", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }



}