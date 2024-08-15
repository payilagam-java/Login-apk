package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BorrowerModal implements Serializable,ParentModal{


        @SerializedName("object")
        @Expose
        private ArrayList<BorrowerModal> result;


        public ArrayList<BorrowerModal> getResult() {
            return result;
        }
        public void setResult(ArrayList<BorrowerModal> result) {
            this.result = result;
        }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("borrowerId")
    @Expose
    private String borrowerId;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("maritialStatus")
    @Expose
    private String maritialStatus;

    @SerializedName("mobileNubmer")
    @Expose
    private String mobileNubmer;

    @SerializedName("borrowerStatus")
    @Expose
    private String borrowerStatus;

    @SerializedName("borrowerOccupation")
    @Expose
    private String borrowerOccupation;

    @SerializedName("refferedBy")
    @Expose
    private String refferedBy;

    @SerializedName("countryName")
    @Expose
    private String countryName;

    @SerializedName("cityName")
    @Expose
    private String cityName;

    @SerializedName("stateName")
    @Expose
    private String stateName;

    @SerializedName("lineId")
    @Expose
    private String lineId;

    @SerializedName("lineName")
    @Expose
    private String lineName;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getMobileNubmer() {
        return mobileNubmer;
    }

    public void setMobileNubmer(String mobileNubmer) {
        this.mobileNubmer = mobileNubmer;
    }

    public String getBorrowerStatus() {
        return borrowerStatus;
    }

    public void setBorrowerStatus(String borrowerStatus) {
        this.borrowerStatus = borrowerStatus;
    }

    public String getBorrowerOccupation() {
        return borrowerOccupation;
    }

    public void setBorrowerOccupation(String borrowerOccupation) {
        this.borrowerOccupation = borrowerOccupation;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
