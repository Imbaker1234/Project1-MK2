package com.service;

import com.DAO.ErsUsersDAO;
import com.POJO.ErsUsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class ErsUsersService {

	private static Logger log = LogManager.getLogger(ErsUsersService.class);
	private ErsUsersDAO udao = new ErsUsersDAO();


    //TODO Methodize this for readability and comprehension.
    //Create a login() and register method().
    public ErsUsers validateCredentials(String... incoming) {
        log.info("in ERSUsersService validateCredentials method");
//
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        log.info("Line 23");
        Pattern pattern = Pattern.compile(emailRegex);
//        log.info("Line 25");
//        for (String s : incoming) {
//            if (s.equals("") || s.contains(" ") || s.length() < 150) return null;
//            }
//            log.info("Line 31");
//            log.info("Made it to the if/else statement");
        //The login field provides 2 values and therefore if you are calling this method with 2
        //values then you are calling to log in.
        if (incoming.length == 2) {
            log.info("validating credentials for login");

            ErsUsers user = udao.getUserByCredentials(incoming[0], incoming[1]);
            return user;

            //The register field provides 5 values and therefore if you are calling this method with
            //5 values you are intending to register.
        } else if (udao.getByUsername(incoming[0]) == null && pattern.matcher(incoming[4]).matches()) {
            log.info("validating credentials for registered account");

            String incUsername = incoming[0];
            String incPassword = incoming[1];
            String incFirst = incoming[2];
            String incLast = incoming[3];
            String incEmail = incoming[4];
            log.info("Successfully populated incoming fields from JSON");
            ErsUsers user = new ErsUsers(incUsername, incPassword, incFirst, incLast, incEmail);
            Boolean insert = udao.addUser(user);
            return insert == true ? user : null;

        }
        return null;
    }
}
