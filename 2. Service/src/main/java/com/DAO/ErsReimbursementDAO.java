package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.POJO.ErsReimbursement;
import com.POJO.ErsUsers;
import com.util.ConnectionFactory;

public class ErsReimbursementDAO {

	public ArrayList<ErsReimbursement> retrieveAllReimbs() {

		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ers_reimbursement_status");

			if (rs != null) {

				while (rs.next()) {

					String reimbId = rs.getString(1);
					String reimbAmount = rs.getString(2);
					Timestamp reimbSubmitted = rs.getTimestamp(3);
					Timestamp reimbResolved = rs.getTimestamp(4);
					String reimbDescription = rs.getString(5);
					String reimbReceipt = rs.getString(6);
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

		} catch (Exception e) {

		}
		return reimbursementlist;
	}

	public ArrayList<ErsReimbursement> retrieveAllReimbsByAuthor(ErsUsers user) {

		String ersUsersId = user.getErsUsersId();
		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ers_reimbursement WHERE reimb_author = " + ersUsersId);

			if (rs != null) {

				while (rs.next()) {

					String reimbId = rs.getString(1);
					String reimbAmount = rs.getString(2);
					Timestamp reimbSubmitted = rs.getTimestamp(3);
					Timestamp reimbResolved = rs.getTimestamp(4);
					String reimbDescription = rs.getString(5);
					String reimbReceipt = rs.getString(6);
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

		} catch (Exception e) {
			System.out.println("Something interesting happened.");
		}
		return reimbursementlist;
	}

	public ArrayList<ErsReimbursement> retrieveAllReimbsByStatus(String reimbStatus) {

		ArrayList<ErsReimbursement> reimbursementlist = new ArrayList<>();

		try (Connection connect = ConnectionFactory.getInstance().getConnection()) {

			Statement stmt = connect.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM ers_reimbursement_status WHERE reimb_author = " + reimbStatus);

			if (rs != null) {

				while (rs.next()) {

					String reimbId = rs.getString(1);
					String reimbAmount = rs.getString(2);
					Timestamp reimbSubmitted = rs.getTimestamp(3);
					Timestamp reimbResolved = rs.getTimestamp(4);
					String reimbDescription = rs.getString(5);
					String reimbReceipt = rs.getString(6);
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

		} catch (Exception e) {
			System.out.println("Something interesting happened.");
		}
		return reimbursementlist;
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
			stmt.setString(6, reimb.getReimbReceipt());
			stmt.setString(7, reimb.getReimbAuthor());
			stmt.setString(8, reimb.getReimbResolver());
			stmt.setString(9, reimb.getReimbStatusId());
			stmt.setString(10, reimb.getReimbTypeId());

			stmt.executeUpdate();
			connect.commit();
			return true;

		} catch (Exception e) {
			System.out.println("Something interesting happened.");
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
			System.out.println("Something interesting happened.");
		}

		return false;
	}

}
