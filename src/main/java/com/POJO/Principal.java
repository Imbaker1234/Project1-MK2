package com.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public @Data
@NoArgsConstructor
@AllArgsConstructor
class Principal {
	
	private int id;
	private String username;
	private String role;

}
