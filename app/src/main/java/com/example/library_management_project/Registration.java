package com.example.library_management_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Users;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    //Initialize
    private EditText nameEditText,emailEditText, passwordEditText, phoneNumberEditText;
    private Button registerButton, backButton;
    private TextView titleTextView;

    //DatabaseReference

    DatabaseReference userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initialize();
    }

    private void initialize(){
        // Initialize the objects
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);
        titleTextView = findViewById(R.id.titleTextView);

        //Initialize OnClickListener
        registerButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        //Initialize Firebase
        userDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://projectlibrary-70b9b-default-rtdb.firebaseio.com/");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        if(id== R.id.registerButton){
            RegisterUser(v,"Registration Successful");
        }if(id== R.id.backButton){
            goBack(v);
        }

    }

    private void goBack(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void RegisterUser(View v, String registrationSuccessful) {

        //Use the phoneNumber as the unique key
        String phoneNumber = phoneNumberEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        if(phoneNumber.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()){

            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

        }else {

            userDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Checking if the phone number has been already used

                    if (snapshot.hasChild(phoneNumber)) {
                        Toast.makeText(Registration.this, "Phone is already registered", Toast.LENGTH_LONG).show();
                    } else {
                        Users user1 = new Users(Long.valueOf(phoneNumber),name,password,email);

                        userDatabase.child("Users").child(String.valueOf(user1.getPhoneNumber())).setValue(user1);

                        Toast.makeText(Registration.this, registrationSuccessful, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Registration.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}