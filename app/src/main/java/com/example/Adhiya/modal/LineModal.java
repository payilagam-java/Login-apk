package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineModal {
	
	@SerializedName("id")
    @Expose
	private int id;
	
	@SerializedName("lineId")
    @Expose
	private String lineId;
	
	@SerializedName("lineName")
    @Expose
	private String lineName;
	
	@SerializedName("lineDescription")
    @Expose
	private String lineDescription;
	
	@SerializedName("lineRoute")
    @Expose
	private String lineRoute;
	
	@SerializedName("lineType")
    @Expose
	private int lineType;
	
	@SerializedName("lineTypeName")
    @Expose
	private String lineTypeName;
	
	@SerializedName("deductionId")
    @Expose
	private int deductionId;
	
	@SerializedName("deductionAmount")
    @Expose
	private double deductionAmount;
	
	@SerializedName("isBeforeInterestDeduction")
    @Expose
	private boolean isBeforeInterestDeduction;
	
	@SerializedName("durationId")
    @Expose
	private int durationId;
	
	@SerializedName("duration")
    @Expose
	private int duration;
	
	@SerializedName("interest")
    @Expose
	private double interest;
	
	@SerializedName("dayOfLine")
    @Expose
	private String dayOfLine;
	
	@SerializedName("organizationId")
    @Expose
	private int organizationId;
	
	@SerializedName("organizationName")
    @Expose
	private String organizationName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getLineDescription() {
		return lineDescription;
	}

	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}

	public String getLineRoute() {
		return lineRoute;
	}

	public void setLineRoute(String lineRoute) {
		this.lineRoute = lineRoute;
	}

	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public String getLineTypeName() {
		return lineTypeName;
	}

	public void setLineTypeName(String lineTypeName) {
		this.lineTypeName = lineTypeName;
	}

	public int getDeductionId() {
		return deductionId;
	}

	public void setDeductionId(int deductionId) {
		this.deductionId = deductionId;
	}

	public double getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public boolean isBeforeInterestDeduction() {
		return isBeforeInterestDeduction;
	}

	public void setBeforeInterestDeduction(boolean isBeforeInterestDeduction) {
		this.isBeforeInterestDeduction = isBeforeInterestDeduction;
	}

	public int getDurationId() {
		return durationId;
	}

	public void setDurationId(int durationId) {
		this.durationId = durationId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public String getDayOfLine() {
		return dayOfLine;
	}

	public void setDayOfLine(String dayOfLine) {
		this.dayOfLine = dayOfLine;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	

}
