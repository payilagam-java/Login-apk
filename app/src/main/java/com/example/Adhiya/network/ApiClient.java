package com.example.Adhiya.network;

import com.example.Adhiya.network.TokenInterceptor;
import com.example.Adhiya.repo.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    final static String BASEURL = "http://arivukarasu-002-site1.ltempurl.com/";
    private static Retrofit retrofit = null;
    private static RetrofitAPI retrofitAPI = null;
    public static RetrofitAPI getApiLogin(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
         retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
         return retrofitAPI;
    }

    public static RetrofitAPI getApiClient(String token){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = null;
        if(token !="") {
            TokenInterceptor interceptor = new TokenInterceptor(token);
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(logging)
                    .build();
        }
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
        return retrofitAPI;

    }
}
