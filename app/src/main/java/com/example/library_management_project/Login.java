package com.example.library_management_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneNumberEditText, passwordEditText;
    private Button loginButton, registerButton;

    DatabaseReference usersDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private void initialize(){
        //Initialize Widgets
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        //Initialize OnClickListener
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        //Initialize Firebase
        usersDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectlibrary-70b9b-default-rtdb.firebaseio.com/");


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        if(id== R.id.loginButton){
            LoginUser("Login Successful");
        }if(id== R.id.registerButton){
            RegisterUser();
        }

    }

    private void RegisterUser() {

        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }

    private void LoginUser(String loginSuccessful) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }else{
            usersDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(phoneNumber)){

                        String validatePassword = snapshot.child(phoneNumber).child("password").getValue(String.class);
                        String userType = snapshot.child("type").getValue(String.class);

                        if(validatePassword.equals(password)){
                            Toast.makeText(Login.this, loginSuccessful.toString(),Toast.LENGTH_LONG).show();

                            SharedPreferences sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("userPhoneNumber", phoneNumber);
                            editor.putString("userType", userType);
                            editor.apply();

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("userType",userType);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, "Email/Password Doesn't Exist, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}