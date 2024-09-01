package com.example.Adhiya;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.util.CommonUtil;
import com.example.Adhiya.util.Datautil;
import com.example.splash.R;

public class DashboardActivity extends AppCompatActivity {

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        CommonUtil.getTitleBar(this,"Dashboard");
        cardView1 = findViewById(R.id.borrower);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BorrowerActivity.class);
                startActivity(intent);
            }
        });

        cardView2 = findViewById(R.id.loan);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LoanActivity.class);
                startActivity(intent);
            }
        });
        cardView3 = findViewById(R.id.collection);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });
        cardView4 = findViewById(R.id.expense);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ExpenseActivity.class);
                startActivity(intent);
            }
        });

//        cardView5 = findViewById(R.id.logout);
//        cardView5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this); // Change "this" to `getActivity()` if you're using this on a fragment
//                builder.setMessage(Datautil.LOGOUT_MSG)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                new ApiClient(DashboardActivity.this).SetString("");
//                                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//
//            }
//        });
    }
}