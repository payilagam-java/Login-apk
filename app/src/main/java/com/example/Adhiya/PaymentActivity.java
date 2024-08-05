package com.example.Adhiya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.adapter.MySpinnerAdapter;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.DataProccessor;
import com.example.splash.R;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private TextView borrowerName;
    private TextView lineName;
    private TextView payableAmount;
    private TextView balance;
    private EditText editPayAmount;
    private Spinner spinnerMode;
    private CollectionModal Refer;
    private RetrofitAPI retrofitAPI;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payout);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
         Refer = (CollectionModal) args.getSerializable("payloan");

        borrowerName = findViewById(R.id.borrowerName);
        lineName = findViewById(R.id.lineName);
        payableAmount = findViewById(R.id.payableAmount);
        editPayAmount = findViewById(R.id.editPayAmount);
        spinnerMode = findViewById(R.id.spinnerMode);
        balance = findViewById(R.id.balance);

        borrowerName.setText(Refer.getBorrowerName());
        lineName.setText(Refer.getLineName());
        payableAmount.setText(Refer.getPayableAmount());
        editPayAmount.setText(Refer.getPayAmount());
        balance.setText(Refer.getBalance());

        ArrayList MarirtalModal= new ArrayList<SpinnerModal>();
        MarirtalModal.add(new SpinnerModal("CashOnHand","1"));
        MarirtalModal.add(new SpinnerModal("GPay","2"));
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(PaymentActivity.this,
                android.R.layout.simple_spinner_item, MarirtalModal);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(adapter1);
        spinnerMode.setOnItemSelectedListener(onItemSelectedListener0);

        Button buttonSave = findViewById(R.id.pay);

        buttonSave.setOnClickListener(v -> {
            Refer.setPayAmount(editPayAmount.getText().toString().trim());
            Refer.setDateOfPaid(DataProccessor.getDate());
            postData(Refer);
        });
    }
    AdapterView.OnItemSelectedListener onItemSelectedListener0 =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    SpinnerModal s = (SpinnerModal)parent.getItemAtPosition(position);
                    Refer.setPaymentMode(s.getValue());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };

    private void postData(CollectionModal collectionModal) {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        Call<ResponseModal> call = retrofitAPI.payment(collectionModal);

        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {

                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    Toast.makeText(PaymentActivity.this, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent borrowerIntent = new Intent(PaymentActivity.this, CollectionListActivity.class);
//                    startActivity(borrowerIntent);
                    finish();
                }else{

                }
            }

            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
