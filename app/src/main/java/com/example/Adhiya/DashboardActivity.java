package com.example.Adhiya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Adhiya.util.DataProccessor;
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

        cardView5 = findViewById(R.id.logout);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataProccessor(DashboardActivity.this).SetString("");
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}