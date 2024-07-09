package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ObjectModal {

    @SerializedName("object")
    @Expose
    private List<BorrowerModal> result;

    public List<BorrowerModal> getResult() {
        return result;
    }

    public void setResult(List<BorrowerModal> result) {
        this.result = result;
    }
}