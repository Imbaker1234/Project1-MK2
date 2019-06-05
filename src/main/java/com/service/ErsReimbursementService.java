package com.service;

import com.DAO.ErsReimbursementDAO;
import com.POJO.ErsReimbursement;
import com.POJO.Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ErsReimbursementService {

	private static Logger log = LogManager.getLogger(ErsReimbursementService.class);
	private ErsReimbursementDAO rdao = new ErsReimbursementDAO();

	public ArrayList<ErsReimbursement> viewPastTickets(Principal user) {
		log.info("ErsReimbursementService : Line 17 : viewPastTickets() called");

		ArrayList<ErsReimbursement> pasttickets = rdao.retrieveAllReimbsByAuthor(user);
		if (pasttickets.size() == 0) {
			log.info("ErsReimbursementService : Line 21 : viewPastTickets() : No past tickets");
			return null;

		}
		return pasttickets;
	}

	public boolean addReimbRequest(Principal user, String amt, String desc, String type) {
		log.info("ErsReimbursementService : Line in reimb service addReimbRequest method");
		
		Double amtReformat;
		int typeReformat;
		try {
			amtReformat = Double.parseDouble(amt);
			typeReformat = Integer.parseInt(type);
			
		} catch (NumberFormatException e) {
			log.info("ErsReimbursementService : Line 38 : addReimbRequest() : NumberFormatterException : Field Invalid");
			return false;
		}

		return rdao.addReimbursement(user.getUsername(), amtReformat, desc, typeReformat);
	}

	public boolean approveDenyReimb(Principal user, String statusupdate, String reimbId) {
		log.info("ErsReimbursementService : Line 46 : approveDenyReimb() called");

		int reimbidReformat;
		try {
			reimbidReformat = Integer.parseInt(reimbId);

		} catch (NumberFormatException e) {
			log.info("ErsReimbursementService : Line 53 : approveDenyReimb() : NumberFormatterException : Field Invalid");
			return false;
		}

		return rdao.updateReimbursementStatus(user, statusupdate, reimbidReformat);

	}

	public ArrayList<ErsReimbursement> filterReimbs(String reimbStatus) {
		log.info("ErsReimbursementService : Line in reimb service approveDenyReimb method");

		int reimbStatusId;
		switch (reimbStatus) {
		case "Pending":
			reimbStatusId = 1;
			break;

		case "Approved":
			reimbStatusId = 2;
			break;

		case "Denied":
			reimbStatusId = 3;
			break;

		default:
			return null;
			
		}
		return rdao.retrieveAllReimbsByStatus(reimbStatusId);

	}

	public ArrayList<ErsReimbursement> viewAllReimbs() {
		log.info("ErsReimbursementService : Line in reimb service viewAllReimbs method");

		return rdao.retrieveAllReimbs();
	}

}
