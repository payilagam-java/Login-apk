package com.example.Adhiya;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.Adhiya.adapter.MySpinnerAdapter;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.LineModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.CommonUtil;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanAddActivity extends AppCompatActivity {

    private TextView borrower;
    private TextView line;
    private TextView linetype;
    private TextView deducted;

    private TextView interest;
    private EditText    laonamount;
    private TextView intamount;
    private TextView distursed;
    private TextView payable;

    private TextView daily;
    private TextView payAmt;
    private TextView nodays;
    private TextView duration;
    private TextView beforeInt;
    private TextView disdate;
    private TextView duedate;

    private TextView cashOnHand;
    private Spinner status;

    private RetrofitAPI retrofitAPI;
    private Dialog dialog;

        List<BorrowerModal> b;
    List<LineModal> lineList;
    BorrowerLoanModal blm = new BorrowerLoanModal();
    LineModal bm;
    void calc(){
        double loan = Double.parseDouble(laonamount.getText().toString());
        double intr = Double.parseDouble(interest.getText().toString());
        double day = Double.parseDouble(nodays.getText().toString());
        double intamt = loan*intr/100;

//        if(bm.getLineType() == 2){
//            day = day*7;
//        }
//        if(bm.getLineType() ==3){
//            day = day * 30;
//        }
        if(beforeInt.getText().toString().equals("No")){
            distursed.setText(laonamount.getText());
            intamount.setText(String.valueOf(intamt));
            payable.setText(String.valueOf(loan+intamt));
            daily.setText(String.valueOf(Math.round((loan+intamt)/day)));
       }else{
            distursed.setText(String.valueOf(loan-intamt));
            intamount.setText(String.valueOf(intamt));
            payable.setText(String.valueOf(loan));
            daily.setText(String.valueOf(Math.round((loan)/day)));
        }
    }

    private String lineStr ;
    private String bwrStr ;
    private String lAmtStr ;
    private String disDateStr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_loan);
        CommonUtil.getTitleBar(this,"Borrower Loan");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Add Loan");
//        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // back button pressed
//                finish();
//            }
//        });

        cashOnHand = findViewById(R.id.cashOnHand);
        //Get the object from input fields
        borrower = findViewById(R.id.borrower);
        line = findViewById(R.id.line);
        linetype = findViewById(R.id.linetype);
        deducted = findViewById(R.id.deducted);
        interest = findViewById(R.id.interest);
        laonamount = findViewById(R.id.laonamount);
        intamount = findViewById(R.id.intamount);
        distursed = findViewById(R.id.distursed);
        payable = findViewById(R.id.payable);
        payAmt = findViewById(R.id.payAmt);
        daily = findViewById(R.id.daily);
        nodays = findViewById(R.id.noofdays);
        duration = findViewById(R.id.duration);
        beforeInt = findViewById(R.id.before);
        disdate = findViewById(R.id.disdate);
        duedate = findViewById(R.id.duedate);
        //status = findViewById(R.id.status);
        ArrayList laonstatus= new ArrayList<SpinnerModal>();
        laonstatus.add(new SpinnerModal("Active","Active"));
        laonstatus.add(new SpinnerModal("Closed","Closed"));
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(LoanAddActivity.this,
                android.R.layout.simple_spinner_item, laonstatus);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        status.setAdapter(adapter1);
//        status.setOnItemSelectedListener(onItemSelectedListener1);


        laonamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // this function is called before text is edited
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // this function is called when text is edited
                Toast.makeText(LoanAddActivity.this,beforeInt.getText().toString(),Toast.LENGTH_SHORT).show();
                if(!(beforeInt.getText().toString().equals("")) && !(laonamount.getText().toString().equals(""))) {
                    calc();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // this function is called after text is edited
            }
        } );
        disdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        LoanAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                disdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                              //  duedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                String dateStr = year+"-"+String.format("%02d", (monthOfYear + 1))+"-"+String.format("%02d", dayOfMonth);
                                blm.setDisbursedDate(dateStr);
                                int noofday = Integer.parseInt(nodays.getText().toString() !=""? nodays.getText().toString() : "0");
                                if(bm.getLineType() == 2){
                                    noofday = noofday*7;
                                }
                                if(bm.getLineType() ==3){
                                    noofday = noofday * 30;
                                }
                                blm.setDueDate(CommonUtil.addDay(dateStr,noofday));
                                duedate.setText(CommonUtil.addDisplayDay(dateStr,noofday));
                            }
                        },
                        year, month, day);
                //Prevent Future date
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

//        duedate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        // on below line we are passing context.
//                        LoanAddActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // on below line we are setting date to our text view.
//                                duedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                blm.setDueDate(year+"-"+String.format("%02d", (monthOfYear + 1))+"-"+String.format("%02d", dayOfMonth));
//                            }
//                        },
//                        year, month, day);
//                datePickerDialog.show();
//            }
//        });

        getBorrowerList("0");
        borrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList ReferModal= new ArrayList<SpinnerModal>();
                for(BorrowerModal bm :b){
                    if( blm.getLineId().equals(bm.getLineId())) {
                        ReferModal.add(new SpinnerModal(bm.getFirstName(), String.valueOf(bm.getId())));
                    }
                }
                Dialog dialog = ProgressUtil.spinnerProgress(LoanAddActivity.this);
                ListView listView=dialog.findViewById(R.id.list_view);
                MySpinnerAdapter adapter=new MySpinnerAdapter(LoanAddActivity.this, R.layout.custom_spinner_item,ReferModal);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String text = adapter.getItem(position).getText();
                        String value =adapter.getItem(position).getValue();
                        borrower.setText(text);
                        blm.setBorrowerId(value);
                        blm.setBId(Integer.valueOf(value));
                        blm.setBorrowerName(text);
                        dialog.dismiss();
                    }
                });
            }
        });

        getLineList("0");
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList ReferModal= new ArrayList<SpinnerModal>();
                for(LineModal bm :lineList){
                    ReferModal.add(new SpinnerModal(bm.getLineName(),bm.getId()));
                }
                Dialog dialog = ProgressUtil.spinnerProgress(LoanAddActivity.this);
                ListView listView=dialog.findViewById(R.id.list_view);
                MySpinnerAdapter adapter=new MySpinnerAdapter(LoanAddActivity.this, R.layout.custom_spinner_item,ReferModal);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        line.setText(adapter.getItem(position).getText());
                        blm.setLineId(adapter.getItem(position).getValue());
                        blm.setLineName(adapter.getItem(position).getText());
                         bm =lineList.get(position);
                        linetype.setText(bm.getLineTypeName());
                        blm.setLineTypeId(bm.getLineType());
                        if(bm.getLineType() == 1){
                            duration.setText("Number of Days");
                            payAmt.setText("Daily Pay Amount");
                        }
                        if(bm.getLineType() == 2){
                            duration.setText("Number of Weeks");
                            payAmt.setText("Weekly Pay Amount");
                        }
                        if(bm.getLineType() ==3){
                            duration.setText("Number of Months");
                            payAmt.setText("Monthly Pay Amount");
                        }
                        blm.setLineTypeName(bm.getLineTypeName());

                        deducted.setText(String.valueOf(bm.getDeductionAmount()));
                        blm.setDeductionId(bm.getDeductionId());
                        blm.setDeductedAmount(String.valueOf(bm.getDeductionAmount()));

                        beforeInt.setText(bm.isBeforeInterestDeduction() == true ? "Yes":"No");


                        nodays.setText(String.valueOf(bm.getDuration()));
                        blm.setDurationId(bm.getDurationId());
                        blm.setDuration(bm.getDuration());

                        interest.setText(String.valueOf(bm.getInterest()));

                        cashOnHand.setText("cash On Hand : "+bm.getCashOnHand());
                        laonamount.getText().clear();
                        intamount.setText("");
                        distursed.setText("");
                        payable.setText("");
                        daily.setText("");
                        disdate.setText("");
                        duedate.setText("");
                        borrower.setText("");
                        dialog.dismiss();
                    }
                });
            }
        });


        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> {
            if (CheckAllFields()) {
//                blm.setLineTypeId(1);
//                blm.setLineTypeName(linetype.getText().toString().trim());
                blm.setDeductedAmount(deducted.getText().toString().trim());
                blm.setInterestAmount(interest.getText().toString().trim());
                blm.setLoanAmount(laonamount.getText().toString().trim());
                blm.setPayableAmount(payable.getText().toString().trim());
                blm.setDisbursedAmount(distursed.getText().toString().trim());
                blm.setInterestAmount(intamount.getText().toString().trim());
                blm.setLoanStatusId(1);
                blm.setLoanId("");
                blm.setPayAmount("0");
                postData(blm);
            }
        });
    }

    private boolean CheckAllFields() {
        lineStr = line.getText().toString().trim();
        bwrStr = borrower.getText().toString().trim();
        lAmtStr = laonamount.getText().toString().trim();
        disDateStr = disdate.getText().toString().trim();
        line.setError(null);

        if (lineStr.length() == 0) {
            linetype.setError("Line is required");
            return false;
        }
        if (bwrStr.length() == 0) {
            borrower.setError("Borrower is required");
            return false;
        }

        if (lAmtStr.length() == 0) {
            laonamount.setError("Date of expense is required");
            return false;
        }
        if (disDateStr.length()== 0) {
            disdate.setError("Distribute Date should not be empty");
            return false;
        }

        return true;
    }

    OnItemSelectedListener onItemSelectedListener1 =
            new OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    SpinnerModal s = (SpinnerModal)parent.getItemAtPosition(position);
                    blm.setLoanStatus(s.getValue());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };

    private void postData(BorrowerLoanModal borrowerLoanModal) {
        retrofitAPI = ApiClient.getApiClient();
        Call<ResponseModal> call = retrofitAPI.addEditLoan(borrowerLoanModal);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    Toast.makeText(LoanAddActivity.this, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();
                    if(responseFromAPI.getStatus() ==1)
                         finish();
                }else{
                    Toast.makeText(LoanAddActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(LoanAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

        private void getBorrowerList(String body){
            retrofitAPI = ApiClient.getApiClient();
            Call call = retrofitAPI.getBorrower(body);
            call.enqueue(new Callback<ResponseModal>() {
                @Override
                public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                    if(response.code() == 200) {                // this method is called when we get response from our api.
                        ResponseModal responseFromAPI = response.body();
                        b = responseFromAPI.getObject();
                    }
                }
                @Override
                public void onFailure(Call<ResponseModal> call, Throwable t) {
                    Toast.makeText(LoanAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    private void getLineList(String body){
        retrofitAPI = ApiClient.getApiClient();
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
                Toast.makeText(LoanAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
