package com.example.Adhiya.network;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.Adhiya.MainActivity;
import com.example.Adhiya.NoInternetActivity;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.repo.RetrofitAPI;
import com.example.Adhiya.util.ActionUtil;
import com.example.Adhiya.util.Datautil;
import com.example.Adhiya.util.ProgressUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Context context;


    public ApiClient(Context context) {
        this.context = context;
    }

    public static String getString(String key) {
        SharedPreferences prefs = context.getSharedPreferences(Datautil.PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void SetString(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Datautil.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("token", token);
        myEdit.apply();
    }

    public static String getToken() {
        return getString("token");
    }

    private static Retrofit retrofit = null;
    private static RetrofitAPI retrofitAPI = null;

    public static RetrofitAPI getApiLogin() {
        if (!DetectConnection.checkInternetConnection(context)) {
            Intent i = new Intent(context, NoInternetActivity.class);
            context.startActivity(i);

        } else {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Datautil.BASEURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            retrofitAPI = retrofit.create(RetrofitAPI.class);
        }
        return retrofitAPI;
    }

    public static RetrofitAPI getApiClient() {
        if (!DetectConnection.checkInternetConnection(context)) {
            Intent i = new Intent(context, NoInternetActivity.class);
            context.startActivity(i);

        } else {

        String token = getToken();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = null;
        if (token != "") {
            TokenInterceptor interceptor = new TokenInterceptor(token);
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(logging)
                    .build();
        }
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Datautil.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
        }
        return retrofitAPI;
    }

    public static void getResponse(Call call, ActionUtil actionUtil) {
        Dialog dialog = ProgressUtil.showProgress(context);
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(Call<ResponseModal> call, Response<ResponseModal> response) {
                if (response.code() == 200) {                // this method is called when we get response from our api.
                    ResponseModal responseFromAPI = response.body();
                    if(!(responseFromAPI.getMessage().toString().equals("Fetched data Succesfully")) && !(responseFromAPI.getMessage().toString().equals("Fetched data Successfully"))) {
                        Toast.makeText(context, responseFromAPI.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (responseFromAPI.getStatus() == 1) {
                        if(actionUtil!=null)
                            actionUtil.successAction(responseFromAPI);
                    }
                } else {
                    Toast.makeText(context, "API Response code " + response.code(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModal> call, Throwable t) {
                dialog.dismiss();
                //Toast.makeText(context, "API Faiure due to " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
