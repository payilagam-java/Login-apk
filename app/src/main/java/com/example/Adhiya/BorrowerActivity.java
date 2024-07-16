package com.example.Adhiya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.modal.ObjectModal;
import com.example.splash.R;
import com.example.Adhiya.network.ApiClient;
import com.example.splash.Contact;
import com.example.Adhiya.ContactAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton addContactButton;
    //  private ContactDatabaseHelper dbHelper;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;
    private com.example.splash.repo.RetrofitAPI retrofitAPI;
    private static final int REQUEST_ADD_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);
        getBorrowerList();
        // dbHelper = new ContactDatabaseHelper(this);

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
        //  displayContacts();
    }


    private void getBorrowerList(){

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        //  Toast.makeText(BorrowerActivity.this, "token added to API"+token, Toast.LENGTH_SHORT).show();
        Call<ObjectModal> call = retrofitAPI.getBorrower("0");
        call.enqueue(new Callback<ObjectModal>() {
            @Override
            public void onResponse(Call<ObjectModal> call, Response<ObjectModal> response) {
                Toast.makeText(BorrowerActivity.this, "response added to API"+response.code(), Toast.LENGTH_SHORT).show();
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ObjectModal responseFromAPI = response.body();
                    Log.d("success", "This is my message " + responseFromAPI.getResult().size());
//                    Log.d("success", "This is my message "+responseFromAPI.getResult().length);
                }
            }

            @Override
            public void onFailure(Call<ObjectModal> call, Throwable t) {
                Toast.makeText(BorrowerActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayContacts() {
        //  contacts = dbHelper.getAllContacts();
        //    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        //    listView.setAdapter(adapter);
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
