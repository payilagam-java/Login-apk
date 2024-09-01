package com.example.Adhiya;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SendCollection;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.CommonUtil;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private TextView borrowerName;
    private TextView lineName;
    private TextView payableAmount;
    private TextView balance;
    private TextView datePay;
    private EditText editPayAmount;
    private Spinner spinnerMode;
    private CollectionModal Refer;
    private RetrofitAPI retrofitAPI;

    SendCollection send;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payout);
        CommonUtil.getTitleBar(this, "Payment");
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
         send = (SendCollection) args.getSerializable("date");
        Refer = (CollectionModal) args.getSerializable("payloan");

        borrowerName = findViewById(R.id.borrowerName);
        lineName = findViewById(R.id.lineName);
        payableAmount = findViewById(R.id.payableAmount);
        editPayAmount = findViewById(R.id.editPayAmount);
        spinnerMode = findViewById(R.id.spinnerMode);
        balance = findViewById(R.id.balance);
        datePay = findViewById(R.id.dateofpay);
        datePay.setText(send.getCollectiondate());
        borrowerName.setText(Refer.getBorrowerName());
        lineName.setText(Refer.getLineName());
        payableAmount.setText(Refer.getPayableAmount());
        if (Refer.getAmountofPaid().equals("0.00")) {
            editPayAmount.setText(Refer.getPayAmount());
        } else {
            editPayAmount.setText(Refer.getAmountofPaid());
        }
        float bal = Float.parseFloat(Refer.getPayableAmount()) - Float.parseFloat(Refer.getBalance());
        balance.setText(String.valueOf(bal));

        ArrayList paymentModal = new ArrayList<SpinnerModal>();
        paymentModal.add(new SpinnerModal("CashOnHand", "1"));
        paymentModal.add(new SpinnerModal("GPay", "2"));
        paymentModal.add(new SpinnerModal("PhonePay", "3"));
        paymentModal.add(new SpinnerModal("NetBanking", "4"));
        paymentModal.add(new SpinnerModal("OtherUPI", "5"));
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(PaymentActivity.this,
                android.R.layout.simple_spinner_item, paymentModal);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(adapter1);
        spinnerMode.setOnItemSelectedListener(onItemSelectedListener0);
        spinnerMode.setSelection(Integer.parseInt(Refer.getPaymentMode()));
        Button buttonSave = findViewById(R.id.pay);

        buttonSave.setOnClickListener(v -> {
            Refer.setAmountofPaid(editPayAmount.getText().toString().trim());
            Refer.setDateOfPaid(send.getCollectiondate());
            postData(Refer);
        });
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener0 =
            new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    SpinnerModal s = (SpinnerModal) parent.getItemAtPosition(position);
                    Refer.setPaymentMode(s.getValue());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };

    private void postData(CollectionModal collectionModal) {
        retrofitAPI = ApiClient.getApiClient();
        Call<ResponseModal> call = retrofitAPI.payment(collectionModal);
        Dialog dialog = ProgressUtil.showProgress(PaymentActivity.this);

        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                dialog.dismiss();
                if (response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
 //                   Toast.makeText(PaymentActivity.this, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
//                    Intent borrowerIntent = new Intent(PaymentActivity.this, CollectionListActivity.class);
//                    Bundle args = new Bundle();
//                    args.putSerializable("collection",(Serializable)send);
//                    borrowerIntent.putExtra("BUNDLE",args);
//                    startActivity(borrowerIntent);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "failed added to API" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
