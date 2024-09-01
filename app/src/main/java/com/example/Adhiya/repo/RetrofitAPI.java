package com.example.Adhiya.repo;


import com.example.Adhiya.modal.BorrowerLoanModal;
import com.example.Adhiya.modal.BorrowerModal;
import com.example.Adhiya.modal.CollectionModal;
import com.example.Adhiya.modal.ExpenseModal;
import com.example.Adhiya.modal.LineModal;
import com.example.Adhiya.modal.LoanStatus;
import com.example.Adhiya.modal.OraganizationModal;
import com.example.Adhiya.modal.ResponseModal;
import com.example.Adhiya.modal.SendCollection;
import com.example.Adhiya.modal.UserModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface RetrofitAPI {
    @POST("api/AccessToken/GetAccessToken")
    Call<String> login(@Body UserModal dataModal);


    @POST("api/Borrower/GetBorrower")
    @Headers({"Content-Type: application/json"})
        Call<ResponseModal<BorrowerModal>> getBorrower(@Body String body);

    @POST("/api/Borrower/InsertUpdateBorrower")
    Call<ResponseModal<BorrowerModal>> addEditBorrower(@Body BorrowerModal borrowerModal);

    @POST("/api/Borrower/DeleteBorrower")
    Call<ResponseModal> deleteBorrower(@Body String body);

    @POST("api/Organization/GetOrganization")
    @Headers({"Content-Type: application/json"})
    Call<OraganizationModal> getCollection(@Body String body);

    @POST("api/Line/GetLine")
    @Headers({"Content-Type: application/json"})
    Call<LineModal> getLine(@Body String body);

    @POST("api/Loan/GetBorrowerLoan")
    @Headers({"Content-Type: application/json"})
    Call<ResponseModal<BorrowerLoanModal>> getLoan(@Body String body);

    @POST("api/Loan/InsertUpdateBorrowerLoan")
    @Headers({"Content-Type: application/json"})
    Call<ResponseModal> addEditLoan(@Body BorrowerLoanModal body);

    @POST("api/Loan/ChangeBorrowerLoanStatus")
    @Headers({"Content-Type: application/json"})
        Call<ResponseModal> loanStatusChange(@Body LoanStatus body);

    @POST("api/Collection/GetCollection")
    @Headers({"Content-Type: application/json"})
    Call<CollectionModal> getCollect(@Body SendCollection dataModal);

    @POST("api/Collection/InsertUpdateCollection")
    @Headers({"Content-Type: application/json"})
    Call<ResponseModal> payment(@Body CollectionModal CollectionModal);


    /*EXPENSE*/
    @POST("api/Collection/GetLineExpense")
    @Headers({"Content-Type: application/json"})
    Call<ExpenseModal> getExpense(@Body String dataModal);

    @POST("api/Collection/InsertUpdateExpense")
    @Headers({"Content-Type: application/json"})
    Call<ResponseModal> addEditExpense(@Body ExpenseModal body);

    @POST("api/Collection/DeleteLineExpense")
    @Headers({"Content-Type: application/json"})
    Call<ResponseModal> deleteExpense(@Body String body);
}
