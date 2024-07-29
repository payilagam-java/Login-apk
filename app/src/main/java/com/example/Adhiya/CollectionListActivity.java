package com.example.Adhiya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.adapter.CollectionAdapter;
import com.example.Adhiya.adapter.LoanAdapter;
import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.DataProccessor;
import com.example.splash.Contact;
import com.example.splash.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<BorrowerLoanModal> adapter;
    private RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.ListView);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<CollectionModal> Refer = (ArrayList<CollectionModal>) args.getSerializable("collection");
        ArrayAdapter adapter = new CollectionAdapter(CollectionListActivity.this, Refer);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                Toast.makeText(CollectionListActivity.this, "Data added to API" , Toast.LENGTH_SHORT).show();

                CollectionModal cm = Refer.get(position);
                Intent intent = new Intent(CollectionListActivity.this, PaymentActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("payloan",(Serializable)cm);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
    }



}
