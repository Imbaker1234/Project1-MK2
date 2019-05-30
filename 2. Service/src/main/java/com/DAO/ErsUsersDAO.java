package com.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.POJO.ErsUsers;
import com.util.ConnectionFactory;

public class ErsUsersDAO {

	//private static Logger log = LogManager.getLogger(ErsUsersDAO.class);

	public ErsUsers getUserByCredentials(String username, String password) {
		//System.out.println("in getUserByCredentials");

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(rs).get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ErsUsers getByUsername(String username) {
		//System.out.println("in getByUsername");

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(rs).get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
	public boolean addUser(ErsUsers user) {
		//System.out.println("in addUser");

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
			e.printStackTrace();
		}
		return false;
	}
	
	private List<ErsUsers> mapResultSet(ResultSet rs) throws SQLException {
		//System.out.println("in mapResultSet");
		
		if (rs != null) {
			List<ErsUsers> users = new ArrayList<>();
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
				users.add(user);
				
			}
			return users;
		}
		return null;
	}

}
