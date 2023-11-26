package com.example.library_management_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.Book;


public class Search extends AppCompatActivity {
    private Spinner searchSpinner;
    private Button searchButton;

    private Button backButton;

    private ListView resultListView;
    private ArrayAdapter<Book> bookAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spn = findViewById(R.id.spinner);

        resultListView = findViewById(R.id.displayBooksListView);
        bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());

        searchSpinner = findViewById(R.id.spinner);
        searchButton = findViewById(R.id.searchBooksButton);
        backButton = findViewById(R.id.buttonBack);


        ArrayList<String> searchType = new ArrayList<String>();
        searchType.add("IBSN");
        searchType.add("Title");
        searchType.add("Author");
        ArrayAdapter<String> adapterSet = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, searchType);
        spn.setAdapter(adapterSet);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBook();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this,MainActivity.class);
                startActivity(intent);
                // finish();
            }
        });
    }
    public void checkBook() {
        int index = searchSpinner.getSelectedItemPosition();
        String path = "";
        switch(index){
            case 0:
                path = "isbn";
                break;
            case 1:
                path = "title";
                break;
            case 2:
                path = "author";
                break;
        }

        EditText search = findViewById(R.id.searchEditText);
        String searchBy = search.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Book");
        Query checkUserData = reference.orderByChild(path).equalTo(searchBy);
        checkUserData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Book> bookList = new ArrayList<>();
                resultListView.setAdapter(bookAdapter);
                if (snapshot.exists()) {

                    for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                        String isbn = bookSnapshot.child("isbn").getValue(String.class);
                        String author = bookSnapshot.child("author").getValue(String.class);
                        String title = bookSnapshot.child("title").getValue(String.class);

                        Calendar calendar = Calendar.getInstance();
                        Date today = calendar.getTime();

                        model.Book book = new model.Book(isbn, author, title, today);
                        bookList.add(book);
                    }

                    // Create an adapter to display the books in a ListView
                    ArrayAdapter<model.Book> bookAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, bookList);
                    resultListView.setAdapter(bookAdapter);

                    // Set item click listener to handle selection
                    resultListView.setOnItemClickListener((parent, view, position, id) -> {
                        // Retrieve the selected item and extract ISBN
                        model.Book selectedBook = bookList.get(position);
                        String selectedIsbn = selectedBook.getIsbn();

                        // Navigate to the Selected activity with the selected ISBN
                        navigateToSelectedActivity(selectedIsbn);
                    });
                } else {
                    // Handle the case where the book is not found
                }
            }
            private void navigateToSelectedActivity(String isbn) {
                Intent intent = new Intent(Search.this, Selected.class);
                // Pass the selected ISBN to the Selected activity
                intent.putExtra("isbn", isbn);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
