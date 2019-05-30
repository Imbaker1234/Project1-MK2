package com.service;

import java.util.regex.Pattern;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;

public class ErsUsersService {

	private ErsUsersDAO udao = new ErsUsersDAO();

	public ErsUsers validateCredentials(String... incoming) {
		// System.out.println("in validateCredentials");

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);

		for (String s : incoming) {
			if (s.equals("") || s.contains(" ") || s.length() > 24 || s.length() < 2)
				return null;

		}

		if (incoming.length == 2) {
			// System.out.println("validating credentials for login");
			
			ErsUsers user = udao.getUserByCredentials(incoming[0], incoming[1]);
			return user;

		} else if (udao.getByUsername(incoming[0]) == null && pattern.matcher(incoming[4]).matches()) {
			// System.out.println("validating credentials for registered account");
			
			System.out.println("test2");
			ErsUsers user = new ErsUsers(incoming[0], incoming[1], incoming[2], incoming[3], incoming[4]);
			Boolean insert = udao.addUser(user);
			return insert == true ? user : null;

		}
		return null;
	}

}
