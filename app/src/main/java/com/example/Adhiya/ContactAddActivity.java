package com.example.Adhiya;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.ContactDatabaseHelper;
import com.example.splash.R;

public class ContactAddActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextContact;

    private ContactDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_borrower);

        dbHelper = new ContactDatabaseHelper(this);

        editTextName = findViewById(R.id.editTextFirstName);
        editTextContact = findViewById(R.id.editTextLasttName);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String contact = editTextContact.getText().toString().trim();

            if (name.isEmpty() || contact.isEmpty()) {
                Toast.makeText(ContactAddActivity.this, "Please enter name and contact", Toast.LENGTH_SHORT).show();
            } else {
                long id = dbHelper.addContact(name, contact);
                if (id != -1) {
                    Toast.makeText(ContactAddActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Finish activity after saving contact
                } else {
                    Toast.makeText(ContactAddActivity.this, "Failed to add contact", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
