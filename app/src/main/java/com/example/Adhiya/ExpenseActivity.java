package com.example.Adhiya;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.adapter.BorrowerAdapter;
import com.example.Adhiya.adapter.ExpenseAdapter;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ExpenseModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.Contact;
import com.example.splash.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton addContactButton;
    //  private ContactDatabaseHelper dbHelper;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;
    private RetrofitAPI retrofitAPI;
    private static final int REQUEST_ADD_CONTACT = 1;
    List<ExpenseModal> b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        listView = findViewById(R.id.contactsListView);
        getExpenseList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {

//                ExpenseModal cm = b.get(position);
//                Intent intent = new Intent(ExpenseActivity.this, ExpenseAddActivity.class);
//                startActivity(intent);
            }
        });
        addContactButton = findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseActivity.this, ExpenseAddActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void getExpenseList(){

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        Dialog dialog = ProgressUtil.showProgress(ExpenseActivity.this);

        Call<ExpenseModal> call = retrofitAPI.getExpense("0");
        call.enqueue(new Callback<ExpenseModal>() {
            @Override
            public void onResponse(Call<ExpenseModal> call, Response<ExpenseModal> response) {
                dialog.dismiss();
            if(response.code() == 200) {                // this method is called when we get response from our api.
                ExpenseModal responseFromAPI = response.body();
                    b = responseFromAPI.getResult();
                ExpenseAdapter adapter = new ExpenseAdapter(ExpenseActivity.this, b);
                    ListView listView = (ListView) findViewById(R.id.contactsListView);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ExpenseModal> call, Throwable t) {
                Toast.makeText(ExpenseActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
