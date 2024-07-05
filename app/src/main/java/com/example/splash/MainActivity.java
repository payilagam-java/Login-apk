package com.example.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.modal.UserModal;
import com.example.splash.repo.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    // Dummy user credentials map (replace with your actual user authentication logic)
    private Map<String, String> userCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        // Initialize user credentials (for demo purposes)
        userCredentials = new HashMap<>();
        userCredentials.put("user", "1234");
        userCredentials.put("Arivu@123", "Ad112");
        // Add more users as needed

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
  /*
                // Check if entered credentials are valid
                if (userCredentials.containsKey(enteredUsername)) {
                    if (userCredentials.get(enteredUsername).equals(enteredPassword)) {
                        // Login successful
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Navigate to BorrowerActivity
                        Intent borrowerIntent = new Intent(MainActivity.this, BorrowerActivity.class);
                        borrowerIntent.putExtra("username", enteredUsername);
                        startActivity(borrowerIntent);

                        // Finish MainActivity
                        finish();
                    } else {
                        // Incorrect password
                        Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User not found
                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                */

                postData(enteredUsername,enteredPassword);
// postData("Ad112","Arivu@123");
            }
        });
    }
    private void postData(String name, String job) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://arivukarasu-002-site1.ltempurl.com/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create(gson))
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        UserModal modal = new UserModal(name, job,"zdeftryuioplmnbhg");

        // calling a method to create a post and passing our modal class.
        Call<String> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

               if(response.code() == 200) {                // this method is called when we get response from our api.
                   String responseFromAPI = response.body();
                   Toast.makeText(MainActivity.this, "Data added to API" + response.code(), Toast.LENGTH_SHORT).show();
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
