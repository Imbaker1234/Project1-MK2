package com.service;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;

public class ErsUsersService {

	private ErsUsersDAO udao = new ErsUsersDAO();

	public ErsUsers validateCredentials(String username, String password) {

		/*if (username.equals("") || password.equals("") || username.contains(" ") || password.contains(" ")) {
			return null;
			
		} else if (username.length() > 12 || password.length() > 12) {
			return null;
			
		}*/
		ErsUsers user = udao.getUserByCredentials(username, password);
		return user;
	}

}
