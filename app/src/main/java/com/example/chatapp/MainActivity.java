package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import  android.widget.ProgressBar;


import com.example.chatapp.ui.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {
    Button Login;
    public EditText email,password,name;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("users");
    private String userId;

    private TextView loginfirebtn;
    private String Email,Password,Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login  = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.Name);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.password);

        if(FirebaseAuth.getInstance().getCurrentUser()!= null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }


        mAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);
        loginfirebtn = (TextView) findViewById(R.id.loginquery);


        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 Email = email.getText().toString();
                 Password =password.getText().toString();
                 Name = name.getText().toString();

                progressbar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                            .show();
                    progressbar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                            .show();
                    progressbar.setVisibility(View.GONE);
                    return;
                }

                //Adding User to app(Sing Up)
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (!task.isSuccessful()) {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this.getApplicationContext(),
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            progressbar.setVisibility(View.GONE);
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            saveUser(Name,Email);
                            startActivity(i);
                            finish();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);//Updating with Name
                        }
                    }
                });
                progressbar.setVisibility(View.GONE);
            }

        });


    }
  // Save user model into database
    public void saveUser(String name,String email){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user  = new User(name, email);
        Log.w("Database check",userId);
        dbRef.child(userId).setValue(user);
    }
    public void updateUI(final FirebaseUser user){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(Name)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                          Toast.makeText(getApplicationContext(),"Hello "+user.getDisplayName(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void performLogin(View v)
    {
        //Passing context to  Login page
        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
    }

}


