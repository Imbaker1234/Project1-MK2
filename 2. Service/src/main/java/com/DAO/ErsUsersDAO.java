package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.POJO.ErsUsers;
import com.util.ConnectionFactory;

public class ErsUsersDAO {

	private static Logger log = LogManager.getLogger(ErsUsersDAO.class);

	public ErsUsers getUserByCredentials(String username, String password) {

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

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
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
