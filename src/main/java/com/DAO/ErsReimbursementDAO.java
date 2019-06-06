package com.DAO;

import com.POJO.ErsReimbursement;
import com.POJO.Principal;
import com.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ErsReimbursementDAO {
	
	private static Logger log = LogManager.getLogger(ErsReimbursementDAO.class);

	
	public List<ErsReimbursement> retrieveAllReimbs() {
		log.info("in reimb DAO retrieveAllReimbs method");
		List<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id";
			PreparedStatement stmt = connect.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return reimbursementlist;
	}

	public List<ErsReimbursement> retrieveAllReimbsByAuthor(Principal user) {
		log.info("in reimb DAO retrieveAllReimbsByAuthor method");
		int ersUsersId = user.getId();
		List<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? ORDER BY reimb_id";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setInt(1, ersUsersId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return null;
	}

	public List<ErsReimbursement> retrieveAllReimbsByStatus(int reimbStatusId) {
		log.info("in reimb DAO retrieveAllReimbsByStatus method");
		List<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ? ORDER BY reimb_id";
			PreparedStatement stmt = connect.prepareStatement(sql);
			stmt.setInt(1, reimbStatusId);
			ResultSet rs = stmt.executeQuery();

			return mapResultSet(reimbursementlist, rs);

		} catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	public boolean addReimbursement(String input1, Double input2, String input3, int input4) {
		log.info("in reimb DAO addReimbursement method");
		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

			String sql = "{call new_reimb(?,?,?,?)}";
			CallableStatement stmt = connect.prepareCall(sql);
			stmt.setString(1, input1);
			stmt.setDouble(2, input2);
			stmt.setString(3, input3);
			stmt.setInt(4, input4);

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return false;
	}

	public boolean updateReimbursementStatus(Principal user, int statusupdate, int reimbId) {
		log.info("in reimb DAO updateReimbursementStatus method");
		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			connect.setAutoCommit(false);

            String sql = "{ call RESOLVE_REIMB(?, ?, ?)}";
            CallableStatement stmt = connect.prepareCall(sql);
			stmt.setInt(1, statusupdate);
			stmt.setString(2, user.getUsername());
			stmt.setInt(3, reimbId);

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return false;
	}

	private List<ErsReimbursement> mapResultSet(List<ErsReimbursement> reimbursementlist, ResultSet rs) throws SQLException {
		log.info("in reimb DAO mapResultSet");
		if (rs != null) {

			while (rs.next()) {

				int reimbId = rs.getInt(1);
				Double reimbAmount = rs.getDouble(2);
				Timestamp reimbSubmitted = rs.getTimestamp(3);
				Timestamp reimbResolved = rs.getTimestamp(4);
				String reimbDescription = rs.getString(5);
				Blob reimbReceipt = rs.getBlob(6);
				int reimbAuthor = rs.getInt(7);
				int reimbResolver = rs.getInt(8);
				int reimbStatusId = rs.getInt(9);
				int reimbTypeId = rs.getInt(10);

				ErsReimbursement reimb = new ErsReimbursement(reimbId, reimbAmount, reimbSubmitted, reimbResolved,
						reimbDescription, reimbReceipt, reimbAuthor, reimbResolver, reimbStatusId, reimbTypeId);
				reimbursementlist.add(reimb);

			}
			return reimbursementlist;
		}
		return null;
	}

}
