package com.DAO;

import com.POJO.ErsUsers;
import com.util.ConnectionFactory;
import com.util.PasswordEncryption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErsUsersDAO {

	private static Logger log = LogManager.getLogger(ErsUsersDAO.class);
	static String salt;

	public ErsUsers getByCredentials(String username, String password) {
		log.info("in ErsUsersDAO getUserByCredentials method");

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			List<ErsUsers> user = mapResultSet(rs);
			if (PasswordEncryption.verifyPassword(password, user.get(0).getErsPassword(), salt) == true) {
				return mapResultSet(rs).get(0);
			}

		} catch (Exception e) {
			log.warn(e.getMessage());
		}

		return null;
	}

	public ErsUsers getByUsername(String username) {
		log.info("in ErsUsersDAO getByUsername method");

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(rs).get(0);

		} catch (Exception e) {
			log.warn(e.getMessage());
		}

		return null;
	}

	public boolean addUser(ErsUsers user) {
		log.info("in ErsUsersDAO addUser method");

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "{call new_user(?,?,?,?,?)}";
			CallableStatement stmt = connect.prepareCall(sql);
			stmt.setString(1, user.getErsUsername());
			stmt.setString(2, user.getErsPassword());
			stmt.setString(3, user.getUserFirstName());
			stmt.setString(4, user.getUserLastName());
			stmt.setString(5, user.getUserEmail());

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return false;
	}

	private List<ErsUsers> mapResultSet(ResultSet rs) throws SQLException {
		log.info("in ErsUsersDAO mapResultSet");

		if (rs != null) {
			List<ErsUsers> users = new ArrayList<>();
			while (rs.next()) {
				int ersUsersId = rs.getInt(1);
				String ersUsername = rs.getString(2);
				String ersPassword = rs.getString(3);
				String userFirstName = rs.getString(4);
				String userLastName = rs.getString(5);
				String userEmail = rs.getString(6);
				int userRoleId = rs.getInt(7);
				salt = rs.getString(8);
				ErsUsers user = new ErsUsers(ersUsersId, ersUsername, ersPassword, userFirstName, userLastName,
						userEmail, userRoleId);
				users.add(user);

			}
			return users;
		}
		return null;
	}

}
