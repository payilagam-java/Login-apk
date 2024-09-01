package com.example.Adhiya;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.Adhiya.adapter.CollectionAdapter;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.modal.SendCollection;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<BorrowerLoanModal> adapter;
    private RetrofitAPI retrofitAPI;
    private SendCollection sendCollection;
    ArrayList<CollectionModal> Refer;
    private FloatingActionButton addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        retrofitAPI = new ApiClient(this).getApiClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        listView = findViewById(R.id.ListView);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
      ///  ArrayList<CollectionModal> Refer = (ArrayList<CollectionModal>) args.getSerializable("collection");
        sendCollection = (SendCollection) args.getSerializable("collection");
        postData();


        addButton = findViewById(R.id.addButton);
        addButton.setVisibility(View.INVISIBLE);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
//            {
//                Toast.makeText(CollectionListActivity.this, "Data added to API" , Toast.LENGTH_SHORT).show();
//
//                CollectionModal cm = Refer.get(position);
//                Intent intent = new Intent(CollectionListActivity.this, PaymentActivity.class);
//                Bundle args = new Bundle();
//                args.putSerializable("payloan",(Serializable)cm);
//                intent.putExtra("BUNDLE",args);
//                startActivity(intent);
//            }
//        });
    }

    private void postData( ){

        Dialog dialog = ProgressUtil.showProgress(CollectionListActivity.this);

        Call<CollectionModal> call = retrofitAPI.getCollect(sendCollection);
        call.enqueue(new Callback<CollectionModal>() {
            @Override
            public void onResponse(Call<CollectionModal> call, Response<CollectionModal> response) {
                dialog.dismiss();
                if(response.code() == 200) {                // this method is called when we get response from our api.
                    CollectionModal responseFromAPI = response.body();
                    Refer = responseFromAPI.getResult();
                    ArrayAdapter adapter = new CollectionAdapter(CollectionListActivity.this, Refer,sendCollection);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<CollectionModal> call, Throwable t) {
                Toast.makeText(CollectionListActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}
