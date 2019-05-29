package com.service;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;

public class ErsUsersService {
	
	private ErsUsersDAO udao = new ErsUsersDAO();
	
	public ErsUsers validateCredentials() {
		String username = "CookieMonster";
		String password = "COOKIECOOKIECOOKIE!!!";
		ErsUsers user = udao.getUserByCredentials(username, password);
		return user;
	}

}
