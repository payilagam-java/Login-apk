package com.example.Adhiya;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.adapter.BorrowerAdapter;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;
import com.example.Adhiya.network.ApiClient;
import com.example.splash.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton addContactButton;
    //  private ContactDatabaseHelper dbHelper;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;
    private RetrofitAPI retrofitAPI;
    private static final int REQUEST_ADD_CONTACT = 1;
    List<BorrowerModal> b;
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
                Intent intent = new Intent(BorrowerActivity.this, BorrowerAddActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("borrower",(Serializable)b);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
              //  startActivityForResult(intent, REQUEST_ADD_CONTACT);
            }
        });

        // Display contacts when activity starts
        //  displayContacts();
    }


    private void getBorrowerList(){

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        Dialog dialog = ProgressUtil.showProgress(BorrowerActivity.this);

        Call<BorrowerModal> call = retrofitAPI.getBorrower("0");
        call.enqueue(new Callback<BorrowerModal>() {
            @Override
            public void onResponse(Call<BorrowerModal> call, Response<BorrowerModal> response) {
                dialog.dismiss();
            if(response.code() == 200) {                // this method is called when we get response from our api.
                    BorrowerModal responseFromAPI = response.body();
                    b = responseFromAPI.getResult();
                    BorrowerAdapter adapter = new BorrowerAdapter(BorrowerActivity.this, b);
                    ListView listView = (ListView) findViewById(R.id.contactsListView);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BorrowerModal> call, Throwable t) {
                Toast.makeText(BorrowerActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK) {
            getBorrowerList();
        }
    }
}
