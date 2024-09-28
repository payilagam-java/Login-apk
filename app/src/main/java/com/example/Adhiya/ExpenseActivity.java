package com.example.Adhiya;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class ExpenseActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ListView listView;
    private FloatingActionButton addContactButton;
    //  private ContactDatabaseHelper dbHelper;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;
    private RetrofitAPI retrofitAPI;
    private static final int REQUEST_ADD_CONTACT = 1;
    List<ExpenseModal> b;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });


        listView = findViewById(R.id.contactsListView);
        getExpenseList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        searchView = findViewById(R.id.searchView);
        listView.setTextFilterEnabled(true);
        setupSearchView();
    }
    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(ExpenseActivity.this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void getExpenseList() {

        retrofitAPI = ApiClient.getApiClient();
        Dialog dialog = ProgressUtil.showProgress(ExpenseActivity.this);

        Call<ExpenseModal> call = retrofitAPI.getExpense("0");
        call.enqueue(new Callback<ExpenseModal>() {
            @Override
            public void onResponse(Call<ExpenseModal> call, Response<ExpenseModal> response) {
                dialog.dismiss();
                if (response.code() == 200) {                // this method is called when we get response from our api.
                    ExpenseModal responseFromAPI = response.body();
                    b = responseFromAPI.getResult();
                    ExpenseAdapter adapter = new ExpenseAdapter(ExpenseActivity.this, b);
                    ListView listView = (ListView) findViewById(R.id.contactsListView);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ExpenseModal> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(ExpenseActivity.this, "failed added to API" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
