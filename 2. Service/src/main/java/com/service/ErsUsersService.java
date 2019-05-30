package com.service;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;

public class ErsUsersService {

	private ErsUsersDAO udao = new ErsUsersDAO();

	public ErsUsers validateCredentials(String ... incoming) {

		for (String s : incoming) {
			if (s.equals("") || s.contains(" ") || s.length() > 12) return null;
		}
		if(incoming.length == 2 ) {
		ErsUsers user = udao.getUserByCredentials(incoming[0], incoming[1]);
		return user;
		} else {
			if(udao.getByUsername(incoming[0]) == null) {
				Boolean successful = udao.addUser(new ErsUsers(incoming[0],incoming[1],incoming[2],incoming[3],incoming[4]));
				
				//TODO Test that we can create out new user.
				if(successful) {
				//TODO Implement logic that will send the new user to their dashboard.
				//Will have to retrieve "full" user from the database by using getByCredentials.
				System.out.println();
				} else {
					System.out.println("Something went wrong. Try again.");
				}
				
			} else {
				System.out.println("Username already in use.");
			}
		}
		return null;
	}

}
