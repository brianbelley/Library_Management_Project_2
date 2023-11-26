package com.example.library_management_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.Book;

public class AddBooks extends AppCompatActivity {
    Button addBtn, backBtn;
    EditText isbn, author, title, date;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        addBtn = findViewById(R.id.addButton);
        backBtn = findViewById(R.id.backButton);

        isbn = findViewById(R.id.isbnEditText);
        author = findViewById(R.id.authorEditText);
        title = findViewById(R.id.titleEditText);
        date = findViewById(R.id.dateEditText);



       addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               database = FirebaseDatabase.getInstance();
               reference = database.getReference("Book");

               String isbnBook = isbn.getText().toString().trim();
               String authorBook = author.getText().toString().trim();
               String titleBook = title.getText().toString().trim();
               String dateBook = date.getText().toString().trim();

              // String dateText = dateEditText.getText().toString();

                // Parse the date using SimpleDateFormat
               /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
               Date selectedDate = null;


               try {
                   selectedDate = dateFormat.parse(dateBook);
               } catch (Exception e) {
                   Toast.makeText(AddBooks.this,"Incorrect Date format, the format is: yyyy-mm-dd", Toast.LENGTH_SHORT).show();
                   finish();
               }*/


               if(isbnBook.isEmpty() || authorBook.isEmpty() || titleBook.isEmpty() || dateBook.isEmpty()){
                   Toast.makeText(AddBooks.this, "All the field must be filled in order to add a book", Toast.LENGTH_SHORT).show();

               }

               else {
                   Book model = new Book( isbnBook, authorBook, titleBook, dateBook);
                   reference.child(isbnBook).setValue(model);

                   Toast.makeText(AddBooks.this, "Book added successfully", Toast.LENGTH_SHORT).show();

               }

           }
       });

       backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(AddBooks.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       });



    }
}