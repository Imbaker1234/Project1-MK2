package com.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.POJO.ErsUsers;
import com.util.ConnectionFactory;

public class ErsUsersDAO {
	
	public ErsUsers getUserByCredentials(String username, String password) {
		
		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {
			
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ers_users WHERE ers_username = " + username + "AND ers_password = " + password);
			
			if (rs != null) {
			
			while (rs.next()) {
				
				String ersUsersId = rs.getString(1);
				String ersUsername = rs.getString(2);
				String ersPassword = rs.getString(3);
				String userFirstName = rs.getString(4);
				String userLastName = rs.getString(5);
				String userEmail = rs.getString(6);
				String userRoleId = rs.getString(7);
				ErsUsers user = new ErsUsers(ersUsersId, ersUsername, ersPassword, userFirstName, userLastName, userEmail, userRoleId);
				return user;
			}
			}
			return null;
			
		} catch (Exception e) {
			
		}
		return null;
	}

	
}
