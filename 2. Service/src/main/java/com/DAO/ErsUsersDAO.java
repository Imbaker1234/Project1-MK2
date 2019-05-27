package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.POJO.ErsUsers;
import com.util.ConnectionFactory;

public class ErsUsersDAO {

	public ErsUsers getUserByCredentials(String username, String password) {

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM ers_users WHERE ers_username = " + username + "AND ers_password = " + password);

			if (rs != null) {

				while (rs.next()) {

					String ersUsersId = rs.getString(1);
					String ersUsername = rs.getString(2);
					String ersPassword = rs.getString(3);
					String userFirstName = rs.getString(4);
					String userLastName = rs.getString(5);
					String userEmail = rs.getString(6);
					String userRoleId = rs.getString(7);
					ErsUsers user = new ErsUsers(ersUsersId, ersUsername, ersPassword, userFirstName, userLastName,
							userEmail, userRoleId);
					return user;
				}
			}
			return null;

		} catch (Exception e) {
			return null;
		}
	}

	public boolean addUser(ErsUsers user) {

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "INSERT INTO ers_users VALUES (?,?,?,?,?,?,?)";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, user.getErsUsersId());
			stmt.setString(2, user.getErsUsername());
			stmt.setString(3, user.getErsPassword());
			stmt.setString(4, user.getUserFirstName());
			stmt.setString(5, user.getUserLastName());
			stmt.setString(6, user.getUserEmail());
			stmt.setString(7, user.getUserRoleId());

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			return false;
		}

	}

}
