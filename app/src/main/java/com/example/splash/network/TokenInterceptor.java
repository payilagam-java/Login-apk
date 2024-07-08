package com.example.splash.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
   public String yourtokenvalue = "";
   public TokenInterceptor(String yourtokenvalue){
       this.yourtokenvalue = yourtokenvalue;
   }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ yourtokenvalue)
                .build();

        return chain.proceed(newRequest);
    }
}