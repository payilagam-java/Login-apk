package com.example.Adhiya.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BorrowerLoanModal implements Serializable {
	@SerializedName("object")
	@Expose
	private List<BorrowerLoanModal> loan;

	public List<BorrowerLoanModal> getLoan() {
		return loan;
	}

	public void setLoan(List<BorrowerLoanModal> result) {
		this.loan = loan;
	}
	
	@SerializedName("id")
    @Expose
	private int id;
	
	@SerializedName("borrowerId")
    @Expose
	private String borrowerId;
	
	@SerializedName("bId")
    @Expose
	private int BId;
	
	@SerializedName("borrowerName")
    @Expose
	private String borrowerName;
	
	@SerializedName("loanId")
    @Expose
	private String loanId;
	
	@SerializedName("organizationId")
    @Expose
	private int organizationId;
	
	@SerializedName("organizationName")
    @Expose
	private String organizationName;
	
	@SerializedName("lineId")
    @Expose
	private String lineId;
	
	@SerializedName("lineName")
    @Expose
	private String lineName;
	
	@SerializedName("loanAmount")
    @Expose
	private String loanAmount;
	
	@SerializedName("disbursedAmount")
    @Expose
	private String disbursedAmount;
	
	@SerializedName("payableAmount")
    @Expose
	private String payableAmount;
	
	@SerializedName("disbursedDate")
    @Expose
	private String disbursedDate;
	
	@SerializedName("loanStatusId")
    @Expose
	private int loanStatusId;
	
	@SerializedName("loanStatus")
    @Expose
	private String loanStatus;
	
	@SerializedName("lineTypeId")
    @Expose
	private int lineTypeId;
	
	@SerializedName("lineTypeName")
    @Expose
	private String lineTypeName;
	
	@SerializedName("deductionId")
    @Expose
	private int deductionId;
	
	@SerializedName("durationId")
    @Expose
	private int durationId;
	
	@SerializedName("duration")
    @Expose
	private int duration;
	
	@SerializedName("deductedAmount")
    @Expose
	private String deductedAmount;
	
	@SerializedName("interestAmount")
    @Expose
	private String interestAmount;
	
	@SerializedName("payAmount")
    @Expose
	private String payAmount;
	
	@SerializedName("interestPercentage")
    @Expose
	private double interestPercentage;
	
	@SerializedName("balanceAmount")
    @Expose
	private int balanceAmount;
	
	@SerializedName("dueDate")
    @Expose
	private String dueDate;
	
	@SerializedName("updatedDate")
    @Expose
	private String updatedDate;
	
	@SerializedName("collectionStatus")
    @Expose
	private boolean collectionStatus;

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

	public int getBId() {
		return BId;
	}

	public void setBId(int bId) {
		this.BId = bId;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
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

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getDisbursedAmount() {
		return disbursedAmount;
	}

	public void setDisbursedAmount(String disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}

	public String getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getDisbursedDate() {
		return disbursedDate;
	}

	public void setDisbursedDate(String disbursedDate) {
		this.disbursedDate = disbursedDate;
	}

	public int getLoanStatusId() {
		return loanStatusId;
	}

	public void setLoanStatusId(int loanStatusId) {
		this.loanStatusId = loanStatusId;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public int getLineTypeId() {
		return lineTypeId;
	}

	public void setLineTypeId(int lineTypeId) {
		this.lineTypeId = lineTypeId;
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

	public String getDeductedAmount() {
		return deductedAmount;
	}

	public void setDeductedAmount(String deductedAmount) {
		this.deductedAmount = deductedAmount;
	}

	public String getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public double getInterestPercentage() {
		return interestPercentage;
	}

	public void setInterestPercentage(double interestPercentage) {
		this.interestPercentage = interestPercentage;
	}

	public int getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(int balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isCollectionStatus() {
		return collectionStatus;
	}

	public void setCollectionStatus(boolean collectionStatus) {
		this.collectionStatus = collectionStatus;
	}
	
	

}
