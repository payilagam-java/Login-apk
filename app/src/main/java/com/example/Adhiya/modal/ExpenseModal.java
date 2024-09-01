package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ExpenseModal implements Serializable {

    @SerializedName("object")
    @Expose
    private ArrayList<ExpenseModal> result;

    public ArrayList<ExpenseModal> getResult() {
        return result;
    }

    public void setResult(ArrayList<ExpenseModal> result) {
        this.result = result;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("lineId")
    @Expose
    private String LineId;

    @SerializedName("lineName")
    @Expose
    private String LineName;


    @SerializedName("expenseReason")
    @Expose
    private String ExpenseReason;

    @SerializedName("dateOfExpense")
    @Expose
    private String DateOfExpense;

    @SerializedName("amount")
    @Expose
    private String Amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public String getExpenseReason() {
        return ExpenseReason;
    }

    public void setExpenseReason(String expenseReason) {
        ExpenseReason = expenseReason;
    }

    public String getDateOfExpense() {
        return DateOfExpense;
    }

    public void setDateOfExpense(String dateOfExpense) {
        DateOfExpense = dateOfExpense;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}