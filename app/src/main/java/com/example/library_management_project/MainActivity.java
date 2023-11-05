package com.example.library_management_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.UserBook;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    //initialize widgets
    ListView userBookListView;
    Button logoutButton, searchBooksButton, addBooksButton;
    String currentUserPhoneNumber;
    DatabaseReference usersDatabase, userBookDatabase,  userBookReference;



    //SharedPref
    SharedPreferences sharedPrefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize(){

        //Initialize widgets
        userBookListView = findViewById(R.id.userBookListView);
        logoutButton = findViewById(R.id.logoutButton);
        searchBooksButton = findViewById(R.id.searchBooksButton);
        addBooksButton = findViewById(R.id.addBooksButton);

        //Set onclick listeners
        logoutButton.setOnClickListener(this);
        searchBooksButton.setOnClickListener(this);

        //SharedPref
        // Get the user's phone number from SharedPreferences
        sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        currentUserPhoneNumber = sharedPrefs.getString("userPhoneNumber", "");

        if (!currentUserPhoneNumber.isEmpty()) {
            // Use the userPhoneNumber to fetch user-related data and populate the table.
            usersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            userBookDatabase = FirebaseDatabase.getInstance().getReference().child("UserBook");
            userBookReference = FirebaseDatabase.getInstance().getReference().child("UserBook");

            // Set a click listener for the "Add Books" button
            addBooksButton.setOnClickListener(this);

            displayUserBookData();
        } else {
            // Handle the case where the phone number is not available.
            // You can redirect the user to the login page or take other appropriate actions.
        }



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id== R.id.logoutButton){
            loggingOut();
        }if(id== R.id.addBooksButton){
            addBooks();
        }if(id== R.id.searchBooksButton){
            searchBooks();
        }
    }

    private void searchBooks() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    private void addBooks() {

    }

    private void loggingOut() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);

    }


    private void displayUserBookData() {
        DatabaseReference userBookReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserPhoneNumber).child("userBooks");

        userBookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserBook> userBooks = new ArrayList<>();

                for (DataSnapshot userBookSnapshot : dataSnapshot.getChildren()) {
                    UserBook userBook = userBookSnapshot.getValue(UserBook.class);

                    // Get the dateUpdated from the database and set it in the UserBook object
                    String dateUpdated = userBookSnapshot.child("dateUpdated").getValue(String.class);
                    if (userBook != null) {
                        userBook.setDateUpdated(dateUpdated);
                        userBooks.add(userBook);
                    }
                }

                // Create an adapter to display the user books in a ListView
                ArrayAdapter<UserBook> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, userBooks);
                userBookListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MainActivity", "Error fetching data: " + databaseError.getMessage());
            }
        });
    }

















}

