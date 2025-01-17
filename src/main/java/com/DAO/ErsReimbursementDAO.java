package com.DAO;

import java.sql.*;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.POJO.ErsReimbursement;
import com.POJO.ErsUsers;
import com.util.ConnectionFactory;

public class ErsReimbursementDAO {
	
	private static Logger log = LogManager.getLogger(ErsReimbursementDAO.class);

	public ArrayList<ErsReimbursement> retrieveAllReimbs() {

		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement";
			PreparedStatement stmt = connect.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return reimbursementlist;
	}

	public ArrayList<ErsReimbursement> retrieveAllReimbsByAuthor(ErsUsers user) {

		String ersUsersId = user.getErsUsersId();
		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, ersUsersId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ErsReimbursement> retrieveAllReimbsByStatus(String reimbId) {

		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, reimbId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			e.printStackTrace();;
		}
		return null;
	}

	public boolean addReimbursement(ErsReimbursement reimb) {

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "INSERT INTO ers_reimbursement VALUES (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, reimb.getReimbId());
			stmt.setString(2, reimb.getReimbAmount());
			stmt.setTimestamp(3, reimb.getReimbSubmitted());
			stmt.setTimestamp(4, reimb.getReimbResolved());
			stmt.setString(5, reimb.getReimbDescription());
			stmt.setBlob(6, reimb.getReimbReceipt());
			stmt.setString(7, reimb.getReimbAuthor());
			stmt.setString(8, reimb.getReimbResolver());
			stmt.setString(9, reimb.getReimbStatusId());
			stmt.setString(10, reimb.getReimbTypeId());

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateReimbursementStatus(ErsReimbursement reimb, ErsUsers user) {

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolver = ? WHERE reimb_id = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, reimb.getReimbStatusId());
			stmt.setString(2, user.getErsUsersId());
			stmt.setString(3, reimb.getReimbId());

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private ArrayList<ErsReimbursement> mapResultSet(ArrayList<ErsReimbursement> reimbursementlist, ResultSet rs)
			throws SQLException {
		if (rs != null) {

			while (rs.next()) {

				String reimbId = rs.getString(1);
				String reimbAmount = rs.getString(2);
				Timestamp reimbSubmitted = rs.getTimestamp(3);
				Timestamp reimbResolved = rs.getTimestamp(4);
				String reimbDescription = rs.getString(5);
				Blob reimbReceipt = rs.getBlob(6);
				String reimbAuthor = rs.getString(7);
				String reimbResolver = rs.getString(8);
				String reimbStatusId = rs.getString(9);
				String reimbTypeId = rs.getString(10);

				ErsReimbursement reimb = new ErsReimbursement(reimbId, reimbAmount, reimbSubmitted, reimbResolved,
						reimbDescription, reimbReceipt, reimbAuthor, reimbResolver, reimbStatusId, reimbTypeId);
				reimbursementlist.add(reimb);

			}
			return reimbursementlist;
		}
		return null;
	}

}
