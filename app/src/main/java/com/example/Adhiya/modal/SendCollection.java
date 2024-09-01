package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendCollection implements Serializable {

    private String lineId;
    private String organizationId;
    private String Collectiondate;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getCollectiondate() {
        return Collectiondate;
    }

    public void setCollectiondate(String collectiondate) {
        Collectiondate = collectiondate;
    }
}
