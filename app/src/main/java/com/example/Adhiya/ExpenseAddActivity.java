package com.example.Adhiya;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.adapter.MySpinnerAdapter;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.ExpenseModal;
import com.example.Adhiya.modal.LineModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.DataProccessor;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseAddActivity extends AppCompatActivity {


    private TextView line;
    private EditText reason;

    private TextView expensedate;
    private EditText expenseamount;

    private RetrofitAPI retrofitAPI;
    private Dialog dialog;

    List<BorrowerModal> b;
    List<LineModal> lineList;
    ExpenseModal em = new ExpenseModal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_expense);

        //Get the object from input fields
        line = findViewById(R.id.expenseline);
        reason = findViewById(R.id.reason);
        expensedate = findViewById(R.id.expensedate);
        expenseamount = findViewById(R.id.expenseamount);

        expensedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        ExpenseAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                expensedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                em.setDateOfExpense(year+"-"+String.format("%02d", (monthOfYear + 1))+"-"+String.format("%02d", dayOfMonth));
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });


        getLineList("1");
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList ReferModal= new ArrayList<SpinnerModal>();
                for(LineModal bm :lineList){
                    ReferModal.add(new SpinnerModal(bm.getLineName(),bm.getId()));
                }
                Dialog dialog = ProgressUtil.spinnerProgress(ExpenseAddActivity.this);
                ListView listView=dialog.findViewById(R.id.list_view);
                MySpinnerAdapter adapter=new MySpinnerAdapter(ExpenseAddActivity.this, R.layout.custom_spinner_item,ReferModal);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        line.setText(adapter.getItem(position).getText());
                        em.setLineId(adapter.getItem(position).getValue());
                        em.setLineName(adapter.getItem(position).getText());
                        dialog.dismiss();
                    }
                });
            }
        });


        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            em.setExpenseReason(reason.getText().toString().trim());
            em.setAmount(expenseamount.getText().toString().trim());
            postData(em);
        });
    }



    private void postData(ExpenseModal expenseModal) {
        retrofitAPI = ApiClient.getApiClient(new DataProccessor(this).getToken());
        Call<ResponseModal> call = retrofitAPI.addEditExpense(expenseModal);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    Toast.makeText(ExpenseAddActivity.this, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ExpenseAddActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(ExpenseAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

//        private void getBorrowerList(String body){
//            retrofitAPI = ApiClient.getApiClient(new DataProccessor(this).getToken());
//            Call<BorrowerModal> call = retrofitAPI.getBorrower(body);
//            call.enqueue(new Callback<BorrowerModal>() {
//                @Override
//                public void onResponse(Call<BorrowerModal> call, Response<BorrowerModal> response) {
//                    if(response.code() == 200) {                // this method is called when we get response from our api.
//                        BorrowerModal responseFromAPI = response.body();
//                        b = responseFromAPI.getResult();
//                    }
//                }
//                @Override
//                public void onFailure(Call<BorrowerModal> call, Throwable t) {
//                    Toast.makeText(ExpenseAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

    private void getLineList(String body){
        retrofitAPI = ApiClient.getApiClient(new DataProccessor(this).getToken());
        Call<LineModal> call = retrofitAPI.getLine(body);
        call.enqueue(new Callback<LineModal>() {
            @Override
            public void onResponse(Call<LineModal> call, Response<LineModal> response) {
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    LineModal responseFromAPI = response.body();
                    lineList = responseFromAPI.getResult();
                }
            }
            @Override
            public void onFailure(Call<LineModal> call, Throwable t) {
                Toast.makeText(ExpenseAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
