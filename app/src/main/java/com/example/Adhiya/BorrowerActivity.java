package com.example.Adhiya;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.Adhiya.adapter.BorrowerAdapter;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ActionUtil;
import com.example.Adhiya.util.CommonUtil;
import com.example.Adhiya.util.Datautil;
import com.example.splash.R;
import com.example.Adhiya.network.ApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BorrowerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ActionUtil {

    private ListView listView;
    private FloatingActionButton addButton;
    private RetrofitAPI retrofitAPI;
    SearchView searchView;
    ApiClient apiClient = new ApiClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower);
        CommonUtil.getTitleBar(this,"Borrower");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        retrofitAPI = apiClient.getApiClient();
        apiClient.getResponse(retrofitAPI.getBorrower(Datautil.GETALL), this);

        listView = findViewById(R.id.contactsListView);
        addButton = findViewById(R.id.addContactButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerActivity.this, BorrowerAddActivity.class);
                startActivity(intent);
            }
        });
        searchView = findViewById(R.id.searchView);
        listView.setTextFilterEnabled(true);
        setupSearchView();

        //getBorrowerList();
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(BorrowerActivity.this);
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

    @Override
    public void successAction(ResponseModal responseModal) {
       List borrower_list = responseModal.getObject();
        BorrowerAdapter adapter = new BorrowerAdapter(BorrowerActivity.this, borrower_list);
        ListView listView = (ListView) findViewById(R.id.contactsListView);
        listView.setAdapter(adapter);
    }

    //
//    private void getBorrowerList(){
//        retrofitAPI = ApiClient.getApiClient();
//        Dialog dialog = ProgressUtil.showProgress(BorrowerActivity.this);
//
//        Call<BorrowerModal> call = retrofitAPI.getBorrower("0");
//        call.enqueue(new Callback<BorrowerModal>() {
//            @Override
//            public void onResponse(Call<BorrowerModal> call, Response<BorrowerModal> response) {
//                dialog.dismiss();
//            if(response.code() == 200) {                // this method is called when we get response from our api.
//                    BorrowerModal responseFromAPI = response.body();
//                    b = responseFromAPI.getResult();
//                    BorrowerAdapter adapter = new BorrowerAdapter(BorrowerActivity.this, b);
//                    ListView listView = (ListView) findViewById(R.id.contactsListView);
//                    listView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BorrowerModal> call, Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(BorrowerActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
