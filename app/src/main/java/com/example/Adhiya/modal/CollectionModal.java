package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CollectionModal implements Serializable {


    @SerializedName("object")
    @Expose
    private ArrayList<CollectionModal> result;


    public ArrayList<CollectionModal> getResult() {
        return result;
    }
    public void setResult(ArrayList<CollectionModal> result) {
        this.result = result;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("borrowerId")
    @Expose
    private String borrowerId;

    @SerializedName("borrowerName")
    @Expose
    private String borrowerName;

    @SerializedName("lineId")
    @Expose
    private int lineId;

    @SerializedName("lineName")
    @Expose
    private String lineName;

    @SerializedName("loanId")
    @Expose
    private String loanId;

    @SerializedName("amountofPaid")
    @Expose
    private String amountofPaid;

    @SerializedName("dateOfPaid")
    @Expose
    private String dateOfPaid;

    @SerializedName("loanAmount")
    @Expose
    private String loanAmount;

    @SerializedName("payAmount")
    @Expose
    private String payAmount;

    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;

    @SerializedName("payableAmount")
    @Expose
    private String payableAmount;

    @SerializedName("balance")
    @Expose
    private String balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getAmountofPaid() {
        return amountofPaid;
    }

    public void setAmountofPaid(String amountofPaid) {
        this.amountofPaid = amountofPaid;
    }

    public String getDateOfPaid() {
        return dateOfPaid;
    }

    public void setDateOfPaid(String dateOfPaid) {
        this.dateOfPaid = dateOfPaid;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
