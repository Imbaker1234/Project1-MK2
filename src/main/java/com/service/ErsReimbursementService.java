package com.service;

import com.DAO.ErsReimbursementDAO;
import com.POJO.ErsReimbursement;
import com.POJO.Principal;
import com.POJO.ReimbPrinc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ErsReimbursementService {

	private static Logger log = LogManager.getLogger(ErsReimbursementService.class);
	private ErsReimbursementDAO rdao = new ErsReimbursementDAO();

	public List<ReimbPrinc> viewPastTickets(Principal user) {
		log.info("ErsReimbursementService : Line 17 : viewPastTickets() called");

		List<ErsReimbursement> pasttickets = rdao.retrieveAllReimbsByAuthor(user);
		
		if (pasttickets.size() == 0) {
			log.info("ErsReimbursementService : Line 21 : viewPastTickets() : No past tickets");
			return null;

		}
		return reimbToPrince(pasttickets);
	}

	public boolean addReimbRequest(Principal user, String amt, String desc, String type) {
        log.info("ErsReimbursementService : addReimbRequest() called");
		
		Double amtReformat;
		int typeReformat;
		try {
			amtReformat = Double.parseDouble(amt);
			typeReformat = Integer.parseInt(type);
			
		} catch (NumberFormatException e) {
            log.info("ErsReimbursementService : addReimbRequest() : NumberFormatterException : Field Invalid");
			return false;
		}
		return rdao.addReimbursement(user.getUsername(), amtReformat, desc, typeReformat);
	}

	public boolean approveDenyReimb(Principal user, String reimbId, String statusupdate) {
        log.info("ErsReimbursementService : approveDenyReimb() called");
        
		int reimbidReformat;
		int statusReformat = 0;
		try {
			reimbidReformat = Integer.parseInt(reimbId);
			if (statusupdate.equals("Approved")) statusReformat = 2;
			else if (statusupdate.equals("Denied")) statusReformat = 3;
			
		} catch (NumberFormatException e) {
			log.info("ErsReimbursementService : Line 53 : approveDenyReimb() : NumberFormatterException : Field Invalid");
			return false;
		}
		return rdao.updateReimbursementStatus(user, statusReformat, reimbidReformat);
	}

	public List<ReimbPrinc> filterReimbs(String reimbStatus) {
        log.info("ErsReimbursementService : filterReimbs() called");

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
		List<ErsReimbursement> pasttickets = rdao.retrieveAllReimbsByStatus(reimbStatusId);
		return reimbToPrince(pasttickets);
	}

	public List<ReimbPrinc> viewAllReimbs() {

        log.info("ErsReimbursementService : viewAllReimbs() called");
		List<ErsReimbursement> pasttickets = rdao.retrieveAllReimbs();
        log.info("Past Tickets\n" + pasttickets);
		return reimbToPrince(pasttickets);
		
	}
	
	public List<ReimbPrinc> reimbToPrince(List<ErsReimbursement> pasttickets) {

		List<ReimbPrinc> outputlist = new ArrayList<>();
		
		for (ErsReimbursement i : pasttickets) {
			int id = i.getReimbId();
			Double amt = i.getReimbAmount();
			String desc = i.getReimbDescription();
			String submit = i.getReimbSubmitted().toString().substring(0,10);
			//System.out.println(submit); //DEBUG
			String resolve = "";
			if (i.getReimbResolved() != null) {
			resolve = i.getReimbResolved().toString().substring(0,10);
			}
			String status = "";
			if (i.getReimbStatusId() == 1) status = "Pending";
			if (i.getReimbStatusId() == 2) status = "Resolved";
			if (i.getReimbStatusId() == 3) status = "Denied";
			ReimbPrinc e = new ReimbPrinc(id, amt, submit, resolve, desc, status);
			outputlist.add(e);
		}
        log.info("ErsReimbersementService: OutputList :\n" + outputlist);
		return outputlist;
	}
}
