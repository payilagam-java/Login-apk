package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OraganizationModal implements Serializable {


	@SerializedName("object")
	@Expose
	private ArrayList<OraganizationModal> result;


	public ArrayList<OraganizationModal> getResult() {
		return result;
	}
	public void setResult(ArrayList<OraganizationModal> result) {
		this.result = result;
	}

	@SerializedName("id")
    @Expose
	private String id;

	@SerializedName("organizationId")
    @Expose
	private String organizationId;
	
	@SerializedName("organizationName")
    @Expose
	private String organizationName;
	
	@SerializedName("organizationDescription")
    @Expose
	private String organizationDescription;
	
	@SerializedName("organizationType")
    @Expose
	private String organizationType;
	
	@SerializedName("parentId")
    @Expose
	private int parentId;
	
	@SerializedName("organizationPhoneNumber")
    @Expose
	private long organizationPhoneNumber;
	
	@SerializedName("organizationAddress")
    @Expose
	private String organizationAddress;
	
	@SerializedName("countryId")
    @Expose
	private int countryId;
	
	@SerializedName("stateName")
    @Expose
	private String stateName;
	
	@SerializedName("stateId")
    @Expose
	private int stateId;
	
	@SerializedName("cityName")
    @Expose
	private String cityName;
	
	@SerializedName("cityId")
    @Expose
	private int cityId;
	
	@SerializedName("pincode")
    @Expose
	private int pincode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationDescription() {
		return organizationDescription;
	}

	public void setOrganizationDescription(String organizationDescription) {
		this.organizationDescription = organizationDescription;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public long getOrganizationPhoneNumber() {
		return organizationPhoneNumber;
	}

	public void setOrganizationPhoneNumber(long organizationPhoneNumber) {
		this.organizationPhoneNumber = organizationPhoneNumber;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
	

}
