package com.service;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;

public class ErsUsersService {
	
	private ErsUsersDAO udao = new ErsUsersDAO();
	
	public ErsUsers validateCredentials() {
		String username = "Kerr007";
		String password = "Forward123";
		ErsUsers user = udao.getUserByCredentials(username, password);
		return user;
	}

}
