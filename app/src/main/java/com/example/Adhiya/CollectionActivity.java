package com.example.Adhiya;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Adhiya.adapter.BorrowerAdapter;
import com.example.Adhiya.adapter.MySpinnerAdapter;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.modal.LineModal;
import com.example.Adhiya.modal.OraganizationModal;
import com.example.Adhiya.modal.SendCollection;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.modal.UserModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionActivity extends AppCompatActivity {
    private TextView spinnerOrg;
    private TextView spinnerLine;
    private TextView collDate;
    private Button button;
    Dialog dialog;
    private RetrofitAPI retrofitAPI;
    private OraganizationModal orgModal = new OraganizationModal();
    private LineModal lineModal = new LineModal();
    private Button borrowerButton;

    private SendCollection sendCollection = new SendCollection();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_collection);
        spinnerOrg= findViewById(R.id.spinnerOrgnization);
        spinnerOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColectionList();
            }
        });

        spinnerLine= findViewById(R.id.spinnerLine);
        spinnerLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLineList();
            }
        });

        collDate= findViewById(R.id.editTextDate);
        collDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        CollectionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                collDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                sendCollection.setCollectiondate(year+"-"+(monthOfYear + 1) +"-"+dayOfMonth);
                            }
                        },

                        year, month, day);

                datePickerDialog.show();
            }
        });

        button =findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();

            }
        });


    }

    private void postData( ){
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        Dialog dialog = ProgressUtil.showProgress(CollectionActivity.this);

        Call<CollectionModal> call = retrofitAPI.getCollect(sendCollection);
        call.enqueue(new Callback<CollectionModal>() {
            @Override
            public void onResponse(Call<CollectionModal> call, Response<CollectionModal> response) {
                dialog.dismiss();
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    CollectionModal responseFromAPI = response.body();
                   ArrayList<CollectionModal> b = responseFromAPI.getResult();
                    Intent intent = new Intent(CollectionActivity.this, CollectionListActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("collection",(Serializable)b);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<CollectionModal> call, Throwable t) {
                Toast.makeText(CollectionActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getColectionList(){

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        Dialog dialog1 = ProgressUtil.showProgress(CollectionActivity.this);
        Call<OraganizationModal> call = retrofitAPI.getCollection("0");
        call.enqueue(new Callback<OraganizationModal>() {
            @Override
            public void onResponse(Call<OraganizationModal> call, Response<OraganizationModal> response) {
                dialog1.dismiss();
                if(response.code() == 200) {
                    OraganizationModal responseFromAPI = response.body();
                    List<OraganizationModal> b = responseFromAPI.getResult();
                    // this method is called when we get response from our api.
                    dialog=new Dialog(CollectionActivity.this);
                    dialog.setContentView(R.layout.dialog_searchable_spinner);
                    dialog.getWindow().setLayout(650,800);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    ListView listView=dialog.findViewById(R.id.list_view);

                    ArrayList orgData= new ArrayList<SpinnerModal>();
                    for(OraganizationModal org :b){
                    orgData.add(new SpinnerModal(org.getOrganizationName(),org.getId()));
                    }
                    MySpinnerAdapter adapter=new MySpinnerAdapter(CollectionActivity.this, R.layout.custom_spinner_item,orgData);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            spinnerOrg.setText(adapter.getItem(position).getText());
                            sendCollection.setOrganizationId(adapter.getItem(position).getValue());
                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<OraganizationModal> call, Throwable t) {
                Toast.makeText(CollectionActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getLineList(){

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String token = sh.getString("token", "");
        retrofitAPI = ApiClient.getApiClient(token);
        Dialog dialog1 = ProgressUtil.showProgress(CollectionActivity.this);
        Call<LineModal> call = retrofitAPI.getLine("0");
        call.enqueue(new Callback<LineModal>() {
            @Override
            public void onResponse(Call<LineModal> call, Response<LineModal> response) {
                dialog1.dismiss();
                if(response.code() == 200) {
                    LineModal responseFromAPI = response.body();
                    List<LineModal> b = responseFromAPI.getResult();
                    // this method is called when we get response from our api.
                    dialog=new Dialog(CollectionActivity.this);
                    dialog.setContentView(R.layout.dialog_searchable_spinner);
                    dialog.getWindow().setLayout(650,800);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    ListView listView=dialog.findViewById(R.id.list_view);

                    ArrayList orgData= new ArrayList<SpinnerModal>();
                    for(LineModal org :b){
                        orgData.add(new SpinnerModal(org.getLineName(),org.getId()));
                    }
                    MySpinnerAdapter adapter=new MySpinnerAdapter(CollectionActivity.this, R.layout.custom_spinner_item,orgData);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            spinnerLine.setText(adapter.getItem(position).getText());
                            sendCollection.setLineId(adapter.getItem(position).getValue());
                            // Dismiss dialog
                            dialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<LineModal> call, Throwable t) {
                Toast.makeText(CollectionActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}