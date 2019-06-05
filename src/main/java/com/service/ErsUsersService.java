package com.service;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;
import com.util.PasswordEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class ErsUsersService {

	private static Logger log = LogManager.getLogger(ErsUsersService.class);
	private ErsUsersDAO udao = new ErsUsersDAO();
	
	/**
	 * Checks for valid credentials and procedes to retrieve a user for the login functionality or store and then retrieve that user
	 * for the register functionality.
	 * 
	 * The login field provides 2 values and therefore if you are calling this
	 * method with 2 values then you are calling to log in
	 * 
	 * The register field provides 5 values and therefore if you are calling this
	 * method with 5 values you are intending to register
	 * 
	 * @param incoming
	 * @return
	 */

	public ErsUsers validateCredentials(String... incoming) {
		log.info("ErsUsersService: Line 32 : in ERSUsersService validateCredentials method");

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);

		for (String s : incoming) {
			if (s.equals("") || s.contains(" ") || s.length() > 25 || s.length() < 3)
				return null;
		}

		if (incoming.length == 2) {
			log.info("ErsUsersService: Line 44 : 2 arguments provided. Validating credentials for login");

			ErsUsers user = udao.getByCredentials(incoming[0], incoming[1]);
			log.info("ErsUsersService: Line 47 : ErsUsersService retrieved\n");
			return user;

		} else if (udao.getByUsername(incoming[0]) == null && pattern.matcher(incoming[4]).matches()) {
			log.info("ErsUsersService: Line 51 : 5 arguments provided. Validating credentials for registered account");

			String incUsername = incoming[0];
			String salt = PasswordEncryption.generateSalt(512).get();
			String passKey = PasswordEncryption.hashPassword(incoming[1], salt).get();
			String incFirst = incoming[2];
			String incLast = incoming[3];
			String incEmail = incoming[4];
			log.info("ErsUsersService: Line 59 : Successfully populated incoming fields from JSON");
			ErsUsers user = new ErsUsers(incUsername, passKey, incFirst, incLast, incEmail);
			boolean insert = udao.addUser(user, salt);

			if (insert) {
				log.info("ErsUsersService: Line 64 : User successfully added.");
				ErsUsers user2 = udao.getByCredentials(incoming[0], incoming[1]);
				System.out.println(user2 + user2.getErsUsername() + user2.getErsUsersId());
				return user2;
			} else {
				log.info("ErsUsersService: Line 66 : User not added.");
				return null;
			}

		}
		return null;
	}
}
