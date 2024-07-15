package com.example.Adhiya.network;

import com.example.splash.repo.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
        OkHttpClient client = null;
        if(token !="") {
            com.example.splash.network.TokenInterceptor interceptor = new com.example.splash.network.TokenInterceptor(token);
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
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
