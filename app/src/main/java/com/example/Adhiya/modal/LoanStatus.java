package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanStatus {

    @SerializedName("BorrowerLoanId")
    @Expose
    private String BorrowerLoanId;

    @SerializedName("LoanStatusId")
    @Expose
    private int LoanStatusId;

    public String getBorrowerLoanId() {
        return BorrowerLoanId;
    }

    public void setBorrowerLoanId(String borrowerLoanId) {
        BorrowerLoanId = borrowerLoanId;
    }

    public int getLoanStatusId() {
        return LoanStatusId;
    }

    public void setLoanStatusId(int loanStatusId) {
        LoanStatusId = loanStatusId;
    }
}
