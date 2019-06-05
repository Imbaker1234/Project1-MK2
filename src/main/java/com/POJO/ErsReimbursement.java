package com.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class ErsReimbursement {

	private int reimbId;
	private Double reimbAmount;
	private java.sql.Timestamp reimbSubmitted;
	private java.sql.Timestamp reimbResolved;
	private String reimbDescription;
	private Blob reimbReceipt;
	private int reimbAuthor;
	private int reimbResolver;
	private int reimbStatusId;
	private int reimbTypeId;

}
