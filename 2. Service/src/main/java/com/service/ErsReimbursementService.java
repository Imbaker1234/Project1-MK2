package com.service;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.DAO.ErsReimbursementDAO;
import com.POJO.ErsReimbursement;
import com.POJO.ErsUsers;

public class ErsReimbursementService {
	
	private static Logger log = LogManager.getLogger(ErsReimbursementService.class);
	private ErsReimbursementDAO rdao = new ErsReimbursementDAO();
	
	public ArrayList<ErsReimbursement> viewPastTickets(ErsUsers user) {
		log.info("in reimb service viewPastTickets method");
		
		ArrayList<ErsReimbursement> pasttickets = rdao.retrieveAllReimbsByAuthor(user);
		if (pasttickets.size() == 0) {
			System.out.println("No past tickets");
			return null;
			
		}
		return pasttickets;
	}
	
	public ArrayList<ErsReimbursement> addReimbRequest(ErsUsers user, ErsReimbursement reimb) {
		log.info("in reimb service addReimbRequest method");
		
		Boolean addedReimb = rdao.addReimbursement(reimb);
		if (addedReimb == true) {
			return rdao.dashboardDisplayPendingReimbs(user.getErsUsersId(), reimb.getReimbStatusId());
		}
		return null;
	}
	
	public boolean approveDenyReimb(ErsUsers user, ErsReimbursement reimb) {
		log.info("in reimb service approveDenyReimb method");
		
		return rdao.updateReimbursementStatus(user, reimb);
		
	}
	
	public ArrayList<ErsReimbursement> filterReimbs(String reimbId) {
		log.info("in reimb service approveDenyReimb method");
		
		return rdao.retrieveAllReimbsByStatus(reimbId);
		
	}

}
