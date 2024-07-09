package com.example.Adhiya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
public class BorrowerActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton addContactButton;
    private ContactDatabaseHelper dbHelper;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;

    private static final int REQUEST_ADD_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);

        dbHelper = new ContactDatabaseHelper(this);

        listView = findViewById(R.id.contactsListView);
        addContactButton = findViewById(R.id.addContactButton);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerActivity.this, ContactAddActivity.class);
                startActivityForResult(intent, REQUEST_ADD_CONTACT);
            }
        });

        // Display contacts when activity starts
        displayContacts();
    }

    private void displayContacts() {
        contacts = dbHelper.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK) {
            // Refresh contact list after adding a contact
            displayContacts();
        }
    }
}
