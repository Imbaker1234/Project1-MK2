package com.service;

import java.util.regex.Pattern;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;

public class ErsUsersService {

	private ErsUsersDAO udao = new ErsUsersDAO();

	public ErsUsers validateCredentials(String... incoming) {

		for (String s : incoming) {
			if (s.equals("") || s.contains(" ") || s.length() > 12 || s.length() < 2)
				return null;

		}

		if (incoming.length == 2) {
			ErsUsers user = udao.getUserByCredentials(incoming[0], incoming[1]);
			return user;

		} else if (udao.getByUsername(incoming[0]) == null) {

			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";
			Pattern pat = Pattern.compile(emailRegex);
			
			if (pat.matcher(incoming[4]).matches()) {
				ErsUsers user = new ErsUsers(incoming[0], incoming[1], incoming[2], incoming[3], incoming[4]);
				Boolean insert = udao.addUser(user);
				return insert == true ? user : null;

			}
		}
		return null;
	}

}
