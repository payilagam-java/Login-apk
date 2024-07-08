package com.example.splash.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ObjectModal {

    @SerializedName("object")
    @Expose
    private BorrowerModal[] result;

    public BorrowerModal[] getResult() {
        return result;
    }

    public void setResult(BorrowerModal[] result) {
        this.result = result;
    }
}