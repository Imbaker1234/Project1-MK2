package com.service;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.DAO.ErsReimbursementDAO;
import com.POJO.ErsReimbursement;
import com.POJO.Principal;

public class ErsReimbursementService {

	private static Logger log = LogManager.getLogger(ErsReimbursementService.class);
	private ErsReimbursementDAO rdao = new ErsReimbursementDAO();

	public ArrayList<ErsReimbursement> viewPastTickets(Principal user) {
		log.info("in reimb service viewPastTickets method");

		ArrayList<ErsReimbursement> pasttickets = rdao.retrieveAllReimbsByAuthor(user);
		if (pasttickets.size() == 0) {
			log.info("No past tickets");
			return null;

		}
		return pasttickets;
	}

	public ArrayList<ErsReimbursement> addReimbRequest(Principal user, ErsReimbursement reimb) {
		log.info("in reimb service addReimbRequest method");

		Boolean addedReimb = rdao.addReimbursement(reimb);
		if (addedReimb == true) {
			return rdao.dashboardDisplayPendingReimbs(user.getId(), reimb.getReimbStatusId());
		}
		return null;
	}

	public boolean approveDenyReimb(Principal user, ErsReimbursement reimb) {
		log.info("in reimb service approveDenyReimb method");

		return rdao.updateReimbursementStatus(user, reimb);

	}

	public ArrayList<ErsReimbursement> filterReimbs(String reimbStatus) {
		log.info("in reimb service approveDenyReimb method");

		String reimbStatusId = null;
		switch (reimbStatus) {
		case "Pending":
			reimbStatusId = "1";
			break;

		case "Approved":
			reimbStatusId = "2";
			break;

		case "Denied":
			reimbStatusId = "3";
			break;

		default:
			return null;
			
		}
		return rdao.retrieveAllReimbsByStatus(reimbStatusId);

	}

	public ArrayList<ErsReimbursement> viewAllReimbs() {
		log.info("in reimb service viewAllReimbs method");

		return rdao.retrieveAllReimbs();
	}

}
