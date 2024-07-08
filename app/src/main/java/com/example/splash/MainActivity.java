package com.example.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.modal.UserModal;
import com.example.splash.network.ApiClient;
import com.example.splash.repo.RetrofitAPI;

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
        postData("Ad112","Arivu@123");
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

            //    postData(enteredUsername,enteredPassword);

            }
        });
    }
    private void postData(String name, String job) {
        retrofitAPI = ApiClient.getApiLogin();
        UserModal modal = new UserModal(name, job,"zdeftryuioplmnbhg");
        Call<String> call = retrofitAPI.login(modal);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

               if(response.code() == 200) {                // this method is called when we get response from our api.
                   String responseFromAPI = response.body();
                   SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                   SharedPreferences.Editor myEdit = sharedPreferences.edit();

                   // write all the data entered by the user in SharedPreference and apply
                   myEdit.putString("token", response.body());;
                   myEdit.apply();
                   //Toast.makeText(MainActivity.this, "Data added to API" + response.body(), Toast.LENGTH_SHORT).show();
                   Intent borrowerIntent = new Intent(MainActivity.this, BorrowerActivity.class);
                   startActivity(borrowerIntent);
                   // Finish MainActivity
                   finish();
               }else{
                   JSONObject jobj = new JSONObject();
                   try {
                       jobj = new JSONObject(response.errorBody().toString());
                       Toast.makeText(MainActivity.this, "Data added to API " + jobj.get("title"), Toast.LENGTH_SHORT).show();
                   } catch (Exception e) {
                       Toast.makeText(MainActivity.this, "Something went wrong!! ", Toast.LENGTH_SHORT).show();
                   }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                //responseTV.setText("Error found is : " + t.getMessage());
                Toast.makeText(MainActivity.this, "failed added to API"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
