package com.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class ReimbPrinc {

	private int id;
	private double amount;
	private String submitDate;
	private String resolvedDate;
	private String description;
	private String status;

}
