package com.POJO;
import java.sql.Blob;
import java.sql.Timestamp;

public class ErsReimbursement {

	private String reimbId;
	private String reimbAmount;
	private java.sql.Timestamp reimbSubmitted;
	private java.sql.Timestamp reimbResolved;
	private String reimbDescription;
	private Blob reimbReceipt;
	private String reimbAuthor;
	private String reimbResolver;
	private String reimbStatusId;
	private String reimbTypeId;

	public ErsReimbursement(String reimbId, String reimbAmount, Timestamp reimbSubmitted, Timestamp reimbResolved,
                            String reimbDescription, Blob reimbReceipt, String reimbAuthor, String reimbResolver,
                            String reimbStatusId, String reimbTypeId) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbResolved = reimbResolved;
		this.reimbDescription = reimbDescription;
		this.reimbReceipt = reimbReceipt;
		this.reimbAuthor = reimbAuthor;
		this.reimbResolver = reimbResolver;
		this.reimbStatusId = reimbStatusId;
		this.reimbTypeId = reimbTypeId;
	}

	public String getReimbId() {
		return reimbId;
	}

	public void setReimbId(String reimbId) {
		this.reimbId = reimbId;
	}

	public String getReimbAmount() {
		return reimbAmount;
	}

	public void setReimbAmount(String reimbAmount) {
		this.reimbAmount = reimbAmount;
	}

	public java.sql.Timestamp getReimbSubmitted() {
		return reimbSubmitted;
	}

	public void setReimbSubmitted(java.sql.Timestamp reimbSubmitted) {
		this.reimbSubmitted = reimbSubmitted;
	}

	public java.sql.Timestamp getReimbResolved() {
		return reimbResolved;
	}

	public void setReimbResolved(java.sql.Timestamp reimbResolved) {
		this.reimbResolved = reimbResolved;
	}

	public String getReimbDescription() {
		return reimbDescription;
	}

	public void setReimbDescription(String reimbDescription) {
		this.reimbDescription = reimbDescription;
	}

	public Blob getReimbReceipt() {
		return reimbReceipt;
	}

	public void setReimbReceipt(Blob reimbReceipt) {
		this.reimbReceipt = reimbReceipt;
	}

	public String getReimbAuthor() {
		return reimbAuthor;
	}

	public void setReimbAuthor(String reimbAuthor) {
		this.reimbAuthor = reimbAuthor;
	}

	public String getReimbResolver() {
		return reimbResolver;
	}

	public void setReimbResolver(String reimbResolver) {
		this.reimbResolver = reimbResolver;
	}

	public String getReimbStatusId() {
		return reimbStatusId;
	}

	public void setReimbStatusId(String reimbStatusId) {
		this.reimbStatusId = reimbStatusId;
	}

	public String getReimbTypeId() {
		return reimbTypeId;
	}

	public void setReimbTypeId(String reimbTypeId) {
		this.reimbTypeId = reimbTypeId;
	}

}
