package com.POJO;

public class ErsUsers {

	private String ersUsersId;
	private String ersUsername;
	private String ersPassword;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String userRoleId;

	public ErsUsers(String ersUsersId, String ersUsername, String ersPassword, String userFirstName,
			String userLastName, String userEmail, String userRoleId) {
		
		super();
		this.ersUsersId = ersUsersId;
		this.ersUsername = ersUsername;
		this.ersPassword = ersPassword;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userRoleId = userRoleId;
		
	}

	//THIS IS ONLY CALLED WHEN WE CREATE A NEW USER FROM THE REGISTER SCREEN
	//USER ID AND ROLE ARE APPLIED AUTOMATICALLY BY THE DATABASE.
	public ErsUsers(String ersUsername, String ersPassword, String userFirstName, String userLastName, String userEmail) {
		
		this.ersUsername = ersUsername;
		this.ersPassword = ersPassword;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		
	}

	public String getErsUsersId() {
		return ersUsersId;
	}

	public void setErsUsersId(String ersUsersId) {
		this.ersUsersId = ersUsersId;
	}

	public String getErsUsername() {
		return ersUsername;
	}

	public void setErsUsername(String ersUsername) {
		this.ersUsername = ersUsername;
	}

	public String getErsPassword() {
		return ersPassword;
	}

	public void setErsPassword(String ersPassword) {
		this.ersPassword = ersPassword;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	@Override
	public String toString() {
		return "ErsUsers [ersUsersId=" + ersUsersId + ", ersUsername=" + ersUsername + ", ersPassword=" + ersPassword
				+ ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", userEmail=" + userEmail
				+ ", userRoleId=" + userRoleId + "]";
	}

}
