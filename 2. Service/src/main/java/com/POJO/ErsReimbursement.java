package com.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class ErsReimbursement {

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
<<<<<<< HEAD

=======
	
>>>>>>> 3d15da6506159deffc83d216b357add4cd1450ae
}
