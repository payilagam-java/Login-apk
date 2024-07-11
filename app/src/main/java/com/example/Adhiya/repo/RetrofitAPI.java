package com.example.splash.repo;


import com.example.splash.modal.ObjectModal;
import com.example.splash.modal.UserModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface RetrofitAPI {
    @POST("api/AccessToken/GetAccessToken")
    Call<String> login(@Body UserModal dataModal);


    @POST("api/Borrower/GetBorrower")
    @Headers({"Content-Type: application/json"})
    Call<ObjectModal> getBorrower(@Body String body);
    //Call<BorrowerModal> getBorrower(@Body BorrowerModal borrowerModal);
}
