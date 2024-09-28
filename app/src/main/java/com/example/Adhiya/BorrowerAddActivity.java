package com.example.Adhiya;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.Adhiya.adapter.MySpinnerAdapter;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.LineModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SpinnerModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ActionUtil;
import com.example.Adhiya.util.CommonUtil;
import com.example.Adhiya.util.GPSTracker;
import com.example.Adhiya.util.ProgressUtil;

import com.example.splash.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerAddActivity extends AppCompatActivity implements ActionUtil {

    private EditText editTextFirstName;
    private EditText editTextLasttName;
    private Spinner spinnerGender;
    private Spinner spinnerMaritalStatus;
    private EditText editMobile;
    private EditText editOccupation;
    private TextView spinnerReferred;
    private TextView spinnerLine;
    private RetrofitAPI retrofitAPI;
    private BorrowerModal borrowerModal = new BorrowerModal();
    BorrowerModal editBrw;
    Dialog dialog1;
    List<BorrowerModal> b;
    List<LineModal> lineList;
    ApiClient apiClient = new ApiClient(this);
    private final int REQUEST_FINE_LOCATION = 1234;
    LocationManager locationManager;
    Context mContext;
    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            borrowerModal.setLatitude(latitude+"");
            borrowerModal.setLongitude(longitude+"");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {   }

        @Override
        public void onProviderEnabled(String provider) {   }

        @Override
        public void onProviderDisabled(String provider) {    }
    };

    private void isLocationEnabled() {

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_borrower);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        }

        mContext=this;
        locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                2000,
                10, locationListenerGPS);
        isLocationEnabled();

        //Get the object from input fields
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLasttName = findViewById(R.id.editTextLasttName);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerMaritalStatus = findViewById(R.id.spinnerMaritalStatus);
        editMobile = findViewById(R.id.editMobile);
        editOccupation = findViewById(R.id.editOccupation);
        spinnerReferred = findViewById(R.id.spinnerReferred);
        spinnerLine = findViewById(R.id.spinnerLine);


        borrowerModal.setDob("1999-01-01");
        ArrayList genderModal = new ArrayList<SpinnerModal>();
        genderModal.add(new SpinnerModal("-- None --", ""));
        genderModal.add(new SpinnerModal("Male", "1"));
        genderModal.add(new SpinnerModal("Female", "2"));
        genderModal.add(new SpinnerModal("Other", "3"));
        MySpinnerAdapter adapter0 = new MySpinnerAdapter(BorrowerAddActivity.this,
                R.layout.custom_spinner_item, genderModal);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter0);
        spinnerGender.setOnItemSelectedListener(onItemSelectedListener0);

        ArrayList MarirtalModal = new ArrayList<SpinnerModal>();
        MarirtalModal.add(new SpinnerModal("-- None --", "0"));
        MarirtalModal.add(new SpinnerModal("Single", "1"));
        MarirtalModal.add(new SpinnerModal("Married", "2"));
        MySpinnerAdapter adapter1 = new MySpinnerAdapter(BorrowerAddActivity.this,
                android.R.layout.simple_spinner_item, MarirtalModal);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaritalStatus.setAdapter(adapter1);
        spinnerMaritalStatus.setOnItemSelectedListener(onItemSelectedListener1);
        String title = "Add Borrower";
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("EDIT");
        if (args != null) {
            title = "Edit Borrower";
            editBrw = (BorrowerModal) args.getSerializable("borrower");
            editTextFirstName.setText(editBrw.getFirstName());
            editTextLasttName.setText(editBrw.getLastName());
            editMobile.setText(editBrw.getMobileNubmer());
            editOccupation.setText(editBrw.getBorrowerOccupation());
            spinnerGender.setSelection(Integer.parseInt(editBrw.getGender()));
            spinnerMaritalStatus.setSelection(Integer.parseInt(editBrw.getMaritialStatus()));
            spinnerLine.setText(editBrw.getLineName());
            borrowerModal = editBrw;
        }
        CommonUtil.getTitleBar(this,title);
        getBorrowerList("0");
        spinnerReferred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList  ReferModal = new ArrayList<SpinnerModal>();
                for (BorrowerModal bm : b) {
                    ReferModal.add(new SpinnerModal(bm.getBorrowerId() + " - " + bm.getFirstName(), String.valueOf(bm.getId())));
                }

                Dialog dialog = ProgressUtil.spinnerProgress(BorrowerAddActivity.this);
                ListView listView = dialog.findViewById(R.id.list_view);
                MySpinnerAdapter adapter = new MySpinnerAdapter(BorrowerAddActivity.this, R.layout.custom_spinner_item, ReferModal);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinnerReferred.setText(adapter.getItem(position).getText());
                        borrowerModal.setRefferedBy(adapter.getItem(position).getValue());
                        dialog.dismiss();
                    }
                });
                EditText search = dialog.findViewById(R.id.edit_text);
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });


        getLineList("0");
        spinnerLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList ReferModal = new ArrayList<SpinnerModal>();
                for (LineModal bm : lineList) {
                    ReferModal.add(new SpinnerModal(bm.getLineName(), bm.getId()));
                }
                Dialog dialog = ProgressUtil.spinnerProgress(BorrowerAddActivity.this);
                ListView listView = dialog.findViewById(R.id.list_view);
                MySpinnerAdapter adapter = new MySpinnerAdapter(BorrowerAddActivity.this, R.layout.custom_spinner_item, ReferModal);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinnerLine.setText(adapter.getItem(position).getText());
                        borrowerModal.setLineId(adapter.getItem(position).getValue());
                        dialog.dismiss();
                    }
                });
            }
        });




        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            borrowerModal.setFirstName(editTextFirstName.getText().toString().trim());
            borrowerModal.setLastName(editTextLasttName.getText().toString().trim());
            borrowerModal.setMobileNubmer(editMobile.getText().toString().trim());
            borrowerModal.setBorrowerOccupation(editOccupation.getText().toString().trim());
          //  borrowerModal.setLineId("1");
            postData(borrowerModal);
        });
    }


    OnItemSelectedListener onItemSelectedListener0 =
            new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    SpinnerModal s = (SpinnerModal) parent.getItemAtPosition(position);
                    borrowerModal.setGender(s.getValue());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
    OnItemSelectedListener onItemSelectedListener1 =
            new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    SpinnerModal s = (SpinnerModal) parent.getItemAtPosition(position);
                    borrowerModal.setMaritialStatus(s.getValue());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };


    private void postData(BorrowerModal borrowerModal) {
        retrofitAPI = new ApiClient(this).getApiClient();
        Call call = retrofitAPI.addEditBorrower(borrowerModal);
        apiClient.getResponse(call, this);
//        call.enqueue(new Callback<ResponseModal>() {
//            @Override
//            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
//
//                if(response.code() == 200) {                // this method is called when we get response from our api.
//                    ResponseModal responseFromAPI = response.body();
//                    Toast.makeText(BorrowerAddActivity.this, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent borrowerIntent = new Intent(BorrowerAddActivity.this, BorrowerActivity.class);
//                    startActivity(borrowerIntent);
//                    finish();
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModal> call, Throwable t) {
//                Toast.makeText(BorrowerAddActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    private void getBorrowerList(String body) {
        retrofitAPI = ApiClient.getApiClient();
        Call call = retrofitAPI.getBorrower(body);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                if (response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    b = responseFromAPI.getObject();
                    if(editBrw!=null) {
                        String reffered = "";
                        for (BorrowerModal bm : b) {
                            if (String.valueOf(bm.getId()).equals(editBrw.getRefferedBy())) {
                                reffered = bm.getBorrowerId() + " - " + bm.getFirstName();
                            }
                            spinnerReferred.setText(reffered);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                Toast.makeText(BorrowerAddActivity.this, "failed added to API" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLineList(String body) {
        retrofitAPI = ApiClient.getApiClient();
        Call<LineModal> call = retrofitAPI.getLine(body);
        call.enqueue(new Callback<LineModal>() {
            @Override
            public void onResponse(Call<LineModal> call, Response<LineModal> response) {
                if (response.code() == 200) {                // this method is called when we get response from our api.
                    LineModal responseFromAPI = response.body();
                    lineList = responseFromAPI.getResult();
                }
            }

            @Override
            public void onFailure(Call<LineModal> call, Throwable t) {
                Toast.makeText(BorrowerAddActivity.this, "failed added to API" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void successAction(ResponseModal list) {
        Intent borrowerIntent = new Intent(BorrowerAddActivity.this, BorrowerActivity.class);
        startActivity(borrowerIntent);
        finish();
    }
}
