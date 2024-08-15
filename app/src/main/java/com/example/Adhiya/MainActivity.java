package com.example.Adhiya;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Adhiya.BorrowerActivity;
import com.example.Adhiya.modal.UserModal;
import com.example.Adhiya.util.DataProccessor;
import com.example.Adhiya.util.ProgressUtil;
import com.example.splash.R;
import com.example.Adhiya.network.ApiClient;
import com.example.Adhiya.repo.RetrofitAPI;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private RetrofitAPI retrofitAPI;
    // Dummy user credentials map (replace with your actual user authentication logic)
    private Map<String, String> userCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // postData("Ad111","arivu");
        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get entered username and password
                String enteredUsername = usernameEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();
                // Validate username and password
                if (TextUtils.isEmpty(enteredUsername)) {
                    usernameEditText.setError("Please enter your username");
                    return;
                }
                if (TextUtils.isEmpty(enteredPassword)) {
                    passwordEditText.setError("Please enter your password");
                    return;
                }
                postData(enteredUsername, enteredPassword);
            }
        });
    }

    private void postData(String name, String pass) {
        retrofitAPI = ApiClient.getApiLogin();
        UserModal modal = new UserModal(name, pass, "zdeftryuioplmnbhg");
        Call<String> call = retrofitAPI.login(modal);
        Dialog dialog = ProgressUtil.showProgress(MainActivity.this);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                if (response.code() == 200) {                // this method is called when we get response from our api.
                    String responseFromAPI = response.body();
                    new DataProccessor(MainActivity.this).SetString(responseFromAPI);
                    Intent borrowerIntent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(borrowerIntent);
                    finish();
                } else if (response.code() == 401) {
                    Toast.makeText(MainActivity.this, "Invalid Username and password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong!!" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
