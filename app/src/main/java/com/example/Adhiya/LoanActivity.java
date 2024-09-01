package com.example.Adhiya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.Adhiya.adapter.LoanAdapter;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ActionUtil;
import com.example.Adhiya.util.CommonUtil;
import com.example.Adhiya.util.Datautil;
import com.example.splash.Contact;
import com.example.splash.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LoanActivity extends AppCompatActivity implements ActionUtil {

    private ListView listView;
    private FloatingActionButton addButton;
    //  private ContactDatabaseHelper dbHelper;
    private ArrayAdapter<BorrowerLoanModal> adapter;
    private List<Contact> contacts;
    private RetrofitAPI retrofitAPI;
    private static final int REQUEST_ADD_CONTACT = 1;
    ApiClient apiClient = new ApiClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        CommonUtil.getTitleBar(this,"Borrower Loan");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Borrower Loan");
//        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // back button pressed
//                finish();
//            }
//        });

        getList();
        listView = findViewById(R.id.ListView);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this, LoanAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void successAction(ResponseModal responseModal) {
        List loan_list = responseModal.getObject();
        ArrayAdapter adapter = new LoanAdapter(LoanActivity.this, loan_list);
        listView.setAdapter(adapter);
    }

    private void getList() {

        retrofitAPI = ApiClient.getApiClient();
        apiClient.getResponse(retrofitAPI.getLoan(Datautil.GETALL), this);
//        Call<BorrowerLoanModal> call = retrofitAPI.getLoan(body);
//        call.enqueue(new Callback<BorrowerLoanModal>() {
//            @Override
//            public void onResponse(Call<BorrowerLoanModal> call, Response<BorrowerLoanModal> response) {
//
//                if(response.code() == 200) {                // this method is called when we get response from our api.
//                    BorrowerLoanModal responseFromAPI = response.body();
//                    List<BorrowerLoanModal> b = responseFromAPI.getLoan();
//
//                  //  Toast.makeText(LoanActivity.this, "response added to API"+response.body(), Toast.LENGTH_SHORT).show();
//                    ArrayAdapter adapter = new LoanAdapter(LoanActivity.this, b);
//                    listView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BorrowerLoanModal> call, Throwable t) {
//
//                Toast.makeText(LoanActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
