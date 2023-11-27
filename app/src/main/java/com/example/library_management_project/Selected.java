package com.example.library_management_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Selected extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        // Retrieve the ISBN from the intent
        String isbn = getIntent().getStringExtra("isbn");

        // Set the ISBN in the EditText
        EditText isbnEditText = findViewById(R.id.isbnEditText);
        isbnEditText.setText(isbn);

        // Set up the spinner with an adapter
        Spinner typeOfActionSpinner = findViewById(R.id.typeOfActionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.type_of_action_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfActionSpinner.setAdapter(adapter);

        // Assuming you have a button with the ID "selectButton" in your XML
        Button selectButton = findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected type of action from the spinner
                String selectedTypeOfAction = typeOfActionSpinner.getSelectedItem().toString();

                // Retrieve the user's information from SharedPreferences
                String userPhoneNumber = getUserPhoneNumberFromSharedPreferences();

                try {
                    // Ensure userPhoneNumber is not empty before proceeding
                    if (!userPhoneNumber.isEmpty()) {
                        // Get the current date as the key
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = dateFormat.format(new Date());

                        // Now create a JSON structure and update the User table in the database
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("Users");
                        DatabaseReference newUserReference = userReference.child(userPhoneNumber).child("userBooks").child(currentDate);

                        // Create a UserBooks object
                        Map<String, Object> userBooks = new HashMap<>();
                        userBooks.put("dateUpdated", currentDate);
                        userBooks.put("isbn", isbn);
                        userBooks.put("typeOfAction", selectedTypeOfAction);

                        // Update the User table with the userBooks information
                        newUserReference.setValue(userBooks);

                        Toast.makeText(v.getContext(),"Database update successful",Toast.LENGTH_LONG).show();

                        // Go back to the Search activity
                        finish();
                    }
                }catch (Exception e){
                    Toast.makeText(v.getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Helper method to retrieve user's phone number from SharedPreferences
    private String getUserPhoneNumberFromSharedPreferences() {
        // Replace with your actual key for user phone number in SharedPreferences
        String keyUserPhoneNumber = "userPhoneNumber";
        // Retrieve user's phone number from SharedPreferences
        return getSharedPreferences("MyPrefs", MODE_PRIVATE).getString(keyUserPhoneNumber, "");
    }
}



