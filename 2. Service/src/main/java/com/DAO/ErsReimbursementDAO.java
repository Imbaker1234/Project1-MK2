package com.DAO;

import java.sql.*;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.POJO.ErsReimbursement;
import com.POJO.Principal;
import com.util.ConnectionFactory;

public class ErsReimbursementDAO {
	
	private static Logger log = LogManager.getLogger(ErsReimbursementDAO.class);

	
	public ArrayList<ErsReimbursement> retrieveAllReimbs() {
		log.info("in reimb DAO retrieveAllReimbs method");
		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement";
			PreparedStatement stmt = connect.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return reimbursementlist;
	}

	public ArrayList<ErsReimbursement> retrieveAllReimbsByAuthor(Principal user) {
		log.info("in reimb DAO retrieveAllReimbsByAuthor method");
		String ersUsersId = user.getId();
		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, ersUsersId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	public ArrayList<ErsReimbursement> retrieveAllReimbsByStatus(String reimbStatusId) {
		log.info("in reimb DAO retrieveAllReimbsByStatus method");
		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, reimbStatusId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			e.printStackTrace();;
		}
		return null;
	}
	
	public ArrayList<ErsReimbursement> dashboardDisplayPendingReimbs(String userId) {
		log.info("in reimb DAO dashboardDisplayPendingReimbs method");
		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ? AND reimb_author = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, "1");
			stmt.setString(2, userId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			e.printStackTrace();;
		}
		return null;
	}

	public boolean addReimbursement(String input1, String input2, String input3, String input4) {
		log.info("in reimb DAO addReimbursement method");
		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "{call new_reimb(?,?,?,?)}";
			CallableStatement stmt = connect.prepareCall(sql);
			stmt.setString(1, input1);
			stmt.setDouble(2, Double.parseDouble(input2));
			stmt.setString(3, input3);
			stmt.setInt(4, Integer.parseInt(input4));

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return false;
	}

	public boolean updateReimbursementStatus(Principal user, String statusupdate, String reimbId) {
		log.info("in reimb DAO updateReimbursementStatus method");
		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolver = ? WHERE reimb_id = ?";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setString(1, statusupdate);
			stmt.setString(2, user.getId());
			stmt.setString(3, reimbId);

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return false;
	}

	private ArrayList<ErsReimbursement> mapResultSet(ArrayList<ErsReimbursement> reimbursementlist, ResultSet rs) throws SQLException {
		log.info("in reimb DAO mapResultSet");
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
