package com.example.library_management_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;


public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spn = findViewById(R.id.spinner);

        ArrayList<String> searchType = new ArrayList<String>();
        searchType.add("IBM");
        searchType.add("Title");
        searchType.add("Author");
        searchType.add("Year Released");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,searchType);
        spn.setAdapter(adapter);
       /*
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(SpinnerTest.this, item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        */
    }
}