package com.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class ErsUsers {

    private int ersUsersId;
	private String ersUsername;
	private String ersPassword;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
    private int userRoleId;

    //This method is only called when new users are created via JSON passed in from the login page.
    public ErsUsers(String incUsername, String incPassword, String incFirst, String incLast, String incEmail) {
        this.ersUsername = incUsername;
        this.ersPassword = incPassword;
        this.userFirstName = incFirst;
        this.userLastName = incLast;
        this.userEmail = incEmail;
    }


    public String getUserRoleName(int userRoleId) {
		String rolename = "";
		switch (userRoleId) {
            case 1:
                rolename = "employee";
                break;
            case 2:
                rolename = "admin";
                break;
		}
		return rolename;
	}

}
